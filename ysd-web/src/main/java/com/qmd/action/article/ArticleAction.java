package com.qmd.action.article;

import com.qmd.action.base.BaseAction;
import com.qmd.enums.HelpCenterEnum;
import com.qmd.mode.article.Article;
import com.qmd.mode.article.ArticleCategory;
import com.qmd.service.article.ArticleService;
import com.qmd.service.borrow.BorrowService;
import org.apache.commons.lang3.StringUtils;
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
	private String enumKey;
	
	private String []signKey;//string 分类
	
	private List l;//前台展示article
	private String title;//页面大标题

	
	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public List getL() {
		return l;
	}


	public void setL(List l) {
		this.l = l;
	}


	public String[] getSignKey() {
		return signKey;
	}


	public void setSignKey(String[] signKey) {
		this.signKey = signKey;
	}


	/**
	 * 读取文章内容
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
		Map<String , Object> mapa = new HashMap<String, Object>();
		mapa.put("id", article.getArticleCategoryId());
		articleCategory = articleService.getArticleCategory(mapa);
		if(articleCategory == null){
			addActionError("内容已删除或过期!");
			redirectUrl=getContextPath()+"/about.do";
			return "error_ftl";
		}
		sign = articleCategory.getSign();
		
		//获取关于项目介绍的分类
//		mapa.put("id", articleCategory.getParentId());
//		ArticleCategory ac  = articleService.getArticleCategory(mapa);
//		if(ac.getSign().equals("project_introduction")){
//			articleCategorySuperior = ac;
//		}
		
		article.setHits(article.getHits()+1);
		articleService.update(article);//修改浏览量
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/**
	 * qxw 
	 * help-center
	 * @return
	 */
	@Action(value="/article/helpCenter",results={@Result(name="success", location="/content/article/article_help_detail.ftl", type="freemarker")})
	public String helpCenterArticle(){
		
		try{
		log.info("帮助中心内容");
		int articleCategoryId=92;//92对应常见问题的分类
		if(enumKey==null||enumKey==""){
			addActionError("参数错误");
			return "error_ftl";
		}
		
		if(HelpCenterEnum.findValueByKey(enumKey)==null){
			addActionError("参数错误");
			return "error_ftl";
		}
		
		sign ="help_center";
		
		title=HelpCenterEnum.findValueByKey(enumKey).getTitle();
		
		
		
		//System.out.println("========="+HelpCenterEnum.findValueByKey(enumKey).getValue());
		signKey=HelpCenterEnum.findValueByKey(enumKey).getValue().trim().split(",");
		
		l=HelpCenterEnum.getListMapBy(signKey);

		Map<String , Object> map = new HashMap<String, Object>();
		map.put("articleCategoryId", articleCategoryId);
		map.put("EnumAarray",signKey);
		
		articleList= articleService.selectListAByMap(map);
		

		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@Action(value="/article/list",results={@Result(name="success",location="/content/article/article_list.ftl",type="freemarker")})
	public String articleList(){
		log.info("文章列表");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("articleCategoryId", id);
		articleList = articleService.getArticleByArticleCategoryId(map);
		if(articleList== null || articleList.size()<1){
			addActionError("参数错误");
			return "error_ftl";
		}
		return SUCCESS;
	}
	/**
	 * 帮助中心
	 * @return
	 */
	@Action(value="/help",results={@Result(name="success",location="/content/article/help.ftl",type="freemarker")})
	public String help(){
		return SUCCESS;
	}
	
	/**
	 * 关于金贷通
	 * zdl 修改：关于我们-平台介绍页面：about_us.ftl静态页面
	 * @return
	 */
	@Action(value = "/about", results = {
			@Result(name = "success", location = "/content/article/about.ftl", type = "freemarker"),
			@Result(name = "success1", location = "/content/article/about_mt.ftl", type = "freemarker"),
			@Result(name = "success2", location = "/content/article/about_honor.ftl", type = "freemarker"),
			@Result(name = "succ_content", location = "/content/article/article_content.ftl", type = "freemarker"),
			@Result(name = "succ_about", location = "/content/article/about_us.ftl", type = "freemarker"),
			@Result(name = "help_center_new", location = "/content/article/article_help_new.ftl", type = "freemarker")})
	public String about() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(sign) || sign.equals("about_us")) {
			sign = "about_us";
			id = "441";
			content();
			return "succ_about";

		}
		//zdl 联系我们
		if(StringUtils.equals(sign, "contactus")){
			id = "675";
			content();
			return "succ_content";
		}
		
		if (sign.equals("help_center")) {
			return "help_center_new";
		}
		
		
		map.put("sign", sign);
		articleCategory = articleService.getArticleCategory(map);
		if (articleCategory == null) {
			addActionError("参数错误!");
			return "error_ftl";
		}
		pager.setPageSize(10);
		if (sign.equals("media_report")) {
			pager.setPageSize(5);
		}
		map.put("pager", pager);
		pager = articleService.getArticlePagerBySign(pager, map);

		System.out.println("sign===" + sign);
		if (sign.equals("media_report")) {
			return "success1";//媒体
		}
		
		if (sign.equals("honor")) {
			return "success2";//荣誉
		}
		
		return SUCCESS;
	}
	
	/**
	 * 安全保障 01
	 * @return
	 */	
	@Action(value="/safe1",results={@Result(name="success",location="/content/article/safe1.ftl",type="freemarker")})
	public String safe1(){
		return SUCCESS;
	}

	/**
	 * 安全保障 02
	 * @return
	 */	
	@Action(value="/safe2",results={@Result(name="success",location="/content/article/safe2.ftl",type="freemarker")})
	public String safe2(){
		return SUCCESS;
	}
	/**
	 * 安全保障 03
	 * @return
	 */	
	@Action(value="/safe3",results={@Result(name="success",location="/content/article/safe3.ftl",type="freemarker")})
	public String safe3(){
		return SUCCESS;
	}
