package com.qmd.action.user;

import com.qmd.action.base.BaseAction;
import com.qmd.mode.admin.Admin;
import com.qmd.mode.phone.PhoneLimit;
import com.qmd.mode.user.User;
import com.qmd.service.mail.MailService;
import com.qmd.service.phone.PhoneLimitService;
import com.qmd.service.user.UserService;
import com.qmd.service.util.ListingService;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConfigUtil;
import com.qmd.util.ConstantUtil;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 短信发送-注册
 * @author Xsf
 *
 */
@Service("sMSAction")
@InterceptorRefs({
	@InterceptorRef(value = "qmdDefaultStack")
})
public class SMSAction extends BaseAction {
	
	private static final long serialVersionUID = -3317398075827939752L;
	Logger log = Logger.getLogger(SMSAction.class);
	@Resource
	UserService userService;
	@Resource
	MailService mailService;

	@Resource
	PhoneLimitService phoneLimitService;
	@Resource
	ListingService listingService;
	
	private User user;

	private String phoneReg;//注册手机号
	private String codeReg;//手机号验证码
	private String phone;
//	private String ptype;//发送手机号码的类型;0-用户注册，1-其它
	
	
	
	/**
	 * 发送短信
	 * (手机号码作为key保存验证码)
	 * 使用场景： 注册  
	 */
	@Action(value="/sendPCode",results={@Result(type="json")})
	public String sendPCode() {
		// result :0 成功 1 失败
				String smsResult = YueSmsUtils.fail("初始化失败");
		if(StringUtils.isEmpty(phoneReg)) {
//			reason = "系统无法获取手机号";
//			smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
			return ajax(YueSmsUtils.fail("系统无法获取手机号"));
		}
		
		Map<String,Object> map= new HashMap<String ,Object>();
		map.put("phone", phoneReg);
		List<User> userSelecListt=this.userService.getUserList(map);
		
		if(userSelecListt.size()>0 ){
//			reason = "手机号已经注册过帐号";
//			smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason+ "\"}";
			return ajax(YueSmsUtils.fail("手机号已经注册过帐号"));
		}
		
		// 5分钟限制
		boolean isLimit = smsLimitCheck(phoneReg);
		if (isLimit) {
//			reason = "该手机号获取验证码太频繁，请5分钟后再尝试";
//			smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
			return ajax(YueSmsUtils.fail("该手机号获取验证码太频繁，请5分钟后再尝试"));
		}
		
		// 短信开关
		Boolean isSendSms= isSendSwitch();
		
		if(isSendSms){
			String phoneCode= CommonUtil.getRandomString(6);//6位短信验证码
			setSession(User.USER_PHONE_NUM, phoneCode);
            IPYYSMSResult ipyysmsResult = YueSmsUtils.sendForMemberRegister(phoneReg, phoneCode);
            if (ipyysmsResult.ok()) {
                setSession(phoneReg, phoneCode);// 发送成功，保存session
                return ajax(YueSmsUtils.success());
            } else {
                return ajax(YueSmsUtils.fail(ipyysmsResult.getMessage()));
            }

            /*try {
			
				HttpUtil hu=new HttpUtil();
				String content = CommonUtil.CODE_CONTENT + phoneCode+"。欢迎您的加入，轻松乐享财富增值。";
				NoteResult noteResult=hu.sendSms(content, phoneReg);//发送短信
				
				if("0".equals(noteResult.getResult())){
					setSession(phoneReg, phoneCode);// 发送成功，保存session
					reason = "短信发送成功";
					smsResult = "{\"result\":\"" + 0 + "\",\"reason\":\""+ reason + "\"}";
					return ajax(smsResult);
				}else{
					reason = noteResult.getDescription();
					smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\""
							+ reason + "\"}";
					return ajax(smsResult);
				}
				
			} catch (Exception e) {
				reason = "短信功能无法使用";
				smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason
						+ "\"}";
				return ajax(smsResult);
			}*/
		}else{
			setSession(phoneReg, "111111");
			/*reason = "测试短信发送成功";
			smsResult = "{\"result\":\"" + 0 + "\",\"reason\":\""
					+ reason + "\"}";*/
			return ajax(YueSmsUtils.result("0", "测试短信发送成功"));
		}
	
		
	}
	
