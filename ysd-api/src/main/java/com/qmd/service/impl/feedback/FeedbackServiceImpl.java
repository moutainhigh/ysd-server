package com.qmd.service.impl.feedback;


import com.qmd.dao.feedback.FeedbackDao;
import com.qmd.mode.feedback.Feedback;
import com.qmd.service.feedback.FeedbackService;
import com.qmd.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("feedbackService")
public class FeedbackServiceImpl extends BaseServiceImpl<Feedback,Integer> implements FeedbackService {

	@Resource
	private FeedbackDao feedbackDao;
	
	@Resource
	public void setBaseDao(FeedbackDao feedbackDao) {
		super.setBaseDao(feedbackDao);
	}

	@Override
	public Integer saveNew(Feedback entity) {
		return feedbackDao.saveNew(entity);
	}
	
	
}
