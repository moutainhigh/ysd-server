package com.qmd.mode.util;

public class MailRepayForInvestor {

	private String userName;// 用户名
	private String userMail;// 邮箱地址
	private String borrowName;// 标的名称
	private String repayDate;// 还款时间
	private String repayMoney; // 还款金额
	private Integer borrowId;//标ID
	
	private String repayCapital;// 本金
	private String repayInterest;// 利息

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}

	public String getRepayMoney() {
		return repayMoney;
	}

	public void setRepayMoney(String repayMoney) {
		this.repayMoney = repayMoney;
	}

	public String getRepayCapital() {
		return repayCapital;
	}

	public void setRepayCapital(String repayCapital) {
		this.repayCapital = repayCapital;
	}

	public String getRepayInterest() {
		return repayInterest;
	}

	public void setRepayInterest(String repayInterest) {
		this.repayInterest = repayInterest;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}
	

}