	/**
	 * 短信号码限制
	 * 
	 * @return true限制 false不限
	 */
	private boolean smsLimitCheck(String checkPhoneNo) {
		
		
		if (StringUtils.isEmpty(checkPhoneNo)) {
			return false;
		}
		Integer phoneNumber=Integer.valueOf(listingService.getKeyValue(ConstantUtil.PHONE_LIMIT));//获取短信限制
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(checkPhoneNo);
		if (!m.matches()) {
			return false;
		}
		
		
//		Map<String,Object> map_p=new HashMap<String,Object>();
//		map_p.put("phone", checkPhoneNo);
//		List<PhoneLimit>phoneLimitList=phoneLimitService.queryListByMap(map_p);
		List<PhoneLimit>phoneLimitList=phoneLimitService.getPhoneLimitList(checkPhoneNo);
		if(phoneLimitList.size()<1){
			PhoneLimit pl=new PhoneLimit();
			pl.setPhone(checkPhoneNo);//限制手机号
			pl.setIp(getRequestRemoteAddr());//ip
			pl.setCreateDate(new Date());//最近时间
			pl.setSendNumber(1);//发送次数
			phoneLimitService.save(pl);
		}else{
			PhoneLimit pl=phoneLimitList.get(0);
			long noeTime=new Date().getTime();
			long latsTime=pl.getCreateDate().getTime();
			long cha=noeTime-latsTime;

			if(cha < 60 * 1000){
				return true;
			}
			
			if(cha>5*60*1000*288){
				//时间差大于5分钟
				pl.setIp(getRequestRemoteAddr());//ip
				pl.setCreateDate(new Date());//最近时间
				pl.setSendNumber(1);//发送次数
				phoneLimitService.update(pl);
			}else{
				//时间差小于5分钟
				if(pl.getSendNumber()>phoneNumber){
					//发送次数大于设定的次数
//					return ajaxJson("M0008_6",ApiConstantUtil.M0008_6);
					return true;
				}else{
					//发送次数小于设定次数
					pl.setIp(getRequestRemoteAddr());//ip
					pl.setSendNumber(pl.getSendNumber()+1);//发送次数
					phoneLimitService.update(pl);
				}
				
				
			}
		}
		return false;
		
	}
	
