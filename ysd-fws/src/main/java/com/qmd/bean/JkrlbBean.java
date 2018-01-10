package com.qmd.bean;

import java.math.BigDecimal;

public class JkrlbBean {

	private Integer id;
	private Integer memberType;
	private String username;
	private String phone;
	private BigDecimal borrowerCollectionCapital;// 借款人待付本金
	private BigDecimal borrowerCollectionInterest;// 借款人待付利息
	private Integer whqxm;
	private Integer yhqxm;
	
	public Integer getId() {
		return id;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public String getUsername() {
		return username;
	}
	public String getPhone() {
		return phone;
	}
	public BigDecimal getBorrowerCollectionCapital() {
		return borrowerCollectionCapital;
	}
	public BigDecimal getBorrowerCollectionInterest() {
		return borrowerCollectionInterest;
	}
	public Integer getWhqxm() {
		return whqxm;
	}
	public Integer getYhqxm() {
		return yhqxm;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setBorrowerCollectionCapital(BigDecimal borrowerCollectionCapital) {
		this.borrowerCollectionCapital = borrowerCollectionCapital;
	}
	public void setBorrowerCollectionInterest(BigDecimal borrowerCollectionInterest) {
		this.borrowerCollectionInterest = borrowerCollectionInterest;
	}
	public void setWhqxm(Integer whqxm) {
		this.whqxm = whqxm;
	}
	public void setYhqxm(Integer yhqxm) {
		this.yhqxm = yhqxm;
	}
	
	
	

}
