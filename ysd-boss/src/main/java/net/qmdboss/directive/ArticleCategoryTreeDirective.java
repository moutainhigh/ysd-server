package net.qmdboss.directive;

import freemarker.core.Environment;
import freemarker.template.*;
import net.qmdboss.entity.ArticleCategory;
import net.qmdboss.service.ArticleCategoryService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component("articleCategoryTreeDirective")
public class ArticleCategoryTreeDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "article_category_tree";
	
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<ArticleCategory> articleCategoryTree = articleCategoryService.getArticleCategoryTree();
		if (body != null && articleCategoryTree != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(articleCategoryTree);
			}
			body.render(env.getOut());
		}
	}

}