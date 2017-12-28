package com.qmd.action.user;

import com.qmd.action.base.BaseAction;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.service.user.AccountBankService;
import com.qmd.service.user.UserService;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userBankAction")
@InterceptorRefs({
	@InterceptorRef(value = "userVerifyInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
public class UserBankAction extends BaseAction {
	
	private static final long serialVersionUID = -7106951613771711040L;
	private String smsCode;//短信验证码
	private User user;
	private AccountBank accountBank;
	
	@Resource
	UserService userService;
	@Resource
	AccountBankService accountBankService;
	
	public List<AccountBank> getAccountBankList(){
		return this.userService.queryAccountBank(getLoginUser().getId());
	}
	
	/**
	 * 跳转到添加/编辑银行账户页面
	 * @return
	
	@Action(value="/userBank/toBank",
			results={@Result(name="success", location="/content/user/bank_detail.ftl", type="freemarker"),
					@Result(name="input", location="/content/user/bank_input.ftl", type="freemarker"),
					@Result(name="msg", location="/content/message_page.ftl", type="freemarker")})
	public String toBank(){
		if(getAccountBankList().size() >0){
			accountBank = getAccountBankList().get(0);
			return SUCCESS;
		}else{
			return "input";
		}
	}
	 */
	
	/**
	@Action(value="/userBank/bankSignSave",results={@Result(name="success", location="/content/message_page.ftl", type="freemarker")})
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String bankSignSave() {
		reloadUser();
		User userLogin = getLoginUser();
		msgUrl = "/userBank/toBank.do";
		if(accountBank.getId()==null){
			if(getAccountBankList().size()>=1){
				msg="一个用户最多添加1个银行账号!";
				return "error_ftl";
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
		// 验证手机验证码
		if (StringUtils.isBlank(smsCode)) {
			msg = "请输入短信验证码";
			return "error_ftl";
		}

		if (!smsCode.equals(userLogin.getPhoneCode())) {
			msg = "短信验证码不正确";
			return "error_ftl";
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
		if(StringUtils.isEmpty(accountBank.getBranch())){
			msg="请输入开户行名称!";
			return "error_ftl";
		}
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
		User qUser = userService.getUser(qMap);
		if(qUser != null){
			if(qUser.getId().compareTo(userLogin.getId())!=0){
				msg="该身份证已被认证!";
				return "error_ftl";
			}
			
		}else{
			userLogin.setCardId(user.getCardId());
			userLogin.setCardType("1");
			String birthday = user.getCardId().substring(6, 14);
			userLogin.setBirthday(CommonUtil.getString2Date(birthday, "yyyyMMdd"));
			String sex ="1";
			String str= user.getCardId().substring(16, 17);
			if((Integer.valueOf(str)/2)==0){
				sex = "2";
			}
			userLogin.setSex(sex);
		}
		accountBank.setModifyDate(new Date());
		accountBank.setAddIp(getRequestRemoteAddr());
		if(accountBank.getId()==null){
			accountBank.setCreateDate(new Date());
			accountBank.setUserId(userLogin.getId());
			accountBank.setBtype("0");
		}else{
			accountBank.setBtype("1");
		}

		int ret = accountBankService.insertBankInfo(userLogin, accountBank);
		if(ret ==1 ){
			msg = "绑卡认证成功";
		}else{
			msg = "绑卡认证失败";
		}
		return SUCCESS;
	}
 */
	/**
	 * 获取手机验证码
	 * @return
	 
	@Action(value="/userBank/ajaxGetPhoneCode",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String ajaxGetPhoneCode(){
		removeCookie(ConstantUtil.USER_PHONE_COOKIE_NAME);
		String code="";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date d = new Date();
		String tranDateTime = simpleDateFormat.format(d);
		String timestamp = d.getTime()+"";
		String mobile = getLoginUser().getPhone();
		String jsonBody ="{\"mobile\":\""+mobile+"\",\"exCode\":\"HZJCB_UNPY_SMSCODE\",\"merId\":\"1000000503\",\"tranDateTime\": \""+tranDateTime+"\",\"timestamp\": \""+timestamp+"\"}";	
		UnionPayInfo info = UnionPayUtils.unionToPay(jsonBody);
		if("0000".equals(info.getRetCode())){
			code = "";
			setCookie(ConstantUtil.USER_PHONE_COOKIE_NAME, code,60);
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put(STATUS_PARAMETER_NAME, Status.success);
			jsonMap.put(MESSAGE_PARAMETER_NAME, "验证码发送成功");
			jsonMap.put("code", code);
			return ajax(jsonMap);
		}else{
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put(STATUS_PARAMETER_NAME, Status.error);
			jsonMap.put(MESSAGE_PARAMETER_NAME, "验证码发送失败");
			return ajax(jsonMap);
		}
		
		
	}
	*/
	
	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public AccountBank getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(AccountBank accountBank) {
		this.accountBank = accountBank;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
