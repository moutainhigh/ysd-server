package com.qmd.service.impl.user;

import com.qmd.dao.user.UserHongbaoDao;
import com.qmd.mode.user.UserHongbao;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.UserHongbaoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service("userHongbaoService")
public class UserHongbaoServiceImpl extends
BaseServiceImpl<UserHongbao, Integer> implements UserHongbaoService {

	@Resource
	UserHongbaoDao userHongbaoDao;
	
	@Resource
	public void setBaseDao(UserHongbaoDao userHongbaoDao) {
		super.setBaseDao(userHongbaoDao);
	}

	@Override
	public List<net.qmdboss.beans.UserHongbao> queryHBListByMap(
			Map<String, Object> map) {
		return userHongbaoDao.queryHBListByMap(map);
	}
	
	public BigDecimal getSelectSumMoney(Map<String,Object> map){
		return userHongbaoDao.getSelectSumMoney(map);
	}

	@Override
	public Integer queryCountNotLookHB(Integer userId) {
		return userHongbaoDao.queryCountNotLookHB(userId);
	}

	@Override
	public Integer updateHBIsLooked(Integer userId) {
		return userHongbaoDao.updateHBIsLooked(userId);
	}

	public List<net.qmdboss.beans.UserHongbao> queryHbListByMapNew(Map<String,Object> map){
		
		return userHongbaoDao.queryHbListByMapNew(map);
	}
}
