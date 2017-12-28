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
//    	freemarker.template.Configuration config = (freemarker.template.Configuration) servletContext.getAttribute(CONFIG_SERVLET_CONTEXT_KEY);
//		
//	      if(config==null){
//	    	  try {
//					config = super.createConfiguration(servletContext);
//				} catch (TemplateException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				// config.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
				
				SubstringMethod substringMethod = (SubstringMethod) SpringUtil.getBean("substringMethod");
				SubstringWordMethod substringWordMethod = (SubstringWordMethod) SpringUtil.getBean("substringWordMethod");
				
//				CheckboxDirective checkboxDirective = (CheckboxDirective) SpringUtil.getBean("checkboxDirective");
				PaginationDirective paginationDirective = (PaginationDirective) SpringUtil.getBean("paginationDirective");
//				CommentListDirective commentListDirective = (CommentListDirective) SpringUtil.getBean("commentListDirective");
				ListingChildListDirective listingChildListDirective = (ListingChildListDirective) SpringUtil.getBean("listingChildListDirective");
				ListingChildNameDirective listingChildNameDirective = (ListingChildNameDirective) SpringUtil.getBean("listingChildNameDirective");
				OverdueMethod overdueMethod = (OverdueMethod) SpringUtil.getBean("overdueMethod");
				ArticleContentDirective articleContentDirective = (ArticleContentDirective) SpringUtil.getBean("articleContentDirective");
				BorrowNubbleDirective borrowNubbleDirective = (BorrowNubbleDirective) SpringUtil.getBean("borrowNubbleDirective");
				BorrowTopDirective borrowTopDirective = (BorrowTopDirective) SpringUtil.getBean("borrowTopDirective");
				UserAccountDetailDirective userAccountDetailDirective = (UserAccountDetailDirective)SpringUtil.getBean("userAccountDetailDirective");
				UserAccountDetailCountDirective userAccountDetailCountDirective = (UserAccountDetailCountDirective)SpringUtil.getBean("userAccountDetailCountDirective");
				UserAccountDetailCountMulitDirective userAccountDetailCountMulitDirective = (UserAccountDetailCountMulitDirective)SpringUtil.getBean("userAccountDetailCountMulitDirective");
				CovertMethod covertMethod = (CovertMethod) SpringUtil.getBean("covertMethod");
				ArticleCategoryDirective articleCategoryDirective = (ArticleCategoryDirective) SpringUtil.getBean("articleCategoryDirective");
				PageFlipDirective pageFlipDirective = (PageFlipDirective) SpringUtil.getBean("pageFlipDirective");
				//@author zdl 新的分页
				PageFlipNewDirective pageFlipNewDirective = (PageFlipNewDirective) SpringUtil.getBean("pageFlipNewDirective");
				AprOfYearDirective aprOfYearDirective = (AprOfYearDirective) SpringUtil.getBean("aprOfYearDirective");
				
				ImageUrlDecodeDirective imageUrlDecodeDirective = (ImageUrlDecodeDirective) SpringUtil.getBean("imageUrlDecodeDirective");
				
				NumberPercentDirective numberPercentDirective = (NumberPercentDirective) SpringUtil.getBean("numberPercentDirective");
				
				ListingKeyValueDirective listingKeyValueDirective = (ListingKeyValueDirective) SpringUtil.getBean("listingKeyValueDirective");
				NumberFormatDirective numberFormatDirective = (NumberFormatDirective) SpringUtil.getBean("numberFormatDirective");
				DigitUppercaseDirective digitUppercaseDirective = (DigitUppercaseDirective) SpringUtil.getBean("digitUppercaseDirective");
				
				
				
