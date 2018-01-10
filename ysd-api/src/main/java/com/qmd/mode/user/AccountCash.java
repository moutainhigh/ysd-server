package com.qmd.mode.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountCash implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7327462985256374737L;
	private int id ;
	private int userId;//用户ID
	private int status;//状态码【0-审核中；1-审核成功；2-审核失败；3-用户取消；4-处理中】
	private String account; //银行账号
	private String bank; //所属银行      
	private String branch;//支行
	private  BigDecimal total;//提现金额
	private BigDecimal credited;//到账总额
	private BigDecimal fee;//手续费
	private int verifyUserid;//审核ID
	private Date verifyTime; //审核时间
	private String verifyRemark;//审核备注
	private String addip;//添加人IP
	private Date modifyDate;//
	private Date createDate;//创建时间
	private String name;//对应查出的银行名称
	
	private BigDecimal ableCashMoney;//本次可提现金额
	private BigDecimal freeCashMoney;//本次免费提现金额

	private String tradeNo;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
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
	public int getVerifyUserid() {
		return verifyUserid;
	}
	public void setVerifyUserid(int verifyUserid) {
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
	public String getAddip() {
		return addip;
	}
	public void setAddip(String addip) {
		this.addip = addip;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
