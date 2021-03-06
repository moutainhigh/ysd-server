package net.qmdboss.service.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.CommentDao;
import net.qmdboss.entity.Comment;
import net.qmdboss.entity.Goods;
import net.qmdboss.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service实现类 - 评论
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX65B94A1C020124AC5E38C179CA8D1CD0
 * ============================================================================
 */

@Service("commentServiceImpl")
public class CommentServiceImpl extends BaseServiceImpl<Comment, Integer> implements CommentService {
	
	@Resource(name = "commentDaoImpl")
	private CommentDao commentDao;
	
	@Resource(name = "commentDaoImpl")
	public void setBaseDao(CommentDao commentDao) {
		super.setBaseDao(commentDao);
	}
	
	@Transactional(readOnly = true)
	public Pager getCommentPager(Pager pager) {
		return commentDao.getCommentPager(pager);
	}
	
	@Transactional(readOnly = true)
	public Pager getCommentPager(Pager pager, Goods goods) {
		return commentDao.getCommentPager(pager, goods);
	}
	
	@Transactional(readOnly = true)
	public List<Comment> getCommentList(Goods goods, Integer maxResults) {
		return commentDao.getCommentList(goods, maxResults);
	}

}