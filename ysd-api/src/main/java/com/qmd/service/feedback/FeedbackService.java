package com.qmd.service.feedback;


import com.qmd.mode.feedback.Feedback;
import com.qmd.service.BaseService;


public interface FeedbackService extends BaseService<Feedback,Integer> {

	/**
	 * 新的保存反馈
	 * @author zdl
	 */
	public Integer saveNew(Feedback entity);
}
