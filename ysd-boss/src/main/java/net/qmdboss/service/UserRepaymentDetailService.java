package net.qmdboss.service;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.UserRepaymentDetail;

import java.util.List;
import java.util.Map;



public interface UserRepaymentDetailService extends
		BaseService<UserRepaymentDetail, Integer> {
	
	public Pager findUserRepaymentDetailPager(Pager pager,String status);
	
	public Pager findUserRepaymentDetailPager(Pager pager,Map<String,Object> map);
	
	public List<UserRepaymentDetail> findUserRepaymentDetailList (
			Map<String, Object> map);
	
	/**
	 * 优化投资人待收明细
	 * @param map
	 * @return
	 */
	public Pager findUserRepaymentDetailPagerByY(Pager pager,Map<String, Object> map);

}
