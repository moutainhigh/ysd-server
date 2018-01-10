package com.qmd.dao.util;

import com.qmd.mode.util.Hongbao;
import com.qmd.mode.util.Listing;
import com.qmd.mode.util.Scrollpic;
import com.qmd.mode.util.Setting;

import java.util.List;
import java.util.Map;

public interface ListingDao {
	
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
	 * 根据子级keyValue和查询父级name
	 * @param map
	 * @return
	 */
	public String findListingByKeyValue(String  keyValue);
	
	/**
	 * 根据父级sign查询子级
	 * @param map
	 * @return
	 */
	public List<Listing> findChildListingBySing(Map<String,Object> map);

	/**
	 * 查询首页图片滚动
	 * @return
	 */
	public List<Scrollpic> findScrollpicList(Integer type);
	

	/**
	 * 通过id获取Listing对象
	 * @param id
	 * @return
	 */
	public Listing getListing (Integer id);
	
	/**
	 * 通过sign获取Listing对象
	 * @param sign
	 * @return
	 */
	public Listing getListingBysign(String sign);
	
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
	
	/**
	 * 通过type获取系统红包设置
	 * @param id
	 * @return
	 */
	public Hongbao getHongbao(Integer type);
	
	/**
	 * 通过Map获取首页局部数据
	 * @param id
	 * @return
	 */
	public String selectSumHomeData(Map<String,Object> map);
}
