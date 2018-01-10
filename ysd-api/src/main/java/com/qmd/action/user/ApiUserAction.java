package com.qmd.action.user;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.BaseBean;
import com.qmd.bean.userInfo.UserBean;
import com.qmd.bean.userInfo.UserRealBean;
import com.qmd.bean.userInfo.UserSafePwdBean;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.service.borrow.BorrowTenderService;
import com.qmd.service.user.AccountBankService;
import com.qmd.service.user.UserService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.ConfigUtil;
import com.qmd.util.JsonUtil;
import com.qmd.util.md5.PWDUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户
 */
@Service("apiUserAction")
@InterceptorRefs({ @InterceptorRef(value = "apiUserInterceptor"),
		@InterceptorRef(value = "qmdDefaultStack") })
public class ApiUserAction extends ApiBaseAction {

	private static final long serialVersionUID = -8440843074109637391L;

	Logger log = Logger.getLogger(ApiUserAction.class);
	@Resource
	UserService userService; 
	@Resource
	AccountBankService accountBankService;

//	private Integer userId;
	private String realName;
	private String cardId;
	private String oldPassword;
	private String newPassword;
	private String codeReq;
	private String phone;
	private User user;
	private String codeReg;//手机号验证码

	@Action(value = "/api/user")
	public String apiUserDetail() {

//		if (id == null) {
//			return ajaxJson("E0002", ApiConstantUtil.E0002);
//		}
//		if (Integer.parseInt(id) != getLoginUser().getId().intValue()) {
//			return ajaxJson("E0002", ApiConstantUtil.E0002);
//		}

		User user = userService.getById(getLoginUser().getId(), new User());
		if (user == null) {
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}

		UserBean userBean = new UserBean(user);

		List<AccountBank> list = accountBankService
				.getAccountBankList(getLoginUser().getId());
		if (list == null || list.size() == 0) {
			userBean.setBankStatus(0);
		} else {
			if(list.get(0).getStatus().compareTo(1) ==0){
				userBean.setBankStatus(1);
			}else{
				userBean.setBankStatus(0);
			}
		}
		
		userBean.setHandStatus(user.getGesture()!=null && !"".equals(user.getGesture()) ?1:0);

		return ajax(JsonUtil.toJson(userBean));

	}

	@Action(value = "/api/userReal/detail")
	public String apiUserRealDetail() {

//		if (id == null) {
//			return ajaxJson("E0002", ApiConstantUtil.E0002);
//		}
//		if (Integer.parseInt(id) != getLoginUser().getId().intValue()) {
//			return ajaxJson("E0002", ApiConstantUtil.E0002);
//		}

		User user = userService.getById(getLoginUser().getId(), new User());
		if (user == null) {
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}

		UserRealBean userRealBean = new UserRealBean(user);

		return ajax(JsonUtil.toJson(userRealBean));

	}

