package com.qmd.util;

import java.util.Date;

public class WanderRepayPlanEach {

	private Integer issue;// 第几期
	private Date repayDate;// 还款日期
	private String repayDateStr;// 还款日期
	private int daysNum;// 借款的天数
	private double incomeEachPiece;// 每一元投资的收益
	private boolean clickFlg;	// 可选标记 当日(包含当日)之前的日期，不可选择投资

	/**
	 * 第几期
	 * 
	 * @return
	 */
	public Integer getIssue() {
		return issue;
	}

	/**
	 * 第几期
	 * 
	 * @param issue
	 */
	public void setIssue(Integer issue) {
		this.issue = issue;
	}

	/**
	 * 还款日期
	 * 
	 * @return
	 */
	public Date getRepayDate() {
		return repayDate;
	}

	/**
	 * 还款日期
	 * 
	 * @param repayDate
	 */
	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	/**
	 * 还款日期
	 * 
	 * @return
	 */
	public String getRepayDateStr() {
		return repayDateStr;
	}

	/**
	 * 还款日期
	 * 
	 * @param repayDateStr
	 */
	public void setRepayDateStr(String repayDateStr) {
		this.repayDateStr = repayDateStr;
	}

	/**
	 * 借款的天数
	 * 
	 * @return
	 */
	public int getDaysNum() {
		return daysNum;
	}

	/**
	 * 借款的天数
	 * 
	 * @param daysNum
	 */
	public void setDaysNum(int daysNum) {
		this.daysNum = daysNum;
	}

	/**
	 * 每一元投资的收益
	 * @return
	 */
	public double getIncomeEachPiece() {
		return incomeEachPiece;
	}

	/**
	 * 每一元投资的收益
	 * 
	 * @param incomeEachPiece
	 */
	public void setIncomeEachPiece(double incomeEachPiece) {
		this.incomeEachPiece = incomeEachPiece;
	}

	public boolean isClickFlg() {
		return clickFlg;
	}

	public void setClickFlg(boolean clickFlg) {
		this.clickFlg = clickFlg;
	}

}
