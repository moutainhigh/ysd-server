package com.qmd.directive;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("covertMethod")
public class CovertMethod implements TemplateMethodModel {

	
	public static final String TAG_NAME = "covertString";

	@SuppressWarnings("unchecked")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments.size() == 1) {
			String str = arguments.get(0).toString();
			return new SimpleScalar(covertString(str));
		} else {
			throw new TemplateModelException("Wrong arguments");
		}
	}
	
	/**
	 * 去除字符串中的html标签
	 * @param content
	 * @return
	 */
	@SuppressWarnings("static-access")
	public  static String covertString(String content) {
			if(content==null){
				content="";
			}
			char[] temp = content.toCharArray();
			int start = 0;
			int end = 0;
			for (int count = 0; count < temp.length; count++) {
				if (temp[count] == '<') {
					start = count;
				}
				if (temp[count] == '>') {
					end = count;
					for (; start <= end; start++) {
						temp[start] = '\0';
					}
				}
			}
			return new String(temp).replaceAll("\0", "").replaceAll("&nbsp;", "").replaceAll("&nbsp", "")
				.replaceAll("&quot;", "").replaceAll("&copy;", "").replaceAll(
						"&apos;", "").replaceAll("&ldquo;", "").replaceAll(
						"&rdquo;", "").replaceAll("\n", "").replaceAll("\r", "").trim();
		}
}
