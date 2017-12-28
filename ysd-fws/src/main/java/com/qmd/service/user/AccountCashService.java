package com.qmd.service.user;

import com.qmd.mode.borrow.Borrow;
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
//	public  AccountCash cashMoney(User user, AccountCash  accountCash,AccountBank accountBank,String cashType,String ip);
	
	/**
	 * 服务商 【月标】放款
	 * @param user
	 * @param accountCash
	 * @param accountBank
	 * @param cashType
	 * @param ip
	 * @return
	 */
	public  AccountCash cashMoneyByAgencyMonth(User user, AccountCash accountCash,Borrow borrow,AccountBank accountBank,String cashType,String ip);
	/**
	 * 服务商 【流转标】放款
	 * @param user
	 * @param accountCash
	 * @param accountBank
	 * @param cashType
	 * @param ip
	 * @return
	 */
	public  AccountCash cashMoneyByAgencyFlow(User user, AccountCash accountCash,Borrow borrow,AccountBank accountBank,String cashType,String ip);
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
	 * 获取提现列表
	 */
	
	public List<AccountCash> gainCashLish(Map<String,Object> map);
	
	
	/**
	 * 服务商 【撤销放款】
	 * @param ac
	 * @param userId 服务商userId
	 */
	public int reCallCashByAgency(Integer id);
}
