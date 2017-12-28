package com.qmd.dao.user;

import com.qmd.dao.BaseDao;
import com.qmd.mode.user.AccountCash;
import com.qmd.util.Pager;

import java.util.List;
import java.util.Map;

public interface AccountCashDao extends BaseDao<AccountCash, Integer> {

	public Integer save(AccountCash accountCash);
	
	
	public Pager getAccountCashList(Pager page, Map<String,Object> map);
	
	public AccountCash get(Integer id);
	
	/**
	 * 修改还款记录
	 * @param accountCash
	 */
	public void updateCash(AccountCash accountCash);
	
	/**
	 * 获取提现列表
	 */
	public List<AccountCash> gainCashLish(Map<String, Object> map);
	
	/**
	 * 提现次数
	 * @param map
	 * @return
	 */
	public Integer queryAccountCashListCount(Map<String,Object> map);
}
