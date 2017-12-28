package com.qmd.action.user;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.mode.admin.Admin;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.phone.PhoneLimit;
import com.qmd.mode.place.PlaceChilder;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.Agency;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserInfo;
import com.qmd.mode.util.Hongbao;
import com.qmd.mode.util.Scrollpic;
import com.qmd.service.area.AreaService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.phone.PhoneLimitService;
import com.qmd.service.place.PlaceChilderService;
import com.qmd.service.user.*;
import com.qmd.service.util.ListingService;
import com.qmd.util.*;
import com.qmd.util.md5.PWDUtil;
import com.ysd.biz.YueSmsUtils;
import com.ysd.ipyy.IPYYSMSResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户登录-注册
 * 
 * @author Xsf
 * 
 */
@Service("userAction")
public class UserAction extends BaseAction {

	private static final long serialVersionUID = -3317398075827939752L;
	Logger log = Logger.getLogger(UserAction.class);
	@Resource
	UserService userService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	AreaService areaService;
	@Resource
	ListingService listingService;
	@Resource
	PhoneLimitService phoneLimitService;
	@Resource
	AgencyService agencyService;
	@Resource
	BorrowService borrowService;
	@Resource
	AccountBankService accountBankService;
	@Resource
	PlaceChilderService placeChilderService;
	@Resource
	UserHongbaoService userHongbaoService;
	
	private User user;
	private String p;
	private List<User> userList;
	private String message;
	private String areaId;
	private String loginRedirectUrl;
	private String r;// 推广参数
	private String f;// 手机推广参数
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
	private String mycode;

	private Agency agency;
	private int agencyId;
	private List<Borrow> borrowList;

	private String strtype;
	private String password;
	private BigDecimal rechageHBMoney;//获得注册红包总金额
	
	private String tokenFlg;//防重复提交token
	
	public void validate() {
		log.info("validate succ------------------");
	}

	/**
	 * 首页
	 * 
	 * @return
	 */
	@Action(value = "/user/index", results = { @Result(name = "success", location = "/content/index.ftl", type = "freemarker") })
	public String index() {
		log.info("跳转到首页");
		return SUCCESS;
	}

	/**
	 * 跳转到登录页面
	 * 
	 * @return
	 */
	@Action(value = "/user/login", results = { @Result(name = "success", location = "/content/login/login.ftl", type = "freemarker") })
	public String login() {
		log.info("跳转到登录页面");
		if (getCookie(User.USER_USERNAME_COOKIE_NAME) != null) {
			removeCookie(User.USER_USERNAME_COOKIE_NAME);
		}
		if (getCookie(User.USER_USERTYPE_COOKIE_NAME) != null) {
			removeCookie(User.USER_USERTYPE_COOKIE_NAME);
		}
		if (getCookie(User.USER_USEREALSTATUS_COOKIE_NAME) != null) {
			removeCookie(User.USER_USEREALSTATUS_COOKIE_NAME);
		}
		return SUCCESS;
	}

	@Action(value = "/user/agencyIndex", results = { @Result(name = "success", location = "/content/user/agenycenter.ftl", type = "freemarker") })
	public String agencyIndex() {
		agency = agencyService.getById(agencyId, new Agency());
		Pager agencyBorrow = new Pager();
		agencyBorrow.setPageSize(8);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agencyId", agencyId);
		map.put("orderBy", "b.`add_time` desc");
		agencyBorrow = borrowService.queryBorrowList(agencyBorrow, map);
		borrowList = (List<Borrow>) agencyBorrow.getResult();
		return SUCCESS;
	}

	/**
	 * 跳转到注册协议
	 * 
	 * @return
	 */
	@Action(value = "/regTreaty", results = { @Result(name = "success", location = "/content/user/reg_treaty.ftl", type = "freemarker") })
	public String treaty() {
		log.info("跳转到注册协议页面");
		return SUCCESS;
	}

	/**
	 * 用户登录检查用户名
	 * 
	 * @return
	 */
	@Action(value = "/user/checkUsername", results = { @Result(type = "plainText") })
	@InputConfig(resultName = "ajaxError")
	public String checkUsername() {
		log.info("用户登录时检查用户名");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", user.getUsername());
		if (this.userService.getUser(map) != null) {
			log.info("有此用户");
			return ajax("true");
		} else {
			log.info("用户名错误");
			return ajax("false");
		}
	}

	/**
	 * 用户登录检查用户名
	 * 
	 * @return
	 */
	@Action(value = "/user/checkPowsPhone", results = { @Result(type = "plainText") })
	@InputConfig(resultName = "ajaxError")
	public String checkPowsPhone() {
		log.info("用户登录时检查用户名");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", user.getPhone());
		if (this.userService.getUser(map) != null) {
			log.info("有此用户");
			return ajax("true");
		} else {
			log.info("用户名错误");
			return ajax("false");
		}
	}

	/**
	 * 验证码正确否
	 * */
	@Action(value = "/user/checkVerifyCode", results = { @Result(type = "plainText") })
	@InputConfig(resultName = "ajaxError")
	public String checkVerifyCode() {
		log.info("检查验证码");
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		if (!this.getMycode().equalsIgnoreCase(random)) {
			log.info("验证码输入错误!");
			return ajax("false");
		}
		log.info("验证码正确");
		return ajax("true");
	}

	/**
	 * 用户注册检查用户名
	 * 
	 * @return
	 */
	@Action(value = "/user/checkUsernameReg", results = { @Result(type = "plainText") })
	@InputConfig(resultName = "ajaxError")
	public String checkUsernameReg() {
		log.info("检查用户名");
		Map<String, Object> map = new HashMap<String, Object>();
		if (HasKeyWord.hasKeyWord(getServletContext(), user.getUsername())) {
			log.info("含敏感字符");
			return ajax("false");
		}
		map.put("username", user.getUsername());
		if (this.userService.getUser(map) == null) {
			log.info("没有此用户名,可以注册");
			return ajax("true");
		} else {
			log.info("用户名已注册!");
			return ajax("false");
		}
	}

	/**
	 * 检查Email【注册时用】
	 * 
	 * @return
	 */
	@Action(value = "/user/checkEmail", results = { @Result(type = "plainText") })
	@InputConfig(resultName = "ajaxError")
	public String checkEmail() {
		log.info("检查Email");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", user.getEmail());
		if (this.userService.getUser(map) == null) {
			log.info("没有用户注册过，此Email可用");
			return ajax("true");
		} else {
			log.info("用户注册过，此Email不可用");
			return ajax("false");
		}
	}

