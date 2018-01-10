package com.qmd.service.user;

import com.qmd.mode.user.UserInfo;
import com.qmd.service.BaseService;

public interface UserInfoService  extends BaseService<UserInfo, Integer>{

	/**
	 * 注册添加用户详细信息
	 * @param user
	 */
//	public void addUserInfo(UserInfo userInfo);
	
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
//	public void updateDetailProfile(UserInfo userInfo);
	/**
	 * 修改企业资料
	 * @param userInfo
	 */
//	public void updateEnterprise(UserInfo userInfo);
	
	
	/**
	 * 获取用户收货地址列表
	 * @return
	 */
//	public List<UserAddress> getUserAddressList(Map<String,Object> map);
	
	/**
	 * 添加用户收货地址
	 * @param userAddress
	 */
//	public int saveUserAddress(UserAddress userAddress);
	
	/**
	 * 修改用户收货地址【删除时 isDelete =1】
	 * @param userAddress
	 */
//	public int updateUserAddress(UserAddress userAddress);
	
}
