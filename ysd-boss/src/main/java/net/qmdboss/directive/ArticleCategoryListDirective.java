package net.qmdboss.directive;

import freemarker.core.Environment;
import freemarker.template.*;
import net.qmdboss.entity.ArticleCategory;
import net.qmdboss.service.ArticleCategoryService;
import net.qmdboss.util.DirectiveUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component("articleCategoryListDirective")
public class ArticleCategoryListDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "article_category_list";
	private static final String PARENT_ID_PARAMETER_NAME = "parent_id";
	private static final String PARENT_SIGN_PARAMETER_NAME = "parent_sign";
	private static final String COUNT_PARAMETER_NAME = "count";
	private static final String IS_CONTAIN_CHILDREN_PARAMETER_NAME = "isContainChildren";
	private static final Boolean DEFAULT_IS_CONTAIN_CHILDREN = false;
	
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer parentId = DirectiveUtil.getIntegerParameter(PARENT_ID_PARAMETER_NAME, params);
		String parentSign = DirectiveUtil.getStringParameter(PARENT_SIGN_PARAMETER_NAME, params);
		Integer count = DirectiveUtil.getIntegerParameter(COUNT_PARAMETER_NAME, params);
		Boolean isContainChildren = DirectiveUtil.getBooleanParameter(IS_CONTAIN_CHILDREN_PARAMETER_NAME, params);
		
		if (isContainChildren == null) {
			isContainChildren = DEFAULT_IS_CONTAIN_CHILDREN;
		}
		
		ArticleCategory articleCategory = null;
		List<ArticleCategory> articleCategoryList = null;
		if (parentId != null) {
			articleCategory = articleCategoryService.load(parentId);
		} else if (StringUtils.isNotEmpty(parentSign)) {
			articleCategory = articleCategoryService.getArticleCategoryBySign(parentSign);
		}
		articleCategoryList = articleCategoryService.getChildrenArticleCategoryList(articleCategory, isContainChildren, count);
		
		if (body != null && articleCategoryList != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(articleCategoryList);
			}
			body.render(env.getOut());
		}
	}

}