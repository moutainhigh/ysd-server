package com.qmd.dao.feedback;

import com.qmd.dao.BaseDao;
import com.qmd.mode.feedback.Feedback;

public interface FeedbackDao extends BaseDao<Feedback,Integer> {

	/**
	 * 新的保存意见反馈
	 * @param entity
	 * @author zdl
	 */
	public Integer saveNew(Feedback entity);
}
