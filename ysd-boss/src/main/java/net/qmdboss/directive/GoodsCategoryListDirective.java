package net.qmdboss.directive;

import freemarker.core.Environment;
import freemarker.template.*;
import net.qmdboss.entity.GoodsCategory;
import net.qmdboss.service.GoodsCategoryService;
import net.qmdboss.util.DirectiveUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component("goodsCategoryListDirective")
public class GoodsCategoryListDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "goods_category_list";
	private static final String PARENT_ID_PARAMETER_NAME = "parent_id";
	private static final String PARENT_SIGN_PARAMETER_NAME = "parent_sign";
	private static final String COUNT_PARAMETER_NAME = "count";
	private static final String IS_CONTAIN_CHILDREN_PARAMETER_NAME = "isContainChildren";
	private static final Boolean DEFAULT_IS_CONTAIN_CHILDREN = false;
	
	@Resource(name = "goodsCategoryServiceImpl")
	private GoodsCategoryService goodsCategoryService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer parentId = DirectiveUtil.getIntegerParameter(PARENT_ID_PARAMETER_NAME, params);
		String parentSign = DirectiveUtil.getStringParameter(PARENT_SIGN_PARAMETER_NAME, params);
		Integer count = DirectiveUtil.getIntegerParameter(COUNT_PARAMETER_NAME, params);
		Boolean isContainChildren = DirectiveUtil.getBooleanParameter(IS_CONTAIN_CHILDREN_PARAMETER_NAME, params);
		
		if (isContainChildren == null) {
			isContainChildren = DEFAULT_IS_CONTAIN_CHILDREN;
		}
		
		GoodsCategory goodsCategory = null;
		List<GoodsCategory> goodsCategoryList = null;
		if ( parentId != null) {
			goodsCategory = goodsCategoryService.load(parentId);
		} else if (StringUtils.isNotEmpty(parentSign)) {
			goodsCategory = goodsCategoryService.getGoodsCategoryBySign(parentSign);
		}
		goodsCategoryList = goodsCategoryService.getChildrenGoodsCategoryList(goodsCategory, isContainChildren, count);
		
		if (body != null && goodsCategoryList != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(goodsCategoryList);
			}
			body.render(env.getOut());
		}
	}

}