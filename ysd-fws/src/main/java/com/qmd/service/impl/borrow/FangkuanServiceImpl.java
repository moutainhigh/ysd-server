package com.qmd.service.impl.borrow;

import com.qmd.bean.BorrowFangkuanBean;
import com.qmd.dao.borrow.BorrowAccountDetailDao;
import com.qmd.dao.borrow.BorrowDaoService;
import com.qmd.dao.borrow.FangkuanDao;
import com.qmd.dao.user.UserDao;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowAccountDetail;
import com.qmd.mode.borrow.Fangkuan;
import com.qmd.mode.user.User;
import com.qmd.service.borrow.FangkuanService;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.util.BorrowAccountDetailUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.Pager;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("fangkuanService")
public class FangkuanServiceImpl extends BaseServiceImpl<Fangkuan,Integer> implements FangkuanService  {


	@Resource
	FangkuanDao fangkuanDao;

	@Resource
	BorrowDaoService borrowDao;
	@Resource
	BorrowAccountDetailDao borrowAccountDetailDao;
	@Resource
	UserDao userDao;

	@Resource
	public void setBaseDao(FangkuanDao fangkuanDao) {
		super.setBaseDao(fangkuanDao);
	}

	public Pager findBorrowFangkuanPager(Map<String,Object> qMap,Pager pager){
		return fangkuanDao.findBorrowFangkuanPager(qMap,pager);
	}
	
	public BorrowFangkuanBean getBorrowFangkuan(Integer id){
		return fangkuanDao.getBorrowFangkuan(id);
	}

	public Integer updateByAgency(Integer id,String ip){
		Fangkuan fk = fangkuanDao.getForUpdate(id);//放款申请记录
		if(fk.getStatus().compareTo(2) !=0){
			return 0;
		}

		//项目放款状态更改状态
		Borrow b = borrowDao.getForUpdate(fk.getBorrowId());
		if(b.getFangkuanStatus().compareTo(2) !=0){
			return 0;
		}
		fk.setVerifyTime(new Date());
		fk.setVerifyRemark("手动撤回");
		fk.setStatus(0);
		fk.setIp(ip);
		fangkuanDao.update(fk);

		BorrowAccountDetail bad1 = BorrowAccountDetailUtil.fillBorrowAccountDetailUtil("borrower_fangkuan_apply_fail", fk.getFangkuanMoney(), fk.getUserId(), fk.getAgencyId(), "借款人放款申请手动撤回", ip, b);
		borrowAccountDetailDao.save(bad1);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("borrowId", fk.getBorrowId());
		map.put("type", 2);
		map.put("status", 2);
		List<Fangkuan> fkList = fangkuanDao.queryListByMap(map);
		if(fkList != null && fkList.size()==1){
			Fangkuan bzj_fk = fangkuanDao.getForUpdate(fkList.get(0).getId());
			bzj_fk.setVerifyTime(new Date());
			bzj_fk.setVerifyRemark("手动撤回");
			bzj_fk.setStatus(0);
			bzj_fk.setIp(ip);
			fangkuanDao.update(bzj_fk);

			BorrowAccountDetail bad2 = BorrowAccountDetailUtil.fillBorrowAccountDetailUtil("deposit_fangkuan_apply_fail", bzj_fk.getFangkuanMoney(), fk.getUserId(), fk.getAgencyId(), "保证金放款申请手动撤回", ip, b);
			borrowAccountDetailDao.save(bad2);
		}
		
		map.put("type", 3);
		fkList = fangkuanDao.queryListByMap(map);
		if(fkList != null && fkList.size()==1){
			Fangkuan fee_fk = fangkuanDao.getForUpdate(fkList.get(0).getId());
			fee_fk.setVerifyTime(new Date());
			fee_fk.setVerifyRemark("手动撤回");
			fee_fk.setStatus(0);
			fee_fk.setIp(ip);
			fangkuanDao.update(fee_fk);
			BorrowAccountDetail bad3 = BorrowAccountDetailUtil.fillBorrowAccountDetailUtil("fee_fangkuan_apply_fail", fee_fk.getFangkuanMoney(), fk.getUserId(), fk.getAgencyId(), "手续费放款申请手动撤回", ip, b);
			borrowAccountDetailDao.save(bad3);
		}
		
		b.setFangkuanStatus(0);//未放款
		borrowDao.update(b);
		return 1;
	}
	
