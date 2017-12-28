package net.qmdboss.service;

import net.qmdboss.entity.Areashop;

import java.util.List;

/**
 * Service接口 - 地区
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX0093A02DC48CD73BDC71C06BA52A9AD2
 * ============================================================================
 */

public interface AreashopService extends BaseService<Areashop, Integer> {
	
	/**
	 * 根据ID获取下级地区
	 * 
	 * @param id
	 *            ID,若为null则获取顶级地区
	 * 
	 * @return 下级地区
	 * 
	 */
	public List<Areashop> getAreaList(Integer id);
	
	/**
	 * 获取所有顶级地区集合
	 * 
	 * @return 所有顶级地区集合
	 * 
	 */
	public List<Areashop> getRootAreaList();
	
	/**
	 * 根据Area对象获取所有上级地区集合,若无上级地区则返回null
	 * 
	 * @return 上级地区集合
	 * 
	 */
	public List<Areashop> getParentAreaList(Areashop area);
	
	/**
	 * 根据Area对象获取所有子类集合,若无子类则返回null
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<Areashop> getChildrenAreaList(Areashop area);

}