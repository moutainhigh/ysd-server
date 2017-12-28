package com.qmd.intereptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.qmd.util.ConstantUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

@Service("paramIntereptor")
public class ParamIntereptor implements Interceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Logger workLog = Logger.getLogger("userWorkLog");
	
	Logger log = Logger.getLogger(ParamIntereptor.class);
	String old;
	HttpServletResponse response;
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext
				.get(StrutsStatics.HTTP_REQUEST);
		//log.info("请求地址:" + request.getRequestURL() + "?" + request.getQueryString());
		
		long bl = System.currentTimeMillis();
		workLog.debug("【开始】URL["+request.getRequestURL()+"]IP=["+request.getHeader("x-forwarded-for")+"]AG=["+request.getHeader("user-agent")+"]RF=["+request.getHeader("referer")+"]");
		
		log.info("请求地址:" + request.getRequestURL());
		log.info("参数拦截器开始");
		Iterator<?> iter = request.getParameterMap().keySet().iterator();
		String key,value;
		String[] values;
		try{
			while (iter.hasNext()) {
				key = (String) iter.next();
				values = (String[])request.getParameterMap().get(key);
				for(int i = 0; i < values.length; i ++){
					value = values[i];
					if(value.toLowerCase().contains("select")){
						log.info("非法参数，返回首页");
						response = (HttpServletResponse)actionContext.get(StrutsStatics.HTTP_RESPONSE);
						response.sendRedirect(ConstantUtil.QMD_INTERCEPT_SENDREDIRECT);
						return null;
					}
//					for(String word:ConstantUtil.ATTACKWORD) {
//						if(value.toLowerCase().contains(word)){
//							log.info("非法参数，返回首页");
//							response = (HttpServletResponse)actionContext.get(StrutsStatics.HTTP_RESPONSE);
//							response.sendRedirect(ConstantUtil.QMD_INTERCEPT_SENDREDIRECT);
//							return null;
//						}
//					}
					
					value = new String(value.getBytes("GBK"), "UTF-8");
				}
			}
			log.info("参数编码转换完毕");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		log.info("参数拦截器结束");
		
		String ret = invocation.invoke();;
		
		long el = System.currentTimeMillis();
		workLog.debug("【结束】ret=["+ret+"] ["+(el-bl)+"]");
		
		return ret;
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		log.info("参数拦截器销毁");
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		log.info("参数拦截器初始化成功");
	}
}
