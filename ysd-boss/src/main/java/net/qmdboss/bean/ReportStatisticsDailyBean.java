package net.qmdboss.bean;

import java.math.BigDecimal;

public class ReportStatisticsDailyBean {
	private BigDecimal total;// 用户总金额（可用金额+冻结金额+待收金额）
	private BigDecimal ableMoney;// 可用金额，可提现金额
	private BigDecimal continueTotal;//
	private BigDecimal unableMoney;// 冻结金额
	private BigDecimal investorCollectionCapital;// 投资者待收本金
	private BigDecimal investorCollectionInterest;// 投资者待收利息
	private Integer dailyWorkDateInt;// 对象日期（创建日期的前一天 YYYYMMDD）

	public ReportStatisticsDailyBean() {
	}

	public ReportStatisticsDailyBean(BigDecimal total, BigDecimal ableMoney,
			BigDecimal continueTotal, BigDecimal unableMoney,
			BigDecimal investorCollectionCapital,
			BigDecimal investorCollectionInterest, Integer dailyWorkDateInt) {
		this.total = total;
		this.ableMoney = ableMoney;
		this.continueTotal = continueTotal;
		this.unableMoney = unableMoney;
		this.investorCollectionCapital = investorCollectionCapital;
		this.investorCollectionInterest = investorCollectionInterest;
		this.dailyWorkDateInt = dailyWorkDateInt;

	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getAbleMoney() {
		return ableMoney;
	}

	public void setAbleMoney(BigDecimal ableMoney) {
		this.ableMoney = ableMoney;
	}

	public BigDecimal getUnableMoney() {
		return unableMoney;
	}

	public void setUnableMoney(BigDecimal unableMoney) {
		this.unableMoney = unableMoney;
	}

	public BigDecimal getInvestorCollectionCapital() {
		return investorCollectionCapital;
	}

	public void setInvestorCollectionCapital(
			BigDecimal investorCollectionCapital) {
		this.investorCollectionCapital = investorCollectionCapital;
	}

	public BigDecimal getInvestorCollectionInterest() {
		return investorCollectionInterest;
	}

	public void setInvestorCollectionInterest(
			BigDecimal investorCollectionInterest) {
		this.investorCollectionInterest = investorCollectionInterest;
	}

	public Integer getDailyWorkDateInt() {
		return dailyWorkDateInt;
	}

	public void setDailyWorkDateInt(Integer dailyWorkDateInt) {
		this.dailyWorkDateInt = dailyWorkDateInt;
	}

	public BigDecimal getContinueTotal() {
		return continueTotal;
	}

	public void setContinueTotal(BigDecimal continueTotal) {
		this.continueTotal = continueTotal;
	}

}
