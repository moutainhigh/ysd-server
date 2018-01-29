package com.qmd.action.activity;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.action.base.BaseAction;
import com.qmd.bean.PageBean;
import com.qmd.bean.activity.ActivityList;
import com.qmd.mode.activity.Activity;
import com.qmd.mode.borrow.AwarInfo;
import com.qmd.mode.borrow.AwarListinfo;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAward;
import com.qmd.service.activity.ActivityService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.JsonUtil;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("activityAction")
public class ActivityAction extends ApiBaseAction {

	private static final long serialVersionUID = -324311598810634127L;

	@Resource
	private ActivityService activityService;
	private Activity activity;
	private ActivityList activityList;
	private Integer userId;
	private String name;
	private String phone;
	private String address;



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


	List<UserAward> awardInfoList;

	public List<UserAward> getAwardInfoList() {
		return awardInfoList;
	}

	public void setAwardInfoList(List<UserAward> awardInfoList) {
		this.awardInfoList = awardInfoList;
	}

	private AwarInfo awarInfo;

	public AwarInfo getAwarInfo() {
		return awarInfo;
	}

	public void setAwarInfo(AwarInfo awarInfo) {
		this.awarInfo = awarInfo;
	}
	/**
	 * 抽奖校验
	 * sjc 20180116
	 *
	 * @return
	 */
	@Action(value = "/api/checkAwardPeople/detail",results={@Result(name="success", location="/content/h5cj/cj.ftl", type="freemarker")})
	public String checkAwardPeople() {
		awarInfo = new AwarInfo();
		List<AwarListinfo> awarInfoList = new ArrayList<>();
		List<UserAward> list = this.userService.checkAwardPeople(Integer.parseInt(id));
		if(list == null|| list.isEmpty()){
			awarInfo.setIsAward("0");
		}else{
			awarInfo.setIsAward("1");
		}
		awardInfoList = this.userService.getAwardList();

		UserAward userAward = this.userService.getAwardInfo(Integer.parseInt(id));
		if (userAward == null){
			awarInfo.setMyAwar(null);
			awarInfo.setAwardCode(null);
		}else{
			awarInfo.setMyAwar(userAward.getAwardName());
			awarInfo.setAwardCode(userAward.getAwardNameCode());
		}
		awarInfo.setUserId(id);
		return SUCCESS;
	}

	@Action(value = "/api/infolist",results={@Result(name="success", location="/content/h5cj/cj_info.ftl", type="freemarker")})
	public String infoList() {
		return SUCCESS;
	}

	@Action(value = "/api/rule",results={@Result(name="success", location="/content/h5cj/cj_rule.ftl", type="freemarker")})
	public String rule() {
		return SUCCESS;
	}


	/**
	 * 抽奖码校验
	 * sjc 20180118
	 *
	 * @return
	 */
	@Action(value = "/api/checkAwardCode/detail")
	public String checkAwardCode(){
		boolean isAwar = this.userService.checkAwardCode(id);
		if(isAwar == false) return ajax("0");
		else {
			UserAward userAward = new UserAward();
			userAward.setAwardCode(id);
			Random random = new Random();
			int award = random.nextInt(100);
			if(award >= 0 && award < 51 ) {
				userAward.setAwardNameCode(4);
				userAward.setAwardName("吉祥春节对联");
			}
			else if(award>=51 && award< 58) {
				userAward.setAwardNameCode(8);
				userAward.setAwardName("进口有机大米");
			}
			else if(award>=58 && award< 65) {
				userAward.setAwardNameCode(7);
				userAward.setAwardName("舒肤床上四件套");
			}
			else if(award>=65 && award< 72) {
				userAward.setAwardNameCode(1);
				userAward.setAwardName("精美瓷器碗碟");
			}
			else if(award>=72 && award< 79) {
				userAward.setAwardNameCode(2);
				userAward.setAwardName("多功能榨汁机");
			}
			else if(award>=79 && award< 86) {
				userAward.setAwardNameCode(3);
				userAward.setAwardName("3D耳机");
			}
			else if(award>=86 && award< 93) {
				userAward.setAwardNameCode(5);
				userAward.setAwardName("便携式体重秤");
			}
			else if(award>=93 && award< 100) {
				userAward.setAwardNameCode(6);
				userAward.setAwardName("大毫安充电宝");
			}
			this.userService.updateAwardInfo(userAward);
			return ajax(JsonUtil.toJson(userAward));
		}
	}

	/**
	 * 中奖人信息录入
	 * sjc 20180118
	 *
	 * @return
	 */
	@Action(value = "/api/awardPeopleInfo/detail")
	public String awardPeopleInfo(){
		if (id == null || id.isEmpty()) return ajax("0");
		if (userId == null) return ajax("0");
		if (name == null || name.isEmpty()) return  ajax("0");
		if (phone == null || phone.isEmpty()) return  ajax("0");
		if (address == null ||address.isEmpty()) return  ajax("0");
		UserAward userAward = new UserAward();
		userAward.setAwardCode(id);
		userAward.setUserId(userId);
		userAward.setName(name);
		userAward.setPhone(phone);
		userAward.setAddress(address);
		this.userService.updateAwardInfo(userAward);
		return ajax("1");
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
