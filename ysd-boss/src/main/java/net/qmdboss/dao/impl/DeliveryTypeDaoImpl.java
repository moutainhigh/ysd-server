package net.qmdboss.dao.impl;

import net.qmdboss.dao.DeliveryTypeDao;
import net.qmdboss.entity.DeliveryType;
import net.qmdboss.entity.Order;
import net.qmdboss.entity.Reship;
import net.qmdboss.entity.Shipping;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Dao实现类 - 配送方式
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX8FA9BB4F72C2C9F3D643830EB2B318C3
 * ============================================================================
 */

@Repository("deliveryTypeDaoImpl")
public class DeliveryTypeDaoImpl extends BaseDaoImpl<DeliveryType, Integer> implements DeliveryTypeDao {

	public DeliveryType getDefaultDeliveryType() {
		String hql = "from DeliveryType as deliveryType where deliveryType.isDefault = :isDefault";
		return (DeliveryType) getSession().createQuery(hql).setParameter("isDefault", true).setMaxResults(1).uniqueResult();
	}
	
	// 关联处理
	@Override
	public void delete(DeliveryType deliveryType) {
		Set<Order> orderSet = deliveryType.getOrderSet();
		if (orderSet != null) {
			for (Order order : orderSet) {
				order.setDeliveryType(null);
			}
		}
		
		Set<Shipping> shippingSet = deliveryType.getShippingSet();
		if (shippingSet != null) {
			for (Shipping shipping : shippingSet) {
				shipping.setDeliveryType(null);
			}
		}
		
		Set<Reship> reshipSet = deliveryType.getReshipSet();
		if (reshipSet != null) {
			for (Reship reship : reshipSet) {
				reship.setDeliveryType(null);
			}
		}
		
		super.delete(deliveryType);
	}

	// 关联处理
	@Override
	public void delete(Integer id) {
		DeliveryType deliveryType = super.load(id);
		this.delete(deliveryType);
	}

	// 关联处理
	@Override
	public void delete(Integer[] ids) {
		for (Integer id : ids) {
			DeliveryType deliveryType = super.load(id);
			this.delete(deliveryType);
		}
	}

}