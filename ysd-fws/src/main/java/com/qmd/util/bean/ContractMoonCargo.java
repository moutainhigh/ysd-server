package com.qmd.util.bean;

/**
 * 合同参数--月标--动产
 */
public class ContractMoonCargo extends ContractParamBase {

	// 出质人
	private String pawner;
	// 借款用途
	private String purpose;
	// 监管人
	private String supervisor;
	// 质押率
	private String pledgeRate;
	// 最高质押率
	private String pledgeRateTop;
	// 拍卖公司
	private String auction;
	// 保险名称
	private String insuranceName;

	private String pledgeMoneyTotal;

	public String getPawner() {
		return pawner;
	}

	public void setPawner(String pawner) {
		this.pawner = pawner;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getPledgeRate() {
		return pledgeRate;
	}

	public void setPledgeRate(String pledgeRate) {
		this.pledgeRate = pledgeRate;
	}

	public String getPledgeRateTop() {
		return pledgeRateTop;
	}

	public void setPledgeRateTop(String pledgeRateTop) {
		this.pledgeRateTop = pledgeRateTop;
	}

	public String getAuction() {
		return auction;
	}

	public void setAuction(String auction) {
		this.auction = auction;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public String getPledgeMoneyTotal() {
		return pledgeMoneyTotal;
	}

	public void setPledgeMoneyTotal(String pledgeMoneyTotal) {
		this.pledgeMoneyTotal = pledgeMoneyTotal;
	}

}
