package com.qmd.util.bean;

/**
 * 合同参数--月标--汽车--抵押物
 */
public class ContractMoonCargoPledge extends ContractPledgeBase{

	// 抵押物名称
	private String pawnName;
	// 规格与计量单位
	private String pawnUnit;
	// 数量
	private String pawnQuantity;
	// 价格
	private String pawnUnitPrice;
	// 价值
	private String pawnTotalPrice;
	// 备注
	private String pawnRemark;

	public String getPawnName() {
		return pawnName;
	}

	public void setPawnName(String pawnName) {
		this.pawnName = pawnName;
	}

	public String getPawnUnit() {
		return pawnUnit;
	}

	public void setPawnUnit(String pawnUnit) {
		this.pawnUnit = pawnUnit;
	}

	public String getPawnQuantity() {
		return pawnQuantity;
	}

	public void setPawnQuantity(String pawnQuantity) {
		this.pawnQuantity = pawnQuantity;
	}

	public String getPawnUnitPrice() {
		return pawnUnitPrice;
	}

	public void setPawnUnitPrice(String pawnUnitPrice) {
		this.pawnUnitPrice = pawnUnitPrice;
	}

	public String getPawnTotalPrice() {
		return pawnTotalPrice;
	}

	public void setPawnTotalPrice(String pawnTotalPrice) {
		this.pawnTotalPrice = pawnTotalPrice;
	}

	public String getPawnRemark() {
		return pawnRemark;
	}

	public void setPawnRemark(String pawnRemark) {
		this.pawnRemark = pawnRemark;
	}

}
