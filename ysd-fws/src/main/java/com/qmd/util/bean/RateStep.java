package com.qmd.util.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class RateStep implements Serializable {

	private static final long serialVersionUID = 2257146772725200856L;
	
	// 序号
	private int seq;
	// 天利率
	private BigDecimal rateDay;
	// 月利率
	private BigDecimal rateMonty;
	// 年利率
	private BigDecimal rateYear;
	// 起购份数
	private int pieceSize;

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

	public int getPieceSize() {
		return pieceSize;
	}

	public void setPieceSize(int pieceSize) {
		this.pieceSize = pieceSize;
	}
}
