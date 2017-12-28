package net.qmdboss.directive;

import freemarker.core.Environment;
import freemarker.template.*;
import net.qmdboss.entity.Article;
import net.qmdboss.entity.ArticleCategory;
import net.qmdboss.service.ArticleCategoryService;
import net.qmdboss.service.ArticleService;
import net.qmdboss.util.DirectiveUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component("articleListDirective")
public class ArticleListDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "article_list";
	private static final String ARTICLE_CATEGORY_ID_PARAMETER_NAME = "article_category_id";
	private static final String ARTICLE_CATEGORY_SIGN_PARAMETER_NAME = "article_category_sign";
	private static final String TYPE_PARAMETER_NAME = "type";
	private static final String COUNT_PARAMETER_NAME = "count";
	private static final String IS_CONTAIN_CHILDREN_PARAMETER_NAME = "isContainChildren";
	private static final Boolean DEFAULT_IS_CONTAIN_CHILDREN = true;
	
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;
	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer articleCategoryId = DirectiveUtil.getIntegerParameter(ARTICLE_CATEGORY_ID_PARAMETER_NAME, params);
		String articleCategorySign = DirectiveUtil.getStringParameter(ARTICLE_CATEGORY_SIGN_PARAMETER_NAME, params);
		String type = DirectiveUtil.getStringParameter(TYPE_PARAMETER_NAME, params);
		Integer count = DirectiveUtil.getIntegerParameter(COUNT_PARAMETER_NAME, params);
		Boolean isContainChildren = DirectiveUtil.getBooleanParameter(IS_CONTAIN_CHILDREN_PARAMETER_NAME, params);
		
		if (isContainChildren == null) {
			isContainChildren = DEFAULT_IS_CONTAIN_CHILDREN;
		}
		
		ArticleCategory articleCategory = null;
		List<Article> articleList = null;
		if (articleCategoryId != null) {
			articleCategory = articleCategoryService.load(articleCategoryId);
		} else if (StringUtils.isNotEmpty(articleCategorySign)) {
			articleCategory = articleCategoryService.getArticleCategoryBySign(articleCategorySign);
		}
		articleList = articleService.getArticleList(articleCategory, type, isContainChildren, count);
		
		if (body != null && articleList != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(articleList);
			}
			body.render(env.getOut());
		}
	}

}