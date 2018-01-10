package com.qmd.action.html;

import com.qmd.action.base.BaseAction;
import com.qmd.service.html.HtmlService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Action类 - 生成静态页面
 */

@Service("BuildHtmlAction")
public class BuildHtmlAction extends BaseAction {

	private static final long serialVersionUID = -2197609380142883572L;

	private ServletContext servletContext;
	@Resource(name = "htmlService")
	private HtmlService htmlService;
	
	private String buildType;// 更新类型
	private String buildContent;// 更新内容
	private Integer maxResults;// 每次更新数
	private Integer firstResult;// 起始结果数
	private Date beginDate;// 开始日期
	private Date endDate;// 结束日期
	
	public String allInput() {
		return "all_input";
	}
	
	// 一键更新
	@Action(value="/buildHtml/ajaxAll",results={@Result(type="json")})
	public String all() throws Exception {
		long beginTimeMillis = System.currentTimeMillis();
//		if (StringUtils.isEmpty(buildContent)) {
//			buildContent = "header";
//		}
		//更新基本数据信息
		htmlService.replaceSetting();
		
//		if (buildContent.equalsIgnoreCase("header")) {
			
			htmlService.buildHeaderHtml();
			htmlService.buildBottomHtml();
			htmlService.build404Html();
			
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put(STATUS_PARAMETER_NAME, "success");
			jsonMap.put("buildCount", 1);
			jsonMap.put("buildTime", System.currentTimeMillis() - beginTimeMillis);
			return ajax(jsonMap);
//		} 
//		return NONE;
	}
	

	
	// 获取默认开始日期
	public Date getDefaultBeginDate() {
		return DateUtils.addDays(new Date(), -7);
	}

	// 获取默认结束日期
	public Date getDefaultEndDate() {
		return new Date();
	}

	public String getBuildType() {
		return buildType;
	}

	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}

	public String getBuildContent() {
		return buildContent;
	}

	public void setBuildContent(String buildContent) {
		this.buildContent = buildContent;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
	
	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}