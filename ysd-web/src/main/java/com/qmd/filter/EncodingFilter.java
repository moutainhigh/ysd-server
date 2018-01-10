package com.qmd.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

public class EncodingFilter implements Filter {
	protected String encoding = null;
	protected FilterConfig filterConfig = null;
	protected boolean ignore = true;
	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (ignore || (request.getCharacterEncoding() == null)) {
			String encoding = selectEncoding(request);
			if (encoding != null) {
				HttpServletRequest httpServletRequest = (HttpServletRequest) request;
				HttpServletResponse httpServletResponse = (HttpServletResponse)response;


				String gbkUrl = httpServletRequest.getRequestURL().toString();
				System.out.println("gbkUrl:"+gbkUrl);

				if(gbkUrl.endsWith("Notify.do")){

					httpServletRequest.setCharacterEncoding("GBK");
					httpServletResponse.setCharacterEncoding("GBK");
					String value = httpServletRequest.getParameter("RespDesc");

					httpServletRequest.setCharacterEncoding("UTF-8");

					//System.out.println(URLDecoder.decode(value,"GBK"));
				}else{
					if (httpServletRequest.getMethod().toLowerCase().equals("post")) {
						httpServletRequest.setCharacterEncoding(encoding);
						httpServletResponse.setCharacterEncoding(encoding);

					} else {

					}
				}


			}
		}
		chain.doFilter(request, response);
	}
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
		String value = filterConfig.getInitParameter("ignore");
		if (value == null)
			this.ignore = true;
		else if (value.equalsIgnoreCase("true"))
			this.ignore = true;
		else if (value.equalsIgnoreCase("yes"))
			this.ignore = true;
		else
			this.ignore = false;
	}
	protected String selectEncoding(ServletRequest request) {
		return (this.encoding);
	}
}