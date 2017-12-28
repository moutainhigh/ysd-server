package com.qmd.service.impl.mail;


import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserRepaymentDetail;
import com.qmd.mode.util.MailRepayForInvestor;
import com.qmd.service.mail.MailService;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.springframework.core.task.TaskExecutor;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Service实现类 - 邮件服务
 */

@Service("mailService")
public class MailServiceImpl implements MailService, ServletContextAware {

	private ServletContext servletContext;
	@Resource(name = "freemarkerManager")
	private FreemarkerManager freemarkerManager;
	@Resource(name = "javaMailSender")
	private JavaMailSenderImpl javaMailSender;
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
		
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public JavaMailSenderImpl getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	// 增加邮件发送任务
	public void addSendMailTask(final MimeMessage mimeMessage) {
		try {
			taskExecutor.execute(
				new Runnable() {
					public void run() {
						getJavaMailSender().send(mimeMessage);
					}
				}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMail(String subject, String templatePath, Map<String, Object> data, String toMail) {
		try {
			String mail_from = (String)servletContext.getAttribute("qmd.setting.smtpFromMail");
			String side_name = (String)servletContext.getAttribute("qmd.setting.name");
						
			String host = (String)servletContext.getAttribute("qmd.setting.smtpHost");
			Integer port = (Integer)servletContext.getAttribute("qmd.setting.smtpPort");
			String username = (String)servletContext.getAttribute("qmd.setting.smtpUsername");
			String password = (String)servletContext.getAttribute("qmd.setting.smtpPassword");
			String websiteurl = (String)servletContext.getAttribute("qmd.setting.url");
			String side_phone = (String)servletContext.getAttribute("qmd.setting.phone");
			String side_qq = (String)servletContext.getAttribute("qmd.setting.officialQq");
			
			String logoPath = (String)servletContext.getAttribute("qmd.setting.logoPath");
			data.put("imgbase", ConstantUtil.WEB_IMG);
			data.put("logoPath", logoPath);
			
			data.put("side_name", side_name);
			data.put("websiteurl", websiteurl);
			data.put("site_phone", side_phone);
			data.put("site_qq", side_qq);
			
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
			
			//mimeMessageHelper.setFrom(mail_from);
			mimeMessageHelper.setFrom(MimeUtility.encodeWord(side_name) + " <" + mail_from + ">");
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text, true);
			addSendMailTask(mimeMessage);
		} catch (Exception e) {
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
	
	// 获取公共数据
	public Map<String, Object> getCommonData() {
		Map<String, Object> commonData = new HashMap<String, Object>();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
		ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
		commonData.put("bundle", resourceBundleModel);
		commonData.put("base", getContextPath());
		return commonData;
	}

	public void sendSmtpTestMail(String toMail) {
//		Setting setting = SettingUtil.getSetting();
//		Map<String, Object> data = getCommonData();
//		MailTemplateConfig mailTemplateConfig = TemplateConfigUtil.getMailTemplateConfig(MailTemplateConfig.SMTP_TEST);
//		String subject = mailTemplateConfig.getSubject();
//		String templatePath = mailTemplateConfig.getTemplatePath();
		String subject = "主题";
//		String templatePath ="content/mail/mail_smtp_test.ftl" ;


		try {

			//javaMailSenderImpl.setHost(smtpHost);
			//javaMailSenderImpl.setPort(smtpPort);
			//javaMailSenderImpl.setUsername(smtpUsername);
			//javaMailSenderImpl.setPassword(smtpPassword);
			MimeMessage mimeMessage = this.getJavaMailSender().createMimeMessage();
//			Configuration configuration = freemarkerManager.getConfiguration(servletContext);
//			Template template = configuration.getTemplate(templatePath);
//			String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
//			mimeMessageHelper.setFrom(MimeUtility.encodeWord("测试邮件") + " <" + smtpFromMail + ">");
			//mimeMessageHelper.setFrom(smtpFromMail);
//			mimeMessageHelper.setFrom("280030232@qq.com");
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText("测试邮件text", true);
//			mimeMessageHelper.setText(text, true);
			this.getJavaMailSender().send(mimeMessage);
//			javaMailSender.send(mimeMessage);
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	
	public void sendEmailRecover(User user){
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("user", user);
		data.put("url", ConstantUtil.WEB_SITE);
		data.put("ck",  CommonUtil.encodeUrl(user.getId()+"##"+user.getEmailCertificationKey()));//加密串
		String subject = "邮箱认证通知";
		String templatePath = ConstantUtil.SENDEMAIL_TEMPLATE ;
		sendMail(subject, templatePath, data, user.getEmail());
//		sendSmtpTestMail(user.getEmail());
	}
	
	/**
	 * 发送还款信息给投资者
	 * @param investor 投资者发送信息
	 */
	public void sendRepayForInvestor(MailRepayForInvestor investor ){
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("userName", investor.getUserName());
		data.put("borrowName", investor.getBorrowName());
		data.put("borrowId", investor.getBorrowId());
		data.put("repayDate", investor.getRepayDate());
		data.put("repayMoney", CommonUtil.setPriceScale2String(investor.getRepayMoney()));
		data.put("url", ConstantUtil.WEB_SITE);
		String subject = "还款通知";
		String templatePath = ConstantUtil.SENDREFUND_TEMPLATE;
		sendMail(subject, templatePath, data, investor.getUserMail());
//		sendSmtpTestMail(user.getEmail());
	}
	
	/**
	 * 发送借款协议邮件
	 */
	public void sendAgreementForInvestor(Borrow borrow,UserRepaymentDetail userRepaymentDetail,User user){
		Map<String,Object> data = new HashMap<String,Object>();
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat simple1 = new SimpleDateFormat("yyyy-MM-dd");
		String time = simple.format(new Date());
		String agreementId=time+borrow.getId();
		data.put("agreementId", agreementId);
		data.put("borrow", borrow);
		data.put("user", user);
		String lastRdate=simple1.format(new Date());
		data.put("lastRdate", lastRdate);
		data.put("userRepaymentDetail",userRepaymentDetail);
		String subject = "借款协议";
		String templatePath = ConstantUtil.SENDAGREEMENT_TEMPLATE;
		sendMail(subject, templatePath, data, user.getEmail());
	}
	
	/**
	 * 发送借款协议邮件
	 */
	public void sendAgreementForInvestor(Borrow borrow,List<UserRepaymentDetail> userRepaymentDetailList,User user){
		Map<String,Object> data = new HashMap<String,Object>();
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat simple1 = new SimpleDateFormat("yyyy-MM-dd");
		String time = simple.format(new Date());
		String agreementId=time+borrow.getId();
		data.put("agreementId", agreementId);
		data.put("borrow", borrow);
		data.put("user", user);
		String lastRdate=simple1.format(new Date());
		data.put("lastRdate", lastRdate);
		//data.put("userRepaymentDetailList",userRepaymentDetailList);
		//TODO 借款标
		String subject = "借款协议";
		String templatePath = ConstantUtil.SENDAGREEMENT_TEMPLATE;
		sendMail(subject, templatePath, data, user.getEmail());
	}
	/**
	 * 注册邮箱验证
	 */
	public void sendEmailRegister(User user){
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("user", user);
		data.put("url", ConstantUtil.WEB_SITE);
		data.put("ck",  CommonUtil.encodeUrl(user.getId()+"##"+user.getEmailCertificationKey()));//加密串
		String subject = "邮箱认证通知";
		String templatePath = ConstantUtil.SENDEMAIL_TEMPLATE_REGISTER ;
		sendMail(subject, templatePath, data, user.getEmail());
//		sendSmtpTestMail(user.getEmail());
	}
	
	
	public void sendEmail(User user,String subject,String templatePath,String ck){
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("user", user);
		data.put("url", ConstantUtil.WEB_SITE);
		data.put("ck", CommonUtil.encodeUrl(ck));
		if(StringUtils.isNotEmpty(templatePath)){
			sendMail(subject, templatePath, data, user.getEmail());
		}
	}
	/**
	 * 获取虚拟路径
	 * 
	 * @return 虚拟路径
	 */
	private String getContextPath() {
//		if (servletContext.getMajorVersion() < 2 || (servletContext.getMajorVersion() == 2 && servletContext.getMinorVersion() < 5)) {
//			return SettingUtil.getSetting().getContextPath();
			return "";
//		} else {
//			return servletContext.getContextPath();
//		}
	}



	@Override
	public void sendSmtpTestMail(String smtpFromMail, String smtpHost,
			Integer smtpPort, String smtpUsername, String smtpPassword,
			String toMail) {
		// TODO Auto-generated method stub
		
	}

	public FreemarkerManager getFreemarkerManager() {
		return freemarkerManager;
	}

	public void setFreemarkerManager(FreemarkerManager freemarkerManager) {
		this.freemarkerManager = freemarkerManager;
	}



}