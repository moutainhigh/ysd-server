package com.qmd.directive;

import com.qmd.service.util.ListingService;
import com.qmd.util.CommonUtil;
import com.qmd.util.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component("listingSignDecimalDirective")
public class ListingSignDecimalDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "listing_sign_decimal";
	private static final String SIGN_PARAMETER_NAME = "sign";
	private static final String SCALE_PARAMETER_NAME = "scale";
	
	
	@Resource
	private ListingService listingService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String sign = DirectiveUtil.getStringParameter(SIGN_PARAMETER_NAME, params);
		int scale = DirectiveUtil.getIntegerParameter(SCALE_PARAMETER_NAME, params);
		Map<String,Object> map= new HashMap<String ,Object>();
		map.put("sign", sign);
		BigDecimal ret = BigDecimal.ZERO;
		if (StringUtils.isNotEmpty(sign) ) {
			String val = listingService.getKeyValue(sign);
			if(val!=null && StringUtils.isNotEmpty(val)) {
				ret = CommonUtil.setPriceScale2BigDecimal(val,scale);
			}
		}
		if (body != null && ret != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(ret);
			}
			body.render(env.getOut());
		}
	}

}