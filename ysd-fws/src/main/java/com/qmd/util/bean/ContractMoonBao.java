package com.qmd.util.bean;

/**
 * 合同参数--抵押质押-保证
 */
public class ContractMoonBao extends ContractParamBase {

	private String legalPerson;// 法定代表人
	private String address;// 住所地址
	private String telphone;//联系电话
	
	private String purpose;//借款用途
	private String payment;//还款来源
	private String contractSigned;//合同签约地
	
	private String consignee;//受托人
	private String consigneeCardId;//受托人身份证
	
	private String introduce;//公司居间介绍方
	
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getContractSigned() {
		return contractSigned;
	}

	public void setContractSigned(String contractSigned) {
		this.contractSigned = contractSigned;
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

}
