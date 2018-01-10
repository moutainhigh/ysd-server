package com.qmd.util;

import com.qmd.mode.borrow.Borrow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WanderRepayPlanDetail {

	public WanderRepayPlanDetail(Borrow borrow) {
		init(borrow);
	}

	private Integer totalIssue;// 总共还款期数
	private Date lastDate;// 最后日期
	private boolean clickFlg;// 是否可以点击 当日日期 小于最后日期，页面可用

	private List<WanderRepayPlanEach> wanderRepayPlanEach;// 每期还款明细

	public void init(Borrow borrow) {

		String plan = borrow.getWanderStages();// 还款计划

		// 分解每期还款计划
		String str = plan.trim();
		if (str.endsWith(":")) {
			str = str.substring(0, str.length() - 1);
		}

		String[] strs = str.split(":");
		totalIssue = strs.length;// 取得还款期数

		double dayApr = borrow.getApr() / 1000;// 每元每天收益
		//

		// 已还款天数
		WanderRepayPlanEach each = null;
		wanderRepayPlanEach = new ArrayList<WanderRepayPlanEach>();

		Date today = new Date();
		for (int i = 0; i < strs.length; i++) {
			each = new WanderRepayPlanEach();

			String temp = strs[i];
			String[] items = temp.split(","); // 借款天数,还款本金

			each.setIssue(Integer.parseInt(items[0]));
			each.setRepayDateStr(items[1]);
			each.setRepayDate(CommonUtil.getString2Date(items[1], "yyyy-MM-dd"));
			each.setDaysNum(CommonUtil.getDateSubtractDay(new Date(),
					each.getRepayDate()).intValue());
			each.setIncomeEachPiece(each.getDaysNum() * dayApr);

			if (today.compareTo(each.getRepayDate()) < 0) {
				each.setClickFlg(true);
			}

			wanderRepayPlanEach.add(each);

			if (i == strs.length - 1) {
				lastDate = each.getRepayDate();
			}

		}

		if (today.compareTo(lastDate) < 0) {
			clickFlg = true;
		}

	}

	/**
	 * 总共还款期数
	 * 
	 * @return
	 */
	public Integer getTotalIssue() {
		return totalIssue;
	}

	/**
	 * 总共还款期数
	 * 
	 * @param totalIssue
	 */
	public void setTotalIssue(Integer totalIssue) {
		this.totalIssue = totalIssue;
	}

	/**
	 * 每期还款明细
	 * 
	 * @return
	 */
	public List<WanderRepayPlanEach> getWanderRepayPlanEach() {
		return wanderRepayPlanEach;
	}

	/**
	 * 每期还款明细
	 * 
	 * @param wanderRepayPlanEach
	 */
	public void setWanderRepayPlanEach(
			List<WanderRepayPlanEach> wanderRepayPlanEach) {
		this.wanderRepayPlanEach = wanderRepayPlanEach;
	}

	/**
	 * 最后一期日期
	 * 
	 * @return
	 */
	public Date getLastDate() {
		return lastDate;
	}

	/**
	 * 最后一期日期
	 * 
	 * @param lastDate
	 */
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	/**
	 * 当日是否可用
	 * 
	 * @return
	 */
	public boolean isClickFlg() {
		return clickFlg;
	}

	/**
	 * 当日是否可用
	 * 
	 * @param clickFlg
	 */
	public void setClickFlg(boolean clickFlg) {
		this.clickFlg = clickFlg;
	}

}
