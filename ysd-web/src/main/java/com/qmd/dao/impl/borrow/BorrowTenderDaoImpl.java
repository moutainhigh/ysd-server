package com.qmd.dao.impl.borrow;

import com.qmd.dao.borrow.BorrowTenderDaoService;
import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.mode.amount.Amount;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.util.Pager;
import org.springframework.stereotype.Repository;

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
	public Amount selectAllrepaymentYesinterestByUserid(Map<String,Object> map){
		return this.getSqlSession().selectOne("BorrowTender.selectAllDetailByUserid", map);
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

	public List<BorrowTender> getTenderDetailByUserid(Map<String, Object> map){
		return this.getSqlSession().selectList("BorrowTender.getTenderDetailByUserid", map);
	}
	
	public Pager queryUncollectedDetailList(Pager pager,Map<String,Object> map){
		Integer count =Integer.parseInt(this.getSqlSession().selectOne("BorrowTender.queryTenderDetailList",map).toString()) ;
		pager.setTotalCount(count);
		map.put("pager", pager);
		List<BorrowTender> bList = getTenderDetailByUserid(map);
		pager.setResult(bList);
		return pager;
	}
	
	public List<BorrowTender> getJkmxByUserid(Map<String, Object> map){
		return this.getSqlSession().selectList("BorrowTender.getJkmxUserid", map);
	}
	
	public Pager queryJkmxList(Pager pager,Map<String,Object> map){
		Integer count =Integer.parseInt(this.getSqlSession().selectOne("BorrowTender.queryJkmxList",map).toString()) ;
		pager.setTotalCount(count);
		map.put("pager", pager);
		List<BorrowTender> bList = getJkmxByUserid(map);
		pager.setResult(bList);
		return pager;
	}
	
	public List<BorrowTender> getTenderDetailByMaxMoney(Map<String, Object> map){
		return this.getSqlSession().selectList("BorrowTender.getTenderDetailByMaxMoney", map);
	}

	@Override
	public Pager queryPagerListByMap(Pager pager, Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer count =Integer.parseInt(this.getSqlSession().selectOne("BorrowTender.queryPagerListCount",map).toString()) ;
		pager.setTotalCount(count);
		map.put("pager", pager);
		List<BorrowTender> bList = getTenderDetailPagerByMap(map);
		pager.setResult(bList);
		return pager;
	}
	
	public List<BorrowTender> getTenderDetailPagerByMap(Map<String, Object> map){
		return this.getSqlSession().selectList("BorrowTender.queryPagerListByMap", map);
	}
	
}
