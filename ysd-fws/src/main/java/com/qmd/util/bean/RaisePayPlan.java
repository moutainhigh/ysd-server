package com.qmd.util.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 还款计划
 * 
 */
public class RaisePayPlan {

	private BigDecimal catipalTotal;// 总本金
	private BigDecimal catipalPeriod;// 分期本金
	private BigDecimal interestTotal;// 总利息
	private BigDecimal interestPeriod;// 分期利息
	private BigDecimal apr;// 利率（天息）
	private int period;// 每期天数
	private int divide;// 期数

	private List<RaisePayPlanPeriod> raisePayPlanPeriodList;

	public BigDecimal getCatipalTotal() {
		return catipalTotal;
	}

	public void setCatipalTotal(BigDecimal catipalTotal) {
		this.catipalTotal = catipalTotal;
	}

	public BigDecimal getCatipalPeriod() {
		return catipalPeriod;
	}

	public void setCatipalPeriod(BigDecimal catipalPeriod) {
		this.catipalPeriod = catipalPeriod;
	}

	public BigDecimal getInterestTotal() {
		return interestTotal;
	}

	public void setInterestTotal(BigDecimal interestTotal) {
		this.interestTotal = interestTotal;
	}

	public BigDecimal getInterestPeriod() {
		return interestPeriod;
	}

	public void setInterestPeriod(BigDecimal interestPeriod) {
		this.interestPeriod = interestPeriod;
	}

	public BigDecimal getApr() {
		return apr;
	}

	public void setApr(BigDecimal apr) {
		this.apr = apr;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getDivide() {
		return divide;
	}

	public void setDivide(int divide) {
		this.divide = divide;
	}

	public List<RaisePayPlanPeriod> getRaisePayPlanPeriodList() {
		return raisePayPlanPeriodList;
	}

	public void setRaisePayPlanPeriodList(
			List<RaisePayPlanPeriod> raisePayPlanPeriodList) {
		this.raisePayPlanPeriodList = raisePayPlanPeriodList;
	}

}
