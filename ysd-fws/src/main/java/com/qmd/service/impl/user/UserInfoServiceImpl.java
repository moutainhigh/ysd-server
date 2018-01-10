package com.qmd.service.impl.user;


import com.qmd.dao.area.AreaDao;
import com.qmd.dao.user.UserInfoDao;
import com.qmd.mode.user.UserInfo;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
 * Service实现类 - 用户详细信息
 * ============================================================================
 */
@Service("userInfoService")
public class UserInfoServiceImpl  extends BaseServiceImpl<UserInfo, Integer> implements UserInfoService {
	
	@Resource
	UserInfoDao userInfoDao;
	@Resource
	AreaDao areaDao;
	
	@Resource
	public void setBaseDao(UserInfoDao userInfoDao) {
		super.setBaseDao(userInfoDao);
	}
	
//	@Override
//	public void addUserInfo(UserInfo userInfo){
//		//根据areaID 查找domain
////		String areaStore = areaDao.getAreaDomain(userinfo.getAreaStore);
////		userInfo.setAreaStore(areaStore);
//		this.userInfoDao.addUserInfo(userInfo);
//	}

	public UserInfo findByUserId(Integer userId){
		return this.userInfoDao.findByUserId(userId);
	}
	
//	public void updateDetailProfile(UserInfo userInfo){
//		this.userInfoDao.updateDetailProfile(userInfo);
//	}
	
//	public void updateEnterprise(UserInfo userInfo){
//		this.userInfoDao.updateEnterprise(userInfo);
//		
//	}
	
	
//	public List<UserAddress> getUserAddressList(Map<String,Object> map){
//		
//		return userInfoDao.getUserAddressList(map);
//	}
	
//	public int saveUserAddress(UserAddress userAddress){
//		return userInfoDao.saveUserAddress(userAddress);
//	}
	
//	public int updateUserAddress(UserAddress userAddress){
//		return userInfoDao.updateUserAddress(userAddress);
//	}
}
