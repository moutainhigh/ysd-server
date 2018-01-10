package com.qmd.intereptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.qmd.bean.BaseBean;
import com.qmd.mode.user.User;
import com.qmd.service.user.UserService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.JsonUtil;
import com.qmd.util.redis.CacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器 - 判断会员是否登录
 */
@Service("apiUserInterceptor")
public class ApiUserInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = -4174205159318580661L;
	
	@Resource
	UserService userService;

	@Override
	public String doIntercept(ActionInvocation actionInvocation)
			throws Exception {
		ActionContext actionContext = actionInvocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		
		// 首先要从redis里对比token，如果redis出错了，则在catch代码里取数据库里保存的token并对比
		String token = request.getParameter("token");
		System.out.println("*****当前token******："+token);
		if (StringUtils.isNotEmpty(token)) {
			if(!token.equals("(null)")){
				User user = null;
				try {
					user = (User) CacheUtil.getObjValueWithOutCatch("user:" + token);
				} catch (Exception e) {
					e.printStackTrace();
					user = this.userService.getByToken(token);
				}
	
				if (user != null && user.getId() != null) {
					return actionInvocation.invoke();
				}
			}
		}

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");


		BaseBean bean = new BaseBean();
		if ("/api/userCenter.do".equals(request.getRequestURI())) {// 会员中心，特殊处理
			bean.setRcd("E0001");
			bean.setRmg(ApiConstantUtil.E0001);
		}else{
			bean.setRcd("E0001");
			bean.setRmg(ApiConstantUtil.E0001);
		}
		JsonUtil.toJson(response, bean);
		return null;
	}

}