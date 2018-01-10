package com.qmd.service.payment;

import java.util.Map;

public interface PayService {

	/**
	 * 充值成功后，修改充值状态
	 * @param map【status,tradeNo】
	 * @param detailMap -资金流水记录
	 * 
	 * @return 0 成功，1已经充值，2非充值状态
	 */
	public int rechargeSuccess(Map<String,Object> map,Map<String,Object> detailMap);
	
	/**
	 * 充值失败
	 */
	public void rechargeError(Map<String,Object> map);
	
}
