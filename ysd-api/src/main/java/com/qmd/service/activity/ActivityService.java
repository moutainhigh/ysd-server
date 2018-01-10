package com.qmd.service.activity;

import com.qmd.mode.activity.Activity;
import com.qmd.service.BaseService;

import java.util.Map;

public interface ActivityService extends BaseService<Activity, Integer> {
	
	
	//查询最新活动
	public Activity queryLastActivity(Map<String,Object> map);
}