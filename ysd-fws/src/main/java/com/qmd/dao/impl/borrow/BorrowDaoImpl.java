package com.qmd.dao.impl.borrow;

import com.qmd.dao.borrow.BorrowDaoService;
import com.qmd.mode.borrow.Borrow;
import com.qmd.util.Pager;
import com.qmd.util.bean.ProductBean;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository("borrowDaoService")
/**
 * BorrowDaoImpl 标DAO层实现代码
 * @author Administrator
 *
 */
public class BorrowDaoImpl extends SqlSessionDaoSupport implements BorrowDaoService {

	@Override
	public Pager queryBorrowList(Pager page,Map<String,Object> qMap) {
		// TODO Auto-generated method stub
		Integer count = (Integer)this.getSqlSession().selectOne("Borrow.queryBorrowListCount", qMap);
		page.setTotalCount(count);
		qMap.put("start", (page.getPageNumber()-1)*page.getPageSize());
		qMap.put("end", page.getPageSize());

		List<Borrow> borrowList = this.getSqlSession().selectList("Borrow.queryBorrowList", qMap);
		page.setResult(borrowList);
		return page;
	}
	
	@Override
	public Pager queryBorrowListForRepay(Pager page,Map<String,Object> qMap) {
		// TODO Auto-generated method stub
		Integer count = (Integer)this.getSqlSession().selectOne("Borrow.queryBorrowListCountForRepay", qMap);
		page.setTotalCount(count);
		qMap.put("start", (page.getPageNumber()-1)*page.getPageSize());
		qMap.put("end", page.getPageSize());

		List<Borrow> borrowList = this.getSqlSession().selectList("Borrow.queryBorrowListForRepay", qMap);
		page.setResult(borrowList);
		return page;
	}
	
	@Override
	public Pager queryUserTenderBorrowList(Pager page,Map<String,Object> qMap) {
		// TODO Auto-generated method stub
		Integer count = (Integer)this.getSqlSession().selectOne("Borrow.queryUserTenderBorrowListCount", qMap);
		page.setTotalCount(count);
		qMap.put("start", (page.getPageNumber()-1)*page.getPageSize());
		qMap.put("end", page.getPageSize());
		List<Borrow> borrowList = this.getSqlSession().selectList("Borrow.queryUserTenderBorrowList", qMap);
		page.setResult(borrowList);
		return page;
	}
	@Override
	public Borrow getBorrowById(Integer id) {
		// TODO Auto-generated method stub
		Borrow borrow = this.getSqlSession().selectOne("Borrow.getBorrowById", id);
		return borrow;
	}
	@Override
	public Borrow getBorrow(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Borrow> queryUserBorrowList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("Borrow.queryUserBorrowList", map);
	}
	
	@Override
	public Double queryBorrowAccountSum(Map<String, Object> map) {
		return (Double)this.getSqlSession().selectOne("Borrow.queryBorrowAccountSum", map);
	}
	
	@Override
	public List<Borrow> queryBorrow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Borrow queryByID(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Borrow borrow) {
		// TODO Auto-generated method stub
		this.getSqlSession().update("Borrow.updateborr", borrow);
	}
	
	@Override
	public void updateBorrow(Borrow borrow) {
		// TODO Auto-generated method stub
		this.getSqlSession().update("Borrow.updateBorrow", borrow);
	}
	
	

	@Override
	public Integer add(Borrow borrow) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("Borrow.addBor", borrow);
	}
	
	@Override
	public void insertBorrow(Borrow borrow) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert("Borrow.Borrow_insert", borrow);
	}
	@Override
	public void delect(Integer id) {
		// TODO Auto-generated method stub
		
		this.getSqlSession().delete("Borrow.delectborr", id);
	}
	
	@Override
	public Borrow getForUpdate(Integer id) {
		return this.getSqlSession().selectOne("Borrow.getForUpdate", id);
	}
	

	public List<ProductBean> getProductBeanList(Map<String,Object> map){
		return this.getSqlSession().selectList("Borrow.getProductBeanList",map);
	}


	public Pager queryReservationList(Pager page,Map<String,Object> qMap){
		// TODO Auto-generated method stub
		Integer count = (Integer)this.getSqlSession().selectOne("Borrow.queryReservationCount", qMap);
		page.setTotalCount(count);
		qMap.put("start", (page.getPageNumber()-1)*page.getPageSize());
		qMap.put("end", page.getPageSize());
		List<Borrow> borrowList = this.getSqlSession().selectList("Borrow.queryReservationList", qMap);
		page.setResult(borrowList);
		return page;
		
	}

	@Override
	public List<Borrow> queryZqzrBorrowList(Map<String, Object> map) {
		return this.getSqlSession().selectList("Borrow.queryZqzrBorrowList", map);
	}
	
	@Override
	public Pager queryBorrowListCopy(Pager page,Map<String,Object> qMap) {
		Integer count = (Integer)this.getSqlSession().selectOne("Borrow.queryBorrowListCount", qMap);
		page.setTotalCount(count);
		qMap.put("start", (page.getPageNumber()-1)*page.getPageSize());
		qMap.put("end", page.getPageSize());

		List<Borrow> borrowList = this.getSqlSession().selectList("Borrow.queryBorrowListCopy", qMap);
		page.setResult(borrowList);
		return page;
	}
	
	@Override
	public Pager queryBorrowList2(Pager page,Map<String,Object> qMap) {
		// TODO Auto-generated method stub
		Integer count = (Integer)this.getSqlSession().selectOne("Borrow.queryBorrowListCount2", qMap);
		page.setTotalCount(count);
		qMap.put("start", (page.getPageNumber()-1)*page.getPageSize());
		qMap.put("end", page.getPageSize());

		List<Borrow> borrowList = this.getSqlSession().selectList("Borrow.queryBorrowList2", qMap);
		page.setResult(borrowList);
		return page;
	}
}
