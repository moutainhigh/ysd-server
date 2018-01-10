package com.qmd.dao.user;

import com.qmd.dao.BaseDao;
import com.qmd.mode.user.UserInfo;

public interface UserInfoDao extends BaseDao<UserInfo, Integer>{

	/**
	 * 注册添加用户详细信息
	 * @param user
	 */
	public void addUserInfo(UserInfo userInfo);
	
	/**
	 * 根据用户ID查找用户详情
	 * @param userId
	 * @return
	 */
	public UserInfo findByUserId(Integer userId);
	
	/**
	 * 修改用户详细信息
	 * @param userInfo
	 */
	public void updateDetailProfile(UserInfo userInfo);
	
	/**
	 * 修改企业资料
	 * @param userInfo
	 */
	public void updateEnterprise(UserInfo userInfo);
	
	public int updateIdentify(UserInfo userInfo);
}
