package com.qmd.bean.article;

import com.qmd.bean.BaseBean;
import com.qmd.bean.PageBean;

import java.util.List;

public class ArticleList extends BaseBean {

	private static final long serialVersionUID = -7683603982464447706L;

	public ArticleList() {
		setRcd("R0001");
	}

	private List<ArticleItem> articleItemList;

	private PageBean pageBean;

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public List<ArticleItem> getArticleItemList() {
		return articleItemList;
	}

	public void setArticleItemList(List<ArticleItem> articleItemList) {
		this.articleItemList = articleItemList;
	}

}
