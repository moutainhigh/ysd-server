package com.qmd.action.user;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.user.UserDockedBean;
import com.qmd.mode.user.User;
import com.qmd.service.user.UserService;
import com.qmd.util.ThreeDES;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

//import sun.misc.BASE64Decoder;


/**
 * 用户登录-注册
 */
@Service("apiUserDockedAction")
public class ApUserDockedAction extends ApiBaseAction {

	private static final long serialVersionUID = -3317398075827939752L;
	Logger log = Logger.getLogger(ApUserDockedAction.class);
	@Resource
	UserService userService;

	private User user;

	/**
	 * 用户绑定接口
	 * @return
	 */
	@Action(value="/api/bindings",results={@Result(type="json")})
	public String bindings() throws Exception {
		
		String appId = getRequest().getParameter("appId");
		String password = getRequest().getParameter("password");
		String phone = getRequest().getParameter("phone");
		String username = getRequest().getParameter("username");
		String sign = getRequest().getParameter("sign");
		
		String appKey = "123456789012345612345678";
		

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("appId", appId);
		params.put("password", password);
		params.put("phone", phone);
		params.put("username", username);
		
		String bsign = ThreeDES.getMD5Sign(params);
		UserDockedBean bean = new UserDockedBean();
		if(bsign.equals(sign)){
			username = decodeStr(username,appKey);
			if(!username.equals("-1")){
				password = decodeStr(password,appKey);
				phone    = decodeStr(phone,appKey);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username", username);

			 user  = this.userService.getUser(map);
			 if(user !=null){
				if(userService.isPassword(username, password, "0")){
					bean.setSuccess(true);
					bean.setMsg("OK");
					bean.setPhone(user.getPhone());
					bean.setUsername(user.getUsername());
					bean.setRegTime(user.getCreateDate());
					bean.setEmail(user.getEmail());
				}else{
					bean.setSuccess(false);
				}
			 }else{
				 bean.setSuccess(false);
			 }
		  }else{
			  bean.setSuccess(false);
		  }
		
		
		return ajax(bean);
	}
	
	
	public String decodeStr(String str, String appKey){
//		BASE64Decoder enc = new BASE64Decoder();
		String re = "0";
		byte[] bytes;
		try {
			/*bytes = ThreeDES.decryptMode(appKey
					.getBytes(), enc.decodeBuffer(str));*/

            bytes = ThreeDES.decryptMode(appKey
                    .getBytes(), Base64.decodeBase64(str));
		
		 re = new String(bytes, "utf-8");// 注意这一步要指定utf8
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "-1";
		}
		return re;
		
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

