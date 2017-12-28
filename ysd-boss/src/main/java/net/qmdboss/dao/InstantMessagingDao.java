package net.qmdboss.dao;

import net.qmdboss.entity.InstantMessaging;
import net.qmdboss.entity.InstantMessaging.InstantMessagingType;

import java.util.List;

/**
 * Dao接口 - 在线客服
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX28061A4E3AB2BF6EE590DF6B934079B7
 * ============================================================================
 */

public interface InstantMessagingDao extends BaseDao<InstantMessaging, Integer> {

	/**
	 * 根据在线客服类型、最大结果数获取在线客服集合
	 *            
	 * @param instantMessagingType
	 *            在线客服类型,null表示无限制
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 在线客服集合
	 */
	public List<InstantMessaging> getInstantMessagingList(InstantMessagingType instantMessagingType, Integer maxResults);

}