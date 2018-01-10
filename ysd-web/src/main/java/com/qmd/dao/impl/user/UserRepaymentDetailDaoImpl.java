package com.qmd.dao.impl.user;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.UserRepaymentDetailDao;
import com.qmd.mode.user.UserRepaymentDetail;
import com.qmd.util.Pager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("userRepaymentDetailDao")
public class UserRepaymentDetailDaoImpl extends
		BaseDaoImpl<UserRepaymentDetail, Integer> implements
		UserRepaymentDetailDao {

	public List<UserRepaymentDetail> queryUserRepaymentDetail(UserRepaymentDetail userRepaymentDetail) {
		List<UserRepaymentDetail> userRepaymentDetailList = this
				.getSqlSession().selectList(
						"UserRepaymentDetail.queryUserRepaymentDetail", userRepaymentDetail);
		return userRepaymentDetailList;
	}

	public Pager queryUserRepaymentDetailPager(Pager pager,
			Map<String, Object> map) {
		Integer count = Integer.parseInt(this
				.getSqlSession()
				.selectOne("UserRepaymentDetail.queryUserRepaymentDetailCount",
						map).toString());
		pager.setTotalCount(count);
		map.put("pager", pager);
		List<UserRepaymentDetail> bList = getUserRepaymentDetailPager(map);
		pager.setResult(bList);
		return pager;
	}

	public List<UserRepaymentDetail> getUserRepaymentDetailPager(
			Map<String, Object> map) {
		List<UserRepaymentDetail> bList = this.getSqlSession().selectList(
				"UserRepaymentDetail.queryUserRepaymentDetailPager", map);
		return bList;
	}
	
	public Pager queryUserRepaymentDetailPagerForIncome(Pager pager,
			Map<String, Object> map) {
		Integer count = Integer.parseInt(this
				.getSqlSession()
				.selectOne("UserRepaymentDetail.queryUserRepaymentDetailCountForIncome",
						map).toString());
		pager.setTotalCount(count);
		map.put("pager", pager);
		List<UserRepaymentDetail> bList = queryUserRepaymentDetailPagerForIncome(map);
		pager.setResult(bList);
		return pager;
	}
	
	public List<UserRepaymentDetail> queryUserRepaymentDetailPagerForIncome(
			Map<String, Object> map) {
		List<UserRepaymentDetail> bList = this.getSqlSession().selectList(
				"UserRepaymentDetail.queryUserRepaymentDetailPagerForIncome", map);
		return bList;
	}
	
	public List<UserRepaymentDetail> queryUserRepaymentDetailAnalysis(
			Map<String, Object> map) {
		List<UserRepaymentDetail> bList = this.getSqlSession().selectList(
				"UserRepaymentDetail.queryUserRepaymentDetailForAnalysis", map);
		return bList;
	}
	
	

}
