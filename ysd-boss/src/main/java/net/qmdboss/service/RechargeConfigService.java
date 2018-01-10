package net.qmdboss.service;

import net.qmdboss.entity.RechargeConfig;

import java.util.List;

/**
 * Service接口-支付方式
 * @author Xsf
 *
 */
public interface RechargeConfigService extends BaseService<RechargeConfig, Integer>{

	/**
	 * 根据支付产品标识查找支付方式
	 * @param paymentProductId
	 * @return
	 */
	public RechargeConfig find(String paymentProductId);
	
	
	/**
	 * 判断支付方式ID是否唯一(不区分大小写)
	 * @param oldProductId
	 * @param newProductId
	 * @return
	 */
	public Boolean isUniqueByProductId(String oldProductId,String newProductId);
	
	/**
	 * 获取所有启用的支付方式
	 * @return
	 */
	public List<RechargeConfig> getRechargeConfigList();
	
	
}
