package com.qmd.dao.borrow;

import com.qmd.bean.BorrowFangkuanBean;
import com.qmd.dao.BaseDao;
import com.qmd.mode.borrow.Fangkuan;
import com.qmd.util.Pager;

import java.util.Map;

public interface FangkuanDao  extends BaseDao<Fangkuan, Integer> {

	public Pager findBorrowFangkuanPager(Map<String,Object> qMap,Pager pager);
	
	public BorrowFangkuanBean getBorrowFangkuan(Integer id);
}
