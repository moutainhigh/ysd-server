package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
@Entity
public class ReportUserAccountBackup extends BaseEntity {

	private static final long serialVersionUID = 7914214980408604193L;

	private Integer accountId;// 编号
	private BigDecimal total;// 用户总金额（可用金额+冻结金额+待收金额）
	private BigDecimal ableMoney;// 可用金额，可提现金额
	private BigDecimal unableMoney;// 冻结金额
	private BigDecimal collection; // 待收金额
	private BigDecimal investorCollectionCapital;// 投资者待收本金
	private BigDecimal investorCollectionInterest;// 投资者待收利息
	private BigDecimal borrowerCollectionCapital;// 借款人待付本金
	private BigDecimal borrowerCollectionInterest;// 借款人待付利息
	private Date dailyCreateDate;// 创建时间（一般为对象日期第二天凌晨）
	private Integer dailyWorkDateInt;// 对象日期（创建日期的前一天 YYYYMMDD）
	private BigDecimal continueTotal;
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_report_user_account_backup_user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	



	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getAbleMoney() {
		return ableMoney;
	}

	public void setAbleMoney(BigDecimal ableMoney) {
		this.ableMoney = ableMoney;
	}

	public BigDecimal getUnableMoney() {
		return unableMoney;
	}

	public void setUnableMoney(BigDecimal unableMoney) {
		this.unableMoney = unableMoney;
	}

	public BigDecimal getCollection() {
		return collection;
	}

	public void setCollection(BigDecimal collection) {
		this.collection = collection;
	}

	public BigDecimal getInvestorCollectionCapital() {
		return investorCollectionCapital;
	}

	public void setInvestorCollectionCapital(
			BigDecimal investorCollectionCapital) {
		this.investorCollectionCapital = investorCollectionCapital;
	}

	public BigDecimal getInvestorCollectionInterest() {
		return investorCollectionInterest;
	}

	public void setInvestorCollectionInterest(
			BigDecimal investorCollectionInterest) {
		this.investorCollectionInterest = investorCollectionInterest;
	}

	public BigDecimal getBorrowerCollectionCapital() {
		return borrowerCollectionCapital;
	}

	public void setBorrowerCollectionCapital(
			BigDecimal borrowerCollectionCapital) {
		this.borrowerCollectionCapital = borrowerCollectionCapital;
	}

	public BigDecimal getBorrowerCollectionInterest() {
		return borrowerCollectionInterest;
	}

	public void setBorrowerCollectionInterest(
			BigDecimal borrowerCollectionInterest) {
		this.borrowerCollectionInterest = borrowerCollectionInterest;
	}

	public Date getDailyCreateDate() {
		return dailyCreateDate;
	}

	public void setDailyCreateDate(Date dailyCreateDate) {
		this.dailyCreateDate = dailyCreateDate;
	}

	public Integer getDailyWorkDateInt() {
		return dailyWorkDateInt;
	}

	public void setDailyWorkDateInt(Integer dailyWorkDateInt) {
		this.dailyWorkDateInt = dailyWorkDateInt;
	}

	public BigDecimal getContinueTotal() {
		return continueTotal;
	}

	public void setContinueTotal(BigDecimal continueTotal) {
		this.continueTotal = continueTotal;
	}

}
