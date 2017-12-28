package com.qmd.service.area;

import com.qmd.mode.area.Area;
import com.qmd.service.BaseService;

import java.util.List;


/**
 * Service接口 - 地区
 */

public interface AreaService extends BaseService<Area, String> {
	
	/**
	 * 根据ID获取下级地区
	 * 
	 * @param id
	 *            ID,若为null则获取顶级地区
	 * 
	 * @return 下级地区
	 * 
	 */
	public List<Area> getAreaList(String id);
	
	/**
	 * 获取所有顶级地区集合
	 * 
	 * @return 所有顶级地区集合
	 * 
	 */
	public List<Area> getRootAreaList();
	
	/**
	 * 根据Area对象获取所有上级地区集合,若无上级地区则返回null
	 * 
	 * @return 上级地区集合
	 * 
	 */
	public List<Area> getParentAreaList(Area area);
	
	/**
	 * 根据Area对象获取所有子类集合,若无子类则返回null
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<Area> getChildrenAreaList(String pid);
	
	
	/**
	 * 查找 domain
	 * @param id
	 * @return
	 */
	public String getAreaDomain(String id);
	
	/**
	 * 查找name
	 * @param id
	 * @return
	 */
	public String getAreaName(String id);

}