package com.qmd.directive;

import com.qmd.mode.article.Article;
import com.qmd.service.article.ArticleService;
import com.qmd.util.DirectiveUtil;
import com.qmd.util.Pager;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据分类获取文章列表
 * @author Xsf
 *
 */
@Component("articleContentDirective")
public class ArticleContentDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "article_list";
	private static final String SIGN_PARAMETER_NAME = "sign";
	private static final String COUNT_PARAMETER_NAME = "count";
	
	@Resource
	private ArticleService articleService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String sign = DirectiveUtil.getStringParameter(SIGN_PARAMETER_NAME, params);
		Integer count = DirectiveUtil.getIntegerParameter(COUNT_PARAMETER_NAME, params);
		List<Article> articleList = null;
		Pager pager = new Pager();
		Map<String,Object> map= new HashMap<String ,Object>();
		map.put("sign", sign);
		pager.setThisSize(0);
		pager.setPageSize(count);
		map.put("pager", pager);
		articleList = articleService.getArticleBySign(map);
			
		if (body != null && articleList != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(articleList);
			}
			body.render(env.getOut());
		}
	}
	
}
