package net.qmdboss.service;

import net.qmdboss.entity.Admin;
import net.qmdboss.entity.Borrow;

public interface BorrowWanderService extends BaseService<Borrow, Integer> {

	/**
	 * 流转标初审通过
	 * 
	 * @param borParam
	 * @param id
	 * @param admin
	 * @return 0通过,1状态不对
	 */
	public int updateBorrowWander(Borrow borParam,Integer id, Admin admin);
}
