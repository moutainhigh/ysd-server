package com.qmd.action.user;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAwardDetail;
import com.qmd.mode.user.UserSpreadDetail;
import com.qmd.mode.util.Listing;
import com.qmd.service.user.UserAwardDetailService;
import com.qmd.service.user.UserService;
import com.qmd.service.user.UserSpreadDetailService;
import com.qmd.service.util.ListingService;
import com.qmd.util.ConstantUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 投资人推广
 * @author zf
 *
 */
@Service("spreadAction")
@InterceptorRefs({
	@InterceptorRef(value = "userVerifyInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
public class SpreadAction extends BaseAction {

	private static final long serialVersionUID = -613026960268080179L;
	
	@Resource
	UserService userService;
	@Resource
	UserSpreadDetailService userSpreadDetailService;
	@Resource
	UserAwardDetailService userAwardDetailService;
	@Resource
	ListingService listingService;

	
	private BigDecimal totalMoney;//好友投资奖励
	private BigDecimal totalRegisterMoney;//推荐注册奖励
	private Integer totalCount;//好友总数
	private BigDecimal totalOneMoney;//累计一级奖励
	private User user;
	private UserSpreadDetail userSpreadDetail;//推广记录表
	private String r;//推广参数
	private Integer n; 
	private String uname;//检索用户名
	private String minDate;//起始时间
	private String maxDate;//截止时间
	
	private Integer userNums;//客户发展数
	private Integer cardActiveNums;//会员卡激活数
	private Integer cardNums;//会员卡总数
	private BigDecimal investTotal;//所属会员投资总额
	
	private String order;//排序【1：按投资金额降序排列；2：按投资金额升序排列；】
	private String listNum;//投资奖励比例

	/**
	 * 跳转到生成 推广链接 页面
	 * @return
	 */
	@Action(value="/spread/toGetLink",results={@Result(name="success", location="/content/spread/tg/getlink_friends.ftl", type="freemarker"),
										       @Result(name="msg", location="/content/message_page.ftl", type="freemarker")})
	public String toGetLink(){
		Map<String,Object> map= new HashMap<String ,Object>();
		userSpreadDetail = new UserSpreadDetail();
		Integer userId = getLoginUser().getId();
		userSpreadDetail.setTgUserId(userId);
		
//		totalMoney = userSpreadDetailService.getSumMoney(userSpreadDetail); //

		userSpreadDetail.setType("account_type_award");
		totalMoney = userSpreadDetailService.getRegisterSumMoney(userSpreadDetail);
		userSpreadDetail.setType("money_log_friend_name_email_tender_award");
		totalRegisterMoney = userSpreadDetailService.getRegisterSumMoney(userSpreadDetail);
		String orderBy = "t.create_date desc";
	//********************获取奖励比例参数*********************************
		Listing listing_a = listingService.getListing(ConstantUtil.TUI_JIAN_AWARD);
		 listNum = String.valueOf((Double.parseDouble(listing_a.getKeyValue())*100));
		if(user== null){
			user = new User();
		}
		if(StringUtils.isNotEmpty(uname)){//根据用户名搜索
			map.put("username", uname);
			User u = userService.getUser(map);
			if(u != null && u.getTgOneLevelUserId() != null && u.getTgOneLevelUserId().compareTo(userId) ==0){//一级推广
				map.put("tgType", 1);
				map.put("tgOneLevelUserId", userId);
//				map.put("tgTwoLevelUserId", null);
			}else{
				msg = "此好友不存在，不能查看！";
				msgUrl = "/spread/toGetLink.do";
				return "msg";
			}
		}else{
			map.put("tgType", 1);
			map.put("tgOneLevelUserId", userId);
//			map.put("tgTwoLevelUserId", null);
			if(n != null){
				User u = userService.getById(n, new User());
				if(u != null && u.getTgOneLevelUserId()!= null &&  u.getTgOneLevelUserId().compareTo(userId)==0){
					map.put("tgType", 2);
					map.put("tgOneLevelUserId", u.getId());
//					map.put("tgTwoLevelUserId", userId);
					userId = u.getId();
				}else{			
					msg = "此好友不存在，不能查看！";
					msgUrl = "/spread/toGetLink.do";
					return "msg";
				}
			}
		}
		user = userService.getById(userId, new User());
		map.put("orderBy", orderBy);
		pager =  userService.querySpreadListByMap(pager,map);
		totalCount = pager.getTotalCount();
		return SUCCESS;
	}
	
	/**
	 * 生成 推广链接
	 * @return
	 */
	@Action(value="/spread/ajaxGetLink", results = { @Result(type = "json") })
	@InputConfig(resultName = "ajaxError")
	public String ajaxGetLink(){
		if(user == null || StringUtils.isEmpty(user.getTgNo())){
			return ajax(Status.error,"参数错误");
		}
		User u = new User();
		u.setTgNo(user.getTgNo());
		List<User> user_list = userService.queryListByObject(u);
		if(user_list == null || user_list.size() > 0){
			return ajax(Status.error,"推广编号已存在！");
		}
		User loginUser = getLoginUser(); 
		loginUser.setTgNo(user.getTgNo());
		userService.update(loginUser);
		return ajax(Status.success,"生成推广链接成功！");
	}

	/**
	 * 收入统计列表
	 * @return
	 */
	@Action(value="/spread/total",results={@Result(name="success", location="/content/spread/tg/total.ftl", type="freemarker")})
	public String total(){
		if(userSpreadDetail == null){
			userSpreadDetail = new UserSpreadDetail();
		}
		userSpreadDetail.setTgUserId(getLoginUser().getId());
		String orderBy = "t.create_date desc ";
		pager = userSpreadDetailService.queryPageByOjbect(userSpreadDetail, 20, pager.getPageNumber(), orderBy);
		totalMoney = userSpreadDetailService.getSumMoney(userSpreadDetail);
		
		userSpreadDetail.setStatusCode(1);//一级奖励
		totalOneMoney = userSpreadDetailService.getSumMoney(userSpreadDetail);
		return SUCCESS;
	}
	
	/**
	 * 推广注册奖励明细
	 * @return
	 */
	@Action(value="/spread/registerAward",
			results={@Result(name="award", location="/content/spread/tg/register_award.ftl", type="freemarker")})
	public String accountAwardDetail() {
		
//		reloadUser();// 重新载入用户信息
//		user = getLoginUser();
//		
//		UserAwardDetail obj = new UserAwardDetail();
//		obj.setUserId(user.getId());
//		obj.setRelateKey("user_id");
//		obj.setType(ConstantUtil.MONEY_LOG_NAME_EMAIL_TENDER_AWARD);
//		pager =  userAwardDetailService.queryPageByOjbect(obj, 10, pager.getPageNumber(), "create_date desc");
//		return "award";
		
		reloadUser();// 重新载入用户信息
		user = getLoginUser();
		
		UserAwardDetail obj = new UserAwardDetail();
		obj.setUserId(user.getId());
		pager =  userAwardDetailService.queryPageByOjbect(obj, 10, pager.getPageNumber(), "create_date desc");
		return "award";
	}
	

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getTotalRegisterMoney() {
		return totalRegisterMoney;
	}

	public void setTotalRegisterMoney(BigDecimal totalRegisterMoney) {
		this.totalRegisterMoney = totalRegisterMoney;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public BigDecimal getTotalOneMoney() {
		return totalOneMoney;
	}

	public void setTotalOneMoney(BigDecimal totalOneMoney) {
		this.totalOneMoney = totalOneMoney;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserSpreadDetail getUserSpreadDetail() {
		return userSpreadDetail;
	}

	public void setUserSpreadDetail(UserSpreadDetail userSpreadDetail) {
		this.userSpreadDetail = userSpreadDetail;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
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

	public Integer getUserNums() {
		return userNums;
	}

	public void setUserNums(Integer userNums) {
		this.userNums = userNums;
	}

	public Integer getCardActiveNums() {
		return cardActiveNums;
	}

	public void setCardActiveNums(Integer cardActiveNums) {
		this.cardActiveNums = cardActiveNums;
	}

	public Integer getCardNums() {
		return cardNums;
	}

	public void setCardNums(Integer cardNums) {
		this.cardNums = cardNums;
	}

	public BigDecimal getInvestTotal() {
		return investTotal;
	}

	public void setInvestTotal(BigDecimal investTotal) {
		this.investTotal = investTotal;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getListNum() {
		return listNum;
	}

	public void setListNum(String listNum) {
		this.listNum = listNum;
	}
	
	

}
