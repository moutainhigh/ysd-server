package com.qmd.bean.center;

import com.qmd.bean.BaseBean;

import java.math.BigDecimal;

/**
 * 自动投资
 *
 */
public class UserAuto extends BaseBean{


	private static final long serialVersionUID = 1362725099725030873L;


	public UserAuto() {
		setRcd("R0001");
	}
	
	
	private Integer autoTenderStatus;// 自投状态1启用0关闭
	private String ableMoney;//可用金额
	private String continueTotal; //续投金额
	private Integer autoTenderRule;// 自投规则0全投，1设置
	private BigDecimal autoTenderMoneyTop;// 自投上限金额
//	private BigDecimal autoTenderMoneyLeave;// 自投保留金额
//
//	private BigDecimal autoTenderRateBegin;// 自投利率开始
//	private BigDecimal autoTenderRateEnd;// 自投利率结束
//	private Integer autoTenderLimitBegin;// 自投期限开始
//	private Integer autoTenderLimitEnd;// 自投期限结束
//	
//	private BigDecimal autoTenderRewardBegin;//奖励开始
//	private BigDecimal autoTenderRewardEnd;//奖励结束
	private  String autoTenderBorrowType;//自动投标类型
	
//	private Integer	tenderAutoWayOne;// 1 按月付息，到期还本 (含一月标)
//	private Integer	tenderAutoWayTwo;//2按月分期还本息
//	private Integer	tenderAutoWayThree;// 3到期还本息
	
	
	public Integer getAutoTenderStatus() {
		return autoTenderStatus;
	}
	public void setAutoTenderStatus(Integer autoTenderStatus) {
		this.autoTenderStatus = autoTenderStatus;
	}
	public String getAbleMoney() {
		return ableMoney;
	}
	public void setAbleMoney(String ableMoney) {
		this.ableMoney = ableMoney;
	}
	public String getContinueTotal() {
		return continueTotal;
	}
	public void setContinueTotal(String continueTotal) {
		this.continueTotal = continueTotal;
	}
	public Integer getAutoTenderRule() {
		return autoTenderRule;
	}
	public void setAutoTenderRule(Integer autoTenderRule) {
		this.autoTenderRule = autoTenderRule;
	}
	public BigDecimal getAutoTenderMoneyTop() {
		return autoTenderMoneyTop;
	}
	public void setAutoTenderMoneyTop(BigDecimal autoTenderMoneyTop) {
		this.autoTenderMoneyTop = autoTenderMoneyTop;
	}
//	public BigDecimal getAutoTenderMoneyLeave() {
//		return autoTenderMoneyLeave;
//	}
//	public void setAutoTenderMoneyLeave(BigDecimal autoTenderMoneyLeave) {
//		this.autoTenderMoneyLeave = autoTenderMoneyLeave;
//	}
//	public BigDecimal getAutoTenderRateBegin() {
//		return autoTenderRateBegin;
//	}
//	public void setAutoTenderRateBegin(BigDecimal autoTenderRateBegin) {
//		this.autoTenderRateBegin = autoTenderRateBegin;
//	}
//	public BigDecimal getAutoTenderRateEnd() {
//		return autoTenderRateEnd;
//	}
//	public void setAutoTenderRateEnd(BigDecimal autoTenderRateEnd) {
//		this.autoTenderRateEnd = autoTenderRateEnd;
//	}
//	public Integer getAutoTenderLimitBegin() {
//		return autoTenderLimitBegin;
//	}
//	public void setAutoTenderLimitBegin(Integer autoTenderLimitBegin) {
//		this.autoTenderLimitBegin = autoTenderLimitBegin;
//	}
//	public Integer getAutoTenderLimitEnd() {
//		return autoTenderLimitEnd;
//	}
//	public void setAutoTenderLimitEnd(Integer autoTenderLimitEnd) {
//		this.autoTenderLimitEnd = autoTenderLimitEnd;
//	}
//	public BigDecimal getAutoTenderRewardBegin() {
//		return autoTenderRewardBegin;
//	}
//	public void setAutoTenderRewardBegin(BigDecimal autoTenderRewardBegin) {
//		this.autoTenderRewardBegin = autoTenderRewardBegin;
//	}
//	public BigDecimal getAutoTenderRewardEnd() {
//		return autoTenderRewardEnd;
//	}
//	public void setAutoTenderRewardEnd(BigDecimal autoTenderRewardEnd) {
//		this.autoTenderRewardEnd = autoTenderRewardEnd;
//	}
//	public Integer getTenderAutoWayOne() {
//		return tenderAutoWayOne;
//	}
//	public void setTenderAutoWayOne(Integer tenderAutoWayOne) {
//		this.tenderAutoWayOne = tenderAutoWayOne;
//	}
//	public Integer getTenderAutoWayTwo() {
//		return tenderAutoWayTwo;
//	}
//	public void setTenderAutoWayTwo(Integer tenderAutoWayTwo) {
//		this.tenderAutoWayTwo = tenderAutoWayTwo;
//	}
//	public Integer getTenderAutoWayThree() {
//		return tenderAutoWayThree;
//	}
//	public void setTenderAutoWayThree(Integer tenderAutoWayThree) {
//		this.tenderAutoWayThree = tenderAutoWayThree;
//	}
	public String getAutoTenderBorrowType() {
		return autoTenderBorrowType;
	}
	public void setAutoTenderBorrowType(String autoTenderBorrowType) {
		this.autoTenderBorrowType = autoTenderBorrowType;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
