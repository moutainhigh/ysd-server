package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

/**
 * Account 用户帐户属性类
 * @author 詹锋
 *
 */
@Entity
public class UserAccount extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private Integer userId;//用户ID
	private BigDecimal total;//用户总金额（可用金额+冻结金额+待收金额）
	private BigDecimal ableMoney;//可用金额，可提现金额
	private BigDecimal unableMoney;//冻结金额
	private BigDecimal collection;//待收金额
	private BigDecimal investorCollectionCapital;//投资者待收本金
	private BigDecimal investorCollectionInterest;//投资者待收利息
	private BigDecimal	borrowerCollectionCapital;//借款人待付本金
	private BigDecimal borrowerCollectionInterest;//借款人待付利息
	private BigDecimal continueTotal;//用户续投总额
	private Integer userPoints;
	private BigDecimal awardMoney;//奖励
	
	private BigDecimal tasteMoney;// 体验资金
	
	private User user;//关联用户表
	
	@OneToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_account_user")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
//	public Integer getUserId() {
//		return userId;
//	}
//	public void setUserId(Integer userId) {
//		this.userId = userId;
//	}
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
	public void setInvestorCollectionCapital(BigDecimal investorCollectionCapital) {
		this.investorCollectionCapital = investorCollectionCapital;
	}
	public BigDecimal getInvestorCollectionInterest() {
		return investorCollectionInterest;
	}
	public void setInvestorCollectionInterest(BigDecimal investorCollectionInterest) {
		this.investorCollectionInterest = investorCollectionInterest;
	}
	public BigDecimal getBorrowerCollectionCapital() {
		return borrowerCollectionCapital;
	}
	public void setBorrowerCollectionCapital(BigDecimal borrowerCollectionCapital) {
		this.borrowerCollectionCapital = borrowerCollectionCapital;
	}
	public BigDecimal getBorrowerCollectionInterest() {
		return borrowerCollectionInterest;
	}
	public void setBorrowerCollectionInterest(BigDecimal borrowerCollectionInterest) {
		this.borrowerCollectionInterest = borrowerCollectionInterest;
	}
	public BigDecimal getContinueTotal() {
		return continueTotal;
	}
	public void setContinueTotal(BigDecimal continueTotal) {
		this.continueTotal = continueTotal;
	}
	public Integer getUserPoints() {
		return userPoints;
	}
	public void setUserPoints(Integer userPoints) {
		this.userPoints = userPoints;
	}
	public BigDecimal getAwardMoney() {
		return awardMoney;
	}
	public void setAwardMoney(BigDecimal awardMoney) {
		this.awardMoney = awardMoney;
	}
	public BigDecimal getTasteMoney() {
		return tasteMoney;
	}
	public void setTasteMoney(BigDecimal tasteMoney) {
		this.tasteMoney = tasteMoney;
	}
	
}
