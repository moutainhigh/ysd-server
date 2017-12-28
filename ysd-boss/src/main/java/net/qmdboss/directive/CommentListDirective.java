package net.qmdboss.directive;

import freemarker.core.Environment;
import freemarker.template.*;
import net.qmdboss.entity.Comment;
import net.qmdboss.entity.Goods;
import net.qmdboss.service.CommentService;
import net.qmdboss.service.GoodsService;
import net.qmdboss.util.DirectiveUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component("commentListDirective")
public class CommentListDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "comment_list";
	private static final String GOODS_ID_PARAMETER_NAME = "goods_id";
	private static final String COUNT_PARAMETER_NAME = "count";
	
	@Resource(name = "commentServiceImpl")
	private CommentService commentService;
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer goodsId = DirectiveUtil.getIntegerParameter(GOODS_ID_PARAMETER_NAME, params);
		Integer count = DirectiveUtil.getIntegerParameter(COUNT_PARAMETER_NAME, params);
		
		Goods goods = null;
		if (goodsId != null) {
			goods = goodsService.load(goodsId);
		}
		List<Comment> commentList = commentService.getCommentList(goods, count);
		
		if (body != null && commentList != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(commentList);
			}
			body.render(env.getOut());
		}
	}

}