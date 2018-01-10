package com.qmd.util.bean;


public class BorrowContent {
	private String financierInfo;//融资人信息
	private String purposeFactor;//资金用途
	private String paymentFactor;//还款来源
	
	public String getFinancierInfo() {
		return financierInfo;
	}
	public void setFinancierInfo(String financierInfo) {
		this.financierInfo = financierInfo;
	}
	public String getPurposeFactor() {
		return purposeFactor;
	}
	public void setPurposeFactor(String purposeFactor) {
		this.purposeFactor = purposeFactor;
	}
	public String getPaymentFactor() {
		return paymentFactor;
	}
	public void setPaymentFactor(String paymentFactor) {
		this.paymentFactor = paymentFactor;
	}
}
