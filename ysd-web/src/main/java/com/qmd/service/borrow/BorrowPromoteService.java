package com.qmd.service.borrow;

import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.User;
import com.qmd.service.BaseService;

public interface BorrowPromoteService extends BaseService<BorrowTender,Integer> {

	/**
	 * 用户投标
	 *  @return 0 投标成功,1已满标,2可用余额不足,3续投余额不足，4 标的状态不对（非1）
	 */
	public int borrowInvestDo(User user,BorrowTender borrowTender,String ip,Integer[] hongbaoId);//用户投标
	
	public int borrowInvestDoHNew(User user,BorrowTender borrowTender,String ip,Integer[] hongbaoId);//用户投标

	
}
