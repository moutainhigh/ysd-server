package com.qmd.util.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class DirectInfo implements Serializable {

	private static final long serialVersionUID = 1845410060423425813L;

	// 用户ID
	private Integer directUserId;
	// 用户名
	private String directUserName;
	// 直投金额
	private String directMoney;
	//直投利率
	private BigDecimal directRateDay;
	//直投年化利率
	private BigDecimal directRateYear;

	public Integer getDirectUserId() {
		return directUserId;
	}

	public void setDirectUserId(Integer directUserId) {
		this.directUserId = directUserId;
	}

	public String getDirectUserName() {
		return directUserName;
	}

	public void setDirectUserName(String directUserName) {
		this.directUserName = directUserName;
	}

	public String getDirectMoney() {
		return directMoney;
	}

	public void setDirectMoney(String directMoney) {
		this.directMoney = directMoney;
	}

	public BigDecimal getDirectRateDay() {
		return directRateDay;
	}

	public void setDirectRateDay(BigDecimal directRateDay) {
		this.directRateDay = directRateDay;
	}

	public BigDecimal getDirectRateYear() {
		return directRateYear;
	}

	public void setDirectRateYear(BigDecimal directRateYear) {
		this.directRateYear = directRateYear;
	}

}
