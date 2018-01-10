package net.qmdboss.util;

import net.qmdboss.bean.RepaymentDetail;
import net.qmdboss.bean.RepaymentInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工具类 - 公用
 */

public class PromoteUtil {

	/**
	 * 
	 * @param borrowIsday
	 *            0月标，1天标
	 * @param borrowStyle
	 *            还款方式
	 * @param borrowTimeLimit
	 *            借款总时长
	 * @param borrowDivides
	 *            分期数量
	 * @param borrowAccount
	 *            借款金额
	 * @param borrowApr
	 *            利率
	 * @return
	 */
	public static RepaymentInfo promotePlan(int borrowIsday, int borrowStyle,
			int borrowTimeLimit, int borrowDivides, BigDecimal borrowAccount,
			BigDecimal borrowApr) {
		
		if (borrowIsday == 0) {
			borrowDivides = borrowTimeLimit;
		}
		
		Date thisDate=new Date();

		RepaymentInfo repaymentInfo = new RepaymentInfo();

		List<RepaymentDetail> repaymentDetailList = new ArrayList<RepaymentDetail>();

		int repaymentPeriod = borrowTimeLimit / borrowDivides;

		BigDecimal repaymentAccount = 
				NumberUtil.setPriceScale(borrowAccount.doubleValue()
						/ borrowDivides);
		BigDecimal repaymentInterest = BigDecimal.ZERO;

		BigDecimal periodApr = BigDecimal.ZERO;
		if (borrowIsday == 0) {
			periodApr = BigDecimal.valueOf(borrowApr.doubleValue()/1200);
		} else {
			periodApr = BigDecimal.valueOf(borrowApr.doubleValue()/1000);
		}

		BigDecimal periodAccount = borrowAccount;

		BigDecimal totalRepaymentInterest = BigDecimal.ZERO;

		for (int i = 0; i < borrowDivides; i++) {

			RepaymentDetail bean = new RepaymentDetail();
			bean.setOrderNum(i + 1);
			if (borrowIsday == 0) {
				//periodApr = borrowApr.divide(BigDecimal.valueOf(1200));
				bean.setRepaymentDate(CommonUtil.getMonthAfter(thisDate,(i+1)));
			} else {
				bean.setRepaymentDate(CommonUtil.getDateAfter(thisDate,(i+1)*repaymentPeriod));
			}
			bean.setRepaymentDateInt(CommonUtil.getIntDate(bean.getRepaymentDate()));

			repaymentInterest = periodApr.multiply(
					BigDecimal.valueOf(repaymentPeriod))
					.multiply(periodAccount);
			
			repaymentInterest = NumberUtil.setPriceScale(repaymentInterest);
			
			bean.setInterest(repaymentInterest);

			bean.setRepaymentNum(bean.getOrderNum() * repaymentPeriod);

			if (borrowStyle == 3) {
				if (i != borrowDivides - 1) {
					bean.setCapital(repaymentAccount);// 还本金
					bean.setAccount(repaymentAccount.add(repaymentInterest));// 还本金+利息
					periodAccount = periodAccount.subtract(repaymentAccount);
				} else {
					bean.setCapital(periodAccount);// 还本金
					bean.setAccount(periodAccount.add(repaymentInterest));// 还本金+利息
				}
			} else if (borrowStyle == 1) {
				if (i != borrowDivides - 1) {
					bean.setCapital(BigDecimal.ZERO);// 还本金
					bean.setAccount(repaymentInterest);// 还本金+利息
				} else {
					bean.setCapital(periodAccount);// 还本金
					bean.setAccount(periodAccount.add(repaymentInterest));// 还本金+利息
				}
			} else if (borrowStyle == 2) {
				if (i != borrowDivides - 1) {
					bean.setCapital(BigDecimal.ZERO);// 还本金
					bean.setAccount(BigDecimal.ZERO);// 还本金+利息
					bean.setInterest(BigDecimal.ZERO);
				} else {
					bean.setCapital(periodAccount);// 还本金
					bean.setAccount(periodAccount.add(repaymentInterest
							.multiply(BigDecimal.valueOf(borrowDivides))));// 还本金+利息
					bean.setInterest(repaymentInterest.multiply(BigDecimal
							.valueOf(borrowDivides)));
				}
			}

			totalRepaymentInterest = totalRepaymentInterest
					.add(repaymentInterest);

			repaymentDetailList.add(bean);

		}

		repaymentInfo.setAccount(borrowAccount.add(totalRepaymentInterest));
		repaymentInfo.setCapital(borrowAccount);
		repaymentInfo.setInterest(totalRepaymentInterest);

		repaymentInfo.setRepaymentDetailList(repaymentDetailList);
		
		repaymentInfo.setOrderNum(repaymentDetailList.size());

		return repaymentInfo;
	}

}