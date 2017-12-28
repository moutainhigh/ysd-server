package com.qmd.dao.borrow;

import com.qmd.dao.BaseDao;
import com.qmd.mode.borrow.BorrowTemp;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.util.Pager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BorrowTenderDaoService extends BaseDao<BorrowTender,Integer>{
	public List<BorrowTender> getBorrowTenderByBorrowId(Integer borrow_id);

	public Object updateBorrowDetail(Map<String, Object> map);
	
	public Object upateBorrowDetailAgile(Map<String, Object> map);

	/**
	 * 查询用户投标列表
	 * @param map
	 * @return
	 */
	public List<BorrowTender> getTenderDetailByUserid(Map<String, Object> map);
	
	/**
	 * 查询用户投标-分页
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager queryUncollectedDetailList(Pager pager,Map<String,Object> map);
	
	/**
	 * 查询借款人 借款明细-列表
	 * @param map
	 * @return
	 */
	public List<BorrowTemp> getJkmxByUserid(Map<String, Object> map);
	
	/**
	 * 查询借款人 借款明细-分页
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager queryJkmxList(Pager pager,Map<String,Object> map);
	
	
	public Object getBorrowDetailById(Integer borrowDetailId);
	/**
	 * 周期内投资金额
	 * @param map
	 * @return
	 */
	public BigDecimal querySumTenderMoney(Map<String,Object> map) ;
	
	public Integer queryTenderDetailList(Map<String,Object> map);

	public int getDeferApplyNum(Map<String,Object> map);
	
	public Integer queryTenderCount(Map<String,Object> map);
	
	public int updateBorrowTemp(BorrowTemp borrowTemp);
}
