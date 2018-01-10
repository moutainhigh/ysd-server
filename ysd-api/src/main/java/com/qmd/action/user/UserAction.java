package com.qmd.action.user;

import com.qmd.action.base.BaseAction;
import com.qmd.bean.BaseBean;
import com.qmd.mode.user.User;
import com.qmd.service.borrow.BorrowTenderService;
import com.qmd.service.user.AccountBankService;
import com.qmd.service.user.UserService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.JsonUtil;
import com.qmd.util.md5.PWDUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户
 */
@Service("userAction")
@InterceptorRefs({ @InterceptorRef(value = "userVerifyInterceptor"),
		@InterceptorRef(value = "qmdDefaultStack") })
public class UserAction extends BaseAction {

	private static final long serialVersionUID = -8440843074109637391L;

	Logger log = Logger.getLogger(UserAction.class);
	@Resource
	UserService userService; 
	@Resource
	AccountBankService accountBankService;
	@Resource
	BorrowTenderService borrowTenderService;

	private Integer userId;
	private String realName;
	private String cardId;
	private String oldPassword;
	private String newPassword;
	private String codeReq;
	private String phone;
	private User user;


	
	
	/**
	 * 重置登录密码
	 */
	@Action(value = "/user/lostLoginPwd",results={@Result(type="json")})
	public String lostLoginPwd() {
		
		if (StringUtils.isEmpty(phone)) {
			return ajax("M0008_13", ApiConstantUtil.M0008_13);
		}
		
		if (StringUtils.isEmpty(codeReq)) {
			return ajax("M0008_0", ApiConstantUtil.M0008_0);
		}
		
		if(getSession(phone)==null) {
			return ajax("M0008_12", ApiConstantUtil.M0008_12);
		}
		String aa = (String)getSession(phone);
		if (StringUtils.isEmpty(aa)) {
			return ajax("M0008_12", ApiConstantUtil.M0008_12);
		}
		if(!aa.equals(codeReq)) {
			return ajax("M0008_1", ApiConstantUtil.M0008_1);
		}

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("phone", phone);
		map.put("isEnabled", true);
		List<User> userList = userService.getUserList(map);
		if (userList==null ||userList.size()<1) {
			return ajax("M0008_5", ApiConstantUtil.M0008_5);
		}
		
		User user = userList.get(0);
		if (user == null) {
			return ajax("E0002", ApiConstantUtil.E0002);
		}
		if (user.getIsLock()) {
			return ajax("M0003", ApiConstantUtil.M0003);
		}
		
		user.setPassword(PWDUtil.encode(newPassword,user.getRandomNum()));
		
		userService.updatePassowrd(user);
		
		BaseBean bean = new BaseBean();
		bean.setRcd("R0001");
		bean.setRmg("设置成功");

		return ajax(JsonUtil.toJson(bean));

	}
	
	
	/**
	 * 变更手机号码申请
	 */
	@Action(value = "/upPhone")
	public String upPhone() {
		
		if (StringUtils.isEmpty(codeReq)) {
			return ajax("M0008_0", ApiConstantUtil.M0008_0);
		}
		if (StringUtils.isEmpty(oldPassword)) {
			return ajax("S0002", ApiConstantUtil.S0002);
		}
		if(getSession(User.USER_PHONE_NUM)==null) {
			return ajax("M0008_12", ApiConstantUtil.M0008_12);
		}
		String aa = (String)getSession(User.USER_PHONE_NUM);
		if (StringUtils.isEmpty(aa)) {
			return ajax("M0008_12", ApiConstantUtil.M0008_12);
		}
		if(!aa.equals(codeReq)) {
			return ajax("M0008_1", ApiConstantUtil.M0008_1);
		}
		
		User user = userService.getById(getLoginUser().getId(), new User());
		if (user == null) {
			return ajax("E0002", ApiConstantUtil.E0002);
		}
		if (StringUtils.isEmpty(user.getPayPassword())) {
			return ajax("E0002", ApiConstantUtil.E0002);
		}
			
		if(!user.getPayPassword().equals(PWDUtil.encode(oldPassword,user.getRandomNum()))) {
			return ajax("S0002", ApiConstantUtil.S0002);
		}
		
		setSession(user.getPhone()+user.getId(),1);
		
		BaseBean bean = new BaseBean();
		bean.setRcd("R0001");
		bean.setRmg("申请成功");

		//UserSafePwdBean userSafePwdBean = new UserSafePwdBean(user);

		return ajax(JsonUtil.toJson(bean));

	}
	
	/**
	 * 变更手机号码提交
	 */
	@Action(value = "/user/upPhoneb")
	public String upPhone2() {
		
		if (StringUtils.isEmpty(codeReq)) {
			return ajax("M0008_0", ApiConstantUtil.M0008_0);
		}
		if (StringUtils.isEmpty(phone)) {
			return ajax("M0008_13", ApiConstantUtil.M0008_13);
		}
		
		if(getSession(User.USER_PHONE_NUM)==null) {
			return ajax("M0008_12", ApiConstantUtil.M0008_12);
		}
		
		if(getSession(phone)==null) {
			return ajax("M0008_1",ApiConstantUtil.M0008_1);
		} 
		if(!codeReq.equals(getSession(phone))){
			return ajax("M0008_1",ApiConstantUtil.M0008_1);
		}
		
		User user = (User) getSession(User.USER_ID_SESSION_NAME);
		if (user == null || user.getId() == null) {
			return ajax("E0001",ApiConstantUtil.E0001);
		}
		if (getSession(user.getPhone()+user.getId())==null) {
			return ajax("E0002",ApiConstantUtil.E0002);
		}
		
		
		user.setUsername(phone);
		user.setPhone(phone);
		userService.updatePhone(user);
		
		BaseBean bean = new BaseBean();
		bean.setRcd("R0001");
		bean.setRmg("更改成功");

		//UserSafePwdBean userSafePwdBean = new UserSafePwdBean(user);

		return ajax(JsonUtil.toJson(bean));

	}
	
	

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getCodeReq() {
		return codeReq;
	}

	public void setCodeReq(String codeReq) {
		this.codeReq = codeReq;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	

}