/**
 * 新手指引
 * @return
 */	
@Action(value="/newPoint",results={@Result(name="success",location="/content/article/newPoint.ftl",type="freemarker")})
public String newPoint(){
return SUCCESS;
}

/**客户端下载
 * qxw
 * @return 
 */
@Action(value="/newDownload",results={@Result(name="success",location="/content/article/phone_client.ftl",type="freemarker")})
public String newDownload(){
return SUCCESS;
}
/**
 * 帮助中心
 * @return
 */	
@Action(value="/helpCenter",results={@Result(name="success",location="/content/article/helpCenter.ftl",type="freemarker")})
public String helpCenter(){
return SUCCESS;
}
/**
 * 质押宝
 * @return
 */	
@Action(value="/zhiYa",results={@Result(name="success",location="/content/article/zhiYa.ftl",type="freemarker")})
public String zhiYa(){
return SUCCESS;
}
/**
 * 抵押宝
 * @return
 */	
@Action(value="/diYa",results={@Result(name="success",location="/content/article/diYa.ftl",type="freemarker")})
public String diYa(){
return SUCCESS;
}
/**
 * 按揭宝
 * @return
 */	
@Action(value="/anJie",results={@Result(name="success",location="/content/article/anJie.ftl",type="freemarker")})
public String anJie(){
return SUCCESS;
}
/**
 * 好礼
 * @return
 */	
@Action(value="/haoli",results={@Result(name="success",location="/content/article/haoli.ftl",type="freemarker")})
public String haoli(){
return SUCCESS;
}
	/**
	 * 关于金贷通中的项目介绍
	 * @return
	 */
	@Action(value="/aboutProgram",results={@Result(name="success",location="/content/article/about_program.ftl",type="freemarker")})
	public String aboutProgram(){
		
		Map<String,Object> cmap = new HashMap<String,Object>();
		if(StringUtils.isEmpty(sign)){
			sign = "project_introduction";
		}
		cmap.put("sign", sign);
		articleCategory = articleService.getArticleCategory(cmap);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(StringUtils.isEmpty(sign)||"project_introduction".equals(sign)){
			String[] strs = {"xmjs_the_pledge","xmjs_assignment_of_debt"};
			map.put("signs", strs);
		} else {
			String[] strs = {sign};
			map.put("signs", strs);
		}
		
		pager.setPageSize(20);
		map.put("pager", pager);
		pager = articleService.getArticlePagerBySignMany(pager, map);
		return SUCCESS;
	}
	
	/**
	 * 项目介绍
	 * @return
	 */
	@Action(value="/introduction",results={@Result(name="success",location="/content/article/project_introduction.ftl",type="freemarker")})
	public String introduction(){
		if(StringUtils.isNotEmpty(sign)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("sign", sign);
			articleCategory = articleService.getArticleCategory(map);
			if(articleCategory == null){
				addActionError("参数错误!");
				return "error_ftl";
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 线下配对
	 * @return
	 */
	
	@Action(value="/matching",results={@Result(name="success",location="/content/article/off_line_matching.ftl",type="freemarker")})
	public String matching(){
		Map<String,Object> map = new HashMap<String,Object>();
		pager.setPageSize(5);
		//成功案例
		map.put("sign", "offline_matching");//线下配对-仅用于获取描述
		articleCategory = articleService.getArticleCategory(map);
		map.put("sign", "successful_case");//成功安全-获取列表页
		pager = articleService.getArticlePagerBySign(pager, map);
		return SUCCESS;
	}
	
	/**
	 * 投资中心
	 * @return
	 */
	@Action(value="/inverstment",results={@Result(name="success",location="/content/article/investment_center.ftl",type="freemarker")})
	public String inverstment(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sign", sign);
		articleCategory = articleService.getArticleCategory(map);
		if(articleCategory == null){
			addActionError("参数错误!");
			return "error_ftl";
		}
		articleCategoryList = articleService.getChildArticleCategoryByIdList(articleCategory.getId());
		return SUCCESS;
	}
	
	@Action(value="/hongbao",results={@Result(name="success", type="freemarker",location="/content/article/hongbao.ftl")})
	public String hongbao() {
		return SUCCESS;
	}
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


	public String getEnumKey() {
		return enumKey;
	}


	public void setEnumKey(String enumKey) {
		this.enumKey = enumKey;
	}


	
	
	
	
	
}
