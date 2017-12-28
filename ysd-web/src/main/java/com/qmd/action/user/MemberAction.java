package com.qmd.action.user;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.mode.phone.PhoneLimit;
import com.qmd.mode.user.User;
import com.qmd.service.phone.PhoneLimitService;
import com.qmd.service.util.ListingService;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;
import com.qmd.util.ImageUtil;
import com.qmd.util.md5.MD5;
import com.qmd.util.md5.PWDUtil;
import com.ysd.biz.YueSmsUtils;
import com.ysd.ipyy.IPYYSMSResult;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员
 * 
 * @author Administrator
 * 
 */
@Service("memberAction")
@InterceptorRefs({ @InterceptorRef(value = "userVerifyInterceptor"),
		@InterceptorRef(value = "qmdDefaultStack") })
public class MemberAction extends BaseAction {

	private static final long serialVersionUID = 699796170124365177L;

	Logger log = Logger.getLogger(MemberAction.class);

	private User user;
	private String p;
	private String newPassword;// 新密码
	private String newPayPassword;// 支付密码
	private String newPassword2;// 重复输入的密码
	private String oldPassword;// 新密码
	private String oldPayPassword;// 支付密码
	private String phoneReg;// 注册手机号
	private String code;// 验证码
	private File imageFile;// 上传图片文件
	private String loginRedirectUrl;

	@Resource
	ListingService listingService;
	@Resource
	PhoneLimitService phoneLimitService;

	/**
	 * 修改登录密码
	 * 
	 * @return
	 */
	@Action(value = "/member/passwordUpdate")
	public String passwordUpdate() {
		User userLogin = getLoginUser();
		User uu = userService.getById(getLoginUser().getId(), new User());
		String temp = "修改成功，请妥善保存，为了资金安全，请勿外泄";
		if (p.equals("1")) {
			log.info("执行登录密码修改方法;老密码" + user.getPassword());
			log.info("执行登录密码修改方法;老密码" + MD5.getMD5Str(user.getPassword()));
			log.info("执行登录密码修改方法;新密码" + newPassword);
			log.info("执行登录密码修改方法;新密码2" + newPassword2);
			if (!newPassword2.equals(newPassword)) {
				log.info("两次输入的密码不相同");
				msg = "两次输入的密码不相同";
				msgUrl = "";
				return "error_ftl";
			}
			if (!userService.isPassword(userLogin.getUsername(),
					user.getPassword(), "0")) {
				log.info("当前密码不正确");
				msg = "当前密码不正确";
				msgUrl = "";
				return "error_ftl";
			}
			uu.setPassword(PWDUtil.encode(newPassword, userLogin.getRandomNum()));
		} else if (p.equals("2")) {
			log.info("执行交易密码修改方法");

			// 验证手机验证码
			if (StringUtils.isBlank(code)) {
				msg = "请输入验证码";
				msgUrl = "";
				return "error_ftl";
			}

			if (!code.equals(uu.getPhoneCode())) {
				msg = "验证码不正确";
				msgUrl = "";
				return "error_ftl";
			}
			temp = "设置成功，请妥善保存，为了资金安全，请勿外泄";

			uu.setPayPassword(PWDUtil.encode(newPayPassword,
					userLogin.getRandomNum()));

		}
		uu.setModifyDate(new Date());

		this.userService.updatePassowrd(uu);
		log.info("密码" + temp + "成功");

		setSession(User.USER_ID_SESSION_NAME, uu);
		msg = temp;
		msgUrl = "";
		return "success_ftl";
	}

	/**
	 * 验证登录密码
	 * 
	 * @return
	 */
	@Action(value = "/member/ajaxPassword", results = { @Result(type = "plainText") })
	@InputConfig(resultName = "ajaxError")
	public String ajaxPassword() {
		User userLogin = getLoginUser();
		if (!userService.isPassword(userLogin.getUsername(),
				user.getPassword(), "0")) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}