	@Action(value = "/api/userReal")
	public String apiUserReal() {


		User loguser = userService.getById(getLoginUser().getId(), new User());
		if (loguser == null) {
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}

		if (loguser.getRealStatus() != null && loguser.getRealStatus() == 1) {
			return ajaxJson("M0008", ApiConstantUtil.M0008);
		}
		if (loguser.getRealStatus() != null && loguser.getRealStatus() == 2) {
			return ajaxJson("M0009", ApiConstantUtil.M0009);
		}
		if(StringUtils.isEmpty(user.getRealName())){
			return ajaxJson("M0009_3", "请输入真实姓名");
		}
		if(StringUtils.isEmpty(user.getCardId())){
			return ajaxJson("M0009_4", "请输入身份证号");
		}
		if(StringUtils.isEmpty(user.getPayPassword())){
			return ajaxJson("M0009_4", "请设置交易密码");
		}

		User entity = loguser;
		entity.setRealName(user.getRealName());
		entity.setCardId(user.getCardId());// 证件号码
		entity.setModifyDate(new Date());
		entity.setRealStatus(0);
		entity.setPayPassword(PWDUtil.encode(user.getPayPassword(),entity.getRandomNum()));
		int ret = this.userService.authRealName(entity);
		BaseBean baseBean = new BaseBean();
		if(ret == 2){
			return ajaxJson("M0009_1", ApiConstantUtil.M0009_1);
		}else if(ret ==1){
			return ajaxJson("M0009_2", ApiConstantUtil.M0009_2);
		}else{
			baseBean.setRcd("R0001");
		}
		return ajax(JsonUtil.toJson(baseBean));

	}
	
	
	@Action(value = "/api/userSafePwd/detail")
	public String apiUserSafePwdDetail() {

//		if (id == null) {
//			return ajaxJson("E0002", ApiConstantUtil.E0002);
//		}
//		if (Integer.parseInt(id) != getLoginUser().getId().intValue()) {
//			return ajaxJson("E0002", ApiConstantUtil.E0002);
//		}

		User user = userService.getById(getLoginUser().getId(), new User());
		if (user == null) {
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}

		UserSafePwdBean userSafePwdBean = new UserSafePwdBean(user);

		return ajax(JsonUtil.toJson(userSafePwdBean));

	}

	
	/**
	 * 新的修改交易密码
	 * @author
	 */
	@Action(value = "/api/userSafePasswordUpdate")
	public String apiUserSafePasswordUpdate() {

		if (StringUtils.isEmpty(codeReq)) {
			return ajaxJson("E0002", ApiConstantUtil.M0008_0);
		}
		if(getSession(getLoginUser().getPhone())==null) {
			return ajaxJson("E0002", ApiConstantUtil.M0008_12);
		}
		String aa = (String)getSession(getLoginUser().getPhone());

        if (!ConfigUtil.getConfigUtil().isTestSmsCode(codeReq)) {
            log.error("重置交易密码,codeReq:" + codeReq + "**aa" + aa);
            if (StringUtils.isEmpty(aa)) {
                return ajaxJson("E0002", ApiConstantUtil.M0008_12);
            }

            if(!aa.equals(codeReq)) {
                return ajaxJson("E0002", ApiConstantUtil.M0008_1);
            }
        } else {
            log.info("使用万能短信验证码");
        }

		
		User user = userService.getById(getLoginUser().getId(), new User());
		if (user == null) {
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}

		if (StringUtils.isEmpty(newPassword)) {
			return ajaxJson("E0002", "请输入新的交易密码");
		}
		if(StringUtils.isEmpty(cardId)){
			return ajaxJson("E0002", "请输入身份证后8位");
		}
		if(user.getCardId().length()<8){
			return ajaxJson("E0002", "原始身份证号码有误");
		}
		if(!cardId.equals(user.getCardId().substring(user.getCardId().length()-8, user.getCardId().length()))){
			return ajaxJson("E0002", "身份证后8位输入错误");
		}

		user.setPayPassword(PWDUtil.encode(newPassword,user.getRandomNum()));
		userService.updatePassowrd(user);
		
		BaseBean bean = new BaseBean();
		bean.setRcd("R0001");
		bean.setRmg("设置成功");

		return ajax(JsonUtil.toJson(bean));

	}
	
	@Action(value = "/api/userSafePwdUpdate")
	public String apiUserSafePwdUpdate() {
		
		if (StringUtils.isEmpty(newPassword)) {
			return ajaxJson("E0002", "请输入新的交易密码");
		}
		if (StringUtils.isEmpty(oldPassword)) {
			return ajaxJson("E0002", "请输入之前的交易密码");
		}
		User user = userService.getById(getLoginUser().getId(), new User());
		if (user == null) {
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}
		if (!StringUtils.isEmpty(user.getPayPassword())) {
			
			
			
			if(!user.getPayPassword().equals(PWDUtil.encode(oldPassword,user.getRandomNum()))) {
				return ajaxJson("S0002", ApiConstantUtil.S0002);
			}
		}
		
		user.setPayPassword(PWDUtil.encode(newPassword,user.getRandomNum()));
		
		userService.updatePassowrd(user);
		
		BaseBean bean = new BaseBean();
		bean.setRcd("R0001");
		bean.setRmg("设置成功");

//		UserSafePwdBean userSafePwdBean = new UserSafePwdBean(user);

		return ajax(JsonUtil.toJson(bean));

	}
	
	@Action(value = "/api/userLoginPwdUpdate")
	public String userLoginPwdUpdate() {
		
		if (StringUtils.isEmpty(oldPassword)) {
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}
		
		if (StringUtils.isEmpty(newPassword)) {
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}

		User user = userService.getById(getLoginUser().getId(), new User());
		if (user == null||StringUtils.isEmpty(user.getPassword())) {
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}
		
		if(!user.getPassword().equals(PWDUtil.encode(oldPassword,user.getRandomNum()))) {
			return ajaxJson("S0003", ApiConstantUtil.S0003);
		}
		
		user.setPassword(PWDUtil.encode(newPassword,user.getRandomNum()));
		
		userService.updatePassowrd(user);
		
		BaseBean bean = new BaseBean();
		bean.setRcd("R0001");
		bean.setRmg("设置成功");

//		UserSafePwdBean userSafePwdBean = new UserSafePwdBean(user);

		return ajax(JsonUtil.toJson(bean));

	}
	
