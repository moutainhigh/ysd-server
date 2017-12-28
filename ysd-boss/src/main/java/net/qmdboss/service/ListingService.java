package net.qmdboss.service;

import net.qmdboss.entity.Listing;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service接口 - 分类
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXF631FF5629197454FEED5CB4F84F0ED2
 * ============================================================================
 */

public interface ListingService extends BaseService<Listing, Integer> {
	
	/**
	 * 判断标识是否存在（不区分大小写）
	 * 
	 * @param Sign
	 *            标识
	 * 
	 */
	public boolean isExistBySign(String sign);
	
	/**
	 * 判断标识是否唯一（不区分大小写）
	 * 
	 * @param oldSign
	 *            旧标识
	 *            
	 * @param newSign
	 *            新标识
	 * 
	 */
	public boolean isUniqueBySign(String oldSign, String newSign);
	
	/**
	 * 根据标识获取Listing对象
	 * 
	 * @param Sign
	 *            Sign
	 * 
	 */
	public Listing getListingBySign(String sign);
	
	/**
	 * 获取分类树集合
	 * 
	 * @return 分类树集合
	 * 
	 */
	public List<Listing> getListingTree();
	
	/**
	 * 获取分类树集合;
	 * 
	 * @return 分类树集合
	 * 
	 */
	public List<Listing> getListingTreeList();
	
	/**
	 * 获取顶级分类集合
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 顶级分类集合
	 * 
	 */
	public List<Listing> getRootListingList(Integer maxResults);
	
	/**
	 * 根据Listing对象获取所有父类集合,若无父类则返回null
	 * 
	 * @param Listing
	 *            Listing
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
	 *            Listing
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
	 * 根据Listing对象获取路径集合
	 * 
	 * @param Listing
	 *            Listing
	 * 
	 * @return Listing路径集合
	 * 
	 */
	public List<Listing> getListingPathList(Listing listing);
	
	
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
	 * 统计邀请好友的总收益
	 * @param id
	 * @return
	 */
	public BigDecimal findSumMoneyBySpread(Integer id);
	

}