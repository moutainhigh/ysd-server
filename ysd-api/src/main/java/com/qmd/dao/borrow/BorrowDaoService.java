package com.qmd.dao.borrow;

import com.qmd.mode.borrow.Borrow;
import com.qmd.util.Pager;

import java.util.List;
import java.util.Map;

public interface BorrowDaoService {
	public Pager queryBorrowList(Pager page,Map<String,Object> qMap);
	/**
	 * 还款未完成的标(质押标,流转标)
	 * @param page
	 * @param qMap
	 * @return
	 */
	public Pager queryBorrowListForRepay(Pager page,Map<String,Object> qMap);
	public Pager queryUserTenderBorrowList(Pager page,Map<String,Object> qMap);
	public Borrow getBorrowById(Integer id);
	public List<Borrow> queryUserBorrowList(Map<String,Object> map);
	public Borrow getBorrow(Map<String,Object> map);
	
	public Double queryBorrowAccountSum(Map<String, Object> map);
	
	public List<Borrow> queryBorrow();
	
	public Borrow queryByID(String id);
	
	public void update(Borrow borrow);
	
	public void add(Borrow borrow);
	
	public void insertBorrow(Borrow borrow);
	/**
	 * 删除标
	 * @param id
	 */
	public void delect(Integer id);
	/**
	 * 锁定标 
	 */
	public Borrow getForUpdate(Integer id);
	
	public void updateParent(Borrow borrow);
	
	public Pager queryBorrowListCopy(Pager page,Map<String,Object> qMap);
}
