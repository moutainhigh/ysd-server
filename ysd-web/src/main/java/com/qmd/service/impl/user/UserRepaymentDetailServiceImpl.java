package com.qmd.service.impl.user;

import com.qmd.dao.user.UserRepaymentDetailDao;
import com.qmd.mode.user.UserRepaymentDetail;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.UserRepaymentDetailService;
import com.qmd.util.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("userRepaymentDetailService")
public class UserRepaymentDetailServiceImpl extends
		BaseServiceImpl<UserRepaymentDetail, Integer> implements
		UserRepaymentDetailService {

	@Resource
	UserRepaymentDetailDao userRepaymentDetailDao;

	@Resource
	public void setBaseDao(UserRepaymentDetailDao userRepaymentDetailDao) {
		super.setBaseDao(userRepaymentDetailDao);
	}

	public Pager queryUserRepaymentDetailPager(Pager pager,
			Map<String, Object> map) {
		return userRepaymentDetailDao.queryUserRepaymentDetailPager(pager, map);
	}

	public List<UserRepaymentDetail> queryUserRepaymentDetailForAnalysis(
			Map<String, Object> map) {
		return userRepaymentDetailDao.queryUserRepaymentDetailAnalysis(map);
	}
	
	public Pager queryUserRepaymentDetailPagerForIncome(Pager pager,
			Map<String, Object> map) {
		return userRepaymentDetailDao.queryUserRepaymentDetailPagerForIncome(pager, map);
	}


	/**
	 * 按map需求获取用户还款列表
	 * @param map
	 * @return
	 */
	@Override
	public List<UserRepaymentDetail> queryUserRepaymentDetailList(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userRepaymentDetailDao.queryUserRepaymentDetailAnalysis(map);
	}
	

}
