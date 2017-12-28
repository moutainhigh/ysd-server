package com.qmd.action.user;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.mode.agency.Agency;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserInfo;
import com.qmd.service.area.AreaService;
import com.qmd.service.user.AccountBankService;
import com.qmd.service.user.UserInfoService;
import com.qmd.service.user.UserService;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConfigUtil;
import com.qmd.util.HasKeyWord;
import com.qmd.util.Pager;
import com.qmd.util.md5.MD5;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@InterceptorRefs({
		@InterceptorRef(value = "userVerifyInterceptor"),
		@InterceptorRef(value = "qmdDefaultStack")
})
@Service("agencyAction")
public class AgencyAction extends BaseAction {

	private static final long serialVersionUID = -6320550579458608714L;

	@Resource
	UserService userService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	AccountBankService accountBankService;
	@Resource
	AreaService areaService;

	private User user;
	private List<User> userList;
	private String areaId;
	private Agency agency;
	private AccountBank accountBank;
	private UserInfo userInfo;
	private File img;
	private String keyName;
	private String keyPhone;
	private String cardImageFore;
	private String cardImageBack;

	private String img1;
	private String img2;
	private String img3;


	@Action(value="/agency/borrowerList",
			results={@Result(name="success", location="/content/agency/borrowerList.ftl", type="freemarker")})
	public String borrowerList() {

		if(pager == null){
			pager = new Pager();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("inviteUserid",getLoginUser().getId());
		map.put("status", 0);
		if(StringUtils.isNotEmpty(keyName)){
			map.put("realName", keyName);
		}
		if(StringUtils.isNotEmpty(keyPhone)){
			map.put("phone", keyPhone);
		}
//		pager = userService.queryUser(pager, map);
		pager = userService.queryJkrlb(pager, map);
		return SUCCESS;
	}

	/**
	 * 删除
	 * @return
	 */
	@Action(value="/agency/ajaxDelectUser",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String ajaxDelectUser(){
		Boolean flag=false;
		User user1 = userService.getById(user.getId());
		if(user.getStatus()==0){
			flag=true;
		}
		if(flag){
			user1.setStatus(-1);
			this.userService.update(user1);
//			this.borrowService.delectBorrow(borrow.getId());
			return ajax(Status.success,"删除成功!");
		}else{
			return ajax(Status.error,"参数错误!");
		}
	}
	@Action(value="/agency/toUpdateBorrower",
			results={@Result(name="success", location="/content/agency/updateBorrower.ftl", type="freemarker")})
	public String toUpdateBorrower() {
		user = userService.getById(user.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", user.getId());

		userInfo = userInfoService.queryListByMap(map).get(0);

		List<AccountBank> list  =accountBankService.queryListByMap(map);
		if(list.size()>0){
			accountBank = accountBankService.queryListByMap(map).get(0);
		}

		return SUCCESS;
	}

	@Action(value="/agency/lockBorrower",
			results={@Result(name="success", location="/content/agency/lockBorrower.ftl", type="freemarker")})
	public String lockBorrower() {
		user = userService.getById(user.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", user.getId());
		userInfo = userInfoService.queryListByMap(map).get(0);

		List<AccountBank> list  =accountBankService.queryListByMap(map);
		if(list.size()>0){
			accountBank = accountBankService.queryListByMap(map).get(0);
		}

		return SUCCESS;
	}


	@Action(value="/agency/inputBorrower",
			results={@Result(name="success", location="/content/agency/inputBorrower.ftl", type="freemarker")})
	public String inputBorrower() {
		return SUCCESS;
	}


	@Action(value="/agency/insertBorrower",
			results={@Result(name="success", location="borrowerList.do", type="redirectAction")})
	public String insertBorrower() {
		if(user == null){
			addActionError("请输入用户信息");
			return "error_ftl";
		}
		if(userInfo == null){
			addActionError("请输入用户信息");
			return "error_ftl";
		}
//		if(StringUtils.isEmpty(user.getUsername())){
//			addActionError("请输入用户名");
//			return "error_ftl";
//		}
//		if(StringUtils.isEmpty(user.getPassword())){
//			addActionError("请填写用户密码");
//			return "error_ftl";
//		}
		user.setUsername(user.getRealName());
		user.setPassword("qtz**&&^^qtz");
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(user.getPhone());
		if(!m.matches()){
			addActionError("手机号码格式不正确!");
			return "error_ftl";
		}
		if(StringUtils.isEmpty(accountBank.getBankId())){
			addActionError("请选择开户银行!");
			return "error_ftl";
		}
		if(StringUtils.isEmpty(accountBank.getAccount())){
			addActionError("请输入银行账号!");
			return "error_ftl";
		}
		accountBank.setAccount(accountBank.getAccount().replaceAll(" ", ""));

		if(StringUtils.isEmpty(accountBank.getBranch())){
			addActionError("请输入开户行名称!");
			return "error_ftl";
		}

		Map<String,Object> map= new HashMap<String ,Object>();
		if(HasKeyWord.hasKeyWord(getServletContext(),user.getUsername())){
			addActionError("含敏感字符,请重新输入!");
			return "error_ftl";
		}
		map.put("username", user.getUsername());
		if(this.userService.getUser(map)!=null){
			addActionError("此用户名已注册,请重新输入!");
			return "error_ftl";
		}

		if(StringUtils.isNotEmpty(cardImageFore)){
			user.setCardPic1(cardImageFore);
		}
		if(StringUtils.isNotEmpty(cardImageBack)){
			user.setCardPic2(cardImageBack);
		}
		user.setTypeId(1);//0：投资人;1：借贷人
		user.setMemberType(0);//0：个人;1：企业
		user.setEmail(user.getEmail());

		String random = CommonUtil.getRandomString(8);
		user.setRandomNum(random);//保存随机8位密码
		user.setPassword( MD5.getMD5Str(MD5.getMD5Str(user.getPassword())+random));//对密码进行加密处理

		user.setAddIp(getRequestRemoteAddr());
		user.setLastIp(getRequestRemoteAddr());
		user.setInviteUserid(getLoginUser().getId());
		user.setStatus(0);
		String ip=getRequestRemoteAddr();
		this.userService.addUser(user,userInfo,accountBank,ip,getLoginUser().getAgencyid());

		return SUCCESS;

	}



	@Action(value="/agency/updateBorrower",
			results={@Result(name="success", location="borrowerList.do", type="redirectAction")})
	public String updateBorrower() {
		User user1 = userService.getById(user.getId(), new User());
		if(user == null){
			addActionError("请输入用户信息");
			return "error_ftl";
		}
		if(userInfo == null){
			addActionError("请输入用户信息");
			return "error_ftl";
		}

		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(user.getPhone());
		if(!m.matches()){
			addActionError("手机号码格式不正确!");
			return "error_ftl";
		}
		if(StringUtils.isEmpty(accountBank.getBankId())){
			addActionError("请选择开户银行!");
			return "error_ftl";
		}
		if(StringUtils.isEmpty(accountBank.getAccount())){
			addActionError("请输入银行账号!");
			return "error_ftl";
		}

		accountBank.setAccount(accountBank.getAccount().replaceAll(" ", ""));

		if(StringUtils.isEmpty(accountBank.getBranch())){
			addActionError("请输入开户行名称!");
			return "error_ftl";
		}

		Map<String,Object> map= new HashMap<String ,Object>();
		if(HasKeyWord.hasKeyWord(getServletContext(),user.getUsername())){
			addActionError("含敏感字符,请重新输入!");
			return "error_ftl";
		}
//		if(StringUtils.isEmpty(user.getPassword())){
//			user.setPassword(user1.getPassword());
//		}else{
//			String random = CommonUtil.getRandomString(8);
//			user.setRandomNum(random);//保存随机8位密码
//			user.setPassword( MD5.getMD5Str(MD5.getMD5Str(user.getPassword())+random));//对密码进行加密处理
//		}
		if(StringUtils.isNotEmpty(cardImageFore)){
			user.setCardPic1(cardImageFore);
		}
		if(StringUtils.isNotEmpty(cardImageBack)){
			user.setCardPic2(cardImageBack);
		}
		user.setModifyDate(new Date());

		user.setLastIp(getRequestRemoteAddr());
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("userId", user.getId());
		UserInfo info= userInfoService.queryListByMap(map1).get(0);
		AccountBank  back= accountBankService.queryListByMap(map1).get(0);
		userInfo.setId(info.getId());
		accountBank.setId(back.getId());
		this.userService.updateUser(user,userInfo,accountBank);
		return SUCCESS;

	}

	/**
	 * 添加企业借款人
	 * @return
	 */
	@Action(value="/agency/inputqyBorrower",
			results={@Result(name="success", location="/content/agency/inputqyBorrower.ftl", type="freemarker")})
	public String inputqyBorrower() {
		if(user != null && user.getId() != null){
			user = userService.getById(user.getId());
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userId", user.getId());
			userInfo = userInfoService.queryListByMap(map).get(0);
			List<AccountBank> ablist =accountBankService.queryListByMap(map);
			accountBank = new AccountBank();
			if(ablist.size()>0){
				accountBank = ablist.get(0);
			}

		}
		return SUCCESS;
	}

	/**
	 * 查看详情
	 * @return
	 */
	@Action(value="/agency/lockqyBorrower",
			results={@Result(name="success", location="/content/agency/lockqyBorrower.ftl", type="freemarker")})
	public String lockqyBorrower() {

		if(user != null && user.getId() != null){
			user = userService.getById(user.getId());
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userId", user.getId());
			userInfo = userInfoService.queryListByMap(map).get(0);
			List<AccountBank> ablist = accountBankService.queryListByMap(map);
			accountBank = new AccountBank();
			if(ablist.size()>0){
				accountBank = ablist.get(0);
			}

		}
		return SUCCESS;
	}


	@Action(value="/agency/insertqyBorrower",
			results={@Result(name="success", location="/content/agency/inputqyBorrower.ftl", type="freemarker")})
	public String insertqyBorrower() {
		if(user == null){
			addActionError("请输入用户信息");
			return "error_ftl";
		}
		if(userInfo == null){
			addActionError("请输入用户信息");
			return "error_ftl";
		}
		user.setUsername(user.getRealName());
		user.setPassword("qtz**&&^^qtz");
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(userInfo.getPrivatePhone());
		if(!m.matches()){
			addActionError("手机号码格式不正确!");
			return "error_ftl";
		}
		if(StringUtils.isEmpty(accountBank.getBankId())){
			addActionError("请选择开户银行!");
			return "error_ftl";
		}
		if(StringUtils.isEmpty(accountBank.getAccount())){
			addActionError("请输入银行账号!");
			return "error_ftl";
		}
		accountBank.setAccount(accountBank.getAccount().replaceAll(" ", ""));

		if(StringUtils.isEmpty(accountBank.getBranch())){
			addActionError("请输入开户行名称!");
			return "error_ftl";
		}

		Map<String,Object> map= new HashMap<String ,Object>();
		if(HasKeyWord.hasKeyWord(getServletContext(),user.getUsername())){
			addActionError("含敏感字符,请重新输入!");
			return "error_ftl";
		}
		map.put("username", user.getUsername());
		if(this.userService.getUser(map)!=null){
			addActionError("此用户名已注册,请重新输入!");
			return "error_ftl";
		}
		if(StringUtils.isNotEmpty(areaId)){
			String domain = areaService.getAreaDomain(areaId);
			String[] areaStore = CommonUtil.splitString(domain);

			StringBuffer areaSotreBuffer = new StringBuffer();
			if(StringUtils.isNotEmpty(areaStore[0])){
				user.setProvince(areaStore[0]);
				areaSotreBuffer.append(areaService.getAreaName(areaStore[0]));
				if(StringUtils.isNotEmpty(areaStore[1])){
					user.setCity(areaStore[1]);
					areaSotreBuffer.append(",").append(areaService.getAreaName(areaStore[1]));
					if(StringUtils.isNotEmpty(areaStore[2])){
						user.setArea(areaStore[2]);
						areaSotreBuffer.append(",").append(areaService.getAreaName(areaStore[2]));
					}
				}
			}
			user.setAreaStore(areaSotreBuffer.toString());
		}
		if(StringUtils.isNotEmpty(cardImageFore)){
			user.setCardPic1(cardImageFore);
		}
		if(StringUtils.isNotEmpty(cardImageBack)){
			user.setCardPic2(cardImageBack);
		}

		if(StringUtils.isNotEmpty(img1)){
			userInfo.setPrivateCharterImg(img1);
		}

		if(StringUtils.isNotEmpty(img2)){
			userInfo.setPrivateTaxImg(img2);
		}

		if(StringUtils.isNotEmpty(img3)){
			userInfo.setPrivateOrganizationImg(img3);
		}
		user.setTypeId(1);//0：投资人;1：借款人
		user.setMemberType(1);//0：个人;1：企业
		user.setEmail(user.getEmail());

		String random = CommonUtil.getRandomString(8);
		user.setRandomNum(random);//保存随机8位密码
		user.setPassword( MD5.getMD5Str(MD5.getMD5Str(user.getPassword())+random));//对密码进行加密处理

		user.setAddIp(getRequestRemoteAddr());
		user.setLastIp(getRequestRemoteAddr());
		user.setInviteUserid(getLoginUser().getId());
		user.setStatus(0);
		user.setPhone(userInfo.getPrivatePhone());
		String ip=getRequestRemoteAddr();
		if(user.getId() == null){
			this.userService.addUser(user,userInfo,accountBank,ip,getLoginUser().getAgencyid());
		}else{
			Map<String,Object> map1 = new HashMap<String,Object>();
			map1.put("userId", user.getId());
			UserInfo info= userInfoService.queryListByMap(map1).get(0);
			AccountBank  back= accountBankService.queryListByMap(map1).get(0);
			userInfo.setId(info.getId());
			accountBank.setId(back.getId());
			this.userService.updateUser(user,userInfo,accountBank);
		}
		return SUCCESS;

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public File getImg() {
		return img;
	}

	public void setImg(File img) {
		this.img = img;
	}

	public AccountBank getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(AccountBank accountBank) {
		this.accountBank = accountBank;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyPhone() {
		return keyPhone;
	}

	public void setKeyPhone(String keyPhone) {
		this.keyPhone = keyPhone;
	}

	public String getCardImageFore() {
		return cardImageFore;
	}

	public String getCardImageBack() {
		return cardImageBack;
	}

	public void setCardImageFore(String cardImageFore) {
		this.cardImageFore = cardImageFore;
	}

	public void setCardImageBack(String cardImageBack) {
		this.cardImageBack = cardImageBack;
	}

	public String getImg1() {
		return img1;
	}

	public String getImg2() {
		return img2;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}


}
