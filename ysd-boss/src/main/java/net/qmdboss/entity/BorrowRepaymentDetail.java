package net.qmdboss.entity;

import net.qmdboss.util.CommonUtil;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * BorrowRepaymentDetail 还款明细类
 * @author 詹锋
 *
 */
@Entity
public class BorrowRepaymentDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer status;//还款状态【0：未还; 1：已还】
	private Integer webstatus;//网站是否替借款人还款【0：未还; 1：已还】
	private Integer orderNum;//借款标分期顺序
	private Date repaymentTime;//预计本期还款时间
	private Date repaymentYestime;//本期还款时间
	private BigDecimal repaymentAccount;//预计本期应还金额
	private BigDecimal repaymentYesaccount;//本期已还金额
	private Integer lateDays;//逾期天数
	private BigDecimal lateInterest;//逾期罚款金额
	private BigDecimal interest;//本期所还利息
	private BigDecimal capital;//本期所还本金
	private BigDecimal forfeit;//滞纳金
	private BigDecimal reminderFee;//提醒费--改为奖励使用
	private Date addTime;//添加时间
	private String operatorId;//操作IP
	private Integer repaymentDateInt;
	private Borrow borrow;
	private Set<UserRepaymentDetail> userRepaymentDetailSet=new LinkedHashSet<UserRepaymentDetail>();//投资人
	
	private Integer rechargeStatus;
	private BigDecimal rechargeMoney;
	
	
	@OneToMany(mappedBy = "borrowRepaymentDetail", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Set<UserRepaymentDetail> getUserRepaymentDetailSet() {
		return userRepaymentDetailSet;
	}
	public void setUserRepaymentDetailSet(
			Set<UserRepaymentDetail> userRepaymentDetailSet) {
		this.userRepaymentDetailSet = userRepaymentDetailSet;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_borRepdetail_borrow")
	public Borrow getBorrow() {
		return borrow;
	}
	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}
	
	@Transient
	public BigDecimal getShowDepositMoney() {
		if (repaymentDateInt==CommonUtil.getIntDate(borrow.getFinalRepayDate())) {
			return borrow.getDepositMoney();
		}
		return BigDecimal.ZERO;
		
	}
	
	@Transient
	public BigDecimal getShowRepaymentAccount() {
		return repaymentAccount.subtract(getShowDepositMoney());
	}
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getWebstatus() {
		return webstatus;
	}
	public void setWebstatus(Integer webstatus) {
		this.webstatus = webstatus;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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
	public BigDecimal getRepaymentAccount() {
		return repaymentAccount;
	}
	public void setRepaymentAccount(BigDecimal repaymentAccount) {
		this.repaymentAccount = repaymentAccount;
	}
	public BigDecimal getRepaymentYesaccount() {
		return repaymentYesaccount;
	}
	public void setRepaymentYesaccount(BigDecimal repaymentYesaccount) {
		this.repaymentYesaccount = repaymentYesaccount;
	}
	public Integer getLateDays() {
		return lateDays;
	}
	public void setLateDays(Integer lateDays) {
		this.lateDays = lateDays;
	}
	public BigDecimal getLateInterest() {
		return lateInterest;
	}
	public void setLateInterest(BigDecimal lateInterest) {
		this.lateInterest = lateInterest;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	public BigDecimal getCapital() {
		return capital;
	}
	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}
	public BigDecimal getForfeit() {
		return forfeit;
	}
	public void setForfeit(BigDecimal forfeit) {
		this.forfeit = forfeit;
	}
	public BigDecimal getReminderFee() {
		return reminderFee;
	}
	public void setReminderFee(BigDecimal reminderFee) {
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
	public Integer getRepaymentDateInt() {
		return repaymentDateInt;
	}
	public void setRepaymentDateInt(Integer repaymentDateInt) {
		this.repaymentDateInt = repaymentDateInt;
	}
	public Integer getRechargeStatus() {
		return rechargeStatus;
	}
	public void setRechargeStatus(Integer rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}
	public BigDecimal getRechargeMoney() {
		return rechargeMoney;
	}
	public void setRechargeMoney(BigDecimal rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}
}
