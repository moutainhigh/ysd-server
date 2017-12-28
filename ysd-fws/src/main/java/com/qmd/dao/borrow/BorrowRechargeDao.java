package com.qmd.dao.borrow;

import com.qmd.dao.BaseDao;
import com.qmd.mode.borrow.BorrowRecharge;
import com.qmd.util.Pager;

import java.util.Map;

public interface BorrowRechargeDao extends BaseDao<BorrowRecharge, Integer> {
	
	public Pager queryPagerBorrowRecharge(Pager page,Map<String,Object> qMap);
}