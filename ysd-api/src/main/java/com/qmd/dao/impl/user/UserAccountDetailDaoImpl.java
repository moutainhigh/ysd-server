package com.qmd.dao.impl.user;


import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.UserAccountDetailDao;
import com.qmd.mode.user.UserAccountDetail;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository("userAccountDetailDao")
public class UserAccountDetailDaoImpl extends BaseDaoImpl<UserAccountDetail,Integer> implements UserAccountDetailDao {
	
	@Override
	public Integer save(UserAccountDetail userAccountDetail){
		if(userAccountDetail != null && userAccountDetail.getMoney() != null && userAccountDetail.getMoney().compareTo(BigDecimal.ZERO) >0){
			this.getSqlSession().insert("UserAccountDetail.insert",userAccountDetail);
		}
		return 1;
	}
	
	public BigDecimal getSumMoney(Integer userId,String type){
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("type", type);
		BigDecimal sum = this.getSqlSession().selectOne("UserAccountDetail.selectSumMoneyBySign", map);
		return sum==null?new BigDecimal(0):sum;
	}
	
	public BigDecimal getSumMoney(Integer userId,String type,Date minDate,Date maxDate){
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("type", type);
		map.put("minDate", minDate);
		map.put("maxDate", maxDate);
		
		BigDecimal sum = this.getSqlSession().selectOne("UserAccountDetail.selectSumMoneyBySignDate", map);
		return sum==null?new BigDecimal(0):sum;
	}
	
	public void update(UserAccountDetail userAccountDetail){
		this.getSqlSession().insert("UserAccountDetail.insert", userAccountDetail);
	}
}
