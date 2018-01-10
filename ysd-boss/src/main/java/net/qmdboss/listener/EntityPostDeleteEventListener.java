package net.qmdboss.listener;

import net.qmdboss.entity.Article;
import net.qmdboss.entity.Comment;
import net.qmdboss.entity.Goods;
import net.qmdboss.entity.Product;
import net.qmdboss.service.JobService;
import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("entityPostDeleteEventListener")
public class EntityPostDeleteEventListener implements PostDeleteEventListener {
	
	private static final long serialVersionUID = 616415776098455744L;
	
	private static final String NAVIGATION_ENTITY_NAME = "net.shopxx.entity.Navigation";
	private static final String FRIEND_LINK_ENTITY_NAME = "net.shopxx.entity.FriendLink";
	private static final String ARTICLE_CATEGORY_ENTITY_NAME = "net.shopxx.entity.ArticleCategory";
	private static final String GOODS_CATEGORY_ENTITY_NAME = "net.shopxx.entity.GoodsCategory";
	private static final String ARTICLE_ENTITY_NAME = "net.shopxx.entity.Article";
	private static final String GOODS_ENTITY_NAME = "net.shopxx.entity.Goods";
	private static final String PRODUCT_ENTITY_NAME = "net.shopxx.entity.Product";
	private static final String COMMENT_ENTITY_NAME = "net.shopxx.entity.Comment";
	private static final String INSTANT_MESSAGING_ENTITY_NAME = "net.shopxx.entity.InstantMessaging";
	
	@Resource(name = "jobServiceImpl")
	private JobService jobService;

	public void onPostDelete(PostDeleteEvent event) {
		EntityPersister entityPersister = event.getPersister();
		String entityName = entityPersister.getEntityName();
		
		if (entityName.equals(NAVIGATION_ENTITY_NAME)) {
			jobService.buildIndexHtml();
			jobService.buildLoginHtml();
			jobService.buildArticleContentHtml();
			jobService.buildGoodsContentHtml();
		} else if (entityName.equals(FRIEND_LINK_ENTITY_NAME)) {
			jobService.buildIndexHtml();
			jobService.buildLoginHtml();
			jobService.buildArticleContentHtml();
			jobService.buildGoodsContentHtml();
		} else if (entityName.equals(ARTICLE_CATEGORY_ENTITY_NAME)) {
			jobService.buildIndexHtml();
			jobService.buildArticleContentHtml();
		} else if (entityName.equals(GOODS_CATEGORY_ENTITY_NAME)) {
			jobService.buildIndexHtml();
			jobService.buildGoodsContentHtml();
		} else if (entityName.equals(ARTICLE_ENTITY_NAME)) {
			jobService.buildIndexHtml();
			Article article = (Article) event.getEntity();
			jobService.deleteArticleContentHtml(article.getHtmlPath(), article.getPageCount());
		} else if (entityName.equals(GOODS_ENTITY_NAME)) {
			jobService.buildIndexHtml();
			Goods goods = (Goods) event.getEntity();
			jobService.deleteGoodsContentHtml(goods.getHtmlPath());
		} else if (entityName.equals(PRODUCT_ENTITY_NAME)) {
			Product product = (Product) event.getEntity();
			jobService.buildGoodsContentHtml(product.getGoods().getId().toString());
		} else if (entityName.equals(COMMENT_ENTITY_NAME)) {
			Comment comment = (Comment) event.getEntity();
			jobService.buildGoodsContentHtml(comment.getGoods().getId().toString());
		} else if (entityName.equals(INSTANT_MESSAGING_ENTITY_NAME)) {
			jobService.buildShopJs();
		}
	}

}