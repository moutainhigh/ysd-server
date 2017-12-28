package com.qmd.dao.impl.borrow;

import com.qmd.dao.borrow.BorrowRepaymentDetailDaoService;
import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.util.Pager;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * BorrowRepaymentDetailDaoImpl 还款详细DAO层实现代码
 * @author Administrator
 *
 */
@Repository("borrowRepaymentDetailDaoService")
public class BorrowRepaymentDetailDaoImpl extends BaseDaoImpl<BorrowRepaymentDetail,Integer>
		implements BorrowRepaymentDetailDaoService {
	@Resource
	BorrowRepaymentDetailDaoService borrowRepaymentDetailDaoService;
	@Override
	public List<BorrowRepaymentDetail> queryUserBorrowList(
			Integer id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("BorrowRepaymentDetail.queryBorrowRepList", id);
	}

	@Override
	public BorrowRepaymentDetail get(Integer id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("BorrowRepaymentDetail.select", id);
	}

	public Pager queryUncollectedDetailList(Pager pager,Map<String,Object> map){
		Integer count =Integer.parseInt(this.getSqlSession().selectOne("BorrowRepaymentDetail.queryUncollectedDetailList",map).toString()) ;
		pager.setTotalCount(count);
		map.put("pager", pager);
		List<BorrowRepaymentDetail> bList = getBorrowRepaymentDetailList(map);
		pager.setResult(bList);
		return pager;
	}

	public List<BorrowRepaymentDetail> getBorrowRepaymentDetailList(Map<String,Object> map){
		List<BorrowRepaymentDetail> bList= this.getSqlSession().selectList("BorrowRepaymentDetail.getBorrowRepaymentDetailList",map);
		return bList;
	}
	
	public Pager queryBorrowerDetailList(Pager pager,Map<String,Object> map){
		Integer count =Integer.parseInt(this.getSqlSession().selectOne("BorrowRepaymentDetail.queryHkmxList",map).toString()) ;
		pager.setTotalCount(count);
		map.put("pager", pager);
		List<BorrowRepaymentDetail> bList = getBorrowerDetailList(map);
		pager.setResult(bList);
		return pager;
	}
	
	public List<BorrowRepaymentDetail> getBorrowerDetailList(Map<String,Object> map){
		List<BorrowRepaymentDetail> bList= this.getSqlSession().selectList("BorrowRepaymentDetail.getBorrowHkmxList",map);
		return bList;
	}
	
	public List<BorrowRepaymentDetail> queryBorrowRepaymentList(Map<String,Object> map) {
		
		return this.getSqlSession().selectList("BorrowRepaymentDetail.queryBorrowRepaymentDetailList",map);
	}
	
	@Override
	public BorrowRepaymentDetail load(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BorrowRepaymentDetail> getAllList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getTotalCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer save(BorrowRepaymentDetail entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(BorrowRepaymentDetail borrowRepaymentDetail) {
		// TODO Auto-generated method stub
		this.getSqlSession().update("BorrowRepaymentDetail.update", borrowRepaymentDetail);
	}

	@Override
	public void delete(BorrowRepaymentDetail entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void evict(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BorrowRepaymentDetail queryBorrowRepayment(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("BorrowRepaymentDetail.queryBorrowRepaymentDetail", map);
	}

	public Integer queryRepaymentNotCount(Map<String,Object> map) {
		return this.getSqlSession().selectOne("BorrowRepaymentDetail.queryRepaymentNotCount",map);
	}
	
	public Integer queryRepaymentNextCount(Map<String,Object> map) {
		return this.getSqlSession().selectOne("BorrowRepaymentDetail.queryRepaymentNextCount",map);
	}
	
	
}
