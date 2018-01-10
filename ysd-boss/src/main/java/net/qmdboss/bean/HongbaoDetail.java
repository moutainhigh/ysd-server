package net.qmdboss.bean;

import java.math.BigDecimal;

public class HongbaoDetail {

	private BigDecimal money;// 红包金额
	private Integer expDate;// 使用期限
	private Integer limitStart;// 项目期限起始
	private Integer limitEnd;// 项目期限结束
	private Integer isPc;// PC【0：不可用，1：可用】
	private Integer isApp;// APP【0：不可用，1：可用】
	private Integer isHfive;// H5【0：不可用，1：可用】
	//@author:zdl 新增字段
	private Integer investFullMomey;//投资金额满多少可用
	private Integer investmentStart;//投资金额区间下限
	private Integer investmentEnd;//投资金额区间上线
	
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}

	public Integer getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(Integer limitEnd) {
		this.limitEnd = limitEnd;
	}

	public Integer getIsPc() {
		return isPc;
	}

	public void setIsPc(Integer isPc) {
		this.isPc = isPc;
	}

	public Integer getIsApp() {
		return isApp;
	}

	public void setIsApp(Integer isApp) {
		this.isApp = isApp;
	}

	public Integer getIsHfive() {
		return isHfive;
	}

	public void setIsHfive(Integer isHfive) {
		this.isHfive = isHfive;
	}

	public Integer getExpDate() {
		return expDate;
	}

	public void setExpDate(Integer expDate) {
		this.expDate = expDate;
	}

	public Integer getInvestFullMomey() {
		return investFullMomey;
	}

	public void setInvestFullMomey(Integer investFullMomey) {
		this.investFullMomey = investFullMomey;
	}

	public Integer getInvestmentStart() {
		return investmentStart;
	}

	public void setInvestmentStart(Integer investmentStart) {
		this.investmentStart = investmentStart;
	}

	public Integer getInvestmentEnd() {
		return investmentEnd;
	}

	public void setInvestmentEnd(Integer investmentEnd) {
		this.investmentEnd = investmentEnd;
	}

}
