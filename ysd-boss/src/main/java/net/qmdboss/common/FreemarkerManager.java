package net.qmdboss.common;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import net.qmdboss.directive.*;
import net.qmdboss.util.SpringUtil;

import javax.servlet.ServletContext;
import java.util.Locale;

public class FreemarkerManager extends org.apache.struts2.views.freemarker.FreemarkerManager {

	protected Configuration createConfiguration(ServletContext servletContext) throws TemplateException {
		Configuration config = super.createConfiguration(servletContext);
		config.setEncoding(Locale.CHINA, "UTF-8");

		SubstringMethod substringMethod = (SubstringMethod) SpringUtil.getBean("substringMethod");
		CheckboxDirective checkboxDirective = (CheckboxDirective) SpringUtil.getBean("checkboxDirective");
		PaginationDirective paginationDirective = (PaginationDirective) SpringUtil.getBean("paginationDirective");
		CommentListDirective commentListDirective = (CommentListDirective) SpringUtil.getBean("commentListDirective");
		NavigationListDirective navigationListDirective = (NavigationListDirective) SpringUtil.getBean("navigationListDirective");
		FriendLinkListDirective friendLinkListDirective = (FriendLinkListDirective) SpringUtil.getBean("friendLinkListDirective");
		InstantMessagingListDirective instantMessagingListDirective = (InstantMessagingListDirective) SpringUtil
				.getBean("instantMessagingListDirective");
		ArticleCategoryListDirective articleCategoryListDirective = (ArticleCategoryListDirective) SpringUtil.getBean("articleCategoryListDirective");
		ArticleCategoryTreeDirective articleCategoryTreeDirective = (ArticleCategoryTreeDirective) SpringUtil.getBean("articleCategoryTreeDirective");
		ArticleListDirective articleListDirective = (ArticleListDirective) SpringUtil.getBean("articleListDirective");
		GoodsCategoryListDirective goodsCategoryListDirective = (GoodsCategoryListDirective) SpringUtil.getBean("goodsCategoryListDirective");
		GoodsCategoryTreeDirective goodsCategoryTreeDirective = (GoodsCategoryTreeDirective) SpringUtil.getBean("goodsCategoryTreeDirective");
		GoodsListDirective goodsListDirective = (GoodsListDirective) SpringUtil.getBean("goodsListDirective");
		ListingChildListDirective listingChildListDirective = (ListingChildListDirective) SpringUtil.getBean("listingChildListDirective");
		ListingChildNameDirective listingChildNameDirective = (ListingChildNameDirective) SpringUtil.getBean("listingChildNameDirective");
		ArticleCategoryChildNameDirective articleCategoryChildNameDirective = (ArticleCategoryChildNameDirective) SpringUtil
				.getBean("articleCategoryChildNameDirective");
		OverdueMethod overdueMethod = (OverdueMethod) SpringUtil.getBean("overdueMethod");
		RechargeConfigDirective rechargeConfigDirective = (RechargeConfigDirective) SpringUtil.getBean("rechargeConfigDirective");
		UserAccountRechargeDirective userAccountRechargeDirective = (UserAccountRechargeDirective) SpringUtil.getBean("userAccountRechargeDirective");
		AprOfYearDirective aprOfYearDirective = (AprOfYearDirective) SpringUtil.getBean("aprOfYearDirective");
		ImageUrlDecodeDirective imageUrlDecodeDirective = (ImageUrlDecodeDirective) SpringUtil.getBean("imageUrlDecodeDirective");
		NumberPercentDirective numberPercentDirective = (NumberPercentDirective) SpringUtil.getBean("numberPercentDirective");
		UserByAgencyDirective userByAgencyDirective = (UserByAgencyDirective) SpringUtil.getBean("userByAgencyDirective");

		config.setSharedVariable(SubstringMethod.TAG_NAME, substringMethod);
		config.setSharedVariable(CheckboxDirective.TAG_NAME, checkboxDirective);
		config.setSharedVariable(PaginationDirective.TAG_NAME, paginationDirective);
		config.setSharedVariable(CommentListDirective.TAG_NAME, commentListDirective);
		config.setSharedVariable(NavigationListDirective.TAG_NAME, navigationListDirective);
		config.setSharedVariable(FriendLinkListDirective.TAG_NAME, friendLinkListDirective);
		config.setSharedVariable(InstantMessagingListDirective.TAG_NAME, instantMessagingListDirective);
		config.setSharedVariable(ArticleCategoryListDirective.TAG_NAME, articleCategoryListDirective);
		config.setSharedVariable(ArticleCategoryTreeDirective.TAG_NAME, articleCategoryTreeDirective);
		config.setSharedVariable(ArticleListDirective.TAG_NAME, articleListDirective);
		config.setSharedVariable(GoodsCategoryListDirective.TAG_NAME, goodsCategoryListDirective);
		config.setSharedVariable(GoodsCategoryTreeDirective.TAG_NAME, goodsCategoryTreeDirective);
		config.setSharedVariable(GoodsListDirective.TAG_NAME, goodsListDirective);
		config.setSharedVariable(ListingChildListDirective.TAG_NAME, listingChildListDirective);
		config.setSharedVariable(ListingChildNameDirective.TAG_NAME, listingChildNameDirective);
		config.setSharedVariable(ArticleCategoryChildNameDirective.TAG_NAME, articleCategoryChildNameDirective);
		config.setSharedVariable(OverdueMethod.TAG_NAME, overdueMethod);
		config.setSharedVariable(RechargeConfigDirective.TAG_NAME, rechargeConfigDirective);
		config.setSharedVariable(UserAccountRechargeDirective.TAG_NAME, userAccountRechargeDirective);
		config.setSharedVariable(AprOfYearDirective.TAG_NAME, aprOfYearDirective);
		config.setSharedVariable(ImageUrlDecodeDirective.TAG_NAME, imageUrlDecodeDirective);
		config.setSharedVariable(NumberPercentDirective.TAG_NAME, numberPercentDirective);
		config.setSharedVariable(UserByAgencyDirective.TAG_NAME, userByAgencyDirective);

		servletContext.setAttribute(CONFIG_SERVLET_CONTEXT_KEY, config);

		config.setWhitespaceStripping(true);
		return config;
	}

}