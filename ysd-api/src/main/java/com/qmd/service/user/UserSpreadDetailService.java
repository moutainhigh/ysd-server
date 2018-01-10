package com.qmd.service.user;

import com.qmd.mode.user.UserSpreadDetail;
import com.qmd.service.BaseService;

import java.math.BigDecimal;

public interface UserSpreadDetailService  extends BaseService<UserSpreadDetail, Integer>{

	public BigDecimal getSumMoney( UserSpreadDetail userSpreadDetail);
	
	public BigDecimal getRegisterSumMoney( UserSpreadDetail userSpreadDetail);
	
}
