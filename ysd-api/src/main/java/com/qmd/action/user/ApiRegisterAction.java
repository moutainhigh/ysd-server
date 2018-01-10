package com.qmd.action.user;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.reguser.RegUserBean;
import com.qmd.mode.admin.Admin;
import com.qmd.mode.user.User;
import com.qmd.service.mail.MailService;
import com.qmd.service.phone.PhoneLimitService;
import com.qmd.service.user.UserService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConfigUtil;
import com.qmd.util.TokenUtil;
import com.qmd.util.md5.PWDUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户登录-注册
 * @author Xsf
 *
 */
@Service("apiRegisterAction")
@InterceptorRefs({
//	@InterceptorRef(value = "apiUserInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
public class ApiRegisterAction extends ApiBaseAction {
	
	private static final long serialVersionUID = -3317398075827939752L;
	Logger log = Logger.getLogger(ApiRegisterAction.class);
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
	
	private String appType;//0:IOS;1:android
	private String placeName;
	private String im;
	private String idfa;
	private String mac;
	
	/**
	 * 会员注册
	 * @return
	 */
	@Action(value="/api/reg",results={@Result(type="json")})
	public String ajaxReg() throws Exception{
		if(user==null) {
			return ajaxJson("E0002", "请输入注册信息");
		}
		
		if(StringUtils.isEmpty(user.getPhone())) {
			return ajaxJson("E0002", "请输入手机号");
		}

		if(StringUtils.isEmpty(user.getPassword())) {
			return ajaxJson("E0002","请输入密码");
		}

		if(StringUtils.isEmpty(codeReg)) {
			return ajaxJson("E0002", "请输入手机验证码");
		}

        /**
         * 在生产环境,检验短信验证码
         */
		/**yueshang*/
        if (ConfigUtil.isProdEnv()) {
            //获取并移除session中短信验证码
            Object phoneCheckCode = getSession(user.getPhone());
            removeSession(user.getPhone());

            if (!ConfigUtil.getConfigUtil().isTestSmsCode(codeReg)) {
                //验证码
                if(phoneCheckCode==null) {
                    return ajaxJson("M0008_1",ApiConstantUtil.M0008_1);
                }

                if(!codeReg.equals(phoneCheckCode)){
                    return ajaxJson("M0008_1",ApiConstantUtil.M0008_1);
                }
            } else {
                log.info("使用万能密码");
            }

        }
		
		if(!user.getPassword().matches(".*[a-zA-Z]+.*$")){
			return ajaxJson("M0008_3",ApiConstantUtil.M0008_3);
		}
		if(user.getPassword().length()<8 || user.getPassword().length()>16){
			return ajaxJson("M0008_4",ApiConstantUtil.M0008_4);
		}
		
		user.setUsername(user.getPhone());
		if(way == null){
			user.setSourceFrom(1);
		}else{
			user.setSourceFrom(way);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("username",user.getUsername());
		if(this.userService.getUser(map) != null){
			return ajaxJson("M0008_2",ApiConstantUtil.M0008_2);
		}
		
//		String random = (String)getSession(ConstantUtil.RANDOM_COOKIE_NAME);

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
		if(StringUtils.isNotEmpty(deviceToken)){
			user.setDeviceToken(deviceToken);
		}
		
		user.setImei(im);
		user.setAppType(appType);
		user.setPlaceName(placeName);
		System.out.println("-------------placeName======"+placeName);
		user.setIdfa(idfa);
		user.setMac(mac);
		
		String agent = getRequest().getHeader("user-agent"); 
		user.setBrowserType(agent);
		int ret = this.userService.addUser(user);
		
		if(ret ==1){
			return ajaxJson("M0008_2",ApiConstantUtil.M0008_2);
		}
		
		// 设置token
		user.setLastTime(dt);
		String token = TokenUtil.getToken(user.getId().toString(),dt);
		user.setToken(token);
		this.userService.saveToken(user);
		
		RegUserBean model = new RegUserBean();
		
		
		
		model.setRcd("R0001");
		model.setUserId(user.getId());
		model.setToken(token);		
		log.info("自动登录成功");
		return ajax(model);
		
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

	public PhoneLimitService getPhoneLimitService() {
		return phoneLimitService;
	}

	public void setPhoneLimitService(PhoneLimitService phoneLimitService) {
		this.phoneLimitService = phoneLimitService;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

}
