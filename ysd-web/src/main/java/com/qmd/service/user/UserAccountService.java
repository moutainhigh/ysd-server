package com.qmd.service.user;


import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountDetail;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.service.BaseService;
import com.qmd.util.Pager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserAccountService extends BaseService<UserAccount, Integer>{

	/**
	 * 注册添加用户账户总表
	 * @param user
	 */
	public void addUserAccount(UserAccount userAccount);
	
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
	 * @param map 【userId】
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
	
	/**
	 * 查询用户资金单笔详情
	 * @param map 【userId】【id】
	 * @return
	 */
	public UserAccountDetail getAccountDetailByUserIdSingle(Map<String,Object> map);
	
	/**
	 * 获取用户资金记录列表
	 * @param map
	 * @return
	 */
	public List<UserAccountDetail> getAccountDetailByUserIdList(Map<String,Object> map);
	
	public List<UserAccount> getUserAccountAllRecodeList();
	
	/**
	 * 转出续投宝金额
	 * @param id
	 * @param rollTotal
	 * @return
	 */
	public boolean rollOutMoney(Integer id,BigDecimal rollTotal,String ip);
	
}
