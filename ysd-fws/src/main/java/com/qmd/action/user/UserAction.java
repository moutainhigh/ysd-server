package com.qmd.action.user;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.mode.admin.Admin;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.user.User;
import com.qmd.service.agency.AgencyService;
import com.qmd.service.area.AreaService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.user.UserInfoService;
import com.qmd.service.user.UserService;
import com.qmd.service.util.ListingService;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;
import com.qmd.util.HasKeyWord;
import com.qmd.util.JsonUtil;
import com.qmd.util.bean.ShowBean;
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
	AgencyService agencyService;
	@Resource
	ListingService listingService;
	@Resource
	BorrowService borrowService;
	
	
	private User user;
	private String p;
	private List<User> userList;
	private String message;
	private String areaId;
	private String loginRedirectUrl;
	private File cardFile1;//身份证正面图片
	private File cardFile2;//身份证背面图片
	private File uploadDataFile; //上传开通借款功能资料
	private File faceImgFile;//头像图片
	private String newEmail;//新认证Email
	private String kefu;
	private String passwordRecoverKey;//密码Key
	private String r;//推广参数
	private String spreadNo;//推广码
	private String errorMsg;
	private String phoneCode;//手机短信验证码

	Map<String,Object> root = new HashMap<String,Object>();
	
	private String lowestMoney;//最小投资金额
	
	private String codechoose;//是否有推荐码
	
	private String friendUsername;//邀请人用户名
	private Integer tenderAutoRank;//自投排名
	private BigDecimal tenderAutoAccountRate;
	private Integer tenderAutoWayOne;// 1 按月付息，到期还本 (含一月标)
	private Integer tenderAutoWayTwo;//2按月分期还本息
	private Integer tenderAutoWayThree;// 3到期还本息
	
	private String begin;
	private String end;
	
	private List<ShowBean> showBeanList;
	
	public void validate(){
		log.info("validate succ------------------");
	}
	
	
	
	
	/**
	 * 跳转到登录页面/userCenter/index.do
	 * @return
	 */
	@Action(value="/index",results={@Result(name="success", location="/content/user/login.ftl", type="freemarker"),
									@Result(name = "index", location = "userCenter/index", type = "redirectAction")})
	public String login(){
		log.info("跳转到登录页面");
		if(getLoginUser() != null ){
			return "index";
		}
		return SUCCESS;
	}


	/**
	 * 用户登录检查用户名
	 * @return
	 */
	@Action(value="/user/checkUsername",results={@Result(type="plainText")})
	@InputConfig(resultName = "ajaxError")
	public String checkUsername(){
		log.info("用户登录时检查用户名");
		
		if (user==null||StringUtils.isEmpty( user.getUsername())) {
			log.info("用户名错误");
			return ajax("false");
		}
		
		Map<String,Object> map= new HashMap<String ,Object>();
//		map.put("username", user.getUsername());
		map.put("GuanLianDengLu", user.getUsername());
		if(this.userService.getUser(map)!=null){
			log.info("有此用户");
			return ajax("true");
		}else{
			log.info("用户名错误");
			return ajax("false");
		}
	}

	/**
	 * 用户注册检查用户名
	 * @return
	 */
	@Action(value="/user/checkUsernameReg",results={@Result(type="plainText")})
	@InputConfig(resultName = "ajaxError")
	public String checkUsernameReg(){
		log.info("检查用户名");
		Map<String,Object> map= new HashMap<String ,Object>();
		if(HasKeyWord.hasKeyWord(getServletContext(),user.getUsername())){
			log.info("含敏感字符");
			return ajax("false");
		}
		map.put("username", user.getUsername());
		if(this.userService.getUser(map)==null){
			log.info("没有此用户名,可以注册");
			return ajax("true");
		}else{
			log.info("用户名已注册!");
			return ajax("false");
		}
	}
	/**
	 * 检查Email【注册时用】
	 * @return
	 */
	@Action(value="/user/checkEmail",results={@Result(type="plainText")})
	@InputConfig(resultName = "ajaxError")
	public String checkEmail(){
		log.info("检查Email");
		Map<String,Object> map= new HashMap<String ,Object>();
		map.put("email", user.getEmail());
		if(this.userService.getUser(map)==null){
			log.info("没有用户注册过，此Email可用");
			return ajax("true");
		}else{
			log.info("用户注册过，此Email不可用");
			return ajax("false");
		}
	}
	
	/**
	 *  通过邮件找回密码
	 * @return
	 */
	@Action(value="/user/ajaxCheckEmailByFindPwd",results={@Result(type="plainText")})
	@InputConfig(resultName = "ajaxError")
	public String ajaxCheckEmailByFindPwd(){
		log.info("检查Email");
		Map<String,Object> map= new HashMap<String ,Object>();
		map.put("email", user.getEmail());
		if(this.userService.getUser(map)==null){
			log.info("没有用户使用过，不能通过此邮箱找回密码");
			return ajax("false");
		}else{
			log.info("用户注册过，可以通过此Email找回密码");
			return ajax("true");
		}
	}
	
	
	/**
	 * 会员验证登录
	 * @return
	 */
	@Action(value="/user/ajaxLogin",results={@Result(type="json")})
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String ajaxLogin() throws Exception{
		try{
		log.info("会员验证登录");
//		String random = this.getMemcachedByCookie(ConstantUtil.RANDOM_COOKIE_NAME).toString();
		String random = (String)getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		if(this.getMycode()==null||!this.getMycode().equals(random)){
			return ajax(Status.warn,"验证码输入错误!");
		}
		removeSession(ConstantUtil.RANDOM_COOKIE_NAME);
		
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("username",user.getUsername());
		map.put("GuanLianDengLu", user.getUsername());
		User loginUser = this.userService.getUser(map);
		//解除会员账户锁定 
		if(loginUser != null){
			if(loginUser.getIsLock()!=null && loginUser.getIsLock()){
				if((Boolean)getServletContext().getAttribute("qmd.setting.isLoginFailureLock")){
					Integer loginFailureLockTime = (Integer)getServletContext().getAttribute("qmd.setting.loginFailureLockTime");
					if(loginFailureLockTime > 0 ){
						Date lockedDate = loginUser.getLockedDate();
						Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
						if (new Date().after(unlockDate)) {
							loginUser.setLoginFailureCount(0);
							loginUser.setIsLock(false);
							loginUser.setLockedDate(null);
							userService.update(loginUser);
						}
					}
				}else{
					loginUser.setLoginFailureCount(0);
					loginUser.setIsLock(false);
					loginUser.setLockedDate(null);
					userService.update(loginUser);
				}
			}
		if(loginUser.getTypeId() != 3){
			return ajax(Status.error, "您的账号不是服务商，无法登录。");
		}
		
		if(loginUser.getIsEnabled()==null || !loginUser.getIsEnabled() || loginUser.getTypeId() == 1){//临时添加typeId = 3 的借款人禁止登录
			return ajax(Status.error, "您的账号已被禁用,无法登录，如有疑问请联系客服人员!");
		}
		
		if(loginUser.getIsLock()==null || loginUser.getIsLock()){
			return ajax(Status.error,"您的账号已被锁定，如有疑问请联系客服人员!");
		}
		if(!userService.isPassword(user.getUsername(), user.getPassword(), "0")){
			
			if((Boolean)getServletContext().getAttribute("qmd.setting.isLoginFailureLock")){
				Integer loginFailureLockCount = (Integer)getServletContext().getAttribute("qmd.setting.loginFailureLockCount");
				Integer loginFailureCount = loginUser.getLoginFailureCount()==null?0:loginUser.getLoginFailureCount() + 1;
				if(loginFailureCount >= (Integer)getServletContext().getAttribute("qmd.setting.loginFailureLockCount")){
					loginUser.setIsLock(true);
					loginUser.setLockedDate(new Date());
				}
				loginUser.setLoginFailureCount(loginFailureCount);
				userService.update(loginUser);
				if((Boolean)getServletContext().getAttribute("qmd.setting.isLoginFailureLock") && loginFailureLockCount - loginFailureCount <= 3){
					return ajax(Status.error,"若连续" + loginFailureLockCount + "次密码输入错误,您的账号将被锁定!");
				}else{
					return ajax(Status.error,"您的用户名或密码错误!");
				}
			}else{
				return ajax(Status.error,"您的用户名或密码错误!");
			}
		}
		}else{
			return ajax(Status.error,"您的用户名或密码错误!");
		}
		
		loginUser.setLastTime(new Date());
		loginUser.setLastIp(getRequestRemoteAddr());
		loginUser.setLoginTime(user.getLoginTime()==null?0:user.getLoginTime()+1);
		
		//只要登录成功，清空之前密码输入错误次数
		loginUser.setLoginFailureCount(0);
		loginUser.setIsLock(false);
		loginUser.setLockedDate(null);
		
		this.userService.updateUserByLoginSuccess(loginUser);
		if(loginUser.getRealStatus()==0 && loginUser.getEmailStatus()!=2){
//			loginRedirectUrl = "/userCenter/realname.do";
			loginRedirectUrl = getContextPath()+"/userCenter/realIdentify.do";
		}
		
		
		setSession(User.USER_ID_SESSION_NAME, loginUser);
		
		
		
		log.info("【"+loginUser.getUsername()+"】会员登录成功");
		
		}catch(Exception e) {
			e.printStackTrace();
		}

		if(StringUtils.isNotEmpty( getLoginUser().getPayPassword())){
			message = getContextPath() + "/userCenter/index.do";
		}else{
			message = getContextPath() + "/userCenter/toPayPassword.do";
		}
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS_PARAMETER_NAME, Status.success.toString());
		jsonMap.put("username",getLoginUser().getUsername());
		jsonMap.put("realName",getLoginUser().getRealName());
		jsonMap.put("ableMoney",CommonUtil.setPriceScale2String(getLoginUser().getAbleMoney()));
