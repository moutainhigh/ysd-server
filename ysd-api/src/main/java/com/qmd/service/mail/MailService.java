package com.qmd.service.mail;

import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserRepaymentDetail;
import com.qmd.mode.util.MailRepayForInvestor;

import java.util.List;
import java.util.Map;

/**
 * Service接口 - 邮件服务
 */

public interface MailService {
	
	/**
	 * 根据主题、Freemarker模板文件路径、接收邮箱地址、Map数据发送邮件(异步发送)
	 * 
	 * @param subject
	 *            主题
	 * 
	 * @param templatePath
	 *            Freemarker模板文件路径
	 * 
	 * @param data
	 *            Map数据
	 *            
	 * @param toMail
	 *            收件人邮箱
	 * 
	 */
	public void sendMail(String subject, String templatePath, Map<String, Object> data, String toMail);
	
	/**
	 * 发送SMTP邮箱配置测试(同步发送)
	 * 
	 * @param smtpFromMail
	 *            发件人邮箱
	 * 
	 * @param smtpHost
	 *            SMTP服务器地址
	 *            
	 * @param smtpPort
	 *            SMTP服务器端口
	 *            
	 * @param smtpUsername
	 *            SMTP用户名
	 *            
	 * @param smtpPassword
	 *            SMTP密码
	 *            
	 * @param toMail
	 *            收件人邮箱
	 * 
	 */
	public void sendSmtpTestMail(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail);
	
	/**
	 * 发送邮件找回  密码
	 * 
	 * @param member
	 *            会员
	 * 
	 
	public void sendPasswordRecoverMail(User user);
	*/
	/**
	 * 发送验证邮件
	 * 
	 * @param member
	 *            会员
	 * 
	 */
	public void sendEmailRecover(User user);
	
	
	/**
	 * 发送邮件
	 * @param user
	 * @param subject
	 * @param templatePath
	 */
	public void sendEmail(User user,String subject,String templatePath,String ck);
	
	/**
	 * 发送还款信息给投资者
	 * @param investor 投资者发送信息
	 */
	public void sendRepayForInvestor(MailRepayForInvestor investor );
	
	/**
	 * 发送借款协议邮件
	 * @param borrow
	 * @param borrowTender
	 * @param toMail
	 */
	public void sendAgreementForInvestor(Borrow borrow, UserRepaymentDetail userRepaymentDetail, User user);
	/**
	 * 发送借款协议邮件(流转分期）
	 * @param borrow
	 * @param borrowTender
	 * @param toMail
	 */
	public void sendAgreementForInvestor(Borrow borrow,List<UserRepaymentDetail> userRepaymentDetailList,User user);
	
	/**
	 * 注册邮箱验证
	 */
	public void sendEmailRegister(User user);
	
}