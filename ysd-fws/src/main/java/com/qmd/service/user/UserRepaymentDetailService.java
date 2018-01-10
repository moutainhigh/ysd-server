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
}
