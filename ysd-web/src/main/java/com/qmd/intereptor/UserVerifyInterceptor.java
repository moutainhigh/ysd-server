package com.qmd.intereptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.ognl.OgnlValueStack;
import com.qmd.mode.user.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 拦截器 - 判断会员是否登录
 */
@Service("userVerifyInterceptor")
public class UserVerifyInterceptor extends MethodFilterInterceptor {
	
	//Memcached
	private static final long serialVersionUID = -7132217629880765139L;

//	@Resource
//	MemCachedClient memcachedClient;
//	import com.danga.MemCached.MemCachedClient;
	@Override
	public String doIntercept(ActionInvocation actionInvocation) throws Exception {
		ActionContext actionContext = actionInvocation.getInvocationContext();  
		HttpServletRequest request= (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		Cookie[] cookies = request.getCookies();
		
		if (cookies!=null) {  
            for (Cookie cookie : cookies) { 
//            	if(ConstantUtil.USER_ID_COOKIE_NAME.equals(cookie.getName())){
            	if(User.USER_USERNAME_COOKIE_NAME.equals(cookie.getName())){
//            		String uuid = cookie.getValue();
//            		Map<String,User> userMap = new HashMap<String,User>();
//            		userMap=(HashMap)this.getMemcachedClient().get(uuid);
//            		User user=userMap.get(ConstantUtil.USER_NAME);
            		User user = (User) actionInvocation.getInvocationContext().getSession().get(User.USER_ID_SESSION_NAME);
            		if(user==null){
//            			String loginRedirectUrl = request.getServletPath();
//            			String queryString = request.getQueryString();
//            			if (StringUtils.isNotEmpty(queryString)) {
//            				loginRedirectUrl += "?" + queryString;
//            			}
//            			System.out.println(loginRedirectUrl);
//            			OgnlValueStack ognlValueStack = (OgnlValueStack)request.getAttribute("struts.valueStack");
//            			ognlValueStack.set("loginRedirectUrl", loginRedirectUrl);
            			redirectUrl(request);
            			return "login";
            		}else{
            			return actionInvocation.invoke();
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
	
	
	
//	public MemCachedClient getMemcachedClient() {
//		return memcachedClient;
//	}
//
//	public void setMemcachedClient(MemCachedClient memcachedClient) {
//		this.memcachedClient = memcachedClient;
//	}

}