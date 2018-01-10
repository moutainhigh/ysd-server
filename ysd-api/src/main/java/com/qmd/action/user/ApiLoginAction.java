package com.qmd.action.user;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.login.LoginBean;
import com.qmd.mode.admin.Admin;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserInfo;
import com.qmd.service.area.AreaService;
import com.qmd.service.mail.MailService;
import com.qmd.service.phone.PhoneLimitService;
import com.qmd.service.user.UserInfoService;
import com.qmd.service.user.UserService;
import com.qmd.service.util.ListingService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.ConstantUtil;
import com.ysd.biz.PushForBiz;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户登录-注册
 */
@Service("apiLoginAction")
public class ApiLoginAction extends ApiBaseAction {

	private static final long serialVersionUID = -3317398075827939752L;
	Logger log = Logger.getLogger(ApiLoginAction.class);
	@Resource
	UserService userService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	AreaService areaService;
	@Resource
	MailService mailService;
	@Resource
	ListingService listingService;
	@Resource
	PhoneLimitService phoneLimitService;

	private User user;
	private String p;
	private List<User> userList;
	private String message;
	private String areaId;
	private String loginRedirectUrl;
	private String r;// 推广参数
	private String friendUsername;// 邀请人用户名
	private File cardFile1;// 身份证正面图片
	private File cardFile2;// 身份证背面图片
	private File uploadDataFile; // 上传开通借款功能资料
	private File faceImgFile;// 头像图片
	private String newEmail;// 新认证Email
	private String kefu;
	private String passwordRecoverKey;// 密码Key

	private Integer tenderAutoRank;// 自投排名
	private BigDecimal tenderAutoAccountRate;
	private Integer tenderAutoWayOne;// 1 按月付息，到期还本 (含一月标)
	private Integer tenderAutoWayTwo;// 2按月分期还本息
	private Integer tenderAutoWayThree;// 3到期还本息

	private BigDecimal tenderAbleMoney;
	private BigDecimal tenderContinueMoney;
	private String phoneReg;// 注册手机号
	private String codeReg;// 手机号验证码
	private String mailUrl;// 邮箱url
	private String newPayPassword;// 支付密码

	private String username;
	private String password;
	private String isGesture;// 是否清空手势密码
	private String deviceToken;//消息推送
	private String token; // 登录token

	public void validate() {
		log.info("validate succ------------------");
	}

	/**
	 * 会员退出
	 * 
	 * @return
	 */
	@Action(value = "/api/logout", results = { @Result(type = "json") })
	public String ajaxLogout() {
		removeSession(User.USER_ID_SESSION_NAME);
		removeSession(UserInfo.USER_INFO_ID_SESSION_NAME);
		removeCookie(User.USER_USERNAME_COOKIE_NAME);
		removeCookie(User.USER_USERTYPE_COOKIE_NAME);
		removeCookie(User.USER_USEREALSTATUS_COOKIE_NAME);

		userService.logout(token);
		
		return ajaxJson("R0001", "退出成功");
	}

