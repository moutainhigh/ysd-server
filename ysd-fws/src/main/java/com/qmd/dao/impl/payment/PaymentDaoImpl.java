package com.qmd.dao.impl.payment;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.payment.PaymentDao;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.UserAccountRecharge;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("paymentDao")
public class PaymentDaoImpl extends BaseDaoImpl<UserAccountRecharge,Integer>  implements PaymentDao{

	public String getLastPaymentSn(){
		UserAccountRecharge userAccountRecharge = (UserAccountRecharge)this.getSqlSession().selectOne("Payment.getLastPaymentSn");
		return userAccountRecharge.getTradeNo();
	}
	
	
	public List<RechargeConfig> getPaymentConfigList(){
		List<RechargeConfig> rechargeConfigList = this.getSqlSession().selectList("Payment.getPaymentConfigList");
		return rechargeConfigList;
	}
	
	public RechargeConfig getPaymentConfig(Integer id){
		RechargeConfig rechargeConfig = this.getSqlSession().selectOne("Payment.getPaymentConfig",id);
		return rechargeConfig;
	}
	
	public void addUserAccountRecharge(UserAccountRecharge userAccountRecharge){
		this.getSqlSession().insert("Payment.insert",userAccountRecharge);
	}
	
	
	public void rechargeStatus(Map<String,Object> map){
		this.getSqlSession().update("Payment.update",map);
	}
	
	public UserAccountRecharge getUserAccountRechargeByTradeNo(String tradeNo){
		return (UserAccountRecharge)this.getSqlSession().selectOne("Payment.getUserAccountRechargeByTradeNo", tradeNo);
	}
}
