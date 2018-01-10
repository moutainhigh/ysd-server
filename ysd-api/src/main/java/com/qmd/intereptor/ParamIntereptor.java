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
	Logger log = Logger.getLogger(ParamIntereptor.class);
	String old;
	HttpServletResponse response;
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext
				.get(StrutsStatics.HTTP_REQUEST);
		//log.info("请求地址:" + request.getRequestURL() + "?" + request.getQueryString());
		long b = System.currentTimeMillis();
		log.info("【请求开始】地址:[" + request.getRequestURL()+"]SID=["+request.getSession().getId()+"]");
		Iterator<?> iter = request.getParameterMap().keySet().iterator();
		String key,value;
		String[] values;
		StringBuffer sb = new StringBuffer();
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
					//value = new String(value.getBytes("GBK"), "UTF-8");
					sb.append(key).append(":").append(value).append(",");
				}
			}
			log.info("      【请求参数】[" + sb.toString()+"]");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 String result = invocation.invoke();
		 long e = System.currentTimeMillis();
		log.info("【请求结束】地址:[" + request.getRequestURL()+"]SID=["+request.getSession().getId()+"]result=["+result+"]time="+(e-b));
	
		return result;
	}
	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}
}
