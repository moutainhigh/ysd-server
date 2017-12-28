package com.qmd.service.impl.html;

import com.qmd.common.CustomFreemarkerManager;
import com.qmd.dao.article.ArticleDao;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.article.Article;
import com.qmd.mode.util.Setting;
import com.qmd.service.article.ArticleService;
import com.qmd.service.html.HtmlService;
import com.qmd.util.CommonMess;
import com.qmd.util.ConstantUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.io.*;
import java.util.HashMap;
import java.util.List;
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
	@Resource(name = "articleDao")
	private ArticleDao articleDao;
	@Resource(name = "articleService")
	private ArticleService articleService;
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
		
		data.put("site_name",servletContext.getAttribute("qmd.setting.name"));
		data.put("qq_num",servletContext.getAttribute("qmd.setting.qq"));
		data.put("qqs_num",servletContext.getAttribute("qmd.setting.officialQq"));
		data.put("phone",servletContext.getAttribute("qmd.setting.phone"));
		
		buildHtml(templatePath, htmlPath, data);
	}
	
	public void buildBottomHtml() {
//		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.HEADER);
		Map<String, Object> data = getCommonData();
//		String copyright = "版权所有&nbsp;"+servletContext.getAttribute("qmd.setting.companyName")+"&nbsp;&nbsp;&nbsp;"
//						   +"官方QQ群号:"+servletContext.getAttribute("qmd.setting.officialQq")+"<br/>"
//						   +servletContext.getAttribute("qmd.setting.address")+"&nbsp;&nbsp;"
//						   +servletContext.getAttribute("qmd.setting.certtext");
		
		data.put("copyright", servletContext.getAttribute("qmd.setting.certtext"));
		Map<String ,String> map = new HashMap<String,String>();
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
//		data.put("copyright", copyright);
		data.put("copyright", servletContext.getAttribute("qmd.setting.certtext"));
		data.put("qq_num",servletContext.getAttribute("qmd.setting.qq"));
		data.put("qqs_num",servletContext.getAttribute("qmd.setting.officialQq"));
		data.put("phone",servletContext.getAttribute("qmd.setting.phone"));
		data.put("metaKeywords",servletContext.getAttribute("qmd.setting.metaKeywords"));
		data.put("metaDescription",servletContext.getAttribute("qmd.setting.metaDescription"));
		String htmlPath = ConstantUtil.HTML_404_TEMPLATE;// pageTemplateConfig.getHtmlPath();
		String templatePath = ConstantUtil.FTL_404_TEMPLATE;//pageTemplateConfig.getTemplatePath();
		buildHtml(templatePath, htmlPath, data);
	}
	
	@Transactional(readOnly = true)
	public void buildArticleContentHtml(Integer id) {
		Article article = articleDao.get(id);
		buildArticleContentHtml(article);
	}
	
	public void buildArticleContentHtml(Article article) {
		if (article == null) {
			return;
		}
//		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.ARTICLE_CONTENT);
//		Map<String, Object> data = getCommonData();
//		data.put("article", article);
//		data.put("pathList", articleCategoryService.getArticleCategoryPathList(article.getArticleCategory()));
//		String htmlPath = article.getHtmlPath();
//		String prefix = StringUtils.substringBeforeLast(htmlPath, ".");
//		String extension = StringUtils.substringAfterLast(htmlPath, ".");
//		List<String> pageContentList = article.getPageContentList();
//		for (int i = 0; i < pageContentList.size(); i++) {
//			String currentHtmlPath = null;
//			if (i == 0) {
//				currentHtmlPath = htmlPath;
//			} else {
//				currentHtmlPath = prefix + "_" + (i + 1) + "." + extension;
//			}
//			if (article.getIsPublication()) {
//				data.put("content", pageContentList.get(i));
//				data.put("pageNumber", i + 1);
//				data.put("pageCount", pageContentList.size());
//				String templatePath = pageTemplateConfig.getTemplatePath();
//				buildHtml(templatePath, currentHtmlPath, data);
//			} else {
//				File htmlFile = new File(servletContext.getRealPath(currentHtmlPath));
//				if (htmlFile.exists()) {
//					htmlFile.delete();
//				}
//			}
//		}
	}
	
	@Transactional(readOnly = true)
	public void buildArticleContentHtml() {
		long articleTotalCount = articleDao.getTotalCount();
		for (int i = 0; i < articleTotalCount; i += 30) {
			List<Article> articleList = articleDao.getAllList();//.getArticleList(null, null, null, i, 30);
			for (Article article : articleList) {
				buildArticleContentHtml(article);
			}
		}
	}
	
	
	public void replaceSetting(){
		Setting setting = listingDao.getSetting();
	
		servletContext.setAttribute("qmd.setting.name",StringUtils.isEmpty(setting.getName())?"":setting.getName());
		servletContext.setAttribute("qmd.setting.companyName", StringUtils.isEmpty(setting.getCompanyName())?"":setting.getCompanyName());
		servletContext.setAttribute("qmd.setting.metaKeywords", StringUtils.isEmpty(setting.getMetaKeywords())?"":setting.getMetaKeywords());
		servletContext.setAttribute("qmd.setting.metaDescription", StringUtils.isEmpty(setting.getMetaDescription())?"":setting.getMetaDescription());
		servletContext.setAttribute("qmd.setting.url", StringUtils.isEmpty(setting.getUrl())?"":setting.getUrl());
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
	}
	
	public void replaceSetting(ServletContextEvent sce){
		Setting setting = listingDao.getSetting();
	
		sce.getServletContext().setAttribute("qmd.setting.name", setting.getName());
		sce.getServletContext().setAttribute("qmd.setting.companyName", setting.getCompanyName());
		sce.getServletContext().setAttribute("qmd.setting.metaKeywords", setting.getMetaKeywords());
		sce.getServletContext().setAttribute("qmd.setting.metaDescription", setting.getMetaDescription());
		sce.getServletContext().setAttribute("qmd.setting.url", setting.getUrl());
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
		servletContext.setAttribute("qmd.setting.miniMoney", setting.getMiniMoney());
		servletContext.setAttribute("qmd.setting.offLineRechargeDes", setting.getOffLineRechargeDes());
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

	@Override
	public CommonMess buildCommonMess() {
		// TODO Auto-generated method stub
		Setting setting = listingDao.getSetting();
		CommonMess coms = new CommonMess();
		coms.setLogoPath(StringUtils.isEmpty(setting.getLogoPath())?"":setting.getLogoPath());
		String companyName = StringUtils.isEmpty(setting.getCompanyName())?"":setting.getCompanyName();
		String address = StringUtils.isEmpty(setting.getAddress())?"":setting.getAddress();
		String certtext= StringUtils.isEmpty(setting.getCerttext())?"":setting.getCerttext();
		String copyright = StringUtils.isEmpty(setting.getCopyright())?"":setting.getCopyright();
		String xkz = StringUtils.isEmpty(setting.getXkz())?"":setting.getXkz();
		String ywPhone = StringUtils.isEmpty(setting.getYwPhone())?"":setting.getYwPhone();
		
		coms.setAddress(address);
		coms.setCompanyName(companyName);
		coms.setYwPhone(ywPhone);
		coms.setXkz(xkz);
		coms.setCerttext(certtext);
		coms.setCopyright(copyright);
		coms.setPhone(StringUtils.isEmpty(setting.getPhone())?"":setting.getPhone());
		coms.setQq_num(StringUtils.isEmpty(setting.getQq())?"":setting.getQq());
		coms.setQqs_num(StringUtils.isEmpty(setting.getOfficialQq())?"":setting.getOfficialQq());
		coms.setSite_name(StringUtils.isEmpty(setting.getName())?"":setting.getName());
		coms.setMetaDescription( StringUtils.isEmpty(setting.getMetaDescription())?"":setting.getMetaDescription());
		coms.setMetaKeywords(StringUtils.isEmpty(setting.getMetaKeywords())?"":setting.getMetaKeywords());
		coms.setEmail(StringUtils.isEmpty(setting.getEmail())?"":setting.getEmail());
		return coms;
	}

}