package com.qmd.action.feedback;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.BaseBean;
import com.qmd.mode.feedback.Feedback;
import com.qmd.mode.user.User;
import com.qmd.service.feedback.FeedbackService;
import com.qmd.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@InterceptorRefs({ 
	@InterceptorRef(value = "qmdDefaultStack") })
@Service("apiFeedbackAction")
public class ApiFeedbackAction extends ApiBaseAction {

	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(ApiFeedbackAction.class);
	
	private String  content;//反馈内容
	
	private String contact;//联系方式：手机号或邮箱
	
	@Resource
	FeedbackService feedbackService;
	
	
	/**
	 * 新的反馈意见
	 * @return
	 * @author zdl
	 */
	@Action(value="/api/adFeedbackNew",results={@Result(type="json")})
	public String adFeedbackNew() throws Exception{
		if(StringUtils.isEmpty(content)){
			return ajaxJson("E0002", "反馈内容不能为空!");
		}
		if(StringUtils.isEmpty(contact)){
			return ajaxJson("E0002", "请输入手机号或邮箱!");
		}
		//手机号或邮箱验证
		Pattern phoneP = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher phoneM = phoneP.matcher(contact);
        Pattern  emailP= Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher emailM = emailP.matcher(contact);
		if (!phoneM.matches()&&!emailM.matches()) {
			return ajaxJson("E0002", "请输入正确的手机号或邮箱格式!");
		}
		Feedback feedback = new Feedback();
		feedback.setContent(content);
		feedback.setContact(contact);
		User user = getLoginUser();
		if(user!=null){
			feedback.setUserId(user.getId());
		}
		BaseBean bean = new BaseBean();
		feedbackService.saveNew(feedback);
		bean.setRcd("R0001");
		bean.setRmg("反馈成功");
		return ajax(JsonUtil.toJson(bean));
	}
	
	/**
	 * 反馈意见
	 * @return
	 */
	@Action(value="/api/adFeedback",results={@Result(type="json")})
	public String adFeedback() throws Exception{
		if(StringUtils.isEmpty(content)){
			return ajaxJson("E0002", "反馈内容不能为空");
		}
		Feedback feedback = new Feedback();
		feedback.setContent(content);
		User user = getLoginUser();
		if(user!=null){
			feedback.setUserId(user.getId());
		}
		BaseBean bean = new BaseBean();
		feedbackService.save(feedback);
		bean.setRcd("R0001");
		bean.setRmg("反馈成功");
		return ajax(JsonUtil.toJson(bean));
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}


}
