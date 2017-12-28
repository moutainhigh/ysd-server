package net.qmdboss.service.impl;

import net.qmdboss.dao.UserAwardDetailDao;
import net.qmdboss.entity.UserAwardDetail;
import net.qmdboss.service.UserAwardDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("userAwardDetailServiceImpl")
public class UserAwardDetailServiceImpl extends BaseServiceImpl<UserAwardDetail, Integer> implements UserAwardDetailService{
	
	@Resource(name="userAwardDetailDaoImpl")
	private UserAwardDetailDao userAwardDetailDao;
	
	@Resource(name = "userAwardDetailDaoImpl")
	public void setBaseDao(UserAwardDetailDao userAwardDetailDao) {
		super.setBaseDao(userAwardDetailDao);
	}
	

}
