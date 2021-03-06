package com.qmd.util;

import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountDetail;

import java.math.BigDecimal;
import java.util.Date;

public class AccountDetailUtil {

	/**
	 * 
	 * @param type
	 *            类型
	 * @param money
	 *            操作金额
	 * @param toUserId
	 *            对方用户ＩＤ
	 * @param remark
	 *            备注
	 * @param ip
	 *            IP地址
	 * @param userAccount
	 *            账户信息
	 * @return
	 */
	public static UserAccountDetail fillUserAccountDetail(//
			String type,//
			BigDecimal money, //
			Integer toUserId,//
			String remark,//
			String ip,//
			UserAccount userAccount//
	) {


		UserAccountDetail userAccountDetail = new UserAccountDetail();

		userAccountDetail.setType(type);
		userAccountDetail.setMoney(money);
		userAccountDetail.setToUser(toUserId);
		userAccountDetail.setRemark(remark);
		userAccountDetail.setOperatorIp(ip);
		userAccountDetail.setAddTime(new Date());
		userAccountDetail.setUserId(userAccount.getUserId());
		userAccountDetail.setTotal(userAccount.getTotal());
		userAccountDetail.setUseMoney(userAccount.getAbleMoney());
		userAccountDetail.setNoUseMoney(userAccount.getUnableMoney());
		userAccountDetail.setInvestorCollectionCapital(userAccount
				.getInvestorCollectionCapital());
		userAccountDetail.setInvestorCollectionInterest(userAccount
				.getInvestorCollectionInterest());
		userAccountDetail.setBorrowerCollectionCapital(userAccount
				.getBorrowerCollectionCapital());
		userAccountDetail.setBorrowerCollectionInterest(userAccount
				.getBorrowerCollectionInterest());
		userAccountDetail.setContinueTotal(userAccount.getContinueTotal());
		userAccountDetail.setAwardMoney(userAccount.getAwardMoney());
		return userAccountDetail;
	}
	
}
