package com.qmd.service.user;

import com.qmd.mode.user.UserHongbao;
import com.qmd.service.BaseService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserHongbaoService extends BaseService<UserHongbao, Integer> {

	public BigDecimal getSelectSumMoney(Map<String,Object> map);
	
	/**
	 * 查询用户红包列表
	 * @param map
	 * @author zdl
	 */
	public List<net.qmdboss.beans.UserHongbao> queryHBListByMap(Map<String, Object> map);
	/**
	 * 用户未读红包个数
	 * @param userId
	 * @author zdl
	 */
	public Integer queryCountNotLookHB(Integer userId);

	/**
	 * 更新用户未读红包为已读
	 * @param userId
	 * @author zdl
	 */
	public Integer updateHBIsLooked(Integer userId);
	
	public List<net.qmdboss.beans.UserHongbao> queryHbListByMapNew(Map<String,Object> map);
}
