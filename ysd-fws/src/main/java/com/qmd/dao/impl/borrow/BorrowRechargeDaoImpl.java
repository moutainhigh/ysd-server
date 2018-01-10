package com.qmd.dao.impl.borrow;

import com.qmd.dao.borrow.BorrowRechargeDao;
import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowRecharge;
import com.qmd.util.Pager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("borrowRechargeDao")
public class BorrowRechargeDaoImpl extends BaseDaoImpl<BorrowRecharge, Integer>
		implements BorrowRechargeDao {
	
	
	
	@Override
	public Pager queryPagerBorrowRecharge(Pager page,Map<String,Object> qMap) {
		Integer count = (Integer)this.getSqlSession().selectOne("BorrowRecharge.queryCountByMap", qMap);
		page.setTotalCount(count);
		qMap.put("pageStart", (page.getPageNumber()-1)*page.getPageSize());
		qMap.put("pageSize", page.getPageSize());

		List<Borrow> borrowList = this.getSqlSession().selectList("BorrowRecharge.queryListByMap", qMap);
		page.setResult(borrowList);
		return page;
	}
}