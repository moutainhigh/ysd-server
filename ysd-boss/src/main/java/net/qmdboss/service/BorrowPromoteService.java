package net.qmdboss.service;

import net.qmdboss.entity.Admin;
import net.qmdboss.entity.Borrow;

public interface BorrowPromoteService extends BaseService<Borrow, Integer> {

	/**
	 * 初审
	 * 
	 * @param borrow
	 * @param id
	 * @param admin
	 * @return 0 审核成功,1状态不对
	 */
	public int updateBorrowPreliminary(Borrow borrow, Integer id, Admin admin);
	
	/**
	 * 满标审核
	 * @param borrow
	 * @param id
	 * @param admin
	 * @return
	 */
	public int updateBorrowFull(Borrow borrow, Integer id, Admin admin);
	
	
	/**
	 * 新的满标审核
	 * @param borrow
	 * @param id
	 * @param admin
	 * @return
	 * @author zdl
	 */
	public int updateBorrowFullNew(Borrow borrow, Integer id, Admin admin);
	
	
	
	/**
	 * 体验标满标审核
	 * @param borrow
	 * @param id
	 * @param admin
	 * @return
	 */
	public int updateTiYanBorrowFull(Borrow borrow, Integer id, Admin admin);

}
