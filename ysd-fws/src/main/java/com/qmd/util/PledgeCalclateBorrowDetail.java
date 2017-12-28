package com.qmd.util;

public class PledgeCalclateBorrowDetail {

	// 期数（从0开始计数)
	private int issue;
	// 还款天数
	private int days;
	// 本期还款的利息
	private double interest;
	// 本期借款的天数
	private int thisDays;
	// 本期借款的本金
	private double thisCapital;
	// 本期还款本金
	private double repayAccount;
	// 本期还款后的待还本金
	private double norepayAccount;

	public int getIssue() {
		return issue;
	}

	public void setIssue(int issue) {
		this.issue = issue;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public int getThisDays() {
		return thisDays;
	}

	public void setThisDays(int thisDays) {
		this.thisDays = thisDays;
	}

	public double getThisCapital() {
		return thisCapital;
	}

	public void setThisCapital(double thisCapital) {
		this.thisCapital = thisCapital;
	}

	public double getRepayAccount() {
		return repayAccount;
	}

	public void setRepayAccount(double repayAccount) {
		this.repayAccount = repayAccount;
	}

	public double getNorepayAccount() {
		return norepayAccount;
	}

	public void setNorepayAccount(double norepayAccount) {
		this.norepayAccount = norepayAccount;
	}

}
