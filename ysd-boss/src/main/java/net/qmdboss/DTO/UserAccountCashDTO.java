package net.qmdboss.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class UserAccountCashDTO {

	private Integer id;
	private String username;
	private String realName;
	private Integer typeId;//用户类型
	
	private String account;//提现账号
	private String bankName;//银行名字
	private String branch;//支行
	private BigDecimal total;//提现总额
	private BigDecimal credited;//到账金额
	private BigDecimal fee;//手续费
	private BigDecimal changeNum;//调整金额
	private Date createDate;//提现时间
	private Integer status;//status
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	public BigDecimal getChangeNum() {
		return changeNum;
	}
	public void setChangeNum(BigDecimal changeNum) {
		this.changeNum = changeNum;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	
}
