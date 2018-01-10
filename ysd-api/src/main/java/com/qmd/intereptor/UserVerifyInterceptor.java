package com.qmd.intereptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.ognl.OgnlValueStack;
import com.qmd.mode.user.User;
import com.qmd.service.user.UserService;
import com.qmd.util.redis.CacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 拦截器 - 判断会员是否登录
 */
@Service("userVerifyInterceptor")
public class UserVerifyInterceptor extends MethodFilterInterceptor {
	
	private static final long serialVersionUID = -7132217629880765139L;

	@Resource
	UserService userService;
	
	@Override
	public String doIntercept(ActionInvocation actionInvocation) throws Exception {
		ActionContext actionContext = actionInvocation.getInvocationContext();  
		HttpServletRequest request= (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		Cookie[] cookies = request.getCookies();
		
		if (cookies!=null) {  
            for (Cookie cookie : cookies) { 
            	if(User.USER_TOKEN_COOKIE_NAME.equals(cookie.getName())){
            		String token = cookie.getValue();
            		System.out.println("cookie token==="+token);
            		if(StringUtils.isNotEmpty(token)){
            			
            			User user = null;
            			try {
            				user = (User) CacheUtil.getObjValueWithOutCatch("user:" + token);
            			} catch (Exception e) {
            				e.printStackTrace();
            				user = this.userService.getByToken(token);
            			}

            			if (user != null && user.getId() != null) {
            				return actionInvocation.invoke();
            			}else{
            				redirectUrl(request);
	            			return "login";
            			}
            		}
            	}
            }
		}
		redirectUrl(request);
		return "login";
		
		
	}

	public void redirectUrl(HttpServletRequest request){
		String loginRedirectUrl = request.getServletPath();
		String queryString = request.getQueryString();
		if (StringUtils.isNotEmpty(queryString)) {
			loginRedirectUrl += "?" + queryString;
		}
		System.out.println(loginRedirectUrl);
		OgnlValueStack ognlValueStack = (OgnlValueStack)request.getAttribute("struts.valueStack");
		ognlValueStack.set("loginRedirectUrl", loginRedirectUrl);
	}
	

}