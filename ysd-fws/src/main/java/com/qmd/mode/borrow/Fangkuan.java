package com.qmd.mode.borrow;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Fangkuan  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3845504338125572116L;
	private Integer id;
	private Date createDate;
	private Date modifyDate;
	private Integer type;//1:对项目放款；2:对保证金放款；3：对服务费放款
	private Integer agencyId;//服务商ID
	private Integer userId;//用户ID
	private Integer borrowId;//项目ID
	private BigDecimal fangkuanMoney;//放款金额
	private String bankCard;//银行账号
	private String bankBranch;//银行支行
	private Integer status;//0:放款失败；1：放款成功；2：审核中
	private String ip;//申请IP
	private Date verifyTime;
	private String verifyAdmin;
	private String verifyPhone;
	private String verifyRemark;
	private String verifyIp;
	
	//关联查询
	private String name;//项目标题
	private String borrowerRealName;//借款人
	private Date sqMinDate;
	private Date sqMaxDate;
	private Date shMinDate;
	private Date shMaxDate;
	
	
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public Integer getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}
	public BigDecimal getFangkuanMoney() {
		return fangkuanMoney;
	}
	public void setFangkuanMoney(BigDecimal fangkuanMoney) {
		this.fangkuanMoney = fangkuanMoney;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getVerifyTime() {
		return verifyTime;
	}
	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}
	public String getVerifyAdmin() {
		return verifyAdmin;
	}
	public void setVerifyAdmin(String verifyAdmin) {
		this.verifyAdmin = verifyAdmin;
	}
	public String getVerifyPhone() {
		return verifyPhone;
	}
	public void setVerifyPhone(String verifyPhone) {
		this.verifyPhone = verifyPhone;
	}
	public String getVerifyRemark() {
		return verifyRemark;
	}
	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}
	public String getVerifyIp() {
		return verifyIp;
	}
	public void setVerifyIp(String verifyIp) {
		this.verifyIp = verifyIp;
	}
	public String getBorrowerRealName() {
		return borrowerRealName;
	}
	public void setBorrowerRealName(String borrowerRealName) {
		this.borrowerRealName = borrowerRealName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getSqMinDate() {
		return sqMinDate;
	}
	public void setSqMinDate(Date sqMinDate) {
		this.sqMinDate = sqMinDate;
	}
	public Date getSqMaxDate() {
		return sqMaxDate;
	}
	public void setSqMaxDate(Date sqMaxDate) {
		this.sqMaxDate = sqMaxDate;
	}
	public Date getShMinDate() {
		return shMinDate;
	}
	public void setShMinDate(Date shMinDate) {
		this.shMinDate = shMinDate;
	}
	public Date getShMaxDate() {
		return shMaxDate;
	}
	public void setShMaxDate(Date shMaxDate) {
		this.shMaxDate = shMaxDate;
	}

	public String getShowStatus(){
		String str="";
		if(status.compareTo(0)==0){
			str="放款失败";
		}else if(status.compareTo(1) ==0){
			str= "放款成功";
		}else if(status.compareTo(2) ==0){
			str= "审核中";
		}
		return str;
	}
	
}
