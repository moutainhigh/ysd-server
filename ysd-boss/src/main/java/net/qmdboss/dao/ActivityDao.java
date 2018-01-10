package net.qmdboss.dao;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.Activity;

import java.util.Map;

public interface ActivityDao extends BaseDao<Activity,Integer> {
	
	public Pager findPager(Pager pager, Map<String, Object> map);
}