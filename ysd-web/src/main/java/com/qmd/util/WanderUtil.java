package com.qmd.util;

import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.util.NodeBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 流转标公共类
 */

public class WanderUtil {

	/**
	 * 流转标 取得赎回计划
	 * 
	 * @param str
	 * @return
	 */
	public static List<NodeBean> fillWanderPlan(String str) {
		if (str == null || "".equals(str.trim())) {
			return null;
		}
		NodeBean bean = null;
		List<NodeBean> list = new ArrayList<NodeBean>();
		String strs[] = str.split(":");
		for (int i = 0; i < strs.length; i++) {
			String itmes[] = strs[i].split(",");
			bean = new NodeBean();
			bean.setCode(itmes[0]);
			bean.setName(itmes[1]);
			list.add(bean);
		}
		return list;
	}

	public static BigDecimal moneyForWanderPiece(Borrow borrow) {
		double ret = Double.valueOf(borrow.getAccount())
				/ borrow.getWanderPieceSize();

		return new BigDecimal(ret);

	}

	/**
	 * 最大购买份数
	 * 
	 * @param borrow
	 * @param userAbleMoney
	 * @return
	 */
	public static int maxWanderPiece(Borrow borrow, BigDecimal userAbleMoney) {

		// 用户的可用金额，够买几份
//		BigDecimal bd_size = userAbleMoney.divide(borrow.getWanderPieceMoney(),
//				ConstantUtil.getMathContent());
		Double ll = userAbleMoney.doubleValue()/borrow.getWanderPieceMoney().doubleValue();
		int size = ll.intValue();
		//int size = bd_size.intValue();

		// 存在最大购买显示
		if (borrow.getMostAccount() != null
				&& !"".equals(borrow.getMostAccount())
				&& !"0".equals(borrow.getMostAccount())) {
			int most = Integer.valueOf(borrow.getMostAccount());

			size = (size < most) ? size : most;
		}

		// 标的剩余购买份数
		Long lv = Double.valueOf(borrow.getBalance()).longValue()
				/ borrow.getWanderPieceMoney().longValue();
		int ilv = lv.intValue();
		size = (size < ilv) ? size : ilv;

		return size;

	}

	/**
	 * 判断是否整除
	 * @param total
	 * @param piece
	 * @return
	 */
	public static boolean isMod(String total, String piece) {
		try {
			int t = Integer.valueOf(total);
			int p = Integer.valueOf(piece);

			if (p==0) {
				return false;
			} else if (t % p == 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

}