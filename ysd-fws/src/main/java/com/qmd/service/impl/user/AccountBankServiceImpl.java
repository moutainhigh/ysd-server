package com.qmd.service.impl.user;

import com.qmd.dao.user.AccountBankDao;
import com.qmd.mode.user.AccountBank;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.AccountBankService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 查询用户银行卡列表Service实现类
 * @author zhanf
 *
 */
@Service("accountBankService")
public class AccountBankServiceImpl extends
		BaseServiceImpl<AccountBank, Integer> implements AccountBankService {
	@Resource
	AccountBankDao accountBankDao;
	
	@Resource
	public void setBaseDao(AccountBankDao accountBankDao) {
		super.setBaseDao(accountBankDao);
	}

//	@Override
//	public List<AccountBank> getAccountBankList(Integer userId) {
//		// TODO Auto-generated method stub
//		return this.accountBankDao.getAccountBankList(userId);
//	}

	@Override
	public AccountBank getAccountBank(Integer id) {
		// TODO Auto-generated method stub
		return this.accountBankDao.getAccountBank(id);
	}

}
