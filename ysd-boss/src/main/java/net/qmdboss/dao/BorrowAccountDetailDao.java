package net.qmdboss.dao;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.BorrowAccountDetail;

import java.util.Map;

public interface BorrowAccountDetailDao extends BaseDao<BorrowAccountDetail, Integer>  {

	public Pager findPager(Pager pager,Map<String,Object> map);
}
