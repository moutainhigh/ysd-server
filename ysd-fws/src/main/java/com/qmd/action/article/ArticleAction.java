package com.qmd.action.article;

import com.qmd.action.base.BaseAction;
import com.qmd.mode.article.Article;
import com.qmd.mode.article.ArticleCategory;
import com.qmd.mode.user.User;
import com.qmd.service.article.ArticleService;
import com.qmd.service.borrow.BorrowService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("articleAction")
public class ArticleAction  extends BaseAction{

	private static final long serialVersionUID = 4662714871122334826L;
	Logger log = Logger.getLogger(ArticleAction.class);
	@Resource
	ArticleService articleService;
	@Resource
	BorrowService borrowService;
	
	private ArticleCategory articleCategory;//文章分类
	private ArticleCategory articleCategorySuperior;//文章上级分类
	private Article article;
	private List<Article> articleList;
	private String sign;
	private List<ArticleCategory> articleCategoryList;
	
	/**
	 * 读取服务商消息通知
	 * @return
	 */
	@Action(value="/article/content",results={@Result(name="success", location="/content/article/article_content.ftl", type="freemarker")})
	public String content(){
		
		try{
		log.info("文章内容");
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("id", id);
		article = articleService.getArticleById(map);
		if(article== null){
			addActionError("参数错误");
			return "error_ftl";
		}
		User user = getLoginUser();
		if(user == null){
			addActionError("参数错误");
			return "error_ftl";
		}
		Map<String , Object> mapa = new HashMap<String, Object>();
		mapa.put("id", article.getArticleCategoryId());
		articleCategory = articleService.getArticleCategory(mapa);
		if(articleCategory == null){
			addActionError("内容已删除或过期!");
			redirectUrl=getContextPath()+"/about.do";
			return "error_ftl";
		}
		sign = articleCategory.getSign();
		
		if(!sign.equals("agency_message")){
			addActionError("参数错误");
			return "error_ftl";
		}
		article.setHits(article.getHits()+1);
		articleService.update(article);//修改浏览量
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

//	@Action(value="/article/list",results={@Result(name="success",location="/content/article/article_list.ftl",type="freemarker")})
//	public String articleList(){
//		log.info("文章列表");
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("articleCategoryId", id);
//		articleList = articleService.getArticleByArticleCategoryId(map);
//		if(articleList== null || articleList.size()<1){
//			addActionError("参数错误");
//			return "error_ftl";
//		}
//		return SUCCESS;
//	}
//	/**
//	 * 帮助中心
//	 * @return
//	 */
//	@Action(value="/help",results={@Result(name="success",location="/content/article/help.ftl",type="freemarker")})
//	public String help(){
//		return SUCCESS;
//	}
//	
//	/**
//	 * 关于金贷通
//	 * @return
//	 */
//	@Action(value="/about",results={@Result(name="success",location="/content/article/about.ftl",type="freemarker")})
//	public String about(){
//		Map<String,Object> map = new HashMap<String,Object>();
//		if(StringUtils.isEmpty(sign)){
//			sign = "about_us";
//		}
//		map.put("sign", sign);
//		articleCategory = articleService.getArticleCategory(map);
//		if(articleCategory == null){
//			addActionError("参数错误!");
//			return "error_ftl";
//		}
//		pager.setPageSize(20);
//		map.put("pager", pager);
//		pager = articleService.getArticlePagerBySign(pager, map);
//		return SUCCESS;
//	}
//	
//	/**
//	 * 关于金贷通中的项目介绍
//	 * @return
//	 */
//	@Action(value="/aboutProgram",results={@Result(name="success",location="/content/article/about_program.ftl",type="freemarker")})
//	public String aboutProgram(){
//		
//		Map<String,Object> cmap = new HashMap<String,Object>();
//		if(StringUtils.isEmpty(sign)){
//			sign = "project_introduction";
//		}
//		cmap.put("sign", sign);
//		articleCategory = articleService.getArticleCategory(cmap);
//		
//		Map<String,Object> map = new HashMap<String,Object>();
//		
//		if(StringUtils.isEmpty(sign)||"project_introduction".equals(sign)){
//			String[] strs = {"xmjs_the_pledge","xmjs_assignment_of_debt"};
//			map.put("signs", strs);
//		} else {
//			String[] strs = {sign};
//			map.put("signs", strs);
//		}
//		
//		pager.setPageSize(20);
//		map.put("pager", pager);
//		pager = articleService.getArticlePagerBySignMany(pager, map);
//		return SUCCESS;
//	}
//	
//	/**
//	 * 项目介绍
//	 * @return
//	 */
//	@Action(value="/introduction",results={@Result(name="success",location="/content/article/project_introduction.ftl",type="freemarker")})
//	public String introduction(){
//		if(StringUtils.isNotEmpty(sign)){
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("sign", sign);
//			articleCategory = articleService.getArticleCategory(map);
//			if(articleCategory == null){
//				addActionError("参数错误!");
//				return "error_ftl";
//			}
//		}
//		return SUCCESS;
//	}
//	
//	/**
//	 * 线下配对
//	 * @return
//	 */
//	
//	@Action(value="/matching",results={@Result(name="success",location="/content/article/off_line_matching.ftl",type="freemarker")})
//	public String matching(){
//		Map<String,Object> map = new HashMap<String,Object>();
//		pager.setPageSize(5);
//		//成功案例
//		map.put("sign", "offline_matching");//线下配对-仅用于获取描述
//		articleCategory = articleService.getArticleCategory(map);
//		map.put("sign", "successful_case");//成功安全-获取列表页
//		pager = articleService.getArticlePagerBySign(pager, map);
//		return SUCCESS;
//	}
//	
//	/**
//	 * 投资中心
//	 * @return
//	 */
//	@Action(value="/inverstment",results={@Result(name="success",location="/content/article/investment_center.ftl",type="freemarker")})
//	public String inverstment(){
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("sign", sign);
//		articleCategory = articleService.getArticleCategory(map);
//		if(articleCategory == null){
//			addActionError("参数错误!");
//			return "error_ftl";
//		}
//		articleCategoryList = articleService.getChildArticleCategoryByIdList(articleCategory.getId());
//		return SUCCESS;
//	}
	
	
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setArticleCategoryList(List<ArticleCategory> articleCategoryList) {
		this.articleCategoryList = articleCategoryList;
	}
	public List<ArticleCategory> getArticleCategoryList(){
		return articleCategoryList;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	public ArticleCategory getArticleCategorySuperior() {
		return articleCategorySuperior;
	}

	public void setArticleCategorySuperior(ArticleCategory articleCategorySuperior) {
		this.articleCategorySuperior = articleCategorySuperior;
	}
	
	
}
