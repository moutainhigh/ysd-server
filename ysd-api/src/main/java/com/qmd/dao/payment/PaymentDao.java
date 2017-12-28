package com.qmd.dao.payment;

import com.qmd.dao.BaseDao;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.UserAccountRecharge;

import java.util.List;
import java.util.Map;

public interface PaymentDao extends BaseDao<UserAccountRecharge,Integer>{

	
	
	/**
	 * 获取最后生成的支付编号
	 * 
	 * @return 支付编号
	 */
	public String getLastPaymentSn();
	
	
	/**
	 * 获取支付方式列表
	 * @return
	 */
	public List<RechargeConfig> getPaymentConfigList();
	
	/**
	 * 根据ID获取支付方式
	 * @param id
	 * @return
	 */
	public RechargeConfig getPaymentConfig(Integer id);
	
	
	/**
	 * 添加充值记录
	 * @param userAccountRecharge
	 */
	public void addUserAccountRecharge(UserAccountRecharge userAccountRecharge);
	
	/**
	 * 充值成功/失败，修改充值状态
	 * @param map
	 */
	public void rechargeStatus(Map<String,Object> map);
	
	
	/**
	 * 根据充值编号查询用户充值记录
	 * @param tradeNo
	 * @return
	 */
	public UserAccountRecharge getUserAccountRechargeByTradeNo(String tradeNo);
	

	
}
