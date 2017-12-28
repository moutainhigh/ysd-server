package com.qmd.dao.impl.article;

import com.qmd.dao.article.ArticleDao;
import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.mode.article.Article;
import com.qmd.mode.article.ArticleCategory;
import com.qmd.util.Pager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository("articleDao")
public class ArticleDaoImpl  extends BaseDaoImpl<Article, Integer>  implements ArticleDao{

	public List<ArticleCategory> getChildArticleCategoryByIdList(Integer id){
		List<ArticleCategory> articleCategoryList = this.getSqlSession().selectList("Article.getChildArticleCategoryByIdList",id);
		return articleCategoryList;
	}
	
	public ArticleCategory getArticleCategory(Map<String,Object> map){
		ArticleCategory articleCategory = this.getSqlSession().selectOne("Article.getArticleCategory",map);
		return articleCategory;
	}
	
	public List<Article> getArticleByArticleCategoryId(Map<String,Object> map){
		List<Article> articleList = this.getSqlSession().selectList("Article.getArticleByArticleCategoryId",map);
		return articleList;
	}
	
	public Article getArticleById(Map<String,Object> map){
		Article article = (Article)this.getSqlSession().selectOne("Article.getArticleByArticleCategoryId",map);
		return article;
	}
	
	public List<Article> getArticleBySign(Map<String,Object> map){
		List<Article> articleList = this.getSqlSession().selectList("Article.getArticleBySign",map);
		return articleList;
	}
	
	public Pager getArticlePagerBySign(Pager pager,Map<String,Object> map){
			Integer count = Integer.parseInt(this.getSqlSession().selectOne("Article.getArticleBySignCount",map).toString());
			pager.setTotalCount(count);
			map.put("pager", pager);
			List<Article> aList = getArticleBySign(map);
			pager.setResult(aList);
			return pager;
	}
	
	public Integer getArticleBySignCount(Map<String,Object> map){
		Integer count = Integer.parseInt(this.getSqlSession().selectOne("Article.getArticleBySignCount",map).toString());
		return count;
}
	
	public List<Article> getArticleBySignMany(Map<String,Object> map){
		List<Article> articleList = this.getSqlSession().selectList("Article.getArticleBySignMany",map);
		return articleList;
	}
	
	public Pager getArticlePagerBySignMany(Pager pager,Map<String,Object> map){
			Integer count = Integer.parseInt(this.getSqlSession().selectOne("Article.getArticleBySignManyCount",map).toString());
			pager.setTotalCount(count);
			map.put("pager", pager);
			List<Article> aList = getArticleBySignMany(map);
			pager.setResult(aList);
			return pager;
	}
	
}
