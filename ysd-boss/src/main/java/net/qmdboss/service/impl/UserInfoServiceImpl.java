package net.qmdboss.service.impl;

import net.qmdboss.dao.UserInfoDao;
import net.qmdboss.entity.UserInfo;
import net.qmdboss.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userInfoServiceImpl")
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, Integer> implements UserInfoService{
	@Resource(name="userInfoDaoImpl")
	private UserInfoDao userInfoDao;
	
	@Resource(name = "userInfoDaoImpl")
	public void setBaseDao(UserInfoDao userInfoDao) {
		super.setBaseDao(userInfoDao);
	}

}
