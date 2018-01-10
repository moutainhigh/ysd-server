package com.qmd.util;

public class MonthPaymentDetail {
	//还款期数
	private String month;
	//还款金额
	private String monthPaymentAmount;
	//还款本金
	private String benjinM;
	//还款利息
	private String lixiM;
	//本期剩余本金
	private String monthAmount;
	//还款的天数
	private String dateNum;
	public String getMonthAmount() {
		return monthAmount;
	}
	public void setMonthAmount(String monthAmount) {
		this.monthAmount = monthAmount;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getMonthPaymentAmount() {
		return monthPaymentAmount;
	}
	public void setMonthPaymentAmount(String monthPaymentAmount) {
		this.monthPaymentAmount = monthPaymentAmount;
	}
	public String getBenjinM() {
		return benjinM;
	}
	public void setBenjinM(String benjinM) {
		this.benjinM = benjinM;
	}
	public String getLixiM() {
		return lixiM;
	}
	public void setLixiM(String lixiM) {
		this.lixiM = lixiM;
	}
	public String getDateNum() {
		return dateNum;
	}
	public void setDateNum(String dateNum) {
		this.dateNum = dateNum;
	}
	
	
}