//				FriendLinkListDirective friendLinkListDirective = (FriendLinkListDirective) SpringUtil.getBean("friendLinkListDirective");
//				InstantMessagingListDirective instantMessagingListDirective = (InstantMessagingListDirective) SpringUtil.getBean("instantMessagingListDirective");
//				ArticleCategoryListDirective articleCategoryListDirective = (ArticleCategoryListDirective) SpringUtil.getBean("articleCategoryListDirective");
//				ArticleCategoryTreeDirective articleCategoryTreeDirective = (ArticleCategoryTreeDirective) SpringUtil.getBean("articleCategoryTreeDirective");
//				ArticleListDirective articleListDirective = (ArticleListDirective) SpringUtil.getBean("articleListDirective");
//				GoodsCategoryListDirective goodsCategoryListDirective = (GoodsCategoryListDirective) SpringUtil.getBean("goodsCategoryListDirective");
//				GoodsCategoryTreeDirective goodsCategoryTreeDirective = (GoodsCategoryTreeDirective) SpringUtil.getBean("goodsCategoryTreeDirective");
//				GoodsListDirective goodsListDirective = (GoodsListDirective) SpringUtil.getBean("goodsListDirective");
//				
				config.setSharedVariable(SubstringMethod.TAG_NAME, substringMethod);
				config.setSharedVariable(SubstringWordMethod.TAG_NAME, substringWordMethod);
				
//				config.setSharedVariable(CheckboxDirective.TAG_NAME, checkboxDirective);
				config.setSharedVariable(PaginationDirective.TAG_NAME, paginationDirective);
//				config.setSharedVariable(CommentListDirective.TAG_NAME, commentListDirective);
				config.setSharedVariable(ListingChildListDirective.TAG_NAME, listingChildListDirective);
				config.setSharedVariable(ListingChildNameDirective.TAG_NAME, listingChildNameDirective);
				config.setSharedVariable(OverdueMethod.TAG_NAME, overdueMethod);
				config.setSharedVariable(ArticleContentDirective.TAG_NAME, articleContentDirective);
				config.setSharedVariable(BorrowNubbleDirective.TAG_NAME, borrowNubbleDirective);
				config.setSharedVariable(BorrowTopDirective.TAG_NAME, borrowTopDirective);
				
				
				config.setSharedVariable(UserAccountDetailDirective.TAG_NAME, userAccountDetailDirective);
				config.setSharedVariable(UserAccountDetailCountDirective.TAG_NAME, userAccountDetailCountDirective);
				config.setSharedVariable(UserAccountDetailCountMulitDirective.TAG_NAME, userAccountDetailCountMulitDirective);
				config.setSharedVariable(CovertMethod.TAG_NAME, covertMethod);
				config.setSharedVariable(ArticleCategoryDirective.TAG_NAME, articleCategoryDirective);
				config.setSharedVariable(PageFlipDirective.TAG_NAME, pageFlipDirective);
				//author zdl 新的分页标签
				config.setSharedVariable(PageFlipNewDirective.TAG_NAME, pageFlipNewDirective);
				config.setSharedVariable(AprOfYearDirective.TAG_NAME, aprOfYearDirective);
				config.setSharedVariable(ImageUrlDecodeDirective.TAG_NAME, imageUrlDecodeDirective);
				config.setSharedVariable(NumberPercentDirective.TAG_NAME, numberPercentDirective);
				config.setSharedVariable(ListingKeyValueDirective.TAG_NAME, listingKeyValueDirective);
				config.setSharedVariable(NumberFormatDirective.TAG_NAME, numberFormatDirective);
				config.setSharedVariable(DigitUppercaseDirective.TAG_NAME, digitUppercaseDirective);
				
				
				
				
				
//				
				
//				config.setSharedVariable(FriendLinkListDirective.TAG_NAME, friendLinkListDirective);
//				config.setSharedVariable(InstantMessagingListDirective.TAG_NAME, instantMessagingListDirective);
//				config.setSharedVariable(ArticleCategoryListDirective.TAG_NAME, articleCategoryListDirective);
//				config.setSharedVariable(ArticleCategoryTreeDirective.TAG_NAME, articleCategoryTreeDirective);
//				config.setSharedVariable(ArticleListDirective.TAG_NAME, articleListDirective);
//				config.setSharedVariable(GoodsCategoryListDirective.TAG_NAME, goodsCategoryListDirective);
//				config.setSharedVariable(GoodsCategoryTreeDirective.TAG_NAME, goodsCategoryTreeDirective);
//				config.setSharedVariable(GoodsListDirective.TAG_NAME, goodsListDirective);
				
				servletContext.setAttribute(CONFIG_SERVLET_CONTEXT_KEY, config);
//	      }
	      config.setWhitespaceStripping(true);
        return config; 
    } 
 
 
} 
