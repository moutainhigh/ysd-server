package com.qmd.dao.impl.user;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.UserInfoDao;
import com.qmd.mode.user.UserInfo;
import org.springframework.stereotype.Repository;

@Repository("userInfoDao")
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo, Integer>  implements UserInfoDao {

	@Override
	public void addUserInfo(UserInfo userInfo){
		this.getSqlSession().insert("UserInfo.addUserInfo",userInfo);
	}
	
	public UserInfo findByUserId(Integer userId){
		return this.getSqlSession().selectOne("UserInfo.findByUserId", userId);
	}
	
	public void updateDetailProfile(UserInfo userInfo){
		this.getSqlSession().update("UserInfo.updateDetailProfile", userInfo);
	}
	
	public void updateEnterprise(UserInfo userInfo){
		this.getSqlSession().update("UserInfo.updateEnterprise",userInfo);
	}
	
	
	
	public int updateIdentify(UserInfo userInfo){
		return this.getSqlSession().update("UserInfo.updateIdentify", userInfo);
	}
	
	
}
