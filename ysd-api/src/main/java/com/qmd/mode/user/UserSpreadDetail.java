package com.qmd.mode.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户推广记录表
 */
public class UserSpreadDetail implements Serializable {

	private static final long serialVersionUID = -8818383537168946752L;

	private Integer id;// 编号
	private Date createDate;// 添加时间
	private Date modifyDate;// 修改时间
	private Integer tenderUserId;// 投标用户ID
	private BigDecimal tenderMoney;//投标金额
	private BigDecimal tgMoney;//推广金额
	private Integer statusCode;// 状态【1：一级推广；2：二级推广】
	private Integer tgUserId;// 推广用户ID
	private Integer borrowId;//标ID
	private String remark;// 备注
	
	//仅查询用
	private String username;//用户名
	private Date minDate;
	private Date maxDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getTenderUserId() {
		return tenderUserId;
	}

	public void setTenderUserId(Integer tenderUserId) {
		this.tenderUserId = tenderUserId;
	}

	public BigDecimal getTenderMoney() {
		return tenderMoney;
	}

	public void setTenderMoney(BigDecimal tenderMoney) {
		this.tenderMoney = tenderMoney;
	}

	public BigDecimal getTgMoney() {
		return tgMoney;
	}

	public void setTgMoney(BigDecimal tgMoney) {
		this.tgMoney = tgMoney;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getTgUserId() {
		return tgUserId;
	}

	public void setTgUserId(Integer tgUserId) {
		this.tgUserId = tgUserId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

}
