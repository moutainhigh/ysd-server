package com.qmd.service.impl.activity;

import com.qmd.dao.activity.ActivityDao;
import com.qmd.mode.activity.Activity;
import com.qmd.service.activity.ActivityService;
import com.qmd.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("activityService")
public class ActivityServiceImpl extends BaseServiceImpl<Activity, Integer>
		implements ActivityService {
	@SuppressWarnings("unused")
	@Resource
	private ActivityDao activityDao;

	@Resource
	public void setBaseDao(ActivityDao activityDao) {
		super.setBaseDao(activityDao);
	}
}
