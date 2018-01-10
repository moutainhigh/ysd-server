package com.qmd.util.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class RateStepAgile implements Serializable {

	private static final long serialVersionUID = 210572252910133663L;

	// 序号
	private int seq;
	// 天利率
	private BigDecimal rateDay;
	// 月利率
	private BigDecimal rateMonty;
	// 年利率
	private BigDecimal rateYear;
	// 天数开始
	private int daysBegin;
	// 天数结束
	private int daysEnd;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public BigDecimal getRateDay() {
		return rateDay;
	}

	public void setRateDay(BigDecimal rateDay) {
		this.rateDay = rateDay;
	}

	public BigDecimal getRateMonty() {
		return rateMonty;
	}

	public void setRateMonty(BigDecimal rateMonty) {
		this.rateMonty = rateMonty;
	}

	public BigDecimal getRateYear() {
		return rateYear;
	}

	public void setRateYear(BigDecimal rateYear) {
		this.rateYear = rateYear;
	}

	public int getDaysBegin() {
		return daysBegin;
	}

	public void setDaysBegin(int daysBegin) {
		this.daysBegin = daysBegin;
	}

	public int getDaysEnd() {
		return daysEnd;
	}

	public void setDaysEnd(int daysEnd) {
		this.daysEnd = daysEnd;
	}

}
