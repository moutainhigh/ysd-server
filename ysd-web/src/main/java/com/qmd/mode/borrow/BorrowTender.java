package com.qmd.mode.borrow;

import com.qmd.util.CalculationUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * fy_borrow_detail表
 * @author 
 *
 */
public class BorrowTender {
	 private Integer id;

	 private Integer userId;//用户ID
	 private String status;//投标状态【1：全部通过;2：部分通过】
	 private Integer backStatus;//回滚状态值[0-表示没有回滚，1-表示撤销回滚】
	 private Integer borrowId;//标ID
	 private String money;//预计投标金额
	 private String account;//实际成功金额
	 private String repaymentAccount;//应还款金额（借款金额+利息）
	 private String interest;//借款利息
	 private String repaymentYesaccount;//已还款金额
	 private String waitAccount;//待还款金额
	 private String repaymentYesinterest;//已还利息
	 private String waitInterest;//待还利息
	 private String addPersion;//添加人（投标用户ID）
	 private Date createTime;//创建时间
	 private String operatorIp;//操作IP
	 private String payPassword;//支付密码
	 private Date modifyDate;
	 private Date createDate;
	 private String username;
	 
	 private String wanderPiece;
	 
	 private String ableAmount;//可用金额
	 private String continueAmount;// 续投金额
	 private String hongbaoAmount;//红包金额
	 private String tasteAmount;//体验资金
	 
	 
	 private Date repaymentDate;
	 private BigDecimal loanAmountFee;//借出总额
	 private String name;
	 private BigDecimal borrowAccount;//借款人的借款金额
	 private double apr;//年利率 
	 private String title;
	 private String type; //标类型
	 private String style;//还款方式
	 private String isday;// 是否天标
	 private String timeLimit;
	 private Integer autoTenderStatus; // 1自动，0手动
	 private Integer borrowStatus;
	 
	 private BigDecimal varyYearRate;
	 private Integer varyMonthLimit;
	 private String reward;//流转标计算的奖励
	 private Integer clientType;//

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
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
	public Integer getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOperatorIp() {
		return operatorIp;
	}
	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}
	public String getWanderPiece() {
		return wanderPiece;
	}
	public void setWanderPiece(String wanderPiece) {
		this.wanderPiece = wanderPiece;
	}
	public String getAbleAmount() {
		return ableAmount;
	}
	public void setAbleAmount(String ableAmount) {
		this.ableAmount = ableAmount;
	}
	public String getContinueAmount() {
		return continueAmount;
	}
	public void setContinueAmount(String continueAmount) {
		this.continueAmount = continueAmount;
	}
	public Integer getBackStatus() {
		return backStatus;
	}
	public void setBackStatus(Integer backStatus) {
		this.backStatus = backStatus;
	}
	public Date getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public BigDecimal getLoanAmountFee() {
		return loanAmountFee;
	}
	public void setLoanAmountFee(BigDecimal loanAmountFee) {
		this.loanAmountFee = loanAmountFee;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getBorrowAccount() {
		return borrowAccount;
	}
	public void setBorrowAccount(BigDecimal borrowAccount) {
		this.borrowAccount = borrowAccount;
	}
	public double getApr() {
		return apr;
	}
	public void setApr(double apr) {
		this.apr = apr;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getAutoTenderStatus() {
		return autoTenderStatus;
	}
	public void setAutoTenderStatus(Integer autoTenderStatus) {
		this.autoTenderStatus = autoTenderStatus;
	}
	public BigDecimal getVaryYearRate() {
		return varyYearRate;
	}
	public void setVaryYearRate(BigDecimal varyYearRate) {
		this.varyYearRate = varyYearRate;
	}
	public Integer getVaryMonthLimit() {
		return varyMonthLimit;
	}
	public void setVaryMonthLimit(Integer varyMonthLimit) {
		this.varyMonthLimit = varyMonthLimit;
	}
	
	public BigDecimal getShowMoney() {
		if(money==null)return BigDecimal.ZERO;
		return new BigDecimal(money);
	}
	
	public BigDecimal getShowAccount() {
		if(account==null)return BigDecimal.ZERO;
		return new BigDecimal(account);
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getIsday() {
		return isday;
	}
	public void setIsday(String isday) {
		this.isday = isday;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getHongbaoAmount() {
		return hongbaoAmount;
	}
	public void setHongbaoAmount(String hongbaoAmount) {
		this.hongbaoAmount = hongbaoAmount;
	}
	public Integer getBorrowStatus() {
		return borrowStatus;
	}
	public void setBorrowStatus(Integer borrowStatus) {
		this.borrowStatus = borrowStatus;
	}
	public String getTasteAmount() {
		return tasteAmount;
	}
	public void setTasteAmount(String tasteAmount) {
		this.tasteAmount = tasteAmount;
	}
	
	public String getCreateDateStr(){
		return CalculationUtil.getTimeDiff(createDate.getTime());
	}
	public Integer getClientType() {
		return clientType;
	}
	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}
	
}
