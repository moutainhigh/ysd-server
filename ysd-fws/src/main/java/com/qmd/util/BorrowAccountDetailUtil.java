package com.qmd.util;

import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowAccountDetail;

import java.math.BigDecimal;

public class BorrowAccountDetailUtil {

	public static BorrowAccountDetail fillBorrowAccountDetailUtil(//
			String type,//
			BigDecimal money, //
			Integer userId,//
			Integer agencyId,//
			String remark,//
			String ip,//
			Borrow borrow//
	) {

		BorrowAccountDetail bad = new BorrowAccountDetail();
		bad.setAgencyId(agencyId);
		bad.setBorrowId(borrow.getId());
		bad.setUserId(userId);
		bad.setType(type);
		bad.setMoney(money);
		bad.setIp(ip);
		bad.setRemark(remark);
		bad.setBorrowTotal(new BigDecimal(borrow.getAccount()));
		bad.setBorrowCapitalYes(borrow.getPayedCapital());
		bad.setBorrowInterestYes(borrow.getPayedInterest());
		
		bad.setBorrowCapitalNo(borrow.getRepayCapital());
		bad.setBorrowInterestNo(borrow.getPayedInterest());
		
		return bad;
	}

}
