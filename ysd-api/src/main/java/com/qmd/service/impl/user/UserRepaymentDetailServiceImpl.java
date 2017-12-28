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
	
	/**
	 * 修改续投
	 * @param obj
	 * @return 1修改成功，2已经还款不能修改，3状态一致不能修改
	 */
	@Override
	public int changeApplyContinueTotal(UserRepaymentDetail obj) {
		
		UserRepaymentDetail entity = userRepaymentDetailDao.getForUpdate(obj.getId(), obj);
		if (obj.getApplyContinueTotal().equals(entity.getApplyContinueTotal())) {
			// 已经还款的，不修改
			return 2;
		}
		if (obj.getApplyContinueTotal().equals(entity.getApplyContinueTotal())) {
			// 状态一致的，不需要修改
			return 3;
		}
		
		UserRepaymentDetail urd = new UserRepaymentDetail();
		urd.setId(obj.getId());
		urd.setApplyContinueTotal(obj.getApplyContinueTotal());
		userRepaymentDetailDao.update(urd);
		
		return 1;
		
	}

}
