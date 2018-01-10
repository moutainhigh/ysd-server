package com.qmd.dao.user;

import com.qmd.dao.BaseDao;
import com.qmd.mode.user.UserSpreadDetail;

import java.math.BigDecimal;

public interface UserSpreadDetailDao extends BaseDao<UserSpreadDetail, Integer>{

	public BigDecimal getSumMoney( UserSpreadDetail userSpreadDetail);
	
	public BigDecimal getRegisterSumMoney( UserSpreadDetail userSpreadDetail);
}
