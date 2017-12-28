package net.qmdboss.service;

import net.qmdboss.entity.Admin;
import net.qmdboss.entity.Borrow;

public interface BorrowMonthService extends BaseService<Borrow, Integer> {

	/**
	 * 满标审核月标
	 * @param borrow
	 * @param id
	 * @param admin
	 * @return 0审核通过,状态不对
	 */
	public int updateBorrowMonthFull(Borrow borrow, Integer id, Admin admin);
}
