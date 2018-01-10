package com.qmd.util.bean;

/**
 * 合同参数--月标--房产
 */
public class ContractMoonHouse extends ContractParamBase {

	// 抵押人
	private String pawner;
	// 借款用途
	private String purpose;
	// 抵押物地址
	private String pawnAddress;
	// 抵押物面积
	private String pawnArea;
	// 抵押物价值
	private String pawnMoney;
	// 房屋所有权证
	private String pawnWarrant;
	// 国有土地使用证
	private String pawnEarth;

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

	public String getPawnAddress() {
		return pawnAddress;
	}

	public void setPawnAddress(String pawnAddress) {
		this.pawnAddress = pawnAddress;
	}

	public String getPawnArea() {
		return pawnArea;
	}

	public void setPawnArea(String pawnArea) {
		this.pawnArea = pawnArea;
	}

	public String getPawnMoney() {
		return pawnMoney;
	}

	public void setPawnMoney(String pawnMoney) {
		this.pawnMoney = pawnMoney;
	}

	public String getPawnWarrant() {
		return pawnWarrant;
	}

	public void setPawnWarrant(String pawnWarrant) {
		this.pawnWarrant = pawnWarrant;
	}

	public String getPawnEarth() {
		return pawnEarth;
	}

	public void setPawnEarth(String pawnEarth) {
		this.pawnEarth = pawnEarth;
	}

}
