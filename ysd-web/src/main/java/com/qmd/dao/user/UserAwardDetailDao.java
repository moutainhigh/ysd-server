package com.qmd.dao.user;

import com.qmd.dao.BaseDao;
import com.qmd.mode.user.UserAwardDetail;
import com.qmd.util.Pager;

import java.util.Map;

public interface UserAwardDetailDao extends BaseDao<UserAwardDetail, Integer> {
	
	public Pager queryHbDetailList(Map<String,Object> map,Integer pageSize,Integer currentPage);
}