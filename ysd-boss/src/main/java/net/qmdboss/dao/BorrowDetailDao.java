package net.qmdboss.dao;

import net.qmdboss.entity.Borrow;
import net.qmdboss.entity.BorrowDetail;

import java.util.List;
import java.util.Map;

public interface BorrowDetailDao extends BaseDao<BorrowDetail, Integer> {

	public List<BorrowDetail> getBorrowDetailList(Borrow borrow,Map<String,String> map);
}
