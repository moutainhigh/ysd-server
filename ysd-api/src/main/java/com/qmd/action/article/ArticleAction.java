package com.qmd.action.article;

import com.qmd.action.base.BaseAction;
import com.qmd.bean.PageBean;
import com.qmd.bean.article.ArticleBean;
import com.qmd.bean.article.ArticleItem;
import com.qmd.bean.article.ArticleList;
import com.qmd.mode.article.Article;
import com.qmd.mode.article.ArticleCategory;
import com.qmd.service.article.ArticleService;
import com.qmd.util.Pager;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InterceptorRefs({ @InterceptorRef(value = "qmdDefaultStack") })
@Service("articleAction")
public class ArticleAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(ApiArticleAction.class);
	@Resource
	ArticleService articleService;
	
	private int way;

	private String title;
	private ArticleList beanList;
	private ArticleBean bean;
	
	@Action(value = "/article/list",results={@Result(name="success", location="/content/article/list.ftl", type="freemarker")})
	public String articleList() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sign", id);
		ArticleCategory articleCategory = articleService.getArticleCategory(map);
		if (articleCategory == null || articleCategory.getId() == null) {// 文章分类不存在
			msg="此分类不存在";
			return "error_ftl";
		}
		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(10);
		}
		title = articleCategory.getName();
		ArticleList bean = new ArticleList();

		map.put("pager", pager);
			map.put("isWei", true);

		pager = articleService.getArticlePagerBySign(pager, map);

		PageBean pb = new PageBean();
		pb.setPageNumber(pager.getPageNumber());
		pb.setPageCount(pager.getPageCount());
		pb.setPageSize(pager.getPageSize());
		pb.setTotalCount(pager.getTotalCount());
		bean.setPageBean(pb);

		List<ArticleItem> articleItemList = new ArrayList<ArticleItem>();

		List<Article> itemList = (List<Article>) pager.getResult();

		for (Article b : itemList) {
			ArticleItem item = new ArticleItem();
			item.setId(b.getId());
			item.setTitle(b.getTitle());
			item.setContent(b.getContent());
			articleItemList.add(item);
		}
		bean.setArticleItemList(articleItemList);

		beanList = bean;
		return SUCCESS;
	}

	/**
	 * 读取文章内容
	 * 
	 * @return
	 */
	@Action(value = "/article/detail",results={@Result(name="success", location="/content/article/detail.ftl", type="freemarker")})
	public String content() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		Article article = articleService.getArticleById(map);
		if (article == null) {
			msg="此文章不存在";
			return "error_ftl";
		}

		article.setHits(article.getHits() + 1);
		articleService.update(article);// 修改浏览量

		ArticleBean articleBean = new ArticleBean();
		articleBean.setTitle(article.getTitle());
		articleBean.setCreateDate(article.getCreateDate());
		articleBean.setContent(article.getContent());
		
		bean = articleBean;
		
		return SUCCESS;
	}

	public int getWay() {
		return way;
	}

	public void setWay(int way) {
		this.way = way;
	}

	public ArticleBean getBean() {
		return bean;
	}

	public void setBean(ArticleBean bean) {
		this.bean = bean;
	}

	public ArticleList getBeanList() {
		return beanList;
	}

	public void setBeanList(ArticleList beanList) {
		this.beanList = beanList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
