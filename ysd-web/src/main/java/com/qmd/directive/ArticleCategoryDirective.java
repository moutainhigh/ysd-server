package com.qmd.directive;

import com.qmd.mode.article.ArticleCategory;
import com.qmd.service.article.ArticleService;
import com.qmd.util.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据sign获取文章分类
 * @author Xsf
 *
 */
@Component("articleCategoryDirective")
public class ArticleCategoryDirective implements TemplateDirectiveModel {

	public static final String TAG_NAME = "article_category";
	private static final String SIGN_PARAMETER_NAME = "sign";
	
	@Resource
	private ArticleService articleService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String sign = DirectiveUtil.getStringParameter(SIGN_PARAMETER_NAME, params);
		List<ArticleCategory> articleCategory = null;
		Map<String,Object> map= new HashMap<String ,Object>();
		map.put("sign", sign);
		ArticleCategory ac = articleService.getArticleCategory(map);
		if(ac != null){
			articleCategory = articleService.getChildArticleCategoryByIdList(ac.getId());//二级分类列表
		}
		
		if (body != null && articleCategory != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(articleCategory);
			}
			body.render(env.getOut());
		}
	}
}
