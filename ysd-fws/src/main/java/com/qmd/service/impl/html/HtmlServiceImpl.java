package com.qmd.service.impl.html;

import com.qmd.common.CustomFreemarkerManager;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.util.Setting;
import com.qmd.service.html.HtmlService;
import com.qmd.util.ConstantUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Service实现类 - 生成静态
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXE7656ABEA5A60D076604DDCE20B403C9
 * ============================================================================
 */

@Service("htmlService")
public class HtmlServiceImpl implements HtmlService, ServletContextAware {

	private ServletContext servletContext;
	@Resource(name = "freemarkerManager")
	private CustomFreemarkerManager freemarkerManager;
	@Resource(name = "listingDao")
	private ListingDao listingDao;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	// 获取公用数据
	public Map<String, Object> getCommonData() {
//		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
//		ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
		
		Map<String, Object> commonData = new HashMap<String, Object>();
//		commonData.put("bundle", resourceBundleModel);
//		commonData.put("base", getContextPath());
//		commonData.put("setting", SettingUtil.getSetting());
//		commonData.put("currencyFormat", CommonUtil.getCurrencyFormat());
		
		String side_name = (String)servletContext.getAttribute("qmd.setting.name");
		
		commonData.put("name", side_name);
		commonData.put("base", ConstantUtil.WEB_SITE);
		commonData.put("imgbase", ConstantUtil.WEB_IMG);
		return commonData;
	}
	
