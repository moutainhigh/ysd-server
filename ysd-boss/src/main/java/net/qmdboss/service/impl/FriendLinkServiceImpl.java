package net.qmdboss.service.impl;

import net.qmdboss.dao.FriendLinkDao;
import net.qmdboss.entity.FriendLink;
import net.qmdboss.service.FriendLinkService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service实现类 - 友情链接
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXD710B9580A96A2B957C605ADE37619E2
 * ============================================================================
 */

@Service("friendLinkServiceImpl")
public class FriendLinkServiceImpl extends BaseServiceImpl<FriendLink, Integer> implements FriendLinkService {
	
	@Resource(name = "friendLinkDaoImpl")
	FriendLinkDao friendLinkDao;

	@Resource(name = "friendLinkDaoImpl")
	public void setBaseDao(FriendLinkDao friendLinkDao) {
		super.setBaseDao(friendLinkDao);
	}
	
	@Cacheable(modelId = "friendLinkCaching")
	@Transactional(readOnly = true)
	public List<FriendLink> getFriendLinkList(String type, Integer maxResults) {
		List<FriendLink> friendLinkList = friendLinkDao.getFriendLinkList(type, maxResults);
		if (friendLinkList != null) {
			for (FriendLink friendLink : friendLinkList) {
				if (Hibernate.isInitialized(friendLink)) {
					Hibernate.initialize(friendLink);
				}
			}
		}
		return friendLinkList;
	}

	@Override
	@CacheFlush(modelId = "friendLinkFlushing")
	public void delete(FriendLink friendLink) {
		friendLinkDao.delete(friendLink);
	}

	@Override
	@CacheFlush(modelId = "friendLinkFlushing")
	public void delete(Integer id) {
		friendLinkDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "friendLinkFlushing")
	public void delete(Integer[] ids) {
		friendLinkDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "friendLinkFlushing")
	public Integer save(FriendLink friendLink) {
		return friendLinkDao.save(friendLink);
	}

	@Override
	@CacheFlush(modelId = "friendLinkFlushing")
	public void update(FriendLink friendLink) {
		friendLinkDao.update(friendLink);
	}

}