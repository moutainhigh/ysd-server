package com.qmd.util.bean;

import java.math.BigDecimal;

public class CashOutBean {

	private String maxCashMoney;// //最大提现金额
	private String minCashMoney;// 最小提现金额
	private double ableCashMoney;// 可提现金额
	private double ableRecharge;// 免费提现总额

	private String feeValue;
	private String cashType;
	private String fixedFee;

	private int recentTenderDays;
	private int recentRepayDays;
	private BigDecimal recentTenderMoney;
	private BigDecimal recentFeeMoney;

	private BigDecimal userTenderMoney;// 近期投资
	private BigDecimal userRepayMoney;// 近期还款金额
	private BigDecimal userRecentFee;// 近期提现费用

	public String getMaxCashMoney() {
		return maxCashMoney;
	}

	public void setMaxCashMoney(String maxCashMoney) {
		this.maxCashMoney = maxCashMoney;
	}

	public String getMinCashMoney() {
		return minCashMoney;
	}

	public void setMinCashMoney(String minCashMoney) {
		this.minCashMoney = minCashMoney;
	}

	public double getAbleCashMoney() {
		return ableCashMoney;
	}

	public void setAbleCashMoney(double ableCashMoney) {
		this.ableCashMoney = ableCashMoney;
	}

	public double getAbleRecharge() {
		return ableRecharge;
	}

	public void setAbleRecharge(double ableRecharge) {
		this.ableRecharge = ableRecharge;
	}

	public String getFeeValue() {
		return feeValue;
	}

	public void setFeeValue(String feeValue) {
		this.feeValue = feeValue;
	}

	public String getCashType() {
		return cashType;
	}

	public void setCashType(String cashType) {
		this.cashType = cashType;
	}

	public String getFixedFee() {
		return fixedFee;
	}

	public void setFixedFee(String fixedFee) {
		this.fixedFee = fixedFee;
	}

	public int getRecentTenderDays() {
		return recentTenderDays;
	}

	public void setRecentTenderDays(int recentTenderDays) {
		this.recentTenderDays = recentTenderDays;
	}

	public int getRecentRepayDays() {
		return recentRepayDays;
	}

	public void setRecentRepayDays(int recentRepayDays) {
		this.recentRepayDays = recentRepayDays;
	}

	public BigDecimal getRecentTenderMoney() {
		return recentTenderMoney;
	}

	public void setRecentTenderMoney(BigDecimal recentTenderMoney) {
		this.recentTenderMoney = recentTenderMoney;
	}

	public BigDecimal getRecentFeeMoney() {
		return recentFeeMoney;
	}

	public void setRecentFeeMoney(BigDecimal recentFeeMoney) {
		this.recentFeeMoney = recentFeeMoney;
	}

	public BigDecimal getUserTenderMoney() {
		return userTenderMoney;
	}

	public void setUserTenderMoney(BigDecimal userTenderMoney) {
		this.userTenderMoney = userTenderMoney;
	}

	public BigDecimal getUserRepayMoney() {
		return userRepayMoney;
	}

	public void setUserRepayMoney(BigDecimal userRepayMoney) {
		this.userRepayMoney = userRepayMoney;
	}

	public BigDecimal getUserRecentFee() {
		return userRecentFee;
	}

	public void setUserRecentFee(BigDecimal userRecentFee) {
		this.userRecentFee = userRecentFee;
	}

}