	/**
	 * 短信开关 
	 * @return true 真发 false 模拟
	 */
	private boolean isSendSwitch() {
		//Boolean isSendSms = false;
		if ("1".equals(ConfigUtil.getConfigUtil().get("qmd.register.sms.switch"))) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 发送短信
	 * (手机号码作为key保存验证码)
	 * 使用场景：1 找回登录密码（手机号码必须已在数据库中）
	 * 
	 * @return
	 */
	@Action(value = "/sendPCodeb", results = { @Result(type = "json") })
	public String sendPCodeb() {
		log.info("发送手机验证码");
		String smsResult = YueSmsUtils.fail("初始化失败");
		if (StringUtils.isEmpty(phoneReg)) {
            smsResult = YueSmsUtils.fail("系统无法获取手机号");
        }
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("phone", phoneReg);
		map.put("isEnabled", true);
		List<User> userList = userService.getUserList(map);
		if (userList==null ||userList.size()<1) {
//			reason = "系统无法获取手机号";
//			smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
            smsResult = YueSmsUtils.fail("系统无法获取手机号");
        }
		
		User user = userList.get(0);
		if (user == null) {
//			reason = "系统无法获取手机号";
//			smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
            smsResult = YueSmsUtils.fail("系统无法获取手机号");
        }
		if (user.getIsLock()) {
//			reason = "用户被锁定";
//			smsResult = "{\"result\":\"" + 2 + "\",\"reason\":\"" + reason + "\"}";
            smsResult = YueSmsUtils.fail("用户被锁定");
        }
		
		// 5分钟限制
		boolean isLimit = smsLimitCheck(phoneReg);
		if (isLimit) {
//			reason = "该手机号获取验证码太频繁，请5分钟后再尝试";
//			smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\""+ reason + "\"}";
			return ajax(YueSmsUtils.fail("该手机号获取验证码太频繁，请5分钟后再尝试"));
		}

		Boolean isSendSms= isSendSwitch();

		if (isSendSms) {
			String phoneCode = CommonUtil.getRandomString(6);// 6位短信验证码
            IPYYSMSResult ipyysmsResult = YueSmsUtils.sendForForgetPwd(phoneReg, phoneCode);
            if (ipyysmsResult.ok()) {
                setSession(phoneReg, phoneCode);
                smsResult = YueSmsUtils.success();
            } else {
                smsResult = YueSmsUtils.fail(ipyysmsResult.getMessage());
            }

            /*try {

				HttpUtil hu = new HttpUtil();
				NoteResult noteResult = hu.sendSms(CommonUtil.CODE_CONTENT + phoneCode+"。您正在找回密码，如不是您本人操作，请立即联系客服。", phoneReg);// 发送短信

				if ("0".equals(noteResult.getResult())) {
					setSession(phoneReg, phoneCode);
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
		} else {
			setSession(phoneReg, "111111");
			/*reason = "测试短信发送成功";
			smsResult = "{\"result\":\"" + 0 + "\",\"reason\":\""
					+ reason + "\"}";*/
            smsResult = YueSmsUtils.result("0", "测试短信发送成功");
        }
		return ajax(smsResult);
	}
	
	/**
	 * 发送短信
	 * 发送会员表中存在的手机号码
	 * (验证码存会员信息)
	 * 使用场景： 
	 */
	@Action(value="/sendPCodeUser",results={@Result(type="json")})
	public String sendPCodeUser() {
		
//		if (getSession(User.USER_ID_SESSION_NAME) == null) {
//			return ajaxJson("E0001",ApiConstantUtil.E0001);
//		}
//		User user = (User) getSession(User.USER_ID_SESSION_NAME);
//		if (user == null || user.getId() == null) {
//			return ajaxJson("E0001",ApiConstantUtil.E0001);
//		}
//		String reason = "初始化失败";
		String smsResult = YueSmsUtils.fail("初始化失败");
		User user = getLoginUser();
		if (user == null || user.getId() == null) {
//			reason = "请登录";
//			smsResult = "{\"result\":\"" + 3 + "\",\"reason\":\"" + reason+ "\"}";

            smsResult = YueSmsUtils.result("3", "请登录");
        }
		if(StringUtils.isEmpty(user.getPhone())) {
//			reason = "系统无法获取手机号";
//			smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
            smsResult = YueSmsUtils.fail("系统无法获取手机号");
        }
		
		// 5分钟限制
		boolean isLimit = smsLimitCheck(user.getPhone());
		if (isLimit) {
//			reason = "该手机号获取验证码太频繁，请5分钟后再尝试";
//			smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\""+ reason + "\"}";
			return ajax(YueSmsUtils.fail("该手机号获取验证码太频繁，请5分钟后再尝试"));
		}
		
		// 短信开关
		Boolean isSendSms= isSendSwitch();
		
		if(isSendSms){
			String phoneCode= CommonUtil.getRandomString(6);//6位短信验证码
            IPYYSMSResult ipyysmsResult = YueSmsUtils.sendForCommon(user.getPhone(), phoneCode);
            if (ipyysmsResult.ok()) {
                setSession(user.getPhone(), phoneCode);// 发送成功，保存session
                smsResult = YueSmsUtils.success();
            } else {
                smsResult = YueSmsUtils.fail(ipyysmsResult.getMessage());
            }

//			setSession(User.USER_PHONE_NUM, phoneCode);
			/*try {
			
				HttpUtil hu=new HttpUtil();
				NoteResult noteResult=hu.sendSms(CommonUtil.CODE_CONTENT+phoneCode, user.getPhone());//发送短信
				
				if("0".equals(noteResult.getResult())){
					setSession(user.getPhone(), phoneCode);// 发送成功，保存session
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
		}else{
			setSession(user.getPhone(), "111111");
//			reason = "测试短信发送成功";
//			smsResult = "{\"result\":\"" + 0 + "\",\"reason\":\""+ reason + "\"}";
            smsResult = YueSmsUtils.result("0", "测试短信发送成功");
        }
		return ajax(smsResult);
		
	}
	
//	/**
//	 * 更换手机号码发送短信
//	 * 发送新号码
//	 *
//	 */
//	@Action(value="/api/sendPCodeUserb",results={@Result(type="json")})
//	public String sendPCodeUserb() {
//		
//		String reason = "初始化失败";
//		String temp = "{\"result\":\"" + 1 + "\",\"reason\":\" " + reason
//				+ "\"}";
//		User user = getLoginUser();
//		if (user == null || user.getId() == null) {
//			reason = "请登录";
//			temp = "{\"result\":\"" + 3 + "\",\"reason\":\"" + reason
//					+ "\"}";
//		}
//		
//		if (StringUtils.isEmpty(phoneReg)) {
//			reason = "系统无法获取手机号";
//			temp = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
//		}
//		if(StringUtils.isEmpty(user.getPhone())) {
//			return ajaxJson("E0002",ApiConstantUtil.E0002); 
//		}
//		
//		if (getSession(user.getPhone()+user.getId())==null) {
//			return ajaxJson("E0002",ApiConstantUtil.E0002);
//		}
//		
//		Map<String,Object> map= new HashMap<String ,Object>();
//		map.put("phone", phoneReg);
//		List<User> userSelecListt=this.userService.getUserList(map);
//		
//		if(userSelecListt.size()>0 ){
//			return ajaxJson("M0008_7",ApiConstantUtil.M0008_7);
//		}
//		
//		// 5分钟限制
//		boolean isLimit = smsLimitCheck(phoneReg);
//		if (isLimit) {
//			return ajaxJson("M0008_6",ApiConstantUtil.M0008_6);
//		}
//		
//		// 短信开关
//		Boolean isSendSms= isSendSwitch();
//		
//		if(isSendSms){
//			String phoneCode= CommonUtil.getRandomString(6);//6位短信验证码
//			try {
//			
//				HttpUtil hu=new HttpUtil();
//				NoteResult noteResult=hu.sendSms(CommonUtil.CODE_CONTENT+phoneCode, phoneReg);//发送短信
//				
//				if("0".equals(noteResult.getResult())){
//					setSession(phoneReg, phoneCode);// 发送成功，保存session
//					return ajaxJson("R0001","短信发送成功");
//				}else{
//					return ajaxJson("M0008_8",noteResult.getDescription());
//				}
//				
//			} catch (Exception e) {
//				return ajaxJson("S0001",ApiConstantUtil.S0001);
//			}
//		}else{
//			setSession(phoneReg, "111111");
//			return ajaxJson("R0001","测试验证码：111111");
//			
//		}
//		
//	}
//	
//	
//	
//	/**
//	 * 找回登录密码验证
//	 * @return
//	 */
//	@Action(value="/api/checklogp",results={@Result(type="json")})
//	@InputConfig(resultName = "error_ftl,success_ftl")
//	public String apiChecklogp() throws Exception{
//		
//		
//		if (StringUtils.isEmpty(codeReg)) {
//			return ajaxJson("M0008_0", ApiConstantUtil.M0008_0);
//		}
//		if (StringUtils.isEmpty(phone)) {
//			return ajaxJson("M0008_13", ApiConstantUtil.M0008_13);
//		}
//		
//		if(getSession(phone)==null) {
//			return ajaxJson("M0008_1",ApiConstantUtil.M0008_1);
//		} 
//		if(!codeReg.equals(getSession(phone))){
//			return ajaxJson("M0008_1",ApiConstantUtil.M0008_1);
//		}
//		
//		// 5分钟限制
//		boolean isLimit = smsLimitCheck(phone);
//		if (isLimit) {
//			return ajaxJson("M0008_6",ApiConstantUtil.M0008_6);
//		}
//		
//		Map<String,Object> map= new HashMap<String ,Object>();
//		map.put("username", phone);
//		User getuser = this.userService.getUser(map);
//		if (getuser==null||getuser.getId()==null) {
//			return ajaxJson("M0008_14",ApiConstantUtil.M0008_14);
//		}
//		
//		String  randomPwd = CommonUtil.getRandomString(8);
//		
//		// 短信开关
//				Boolean isSendSms= isSendSwitch();
//				
//				if(isSendSms){
//					try {
//					
//						HttpUtil hu=new HttpUtil();
//						NoteResult noteResult=hu.sendSms(CommonUtil.PWS_CONTENT+randomPwd, phone);//发送短信
//						
//						if("0".equals(noteResult.getResult())){
//							
//						}else{
//							return ajaxJson("M0008_8",noteResult.getDescription());
//						}
//						
//					} catch (Exception e) {
//						return ajaxJson("S0001",ApiConstantUtil.S0001);
//					}
//				}else{
//					randomPwd = "111111";
//				}
//		
//		User nuser = new User();
//		
//		nuser.setId(getuser.getId());
//		nuser.setPassword(PWDUtil.encode(randomPwd, getuser.getRandomNum()));//对密码进行加密处理
//		
//		userService.updatePassowrd(nuser);
//		
//		return ajaxJson("R0001","测试密码：111111");
//	}
	
	
	
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

//	public String getPtype() {
//		return ptype;
//	}
//
//	public void setPtype(String ptype) {
//		this.ptype = ptype;
//	}

}
