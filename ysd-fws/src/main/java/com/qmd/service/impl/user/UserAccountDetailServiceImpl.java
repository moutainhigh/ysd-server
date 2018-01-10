package com.qmd.service.impl.user;

import com.qmd.dao.user.UserAccountDetailDao;
import com.qmd.mode.user.UserAccountDetail;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.UserAccountDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service("userAccountDetailService")
public class UserAccountDetailServiceImpl extends
		BaseServiceImpl<UserAccountDetail, Integer> implements
		UserAccountDetailService {

	@Resource
	private UserAccountDetailDao userAccountDetailDao;

	@Resource
	public void setBaseDao(UserAccountDetailDao userAccountDetailDao) {
		super.setBaseDao(userAccountDetailDao);
	}

	public BigDecimal getSumMoney(Integer userid, String type) {
		return userAccountDetailDao.getSumMoney(userid, type);
	}

//	public BigDecimal getSumMoney(Integer userId, String type, Date minDate,
//			Date maxDate) {
//		return userAccountDetailDao.getSumMoney(userId, type, minDate, maxDate);
//	}

}
