package net.qmdboss.service;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.AccountBank;

import java.util.Map;

public interface AccountBankService extends BaseService<AccountBank, Integer> {
	
	public Pager findPagerByMap(Pager pager,Map<String, Object> map);
}