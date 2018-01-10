package net.qmdboss.service;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.PlaceChilder;

import java.util.Map;

public interface PlaceChilderService extends BaseService<PlaceChilder, Integer> {


	public Pager findPager(Pager pager,Map<String,Object> map);
}
