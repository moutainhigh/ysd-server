package com.qmd.dao.impl.activity;

import com.qmd.dao.activity.ActivityDao;
import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.mode.activity.Activity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository("activityDao")
public class ActivityDaoImpl extends BaseDaoImpl<Activity, Integer> implements ActivityDao {
	
	
	
	public  Activity queryLastActivity(Map<String,Object> map){
		
		List<Activity> activityList = this.getSqlSession().selectList("Activity.queryLastActivity",map);
		if(activityList!=null&&activityList.size()>0){
			return activityList.get(0);
		}
		return null;
		
	}

	
	
}