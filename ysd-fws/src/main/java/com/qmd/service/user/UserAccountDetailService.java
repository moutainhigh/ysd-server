package com.qmd.service.user;


import com.qmd.mode.user.UserAccountDetail;
import com.qmd.service.BaseService;

import java.math.BigDecimal;

public interface UserAccountDetailService extends BaseService<UserAccountDetail, Integer>{

	/**
	 * 查询单个类型操作金额总和
	 * @param userid
	 * @param sign
	 * @return
	 */
	public BigDecimal getSumMoney(Integer userid,String type);
	
	
//	public BigDecimal getSumMoney(Integer userId, String type, Date minDate,
//			Date maxDate);
}
