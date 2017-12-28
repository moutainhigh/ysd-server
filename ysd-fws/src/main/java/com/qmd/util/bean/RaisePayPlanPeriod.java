package com.qmd.util.bean;

import java.math.BigDecimal;

/**
 * 还款计划(每期)
 * 
 */
public class RaisePayPlanPeriod {

	private BigDecimal catipalThis;// 本期本金
	private BigDecimal catipalPay;// 还款本金
	private BigDecimal catipalNext;// 剩余本金
	private BigDecimal interestThis;// 本期利息

	public BigDecimal getCatipalThis() {
		return catipalThis;
	}

	public void setCatipalThis(BigDecimal catipalThis) {
		this.catipalThis = catipalThis;
	}

	public BigDecimal getCatipalPay() {
		return catipalPay;
	}

	public void setCatipalPay(BigDecimal catipalPay) {
		this.catipalPay = catipalPay;
	}

	public BigDecimal getCatipalNext() {
		return catipalNext;
	}

	public void setCatipalNext(BigDecimal catipalNext) {
		this.catipalNext = catipalNext;
	}

	public BigDecimal getInterestThis() {
		return interestThis;
	}

	public void setInterestThis(BigDecimal interestThis) {
		this.interestThis = interestThis;
	}

}