	/**
	 * 重置登录密码
	 */
	@Action(value = "/api/lostLoginPwd")
	public String lostLoginPwd() {
		
		if (StringUtils.isEmpty(phone)) {
			return ajaxJson("M0008_13", ApiConstantUtil.M0008_13);
		}
		
		if (StringUtils.isEmpty(codeReq)) {
			return ajaxJson("M0008_0", ApiConstantUtil.M0008_0);
		}
		
		if(getSession(phone)==null) {
			return ajaxJson("M0008_12", ApiConstantUtil.M0008_12);
		}
		if(StringUtils.isEmpty(newPassword)){
			return ajaxJson("M0008_13", "请输入密码");
		}
		String aa = (String)getSession(phone);


        if (!ConfigUtil.getConfigUtil().isTestSmsCode(codeReg)) {


            if(!aa.equals(codeReq)) {
                return ajaxJson("M0008_1", ApiConstantUtil.M0008_1);
            }
        } else {
            log.info("使用万能短信验证码");
        }


		Map<String,Object> map = new HashMap<String,Object>();
		map.put("phone", phone);
		map.put("isEnabled", true);
		List<User> userList = userService.getUserList(map);
		if (userList==null ||userList.size()<1) {
			return ajaxJson("M0008_5", ApiConstantUtil.M0008_5);
		}
		
		User user = userList.get(0);
		if (user == null) {
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}
		if (user.getIsLock()) {
			return ajaxJson("M0003", ApiConstantUtil.M0003);
		}
		
		user.setPassword(PWDUtil.encode(newPassword,user.getRandomNum()));
		
		userService.updatePassowrd(user);
		
		BaseBean bean = new BaseBean();
		bean.setRcd("R0001");
		bean.setRmg("设置成功");

//		UserSafePwdBean userSafePwdBean = new UserSafePwdBean(user);

		return ajax(JsonUtil.toJson(bean));

	}
	
	/**
	 * 重置安全密码
	 */
	@Action(value = "/api/lostSafePwd")
	public String lostSafePwd() {
		
		if (StringUtils.isEmpty(codeReq)) {
			return ajaxJson("M0008_0", ApiConstantUtil.M0008_0);
		}
		System.out.println("-------111="+getLoginUser().getPhone());
		if(getSession(getLoginUser().getPhone())==null) {
			return ajaxJson("M0008_12", ApiConstantUtil.M0008_12);
		}
		System.out.println("-------222="+getLoginUser().getPhone());
		String aa = (String)getSession(getLoginUser().getPhone());
		if (StringUtils.isEmpty(aa)) {
			return ajaxJson("M0008_12", ApiConstantUtil.M0008_12);
		}

        if (!ConfigUtil.getConfigUtil().isTestSmsCode(codeReg)) {
            if(!aa.equals(codeReq)) {
                return ajaxJson("M0008_1", ApiConstantUtil.M0008_1);
            }
        } else {
            log.info("使用万能短信验证码");
        }

		
		User user = userService.getById(getLoginUser().getId(), new User());
		if (user == null) {
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}

		if (StringUtils.isEmpty(newPassword)) {
			return ajaxJson("M0008_1", "请输入新的交易密码");
		}
		if(StringUtils.isEmpty(cardId)){
			return ajaxJson("M0008_1", "请输入身份证后8位");
		}
		if(user.getCardId().length()<8){
			return ajaxJson("M0008_1", "原始身份证号码有误");
		}
		if(!cardId.equals(user.getCardId().substring(user.getCardId().length()-8, user.getCardId().length()))){
			return ajaxJson("M0008_1", "身份证后8位输入错误");
		}

		user.setPayPassword(PWDUtil.encode(newPassword,user.getRandomNum()));
		userService.updatePassowrd(user);
		
		BaseBean bean = new BaseBean();
		bean.setRcd("R0001");
		bean.setRmg("设置成功");

//		UserSafePwdBean userSafePwdBean = new UserSafePwdBean(user);

		return ajax(JsonUtil.toJson(bean));

	}
	
	
	/**
	 * 变更手机号码申请
	 */
	@Action(value = "/api/upPhone")
	public String upPhone() {
		
		if (StringUtils.isEmpty(codeReq)) {
			return ajaxJson("M0008_0", ApiConstantUtil.M0008_0);
		}
		if (StringUtils.isEmpty(oldPassword)) {
			return ajaxJson("S0002", ApiConstantUtil.S0002);
		}
		if(getSession(User.USER_PHONE_NUM)==null) {
			return ajaxJson("M0008_12", ApiConstantUtil.M0008_12);
		}
		String aa = (String)getSession(User.USER_PHONE_NUM);
		if (StringUtils.isEmpty(aa)) {
			return ajaxJson("M0008_12", ApiConstantUtil.M0008_12);
		}

        if (!ConfigUtil.getConfigUtil().isTestSmsCode(codeReg)) {
            if(!aa.equals(codeReq)) {
                return ajaxJson("M0008_1", ApiConstantUtil.M0008_1);
            }
        } else {
            log.info("使用万能短信验证码");
        }
		
		User user = userService.getById(getLoginUser().getId(), new User());
		if (user == null) {
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}
		if (StringUtils.isEmpty(user.getPayPassword())) {
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}
			
		if(!user.getPayPassword().equals(PWDUtil.encode(oldPassword,user.getRandomNum()))) {
			return ajaxJson("S0002", ApiConstantUtil.S0002);
		}
		
		setSession(user.getPhone()+user.getId(),1);
		
		BaseBean bean = new BaseBean();
		bean.setRcd("R0001");
		bean.setRmg("申请成功");

		//UserSafePwdBean userSafePwdBean = new UserSafePwdBean(user);

		return ajax(JsonUtil.toJson(bean));

	}
	
