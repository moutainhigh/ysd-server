package com.qmd.service.impl.util;

import com.qmd.dao.util.ListingDao;
import com.qmd.mode.util.Listing;
import com.qmd.mode.util.Scrollpic;
import com.qmd.mode.util.Setting;
import com.qmd.service.util.ListingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("listingService")
public class ListingServiceImpl implements ListingService{

	@Resource
	private ListingDao listingDao;

	public List<Listing> getChildListingBySignList(String sign ){
		return listingDao.getChildListingBySignList(sign);
	}
	
	public String findChildListingByKeyValue(Map<String,Object> map){
		return listingDao.findChildListingByKeyValue(map);
	}
	
	public ListingDao getListingDao() {
		return listingDao;
	}

	public void setListingDao(ListingDao listingDao) {
		this.listingDao = listingDao;
	}
	
	public List<Scrollpic> findScrollpicList(){
		return listingDao.findScrollpicList();
	}

	@Override
	public Listing findListing(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return listingDao.findListing(map);
	}

	public Setting getSetting(){
		return listingDao.getSetting();
	}

	@Override
	public Listing getListing(Integer id) {
		// TODO Auto-generated method stub
		return listingDao.getListing(id);
	}
	
	
	public String getKeyValue(String sign) {
		return listingDao.getKeyValue(sign);
	}
	
}
