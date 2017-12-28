package com.qmd.mode.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户投标分期还款明细
 */
public class UserRepaymentDetail implements Serializable {

	private static final long serialVersionUID = 2383266145314663467L;

	private Integer id; // ID
	private Integer userId; // 用户ID
	private String status; // 还款状态
	private Integer borrowId; // 标ID
	private String account; // 实际成功金额
	private String repaymentAccount; // 应还款金额（借款金额+利息）
	private String interest; // 借款利息
	private String repaymentYesaccount; // 已还款金额
	private String waitAccount; // 待还款金额
	private String repaymentYesinterest; // 已还利息
	private String waitInterest; // 待还利息
	private String addPersion; // 添加人（投标用户ID）
	private String operatorIp; // 操作IP
	private Date createDate; // 创建时间
	private Date modifyDate; // 修改时间
	private Integer borrowRepaymentId; // 标还款分期ID
	private Integer borrowPeriods; // 标还款顺序
	
	private Date repaymentDate;//应还款时间
	
	private Integer borrowDetailId;//投标ID
	
	private String serviceCharge;//管理费
	private String profit;//净收益
	private String applyContinueTotal;//是否申请续投，如不为0或空，为已申请


	// 关联表的项目

	private String username; // 用户名
	private String borrowName; // 标名
	private String borrowUserName; //借款者用户名
	private String timeLimit; // 标的期限
	private Date repaymentTime;// 本期预计还款时间
	private Date repaymentYestime;// 本期已还款时间
	
	private String orderBy;
	private Integer borrowDivides;//标的期数
	private int divides;// 总期数
	private int borrowtype;//标的类型
	

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRepaymentAccount() {
		return repaymentAccount;
	}

	public void setRepaymentAccount(String repaymentAccount) {
		this.repaymentAccount = repaymentAccount;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getRepaymentYesaccount() {
		return repaymentYesaccount;
	}

	public void setRepaymentYesaccount(String repaymentYesaccount) {
		this.repaymentYesaccount = repaymentYesaccount;
	}

	public String getWaitAccount() {
		return waitAccount;
	}

	public void setWaitAccount(String waitAccount) {
		this.waitAccount = waitAccount;
	}

	public String getRepaymentYesinterest() {
		return repaymentYesinterest;
	}

	public void setRepaymentYesinterest(String repaymentYesinterest) {
		this.repaymentYesinterest = repaymentYesinterest;
	}

	public String getWaitInterest() {
		return waitInterest;
	}

	public void setWaitInterest(String waitInterest) {
		this.waitInterest = waitInterest;
	}

	public String getAddPersion() {
		return addPersion;
	}

	public void setAddPersion(String addPersion) {
		this.addPersion = addPersion;
	}

	public String getOperatorIp() {
		return operatorIp;
	}

	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
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

	public Integer getBorrowRepaymentId() {
		return borrowRepaymentId;
	}

	public void setBorrowRepaymentId(Integer borrowRepaymentId) {
		this.borrowRepaymentId = borrowRepaymentId;
	}

	public Integer getBorrowPeriods() {
		return borrowPeriods;
	}

	public void setBorrowPeriods(Integer borrowPeriods) {
		this.borrowPeriods = borrowPeriods;
	}

	public Date getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public Date getRepaymentTime() {
		return repaymentTime;
	}

	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}

	public Date getRepaymentYestime() {
		return repaymentYestime;
	}

	public void setRepaymentYestime(Date repaymentYestime) {
		this.repaymentYestime = repaymentYestime;
	}

	public String getBorrowUserName() {
		return borrowUserName;
	}

	public void setBorrowUserName(String borrowUserName) {
		this.borrowUserName = borrowUserName;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(String serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public int getDivides() {
		return divides;
	}

	public void setDivides(int divides) {
		this.divides = divides;
	}

	public int getBorrowtype() {
		return borrowtype;
	}

	public void setBorrowtype(int borrowtype) {
		this.borrowtype = borrowtype;
	}

	public Integer getBorrowDetailId() {
		return borrowDetailId;
	}

	public void setBorrowDetailId(Integer borrowDetailId) {
		this.borrowDetailId = borrowDetailId;
	}

	public String getApplyContinueTotal() {
		return applyContinueTotal;
	}

	public void setApplyContinueTotal(String applyContinueTotal) {
		this.applyContinueTotal = applyContinueTotal;
	}

	public Integer getBorrowDivides() {
		return borrowDivides;
	}

	public void setBorrowDivides(Integer borrowDivides) {
		this.borrowDivides = borrowDivides;
	}
	
}
