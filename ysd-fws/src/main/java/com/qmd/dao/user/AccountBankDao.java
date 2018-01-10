package com.qmd.dao.user;

import com.qmd.dao.BaseDao;
import com.qmd.mode.user.AccountBank;

import java.util.List;

/**
 * 用户银行卡列表
 * @author zhanf
 *
 */
public interface AccountBankDao extends BaseDao<AccountBank, Integer> {

	/**
	 * 查询对应用户的银行卡列表
	 * @param userId
	 * @return
	 */
	public List<AccountBank> getAccountBankList(Integer userId);
	
	public AccountBank getAccountBank(Integer id);
}
