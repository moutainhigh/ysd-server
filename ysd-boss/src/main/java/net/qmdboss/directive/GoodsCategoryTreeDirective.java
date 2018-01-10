package net.qmdboss.directive;

import freemarker.core.Environment;
import freemarker.template.*;
import net.qmdboss.entity.GoodsCategory;
import net.qmdboss.service.GoodsCategoryService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component("goodsCategoryTreeDirective")
public class GoodsCategoryTreeDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "goods_category_tree";
	
	@Resource(name = "goodsCategoryServiceImpl")
	private GoodsCategoryService goodsCategoryService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<GoodsCategory> goodsCategoryTree = goodsCategoryService.getGoodsCategoryTree();
		if (body != null && goodsCategoryTree != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(goodsCategoryTree);
			}
			body.render(env.getOut());
		}
	}

}