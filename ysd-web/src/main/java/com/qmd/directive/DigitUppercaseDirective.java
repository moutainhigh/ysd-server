package com.qmd.directive;

import com.qmd.util.CommonUtil;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 数字金额大写转换
 */
@Component("digitUppercaseDirective")
public class DigitUppercaseDirective implements TemplateMethodModel {

	public static final String TAG_NAME = "digitUppercase";

	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments.size() == 1) {
			String str = arguments.get(0).toString();
			Double d = Double.valueOf(str);
			String ret = CommonUtil.digitUppercase(d);
			return new SimpleScalar(ret);
		} else {
			throw new TemplateModelException("Wrong arguments");
		}
	}

}