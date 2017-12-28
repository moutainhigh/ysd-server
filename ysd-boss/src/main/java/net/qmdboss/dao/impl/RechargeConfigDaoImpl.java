package net.qmdboss.dao.impl;

import net.qmdboss.dao.RechargeConfigDao;
import net.qmdboss.entity.RechargeConfig;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("rechargeConfigDaoImpl")
public class RechargeConfigDaoImpl extends BaseDaoImpl<RechargeConfig, Integer> implements RechargeConfigDao{
	/**
	 * 根据支付产品标识查找支付方式
	 * @param paymentProductId
	 * @return
	 */
	public RechargeConfig find(String paymentProductId){
		String hql = "from RechargeConfig as rechargeConfig where rechargeConfig.paymentProductId = :paymentProductId";
		return (RechargeConfig) getSession().createQuery(hql).setParameter("paymentProductId", paymentProductId).uniqueResult();
	
	}
	
	@SuppressWarnings("unchecked")
	public List<RechargeConfig> getRechargeConfigList(){
		String hql = "from RechargeConfig as rechargeConfig where rechargeConfig.isEnabled is true order by rechargeConfig.orderList asc";
		return getSession().createQuery(hql).list();
	}
}
