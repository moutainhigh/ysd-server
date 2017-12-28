package com.qmd.util;

import com.qmd.util.ConstantUtil.RoundType;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类 - 公用
 */

public class CommonUtil {
	
	public static final String WEB_APP_ROOT_KEY = "shopxx.webAppRoot";// WebRoot路径KEY
	public static final String PATH_PREPARED_STATEMENT_UUID = "\\{uuid\\}";// UUID路径占位符
	public static final String PATH_PREPARED_STATEMENT_DATE = "\\{date(\\(\\w+\\))?\\}";// 日期路径占位符
	
	/** 验证码 */
	public static final String CODE_CONTENT = "验证码：";
	/** 验证码 */
	public static final String PWS_CONTENT = "您新的登录密码为";
	
	/**
	 * 获取WebRoot路径
	 * 
	 * @return WebRoot路径
	 */
	public static String getWebRootPath() {
		return System.getProperty(WEB_APP_ROOT_KEY);
	}

	/**
	 * 随机获取UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
	}
	
	/**
	 * 随机获取字符串
	 * 
	 * @param length
	 *            随机字符串长度
	 * 
	 * @return 随机字符串
	 */
	public static String getRandomString(int length) {
		if (length <= 0) {
			return "";
		}
		char[] randomChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 
				};
//				,'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
//				'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(randomChar[Math.abs(random.nextInt()) % randomChar.length]);
		}
		return stringBuffer.toString();
	}
	/**
	 * 保存地区时使用
	 * @param domain 
	 * @return
	 */
	public static String[] splitString(String domain){
		String[] str=new String[3];
		if(domain.split(",").length==0){
			str[0]=domain;
		}else if(domain.split(",").length==1){
			str[0]=domain.split(",")[0];
		}else if(domain.split(",").length==2) {
			str[0]=domain.split(",")[0];
			str[1]=domain.split(",")[1];
		}else if(domain.split(",").length==3){
			str[0]=domain.split(",")[0];
			str[1]=domain.split(",")[1];
			str[2]=domain.split(",")[2];
		}
		return str;
	}
	
	/**
	 * 获取实际路径
	 * 
	 * @param path
	 *            路径
	 */
	public static String getPreparedStatementPath(String path) {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		StringBuffer uuidStringBuffer = new StringBuffer();
		Matcher uuidMatcher = Pattern.compile(PATH_PREPARED_STATEMENT_UUID).matcher(path);
		while(uuidMatcher.find()) {
			uuidMatcher.appendReplacement(uuidStringBuffer, CommonUtil.getUUID());
		}
		uuidMatcher.appendTail(uuidStringBuffer);
		
		StringBuffer dateStringBuffer = new StringBuffer();
		Matcher dateMatcher = Pattern.compile(PATH_PREPARED_STATEMENT_DATE).matcher(uuidStringBuffer.toString());
		while(dateMatcher.find()) {
			String dateFormate = "yyyyMM";
			Matcher dateFormatMatcher = Pattern.compile("\\(\\w+\\)").matcher(dateMatcher.group());
			if (dateFormatMatcher.find()) {
				String dateFormatMatcherGroup = dateFormatMatcher.group();
				dateFormate = dateFormatMatcherGroup.substring(1, dateFormatMatcherGroup.length() - 1);
			}
			dateMatcher.appendReplacement(dateStringBuffer, new SimpleDateFormat(dateFormate).format(new Date()));
		}
		dateMatcher.appendTail(dateStringBuffer);
		
		return dateStringBuffer.toString();
	}

	// 获取图片上传真实路径
	public static String getImageUploadRealPath() {
		String imageUploadPath= StringUtils.removeEnd(ConstantUtil.IMAGE_UPLOAD_PATH, "/");
		return CommonUtil.getPreparedStatementPath(imageUploadPath);
	}
	
	/**
	 * 图片大小
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static Boolean getFileSize(File file) throws IOException{
		FileInputStream fis = null;
        fis = new FileInputStream(file);
        Boolean flag = false;
		if(fis.available()>ConstantUtil.IMAGE_SIZE){
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * 设置价格精确位数
	 * 
	 */
	public static BigDecimal setPriceScale(BigDecimal price,ConstantUtil.RoundType priceRoundType) {
		if (price != null) {
			Integer priceScale = ConstantUtil.PRICESCALE;
			if (priceRoundType == null ||priceRoundType == ConstantUtil.RoundType.roundHalfUp) {
				return price.setScale(priceScale, BigDecimal.ROUND_HALF_UP);
			} else if (priceRoundType == ConstantUtil.RoundType.roundUp) {
				return price.setScale(priceScale, BigDecimal.ROUND_UP);
			} else {
				return price.setScale(priceScale, BigDecimal.ROUND_DOWN);
			}
		}
		return null;
	}
	
	public static String setPriceScale(double price) {
		
		return setPriceScale(BigDecimal.valueOf(price)).toString();
		
	}
	/**
	 * 四舍五入
	 * @param price
	 * @return
	 */
	public static BigDecimal setPriceScale(BigDecimal price) {
		return setPriceScale( price,ConstantUtil.RoundType.roundHalfUp);
	}
	
	public static BigDecimal notEmptyBigDecimal(BigDecimal big) {
		if (big==null) return BigDecimal.valueOf(0);
		return big;
	}
	
	public static double string2double(String str) {
		if (str==null||"".equals(str.trim())) {
			return 0;
		}
		return Double.parseDouble(str);
	}
	
	public static String setPriceScale2String(String money) {
		if (money==null||"".equals(money.trim())) {
			return "0.00";
		}
		return setPriceScale(Double.valueOf(money));
	}
	
	public static double bigDecimal2Double(BigDecimal big) {
		if (big==null) {
			return 0D;
		}
		return big.doubleValue();
	}
	
	/**
	 * 获取货币格式字符串
	 * 
	 */
	public static String getCurrencyFormat() {
		Integer priceScale = ConstantUtil.PRICESCALE;
		String currencySign = ConstantUtil.CURRENCYSIGN;
		String currencyUnit = ConstantUtil.CURRENCYUNIT;
		if (priceScale == 0) {
			return "'" + currencySign + "'#0'" + currencyUnit + "'";
		} else if (priceScale == 1) {
			return "'" + currencySign + "'#0.0'" + currencyUnit + "'";
		} else if (priceScale == 2) {
			return "'" + currencySign + "'#0.00'" + currencyUnit + "'";
		} else if (priceScale == 3) {
			return "'" + currencySign + "'#0.000'" + currencyUnit + "'";
		} else if (priceScale == 4) {
			return "'" + currencySign + "'#0.0000'" + currencyUnit + "'";
		} else {
			return "'" + currencySign + "'#0.00000'" + currencyUnit + "'";
		}
	}
	
	/**
	 * 获取货币格式化
	 * 
	 */
	public static String currencyFormat(BigDecimal price) {
		String currencyFormat = getCurrencyFormat();
		NumberFormat numberFormat = new DecimalFormat(currencyFormat);
		return numberFormat.format(price);
	}
	

