package com.qmd.action.activity;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.PageBean;
import com.qmd.bean.activity.ActivityBean;
import com.qmd.bean.activity.ActivityList;
import com.qmd.mode.activity.Activity;
import com.qmd.service.activity.ActivityService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.JsonUtil;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("apiActivityAction")
public class ApiActivityAction extends ApiBaseAction {

	private static final long serialVersionUID = 1066739577933579364L;

	@Resource
	private ActivityService activityService;

	private Activity activity;

	/**
	 * 活动列表
	 * 
	 * @return
	 */
	@Action(value = "/api/activity")
	public String list() {

		if(activity == null){
			activity = new Activity();
//			activity.setStatus(1);
			int[] array ={1, 2};
			activity.setArray(array);
		}
		pager = activityService.queryPageByOjbect(activity,
				pager.getPageSize(), pager.getPageNumber(),
				" t.order_list ");

		ActivityList bean = new ActivityList();
		
		PageBean pb = new PageBean();
		pb.setPageNumber(pager.getPageNumber());
		pb.setPageCount(pager.getPageCount());
		pb.setPageSize(pager.getPageSize());
		pb.setTotalCount(pager.getTotalCount());
		bean.setPageBean(pb);
		List<Activity> activityList = (List<Activity>) pager.getResult();
		bean.setActivityList(activityList );
		return ajax(JsonUtil.toJson(bean));
		
	}
	
	
	/**
	 * 活动详情
	 * @return
	 */
	@Action(value = "/api/activity/detail")
	public String detail(){
		if(id == null){
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}
		
		activity = activityService.get(Integer.parseInt(id));
		
		ActivityBean bean = new ActivityBean();
		bean.setActivity(activity);
		
		return ajax(JsonUtil.toJson(bean));
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
