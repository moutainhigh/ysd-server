package net.qmdboss.util;

import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX9A02B310F538BFE58B8F04583E341B6E
 * ============================================================================
 */

public class CommonUtil {
	
	public static final String WEB_APP_ROOT_KEY = "qmdboss.webAppRoot";// WebRoot路径KEY
	public static final String PATH_PREPARED_STATEMENT_UUID = "\\{uuid\\}";// UUID路径占位符
	public static final String PATH_PREPARED_STATEMENT_DATE = "\\{date(\\(\\w+\\))?\\}";// 日期路径占位符

	public static final String PR="fy";//前缀
	public static final String WEB_SITE = SettingUtil.getSetting().getShopUrl();
//	public static final String WEB_SITE="http://10.10.10.222:8086/qmd";
	public static final String WEB_IMG = SettingUtil.getSetting().getShopImgContext();
	
	//http://127.0.0.1:8080/qmd/index.img?image=
	public static final String WEB_IMG_ENCODE_URL = WEB_SITE + SettingUtil.getSetting().getShopImgEncodeUrl();
	
	public static final String GFB_WEB="https://www.gopay.com.cn";//国付宝【正式地址  https://www.gopay.com.cn】
	
	public static final String XSZF_WEB="http://www.hnapay.com";//新生支付地址【 正式地址 http://www.hnapay.com】
	
	public static final String BF_WEB="http://paygate.baofoo.com";//宝付支付【 正式地址 http://paygate.baofoo.com】
	
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
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
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
	 * 获取指定格式的日期(String转Date)
	 * 
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date getString2Date(String str, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);// "yyyy-MM-dd HH:mm:ss"
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
	 * 取得日期（数字型)
	 * 
	 * @param date
	 * @return
	 */
	public static int getIntDate(Date date) {
		String dt = getDate2String(date, "yyyyMMdd");
		return Integer.parseInt(dt);
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
//			,'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
//			'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
	Random random = new Random();
	StringBuffer stringBuffer = new StringBuffer();
	for (int i = 0; i < length; i++) {
		stringBuffer.append(randomChar[Math.abs(random.nextInt()) % randomChar.length]);
	}
	return stringBuffer.toString();
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

/** 得到抓取页面的html
 * @param urlPath
 * @return
 * @throws IOException
 */
public static String getHtml(String urlPath,String type) throws IOException{
	String html = "";
	try {
		URL url = new URL(urlPath);
		System.out.println(url);
		HttpURLConnection  con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);   
		con.setDoInput(true);
		con.connect();  
		InputStream in = con.getInputStream();   
		byte[] buf = new byte[4096];   
		int bytesRead;   
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
		while ((bytesRead = in.read(buf)) != -1) {   
		    byteArrayOutputStream.write(buf, 0, bytesRead);   
		}
		if(type.equals("tudou")){
			html = new String(byteArrayOutputStream.toByteArray(),"gbk");
		}else{
			html = new String(byteArrayOutputStream.toByteArray(),"utf-8"); 
		}
		in.close();
		byteArrayOutputStream.close();
		con = null;
	} catch (Exception e) {
		e.printStackTrace();
	}
    return  html;
}
	/**
	 * 标的链接
	 * @param bid
	 * @param bname
	 * @return
	 */
	public static String fillBorrowUrl(Integer bid, String bname) {
		return "<a target=\"_blank\" href=\"" + WEB_SITE
				+ "/borrow/detail.do?bId=" + bid + " \">" + bname + "</a>";
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

}