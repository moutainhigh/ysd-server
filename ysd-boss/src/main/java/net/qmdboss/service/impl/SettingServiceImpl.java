package net.qmdboss.service.impl;

import net.qmdboss.dao.SettingDao;
import net.qmdboss.entity.Setting;
import net.qmdboss.service.SettingService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

@Service("settingServiceImpl")
public class SettingServiceImpl  extends BaseServiceImpl<Setting, Integer> implements SettingService, ServletContextAware {

	private ServletContext servletContext;
	@Resource(name = "settingDaoImpl")
	private SettingDao settingDao;

	@Resource(name = "settingDaoImpl")
	public void setBaseDao(SettingDao settingDao) {
		super.setBaseDao(settingDao);
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	@Override
	public void update(Setting setting){
		
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
		servletContext.setAttribute("qmd.setting.SmtpUsername", setting.getSmtpUsername());
		servletContext.setAttribute("qmd.setting.SmtpPassword", setting.getSmtpPassword());
		servletContext.setAttribute("qmd.setting.miniMoney", setting.getMiniMoney());
		servletContext.setAttribute("qmd.setting.offLineRechargeDes", setting.getOffLineRechargeDes());
		super.update(setting);
	}
	//ServletContextEvent sce sce.getServletContext()
	public void replaceSetting(){
		Setting setting = settingDao.get(1);
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
		servletContext.setAttribute("qmd.setting.SmtpUsername", setting.getSmtpUsername());
		servletContext.setAttribute("qmd.setting.SmtpPassword", setting.getSmtpPassword());
		servletContext.setAttribute("qmd.setting.miniMoney", setting.getMiniMoney());
		servletContext.setAttribute("qmd.setting.offLineRechargeDes", setting.getOffLineRechargeDes());
		
		
		
	}
}
