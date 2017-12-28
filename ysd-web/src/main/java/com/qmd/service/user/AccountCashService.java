package com.qmd.service.user;

import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.AccountCash;
import com.qmd.mode.user.User;
import com.qmd.service.BaseService;
import com.qmd.util.Pager;

import java.util.List;
import java.util.Map;

/**
 * 提现记录Service接口类
 * @author zhanf
 *
 */
public interface AccountCashService extends BaseService<AccountCash, Integer> {
	
	/**
	 * 提现插入记录和冻结提现资金
	 */
	public  AccountCash cashMoney(User user, AccountCash  accountCash,AccountBank accountBank,String cashType,String ip);
	
	/**
	 * 获取提现对象值
	 */
	public AccountCash get(Integer id);
	
	/**
	 * 获取提现记录列表
	 * @param page 
	 */
	public Pager getCashList(Pager page, Map<String,Object> map);
	
	/**
	 * 撤回提现金额
	 */
	
	public void updateCash(AccountCash accountCash ,Integer userId);
	
	/**
	 * 获取提现列表
	 */
	
	public List<AccountCash> gainCashLish(Map<String,Object> map);
	
	public Integer queryAccountCashListCount(Map<String,Object> map);
}