	/**
	 * 验证Email【邮箱认证用】
	 * 
	 * @return
	 */
	@Action(value = "/user/valEmail", results = { @Result(type = "plainText") })
	@InputConfig(resultName = "ajaxError")
	public String valEmail() {
		log.info("验证Email");
		User userLogin = getLoginUser();
		if (userLogin.getEmail() != null
				&& userLogin.getEmail().equals(user.getEmail())) {
			return ajax("true");
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("email", user.getEmail());
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

	/**
	 * 验证手机号
	 * 
	 * @return
	 */
	@Action(value = "/user/valPhone", results = { @Result(type = "plainText") })
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

	/**
	 * 通过邮件找回密码
	 * 
	 * @return
	 */
	@Action(value = "/user/ajaxCheckEmailByFindPwd", results = { @Result(type = "plainText") })
	@InputConfig(resultName = "ajaxError")
	public String ajaxCheckEmailByFindPwd() {
		log.info("检查Email");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", user.getEmail());
		map.put("emailStatus", 1);
		List<User> ul = userService.getUserList(map);

		// if(this.userService.getUser(map)==null){
		if (ul == null || ul.size() != 1) {
			log.info("没有用户使用过，不能通过此邮箱找回密码");
			return ajax("false");
		} else {
			log.info("用户注册过，可以通过此Email找回密码");
			return ajax("true");
		}
	}

	/**
	 * 会员验证登录
	 * 
	 * @return
	 */
	@Action(value = "/user/ajaxLogin", results = { @Result(type = "json") })
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String ajaxLogin() throws Exception {
		try {
			log.info("会员验证登录");
			// String random =
			// this.getMemcachedByCookie(ConstantUtil.RANDOM_COOKIE_NAME).toString();
			String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
			if (this.getMycode() == null
					|| !this.getMycode().equalsIgnoreCase(random)) {
				return ajax(Status.warn, "验证码输入错误!");
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username", user.getUsername());

			User loginUser = this.userService.getUser(map);
			// 解除会员账户锁定
			if (loginUser != null) {
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
					return ajax(Status.error, "您的账号已被禁用,无法登录，如有疑问请联系客服人员!");
				}

				if (loginUser.getIsLock() == null || loginUser.getIsLock()) {
					return ajax(Status.error, "您的账号已被锁定，如有疑问请联系客服人员!");
				}
				if (!userService.isPassword(user.getUsername(),
						user.getPassword(), "0")) {

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
							return ajax(Status.error, "若连续"
									+ loginFailureLockCount
									+ "次密码输入错误,您的账号将被锁定!");
						} else {
							return ajax(Status.error, "您的用户名或密码错误!");
						}
					} else {
						return ajax(Status.error, "您的用户名或密码错误!");
					}
				}
			} else {
				return ajax(Status.error, "您的用户名或密码错误!");
			}

			loginUser.setLastTime(new Date());
			loginUser.setLastIp(getRequestRemoteAddr());
			loginUser.setLoginTime(user.getLoginTime() == null ? 0 : user
					.getLoginTime() + 1);

			// 只要登录成功，清空之前密码输入错误次数
			loginUser.setLoginFailureCount(0);
			loginUser.setIsLock(false);
			loginUser.setLockedDate(null);

			this.userService.updateUserByLoginSuccess(loginUser);
			if (loginUser.getPhoneStatus() != 1) {
				// 手机认证没过
				loginRedirectUrl = getContextPath() +"/user/validatePhone.do";
				// loginRedirectUrl = "/member/validatePhone.do";

			} else {
				if (loginUser.getRealStatus() == 0)
					loginRedirectUrl = getContextPath() +"/userCenter/toBank.do";
			}

			setSession(User.USER_ID_SESSION_NAME, loginUser);
			setCookie(User.USER_USERNAME_COOKIE_NAME, URLEncoder.encode(
					loginUser.getUsername().toLowerCase(), "UTF-8"),
					User.USER_COOKIE_AGE);

			setCookie(User.USER_USERTYPE_COOKIE_NAME, URLEncoder.encode(
					loginUser.getTypeId().toString(), "UTF-8"),
					User.USER_COOKIE_AGE);

			setCookie(User.USER_USEREALSTATUS_COOKIE_NAME, URLEncoder.encode(
					loginUser.getRealStatus() == 1? "1" : "0",
					"UTF-8"), User.USER_COOKIE_AGE);

			removeSession(ConstantUtil.RANDOM_COOKIE_NAME);

			log.info("【" + loginUser.getUsername() + "】会员登录成功");

			if (StringUtils.isNotEmpty(loginRedirectUrl)
					&& !loginRedirectUrl.equals("null")) {
				//redirectUrl = getContextPath() + loginRedirectUrl;
				/*yueshang*/
				redirectUrl =  loginRedirectUrl;
			} else {
				redirectUrl = getContextPath() + "/userCenter/index.do";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajax(Status.success, redirectUrl);
	}

	/**
	 * 跳转到注册页面
	 * 
	 * @return
	 */
	// @Action(value="/user/reg",results={@Result(name="success",
	// location="/content/user/register.ftl", type="freemarker")})
	// public String reg(){
	// log.info("跳转到注册页面");
	// return SUCCESS;
	// }
	/**
	 * 跳转到验证手机页面
	 * 
	 * @return
	 */
	@Action(value = "/user/validatePhone", results = {
			@Result(name = "success", location = "/content/login/reg_phone.ftl", type = "freemarker"),
			@Result(name = "loginDo", location = "login", type = "redirectAction") })
	public String validatePhone() {
		if (getLoginUser() == null || getLoginUser().getId() == null) {
			log.info("跳转到登录页面");
			return "loginDo";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.getLoginUser().getId());
		user = this.userService.getUser(map);
		log.info("跳转到验证手机页面");
		return SUCCESS;
	}

	// /**
	// * 跳转到注册页面
	// * @return
	// */
	// @Action(value="/user/reg",results={@Result(name="investor",
	// location="/content/user/reg_investor.ftl", type="freemarker"),
	// @Result(name="borrower", location="/content/user/reg_borrower.ftl",
	// type="freemarker")})
	// public String reg(){
	// log.info("跳转到注册页面");
	// String typeId = getRequest().getParameter("typeId");
	// if(StringUtils.isNotEmpty(typeId)){
	// return "borrower";//借款人注册页面
	// }else{
	// r = getCookie(User.SPREAD_COOKIE);
	// if(StringUtils.isNotEmpty(r)){
	// User u = new User();
	// u.setTgNo(r);
	// List<User> user_list = userService.queryListByObject(u);
	// if(user_list != null && user_list.size() == 1){
	// u = user_list.get(0);
	// }else{
	// return "investor";//投资人注册页面
	// }
	// friendUsername = u.getUsername();
	// return "fd";
	// }else{
	// return "investor";//投资人注册页面
	// }
	//
	//
	// }
	//
	// }
	//
	//
	//
	// /**
	// * 会员注册
	// * @return
	// * @throws UnsupportedEncodingException
	// */
	// @Action(value = "/user/register",
	// results={@Result(name =
	// "success",location="/content/user/reg_investor_two.ftl",type="freemarker")}
	// // results={@Result(name =
	// "success",location="/content/user/reg_success.ftl",type="freemarker")}
	// )
	// public String register() throws UnsupportedEncodingException{
	// // String random =
	// this.getMemcachedByCookie(ConstantUtil.RANDOM_COOKIE_NAME).toString();
	// String random = (String)getSession(ConstantUtil.RANDOM_COOKIE_NAME);
	// removeSession(ConstantUtil.RANDOM_COOKIE_NAME);
	// // redirectUrl="/qmd/content/user/register.ftl";
	// if(!this.getMycode().equalsIgnoreCase(random)){
	// addActionError("验证码输入错误!");
	// removeSession(ConstantUtil.RANDOM_COOKIE_NAME);
	// return "error_ftl";
	// }
	// // if(StringUtils.isEmpty(user.getEmail())){
	// // addActionError("请填写邮箱!");
	// // return "error_ftl";
	// // }
	// if(StringUtils.isEmpty(user.getPhone())){
	// addActionError("请填写手机号码!");
	// return "error_ftl";
	// }
	// Pattern p =
	// Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
	// Matcher m = p.matcher(user.getPhone());
	// if(!m.matches()){
	// addActionError("手机号码格式不正确!");
	// return "error_ftl";
	// }
	// Map<String,Object> map_phone = new HashMap<String,Object>();
	// map_phone.put("phone", user.getPhone());
	// if(this.userService.getUser(map_phone) != null){
	// addActionError("用户手机号码已存在!");
	// return "error_ftl";
	// }
	// user.setUsername(user.getPhone());
	//
	// // Map<String,Object> map_email = new HashMap<String,Object>();
	// // map_email.put("email", user.getEmail());
	// // if(this.userService.getUser(map_email) != null){
	// // addActionError("用户邮箱已存在!");
	// // return "error_ftl";
	// // }
	//
	// if(!user.getPassword().matches(".*[a-zA-Z]+.*$")){
	// addActionError("密码必须包含字母!");
	// return "error_ftl";
	// }
	// if(user.getPassword().length()<8 || user.getPassword().length()>16){
	// addActionError("密码长度必须在8-16个字符之间!");
	// return "error_ftl";
	// }
	// log.info("会员注册开始");
	// //用户基本信息添加
	// user.setCreateDate(new Date());
	// user.setModifyDate(new Date());
	// user.setTypeId(user.getTypeId());//0：投资人;1：借贷人
	// user.setMemberType(user.getMemberType()==null?0:user.getMemberType());//会员类型【0:个人;1:企业】
	// user.setUsername(user.getUsername());
	// // user.setEmail(user.getEmail());
	//
	// random = CommonUtil.getRandomString(8);
	// user.setRandomNum(random);//保存随机8位密码
	// //user.setPassword(
	// MD5.getMD5Str(MD5.getMD5Str(user.getPassword())+random));//对密码进行加密处理
	// user.setPassword(PWDUtil.encode(user.getPassword(), random));//对密码进行加密处理
	//
	// user.setAddIp(getRequestRemoteAddr());
	// user.setLastIp(getRequestRemoteAddr());
	// // user.setEmailCertificationKey(this.userService.buildRecoverKey());
	// this.userService.addUser(user);
	// // log.info("注册成功并开始发送邮箱验证邮件");
	// // mailService.sendEmailRecover(user);
	// // log.info("注册邮箱验证邮件发送成功");
	// log.info("注册成功开始自动登录");
	//
	// // Map<String,Object> mapuserid = new HashMap<String,Object>();
	// // mapuserid.put("id",user.getId());
	// // user = this.userService.getUser(mapuserid);
	// // Map<String,Object> mapMemcached = new HashMap<String,Object>();
	// // mapMemcached.put(ConstantUtil.USER_NAME, user);
	// // UserInfo userInfo = this.userInfoService.findByUserId(user.getId());
	// // mapMemcached.put(ConstantUtil.USERINFO_NAME, userInfo);
	// // // 写入会员登录Cookie
	// // setCookie(ConstantUtil.USER_USERNAME_COOKIE_NAME,
	// URLEncoder.encode(user.getUsername().toLowerCase(), "UTF-8"));
	// // this.setMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME,
	// mapMemcached);//保存用户信息
	//
	// // 写入会员登录Session
	// setSession(User.USER_ID_SESSION_NAME, user);
	//
	// // 写入会员登录Cookie
	// setCookie(User.USER_USERNAME_COOKIE_NAME,
	// URLEncoder.encode(user.getUsername().toLowerCase(),
	// "UTF-8"),User.USER_COOKIE_AGE);
	//
	//
	// setCookie(User.USER_USERTYPE_COOKIE_NAME,
	// URLEncoder.encode(user.getTypeId().toString(),
	// "UTF-8"),User.USER_COOKIE_AGE);
	//
	// setCookie(User.USER_USEREALSTATUS_COOKIE_NAME, URLEncoder.encode("0",
	// "UTF-8"),User.USER_COOKIE_AGE);
	//
	// log.info("自动登录成功");
	//
	// return SUCCESS;
	// }

	/**
	 * 跳转到注册页面
	 * 
	 * @return
	 */
	@Action(value = "/user/reg", results = { @Result(name = "investor", location = "/content/login/reg.ftl", type = "freemarker") })
	public String reg() {
		log.info("跳转到注册页面");
		removeSession(User.USER_ID_SESSION_NAME);
		removeSession(UserInfo.USER_INFO_ID_SESSION_NAME);
		
		setSession("tokenFlg",TokenProccessor.getInstance().makeToken());
		if (StringUtils.isNotEmpty(r)) {
			User u = new User();
			u.setTgNo(r);
			List<User> user_list = userService.queryListByObject(u);
			if (user_list != null && user_list.size() == 1) {
				u = user_list.get(0);
				friendUsername = u.getUsername();
			} 
		}
		return "investor";// 投资人注册页面

	}
	
	/**
	 * zdl 注册成功欢迎页
	 * @return
	 */
	@Action(value = "/user/regSuccess", results = { @Result(name = "success", location = "/content/login/loginSuccess.ftl",type = "freemarker") })
	public String regSuccess(){
		User login = getLoginUser();
		if(login==null||login.getId()==null){
			msg = "请登录";
			return "error_ftl";
		}
		//用户获注册红包总和
		Map<String,Object> queryUHBMap = new HashMap<String,Object>();
		queryUHBMap.put("userId", login.getId());
		queryUHBMap.put("source", 1);//1来源注册
		queryUHBMap.put("status", 0);//0状态未使用
		rechageHBMoney = userHongbaoService.getSelectSumMoney(queryUHBMap);
		return "success";
	}
	
	/**
	 * 会员注册
	 * @author zdl 修改
	 * @return
	 * @throws UnsupportedEncodingException
	 */
//	@Action(value = "/user/register", results = { @Result(name = "success", location = "/content/login/reg_finish.ftl", type = "freemarker") })
	@Action(value = "/user/register", results = { @Result(name = "success", location = "/user/regSuccess.do",type = "redirect") })
	public String register() throws UnsupportedEncodingException {
		// @zdl-m1-start:图形验证码已在发短信验证码时remove了此处无用，故注掉。
		//String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		//removeSession(ConstantUtil.RANDOM_COOKIE_NAME);
		// if (!this.getMycode().equalsIgnoreCase(random)) {
		// msg = "验证码输入错误";
		// removeSession(ConstantUtil.RANDOM_COOKIE_NAME);
		// return "error_ftl";
		// }
		// @zdl-m1-end;

		// @zdl-m2-start:每次请求都应remove session中的手机验证码，以防表单重复提交
		String code = (String) getSession(User.USER_PHONE_NUM);
		removeSession(User.USER_PHONE_NUM);
		// @zdl-m2-end;
		

		
		if (StringUtils.isEmpty(user.getPhone())) {
			msg = "请填写手机号码!";
			return "error_ftl";
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(user.getPhone());
		if (!m.matches()) {
			msg = "手机号码格式不正确!";
			return "error_ftl";
		}
		Map<String, Object> map_phone = new HashMap<String, Object>();
		map_phone.put("phone", user.getPhone());
		if (this.userService.getUser(map_phone) != null) {
			msg = "用户手机号码已存在!";
			return "error_ftl";
		}
		user.setUsername(user.getPhone());

		if (!user.getPassword().matches(".*[a-zA-Z]+.*$")) {
			msg = "密码必须包含字母!";
			return "error_ftl";
		}
		if (user.getPassword().length() < 8 || user.getPassword().length() > 16) {
			msg = "密码长度必须在8-16个字符之间!";
			return "error_ftl";
		}

		
		/**yueshang*/
        if (ConfigUtil.isProdEnv()) {
			if (StringUtils.isBlank(codeReg)) {
				msg = "请输入短信验证码!";
				return "error_ftl";
			}
			
			if (!codeReg.equals(code)) {
				msg = "验证码错误!";
				msgUrl = "/user/reg.do";
				return "error_ftl";
			}
        }

		/**
		if(TokenProccessor.getInstance().isRepeatSubmit(tokenFlg, getSession("tokenFlg"))){
			msg = "不能重复提交表单";
			return "error_ftl";
			
		}
		
		removeSession("tokenFlg");
		**/
		// ----------推广加----------------

		String tgNo = r;// 推广链接-编号
		if (StringUtils.isNotEmpty(tgNo)) {
			msgUrl = getContextPath() + "/fd/" + tgNo ;
		} else {
			msgUrl = getContextPath() + "/user/reg.do";
		}

		log.info("会员注册开始");
		// 用户基本信息添加
		user.setCreateDate(new Date());
		user.setModifyDate(new Date());
		user.setTypeId(0);// TODO 0：投资人;1：借贷人
		user.setMemberType(0);// TODO 会员类型【0:个人;1:企业】
		// user.setUsername(user.getUsername());

		// ----------推广加-----------
//		if (StringUtils.isNotEmpty(tgNo)) {
//			User u = new User();
//			u.setTgNo(tgNo);
//			List<User> user_list = userService.queryListByObject(u);
//			if (user_list != null && user_list.size() == 1) {
//				u = user_list.get(0);
//				user.setTgOneLevelUserId(u.getId());// 一级推广人
//			}
//		}
		Map<String,Object> map = new HashMap<String,Object>();
		String placeCookie = getCookie(ConstantUtil.PLACE_COOKIE_NAME);
		if(StringUtils.isNotEmpty(placeCookie)){
			placeCookie = CommonUtil.decodeUrl(placeCookie);
			String flag = placeCookie.split("_")[0];
			if("1".equals(flag)){//渠道活动注册用户
				String spreadRandom = placeCookie.split("_")[1];
				map.clear();
				map.put("random", spreadRandom);
				map.put("status", 1);
				List<PlaceChilder> list = placeChilderService.queryListByMap(map);
				if(list != null && list.size() >0){
					PlaceChilder pc = list.get(0);
					user.setPlaceChilderId(pc.getId());
				}
			}else if ("0".equals(flag)){
				tgNo = placeCookie.split("_")[1];
				User u = new User();
				u.setTgNo(tgNo);
				List<User> user_list = userService.queryListByObject(u);
				if (user_list != null && user_list.size() == 1) {
					u = user_list.get(0);
					user.setTgOneLevelUserId(u.getId());// 一级推广人
				}
			}
		}
		// @zdl-m3-start:图形验证码那个变量被注掉了，所以此处自己声明了下
		// random = CommonUtil.getRandomString(8);
		String random = CommonUtil.getRandomString(8);
		// @zdl-m3-end;
		user.setRandomNum(random);// 保存随机8位密码
		user.setPassword(PWDUtil.encode(user.getPassword(), random));// 对密码进行加密处理

		user.setAddIp(getRequestRemoteAddr());
		user.setLastIp(getRequestRemoteAddr());
		user.setPhoneStatus(1);
		user.setPhoneCode(codeReg);
		
		String agent = getRequest().getHeader("user-agent"); 
		user.setBrowserType(agent);
		this.userService.addUser(user);
		log.info("注册成功开始自动登录");

		// 写入会员登录Session
		setSession(User.USER_ID_SESSION_NAME, user);

		// 写入会员登录Cookie
		setCookie(User.USER_USERNAME_COOKIE_NAME,
				URLEncoder.encode(user.getUsername().toLowerCase(), "UTF-8"),
				User.USER_COOKIE_AGE);

		setCookie(User.USER_USERTYPE_COOKIE_NAME,
				URLEncoder.encode(user.getTypeId().toString(), "UTF-8"),
				User.USER_COOKIE_AGE);

		setCookie(User.USER_USEREALSTATUS_COOKIE_NAME,
				URLEncoder.encode("0", "UTF-8"), User.USER_COOKIE_AGE);

		log.info("自动登录成功");
		
		//获取首次投资红包金额
		//hongbao = listingService.getHongbao(2);
		return SUCCESS;
	}
	
	/**
	 * 推广会员注册
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@Action(value = "/user/registerFd", results = { @Result(name = "success", location = "/content/user/reg_investor_two.ftl", type = "freemarker") }
	// results={@Result(name =
	// "success",location="/content/user/reg_success.ftl",type="freemarker")}
	)
	public String registerFd() throws UnsupportedEncodingException {
		// String random =
		// this.getMemcachedByCookie(ConstantUtil.RANDOM_COOKIE_NAME).toString();
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		removeSession(ConstantUtil.RANDOM_COOKIE_NAME);
		// redirectUrl="/qmd/content/user/register.ftl";
		// if(!this.getMycode().equals(random)){
		// addActionError("验证码输入错误!");
		// removeSession(ConstantUtil.RANDOM_COOKIE_NAME);
		// return "error_ftl";
		// }
		if (StringUtils.isEmpty(user.getPhone())) {
			addActionError("请填写手机号码!");
			return "error_ftl";
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(user.getPhone());
		if (!m.matches()) {
			addActionError("手机号码格式不正确!");
			return "error_ftl";
		}
		Map<String, Object> map_phone = new HashMap<String, Object>();
		map_phone.put("phone", user.getPhone());
		if (this.userService.getUser(map_phone) != null) {
			addActionError("用户手机号码已存在!");
			return "error_ftl";
		}
		user.setUsername(user.getPhone());
		// if(!user.getUsername().matches("[\\u0391-\\uFFE5\\w]+")){
		// addActionError("用户名只允许包含中文、英文、数字和下划线!");
		// return "error_ftl";
		// }
		// if(HasKeyWord.hasKeyWord(getServletContext(),user.getUsername())){
		// addActionError("用户名含敏感字符,请重新输入!");
		// return "error_ftl";
		// }
		// if(user.getUsername().length()<4 || user.getUsername().length()>16){
		// addActionError("用户名长度必须在4-16个字符之间!");
		// return "error_ftl";
		// }
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", user.getUsername());
		if (this.userService.getUser(map) != null) {
			addActionError("用户名已存在!");
			return "error_ftl";
		}

		if (!user.getPassword().matches(".*[a-zA-Z]+.*$")) {
			addActionError("密码必须包含字母!");
			return "error_ftl";
		}
		if (user.getPassword().length() < 8 || user.getPassword().length() > 16) {
			addActionError("密码长度必须在8-16个字符之间!");
			return "error_ftl";
		}
		// ----------推广加----------------

		String tgNo = r;// 推广链接-编号
		if (StringUtils.isNotEmpty(tgNo)) {
			msgUrl = getContextPath() + "/fd/" + tgNo + ".htm";
		} else {
			msgUrl = getContextPath() + "/user/reg.do";
		}
		// ----------推广加end----------------

		log.info("会员注册开始");
		// 用户基本信息添加
		user.setCreateDate(new Date());
		user.setModifyDate(new Date());
		user.setTypeId(user.getTypeId());// 0：投资人;1：借贷人
		user.setMemberType(user.getMemberType());
		user.setUsername(user.getUsername());
		user.setEmail(user.getEmail());

		// ----------推广加-----------
		if (StringUtils.isNotEmpty(tgNo)) {
			User u = new User();
			u.setTgNo(tgNo);
			List<User> user_list = userService.queryListByObject(u);
			if (user_list != null && user_list.size() == 1) {
				u = user_list.get(0);
				user.setTgOneLevelUserId(u.getId());// 一级推广人
			}
		}
		// ----------推广加end-----------

		random = CommonUtil.getRandomString(8);
		user.setRandomNum(random);// 保存随机8位密码
		// user.setPassword(
		// MD5.getMD5Str(MD5.getMD5Str(user.getPassword())+random));//对密码进行加密处理
		user.setPassword(PWDUtil.encode(user.getPassword(), random));// 对密码进行加密处理

		user.setAddIp(getRequestRemoteAddr());
		user.setLastIp(getRequestRemoteAddr());
		// user.setEmailCertificationKey(this.userService.buildRecoverKey());
		this.userService.addUser(user);
		// log.info("注册成功并开始发送邮箱验证邮件");
		// mailService.sendEmailRecover(user);
		// log.info("注册邮箱验证邮件发送成功");
		log.info("注册成功开始自动登录");

		// Map<String,Object> mapuserid = new HashMap<String,Object>();
		// mapuserid.put("id",user.getId());
		// user = this.userService.getUser(mapuserid);
		// Map<String,Object> mapMemcached = new HashMap<String,Object>();
		// mapMemcached.put(ConstantUtil.USER_NAME, user);
		// UserInfo userInfo = this.userInfoService.findByUserId(user.getId());
		// mapMemcached.put(ConstantUtil.USERINFO_NAME, userInfo);
		// // 写入会员登录Cookie
		// setCookie(ConstantUtil.USER_USERNAME_COOKIE_NAME,
		// URLEncoder.encode(user.getUsername().toLowerCase(), "UTF-8"));
		// this.setMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME,
		// mapMemcached);//保存用户信息

		// 写入会员登录Session
		setSession(User.USER_ID_SESSION_NAME, user);

		// 写入会员登录Cookie
		setCookie(User.USER_USERNAME_COOKIE_NAME,
				URLEncoder.encode(user.getUsername().toLowerCase(), "UTF-8"),
				User.USER_COOKIE_AGE);

		setCookie(User.USER_USERTYPE_COOKIE_NAME,
				URLEncoder.encode(user.getTypeId().toString(), "UTF-8"),
				User.USER_COOKIE_AGE);

		setCookie(User.USER_USEREALSTATUS_COOKIE_NAME,
				URLEncoder.encode("0", "UTF-8"), User.USER_COOKIE_AGE);

		log.info("自动登录成功");

		return SUCCESS;
	}

	/**
	 * 手机推广会员注册
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@Action(value = "/user/checkRegPhone", results = { @Result(name = "success", location = "/content/user/reg_investor_ptwo.ftl", type = "freemarker") })
	public String checkRegPhone() throws UnsupportedEncodingException {
		if (StringUtils.isEmpty(user.getPhone())) {
			addActionError("请填写手机号码!");
			return "error_phone";
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(user.getPhone());
		if (!m.matches()) {
			addActionError("手机号码格式不正确!");
			return "error_phone";
		}
		Map<String, Object> map_phone = new HashMap<String, Object>();
		map_phone.put("phone", user.getPhone());
		if (this.userService.getUser(map_phone) != null) {
			addActionError("用户手机号码已存在!");
			return "error_phone";
		}
		return SUCCESS;
	}

	/**
	 * 推广会员注册
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@Action(value = "/user/registerPfd", results = { @Result(name = "success", location = "/content/user/reg_investor_pthere.ftl", type = "freemarker") }
	// results={@Result(name =
	// "success",location="/content/user/reg_success.ftl",type="freemarker")}
	)
	public String registerPfd() throws UnsupportedEncodingException {
		// String random =
		// this.getMemcachedByCookie(ConstantUtil.RANDOM_COOKIE_NAME).toString();
		// String coderandom = (String)
		// getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		// setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		// if (mycode == null || !mycode.equals(coderandom)) {
		// addActionError("验证码输入错误!");
		// return "error_phone";
		// }
		removeSession(ConstantUtil.RANDOM_COOKIE_NAME);
		
		
		
        	
		String message = "手机验证失败";
		if (!"".equals(user.getPhone()) && user.getPhone() != null) {
			
			/**yueshang*/
	        if (ConfigUtil.isProdEnv()) {
	        	
				if (!"".equals(codeReg) && codeReg != null) {
					System.out.println(getSession(User.USER_PHONE_NUM));
					if (codeReg.equals(getSession(User.USER_PHONE_NUM))) {
						user.setPhoneStatus(1);
						user.setPhoneCode(codeReg);
					} else {
						addActionError("手机短信码不正确!");
						return "error_phone";
					}
				} else {
					addActionError("短信码不能为空");
					return "error_phone";
				}
			}
		} else {
			addActionError("手机号不能为空");
			return "error_phone";
		}
       

		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		removeSession(ConstantUtil.RANDOM_COOKIE_NAME);

		user.setUsername(user.getPhone());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", user.getUsername());
		if (this.userService.getUser(map) != null) {
			addActionError("用户名已存在!");
			return "error_phone";
		}

		if (!user.getPassword().matches(".*[a-zA-Z]+.*$")) {
			addActionError("密码必须包含字母!");
			return "error_ftl";
		}
		if (user.getPassword().length() < 8 || user.getPassword().length() > 16) {
			addActionError("密码长度必须在8-16个字符之间!");
			return "error_ftl";
		}
		// ----------推广加----------------

		String tgNo = r;// 推广链接-编号
		if (StringUtils.isNotEmpty(tgNo)) {
			msgUrl = getContextPath() + "/p_" + tgNo;
		} else {
			msgUrl = getContextPath() + "/user/reg.do";
		}
		// ----------推广加end----------------

		log.info("会员注册开始");
		// 用户基本信息添加
		user.setCreateDate(new Date());
		user.setModifyDate(new Date());
		user.setTypeId(user.getTypeId());// 0：投资人;1：借贷人
		user.setMemberType(user.getMemberType());
		user.setUsername(user.getUsername());
		user.setEmail(user.getEmail());

		// ----------推广加-----------
		if (StringUtils.isNotEmpty(tgNo)) {
			User u = new User();
			u.setTgNo(tgNo);
			List<User> user_list = userService.queryListByObject(u);
			if (user_list != null && user_list.size() == 1) {
				u = user_list.get(0);
				user.setTgOneLevelUserId(u.getId());// 一级推广人
			}
		}
		// ----------推广加end-----------

		random = CommonUtil.getRandomString(8);
		user.setRandomNum(random);// 保存随机8位密码
		// user.setPassword(
		// MD5.getMD5Str(MD5.getMD5Str(user.getPassword())+random));//对密码进行加密处理
		user.setPassword(PWDUtil.encode(user.getPassword(), random));// 对密码进行加密处理

		user.setAddIp(getRequestRemoteAddr());
		user.setLastIp(getRequestRemoteAddr());
		// user.setEmailCertificationKey(this.userService.buildRecoverKey());
		this.userService.addUser(user);
		hongbao = listingService.getHongbao(1);
		
		// log.info("注册成功并开始发送邮箱验证邮件");
		// mailService.sendEmailRecover(user);
		// log.info("注册邮箱验证邮件发送成功");
		log.info("注册成功开始自动登录");

		// Map<String,Object> mapuserid = new HashMap<String,Object>();
		// mapuserid.put("id",user.getId());
		// user = this.userService.getUser(mapuserid);
		// Map<String,Object> mapMemcached = new HashMap<String,Object>();
		// mapMemcached.put(ConstantUtil.USER_NAME, user);
		// UserInfo userInfo = this.userInfoService.findByUserId(user.getId());
		// mapMemcached.put(ConstantUtil.USERINFO_NAME, userInfo);
		// // 写入会员登录Cookie
		// setCookie(ConstantUtil.USER_USERNAME_COOKIE_NAME,
		// URLEncoder.encode(user.getUsername().toLowerCase(), "UTF-8"));
		// this.setMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME,
		// mapMemcached);//保存用户信息

		// 写入会员登录Session
		setSession(User.USER_ID_SESSION_NAME, user);

		// 写入会员登录Cookie
		setCookie(User.USER_USERNAME_COOKIE_NAME,
				URLEncoder.encode(user.getUsername().toLowerCase(), "UTF-8"),
				User.USER_COOKIE_AGE);

		setCookie(User.USER_USERTYPE_COOKIE_NAME,
				URLEncoder.encode(user.getTypeId().toString(), "UTF-8"),
				User.USER_COOKIE_AGE);

		setCookie(User.USER_USEREALSTATUS_COOKIE_NAME,
				URLEncoder.encode("0", "UTF-8"), User.USER_COOKIE_AGE);

		log.info("自动登录成功");

		return SUCCESS;
	}

	/**
	 * 跳转到邮件找回密码页面
	 * 
	 * @return
	 */
	@Action(value = "/user/lostpass", results = { @Result(name = "success", location = "/content/user/lostpass_by_email.ftl", type = "freemarker") })
	public String lostpass() {
		return SUCCESS;
	}

	/**
	 * 跳转到邮件找回密码页面
	 * 
	 * @return
	 */
	@Action(value = "/user/lostpass/usephone", results = { @Result(name = "success", location = "/content/user/lostpass_by_phone.ftl", type = "freemarker") })
	public String usephone() {
		return SUCCESS;
	}

	// 验证邮件方法
	@Action(value = "/user/ec")
	public String emailCheck() {
		log.info("进入验证邮件方法");
		User userLogin = null;
		String email_k = "";
		redirectUrl = getContextPath() + "/user/index.do";
		if (StringUtils.isNotEmpty(p)) {
			p = CommonUtil.decodeUrl(p);// 解密
			if (StringUtils.isNotEmpty(p) && p.split("##").length > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", p.split("##")[0].toString());
				userLogin = this.userService.getUser(map);
				email_k = p.split("##")[1].toString();
			}
		} else {
			addActionError("对不起,此邮件验证链接已失效!");
			return ERROR;
		}
		if (userLogin == null
				|| !StringUtils.equalsIgnoreCase(
						userLogin.getEmailCertificationKey(), email_k)) {
			addActionError("对不起,此邮件验证链接已失效!");
			return ERROR;
		}
		Date emailRecoverKeyBuildDate = this.userService
				.getRecoverKeyBuildDate(email_k);
		Date emailRecoverKeyExpiredDate = DateUtils.addMinutes(
				emailRecoverKeyBuildDate, ConstantUtil.RECOVER_KEY_PERIOD);
		if (new Date().after(emailRecoverKeyExpiredDate)) {
			addActionError("对不起,此邮件验证链接已过期!");
			return ERROR;
		}
		userLogin.setEmailCertificationKey(null);
		// userLogin.setEmailStatus(ConstantUtil.APPLY_STATUS_YES);
		userLogin.setModifyDate(new Date());
		this.userService.updateEmail_tg(userLogin, obtainUserIp());
		log.info("验证邮件成功");
		// 更新用户信息
		// this.replaceMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME,
		// ConstantUtil.USER_NAME, userLogin);
		setSession(User.USER_ID_SESSION_NAME, userLogin);
		addActionMessage("验证邮件成功");
		redirectUrl = getContextPath() + "/userCenter/toEmail.do";
		return "success_ftl";
	}


	/**
	 * 获取手机验证码 找回密码用
	 * 
	 * @return
	 */
	@Action(value = "/user/ajaxGetPhoneCode", results = { @Result(type = "json") })
	@InputConfig(resultName = "ajaxError")
	public String ajaxGetPhoneCode() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", user.getUsername());
		map.put("phone", user.getPhone());
		User getuser = this.userService.getUser(map);
		if (getuser == null) {
			return ajax(Status.error, "用户名与手机号码不匹配，无法获取验证码！");
		}
		if (!getuser.getPhoneStatus().equals(ConstantUtil.APPLY_STATUS_YES)) {
			return ajax(Status.error, "此手机号没有认证，无法获取验证码！");
		}

		log.info("获取手机验证码");
		removeCookie(ConstantUtil.USER_PHONE_COOKIE_NAME);
		String code = CommonUtil.getRandomString(6);
		setCookie(ConstantUtil.USER_PHONE_COOKIE_NAME, code, 60);
		log.info("手机号：" + user.getPhone() + ";验证码是:" + code.toString()
				+ ";phoneCodeCookie="
				+ getCookie(ConstantUtil.USER_PHONE_COOKIE_NAME));

		// 临时保存验证码【以后替换手机发送验证码】
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS_PARAMETER_NAME, Status.success);
		jsonMap.put(MESSAGE_PARAMETER_NAME, "验证码发送成功");
		jsonMap.put("code", code);
		return ajax(jsonMap);
	}

	/**
	 * 手机验证成功，跳转到修改密码页面
	 * 
	 * @return
	 */
	@Action(value = "/user/lostpass/findPwdByPhone", results = { @Result(name = "success", location = "/content/user/set_password.ftl", type = "freemarker") })
	public String findPwdByPhone() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", user.getPhone());
		user = userService.getUser(map);
		if (user.getPhoneCode().equals(codeReg)) {
			return SUCCESS;
		} else {
			addActionError("验证码输入不正确!");
			return "error_ftl";
		}

	}


	/**
	 * 用户登录检查用户名
	 * 
	 * @return
	 */
	@Action(value = "/user/checkForUserLogin", results = { @Result(type = "plainText") })
	@InputConfig(resultName = "ajaxError")
	public String checkForUserLogin() {
		User user = getLoginUser();
		if (user != null && user.getUsername() != null) {
			return ajax("true");
		}
		return ajax("false");
	}

	/**
	 * 投标校验
	 * 
	 * @return
	 */
	@Action(value = "/user/checkForTender", results = { @Result(type = "plainText") })
	@InputConfig(resultName = "ajaxError")
	public String checkForTender() {
		reloadUser();
		User user = getLoginUser();
		if (user == null || user.getUsername() == null) {
			return ajax("0");
		}

		if (user.getTypeId() == null || user.getTypeId() != 0) {
			return ajax("2");
		}
		Borrow b = borrowService.getBorrowById(Integer.parseInt(id));
		if ("17".equals(b.getType())) {
			if (user.getTasteMoney().compareTo(tenderAbleMoney) < 0) {
				return ajax("5");
			}
		} else {
			if (user.getAbleMoney().compareTo(tenderAbleMoney) < 0) {
				return ajax("3");
			}
		}
		return ajax("1");
	}

	/**
	 * 跳转到计算器
	 * 
	 * @return
	 */
	@Action(value = "/counterInit", results = { @Result(name = "success", location = "/content/user/income_counter.ftl", type = "freemarker") })
	public String counterInit() {
		log.info("跳转到计算器页面");
		return SUCCESS;
	}

	/**
	 * 跳转到自投设置页面
	 * 
	 * @return
	 */
	@Action(value = "/user/tenderAuto", results = { @Result(name = "success", location = "/content/user/tender_auto.ftl", type = "freemarker") })
	public String tenderAuto() {

		reloadUser();
		user = getLoginUser();

		tenderAutoWayOne = 0;// 1 按月付息，到期还本 (含一月标)
		tenderAutoWayTwo = 0;// 2按月分期还本息
		tenderAutoWayThree = 0;// 3到期还本息
		String ways = user.getAutoTenderRepayWay();
		if (ways != null) {
			String way[] = ways.split(",");
			if (way != null && way.length == 3) {
				for (int i = 0; i < way.length; i++) {
					if ("1".equals(way[i])) {
						if (i == 0) {
							tenderAutoWayOne = 1;
						} else if (i == 1) {
							tenderAutoWayTwo = 1;
						} else if (i == 2) {
							tenderAutoWayThree = 1;
						}
					}
				}
			}
		}

		tenderAutoRank = userService.queryTenderAutoRank(user.getId());

		// notList = new ArrayList<NotNumber>();
		strtype = user.getAutoTenderBorrowType();
		// if(StringUtils.isNotEmpty(strtype)){
		// if (strtype.endsWith(",")) {
		// strtype = strtype.substring(0, strtype.length() - 1);
		// }
		//
		// String[] tgs = strtype.split(",");
		//
		// for (int i = 0; i < tgs.length; i++) {
		//
		// NotNumber nt = new NotNumber();
		// nt.setNum(tgs[i]);
		// notList.add(nt);
		// }
		// }

		return SUCCESS;
	}

	/**
	 * 自投设置保存
	 * 
	 * @return
	 */
	@Action(value = "/user/saveTenderAuto", results = { @Result(name = "success", location = "/content/user/tender_auto.ftl", type = "freemarker") })
	public String saveTenderAuto() {

		// String random = (String)getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		// setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		// if(!this.getMycode().equalsIgnoreCase(random)){
		// addActionError("验证码输入错误!");
		// return "error_ftl";
		// }
		//
		// if(!userService.isPassword(getLoginUser().getUsername(),
		// user.getPayPassword(), "1")){
		// addActionError("交易密码输入错误!");
		// return "error_ftl";
		// }

		String ways = "";
		if (tenderAutoWayOne != null && tenderAutoWayOne == 1) {
			ways = ways + "1,";
		} else {
			ways = ways + "0,";
		}
		if (tenderAutoWayTwo != null && tenderAutoWayTwo == 1) {
			ways = ways + "1,";
		} else {
			ways = ways + "0,";
		}
		if (tenderAutoWayThree != null && tenderAutoWayThree == 1) {
			ways = ways + "1";
		} else {
			ways = ways + "0";
		}
		user.setAutoTenderRepayWay(ways);
		user.setAutoTenderBorrowType(strtype);
		user.setId(getLoginUser().getId());
		userService.updateTenderAuto(user);

		addActionMessage("自动投标设置成功");
		redirectUrl = getContextPath() + "/user/tenderAuto.do";
		return "success_ftl";
	}

	/**
	 * 自投说明页面
	 * 
	 * @return
	 */
	@Action(value = "/user/tenderAutoRemark", results = { @Result(name = "success", location = "/content/user/tender_auto_remark.ftl", type = "freemarker") })
	public String tenderAutoRemark() {
		String obj = listingService.getKeyValue("auto_tender_account_rate");
		if (obj == null) {
			tenderAutoAccountRate = BigDecimal.ZERO;
		} else {
			tenderAutoAccountRate = new BigDecimal(obj);
		}
		return SUCCESS;
	}

	private Scrollpic scrollpic;
	private Hongbao hongbao;
	/**
	 * 通过推广链接 跳转到 注册 页面， 保存推广参数 到cookie中
	 * 
	 * @return
	 */
	@Action(value = "/spread", results = {
			@Result(name = "investor", location = "/content/spread/place.ftl", type = "freemarker"),
			@Result(name = "success", location = "/content/login/reg.ftl", type = "freemarker") })
	public String toSpread() {
		log.info("通过推广链接  跳转到 注册 页面");
			User u = new User();
			u.setTgNo(r);
			List<User> user_list = userService.queryListByObject(u);
			if (user_list != null && user_list.size() == 1) {
				u = user_list.get(0);
				String cookie = CommonUtil.encodeUrl("0_"+r);
				System.out.println(cookie +"，解密后="+CommonUtil.decodeUrl(cookie));
				setCookie(ConstantUtil.PLACE_COOKIE_NAME, cookie, ConstantUtil.PLACE_COOKIE_AGE);
				friendUsername = u.getUsername();
				List<Scrollpic> list = listingService.findScrollpicList(3);
				if(list != null && list.size() >0){
					scrollpic = list.get(0);
				}
				//获取首次投资红包金额
				hongbao = listingService.getHongbao(2);
				return "investor";
			}else{
				return  SUCCESS;
			}
	}

	
	/**
	 * 发送手机验证码(没有图形验证码校验)
	 * @author zdl
	 */
	@Action(value = "/sendPhoneCodeNew", results = { @Result(type = "json") })
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String sendPhoneCodeNew() {
		log.info("发送手机验证码");
		// result :0 成功 1 失败
		String smsResult = YueSmsUtils.fail("初始化失败");

		if ("".equals(phoneReg) || phoneReg == null) {
			smsResult = YueSmsUtils.fail("系统无法获取手机号");
		} else {
			log.info("检查手机号是否注册");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("phone", phoneReg);
			List<User> userSelecListt = this.userService.getUserList(map);

			if (userSelecListt.size() < 1) {

				// ------------------------五分钟短信限制----------------------------------------------
				Integer phoneNumber = Integer.valueOf(listingService.getKeyValue(ConstantUtil.PHONE_LIMIT));// 获取短信限制
				Map map_p = new HashMap<Object, String>();
				map_p.put("phone", phoneReg);
//				List<PhoneLimit> phoneLimitList = phoneLimitService.queryListByMap(map_p);
				List<PhoneLimit> phoneLimitList = phoneLimitService.getPhoneLimitList(phoneReg);
				
				if (phoneLimitList.size() < 1) {
					PhoneLimit pl = new PhoneLimit();
					pl.setPhone(phoneReg);// 限制手机号
					pl.setIp(getRequestRemoteAddr());// ip
					pl.setCreateDate(new Date());// 最近时间
					pl.setSendNumber(1);// 发送次数
					phoneLimitService.save(pl);
				} else {
					PhoneLimit pl = phoneLimitList.get(0);
					long noeTime = new Date().getTime();
					long latsTime = pl.getCreateDate().getTime();
					long cha = noeTime - latsTime;
					
					if(cha < 60 * 1000){
//						reason = "60秒后再发";
//						smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
//                        smsResult = YueSmsUtils.fail("60秒后再发");
                        return ajax(YueSmsUtils.fail("60秒后再发"));
					}
					
					if (cha > 5 * 60 * 1000*288) {// 一天只能发3条
						// 时间差大于5分钟
						pl.setIp(getRequestRemoteAddr());// ip
						pl.setCreateDate(new Date());// 最近时间
						pl.setSendNumber(1);// 发送次数
						phoneLimitService.update(pl);
					} else {
						// 时间差小于5分钟
						if (pl.getSendNumber() > phoneNumber) {
							// 发送次数大于设定的次数
//							reason = "该手机号获取验证码太频繁，请5分钟后再尝试";
//							smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\""
//									+ reason + "\"}";
							return ajax(YueSmsUtils.fail("该手机号获取验证码太频繁，请5分钟后再尝试"));
						} else {
							// 发送次数小于设定次数
							pl.setIp(getRequestRemoteAddr());// ip
							pl.setSendNumber(pl.getSendNumber() + 1);// 发送次数
							pl.setCreateDate(new Date());
							phoneLimitService.update(pl);
						}

					}
				}
				// ------------------------五分钟短信限制end----------------------------------------------

				// --------发送注册验证码开始----------------
				log.info("发送注册验证码开始");
				String phoneCode = CommonUtil.getRandomString(6);// 6位短信验证码
//				phoneCode = "111111";// @zdl测试短信验证码
				if (getLoginUser() == null) {
					setSession(User.USER_PHONE_NUM, phoneCode);
				} else {
					// ---------------保存验证码--------------
					User uu = new User();
					uu.setPhoneCode(phoneCode);
					uu.setId(getLoginUser().getId());
					userService.updatePhoneCode(uu);
					// ---------------保存验证码 end--------------
				}
				System.out.println(">>>>>" + phoneCode);

                IPYYSMSResult ipyysmsResult = YueSmsUtils.sendForMemberRegister(phoneReg, phoneCode);
                if (ipyysmsResult.ok()) {
                    smsResult = YueSmsUtils.success();
                } else {
                    smsResult = YueSmsUtils.fail(ipyysmsResult.getMessage());
                }

				/*try {
					HttpUtil hu = new HttpUtil();
					String content = CommonUtil.CODE_CONTENT + phoneCode+"。欢迎您的加入，轻松乐享财富增值。";
					NoteResult noteResult = hu.sendSms(content, phoneReg);// 发送短信
					// result = new
					// Client(phoneReg,CommonUtil.CODE_CONTENT+phoneCode).mdsmssend();
//					noteResult.setResult("0");// @zdl测试短信验证码
					if ("0".equals(noteResult.getResult())) {
						reason = "短信发送成功";
						temp = "{\"result\":\"" + 0 + "\",\"reason\":\""
								+ reason + "\"}";
					} else {
						reason = noteResult.getDescription();
						temp = "{\"result\":\"" + 1 + "\",\"reason\":\""
								+ reason + "\"}";
					}

				} catch (Exception e) {
					reason = "短信功能无法使用";
					temp = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason
							+ "\"}";
				}*/

				log.info("发送注册验证码结束");

				// --------发送注册验证码结束----------------

			} else if (userSelecListt.size() > 0){

				log.info("用户注册过，此phone不可用");
//				reason = "手机号已经注册过帐号";
//				smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason
//						+ "\"}";
                smsResult = YueSmsUtils.fail("手机号已经注册过帐号");
            } else {

				// --------发送注册验证码开始----------------

				// ------------------------五分钟短信限制----------------------------------------------
				Integer phoneNumber = Integer.valueOf(listingService.getKeyValue(ConstantUtil.PHONE_LIMIT));// 获取短信限制
				Map map_p = new HashMap<Object, String>();
				map_p.put("phone", phoneReg);
//				List<PhoneLimit> phoneLimitList = phoneLimitService.queryListByMap(map_p);

				List<PhoneLimit> phoneLimitList = phoneLimitService.getPhoneLimitList(phoneReg);
				
				if (phoneLimitList.size() < 1) {
					PhoneLimit pl = new PhoneLimit();
					pl.setPhone(phoneReg);// 限制手机号
					pl.setIp(getRequestRemoteAddr());// ip
					pl.setCreateDate(new Date());// 最近时间
					pl.setSendNumber(1);// 发送次数
					phoneLimitService.save(pl);
				} else {
					PhoneLimit pl = phoneLimitList.get(0);
					long noeTime = new Date().getTime();
					long latsTime = pl.getCreateDate().getTime();
					long cha = noeTime - latsTime;

					if(cha < 60 * 1000){
//						reason = "60秒后再发";
//						smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
						return ajax(YueSmsUtils.fail("60秒后再发"));
					}
					
					if (cha > 5 * 60 * 1000*288) {
						// 时间差大于5分钟
						pl.setIp(getRequestRemoteAddr());// ip
						pl.setCreateDate(new Date());// 最近时间
						pl.setSendNumber(1);// 发送次数
						phoneLimitService.update(pl);
					} else {
						// 时间差小于5分钟
						if (pl.getSendNumber() > phoneNumber) {
							// 发送次数大于设定的次数
//							reason = "该手机号获取验证码太频繁，请5分钟后再尝试";
//							smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\""
//									+ reason + "\"}";
							return ajax(YueSmsUtils.fail("该手机号获取验证码太频繁，请5分钟后再尝试"));
						} else {
							// 发送次数小于设定次数
							pl.setIp(getRequestRemoteAddr());// ip
							pl.setSendNumber(pl.getSendNumber() + 1);// 发送次数
							pl.setCreateDate(new Date());
							phoneLimitService.update(pl);
						}

					}
				}
				// ------------------------五分钟短信限制end----------------------------------------------

				log.info("发送注册验证码开始");
				String phoneCode = CommonUtil.getRandomString(6);// 6位短信验证码
				if (getLoginUser() == null) {
					setSession(User.USER_PHONE_NUM, phoneCode);
				} else {
					// ---------------保存验证码--------------
					User uu = new User();
					uu.setPhoneCode(phoneCode);
					uu.setId(getLoginUser().getId());
					userService.updatePhoneCode(uu);
					// ---------------保存验证码 end--------------
				}

                IPYYSMSResult ipyysmsResult = YueSmsUtils.sendForCommon(phoneReg, phoneCode);
                if (ipyysmsResult.ok()) {
                    smsResult = YueSmsUtils.success();
                } else {
                    smsResult = YueSmsUtils.fail(ipyysmsResult.getMessage());
                }

				/*try {
					HttpUtil hu = new HttpUtil();
					NoteResult noteResult = hu.sendSms(CommonUtil.CODE_CONTENT
							+ phoneCode, phoneReg);// 发送短信
					 result = new
					 Client(phoneReg,CommonUtil.CODE_CONTENT+phoneCode).mdsmssend();//发送短信

					if ("0".equals(noteResult.getResult())) {
						reason = "短信发送成功";
						smsResult = "{\"result\":\"" + 0 + "\",\"reason\":\""
								+ reason + "\"}";
					} else {
						reason = noteResult.getDescription();
						smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\""
								+ reason + "\"}";
					}
				} catch (Exception e) {
					reason = "短信功能无法使用";
					smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason
							+ "\"}";
				}*/

				log.info("发送注册验证码结束");

				// --------发送注册验证码结束----------------

			}
		}
		return ajax(smsResult);
	}
	
	
	
	/**
	 * 发送手机验证码
	 * 
	 * @return
	 */
	@Action(value = "/sendPhoneCode", results = { @Result(type = "json") })
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String sendPhoneCode() {
		log.info("发送手机验证码");
		// result :0 成功 1 失败
//		String reason = "初始化失败";
		String smsResult = YueSmsUtils.fail("初始化失败"); // "{\"result\":\"" + 1 + "\",\"reason\":\" " + reason + "\"}";
		
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);// 图形验证码
		removeSession(ConstantUtil.RANDOM_COOKIE_NAME);
		if(StringUtils.isEmpty(random)){
//			reason = "请获取图形验证码";
//			smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
			return ajax(YueSmsUtils.fail("请获取图形验证码"));
		}
		 if (!mycode.equalsIgnoreCase(random)) {
//			reason = "图形验证码输入错误";
//			smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
			return ajax(YueSmsUtils.fail("图形验证码输入错误"));
		 }
		
		

		if ("".equals(phoneReg) || phoneReg == null) {
//			reason = "系统无法获取手机号";
//			smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
            smsResult = YueSmsUtils.fail("系统无法获取手机号");
        } else {
			log.info("检查手机号是否注册");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("phone", phoneReg);
			List<User> userSelecListt = this.userService.getUserList(map);

			if (userSelecListt.size() < 1) {

				// ------------------------五分钟短信限制----------------------------------------------
				Integer phoneNumber = Integer.valueOf(listingService.getKeyValue(ConstantUtil.PHONE_LIMIT));// 获取短信限制
				Map map_p = new HashMap<Object, String>();
				map_p.put("phone", phoneReg);
//				List<PhoneLimit> phoneLimitList = phoneLimitService.queryListByMap(map_p);
				List<PhoneLimit> phoneLimitList = phoneLimitService.getPhoneLimitList(phoneReg);
				
				if (phoneLimitList.size() < 1) {
					PhoneLimit pl = new PhoneLimit();
					pl.setPhone(phoneReg);// 限制手机号
					pl.setIp(getRequestRemoteAddr());// ip
					pl.setCreateDate(new Date());// 最近时间
					pl.setSendNumber(1);// 发送次数
					phoneLimitService.save(pl);
				} else {
					PhoneLimit pl = phoneLimitList.get(0);
					Date d=new Date();
					long noeTime = d.getTime();
					long latsTime = pl.getCreateDate().getTime();
					System.out.println("=========="+ new Date()+"====================="+pl.getCreateDate());
					long cha = noeTime - latsTime;
					
					if(cha < 60 * 1000){
//						reason = "60秒后再发";
//						smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
						return ajax(YueSmsUtils.fail("60秒后再发"));
					}
					
					if (cha > 5 * 60 * 1000*288) {// 一天只能发3条
						// 时间差大于5分钟
						pl.setIp(getRequestRemoteAddr());// ip
						pl.setCreateDate(d);// 最近时间
						pl.setSendNumber(1);// 发送次数
						phoneLimitService.update(pl);
					} else {
						// 时间差小于5分钟
						if (pl.getSendNumber() > phoneNumber) {
							// 发送次数大于设定的次数
//							reason = "该手机号获取验证码太频繁，请5分钟后再尝试";
//							smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\""+ reason + "\"}";
							return ajax(YueSmsUtils.fail("该手机号获取验证码太频繁，请5分钟后再尝试"));
						} else {
							// 发送次数小于设定次数
							pl.setIp(getRequestRemoteAddr());// ip
							pl.setSendNumber(pl.getSendNumber() + 1);// 发送次数
							pl.setCreateDate(d);
							phoneLimitService.update(pl);
						}

					}
				}
				// ------------------------五分钟短信限制end----------------------------------------------

				// --------发送注册验证码开始----------------
				log.info("发送注册验证码开始");
				String phoneCode = CommonUtil.getRandomString(6);// 6位短信验证码
//				phoneCode = "111111";// @zdl测试短信验证码
				if (getLoginUser() == null) {
					setSession(User.USER_PHONE_NUM, phoneCode);
				} else {
					// ---------------保存验证码--------------
					User uu = new User();
					uu.setPhoneCode(phoneCode);
					uu.setId(getLoginUser().getId());
					userService.updatePhoneCode(uu);
					// ---------------保存验证码 end--------------
				}
				System.out.println(">>>>>" + phoneCode);

                IPYYSMSResult ipyysmsResult = YueSmsUtils.sendForMemberRegister(phoneReg, phoneCode);
                if (ipyysmsResult.ok()) {
                    smsResult = YueSmsUtils.success();
                } else {
                    smsResult = YueSmsUtils.fail(ipyysmsResult.getMessage());
                }
                /*try {
					HttpUtil hu = new HttpUtil();
					String content = CommonUtil.CODE_CONTENT + phoneCode+"。欢迎您的加入，轻松乐享财富增值。";
					NoteResult noteResult = hu.sendSms(content, phoneReg);// 发送短信
					// result = new
					// Client(phoneReg,CommonUtil.CODE_CONTENT+phoneCode).mdsmssend();
//					noteResult.setResult("0");// @zdl测试短信验证码
					if ("0".equals(noteResult.getResult())) {
						reason = "短信发送成功";
						temp = "{\"result\":\"" + 0 + "\",\"reason\":\""
								+ reason + "\"}";
					} else {
						reason = noteResult.getDescription();
						temp = "{\"result\":\"" + 1 + "\",\"reason\":\""
								+ reason + "\"}";
					}

				} catch (Exception e) {
					reason = "短信功能无法使用";
					temp = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason
							+ "\"}";
				}*/

				log.info("发送注册验证码结束");

				// --------发送注册验证码结束----------------

			} else if (userSelecListt.size() > 0){

				log.info("用户注册过，此phone不可用");
//				reason = "手机号已经注册过帐号";
//				smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason+ "\"}";
                smsResult = YueSmsUtils.fail("手机号已经注册过帐号");
            } else {

				// --------发送注册验证码开始----------------

				// ------------------------五分钟短信限制----------------------------------------------
				Integer phoneNumber = Integer.valueOf(listingService.getKeyValue(ConstantUtil.PHONE_LIMIT));// 获取短信限制
				Map map_p = new HashMap<Object, String>();
				map_p.put("phone", phoneReg);
//				List<PhoneLimit> phoneLimitList = phoneLimitService.queryListByMap(map_p);

				List<PhoneLimit> phoneLimitList = phoneLimitService.getPhoneLimitList(phoneReg);
				
				if (phoneLimitList.size() < 1) {
					PhoneLimit pl = new PhoneLimit();
					pl.setPhone(phoneReg);// 限制手机号
					pl.setIp(getRequestRemoteAddr());// ip
					pl.setCreateDate(new Date());// 最近时间
					pl.setSendNumber(1);// 发送次数
					phoneLimitService.save(pl);
				} else {
					PhoneLimit pl = phoneLimitList.get(0);
					long noeTime = new Date().getTime();
					long latsTime = pl.getCreateDate().getTime();
					long cha = noeTime - latsTime;

					if(cha < 60 * 1000){
//						reason = "60秒后再发";
//						smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
						return ajax(YueSmsUtils.fail("60秒后再发"));
					}
					
					if (cha > 5 * 60 * 1000*288) {
						// 时间差大于5分钟
						pl.setIp(getRequestRemoteAddr());// ip
						pl.setCreateDate(new Date());// 最近时间
						pl.setSendNumber(1);// 发送次数
						phoneLimitService.update(pl);
					} else {
						// 时间差小于5分钟
						if (pl.getSendNumber() > phoneNumber) {
							// 发送次数大于设定的次数
//							reason = "该手机号获取验证码太频繁，请5分钟后再尝试";
//							smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\""
//									+ reason + "\"}";
							return ajax(YueSmsUtils.fail("该手机号获取验证码太频繁，请5分钟后再尝试"));
						} else {
							// 发送次数小于设定次数
							pl.setIp(getRequestRemoteAddr());// ip
							pl.setCreateDate(new Date());
							pl.setSendNumber(pl.getSendNumber() + 1);// 发送次数
							phoneLimitService.update(pl);
						}

					}
				}
				// ------------------------五分钟短信限制end----------------------------------------------

				log.info("发送注册验证码开始");
				String phoneCode = CommonUtil.getRandomString(6);// 6位短信验证码
				if (getLoginUser() == null) {
					setSession(User.USER_PHONE_NUM, phoneCode);
				} else {
					// ---------------保存验证码--------------
					User uu = new User();
					uu.setPhoneCode(phoneCode);
					uu.setId(getLoginUser().getId());
					userService.updatePhoneCode(uu);
					// ---------------保存验证码 end--------------
				}

                IPYYSMSResult ipyysmsResult = YueSmsUtils.sendForCommon(phoneReg, phoneCode);
                if (ipyysmsResult.ok()) {
                    smsResult = YueSmsUtils.success();
                } else {
                    smsResult = YueSmsUtils.fail(ipyysmsResult.getMessage());
                }

                /*try {
					HttpUtil hu = new HttpUtil();
					NoteResult noteResult = hu.sendSms(CommonUtil.CODE_CONTENT
							+ phoneCode, phoneReg);// 发送短信
					// result = new
					// Client(phoneReg,CommonUtil.CODE_CONTENT+phoneCode).mdsmssend();//发送短信

					if ("0".equals(noteResult.getResult())) {
						reason = "短信发送成功";
						smsResult = "{\"result\":\"" + 0 + "\",\"reason\":\""
								+ reason + "\"}";
					} else {
						reason = noteResult.getDescription();
						smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\""
								+ reason + "\"}";
					}
				} catch (Exception e) {
					reason = "短信功能无法使用";
					smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason
							+ "\"}";
				}*/

				log.info("发送注册验证码结束");

				// --------发送注册验证码结束----------------

			}
		}
		return ajax(smsResult);
	}

	/**
	 * 验证用户时发送手机验证码
	 * 
	 * @return
	 */
	@Action(value = "/sendCheckPhoneCode", results = { @Result(type = "json") })
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String sendCheckPhoneCode() {
		log.info("发送手机验证码");
		// result :0 成功 1 失败
		String reason = "初始化失败";
		String smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\" " + reason
				+ "\"}";
		String phoneCode_reg = null;// 验证码
		
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);// 图形验证码
		removeSession(ConstantUtil.RANDOM_COOKIE_NAME);
		if(StringUtils.isEmpty(random)){
			reason = "请获取图形验证码";
			smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
			return ajax(smsResult);
		}
		 if (!this.getMycode().equalsIgnoreCase(random)) {
			reason = "图形验证码输入错误";
			smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
			return ajax(smsResult);
		 }
		 
		if ("".equals(phoneReg) || phoneReg == null) {
			reason = "系统无法获取手机号";
			smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
		} else {
			log.info("检查手机号是否注册");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("phone", phoneReg);
			List<User> userSelecListt = this.userService.getUserList(map);

			if (userSelecListt.size() < 1) {
				log.info("用户不存在");
				reason = "不存在对应手机号用户";
				smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason
						+ "\"}";

			} else {

				// --------发送注册验证码开始----------------

				// ------------------------五分钟短信限制----------------------------------------------
				Integer phoneNumber = Integer.valueOf(listingService
						.getKeyValue(ConstantUtil.PHONE_LIMIT));// 获取短信限制
				Map map_p = new HashMap<Object, String>();
				map_p.put("phone", phoneReg);
//				List<PhoneLimit> phoneLimitList = phoneLimitService.queryListByMap(map_p);
				
				List<PhoneLimit> phoneLimitList = phoneLimitService.getPhoneLimitList(phoneReg);
				if (phoneLimitList.size() < 1) {
					PhoneLimit pl = new PhoneLimit();
					pl.setPhone(phoneReg);// 限制手机号
					pl.setIp(getRequestRemoteAddr());// ip
					pl.setCreateDate(new Date());// 最近时间
					pl.setSendNumber(1);// 发送次数
					phoneLimitService.save(pl);
				} else {
					PhoneLimit pl = phoneLimitList.get(0);
					long noeTime = new Date().getTime();
					long latsTime = pl.getCreateDate().getTime();
					long cha = noeTime - latsTime;

					if(cha < 60 * 1000){
						reason = "60秒后再发";
						smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
						return ajax(smsResult);
					}
					if (cha > 5 * 60 * 1000*288) {
						// 时间差大于5分钟
						pl.setIp(getRequestRemoteAddr());// ip
						pl.setCreateDate(new Date());// 最近时间
						pl.setSendNumber(1);// 发送次数
						phoneLimitService.update(pl);
					} else {
						// 时间差小于5分钟
						if (pl.getSendNumber() > phoneNumber) {
							// 发送次数大于设定的次数
							reason = "该手机号获取验证码太频繁，请5分钟后再尝试";
							smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\""
									+ reason + "\"}";
							return ajax(smsResult);
						} else {
							// 发送次数小于设定次数
							pl.setIp(getRequestRemoteAddr());// ip
							pl.setSendNumber(pl.getSendNumber() + 1);// 发送次数
							phoneLimitService.update(pl);
						}

					}
				}
				// ------------------------五分钟短信限制end----------------------------------------------

				log.info("发送注册验证码开始");
				String phoneCode = CommonUtil.getRandomString(6);// 6位短信验证码

				// ---------------保存验证码--------------
				User uu = new User();
				uu.setPhoneCode(phoneCode); 
//				uu.setPhoneCode("111111");//@zdl测试短信验证码
				uu.setId(userSelecListt.get(0).getId());
				userService.updatePhoneCode(uu);
				// ---------------保存验证码 end--------------

                IPYYSMSResult ipyysmsResult = YueSmsUtils.sendForForgetPwd(phoneReg, phoneCode);
                if (ipyysmsResult.ok()) {
                    smsResult = YueSmsUtils.success();
                } else {
                    smsResult = YueSmsUtils.fail(ipyysmsResult.getMessage());
                }

                /*try {
					HttpUtil hu = new HttpUtil();
					NoteResult noteResult = hu.sendSms(CommonUtil.CODE_CONTENT + phoneCode+"。您正在找回密码，如不是您本人操作，请立即联系客服。", phoneReg);// 发送短信
					// result = new
					// Client(phoneReg,CommonUtil.CODE_CONTENT+phoneCode).mdsmssend();//发送短信
					
//					noteResult.setResult("0");//@zdl测试短信验证码
					if ("0".equals(noteResult.getResult())) {
						reason = "短信发送成功";
						smsResult = "{\"result\":\"" + 0 + "\",\"reason\":\""
								+ reason + "\"}";
					} else {
						reason = noteResult.getDescription();
						smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\""
								+ reason + "\"}";
					}
				} catch (Exception e) {
					reason = "短信功能无法使用";
					smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason
							+ "\"}";
				}*/

				log.info("发送注册验证码结束");

				// --------发送注册验证码结束----------------

			}
		}
		return ajax(smsResult);
	}

	/**
	 * 验证手机号
	 * 
	 * @return
	 */
	@Action(value = "/checkPhone", results = { @Result(name = "success", location = "/content/login/reg_finish.ftl", type = "freemarker") })
	public String checkPhone() {
		log.info("验证手机验证码");
		msg = "手机验证失败";
		try {
			if (!"".equals(user.getPhone()) && user.getPhone() != null) {
				if (!"".equals(codeReg) && codeReg != null) {
					User uu = userService.getById(getLoginUser().getId(),
							new User());
					if (codeReg.equals(uu.getPhoneCode())) {
						User loginUser = getLoginUser();
						loginUser.setPhone(user.getPhone());
						loginUser.setPhoneStatus(1);
						loginUser.setPhoneCode(uu.getPhoneCode());
						userService.update(loginUser);

						removeCookie(User.USER_USERNAME_COOKIE_NAME);
						removeSession(User.USER_ID_SESSION_NAME);
						setSession(User.USER_ID_SESSION_NAME, loginUser);
						setCookie(User.USER_USERNAME_COOKIE_NAME,
								URLEncoder.encode(loginUser.getUsername()
										.toLowerCase(), "UTF-8"),
								User.USER_COOKIE_AGE);

						setCookie(User.USER_USERTYPE_COOKIE_NAME,
								URLEncoder.encode(loginUser.getTypeId()
										.toString(), "UTF-8"),
								User.USER_COOKIE_AGE);

						setCookie(
								User.USER_USEREALSTATUS_COOKIE_NAME,
								URLEncoder.encode(
										loginUser.getRealStatus() == 1 ? "1"
												: "0", "UTF-8"),
								User.USER_COOKIE_AGE);

						removeSession(ConstantUtil.RANDOM_COOKIE_NAME);
						return SUCCESS;
					} else {
						user.setId(uu.getId());
						msg = "验证码不正确";
					}
				} else {
					msg = "验证码不能为空";
				}
			} else {
				msg = "手机号不能为空";
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		redirectUrl = getContextPath() + "/user/validatePhone.do";
		return "error_ftl";
	}

	/**
	 * 会员中心验证手机号
	 * 
	 * @return
	 */
	@Action(value = "/checkUserPhone", results = { @Result(name = "success", location = "/userCenter/toPhone.do", type = "redirect") })
	public String checkUserPhone() {
		log.info("验证手机验证码");
		String message = "手机验证失败";
		if (!"".equals(user.getPhone()) && user.getPhone() != null) {
			if (!"".equals(codeReg) && codeReg != null) {
				User uu = userService.getById(getLoginUser().getId(),
						new User());
				if (codeReg.equals(uu.getPhoneCode())) {
					User loginUser = getLoginUser();
					loginUser.setPhone(user.getPhone());
					loginUser.setPhoneStatus(1);
					userService.update(loginUser);
					message = "恭喜你验证成功";
				} else {
					message = "验证码不正确";
				}
			} else {
				message = "验证码不能为空";
			}
		} else {
			message = "手机号不能为空";
		}
		addActionMessage(message);
		redirectUrl = getContextPath() + "/userCenter/toPhone.do";
		return "success_ftl";
	}

	/**
	 * 跳转到验证邮箱页面
	 * 
	 * @return
	 */
	@Action(value = "/user/validateEmail", results = {
			@Result(name = "success", location = "/content/user/reg_investor_two_email.ftl", type = "freemarker"),
			@Result(name = "loginDo", location = "login", type = "redirectAction") })
	public String validateEmail() {
		if (getLoginUser() == null || getLoginUser().getId() == null) {
			log.info("跳转到登录页面");
			return "loginDo";
		}
		log.info("跳转到验证邮箱页面");
		return SUCCESS;
	}


	// 验证注册邮件方法
	@Action(value = "/user/re")
	public String emailRegister() {
		log.info("进入验证注册邮件方法");
		User userLogin = null;
		String email_k = "";
		redirectUrl = getContextPath() + "/user/index.do";
		if (StringUtils.isNotEmpty(p)) {
			p = CommonUtil.decodeUrl(p);// 解密
			if (StringUtils.isNotEmpty(p) && p.split("##").length > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", p.split("##")[0].toString());
				userLogin = this.userService.getUser(map);
				email_k = p.split("##")[1].toString();
			}
		} else {
			addActionError("对不起,此邮件验证链接已失效!");
			return ERROR;
		}
		if (userLogin == null
				|| !StringUtils.equalsIgnoreCase(
						userLogin.getEmailCertificationKey(), email_k)) {
			addActionError("对不起,此邮件验证链接已失效!");
			return ERROR;
		}
		Date emailRecoverKeyBuildDate = this.userService
				.getRecoverKeyBuildDate(email_k);
		Date emailRecoverKeyExpiredDate = DateUtils.addMinutes(
				emailRecoverKeyBuildDate, ConstantUtil.RECOVER_KEY_PERIOD);
		if (new Date().after(emailRecoverKeyExpiredDate)) {
			addActionError("对不起,此邮件验证链接已过期!");
			return ERROR;
		}
		userLogin.setEmailCertificationKey(null);
		// userLogin.setEmailStatus(ConstantUtil.APPLY_STATUS_YES);
		userLogin.setModifyDate(new Date());
		this.userService.updateEmail_tg(userLogin, obtainUserIp());
		log.info("验证邮件成功");
		// 更新用户信息
		// this.replaceMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME,
		// ConstantUtil.USER_NAME, userLogin);
		setSession(User.USER_ID_SESSION_NAME, userLogin);
		addActionMessage("验证邮件成功");
		redirectUrl = getContextPath() + "/succsessTo.do";
		return "success_ftl";
	}

	/**
	 * 跳转到注册成功页面
	 * 
	 * @return
	 */
	@Action(value = "/succsessTo", results = { @Result(name = "success", location = "/content/user/reg_investor_there.ftl", type = "freemarker") })
	public String succsessTo() {
		log.info("跳转到注册成功页面");
		return SUCCESS;
	}

	/**
	 * 跳转到邮件发送成功页面
	 * 
	 * @return
	 */
	@Action(value = "/succsesEmail", results = { @Result(name = "success", location = "/content/user/reg_investor_email.ftl", type = "freemarker") })
	public String succsesEmail() {
		log.info("跳转到邮件发送成功页面");
		this.mailUrl = "http://" + mailUrl;
		return SUCCESS;
	}

	/**
	 * 修改支付密码
	 * 
	 * @return
	 */
	@Action(value = "/ajaxUpdatePassword", results = { @Result(type = "json") })
	@InputConfig(resultName = "ajaxError")
	public String ajaxUpdatePassword() {
		User userLogin = getLoginUser();
		String temp = "修改成功，请妥善保存，为了资金安全，请勿外泄";
		log.info("交易密码修改");
		if (userLogin.getPayPassword() == null) {
			temp = "设置成功，请妥善保存，为了资金安全，请勿外泄";
		}
		userLogin.setPayPassword(PWDUtil.encode(newPayPassword,
				userLogin.getRandomNum()));

		userLogin.setModifyDate(new Date());
		this.userService.updatePassowrd(userLogin);
		log.info("密码" + temp + "成功");
		// 更新用户信息
		setSession(User.USER_ID_SESSION_NAME, userLogin);
		return ajax(Status.success, temp);
	}

	/**
	 * 跳转到密码设置成功页面
	 * 
	 * @return
	 */
	@Action(value = "/mmsucess", results = { @Result(name = "success", location = "/content/user/reg_investor_there_message.ftl", type = "freemarker") })
	public String setSucess() {
		return SUCCESS;
	}

	/**
	 * 跳转到找回密码页面
	 * 
	 * @return
	 */
	@Action(value = "/user/findPsw", results = { @Result(name = "success", location = "/content/login/find_psw_username.ftl", type = "freemarker") })
	public String toFindPsw() {
		return SUCCESS;
	}

	/**
	 * 检查手机号码和验证码，找回密码的第一步
	 * 
	 * @return
	 */
	@Action(value = "/user/findPsw1", results = { @Result(name = "success", location = "/content/login/find_psw_realname.ftl", type = "freemarker") })
	public String checkPhoneAndCode() {

		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		if (this.getMycode() == null
				|| !this.getMycode().equalsIgnoreCase(random)) {
			msg = "验证码输入错误!";
			msgUrl = "";
			return "error_ftl";
		}

		if (user == null || StringUtils.isBlank(user.getPhone())) {
			msg = "请输入手机号码!";
			msgUrl = "";
			return "error_ftl";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", user.getPhone());
        map.put("typeId", "0");
        // 只查询投资者
		user = this.userService.getUser(map);

		if (user == null) {
			msg = "用户不存在!";
			msgUrl = "";
			return "error_ftl";
		}

		return SUCCESS;
	}

	/**
	 * 手机验证码，找回密码的第二步
	 * 
	 * @return
	 */
	@Action(value = "/user/findPsw2", results = { @Result(name = "success", location = "/content/login/find_psw_setPsw.ftl", type = "freemarker") })
	public String validateRealName() {

		if (user == null) {
			msg = "参数错误!";
			msgUrl = "";
			return "error_ftl";
		}

		// if(StringUtils.isBlank(user.getRealName())){
		// msg="请输入真实姓名!";
		// msgUrl="";
		// return "error_ftl";
		// }
		//
		// if(StringUtils.isBlank(user.getCardId())){
		// msg="请输入身份证号!";
		// msgUrl="";
		// return "error_ftl"
		// }

		if (StringUtils.isBlank(user.getPhone())) {
			msg = "请输入手机号!";
			msgUrl = "";
			return "error_ftl";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", user.getPhone());
        map.put("typeId", "0");
        User entity = this.userService.getUser(map);

		if (entity == null) {
			msg = "用户不存在!";
			msgUrl = "";
			return "error_ftl";
		}

		// if(!entity.getRealName().equals(user.getRealName())||!entity.getCardId().equals(user.getCardId())){
		// msg="输入的信息不正确!";
		// msgUrl="";
		// return "error_ftl";
		// }

		if (!codeReg.equals(entity.getPhoneCode())) {
			msg = "验证码不正确!";
			msgUrl = "";
			return "error_ftl";
		}

		setSession(User.SET_PSW_PHONE, user.getPhone());
		return SUCCESS;
	}

	/**
	 * 重置密码，找回密码的第三步
	 * 
	 * @return
	 */
	@Action(value = "/user/findPsw3", results = { @Result(name = "success", location = "/content/login/find_psw_finish.ftl", type = "freemarker") })
	public String setPsw() {
		String phone = (String) getSession(User.SET_PSW_PHONE);
		if (StringUtils.isBlank(phone)) {
			msg = "操作不正确!";
			msgUrl = "";
			return "error_ftl";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		User entity = this.userService.getUser(map);
		if (entity == null) {
			msg = "用户不存在!";
			msgUrl = "";
			return "error_ftl";
		}

		entity.setPassword(PWDUtil.encode(password, entity.getRandomNum()));// 对密码进行加密处理

		this.userService.updatePassowrd(entity);

		return SUCCESS;
	}

	/**
	 * 验证图片验证码
	 * @return
	 */
	@Action(value = "/user/ajaxVerifyCode", results = { @Result(type = "json") })
	@InputConfig(resultName = "ajaxError")
	public String ajaxVerifyCode() {
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		if (StringUtils.isEmpty(mycode)) {
			return ajax(Status.error, "请输入验证码");
		}
		if (!mycode.equalsIgnoreCase(random)) {
			return ajax(Status.error, "验证码错误");
		}
		return ajax(Status.success, "验证成功");
	}

	/**
	 * 验证是否实名认证
	 * 
	 * @return
	 */
	@Action(value = "/user/ajaxCheckRealStatus", results = { @Result(type = "json") })
	@InputConfig(resultName = "ajaxError")
	public String ajaxCheckRealStatus() {
		User u = getLoginUser();
		User entity = userService.get(u.getId());
		if (entity.getRealStatus() == 1) {
			return ajax(Status.success, "验证成功");
		}
		return ajax(Status.error, "您还未通过实名认证，点击确定进入实名认证页面。");
	}
	
	/**
	 * 验证是否绑定银行卡
	 * @return
	 */
	@Action(value = "/user/ajaxCheckBindBank", results = { @Result(type = "json") })
	public String ajaxCheckBindBank(){
		User u = getLoginUser();
		List<AccountBank> list = accountBankService.getAccountBankList(u.getId());
		if(list == null || list.size() == 0)
			return ajax(Status.error, "您还未绑定银行卡，请先绑定提现银行卡。");
		return ajax(Status.success, "验证成功");
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

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getMycode() {
		return mycode;
	}

	public void setMycode(String mycode) {
		this.mycode = mycode;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public int getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}

	public List<Borrow> getBorrowList() {
		return borrowList;
	}

	public void setBorrowList(List<Borrow> borrowList) {
		this.borrowList = borrowList;
	}

	public String getStrtype() {
		return strtype;
	}

	public void setStrtype(String strtype) {
		this.strtype = strtype;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Hongbao getHongbao() {
		return hongbao;
	}

	public void setHongbao(Hongbao hongbao) {
		this.hongbao = hongbao;
	}

	public Scrollpic getScrollpic() {
		return scrollpic;
	}

	public void setScrollpic(Scrollpic scrollpic) {
		this.scrollpic = scrollpic;
	}

	public BigDecimal getRechageHBMoney() {
		return rechageHBMoney;
	}

	public void setRechageHBMoney(BigDecimal rechageHBMoney) {
		this.rechageHBMoney = rechageHBMoney;
	}

	public String getTokenFlg() {
		return tokenFlg;
	}

	public void setTokenFlg(String tokenFlg) {
		this.tokenFlg = tokenFlg;
	}
	
	

}
