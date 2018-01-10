package com.qmd.dao.impl.borrow;

import com.qmd.dao.borrow.BorrowTenderDaoService;
import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.mode.borrow.BorrowTemp;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.util.Pager;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository("borrowTenderDaoService")
public class BorrowTenderDaoImpl extends BaseDaoImpl<BorrowTender, Integer> implements BorrowTenderDaoService {

	@Override
	public List<BorrowTender> getBorrowTenderByBorrowId(Integer borrow_id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("BorrowTender.queryBorrowTenderList", borrow_id);
	}
	
	@Override
	public Object getBorrowDetailById(Integer borrowDetailId) {
		return this.getSqlSession().selectOne("BorrowTender.queryBorrowTenderById", borrowDetailId);
	}

	@Override
	public Object updateBorrowDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("BorrowTender.upateBorrowDetail", map);
	}
	
	public int updateBorrowTemp(BorrowTemp borrowTemp) {
		return this.getSqlSession().update("BorrowTender.updateBorrowTemp", borrowTemp);
	}
	
	@Override
	public Object upateBorrowDetailAgile(Map<String, Object> map) {
		return this.getSqlSession().update("BorrowTender.upateBorrowDetailAgile", map);
	}

	public List<BorrowTender> getTenderDetailByUserid(Map<String, Object> map){
		return this.getSqlSession().selectList("BorrowTender.getTenderDetailByUserid", map);
	}
	
	public Integer queryTenderDetailList(Map<String,Object> map) {
		return Integer.parseInt(this.getSqlSession().selectOne("BorrowTender.queryTenderDetailList",map).toString()) ;
	}
	
	public Pager queryUncollectedDetailList(Pager pager,Map<String,Object> map){
		Integer count =Integer.parseInt(this.getSqlSession().selectOne("BorrowTender.queryTenderDetailList",map).toString()) ;
		pager.setTotalCount(count);
		map.put("pager", pager);
		List<BorrowTender> bList = getTenderDetailByUserid(map);
		pager.setResult(bList);
		return pager;
	}
	
	public List<BorrowTemp> getJkmxByUserid(Map<String, Object> map){
		return this.getSqlSession().selectList("BorrowTender.getJkmxUserid", map);
	}
	
	public Pager queryJkmxList(Pager pager,Map<String,Object> map){
		Integer count =Integer.parseInt(this.getSqlSession().selectOne("BorrowTender.queryJkmxList",map).toString()) ;
		pager.setTotalCount(count);
		map.put("pager", pager);
		List<BorrowTemp> bList = getJkmxByUserid(map);
		pager.setResult(bList);
		return pager;
	}
	
	public BigDecimal querySumTenderMoney(Map<String,Object> map) {
		BigDecimal sum = this.getSqlSession().selectOne("BorrowTender.querySumTenderMoney", map);
		return sum==null?BigDecimal.ZERO:sum;
	}
	

	
	public List<BorrowTender> getZqList(Map<String,Object> map){
		return this.getSqlSession().selectList("BorrowTender.queryZqList", map);
	}
	
	
	public Pager queryZqList(Pager pager,Map<String,Object> map){
		
		Integer count =Integer.parseInt(this.getSqlSession().selectOne("BorrowTender.queryZqListCount",map).toString()) ;
		pager.setTotalCount(count);
		map.put("pager", pager);
		List<BorrowTender> bList = getZqList(map);
		pager.setResult(bList);
		return pager;
	}
	

	public int getDeferApplyNum(Map<String,Object> map){
		Integer count = this.getSqlSession().selectOne("BorrowTender.queryDeferApplyNum",map) ;
		return count == null ? 0 : count ;
	}
	
	/**
	 * 获取用户的投标次数
	 */
	public Integer queryTenderCount(Map<String,Object> map) {
		return Integer.parseInt(this.getSqlSession().selectOne("BorrowTender.queryTenderCount",map).toString()) ;
	}
}
