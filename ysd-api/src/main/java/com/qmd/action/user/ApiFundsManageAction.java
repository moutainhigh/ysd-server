package com.qmd.action.user;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.PageBean;
import com.qmd.bean.funds.UserAccDetail;
import com.qmd.bean.funds.UserAccDetailList;
import com.qmd.bean.funds.UserHongbao;
import com.qmd.bean.funds.UserHongbaoList;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccountDetail;
import com.qmd.mode.user.UserAwardDetail;
import com.qmd.mode.user.UserSpreadDetail;
import com.qmd.mode.util.Listing;
import com.qmd.service.user.*;
import com.qmd.service.util.ListingService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资金管理
 *
 */
@InterceptorRefs({ 
		@InterceptorRef(value = "apiUserInterceptor"),
		@InterceptorRef(value = "qmdDefaultStack") })
	@Service("apiFundsManageAction")
public class ApiFundsManageAction extends ApiBaseAction   {

	private static final long serialVersionUID = 6388112224388918414L;
	
	private User user;
	private String type;//资金明细

	@Resource
	UserService userService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	UserAccountDetailService userAccountDetailService;
	@Resource
	UserAccountRechargeService userAccountRechargeService;
	@Resource
	AccountBankService accountBankService;
	@Resource
	AccountCashService  accountCashService;
	@Resource
	private ListingService listingService;
	@Resource
	UserSpreadDetailService userSpreadDetailService;
	@Resource
	UserHongbaoService userHongbaoService;

	/**
	 * 我的红包
	 * @return
	
	@Action(value = "/api/hongbaoTo")
	public String hongbaoTo(){
		try{
			UserSpreadDetail userSpreadDetail = new UserSpreadDetail();
			Integer userId = getLoginUser().getId();
			userSpreadDetail.setTgUserId(userId);
			
			//推荐注册奖励
//			BigDecimal totalRegisterMoney = userSpreadDetailService.getRegisterSumMoney(userSpreadDetail);
			//好友投资奖励
//			BigDecimal totalMoney = userSpreadDetailService.getSumMoney(userSpreadDetail);

			Map<String,Object> map= new HashMap<String ,Object>();
			map.put("tgType", 1);
			map.put("tgOneLevelUserId", userId);
			
			pager =  userService.querySpreadListByMap(pager,map);
			//好友总数
			Integer totalCount = pager.getTotalCount();
			UserAccount ua = new UserAccount();
			ua = userAccountService.getUserAccountByUserId(userId);
			
			map = new HashMap<String, Object>();
			map.put("userId", userId);
			BigDecimal sumGetHbMoney = userHongbaoService.getSelectSumMoney(map);//累计获得红包奖励

			map.put("status", 1);
			BigDecimal sumUseHbMoney = userHongbaoService.getSelectSumMoney(map);//累计使用红包奖励
			
			UserHongbaoBean uhb = new UserHongbaoBean();
			uhb.setHbMoney(ua.getAwardMoney().toString());//红包金额
//			uhb.setAwardMoneyTj(totalRegisterMoney.toString());
//			uhb.setAwardMoneyTz(totalMoney.toString());

			uhb.setSumGetHbMoney(sumGetHbMoney.toString());//累计获得红包奖励
			uhb.setSumUseHbMoney(sumUseHbMoney.toString());//累计使用红包奖励
			
			uhb.setFriendsCount(totalCount);//好友数
			uhb.setTgNo(ConstantUtil.WEB_SITE+"/p_"+getLoginUser().getTgNo());//推广链接
			
			uhb.setSms(null);
			uhb.setWeixin(null);
			uhb.setWeixinFriend(null);
			uhb.setSina(null);
			uhb.setTencent(null);
			uhb.setQq(null);			
			
			return ajax(JsonUtil.toJson(uhb));
		}catch (Exception e) {
			e.printStackTrace();
			 return ajaxJson("S0001",ApiConstantUtil.S0001);
		}
	}
	 */

	@Resource
	UserAwardDetailService userAwardDetailService;
	/**
	 * 红包明细
	 * @return
	 */
	@Action(value = "/api/hongbaoList")
	public String hongbaoList(){
		try{
			UserSpreadDetail userSpreadDetail = new UserSpreadDetail();
			Integer userId = getLoginUser().getId();
			userSpreadDetail.setTgUserId(userId);
			
			//推荐注册奖励
//			BigDecimal totalRegisterMoney = userSpreadDetailService.getRegisterSumMoney(userSpreadDetail);
			//好友投资奖励
//			BigDecimal totalMoney = userSpreadDetailService.getSumMoney(userSpreadDetail);

//			UserAccount ua = new UserAccount();
//			ua = userAccountService.getUserAccountByUserId(userId);
			
			UserHongbaoList uhbList = new UserHongbaoList();
//			uhbList.setAwardMoney(ua.getAwardMoney().toString());
//			uhbList.setAwardMoneyTj(totalRegisterMoney.toString());
//			uhbList.setAwardMoneyTz(totalMoney.toString());
			
			List<UserHongbao> hongbaoList = new ArrayList<UserHongbao>();
			reloadUser();// 重新载入用户信息
			user = getLoginUser();
			
			UserAwardDetail obj = new UserAwardDetail();
			obj.setUserId(user.getId());
			obj.setReserve1("hongbao");
			pager =  userAwardDetailService.queryPageByOjbect(obj, pager.getPageSize(), pager.getPageNumber(), "create_date desc");
			List<UserAwardDetail> uadList = (List<UserAwardDetail>) pager.getResult();
			for(UserAwardDetail uad : uadList){
				UserHongbao uhb = new UserHongbao();
//				uhb.setAbleAwardMoney(uad.getAwardMoney().toString());
				uhb.setCreateDate(uad.getCreateDate());
				uhb.setMoney(uad.getMoney().toString());
				uhb.setSignFlg(uad.getSignFlg());
				uhb.setType(uad.getType());
				if(uad.getSignFlg().compareTo(1) == 0){
					uhb.setTypeShow(uad.getTypeName());
				}else{
					uhb.setTypeShow("投资使用红包");
				}
//				uhb.setTypeShow(uad.getRemark());
				uhb.setHbNo(uad.getReserve1());//红包编号
				hongbaoList.add(uhb);
			}
			
			PageBean pb = new PageBean();
			pb.setPageNumber(pager.getPageNumber());
			pb.setPageCount(pager.getPageCount());
			pb.setPageSize(pager.getPageSize());
			pb.setTotalCount(pager.getTotalCount());
			uhbList.setPageBean(pb);
			
			uhbList.setUserHongbaoList(hongbaoList);
			return ajax(JsonUtil.toJson(uhbList));
		}catch (Exception e) {
			e.printStackTrace();
			 return ajaxJson("S0001",ApiConstantUtil.S0001);
		}
	}
	

