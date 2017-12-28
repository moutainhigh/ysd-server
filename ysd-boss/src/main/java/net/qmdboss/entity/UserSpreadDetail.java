package net.qmdboss.entity;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * 用户推广记录表
 */
@Entity
public class UserSpreadDetail extends BaseEntity  {

	private static final long serialVersionUID = 1L;
	
	private Integer tenderUserId;// 投标用户ID
	private BigDecimal tenderMoney;//投标金额
	private BigDecimal tgMoney;//推广金额
	private Integer statusCode;// 状态【1：一级推广；2：二级推广】
	private Integer tgUserId;// 推广用户ID
	private Integer borrowId;//标ID
	private String remark;// 备注
	

	

	public Integer getTenderUserId() {
		return tenderUserId;
	}

	public void setTenderUserId(Integer tenderUserId) {
		this.tenderUserId = tenderUserId;
	}

	public BigDecimal getTenderMoney() {
		return tenderMoney;
	}

	public void setTenderMoney(BigDecimal tenderMoney) {
		this.tenderMoney = tenderMoney;
	}

	public BigDecimal getTgMoney() {
		return tgMoney;
	}

	public void setTgMoney(BigDecimal tgMoney) {
		this.tgMoney = tgMoney;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getTgUserId() {
		return tgUserId;
	}

	public void setTgUserId(Integer tgUserId) {
		this.tgUserId = tgUserId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

}
