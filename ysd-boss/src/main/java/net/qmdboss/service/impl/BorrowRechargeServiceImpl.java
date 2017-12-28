package net.qmdboss.service.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.*;
import net.qmdboss.entity.*;
import net.qmdboss.service.BorrowRechargeService;
import net.qmdboss.util.BorrowAccountDetailUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Service实现类
 * 
 * @author Administrator
 * 
 */
@Service("borrowRechargeServiceImpl")
public class BorrowRechargeServiceImpl extends BaseServiceImpl<BorrowRecharge, Integer>
		implements BorrowRechargeService {
	Logger log = Logger.getLogger(BorrowRechargeServiceImpl.class);
	
	@Resource(name = "borrowRechargeDaoImpl")
	private BorrowRechargeDao borrowRechargeDao;
	
	@Resource(name = "borrowDaoImpl")
	private BorrowDao borrowDao;
	@Resource(name = "borrowRepaymentDetailDaoImpl")
	private BorrowRepaymentDetailDao borrowRepaymentDetailDao;
	@Resource(name = "borrowAccountDetailDaoImpl")
	private BorrowAccountDetailDao borrowAccountDetailDao;
	@Resource(name = "settingDaoImpl")
	private SettingDao settingDao;
	
	@Resource(name = "borrowRechargeDaoImpl")
	public void setBaseDao(BorrowRechargeDao borrowRechargeDao) {
		super.setBaseDao(borrowRechargeDao);
	}
	
	public int updateBorrowRechargeOK(Integer id,Integer borrowId,Integer borrowRepaymentDetailId,BorrowRecharge bean,String ip) {
		BorrowRecharge borrowRecharge = borrowRechargeDao.loadLock(id);
		Borrow borrow = borrowDao.loadLock(borrowId);
		BorrowRepaymentDetail borrowRepaymentDetail = null;
		if(borrowRecharge.getType()!=null && borrowRecharge.getType()==1 ) {
			borrowRepaymentDetail = borrowRepaymentDetailDao.loadLock(borrowRepaymentDetailId);
		}
		
		if(borrowRecharge.getStatus()==null||borrowRecharge.getStatus()!=2) {
			return 1;
		}
		if(borrowRecharge.getMoney()==null||borrowRecharge.getMoney().compareTo(BigDecimal.ZERO)<0) {
			return 2;
		}
		
		borrow.setBorrowMoney( (borrow.getBorrowMoney()==null?BigDecimal.ZERO:borrow.getBorrowMoney()).add(borrowRecharge.getMoney()));
		borrowDao.update(borrow);
		
		borrowRecharge.setStatus(1);
		
		borrowRecharge.setVerifyTime(new Date());
		borrowRecharge.setVerifyAdmin(bean.getVerifyAdmin());
		borrowRecharge.setVerifyPhone(bean.getVerifyPhone());
		borrowRecharge.setVerifyRemark(bean.getVerifyRemark());
		
		borrowRechargeDao.update(borrowRecharge);
		
		if(borrowRepaymentDetail!=null) {
			borrowRepaymentDetail.setRechargeStatus(1);
			borrowRepaymentDetailDao.update(borrowRepaymentDetail);
		}
		
		if(borrowRecharge.getType()==1&& borrowRecharge.getEndFlg()!=null && borrowRecharge.getEndFlg()==1 ) {
			
			if(borrow.getDepositMoney()!=null && borrow.getDepositMoney().compareTo(BigDecimal.ZERO)>0) {
			
				BorrowRecharge bzj = new BorrowRecharge();
				bzj.setType(2);
				bzj.setUser(borrowRecharge.getUser());
				bzj.setAgency(borrowRecharge.getAgency());
				bzj.setBorrow(borrowRecharge.getBorrow());
				bzj.setEndFlg(0);
				bzj.setMoney(borrow.getDepositMoney()==null?BigDecimal.ZERO:borrow.getDepositMoney());// decimal(15,2) NULL 金额
				
				bzj.setRechargeName("保证金账户");
				bzj.setRechargeDate(new Date());
				bzj.setRechargeType(1);
				
				bzj.setRechargeAccount("--"); 
				bzj.setRechargeBank("--");
	
				bzj.setStatus(2);// int(10) NULL 状态 
				
				borrowRechargeDao.save(bzj);
			}
		}
		
		String optType ="borrow_recharge_money";
		String temp ="还款充值";
		if(borrowRecharge.getType()!=null && borrowRecharge.getType()==2) {
			optType ="borrow_recharge_deposit";
			temp ="退回保证金";
			 
			Setting setting = settingDao.loadLock(1);
			setting.setDepositMoney(setting.getDepositMoney().subtract(borrowRecharge.getMoney()));
			settingDao.update(setting);
		}
		
		
		BorrowAccountDetail accountLog = BorrowAccountDetailUtil.fillBorrowAccountDetailUtil(optType, borrowRecharge.getMoney(), borrowRecharge.getUser(), borrowRecharge.getAgency(),temp, ip, borrowRecharge.getBorrow());
		borrowAccountDetailDao.save(accountLog);
		
		return 0;
		
	}
	
	public int updateBorrowRechargeNG(Integer id,Integer borrowRepaymentDetailId,BorrowRecharge bean) {
		BorrowRecharge borrowRecharge = borrowRechargeDao.loadLock(id);
		BorrowRepaymentDetail borrowRepaymentDetail = null;
		if(borrowRecharge.getType()!=null && borrowRecharge.getType()==1 ) {
			borrowRepaymentDetail = borrowRepaymentDetailDao.loadLock(borrowRepaymentDetailId);
		}
		
		if(borrowRecharge.getStatus()==null||borrowRecharge.getStatus()!=2) {
			return 1;
		}
		
		borrowRecharge.setStatus(3);
		
		borrowRecharge.setVerifyTime(new Date());
		borrowRecharge.setVerifyAdmin(bean.getVerifyAdmin());
		borrowRecharge.setVerifyPhone(bean.getVerifyPhone());
		borrowRecharge.setVerifyRemark(bean.getVerifyRemark());
		
		borrowRechargeDao.update(borrowRecharge);
		
		if(borrowRepaymentDetail!=null) {
			borrowRepaymentDetail.setRechargeStatus(3);
			borrowRepaymentDetailDao.update(borrowRepaymentDetail);
		}
		
		return 0;
		
	}

	

	@Override
	public Pager getBorrowRechargePage(BorrowRecharge borrowRecharge,  Pager pager) {
		// TODO Auto-generated method stub
		return borrowRechargeDao.getBorrowRechargePager(borrowRecharge, pager);
	}

	private Criteria getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
