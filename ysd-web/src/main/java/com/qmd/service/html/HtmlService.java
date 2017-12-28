package com.qmd.service.html;

import com.qmd.mode.article.Article;
import com.qmd.util.CommonMess;

import javax.servlet.ServletContextEvent;
import java.util.Map;

/**
 * Service接口 - 生成静态
 */

public interface HtmlService {
	
	/**
	 * 根据Freemarker模板文件路径、Map数据生成HTML
	 * 
	 * @param templatePath
	 *            Freemarker模板文件路径
	 *            
	 * @param htmlPath
	 *            生成HTML路径
	 * 
	 * @param data
	 *            Map数据
	 * 
	 */
	public void buildHtml(String templatePath, String htmlPath, Map<String, Object> data);
	
	
	/**
	 * 生成公共头部HTML
	 * 
	 */
	public void buildHeaderHtml();
	/**
	 * 生成公共底部
	 */
	public void buildBottomHtml();
	
	/**
	 * 生成404.html
	 */
	public void build404Html();
	
	/**
	 * 根据ID生成文章内容HTML
	 * 
	 * @param id
	 *            文章ID
	 */
	public void buildArticleContentHtml(Integer id);
	
	/**
	 * 根据文章生成文章内容HTML
	 * 
	 * @param article
	 *            文章
	 */
	public void buildArticleContentHtml(Article article);
	
	/**
	 * 生成所有文章内容HTML
	 * 
	 */
	public void buildArticleContentHtml();
	
	/**
	 * 更新Setting数据
	 */
	public void replaceSetting();
	

	/**
	 * 加载Setting数据
	 */
	public void replaceSetting(ServletContextEvent sce);
	
	
	/**
	 * 生成公共数据
	 * 
	 */
	public CommonMess buildCommonMess();
	
}