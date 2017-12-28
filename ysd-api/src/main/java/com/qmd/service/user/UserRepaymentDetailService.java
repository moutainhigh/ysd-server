package com.qmd.service.user;

import com.qmd.mode.user.UserRepaymentDetail;
import com.qmd.service.BaseService;
import com.qmd.util.Pager;

import java.util.List;
import java.util.Map;

public interface UserRepaymentDetailService extends
		BaseService<UserRepaymentDetail, Integer> {

	/**
	 * 获取用户的分期还款明细(分页)
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager queryUserRepaymentDetailPager(Pager pager,
			Map<String, Object> map);
	
	
	/**
	 * 取得用户还款信息
	 * @param map
	 * @return
	 */
	public List<UserRepaymentDetail> queryUserRepaymentDetailForAnalysis(
			Map<String, Object> map);
	
	public Pager queryUserRepaymentDetailPagerForIncome(Pager pager,
			Map<String, Object> map);
	
	/**
	 * 按map需求获取用户还款列表
	 * @param map
	 * @return
	 */
	public List<UserRepaymentDetail> queryUserRepaymentDetailList(Map<String, Object> map);
	
	/**
	 * 修改续投
	 * @param obj
	 * @return 1修改成功，2已经还款不能修改，3状态一致不能修改
	 */
	public int changeApplyContinueTotal(UserRepaymentDetail obj);
}
