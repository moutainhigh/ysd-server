package net.qmdboss.dao;

import net.qmdboss.entity.UserAnalys;

public interface UserAnalysDao extends BaseDao<UserAnalys, Integer> {

	
	public UserAnalys getUserAnalysByUserId(Integer userId);
}