/**
 * 	进行加法运算
 * @param d1
 * @param d2
 * @return
 */
 public static BigDecimal add(BigDecimal d1, BigDecimal d2)
 {        // 进行加法运算 
	          
	   return setPriceScale((d1.add(d2)),RoundType.roundHalfUp); 
 } 
 
 /**
  * 进行减法运算 
  * @param d1
  * @param d2
  * @return
  */
public static BigDecimal sub(BigDecimal d1, BigDecimal d2)
 {        // 进行减法运算 
	           
	 return setPriceScale((d1.subtract(d2)),RoundType.roundHalfUp); 
 } 

/**
 * 进行乘法运算
 * @param d1
 * @param d2
 * @return
 */
public static BigDecimal mul(BigDecimal d1, BigDecimal d2)
{        // 进行乘法运算 
	          
	   return setPriceScale((d1.multiply(d2)),RoundType.roundHalfUp); 
} 


	/**
	 * 验证时间格式是否正确
	 * @param s
	 * @return
	 */
	public static boolean isValidDate(String s)
	{
	    try
	    {
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	         dateFormat.parse(s);
	         return true;
	     }
	    catch (Exception e)
	    {
	        // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
	        return false;
	    }
	}
	
	/**
	 * 取得long型时间
	 * @return
	 */
	public static long getDateTimeLong() {
		Date date = new Date();
		return date.getTime();
	}
	

	   /** 
	    * 得到几天前的时间 
	    * @param d 
	    * @param day 
	    * @return 
	   */  
public static Date getDateBefore(Date d,int day){  
	  Calendar now =Calendar.getInstance();  
	  now.setTime(d);  
	  now.set(Calendar.DATE,now.get(Calendar.DATE)-day);  
	  return now.getTime();  
}  
    

/**
 *  得到几天后的时间 
 * @param d
 * @param day
 * @return
 */
public static Date getDateAfter(Date d,int day){  
	if (d==null) {
		d= new Date();
	}
	 Calendar now =Calendar.getInstance();  
	 now.setTime(d);  
	 now.set(Calendar.DATE,now.get(Calendar.DATE)+day);  
	 return now.getTime();  
  }  

/**
 * 转译传入查询语句中的特性字符
 * @param str
 * @return
 */
public static String changeChar(String str){
	str=str.replaceAll("\\\\", "\\\\\\\\");
	str=str.replaceAll("'", "\\\\'");
	str=str.replaceAll("_", "\\\\_");
	str=str.replaceAll("%", "\\\\%");
	return str;
}

public static int getDayFromDate(Date date) {
	 Calendar now =Calendar.getInstance();  
	 now.setTime(date);  
	 return now.get(Calendar.DAY_OF_MONTH);
}

/**
 * 取得日期（数字型)
 * @param date
 * @return
 */
public static int getIntDate(Date date) {
	String dt = getDate2String(date,"yyyyMMdd");
	return Integer.parseInt(dt);
}

/**
 * 取得当月第一天日期
 * @param date
 * @return
 */
