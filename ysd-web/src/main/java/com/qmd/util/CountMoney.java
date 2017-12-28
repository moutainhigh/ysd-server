package com.qmd.util;

import java.io.IOException;
import java.text.DecimalFormat;

public class CountMoney {

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		//System.out.println("以下程序将实现等额本息还款的计算");
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//System.out.println("请输入贷款金额：");
		double payment = Double.parseDouble("100");// 贷款金额

		//System.out.print("请输入年利率："); // 年利率
		double ret = Double.parseDouble("0.21");

		//System.out.print("请输入贷款期数,即贷款月数："); // 分期月份数量
		int num = Integer.parseInt("1");

		//br.close();

		System.out.println("每月还款金额为：" + payPerMonth(payment, ret, num));
	}

	public static String payPerMonth(double payment, double ret, int num) {
		// 等额本息还款计算,其中date 为月利率
		DecimalFormat df = new DecimalFormat("#.00");
		double date = ret / 12;
		double monthTotal = (payment * date * Math.pow(1+date, num)) / (Math.pow(1+date,num) - 1);
		return df.format(monthTotal);	
		}
}