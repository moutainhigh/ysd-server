package com.qmd.service.impl.borrow;

import com.qmd.dao.borrow.BorrowDaoService;
import com.qmd.dao.borrow.BorrowRechargeDao;
import com.qmd.dao.borrow.BorrowRepaymentDetailDaoService;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowRecharge;
import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.service.borrow.BorrowRechargeService;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.util.Pager;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("borrowRechargeService")
public class BorrowRechargeServiceImpl extends BaseServiceImpl<BorrowRecharge, Integer>
		implements BorrowRechargeService {
	
	
	@Resource
	private BorrowRechargeDao borrowRechargeDao;
	@Resource
	private BorrowDaoService borrowDaoService;
	
	@Resource
	BorrowRepaymentDetailDaoService borrowRepaymentDetailDaoService;

	@Resource
	public void setBaseDao(BorrowRechargeDao borrowRechargeDao) {
		super.setBaseDao(borrowRechargeDao);
	}
	
	public Pager queryPagerBorrowRecharge(Pager page,Map<String,Object> qMap) {
		return borrowRechargeDao.queryPagerBorrowRecharge(page, qMap);
	}
	
	
	public int saveBorrowRecharge(BorrowRecharge bean) {
		
		BorrowRepaymentDetail borrowRepaymentDetail = borrowRepaymentDetailDaoService.getForUpdate(bean.getBorrowRepaymentId());
		
		Borrow borrow = borrowDaoService.getBorrowById(borrowRepaymentDetail.getBorrowId());
		
		Map<String,Object> md = new HashMap<String,Object>();
		md.put("borrowId", borrowRepaymentDetail.getBorrowId());
		md.put("id", bean.getBorrowRepaymentId());
		int end = 0;
		Integer last = borrowRepaymentDetailDaoService.queryRepaymentNextCount(md);
		if(last==null || last==0) {
			end = 1;
		}
		
		if(borrowRepaymentDetail.getRechargeStatus()==null||borrowRepaymentDetail.getRechargeStatus()==0) {
			bean.setStatus(2);
			bean.setBorrowId(borrowRepaymentDetail.getBorrowId());
			bean.setUserId(borrow.getUserId());
			bean.setType(1);
			bean.setEndFlg(end);
			borrowRechargeDao.save(bean);
		}
		
		
		if(borrowRepaymentDetail.getRechargeStatus()==1||borrowRepaymentDetail.getRechargeStatus()==2) {
			return 1;
		}
		
		if(borrowRepaymentDetail.getRechargeStatus()==3) {
			
			Map<String,Object> mm = new HashMap<String,Object>();
			mm.put("borrowRepaymentId", borrowRepaymentDetail.getId());
			mm.put("status", 3);
			mm.put("orderBy", "t.id desc");
			List<BorrowRecharge> brlist = borrowRechargeDao.queryListByMap(mm);
			if(brlist!=null && brlist.size()>0) {
				for(BorrowRecharge temp:brlist) {
					BorrowRecharge old = borrowRechargeDao.getForUpdate(temp.getId());
					
					BorrowRecharge up = new BorrowRecharge();
					up.setId(old.getId());
					up.setStatus(0);
					borrowRechargeDao.update(up);
				}
			}
			bean.setStatus(2);
			bean.setBorrowId(borrowRepaymentDetail.getBorrowId());
			bean.setUserId(borrow.getUserId());
			bean.setType(1);
			bean.setEndFlg(end);
			borrowRechargeDao.save(bean);
		}
		
		BorrowRepaymentDetail entity = new BorrowRepaymentDetail();
		entity.setId(bean.getBorrowRepaymentId());
		entity.setRechargeStatus(2);
		
		borrowRepaymentDetailDaoService.baseUpdate(entity);
		
		return 0;
	}

	public int updateByAgency(Integer id,String ip){
		
		BorrowRecharge bean = borrowRechargeDao.getById(id);
		if(bean == null){
			return 0;
		}
		bean.setStatus(4);
		bean.setVerifyRemark("主动撤销");
		borrowRechargeDao.update(bean);
		BorrowRepaymentDetail entity = new BorrowRepaymentDetail();
		entity.setId(bean.getBorrowRepaymentId());
		entity.setRechargeStatus(4);
		
		borrowRepaymentDetailDaoService.update(entity);
		
		return 1;
	}
	
}
