package com.qmd.util;

import java.math.BigDecimal;

public class PledgeRepayPlanEach {

	private Integer issue;// 第几期
	private Integer daily;// 第几天还款
	private BigDecimal issueTotal;// 本期本息总额
	private BigDecimal issueCapital;// 本期本金
	private BigDecimal issueInterest;// 本期利息
	private BigDecimal rateCapital;// 还款本金百分比

	private Integer countDaily;// 本期计算用的天数
	private BigDecimal countCapital;// 本期计算用的待还本金

	/**
	 * 第几期
	 * 
	 * @return
	 */
	public Integer getIssue() {
		return issue;
	}

	/**
	 * 第几期
	 * 
	 * @param issue
	 */
	public void setIssue(Integer issue) {
		this.issue = issue;
	}

	/**
	 * 第几天还款
	 * 
	 * @return
	 */
	public Integer getDaily() {
		return daily;
	}

	/**
	 * 第几天还款
	 * 
	 * @param daily
	 */
	public void setDaily(Integer daily) {
		this.daily = daily;
	}

	/**
	 * 本期本息总额
	 * 
	 * @return
	 */
	public BigDecimal getIssueTotal() {
		return issueTotal;
	}

	/**
	 * 本期本息总额
	 * 
	 * @param issueTotal
	 */
	public void setIssueTotal(BigDecimal issueTotal) {
		this.issueTotal = issueTotal;
	}

	/**
	 * 本期本金
	 * 
	 * @return
	 */
	public BigDecimal getIssueCapital() {
		return issueCapital;
	}

	/**
	 * 本期本金
	 * 
	 * @param issueCapital
	 */
	public void setIssueCapital(BigDecimal issueCapital) {
		this.issueCapital = issueCapital;
	}

	/**
	 * 本期利息
	 * 
	 * @return
	 */
	public BigDecimal getIssueInterest() {
		return issueInterest;
	}

	/**
	 * 本期利息
	 * 
	 * @param issueInterest
	 */
	public void setIssueInterest(BigDecimal issueInterest) {
		this.issueInterest = issueInterest;
	}

	/**
	 * 还款本金百分比
	 * 
	 * @return
	 */
	public BigDecimal getRateCapital() {
		return rateCapital;
	}

	/**
	 * 还款本金百分比
	 * 
	 * @param rateCapital
	 */
	public void setRateCapital(BigDecimal rateCapital) {
		this.rateCapital = rateCapital;
	}

	/**
	 * 本期计算用的天数
	 * 
	 * @return
	 */
	public Integer getCountDaily() {
		return countDaily;
	}

	/**
	 * 本期计算用的天数
	 * 
	 * @param countDaily
	 */
	public void setCountDaily(Integer countDaily) {
		this.countDaily = countDaily;
	}

	/**
	 * 本期计算用的待还本金
	 * 
	 * @return
	 */
	public BigDecimal getCountCapital() {
		return countCapital;
	}

	/**
	 * 本期计算用的待还本金
	 * 
	 * @param countCapital
	 */
	public void setCountCapital(BigDecimal countCapital) {
		this.countCapital = countCapital;
	}

}
