package net.qmdboss.action.admin;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.*;
import net.qmdboss.entity.Role;
import net.qmdboss.entity.User;
import net.qmdboss.entity.UserAccount;
import net.qmdboss.entity.UserInfo;
import net.qmdboss.service.*;
import net.qmdboss.util.CommonUtil;
import net.qmdboss.util.ImageUtil;
import net.qmdboss.util.PWDUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

@ParentPackage("admin")
public class UserAction extends BaseAdminAction {

	private static final long serialVersionUID = 6568299859486121451L;

	private User user;
	private UserInfo userInfo;
	private Integer verifyType; // 审核类型【1：实名认证；2：借款人审核;3:vip审核(扣费)】

	private Integer verifyValue;// 审核状态
	private String remark;// 备注
	private String areaId;
	private String reupassword;

	private File cardPic1;
	private File cardPic2;
	private File privateCharterImg;
	private File privateTaxImg;
	private File privateOrganizationImg;
	private File accountLicenceImg;
	private Boolean isEdit;//是否有权限修改用户名

	private Integer status;// 状态：0全部,1正常，2未启用，3已锁定

	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "userInfoServiceImpl")
	private UserInfoService userInfoService;
	@Resource(name = "userAccountServiceImpl")
	private UserAccountService userAccountService;
	
	@Resource(name = "listingServiceImpl")
	private ListingService  listingService;

	private Date beginDate;
	private Date endDate;
	private  Boolean showAll=true;
	private BigDecimal  sumMoney;
	
	// 列表
	public String list() {
		if (verifyType == null) {
			verifyType = 0;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String adminPlaceName=adminService.getLoginAdmin().getUsername();
		
		
		String placeName=listingService.findChildListingByKeyValue("place_admin", adminPlaceName);
		if(StringUtils.isNotBlank(placeName)){
			showAll=false;
			map.put("placeChilderName", placeName);
		}
		
		/**
		if("yufeng".equals(adminPlaceName)){
			showAll=false;
			map.put("placeChilderName", "浴风");
		}
	
		**/
		

		
		if(beginDate != null && endDate != null){
			map.put("beginDate", CommonUtil.getDate2String(CommonUtil.date2begin(beginDate), "yyyy-MM-dd HH:mm:ss"));
			
			map.put("endDate", CommonUtil.getDate2String(CommonUtil.date2end(endDate), "yyyy-MM-dd HH:mm:ss"));
		}
//		else{
//			addErrorMessages("请输入起始时间或截止时间");
//			return ERROR;
//		}
		
//		pager = userService.getUserPager(status, verifyType, pager);
		pager = userService.findPagerByHsql(pager, map);
		return LIST;
	}

	// 编辑列表
	public String editList() {
		if (verifyType == null) {
			verifyType = 0;
		}

		pager = userService.getUserPager(status, verifyType, pager);

		return "edit_list";
	}

	// 会员安全管理-用于用户登录密码，安全密码，认证信息 的修改
	public String securityList() {
		pager = userService.findPager(pager);
		return "security_list";
	}

	public String security() {
		user = userService.load(id);
		return "security";
	}

	// 会员安全管理-修改信息
	public String securityManage() {
		User persistent = userService.load(id);
		if (StringUtils.isNotEmpty(user.getPassword())) {
			persistent.setPassword(PWDUtil.encode(user.getPassword(),
					persistent.getRandomNum()));
		}
		if (StringUtils.isNotEmpty(user.getPayPassword())) {
			persistent.setPayPassword(PWDUtil.encode(user.getPayPassword(),
					persistent.getRandomNum()));
		}
		persistent.setPhone(user.getPhone());
		persistent.setPhoneStatus(user.getPhoneStatus());
		persistent.setEmail(user.getEmail());
		persistent.setEmailStatus(user.getEmailStatus());
		userService.update(persistent);
		logInfo = "会员安全管理：" + persistent.getUsername();
		redirectUrl = "user!securityList.action";
		return SUCCESS;
	}

	public String realnameList() {
		verifyType = 1;
		pager = userService.getUserPager(verifyType, pager);
		return "list_by_realname";
	}

	public String businessList() {
		verifyType = 4;
		pager = userService.getUserPager(verifyType, pager);
		return "list_by_business";
	}

	public String grInfoList() {
		verifyType = 5;
		pager = userService.getUserPager(verifyType, pager);
		return "list_by_gr_information";
	}

	public String qyInfoList() {
		verifyType = 6;
		pager = userService.getUserPager(verifyType, pager);
		return "list_by_qy_information";
	}

	public String phoneList() {
		verifyType = 7;
		pager = userService.getUserPager(verifyType, pager);
		return "list_by_phone";
	}

	public String edit() {
		user = userService.load(id);
		
		Set<Role> s=adminService.getLoginAdmin().getRoleSet();
	     isEdit=false;
		 Iterator<Role> i = s.iterator();//先迭代出来  
	      while(i.hasNext()){//遍历   
	            if(i.next().getId()==1){
	            	isEdit=true;
	            	break;
	            }
	       }  
		
		return INPUT;
	}

	/**
	 * 查看详情
	 * 
	 * @return
	 */
	public String info() {
		user = userService.load(id);
		return "info";
	}

	// 更新
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "user.phone", message = "用户名不允许为空!") }, stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "user.phone", minLength = "4", maxLength = "16", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "user.username", minLength = "4", maxLength = "16", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "user.password", minLength = "8", maxLength = "16", message = "密码长度必须在${minLength}到${maxLength}之间!") }, emails = { @EmailValidator(fieldName = "user.email", message = "E-mail格式错误!") })
	@InputConfig(resultName = "error")
	public String update() {
		User persistent = userService.load(id);
		// persistent.setUsername(user.getUsername());
		// if (StringUtils.isNotEmpty(user.getPassword())) {
		// persistent.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(user.getPassword())+persistent.getRandomNum()));
		// }
		persistent.setUsername(user.getUsername());
		persistent.setPhone(user.getPhone());
		persistent.setEmail(user.getEmail());
		persistent.setIsEnabled(user.getIsEnabled());
		persistent.setIsLock(user.getIsLock());
		persistent.setLoginFailureCount(0);
		if (persistent.getRealStatus() == null
				|| persistent.getRealStatus() == 0) {// 只有未通过实名认证的，才能修改其实名认证信息
			persistent.setRealStatus(user.getRealStatus());
			persistent.setRealName(user.getRealName());
			logInfo = "实名认证会员: " + persistent.getUsername();
		}
		userService.update(persistent);
		logInfo = "编辑会员: " + persistent.getUsername();
		redirectUrl = "user!editList.action";
		return SUCCESS;
	}

	public String input() {
		return INPUT;
	}

	// 保存
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "user.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "user.password", message = "密码不允许为空!"),
			@RequiredStringValidator(fieldName = "user.email", message = "E-mail不允许为空!") }, stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "user.username", minLength = "4", maxLength = "16", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "user.password", minLength = "8", maxLength = "16", message = "密码长度必须在${minLength}到${maxLength}之间!") }, emails = { @EmailValidator(fieldName = "user.email", message = "E-mail格式错误!") }, regexFields = { @RegexFieldValidator(fieldName = "user.username", regexExpression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "用户名只允许包含中文、英文、数字和下划线!") })
	@InputConfig(resultName = "error")
	public String save() {
		if (userService.getUserByUsername(user.getUsername()) != null) {
			addActionError("用户名已存在!");
			return ERROR;
		}
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		user.setTypeId(0);
		user.setLoginTime(1);
		user.setAddIp(getRequest().getRemoteAddr());
		userService.save(user);
		logInfo = "添加会员: " + user.getUsername();
		redirectUrl = "user!list.action";
		return SUCCESS;
	}

	// 审核操作 verifyType-> 1:实名认证
	public String verifycheck() {
		StringBuffer logInfoStringBuffer = new StringBuffer();
		user = userService.load(id);
		logInfoStringBuffer.append(user.getUsername());
		if (verifyType == 1) {

			if (verifyValue == 1) {
				user.setSceneStatus(12);
			}
			logInfoStringBuffer.append("个人账户实名认证；");
			user.setRealStatus(verifyValue);
		} else if (verifyType == 2) {
			logInfoStringBuffer.append("借款人审核；");
			user.setTypeId(verifyValue);
		} else if (verifyType == 3) {
			logInfoStringBuffer.append("VIP审核；");
		} else if (verifyType == 4) {
			logInfoStringBuffer.append("企业资料实名认证；");
			user.setRealStatus(verifyValue);
		} else if (verifyType == 5) {
			logInfoStringBuffer.append("个人借款者资质认证；");
		} else if (verifyType == 6) {
			logInfoStringBuffer.append("企业借款者资质认证；");
		} else if (verifyType == 7) {
			logInfoStringBuffer.append("手机号认证；");
			user.setPhoneStatus(verifyValue);
		}
		logInfoStringBuffer.append("审核结果:");
		if (verifyValue == 1) {// 通过
			logInfoStringBuffer.append("通过；");
		} else {// 拒绝
			logInfoStringBuffer.append("拒绝；");
		}

		userService.update(user, verifyType, getRequest());
		if (user.getEmailStatus() != null && user.getEmailStatus().equals(1)) {
			mailService.sendApprove(user, verifyType, verifyValue);// 发送认证邮件
		}
		logInfoStringBuffer.append("备注:" + remark);
		logInfo = logInfoStringBuffer.toString();

		pager = userService.findPager(pager);
		if (verifyType == 1) {
			redirectUrl = "user!realnameList.action?pager.pageNumber="
					+ pager.getPageNumber() + "&pager.orderBy="
					+ pager.getOrderBy() + "&pager.order=" + pager.getOrder()
					+ "&pager.searchBy=" + pager.getSearchBy()
					+ "&pager.keyword=" + pager.getKeyword();
		} else if (verifyType == 4) {
			redirectUrl = "user!businessList.action?pager.pageNumber="
					+ pager.getPageNumber() + "&pager.orderBy="
					+ pager.getOrderBy() + "&pager.order=" + pager.getOrder()
					+ "&pager.searchBy=" + pager.getSearchBy()
					+ "&pager.keyword=" + pager.getKeyword();
		} else if (verifyType == 5) {
			redirectUrl = "user!grInfoList.action?pager.pageNumber="
					+ pager.getPageNumber() + "&pager.orderBy="
					+ pager.getOrderBy() + "&pager.order=" + pager.getOrder()
					+ "&pager.searchBy=" + pager.getSearchBy()
					+ "&pager.keyword=" + pager.getKeyword();
		} else if (verifyType == 6) {
			redirectUrl = "user!qyInfoList.action?pager.pageNumber="
					+ pager.getPageNumber() + "&pager.orderBy="
					+ pager.getOrderBy() + "&pager.order=" + pager.getOrder()
					+ "&pager.searchBy=" + pager.getSearchBy()
					+ "&pager.keyword=" + pager.getKeyword();
		} else if (verifyType == 7) {
			redirectUrl = "user!phoneList.action?pager.pageNumber="
					+ pager.getPageNumber() + "&pager.orderBy="
					+ pager.getOrderBy() + "&pager.order=" + pager.getOrder()
					+ "&pager.searchBy=" + pager.getSearchBy()
					+ "&pager.keyword=" + pager.getKeyword();
		}
		return SUCCESS;
	}

	public String verify() {
		user = userService.load(id);
		return "verify";
	}

	// 添加企业借款人
	public String addqyInfo() {
		return "addqyInfo";
	}

	// 添加企业借款人
	public String addgrInfo() {
		return "addgrInfo";
	}

	public String infoSave() {

		if (StringUtils.isEmpty(user.getPassword())) {
			this.addErrorMessages("登录密码不能为空!");
			// addActionError("登录密码不能为空!");
			return ERROR;
		}
		if (!user.getPassword().equals(reupassword)) {
			this.addErrorMessages("两次密码必须相等!");
			// addActionError("两次密码必须相等!");
			return ERROR;
		} else if (StringUtils.isEmpty(user.getUsername())) {
			this.addErrorMessages("两次密码必须相等!");
			// addActionError("用户名不能为空!");
			return ERROR;
		} else if (StringUtils.isEmpty(user.getPhone())) {
			this.addErrorMessages("联系电话不能为空!");
			// addActionError("联系电话不能为空!");
			return ERROR;
		} else if (StringUtils.isEmpty(user.getEmail())) {
			this.addErrorMessages("电子邮箱不能为空!");
			// addActionError("联系电话不能为空!");
			return ERROR;
		} else if (StringUtils.isEmpty(user.getRealName())) {
			this.addErrorMessages("姓名不能为空!");
			// addActionError("姓名不能为空!");
			return ERROR;
		}
		List<User> usrlist = userService.getByPhone(user.getPhone());
		if (usrlist != null) {
			if (usrlist.size() > 0) {
				this.addErrorMessages("添加用户手机号已存在!");
				// addActionError("添加用户手机号已存在!");
				return ERROR;
			}

		}
		User usr = userService.getUserByUsername(user.getUsername());
		if (usr != null) {
			this.addErrorMessages("用户名已存在!");
			// addActionError("用户名已存在!");
			return ERROR;
		}

		String random = CommonUtil.getRandomString(8);
		user.setRandomNum(random);// 保存随机8位密码
		if (StringUtils.isNotEmpty(user.getPassword())) {
			user.setPassword(PWDUtil.encode(user.getPassword(), random));// 对密码进行加密处理
		}
		// 处理省市区
		// if(StringUtils.isEmpty(areaId)){
		// addErrorMessages("请选择平台服务商地址");
		// return ERROR;
		// }

		// String domain =
		// areaService.get(Integer.parseInt(areaId)).getDomain();
		// if(StringUtils.isNotEmpty(domain)){
		// String[] areaStore = CommonUtil.splitString(domain);
		// user.setProvince(areaStore[0]);//省
		// user.setCity(areaStore[1]);//市
		// user.setArea(areaStore[2]);//区
		// StringBuffer areaSotreBuffer = new StringBuffer();
		// if(StringUtils.isNotEmpty(areaStore[0])){
		// areaSotreBuffer.append(areaService.get(Integer.parseInt(areaStore[0])).getName());
		// if(StringUtils.isNotEmpty(areaStore[1])){
		// areaSotreBuffer.append(",").append(areaService.get(Integer.parseInt(areaStore[1])).getName());
		// if(StringUtils.isNotEmpty(areaStore[2])){
		// areaSotreBuffer.append(",").append(areaService.get(Integer.parseInt(areaStore[2])).getName());
		// }
		// }
		// }
		// user.setAreaStore(areaSotreBuffer.toString());
		// }
		if (cardPic1 != null) {
			String path = ImageUtil
					.copyImageFile(getServletContext(), cardPic1);
			user.setCardPic1(path);
		}
		if (cardPic2 != null) {
			String path = ImageUtil
					.copyImageFile(getServletContext(), cardPic2);
			user.setCardPic2(path);
		}
		if (privateCharterImg != null) {
			String path = ImageUtil.copyImageFile(getServletContext(),
					privateCharterImg);
			userInfo.setPrivateCharterImg(path);
		}
		if (privateTaxImg != null) {
			String path = ImageUtil.copyImageFile(getServletContext(),
					privateTaxImg);
			userInfo.setPrivateTaxImg(path);
		}
		if (privateOrganizationImg != null) {
			String path = ImageUtil.copyImageFile(getServletContext(),
					privateOrganizationImg);
			userInfo.setPrivateOrganizationImg(path);
		}
		user.setEmailStatus(1);
		user.setRealStatus(1);
		user.setStatus(0);
		user.setPhoneStatus(1);
		user.setIsEnabled(true);
		user.setIsLock(false);
		user.setLastTime(new Date());
		user.setAddIp(getRequest().getRemoteAddr());
		userService.save(user);
		userInfo.setUser(user);
		userInfoService.save(userInfo);
		BigDecimal def_account = new BigDecimal(0);
		UserAccount userAccount = new UserAccount();
		userAccount.setCreateDate(new Date());
		userAccount.setModifyDate(new Date());
		userAccount.setTotal(def_account);
		userAccount.setAbleMoney(def_account);
		userAccount.setUnableMoney(def_account);
		userAccount.setCollection(def_account);
		userAccount.setInvestorCollectionCapital(def_account);
		userAccount.setInvestorCollectionInterest(def_account);
		userAccount.setBorrowerCollectionCapital(def_account);
		userAccount.setBorrowerCollectionInterest(def_account);
		userAccount.setContinueTotal(def_account);
		userAccount.setAwardMoney(def_account);
		userAccount.setUser(user);
		userAccountService.save(userAccount);

		redirectUrl = "user!list.action";
		return SUCCESS;
	}

	// 判断是否存在用户名【用于添加会员】
	public String checkUsernameByAdd() {
		String username = user.getUsername();

		System.out.println("================================" + username);

		if (userService.getUserByUsername(username) == null) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}

	// 判断是否存在用户名【用于线下充值，扣款】
	public String checkUsername() {
		String username = user.getUsername();

		System.out.println("================================" + username);

		if (userService.getUserByUsername(username) == null) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}

	// 是否已存在 ajax验证-编辑用户
	public String checkEmail() {
		String email = user.getEmail();

		System.out.println(id + "================================" + email);
		try {
			if (userService.getUserByEmail(email, id) != null) {
				return ajax("false");
			} else {
				return ajax("true");
			}
		} catch (Exception e) {
			return ajax("false");
		}
	}

	// 是否已存在 ajax验证-编辑用户
	public String checkPhone() {
		String phone = user.getPhone();

		System.out.println(id + "================================" + phone);
		try {
			if (userService.getUserByPhone(phone, id) != null) {
				return ajax("false");
			} else {
				return ajax("true");
			}
		} catch (Exception e) {
			return ajax("false");
		}
	}

	// 是否已存在 ajax验证-编辑用户
	public String checkUserPhone() {
		String phone = user.getPhone();

		System.out.println(id + "================================" + phone);
		try {
			if (userService.getByPhone(phone) != null) {
				return ajax("false");
			} else {
				return ajax("true");
			}
		} catch (Exception e) {
			return ajax("false");
		}
	}

	/**
	 * 借款人列表
	 * @return
	 */
	public String borrowerList(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", 1);
		map.put("isEnabled", true);
		pager = userService.findPagerByMap(pager, map);
		return "borrower_list";
	}
	
	public String borrowerInfo(){
		
		user = userService.load(id);
		
		return "borrower_info";
	}
	
	private String username;//用户名
	/**
	 * 好友邀请信息
	 * @return
	 */
	public String spreadlist(){

		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(username))
			map.put("username", username);
		pager = userService.findPager(pager, map);
		return "list_by_spread";
	}
	
	
	/**
	 * 好友奖励信息
	 * @return
	 */
	public String spreadlistMoney(){

		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(username)){
			User u=userService.getUserByUsername(username);
			if(u!=null){
				map.put("username",u.getId());
				sumMoney=listingService.findSumMoneyBySpread(u.getId());			
			}
		}
				
		pager = userService.findInviteAwardPagerBySql(pager, map);
		
		
		return "list_by_spread_money";
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(Integer verifyType) {
		this.verifyType = verifyType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getVerifyValue() {
		return verifyValue;
	}

	public void setVerifyValue(Integer verifyValue) {
		this.verifyValue = verifyValue;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public File getCardPic1() {
		return cardPic1;
	}

	public void setCardPic1(File cardPic1) {
		this.cardPic1 = cardPic1;
	}

	public File getCardPic2() {
		return cardPic2;
	}

	public void setCardPic2(File cardPic2) {
		this.cardPic2 = cardPic2;
	}

	public File getPrivateCharterImg() {
		return privateCharterImg;
	}

	public void setPrivateCharterImg(File privateCharterImg) {
		this.privateCharterImg = privateCharterImg;
	}

	public File getPrivateTaxImg() {
		return privateTaxImg;
	}

	public void setPrivateTaxImg(File privateTaxImg) {
		this.privateTaxImg = privateTaxImg;
	}

	public File getPrivateOrganizationImg() {
		return privateOrganizationImg;
	}

	public void setPrivateOrganizationImg(File privateOrganizationImg) {
		this.privateOrganizationImg = privateOrganizationImg;
	}

	public File getAccountLicenceImg() {
		return accountLicenceImg;
	}

	public void setAccountLicenceImg(File accountLicenceImg) {
		this.accountLicenceImg = accountLicenceImg;
	}

	public String getReupassword() {
		return reupassword;
	}

	public void setReupassword(String reupassword) {
		this.reupassword = reupassword;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getIsEdit() {
		return isEdit;
	}

	public Boolean getShowAll() {
		return showAll;
	}

	public void setShowAll(Boolean showAll) {
		this.showAll = showAll;
	}

	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}

	public BigDecimal getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(BigDecimal sumMoney) {
		this.sumMoney = sumMoney;
	}
	
	

	
}
