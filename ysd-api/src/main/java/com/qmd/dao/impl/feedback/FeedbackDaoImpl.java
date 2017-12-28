package com.qmd.dao.impl.feedback;

import com.qmd.dao.feedback.FeedbackDao;
import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.mode.feedback.Feedback;
import org.springframework.stereotype.Repository;

@Repository("feedbackDao")
public class FeedbackDaoImpl extends BaseDaoImpl<Feedback, Integer>  implements FeedbackDao {
	
	@Override
	public Integer saveNew(Feedback entity) {
		return this.getSqlSession().insert(getNameSpace(entity) + ".insertNew",entity);
	}
	

}
