package net.qmdboss.dao.impl;

import net.qmdboss.dao.ProductDao;
import net.qmdboss.entity.DeliveryItem;
import net.qmdboss.entity.OrderItem;
import net.qmdboss.entity.Product;
import net.qmdboss.util.SettingUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Dao实现类 - 货品
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX027556F67EB15567DE4DCC7E9D2E5516
 * ============================================================================
 */

@Repository("productDaoImpl")
public class ProductDaoImpl extends BaseDaoImpl<Product, Integer> implements ProductDao {
	
	public boolean isExistByProductSn(String productSn) {
		String hql = "from Product as product where lower(product.productSn) = lower(:productSn)";
		Product product = (Product) getSession().createQuery(hql).setParameter("productSn", productSn).uniqueResult();
		if (product != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getSiblingsProductList(Integer productId) {
		Product product = super.load(productId);
		String hql = "from Product as product where product.goods = :goods";
		return getSession().createQuery(hql).setParameter("goods", product.getGoods()).list();
	}
	
	public Long getStoreAlertCount() {
		String hql = "select count(*) from Product as product where product.isMarketable = :isMarketable and product.store is not null and product.store - product.freezeStore <= :freezeStore";
		return (Long) getSession().createQuery(hql).setParameter("isMarketable", true).setParameter("freezeStore", SettingUtil.getSetting().getStoreAlertCount()).uniqueResult();
	}

	// 关联处理
	@Override
	public void delete(Product product) {
		Set<OrderItem> orderItemSet = product.getOrderItemSet();
		if (orderItemSet != null) {
			for (OrderItem orderItem : orderItemSet) {
				orderItem.setProduct(null);
			}
		}
		
		Set<DeliveryItem> deliveryItemSet = product.getDeliveryItemSet();
		if (deliveryItemSet != null) {
			for (DeliveryItem deliveryItem : deliveryItemSet) {
				deliveryItem.setProduct(null);
			}
		}
		
		super.delete(product);
	}

	// 关联处理
	@Override
	public void delete(Integer id) {
		Product product = load(id);
		this.delete(product);
	}

	// 关联处理
	@Override
	public void delete(Integer[] ids) {
		for (Integer id : ids) {
			Product product = load(id);
			this.delete(product);
		}
	}

}