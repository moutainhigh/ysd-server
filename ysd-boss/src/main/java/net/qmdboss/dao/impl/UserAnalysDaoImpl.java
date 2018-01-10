package net.qmdboss.dao.impl;

import net.qmdboss.dao.UserAnalysDao;
import net.qmdboss.entity.UserAnalys;
import org.springframework.stereotype.Repository;

@Repository("userAnalysDaoImpl")
public class UserAnalysDaoImpl extends BaseDaoImpl<UserAnalys, Integer> implements UserAnalysDao {

	public UserAnalys getUserAnalysByUserId(Integer userId) {
		String hql = "from UserAnalys as analys where lower(analys.userId) = lower(:userId)";
		UserAnalys analys = (UserAnalys) getSession().createQuery(hql).setParameter("userId", userId).uniqueResult();
		return analys;
	}
}
