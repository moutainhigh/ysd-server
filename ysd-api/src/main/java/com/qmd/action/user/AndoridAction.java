package com.qmd.action.user;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.mode.user.User;
import com.qmd.service.user.UserService;
import com.qmd.util.JsonUtil;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("andoridAction")
@InterceptorRefs({@InterceptorRef(value = "qmdDefaultStack") })
public class AndoridAction extends ApiBaseAction {

	private static final long serialVersionUID = -6865125836125472183L;

	@Resource
	UserService userService; 
	
	@Action(value = "/api/androidUser")
	public String apiAndroidUser(){
		User u =(User)getSession(User.USER_ID_SESSION_NAME);
		String sessionId = getRequest().getSession().getId();
		System.out.println("getSessionId="+sessionId);
		return ajax(JsonUtil.toJson(u.getUsername()+"-sessionId="+sessionId));
	}
	

}
