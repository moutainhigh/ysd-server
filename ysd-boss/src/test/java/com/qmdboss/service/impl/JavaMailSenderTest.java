package com.qmdboss.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @author xishengchun on 2017-09-05.
 */
public class JavaMailSenderTest {



    @Test
    public void testJavaMailSender() throws MessagingException, UnsupportedEncodingException {
//        JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl)javaMailSender;
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        int smtpPort = 25;
        String smtpHost = "smtp.mxhichina.com";
        String smtpUsername = "service@yueshang.onaliyun.com";
        String smtpPassword = "LMJjay623098071";
        String smtpFromMail = "service@yueshang.onaliyun.com";

        Properties javaMailProperties = javaMailSenderImpl.getJavaMailProperties();

//        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.debug", "true");

        if (smtpPort == 465) {
            // ssl
//            Properties javaMailProperties = javaMailSenderImpl.getJavaMailProperties();
//            javaMailProperties.put("mail.smtp.auth", "true");
            javaMailProperties.put("mail.smtp.ssl.enable", "true");
//            javaMailProperties.put("mail.smtp.starttls.enable", "true");
            javaMailProperties.put("mail.transport.protocol", "smtp");

            javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            javaMailProperties.put("mail.smtp.socketFactory.port", "465");
        }

        javaMailSenderImpl.setHost(smtpHost);
        javaMailSenderImpl.setPort(smtpPort);
        javaMailSenderImpl.setUsername(smtpUsername);
        javaMailSenderImpl.setPassword(smtpPassword);
        MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();

//        Configuration configuration = freemarkerManager.getConfiguration(servletContext);
//        Template template = configuration.getTemplate(templatePath);
//        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        mimeMessageHelper.setFrom(MimeUtility.encodeWord("乐商贷") + " <" + smtpFromMail + ">");
        mimeMessageHelper.setTo("xishengchun@163.com");
        mimeMessageHelper.setSubject("测试邮件");
        mimeMessageHelper.setText("测试邮件", true);
        javaMailSenderImpl.send(mimeMessage);
    }
}
