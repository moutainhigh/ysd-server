package com.qmd.action.article;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.PageBean;
import com.qmd.bean.article.ArticleBean;
import com.qmd.bean.article.ArticleItem;
import com.qmd.bean.article.ArticleList;
import com.qmd.mode.article.Article;
import com.qmd.mode.article.ArticleCategory;
import com.qmd.service.article.ArticleService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.JsonUtil;
import com.qmd.util.Pager;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InterceptorRefs({ @InterceptorRef(value = "qmdDefaultStack") })
@Service("apiArticleAction")
public class ApiArticleAction extends ApiBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(ApiArticleAction.class);
	@Resource
	ArticleService articleService;
	
	private int way;

	@SuppressWarnings("unchecked")
	@Action(value = "/api/articleList/detail")
	public String articleList() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sign", id);
		ArticleCategory articleCategory = articleService
				.getArticleCategory(map);
		if (articleCategory == null || articleCategory.getId() == null) {// 文章分类不存在
			return ajaxJson("E0002", "文章分类不存在");
		}
		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(10);
		}

		ArticleList bean = new ArticleList();

		map.put("pager", pager);
		if(way==1){
			map.put("isWei", true);
		}else if(way==0){
			map.put("isPc", true);
		}else if(way==2){
			map.put("isApp", true);
		}else if(way==3){
			map.put("isIos", true);
		}
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
			item.setCreateDate(b.getCreateDate());
			articleItemList.add(item);
		}
		bean.setArticleItemList(articleItemList);

		return ajax(JsonUtil.toJson(bean));
	}

	/**
	 * 读取文章内容
	 * 
	 * @return
	 */
	@Action(value = "/api/article/detail")
	public String content() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		Article article = articleService.getArticleById(map);
		if (article == null) {
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}

		article.setHits(article.getHits() + 1);
		articleService.update(article);// 修改浏览量

		ArticleBean bean = new ArticleBean();
		bean.setTitle(article.getTitle());
		bean.setCreateDate(article.getCreateDate());
		bean.setContent(article.getContent());
		return ajax(JsonUtil.toJson(bean));
	}

	public int getWay() {
		return way;
	}

	public void setWay(int way) {
		this.way = way;
	}
	
	

}
