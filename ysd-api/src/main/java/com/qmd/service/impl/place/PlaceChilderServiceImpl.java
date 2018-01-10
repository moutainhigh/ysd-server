package com.qmd.service.impl.place;

import com.qmd.dao.place.PlaceChilderDao;
import com.qmd.mode.place.PlaceChilder;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.place.PlaceChilderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("placeChilderService")
public class PlaceChilderServiceImpl extends BaseServiceImpl<PlaceChilder,Integer> implements PlaceChilderService {

	@Resource
	private PlaceChilderDao placeChilderDao;
	@Resource
	public void setBaseDao(PlaceChilderDao placeChilderDao){
		super.setBaseDao(placeChilderDao);
	}
	
	
	
	
	
}