	/**
	 * 跳转到登录密码页面
	 * 
	 * @return
	 */
	@Action(value = "/member/toPassword", results = { @Result(name = "success", location = "/content/user/password.ftl", type = "freemarker") })
	public String toPassword() {
		log.info("跳转到修改登录密码页面");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.getLoginUser().getId());
		user = this.userService.getUser(map);
		return SUCCESS;
	}

	/**
	 * 跳转到支付页面
	 * 
	 * @return
	 */
	@Action(value = "/member/toPayPassword", results = { @Result(name = "success", location = "/content/user/pay_password.ftl", type = "freemarker") })
	public String toPayPassword() {
		log.info("跳转到修改支付密码页面");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.getLoginUser().getId());
		user = this.userService.getUser(map);
		return SUCCESS;
	}

	/**
	 * 跳转到实名认证页面
	 * 
	 * @return
	 */
	@Action(value = "/member/toRealName", results = { @Result(name = "success", location = "/content/user/realname.ftl", type = "freemarker") })
	public String toRealName() {
		log.info("跳转到实名认证页面");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.getLoginUser().getId());
		user = this.userService.getUser(map);
		if(StringUtils.isNotEmpty(user.getRealName())){
			if(user.getRealName().length() > 1){// 姓名打码
				String name = user.getRealName();
				int l = name.length()-1;
				for(int i=0;i<l;i++){
					if(i==0){
						name = name.substring(0, 1) + "*";
					}else{
						name += "*";
					}			
				}
				
				user.setRealName(name);
			}
		}
		return SUCCESS;
	}

	/**
	 * 发送手机验证码-修改安全密码
	 * 
	 * @return
	 */
	@Action(value = "/sendMyPhoneCode", results = { @Result(type = "json") })
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String sendMyPhoneCode() {
		log.info("发送手机验证码");
		// result :0 成功 1 失败
		String smsResult = YueSmsUtils.fail("初始化失败");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.getLoginUser().getId());
		user = this.userService.getUser(map);
		if (StringUtils.isBlank(user.getPhone())) {
            smsResult = YueSmsUtils.fail("系统无法获取手机号");
        } else {

			// --------发送注册验证码开始----------------

			// ------------------------五分钟短信限制----------------------------------------------
			Integer phoneNumber = Integer.valueOf(listingService
					.getKeyValue(ConstantUtil.PHONE_LIMIT));// 获取短信限制
			Map map_p = new HashMap<Object, String>();
			map_p.put("phone", user.getPhone());
			List<PhoneLimit> phoneLimitList = phoneLimitService
					.queryListByMap(map_p);
			if (phoneLimitList.size() < 1) {
				PhoneLimit pl = new PhoneLimit();
				pl.setPhone(user.getPhone());// 限制手机号
				pl.setIp(getRequestRemoteAddr());// ip
				pl.setCreateDate(new Date());// 最近时间
				pl.setSendNumber(1);// 发送次数
				phoneLimitService.save(pl);
			} else {
				PhoneLimit pl = phoneLimitList.get(0);
				long noeTime = new Date().getTime();
				long latsTime = pl.getCreateDate().getTime();
				long cha = noeTime - latsTime;
				if (cha > 5 * 60 * 1000) {
					// 时间差大于5分钟
					pl.setIp(getRequestRemoteAddr());// ip
					pl.setCreateDate(new Date());// 最近时间
					pl.setSendNumber(1);// 发送次数
					phoneLimitService.update(pl);
				} else {
					// 时间差小于5分钟
					if (pl.getSendNumber() > phoneNumber) {
						// 发送次数大于设定的次数
						return ajax(YueSmsUtils.fail("该手机号获取验证码太频繁，请5分钟后再尝试"));
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
//			phoneCode = "111111";//@zdl测试短信验证码
			// ---------------保存验证码--------------
			User uu = new User();
			uu.setPhoneCode(phoneCode);
			uu.setId(user.getId());
			userService.updatePhoneCode(uu);
			// ---------------保存验证码 end--------------


            IPYYSMSResult ipyysmsResult = YueSmsUtils.sendForUpdateTradePwd(user.getPhone(), phoneCode);
            if (ipyysmsResult.ok()) {
                smsResult = YueSmsUtils.success();
            } else {
                smsResult = YueSmsUtils.fail(ipyysmsResult.getMessage());
            }

            /*try {
				HttpUtil hu = new HttpUtil();
				NoteResult noteResult = hu.sendSms(CommonUtil.CODE_CONTENT+ phoneCode+"。您正在修改交易密码，如不是您本人操作，请立即联系客服。", user.getPhone());// 发送短信
//				noteResult.setResult("0");//@zdl测试短信验证码
				if ("0".equals(noteResult.getResult())) {
					reason = "短信发送成功";
					temp = "{\"result\":\"" + 0 + "\",\"reason\":\"" + reason
							+ "\"}";
				} else {
					reason = noteResult.getDescription();
					temp = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason
							+ "\"}";
				}
			} catch (Exception e) {
				reason = "短信功能无法使用";
				temp = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason
						+ "\"}";
			}*/

			log.info("发送注册验证码结束");

			// --------发送注册验证码结束----------------

		}
		return ajax(smsResult);
	}


	/**
	 * 发送手机验证码-提现
	 * 
	 * @return
	 */
	@Action(value = "/sendPhoneCodeCash", results = { @Result(type = "json") })
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String sendPhoneCodeCash() {
		log.info("发送手机验证码");
		// result :0 成功 1 失败
//		String reason = "初始化失败";
		String smsResult = YueSmsUtils.fail("初始化失败");// "{\"result\":\"" + 1 + "\",\"reason\":\" " + reason + "\"}";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.getLoginUser().getId());
		user = this.userService.getUser(map);
		if (StringUtils.isBlank(user.getPhone())) {
//			reason = "系统无法获取手机号";
//			smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason + "\"}";
            smsResult = YueSmsUtils.fail("系统无法获取手机号");
        } else {

			// --------发送注册验证码开始----------------

			// ------------------------五分钟短信限制----------------------------------------------
			Integer phoneNumber = Integer.valueOf(listingService
					.getKeyValue(ConstantUtil.PHONE_LIMIT));// 获取短信限制
			Map map_p = new HashMap<Object, String>();
			map_p.put("phone", user.getPhone());
			List<PhoneLimit> phoneLimitList = phoneLimitService
					.queryListByMap(map_p);
			if (phoneLimitList.size() < 1) {
				PhoneLimit pl = new PhoneLimit();
				pl.setPhone(user.getPhone());// 限制手机号
				pl.setIp(getRequestRemoteAddr());// ip
				pl.setCreateDate(new Date());// 最近时间
				pl.setSendNumber(1);// 发送次数
				phoneLimitService.save(pl);
			} else {
				PhoneLimit pl = phoneLimitList.get(0);
				long noeTime = new Date().getTime();
				long latsTime = pl.getCreateDate().getTime();
				long cha = noeTime - latsTime;
				if (cha > 5 * 60 * 1000) {
					// 时间差大于5分钟
					pl.setIp(getRequestRemoteAddr());// ip
					pl.setCreateDate(new Date());// 最近时间
					pl.setSendNumber(1);// 发送次数
					phoneLimitService.update(pl);
				} else {
					// 时间差小于5分钟
					if (pl.getSendNumber() > phoneNumber) {
						// 发送次数大于设定的次数
						/*reason = "该手机号获取验证码太频繁，请5分钟后再尝试";
						smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\""
								+ reason + "\"}";*/
						return ajax(YueSmsUtils.fail("该手机号获取验证码太频繁，请5分钟后再尝试"));
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

			System.out.println("------------------------phoneCode:"+phoneCode);
			// ---------------保存验证码--------------
			User uu = new User();
			uu.setPhoneCode(phoneCode);
//			uu.setPhoneCode("111111");//@zdl测试短信验证码
			uu.setId(user.getId());
			userService.updatePhoneCode(uu);
			// ---------------保存验证码 end--------------

            IPYYSMSResult ipyysmsResult = YueSmsUtils.sendForGetCash(user.getPhone(), phoneCode);
            if (ipyysmsResult.ok()) {
                smsResult = YueSmsUtils.success();
            } else {
                smsResult = YueSmsUtils.fail(ipyysmsResult.getMessage());
            }

            /*try {
				HttpUtil hu = new HttpUtil();
				NoteResult noteResult = hu.sendSms(CommonUtil.CODE_CONTENT+ phoneCode+"。您正在申请提现，如不是您本人操作，请立即联系客服。", user.getPhone());// 发送短信
//				noteResult.setResult("0");//@zdl测试短信验证码
				if ("0".equals(noteResult.getResult())) {
					reason = "短信发送成功";
					smsResult = "{\"result\":\"" + 0 + "\",\"reason\":\"" + reason
							+ "\"}";
				} else {
					reason = noteResult.getDescription();
					smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason
							+ "\"}";
				}
			} catch (Exception e) {
				reason = "短信功能无法使用";
				smsResult = "{\"result\":\"" + 1 + "\",\"reason\":\"" + reason
						+ "\"}";
			}*/

			log.info("发送注册验证码结束");

			// --------发送注册验证码结束----------------

		}
		return ajax(smsResult);
	}
	
	/**
	 * 修改实名认证信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "/member/identity", results = { @Result(name = "success", location = "realname", type = "redirectAction") })
	@InputConfig(resultName = "error_ftl")
	public String identity() throws IOException {
		User userLogin = getLoginUser();
		if (userLogin.getRealStatus() == 1) {
			msg = "您的账户认证已通过,不要重复申请！";
			msgUrl = "";
			return "error_ftl";
		}

		if (StringUtils.isNotEmpty(user.getRealName())) {
			userLogin.setRealName(user.getRealName());// 真实姓名
		} else {
			msg = "请输入真实姓名";
			msgUrl = "";
			return "error_ftl";
		}
		userLogin.setCardType("1");// 证件类别 默认为身份证

		if (StringUtils.isNotEmpty(user.getCardId())) {
			userLogin.setCardId(user.getCardId());// 证件号码
		} else {
			msg = "请输入身份证号码";
			msgUrl = "";
			return "error_ftl";
		}
		// userLogin.setRealStatus(ConstantUtil.APPLY_STATUS_ING);//身份实名认证中
		userLogin.setModifyDate(new Date());
		int ret = this.userService.authRealName(userLogin);
		if (ret == 2) {
			msg = "此身份证已认证";
			msgUrl = "";
			return "error_ftl";
		} else if (ret == 1) {
			msg = "姓名和身份证不匹配";
			msgUrl = "";
			return "error_ftl";
		}
		setSession(User.USER_ID_SESSION_NAME, userLogin);
//		msg = "实名认证成功";
//		msgUrl = "/member/realname.do";
		addActionMessage("实名认证成功");
		redirectUrl = getContextPath() + "/userCenter/borrowDetailList.do";
		return "success_ftl";
	}

	/**
	 * 跳转到修改头像页面
	 * 
	 * @return
	 */
	@Action(value = "/member/toLitpic", results = { @Result(name = "success", location = "/content/user/litpic.ftl", type = "freemarker") })
	public String toLitpic() {
		log.info("跳转到修改头像页面");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.getLoginUser().getId());
		user = this.userService.getUser(map);
		return SUCCESS;
	}

	/**
	 * 上传头像
	 * 
	 * @return
	 */
	@Action(value = "/member/ajaxUploadLitpic")
	@InputConfig(resultName = "ajaxError")
	public String ajaxUploadLitpic() {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (imageFile == null) {
			jsonMap.put("error", 1);
			jsonMap.put("message", "请选择上传文件!");
			return ajax(jsonMap);
		}

		String uploadPath = ImageUtil.copyImageFile(getServletContext(),
				imageFile);

		if (uploadPath == null) {
			jsonMap.put("error", 1);
			jsonMap.put("message", "请选择正确文件!");
			return ajax(jsonMap);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.getLoginUser().getId());
		user = this.userService.getUser(map);
		user.setLitpic(uploadPath);
		this.userService.updateLitpic(user);

		jsonMap.put("error", 0);
		jsonMap.put("url", uploadPath);

		return ajax(jsonMap);
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

	public String getNewPassword2() {
		return newPassword2;
	}

	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
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

	public String getPhoneReg() {
		return phoneReg;
	}

	public void setPhoneReg(String phoneReg) {
		this.phoneReg = phoneReg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public String getLoginRedirectUrl() {
		return loginRedirectUrl;
	}

	public void setLoginRedirectUrl(String loginRedirectUrl) {
		this.loginRedirectUrl = loginRedirectUrl;
	}
	
	
	
	@Action(value = "/member/passwordUpdateNew")
	public String passwordUpdateNew() {
		User userLogin = getLoginUser();
		User uu = userService.getById(getLoginUser().getId(), new User());
		String temp = "修改成功，请妥善保存，为了资金安全，请勿外泄";
		if (p.equals("1")) {
			log.info("执行登录密码修改方法;老密码" + user.getPassword());
			log.info("执行登录密码修改方法;老密码" + MD5.getMD5Str(user.getPassword()));
			log.info("执行登录密码修改方法;新密码" + newPassword);
			log.info("执行登录密码修改方法;新密码2" + newPassword2);
			if (!newPassword2.equals(newPassword)) {
				log.info("两次输入的密码不相同");
				msg = "两次输入的密码不相同";
				msgUrl = "";
				//return "error_ftl";
				return "两次输入的密码不相同";
			}
			if (!userService.isPassword(userLogin.getUsername(),
					user.getPassword(), "0")) {
				log.info("当前密码不正确");
				msg = "当前密码不正确";
				msgUrl = "";
				return "error_ftl";
			}
			uu.setPassword(PWDUtil.encode(newPassword, userLogin.getRandomNum()));
		} else if (p.equals("2")) {
			log.info("执行交易密码修改方法");

			// 验证手机验证码
			if (StringUtils.isBlank(code)) {
				msg = "请输入验证码";
				msgUrl = "";
				return "error_ftl";
			}

			if (!code.equals(uu.getPhoneCode())) {
				msg = "验证码不正确";
				msgUrl = "";
				return "error_ftl";
			}
			temp = "设置成功，请妥善保存，为了资金安全，请勿外泄";

			uu.setPayPassword(PWDUtil.encode(newPayPassword,
					userLogin.getRandomNum()));

		}
		uu.setModifyDate(new Date());

		this.userService.updatePassowrd(uu);
		log.info("密码" + temp + "成功");

		setSession(User.USER_ID_SESSION_NAME, uu);
		msg = temp;
		msgUrl = "";
		return "success_ftl";
	}
}
