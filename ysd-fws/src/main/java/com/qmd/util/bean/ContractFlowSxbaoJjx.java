package com.qmd.util.bean;

/**
 * 合同参数--流转--随心宝 积极型
 */
public class ContractFlowSxbaoJjx extends ContractParamBase{
	
//	private String creditAssignmenterName;// 债权出让人姓名
//	private String creditAssignmenterLegal;//转让法人
	
	private String consignee;//受托人
	private String consigneeCardId;//受托人身份证
	
	private String originalBorrowerName;// 原始借款人姓名
	
//	private String originalContractCode;//原始保理合同编号
	
//	private String originalBorrowPledger;// 原始借款抵押人姓名
	private String originalBorrowDate;// 原始借款时间
	private String originalBorrowAccount;// 原始借款金额
	private String originalBorrowAccountOwe;//借款欠款
	private String originalBorrowAccountSxbao;//借款欠款  稳健型
	private String originalBorrowAccountSxbaoWjx;//借款欠款  稳健型
	private String originalBorrowAccountSxbaoJjx;//借款欠款  积极型
	
	private String agencyDbNameSxbaoWjx;//稳健型担保公司名称
//	private String originalBorrowTimelimit; // 原始借款期限
//	private String originalBorrowApr; // 原始借款利率
//	private String originalBorrowStyle; // 原始借款还款方式
	public String getOriginalBorrowDate() {
		return originalBorrowDate;
	}
	public void setOriginalBorrowDate(String originalBorrowDate) {
		this.originalBorrowDate = originalBorrowDate;
	}
	public String getOriginalBorrowAccount() {
		return originalBorrowAccount;
	}
	public void setOriginalBorrowAccount(String originalBorrowAccount) {
		this.originalBorrowAccount = originalBorrowAccount;
	}
	public String getOriginalBorrowAccountOwe() {
		return originalBorrowAccountOwe;
	}
	public void setOriginalBorrowAccountOwe(String originalBorrowAccountOwe) {
		this.originalBorrowAccountOwe = originalBorrowAccountOwe;
	}
	public String getOriginalBorrowAccountSxbao() {
		return originalBorrowAccountSxbao;
	}
	public void setOriginalBorrowAccountSxbao(String originalBorrowAccountSxbao) {
		this.originalBorrowAccountSxbao = originalBorrowAccountSxbao;
	}
	public String getOriginalBorrowAccountSxbaoWjx() {
		return originalBorrowAccountSxbaoWjx;
	}
	public void setOriginalBorrowAccountSxbaoWjx(
			String originalBorrowAccountSxbaoWjx) {
		this.originalBorrowAccountSxbaoWjx = originalBorrowAccountSxbaoWjx;
	}
	public String getOriginalBorrowAccountSxbaoJjx() {
		return originalBorrowAccountSxbaoJjx;
	}
	public void setOriginalBorrowAccountSxbaoJjx(
			String originalBorrowAccountSxbaoJjx) {
		this.originalBorrowAccountSxbaoJjx = originalBorrowAccountSxbaoJjx;
	}
	public String getOriginalBorrowerName() {
		return originalBorrowerName;
	}
	public void setOriginalBorrowerName(String originalBorrowerName) {
		this.originalBorrowerName = originalBorrowerName;
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
	public String getAgencyDbNameSxbaoWjx() {
		return agencyDbNameSxbaoWjx;
	}
	public void setAgencyDbNameSxbaoWjx(String agencyDbNameSxbaoWjx) {
		this.agencyDbNameSxbaoWjx = agencyDbNameSxbaoWjx;
	}
	
	
}
