package com.qmd.dao.impl.util;

import com.qmd.dao.util.ListingDao;
import com.qmd.mode.util.Hongbao;
import com.qmd.mode.util.Listing;
import com.qmd.mode.util.Scrollpic;
import com.qmd.mode.util.Setting;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("listingDao")
public class ListingDaoImpl  extends SqlSessionDaoSupport  implements ListingDao {

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
	
	public List<Scrollpic> findScrollpicListMap(Map<String, Object> map){
		List<Scrollpic> scrollpicList = this.getSqlSession().selectList("Listing.findScrollpicList",map);
		return scrollpicList;
	}


	@Override
	public Listing getListing(Integer id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("Listing.getListing",id);
	}
	
	public Listing getListingBysign(String sign) {
		return this.getSqlSession().selectOne("Listing.getListingBysign",sign);
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
	
	@Override
	public String getKeyValue(String sign) {
		return this.getSqlSession().selectOne("Listing.getKeyValue",sign);
	}

	@Override
	public List<Listing> findChildListingBySing(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<Listing> liList = this.getSqlSession().selectList("Listing.findChildListingBySing",map);
		return liList; 
	}

	@Override
	public String findListingByKeyValue(String keyValue) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("Listing.findListingByKeyValue",keyValue);
	}
	
	public Hongbao getHongbao(Integer type){
		return this.getSqlSession().selectOne("Listing.getHongbao",type);
	}
	
	public String selectSumHomeData(Map<String, Object> map){
		return this.getSqlSession().selectOne("Listing.selectSumHomeData",map);
	}
}
