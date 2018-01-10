package com.qmd.directive;

import com.qmd.util.DirectiveUtil;
import com.qmd.util.NumberUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 显示数字
 */
@Component("numberFormatDirective")
public class NumberFormatDirective implements TemplateDirectiveModel {

	public static final String TAG_NAME = "numberFormat";
	private static final String VAL_PARAMETER_NAME = "val";
	private static final String MOLD_PARAMETER_NAME = "mold";
	private static final String POINT_PARAMETER_NAME = "point";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String str = DirectiveUtil.getStringParameter(VAL_PARAMETER_NAME,
				params);
		String mold = DirectiveUtil.getStringParameter(MOLD_PARAMETER_NAME,
				params);
		Integer point = DirectiveUtil.getIntegerParameter(POINT_PARAMETER_NAME,
				params);

		if (point == null)
			point = 0;
		BigDecimal d = new BigDecimal(str);
		d.setScale(point, BigDecimal.ROUND_HALF_UP);
		String ret = "";
		if ("C".equals(mold) && d.intValue() >= 1000) {
			ret = NumberUtil.formatNumber(d.doubleValue(), point);
		} else {
			ret = d.toString();
		}

		if (body != null && ret != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(ret);
			}
			body.render(env.getOut());
		}
	}

}