public static Date getFirstDayOfMonth(Date date) {
	 Calendar now =Calendar.getInstance();  
	 now.setTime(date);
	 now.set(Calendar.DATE, 1);
	 return now.getTime();
}

/**
 * 得到几个月后的时间
 * 
 * @param d
 * @param i
 * @return
 */
public static Date getMonthAfter(Date d, int i) {
	Calendar now = Calendar.getInstance();
	now.setTime(d);//设置日历时间   
	now.add(Calendar.MONTH,i);//在日历的月份上增加6个月   
	return now.getTime();
}
/**
 * 计算两个日期的天数
 * @param startDate 开始日期
 * @param endDate 结束日期
 * @return
 */
	public static Long getDateSubtractDay (Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return null;
		}
	
		long seconds = (endDate.getTime() - startDate.getTime()) / 1000;
		
		long day = seconds / (24 * 60 * 60);
		if (seconds % (24 * 60 * 60) > 0) {
			day++;
		}
	
		return day;
	
	}
	
	/**
	 * 将String类型的日期，转化日期类型
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date getString2Date(String str,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);//"yyyy-MM-dd HH:mm:ss"
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date;
	}
	
	/**
	 * 将日期转化成String类型
	 * @param dt
	 * @param format
	 * @return
	 */
	public static String getDate2String(Date dt, String format) {
		SimpleDateFormat sdf=new SimpleDateFormat(format);  
		String str=sdf.format(dt);  

		return str;
	}
	
	/**
	 * 将日期设置为 日期的00:00:00
	 * @param dt
	 * @return
	 */
	public static Date date2begin(Date dt) {
		String str = getDate2String(dt,"yyyy-MM-dd");
		
		str +=" 00:00:00";
		
		return getString2Date(str,"yyyy-MM-dd HH:mm:ss");
	}
	
	public static Date date2end(Date dt) {
		String str = getDate2String(dt,"yyyy-MM-dd");
		
		str +=" 23:59:59";
		
		return getString2Date(str,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 利息取得
	 * @param account 金额
	 * @param day 天数
	 * @param apr 天利率（千分之）
	 * @return
	 */
	public static double getInterest(double account,int day,double apr) {
		return account * apr * day /1000;
	}
	
	/**
	 * 取得对应月的天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysOfMonth(int year,int month) {
		
		int[] s = {0,31,28,31,30,31,30,31,31,30,31,30,31};
		int ret = s[month];
		if (month ==2) {// 2月，判断闰年
			if (year%100==0) {
				if (year%400==0) {
					return 29;
				}
				return 28;
			} else if(year %4 ==0) {
				return 29;
			} 
			
		}
		
		return ret;
		
		
	}
	
	/**
	 * 标的链接
	 * @param bid
	 * @param bname
	 * @return
	 */
	public static String fillBorrowUrl(Integer bid, String bname) {
		return "<a target=\"_blank\" href=\"" + ConstantUtil.WEB_SITE
				+ "/borrow/detail.do?bId=" + bid + " \">" + bname + "</a>";
	}
	
	public static String ipSplit(String str) {
		if (str == null || "".equals(str)) {
			return null;
		}
		String[] ss = str.split(",");
		if (ss == null || ss.length <= 0) {
			return null;
		}
		if (ss[0] == null) {
			return null;
		}
		return ss[0].trim();
	}
	
	/**
	 * 解密
	 * @param str
	 * @return
	 */
	public static String encodeUrl(String str) {
		if (str == null) {
			return "";
		}

		String ret = "";
		try {
			String temp = ImageBase64Util.encode(str);
			ret = ImageDesUtil.encrypt(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}
	
	/**解密*/
	public static String decodeUrl(String param) {
		String ret = "";
		try {
			
			String temp = ImageDesUtil.decrypt(param);
			
			ret = ImageBase64Util.decode(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static boolean checkNumberIntUpZero(String str) {
		
		boolean ret = false;
		if (str==null ||"".equals(str)) {
			return ret;
		}
		try {
			int n = Integer.parseInt(str);

			if (n > 0) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
		
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date d = getDateAfter(new Date(),0);

		System.out.println(d);
		
		
		int a = getDaysOfMonth(2008, 2);
		System.out.println(a);
		a = getDaysOfMonth(2000, 2);
		System.out.println(a);
		a = getDaysOfMonth(2004, 2);
		System.out.println(a);
		a = getDaysOfMonth(2010, 2);
		System.out.println(a);
		
		
		
		a = getDaysOfMonth(1901, 2);
		System.out.println(a);
		a = getDaysOfMonth(1900, 2);
		System.out.println(a);
		
	}
	/**
	 * 随机获取字符串(字母加数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * 
	 * @return 随机字符串
	 */
	public static String getRandomNumAndLetter(int length) {
		if (length <= 0) {
			return "";
		}
		char[] randomChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 
				,'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
				'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm',
				'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D',
				'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'
				};
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(randomChar[Math.abs(random.nextInt()) % randomChar.length]);
		}
		return stringBuffer.toString();
	}

}