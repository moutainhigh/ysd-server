package com.qmd.service.borrow;

import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.service.BaseService;
import com.qmd.util.Pager;

import java.util.List;
import java.util.Map;

/**
 * BorrowRepaymentDetailService 投标还款记录Service借口
 * @author Administrator
 *
 */
public interface BorrowRepaymentDetailService extends BaseService<BorrowRepaymentDetail,Integer> {
	
	public List<BorrowRepaymentDetail> queryUserBorrowList(Integer id);
	public BorrowRepaymentDetail get(Integer id);
	
	/**
	 * 
	 * 未收明细-分页
	 *
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager queryUncollectedDetailList(Pager pager,Map<String,Object> map);
	
	/**
	 * 未收明细列表
	 * @param map
	 * @return
	 */
	public List<BorrowRepaymentDetail> getBorrowRepaymentDetailList(Map<String,Object> map);
	
	
	/**
	 * 借款人还款明细分页
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager queryBorrowerDetailList(Pager pager,Map<String,Object> map);
	
	/**
	 * 借款人还款明细列表
	 * @param map
	 * @return
	 */
	public List<BorrowRepaymentDetail> getBorrowerDetailList(Map<String,Object> map);
	
	public List<BorrowRepaymentDetail> queryBorrowRepaymentList(Map<String,Object> map);
	
	/**
	 * 按条件查询一条还款记录
	 * @param map
	 * @return
	 */
	public BorrowRepaymentDetail queryBorrowRepayment(Map<String,Object> map);
	
	public Integer queryRepaymentNotCount(Map<String,Object> map);
}
