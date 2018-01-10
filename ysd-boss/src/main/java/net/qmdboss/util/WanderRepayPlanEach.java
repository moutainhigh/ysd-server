package net.qmdboss.util;

import java.util.Date;

public class WanderRepayPlanEach {

	private Integer issue;// 第几期
	private Date repayDate;// 还款日期
	private String repayDateStr;// 还款日期

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

}
