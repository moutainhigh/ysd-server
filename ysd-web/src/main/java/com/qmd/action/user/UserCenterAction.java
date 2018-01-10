package com.qmd.action.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.mode.admin.Admin;
import com.qmd.mode.amount.Amount;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.*;
import com.qmd.mode.util.Listing;
import com.qmd.payment.BasePaymentProduct;
import com.qmd.service.area.AreaService;
import com.qmd.service.borrow.BorrowRepaymentDetailService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.borrow.BorrowTenderService;
import com.qmd.service.payment.PaymentService;
import com.qmd.service.user.*;
import com.qmd.service.util.ListingService;
import com.qmd.util.*;
import com.qmd.util.md5.MD5;
import com.qmd.util.md5.PWDUtil;
import com.qmd.util.rongbaoConfig.ReapalConfig;
import com.qmd.util.rongbaoUtils.Decipher;
import com.qmd.util.rongbaoUtils.Md5Utils;
import com.qmd.util.rongbaoUtils.RongbaoUtil;
import com.ysd.common.SMSCodeUtils;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
	 
/**
 * 用户个人中心
 * @author Xsf
 *
 */
@Service("userCenterAction")
@InterceptorRefs({
	@InterceptorRef(value = "userVerifyInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
public class UserCenterAction extends BaseAction {
		
	private static final long serialVersionUID = 6996957697026269082L;
		Logger log = Logger.getLogger(UserCenterAction.class);
		
		private Logger moneyLog = Logger.getLogger("userWithdrawMoneyLog");
		
		@Resource
		UserService userService;
		@Resource
		AreaService areaService;
		@Resource
		UserInfoService userInfoService;
		@Resource
		BorrowTenderService borrowTenderService;
		@Resource
		UserAccountService userAccountService;
		@Resource
		UserAccountDetailService userAccountDetailService;
		@Resource
		UserAccountRechargeService userAccountRechargeService;
		@Resource
		AccountBankService accountBankService;
		@Resource
		AccountCashService  accountCashService;
		@Resource
		ListingService listingService;
		@Resource
		UserRepaymentDetailService userRepaymentDetailService;
		@Resource
		BorrowRepaymentDetailService borrowRepaymentDetailService;
		
		
	
		private String paymentUrl;// 支付请求URL
		
		Pager pager;
		private User user;
		private String p;//临时变量
		private List<User> userList;
		private String message;
		private String areaId;
		private String loginRedirectUrl;
		private String cardImageFore;//身份证正面图片
		private String cardImageBack;//身份证背面图片
		private File cardFile1;//身份证正面图片
		private File cardFile2;//身份证背面图片
		private String cardFile1FileName;
		private String cardFile2FileName;
		private String cardFile1ContentType;
		private String cardFile2ContentType;
		private File uploadDataFile; //上传开通借款功能资料
		private File faceImgFile;//头像图片
		private List<File> fileList;//上传图片集合
		private String kefu;
		private String newPassword;//新密码
		private String newPayPassword;//支付密码
		private String newPassword2;// 重复输入的密码
		private String oldPassword;//新密码
		private String oldPayPassword;//支付密码
		private int x;
		private int y;
		private int destWidth;
		private int destHeight;
		private String minDate;//起始时间
		private String maxDate;//截止时间
		private Map<String, String> parameterMap;//参数
		private String status;//借款还款状态
		private UserInfo userInfo;
		private AccountBank accountBank;
		private List<UserAccountRecharge> userAccRechargeList;
		private List<AccountBank> accountBanklist;
		private AccountCash accountCash;
		private List<AccountCash> accountCashList;//提现记录列表
		private List<Borrow> borrowList;
		private List<BorrowTender> borrowTenderList;
		private double  cashAccount;
		private double cashMoney;
		private double cashFree;
		private String  lastTime;//预计到账时间
		private String cashType;//体现方式类型
		private String feeValue;//额外提现手续费
		private String fixedFee;//固定提现手续费
		private String averageDaily;
		private double ableRecharge;//固定费率现金额
		private double ableCashMoney;//可提现金额
		private String maxCashMoney;//最大提现金额
		private String minCashMoney;//最小提现金额
		private double rollBackMoney;//续投宝可转出金额
		private double rollTotal;//转出金额
		
		private UserRepaymentDetail userRepaymentDetai;
		private BorrowRepaymentDetail borrowRepaymentDetail;
		
		private String borrowerTotal;
		private UserAccount selfAccountView;
		
		private int userCashChargeTimes;
		private BigDecimal userCashChargeMoney;
		
		private int cashChargeTimes;
		private BigDecimal cashChargeMoney;
		private Date todayDate;
		private String btype; // 0:签约；1：修改
		private String type;//用户中心切换类型值
		private String safepwd;// 安全密码
		
		private String phone;
		
		private String jsonResult;//绑卡成功返回数据.
		private Map jsonMap;//绑卡成功返回展示数据
		
		private String bind_token;//绑卡token 防止重复提交
		private String pay_token; //支付token 
		private String tradeNo; //订单号
		
		private String rechargeMoney ;// 充值金额
		private String mycode;//充值图像验证码
		
		private UserAccountRecharge	userAccountRecharge;
		
		public String getSafepwd() {
			return safepwd;
		}

		public void setSafepwd(String safepwd) {
			this.safepwd = safepwd;
		}
		
		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getJsonResult() {
			return jsonResult;
		}

		public void setJsonResult(String jsonResult) {
			this.jsonResult = jsonResult;
		}
		
		



		public Map getJsonMap() {
			return jsonMap;
		}

		public void setJsonMap(Map jsonMap) {
			this.jsonMap = jsonMap;
		}





		@Resource
		BorrowService borrowService;

		private String averageMonth;
		private int averageRank;
		
		private List<UserRepaymentDetail> userRepaymentDetaiList;
		private List<BorrowRepaymentDetail> borrowRepaymentDetailList;
		
		public void validate(){
			log.info("validate succ------------------");
		}

		@Action(value="/userCenter/index",results={
				@Result(name="success", location="borrowDetailList", type="redirectAction")
				})
		public String index(){
			
			return SUCCESS;
		}
		
	    /**
	     * 跳转到注册页面 
	     * @return
	     */
		@Action(value="/userCenter/reg",results={@Result(name="success", location="/content/user/register.ftl", type="freemarker")})
		public String reg(){
			log.info(this.getP() + "===============");
			return SUCCESS;
		}
		
		/**
		 * 会员注销
		 */
		@Action(value="/userCenter/logout",results={@Result(name="success", location="index", type="redirectAction")})
		public String logout() {
//			this.removeMemcached(ConstantUtil.USER_ID_COOKIE_NAME);
//			removeCookie(ConstantUtil.USER_USERNAME_COOKIE_NAME);
			removeSession(User.USER_ID_SESSION_NAME);
			removeSession(UserInfo.USER_INFO_ID_SESSION_NAME);
			removeCookie(User.USER_USERNAME_COOKIE_NAME);
			removeCookie(User.USER_USERTYPE_COOKIE_NAME);
			removeCookie(User.USER_USEREALSTATUS_COOKIE_NAME);
			
			return SUCCESS;
		}
		
		/**
		 * 会员列表
		 * @return
		 */
		@Action(value="/userCenter/list",results={@Result(name="success", location="/content/user/list.ftl", type="freemarker")})
		public String userList(){
//			userList = this.getUserService().queryUser();
//			pager.setPageSize(30);
			Map<String,Object> map = new HashMap<String,Object>();
			
			pager = this.getUserService().queryUser(pager,map);
			return SUCCESS;
		}
		
		/**
		 * 会员中心-跳转到个人资料页面
		 * @return
		 */
		@Action(value="/userCenter/profile",results={@Result(name="success", location="/content/spread/tg/profile.ftl", type="freemarker")})
		public String profile(){
			
			return SUCCESS;
		}
		
		
		/**
		 * 链接跳转到手机认证页面
		 * @return
		 */
		@Action(value="/userCenter/toPhone",results={@Result(name="success", location="/content/user/phone.ftl", type="freemarker"),
													 @Result(name="msg", location="/content/message_page.ftl", type="freemarker")})
		public String userPhone(){
			log.info("进入手机认证页面");
			User loginuser = getLoginUser();
			if (loginuser.getPayPassword()==null||"".equals(loginuser.getPayPassword())) {
				msg = "请先设置交易密码";
				msgUrl = "/userCenter/toPayPassword.do";
				return "msg";
			} else if (loginuser.getRealStatus()==null||loginuser.getRealStatus()!=1) {
				msg = "请先进行账户认证！";
				msgUrl = "/userCenter/realname.do";
				return "msg";
			}
			
			reloadUser();
			return SUCCESS;
		}
		
		
		/**
		 * 检查身份证号是否已通过实名认证
		 * @return
		 */
		@Action(value="/userCenter/checkCardiId",results={@Result(type="plainText")})
		@InputConfig(resultName = "ajaxError")
		public String checkUsername(){
			log.info("检查身份证号是否已通过实名认证");
			Map<String,Object> qMap = new HashMap<String,Object>();
			qMap.put("cardId", user.getCardId());
			qMap.put("realStatus", 1);
			List<User> qUsers = null;
			qUsers = userService.getUserList(qMap);
			
			if(qUsers != null&&qUsers.size() > 1){
				log.info("身份证号之前已通过实名认证");
				return ajax("false");
			} else if(qUsers != null&&qUsers.size() == 1&&!qUsers.get(0).getId().equals(getLoginUser().getId())){
				log.info("身份证号之前已通过实名认证");
				return ajax("false");
			}
			
			qMap = new HashMap<String,Object>();
			qMap.put("cardId", user.getCardId());
			qMap.put("realStatus", 2);
			qUsers = userService.getUserList(qMap);
			
			if(qUsers != null&&qUsers.size() > 1){
				log.info("身份证号之前已申请实名认证");
				return ajax("false");
			} else if(qUsers != null&&qUsers.size() == 1&&!qUsers.get(0).getId().equals(getLoginUser().getId())){
				log.info("身份证号之前已申请实名认证");
				return ajax("false");
			}
			
			log.info("身份证号之前未通过实名认证");
			return ajax("true");
		}

		
		/**
		 * 修改实名认证信息
		 * @return
		 * @throws IOException 
		 */
		@Action(value="/userCenter/realnameUpdate",results={@Result(name="success", location="realname", type="redirectAction")})
		@InputConfig(resultName = "error_ftl")
		public String realNameUpdate() throws IOException{
			log.info("申请实名认证");
			User userLogin = getLoginUser();
			if(userLogin.getRealStatus()==1){
				addActionError("您的账户认证已通过,不要重复申请！");
				return "error_ftl";
			}
			if(userLogin.getRealStatus()==2){
				addActionError("您的账户认证已申请,等待认证！");
				return "error_ftl";
			}
			String payp = userLogin.getPayPassword();
			if(StringUtils.isEmpty(payp)){
				addActionError("请设置交易密码！");
				return "error_ftl";
			}
//			if(!payp.equals(MD5.getMD5Str(user.getPayPassword()))){
//				addActionError("交易密码输入错误!");
//				return "error_ftl";
//			}
			if(!userService.isPassword(userLogin.getUsername(), user.getPayPassword(), "1")){
				addActionError("交易密码输入错误!");
				return "error_ftl";
			}
			if(userLogin.getRealStatus().equals(2)){
				addActionError("请不要重复提交，管理员正在审核中......");
				return "error_ftl";
			}
			if(userLogin.getRealStatus().equals(1)){
				addActionError("请不要重复提交，您的实名认证已通过......");
				return "error_ftl";
			}
			//验证身份证是否重复
			Map<String,Object> qMap = new HashMap<String,Object>();
			qMap.put("cardId", user.getCardId());
			qMap.put("realStatus", 1);
			User qUser = userService.getUser(qMap);
			if(qUser != null){
				addActionError("该身份证之前已经通过实名认证......");
				return "error_ftl";
			}
			
			qMap = new HashMap<String,Object>();
			qMap.put("cardId", user.getCardId());
			qMap.put("realStatus", 2);
			qUser = userService.getUser(qMap);
			
			if(qUser != null){
				addActionError("该身份证之前已经申请实名认证......");
				return "error_ftl";
			}
			
			if(StringUtils.isNotEmpty(user.getRealName())){
				userLogin.setRealName(user.getRealName());//真实姓名
			}
//			userLogin.setNation(user.getNation());//民族
			userLogin.setCardType("1");//证件类别 默认为身份证      user.getCardType()
			
			userLogin.setSex(user.getSex());
			
//			try{
//				if(StringUtils.isNotEmpty(user.getBirthday().toLocaleString())){
//					if(user.getBirthday().after(new Date())){
//						addActionError("生日大于当前时间!请重新输入......");
//						return "error_ftl";
//					}else{
//						userLogin.setBirthday(user.getBirthday());//出生日期
//					}
//				}
//			}catch (Exception e) {
//				addActionError("生日格式有误!请重新输入");
//				return "error_ftl";
//			}

//			if(StringUtils.isNotEmpty(user.getPhone())){
//				userLogin.setPhone(user.getPhone());//手机
//			}

			//处理省市区
			String domain = areaService.getAreaDomain(areaId);
			if("".equals(domain)||null==domain){
				addActionError("您没有选择地区");
				return "error_ftl";
			}
			String[] areaStore = CommonUtil.splitString(domain);
			userLogin.setProvince(areaStore[0]);//省
			userLogin.setCity(areaStore[1]);//市
			userLogin.setArea(areaStore[2]);//区
			StringBuffer areaSotreBuffer = new StringBuffer();
			if(StringUtils.isNotEmpty(areaStore[0])){
				areaSotreBuffer.append(areaService.getAreaName(areaStore[0]));
				if(StringUtils.isNotEmpty(areaStore[1])){
					areaSotreBuffer.append(",").append(areaService.getAreaName(areaStore[1]));
					if(StringUtils.isNotEmpty(areaStore[2])){
						areaSotreBuffer.append(",").append(areaService.getAreaName(areaStore[2]));
					}
				}
			}
			userLogin.setAreaStore(areaSotreBuffer.toString());
			
			if(StringUtils.isNotEmpty(user.getCardId())){
				userLogin.setCardId(user.getCardId());//证件号码
			}
/*		
			if(StringUtils.isEmpty(cardImageFore)) {
				addActionError("请上传身份证图片");
				return "error_ftl";
			}
			if(StringUtils.isEmpty(cardImageBack)) {
				addActionError("请上传身份证图片");
				return "error_ftl";
			}
			
			String cardPic1 = CommonUtil.decodeUrl(cardImageFore);
			String cardPic2 = CommonUtil.decodeUrl(cardImageBack);
			userLogin.setCardPic1(cardPic1);
			userLogin.setCardPic2(cardPic2);
*/			
//			if (cardFile1 != null) {
//				if(CommonUtil.getFileSize(cardFile1)){
//					addActionError("身份证正面图片太大,图片应小于2M");
//					return "error_ftl";
//				}
//				String cardPic1 = ImageUtil.copyImageFile(getServletContext(), cardFile1);
//				userLogin.setCardPic1(cardPic1);//身份证正面上传
//			}
//			if (cardFile2 != null) {
//				if(CommonUtil.getFileSize(cardFile2)){
//					addActionError("身份证背面图片太大,图片应小于2M");
//					return "error_ftl";
//				}
//				String cardPic2 = ImageUtil.copyImageFile(getServletContext(), cardFile2);
//				userLogin.setCardPic2(cardPic2);//身份证背面上传
//			}
			userLogin.setRealStatus(ConstantUtil.APPLY_STATUS_ING);//身份实名认证中
			userLogin.setModifyDate(new Date());
			this.userService.updateRealName(userLogin);
			log.info("成功提交申请实名认证");
			//更新用户信息
//			this.replaceMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME, ConstantUtil.USER_NAME, userLogin);
			setSession(User.USER_ID_SESSION_NAME, userLogin);
			addActionMessage("实名认证申请已提交审核");
			redirectUrl=getContextPath()+"/userCenter/realname.do";
			
			//moneyLog.debug("【文件上传1】name["+getCardFile1FileName()+"] size["+getCardFile1().length()+"] type["+getCardFile1ContentType()+"]");
			//moneyLog.debug("【文件上传2】name["+getCardFile2FileName()+"] size["+getCardFile2().length()+"] type["+getCardFile2ContentType()+"]");
			
			return "success_ftl";
		}
		
		
		/**
		 * 发送邮件成功，跳转到邮件发送成功页面
		 * @return
		 */
		@Action(value="/userCenter/emailSendSuccess",results={@Result(name="success", location="/content/user/email.ftl", type="freemarker")})	
		public String emailSendSuccess(){
			log.info("邮件成功发送到用户邮件，等待用户点击验证......");
			return SUCCESS;
		}
		
		//验证邮件方法
		@Action(value="/userCenter/emailCheck",results={@Result(name="success", location="toEmail", type="redirectAction")})	
		public String emailCheck(){
			log.info("进入验证邮件方法");
			User userLogin = getLoginUser();
			if (userLogin == null || !StringUtils.equalsIgnoreCase(userLogin.getEmailCertificationKey(), user.getEmailCertificationKey())) {
				addActionError("对不起,此邮件验证链接已失效!");
				return ERROR;
			}
			Date emailRecoverKeyBuildDate = this.userService.getRecoverKeyBuildDate(user.getEmailCertificationKey());
			Date emailRecoverKeyExpiredDate = DateUtils.addMinutes(emailRecoverKeyBuildDate, ConstantUtil.RECOVER_KEY_PERIOD);
			if (new Date().after(emailRecoverKeyExpiredDate)) {
				addActionError("对不起,此邮件验证链接已过期!");
				return ERROR;
			}
			userLogin.setEmailCertificationKey(null);
			userLogin.setEmailStatus(ConstantUtil.APPLY_STATUS_YES);
			userLogin.setModifyDate(new Date());
			this.userService.updateEmail(userLogin);
			log.info("验证邮件成功");
			//更新用户信息
//			this.replaceMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME, ConstantUtil.USER_NAME, userLogin);
			setSession(User.USER_ID_SESSION_NAME, userLogin);
			return SUCCESS;
		}
		
		
		//执行手机认证方法
		@Action(value="/userCenter/phoneCheck",results={@Result(name="success", location="toPhone", type="redirectAction")})	
		public String phoneCheck(){
			log.info("执行手机认证方法");
			User userLogin = getLoginUser();
			if("".equals( user.getPayPassword())){
				addActionError("请输入交易密码!");
				return "error_ftl";
			}
			
			if(!userService.isPassword(userLogin.getUsername(), user.getPayPassword(), "1")){
				addActionError("交易密码输入错误!");
				return "error_ftl";
			}
			//修改手机号码
			userLogin.setPhone(user.getPhone());
			userLogin.setModifyDate(new Date());
			userLogin.setPhoneStatus(ConstantUtil.APPLY_STATUS_ING);
			this.userService.updatePhone(userLogin);
			log.info(user.getUsername()+"的手机号:"+user.getPhone()+"认证中");
			//更新用户信息
//			this.replaceMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME, ConstantUtil.USER_NAME, userLogin);
			setSession(User.USER_ID_SESSION_NAME, userLogin);
			addActionMessage("申请手机认证成功");
			redirectUrl=getContextPath()+"/userCenter/toPhone.do";
			return "success_ftl";
//			return SUCCESS;
		}
		
//		/**
//		 * 实名认证通过，修改手机号码
//		 * @return
//		 */
//		@Action(value="/userCenter/ajaxRealPhone",results={@Result(type="plainText")})
//		@InputConfig(resultName = "ajaxError")
//		public String ajaxRealPhone(){
//			log.info("实名认证通过，修改手机号码");
//			User userLogin = getLoginUser();
//			if(user.getPhone().equals(userLogin.getPhone())){
//				return ajax(Status.error,"手机号与原号码一样，不必修改");
//			}
//			userLogin.setPhone(user.getPhone());
//			userService.updatePhone(userLogin);
//			log.info("修改手机号码成功");
//			return ajax(Status.success,"修改成功");
//		}
		
		
		/**
		 * 获取手机验证码
		 * @return
		 */
		@Action(value="/userCenter/ajaxGetPhoneCode",results={@Result(type="json")})
		@InputConfig(resultName = "ajaxError")
		public String ajaxGetPhoneCode(){
			log.info("获取手机验证码");
			removeCookie(ConstantUtil.USER_PHONE_COOKIE_NAME);
			String code = CommonUtil.getRandomString(6);
			setCookie(ConstantUtil.USER_PHONE_COOKIE_NAME, code,60);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", this.getLoginUser().getId());
			user = this.userService.getUser(map);
			log.info("手机号："+user.getPhone()+";验证码是:"+code.toString()+";phoneCodeCookie="+getCookie(ConstantUtil.USER_PHONE_COOKIE_NAME));
			
			//临时保存验证码【以后替换手机发送验证码】
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put(STATUS_PARAMETER_NAME, Status.success);
			jsonMap.put(MESSAGE_PARAMETER_NAME, "验证码发送成功");
			jsonMap.put("code", code);
			return ajax(jsonMap);
		}

//		/**
//		 * 验证手机验证码 暂时无用
//		 * @return
//		 */
//		@Action(value="/userCenter/ajaxCheckPhoneCode",results={@Result(type="plainText")})
//		@InputConfig(resultName = "ajaxError")
//		public String ajaxCheckPhoneCode(){
//			log.info("cookie===="+getCookie(ConstantUtil.USER_PHONE_COOKIE_NAME)+",p="+p);
//			if(getCookie(ConstantUtil.USER_PHONE_COOKIE_NAME).equals(p)){
//				return ajax("true");
//			}else{
//				return ajax("false");
//			}
//		}
		
		
//		/**
//		 * 申请VIP页面跳转
//		 * @return
//		 */
//		@Action(value="/userCenter/toApplyVip",results={@Result(name="success", location="/content/user/applyVip.ftl", type="freemarker")})
//		public String toApplyVip(){
//			log.info("跳转到申请VIP页面");
//			return SUCCESS;
//		}
//		
//		/**
//		 * 执行vip申请方法
//		 * @return
//		 */
//		@Action(value="/userCenter/applyVip",results={@Result(name="success", location="toApplyVip", type="redirectAction")})
//		public String applyVip(){
//			log.info("跳转到执行vip申请方法");
//			User userLogin = getLoginUser();
//			if(kefu != null){
//				applyVip.setAdminId(Integer.parseInt(kefu.split(",")[0]));
//				applyVip.setExaminerName(kefu.split(",")[1]);
//			}
//			
//			//修改用户表VIPStatus状态为2
//			userLogin.setModifyDate(new Date());
//			userLogin.setVipStatus(ConstantUtil.APPLY_STATUS_ING);
//			this.userService.addApplyVip(applyVip,userLogin);
//			log.info("成功申请成为vip");
//			//更新用户信息
////			this.replaceMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME, ConstantUtil.USER_NAME, userLogin);
//			setSession(User.USER_ID_SESSION_NAME, userLogin);
//			return SUCCESS;
//		}
		/**
		 * 跳转到登录密码页面
		 * @return
		 */
		@Action(value="/userCenter/toPassword",results={@Result(name="success", location="/content/user/password.ftl", type="freemarker")})
		public String toPassword(){
			log.info("跳转到修改登录密码页面");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", this.getLoginUser().getId());
			user = this.userService.getUser(map);
			return SUCCESS;
		}
		/**
		 * 跳转到支付页面
		 * @return
		 */
		@Action(value="/userCenter/toPayPassword",results={@Result(name="success", location="/content/user/pay_password.ftl", type="freemarker")})
		public String toPayPassword(){
			log.info("跳转到修改支付密码页面");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", this.getLoginUser().getId());
			user = this.userService.getUser(map);
			return SUCCESS;			
		}
		
		
		/**
		 * 跳转到账户安全页面
		 * @return
		 */
		@Action(value="/userCenter/toAccountSecurity",results={@Result(name="success", location="/content/user/account_security.ftl", type="freemarker")})
		public String toAccountSecurity(){
			return SUCCESS;
		}
		
		/**
		 * 修改登录密码 
		 * @return
		 */
		@Action(value="/userCenter/ajaxPasswordUpdate",results={@Result(type="json")})
		@InputConfig(resultName = "ajaxError")
		public String ajaxPasswordUpdate(){
			User userLogin = getLoginUser();
			String temp="修改成功，请妥善保存，为了资金安全，请勿外泄";
			if(p.equals("1")){
				log.info("执行登录密码修改方法;新密码"+user.getPassword());
				log.info("执行登录密码修改方法;新密码"+MD5.getMD5Str(user.getPassword()));
				log.info("执行登录密码修改方法;老密码"+userLogin.getPassword());
				if(!userService.isPassword(userLogin.getUsername(), user.getPassword(), "0")){
					log.info("当前密码不正确");
					return ajax(Status.error,"当前密码不正确");
				}
				//userLogin.setPassword(MD5.getMD5Str(MD5.getMD5Str(newPassword)+userLogin.getRandomNum()));
				userLogin.setPassword(PWDUtil.encode(newPassword,userLogin.getRandomNum()));
			}else if(p.equals("2")){
				log.info("执行交易密码修改方法");
				if(userLogin.getPayPassword()== null){
					temp="设置成功，请妥善保存，为了资金安全，请勿外泄";
				}
				if(userLogin.getPayPassword()!=null && !userService.isPassword(userLogin.getUsername(), user.getPayPassword(), "1")){
					log.info("当前交易密码不正确");
					return ajax(Status.error,"当前交易密码不正确");
				}
				
				//userLogin.setPayPassword(MD5.getMD5Str(MD5.getMD5Str(newPayPassword)+userLogin.getRandomNum()));
				userLogin.setPayPassword(PWDUtil.encode(newPayPassword,userLogin.getRandomNum()));
				
			}
			userLogin.setModifyDate(new Date());
			
			this.userService.updatePassowrd(userLogin);
			log.info("密码"+temp+"成功");
			//更新用户信息
//			this.replaceMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME, ConstantUtil.USER_NAME, userLogin);
			setSession(User.USER_ID_SESSION_NAME, userLogin);
			return ajax(Status.success,temp);
		}
		
		
		
		

		/**
		 * 跳转到添加/编辑银行账户页面
		 * @return
		*/
		 
		@Action(value="/userCenter/toBank",
				results={@Result(name="success", location="/content/user/bank_detail.ftl", type="freemarker"),
						@Result(name="phone_code", location="/userCenter/bankSignPhoneCodeOri.do", type="redirect"),
						@Result(name="input", location="/content/user/bank_input_new.ftl", type="freemarker"),
						@Result(name="msg", location="/content/message_page.ftl", type="freemarker")})
		public String toBank(){
			User loginuser = getLoginUser();
//			if (loginuser.getRealStatus()==null||loginuser.getRealStatus()!=1) {
//				msg = "请先进行账户认证！";
//				msgUrl = "/userCenter/realname.do";
//				return "msg";
//			}

			User user = userService.get(loginuser.getId());
			if(2==user.getRealStatus().intValue() && !StringUtils.isBlank(user.getBankCustNo())){

				return "phone_code";
			}

			if(getAccountBankList().size() >0){
				accountBank = getAccountBankList().get(0);

				if(accountBank.getStatus().compareTo(1) ==0){
					return SUCCESS;
				}
				return "input";
			}else{
				return "input";
			}
		}
		

		

		@Resource
		PaymentService paymentService;
		
		/**
		 * 银行卡签约 提交
		 * 
		 * @return
		 
		*/
		@Action(value="/userCenter/bankSignSave",results={@Result(name="onLine", location="/content/payment/payment_submit.ftl", type="freemarker")})
		@InputConfig(resultName = "error_ftl,success_ftl")
		public String rechargeSave() {
			try {

				log.info("开始签约");
				User userLogin = getLoginUser();
				User userDb = userService.getById(userLogin.getId(), new User());
				if (userDb == null) {
					msg="参数错误";
					return "error_ftl";
				}
				// zdl: 交易密码
				if(StringUtils.isNotBlank(safepwd)){
					userLogin.setPayPassword(PWDUtil.encode(safepwd,userDb.getRandomNum()));
				}else{
					msg="请设置交易密码";
					return "error_ftl";
				}
					
				
				
				if(accountBank.getId()==null){
					List<AccountBank> accountBankList1 = getAccountBankList();
					if(accountBankList1.size()>=1){
						if(accountBankList1.get(0).getStatus()==1){
							msg="一个用户最多添加1个银行账号!";
							return "error_ftl";
						}else{
							accountBank.setId(accountBankList1.get(0).getId());
						}
						
					
					}
				}else{
					Boolean flag=false;
					List<AccountBank> accountBankList = getAccountBankList();
					for(AccountBank bank:accountBankList){
						if(bank.getId().equals(accountBank.getId())){
							flag=true;
						}
					}
					if(!flag){
						msg="参数错误!";
						return "error_ftl";
					}
				}
				if(StringUtils.isEmpty(accountBank.getBankId())){
					msg="请选择开户银行!";
					return "error_ftl";
				}
				if(StringUtils.isEmpty(accountBank.getAccount())){
					msg="请输入银行账号!";
					return "error_ftl";
				}
				accountBank.setAccount(accountBank.getAccount().replaceAll(" ", ""));
//				if(StringUtils.isEmpty(accountBank.getBranch())){
//					msg="请输入开户行名称!";
//					return "error_ftl";
//				}
				if(StringUtils.isEmpty(user.getRealName())){
					msg="请输入真实姓名!";
					return "error_ftl";
				}else{
					userLogin.setRealName(user.getRealName());
				}
				if(StringUtils.isEmpty(user.getCardId())){
					msg="请输入身份证号!";
					return "error_ftl";
				}
				Map<String,Object> qMap = new HashMap<String,Object>();
				qMap.put("cardId", user.getCardId());
				qMap.put("realStatus", 1);
				User qUser = userService.getUser(qMap);
				if(qUser != null){
					if(qUser.getId().compareTo(userLogin.getId())!=0){
						msg="该身份证已被认证!";
						return "error_ftl";
					}
					
				}else{
					userLogin.setCardId(user.getCardId());
					userLogin.setCardType("1");
					if(user.getCardId().length()!=18){
						msg="该身份证位数不对!";
						return "error_ftl";
					}
					String birthday = user.getCardId().substring(6, 14);
					userLogin.setBirthday(CommonUtil.getString2Date(birthday, "yyyyMMdd"));
					String sex ="1";
					String str= user.getCardId().substring(16, 17);
					if((Integer.valueOf(str)/2)==0){
						sex = "2";
					}
					userLogin.setSex(sex);
				}
//				this.userService.updateRealName(userLogin);
				
//				if(StringUtils.isNotEmpty(areaId)){
//					String domain = areaService.getAreaDomain(areaId);
//					String[] areaStore = CommonUtil.splitString(domain);
//					accountBank.setProvince(areaStore[0]);//省
//					accountBank.setCity(areaStore[1]);//市
//					accountBank.setArea(areaStore[2]);//区
//				}
				accountBank.setModifyDate(new Date());
				accountBank.setAddIp(getRequestRemoteAddr());
				if(accountBank.getId()==null){
					accountBank.setCreateDate(new Date());
					accountBank.setUserId(userLogin.getId());
					accountBank.setBtype("0");
				}else{
					accountBank.setBtype("1");
				}

				UserAccountRecharge userRecharge = new UserAccountRecharge();
				Integer rechargeInterfaceId = 40;

				RechargeConfig rechargeConfig = paymentService.getPaymentConfig(rechargeInterfaceId);
				if (rechargeConfig == null) {
					msg="充值方式不存在!";
					return "error_ftl";
				}
				BasePaymentProduct paymentProduct = null;
				BigDecimal money = new BigDecimal(3);
//				if(accountBank.getBankId().equals("CMB")){
//					 money = new BigDecimal(2);
//				}else{
//					 money = new BigDecimal(0.1);
//				}
				qMap.clear();
				qMap.put("keyValue", accountBank.getBankId());
				Listing listing = listingService.findListing(qMap);
				if(listing != null){
					money = new BigDecimal(listing.getDescription());
				}
				
				BigDecimal rechargeAmount = CommonUtil.setPriceScale(money) ;// 充值金额
				userRecharge.setCreateDate(new Date());
				userRecharge.setModifyDate(new Date());
				userRecharge.setTradeNo(SerialNumberUtil.buildPaymentSn());
				userRecharge.setUserId(getLoginUser().getId());
				userRecharge.setStatus(2);// 充值中
				userRecharge.setMoney(rechargeAmount);
				userRecharge.setRechargeInterfaceId(rechargeInterfaceId);
				userRecharge.setReturned("");
				userRecharge.setType("1");
				userRecharge.setRemark("银行卡签约");
				userRecharge.setFee(BigDecimal.ZERO);
				userRecharge.setReward(BigDecimal.ZERO);// 奖励为0
				userRecharge.setIpUser(getRequestRemoteAddr());
				if(StringUtils.isEmpty(userLogin.getTgNo())){
					userLogin.setTgNo(CommonUtil.getRandomNumAndLetter(6));
				}
				Integer num=paymentService.addUserAccountRecharge(userRecharge,accountBank,userLogin);
				setSession(User.USER_ID_SESSION_NAME, userLogin);
				if(num.compareTo(1)==0){
					msg="重复添加银行卡!";
					return "error_ftl";
				}else{
					log.info("跳转到充值页面");
					log.debug("【充值跳转】" + userLogin.getUsername() + ",单号:" + userRecharge.getTradeNo() + ",金额:" + userRecharge.getMoney() + ",充值方式:"
							+ rechargeConfig.getName());
					paymentProduct = paymentService.getPaymentProduct(rechargeInterfaceId);
					paymentUrl = paymentProduct.getPaymentUrl();
					
					userRecharge.setRealName(userLogin.getRealName());
					userRecharge.setCardId(userLogin.getCardId());
					userRecharge.setRegister( CommonUtil.getDate2String(userLogin.getCreateDate(),"yyyyMMddHHmmss"));
					
					userRecharge.setBankNo(accountBank.getAccount().replaceAll("\\s*", ""));
					userRecharge.setBackUrl(ConstantUtil.WEB_SITE+"/userCenter/toBankInput.do");
					userRecharge.setMobile(userLogin.getPhone());
					userRecharge.setPayCode(accountBank.getBankId());
					
					parameterMap = paymentProduct.getParameterMap(rechargeConfig, userRecharge, getRequest());
					return "onLine";
					
				}
			} catch (Exception e) {
				addActionError("签约失败!");
				return "error_ftl";
			}
		}
		
		
		
		
		/**
		 * 路转到银行账户列表页面
		 * @return
		 
		@Action(value="/userCenter/bankList",results={@Result(name="success", location="/content/user/bank_input.ftl", type="freemarker")})
		public String bankList(){
			return SUCCESS;
		}
		*/
		/**
		 * 删除银行账户
		 * @return
		 
		@Action(value="/userCenter/ajaxBankDelete",results={@Result(type="json")})
		@InputConfig(resultName = "ajaxError")
		public String ajaxBankDelete(){
			log.info("删除银行账户");
			Boolean flag=false;
			List<AccountBank> accountBankList = getAccountBankList();
			for(AccountBank bank:accountBankList){
				if(bank.getId().equals(accountBank.getId())){
					flag=true;
				}
			}
			if(flag){
				this.userService.deleteAccountBank(accountBank.getId());
				return ajax(Status.success,"删除成功!");
			}else{
				return ajax(Status.error,"参数错误!");
			}
		}
		*/
		/**
		 * 个人中心充值记录
		 * @return
		 */
		public List<UserAccountRecharge> getUserAccountRechargeList(){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userId", getLoginUser().getId());
			map.put("max", 5);
			List<UserAccountRecharge> userAccountRechargeList = userAccountService.getUserAccountRechargeList(map);
			return userAccountRechargeList;
		}
		
		/**
		 * 查询用户所有已收利息总额 
		 * @return
		 */
		public Amount getEveryamount(){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userId", getLoginUser().getId());
			Amount amount =this.borrowTenderService.selectAllrepaymentYesinterestByUserid(map);
			if(amount== null){
				amount = new Amount();
			}
			return amount;
		}
		
		/**
		 * 可用余额
		 * @return
		 */
		public UserAccount getSelfaccount(){
			return this.userAccountService.getUserAccountByUserId(getLoginUser().getId());
		}
		
		/**
		 * 跳转到提现页面
		 * @return
		 */
		@Action(value="/userCenter/getMoney",results={@Result(name="success", location="/content/user/cash.ftl", type="freemarker"),
				@Result(name="msg", location="/content/message_page.ftl", type="freemarker")})
		public String getMoney(){

			/* yujian pc不支持提现
			reloadUser();
			user = getLoginUser();
			moneyLog.debug("【开始提现】["+user.getUsername()+"]");
			
			if(getAccountBankList().size() >0){
				accountBank = getAccountBankList().get(0);
			}else{
				msg = "请先添加银行账户！";
				msgUrl = "/userCenter/toBank.do";
				return "msg";
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id" , user.getId());
			accountBanklist = accountBankService.getAccountBankList( user.getId());
			if (user.getRealStatus()==null||user.getRealStatus()!=1) {
				msg = "请先进绑卡认证！";
				msgUrl = "/userCenter/toBank.do";
				return "msg";
			}
			 
			Listing listing =this.listingService.getListing(ConstantUtil.MAX_CASH_MONEY);
			maxCashMoney= listing.getKeyValue();//最大提现金额
			Listing listing1 =this.listingService.getListing(ConstantUtil.MIN_CASH_MONEY);
			minCashMoney= listing1.getKeyValue();//最小提现金额
				
			 if(user.getTypeId()==0){
				 ableCashMoney= user.getAbleMoney().doubleValue();
				 
				 Map<String,Object> map1 = new HashMap<String,Object>();
				 map1.put("userId" , user.getId());
				 int[] cashStatusArray =  {0,1,4};//状态码【0-审核中；1-审核成功；2-审核失败；3-用户取消；4-处理中】
				 map1.put("cashStatusArray" , cashStatusArray);
				 map1.put("minDate" ,  CommonUtil.date2begin(CommonUtil.getFirstDayOfMonth(new Date())));
				 map1.put("maxDate" , new Date());
				 
				 userCashChargeTimes =  accountCashService.queryAccountCashListCount(map1);//当月提现次数
				 
				 String val1 = listingService.getKeyValue(ConstantUtil.CASH_CHARGE_TIMES);
				 String val2 = listingService.getKeyValue(ConstantUtil.CASH_CHARGE_MONEY);
				 cashChargeTimes = Integer.parseInt(val1);
				 cashChargeMoney = new BigDecimal(val2);
				 

				Listing listing2 =this.listingService.getListing(ConstantUtil.FIXED_CASH_FEE);
				fixedFee = listing2.getKeyValue();
			 }else{
				 ableCashMoney=user.getAbleMoney().doubleValue();
				 cashChargeTimes = 100;
				 cashChargeMoney = BigDecimal.ZERO;
				 userCashChargeTimes = 0;
			 }
			 
			 moneyLog.debug("【结束提现】["+user.getUsername()+"]提现类型["+cashType+"]可提现["+ableCashMoney+"]免费提现["+ableRecharge+"]用户可用["+user.getAbleMoney().doubleValue()+"]本月提现次数["+userCashChargeTimes+"]超次数手续费比例["+cashChargeMoney+"]");
			 */
			 return SUCCESS;
			
		}

		private String code;// 验证码

		/**
		 * 获取提现记录列表
		 * @return
		 */
		@Action(value="/userCenter/cashList",results={@Result(name="success", location="/content/user/cash_list.ftl", type="freemarker")})
		public String cashList(){
			try {
				reloadUser();// 重新载入用户信息
				user = getLoginUser();
				if(pager == null){
					pager = new Pager();
				}
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("userId", getLoginUser().getId());
				if(StringUtils.isNotEmpty(minDate) && StringUtils.isNotEmpty(maxDate)){
					SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
					Date min;
					Date max; 
					try {
						min = simple.parse(minDate);
						max = simple.parse(maxDate);
					} catch (ParseException e) {
						addActionError("参数错误");
						return "error_ftl";
					}
					minDate = simple.format(min);
					maxDate = simple.format(max);
					parameterMap = new HashMap<String, String> ();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(max);
					calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
					calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)-1);
					parameterMap.put("minDate", minDate);
					parameterMap.put("maxDate", maxDate);
					map.put("minDate", min);
					map.put("maxDate", calendar.getTime());
				}
				// 记录列表
				pager = this.accountCashService.getCashList(pager,map);
				
				// 提现统计
				Map<String,Object> map1 = new HashMap<String,Object>();
				 int[] array ={1};
				map1.put("array", array);
				map1.put("userId", getLoginUser().getId());
				accountCashList = this.accountCashService.gainCashLish(map1);
				for(AccountCash accountCash: accountCashList ){
					cashAccount += CommonUtil.bigDecimal2Double(accountCash.getTotal());
					cashMoney  += CommonUtil.bigDecimal2Double(accountCash.getCredited());
					cashFree += CommonUtil.bigDecimal2Double(accountCash.getFee());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;	
		}
		/**
		 * 撤回提现金额
		 * @return
		 */
		@Action(value="/userCenter/ajaxUpdateCash",results={@Result(type="json")})
		@InputConfig(resultName = "ajaxError")
		public String ajaxUpdateCash(){
			log.info("撤回提现金额");
			System.out.println(accountCash.getId());
			AccountCash accountCash1 = this.accountCashService.get(accountCash.getId());
			
			if (getLoginUser().getId()==null || accountCash1.getUserId() != accountCash1.getUserId()) {
				return ajax(Status.error,"此提现不能自行撤回!");
			}
			
			Boolean flag=false;
			if(accountCash1.getStatus()==0){
				flag=true;
			}
			if(flag){
				accountCash1.setStatus(3);
				accountCash1.setModifyDate(new Date());
				this.accountCashService.updateCash(accountCash1,getLoginUser().getId());
				
				return ajax(Status.success,"撤回成功!");
			}else{
				return ajax(Status.error,"此提现不能自行撤回!");
			}
		}
		
		public List<AccountCash> getAccountCashList() {
			return accountCashList;
		}

		public void setAccountCashList(List<AccountCash> accountCashList) {
			this.accountCashList = accountCashList;
		}


		
		
		/**
		 * 验证交易密码
		 * @return
		 */
		@Action(value="/userCenter/ajaxPayPassword",results={@Result(type="plainText")})
		@InputConfig(resultName = "ajaxError")
		public String ajaxPayPassword(){
			User userLogin = getLoginUser();
			if(!userService.isPassword(userLogin.getUsername(), user.getPayPassword(), "1")){
				return ajax("false");
			}else{
				return ajax("true");
			}
		}		
		
		/**
		 * 验证交易密码
		 * @return
		 */
		@Action(value="/userCenter/ajaxPassword",results={@Result(type="plainText")})
		@InputConfig(resultName = "ajaxError")
		public String ajaxPassword(){
			User userLogin = getLoginUser();
			if(!userService.isPassword(userLogin.getUsername(), user.getPassword(), "0")){
				return ajax("false");
			}else{
				return ajax("true");
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

		public UserInfo getUserInfo() {
			return userInfo;
		}

		public void setUserInfo(UserInfo userInfo) {
			this.userInfo = userInfo;
		}

		public List<File> getFileList() {
			return fileList;
		}

		public void setFileList(List<File> fileList) {
			this.fileList = fileList;
		}
		public int getRandom(){
			return new Random().nextInt();
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getDestWidth() {
			return destWidth;
		}

		public void setDestWidth(int destWidth) {
			this.destWidth = destWidth;
		}

		public int getDestHeight() {
			return destHeight;
		}

		public void setDestHeight(int destHeight) {
			this.destHeight = destHeight;
		}

		public String getNewPassword() {
			return newPassword;
		}

		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}

		public String getNewPayPassword() {
			return newPayPassword;
		}

		public void setNewPayPassword(String newPayPassword) {
			this.newPayPassword = newPayPassword;
		}

		public AccountBank getAccountBank() {
			return accountBank;
		}

		public void setAccountBank(AccountBank accountBank) {
			this.accountBank = accountBank;
		}
		
		public List<AccountBank> getAccountBankList(){
			return this.userService.queryAccountBank(getLoginUser().getId());
		}
		
		public List<UserAccountRecharge> getUserAccRechargeList() {
			return userAccRechargeList;
		}

		public void setUserAccRechargeList(List<UserAccountRecharge> userAccRechargeList) {
			this.userAccRechargeList = userAccRechargeList;
		}

		public UserInfoService getUserInfoService() {
			return userInfoService;
		}

		public void setUserInfoService(UserInfoService userInfoService) {
			this.userInfoService = userInfoService;
		}

		public BorrowTenderService getBorrowTenderService() {
			return borrowTenderService;
		}

		public void setBorrowTenderService(BorrowTenderService borrowTenderService) {
			this.borrowTenderService = borrowTenderService;
		}

		public UserAccountService getUserAccountService() {
			return userAccountService;
		}

		public void setUserAccountService(UserAccountService userAccountService) {
			this.userAccountService = userAccountService;
		}

		public UserAccountRechargeService getUserAccountRechargeService() {
			return userAccountRechargeService;
		}

		public void setUserAccountRechargeService(
				UserAccountRechargeService userAccountRechargeService) {
			this.userAccountRechargeService = userAccountRechargeService;
		}
		
		





		public AccountCash getAccountCash() {
			return accountCash;
		}

		public void setAccountCash(AccountCash accountCash) {
			this.accountCash = accountCash;
		}

		public AccountBankService getAccountBankService() {
			return accountBankService;
		}

		public void setAccountBankService(AccountBankService accountBankService) {
			this.accountBankService = accountBankService;
		}

		public AccountCashService getAccountCashService() {
			return accountCashService;
		}

		public void setAccountCashService(AccountCashService accountCashService) {
			this.accountCashService = accountCashService;
		}

		public BorrowService getBorrowService() {
			return borrowService;
		}

		public void setBorrowService(BorrowService borrowService) {
			this.borrowService = borrowService;
		}

		public List<Borrow> getBorrowList() {
			return borrowList;
		}

		public void setBorrowList(List<Borrow> borrowList) {
			this.borrowList = borrowList;
		}

		public List<BorrowTender> getBorrowTenderList() {
			return borrowTenderList;
		}

		public void setBorrowTenderList(List<BorrowTender> borrowTenderList) {
			this.borrowTenderList = borrowTenderList;
		}
		public Pager getPager() {
			return pager;
		}
		public void setPager(Pager pager) {
			this.pager = pager;
		}
		public String getMinDate() {
			return minDate;
		}

		public void setMinDate(String minDate) {
			this.minDate = minDate;
		}

		public String getMaxDate() {
			return maxDate;
		}

		public void setMaxDate(String maxDate) {
			this.maxDate = maxDate;
		}

		public Map<String, String> getParameterMap() {
			return parameterMap;
		}

		public void setParameterMap(Map<String, String> parameterMap) {
			this.parameterMap = parameterMap;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getAverageDaily() {
			return averageDaily;
		}

		public void setAverageDaily(String averageDaily) {
			this.averageDaily = averageDaily;
		}

		public double getCashAccount() {
			return cashAccount;
		}

		public void setCashAccount(double cashAccount) {
			this.cashAccount = cashAccount;
		}

		public double getCashMoney() {
			return cashMoney;
		}

		public void setCashMoney(double cashMoney) {
			this.cashMoney = cashMoney;
		}

		public double getCashFree() {
			return cashFree;
		}

		public void setCashFree(double cashFree) {
			this.cashFree = cashFree;
		}

		public String getLastTime() {
			return lastTime;
		}

		public void setLastTime(String lastTime) {
			this.lastTime = lastTime;
		}

		public String getCashType() {
			return cashType;
		}

		public void setCashType(String cashType) {
			this.cashType = cashType;
		}

		public double getAbleRecharge() {
			return ableRecharge;
		}

		public void setAbleRecharge(double ableRecharge) {
			this.ableRecharge = ableRecharge;
		}

		public String getFeeValue() {
			return feeValue;
		}

		public void setFeeValue(String feeValue) {
			this.feeValue = feeValue;
		}

		public String getFixedFee() {
			return fixedFee;
		}

		public void setFixedFee(String fixedFee) {
			this.fixedFee = fixedFee;
		}

		public double getAbleCashMoney() {
			return ableCashMoney;
		}

		public void setAbleCashMoney(double ableCashMoney) {
			this.ableCashMoney = ableCashMoney;
		}

		public UserRepaymentDetail getUserRepaymentDetai() {
			return userRepaymentDetai;
		}

		public void setUserRepaymentDetai(UserRepaymentDetail userRepaymentDetai) {
			this.userRepaymentDetai = userRepaymentDetai;
		}

		public BorrowRepaymentDetail getBorrowRepaymentDetail() {
			return borrowRepaymentDetail;
		}

		public void setBorrowRepaymentDetail(BorrowRepaymentDetail borrowRepaymentDetail) {
			this.borrowRepaymentDetail = borrowRepaymentDetail;
		}

		public String getAverageMonth() {
			return averageMonth;
		}

		public void setAverageMonth(String averageMonth) {
			this.averageMonth = averageMonth;
		}

		public int getAverageRank() {
			return averageRank;
		}

		public void setAverageRank(int averageRank) {
			this.averageRank = averageRank;
		}
		public String getBorrowerTotal() {
			return borrowerTotal;
		}

		public void setBorrowerTotal(String borrowerTotal) {
			this.borrowerTotal = borrowerTotal;
		}

		public String getMaxCashMoney() {
			return maxCashMoney;
		}

		public void setMaxCashMoney(String maxCashMoney) {
			this.maxCashMoney = maxCashMoney;
		}

		public double getRollTotal() {
			return rollTotal;
		}

		public void setRollTotal(double rollTotal) {
			this.rollTotal = rollTotal;
		}

		public double getRollBackMoney() {
			return rollBackMoney;
		}

		public void setRollBackMoney(double rollBackMoney) {
			this.rollBackMoney = rollBackMoney;
		}

		public UserAccount getSelfAccountView() {
			return selfAccountView;
		}

		public void setSelfAccountView(UserAccount selfAccountView) {
			this.selfAccountView = selfAccountView;
		}

		public String getCardFile1FileName() {
			return cardFile1FileName;
		}

		public void setCardFile1FileName(String cardFile1FileName) {
			this.cardFile1FileName = cardFile1FileName;
		}

		public String getCardFile2FileName() {
			return cardFile2FileName;
		}

		public void setCardFile2FileName(String cardFile2FileName) {
			this.cardFile2FileName = cardFile2FileName;
		}

		public String getCardFile1ContentType() {
			return cardFile1ContentType;
		}

		public void setCardFile1ContentType(String cardFile1ContentType) {
			this.cardFile1ContentType = cardFile1ContentType;
		}

		public String getCardFile2ContentType() {
			return cardFile2ContentType;
		}

		public void setCardFile2ContentType(String cardFile2ContentType) {
			this.cardFile2ContentType = cardFile2ContentType;
		}

		public String getCardImageFore() {
			return cardImageFore;
		}

		public void setCardImageFore(String cardImageFore) {
			this.cardImageFore = cardImageFore;
		}

		public String getCardImageBack() {
			return cardImageBack;
		}

		public void setCardImageBack(String cardImageBack) {
			this.cardImageBack = cardImageBack;
		}

		public int getUserCashChargeTimes() {
			return userCashChargeTimes;
		}

		public void setUserCashChargeTimes(int userCashChargeTimes) {
			this.userCashChargeTimes = userCashChargeTimes;
		}

		public BigDecimal getUserCashChargeMoney() {
			return userCashChargeMoney;
		}

		public void setUserCashChargeMoney(BigDecimal userCashChargeMoney) {
			this.userCashChargeMoney = userCashChargeMoney;
		}

		public int getCashChargeTimes() {
			return cashChargeTimes;
		}

		public void setCashChargeTimes(int cashChargeTimes) {
			this.cashChargeTimes = cashChargeTimes;
		}

		public BigDecimal getCashChargeMoney() {
			return cashChargeMoney;
		}

		public void setCashChargeMoney(BigDecimal cashChargeMoney) {
			this.cashChargeMoney = cashChargeMoney;
		}

		public Date getTodayDate() {
			return todayDate;
		}

		public void setTodayDate(Date todayDate) {
			this.todayDate = todayDate;
		}

		public List<UserRepaymentDetail> getUserRepaymentDetaiList() {
			return userRepaymentDetaiList;
		}

		public void setUserRepaymentDetaiList(
				List<UserRepaymentDetail> userRepaymentDetaiList) {
			this.userRepaymentDetaiList = userRepaymentDetaiList;
		}

		public List<BorrowRepaymentDetail> getBorrowRepaymentDetailList() {
			return borrowRepaymentDetailList;
		}

		public void setBorrowRepaymentDetailList(
				List<BorrowRepaymentDetail> borrowRepaymentDetailList) {
			this.borrowRepaymentDetailList = borrowRepaymentDetailList;
		}

		public String getBtype() {
			return btype;
		}

		public void setBtype(String btype) {
			this.btype = btype;
		}

		public String getPaymentUrl() {
			return paymentUrl;
		}

		public void setPaymentUrl(String paymentUrl) {
			this.paymentUrl = paymentUrl;
		}

		public String getMinCashMoney() {
			return minCashMoney;
		}

		public void setMinCashMoney(String minCashMoney) {
			this.minCashMoney = minCashMoney;
		}

		public String getOldPassword() {
			return oldPassword;
		}

		public void setOldPassword(String oldPassword) {
			this.oldPassword = oldPassword;
		}

		public String getOldPayPassword() {
			return oldPayPassword;
		}

		public void setOldPayPassword(String oldPayPassword) {
			this.oldPayPassword = oldPayPassword;
		}

		public String getNewPassword2() {
			return newPassword2;
		}

		public void setNewPassword2(String newPassword2) {
			this.newPassword2 = newPassword2;
		}
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public List<AccountBank> getAccountBanklist() {
			return accountBanklist;
		}

		public void setAccountBanklist(List<AccountBank> accountBanklist) {
			this.accountBanklist = accountBanklist;
		}
		
		
		

		/**
		 * 检测银行卡类别是否一致
		 * <p>解决：“融宝”和“宝付”银行卡简称不一致问题
		 * @param xBank 选择银行卡类别
		 * @param sBank 融宝返回的银行类别
		 * @return
		 */
		private boolean checkBankIsSame(String xBank,String sBank){
			if(StringUtils.isBlank(xBank)||StringUtils.isBlank(sBank)){
				return false;
			}
			//平安银行: 宝付简称，融宝简称
			if((StringUtils.equals(xBank, "PAB") && StringUtils.equals(sBank, "PAYH"))){
				return true;
			}
			//交通银行：宝付简称，融宝简称
			if(StringUtils.equals(xBank, "BCOM") && StringUtils.equals(sBank, "BOCM")){
				return true;
			}
			//广发银行：宝付简称，融宝简称
			if(StringUtils.equals(xBank, "GDB") && StringUtils.equals(sBank, "CGB")){
				return true;
			}
			if(StringUtils.equalsIgnoreCase(xBank, sBank)){
				return true;
			}
			return false;
		}
		
		/**
		 * 银行卡签约 提交 融宝
		 * qxw
		 * yujian 迁移到 NewPaymentAction
		 * @return
		 
		*/
		@Action(value="/userCenter/bankSignSaveHnewBAK",results={@Result(name="onLine", location="/content/payment/payment_submit.ftl", type="freemarker"),
				@Result(name="rongbao", location="/userCenter/payOrder.do", type="redirect")})
		@InputConfig(resultName = "error_ftl,success_ftl")
		public String rechargeSaveHnew() {
			//防止重复提交
			String bind_token_session = (String)getSession(ConstantUtil.BIND_TOKEN);
			removeSession(ConstantUtil.BIND_TOKEN);
			if(bind_token_session == null||!StringUtils.equals(bind_token, bind_token_session)){
				msg="请重新输入信息";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			//验证表单数据不能为空
			if(accountBank == null ||user == null){
				msg="请完善信息";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			try {
				log.info("开始签约");
				//验证：用户信息
				User userLogin = getLoginUser();
				User userDb = userService.getById(userLogin.getId(), new User());
				if (userDb == null) {
					msg="用户不存在";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				List<AccountBank> userBankList = getAccountBankList();
				if(userBankList!=null && userBankList.size() > 1){
					msg="你绑定了"+userBankList.size()+"个银行账号(一个用户只能绑定一个银行卡),请联系客服处理！";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				if(userBankList!=null && userBankList.size() == 1 && userBankList.get(0).getStatus() == 1){
					msg="您已绑卡成功";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				if(userBankList != null && accountBank.getId()!=null && !accountBank.getId().equals(userBankList.get(0).getId())){
					msg="不可修改此银行卡";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				//姓名
				if(StringUtils.isEmpty(user.getRealName())){
					msg="请输入真实姓名!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				//身份证号
				if(StringUtils.isEmpty(user.getCardId())){
					msg="请输入身份证号!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}else if(StringUtils.equals("1", IDCard.IDCardValidate(user.getCardId()))){
					msg="请输入有效身份证号!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				Map<String,Object> qMap = new HashMap<String,Object>();
				qMap.put("cardId", user.getCardId());
				qMap.put("realStatus", 1);
				User qUser = userService.getUser(qMap);
				if(qUser != null && qUser.getId().compareTo(userLogin.getId())!=0){
					msg="该身份证已被认证!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				//银行卡号
				if(StringUtils.isBlank(accountBank.getAccount())){
					msg="请输入银行账号!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				accountBank.setAccount(accountBank.getAccount().replaceAll("\\s*", ""));
				if(NumberUtil.isNotNumber(accountBank.getAccount())){
					msg="银行账号含非法字符!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				String qBResult = RongbaoUtil.queryBankCard(accountBank.getAccount());
				if(StringUtils.isBlank(qBResult)){
					msg="["+accountBank.getAccount()+"]银行账号校验失败!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				Map qBmap = null;
				try{
					qBmap = (Map)JSON.parse(qBResult); 
				}catch(Exception e){
					msg="["+accountBank.getAccount()+"]银行账号校验失败!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				if(qBmap == null || qBmap.isEmpty()){
					msg="["+accountBank.getAccount()+"]银行账号校验失败!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				if(!qBmap.get("result_code").equals("0000")){
					msg=qBmap.get("result_msg").toString();
			    	msgUrl="toBankInput.do";
					return "error_ftl";
				}
				//开户行
				if(StringUtils.isBlank(accountBank.getBankId())){
					msg="请选择开户银行!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				Map<String,Object> queryMap = new HashMap<String,Object>();
				queryMap.put("keyValue", accountBank.getBankId());
				Listing listing = listingService.findListing(queryMap);
				if(listing == null){
					msg="不支持此银行";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				if(!checkBankIsSame(accountBank.getBankId(), qBmap.get("bank_code") != null ? qBmap.get("bank_code").toString():null)){
					msg="卡号与银行不一致";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				//预留手机号
				if(StringUtils.isBlank(accountBank.getPhone())){
					msg="请输入预留手机号!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				if(NumberUtil.isNotNumber(accountBank.getPhone())){
					msg="预留手机号含非法字符!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
				Matcher m = p.matcher(accountBank.getPhone());
				if (!m.matches()) {
					msg = "预留手机号格式不正确!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				//交易密码
				if(StringUtils.isBlank(safepwd)){
					msg="请设置交易密码!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
//				if (!safepwd.matches(".*[a-zA-Z]+.*$")) {
//					msg = "交易密码必须包含字母!";
//					msgUrl="toBankInput.do";
//					return "error_ftl";
//				}
				
				
				if (safepwd.length() < 6 || safepwd.length() > 16) {
					msg = "交易密码长度必须在6-16个字符之间!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				
				//用户信息
				userLogin.setPayPassword(PWDUtil.encode(safepwd,userDb.getRandomNum()));
				userLogin.setRealName(user.getRealName());
				userLogin.setCardId(user.getCardId());
				userLogin.setCardType("1");//身份证
				if(StringUtils.isEmpty(userLogin.getTgNo())){
					userLogin.setTgNo(CommonUtil.getRandomNumAndLetter(6));
				}
				try{
					String sex ="1";
					String str= user.getCardId().substring(16, 17);
					if((Integer.valueOf(str)%2)==0){
						sex = "2";
					}
					userLogin.setSex(sex);
					userLogin.setBirthday(CommonUtil.getString2Date(user.getCardId().substring(6, 14), "yyyyMMdd"));
				}catch(Exception e){
					msg="身份证格式错误";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}
				
				//银行卡信息
				accountBank.setModifyDate(new Date());
				accountBank.setAddIp(getRequestRemoteAddr());
				if(accountBank.getId()==null){
					accountBank.setCreateDate(new Date());
					accountBank.setUserId(userLogin.getId());
					accountBank.setBtype("0");
				}else{
					accountBank.setBtype("1");
				}
				
				//用户充值金额
				BigDecimal reMoney = null;
				try{
					if(StringUtils.isNotBlank(rechargeMoney)){
						reMoney = new BigDecimal(rechargeMoney);
					}
				}catch(Exception e){
					reMoney = new BigDecimal(0);
				}
				//系统设置充值金额
				BigDecimal money = new BigDecimal(listing.getDescription());//默认绑卡金额3块
				BigDecimal rechargeAmount = CommonUtil.setPriceScale(money) ;// 充值金额
				
				if(reMoney != null && reMoney.compareTo(rechargeAmount)>0){
					rechargeAmount = reMoney;
				}
				
				//充值记录 yujian 不加充值记录
				UserAccountRecharge userRecharge = new UserAccountRecharge();
				Integer rechargeInterfaceId = 80;//zdl 融宝快捷支付80
				
				userRecharge.setBankName(listing.getName());
				userRecharge.setCreateDate(new Date());
				userRecharge.setModifyDate(new Date());
				userRecharge.setTradeNo(SerialNumberUtil.buildPaymentSn());
				userRecharge.setUserId(getLoginUser().getId());
				userRecharge.setStatus(2);// 充值中
				userRecharge.setMoney(rechargeAmount);
				userRecharge.setRechargeInterfaceId(rechargeInterfaceId);
				userRecharge.setReturned("");
				userRecharge.setType("1");
				userRecharge.setRemark("银行卡签约");
				userRecharge.setFee(BigDecimal.ZERO);
				userRecharge.setReward(BigDecimal.ZERO);// 奖励为0
				userRecharge.setIpUser(getRequestRemoteAddr());
				
				//保存信息
				Integer num = paymentService.addUserAccountRecharge(userRecharge,accountBank,userLogin);
				setSession(User.USER_ID_SESSION_NAME, userLogin);
				if(num.compareTo(1)==0){
					msg="重复添加银行卡!";
					msgUrl="toBankInput.do";
					return "error_ftl";
				}else{
					log.info("跳转到充值页面");
					log.debug("【充值跳转】" + userLogin.getUsername() + ",单号:" + userRecharge.getTradeNo() + ",金额:" + userRecharge.getMoney() + ",充值方式:融宝快捷支付");
				
					jsonResult= RongbaoUtil.RongbaoPay(userRecharge,accountBank, userLogin);
					log.debug("-------调用融宝返回结果="+jsonResult);
					//验证返回信息
					if(StringUtils.isBlank(jsonResult)){
						msgUrl="toBankInput.do";
						msg="绑卡异常";
						return "error_ftl";
					}
					Map map = null;
					try{
						map = (Map)JSON.parse(jsonResult);  //json转map
					}catch(Exception e){
						msg="绑卡异常";
						msgUrl="toBankInput.do";
						return "error_ftl";
					}
					if(map == null || map.isEmpty()){
						msg="绑卡异常";
						msgUrl="toBankInput.do";
						return "error_ftl";
					}
				  
				    
				    //成功后，将订单号保存在session中
				   setSession(ConstantUtil.ORDER_NO, userRecharge.getTradeNo());

				   return "rongbao";
				}
			} catch (Exception e) {
				addActionError("签约失败!");
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
		}
		
		
		/**
		 * 是首次用招商绑卡
		 * @param bandId 银行卡简称
		 * @param userId 用户id
		 * @param bankCode 银行卡号
		 * @return 
		 */
		/*private boolean isCMBFirstBindToRB(String bandId,Integer userId, String bankCode){
			//非空校验
			if(userId == null || StringUtils.isBlank(bankCode) || StringUtils.isBlank(bandId)){
				return false;
			}
			// 银行卡号长度不得小于6位
			if(bankCode.length() < 6){
				return false;
			}
			//必须是招商卡
			if(!StringUtils.equalsIgnoreCase(bandId, "CMB")){
				return false;
			}
			
			//查找用户是否已绑过输入的这张招商卡
			String card_last = bankCode.substring(bankCode.length() - 4, bankCode.length()); //银行卡后4位数字
			String card_top = bankCode.substring(0, 6); // 银行卡前6位数字
			
			String rest = RongbaoUtil.RongbaoToSelectIsbindCard(userId);//查询用户已绑银行卡列表
			
			if(StringUtils.isBlank(rest)){
				return true;
			}
			
			JSONObject jsonObj = null;
			try{
				jsonObj = JSONObject.parseObject(rest);
			}catch(Exception e){
				
			}
			if(jsonObj == null){
				return true;
			}
			
			JSONArray array = null;
			try{
				array = jsonObj.getJSONArray("bind_card_list");
			}catch(Exception e){
				
			}
			if(array == null || array.isEmpty()){
				return true;
			}
			
			for(int i=0; i < array.size() ;i++){
				JSONObject json = array.getJSONObject(i);
				if(!StringUtils.equalsIgnoreCase(json.getString("bank_code"), "CMB")){
					break;
				}
				if(!StringUtils.equals(json.getString("card_last"), card_last)){
					break;
				}
				if(!StringUtils.equals(json.getString("card_top"), card_top)){
					break;
				}
				return false;//匹配到已有此卡
			}
			
			return true;
		}*/
		
		
		
		/**
		 * 融宝支付卡密鉴权前端通知（即，点击返回商户请求接口）
		 * @return
		 * qxw
		 */
		@Action(value="/userCenter/rbkmreturn",results={@Result(name="success", location="/userCenter/payOrder.do", type="redirect")})
		public String rbkmreturn(){
			log.debug("招商卡密鉴权-前端通知-开始");
			HashMap<String, String> param = new HashMap<String, String>();
			for(Object s:getRequest().getParameterMap().keySet()){
				param.put(s.toString(), getRequest().getParameter(s.toString()));
			}
			String paramStr = JSONObject.toJSONString(param);
			log.debug("【获取通知参数】"+paramStr);
			
			if(StringUtils.isBlank(paramStr)){
				msg="招商卡密鉴权失败";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			//解析密文数据
			String decryData = null;
			try {
				decryData = Decipher.decryptData(paramStr);
				log.debug("【获取通知参数解密后】"+decryData);
			} catch (Exception e) {
				log.debug("【参数解密异常】解密前："+paramStr+"，解密后："+decryData);
				msg="招商卡密鉴权失败";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			if(StringUtils.isBlank(decryData)){
				log.debug("【参数解密后为空】解密前："+paramStr+"，解密后："+decryData);
				msg="招商卡密鉴权失败";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			//获取融宝支付的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			JSONObject jsonObject = null;
			try{	
				jsonObject= JSON.parseObject(decryData);
				log.debug("【获取回调参数解密后，解析为JSONObject】"+jsonObject.toJSONString());
			}catch(Exception e){
				log.debug("【获取回调参数解密后，解析为JSONObject异常】原数据："+decryData);
				msg="招商卡密鉴权失败";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			if(jsonObject==null){
				log.debug("【获取回调参数解密后，解析为JSONObject为空】原数据："+decryData);
				msg="招商卡密鉴权失败";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			
			String merchant_id = param.get("merchant_id");
			
			String bank_card_type = jsonObject.getString("bank_card_type");
			String bank_code = jsonObject.getString("bank_code");
			String bank_name = jsonObject.getString("bank_name");
			String bind_id = jsonObject.getString("bind_id");
			String card_last = jsonObject.getString("card_last");
			String member_id = jsonObject.getString("member_id");
			String order_no = jsonObject.getString("order_no");
			String phone = jsonObject.getString("phone");
			String result_code = jsonObject.getString("result_code");
			String result_msg = jsonObject.getString("result_msg");
			String total_fee = jsonObject.getString("total_fee");
			String trade_no = jsonObject.getString("trade_no");
			String sign = jsonObject.getString("sign");
			
			
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("merchant_id", merchant_id);
			map2.put("bank_card_type", bank_card_type);
			map2.put("bank_code", bank_code);
			map2.put("bank_name", bank_name);
			map2.put("bind_id", bind_id);
			map2.put("card_last", card_last);
			map2.put("member_id", member_id);
			map2.put("order_no", order_no);
			map2.put("phone", phone);
			map2.put("result_code", result_code);
			map2.put("result_msg", result_msg);
			map2.put("total_fee", total_fee);
			map2.put("trade_no", trade_no);
			
			//将返回的参数进行验签
			String mysign = Md5Utils.BuildMysign(map2, ReapalConfig.key);
			log.debug("【解密后的参数验签结果】订单号："+order_no+"，需要签名的参数："+JSONObject.toJSONString(map2)+"，密钥："+ReapalConfig.key+"，结果："+mysign);
			
			//校验：判读该返回结果是否由融宝发出
			if(mysign == null || !StringUtils.equals(mysign, sign)){
				log.debug("【解密后的参数验签结果，校验失败】订单号："+order_no+"，本地验签结果："+mysign+"，接受的验签："+sign);	
				msg="招商卡密鉴权失败";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			//校验：订单号是否为空，商户号一致
			if(StringUtils.isBlank(order_no) || merchant_id==null || !StringUtils.equals(merchant_id, ReapalConfig.merchant_id)){
				log.debug("【订单号是否为空，或商户号一致】订单号："+order_no+"，商户号："+merchant_id);	
				msg="招商卡密鉴权失败";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			if(!StringUtils.equals("0000", result_code)){
				log.debug("【卡密鉴权失败】："+jsonObject.toString());	
				msg="招商卡密鉴权失败";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			return SUCCESS;
		}
		
		
		/***
		 * 融宝
		 * 订单支付页面
		 * yujian
		 * @return
		 */
		@Action(value="/userCenter/payOrderBAK",results={
				@Result(name="success", location="/content/rongbao/pay_rongbao.ftl", type="freemarker")})
		@InputConfig(resultName = "error_ftl,success_ftl")
		public String payOrder(){
			//验证订单是否存在
			String orderNo = (String)getSession(ConstantUtil.ORDER_NO);
			if(StringUtils.isBlank(orderNo)){
				msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
				removeSession(ConstantUtil.ORDER_NO);
				msg = "1.请求错误！";
				return "error_ftl";
			}
			userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(orderNo);
			if(userAccountRecharge == null){
				removeSession(ConstantUtil.ORDER_NO);
				msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
				msg = "2.请求错误！";
				return "error_ftl";
			}
			//验证订单与用户的一致性
			User user = getLoginUser();
			if(user==null || user.getId() == null ||!user.getId().equals(userAccountRecharge.getUserId())){
				removeSession(ConstantUtil.ORDER_NO);
				msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
				msg = "3.请求错误！";
				return "error_ftl";
			}
			//校验订单状态
			if(userAccountRecharge.getStatus() != 2){
				if(userAccountRecharge.getStatus() == 1){
					msg = "订单已支付成功！";
				}else if(userAccountRecharge.getStatus() == 0){
					msg = "订单支付失败！";
				}else{
					msg = "4.请求错误！";
				}
				removeSession(ConstantUtil.ORDER_NO);
				msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
				return "error_ftl";
			}
			//校验银行卡
			user = userService.get(user.getId());
			List<AccountBank> blist = accountBankService.getAccountBankList(user.getId());
			if(blist==null || blist.isEmpty() || blist.size() > 1 ){
				removeSession(ConstantUtil.ORDER_NO);
				msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
				msg = "5.请求错误！";
				return "error_ftl";
			}
			String bankNo = blist.get(0).getAccount();
//			Map<String,Object> qMap = new HashMap<String, Object>();
//			qMap.put("keyValue", accountBank.getBankId());
//			Listing listing = listingService.findListing(qMap);
			
			jsonMap=new HashMap();
		    jsonMap.put("order_no", userAccountRecharge.getTradeNo());//订单号
		    jsonMap.put("money",userAccountRecharge.getMoney());
		    jsonMap.put("create_time",CommonUtil.getDate2String(userAccountRecharge.getCreateDate() ,"yyyy-MM-dd HH:mm:ss"));
		    jsonMap.put("bank_name",userAccountRecharge.getBankName());
		    jsonMap.put("bank_no", bankNo);
		    jsonMap.put("card_id", user.getCardId());
			jsonMap.put("name",user.getRealName());
			jsonMap.put("phone",blist.get(0).getPhone());
			setSession(ConstantUtil.PAY_TOKEN,Long.toString(new Date().getTime()));
			return "success";
		}
		
		
		
		private String orderNo;//商户订单号
		private String checkCode;//验证码
		private String keyNum;//看第几次支付
		
		
		
		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public String getCheckCode() {
			return checkCode;
		}

		public void setCheckCode(String checkCode) {
			this.checkCode = checkCode;
		}
		
		

		public String getKeyNum() {
			return keyNum;
		}

		public void setKeyNum(String keyNum) {
			this.keyNum = keyNum;
		}

		/**
		 * 融宝查询支付结果
		 * yujian
		 * @return
		 */
		@Action(value="/userCenter/payResult",results={@Result(name="success", location="/content/payment/payment_result.ftl", type="freemarker"),
				@Result(name="wait_success", location="/content/payment/payment_result_wait.ftl", type="freemarker")})
		public String queryPayResult(){
			String payOrderNo = null; //订单号
			Object orderNo = getSession(ConstantUtil.QUERY_PAY_ORDER_NO);
			//removeSession(ConstantUtil.QUERY_PAY_ORDER_NO);
			//验证订单是否存在
			if(orderNo == null){
				msg="参数错误";
				msgUrl="/account/detail.do?type=detail&recodeType=account_recharge";
				return "error_ftl";
			}else{
				payOrderNo = (String) orderNo;
			}
			if(StringUtils.isBlank(payOrderNo)){
				msg="参数错误";
				msgUrl="/account/detail.do?type=detail&recodeType=account_recharge";
				return "error_ftl";
			}
			userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(payOrderNo);
			if(userAccountRecharge == null){
				msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
				msg = "请求错误！";
				return "error_ftl";
			}
			//验证订单与用户的一致性
			User user = getLoginUser();
			if(user==null || user.getId() == null ||!user.getId().equals(userAccountRecharge.getUserId())){
				msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
				msg = "请求错误！";
				return "error_ftl";
			}
			//验证订单状态
			if(userAccountRecharge.getStatus() == 1){//成功
				//拿出订单号，和金额
				String order_money = userAccountRecharge.getMoney().toString();
				String order_no =userAccountRecharge.getTradeNo();
				removeSession(ConstantUtil.QUERY_PAY_ORDER_NO);
				return SUCCESS;
			}else if(userAccountRecharge.getStatus() == 2){//充值中
				String result =	RongbaoUtil.RongbaoPayResult(payOrderNo);
				if(StringUtils.isBlank(result)){
					msg="支付异常,请联系客服";
					msgUrl="/account/detail.do?type=detail&recodeType=account_recharge";
				}
				Map map = (Map)JSON.parse(result);  //json转map 融宝返回参数
				if("completed".equals(map.get("status"))){//支付成功
					String order_money = map.get("total_fee").toString();
					String order_no = map.get("order_no").toString();
					
					userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(payOrderNo);
					if(userAccountRecharge.getStatus() == 1){//成功
						return SUCCESS;
					}else{
						msg= "交易处理中....";
						tradeNo=userAccountRecharge.getTradeNo();
						msgUrl="/account/detail.do?type=detail&recodeType=account_recharge";
						return "wait_success";
					}
		
				}else if("processing".equals(map.get("status"))){//等待中
					msg= "交易处理中....";
					tradeNo=userAccountRecharge.getTradeNo();
					msgUrl="/account/detail.do?type=detail&recodeType=account_recharge";
					return "wait_success";
				}else{
					msg = "【"+userAccountRecharge.getTradeNo()+"】订单【支付失败】，如有疑问请及时联系客服。";
					msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";	
				}
				
			}else{//充值失败
				msg = "【"+userAccountRecharge.getTradeNo()+"】订单【支付失败】，如有疑问请及时联系客服。";
				msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
			}
			return "error_ftl";
			
		}
		
		
		
		/**
		 * 融宝查询支付结果 json
		 * @return
		 */
		@Action(value = "/userCenter/payResultJson", results = { @Result(type = "json") })
		@InputConfig(resultName = "ajaxError")
		public String queryPayResultJson(){
			String payOrderNo = null; //订单号
			//Object orderNo = getSession(ConstantUtil.QUERY_PAY_ORDER_NO);
		    //removeSession(ConstantUtil.QUERY_PAY_ORDER_NO);
			//验证订单是否存在
			Map m=new HashMap();
			
			if(orderNo == null){
				msg="参数错误";
				m.put("rcd", "R0003");
				m.put("rmg", msg);
				return ajax(JsonUtil.toJson(m));
			}else{
				payOrderNo =  orderNo;
			}
			if(StringUtils.isBlank(payOrderNo)){
				//msg="参数错误";
				//msgUrl="/account/detail.do?type=detail&recodeType=account_recharge";
				msg="参数错误";
				m.put("rcd", "R0003");
				m.put("rmg", msg);
				return ajax(JsonUtil.toJson(m));
			}
			userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(payOrderNo);
			if(userAccountRecharge == null){
				//msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
				msg = "["+payOrderNo+"]订单不存在！";
				m.put("rcd", "R0003");
				m.put("rmg", msg);
				return ajax(JsonUtil.toJson(m));
			}
			//验证订单与用户的一致性
			User user = getLoginUser();
			if(user==null || user.getId() == null ||!user.getId().equals(userAccountRecharge.getUserId())){
				//msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
				msg = "["+payOrderNo+"]订单用户信息不匹配！";
				m.put("rcd", "R0003");
				m.put("rmg", msg);
				return ajax(JsonUtil.toJson(m));
			}
			//验证订单状态
			
			if(userAccountRecharge.getStatus() == 1){//成功
				//拿出订单号，和金额
				String order_money = userAccountRecharge.getMoney().toString();
				String order_no =userAccountRecharge.getTradeNo();
				m.put("rcd", "R0001");
				m.put("order_money", order_money);
				m.put("order_no", order_no);
				return ajax(JsonUtil.toJson(m));
			}else if(userAccountRecharge.getStatus() == 2){//充值中
				String result =	RongbaoUtil.RongbaoPayResult(payOrderNo);
				if(StringUtils.isBlank(result)){
					msg = "["+payOrderNo+"]订单支付异常,请联系客服";
					//msgUrl="/account/detail.do?type=detail&recodeType=account_recharge";
					m.put("rcd", "R0003");
					m.put("rmg", msg);
					return ajax(JsonUtil.toJson(m));
					
				}
				Map map = (Map)JSON.parse(result);  //json转map 融宝返回参数
				if("completed".equals(map.get("status"))){//支付成功
					String order_money = map.get("total_fee").toString();
					String order_no = map.get("order_no").toString();
					
					userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(payOrderNo);
					if(userAccountRecharge.getStatus() == 1){//成功
						m.put("rcd", "R0001");
						m.put("order_money", order_money);
						m.put("order_no", order_no);
						return ajax(JsonUtil.toJson(m));
						//return SUCCESS;
					}else{
						msg = "["+payOrderNo+"]订单交易处理中....";
						msgUrl="/account/detail.do?type=detail&recodeType=account_recharge";
						//return "error_ftl";
						m.put("rcd", "R0002");
						return ajax(JsonUtil.toJson(m));
						
					}
		
				}else if("processing".equals(map.get("status"))){//等待中
					msg = "["+payOrderNo+"]订单交易处理中....";
					//msgUrl="/account/detail.do?type=detail&recodeType=account_recharge";
					m.put("rcd", "R0002");
					m.put("rmg", msg);
					return ajax(JsonUtil.toJson(m));
				}else{
					msg = "["+payOrderNo+"]订单支付失败，如有疑问请及时联系客服。";
					//msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";	
					
					m.put("rcd", "R0003");
					m.put("rmg", msg);
					return ajax(JsonUtil.toJson(m));
				}
				
			}else{//充值失败
				msg = "["+payOrderNo+"]订单支付失败，如有疑问请及时联系客服。";
				//msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
				m.put("rcd", "R0003");
				m.put("rmg", msg);
				return ajax(JsonUtil.toJson(m));
			
			}
		
		
			
		}
		
		
		/**
		 * 融宝支付
		 * yujian
		 * @return
		 * qxw
		 */
		@Action(value="/userCenter/toPayRongbaoBAK",results={@Result(name="success", location="/userCenter/payResult.do", type="redirect")})
		public String toPayRongbao(){
			String token = (String)getSession(ConstantUtil.PAY_TOKEN); 
			removeSession(ConstantUtil.PAY_TOKEN);
			if(token==null || !StringUtils.equals(token, pay_token)){
				msg="参数错误";
				msgUrl="/userCenter/payOrder.do";
				return "error_ftl";
			}
			if(StringUtils.isBlank(orderNo)){
				msg="订单号为空";
				msgUrl="/userCenter/payOrder.do";
				return "error_ftl";
			}
			if(StringUtils.isBlank(checkCode)){
				msg="手机验证码为空";
				msgUrl="/userCenter/payOrder.do";
				return "error_ftl";
			}
			
			User getuser = getLoginUser();
			UserAccountRecharge userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(orderNo);//根据订单号 查询有无充值记录
			if(userAccountRecharge == null){
				msg="["+orderNo+"]订单不存在!";
				msgUrl="/userCenter/payOrder.do";
				return "error_ftl";
			}
			
			
			if(!userAccountRecharge.getUserId().equals(getuser.getId())){
				msg="["+orderNo+"]订单用户信息不一致!";
				msgUrl="/userCenter/payOrder.do";
				return "error_ftl";
			}
			
			if("1".equals(userAccountRecharge.getStatus().toString())){
				msg="["+orderNo+"]订单已支付成功，请勿重复操作！";
				msgUrl="/userCenter/payOrder.do";
				return "error_ftl";
			}
			
			//支付
			
			jsonResult=RongbaoUtil.RongbaoToPay(orderNo, checkCode);
			
			if(StringUtils.isBlank(jsonResult)){
				msg="["+orderNo+"]订单支付异常,请及时查看资金记录或联系客服！";
				msgUrl="/userCenter/payOrder.do";
				return "error_ftl";
			}
			
			Map map = (Map)JSON.parse(jsonResult);  //json转map 融宝返回参数
			
			//bank添加bindId
			if(map!=null && map.get("bind_id")!=null){
				List<AccountBank> abList = this.userService.queryAccountBank(getuser.getId());
				if(abList != null && abList.size()>0 && StringUtils.isNotBlank(map.get("bind_id").toString())){
					AccountBank bank = abList.get(0);
					AccountBank bankUpdate = new AccountBank();
					bankUpdate.setId(bank.getId());
					bankUpdate.setBindId(map.get("bind_id").toString());
					userService.updateAccountBank(bankUpdate);
				}
			}
			if(!"0000".equals(map.get("result_code"))&&!"3083".equals(map.get("result_code"))&&!"3081".equals(map.get("result_code"))){
				msg="["+orderNo+"]订单支付异常，"+ map.get("result_msg").toString();
				msgUrl="/userCenter/payOrder.do";
				return "error_ftl";
			}
			
			//要查询的订单号，放入session，用于查询订单状态
			setSession(ConstantUtil.QUERY_PAY_ORDER_NO, orderNo);
			
			return SUCCESS;
		}
		
		
		

		public String getBind_token() {
			return bind_token;
		}

		public void setBind_token(String bind_token) {
			this.bind_token = bind_token;
		}

		public String getPay_token() {
			return pay_token;
		}

		public void setPay_token(String pay_token) {
			this.pay_token = pay_token;
		}

		public UserAccountRecharge getUserAccountRecharge() {
			return userAccountRecharge;
		}

		public void setUserAccountRecharge(UserAccountRecharge userAccountRecharge) {
			this.userAccountRecharge = userAccountRecharge;
		}

		public String getTradeNo() {
			return tradeNo;
		}

		public void setTradeNo(String tradeNo) {
			this.tradeNo = tradeNo;
		}

		public String getRechargeMoney() {
			return rechargeMoney;
		}

		public void setRechargeMoney(String rechargeMoney) {
			this.rechargeMoney = rechargeMoney;
		}

		public String getMycode() {
			return mycode;
		}

		public void setMycode(String mycode) {
			this.mycode = mycode;
		}
		
}
