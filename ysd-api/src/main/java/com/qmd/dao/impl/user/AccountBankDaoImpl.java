package com.qmd.dao.impl.user;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.AccountBankDao;
import com.qmd.mode.user.AccountBank;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作用户银行列表
 * @author zhanf
 *
 */
@Repository("accountBankDao")
public class AccountBankDaoImpl extends BaseDaoImpl<AccountBank, Integer>
		implements AccountBankDao {

	/**
	 * 查询对应用户的银行卡列表
	 * @param userId
	 * @return
	 */
	@Override
	public List<AccountBank> getAccountBankList(Integer userId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("AccountBank.getAccountBankList", userId);
	}

	@Override
	public AccountBank getAccountBank(Integer id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("AccountBank.getAccountBank", id);
	}

}
