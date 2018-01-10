package com.qmd.dao.user;

import com.qmd.dao.BaseDao;
import com.qmd.mode.user.UserHongbao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserHongbaoDao extends BaseDao<UserHongbao, Integer>  {

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
	
	
	
	/**
	 * 更新红包
	 * @param 
	 * @author qiu
	 */
	public Integer updateNewHongbao(net.qmdboss.beans.UserHongbao userhongbaoN);
	
	/**
	 * 保存新红包
	 * @param 
	 * @author zdl
	 */
	public Integer saveNewHongbao(net.qmdboss.beans.UserHongbao userhongbaoN);
	
	
	public List<net.qmdboss.beans.UserHongbao> queryHbListByMapNew(Map<String,Object> map);
	
	
	/**
	 * qiu 根据红包id组 获取红包所有满的上限;
	 * @param map
	 * @return
	 */
	public BigDecimal getSumHbInvestFullMomey(Map<String,Object> map);
	
	
	/**
	 * 获取单个新红包
	 * @param map
	 * @return
	 */
	public net.qmdboss.beans.UserHongbao getNewHbById(Integer userId);
	
	
	
	
	
	
}
