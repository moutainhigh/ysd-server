package com.qmd.dao.impl.borrow;

import com.qmd.bean.BorrowFangkuanBean;
import com.qmd.dao.borrow.FangkuanDao;
import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.mode.borrow.Fangkuan;
import com.qmd.util.Pager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("fangkuanDao")
public class FangkuanDaoImpl  extends BaseDaoImpl<Fangkuan, Integer> implements FangkuanDao{


	public Pager findBorrowFangkuanPager(Map<String,Object> qMap,Pager pager){
		
		Integer count = (Integer)this.getSqlSession().selectOne("Fangkuan.queryBorrowListCount", qMap);
		pager.setTotalCount(count);
		qMap.put("pageStart", (pager.getPageNumber()-1)*pager.getPageSize());
		qMap.put("pageSize", pager.getPageSize());
		List<BorrowFangkuanBean> borrowList = this.getSqlSession().selectList("Fangkuan.queryBorrowList", qMap);
		pager.setResult(borrowList);
		return pager;
	}
	
	public BorrowFangkuanBean getBorrowFangkuan(Integer id){
		return this.getSqlSession().selectOne(getClassNameSpace() + ".getBorrowFangkuan", id);
	}
}
