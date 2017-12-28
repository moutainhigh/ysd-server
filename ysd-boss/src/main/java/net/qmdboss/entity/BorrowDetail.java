package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 投标信息属性类
 * @author zhanf
 *
 */
@Entity
public class BorrowDetail extends BaseEntity  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;//投标状态
	private BigDecimal money;//预计投标金额
	private BigDecimal account;//实际成功金额
	private BigDecimal repaymentAccount;//应还款金额（借款金额+利息）
	private BigDecimal interest;//借款利息
	private BigDecimal repaymentYesaccount;//已还款金额
	private BigDecimal waitAccount;//待还款金额
	private BigDecimal repaymentYesinterest;//已还利息
	private BigDecimal waitInterest;//待还利息
	private String addPersion;//添加人（投标用户ID）
	private Date createTime;//添加时间
	private String operatorIp;//操作IP
	private String ableAmount; //可用金额
    private String continueAmount; //续投金额
	private String hongbaoAmount;//红包金额
	private String tasteAmount;//体验资金
    private Integer backStatus;//回滚状态【0-表示正常，1表示撤销回滚资金】
    
    private Integer autoTenderStatus; //

	
	private Borrow borrow;//标字段
	private User user;//发标人信息
	
	private BigDecimal reward;//流转标奖励
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_borDetail_user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_bordetail_borrow")
	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	
	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
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

	public Integer getAutoTenderStatus() {
		return autoTenderStatus;
	}

	public void setAutoTenderStatus(Integer autoTenderStatus) {
		this.autoTenderStatus = autoTenderStatus;
	}

	public BigDecimal getReward() {
		return reward;
	}

	public void setReward(BigDecimal reward) {
		this.reward = reward;
	}

	public String getHongbaoAmount() {
		return hongbaoAmount;
	}

	public void setHongbaoAmount(String hongbaoAmount) {
		this.hongbaoAmount = hongbaoAmount;
	}

	public String getTasteAmount() {
		return tasteAmount;
	}

	public void setTasteAmount(String tasteAmount) {
		this.tasteAmount = tasteAmount;
	}
	
	

}
