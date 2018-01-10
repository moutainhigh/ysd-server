package com.qmd.filter;

import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;

import javax.servlet.*;
import java.io.IOException;

public class ImageDecoderFilter implements Filter {

	FilterConfig config;

	@Override
	public void destroy() {
		config = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		String param = request.getParameter("image");
		String url = null;
		
		if (param != null) {
			param = param.trim();
			
			url = CommonUtil.decodeUrl(param);
			
		}

		ServletContext context = null;
		
		if (url==null ||"".equals(url.trim())||"/".equals(url)) {
			url = "/static/img/nothing.jpg";
			context = config.getServletContext();
		} else {
			context = config.getServletContext().getContext(ConstantUtil.WEB_IMG_CONTEXT_PATH);
		}

//		RequestDispatcher ysd = context.getRequestDispatcher(ConstantUtil.WEB_IMG+ConstantUtil.WEB_IMG_CONTEXT_PATH+url);
		RequestDispatcher rd = request.getRequestDispatcher(ConstantUtil.WEB_IMG+ConstantUtil.WEB_IMG_CONTEXT_PATH+url);
		rd.forward(request, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

		config = arg0;

	}

}