package net.qmdboss.service.impl;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.qmdboss.bean.MailTemplateConfig;
import net.qmdboss.entity.*;
import net.qmdboss.service.ListingService;
import net.qmdboss.service.MailService;
import net.qmdboss.util.CommonUtil;
import net.qmdboss.util.SettingUtil;
import net.qmdboss.util.TemplateConfigUtil;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Service实现类 - 邮件服务
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX002914E795CA825D58C64186BDBFF7A2
 * ============================================================================
 */

@Service("mailServiceImpl")
public class MailServiceImpl implements MailService, ServletContextAware {

	private ServletContext servletContext;
	@Resource(name = "freemarkerManager")
	private FreemarkerManager freemarkerManager;
	@Resource(name = "javaMailSender")
	private JavaMailSender javaMailSender;
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	@Resource(name = "listingServiceImpl")
	private ListingService listingService ;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	// 增加邮件发送任务
	public void addSendMailTask(final MimeMessage mimeMessage) {
		try {
			taskExecutor.execute(
				new Runnable() {
					public void run() {
						javaMailSender.send(mimeMessage);
					}
				}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMail(String subject, String templatePath, Map<String, Object> data, String toMail) {
		try {
			
			
//			Setting setting = SettingUtil.getSetting();
			
			String mail_from = (String)servletContext.getAttribute("qmd.setting.smtpFromMail");
			//String companyName = (String)servletContext.getAttribute("qmd.setting.companyName");
			
			String name = (String)servletContext.getAttribute("qmd.setting.name");
						
			String host = (String)servletContext.getAttribute("qmd.setting.smtpHost");
			Integer port = (Integer)servletContext.getAttribute("qmd.setting.smtpPort");
			String username = (String)servletContext.getAttribute("qmd.setting.SmtpUsername");
			String password = (String)servletContext.getAttribute("qmd.setting.SmtpPassword");
			
			JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl)javaMailSender;
            setMailSSL(javaMailSenderImpl, port);

			javaMailSenderImpl.setHost(host);
			javaMailSenderImpl.setPort(port);
			javaMailSenderImpl.setUsername(username);
			javaMailSenderImpl.setPassword(password);
			MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
			Configuration configuration = freemarkerManager.getConfiguration(servletContext);
			Template template = configuration.getTemplate(templatePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			mimeMessageHelper.setFrom(MimeUtility.encodeWord(name) + " <" + mail_from + ">");
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text, true);
			addSendMailTask(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 获取公共数据
	public Map<String, Object> getCommonData() {
		Map<String, Object> commonData = new HashMap<String, Object>();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
		ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
		commonData.put("bundle", resourceBundleModel);
		commonData.put("base", getContextPath());
		commonData.put("setting", SettingUtil.getSetting());
		return commonData;
	}
	
	public void sendSmtpTestMail(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail) {
		//Setting setting = SettingUtil.getSetting();
		Map<String, Object> data = getCommonData();
		MailTemplateConfig mailTemplateConfig = TemplateConfigUtil.getMailTemplateConfig(MailTemplateConfig.SMTP_TEST);
		String subject = mailTemplateConfig.getSubject();
		String templatePath = mailTemplateConfig.getTemplatePath();
		
		String name = (String)servletContext.getAttribute("qmd.setting.name");
		
		try {
			JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl)javaMailSender;

            setMailSSL(javaMailSenderImpl, smtpPort);


            javaMailSenderImpl.setHost(smtpHost);
			javaMailSenderImpl.setPort(smtpPort);
			javaMailSenderImpl.setUsername(smtpUsername);
			javaMailSenderImpl.setPassword(smtpPassword);
			MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();

			Configuration configuration = freemarkerManager.getConfiguration(servletContext);
			Template template = configuration.getTemplate(templatePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			mimeMessageHelper.setFrom(MimeUtility.encodeWord(name) + " <" + smtpFromMail + ">");
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text, true);
			javaMailSender.send(mimeMessage);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

    private void setMailSSL(JavaMailSenderImpl javaMailSenderImpl, int smtpPort) {
        Properties javaMailProperties = javaMailSenderImpl.getJavaMailProperties();
        javaMailProperties.put("mail.debug", "true");
        if (smtpPort == 465) {
            // ssl
            javaMailProperties.put("mail.smtp.ssl.enable", "true");
            javaMailProperties.put("mail.smtp.starttls.enable", "true");
            javaMailProperties.put("mail.transport.protocol", "smtp");
            javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            javaMailProperties.put("mail.smtp.socketFactory.port", "465");
        }
    }


    /**
	 * 认证审核
	 */
	public void sendApprove(User user,Integer verifyType,Integer verifyValue){
		String temp="";
		if(verifyType==1){
			temp="个人实名";
		}else if(verifyType==2){
			temp="借款人";
		}else if(verifyType==3){
			temp="VIP";
		}else if(verifyType==4){
			temp="企业资料";
		}else if(verifyType==5){
			temp="个人借款者资质";
		}else if(verifyType==6){
			temp="企业借款者资质";
		}else if(verifyType==7){
			temp="手机";
		}
			
		String name = (String)servletContext.getAttribute("qmd.setting.name");
		String phone = (String)servletContext.getAttribute("qmd.setting.phone");
		String qq = (String)servletContext.getAttribute("qmd.setting.officialQq");
		
		Map<String, Object> data = getCommonData();
		data.put("user", user);
		data.put("t", verifyType);
		data.put("v", verifyValue);
		data.put("site_name", name);
		data.put("site_phone", phone);
		data.put("site_qq", qq);
		MailTemplateConfig mailTemplateConfig = TemplateConfigUtil.getMailTemplateConfig(MailTemplateConfig.APPROVE_NOTIFY);
		String subject = temp+mailTemplateConfig.getSubject();
		String templatePath = mailTemplateConfig.getTemplatePath();
		sendMail(subject, templatePath, data, user.getEmail());
	}
	
	
	/**
	 * 满标审核
	 */
	public void sendFullBorrow(BorrowDetail borrowDetail,Borrow borrow){
		Map<String, Object> data = getCommonData();
		data.put("borrowDetail", borrowDetail);
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat simple1 = new SimpleDateFormat("yyyy-MM-dd");
		String time = simple.format(new Date());
		String agreementId=time+borrow.getId();
		data.put("agreementId", agreementId);
		data.put("borrow", borrow);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(borrow.getSuccessTime());
		calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+Integer.parseInt(borrow.getTimeLimit()));
		String lastRdate=simple1.format(calendar.getTime());
		if("4".equals(borrow.getType()) || borrow.getType()==null) {
			Date lasTime=CommonUtil.getMonthAfter(new Date(),Integer.parseInt(borrow.getTimeLimit()));
			lastRdate=simple1.format(lasTime);
		}
		data.put("lastRdate", lastRdate);
		data.put("date", new Date());
		String websiteurl = (String)servletContext.getAttribute("qmd.setting.url"); 
		String logoPath = (String)servletContext.getAttribute("qmd.setting.logoPath");
		String site_name = (String)servletContext.getAttribute("qmd.setting.name");
		String phone = (String)servletContext.getAttribute("qmd.setting.phone");
		String qq = (String)servletContext.getAttribute("qmd.setting.officialQq");
		
		data.put("websiteurl", websiteurl);
		data.put("logoPath", logoPath);
		data.put("site_name", site_name);
		data.put("site_phone", phone);
		data.put("site_qq", qq);
		
		MailTemplateConfig mailTemplateConfig;
		mailTemplateConfig = TemplateConfigUtil.getMailTemplateConfig(MailTemplateConfig.FULLBORROW_NOTIFY);
		String subject = mailTemplateConfig.getSubject();
		String templatePath = mailTemplateConfig.getTemplatePath();
		sendMail(subject, templatePath, data, borrowDetail.getUser().getEmail());
	}
	
	/**
	 * 提现审核
	 */
	public void sendWithdraw(AccountCash accountCash){
		Map<String, Object> data = getCommonData();
		String sign = "account_bank";
		String bankName = listingService.findChildListingByKeyValue(sign, accountCash.getBank());
		String site_name = (String)servletContext.getAttribute("qmd.setting.name");
		String phone = (String)servletContext.getAttribute("qmd.setting.phone");
		String qq = (String)servletContext.getAttribute("qmd.setting.officialQq");
		
		data.put("accountCash", accountCash);
		data.put("bankName", bankName+"-"+accountCash.getBranch()!=null?accountCash.getBranch():"");
		data.put("date", new Date());
		data.put("site_name", site_name);
		data.put("site_phone", phone);
		data.put("site_qq", qq);
		MailTemplateConfig mailTemplateConfig = TemplateConfigUtil.getMailTemplateConfig(MailTemplateConfig.WITHDRAW_NOTIFY);
		String subject = mailTemplateConfig.getSubject();
		String templatePath = mailTemplateConfig.getTemplatePath();
		sendMail(subject, templatePath, data, accountCash.getUser().getEmail());
	}
	
	
	public void sendPasswordRecoverMail(Member member) {
		Map<String, Object> data = getCommonData();
		data.put("member", member);
		MailTemplateConfig mailTemplateConfig = TemplateConfigUtil.getMailTemplateConfig(MailTemplateConfig.PASSWORD_RECOVER);
		String subject = mailTemplateConfig.getSubject();
		String templatePath = mailTemplateConfig.getTemplatePath();
		sendMail(subject, templatePath, data, member.getEmail());
	}
	
	public void sendGoodsNotifyMail(GoodsNotify goodsNotify) {
		Map<String, Object> data = getCommonData();
		data.put("goodsNotify", goodsNotify);
		MailTemplateConfig mailTemplateConfig = TemplateConfigUtil.getMailTemplateConfig(MailTemplateConfig.GOODS_NOTIFY);
		String subject = mailTemplateConfig.getSubject();
		String templatePath = mailTemplateConfig.getTemplatePath();
		sendMail(subject, templatePath, data, goodsNotify.getEmail());
	}
	
	/**
	 * 获取虚拟路径
	 * 
	 * @return 虚拟路径
	 */
	private String getContextPath() {
		if (servletContext.getMajorVersion() < 2 || (servletContext.getMajorVersion() == 2 && servletContext.getMinorVersion() < 5)) {
			return SettingUtil.getSetting().getContextPath();
		} else {
			return servletContext.getContextPath();
		}
	}

}