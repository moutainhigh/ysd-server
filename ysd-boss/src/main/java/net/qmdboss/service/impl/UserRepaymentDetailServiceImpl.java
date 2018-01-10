package net.qmdboss.service.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.UserRepaymentDetailDao;
import net.qmdboss.entity.UserRepaymentDetail;
import net.qmdboss.service.UserRepaymentDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("userRepaymentDetailServiceImpl")
public class UserRepaymentDetailServiceImpl extends
		BaseServiceImpl<UserRepaymentDetail, Integer> implements
		UserRepaymentDetailService {
	
	@Resource(name = "userRepaymentDetailDaoImpl")
	private UserRepaymentDetailDao userRepaymentDetailDao;
	
	@Resource(name = "userRepaymentDetailDaoImpl")
	public void setBaseDao(UserRepaymentDetailDao userRepaymentDetailDao) {
		super.setBaseDao(userRepaymentDetailDao);
	}
	
	public Pager findUserRepaymentDetailPager(Pager pager,String status) {
		return userRepaymentDetailDao.findUserRepaymentDetailPager(pager, status);
	}
	
	public Pager findUserRepaymentDetailPager(Pager pager,Map<String,Object> map) {
		return userRepaymentDetailDao.findUserRepaymentDetailPager(pager, map);
	}
	
	public List<UserRepaymentDetail> findUserRepaymentDetailList (
			Map<String, Object> map) {
		return userRepaymentDetailDao.findUserRepaymentDetailList(map);
	}
	
	public Pager findUserRepaymentDetailPagerByY(Pager pager,Map<String,Object> map) {
		return userRepaymentDetailDao.findUserRepaymentDetailPagerByY(pager, map);
	}

}
