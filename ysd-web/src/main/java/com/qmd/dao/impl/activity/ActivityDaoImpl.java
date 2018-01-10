package com.qmd.dao.impl.activity;

import com.qmd.dao.activity.ActivityDao;
import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.mode.activity.Activity;
import org.springframework.stereotype.Repository;

@Repository("activityDao")
public class ActivityDaoImpl extends BaseDaoImpl<Activity, Integer> implements
		ActivityDao {
}