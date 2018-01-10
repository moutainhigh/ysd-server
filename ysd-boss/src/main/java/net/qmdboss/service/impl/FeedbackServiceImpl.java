package net.qmdboss.service.impl;


import net.qmdboss.dao.FeedbackDao;
import net.qmdboss.entity.Feedback;
import net.qmdboss.service.FeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("feedbackServiceImpl")
public class FeedbackServiceImpl extends
		BaseServiceImpl<Feedback, Integer> implements FeedbackService {

	@Resource(name = "feedbackDaoImpl")
	public void setBaseDao(FeedbackDao feedbackDao) {
		super.setBaseDao(feedbackDao);
	}
}
