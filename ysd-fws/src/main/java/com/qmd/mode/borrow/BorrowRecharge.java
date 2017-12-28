package com.qmd.mode.borrow;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * BorrowRecharge 表属性类
 * 
 * @author Administrator
 * 
 */
public class BorrowRecharge implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// int(10) NOT NULL id
	private Integer type;
	private Integer userId;// int(10) NULL 会员id
	private Integer agencyId;// int(10) NULL 服务商id
	private Integer borrowId;// int(10) NULL 项目id
	private Integer borrowRepaymentId;// int(10) NULL 项目还款id
	private Integer endFlg;//1最后一期0非最后
	private BigDecimal money;// decimal(15,2) NULL 金额
	private String rechargeName;// varchar(64) NULL 充值账户名
	private Date rechargeDate;// datetime NULL 充值时间
	private Integer rechargeType;// int(10) NULL 充值类型
	private String rechargeAccount;// varchar(64) NULL 充值卡号
	private String rechargeBank;// varchar(64) NULL 充值银行
	private String rechargeFile;// varchar(128) NULL 充值凭证
	private String verifyAdmin;// varchar(64) NULL 审核人
	private String verifyPhone;// 审核人手机
	private Date verifyTime;// datetime NULL 审核时间
	private String verifyRemark;// text NULL 审核备注
	private Integer status;// int(10) NULL 状态0未1完2待审3拒绝4撤回
	private Date createDate;// datetime NULL 创建日期
	private Date modifyDate;// datetime NULL 修改日期
	
	
	private String borrowName;
	private String realName;
	private String username;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public Integer getBorrowRepaymentId() {
		return borrowRepaymentId;
	}

	public void setBorrowRepaymentId(Integer borrowRepaymentId) {
		this.borrowRepaymentId = borrowRepaymentId;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getRechargeName() {
		return rechargeName;
	}

	public void setRechargeName(String rechargeName) {
		this.rechargeName = rechargeName;
	}

	public Date getRechargeDate() {
		return rechargeDate;
	}

	public void setRechargeDate(Date rechargeDate) {
		this.rechargeDate = rechargeDate;
	}

	public Integer getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(Integer rechargeType) {
		this.rechargeType = rechargeType;
	}

	public String getRechargeAccount() {
		return rechargeAccount;
	}

	public void setRechargeAccount(String rechargeAccount) {
		this.rechargeAccount = rechargeAccount;
	}

	public String getRechargeBank() {
		return rechargeBank;
	}

	public void setRechargeBank(String rechargeBank) {
		this.rechargeBank = rechargeBank;
	}

	public String getRechargeFile() {
		return rechargeFile;
	}

	public void setRechargeFile(String rechargeFile) {
		this.rechargeFile = rechargeFile;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getVerifyRemark() {
		return verifyRemark;
	}

	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getEndFlg() {
		return endFlg;
	}

	public void setEndFlg(Integer endFlg) {
		this.endFlg = endFlg;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getVerifyAdmin() {
		return verifyAdmin;
	}

	public String getVerifyPhone() {
		return verifyPhone;
	}

	public void setVerifyAdmin(String verifyAdmin) {
		this.verifyAdmin = verifyAdmin;
	}

	public void setVerifyPhone(String verifyPhone) {
		this.verifyPhone = verifyPhone;
	}

}
