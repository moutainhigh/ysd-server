package com.qmd.directive;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 截取字符串(文字）
 *
 */
@Component("substringWordMethod")
public class SubstringWordMethod implements TemplateMethodModel {
	
	public static final String TAG_NAME = "substringWord";

	@SuppressWarnings("unchecked")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments.size() == 2) {
			String str = arguments.get(0).toString();
			Integer length = Integer.valueOf(arguments.get(1).toString());
			return new SimpleScalar(substringWord(str, length, null));
		} else if (arguments.size() == 3) {
			String str = arguments.get(0).toString();
			Integer length = Integer.valueOf(arguments.get(1).toString());
			String moreSuffix = arguments.get(2).toString();
			return new SimpleScalar(substringWord(str, length, moreSuffix));
		} else {
			throw new TemplateModelException("Wrong arguments");
		}
	}
	
	/**
     * 根据字节长度截取字符串前部分
     * 
     * @param str
     *        原字符
     * 
     * @param length
     *        截取字节长度
     * 
     * @param moreSuffix
     *        后缀
     * 
     * @return String
	*/
	@SuppressWarnings("static-access")
	public static String substringWord(String str, int length, String moreSuffix) {
		
		if (str == null) {
			return "";
		}
		String reStr = "";
		
		if(str==null||"".equals(str.trim())) {
			return str;
		}
		
		if (str.length() > length) {
			reStr = str.substring(0,length);
			if (moreSuffix!=null&&!"".equals(moreSuffix)) {
				reStr = reStr + moreSuffix;
			}
		} else {
			reStr = str;
		}
		
		return reStr;
	}

}