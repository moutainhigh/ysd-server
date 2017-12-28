package com.qmd.util;

import java.text.DecimalFormat;

public class PledgeCalculateUtil {

	/**
	 * 借款人的还款计划
	 * 
	 * @param account
	 *            借款总金额
	 * @param apr
	 *            年化利率
	 * @param plan
	 *            还款计划（天数1,还款金额1;天数2,还款金额2;）
	 */
	public static PledgeCalclateBorrowDetail[] calculageForBorrow(
			String account, double apr, String plan) {

		String str = plan.trim();
		if (str.endsWith(":")) {
			str = str.substring(0, str.length() - 1);
		}

		String[] strs = str.split(":");

		PledgeCalclateBorrowDetail[] ret = new PledgeCalclateBorrowDetail[strs.length];

		System.out.println("还款总期数：" + (strs.length));
		// 日化利率
		double aprDay = apr / 365 / 100;
		// 待还款金额
		double amount = Double.parseDouble(account);
		// 已还款天数
		int lastDay = 0;

		for (int i = 0; i < strs.length; i++) {
			PledgeCalclateBorrowDetail detail = new PledgeCalclateBorrowDetail();
			String temp = strs[i];
			String[] items = temp.split(","); // 借款天数,还款本金
			// 借款天数 = 本期天数-已还款天数
			int days = Integer.parseInt(items[0]) - lastDay;
			// 借款利息 = 借款金额 * 借款天数 * 日化利率
			double interest = amount * days * aprDay;

			// 本期借款的本金
			detail.setThisCapital(amount);
			// 本期借款的天数
			detail.setThisDays(days);

			// 本期的借款天数作为下期的已还天数
			lastDay = Integer.parseInt(items[0]);
			// 下期的还款金额 = 待还金额- 本期还款金额
			amount = amount - Double.parseDouble(items[1]);

			// 期数（从0开始计数)
			detail.setIssue(i);
			// 还款天数
			detail.setDays(Integer.parseInt(items[0]));
			// 还款本金
			detail.setRepayAccount(Double.parseDouble(items[1]));
			// 还款利息
			detail.setInterest(interest);
			// 待还本金
			detail.setNorepayAccount(amount);
			ret[i] = detail;
		}

		return ret;

	}

	/**
	 * 投资人的还款计划
	 * 
	 * @param account
	 *            标的借款金额
	 * @param investAmount
	 *            投资金额
	 * @param apr
	 *            年化利率
	 * @param plan
	 *            还款计划
	 */
	public static PledgeCalclateInvestorDetail[] calculageForInvestor(
			double account, double investAmount, double apr,
			PledgeCalclateBorrowDetail[] plan) {

		// 日化利率
		double aprDay = apr / 365 / 100;

		double calcu = investAmount;
		PledgeCalclateInvestorDetail[] ret = new PledgeCalclateInvestorDetail[plan.length];

		for (int i = 0; i < plan.length; i++) {

			PledgeCalclateInvestorDetail bean = new PledgeCalclateInvestorDetail();

			// 本期投资金额
			bean.setInvestAmount(calcu);
			// 本期还款利息 =本期投资金额*日化利率*本期天数
			bean.setInvestInterest(calcu * aprDay * plan[i].getThisDays());
			// 本期还款本金 = 总投资额/标的总借款金额 * 本期标的还款金额
			bean.setInvestCapital(investAmount / account
					* plan[i].getRepayAccount());
			// 本期还款后的待还本金 = 本期投资金额 - 本期的还款本金
			calcu = calcu - bean.getInvestCapital();
			// 本期还款后的待还本金
			bean.setNopayInvest(calcu);

			// ====以下为每期标的还款信息
			// 期数（从0开始计数)
			bean.setIssue(plan[i].getIssue());
			// 还款天数
			bean.setDays(plan[i].getDays());
			// 本期还款的利息
			bean.setInterest(plan[i].getInterest());
			// 本期借款的天数
			bean.setThisDays(plan[i].getThisDays());
			// 本期借款的本金
			bean.setThisCapital(plan[i].getThisCapital());
			// 本期还款本金
			bean.setRepayAccount(plan[i].getRepayAccount());
			// 本期还款后的待还本金
			bean.setNorepayAccount(plan[i].getNorepayAccount());

			ret[i] = bean;
		}

		return ret;

	}
	
	public static String pledgeFormat(double num) {
		DecimalFormat df = new DecimalFormat("#.00");
		String ret = df.format(num);
		return ret;
	}

	public static void main(String[] args) {
		String aa = "10,1000:30,2000:";
		PledgeCalclateBorrowDetail[] ret = calculageForBorrow("3000", 10, aa);

		String temp = "";
		for (PledgeCalclateBorrowDetail detail : ret) {
			temp = " 期数=";
			temp += (detail.getIssue()+1);
			temp += " 还款天数=";
			temp += detail.getDays();
			temp += " 还款本金=";
			temp += pledgeFormat(detail.getRepayAccount());
			temp += "#"+detail.getRepayAccount();
			temp += " 还款利息=";
			temp += pledgeFormat(detail.getInterest());
			temp += "#"+detail.getInterest();
			temp += " 待还本金=";
			temp += pledgeFormat(detail.getNorepayAccount());
			temp += "#"+detail.getNorepayAccount();

			System.out.println(temp);
		}
		System.out.println("--------------------------------");
		PledgeCalclateInvestorDetail[] det = calculageForInvestor(3000, 1000,
				10, ret);

		for (PledgeCalclateInvestorDetail detail : det) {
			temp = " 期数=";
			temp += (detail.getIssue()+1);

			// 本期还款本金 = 总投资额/标的总借款金额 * 本期标的还款金额
			temp += " 还款本金" + pledgeFormat(detail.getInvestCapital());
			temp += " 还款本金" + "#"+detail.getInvestCapital();
			// 本期还款天数
			temp += " 本期天数" + detail.getThisDays();
			// 本期还款利息 =本期投资金额*日化利率*本期天数
			temp += " 还款利息" + pledgeFormat(detail.getInvestInterest());
			temp += " 还款利息" + "#"+detail.getInvestInterest();
			// 本期还款后的待还本金
			temp += " 待还" + pledgeFormat(detail.getNopayInvest());
			temp += " 待还" + "#"+detail.getNopayInvest();

			temp += " 标的还款天数=";
			temp += detail.getDays();
			temp += " 标的还款本金=";
			temp += pledgeFormat(detail.getRepayAccount());
			temp += "#"+detail.getRepayAccount();
			temp += " 标的还款利息=";
			temp += pledgeFormat(detail.getInterest());
			temp += "#"+detail.getInterest();
			temp += " 标的待还本金=";
			temp += pledgeFormat(detail.getNorepayAccount());
			temp += "#"+detail.getNorepayAccount();

			System.out.println(temp);
		}
	}
}
