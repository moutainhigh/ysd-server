package com.qmd.util;

import com.qmd.service.html.HtmlService;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;

public class QmdContextListener implements ServletContextListener {
	Logger log = Logger.getLogger(QmdContextListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		Properties p = new Properties();
		try{
			p.load(QmdContextListener.class.getResourceAsStream("/qmd.properties"));
		}catch(Exception e){
			e.printStackTrace();
		}
		sce.getServletContext().setAttribute("qmd.baseUrl", p.get("qmd.baseUrl"));
		sce.getServletContext().setAttribute("qmd.static.baseUrl", p.get("qmd.static.baseUrl"));
		sce.getServletContext().setAttribute("qmd.content.baseUrl", p.get("qmd.content.baseUrl"));
		sce.getServletContext().setAttribute("qmd.img.baseUrl", p.get("qmd.img.baseUrl"));
		sce.getServletContext().setAttribute("qmd.site.name", p.get("qmd.site.name"));
		
		
	    ServletContext servletContext = sce.getServletContext();  
	    WebApplicationContext wac = null;   
	    wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);  
	    HtmlService htmlService = (HtmlService)wac.getBean("htmlService");
	    htmlService.replaceSetting(sce);//加载数据
	    
//	    htmlService.buildHeaderHtml();
//		htmlService.buildBottomHtml();
		
		
		Properties mail = new Properties();
		try{
			mail.load(QmdContextListener.class.getResourceAsStream("/mail.properties"));
		}catch(Exception e){
			e.printStackTrace();
		}
		sce.getServletContext().setAttribute("mail.from", mail.get("mail.from"));
		
		log.info("系统启动成功，上下文地址：" + sce.getServletContext().getAttribute("qmd.baseUrl"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
