package com.qmd.util.bean;

import java.math.BigDecimal;

public class RepaymentDetail {

	private int orderNum;// 借款标分期顺序
	private BigDecimal interest;// 本期所还利息
	private BigDecimal capital;// 本期所还本金
	private BigDecimal account;
	private Integer repaymentDateInt;// 还款时间

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getCapital() {
		return capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	public Integer getRepaymentDateInt() {
		return repaymentDateInt;
	}

	public void setRepaymentDateInt(Integer repaymentDateInt) {
		this.repaymentDateInt = repaymentDateInt;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

}
