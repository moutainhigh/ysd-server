package net.qmdboss.service.impl;

import net.qmdboss.dao.DeliveryTypeDao;
import net.qmdboss.entity.DeliveryType;
import net.qmdboss.service.DeliveryTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service实现类 - 配送方式
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX368AEE64E9CC4A9CED772AB694BC4162
 * ============================================================================
 */

@Service("deliveryTypeServiceImpl")
public class DeliveryTypeServiceImpl extends BaseServiceImpl<DeliveryType, Integer> implements DeliveryTypeService {

	@Resource(name = "deliveryTypeDaoImpl")
	DeliveryTypeDao deliveryTypeDao;
	
	@Resource(name = "deliveryTypeDaoImpl")
	public void setBaseDao(DeliveryTypeDao deliveryTypeDao) {
		super.setBaseDao(deliveryTypeDao);
	}
	
	@Override
	@Cacheable(modelId = "deliveryTypeCaching")
	@Transactional(readOnly = true)
	public List<DeliveryType> getAllList() {
		return deliveryTypeDao.getAllList();
	}

	@Override
	@CacheFlush(modelId = "deliveryTypeFlushing")
	public void delete(DeliveryType deliveryType) {
		deliveryTypeDao.delete(deliveryType);
	}

	@Override
	@CacheFlush(modelId = "deliveryTypeFlushing")
	public void delete(Integer id) {
		deliveryTypeDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "deliveryTypeFlushing")
	public void delete(Integer[] ids) {
		deliveryTypeDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "deliveryTypeFlushing")
	public Integer save(DeliveryType deliveryType) {
		return deliveryTypeDao.save(deliveryType);
	}

	@Override
	@CacheFlush(modelId = "deliveryTypeFlushing")
	public void update(DeliveryType deliveryType) {
		deliveryTypeDao.update(deliveryType);
	}

}