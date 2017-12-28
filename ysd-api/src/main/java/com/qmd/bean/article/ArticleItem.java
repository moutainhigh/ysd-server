package com.qmd.bean.article;

import java.io.Serializable;
import java.util.Date;

public class ArticleItem implements Serializable {

	private static final long serialVersionUID = 7681548254926138000L;

	private Integer id;// ID
	private String title;// 标题
	private String content;
	protected Date createDate;// 创建日期

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	

}
