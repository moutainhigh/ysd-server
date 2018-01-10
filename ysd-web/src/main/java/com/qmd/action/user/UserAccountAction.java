package com.qmd.action.user;

import com.qmd.action.base.BaseAction;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccountDetail;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.mode.util.Listing;
import com.qmd.service.user.UserAccountService;
import com.qmd.service.user.UserService;
import com.qmd.service.util.ListingService;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	ListingService listingService;
	
	
	private String type;//类型 【资金详情:detail;充值详情:recharge】
	
	private Map<String, String> parameterMap;//参数
	private String minDate;//起始时间
	private String maxDate;//截止时间
	private String recodeType;
	private String awardType;//奖励分类
	private Integer noPayPsw;
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
		
		
		if(type.equals("detail")){//资金详情
			
			if(StringUtils.isNotEmpty(awardType)){
				map.put("type", awardType);
			}else{
				if(recodeType==null||"".equals(recodeType)){
					recodeType = "all";
					Map<String,Object> lmap = new HashMap<String,Object>();
					String[] array = {"account_borrow_tender","account_borrow_hk","account_recharge","account_borrow_cash","account_link"};
					lmap.put("array", array);
					List<Listing> liaList = listingService.findChildListingBySing(lmap);
					String[] strarray = new String[liaList.size()] ;
					for(int i=0;i<liaList.size();i++){
						strarray[i]=liaList.get(i).getKeyValue();
					}
					map.put("strarray", strarray);
					
				}
				if(StringUtils.isNotEmpty(recodeType)){
					if("all".equals(recodeType)){
						Map<String,Object> lmap = new HashMap<String,Object>();
						String[] array = {"account_borrow_tender","account_borrow_hk","account_recharge","account_borrow_cash","account_link"};
						lmap.put("array", array);
						List<Listing> liaList = listingService.findChildListingBySing(lmap);
						String[] strarray = new String[liaList.size()] ;
						for(int i=0;i<liaList.size();i++){
							strarray[i]=liaList.get(i).getKeyValue();
						}
						map.put("strarray", strarray);
					}else{
						Map<String,Object> lmap = new HashMap<String,Object>();
						String[] array = {recodeType};
						lmap.put("array", array);
						List<Listing> liaList = listingService.findChildListingBySing(lmap);
						String[] strarray = new String[liaList.size()] ;
						for(int i=0;i<liaList.size();i++){
							strarray[i]=liaList.get(i).getKeyValue();
						}
						map.put("strarray", strarray);
					}
	//				map.put("type", recodeType);
				}
			
			}
			
			pager.setPageSize(6);
			pager = this.userAccountService.getAccountDetailByUserId(pager, map);
			List<UserAccountDetail> dList = (List<UserAccountDetail>) pager.getResult();
			List<UserAccountDetail> uaList = new ArrayList<UserAccountDetail>();
			for(UserAccountDetail ua:dList){
				ua.setTypeName(listingService.findListingByKeyValue(ua.getType()));
				if(ua.getTypeName().equals("项目回款")||ua.getTypeName().equals("充值")||ua.getTypeName().equals("奖励")){
					ua.setAcctype("1");	
				}else if(ua.getTypeName().equals("项目投资")||ua.getTypeName().equals("资金提现")){
					ua.setAcctype("0");
				}
				uaList.add(ua);
			}
			pager.setResult(uaList);
			return "detail";
		}else if(type.equals("recharge")){//充值详情
//			if(StringUtils.isNotEmpty(recodeType)){
//				map.put("status", recodeType);
//			}
			user = userService.get(user.getId());
			if(StringUtils.isBlank(user.getPayPassword())){
				setNoPayPsw(1);
			}
			map.put("status", 1);
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
	 * 用户资金详情
	 * @return
	 */
	@Action(value="/account/detailFull",
			results={@Result(name="success", location="/content/user/account_detail_full.ftl", type="freemarker")})
	public String detailFull(){
		Map<String,Object> map = new HashMap<String,Object>();
		user = getLoginUser();
		map.put("userId", user.getId());
		map.put("id", id);
		userAccountDetail = userAccountService.getAccountDetailByUserIdSingle(map);
		
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

	public String getAwardType() {
		return awardType;
	}

	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	public Integer getNoPayPsw() {
		return noPayPsw;
	}

	public void setNoPayPsw(Integer noPayPsw) {
		this.noPayPsw = noPayPsw;
	}

	
	
}