	/**
	 * 变更手机号码提交
	 */
	@Action(value = "/api/upPhoneb")
	public String upPhone2() {
		
		if (StringUtils.isEmpty(codeReq)) {
			return ajaxJson("M0008_0", ApiConstantUtil.M0008_0);
		}
		if (StringUtils.isEmpty(phone)) {
			return ajaxJson("M0008_13", ApiConstantUtil.M0008_13);
		}
		
		if(getSession(User.USER_PHONE_NUM)==null) {
			return ajaxJson("M0008_12", ApiConstantUtil.M0008_12);
		}
		
		if(getSession(phone)==null) {
			return ajaxJson("M0008_1",ApiConstantUtil.M0008_1);
		}
        if (!ConfigUtil.getConfigUtil().isTestSmsCode(codeReg)) {
            if(!codeReq.equals(getSession(phone))){
                return ajaxJson("M0008_1",ApiConstantUtil.M0008_1);
            }
        } else {
            log.info("使用万能短信验证码");
        }
		
		User user = (User) getSession(User.USER_ID_SESSION_NAME);
		if (user == null || user.getId() == null) {
			return ajaxJson("E0001",ApiConstantUtil.E0001);
		}
		if (getSession(user.getPhone()+user.getId())==null) {
			return ajaxJson("E0002",ApiConstantUtil.E0002);
		}
		
		
		user.setUsername(phone);
		user.setPhone(phone);
		userService.updatePhone(user);
		
		BaseBean bean = new BaseBean();
		bean.setRcd("R0001");
		bean.setRmg("更改成功");

		//UserSafePwdBean userSafePwdBean = new UserSafePwdBean(user);

		return ajax(JsonUtil.toJson(bean));

	}
	
	
	@Resource
	BorrowTenderService borrowTenderService;
	//判定用户投过表没有
	@Action(value = "/api/userCheckTenderH")
	public String userCheckTenderH() {
	
				//reloadUser();
				User u =getLoginUser(); 
				Map<String, Object> tMap = new HashMap<String, Object>();
				tMap.put("userId", u.getId());
				tMap.put("backStatus", 0);
				tMap.put("noBorrowType", "17");
				Map<String, Object> tMap1 = new HashMap<String, Object>(); //
				List<BorrowTender> btList = borrowTenderService.getTenderDetailByUserid(tMap);
				
					if(u.getId()>=2777&&u.getId()<=2788){
						tMap1.put("tenderStatus", false);
						tMap1.put("msg", "您是新手");
						
					}else if(btList.size() > 0){
						tMap1.put("tenderStatus", true);
						tMap1.put("msg", "新手标仅允许新手投资");
					}else{
						tMap1.put("tenderStatus", false);
						tMap1.put("msg", "您是新手");
					}
				return ajax(JsonUtil.toJson(tMap1));
	}	
	
	//用户近日收益
	@Action(value = "/api/userNowIncome")
	public String userNowIncome() {
	
				//reloadUser();
				User u =getLoginUser(); 
				Map<String, Object> tMap = new HashMap<String, Object>();
				tMap.put("userId", u.getId());
				DecimalFormat    df   = new DecimalFormat("######0.00");   
				Map<String, Object> tMap1 = new HashMap<String, Object>(); //
				tMap1.put("userNowIncome", df.format(borrowTenderService.getUserNowIncome(tMap)));
				return ajax(JsonUtil.toJson(tMap1));
	}	
	
	
	
	
	
	

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getCodeReq() {
		return codeReq;
	}

	public void setCodeReq(String codeReq) {
		this.codeReq = codeReq;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCodeReg() {
		return codeReg;
	}

	public void setCodeReg(String codeReg) {
		this.codeReg = codeReg;
	}
	
	

}