	/**
	 * 会员验证登录
	 * 
	 * @return
	 */
	@Action(value = "/api/login", results = { @Result(type = "json") })
	public String ajaxLogin() throws Exception {

		if (StringUtils.isEmpty(username)) {
			return ajaxJson("M0008_16", ApiConstantUtil.M0008_16);
		} else if (StringUtils.isEmpty(password)) {
			return ajaxJson("M0008_17", ApiConstantUtil.M0008_17);
		}

		User loginUser = null;
		try {
			log.info("会员验证登录");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username", username);

			loginUser = this.userService.getUser(map);
//			String oldDeviceToken = loginUser.getDeviceToken();
//			if(StringUtils.isNotEmpty(deviceToken)){
//				loginUser.setDeviceToken(deviceToken);
//			}
			String oldDeviceToken="";
			// 解除会员账户锁定
			if (loginUser != null) {
			
				oldDeviceToken = loginUser.getDeviceToken();
				if(StringUtils.isNotEmpty(deviceToken)){
					loginUser.setDeviceToken(deviceToken);
				}
				if (loginUser.getIsLock()) {
					
					if ((Boolean) getServletContext().getAttribute(
							"qmd.setting.isLoginFailureLock")) {
						Integer loginFailureLockTime = (Integer) getServletContext()
								.getAttribute(
										"qmd.setting.loginFailureLockTime");
						if (loginFailureLockTime > 0) {
							Date lockedDate = loginUser.getLockedDate();
							Date unlockDate = DateUtils.addMinutes(lockedDate,
									loginFailureLockTime);
							if (new Date().after(unlockDate)) {
								loginUser.setLoginFailureCount(0);
								loginUser.setIsLock(false);
								loginUser.setLockedDate(null);
								userService.update(loginUser);
							}
						}
					} else {
						loginUser.setLoginFailureCount(0);
						loginUser.setIsLock(false);
						loginUser.setLockedDate(null);
						userService.update(loginUser);
					}
				}

				if (loginUser.getIsEnabled() == null
						|| !loginUser.getIsEnabled()) {
					return ajaxJson("M0002", ApiConstantUtil.M0002);
					// return ajax(Status.error, "您的账号已被禁用,无法登录，如有疑问请联系客服人员!");
				}

				if (loginUser.getIsLock() == null || loginUser.getIsLock()) {
					// return ajax(Status.error,"您的账号已被锁定，如有疑问请联系客服人员!");
					return ajaxJson("M0003", ApiConstantUtil.M0003);
				}
				if (!userService.isPassword(username, password, "0")) {

					if ((Boolean) getServletContext().getAttribute(
							"qmd.setting.isLoginFailureLock")) {
						Integer loginFailureLockCount = (Integer) getServletContext()
								.getAttribute(
										"qmd.setting.loginFailureLockCount");
						Integer loginFailureCount = loginUser
								.getLoginFailureCount() == null ? 0 : loginUser
								.getLoginFailureCount() + 1;
						if (loginFailureCount >= (Integer) getServletContext()
								.getAttribute(
										"qmd.setting.loginFailureLockCount")) {
							loginUser.setIsLock(true);
							loginUser.setLockedDate(new Date());
						}
						loginUser.setLoginFailureCount(loginFailureCount);
						userService.update(loginUser);
						if ((Boolean) getServletContext().getAttribute(
								"qmd.setting.isLoginFailureLock")
								&& loginFailureLockCount - loginFailureCount <= 3) {
							// return ajax(Status.error,"若连续" +
							// loginFailureLockCount + "次密码输入错误,您的账号将被锁定!");
							return ajaxJson("M0004",
									ApiConstantUtil
											.getM0004(loginFailureLockCount));
						} else {
							// return ajax(Status.error,"您的用户名或密码错误!");
							return ajaxJson("M0001", ApiConstantUtil.M0001);
						}
					} else {
						// return ajax(Status.error,"您的用户名或密码错误!");
						return ajaxJson("M0001", ApiConstantUtil.M0001);
					}
				}
			} else {
				// return ajax(Status.error,"您的用户名或密码错误!");
				return ajaxJson("M0001", ApiConstantUtil.M0001);
			}

			loginUser.setLastTime(new Date());
			loginUser.setLastIp(getRequestRemoteAddr());
			loginUser.setLoginTime(loginUser.getLoginTime() == null ? 0
					: (loginUser.getLoginTime() + 1));

			// 只要登录成功，清空之前密码输入错误次数
			loginUser.setLoginFailureCount(0);
			loginUser.setIsLock(false);
			loginUser.setLockedDate(null);

			this.userService.updateUserByLoginSuccess(loginUser);
			if (loginUser.getPhoneStatus() != 1
					&& loginUser.getEmailStatus() != 1) {
				// 手机认证没过
				loginRedirectUrl = "/user/validatePhone.do";
			}
			// 保存token	
			this.userService.saveToken(loginUser);
			removeSession(ConstantUtil.RANDOM_COOKIE_NAME);

			log.info("【" + loginUser.getUsername() + "】会员登录成功");

			if(StringUtils.isNotEmpty(oldDeviceToken) && !oldDeviceToken.equals(deviceToken)){
				
				try {

                    /*PushUtil push = new PushUtil();
					if(oldDeviceToken.length() ==44){
						push.pushAndoridUnicast(oldDeviceToken, "登录提醒", "登录提醒", "您的账号在其它设备登录","tc");
					}else if(oldDeviceToken.length() ==64){
						push.pushIosUnicast(oldDeviceToken, "您的账号在其它设备登录", "","tc");
					}*/

                    // added by xsc
                    PushForBiz.pushWhenLogin(oldDeviceToken);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			LoginBean model = new LoginBean();
			model.setRcd("R0001");

			model.setUserId(loginUser.getId());
			model.setUsername(loginUser.getUsername());
			model.setRealName(loginUser.getRealName());
			model.setRealStatus(loginUser.getRealStatus());
			model.setToken(loginUser.getToken());
			return ajax(model);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJson("S0001", ApiConstantUtil.S0001);
		}

	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getLoginRedirectUrl() {
		return loginRedirectUrl;
	}

	public void setLoginRedirectUrl(String loginRedirectUrl) {
		this.loginRedirectUrl = loginRedirectUrl;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public File getCardFile1() {
		return cardFile1;
	}

	public void setCardFile1(File cardFile1) {
		this.cardFile1 = cardFile1;
	}

	public File getCardFile2() {
		return cardFile2;
	}

	public void setCardFile2(File cardFile2) {
		this.cardFile2 = cardFile2;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public AreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public File getFaceImgFile() {
		return faceImgFile;
	}

	public void setFaceImgFile(File faceImgFile) {
		this.faceImgFile = faceImgFile;
	}

	public File getUploadDataFile() {
		return uploadDataFile;
	}

	public void setUploadDataFile(File uploadDataFile) {
		this.uploadDataFile = uploadDataFile;
	}

	// 获取客服管理员
	public List<Admin> getKefuAdminList() {
		List<Admin> adminList = userService.getKefuAdminList();
		return adminList;
	}

	public String getKefu() {
		return kefu;
	}

	public void setKefu(String kefu) {
		this.kefu = kefu;
	}

	public String getPasswordRecoverKey() {
		return passwordRecoverKey;
	}

	public void setPasswordRecoverKey(String passwordRecoverKey) {
		this.passwordRecoverKey = passwordRecoverKey;
	}

	public Integer getTenderAutoRank() {
		return tenderAutoRank;
	}

	public void setTenderAutoRank(Integer tenderAutoRank) {
		this.tenderAutoRank = tenderAutoRank;
	}

	public BigDecimal getTenderAutoAccountRate() {
		return tenderAutoAccountRate;
	}

	public void setTenderAutoAccountRate(BigDecimal tenderAutoAccountRate) {
		this.tenderAutoAccountRate = tenderAutoAccountRate;
	}

	public Integer getTenderAutoWayOne() {
		return tenderAutoWayOne;
	}

	public void setTenderAutoWayOne(Integer tenderAutoWayOne) {
		this.tenderAutoWayOne = tenderAutoWayOne;
	}

	public Integer getTenderAutoWayTwo() {
		return tenderAutoWayTwo;
	}

	public void setTenderAutoWayTwo(Integer tenderAutoWayTwo) {
		this.tenderAutoWayTwo = tenderAutoWayTwo;
	}

	public Integer getTenderAutoWayThree() {
		return tenderAutoWayThree;
	}

	public void setTenderAutoWayThree(Integer tenderAutoWayThree) {
		this.tenderAutoWayThree = tenderAutoWayThree;
	}

	public BigDecimal getTenderAbleMoney() {
		return tenderAbleMoney;
	}

	public void setTenderAbleMoney(BigDecimal tenderAbleMoney) {
		this.tenderAbleMoney = tenderAbleMoney;
	}

	public BigDecimal getTenderContinueMoney() {
		return tenderContinueMoney;
	}

	public void setTenderContinueMoney(BigDecimal tenderContinueMoney) {
		this.tenderContinueMoney = tenderContinueMoney;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public String getFriendUsername() {
		return friendUsername;
	}

	public void setFriendUsername(String friendUsername) {
		this.friendUsername = friendUsername;
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

	public String getMailUrl() {
		return mailUrl;
	}

	public void setMailUrl(String mailUrl) {
		this.mailUrl = mailUrl;
	}

	public String getNewPayPassword() {
		return newPayPassword;
	}

	public void setNewPayPassword(String newPayPassword) {
		this.newPayPassword = newPayPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsGesture() {
		return isGesture;
	}

	public void setIsGesture(String isGesture) {
		this.isGesture = isGesture;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

}
