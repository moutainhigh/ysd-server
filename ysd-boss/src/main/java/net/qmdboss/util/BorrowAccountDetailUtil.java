package net.qmdboss.util;

import net.qmdboss.entity.Agency;
import net.qmdboss.entity.Borrow;
import net.qmdboss.entity.BorrowAccountDetail;
import net.qmdboss.entity.User;

import java.math.BigDecimal;

public class BorrowAccountDetailUtil {

	public static BorrowAccountDetail fillBorrowAccountDetailUtil(//
			String type,//
			BigDecimal money, //
			User user,//
			Agency agency,//
			String remark,//
			String ip,//
			Borrow borrow//
	) {

		BorrowAccountDetail bad = new BorrowAccountDetail();
		bad.setAgency(agency);
		bad.setBorrow(borrow);
		bad.setUser(user);
		bad.setType(type);
		bad.setMoney(money);
		bad.setIp(ip);
		bad.setRemark(remark);
		bad.setBorrowTotal(borrow.getAccount());
		bad.setBorrowCapitalYes(borrow.getPayedCapital());
		bad.setBorrowInterestYes(borrow.getPayedInterest());
		
		bad.setBorrowCapitalNo(borrow.getRepayCapital());
		bad.setBorrowInterestNo(borrow.getPayedInterest());
		
		return bad;
	}

}
