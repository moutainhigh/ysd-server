package net.qmdboss.dao;

import net.qmdboss.entity.RechargeConfig;

import java.util.List;


public interface RechargeConfigDao extends BaseDao<RechargeConfig, Integer> {

	
	/**
	 * 根据支付产品标识查找支付方式
	 * @param paymentProductId
	 * @return
	 */
	public RechargeConfig find(String paymentProductId);
	
	/**
	 * 获取所有启用的支付方式
	 * @return
	 */
	public List<RechargeConfig> getRechargeConfigList();
	
}
