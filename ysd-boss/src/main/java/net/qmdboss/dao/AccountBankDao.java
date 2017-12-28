package net.qmdboss.dao;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.AccountBank;

import java.util.Map;

public interface AccountBankDao extends BaseDao<AccountBank,Integer> {
	
	public Pager findPagerByMap(Pager pager,Map<String, Object> map);
}