	public synchronized Integer saveFangkuan(Integer id,String ip){
		try {
			Borrow b =  borrowDao.getForUpdate(id);
			if(b.getFangkuanStatus().compareTo(0) !=0){
				return 0;
			}
			BigDecimal fee = BigDecimal.ZERO;
			
			if(b.getFeeType().compareTo(1) ==0){
				fee = b.getFeeMoney();
			}else{
				fee = CommonUtil.setPriceScale( new BigDecimal(b.getAccount()).multiply(new BigDecimal(b.getPartAccount())).divide(new BigDecimal(100)));
			}
			BorrowFangkuanBean bean = fangkuanDao.getBorrowFangkuan(id);
			User u = userDao.getById(b.getUserId());
			Fangkuan fk =new Fangkuan();
			fk.setType(1);
			fk.setAgencyId(b.getAgencyId());
			fk.setUserId(u.getId());
			fk.setBorrowId(b.getId());
			BigDecimal fangkuanMoney = new BigDecimal(b.getAccount()).subtract(b.getDepositMoney()).subtract(fee);
			fk.setFangkuanMoney(fangkuanMoney);
			
			fk.setStatus(2);
			fk.setIp(ip);
			fk.setBankCard(bean.getBankCard());
			fk.setBankBranch(bean.getBankBranch());
			fangkuanDao.save(fk);
			
			BorrowAccountDetail bad = BorrowAccountDetailUtil.fillBorrowAccountDetailUtil("borrower_fangkuan_apply_ing", fk.getFangkuanMoney(), u.getId(), u.getInviteUserid(), "借款人放款申请", ip, b);
			borrowAccountDetailDao.save(bad);
			
			
			if(b.getDepositMoney().compareTo(BigDecimal.ZERO) >0){
				Fangkuan bzj_fangkuan = new Fangkuan();//保证金
				bzj_fangkuan.setAgencyId(b.getAgencyId());
				bzj_fangkuan.setUserId(u.getId());
				bzj_fangkuan.setBorrowId(b.getId());
				bzj_fangkuan.setStatus(2);
				bzj_fangkuan.setIp(ip);
				bzj_fangkuan.setType(2);
				bzj_fangkuan.setFangkuanMoney(b.getDepositMoney());
				fangkuanDao.save(bzj_fangkuan);

				BorrowAccountDetail bad1 = BorrowAccountDetailUtil.fillBorrowAccountDetailUtil("deposit_fangkuan_apply_ing", b.getDepositMoney(), u.getId(), u.getInviteUserid(), "保证金放款申请", ip, b);
				borrowAccountDetailDao.save(bad1);
			}
			if(fee.compareTo(BigDecimal.ZERO) >0){
				Fangkuan fee_fangkuan = new Fangkuan();//服务费
				fee_fangkuan.setAgencyId(b.getAgencyId());
				fee_fangkuan.setUserId(u.getId());
				fee_fangkuan.setBorrowId(b.getId());
				fee_fangkuan.setStatus(2);
				fee_fangkuan.setIp(ip);
				fee_fangkuan.setType(3);
				fee_fangkuan.setFangkuanMoney(fee);
				fangkuanDao.save(fee_fangkuan);
				BorrowAccountDetail bad2 = BorrowAccountDetailUtil.fillBorrowAccountDetailUtil("fee_fangkuan_apply_ing", fee, u.getId(), u.getInviteUserid(), "手续费放款申请", ip, b);
				borrowAccountDetailDao.save(bad2);
			}
			b.setFangkuanStatus(2);//放款申请中
			borrowDao.update(b);

		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
}
