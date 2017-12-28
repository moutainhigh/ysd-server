package net.qmdboss.service;

import net.qmdboss.entity.Admin;
import net.qmdboss.entity.Borrow;

public interface BorrowPledgeService extends BaseService<Borrow, Integer> {

	/**
	 * 满标审核(质押标)
	 * 
	 * @param borrow
	 * @param id
	 * @param admin
	 * @return 0审核通过,1状态不对
	 */
	public int updateBorrowPledgeFull(Borrow borrow, Integer id, Admin admin);
}
