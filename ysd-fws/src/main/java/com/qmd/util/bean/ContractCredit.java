package com.qmd.util.bean;

/**
 * 合同参数--信用标
 */
public class ContractCredit extends ContractParamBase {

	private String financeName;// 融资客户姓名
	private String financeLegalPerson;// 融资客户法定代表人
	private String financeAddress;// 融资客户住址
	private String financePost;// 融资客户邮编
	private String financePhone;// 融资客户联系电话
	private String financeSignImg;// 融资客户签章（图片）
	
	private String contractAddress;
	private String chargeRate;//	服务费用占投资金额比例
	private String chargeBankName;//银行
	private String chargeBankCard;//卡号
	private String chargeBankOpen;//开户行
	private String purpose;//借款用途

	public String getFinanceName() {
		return financeName;
	}

	public void setFinanceName(String financeName) {
		this.financeName = financeName;
	}

	public String getFinanceLegalPerson() {
		return financeLegalPerson;
	}

	public void setFinanceLegalPerson(String financeLegalPerson) {
		this.financeLegalPerson = financeLegalPerson;
	}

	public String getFinanceAddress() {
		return financeAddress;
	}

	public void setFinanceAddress(String financeAddress) {
		this.financeAddress = financeAddress;
	}

	public String getFinancePost() {
		return financePost;
	}

	public void setFinancePost(String financePost) {
		this.financePost = financePost;
	}

	public String getFinancePhone() {
		return financePhone;
	}

	public void setFinancePhone(String financePhone) {
		this.financePhone = financePhone;
	}

	public String getFinanceSignImg() {
		return financeSignImg;
	}

	public void setFinanceSignImg(String financeSignImg) {
		this.financeSignImg = financeSignImg;
	}

	public String getContractAddress() {
		return contractAddress;
	}

	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}

	public String getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(String chargeRate) {
		this.chargeRate = chargeRate;
	}

	public String getChargeBankName() {
		return chargeBankName;
	}

	public void setChargeBankName(String chargeBankName) {
		this.chargeBankName = chargeBankName;
	}

	public String getChargeBankCard() {
		return chargeBankCard;
	}

	public void setChargeBankCard(String chargeBankCard) {
		this.chargeBankCard = chargeBankCard;
	}

	public String getChargeBankOpen() {
		return chargeBankOpen;
	}

	public void setChargeBankOpen(String chargeBankOpen) {
		this.chargeBankOpen = chargeBankOpen;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

}
