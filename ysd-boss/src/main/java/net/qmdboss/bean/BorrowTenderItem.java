package net.qmdboss.bean;

import java.util.Date;

public class BorrowTenderItem {

	private String username; // 用户名，
	private String realname; // 姓名，
	private Date createDate; // 投资时间，
	private String money; // 投资金额，
	private String account; // 投资金额，
	private String borrowName;// 投资项目，
	private String timeLimit;// 项目期限，
	private String placeChilderName;// 渠道来源。
	private int clientType;//（0-PC;1-android;2-ios;5-H5)
	private String hongbaoAmount;

	
	
	public String getHongbaoAmount() {
		return hongbaoAmount;
	}

	public void setHongbaoAmount(String hongbaoAmount) {
		this.hongbaoAmount = hongbaoAmount;
	}

	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getPlaceChilderName() {
		return placeChilderName;
	}

	public void setPlaceChilderName(String placeChilderName) {
		this.placeChilderName = placeChilderName;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

}
