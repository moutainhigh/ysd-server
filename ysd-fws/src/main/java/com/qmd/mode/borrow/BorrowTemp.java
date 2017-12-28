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
	private String businessCode;//项目编号
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
	private BigDecimal continueAmount;//投标的续单金额
	
	private double apr;//年利率 
	private String timeLimit;//借款月数
	
	private int divides;// 总期数
	private String type; //标类型
	
	private BigDecimal rateYearHeight;//最高利率（年化）
	
	// 灵活宝
	private Integer borrowAgileId;//
	private Integer agileFlg; // 0定投宝1灵活宝2灵活宝完成
	private Integer agileStatus;//灵活宝状态： 0无，1进行中，2封闭期,3待定,9已完成 
	private String agileCode;
	private BigDecimal agileMoney; // 灵活宝在投金额'
	private BigDecimal agilePeriodYesinterest; // 周期内已获收益
	private Integer agileStepIntdate; // 下次阶梯变化日期
	private BigDecimal agileYesinterest;//
	private Date agileOpenDate;//可赎回开始日期（封闭期次日）
	private Date agileFinalDate;
	
	// 逾期相关字段
	private Integer lateStatus;//逾期状态：0正常1利息逾期2项目逾期
	private BigDecimal latePenalty;//逾期罚金
	private BigDecimal lateAccount;//逾期金额
	private BigDecimal lateAccountPaid;//
	
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
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public BigDecimal getRateYearHeight() {
		return rateYearHeight;
	}
	public void setRateYearHeight(BigDecimal rateYearHeight) {
		this.rateYearHeight = rateYearHeight;
	}
	public Integer getBorrowAgileId() {
		return borrowAgileId;
	}
	public void setBorrowAgileId(Integer borrowAgileId) {
		this.borrowAgileId = borrowAgileId;
	}
	public Integer getAgileFlg() {
		return agileFlg;
	}
	public void setAgileFlg(Integer agileFlg) {
		this.agileFlg = agileFlg;
	}
	public Integer getAgileStatus() {
		return agileStatus;
	}
	public void setAgileStatus(Integer agileStatus) {
		this.agileStatus = agileStatus;
	}
	public String getAgileCode() {
		return agileCode;
	}
	public void setAgileCode(String agileCode) {
		this.agileCode = agileCode;
	}
	public BigDecimal getAgileMoney() {
		return agileMoney;
	}
	public void setAgileMoney(BigDecimal agileMoney) {
		this.agileMoney = agileMoney;
	}
	public BigDecimal getAgilePeriodYesinterest() {
		return agilePeriodYesinterest;
	}
	public void setAgilePeriodYesinterest(BigDecimal agilePeriodYesinterest) {
		this.agilePeriodYesinterest = agilePeriodYesinterest;
	}
	public Integer getAgileStepIntdate() {
		return agileStepIntdate;
	}
	public void setAgileStepIntdate(Integer agileStepIntdate) {
		this.agileStepIntdate = agileStepIntdate;
	}
	public BigDecimal getAgileYesinterest() {
		return agileYesinterest;
	}
	public void setAgileYesinterest(BigDecimal agileYesinterest) {
		this.agileYesinterest = agileYesinterest;
	}
	public Date getAgileOpenDate() {
		return agileOpenDate;
	}
	public void setAgileOpenDate(Date agileOpenDate) {
		this.agileOpenDate = agileOpenDate;
	}
	public Date getAgileFinalDate() {
		return agileFinalDate;
	}
	public void setAgileFinalDate(Date agileFinalDate) {
		this.agileFinalDate = agileFinalDate;
	}
	public Integer getLateStatus() {
		return lateStatus;
	}
	public void setLateStatus(Integer lateStatus) {
		this.lateStatus = lateStatus;
	}
	public BigDecimal getLatePenalty() {
		return latePenalty;
	}
	public void setLatePenalty(BigDecimal latePenalty) {
		this.latePenalty = latePenalty;
	}
	public BigDecimal getLateAccount() {
		return lateAccount;
	}
	public void setLateAccount(BigDecimal lateAccount) {
		this.lateAccount = lateAccount;
	}
	public BigDecimal getLateAccountPaid() {
		return lateAccountPaid;
	}
	public void setLateAccountPaid(BigDecimal lateAccountPaid) {
		this.lateAccountPaid = lateAccountPaid;
	}
	
}
