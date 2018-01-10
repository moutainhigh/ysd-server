package net.qmdboss.directive;

import freemarker.core.Environment;
import freemarker.template.*;
import net.qmdboss.service.ListingService;
import net.qmdboss.util.DirectiveUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
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

		String name=null;
		if (StringUtils.isNotEmpty(sign) && StringUtils.isNotEmpty(keyValue)) {
			name = listingService.findChildListingByKeyValue(sign,keyValue);
		}else{
			name= listingService.findChildListingByKeyValue("",keyValue);
		}
		if (body != null && name != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(name);
			}
			body.render(env.getOut());
		}
	}

}