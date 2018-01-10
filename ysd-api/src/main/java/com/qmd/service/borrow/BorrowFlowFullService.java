package com.qmd.service.borrow;

import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.User;
import com.qmd.service.BaseService;

/**
 * 流转标--到期还本息方式
 */
public interface BorrowFlowFullService extends BaseService<BorrowTender,Integer> {

	/**
	 * 流转标投标
	 * @param user
	 * @param borrowTender
	 * @param ip
	 * @param orderNum
	 * @return 0 投标成功,1已满标,2可用余额不足,3续投余额不足
	 */
	public int borrowInvestDo(User user,BorrowTender borrowTender,String ip);//用户投标
	
}
