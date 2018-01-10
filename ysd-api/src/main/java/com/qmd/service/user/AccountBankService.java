package com.qmd.service.user;

import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.service.BaseService;

import java.util.List;

/**
 * 操作用户提交的银行卡列表Service接口类
 * @author zhanf
 *
 */
public interface AccountBankService extends BaseService<AccountBank, Integer> {

	/**
	 * 通过用户ID查询银行账户列表
	 * @param userId
	 * @return
	 */
	public List<AccountBank> getAccountBankList(Integer userId);
	
	/**
	 * 通过ID查询银行账号等内容
	 * @param id
	 * @return
	 */
	public AccountBank getAccountBank(Integer id);
	
	
	public int insertBankInfo(User user,AccountBank bank);
}
