package com.qmd.directive;

import com.qmd.util.DirectiveUtil;
import com.qmd.util.Pager;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * 分页
 *
 */
@Component("pageFlipDirective")
public class PageFlipDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "pageFlip";
	// 传入参数
	private static final String PAGER_PARAMETER_NAME = "pager";
	
	// 返回参数
	private static final String TOTAL_COUNT_PARAMETER_NAME = "totalCount";// 总条数
	private static final String PAGE_NUMBER_PARAMETER_NAME = "pageNumber";// 当前页码
	private static final String PAGE_SIZE_PARAMETER_NAME = "pageSize";// 单页条数
	private static final String PAGE_COUNT_PARAMETER_NAME = "pageCount";// 总页数
	private static final String PAGE_ITEM_PARAMETER_NAME = "pageItem";// 展现页码

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Pager pager = (Pager) DirectiveUtil.getObjectParameter(PAGER_PARAMETER_NAME, params);
		
		Integer totalCount = null;// 总条数
		Integer pageCount = null;// 总页数
		Integer pageSize = null;// 单页条数
		Integer pageNumber = null;//当前页数
	
		Map<String, String> pageItem = new LinkedHashMap<String, String>();
		Integer startPageItemNumber = null;
		Integer endPageItemNumber = null;
		
		if (pager != null) {
			totalCount = pager.getTotalCount();
			pageCount = pager.getPageCount();
			pageSize = pager.getPageSize();
			pageNumber = pager.getPageNumber();
			
			if (pageCount <= 5) {// 总页数小于11，显示全部
				startPageItemNumber = 1;
				endPageItemNumber = pageCount;
			} else if(pageNumber <=3){// 当前页未头部6条，显示前面11条
				startPageItemNumber = 1;
				endPageItemNumber = 5;
			} else if(pageCount - pageNumber <3){// 当前页为尾部6条，显示最后11条
				startPageItemNumber = pageCount-4;
				endPageItemNumber = pageCount;
			} else {//属于显示中间项(头部，尾部有不显示的项)
				startPageItemNumber = pageNumber - 2;
				endPageItemNumber = pageNumber + 2;
			}
			
			for (int i = startPageItemNumber; i <= endPageItemNumber; i ++) {
				pageItem.put(String.valueOf(i), String.valueOf(i));
			}
		} else {
			return;
		}
		
		if (body != null) {
			
			TemplateModel sourceTotalCount = env.getVariable(TOTAL_COUNT_PARAMETER_NAME);
			TemplateModel sourcePageNumber = env.getVariable(PAGE_NUMBER_PARAMETER_NAME);
			TemplateModel sourcePageSize = env.getVariable(PAGE_SIZE_PARAMETER_NAME);
			TemplateModel sourcePageCount = env.getVariable(PAGE_COUNT_PARAMETER_NAME);
			TemplateModel sourcePageItem = env.getVariable(PAGE_ITEM_PARAMETER_NAME);
			
			
			env.setVariable(TOTAL_COUNT_PARAMETER_NAME, new SimpleNumber(totalCount));
			env.setVariable(PAGE_NUMBER_PARAMETER_NAME, new SimpleNumber(pageNumber));
			env.setVariable(PAGE_SIZE_PARAMETER_NAME, new SimpleNumber(pageSize));
			env.setVariable(PAGE_COUNT_PARAMETER_NAME, new SimpleNumber(pageCount));
			env.setVariable(PAGE_ITEM_PARAMETER_NAME, new SimpleHash(pageItem));
			
			body.render(env.getOut());
			
			env.setVariable(TOTAL_COUNT_PARAMETER_NAME, sourceTotalCount);
			env.setVariable(PAGE_NUMBER_PARAMETER_NAME, sourcePageNumber);
			env.setVariable(PAGE_SIZE_PARAMETER_NAME, sourcePageSize);
			env.setVariable(PAGE_COUNT_PARAMETER_NAME, sourcePageCount);
			env.setVariable(PAGE_ITEM_PARAMETER_NAME, sourcePageItem);
		}
	}

}