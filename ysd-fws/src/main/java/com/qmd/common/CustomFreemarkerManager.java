package com.qmd.common;

import com.qmd.directive.*;
import com.qmd.util.SpringUtil;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import javax.servlet.ServletContext;
import java.util.Locale;
 
/** 
 * @author: jetyou@foxmail.com 
 * @date: 2011-8-22 
 * @time: 16:53:43 
 * @desc: 增加自定义业务标签，此list中的string必须继承于TemplateDirectiveModel，并且配置在SPRING上下文中 
 */ 
public class CustomFreemarkerManager extends FreemarkerManager { 
 
    protected Configuration createConfiguration(ServletContext servletContext) throws TemplateException { 
		        Configuration config = super.createConfiguration(servletContext);
		        config.setEncoding(Locale.CHINA, "UTF-8");
				
				SubstringMethod substringMethod = (SubstringMethod) SpringUtil.getBean("substringMethod");
				SubstringWordMethod substringWordMethod = (SubstringWordMethod) SpringUtil.getBean("substringWordMethod");
				PaginationDirective paginationDirective = (PaginationDirective) SpringUtil.getBean("paginationDirective");
				ListingChildListDirective listingChildListDirective = (ListingChildListDirective) SpringUtil.getBean("listingChildListDirective");
				ListingChildNameDirective listingChildNameDirective = (ListingChildNameDirective) SpringUtil.getBean("listingChildNameDirective");
				OverdueMethod overdueMethod = (OverdueMethod) SpringUtil.getBean("overdueMethod");
				BorrowNubbleDirective borrowNubbleDirective = (BorrowNubbleDirective) SpringUtil.getBean("borrowNubbleDirective");
				BorrowTopDirective borrowTopDirective = (BorrowTopDirective) SpringUtil.getBean("borrowTopDirective");
				CovertMethod covertMethod = (CovertMethod) SpringUtil.getBean("covertMethod");
				PageFlipDirective pageFlipDirective = (PageFlipDirective) SpringUtil.getBean("pageFlipDirective");
				AprOfYearDirective aprOfYearDirective = (AprOfYearDirective) SpringUtil.getBean("aprOfYearDirective");
				ImageUrlDecodeDirective imageUrlDecodeDirective = (ImageUrlDecodeDirective) SpringUtil.getBean("imageUrlDecodeDirective");				
				BorrowStatusDirective borrowStatusDirective = (BorrowStatusDirective) SpringUtil.getBean("borrowStatusDirective");
				RateDirectiveByStep rateDirectiveByStep = (RateDirectiveByStep) SpringUtil.getBean("rateDirectiveByStep");				
				DigitUppercaseDirective digitUppercaseDirective = (DigitUppercaseDirective)SpringUtil.getBean("digitUppercaseDirective");
				ListingSignDecimalDirective listingSignDecimalDirective = (ListingSignDecimalDirective)SpringUtil.getBean("listingSignDecimalDirective");				
				DeferApplyDirective deferApplyDirective = (DeferApplyDirective)SpringUtil.getBean("deferApplyDirective");				
				DeferMoneyDirective deferMoneyDirective = (DeferMoneyDirective)SpringUtil.getBean("deferMoneyDirective");
				NumberPercentDirective numberPercentDirective = (NumberPercentDirective) SpringUtil.getBean("numberPercentDirective");
				
				
				
				config.setSharedVariable(SubstringMethod.TAG_NAME, substringMethod);
				config.setSharedVariable(SubstringWordMethod.TAG_NAME, substringWordMethod);				
				config.setSharedVariable(PaginationDirective.TAG_NAME, paginationDirective);
				config.setSharedVariable(ListingChildListDirective.TAG_NAME, listingChildListDirective);
				config.setSharedVariable(ListingChildNameDirective.TAG_NAME, listingChildNameDirective);
				config.setSharedVariable(OverdueMethod.TAG_NAME, overdueMethod);				
				config.setSharedVariable(BorrowNubbleDirective.TAG_NAME, borrowNubbleDirective);
				config.setSharedVariable(BorrowTopDirective.TAG_NAME, borrowTopDirective);
				config.setSharedVariable(CovertMethod.TAG_NAME, covertMethod);
				config.setSharedVariable(PageFlipDirective.TAG_NAME, pageFlipDirective);
				config.setSharedVariable(AprOfYearDirective.TAG_NAME, aprOfYearDirective);
				config.setSharedVariable(ImageUrlDecodeDirective.TAG_NAME, imageUrlDecodeDirective);
				config.setSharedVariable(BorrowStatusDirective.TAG_NAME, borrowStatusDirective);
				config.setSharedVariable(RateDirectiveByStep.TAG_NAME, rateDirectiveByStep);
				config.setSharedVariable(DigitUppercaseDirective.TAG_NAME, digitUppercaseDirective);
				config.setSharedVariable(ListingSignDecimalDirective.TAG_NAME, listingSignDecimalDirective);
				config.setSharedVariable(DeferApplyDirective.TAG_NAME, deferApplyDirective);
				config.setSharedVariable(DeferMoneyDirective.TAG_NAME, deferMoneyDirective);
				config.setSharedVariable(NumberPercentDirective.TAG_NAME, numberPercentDirective);
				servletContext.setAttribute(CONFIG_SERVLET_CONTEXT_KEY, config);
	      config.setWhitespaceStripping(true);
        return config; 
    } 
 
 
} 
