package net.qmdboss.service.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.MessageDao;
import net.qmdboss.entity.Member;
import net.qmdboss.entity.Message;
import net.qmdboss.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Service实现类 - 消息
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

@Service("messageServiceImpl")
public class MessageServiceImpl extends BaseServiceImpl<Message, Integer> implements MessageService {
	
	@Resource(name = "messageDaoImpl")
	private MessageDao messageDao;
	
	@Resource(name = "messageDaoImpl")
	public void setBaseDao(MessageDao messageDao) {
		super.setBaseDao(messageDao);
	}
	
	@Transactional(readOnly = true)
	public Pager getMemberInboxPager(Member member, Pager pager) {
		return messageDao.getMemberInboxPager(member, pager);
	}
	
	@Transactional(readOnly = true)
	public Pager getMemberOutboxPager(Member member, Pager pager) {
		return messageDao.getMemberOutboxPager(member, pager);
	}
	
	@Transactional(readOnly = true)
	public Pager getMemberDraftboxPager(Member member, Pager pager) {
		return messageDao.getMemberDraftboxPager(member, pager);
	}
	
	@Transactional(readOnly = true)
	public Pager getAdminInboxPager(Pager pager) {
		return messageDao.getAdminInboxPager(pager);
	}
	
	@Transactional(readOnly = true)
	public Pager getAdminOutboxPager(Pager pager) {
		return messageDao.getAdminOutboxPager(pager);
	}
	
	@Transactional(readOnly = true)
	public Long getUnreadMessageCount(Member member) {
		return messageDao.getUnreadMessageCount(member);
	}
	
	@Transactional(readOnly = true)
	public Long getUnreadMessageCount() {
		return messageDao.getUnreadMessageCount();
	}

}