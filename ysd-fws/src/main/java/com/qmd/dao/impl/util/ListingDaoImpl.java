package com.qmd.dao.impl.util;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.util.Listing;
import com.qmd.mode.util.Scrollpic;
import com.qmd.mode.util.Setting;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("listingDao")
public class ListingDaoImpl  extends BaseDaoImpl<Listing, Integer>  implements ListingDao {

	//getListingBySignList
	@Override
	public List<Listing> getChildListingBySignList(String sign){
		List<Listing> listingList = this.getSqlSession().selectList("Listing.getChildListingBySignList",sign.toLowerCase());
		return listingList;
	}

	public String findChildListingByKeyValue(Map<String,Object> map){
		String name = this.getSqlSession().selectOne("Listing.findChildListingByKeyValue",map);
		return name;
	}

	public List<Scrollpic> findScrollpicList(){
		List<Scrollpic> scrollpicList = this.getSqlSession().selectList("Listing.findScrollpicList");
		return scrollpicList;
	}


	@Override
	public Listing getListing(Integer id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("Listing.getListing",id);
	}

	@Override
	public Listing findListing(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Listing listing = this.getSqlSession().selectOne("Listing.getObjecListing",map);
		return listing;
	}

	public Setting getSetting(){
		Setting setting = this.getSqlSession().selectOne("Listing.getSetting");
		return setting;
	}
	
	public String getKeyValue(String sign) {
		return this.getSqlSession().selectOne("Listing.getKeyValue",sign);
	}
	
	
	
}
