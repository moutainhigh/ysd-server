package com.qmd.directive;

import com.qmd.service.util.ListingService;
import com.qmd.util.DirectiveUtil;
import com.qmd.util.NumberUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 根据父级sign 和子级keyValue查询子级name
 * 
 * @author Xsf
 * 
 */
@Component("listingKeyValueDirective")
public class ListingKeyValueDirective implements TemplateDirectiveModel {

	public static final String TAG_NAME = "listing_keyValue";
	private static final String SIGN_PARAMETER_NAME = "sign";
	private static final String MOLD_PARAMETER_NAME = "mold";
	private static final String POINT_PARAMETER_NAME = "point";

	@Resource
	private ListingService listingService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String sign = DirectiveUtil.getStringParameter(SIGN_PARAMETER_NAME,
				params);
		String mold = DirectiveUtil.getStringParameter(MOLD_PARAMETER_NAME,
				params);
		Integer point = DirectiveUtil.getIntegerParameter(POINT_PARAMETER_NAME,
				params);

		String str = null;
		if (StringUtils.isNotEmpty(sign)) {
			str = listingService.getKeyValue(sign);
		}
		if (str != null) {

			if ("N".equals(mold) || "NC".equals(mold)) {
				if (point == null)
					point = 0;
				BigDecimal d = new BigDecimal(str);
				d = d.setScale(point, BigDecimal.ROUND_HALF_UP);
				
				if ("NC".equals(mold) && d.intValue() >= 1000) {
					str = NumberUtil.formatNumber(d.doubleValue(),point);
				} else {
					str = d.toString();
				}
			}
		}
		if (body != null && str != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(str);
			}
			body.render(env.getOut());
		}
	}

}