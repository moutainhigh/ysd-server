package net.qmdboss.service.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.PlaceChilderDao;
import net.qmdboss.entity.PlaceChilder;
import net.qmdboss.service.PlaceChilderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("placeChilderServiceImpl")
public class PlaceChilderServiceImpl extends BaseServiceImpl<PlaceChilder, Integer> implements PlaceChilderService {
	@Resource(name = "placeChilderDaoImpl")
	private PlaceChilderDao placeChilderDao;

	@Resource(name = "placeChilderDaoImpl")
	public void setBaseDao(PlaceChilderDao placeChilderDao) {
		super.setBaseDao(placeChilderDao);
	}
	

	public Pager findPager(Pager pager,Map<String,Object> map){
		return placeChilderDao.findPager(pager, map);
	}
}
