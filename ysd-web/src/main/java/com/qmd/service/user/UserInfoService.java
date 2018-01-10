package com.qmd.service.user;

import com.qmd.mode.user.UserInfo;
import com.qmd.service.BaseService;

public interface UserInfoService  extends BaseService<UserInfo, Integer>{

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
}
