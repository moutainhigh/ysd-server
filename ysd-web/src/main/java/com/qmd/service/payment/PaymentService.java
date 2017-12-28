package com.qmd.service.payment;

import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.payment.BasePaymentProduct;
import com.qmd.service.BaseService;

import java.util.List;
import java.util.Map;

public interface PaymentService extends BaseService<UserAccountRecharge, Integer>{

	/**
	 * 获取最后生成的充值编号
	 * 
	 * @return 充值编号
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
	 * 查询支付产品基类
	 * @param id
	 * @return
	 */
	public BasePaymentProduct getPaymentProduct(Integer id);
	
	
	/**
	 * 添加充值记录
	 * @param userAccountRecharge
	 */
	public void addUserAccountRecharge(UserAccountRecharge userAccountRecharge);
	
	/**
	 * 更新充值支付方式id
	 * @param map
	 */
	public void updateRechargeId(Map<String,Object> map);

	/**
	 * 银行卡签约
	 */
	public Integer  addUserAccountRecharge(UserAccountRecharge userAccountRecharge,AccountBank accountBank,User user);
	
	/**
	 * 根据充值编号查询用户充值记录
	 * @param tradeNo
	 * @return
	 */
	public UserAccountRecharge getUserAccountRechargeByTradeNo(String tradeNo);
	
	
	public int unionPaymentRecharge(UserAccountRecharge recharge, User user);

	
	/**
	 * 融宝获取支付前的数据,先调用绑卡签约,第二次支付
	 * @param userRecharge
	 * @param user
	 * @return
	 */
	public Map postRongbaoPay(UserAccountRecharge userRecharge, User user,AccountBank accountBank);
	
	
}
