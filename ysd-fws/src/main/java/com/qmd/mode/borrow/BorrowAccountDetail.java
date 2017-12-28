package com.qmd.mode.borrow;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BorrowAccountDetail implements Serializable {

	private static final long serialVersionUID = 1138752975424656185L;

	private Integer id;
	private Date createDate;
	private Date modifyDate;
	
	private Integer borrowId;
	private Integer agencyId;
	private Integer userId;
	
	private String type;
	private BigDecimal money;
	private BigDecimal borrowTotal;
	private BigDecimal borrowCapitalYes;//已还本金
	private BigDecimal borrowInterestYes;//已还利息
	private BigDecimal borrowCapitalNo;//未还本金
	private BigDecimal borrowInterestNo;//未还利息
	
	private String ip;
	private String remark;
	
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
	public Integer getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getBorrowTotal() {
		return borrowTotal;
	}
	public void setBorrowTotal(BigDecimal borrowTotal) {
		this.borrowTotal = borrowTotal;
	}
	public BigDecimal getBorrowCapitalYes() {
		return borrowCapitalYes;
	}
	public void setBorrowCapitalYes(BigDecimal borrowCapitalYes) {
		this.borrowCapitalYes = borrowCapitalYes;
	}
	public BigDecimal getBorrowInterestYes() {
		return borrowInterestYes;
	}
	public void setBorrowInterestYes(BigDecimal borrowInterestYes) {
		this.borrowInterestYes = borrowInterestYes;
	}
	public BigDecimal getBorrowCapitalNo() {
		return borrowCapitalNo;
	}
	public void setBorrowCapitalNo(BigDecimal borrowCapitalNo) {
		this.borrowCapitalNo = borrowCapitalNo;
	}
	public BigDecimal getBorrowInterestNo() {
		return borrowInterestNo;
	}
	public void setBorrowInterestNo(BigDecimal borrowInterestNo) {
		this.borrowInterestNo = borrowInterestNo;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
