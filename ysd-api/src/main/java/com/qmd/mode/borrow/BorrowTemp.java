package com.qmd.mode.borrow;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BorrowTemp  implements Serializable{

	private static final long serialVersionUID = -1683848054235124333L;
	private Integer borrowId;//标的ID
	private Integer userId;//用户ID
	private Integer id;//还款记录ID
	private String title;//借款标题
	private Date repaymentDate;//应收时间
	private String name;//借款者
	private BigDecimal borrowAccount;//借款人的借款金额
	private Integer orderNum;//当前期数
	private Integer allNum;//总期数
	private BigDecimal repaymentAccount;//收款总额
	private BigDecimal capitalAccount;//应收本金
	private BigDecimal interestAccount;//应收利息
	private BigDecimal lateInterest;//逾期罚款金额
	private Integer lateDays;//逾期天数
	private Integer status;//状态
	
	private BigDecimal loanAmountFee;//借出总额
	private BigDecimal totalIncomeFee;//收益总额
	private BigDecimal totalReceivedFee;//已收总额
	private BigDecimal collectAmountFee;//待收总额
	private BigDecimal receivedInterestFee;//已收利息
	private BigDecimal collectInterestFee;//待收利息
	
	private BigDecimal ableAmount; // 投标的可用金额
	private BigDecimal continueAmount;//投标的续投金额
	
	private double apr;//年利率 
	private String timeLimit;//借款月数
	
	private int divides;// 总期数
	private String type; //标类型
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getAllNum() {
		return allNum;
	}
	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	public BigDecimal getRepaymentAccount() {
		return repaymentAccount==null?new BigDecimal(0):repaymentAccount;
	}
	public void setRepaymentAccount(BigDecimal repaymentAccount) {
		this.repaymentAccount = repaymentAccount;
	}
	public BigDecimal getCapitalAccount() {
		return capitalAccount==null?new BigDecimal(0):capitalAccount;
	}
	public void setCapitalAccount(BigDecimal capitalAccount) {
		this.capitalAccount = capitalAccount;
	}
	public BigDecimal getInterestAccount() {
		return interestAccount==null?new BigDecimal(0):interestAccount;
	}
	public void setInterestAccount(BigDecimal interestAccount) {
		this.interestAccount = interestAccount;
	}
	public BigDecimal getLateInterest() {
		return lateInterest==null?new BigDecimal(0):lateInterest;
	}
	public void setLateInterest(BigDecimal lateInterest) {
		this.lateInterest = lateInterest;
	}
	public Integer getLateDays() {
		return lateDays;
	}
	public void setLateDays(Integer lateDays) {
		this.lateDays = lateDays;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getLoanAmountFee() {
		return loanAmountFee==null?new BigDecimal(0):loanAmountFee;
	}
	public void setLoanAmountFee(BigDecimal loanAmountFee) {
		this.loanAmountFee = loanAmountFee;
	}
	public BigDecimal getTotalIncomeFee() {
		return totalIncomeFee==null?new BigDecimal(0):totalIncomeFee;
	}
	public void setTotalIncomeFee(BigDecimal totalIncomeFee) {
		this.totalIncomeFee = totalIncomeFee;
	}
	public BigDecimal getTotalReceivedFee() {
		return totalReceivedFee==null?new BigDecimal(0):totalReceivedFee;
	}
	public void setTotalReceivedFee(BigDecimal totalReceivedFee) {
		this.totalReceivedFee = totalReceivedFee;
	}
	public BigDecimal getCollectAmountFee() {
		return collectAmountFee==null?new BigDecimal(0):collectAmountFee;
	}
	public void setCollectAmountFee(BigDecimal collectAmountFee) {
		this.collectAmountFee = collectAmountFee;
	}
	public BigDecimal getReceivedInterestFee() {
		return receivedInterestFee==null?new BigDecimal(0):receivedInterestFee;
	}
	public void setReceivedInterestFee(BigDecimal receivedInterestFee) {
		this.receivedInterestFee = receivedInterestFee;
	}
	public BigDecimal getCollectInterestFee() {
		return collectInterestFee==null?new BigDecimal(0):collectInterestFee;
	}
	public void setCollectInterestFee(BigDecimal collectInterestFee) {
		this.collectInterestFee = collectInterestFee;
	}
	public Integer getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public BigDecimal getBorrowAccount() {
		return borrowAccount;
	}
	public void setBorrowAccount(BigDecimal borrowAccount) {
		this.borrowAccount = borrowAccount;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public double getApr() {
		return apr;
	}
	public void setApr(double apr) {
		this.apr = apr;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public int getDivides() {
		return divides;
	}
	public void setDivides(int divides) {
		this.divides = divides;
	}
	public BigDecimal getAbleAmount() {
		return ableAmount;
	}
	public void setAbleAmount(BigDecimal ableAmount) {
		this.ableAmount = ableAmount;
	}
	public BigDecimal getContinueAmount() {
		return continueAmount;
	}
	public void setContinueAmount(BigDecimal continueAmount) {
		this.continueAmount = continueAmount;
	}
	
}
