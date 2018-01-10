package com.qmd.action.user;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.mode.admin.Admin;
import com.qmd.mode.agency.Agency;
import com.qmd.mode.article.Article;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.*;
import com.qmd.service.agency.AgencyService;
import com.qmd.service.area.AreaService;
import com.qmd.service.article.ArticleService;
import com.qmd.service.borrow.BorrowRepaymentDetailService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.user.*;
import com.qmd.service.util.ListingService;
import com.qmd.util.ConstantUtil;
import com.qmd.util.Pager;
import com.qmd.util.bean.NoteImg;
import com.qmd.util.md5.MD5;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * 用户个人中心
 * 
 * @author Xsf
 * 
 */
@Service("userCenterAction")
@InterceptorRefs({ @InterceptorRef(value = "userVerifyInterceptor"), @InterceptorRef(value = "qmdDefaultStack") })
public class UserCenterAction extends BaseAction {

	private static final long serialVersionUID = 6996957697026269082L;
	Logger log = Logger.getLogger(UserAction.class);
	@Resource
	UserService userService;
	@Resource
	AreaService areaService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	UserAccountRechargeService userAccountRechargeService;
	@Resource
	AccountBankService accountBankService;
	@Resource
	AccountCashService accountCashService;
	@Resource
	ListingService listingService;
	@Resource
	UserRepaymentDetailService userRepaymentDetailService;
	@Resource
	BorrowRepaymentDetailService borrowRepaymentDetailService;
	@Resource
	UserAccountCashService userAccountCashService;

	Pager pager;
	private User user;
	private String p;// 临时变量
	private List<User> userList;
	private String message;
	private String areaId;
	private String loginRedirectUrl;
	private File cardFile1;// 身份证正面图片
	private File cardFile2;// 身份证背面图片
	private File uploadDataFile; // 上传开通借款功能资料
	private File faceImgFile;// 头像图片
	private String cardFile1ContentType;
	private String cardFile2ContentType;
	private String uploadDataFileContentType;
	private String faceImgFileContentType;
	private String cardFile1FileName;
	private String cardFile2FileName;
	private String uploadDataFileFileName;
	private String faceImgFileFileName;
	private String file1;
	private String file2;

	private File imageFile;// 上传图片文件
	private String imageFileFileName;
	private String imageFileContentType;

	private List<File> fileList;// 上传图片集合
	private String newEmail;// 新认证Email
	private String kefu;
	private String newPassword;// 新密码
	private String newPayPassword;// 支付密码
	private int x;
	private int y;
	private int destWidth;
	private int destHeight;
	private String minDate;// 起始时间
	private String maxDate;// 截止时间
	private Map<String, String> parameterMap;// 参数
	private String status;// 借款还款状态
	private UserInfo userInfo;
	private AccountBank accountBank;
	private List<UserAccountRecharge> userAccRechargeList;
	private List<AccountBank> accountBanklist;
	AccountCash accountCash;
	private List<Borrow> borrowList;
	private List<Borrow> borrowListNew;
	private List<BorrowTender> borrowTenderList;
	private double cashAccount;
	private double cashMoney;
	private double cashFree;
	private String lastTime;// 预计到账时间
	private String cashType;// 体现方式类型
	private String feeValue;// 额外提现手续费
	private String fixedFee;// 固定提现手续费
	private String averageDaily;
	private double ableRecharge;// 固定费率现金额
	private double ableCashMoney;// 可提现金额
	private String maxCashMoney;// 最大提现金额
	private String minCashMoney;// 最小提现金额
	private double rollBackMoney;// 续单可转出金额
	private double rollTotal;// 转出金额


	private BigDecimal depositMoney;//保证金账户
	private BigDecimal feeMoney;//收益金账户
	
	private BigDecimal userRecentFee;// 近期提现费用

	private UserRepaymentDetail userRepaymentDetai;
	private BorrowRepaymentDetail borrowRepaymentDetail;

	private String borrowerTotal;

	private Integer deferApplyNum;// 展期申请数目
	private Integer tzhkNum;// 最近回款条数
	private List<BorrowTender> btList;
	private Boolean isPoputWindow;// 是否弹窗
	private Integer subUserCount;

