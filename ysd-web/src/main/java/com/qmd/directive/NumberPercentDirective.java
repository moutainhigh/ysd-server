package com.qmd.directive;

import com.qmd.util.CommonUtil;
import com.qmd.util.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 显示百分号
 */
@Component("numberPercentDirective")
public class NumberPercentDirective implements TemplateDirectiveModel {

	public static final String TAG_NAME = "numberPercent";
	private static final String APR_PARAMETER_NAME = "val";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String apr = DirectiveUtil.getStringParameter(APR_PARAMETER_NAME,
				params);
		double val = 0;
		if (apr != null && !"".equals(apr)) {
			val = Double.valueOf(apr) * 100;
		}
		BigDecimal bd = CommonUtil.setPriceScale(BigDecimal.valueOf(val));

		String ret = bd.toString()+"%";

		if (body != null && ret != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(ret);
			}
			body.render(env.getOut());
		}
	}

}