package net.qmdboss.dao.impl;

import net.qmdboss.dao.FeedbackDao;
import net.qmdboss.entity.Feedback;
import org.springframework.stereotype.Repository;

@Repository("feedbackDaoImpl")
public class FeedbackDaoImpl extends BaseDaoImpl<Feedback, Integer>
		implements FeedbackDao {

}