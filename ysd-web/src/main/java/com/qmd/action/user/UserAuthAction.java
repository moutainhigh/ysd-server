package com.qmd.action.user;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.mode.user.User;
import com.qmd.service.user.UserService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

/**
 * 用户认证
 * @author Administrator
 *
 */
@Service("userAuthAction")
@InterceptorRefs({
	@InterceptorRef(value = "userVerifyInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
public class UserAuthAction extends BaseAction {

	private static final long serialVersionUID = -1203708706065607138L;
	
	private User user;
	@Resource
	UserService userService;
	
	
	/**
	 * 链接跳转到实名认证页面
	 * @return
	 */
	@Action(value="/userCenter/realname",
			results={@Result(name="personal", location="/content/user/realname.ftl", type="freemarker"),
					 @Result(name="business", location="/content/user/business.ftl", type="freemarker"),
					 @Result(name = "input", location = "/content/user/real_identify.ftl", type = "freemarker"),
					 @Result(name = "rengongshenghe", location = "/content/user/real_rengong.ftl", type = "freemarker"),
					 @Result(name="msg", location="/content/message_page.ftl", type="freemarker")})
	@InputConfig(resultName = "error_ftl")
	public String userRealName(){
		reloadUser();
		User loginuser = getLoginUser();
		if (loginuser.getPayPassword()==null||"".equals(loginuser.getPayPassword())) {
			msg = "请先设置交易密码";
			msgUrl = "/userCenter/toPayPassword.do";
			return "msg";
		}
		

		if (loginuser.getRealStatus()==0 && ( loginuser.getSceneStatus() == null || loginuser.getSceneStatus().compareTo(0) ==0 || loginuser.getSceneStatus().compareTo(3) < 0)) {
			return INPUT;
		}
		
		if(loginuser.getSceneStatus().compareTo(3)>=0 && loginuser.getSceneStatus().compareTo(11) < 0 ){
			return "rengongshenghe";
		}
		
		if(loginuser.getMemberType()==null || loginuser.getMemberType()==0){//个人
			return "personal";
		}else{//企业
			return "business";
		}
	}
	
	
	/**
	 * 修改实名认证信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value="/userCenter/identity",results={@Result(name="success", location="realname", type="redirectAction")})
	@InputConfig(resultName = "error_ftl")
	public String identity() throws IOException{
		User userLogin = getLoginUser();
		if(userLogin.getRealStatus()==1){
			addActionError("您的账户认证已通过,不要重复申请！");
			return "error_ftl";
		}
		String payp = userLogin.getPayPassword();
		if(StringUtils.isEmpty(payp)){
			addActionError("请设置交易密码！");
			return "error_ftl";
		}
		if(!userService.isPassword(userLogin.getUsername(), user.getPayPassword(), "1")){
			addActionError("交易密码输入错误!");
			return "error_ftl";
		}
		
		if(StringUtils.isNotEmpty(user.getRealName())){
			userLogin.setRealName(user.getRealName());//真实姓名
		}else{
			addActionError("请输入真实姓名");
			return "error_ftl";
		}
		userLogin.setCardType("1");//证件类别 默认为身份证
		
//		userLogin.setSex(user.getSex());
		
//		userLogin.setAreaStore("");
		
		if(StringUtils.isNotEmpty(user.getCardId())){
			userLogin.setCardId(user.getCardId());//证件号码
		}else{
			addActionError("请输入身份证号码");
			return "error_ftl";
		}
//		userLogin.setRealStatus(ConstantUtil.APPLY_STATUS_ING);//身份实名认证中
		userLogin.setModifyDate(new Date());
		int ret = this.userService.authRealName(userLogin);
		if(ret == 2){
			addActionError("此身份证已认证");
			return "error_ftl";
		}else if(ret ==1){
			addActionError("姓名和身份证不匹配");
			return "error_ftl";
		}
		setSession(User.USER_ID_SESSION_NAME, userLogin);
		addActionMessage("实名认证成功");
		redirectUrl=getContextPath()+"/userCenter/realname.do";
		
		return "success_ftl";
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
