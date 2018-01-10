package com.qmd.dao.impl.user;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.UserSpreadDetailDao;
import com.qmd.mode.user.UserSpreadDetail;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("userSpreadDetailDao")
public class UserSpreadDetailDaoImpl extends BaseDaoImpl<UserSpreadDetail, Integer>
		implements UserSpreadDetailDao {

	public BigDecimal getSumMoney( UserSpreadDetail userSpreadDetail){
		return this.getSqlSession().selectOne("UserSpreadDetail.sumMoney", userSpreadDetail);
	}
	
	public BigDecimal getRegisterSumMoney( UserSpreadDetail userSpreadDetail){
		return this.getSqlSession().selectOne("UserSpreadDetail.registerSumMoney", userSpreadDetail);
	}
}
