package com.qmd.bean.activity;

import com.qmd.bean.BaseBean;
import com.qmd.mode.activity.Activity;

public class ActivityBean extends BaseBean {

	private static final long serialVersionUID = -45420722175596266L;

	private Activity activity;
	
	public ActivityBean(){
		setRcd("R0001");
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
