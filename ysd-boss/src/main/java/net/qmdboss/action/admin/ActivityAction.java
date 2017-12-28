package net.qmdboss.action.admin;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import net.qmdboss.entity.Activity;
import net.qmdboss.service.ActivityService;
import net.qmdboss.util.CommonUtil;
import net.qmdboss.util.ImageUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ParentPackage("admin")
public class ActivityAction extends BaseAdminAction {

	private static final long serialVersionUID = -9015064334816796639L;

	Logger log = Logger.getLogger(ActivityAction.class);
	
	private String title;
	private Date startTime;
	private Date endTime;
	private Integer status;
	private Activity activity;
	private File imgWeb;
	private File imgApp;
	private File imgContentApp;

	@Resource(name = "activityServiceImpl")
	private ActivityService activityService;
	
	public String list(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("status", status);
		pager = activityService.findPager(pager, map);
		return LIST;
	}
	
	public String add(){
		return INPUT;
	}
	
	public String edit(){
		
		if(id == null){
			addActionError("参数错误!");
			return ERROR;
		}
		
		activity = activityService.load(id);
		
		return INPUT;
	}
	
	public String delete(){
		StringBuffer logInfoStringBuffer = new StringBuffer("删除活动: ");
		for (Integer id : ids) {
			Activity a = activityService.load(id);
			activityService.delete(a);
			logInfoStringBuffer.append(a.getTitle() + " ");
		}
		logInfo = logInfoStringBuffer.toString();
		
		return ajax(Status.success, "删除成功!");
	}
	
	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "activity.title", message = "标题不允许为空!")
		},
		intRangeFields = {
				@IntRangeFieldValidator(fieldName = "activity.orderList", min = "0", message = "排序必须为零或正整数!")
		},
		requiredFields = {
				@RequiredFieldValidator(fieldName = "activity.status", message = "状态不允许为空!"),
				@RequiredFieldValidator(fieldName = "activity.startTime", message = "开始时间不允许为空!"),
				@RequiredFieldValidator(fieldName = "activity.endTime", message = "结束时间不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save(){
		
		if(imgWeb!= null){
			String path = ImageUtil.copyImageFile(getServletContext(), imgWeb);
			activity.setImgWeb(path);
		}
		
		if(imgApp!= null){
			String path = ImageUtil.copyImageFile(getServletContext(), imgApp);
			activity.setImgApp(path);
		}
		if(imgContentApp!= null){
			String path = ImageUtil.copyImageFile(getServletContext(), imgContentApp);
			String str=CommonUtil.WEB_IMG + path;
			activity.setContent(str);
		}
		
		activityService.save(activity);
		logInfo = "添加活动: " + activity.getTitle();
		redirectUrl = "activity!list.action";
		return SUCCESS;
	}
	
	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "activity.title", message = "标题不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update(){
		Activity persistent = activityService.load(id);
		if(imgWeb!= null){
			String path = ImageUtil.copyImageFile(getServletContext(), imgWeb);
			activity.setImgWeb(path);
		}else{
			activity.setImgWeb(persistent.getImgWeb());
		}
		
		if(imgApp!= null){
			String path = ImageUtil.copyImageFile(getServletContext(), imgApp);
			activity.setImgApp(path);
		}else{
			activity.setImgApp(persistent.getImgApp());
		}
		
		
		if(imgContentApp!= null){
			String path = ImageUtil.copyImageFile(getServletContext(), imgContentApp);
			String str=CommonUtil.WEB_IMG + path;
			activity.setContent(str);
		}else{
			activity.setContent(persistent.getContent());
		}
		
		
		
		BeanUtils.copyProperties(activity, persistent, new String[] {"id", "createDate", "modifyDate"});
		
		activityService.update(persistent);
		logInfo = "编辑活动: " + activity.getTitle();
		redirectUrl = "activity!list.action";
		return SUCCESS;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public File getImgWeb() {
		return imgWeb;
	}

	public void setImgWeb(File imgWeb) {
		this.imgWeb = imgWeb;
	}

	public File getImgApp() {
		return imgApp;
	}

	public void setImgApp(File imgApp) {
		this.imgApp = imgApp;
	}

	public File getImgContentApp() {
		return imgContentApp;
	}

	public void setImgContentApp(File imgContentApp) {
		this.imgContentApp = imgContentApp;
	}
}
