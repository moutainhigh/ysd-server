package com.qmd.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HasKeyWord{
	
	public static boolean hasKeyWord(ServletContext servletContext,String str){
		String stext =str.replaceAll("[^\\u4e00-\\u9fa5]", "");//汉字所在的utf-8编码范围，此句仅输出汉字，且输出中不包含标点符号，英文字母
		List<String> sensitiveKeyList = getSensitiveKeyWordList(servletContext);
		for(String keyword : sensitiveKeyList){
			if(stext.indexOf(keyword)>-1){
				return true;
			}
		}
		return false;
	}
	
	// 获取敏感词汇集合
	public static  List<String> getSensitiveKeyWordList(ServletContext servletContext) {
		String sensitiveKeyWord = (String) servletContext.getAttribute("qmd.setting.sensitiveKeyWord");
		return StringUtils.isNotEmpty(sensitiveKeyWord) ? Arrays.asList(sensitiveKeyWord.split(",")) : new ArrayList<String>();
	}
}
