package com.qmd.util;

import com.qmd.mode.borrow.Borrow;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MonthRepayPlanDetail {

	public MonthRepayPlanDetail(Borrow borrow) {
		if ("6".equals(borrow.getType())) {
			initMoon(borrow);
		} else {
			init(borrow);
		}
	}

	private BigDecimal totalRepay;// 最终还款本息
	private BigDecimal sumCapital;// 还款本金总和
	private BigDecimal sumInterest;// 还款利息总和
	private Integer totalIssue;// 总共还款期数
	private BigDecimal monthDailyDec;// 月利率
	private double monthDaily;// 月利率

	private List<PledgeRepayPlanEach> pledgeRepayPlanEach;// 每期还款明细

	public void init(Borrow borrow) {

		totalRepay = new BigDecimal(0);
		sumCapital = new BigDecimal(0);
		sumInterest = new BigDecimal(0);

		String account = borrow.getAccount();
		monthDaily = borrow.getApr() / 1200;// 月化利率
		monthDailyDec = new BigDecimal(monthDaily);
		String plan = borrow.getBorStages();// 还款计划

		// 分解每期还款计划
		String str = plan.trim();
		if (str.endsWith(":")) {
			str = str.substring(0, str.length() - 1);
		}

		String[] strs = str.split(":");
		totalIssue = strs.length;// 取得还款期数

		// 待还款金额
		double amount = Double.parseDouble(account);
		// 已还款天数
		int lastDay = 0;
		PledgeRepayPlanEach each = null;
		pledgeRepayPlanEach = new ArrayList<PledgeRepayPlanEach>();
		for (int i = 0; i < strs.length; i++) {
			each = new PledgeRepayPlanEach();

			String temp = strs[i];
			String[] items = temp.split(","); // 借款天数,还款本金
			// 借款天数 = 本期天数-已还款天数
			int days = Integer.parseInt(items[0]) - lastDay;
			// 借款利息 = 借款金额 * 借款天数 * 日化利率
			double interest = amount * days * monthDaily;

			// 本期计算用的待还本金
			each.setCountCapital(CommonUtil.setPriceScale(new BigDecimal(amount),ConstantUtil.RoundType.roundHalfUp));
			// 本期计算用的天数
			each.setCountDaily(days);

			// 本期的借款天数作为下期的已还天数
			lastDay = Integer.parseInt(items[0]);
			// 下期的还款金额 = 待还金额- 本期还款金额
			amount = amount - Double.parseDouble(items[1]);

			// 期数（从1开始计数)// 所有期数，都从1开始计算
			each.setIssue(i + 1);
			// 还款天数
			each.setDaily(Integer.parseInt(items[0]));
			// 还款本金
			each.setIssueCapital(new BigDecimal(items[1]));
			// 还款利息
			each.setIssueInterest(CommonUtil.setPriceScale(new BigDecimal(interest),ConstantUtil.RoundType.roundHalfUp));
			// 还款本息
			each.setIssueTotal(CommonUtil.setPriceScale(each.getIssueCapital().add(each.getIssueInterest()),ConstantUtil.RoundType.roundHalfUp));
			// 还款本金百分比
			each.setRateCapital(CommonUtil.setPriceScale(new BigDecimal(Double.parseDouble(items[1])/Double.parseDouble(account)*100),ConstantUtil.RoundType.roundHalfUp));

			pledgeRepayPlanEach.add(each);

			totalRepay = totalRepay.add(each.getIssueCapital()).add(
					each.getIssueInterest());
			sumCapital = sumCapital.add(each.getIssueCapital());
			sumInterest = sumInterest.add(each.getIssueInterest());
		}

	}
	
	public void initMoon(Borrow borrow) {

		totalRepay = new BigDecimal(0);
		sumCapital = new BigDecimal(0);
		sumInterest = new BigDecimal(0);

		String account = borrow.getAccount();
		monthDaily = borrow.getApr() / 1200;// 月化利率
		monthDailyDec = new BigDecimal(monthDaily);
		
		//每月该还的利息
		BigDecimal interestMoney =CommonUtil.setPriceScale2BigDecimal(Double .parseDouble(account) * monthDaily);
		
		totalIssue = Integer.parseInt(borrow.getTimeLimit());// 取得还款期数

		// 待还款金额
		double amount = Double.parseDouble(account);
		// 已还款天数
		//int lastDay = 0;
		PledgeRepayPlanEach each = null;
		pledgeRepayPlanEach = new ArrayList<PledgeRepayPlanEach>();
		for (int i = 0; i < totalIssue; i++) {
			each = new PledgeRepayPlanEach();

			// 期数（从1开始计数)// 所有期数，都从1开始计算
			each.setIssue(i+1);
			// 还款天数
			each.setDaily(1);
			// 还款本金
			each.setIssueCapital(new BigDecimal("0.00"));
			// 还款利息
			each.setIssueInterest(interestMoney);
			// 还款本息
			each.setIssueTotal(interestMoney);
			// 还款本金百分比
			each.setRateCapital(BigDecimal.ZERO);

			pledgeRepayPlanEach.add(each);

			totalRepay = totalRepay.add(each.getIssueCapital()).add(
					each.getIssueInterest());
			sumCapital = sumCapital.add(each.getIssueCapital());
			sumInterest = sumInterest.add(each.getIssueInterest());
		}
		
		//----最后一期还本金
		each = new PledgeRepayPlanEach();
		// 期数（从1开始计数)// 所有期数，都从1开始计算
		each.setIssue(totalIssue + 1);
		// 还款天数
		each.setDaily(1);
		// 还款本金
		each.setIssueCapital(CommonUtil.setPriceScale2BigDecimal(amount));
		// 还款利息
		each.setIssueInterest(new BigDecimal("0.00"));
		// 还款本息
		each.setIssueTotal(each.getIssueCapital());
		// 还款本金百分比
		each.setRateCapital(new BigDecimal("100"));

		pledgeRepayPlanEach.add(each);

		totalRepay = totalRepay.add(each.getIssueCapital()).add(
				each.getIssueInterest());
		sumCapital = sumCapital.add(each.getIssueCapital());
		sumInterest = sumInterest.add(each.getIssueInterest());
		
		

	}

	/**
	 * 最终还款本息
	 * 
	 * @return
	 */
	public BigDecimal getTotalRepay() {
		return totalRepay;
	}

	/**
	 * 最终还款本息
	 * 
	 * @param totalRepay
	 */
	public void setTotalRepay(BigDecimal totalRepay) {
		this.totalRepay = totalRepay;
	}

	/**
	 * 还款本金总和
	 * 
	 * @return
	 */
	public BigDecimal getSumCapital() {
		return sumCapital;
	}

	/**
	 * 还款本金总和
	 * 
	 * @param sumCapital
	 */
	public void setSumCapital(BigDecimal sumCapital) {
		this.sumCapital = sumCapital;
	}

	/**
	 * 还款利息总和
	 * 
	 * @return
	 */
	public BigDecimal getSumInterest() {
		return sumInterest;
	}

	/**
	 * 还款利息总和
	 * 
	 * @param sumInterest
	 */
	public void setSumInterest(BigDecimal sumInterest) {
		this.sumInterest = sumInterest;
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
	 * 日利率
	 * 
	 * @return
	 */
	public BigDecimal getMonthDailyDec() {
		return monthDailyDec;
	}

	/**
	 * 日利率
	 * 
	 * @param aprDailyDec
	 */
	public void setMonthDailyDec(BigDecimal monthDailyDec) {
		this.monthDailyDec = monthDailyDec;
	}

	/**
	 * 日利率
	 * 
	 * @return
	 */
	public double getMonthDaily() {
		return monthDaily;
	}

	/**
	 * 日利率
	 * 
	 * @param aprDaily
	 */
	public void setMonthDaily(double monthDaily) {
		this.monthDaily = monthDaily;
	}

	/**
	 * 每期还款明细
	 * 
	 * @return
	 */
	public List<PledgeRepayPlanEach> getPledgeRepayPlanEach() {
		return pledgeRepayPlanEach;
	}

	/**
	 * 每期还款明细
	 * 
	 * @param pledgeRepayPlanEach
	 */
	public void setPledgeRepayPlanEach(
			List<PledgeRepayPlanEach> pledgeRepayPlanEach) {
		this.pledgeRepayPlanEach = pledgeRepayPlanEach;
	}

}
