package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

/**
 * UserRrepaymentDetail 投资人每期收益明细
 * @author 詹锋
 * time 2013-1-24
 */
@Entity
public class UserRepaymentDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String status;//还款状态【0-为还；1已还】
	private BigDecimal account;//实际成功金额
	private BigDecimal repaymentAccount;//应还款金额（借款金额+利息）
	private BigDecimal interest;//借款利息
	private BigDecimal repaymentYesaccount;//已还款金额
	private BigDecimal waitAccount;//本期待还款本金
	private BigDecimal repaymentYesinterest;//已还利息
	private BigDecimal waitInterest;//本期待还利息
	private String addPersion;//添加人（作操用户ID）
	private String operatorIp;//操作IP
	private Integer borrowPeriods;//标还款顺序
	private BigDecimal serviceCharge;//管理费
	private BigDecimal profit;//净收益
	
	private Date repaymentDate;//已还款时间
	
	private User user;//发标人人信息
	private Borrow borrow;//对应标
	private BorrowRepaymentDetail  borrowRepaymentDetail;//对应还款明细
	private BorrowDetail borrowDetail;
	
	private String applyContinueTotal;
	
	private Integer borrowDivides;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_userRrepaymentDetail_user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_userRrepaymentDetail_borrow")
	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_userRrepaymentDetail_borrowRepaymentDetail")
	public BorrowRepaymentDetail getBorrowRepaymentDetail() {
		return borrowRepaymentDetail;
	}

	public void setBorrowRepaymentDetail(BorrowRepaymentDetail borrowRepaymentDetail) {
		this.borrowRepaymentDetail = borrowRepaymentDetail;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_userRrepaymentDetail_borrowDetail")
	public BorrowDetail getBorrowDetail() {
		return borrowDetail;
	}

	public void setBorrowDetail(BorrowDetail borrowDetail) {
		this.borrowDetail = borrowDetail;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getAccount() {
		return account;
	}
	public void setAccount(BigDecimal account) {
		this.account = account;
	}
	public BigDecimal getRepaymentAccount() {
		return repaymentAccount;
	}
	public void setRepaymentAccount(BigDecimal repaymentAccount) {
		this.repaymentAccount = repaymentAccount;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	public BigDecimal getRepaymentYesaccount() {
		return repaymentYesaccount;
	}
	public void setRepaymentYesaccount(BigDecimal repaymentYesaccount) {
		this.repaymentYesaccount = repaymentYesaccount;
	}
	public BigDecimal getWaitAccount() {
		return waitAccount;
	}
	public void setWaitAccount(BigDecimal waitAccount) {
		this.waitAccount = waitAccount;
	}
	public BigDecimal getRepaymentYesinterest() {
		return repaymentYesinterest;
	}
	public void setRepaymentYesinterest(BigDecimal repaymentYesinterest) {
		this.repaymentYesinterest = repaymentYesinterest;
	}
	public BigDecimal getWaitInterest() {
		return waitInterest;
	}
	public void setWaitInterest(BigDecimal waitInterest) {
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

	public Integer getBorrowPeriods() {
		return borrowPeriods;
	}

	public void setBorrowPeriods(Integer borrowPeriods) {
		this.borrowPeriods = borrowPeriods;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public Date getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
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
