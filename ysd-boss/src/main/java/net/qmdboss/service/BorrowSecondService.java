package net.qmdboss.service;

import net.qmdboss.entity.Admin;
import net.qmdboss.entity.Borrow;

public interface BorrowSecondService extends BaseService<Borrow, Integer> {

	/**
	 * 满标审核(秒标)
	 * 
	 * @param borrow
	 * @param id
	 * @param admin
	 * @return
	 * 
	 * @return 0审核通过,1状态不对,2还款利息大于借款人可用余额
	 */
	public int updateBorrowSecondFull(Borrow borrow, Integer id, Admin admin);
}
