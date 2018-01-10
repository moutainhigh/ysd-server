package com.qmd.dao.user;


import com.qmd.dao.BaseDao;
import com.qmd.mode.user.UserAccountDetail;

import java.math.BigDecimal;
import java.util.Date;

public interface UserAccountDetailDao extends BaseDao<UserAccountDetail,Integer> {
	
	
	/**
	 * 查询单个类型操作金额总和
	 * @param userid
	 * @param sign
	 * @return
	 */
	public BigDecimal getSumMoney(Integer userid,String type);
	
	public BigDecimal getSumMoney(Integer userId,String type,Date minDate,Date maxDate);
	
	
	public void update(UserAccountDetail userAccountDetail);
	
}
