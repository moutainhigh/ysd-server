package net.qmdboss.service.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.PlaceDao;
import net.qmdboss.entity.Place;
import net.qmdboss.service.PlaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("placeServiceImpl")
public class PlaceServiceImpl  extends BaseServiceImpl<Place, Integer> implements PlaceService {
	
	@Resource(name = "placeDaoImpl")
	private PlaceDao placeDao;
	
	@Resource(name = "placeDaoImpl")
	public void setBaseDao(PlaceDao placeDao) {
		super.setBaseDao(placeDao);
	}
	

	public Pager findPager(Pager pager,Map<String,Object> map){
		return placeDao.findPager(pager,map);
	}
}
