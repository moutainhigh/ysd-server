package com.qmd.mode.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金流水记录表
 * @author Xsf
 *
 */

public class UserAccountDetail implements Serializable{
	  
	private static final long serialVersionUID = 2511619748298279437L;
	  
	  private Integer id;
	  private Date createDate;
	  private Date modifyDate;
	  private Integer userId; //用户ID 
	  private String type;//用户资金类别【recharge:用户充值 ;fee:充值手续费;vip:vip会员费】
	  private BigDecimal total;//总金额
	  private BigDecimal money;//操作金额
	  private BigDecimal useMoney;//可用金额
	  private BigDecimal noUseMoney;//冻结金额
	  private BigDecimal collection;//当前待收金额
	  private Integer toUser;//交易方
	  private String operatorer;//操作人
	  private String remark;//明细描述
	  private Date addTime;//添加时间
	  private String operatorIp;//操作IP
	  private String typeName;//资金类别上级名称
	  private String acctype;//资金类型（0-支出；1-收入)
	  
	  private BigDecimal investorCollectionCapital;//投资者待收本金
	  private BigDecimal investorCollectionInterest;//投资者待收利息
	  private BigDecimal borrowerCollectionCapital;//借款人待付本金
	  private BigDecimal borrowerCollectionInterest;//借款人待付利息
	  
	  private BigDecimal continueTotal;// 续投金额
	  private BigDecimal tasteMoney;//体验金账户
	  
	  private BigDecimal awardMoney;//红包账户
	  private Integer borrowId;//标id
	  
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getTotal() {
		return total ==null ? new BigDecimal(0):total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getMoney() {
		return money ==null ? new BigDecimal(0):money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getUseMoney() {
		return useMoney ==null ? new BigDecimal(0):useMoney;
	}
	public void setUseMoney(BigDecimal useMoney) {
		this.useMoney = useMoney;
	}
	public BigDecimal getNoUseMoney() {
		return noUseMoney ==null ? new BigDecimal(0):noUseMoney;
	}
	public void setNoUseMoney(BigDecimal noUseMoney) {
		this.noUseMoney = noUseMoney;
	}
	public BigDecimal getCollection() {
		return collection ==null ? new BigDecimal(0):collection;
	}
	public void setCollection(BigDecimal collection) {
		this.collection = collection;
	}
	public Integer getToUser() {
		return toUser;
	}
	public void setToUser(Integer toUser) {
		this.toUser = toUser;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getOperatorIp() {
		return operatorIp;
	}
	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
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
	public String getOperatorer() {
		return operatorer;
	}
	public void setOperatorer(String operatorer) {
		this.operatorer = operatorer;
	}
	
	/**
	 * 投资者待收本金
	 * @return
	 */
	public BigDecimal getInvestorCollectionCapital() {
		return investorCollectionCapital;
	}
	/**
	 * 投资者待收本金
	 * @param investorCollectionCapital
	 */
	public void setInvestorCollectionCapital(BigDecimal investorCollectionCapital) {
		this.investorCollectionCapital = investorCollectionCapital;
	}
	/**
	 * 投资者待收利息
	 * @return
	 */
	public BigDecimal getInvestorCollectionInterest() {
		return investorCollectionInterest;
	}
	/**
	 * 投资者待收利息
	 * @param investorCollectionInterest
	 */
	public void setInvestorCollectionInterest(BigDecimal investorCollectionInterest) {
		this.investorCollectionInterest = investorCollectionInterest;
	}
	/**
	 * 借款人待付本金
	 * @return
	 */
	public BigDecimal getBorrowerCollectionCapital() {
		return borrowerCollectionCapital;
	}
	/**
	 * 借款人待付本金
	 * @param borrowerCollectionCapital
	 */
	public void setBorrowerCollectionCapital(BigDecimal borrowerCollectionCapital) {
		this.borrowerCollectionCapital = borrowerCollectionCapital;
	}
	/**
	 * 借款人待付利息
	 * @return
	 */
	public BigDecimal getBorrowerCollectionInterest() {
		return borrowerCollectionInterest;
	}
	/**
	 * 借款人待付利息
	 * @param borrowerCollectionInterest
	 */
	public void setBorrowerCollectionInterest(BigDecimal borrowerCollectionInterest) {
		this.borrowerCollectionInterest = borrowerCollectionInterest;
	}
	/**
	 * 续投金额
	 * @return
	 */
	public BigDecimal getContinueTotal() {
		return continueTotal ==null ? BigDecimal.valueOf(0):continueTotal;
	}
	/**
	 * 续投金额
	 * @param continueTotal
	 */
	public void setContinueTotal(BigDecimal continueTotal) {
		this.continueTotal = continueTotal;
	}
	public BigDecimal getAwardMoney() {
		return awardMoney;
	}
	public void setAwardMoney(BigDecimal awardMoney) {
		this.awardMoney = awardMoney;
	}
	public Integer getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getAcctype() {
		return acctype;
	}
	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}
	public BigDecimal getTasteMoney() {
		return tasteMoney;
	}
	public void setTasteMoney(BigDecimal tasteMoney) {
		this.tasteMoney = tasteMoney;
	}
	
}
