package net.qmdboss.dao;


import net.qmdboss.bean.Pager;
import net.qmdboss.entity.BorrowRecharge;


/**
 * Dao接口 BorrowRecharge
 * @author Administrator
 *
 */
public interface BorrowRechargeDao extends BaseDao<BorrowRecharge, Integer> {

//	/**
//	 * 
//	 * 
//	 */
//	public boolean isExistByUsername(String username);
//	
//	/**
//	 * 
//	 * 
//	 */
//	public Borrow getBorrowByUsername(String username);
//	
//	
	/**
	 * 根据borrowRecharge和Pager对象,获取此分类下的标分页对象（）
	 * 
	 * @param borrowRecharge
	 *            标属性
	 *            
	 * @param pager
	 *            分页对象
	 * 
	 * @return Pager
	 */
	public Pager getBorrowRechargePager(BorrowRecharge borrowRecharge, Pager pager);
//	
//	
//	/**
//	 * 根据borrow和Pager对象,获取过期标分页对象
//	 * @param borrow
//	 * @param borrowId
//	 * @param pager
//	 * @return
//	 */
//	public Pager getOutDateBorrowPager(Borrow borrow,Integer borrowId, Pager pager);
//	
//	
//	
//	/**
//	 * 查询借款总金额 -xsf
//	 * @param startDate
//	 * @param endDate
//	 * @return
//	 */
//	public BigDecimal getAccountYesTotal(Date startDate,Date endDate);
}
