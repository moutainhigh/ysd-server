package com.qmd.bean.activity;

import com.qmd.bean.BaseBean;
import com.qmd.bean.PageBean;
import com.qmd.mode.activity.Activity;

import java.util.List;

public class ActivityList extends BaseBean {

	private static final long serialVersionUID = -1846825571396318770L;

	private List<Activity> activityList;
	
	private PageBean pageBean;

	public ActivityList(){
		setRcd("R0001");
	}
	
	public List<Activity> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<Activity> activityList) {
		this.activityList = activityList;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
	
}
