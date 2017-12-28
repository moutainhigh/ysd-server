package com.qmd.dao.user;

import com.qmd.dao.BaseDao;
import com.qmd.mode.user.UserRepaymentDetail;
import com.qmd.util.Pager;

import java.util.List;
import java.util.Map;

public interface UserRepaymentDetailDao extends
		BaseDao<UserRepaymentDetail, Integer> {

	/**
	 * 获取用户的分期还款明细
	 * 
	 */
	public List<UserRepaymentDetail> queryUserRepaymentDetail(
			UserRepaymentDetail userRepaymentDetail);

	/**
	 * 获取用户的分期还款明细
	 * 
	 */
	public List<UserRepaymentDetail> getUserRepaymentDetailPager(
			Map<String, Object> map);

	/**
	 * 获取用户的分期还款明细(分页)
	 * 
	 */
	public Pager queryUserRepaymentDetailPager(Pager pager,
			Map<String, Object> map);
	
	public List<UserRepaymentDetail> queryUserRepaymentDetailAnalysis(
			Map<String, Object> map);

	public Pager queryUserRepaymentDetailPagerForIncome(Pager pager,
			Map<String, Object> map);
	
	public List<UserRepaymentDetail> queryUserRepaymentDetailPagerForIncome(
			Map<String, Object> map);
}
