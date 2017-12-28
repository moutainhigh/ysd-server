package com.qmd.mode.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 用户账户总表
 * @author xsf
 *
 */
public class UserAccount  implements Serializable{

	private static final long serialVersionUID = 4369348773018451326L;

	private Integer id ;//编号
	private Date createDate;//添加时间
    private Date modifyDate;//修改时间
	private Integer userId;//用户ID
	private BigDecimal total;//用户总金额（可用金额+冻结金额+待收金额）
	private BigDecimal ableMoney;//可用金额，可提现金额
	private BigDecimal unableMoney;//冻结金额
	private BigDecimal collection; //待收金额
	private BigDecimal investorCollectionCapital;//投资者待收本金
	private BigDecimal investorCollectionInterest;//投资者待收利息
	private BigDecimal	borrowerCollectionCapital;//借款人待付本金
	private BigDecimal borrowerCollectionInterest;//借款人待付利息
	private BigDecimal continueTotal;//用户续投总额
	
	private BigDecimal tasteMoney;// 体验资金 -- 不计入总额
	
	//-------推广加-------------
	private BigDecimal awardMoney;//奖励

	
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
	public BigDecimal getTotal() {
		return total == null ? new BigDecimal(0) :total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getAbleMoney() {
		return ableMoney == null ? new BigDecimal(0) :ableMoney;
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
		return collection == null ? new BigDecimal(0) :collection;
	}
	public void setCollection(BigDecimal collection) {
		this.collection = collection;
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
		return continueTotal == null ? new BigDecimal(0) :continueTotal;
	}
	public void setContinueTotal(BigDecimal continueTotal) {
		this.continueTotal = continueTotal;
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
