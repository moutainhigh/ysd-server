package net.qmdboss.dao;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.UserRepaymentDetail;

import java.util.List;
import java.util.Map;

/**
 * UserRepaymentDetailDao 对用投标人的还款明细列表Dao接口类
 * @author zhanf
 *
 */
public interface UserRepaymentDetailDao extends
		BaseDao<UserRepaymentDetail, Integer> {
	
	public Pager findUserRepaymentDetailPager(Pager pager,String status);
	
	public Pager findUserRepaymentDetailPager(Pager pager,Map<String,Object> map);
	
	public List<UserRepaymentDetail> findUserRepaymentDetailList(
			Map<String, Object> map);

	public Pager findUserRepaymentDetailPagerByY(Pager pager,	Map<String, Object> map);

}
