package com.qmd.util;

import com.qmd.mode.util.Listing;
import com.qmd.service.html.HtmlService;
import com.qmd.service.util.ListingService;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		sce.getServletContext().setAttribute("qmd.register.sms.switch" , p.get("qmd.register.sms.switch"));
		

		sce.getServletContext().setAttribute("qmd.email.smtpFromMail", p.get("qmd.email.smtpFromMail"));
		sce.getServletContext().setAttribute("qmd.email.smtpHost", p.get("qmd.email.smtpHost"));
		sce.getServletContext().setAttribute("qmd.email.smtpPort", p.get("qmd.email.smtpPort"));
		sce.getServletContext().setAttribute("qmd.email.smtpUsername", p.get("qmd.email.smtpUsername"));
		sce.getServletContext().setAttribute("qmd.email.smtpPassword", p.get("qmd.email.smtpPassword"));

		sce.getServletContext().setAttribute("qmd.sms.spUsername", p.get("qmd.sms.spUsername"));
		sce.getServletContext().setAttribute("qmd.sms.spPassword", p.get("qmd.sms.spPassword"));
		
	    ServletContext servletContext = sce.getServletContext();  
	    WebApplicationContext wac = null;   
	    wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);  
	    HtmlService htmlService = (HtmlService)wac.getBean("htmlService");
	    htmlService.replaceSetting(sce);//加载数据
	    
//	    htmlService.buildHeaderHtml();
//		htmlService.buildBottomHtml();
	    
	    String identity_username = ImageDesUtil.mysqlDecrypt(p.getProperty("qmd.identity.username"));
	    String identity_password =  ImageDesUtil.mysqlDecrypt(p.getProperty("qmd.identity.password"));
	    
//	    com.qmd.client.identity.IdentifierServiceStub.Credential cred = new com.qmd.client.identity.IdentifierServiceStub.Credential();
//        cred.setUserName(identity_username);
//        cred.setPassword(identity_password);
//        sce.getServletContext().setAttribute("qmd.identity.cred", cred);
		
		Properties mail = new Properties();
		try{
			mail.load(QmdContextListener.class.getResourceAsStream("/mail.properties"));
		}catch(Exception e){
			e.printStackTrace();
		}
		sce.getServletContext().setAttribute("mail.from", mail.get("mail.from"));
		
		log.info("系统启动成功，上下文地址：" + sce.getServletContext().getAttribute("qmd.baseUrl"));
		
		 ListingService listingService = (ListingService)wac.getBean("listingService");
		 
		List<Listing> listingList =  listingService.getChildListingBySignList("borrow_business_type");
		Map<String,Object> map = new HashMap<String,Object>();
		for (Listing listing:listingList) {
			List<Listing> listingSubList =  listingService.getChildListingBySignList(listing.getSign());
			listing.setSubListing(listingSubList);
			map.put(listing.getKeyValue(),listing);
		}
		
		sce.getServletContext().setAttribute("borrow_business_type", map);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