	public void buildHtml(String templatePath, String htmlPath, Map<String, Object> data) {
		try {
			Configuration configuration = freemarkerManager.getConfiguration(servletContext);
			Template template = configuration.getTemplate(templatePath);
			File htmlFile = new File(servletContext.getRealPath(htmlPath));
			
			System.out.println("=====#####=====begin");
			System.out.println("     htmlPath:"+htmlPath);
			System.out.println("     RealPath:"+servletContext.getRealPath(htmlPath));
			System.out.println("     FilePath:"+htmlFile.getPath());
			System.out.println("     FileAbsolutePath:"+htmlFile.getAbsolutePath());
			System.out.println("=====#####=====end");
			
			File htmlDirectory = htmlFile.getParentFile();
			if (!htmlDirectory.exists()) {
				htmlDirectory.mkdirs();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
			template.process(data, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buildHeaderHtml() {
//		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.HEADER);
		Map<String, Object> data = getCommonData();
		data.put("logoPath", servletContext.getAttribute("qmd.setting.logoPath"));
		String htmlPath = ConstantUtil.HEADER_HTML_TEMPLATE;// pageTemplateConfig.getHtmlPath();
		String templatePath = ConstantUtil.HEADER_FTL_TEMPLATE;//pageTemplateConfig.getTemplatePath();
		buildHtml(templatePath, htmlPath, data);
	}
	
	public void buildBottomHtml() {
//		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.HEADER);
		Map<String, Object> data = getCommonData();
//		String copyright = "版权所有&nbsp;"+servletContext.getAttribute("qmd.setting.companyName")+"&nbsp;&nbsp;&nbsp;"
//						   +"官方QQ群号:"+servletContext.getAttribute("qmd.setting.officialQq")+"<br/>"
//						   +servletContext.getAttribute("qmd.setting.address")+"&nbsp;&nbsp;"
//						   +servletContext.getAttribute("qmd.setting.certtext");
		String copyright ="ICP备案："+servletContext.getAttribute("qmd.setting.certtext");
		data.put("copyright", copyright);
		data.put("qq_num",servletContext.getAttribute("qmd.setting.qq"));
		data.put("qqs_num",servletContext.getAttribute("qmd.setting.officialQq"));
		data.put("phone",servletContext.getAttribute("qmd.setting.phone"));
		String htmlPath = ConstantUtil.FOOTER_HTML_TEMPLATE;// pageTemplateConfig.getHtmlPath();
		String templatePath = ConstantUtil.FOOTER_FTL_TEMPLATE;//pageTemplateConfig.getTemplatePath();
		buildHtml(templatePath, htmlPath, data);
	}
	
	public void build404Html(){
		Map<String, Object> data = getCommonData();
		data.put("logoPath", servletContext.getAttribute("qmd.setting.logoPath"));
//		String copyright = "版权所有&nbsp;"+servletContext.getAttribute("qmd.setting.companyName")+"&nbsp;&nbsp;&nbsp;"
//				   +"官方QQ群号:"+servletContext.getAttribute("qmd.setting.officialQq")+"<br/>"
//				   +servletContext.getAttribute("qmd.setting.address")+"&nbsp;&nbsp;"
//				   +servletContext.getAttribute("qmd.setting.certtext");
		String copyright ="ICP备案："+servletContext.getAttribute("qmd.setting.certtext");
		data.put("copyright", copyright);
		data.put("qq_num",servletContext.getAttribute("qmd.setting.qq"));
		data.put("qqs_num",servletContext.getAttribute("qmd.setting.officialQq"));
		data.put("phone",servletContext.getAttribute("qmd.setting.phone"));
		data.put("metaKeywords",servletContext.getAttribute("qmd.setting.metaKeywords"));
		data.put("metaDescription",servletContext.getAttribute("qmd.setting.metaDescription"));
		String htmlPath = ConstantUtil.HTML_404_TEMPLATE;// pageTemplateConfig.getHtmlPath();
		String templatePath = ConstantUtil.FTL_404_TEMPLATE;//pageTemplateConfig.getTemplatePath();
		buildHtml(templatePath, htmlPath, data);
	}
	
	public void buildContractHtml(String templatePath, String htmlPath, Map<String, Object> data) {
		try {
			Configuration configuration = freemarkerManager.getConfiguration(servletContext);
			Template template = configuration.getTemplate(templatePath);
			File htmlFile = new File(htmlPath);
			
			System.out.println("=====#####=====begin");
			System.out.println("     htmlPath:"+htmlPath);
			//System.out.println("     RealPath:"+servletContext.getRealPath(htmlPath));
			System.out.println("     FilePath:"+htmlFile.getPath());
			System.out.println("     FileAbsolutePath:"+htmlFile.getAbsolutePath());
			System.out.println("=====#####=====end");
			
			File htmlDirectory = htmlFile.getParentFile();
			if (!htmlDirectory.exists()) {
				htmlDirectory.mkdirs();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
			template.process(data, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void replaceSetting(){
		Setting setting = listingDao.getSetting();
	
		servletContext.setAttribute("qmd.setting.name",ConstantUtil.AGENCY_WEB_NAME);
		servletContext.setAttribute("qmd.setting.companyName", StringUtils.isEmpty(setting.getCompanyName())?"":setting.getCompanyName());
		servletContext.setAttribute("qmd.setting.metaKeywords", StringUtils.isEmpty(setting.getMetaKeywords())?"":setting.getMetaKeywords());
		servletContext.setAttribute("qmd.setting.metaDescription", StringUtils.isEmpty(setting.getMetaDescription())?"":setting.getMetaDescription());
		servletContext.setAttribute("qmd.setting.url", StringUtils.isEmpty(setting.getUrl())?"":setting.getUrl());
		servletContext.setAttribute("qmd.setting.memberUrl", ConstantUtil.WEB_MEMBER_SITE);
		servletContext.setAttribute("qmd.setting.logoPath", StringUtils.isEmpty(setting.getLogoPath())?"":setting.getLogoPath());
		servletContext.setAttribute("qmd.setting.phone", StringUtils.isEmpty(setting.getPhone())?"":setting.getPhone());
		servletContext.setAttribute("qmd.setting.qq", StringUtils.isEmpty(setting.getQq())?"":setting.getQq());
		servletContext.setAttribute("qmd.setting.officialQq", StringUtils.isEmpty(setting.getOfficialQq())?"":setting.getOfficialQq());
		servletContext.setAttribute("qmd.setting.address", StringUtils.isEmpty(setting.getAddress())?"":setting.getAddress());
		servletContext.setAttribute("qmd.setting.email", StringUtils.isEmpty(setting.getEmail())?"":setting.getEmail());
		servletContext.setAttribute("qmd.setting.zipCode", StringUtils.isEmpty(setting.getZipCode())?"":setting.getZipCode());
		servletContext.setAttribute("qmd.setting.certtext", StringUtils.isEmpty(setting.getCerttext())?"":setting.getCerttext());
		servletContext.setAttribute("qmd.setting.watermarkImagePath", StringUtils.isEmpty(setting.getWatermarkImagePath())?"":setting.getWatermarkImagePath());
		servletContext.setAttribute("qmd.setting.watermarkPosition", StringUtils.isEmpty(setting.getWatermarkPosition())?"":setting.getWatermarkPosition());
		servletContext.setAttribute("qmd.setting.watermarkAlpha", setting.getWatermarkAlpha()==null?0:setting.getWatermarkAlpha());
		servletContext.setAttribute("qmd.setting.isLoginFailureLock", setting.getIsLoginFailureLock());
		servletContext.setAttribute("qmd.setting.loginFailureLockCount", setting.getLoginFailureLockCount());
		servletContext.setAttribute("qmd.setting.loginFailureLockTime", setting.getLoginFailureLockTime());
		servletContext.setAttribute("qmd.setting.smtpFromMail", setting.getSmtpFromMail());
		servletContext.setAttribute("qmd.setting.smtpHost", setting.getSmtpHost());
		servletContext.setAttribute("qmd.setting.smtpPort", setting.getSmtpPort());
		servletContext.setAttribute("qmd.setting.smtpUsername", setting.getSmtpUsername());
		servletContext.setAttribute("qmd.setting.smtpPassword", setting.getSmtpPassword());
		servletContext.setAttribute("qmd.setting.sensitiveKeyWord", setting.getSensitiveKeyWord());
		servletContext.setAttribute("qmd.setting.miniMoney", setting.getMiniMoney());
		servletContext.setAttribute("qmd.setting.offLineRechargeDes", setting.getOffLineRechargeDes());
		servletContext.setAttribute("qmd.setting.isXutou", setting.getIsXutou());
		

		servletContext.setAttribute("qmd.setting.spCode", setting.getSpCode());
		servletContext.setAttribute("qmd.setting.spUsername", setting.getSpUsername());
		servletContext.setAttribute("qmd.setting.spPassword", setting.getSpPassword());
	}
	
	public void replaceSetting(ServletContextEvent sce){
		Setting setting = listingDao.getSetting();
	
		sce.getServletContext().setAttribute("qmd.setting.name", setting.getName()+"服务商业务管理平台");
		sce.getServletContext().setAttribute("qmd.setting.companyName", setting.getCompanyName());
		sce.getServletContext().setAttribute("qmd.setting.metaKeywords", setting.getMetaKeywords());
		sce.getServletContext().setAttribute("qmd.setting.metaDescription", setting.getMetaDescription());
		sce.getServletContext().setAttribute("qmd.setting.url", setting.getUrl());
		sce.getServletContext().setAttribute("qmd.setting.memberUrl", ConstantUtil.WEB_MEMBER_SITE);
		sce.getServletContext().setAttribute("qmd.setting.logoPath", setting.getLogoPath());
		sce.getServletContext().setAttribute("qmd.setting.phone", setting.getPhone());
		sce.getServletContext().setAttribute("qmd.setting.qq", setting.getQq());
		sce.getServletContext().setAttribute("qmd.setting.officialQq", StringUtils.isEmpty(setting.getOfficialQq())?"":setting.getOfficialQq());
		sce.getServletContext().setAttribute("qmd.setting.address", setting.getAddress());
		sce.getServletContext().setAttribute("qmd.setting.email", setting.getEmail());
		sce.getServletContext().setAttribute("qmd.setting.zipCode", setting.getZipCode());
		sce.getServletContext().setAttribute("qmd.setting.certtext", setting.getCerttext());
		sce.getServletContext().setAttribute("qmd.setting.watermarkImagePath", setting.getWatermarkImagePath());
		sce.getServletContext().setAttribute("qmd.setting.watermarkPosition", setting.getWatermarkPosition());
		sce.getServletContext().setAttribute("qmd.setting.watermarkAlpha", setting.getWatermarkAlpha());
		sce.getServletContext().setAttribute("qmd.setting.isLoginFailureLock", setting.getIsLoginFailureLock());
		sce.getServletContext().setAttribute("qmd.setting.loginFailureLockCount", setting.getLoginFailureLockCount());
		sce.getServletContext().setAttribute("qmd.setting.loginFailureLockTime", setting.getLoginFailureLockTime());
		sce.getServletContext().setAttribute("qmd.setting.smtpFromMail", setting.getSmtpFromMail());
		sce.getServletContext().setAttribute("qmd.setting.smtpHost", setting.getSmtpHost());
		sce.getServletContext().setAttribute("qmd.setting.smtpPort", setting.getSmtpPort());
		sce.getServletContext().setAttribute("qmd.setting.smtpUsername", setting.getSmtpUsername());
		sce.getServletContext().setAttribute("qmd.setting.smtpPassword", setting.getSmtpPassword());
		sce.getServletContext().setAttribute("qmd.setting.sensitiveKeyWord", setting.getSensitiveKeyWord());
		sce.getServletContext().setAttribute("qmd.setting.miniMoney", setting.getMiniMoney());
		sce.getServletContext().setAttribute("qmd.setting.offLineRechargeDes", setting.getOffLineRechargeDes());

		sce.getServletContext().setAttribute("qmd.setting.spCode", setting.getSpCode());
		sce.getServletContext().setAttribute("qmd.setting.spUsername", setting.getSpUsername());
		sce.getServletContext().setAttribute("qmd.setting.spPassword", setting.getSpPassword());
	}
	
	
	/**
	 * 获取虚拟路径
	 * 
	 * @return 虚拟路径
	 */
	private String getContextPath() {
		if (servletContext.getMajorVersion() < 2 || (servletContext.getMajorVersion() == 2 && servletContext.getMinorVersion() < 5)) {
//			return SettingUtil.getSetting().getContextPath();
			return "";
		} else {
			return servletContext.getContextPath();
		}
	}

}