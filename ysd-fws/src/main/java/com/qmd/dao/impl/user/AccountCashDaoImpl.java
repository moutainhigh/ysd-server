package com.qmd.dao.impl.user;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.AccountCashDao;
import com.qmd.mode.user.AccountCash;
import com.qmd.util.Pager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 添加银行提现记录
 * @author zhanf
 *
 */
@Repository("accountCashDao")
public class AccountCashDaoImpl extends BaseDaoImpl<AccountCash, Integer>
		implements AccountCashDao {

	@Override
	public Integer save(AccountCash accountCash) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("AccountCash.save", accountCash);
	}

	@Override
	public Pager getAccountCashList(Pager page,Map<String,Object> map) {
		// TODO Auto-generated method stub
		Integer count = (Integer)this.getSqlSession().selectOne("AccountCash.queryAccountCashListCount",map);
		page.setTotalCount(count);
		map.put("start", (page.getPageNumber()-1)*page.getPageSize());
		map.put("end", page.getPageSize());

		List<AccountCash> accountCashList = this.getSqlSession().selectList("AccountCash.queryAccountCashList", map);
		page.setResult(accountCashList);
		return page;
	}
	
	@Override
	public AccountCash get(Integer id){
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("AccountCash.get", id);
	}

	@Override
	public void updateCash(AccountCash accountCash) {
		// TODO Auto-generated method stub
		 this.getSqlSession().update("AccountCash.update", accountCash);
	}

	@Override
	public List<AccountCash> gainCashLish(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<AccountCash> accountCashList = this.getSqlSession().selectList("AccountCash.accountCashList", map);
		return accountCashList;
	}
}
