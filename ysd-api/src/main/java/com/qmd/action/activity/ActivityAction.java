package com.qmd.action.activity;

import com.qmd.action.base.BaseAction;
import com.qmd.bean.PageBean;
import com.qmd.bean.activity.ActivityList;
import com.qmd.mode.activity.Activity;
import com.qmd.service.activity.ActivityService;
import com.qmd.util.ApiConstantUtil;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("activityAction")
public class ActivityAction extends BaseAction {

	private static final long serialVersionUID = -324311598810634127L;

	@Resource
	private ActivityService activityService;

	private Activity activity;
	
	private ActivityList activityList;
	
	/**
	 * 活动列表
	 * 
	 * @return
	 */
	@Action(value = "/activity/list",results={@Result(name="success", location="/content/h5activity/list.ftl", type="freemarker")})
	public String list() {

		if(activity == null){
			activity = new Activity();
			activity.setStatus(1);
		}
		pager = activityService.queryPageByOjbect(activity,	pager.getPageSize(), pager.getPageNumber()," t.order_list ");

		ActivityList bean = new ActivityList();
		
		PageBean pb = new PageBean();
		pb.setPageNumber(pager.getPageNumber());
		pb.setPageCount(pager.getPageCount());
		pb.setPageSize(pager.getPageSize());
		pb.setTotalCount(pager.getTotalCount());
		bean.setPageBean(pb);
		List<Activity> aList = (List<Activity>) pager.getResult();
		bean.setActivityList(aList );
		activityList = bean;
		return SUCCESS;
	}
	
	
	/**
	 * 活动详情
	 * @return
	 */
	@Action(value = "/activity/detail",results={@Result(name="success", location="/content/h5activity/detail.ftl", type="freemarker")})
	public String detail(){
		if(id == null){
			return ajax("E0002", ApiConstantUtil.E0002);
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


	public ActivityList getActivityList() {
		return activityList;
	}


	public void setActivityList(ActivityList activityList) {
		this.activityList = activityList;
	}
}
