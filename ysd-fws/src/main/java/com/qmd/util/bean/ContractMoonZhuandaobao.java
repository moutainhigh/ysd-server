package com.qmd.util.bean;

/**
 * 合同参数--抵押质押-转贷宝
 */
public class ContractMoonZhuandaobao extends ContractParamBase {

	private String address;// 住所地址
//	
	private String consignee;//受托人
	private String consigneeCardId;//受托人身份证
//	
	private String introduce;//公司居间介绍方
	
	private String bankName;//借款归还方
	private String bankContractId;//借款归还合同编号
	private String bankMoney;//借款归还金额
	private String bankMoneyBig;//借款归还金额（大写）

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsigneeCardId() {
		return consigneeCardId;
	}

	public void setConsigneeCardId(String consigneeCardId) {
		this.consigneeCardId = consigneeCardId;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankContractId() {
		return bankContractId;
	}

	public void setBankContractId(String bankContractId) {
		this.bankContractId = bankContractId;
	}

	public String getBankMoney() {
		return bankMoney;
	}

	public void setBankMoney(String bankMoney) {
		this.bankMoney = bankMoney;
	}

	public String getBankMoneyBig() {
		return bankMoneyBig;
	}

	public void setBankMoneyBig(String bankMoneyBig) {
		this.bankMoneyBig = bankMoneyBig;
	}

}
