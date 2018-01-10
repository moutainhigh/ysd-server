package net.qmdboss.service.impl;

import net.qmdboss.dao.DeliveryItemDao;
import net.qmdboss.entity.DeliveryItem;
import net.qmdboss.service.DeliveryItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Service实现类 - 发货项
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX5CCDCA53AF8463D621530B1ADA0CE130
 * ============================================================================
 */

@Service("deliveryItemServiceImpl")
public class DeliveryItemServiceImpl extends BaseServiceImpl<DeliveryItem, Integer> implements DeliveryItemService {

	@Resource(name = "deliveryItemDaoImpl")
	public void setBaseDao(DeliveryItemDao deliveryItemDao) {
		super.setBaseDao(deliveryItemDao);
	}

}