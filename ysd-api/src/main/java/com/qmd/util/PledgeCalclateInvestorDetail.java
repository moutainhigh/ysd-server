package com.qmd.util;

public class PledgeCalclateInvestorDetail extends PledgeCalclateBorrowDetail {

	// 本期投资金额
	private double investAmount;
	// 本期还款本金
	private double investCapital;
	// 本期还款利息
	private double investInterest;
	// 本期还款后的待还本金
	private double nopayInvest;

	public double getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(double investAmount) {
		this.investAmount = investAmount;
	}

	public double getInvestCapital() {
		return investCapital;
	}

	public void setInvestCapital(double investCapital) {
		this.investCapital = investCapital;
	}

	public double getInvestInterest() {
		return investInterest;
	}

	public void setInvestInterest(double investInterest) {
		this.investInterest = investInterest;
	}

	public double getNopayInvest() {
		return nopayInvest;
	}

	public void setNopayInvest(double nopayInvest) {
		this.nopayInvest = nopayInvest;
	}

}
