package com.qmd.util.bean;

/**
 * 合同参数--券贷通
 */
public class ContractMoonSecurities extends ContractParamBase {

	private String address;// 住所地址
	private String consignee;//受托人
	private String consigneeCardId;//受托人身份证
	
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
	
	
}
