package com.qmd.service.payment;

import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.service.BaseService;

import java.util.List;

public interface PaymentService extends BaseService<UserAccountRecharge, Integer>{

	/**
	 * 获取最后生成的充值编号
	 * 
	 * @return 充值编号
	 */
//	public String getLastPaymentSn();
	
	
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
	 * 查询支付产品基类
	 * @param id
	 * @return
	 */
//	public BasePaymentProduct getPaymentProduct(Integer id);
	
	
	/**
	 * 添加充值记录
	 * @param userAccountRecharge
	 */
//	public void addUserAccountRecharge(UserAccountRecharge userAccountRecharge);
	
	/**
	 * 添加机构充值记录
	 * @param userAccountRecharge
	 */
	public void addUserAccountRechargeAgence(UserAccountRecharge userAccountRecharge);
	/**
	 * 添加平台服务商还款充值记录
	 * @param addUserAccountRechargeAgence
	 */
	public void addUserAccountRechargeRepaymentByAgency(UserAccountRecharge userAccountRecharge);
	/**
	 * 充值成功后，修改充值状态
	 * @param map【status,tradeNo】
	 * @param detailMap -资金流水记录
	 * 
	 * @return 0 成功，1已经充值，2非充值状态
	 */
//	public int rechargeSuccess(Map<String,Object> map,Map<String,Object> detailMap);
	
	/**
	 * 充值失败
	 */
//	public void rechargeError(Map<String,Object> map);
	
	/**
	 * 根据充值编号查询用户充值记录
	 * @param tradeNo
	 * @return
	 */
	public UserAccountRecharge getUserAccountRechargeByTradeNo(String tradeNo);
	
	
}
