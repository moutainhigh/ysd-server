package com.qmd.mode.amount;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户投标记录总记录
 * @author Xsf
 *
 */
public class Amount  implements Serializable{
	
	private static final long serialVersionUID = -4510602661057247523L;
	
	private BigDecimal repaymentYesinterest;//已收利息总额 
	private BigDecimal waitInterest;//待收利息总额
	private BigDecimal tenderAccount;//用户投标总额
	private BigDecimal recentDueInAccount;//最近待收金额
	private Date recentDueInDate;//最近待收时间
	private BigDecimal recentRefundAccount;//最近待还金额
	private Date recentRefundInDate;//最近待还金额
	
	public Amount(){
		repaymentYesinterest = new BigDecimal(0);
		tenderAccount = new BigDecimal(0);
		waitInterest = new BigDecimal(0);
		recentDueInAccount = new BigDecimal(0);
		recentDueInDate=new Date();
	}
	public BigDecimal getRepaymentYesinterest() {
		return repaymentYesinterest;
	}
	public void setRepaymentYesinterest(BigDecimal repaymentYesinterest) {
		this.repaymentYesinterest = repaymentYesinterest;
	}
	public BigDecimal getTenderAccount() {
		return tenderAccount;
	}
	public void setTenderAccount(BigDecimal tenderAccount) {
		this.tenderAccount = tenderAccount;
	}
	public BigDecimal getWaitInterest() {
		return waitInterest;
	}
	public void setWaitInterest(BigDecimal waitInterest) {
		this.waitInterest = waitInterest;
	}
	public BigDecimal getRecentDueInAccount() {
		return recentDueInAccount;
	}
	public void setRecentDueInAccount(BigDecimal recentDueInAccount) {
		this.recentDueInAccount = recentDueInAccount;
	}
	public Date getRecentDueInDate() {
		return recentDueInDate;
	}
	public void setRecentDueInDate(Date recentDueInDate) {
		this.recentDueInDate = recentDueInDate;
	}
	public BigDecimal getRecentRefundAccount() {
		return recentRefundAccount;
	}
	public void setRecentRefundAccount(BigDecimal recentRefundAccount) {
		this.recentRefundAccount = recentRefundAccount;
	}
	public Date getRecentRefundInDate() {
		return recentRefundInDate;
	}
	public void setRecentRefundInDate(Date recentRefundInDate) {
		this.recentRefundInDate = recentRefundInDate;
	}
	
	
}
