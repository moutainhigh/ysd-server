package net.qmdboss.service;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.BorrowAccountDetail;

import java.util.Map;

public interface BorrowAccountDetailService extends BaseService<BorrowAccountDetail, Integer> {

	public Pager findPager(Pager pager,Map<String,Object> map);
}
