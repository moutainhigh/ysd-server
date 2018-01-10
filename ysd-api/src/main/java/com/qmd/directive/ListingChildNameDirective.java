package com.qmd.directive;

import com.qmd.service.util.ListingService;
import com.qmd.util.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 根据父级sign 和子级keyValue查询子级name
 * @author Xsf
 *
 */
@Component("listingChildNameDirective")
public class ListingChildNameDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "listing_childname";
	private static final String SIGN_PARAMETER_NAME = "sign";
	private static final String KEY_VALUE_PARAMETER_NAME = "key_value";
	
	@Resource
	private ListingService listingService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String sign = DirectiveUtil.getStringParameter(SIGN_PARAMETER_NAME, params);
		String keyValue = DirectiveUtil.getStringParameter(KEY_VALUE_PARAMETER_NAME, params);
		Map<String,Object> map= new HashMap<String ,Object>();
		map.put("sign", sign);
		map.put("keyValue", keyValue);
		String name=null;
		if (StringUtils.isNotEmpty(sign) && StringUtils.isNotEmpty(keyValue)) {
			name = listingService.findChildListingByKeyValue(map);
		}
		if (body != null && name != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(name);
			}
			body.render(env.getOut());
		}
	}

}