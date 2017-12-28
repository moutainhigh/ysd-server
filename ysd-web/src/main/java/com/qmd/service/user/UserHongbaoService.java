package com.qmd.service.user;

import com.qmd.mode.user.UserHongbao;
import com.qmd.service.BaseService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserHongbaoService extends BaseService<UserHongbao, Integer> {

	public BigDecimal getSelectSumMoney(Map<String,Object> map);
	
	
	public List<net.qmdboss.beans.UserHongbao> queryHbListByMapNew(Map<String,Object> map);
	
	/**
	 * 投资额为X时可得到的土豪奖金额
	 * @author zdl
	 */
	public BigDecimal getThMoneyCurrentMostInvest(BigDecimal most);
	
	/**
	 * 得到最大截标红包金额
	 * @author zdl
	 */
	public BigDecimal getJbMoneyMost();
}
