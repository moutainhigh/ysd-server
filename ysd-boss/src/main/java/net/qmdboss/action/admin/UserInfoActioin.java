package net.qmdboss.action.admin;

import net.qmdboss.entity.User;
import net.qmdboss.entity.UserInfo;
import net.qmdboss.service.UserInfoService;
import net.qmdboss.service.UserService;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;

@ParentPackage("admin")
public class UserInfoActioin extends BaseAdminAction{
	private static final long serialVersionUID = -2746010320679258631L;
	
	private UserInfo userInfo;
	private User user;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "userServiceImpl")
	private UserInfoService userInfoService;
	
	// 列表
	public String list() {
//		if(verifyType==null){
//			verifyType=0;
//		}, criteria
		pager = userInfoService.findPager(pager);
//		pager = userService.findPager(pager);
		return LIST;
	}
	
}
