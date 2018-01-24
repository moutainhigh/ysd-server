package net.qmdboss.service;


import net.qmdboss.bean.Pager;
import net.qmdboss.entity.Admin;
import net.qmdboss.entity.Borrow;
import net.qmdboss.entity.UserAward;

import java.math.BigDecimal;
import java.util.Date;



public interface BorrowService extends BaseService<Borrow, Integer> {

	/**
	 * 根据用户名判断此用户是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByUsername(String username);
	
	/**
	 * 根据用户名获取会员对象,若会员不存在,则返回null（不区分大小写）
	 * 
	 */
	public Borrow getBorrowByUsername(String username);
	
	public Pager getBorrowPage( Borrow borrow ,Pager pager);
	
	/**
	 * 初审
	 * @param borrow
	 * @param id
	 * @param admin
	 * @return 0 审核成功,1状态不对
	 */
	public int updateBorrowPreliminary(Borrow borrow,Integer id, Admin admin);
	
	/**
	 * 获取过期
	 * @param borrow
	 * @param borrowId
	 * @param pager
	 * @return
	 */
	public Pager getOutDateBorrowPage(Borrow borrow ,Integer borrowId ,Pager pager);
	
	/**
	 * 满标审核不通过，回滚投资人投资金额
	 * @param borrow
	 * @param id
	 * @param admin
	 * 
	 * @return 0 通过，1状态不对
	 */
	public int updateBorrowBack(Borrow borrow ,Integer id,Admin admin);
	
	/**
	 * 撤回标 ，若有投资记录回滚资金
	 * @param id
	 * @param admin
	 * @return
	 */
	public boolean withdrawBorrow(Integer id,Admin admin);
	
	
	
	/**
	 * 查询借款总金额 -xsf
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public BigDecimal getAccountYesTotal(Date startDate,Date endDate);
	
	
	public void updateBorrow(Borrow borrow ,Integer id,Admin admin);

}
