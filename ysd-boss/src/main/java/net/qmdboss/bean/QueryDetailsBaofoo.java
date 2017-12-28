package net.qmdboss.bean;

import java.math.BigDecimal;

public class QueryDetailsBaofoo {
	/**
	 * 参数值之间用”|”间隔,参数顺序：
		MerchantID|TransID|CheckResult|factMoney|SuccTime|Md5Sign
	 */
	private String merchantID;//商户号【 合作商户的商户号】
	private String transId;//商户订单号 【合作商户的订单号。TransID唯一确定一条订单】
	private String checkResult;//支付结果  【 Y：成功 F：失败 P：处理中 N：没有订单 】
	private BigDecimal factMoney;// 实际成功金额《元》【单位（分），实际支付成功的金额】
	private String succTime;//支付完成时间【订单支付完成时间（yyyyMMddHHmmss）】
	private String md5Sign;//Md5签名字段
	
	public String getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public BigDecimal getFactMoney() {
		return factMoney;
	}
	public void setFactMoney(BigDecimal factMoney) {
		this.factMoney = factMoney;
	}
	public String getSuccTime() {
		return succTime;
	}
	public void setSuccTime(String succTime) {
		this.succTime = succTime;
	}
	public String getMd5Sign() {
		return md5Sign;
	}
	public void setMd5Sign(String md5Sign) {
		this.md5Sign = md5Sign;
	}
}
