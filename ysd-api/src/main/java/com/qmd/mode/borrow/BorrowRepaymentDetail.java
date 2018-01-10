package com.qmd.mode.borrow;

import com.qmd.util.CommonUtil;
import com.qmd.util.NumberUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * BorrowRepaymentDetail 标还款明细记录属性类
 * 
 * @author zhanf
 * 
 */
public class BorrowRepaymentDetail {

	private int id;// id号
	private int status;// 还款状态【0：未还; 1：已还】
	private int webstatus;// 网站是否替借款人还款【0：未还; 1：已还】
	private int orderNum;// 借款标分期顺序
	private int borrowId;// 标ID
	private Date repaymentTime;// 预计本期还款时间
	private Date repaymentYestime;// 本期时间还款时间
	private String repaymentAccount;// 本期应还金额
	private String repaymentYesaccount;// 本期已还金额
	private int lateDays;// 逾期天数
	private String lateInterest;// 逾期罚款金额
	private String interest;// 本期所还利息
	private String capital;// 本期所还本金
	private String forfeit;// 滞纳金
	private String reminderFee;// 提醒费
	private Date addTime;// 添加时间
	private String operatorId;// 操作IP
	private Date createDate;
	private Date modifyDate;
	private Integer repaymentDateInt;// 还款时间
	
	private List<BorrowRepaymentDetail> borrowRepaymentDetailList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getWebstatus() {
		return webstatus;
	}

	public void setWebstatus(int webstatus) {
		this.webstatus = webstatus;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
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

	public String getRepaymentAccount() {
		return repaymentAccount;
	}

	public void setRepaymentAccount(String repaymentAccount) {
		this.repaymentAccount = repaymentAccount;
	}

	public String getRepaymentYesaccount() {
		return repaymentYesaccount;
	}

	public void setRepaymentYesaccount(String repaymentYesaccount) {
		this.repaymentYesaccount = repaymentYesaccount;
	}

	public int getLateDays() {
		return lateDays;
	}

	public void setLateDays(int lateDays) {
		this.lateDays = lateDays;
	}

	public String getLateInterest() {
		return lateInterest;
	}

	public void setLateInterest(String lateInterest) {
		this.lateInterest = lateInterest;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getForfeit() {
		return forfeit;
	}

	public void setForfeit(String forfeit) {
		this.forfeit = forfeit;
	}

	public String getReminderFee() {
		return reminderFee;
	}

	public void setReminderFee(String reminderFee) {
		this.reminderFee = reminderFee;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
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

	public Integer getRepaymentDateInt() {
		return repaymentDateInt;
	}

	public void setRepaymentDateInt(Integer repaymentDateInt) {
		this.repaymentDateInt = repaymentDateInt;
	}

	public List<BorrowRepaymentDetail> getBorrowRepaymentDetailList() {
		return borrowRepaymentDetailList;
	}

	public void setBorrowRepaymentDetailList(
			List<BorrowRepaymentDetail> borrowRepaymentDetailList) {
		this.borrowRepaymentDetailList = borrowRepaymentDetailList;
	}

	public double getShowRepayTotal() {

		return CommonUtil.string2double(interest)
				+ CommonUtil.string2double(capital);
	}
	
	public BigDecimal getShowRepayTotal2() {
		return NumberUtil.double2BigDecimal(getShowRepayTotal());
	}

}
