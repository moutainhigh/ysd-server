package net.qmdboss.dao;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.Place;

import java.util.Map;

public interface PlaceDao extends BaseDao<Place, Integer> {


	public Pager findPager(Pager pager,Map<String,Object> map);
}
