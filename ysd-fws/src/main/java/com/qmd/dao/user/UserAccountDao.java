package com.qmd.dao.user;


import com.qmd.dao.BaseDao;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.util.Pager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserAccountDao extends BaseDao<UserAccount,Integer> {
	
	/**
	 * 注册添加用户账户总表
	 * @param user
	 */
	public void addUserAccount(UserAccount userAccount);
	
	public void updateAll(UserAccount userAccount);
	

	/**
	 * 查询用户账户
	 * @param userId
	 * @return
	 */
	public UserAccount getUserAccountByUserId(Integer userId);
	

	/**
	 * 查询最新充值记录-首页展示
	 * @param max 条数
	 * @return
	 */
	public List<UserAccountRecharge> getAccountRechargeByNew(Integer maxs);
	
	/**
	 * 查询用户充值记录
	 * @param pager
	 * @param map 【userId min max】
	 * @return
	 */
	public Pager getAccountRechargeByUserId(Pager pager,Map<String,Object> map);
	/**
	 * 获取用户充值列表
	 * @param map
	 * @return
	 */
	public List<UserAccountRecharge> getUserAccountRechargeList(Map<String,Object> map);

	/**
	 * 查询用户资金记录
	 * @param pager 
	 * @param map 【userId】
	 * @return
	 */
	public Pager getAccountDetailByUserId(Pager pager,Map<String,Object> map);
	
	
	public List<UserAccount> getUserAccountAllRecodeList();
	

	public BigDecimal getSumDepositMoney(Map<String,Object> map);
}
