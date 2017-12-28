package net.qmdboss.entity;


import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class AccountCash  extends BaseEntity {


	private static final long serialVersionUID = 1L;
	
//	private int id ;
//	private int UserId;//用户ID
	private Integer status;//状态码【0-待审核，1-审核通过，2-审核不通过，3-未知待查】
	private String account; //银行账号
	private String bank; //所属银行      
	private String branch;//支行
	private  BigDecimal total;//取现金额
	private BigDecimal credited;//到账总额
	private BigDecimal fee;//手续费
	private Integer verifyUserid;//审核ID
	private Date verifyTime; //审核时间
	private String verifyRemark;//审核备注
	private BigDecimal changeNum;//调整的金额
	private String changeRemark;//调整是备注
	private Date addtime ;//添加时间
	private String addip;//添加人IP
	private BigDecimal ableCashMoney;//提现时可提现金额
	private BigDecimal freeCashMoney;//提现时可免费提现总额

	private User user;//发表人人信息
	private String tradeNo;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_userAccount_user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getCredited() {
		return credited;
	}

	public void setCredited(BigDecimal credited) {
		this.credited = credited;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Integer getVerifyUserid() {
		return verifyUserid;
	}

	public void setVerifyUserid(Integer verifyUserid) {
		this.verifyUserid = verifyUserid;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getVerifyRemark() {
		return verifyRemark;
	}

	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddip() {
		return addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public BigDecimal getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(BigDecimal changeNum) {
		this.changeNum = changeNum;
	}

	public String getChangeRemark() {
		return changeRemark;
	}

	public void setChangeRemark(String changeRemark) {
		this.changeRemark = changeRemark;
	}

	public BigDecimal getAbleCashMoney() {
		return ableCashMoney;
	}

	public void setAbleCashMoney(BigDecimal ableCashMoney) {
		this.ableCashMoney = ableCashMoney;
	}

	public BigDecimal getFreeCashMoney() {
		return freeCashMoney;
	}

	public void setFreeCashMoney(BigDecimal freeCashMoney) {
		this.freeCashMoney = freeCashMoney;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
}
