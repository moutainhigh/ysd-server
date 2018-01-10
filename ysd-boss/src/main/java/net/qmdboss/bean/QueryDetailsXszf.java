package net.qmdboss.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 【新生支付】返回查询订单结果
 * @author Xsf
 * queryDetails=orderId,orderAmount,payAmount,acquiringTime,completeTime,orderNo, stateCode 
 | orderId,orderAmount,payAmount,acquiringTime,completeTime,orderNo, stateCode |„„(多条订单信息之 
 间以|隔开) 

 *
 */
public class QueryDetailsXszf {

	private String orderId;//商户订单号
	private BigDecimal orderAmount;//商户订单金额
	private BigDecimal payAmount;//实际支付金额（分）
	private Date acquiringTime;//收单时间20130321020306 
	private Date completeTime;//支付完成时间 
	private String orderNo;//支付流水号
	private String stateCode;//状态码 0：已接受     1：处理中     2：处理成功     3：处理失败 
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public Date getAcquiringTime() {
		return acquiringTime;
	}
	public void setAcquiringTime(Date acquiringTime) {
		this.acquiringTime = acquiringTime;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
}
