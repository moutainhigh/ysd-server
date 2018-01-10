package com.qmd.action.user;

import com.qmd.action.base.BaseAction;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccountDetail;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.service.user.UserAccountService;
import com.qmd.service.user.UserAwardDetailService;
import com.qmd.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@InterceptorRefs({
	@InterceptorRef(value = "userVerifyInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
@Service("userAccountAction")
public class UserAccountAction extends BaseAction {

	private static final long serialVersionUID = -3389767984109684914L;
	
	private User user;
	private UserAccountRecharge userAccountRecharge;
	private UserAccountDetail userAccountDetail;
	private BigDecimal sumRecharge;
	
	private RechargeConfig rechargeConfig;
	@Resource
	UserService userService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	UserAwardDetailService userAwardDetailService;
	private String type;//类型 【资金详情:detail;充值详情:recharge】
	
	private Map<String, String> parameterMap;//参数
	private String minDate;//起始时间
	private String maxDate;//截止时间
	private String recodeType;
	
	@Action(value="/account/recode",
			results={@Result(name="recode", location="/content/user/account_detail.ftl", type="freemarker")})
	public String recode() {
		
		reloadUser();// 重新载入用户信息
		Map<String,Object> map = new HashMap<String,Object>();
		user = getLoginUser();
		map.put("userId", user.getId());
		if(StringUtils.isNotEmpty(minDate)){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			Date min;
			try {
				min = simple.parse(minDate);
			} catch (ParseException e) {
				addActionError("参数错误");
				return "error_ftl";
			}
			minDate = simple.format(min);
			map.put("minDate", min);
		}
		
		if(StringUtils.isNotEmpty(maxDate)){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			Date max; 
			try {
				max = simple.parse(maxDate);
			} catch (ParseException e) {
				addActionError("参数错误");
				return "error_ftl";
			}
			maxDate = simple.format(max);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(max);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)-1);
			map.put("maxDate", calendar.getTime());
		}
		if(StringUtils.isNotEmpty(type) && type.equals("detail")){//资金详情
			if(recodeType==null||"".equals(recodeType)){
				recodeType = "all";
			}
			if(StringUtils.isNotEmpty(recodeType)&&!"all".equals(recodeType)){
				map.put("type", recodeType);
			}
		}
		pager = this.userAccountService.getAccountDetailByUserId(pager, map);
		return "recode";
	}
	
	/**
	 * 用户资金详情列表-充值详情列表
	 * @return
	 */
	@Action(value="/account/detail",
			results={@Result(name="detail", location="/content/user/account_detail.ftl", type="freemarker"),
					 @Result(name="statistics", location="/content/user/account_statistics.ftl", type="freemarker"),
					 @Result(name="recharge", location="/content/user/account_recharge.ftl", type="freemarker")})
	public String list(){
		
		reloadUser();// 重新载入用户信息
		Map<String,Object> map = new HashMap<String,Object>();
		user = getLoginUser();
		map.put("userId", user.getId());
		
		if(StringUtils.isNotEmpty(minDate)){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			Date min;
			try {
				min = simple.parse(minDate);
			} catch (ParseException e) {
				addActionError("参数错误");
				return "error_ftl";
			}
			minDate = simple.format(min);
			map.put("minDate", min);
		}
		
		if(StringUtils.isNotEmpty(maxDate)){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			Date max; 
			try {
				max = simple.parse(maxDate);
			} catch (ParseException e) {
				addActionError("参数错误");
				return "error_ftl";
			}
			maxDate = simple.format(max);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(max);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)-1);
			map.put("maxDate", calendar.getTime());
		}
		
		if(type.equals("detail")){//资金详情
			if(recodeType==null||"".equals(recodeType)){
				recodeType = "all";
			}
			if(StringUtils.isNotEmpty(recodeType)&&!"all".equals(recodeType)){
				map.put("type", recodeType);
			}
			pager = this.userAccountService.getAccountDetailByUserId(pager, map);
			return "detail";
		}else if(type.equals("recharge")){//充值详情
			if(StringUtils.isNotEmpty(recodeType)){
				map.put("status", recodeType);
			}
			
			Object sum = userAccountService.getByStatementId("UserAccountRecharge.getUserAccountRechargeSum", map);
			if(sum==null){
				sumRecharge=new BigDecimal(0);
			}else{
				sumRecharge = new BigDecimal(sum.toString());
			}
			pager = this.userAccountService.getAccountRechargeByUserId(pager, map);
			return "recharge";
		}else if(type.equals("statistics")){//统计详情
			return "statistics";
		}else{
			addActionError("参数错误");
			return "error_ftl";
		}
	}
	
	/**
	 * 机构充值记录
	 * @return
	 */
	@Action(value="/account/rechargeAgencyList",
			results={@Result(name="success", location="/content/agency/pt/rechargeList.ftl", type="freemarker")})
	public String rechargeAgencyList(){
		
		reloadUser();// 重新载入用户信息
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rechargeSource", 2);
		user = getLoginUser();
		map.put("rechargeRequesterId", user.getId());
		
		if(StringUtils.isNotEmpty(minDate)){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			Date min;
			try {
				min = simple.parse(minDate);
			} catch (ParseException e) {
				addActionError("参数错误");
				return "error_ftl";
			}
			minDate = simple.format(min);
			map.put("minDate", min);
		}
		
		if(StringUtils.isNotEmpty(maxDate)){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			Date max; 
			try {
				max = simple.parse(maxDate);
			} catch (ParseException e) {
				addActionError("参数错误");
				return "error_ftl";
			}
			maxDate = simple.format(max);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(max);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)-1);
			map.put("maxDate", calendar.getTime());
		}
		
		
//			if(StringUtils.isNotEmpty(recodeType)){
//				map.put("status", recodeType);
//			}
		map.put("status", "1");
//			Object sum = userAccountService.getByStatementId("UserAccountRecharge.getUserAccountRechargeSum", map);
//			if(sum==null){
//				sumRecharge=new BigDecimal(0);
//			}else{
//				sumRecharge = new BigDecimal(sum.toString());
//			}
		map.put("agencyFlag", 1);
		map.put("agencyId", user.getAgencyid());
		map.put("agencyType", user.getAgencytype());
			pager = this.userAccountService.getAccountRechargeByUserId(pager, map);
		
		return SUCCESS;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserAccountRecharge getUserAccountRecharge() {
		return userAccountRecharge;
	}
	public void setUserAccountRecharge(UserAccountRecharge userAccountRecharge) {
		this.userAccountRecharge = userAccountRecharge;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public RechargeConfig getRechargeConfig() {
		return rechargeConfig;
	}


	public void setRechargeConfig(RechargeConfig rechargeConfig) {
		this.rechargeConfig = rechargeConfig;
	}


	public Map<String, String> getParameterMap() {
		return parameterMap;
	}


	public void setParameterMap(Map<String, String> parameterMap) {
		this.parameterMap = parameterMap;
	}


	public String getMinDate() {
		return minDate;
	}


	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}


	public String getMaxDate() {
		return maxDate;
	}


	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public UserAccountDetail getUserAccountDetail() {
		return userAccountDetail;
	}

	public void setUserAccountDetail(UserAccountDetail userAccountDetail) {
		this.userAccountDetail = userAccountDetail;
	}

	public String getRecodeType() {
		return recodeType;
	}

	public void setRecodeType(String recodeType) {
		this.recodeType = recodeType;
	}

	public BigDecimal getSumRecharge() {
		return sumRecharge;
	}

	public void setSumRecharge(BigDecimal sumRecharge) {
		this.sumRecharge = sumRecharge;
	}

	
	
}
