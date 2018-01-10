package com.qmd.action.user;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.mode.admin.Admin;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserInfo;
import com.qmd.service.mail.MailService;
import com.qmd.service.phone.PhoneLimitService;
import com.qmd.service.user.UserService;
import com.qmd.util.CommonUtil;
import com.qmd.util.md5.PWDUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户登录-注册
 * @author Xsf
 *
 */
@Service("registerAction")
@InterceptorRefs({
	@InterceptorRef(value = "qmdDefaultStack")
})
public class RegisterAction extends BaseAction {
	
	private static final long serialVersionUID = -3317398075827939752L;
	Logger log = Logger.getLogger(RegisterAction.class);
	@Resource
	UserService userService;
	@Resource
	MailService mailService;

	@Resource
	PhoneLimitService phoneLimitService;
	
	private User user;
	private Integer way;

	private String phoneReg;//注册手机号
	private String codeReg;//手机号验证码
	private String deviceToken;//消息推送
	private String password2;
	
	
	/**
	 * 跳转到注册页面
	 * 
	 * @return
	 */
	@Action(value = "/reg", results = { @Result(name = "investor", location = "/content/h5reg/reg.ftl", type = "freemarker") })
	public String reg() {
		log.info("跳转到注册页面");
		removeSession(User.USER_ID_SESSION_NAME);
		removeSession(UserInfo.USER_INFO_ID_SESSION_NAME);
		removeCookie(User.USER_TOKEN_COOKIE_NAME);
//		if (StringUtils.isNotEmpty(r)) {
//			User u = new User();
//			u.setTgNo(r);
//			List<User> user_list = userService.queryListByObject(u);
//			if (user_list != null && user_list.size() == 1) {
//				u = user_list.get(0);
//				friendUsername = u.getUsername();
//			} 
//		}
		return "investor";// 投资人注册页面

	}

	/**
	 * 会员注册
	 * @return
	 */
	@Action(value = "/register",  results = { @Result(type = "json") })
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String register() throws UnsupportedEncodingException {
		
		if(user==null) {
			return ajax(Status.error, "请输入注册信息!");
		}
		
		if(StringUtils.isEmpty(user.getPhone())) {
			return ajax(Status.error, "请填写手机号码!");
		}
		if(StringUtils.isEmpty(user.getPassword())) {
			return ajax(Status.error, "请输入密码!");
		}
		if(StringUtils.isEmpty(codeReg)) {
			return ajax(Status.error, "请输入手机验证码!");
		}
		if(StringUtils.isEmpty(password2)||!user.getPassword().equals(password2)) {
			return ajax(Status.error, "两次密码不一致");
			
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(user.getPhone());
		if (!m.matches()) {
			return ajax(Status.error, "手机号码格式不正确!");
		}
		
		Map<String, Object> map_phone = new HashMap<String, Object>();
		map_phone.put("phone", user.getPhone());
		if (this.userService.getUser(map_phone) != null) {
			return ajax(Status.error, "用户手机号码已存在!");
		}
		if(!user.getPassword().matches(".*[a-zA-Z]+.*$")){
			return ajax(Status.error, "密码必须包含字母!");
		}
		if(user.getPassword().length()<8 || user.getPassword().length()>16){
			return ajax(Status.error, "密码长度必须在8-16个字符之间!");
		}
		
		if(getSession(user.getPhone())==null) {
			return ajax(Status.error, "请获取验证码!");
		} 
		if(!codeReg.equals(getSession(user.getPhone()))){
			return ajax(Status.error, "验证码错误!");
		}
		
		user.setUsername(user.getPhone());
		if(way == null){
			user.setSourceFrom(1);
		}else{
			user.setSourceFrom(way);
		}
		
//		String random = (String)getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		removeSession(user.getPhone());

		log.info("会员注册开始");
		//用户基本信息添加
		Date dt = new Date();
		user.setCreateDate(dt);
		user.setModifyDate(dt);
		user.setTypeId(0);//0：投资人;1：借贷人
		user.setMemberType(0);
		user.setUsername(user.getUsername());
		user.setEmail(user.getEmail());
		
		String  random = CommonUtil.getRandomString(8);
		user.setRandomNum(random);//保存随机8位密码
		//user.setPassword( MD5.getMD5Str(MD5.getMD5Str(user.getPassword())+random));//对密码进行加密处理
		user.setPassword(PWDUtil.encode(user.getPassword(), random));//对密码进行加密处理
		
		user.setAddIp(getRequestRemoteAddr());
		user.setLastIp(getRequestRemoteAddr());
		user.setPhoneStatus(1);
//		user.setEmailCertificationKey(this.userService.buildRecoverKey());
//		if(StringUtils.isNotEmpty(deviceToken)){
//			user.setDeviceToken(deviceToken);
//		}
		this.userService.addUser(user);
		
//		// 设置token
//		user.setLastTime(dt);
//		String token = TokenUtil.getToken(user.getId().toString(),dt);
//		user.setToken(token);
//		this.userService.updateToken(user);
		
		
//		log.info("注册邮箱验证邮件发送成功");
		log.info("注册成功开始自动登录");
		// 写入会员登录Session
		setSession(User.USER_ID_SESSION_NAME, user);
		
		// 写入会员登录Cookie
		setCookie(User.USER_USERNAME_COOKIE_NAME, URLEncoder.encode(user.getUsername().toLowerCase(), "UTF-8"),User.USER_COOKIE_AGE);
		
		setCookie(User.USER_USERTYPE_COOKIE_NAME, URLEncoder.encode(user.getTypeId().toString(), "UTF-8"),User.USER_COOKIE_AGE);
		
		setCookie(User.USER_USEREALSTATUS_COOKIE_NAME, URLEncoder.encode("0", "UTF-8"),User.USER_COOKIE_AGE);
		
		log.info("自动登录成功");
		redirectUrl = getContextPath() + "/userCenter/index.do";
		return ajax(Status.success, "注册成功");
		
	}
	
	/**
	 * 验证手机号
	 * 
	 * @return
	 */
	@Action(value = "/valPhone", results = { @Result(type = "plainText") })
	@InputConfig(resultName = "ajaxError")
	public String valPhone() {
		log.info("验证手机号");
		if ("".equals(user.getPhone())) {
			return ajax("false");
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("phone", user.getPhone());
			try {
				User u = userService.getUser(map);
				if (u == null) {
					return ajax("true");
				} else {
					return ajax("false");
				}
			} catch (Exception e) {
				return ajax("false");
			}
		}
	}

	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	
	// 获取客服管理员
	public List<Admin> getKefuAdminList() {
		 List<Admin> adminList = userService.getKefuAdminList();
		 return adminList;
	}

	public String getPhoneReg() {
		return phoneReg;
	}

	public void setPhoneReg(String phoneReg) {
		this.phoneReg = phoneReg;
	}

	public String getCodeReg() {
		return codeReg;
	}

	public void setCodeReg(String codeReg) {
		this.codeReg = codeReg;
	}

	public Integer getWay() {
		return way;
	}

	public void setWay(Integer way) {
		this.way = way;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

}
