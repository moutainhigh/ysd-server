package com.qmd.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BorrowFangkuanBean {
	
	private Integer id;//项目ID
	private String name;//项目标题
	private String borrowerRealName;//借款人
	private Date firstRepaymentTime;//起息日
	private BigDecimal borrowTotal;//项目金额
	private BigDecimal borrowMoney;//项目余额
	
	private BigDecimal feeMoney;//服务费
	private Integer feeType;//手续费方式0比例1固定
	private BigDecimal partAccount;//手续费比例
	
	private BigDecimal depositMoney;//保证金
	private Integer borrowStatus;//项目状态
	private Integer borrowFangkuanStatus;//项目放款状态
	
	private String bankCard;//银行账号
	private String bankBranch;//银行支行
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBorrowerRealName() {
		return borrowerRealName;
	}
	public void setBorrowerRealName(String borrowerRealName) {
		this.borrowerRealName = borrowerRealName;
	}
	public Date getFirstRepaymentTime() {
		return firstRepaymentTime;
	}
	public void setFirstRepaymentTime(Date firstRepaymentTime) {
		this.firstRepaymentTime = firstRepaymentTime;
	}
	public BigDecimal getBorrowTotal() {
		return borrowTotal;
	}
	public void setBorrowTotal(BigDecimal borrowTotal) {
		this.borrowTotal = borrowTotal;
	}
	public BigDecimal getBorrowMoney() {
		return borrowMoney;
	}
	public void setBorrowMoney(BigDecimal borrowMoney) {
		this.borrowMoney = borrowMoney;
	}
	public BigDecimal getFeeMoney() {
		return feeMoney;
	}
	public void setFeeMoney(BigDecimal feeMoney) {
		this.feeMoney = feeMoney;
	}
	public BigDecimal getDepositMoney() {
		return depositMoney;
	}
	public void setDepositMoney(BigDecimal depositMoney) {
		this.depositMoney = depositMoney;
	}
	public Integer getBorrowStatus() {
		return borrowStatus;
	}
	public void setBorrowStatus(Integer borrowStatus) {
		this.borrowStatus = borrowStatus;
	}
	public Integer getBorrowFangkuanStatus() {
		return borrowFangkuanStatus;
	}
	public void setBorrowFangkuanStatus(Integer borrowFangkuanStatus) {
		this.borrowFangkuanStatus = borrowFangkuanStatus;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public Integer getFeeType() {
		return feeType;
	}
	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	public BigDecimal getPartAccount() {
		return partAccount;
	}
	public void setPartAccount(BigDecimal partAccount) {
		this.partAccount = partAccount;
	}
	
	
	
	
}
