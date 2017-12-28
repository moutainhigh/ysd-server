package com.qmd.dao.util;

import com.qmd.dao.BaseDao;
import com.qmd.mode.util.Listing;
import com.qmd.mode.util.Scrollpic;
import com.qmd.mode.util.Setting;

import java.util.List;
import java.util.Map;

public interface ListingDao extends BaseDao<Listing,Integer>{
	
	/**
	 * 根据标识Key 获取子级列表
	 * @param sign
	 * @return
	 */
	public List<Listing> getChildListingBySignList(String sign );
	
	/**
	 * 根据父级sign 和子级keyValue查询子级name
	 * @param map
	 * @return
	 */
	public String findChildListingByKeyValue(Map<String,Object> map);

	/**
	 * 查询首页图片滚动
	 * @return
	 */
	public List<Scrollpic> findScrollpicList();
	

	/**
	 * 通过id获取Listing对象
	 * @param id
	 * @return
	 */
	public Listing getListing (Integer id);
	
	/**
	 * 根据条件查询Listing对象
	 * @param map
	 * @return
	 */
	public Listing findListing(Map<String,Object> map);
	
	
	/**
	 * 获取网站基本配置
	 * @return
	 */
	public Setting getSetting();

	/**
	 * 根据sign取得key
	 * @param sign
	 * @return
	 */
	public String getKeyValue(String sign);
}
