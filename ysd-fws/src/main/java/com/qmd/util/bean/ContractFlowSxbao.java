package com.qmd.util.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同参数--流转--随心宝 积极型
 */
public class ContractFlowSxbao {

	private Integer scd;
	private Date begin;
	private Date end;
	private BigDecimal num;
	private BigDecimal num2;

	public Integer getScd() {
		return scd;
	}

	public void setScd(Integer scd) {
		this.scd = scd;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public BigDecimal getNum2() {
		return num2;
	}

	public void setNum2(BigDecimal num2) {
		this.num2 = num2;
	}

}
