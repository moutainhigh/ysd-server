package net.qmdboss.dao;

import net.qmdboss.entity.Listing;

import java.math.BigDecimal;
import java.util.List;

/**
 * Dao接口 - 列表分类
 * @author xsf
 *
 */

public interface ListingDao extends BaseDao<Listing, Integer> {
	
	/**
	 * 判断标识是否存在（不区分大小写）
	 * 
	 * @param sign
	 *            标识
	 * 
	 */
	public boolean isExistBySign(String sign);
	
	/**
	 * 根据标识获取文章分类
	 * 
	 * @param sign
	 *            标识
	 * 
	 */
	public Listing getListingBySign(String sign);
	
	/**
	 * 获取文章分类树集合
	 * 
	 * @return 文章分类树集合
	 * 
	 */
	public List<Listing> getListingTree();
	
	/**
	 * 获取顶级文章分类集合
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 顶级文章分类集合
	 * 
	 */
	public List<Listing> getRootListingList(Integer maxResults);
	
	/**
	 * 根据Listing对象获取所有父类集合,若无父类则返回null
	 * 
	 * @param Listing
	 *            文章分类,null表示无限制
	 *            
	 * @param isContainParent
	 *            是否包含所有父分类
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 父类集合
	 * 
	 */
	public List<Listing> getParentListingList(Listing Listing, boolean isContainParent, Integer maxResults);
	
	/**
	 * 根据Listing对象获取所有子类集合,若无子类则返回null
	 * 
	 * @param Listing
	 *            文章分类,null表示无限制
	 *            
	 * @param isContainChildren
	 *            是否包含所有子分类
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<Listing> getChildrenListingList(Listing Listing, boolean isContainChildren, Integer maxResults);

	
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
	public String findChildListingByKeyValue(String sign,String keyValue);
	
	
	
	/**
	 * 根据父级sign 和子级name查询子级keyValue
	 * @param map
	 * @return
	 */
	public String findChildListingByname(String sign,String name);

	/**
	 * 统计邀请好友总收益
	 * @param id
	 * @return
	 */
	public BigDecimal findSumMoneyBySpread(Integer id);
	
	
	
}