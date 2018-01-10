package net.qmdboss.directive;

import freemarker.core.Environment;
import freemarker.template.*;
import net.qmdboss.entity.Goods;
import net.qmdboss.entity.GoodsCategory;
import net.qmdboss.service.GoodsCategoryService;
import net.qmdboss.service.GoodsService;
import net.qmdboss.util.DirectiveUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component("goodsListDirective")
public class GoodsListDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "goods_list";
	private static final String GOODS_CATEGORY_ID_PARAMETER_NAME = "goods_category_id";
	private static final String GOODS_CATEGORY_SIGN_PARAMETER_NAME = "goods_category_sign";
	private static final String TYPE_PARAMETER_NAME = "type";
	private static final String COUNT_PARAMETER_NAME = "count";
	private static final String IS_CONTAIN_CHILDREN_PARAMETER_NAME = "isContainChildren";
	private static final Boolean DEFAULT_IS_CONTAIN_CHILDREN = true;
	
	@Resource(name = "goodsCategoryServiceImpl")
	private GoodsCategoryService goodsCategoryService;
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer goodsCategoryId = DirectiveUtil.getIntegerParameter(GOODS_CATEGORY_ID_PARAMETER_NAME, params);
		String goodsCategorySign = DirectiveUtil.getStringParameter(GOODS_CATEGORY_SIGN_PARAMETER_NAME, params);
		String type = DirectiveUtil.getStringParameter(TYPE_PARAMETER_NAME, params);
		Integer count = DirectiveUtil.getIntegerParameter(COUNT_PARAMETER_NAME, params);
		Boolean isContainChildren = DirectiveUtil.getBooleanParameter(IS_CONTAIN_CHILDREN_PARAMETER_NAME, params);
		
		if (isContainChildren == null) {
			isContainChildren = DEFAULT_IS_CONTAIN_CHILDREN;
		}
		
		GoodsCategory goodsCategory = null;
		List<Goods> goodsList = null;
		if ( goodsCategoryId != null) {
			goodsCategory = goodsCategoryService.load(goodsCategoryId);
		} else if (StringUtils.isNotEmpty(goodsCategorySign)) {
			goodsCategory = goodsCategoryService.getGoodsCategoryBySign(goodsCategorySign);
		}
		goodsList = goodsService.getGoodsList(goodsCategory, type, isContainChildren, count);
		
		if (body != null && goodsList != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(goodsList);
			}
			body.render(env.getOut());
		}
	}

}