package com.qmd.action.activity;

import com.qmd.action.base.BaseAction;
import com.qmd.mode.activity.Activity;
import com.qmd.service.activity.ActivityService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("activityAction")
public class ActivityAction extends BaseAction {

	private static final long serialVersionUID = 1066739577933579364L;

	@Resource
	private ActivityService activityService;

	private Activity activity;

	/**
	 * 活动列表
	 * 
	 * @return
	 */
	@Action(value = "/activity/list", results = { @Result(name = "success", location = "/content/activity/list.ftl", type = "freemarker") })
	public String list() {

		if(activity == null){
			activity = new Activity();
			activity.setStatus(1);
		}
		pager = activityService.queryPageByOjbect(activity,
				pager.getPageSize(), pager.getPageNumber(),
				" t.order_list ");

		return SUCCESS;
	}
	
	
	/**
	 * 活动详情
	 * @return
	 */
	@Action(value = "/activity/detail", results = { @Result(name = "success", location = "/content/activity/detail.ftl", type = "freemarker") })
	public String detail(){
		if(id == null){
			msg = "参数错误！";
			msgUrl = "";
			return "error_ftl";
		}
		
		activity = activityService.get(Integer.parseInt(id));
		
		return SUCCESS;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
