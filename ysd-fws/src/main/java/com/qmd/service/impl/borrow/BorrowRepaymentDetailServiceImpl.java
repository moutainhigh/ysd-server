package com.qmd.service.impl.borrow;

import com.qmd.dao.borrow.BorrowRepaymentDetailDaoService;
import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.service.borrow.BorrowRepaymentDetailService;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.util.Pager;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository("borrowRepaymentDetailService")
public class BorrowRepaymentDetailServiceImpl extends BaseServiceImpl<BorrowRepaymentDetail, Integer>
		implements BorrowRepaymentDetailService {
	@Resource
	BorrowRepaymentDetailDaoService borrowRepaymentDetailDaoService;
	
	

	@Override
	public List<BorrowRepaymentDetail> queryUserBorrowList(
			Integer id) {
		// TODO Auto-generated method stub
		
		return borrowRepaymentDetailDaoService.queryUserBorrowList(id);
	}
	@Override
	public BorrowRepaymentDetail get(Integer id) {
		// TODO Auto-generated method stub
		return borrowRepaymentDetailDaoService.get(id);
	}

	
//	public Pager queryUncollectedDetailList(Pager pager,Map<String,Object> map){
//		return borrowRepaymentDetailDaoService.queryUncollectedDetailList(pager, map);
//	}
//	public List<BorrowRepaymentDetail> getBorrowRepaymentDetailList(Map<String,Object> map){
//		return borrowRepaymentDetailDaoService.getBorrowRepaymentDetailList(map);
//	}
	
	public Pager queryBorrowerDetailList(Pager pager,Map<String,Object> map){
		return borrowRepaymentDetailDaoService.queryBorrowerDetailList(pager, map);
	}
	
//	public List<BorrowRepaymentDetail> getBorrowerDetailList(Map<String,Object> map){
//		return borrowRepaymentDetailDaoService.getBorrowerDetailList(map);
//	}
	
//	public List<BorrowRepaymentDetail> queryBorrowRepaymentList(Map<String,Object> map) {
//		return borrowRepaymentDetailDaoService.queryBorrowRepaymentList(map);
//	}
	@Override
	public BorrowRepaymentDetail queryBorrowRepayment(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return borrowRepaymentDetailDaoService.queryBorrowRepayment(map);
	}
	
	@Override
	public List<BorrowRepaymentDetail> queryBorrowRepaymentList(Map<String,Object> map) {
		return borrowRepaymentDetailDaoService.queryBorrowRepaymentList(map);
	}

	public Integer queryRepaymentNotCount(Map<String,Object> map)  {
		return borrowRepaymentDetailDaoService.queryRepaymentNotCount(map);
	}
}