//		jsonMap.put("message","/userCenter/index.do");
		if(StringUtils.isNotEmpty(loginRedirectUrl)){
			message = loginRedirectUrl;
		}
		jsonMap.put(MESSAGE_PARAMETER_NAME, message);
		
		return ajax(jsonMap);
	}

	
	
	
	

	/**
	 * 用户登录检查用户名
	 * @return 0 未登陆,1登陆并实名,2 登陆未实名
	 */
	@Action(value="/user/checkForUserLogin",results={@Result(type="plainText")})
	@InputConfig(resultName = "ajaxError")
	public String checkForUserLogin(){
		User user = getLoginUser();
		if(user==null||user.getUsername()==null) {
			return ajax("0");
		}
		reloadUser();
		if (user.getRealStatus()==null || user.getRealStatus()!=1) {
			return ajax("2");
		}
		
		Borrow bor = borrowService.getBorrowById(Integer.parseInt(id));
//		if(bor.getTenderSubject()!=null && bor.getTenderSubject()==1) {
//			String subjectdays = listingService.getKeyValue(ConstantUtil.BORROW_TENDER_SUBJECT_DAYS);
//			Date end = new Date();
//			Date begin = CommonUtil.date2begin(CommonUtil.getDateBefore(end, (Integer.parseInt(subjectdays)-1)));
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("userId", user.getId());
//			map.put("noBusinessType", "99");
//			map.put("minDate", begin);
//			map.put("maxDate", end);
//			map.put("noBorrowId", bor.getId());
//			map.put("tenderSubject", 1); //新客专享
//			
////			Integer count1 = borrowTenderService.queryTenderDetailList(map);
////			if (count1!=null &&count1 >0) {//"最近"+subjectdays+"天无投资，不能投资本项目！"
////				return ajax("3");
////			}
//			
//		}
		
		return ajax("1");
	}
	

	/**
	 * 用户登录检查用户名
	 * @return
	 */
	@Action(value="/user/checkByUserLogin",results={@Result(type="plainText")})
	@InputConfig(resultName = "ajaxError")
	public String checkByUserLogin(){
		User user = getLoginUser();
		if (user!=null&&user.getUsername()!=null) {
			return ajax("true");
		}
		return ajax("false");
	}
	
	

	
	//根据用户名获取用户信息
	@Action(value = "/user/ajaxUserMessage", results = { @Result(name = "success", params = {"root", "root" }, type = "json") })
	public String ajaxUserMessage(){
		Map<String,Object> map= new HashMap<String ,Object>();
		map.put("typeId", 0);
		map.put("username", user.getUsername());
		User u = this.userService.getUser(map);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if( u == null){
			jsonMap.put(STATUS_PARAMETER_NAME, Status.error.toString());
		}else{
			jsonMap.put(STATUS_PARAMETER_NAME, Status.success.toString());
			jsonMap.put("user",u);
			jsonMap.put(MESSAGE_PARAMETER_NAME, message);
		}
		return ajax(jsonMap);
	}
	
	
	@Action(value="/show",results={@Result(name="success", location="/content/user/user_show.ftl", type="freemarker")})
	public String showUser() {
		
		if (getLoginUser()==null || getLoginUser().getId()!=2) {
			return "error_ftl";
		}
		
		if(r==null) r="c";
		
		List<ShowBean> l = showUserData();
		msg = JsonUtil.toJson(l);
		return SUCCESS;
	}
	
	private List<ShowBean> showUserData() {
		Map<String,Object> map = new HashMap<String,Object>();
//		String b = getRequest().getParameter("b");
//		String e = getRequest().getParameter("e");
		Date date0 = null;
		Date date1 = null;
		if (StringUtils.isEmpty(begin)) {
			date0 = new Date();
		} else {
			date0 = CommonUtil.getString2Date(begin,"yyyyMMdd");
			if(date0==null) {
				date0 = new Date();
			}
		}
		begin =  CommonUtil.getDate2String(date0,"yyyyMMdd");
		if (StringUtils.isEmpty(end)) {
			date1 = new Date();
		} else {
			date1 = CommonUtil.getString2Date(end,"yyyyMMdd");
			if(date1==null) {
				date1 = new Date();
			}
		}
		end =  CommonUtil.getDate2String(date1,"yyyyMMdd");
		date0 = CommonUtil.date2begin(date0);
		date1 = CommonUtil.date2end(date1);
		
		map.put("startTime", date0);
		map.put("endTime",date1);
		
		showBeanList = userService.queryDirectInfo(r, map) ;
		if ("c".equals(r)) {
			kefu ="充值";
		} else if ("t0".equals(r)) {
			kefu ="提现中";
		} else if ("t1".equals(r)) {
			kefu ="提现完成";
		} 
		kefu = kefu+"["+CommonUtil.getIntDate(date0)+"-"+CommonUtil.getIntDate(date1)+"]";
		
		return showBeanList;
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

	public String getLowestMoney() {
		return lowestMoney;
	}

	public void setLowestMoney(String lowestMoney) {
		this.lowestMoney = lowestMoney;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public String getCodechoose() {
		return codechoose;
	}

	public void setCodechoose(String codechoose) {
		this.codechoose = codechoose;
	}

	public String getSpreadNo() {
		return spreadNo;
	}

	public void setSpreadNo(String spreadNo) {
		this.spreadNo = spreadNo;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Map<String, Object> getRoot() {
		return root;
	}

	public void setRoot(Map<String, Object> root) {
		this.root = root;
	}

	public String getFriendUsername() {
		return friendUsername;
	}

	public void setFriendUsername(String friendUsername) {
		this.friendUsername = friendUsername;
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

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public List<ShowBean> getShowBeanList() {
		return showBeanList;
	}

	public void setShowBeanList(List<ShowBean> showBeanList) {
		this.showBeanList = showBeanList;
	}

	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
}
