package com.qmd.util;

import com.qmd.mode.borrow.Borrow;

import java.math.BigDecimal;

/**
 * 月标公共类
 * @author xsf
 *
 */
public class MonthUtil {
	
	/**
	 * 每份金额 = 借款标总金额/总份数
	 * @param borrow
	 * @return
	 */
	public static BigDecimal moneyForPerPiece(Borrow borrow) {
		double ret = Double.valueOf(borrow.getAccount())
				/ borrow.getWanderPieceSize();

		return new BigDecimal(ret);

	}
	
	
	
}
