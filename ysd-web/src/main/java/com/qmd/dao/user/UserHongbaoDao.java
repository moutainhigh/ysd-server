package com.qmd.dao.user;

import com.qmd.dao.BaseDao;
import com.qmd.mode.user.UserHongbao;
import com.qmd.mode.util.Hongbao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserHongbaoDao extends BaseDao<UserHongbao, Integer>  {

	public BigDecimal getSelectSumMoney(Map<String,Object> map);
	
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
	
	/**
	 * 通过ID得到系统红包 
	 * @param id
	 * @author zdl
	 */
	public Hongbao getSysHongBaoById(Integer id);
	
}