	private String averageMonth;
	private int averageRank;
	private String[] vouchers;// 企业详细信息图片地址
	private String[] vouchersTitle;// 图片标题
	private List<NoteImg> voucherImgList;
	private Agency agency;
	private List<UserRepaymentDetail> userRepaymentDetailList;
	private String phoneCode;
	private Integer userNums;//所属会员数量
	private List<Article> articleList;//文章列表
	
	@Resource
	BorrowService borrowService;
	@Resource
	AgencyService agencyService;
	@Resource
	ArticleService articleService;
	@Resource
	UserAccountDetailService userAccountDetailService;

	public void validate() {
		log.info("validate succ------------------");
	}

	/**
	 * 个人中心首页
	 * @return
	 */
	@Action(value = "/userCenter/index", results = {
			@Result(name = "docking", location = "/content/user/usercenter_docking.ftl", type = "freemarker"),// 对接
			@Result(name = "spread", location = "/content/user/usercenter_spread.ftl", type = "freemarker"),// 推广
			@Result(name = "assure", location = "/content/user/usercenter_assure.ftl", type = "freemarker") })
	public String index() {
		try {
			User user = getLoginUser();
			Pager pageBorrow = new Pager();
			pageBorrow.setPageSize(5);
			Map<String, Object> qMap = new HashMap<String, Object>();
			qMap.put("userId", user.getId());
			depositMoney = userAccountService.getSumDepositMoney(qMap);
			feeMoney  = userAccountDetailService.getSumMoney(user.getId(), "fee_fangkuan_apply_success");
			
			//最近还款
			pager = new Pager();
			pager.setPageSize(5);
			qMap.clear();
			qMap.put("agencyId", user.getAgencyid());
			qMap.put("orderBy", " b.verify_time desc");
			borrowList = (List<Borrow>) this.getBorrowService().queryBorrowListForRepay(pager,qMap).getResult();
			
			//最新项目
			qMap.clear();
			pager = new Pager();
			pager.setPageSize(5);
			qMap.put("aguserId", user.getAgencyid());
			qMap.put("bType", 0);
			int[] array = {0,1,2,3,4,5,6,7};
			qMap.put("array", array);
			qMap.put("orderBy", "create_date desc");
			borrowListNew = (List<Borrow>) this.getBorrowService().queryBorrowList(pager,qMap).getResult();
			
			if (user.getTypeId() == 3 && "1".equals(user.getAgencytype())) {// 平台服务商
				agency = agencyService.baseLoad(user.getAgencyid());
				return "docking";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return SUCCESS;
	}

	/**
	 * 会员注销
	 */
	@Action(value = "/userCenter/logout", results = { @Result(name = "success", location = "/index", type = "redirectAction") })
	public String logout() {
		// this.removeMemcached(ConstantUtil.USER_ID_COOKIE_NAME);
		removeSession(User.USER_ID_SESSION_NAME);
		removeSession(UserInfo.USER_INFO_ID_SESSION_NAME);

		return SUCCESS;
	}

	
	/**
	 * 检查身份证号是否已通过实名认证
	 * 
	 * @return
	 */
	 @Action(value="/userCenter/checkCardiId",results={@Result(type="plainText")})
	 @InputConfig(resultName = "ajaxError")
	 public String checkUsername(){
		 log.info("检查身份证号是否已通过实名认证");
		 Map<String,Object> qMap = new HashMap<String,Object>();
		 qMap.put("cardId", user.getCardId());
		 qMap.put("realStatus", 1);
		 qMap.put("typeId",1);
		 List<User> qUsers = null;
		 qUsers = userService.getUserList(qMap);
		
		 if(qUsers != null&&qUsers.size() > 1){
			 log.info("身份证号之前已通过实名认证");
			 return ajax("false");
		 } else if(qUsers != null&&qUsers.size() ==
			 1&&!qUsers.get(0).getId().equals(getLoginUser().getId())){
			 log.info("身份证号之前已通过实名认证");
			 return ajax("false");
		 } else{
			 log.info("身份证号之前未通过实名认证");
			 return ajax("true");
		 }
	 }


	
	

	/**
	 * 修改登录/安全密码
	 * 
	 * @return
	 */
	@Action(value="/userCenter/ajaxPasswordUpdate",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String passwordUpdate() {
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		if (!this.getMycode().equals(random)) {
			return ajax(Status.warn,"验证码输入错误!");
		}
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		User userLogin = getLoginUser();
		String temp = "修改";
		if (userLogin == null || StringUtils.isEmpty(p)) {
			return ajax(Status.warn,"参数错误，请重新操作!");
		}
		if (p.equals("1")) {
			log.info("执行登录密码修改方法;新密码" + user.getPassword());
			log.info("执行登录密码修改方法;新密码" + MD5.getMD5Str(user.getPassword()));
			log.info("执行登录密码修改方法;老密码" + userLogin.getPassword());
			if (user == null || !userService.isPassword(userLogin.getUsername(), user.getPassword(), "0")) {
				log.info("原登录密码不正确");
				return ajax(Status.warn,"原登录密码不正确!");
			}
			userLogin.setPassword(MD5.getMD5Str(MD5.getMD5Str(newPassword) + userLogin.getRandomNum()));
		} else if (p.equals("2")) {
			log.info("执行安全密码修改方法");
			if (userLogin.getPayPassword() == null) {
				temp = "保存";
			}
			if (userLogin.getPayPassword() != null && !userService.isPassword(userLogin.getUsername(), user.getPayPassword(), "1")) {
				log.info("原安全密码不正确");
				return ajax(Status.warn,"原安全密码不正确!");
			}

			userLogin.setPayPassword(MD5.getMD5Str(MD5.getMD5Str(newPayPassword) + userLogin.getRandomNum()));
		}
		userLogin.setModifyDate(new Date());

		this.userService.updatePassowrd(userLogin);
		log.info("密码" + temp + "成功");
		// 更新用户信息
		// this.replaceMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME,
		// ConstantUtil.USER_NAME, userLogin);
		setSession(User.USER_ID_SESSION_NAME, userLogin);
		return ajax(Status.success,"操作成功!");
	}
	
	/**
	 * 可用余额
	 * 
	 * @return
	 */
	public UserAccount getSelfaccount() {
		return this.userAccountService.getUserAccountByUserId(getLoginUser().getId());
	}
	
	/**
	 * 跳转到修改登录密码页面
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
	 * 跳转到修改安全密码页面
	 * @return
	 */
	@Action(value="/userCenter/toPayPassword",results={@Result(name="success", location="/content/user/pay_password.ftl", type="freemarker")})
	public String toPayPassword(){
		log.info("跳转到修改安全密码页面");
		return SUCCESS;			
	}
	
	
	/**
	 * 验证安全密码
	 * 
	 * @return
	 */
	@Action(value = "/userCenter/ajaxPayPassword", results = { @Result(type = "plainText") })
	@InputConfig(resultName = "ajaxError")
	public String ajaxPayPassword() {
		User userLogin = getLoginUser();
		if (!userService.isPassword(userLogin.getUsername(), user.getPayPassword(), "1")) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}

	/**
	 * 验证安全密码
	 * 
	 * @return
	 */
	@Action(value = "/userCenter/ajaxPassword", results = { @Result(type = "plainText") })
	@InputConfig(resultName = "ajaxError")
	public String ajaxPassword() {
		User userLogin = getLoginUser();
		if (!userService.isPassword(userLogin.getUsername(), user.getPassword(), "0")) {
			return ajax("false");
		} else {
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

	public int getRandom() {
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

	public List<AccountBank> getAccountBankList() {
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


	public UserAccountService getUserAccountService() {
		return userAccountService;
	}

	public void setUserAccountService(UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}

	public UserAccountRechargeService getUserAccountRechargeService() {
		return userAccountRechargeService;
	}

	public void setUserAccountRechargeService(UserAccountRechargeService userAccountRechargeService) {
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

	public String getUploadDataFileContentType() {
		return uploadDataFileContentType;
	}

	public void setUploadDataFileContentType(String uploadDataFileContentType) {
		this.uploadDataFileContentType = uploadDataFileContentType;
	}

	public String getFaceImgFileContentType() {
		return faceImgFileContentType;
	}

	public void setFaceImgFileContentType(String faceImgFileContentType) {
		this.faceImgFileContentType = faceImgFileContentType;
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

	public String getUploadDataFileFileName() {
		return uploadDataFileFileName;
	}

	public void setUploadDataFileFileName(String uploadDataFileFileName) {
		this.uploadDataFileFileName = uploadDataFileFileName;
	}

	public String getFaceImgFileFileName() {
		return faceImgFileFileName;
	}

	public void setFaceImgFileFileName(String faceImgFileFileName) {
		this.faceImgFileFileName = faceImgFileFileName;
	}

	public String getMinCashMoney() {
		return minCashMoney;
	}

	public void setMinCashMoney(String minCashMoney) {
		this.minCashMoney = minCashMoney;
	}

	public String[] getVouchers() {
		return vouchers;
	}

	public void setVouchers(String[] vouchers) {
		this.vouchers = vouchers;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public String getImageFileFileName() {
		return imageFileFileName;
	}

	public void setImageFileFileName(String imageFileFileName) {
		this.imageFileFileName = imageFileFileName;
	}

	public String getImageFileContentType() {
		return imageFileContentType;
	}

	public void setImageFileContentType(String imageFileContentType) {
		this.imageFileContentType = imageFileContentType;
	}

	public String[] getVouchersTitle() {
		return vouchersTitle;
	}

	public void setVouchersTitle(String[] vouchersTitle) {
		this.vouchersTitle = vouchersTitle;
	}

	public List<NoteImg> getVoucherImgList() {
		return voucherImgList;
	}

	public void setVoucherImgList(List<NoteImg> voucherImgList) {
		this.voucherImgList = voucherImgList;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public List<UserRepaymentDetail> getUserRepaymentDetailList() {
		return userRepaymentDetailList;
	}

	public void setUserRepaymentDetailList(List<UserRepaymentDetail> userRepaymentDetailList) {
		this.userRepaymentDetailList = userRepaymentDetailList;
	}

	public String getFile1() {
		return file1;
	}

	public void setFile1(String file1) {
		this.file1 = file1;
	}

	public String getFile2() {
		return file2;
	}

	public void setFile2(String file2) {
		this.file2 = file2;
	}

	public BigDecimal getUserRecentFee() {
		return userRecentFee;
	}

	public void setUserRecentFee(BigDecimal userRecentFee) {
		this.userRecentFee = userRecentFee;
	}



	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}



	public Integer getDeferApplyNum() {
		return deferApplyNum;
	}

	public void setDeferApplyNum(Integer deferApplyNum) {
		this.deferApplyNum = deferApplyNum;
	}

	public Integer getTzhkNum() {
		return tzhkNum;
	}

	public void setTzhkNum(Integer tzhkNum) {
		this.tzhkNum = tzhkNum;
	}

	public List<BorrowTender> getBtList() {
		return btList;
	}

	public void setBtList(List<BorrowTender> btList) {
		this.btList = btList;
	}

	public Boolean getIsPoputWindow() {
		return isPoputWindow;
	}

	public void setIsPoputWindow(Boolean isPoputWindow) {
		this.isPoputWindow = isPoputWindow;
	}

	public Integer getSubUserCount() {
		return subUserCount;
	}

	public void setSubUserCount(Integer subUserCount) {
		this.subUserCount = subUserCount;
	}

	public Integer getUserNums() {
		return userNums;
	}

	public void setUserNums(Integer userNums) {
		this.userNums = userNums;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public BigDecimal getDepositMoney() {
		return depositMoney;
	}

	public void setDepositMoney(BigDecimal depositMoney) {
		this.depositMoney = depositMoney;
	}

	public BigDecimal getFeeMoney() {
		return feeMoney;
	}

	public void setFeeMoney(BigDecimal feeMoney) {
		this.feeMoney = feeMoney;
	}

	public List<Borrow> getBorrowListNew() {
		return borrowListNew;
	}

	public void setBorrowListNew(List<Borrow> borrowListNew) {
		this.borrowListNew = borrowListNew;
	}

}
