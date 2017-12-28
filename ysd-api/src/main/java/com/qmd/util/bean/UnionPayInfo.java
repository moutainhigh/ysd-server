package com.qmd.util.bean;

public class UnionPayInfo {

	private String retCode;// 返回码，0000为交易成功
	private String retDesc;// 失败返回错误描述
	private String bindId;// 成功返回银行卡唯一绑定号　
	private String exCode;//
	private String orderNo;// 商户请求订单编号
	private String processUid;// VL64 服务器正确处理后的唯一标识号
	// 扣款相关
	private String orderStatus;// 订单状态 0:已接受, 1:处理中,2:处理成功,3:处理失败
	private String processDate;// 系统处理日期
	private String queryId;// FL16扣款成功返回的平台流水号

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetDesc() {
		return retDesc;
	}

	public void setRetDesc(String retDesc) {
		this.retDesc = retDesc;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getExCode() {
		return exCode;
	}

	public void setExCode(String exCode) {
		this.exCode = exCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProcessUid() {
		return processUid;
	}

	public void setProcessUid(String processUid) {
		this.processUid = processUid;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

}
