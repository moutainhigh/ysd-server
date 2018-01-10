package net.qmdboss.dao.impl;

import net.qmdboss.dao.UserInfoDao;
import net.qmdboss.entity.UserInfo;
import org.springframework.stereotype.Repository;

@Repository("userInfoDaoImpl")
public class UserInfoDaoImpl  extends BaseDaoImpl<UserInfo, Integer> implements UserInfoDao{

}
