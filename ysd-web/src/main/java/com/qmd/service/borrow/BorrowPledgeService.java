package com.qmd.service.borrow;

import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.user.User;
import com.qmd.service.BaseService;

public interface BorrowPledgeService extends BaseService<Borrow, Integer> {

	/**
	 * 添加表
	 * 
	 * @param borrow
	 */
	public void insertBorrow(Borrow borrow);// 添加borrrow值进数据库
	
	/**
	 * 投标
	 * @param user 用户（投资者）
	 * @param investMoney 投资金额
	 * @param bId 标ID
	 * @param ip 操作者IP
	 * @return
	 */
	public int insertBorrowInvest(User user, String investMoney, Integer bId,String ip);

}
