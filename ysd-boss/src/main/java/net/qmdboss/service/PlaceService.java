package net.qmdboss.service;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.Place;

import java.util.Map;

public interface PlaceService extends BaseService<Place, Integer> {
	
	public Pager findPager(Pager pager,Map<String,Object> map);

}
