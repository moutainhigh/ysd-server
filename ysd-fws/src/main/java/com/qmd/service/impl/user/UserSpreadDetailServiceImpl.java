package com.qmd.service.impl.user;

import com.qmd.mode.user.UserSpreadDetail;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.UserSpreadDetailService;
import org.springframework.stereotype.Service;

@Service("userSpreadDetailService")
public class UserSpreadDetailServiceImpl extends
		BaseServiceImpl<UserSpreadDetail, Integer> implements
		UserSpreadDetailService {

//	@Resource
//	UserSpreadDetailDao userSpreadDetailDao;
//
//	@Resource
//	public void setBaseDao(UserSpreadDetailDao userSpreadDetailDao) {
//		super.setBaseDao(userSpreadDetailDao);
//	}
//
//	public BigDecimal getSumMoney( UserSpreadDetail userSpreadDetail){
//		return userSpreadDetailDao.getSumMoney(userSpreadDetail);
//	}
//	
//
//	public BigDecimal getRegisterSumMoney( UserSpreadDetail userSpreadDetail){
//		return userSpreadDetailDao.getRegisterSumMoney(userSpreadDetail);
//	}
}
