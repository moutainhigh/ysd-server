package net.qmdboss.util;

import java.math.BigDecimal;

public class NumberUtil {

	public static final int PRICESCALE = 2;

	public static double bigDecimal2Double(BigDecimal big) {
		if (big == null) {
			return 0D;
		}
		return big.doubleValue();
	}

	public static BigDecimal setPriceScale(BigDecimal price) {
		return setPriceScale(price, null);
	}

	public static BigDecimal setPriceScale(Double price) {
		BigDecimal price2 = BigDecimal.ZERO;
		if (price != null) {
			price2 = BigDecimal.valueOf(price);
		}
		return setPriceScale(price2, null);
	}

	/**
	 * 设置价格精确位数
	 * 
	 */
	public static BigDecimal setPriceScale(BigDecimal price, Integer pricescale) {
		if (price == null) {
			price = BigDecimal.ZERO;
		}

		if (pricescale == null) {
			pricescale = PRICESCALE;// 默认2位小数
		}

		return price.setScale(pricescale, BigDecimal.ROUND_HALF_UP);

	}

}
