package net.qmdboss.service;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.BorrowRecharge;

public interface BorrowRechargeService extends
		BaseService<BorrowRecharge, Integer> {

	public Pager getBorrowRechargePage(BorrowRecharge borrowRecharge,
			Pager pager);
	
	public int updateBorrowRechargeOK(Integer id,Integer borrowId,Integer borrowRepaymentDetailId,BorrowRecharge bean,String ip);
	
	public int updateBorrowRechargeNG(Integer id,Integer borrowRepaymentDetailId,BorrowRecharge bean);

}
