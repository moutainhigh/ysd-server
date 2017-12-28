package net.qmdboss.service;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.Activity;

import java.util.Map;

public interface ActivityService extends BaseService<Activity,Integer> {
	
	public Pager findPager(Pager pager, Map<String, Object> map);
}