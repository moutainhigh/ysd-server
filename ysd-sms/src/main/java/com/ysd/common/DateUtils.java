package com.ysd.common;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {
	
	public static final String DATE_FORMAT_NUM_MILL = "yyyyMMddHHmmssSSS";

	public static final String DATE_FORMAT_DIVIDE_SECOND = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_FORMAT_NUM_SECOND = "yyyyMMddHHmmss";

	public static final String DATE_FORMAT_DIVIDE = "yyyy-MM-dd";

	public static final String DATE_FORMAT_DIVIDE_MONTH = "yyyy-MM";

	public static final String DATE_FORMAT_HOUR = "HH:mm";

	public static String getDateTime(Date date, String format) {
		if (date == null || StringUtils.isEmpty(format)) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static String getDefaultDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DIVIDE);
		return sdf.format(date);
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurrentDate() {
		return getDateTime(new Date(), DATE_FORMAT_NUM_MILL);
	}
	
	/**
	 * 解析时间，如异常则返回当前时间
	 * @param date
	 * @return
	 */
	public static Date getDateFromString(String date) {
        Date result = getDateFromString(date, DATE_FORMAT_DIVIDE);
        if (result == null) {
            return new Date();
        } else {
            return result;
        }
    }

	public static Date getDateFromStringNull(String date) {
        return getDateFromString(date, DATE_FORMAT_DIVIDE);
    }

    public static Date getDateFromString(String date, String format) {
        if (StringUtils.isNotEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getTodayString(String format){
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(today);
    }
	
	public static Date toDate(final String date, final String format) {
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Date getDate(int day) {
		if (day <= 0) {
			return new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}
	
	public static String getDateStr(int day) {
		return getDateTime(getDate(day), DATE_FORMAT_DIVIDE);
	}

	public static boolean before(Date d1, Date d2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(d1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(d2);
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);

		return calendar1.before(calendar2);
	}

    /**
     * 计算当前时间与指定时间的间隔
     * @param date
     * @param format
     * @return
     */
    public static long diffMills(String date, String format) {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(format)) {
            return -1;
        }
        Date date1 = getDateFromString(date, format);

        if (date1 == null) {
            return -1;
        }

        long t1 = date1.getTime();
        long t2 = System.currentTimeMillis();// 当前时间

        long diff = t2 - t1;
        return diff;
    }

    public static long diffMinute(String date) {
        long diffMill = diffMills(date, DATE_FORMAT_NUM_SECOND);
        long diffMinute =  diffMill / (1000 * 60);
        return diffMinute;
    }

    /**
     * 在当天的基础上加day后，生成新的日期
     * @param day
     * @return
     */
	public static Date getIntervalDate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }

    /**
     * 当前月的第一天
     * @param date
     * @return
     */
    public static Date getFirstOfMonth(Date date) {
        Calendar curr = Calendar.getInstance();
        curr.setTime(date);
        curr.set(Calendar.DAY_OF_MONTH, 1);
        curr.set(Calendar.HOUR_OF_DAY, 0);
        curr.set(Calendar.MINUTE, 0);
        curr.set(Calendar.SECOND, 0);
        return curr.getTime();
    }

    /**
     * 当前月的最后一天
     * @param date
     * @return
     */
    public static Date getLastOfMonth(Date date) {
        Calendar curr = Calendar.getInstance();
        curr.setTime(date);
        int maxDay = curr.getActualMaximum(Calendar.DAY_OF_MONTH);
        curr.set(Calendar.DAY_OF_MONTH, maxDay);
        curr.set(Calendar.HOUR_OF_DAY, 23);
        curr.set(Calendar.MINUTE, 59);
        curr.set(Calendar.SECOND, 59);
        return curr.getTime();
    }


    /**
     * 根据当前时间返回上一个月的第一天日期
     * @param date
     * @return
     */
    public static Date getPreFirstOfMonth(Date date) {
        Calendar curr = Calendar.getInstance();
        curr.setTime(date);
        curr.add(Calendar.MONTH, -1);
        curr.set(Calendar.DAY_OF_MONTH, 1);
        curr.set(Calendar.HOUR_OF_DAY, 0);
        curr.set(Calendar.MINUTE, 0);
        curr.set(Calendar.SECOND, 0);
        return curr.getTime();
    }

    /**
     * 上个月最后一天
     * @param date
     * @return
     */
    public static Date getPreLastOfMonth2(Date date){
        Calendar curr = Calendar.getInstance();
        curr.setTime(date);
        curr.set(Calendar.DAY_OF_MONTH, 0);// 设置为空零后，该日期将会变成上个月的最后一天
        curr.set(Calendar.HOUR_OF_DAY, 23);
        curr.set(Calendar.MINUTE, 59);
        curr.set(Calendar.SECOND, 59);
        return curr.getTime();
    }

    /**
     * 上个月最后一天
     * @param date
     * @return
     */
    public static Date getPreLastOfMonth(Date date){
        Calendar curr = Calendar.getInstance();
        curr.setTime(date);
        curr.add(Calendar.MONTH, -1);
        int maxDay = curr.getActualMaximum(Calendar.DAY_OF_MONTH);
        curr.set(Calendar.DAY_OF_MONTH, maxDay);
        curr.set(Calendar.HOUR_OF_DAY, 0);
        curr.set(Calendar.MINUTE, 0);
        curr.set(Calendar.SECOND, 0);
        return curr.getTime();
    }


    public static void main(String[] args) {
//        String dateCreated = "20150903153000";
//        long result = diffMinute(dateCreated);
//        System.out.println("result:" + result);
//        Date d = getIntervalDate(3);
//        System.out.println(d);

//        Date d1 = getDateFromString("2015-09-08");
//        boolean before = before(d1, new Date());
//        System.out.println("result:" + before);

//        Calendar cale = Calendar.getInstance();
//        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
//        System.out.println("-----2------lastDay:"+cale.getTime());

        long diffMinute = diffMinute("20150922091124");
        System.out.println("diffMinute:" + diffMinute);

//        Date d = getPreFirstOfMonth(new Date());
//        Date d2 = getPreLastOfMonth(new Date());
//        System.out.println(d + "**" + d2);
    }

}
