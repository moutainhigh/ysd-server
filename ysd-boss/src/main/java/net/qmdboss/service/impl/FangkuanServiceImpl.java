package net.qmdboss.service.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.*;
import net.qmdboss.entity.*;
import net.qmdboss.service.AdminService;
import net.qmdboss.service.FangkuanService;
import net.qmdboss.util.BorrowAccountDetailUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Service("fangkuanServiceImpl")
public class FangkuanServiceImpl extends BaseServiceImpl<Fangkuan, Integer> implements FangkuanService {

	
	@Resource(name = "fangkuanDaoImpl")
	private FangkuanDao fangkuanDao;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "settingDaoImpl")
	private SettingDao settingDao;
	@Resource(name = "userAccountDaoImpl")
	private UserAccountDao userAccountDao;
	@Resource(name = "userAccountDetailDaoImpl")
	private UserAccountDetailDao userAccountDetailDao;
	@Resource(name = "borrowDaoImpl")
	private BorrowDao borrowDao;
	@Resource(name = "borrowAccountDetailDaoImpl")
	private BorrowAccountDetailDao borrowAccountDetailDao;
	
	@Resource(name = "fangkuanDaoImpl")
	public void setBaseDao(FangkuanDao fangkuanDao) {
		super.setBaseDao(fangkuanDao);
	}
	
	public Pager findPager(Pager pager,Map<String,Object> map){
		return fangkuanDao.findPager(pager, map);
	}
	
	public Integer verify(Fangkuan fangkuan,String ip){
		Fangkuan fk = fangkuanDao.loadLock(fangkuan.getId());
		Admin admin =adminService.getLoginAdmin();
		Borrow borrow = borrowDao.loadLock(fk.getBorrow().getId());
		
		
		if(fangkuan.getStatus().compareTo(1)==0){
			if(fk.getType().compareTo(1)==0){//借款人放款
				
				if(borrow.getFangkuanStatus().compareTo(2) !=0){
					return 0;
				}
				
				//借款人账户增加
				UserAccount ua =  userAccountDao.loadLockTable(fk.getUser());
				ua.setTotal(ua.getTotal().add(fk.getFangkuanMoney()));
				ua.setAbleMoney(ua.getAbleMoney().add(fk.getFangkuanMoney()));
				this.userAccountDao.update(ua);

				// 资金记录
//				UserAccountDetail userAccountDetail = InterestCalUtil.saveAccountDetail("fk_success",//
//						fk.getFangkuanMoney(),//
//						"放款成功", //
//						fk.getUser(),//
//						ua,//
//						0,//
//						admin.getName(),//
//						admin.getLoginIp());
//				userAccountDetailDao.save(userAccountDetail);
				//项目余额减少
				borrow.setBorrowMoney(BigDecimal.ZERO);
				borrow.setFangkuanStatus(1);
				borrowDao.update(borrow);
				
				BorrowAccountDetail bad1 = BorrowAccountDetailUtil.fillBorrowAccountDetailUtil("borrower_fangkuan_apply_success", fk.getFangkuanMoney(), fk.getUser(), fk.getAgency(), "借款人放款申请成功", ip, borrow);
				borrowAccountDetailDao.save(bad1);
			
			}else{
				if(borrow.getFangkuanStatus().compareTo(1) !=0){
					return 2;
				}
				
				Setting setting = settingDao.get(1);
				String str="";
				if(fk.getType().compareTo(2) ==0){
					setting.setDepositMoney(setting.getDepositMoney().add(fk.getFangkuanMoney()));
					str="deposit_fangkuan_apply_success";
				}else if(fk.getType().compareTo(3) ==0){
					setting.setFeeMoney(setting.getFeeMoney().add(fk.getFangkuanMoney()));
					str="fee_fangkuan_apply_success";
				}
				settingDao.update(setting);
				
				BorrowAccountDetail bad1 = BorrowAccountDetailUtil.fillBorrowAccountDetailUtil(str, fk.getFangkuanMoney(), fk.getUser(), fk.getAgency(), "借款人放款申请成功", ip, borrow);
				borrowAccountDetailDao.save(bad1);
			}
		}else{//审核失败
			
			if(fk.getType().compareTo(1)==0){//借款人放款
				if(borrow.getFangkuanStatus().compareTo(2) !=0){
					return 0;
				}

				borrow.setBorrowMoney(borrow.getAccount());
				borrow.setFangkuanStatus(0);
				borrowDao.update(borrow);

				BorrowAccountDetail bad1 = BorrowAccountDetailUtil.fillBorrowAccountDetailUtil("borrower_fangkuan_apply_fail", fk.getFangkuanMoney(), fk.getUser(), fk.getAgency(), "借款人放款申请审核失败", ip, borrow);
				borrowAccountDetailDao.save(bad1);
				
			}else{
				if(borrow.getFangkuanStatus().compareTo(1) !=0){
					return 2;
				}
				String str="";
				if(fk.getType().compareTo(2) ==0){
					str="deposit_fangkuan_apply_fail";
				}else if(fk.getType().compareTo(3) ==0){
					str="fee_fangkuan_apply_fail";
				}
				
				BorrowAccountDetail bad1 = BorrowAccountDetailUtil.fillBorrowAccountDetailUtil(str, fk.getFangkuanMoney(), fk.getUser(), fk.getAgency(), "借款人放款申请审核失败", ip, borrow);
				borrowAccountDetailDao.save(bad1);
				
			}
		}
		fk.setStatus(fangkuan.getStatus());
		fk.setVerifyAdmin(admin.getName());
		fk.setVerifyPhone(admin.getPhone());
		fk.setVerifyRemark(fangkuan.getVerifyRemark());
		fk.setVerifyTime(new Date());
		fk.setVerifyIp(ip);
		fangkuanDao.update(fk);
		return 1;
	}
}
