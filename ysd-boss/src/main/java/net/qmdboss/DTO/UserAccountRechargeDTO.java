package net.qmdboss.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class UserAccountRechargeDTO {
	
	private Integer id;
	private String username;
	private String realName;
	private Date createDate;
	private Date rechargeDate;
	private String tradeNo;
	private String type;
	private int rechargeInterfaceId;
	private BigDecimal fee;//费用
	private BigDecimal reward;
	private int status;
	private BigDecimal money;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getRechargeDate() {
		return rechargeDate;
	}
	public void setRechargeDate(Date rechargeDate) {
		this.rechargeDate = rechargeDate;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getRechargeInterfaceId() {
		return rechargeInterfaceId;
	}
	public void setRechargeInterfaceId(int rechargeInterfaceId) {
		this.rechargeInterfaceId = rechargeInterfaceId;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public BigDecimal getReward() {
		return reward;
	}
	public void setReward(BigDecimal reward) {
		this.reward = reward;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	
	

}
