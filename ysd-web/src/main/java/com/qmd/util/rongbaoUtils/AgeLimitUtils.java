package com.qmd.util.rongbaoUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 融宝年龄限制：[18,70]岁可绑
 * 判定用户年龄是否在融宝年龄限制内
 * @author zdl
 *
 */
public class AgeLimitUtils {

	/**
	 * 根据身份证号，判定
	 * @return
	 */
	public static boolean isInLimit(String cardId){
		String data = cardId.substring(6, 14);
		String ageStr = calculateDate(data);
		Integer age = Integer.valueOf(ageStr);
		if(age >= 18 && age <= 70){
			return true;
		}
		return false;
	}
	
	private static String calculateDate(String data){
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		Date beDate = null;
		Date edDate = null;
		try {
			beDate = myFormatter.parse(data);//出生日期
			edDate = myFormatter.parse(getTimeStr(date, "yyyyMMdd"));//当前日期
		} catch (ParseException e) {
			return "0";
		}
		if(edDate==null|| beDate == null || edDate.getTime()<beDate.getTime()){
			return "0";//出生日期不能大于系统当前日期
		}
		long day = (edDate.getTime()-beDate.getTime())/(24*60*60*1000)+1;
		String year = new DecimalFormat("#.00").format(day/365f);
		String age[] = year.split("\\.");
		return age[0].replace(" ","").length()<1?"0":age[0];
	}
	
	
	/**
	 * 按照指定格式将日期转换成字符串
	 * @param date
	 * @param format
	 * @return
	 */
	private static String getTimeStr(Date date,String format){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}
	
	
}
