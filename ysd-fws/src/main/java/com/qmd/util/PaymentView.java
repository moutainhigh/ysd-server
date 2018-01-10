package com.qmd.util;

public class PaymentView {
	//月还款额
	private String monthTotalAmount;
	//总利息
	private String totalLiXi;
	//还款总金额
	private String totalAmount;
	//分期还款明细
	MonthPaymentDetail[] paymentDetail;
	public MonthPaymentDetail[] getPaymentDetail() {
		return paymentDetail;
	}
	public void setPaymentDetail(MonthPaymentDetail[] paymentDetail) {
		this.paymentDetail = paymentDetail;
	}
	public String getMonthTotalAmount() {
		return monthTotalAmount;
	}
	public void setMonthTotalAmount(String monthTotalAmount) {
		this.monthTotalAmount = monthTotalAmount;
	}
	public String getTotalLiXi() {
		return totalLiXi;
	}
	public void setTotalLiXi(String totalLiXi) {
		this.totalLiXi = totalLiXi;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
}
