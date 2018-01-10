package net.qmdboss.dao;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.PlaceChilder;

import java.util.Map;

public interface PlaceChilderDao extends BaseDao<PlaceChilder, Integer> {


	public Pager findPager(Pager pager,Map<String,Object> map);
}
