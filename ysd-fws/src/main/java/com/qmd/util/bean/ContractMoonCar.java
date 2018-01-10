package com.qmd.util.bean;

/**
 * 合同参数--月标--汽车
 */
public class ContractMoonCar extends ContractParamBase {

	// 出质人
	private String pawner;
	// 借款用途
	private String purpose;

	private String pledgeMoneyTotal;

	// private List<ContractMoonCarPledge> contractCarPledgeList;

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

	public String getPledgeMoneyTotal() {
		return pledgeMoneyTotal;
	}

	public void setPledgeMoneyTotal(String pledgeMoneyTotal) {
		this.pledgeMoneyTotal = pledgeMoneyTotal;
	}

}
