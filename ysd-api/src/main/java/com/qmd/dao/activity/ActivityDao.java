package com.qmd.dao.activity;

import com.qmd.dao.BaseDao;
import com.qmd.mode.activity.Activity;

import java.util.Map;

public interface ActivityDao extends BaseDao<Activity, Integer> {
	
	
	
	
		//查询最新活动
		public Activity queryLastActivity(Map<String,Object> map);
			
}