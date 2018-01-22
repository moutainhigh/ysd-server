package com.qmd.action.user;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.userInfo.UserCenterBean;
import com.qmd.mode.admin.Admin;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.*;
import com.qmd.service.area.AreaService;
import com.qmd.service.borrow.BorrowRepaymentDetailService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.borrow.BorrowTenderService;
import com.qmd.service.mail.MailService;
import com.qmd.service.payment.PaymentService;
import com.qmd.service.user.*;
import com.qmd.service.util.ListingService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConfigUtil;
import com.qmd.util.JsonUtil;
import com.qmd.util.Pager;
import com.qmd.util.md5.PWDUtil;
import com.qmd.util.redis.CacheUtil;
import com.qmd.util.rongbaoUtils.RongbaoUtil;
import com.ysd.biz.YueSmsUtils;
import com.ysd.ipyy.IPYYSMSResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;
	 
/**
 * 用户个人中心
 * @author Xsf
 *
 */
@Service("apiUserCenterAction")
@InterceptorRefs({
	@InterceptorRef(value = "apiUserInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
public class ApiUserCenterAction extends ApiBaseAction {
		
	private static final long serialVersionUID = 6996957697026269082L;
		Logger log = Logger.getLogger(ApiUserCenterAction.class);
		
		private Logger moneyLog = Logger.getLogger("userWithdrawMoneyLog");
		
		@Resource
		UserService userService;
		@Resource
		AreaService areaService;
		@Resource
		MailService mailService;
		@Resource
		PaymentService paymentService;
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
		private String newEmail;//新认证Email
		private String kefu;
		private String newPassword;//新密码
		private String newPayPassword;//支付密码
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
		AccountCash accountCash;
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
		private double rollBackMoney;//续投宝可转出金额
		private double rollTotal;//转出金额
		private String codeReg;//手机号验证码
		private String password;//密码
		private String phone;//手机号码
		private String email;//邮箱
		private String payPassword;//安全密码
		private UserRepaymentDetail userRepaymentDetai;
		private BorrowRepaymentDetail borrowRepaymentDetail;
		private String borrowerTotal;
		private UserAccount selfAccountView;
		
		private int userCashChargeTimes;
		private BigDecimal userCashChargeMoney;
		
		private int cashChargeTimes;
		private BigDecimal cashChargeMoney;
		private Date todayDate;
		private Map jsonMap;
		private String gesture;//手势密码
		private String orderNo;//订单号
		
		@Resource
		BorrowService borrowService;


		private String averageMonth;
		private int averageRank;
		private List<UserRepaymentDetail> userRepaymentDetaiList;
		private List<BorrowRepaymentDetail> borrowRepaymentDetailList;
		public void validate(){
			log.info("validate succ------------------");
		}
		
		/**
		 * 会员中心首页
		 * @return
		 */
		@Action(value="/api/userCenter",results={@Result(type="json")})
		@InputConfig(resultName = "error_ftl,success_ftl")
		public String userCenter() {
			reloadUser();
			Map<String,Object> map= new HashMap<String ,Object>();
			map.put("id", getLoginUser().getId());
			User user = this.userService.getUser(map);
			UserCenterBean bean = new UserCenterBean(user);
			BigDecimal totalIncome = userAccountDetailService.getSumMoney(getLoginUser().getId(),"interest_repayment");
			bean.setTotalIncome(totalIncome);//累计收益
			Map<String, Object> tMap = new HashMap<String, Object>();
			tMap.put("userId", user.getId());
			tMap.put("backStatus", 0);
			List<BorrowTender> btList = borrowTenderService.getTenderDetailByUserid(tMap);
			Date time = null;
			BigDecimal money = BigDecimal.ZERO;
			if(btList != null && btList.size() > 0){
				time = btList.get(0).getRepaymentDate();
				money = btList.get(0).getLoanAmountFee();
			}
			bean.setTenderTime(time);//最近投资日期
			bean.setTenderMoney(money);//投资金额
			time = null;
			money = BigDecimal.ZERO;
			tMap.clear();
			tMap.put("userId", user.getId());
			tMap.put("status", 0);
			List<UserRepaymentDetail> brdList = userRepaymentDetailService.queryUserRepaymentDetailList(tMap);
			if(brdList != null && brdList.size() > 0){
				time = brdList.get(0).getRepaymentDate();
				money =new BigDecimal(brdList.get(0).getRepaymentAccount()).setScale(2);
			}
			bean.setRepaymentMoney(money);//累计回款额
			bean.setRepaymentTime(time);//最近回款日期
			return ajax(bean);
		}
		
//		/**
//		 * 会员验证登录
//		 * @return
//		 */
//		@Action(value="/api/user",results={@Result(type="json")})
//		@InputConfig(resultName = "error_ftl,success_ftl")
//		public String apiUser() {
//			User user = getLoginUser();
//			
//			return ajax(user);
//		}


		/**
		 * 修改会员密码 
		 * @return
		 */
		@Action(value="/api/updatePwd",results={@Result(type="json")})
		@InputConfig(resultName = "error_ftl,success_ftl")
		public String ajaxUpdatePwd(){
			Map<String,Object> map= new HashMap<String ,Object>();
			map.put("id", getLoginUser().getId());
			User userLogin = this.userService.getUser(map);
			if(StringUtils.isEmpty(user.getPassword())){
				return ajaxJson("S0003",ApiConstantUtil.S0003);
			}
			userLogin.setPassword(PWDUtil.encode(user.getPassword(),userLogin.getRandomNum()));
			userLogin.setPasswordRecoverKey(null);
			userService.updatePassowrd(userLogin);

			setSession(User.USER_ID_SESSION_NAME, userLogin);
			return ajaxJson("R0001","修改成功");
		}
		
		/**
		 * 修改手势密码
		 * @return
		 */
		@Action(value="/api/updateGesture",results={@Result(type="json")})
		@InputConfig(resultName = "error_ftl,success_ftl")
		public String updateGesture(){
			Map<String,Object> map= new HashMap<String ,Object>();
			map.put("id", getLoginUser().getId());
			User userLogin = this.userService.getUser(map);
			
			userLogin.setGesture(gesture);
			userService.updatePassowrd(userLogin);

			setSession(User.USER_ID_SESSION_NAME, userLogin);
			return ajaxJson("R0001","修改成功");
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

		public List<AccountBank> getAccountBanklist() {
			return accountBanklist;
		}

		public void setAccountBanklist(List<AccountBank> accountBanklist) {
			this.accountBanklist = accountBanklist;
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

		public void setUserRepaymentDetaiList(List<UserRepaymentDetail> userRepaymentDetaiList) {
			this.userRepaymentDetaiList = userRepaymentDetaiList;
		}

		public List<BorrowRepaymentDetail> getBorrowRepaymentDetailList() {
			return borrowRepaymentDetailList;
		}

		public void setBorrowRepaymentDetailList(List<BorrowRepaymentDetail> borrowRepaymentDetailList) {
			this.borrowRepaymentDetailList = borrowRepaymentDetailList;
		}

		public String getCodeReg() {
			return codeReg;
		}

		public void setCodeReg(String codeReg) {
			this.codeReg = codeReg;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getGesture() {
			return gesture;
		}

		public void setGesture(String gesture) {
			this.gesture = gesture;
		}

		public Map getJsonMap() {
			return jsonMap;
		}

		public void setJsonMap(Map jsonMap) {
			this.jsonMap = jsonMap;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		/**
		 * 获取手机验证码 融宝发 我们调
		 * 
		 * @return
		 */
		@Action(value = "/api/ajaxGetPhoneCodeRongbao", results = { @Result(type = "json") })
		@InputConfig(resultName = "ajaxError")
		public String ajaxGetPhoneCodeRongbao() {
			User getuser = getLoginUser();
			jsonMap=new HashMap();
			//User getuser = this.userService.getUser(map);
			if (getuser == null) {
				jsonMap.put("rcd","1000");
				jsonMap.put("rmg", "用户不存在,无法获取验证码！");
				return ajax(jsonMap);
			}
			UserAccountRecharge userAccountRecharge=new UserAccountRecharge();
			userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(orderNo);//根据订单号 查询有无充值记录
			if(userAccountRecharge == null){
				System.out.println("【去融宝前查看订单,】NG["+orderNo+"]充值记录不存在!");
				jsonMap.put("rcd","1000");
				jsonMap.put("rmg", "充值记录不存在");
				return ajax(jsonMap);	
			}
			if(!userAccountRecharge.getUserId().equals(getuser.getId())){
				jsonMap.put("rcd","1000");
				jsonMap.put("rmg", "充值记录异常");
				return ajax(jsonMap);	
			}
			if("1".equals(userAccountRecharge.getStatus())){
				jsonMap.put("rcd","1000");
				jsonMap.put("rmg", "充值记录异常");
				return ajax(jsonMap);	
			}
			/*yujian String res=RongbaoUtil.RongbaoToSendSms(orderNo);
			if(StringUtils.isBlank(res)){
				jsonMap.put("rcd","1000");
				jsonMap.put("rmg", "验证码发送错误");
				return ajax(jsonMap);	
			}*/
			User userLogin = getLoginUser();
			String phoneCode = CommonUtil.getRandomString(6);// 6位短信验证码
			//临时保存验证码【以后替换手机发送验证码】
			System.out.println("----------------------------------------------------phoneCode:"+phoneCode);
			IPYYSMSResult ipyysmsResult =YueSmsUtils.sendForRecharge(userLogin.getPhone(), phoneCode);
			  if (!ipyysmsResult.ok()) {
	        	  	jsonMap.put("code","1000");
	  			jsonMap.put("msg", "验证码发送错误");
	  			return ajax(JsonUtil.toJson(jsonMap));
	          }
			CacheUtil.setObjValue("sms:code:"+token, phoneCode);
			// 过期时间
		    CacheUtil.setExpire("sms:code:"+token, Integer.parseInt(ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_REDIS_TOKEN_EXPIRE)));
			jsonMap.put("rcd","R0001");
			jsonMap.put("rmg", "验证码发送成功");
			return ajax(jsonMap);
		}
}
