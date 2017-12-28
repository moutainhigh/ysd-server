package com.qmd.service.impl.article;

import com.qmd.dao.article.ArticleDao;
import com.qmd.mode.article.Article;
import com.qmd.mode.article.ArticleCategory;
import com.qmd.service.article.ArticleService;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.util.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("articleService")
public class ArticleServiceImpl extends BaseServiceImpl<Article,Integer> implements ArticleService{
	
	@Resource
	private ArticleDao articleDao;

	@Resource
	public void setBaseDao(ArticleDao articleDao) {
		super.setBaseDao(articleDao);
	}
	
	@Override
	public List<ArticleCategory> getChildArticleCategoryByIdList(Integer id){
		return articleDao.getChildArticleCategoryByIdList(id);
	}

	public ArticleCategory getArticleCategory(Map<String,Object> map){
		return articleDao.getArticleCategory(map);
	}
	@Override
	public List<Article> getArticleByArticleCategoryId(Map<String,Object> map){
		return articleDao.getArticleByArticleCategoryId(map);
	}
	
	@Override
	public Article getArticleById(Map<String,Object> map){
		return articleDao.getArticleById(map);
	}


	public List<Article> getArticleBySign(Map<String,Object> map){
		return articleDao.getArticleBySign(map);
	}
	
	public Pager getArticlePagerBySign(Pager pager,Map<String,Object> map){
		return articleDao.getArticlePagerBySign(pager,map);
	}
	
	public List<Article> getArticleBySignMany(Map<String,Object> map){
		return articleDao.getArticleBySignMany(map);
	}
	
	public Pager getArticlePagerBySignMany(Pager pager,Map<String,Object> map){
		return articleDao.getArticlePagerBySignMany(pager,map);
	}
	
	public Integer getArticleBySignCount(Map<String,Object> map) {
		return articleDao.getArticleBySignCount(map);
	}
	
}
