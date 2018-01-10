package com.qmd.directive;

import com.qmd.mode.util.Listing;
import com.qmd.service.util.ListingService;
import com.qmd.util.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 数据配置 查询列表数据
 * @author Xsf
 *
 */
@Component("listingChildListDirective")
public class ListingChildListDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "listing_childlist";
	private static final String SIGN_PARAMETER_NAME = "sign";
//	private static final String COUNT_PARAMETER_NAME = "count";
	
	@Resource
	private ListingService listingService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String sign = DirectiveUtil.getStringParameter(SIGN_PARAMETER_NAME, params);
//		Integer count = DirectiveUtil.getIntegerParameter(COUNT_PARAMETER_NAME, params);
		
		List<Listing> listingList = null;
		if (StringUtils.isNotEmpty(sign)) {
			 listingList = listingService.getChildListingBySignList(sign);
		}
		
		
		
		if (body != null && listingList != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(listingList);
			}
			body.render(env.getOut());
		}
	}

}