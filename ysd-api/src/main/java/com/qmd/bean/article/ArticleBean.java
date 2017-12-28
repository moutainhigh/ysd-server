package com.qmd.bean.article;

import com.qmd.bean.BaseBean;

import java.util.Date;

public class ArticleBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 449721654050777595L;

	public ArticleBean() {
		setRcd("R0001");
	}

	private Date createDate;// 创建日期
	private String title;// 标题
	private String content;// 内容

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