	/**
	 * 好友列表
	 * @return
	
	@Action(value = "/api/friendsList")
	public String friendsList(){
		try{
			UserSpreadDetail userSpreadDetail = new UserSpreadDetail();
			Integer userId = getLoginUser().getId();
			userSpreadDetail.setTgUserId(userId);
			
			//推荐注册奖励
			BigDecimal totalRegisterMoney = userSpreadDetailService.getRegisterSumMoney(userSpreadDetail);
			//好友投资奖励
			BigDecimal totalMoney = userSpreadDetailService.getSumMoney(userSpreadDetail);
			
			UserFriendList ufList= new UserFriendList();

			
			ufList.setAwardTotalMoneyTj(totalRegisterMoney.toString());
			ufList.setAwardTotalMoneyTz(totalMoney.toString());
			
			List<UserFriend> userFriendList = new ArrayList<UserFriend>();
			
			
			Map<String,Object> map= new HashMap<String ,Object>();
			map.put("tgType", 1);
			map.put("tgOneLevelUserId", userId);
			map.put("orderBy", " t.id desc ");
			pager =  userService.querySpreadListByMap(pager,map);
			
			List<User> uList = (List<User>) pager.getResult();
			
			for(User u : uList){
				UserFriend uf = new UserFriend();
				if(u.getTgStatus().compareTo(1) == 0){
					uf.setAwardMoneyTj(u.getSumTenderMoney().toString());
				}
				uf.setAwardMoneyTz(u.getSumTgMoney().toString());
				uf.setCreateDate(u.getCreateDate());
				uf.setEmailStatus(u.getEmailStatus().toString());
				uf.setFriendName(u.getUsername());
				uf.setPhoneStatus(u.getPhoneStatus().toString());
				uf.setRealNameStatus(u.getRealStatus().toString());
				
				userFriendList.add(uf);
			}
			
			ufList.setUserFriendList(userFriendList);
			
			PageBean pb = new PageBean();
			pb.setPageNumber(pager.getPageNumber());
			pb.setPageCount(pager.getPageCount());
			pb.setPageSize(pager.getPageSize());
			pb.setTotalCount(pager.getTotalCount());
			ufList.setPageBean(pb);
			
			return ajax(JsonUtil.toJson(ufList));
		}catch (Exception e) {
			e.printStackTrace();
			 return ajaxJson("S0001",ApiConstantUtil.S0001);
		}
	}
	 */

	

	private String recodeType;
	private String awardType;//奖励分类
	
	/**
	 * 资金记录
	 * @return
	 */
	@Action(value = "/api/accountDetailList")
	public String accountDetailList(){

		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", getLoginUser().getId());
			
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
				}
			
			pager = this.userAccountService.getAccountDetailByUserId(pager, map);
			List<UserAccountDetail> dList = (List<UserAccountDetail>) pager.getResult();
			List<UserAccDetail> uadList = new ArrayList<UserAccDetail>();
			for(UserAccountDetail uad:dList){
				UserAccDetail u = new UserAccDetail();
				uad.setTypeName(listingService.findListingByKeyValue(uad.getType()));
				if(uad.getTypeName().equals("项目回款")||uad.getTypeName().equals("充值")||uad.getTypeName().equals("奖励")){
					u.setSign("1");
				}else if(uad.getTypeName().equals("项目投资")||uad.getTypeName().equals("资金提现")){
					u.setSign("-1");
				}
				
				u.setAbleMoney(uad.getUseMoney().toString());
				u.setCreateDate(uad.getCreateDate());
				u.setMoney(uad.getMoney().toString());
				u.setRemark(uad.getRemark());
				u.setType(uad.getType());
				u.setTypeShow(uad.getTypeName());
//				u.setTasteMoney(uad.getTasteMoney().toString());
				uadList.add(u);
			}

			UserAccDetailList bean = new UserAccDetailList();
			
			PageBean pb = new PageBean();
			pb.setPageNumber(pager.getPageNumber());
			pb.setPageCount(pager.getPageCount());
			pb.setPageSize(pager.getPageSize());
			pb.setTotalCount(pager.getTotalCount());
			bean.setPageBean(pb);
			
			bean.setUserAccDetailList(uadList);
			return ajax(JsonUtil.toJson(bean));
		}catch (Exception e) {
			e.printStackTrace();
			 return ajaxJson("S0001",ApiConstantUtil.S0001);
		}
		
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getAwardType() {
		return awardType;
	}


	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}


	public String getRecodeType() {
		return recodeType;
	}


	public void setRecodeType(String recodeType) {
		this.recodeType = recodeType;
	}
	
	
	
	
	
	
	
	

}
