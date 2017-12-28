package com.qmd.util;

import com.qmd.mode.borrow.Borrow;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

/**
 * CalculationUtil计算公式类
 * 
 * @author zhanf
 *
 */
public class CalculationUtil {
	Logger log = Logger.getLogger(CalculationUtil.class);
	
	/**
	 * 计算每日利率
	 * @param borrow
	 * @return
	 */
	public static double dayInterest(Borrow borrow) {
		double dayInterest = borrow.getApr()/1000;
		return dayInterest;
	}
	
	/**
	 * 计算每日利率
	 * @param borrow
	 * @return
	 */
	public static double monthInterest(Borrow borrow) {
		double monthInterest = borrow.getApr()/1200;
		return monthInterest;
	}
	
	/**
	 * 计算还款总额(总本金，总利息，不含奖励)
	 * @param apr
	 * @param timeLimit
	 * @param account
	 * @param borStages
	 * @return
	 */
	public static String totalMoney(Borrow borrow) {

		//每天利率
		double dayInterest = dayInterest(borrow);
		double total = 0;
		double day = 0;
		if (borrow.getBorStages() != null
				&& !"".equals(borrow.getBorStages().trim())) {
			String str[] = borrow.getBorStages().split(":");
			double interest = 0;
			double account = Double.valueOf(borrow.getAccount());
			for (int i = 0; i < str.length; i++) {
				String str1[] = str[i].split(",");
				// 本期的天数（本期日期-上一期的天数）
				day = Double.valueOf(str1[0]) - day;
				// 本期利息
				interest = day * dayInterest * account;
				day = Double.valueOf(str1[0]);
				account = account-Double.parseDouble(str1[1]);
				
				total += Double.parseDouble(str1[1])+interest;
				
				System.out.println("第"+(i+1)+"期 值:["+str[i]+"] 本金:" + str1[1]+" 利息:"+interest+" 剩余借款:"+account+" 累计总额:"+total);
				
			}
		}
		// 不含奖励
		/*
		if (borrow.getAward().equals("1")) {
			total += (Double.parseDouble(borrow.getFunds()) / 100)
					* Double.parseDouble(borrow.getAccount());
		}
		*/
		DecimalFormat df = new java.text.DecimalFormat("#0.00");
		return df.format(total);
	}
	
	
	/**
	 * 计算月标还款总额(总本金，总利息，不含奖励)
	 * @param apr
	 * @param timeLimit
	 * @param account
	 * @param borStages
	 * @return
	 */
	public static String totalMonthMoney(Borrow borrow) {

		//每天利率
		double monthInterest = monthInterest(borrow);
		double total = 0;
		double month = 0;
		if (borrow.getBorStages() != null
				&& !"".equals(borrow.getBorStages().trim())) {
			String str[] = borrow.getBorStages().split(":");
			double interest = 0;
			double account = Double.valueOf(borrow.getAccount());
			for (int i = 0; i < str.length; i++) {
				String str1[] = str[i].split(",");
				// 本期的天数（本期日期-上一期的天数）
				month = Double.valueOf(str1[0]) - month;
				// 本期利息
				interest = month * monthInterest * account;
				month = Double.valueOf(str1[0]);
				account = account-Double.parseDouble(str1[1]);
				
				total += Double.parseDouble(str1[1])+interest;
				
				System.out.println("第"+(i+1)+"期 值:["+str[i]+"] 本金:" + str1[1]+" 利息:"+interest+" 剩余借款:"+account+" 累计总额:"+total);
				
			}
		}
		// 不含奖励
		/*
		if (borrow.getAward().equals("1")) {
			total += (Double.parseDouble(borrow.getFunds()) / 100)
					* Double.parseDouble(borrow.getAccount());
		}
		*/
		DecimalFormat df = new java.text.DecimalFormat("#0.00");
		return df.format(total);
	}
	

	public static void main(String[] args){
		//CalculationUtil util = new CalculationUtil();
		//System.out.println(util.getInterest("100", 21, "2"));
		
	}
}
