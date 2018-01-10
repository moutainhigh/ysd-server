package com.qmd.mode.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户账户总表
 * 
 * @author xsf
 * 
 */
public class UserAccount implements Serializable {

	private static final long serialVersionUID = 4369348773018451326L;

	private Integer id;// 编号
	private Date createDate;// 添加时间
	private Date modifyDate;// 修改时间
	private Integer userId;// 用户ID
	private BigDecimal total;// 用户总金额（可用金额+冻结金额+待收金额）
	private BigDecimal ableMoney;// 可用金额，可提现金额
	private BigDecimal unableMoney;// 冻结金额
	private BigDecimal collection; // 待收金额
	private BigDecimal investorCollectionCapital;// 投资者待收本金
	private BigDecimal investorCollectionInterest;// 投资者待收利息
	private BigDecimal borrowerCollectionCapital;// 借款人待付本金
	private BigDecimal borrowerCollectionInterest;// 借款人待付利息
	private BigDecimal continueTotal;// 用户续投总额
	private BigDecimal cashMoney;// 冻结提现金额

	private BigDecimal wmpsMoney;// 理财冻结资金

	private BigDecimal tasteMoney;// 体验资金
	private BigDecimal tasteMoneyFrozen;// 体验资金冻结
	private BigDecimal investorCollectionTaste;// 投资者待收体验资金
	private BigDecimal borrowerCollectionTaste;// 借款人待付体验资金
	private BigDecimal directMoney;// 直投资金
	private BigDecimal directMoneyFrozen;// 直投资金冻结
	private BigDecimal investorCollectionDirect;// 投资者待收直投资金
	private BigDecimal borrowerCollectionDirect;// 借款人待付直投资金
	
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
		return total == null ? new BigDecimal(0) : total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getAbleMoney() {
		return ableMoney == null ? new BigDecimal(0) : ableMoney;
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
		return collection == null ? new BigDecimal(0) : collection;
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

	public BigDecimal getContinueTotal() {
		return continueTotal == null ? new BigDecimal(0) : continueTotal;
	}

	public void setContinueTotal(BigDecimal continueTotal) {
		this.continueTotal = continueTotal;
	}

	public BigDecimal getCashMoney() {
		return cashMoney;
	}

	public void setCashMoney(BigDecimal cashMoney) {
		this.cashMoney = cashMoney;
	}

	public BigDecimal getWmpsMoney() {
		return wmpsMoney == null ? new BigDecimal(0) : wmpsMoney;
	}

	public void setWmpsMoney(BigDecimal wmpsMoney) {
		this.wmpsMoney = wmpsMoney;
	}

	public BigDecimal getTasteMoney() {
		return tasteMoney == null ? new BigDecimal(0) : tasteMoney;
	}

	public void setTasteMoney(BigDecimal tasteMoney) {
		this.tasteMoney = tasteMoney;
	}

	public BigDecimal getTasteMoneyFrozen() {
		return tasteMoneyFrozen == null ? new BigDecimal(0) : tasteMoneyFrozen;
	}

	public void setTasteMoneyFrozen(BigDecimal tasteMoneyFrozen) {
		this.tasteMoneyFrozen = tasteMoneyFrozen;
	}

	public BigDecimal getInvestorCollectionTaste() {
		return investorCollectionTaste == null ? new BigDecimal(0)
				: investorCollectionTaste;
	}

	public void setInvestorCollectionTaste(BigDecimal investorCollectionTaste) {
		this.investorCollectionTaste = investorCollectionTaste;
	}

	public BigDecimal getBorrowerCollectionTaste() {
		return borrowerCollectionTaste == null ? new BigDecimal(0)
				: borrowerCollectionTaste;
	}

	public void setBorrowerCollectionTaste(BigDecimal borrowerCollectionTaste) {
		this.borrowerCollectionTaste = borrowerCollectionTaste;
	}

	public BigDecimal getDirectMoney() {
		return directMoney == null ? new BigDecimal(0) : directMoney;
	}

	public void setDirectMoney(BigDecimal directMoney) {
		this.directMoney = directMoney;
	}

	public BigDecimal getDirectMoneyFrozen() {
		return directMoneyFrozen == null ? new BigDecimal(0)
				: directMoneyFrozen;
	}

	public void setDirectMoneyFrozen(BigDecimal directMoneyFrozen) {
		this.directMoneyFrozen = directMoneyFrozen;
	}

	public BigDecimal getInvestorCollectionDirect() {
		return investorCollectionDirect == null ? new BigDecimal(0)
				: investorCollectionDirect;
	}

	public void setInvestorCollectionDirect(BigDecimal investorCollectionDirect) {
		this.investorCollectionDirect = investorCollectionDirect;
	}

	public BigDecimal getBorrowerCollectionDirect() {
		return borrowerCollectionDirect == null ? new BigDecimal(0)
				: borrowerCollectionDirect;
	}

	public void setBorrowerCollectionDirect(BigDecimal borrowerCollectionDirect) {
		this.borrowerCollectionDirect = borrowerCollectionDirect;
	}

	public BigDecimal getAwardMoney() {
		return awardMoney;
	}

	public void setAwardMoney(BigDecimal awardMoney) {
		this.awardMoney = awardMoney;
	}

}
