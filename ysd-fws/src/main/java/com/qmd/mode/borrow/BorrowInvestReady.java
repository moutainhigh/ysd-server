package com.qmd.mode.borrow;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Borrow 表属性类
 * 
 * @author Administrator
 * 
 */
public class BorrowInvestReady implements Serializable {

	private static final long serialVersionUID = 2205436941772147110L;

	private Integer id;// 系统ID
	private Integer userId;// 用户ID
	private Integer borrowId;// 标ID
	private BigDecimal investMoney;// 投标金额
	private BigDecimal continueMoney;// 续单金额
	private Integer investPiece;// 投标份数
	private Integer continuePiece;// 续单份数
	private Integer wanderPlan;// 还款期数
	private BigDecimal pieceMoney;
	private Integer statusCode;// 状态
	private String remark;// 备注
	private Date createDate;// 创建时间
	private Date modifyDate;// 修改时间

	private BigDecimal tasteMoney;// 体验金额
	private BigDecimal directMoney;// 直投金额
	private Integer tastePiece;// 体验份数
	private Integer directPiece;// 直投份数
	private Integer autoRate;//部分投标 1支持部分投标，2不支持，直接跳回

	// ------非表字段-----------------------------
	private BigDecimal money;

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

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public BigDecimal getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(BigDecimal investMoney) {
		this.investMoney = investMoney;
	}

	public BigDecimal getContinueMoney() {
		return continueMoney;
	}

	public void setContinueMoney(BigDecimal continueMoney) {
		this.continueMoney = continueMoney;
	}

	public Integer getWanderPlan() {
		return wanderPlan;
	}

	public void setWanderPlan(Integer wanderPlan) {
		this.wanderPlan = wanderPlan;
	}

	public BigDecimal getPieceMoney() {
		return pieceMoney;
	}

	public void setPieceMoney(BigDecimal pieceMoney) {
		this.pieceMoney = pieceMoney;
	}

	public Integer getInvestPiece() {
		return investPiece;
	}

	public void setInvestPiece(Integer investPiece) {
		this.investPiece = investPiece;
	}

	public Integer getContinuePiece() {
		return continuePiece;
	}

	public void setContinuePiece(Integer continuePiece) {
		this.continuePiece = continuePiece;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getTasteMoney() {
		return tasteMoney;
	}

	public void setTasteMoney(BigDecimal tasteMoney) {
		this.tasteMoney = tasteMoney;
	}

	public BigDecimal getDirectMoney() {
		return directMoney;
	}

	public void setDirectMoney(BigDecimal directMoney) {
		this.directMoney = directMoney;
	}

	public Integer getTastePiece() {
		return tastePiece;
	}

	public void setTastePiece(Integer tastePiece) {
		this.tastePiece = tastePiece;
	}

	public Integer getDirectPiece() {
		return directPiece;
	}

	public void setDirectPiece(Integer directPiece) {
		this.directPiece = directPiece;
	}

	public Integer getAutoRate() {
		return autoRate;
	}

	public void setAutoRate(Integer autoRate) {
		this.autoRate = autoRate;
	}

}
