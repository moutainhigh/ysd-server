package com.qmd.util;

import com.qmd.util.ConstantUtil.RoundType;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类 - 公用
 */

public class NumberUtil {
	
	/**
	 * 判定字符串是不是数字
	 * @param numStr
	 * @return
	 */
	public static boolean isNotNumber(String numStr){
		if(StringUtils.isBlank(numStr)){
			return true;
		}
		Pattern p = Pattern.compile("^[0-9]*$");
		Matcher m = p.matcher(numStr);
		if (m.matches()) {//匹配到全是数字
			return false;
		}
		return true;
	}
	
	
	/**
	 * 精确数值 double2Bigdicimal
	 * @param price
	 * @return
	 */
	public static BigDecimal double2BigDecimal(double price) {

		return setPriceScale(BigDecimal.valueOf(price));

	}

	/**
	 * 设置价格精确位数
	 * 
	 */
	public static BigDecimal setPriceScale(BigDecimal price,
			ConstantUtil.RoundType priceRoundType) {
		if (price != null) {
			Integer priceScale = ConstantUtil.PRICESCALE;
			if (priceRoundType == null
					|| priceRoundType == ConstantUtil.RoundType.roundHalfUp) {
				return price.setScale(priceScale, BigDecimal.ROUND_HALF_UP);
			} else if (priceRoundType == ConstantUtil.RoundType.roundUp) {
				return price.setScale(priceScale, BigDecimal.ROUND_UP);
			} else {
				return price.setScale(priceScale, BigDecimal.ROUND_DOWN);
			}
		}
		return null;
	}
	
//	public static BigDecimal setPriceScale(BigDecimal price,int scale) {
//		if (price != null) {
//			Integer priceScale = ConstantUtil.PRICESCALE;
//			if (priceRoundType == null
//					|| priceRoundType == ConstantUtil.RoundType.roundHalfUp) {
//				return price.setScale(priceScale, BigDecimal.ROUND_HALF_UP);
//			} else if (priceRoundType == ConstantUtil.RoundType.roundUp) {
//				return price.setScale(priceScale, BigDecimal.ROUND_UP);
//			} else {
//				return price.setScale(priceScale, BigDecimal.ROUND_DOWN);
//			}
//		}
//		return null;
//	}
	
	public static String setPriceScale(double price) {

		return setPriceScale(BigDecimal.valueOf(price)).toString();

	}

	public static int divideTimes(int d1, int d2) {
		int ret = d1 / d2;
		if (d1 % d2 > 0) {
			ret++;
		}

		return ret;
	}

	/**
	 * 四舍五入
	 * 
	 * @param price
	 * @return
	 */
	public static BigDecimal setPriceScale(BigDecimal price) {
		return setPriceScale(price, ConstantUtil.RoundType.roundHalfUp);
	}

	public static BigDecimal notEmptyBigDecimal(BigDecimal big) {
		if (big == null)
			return BigDecimal.valueOf(0);
		return big;
	}

	public static double string2double(String str) {
		if (str == null || "".equals(str.trim())) {
			return 0;
		}
		return Double.parseDouble(str);
	}

	public static String setPriceScale2String(String money) {
		if (money == null || "".equals(money.trim())) {
			return "0.00";
		}
		return setPriceScale(Double.valueOf(money));
	}

	public static double bigDecimal2Double(BigDecimal big) {
		if (big == null) {
			return 0D;
		}
		return big.doubleValue();
	}

	public static String bigDecimal2String(BigDecimal price) {
		return setPriceScale(price).toString();
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
	 * 进行加法运算
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal add(BigDecimal d1, BigDecimal d2) { // 进行加法运算

		return setPriceScale((d1.add(d2)), RoundType.roundHalfUp);
	}

	/**
	 * 进行减法运算
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal sub(BigDecimal d1, BigDecimal d2) { // 进行减法运算

		return setPriceScale((d1.subtract(d2)), RoundType.roundHalfUp);
	}

	/**
	 * 进行乘法运算
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal mul(BigDecimal d1, BigDecimal d2) { // 进行乘法运算

		return setPriceScale((d1.multiply(d2)), RoundType.roundHalfUp);
	}
	
	public static String formatNumber(double d,int point) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMinimumFractionDigits(point);

		return format.format(d);
	}

	public static void main(String[] args) {
		BigDecimal b = BigDecimal.valueOf(1021305.1235);
		b=  b.setScale(0, BigDecimal.ROUND_HALF_UP);
	System.out.println(b);	 
System.out.println(formatNumber(1021305.1235,2));
	}

}