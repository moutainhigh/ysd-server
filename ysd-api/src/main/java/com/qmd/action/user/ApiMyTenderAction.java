package com.qmd.action.user;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.PageBean;
import com.qmd.bean.center.*;
import com.qmd.bean.user.BorrowListingBean;
import com.qmd.bean.user.BorrowListingItem;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserRepaymentDetail;
import com.qmd.mode.util.Listing;
import com.qmd.service.borrow.BorrowTenderService;
import com.qmd.service.user.UserRepaymentDetailService;
import com.qmd.service.util.ListingService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的投资
 *
 */
@InterceptorRefs({ 
	@InterceptorRef(value = "apiUserInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack") })
@Service("apiMyTenderAction")
public class ApiMyTenderAction extends ApiBaseAction  {

	private static final long serialVersionUID = 5698314563224513693L;
	private User user;
	private String status;
	private UserAuto userAuto;

	@Resource
	BorrowTenderService borrowTenderService;
	@Resource
	UserRepaymentDetailService userRepaymentDetailService;
	@Resource
	private ListingService listingService;

	/**
	 * 投资记录
	 * @return
	 */
	@Action(value = "/api/tzjl")
	public String getTzjlList() {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", getLoginUser().getId());

			if (StringUtils.isNotEmpty(status)) {
//				if("5".equals(status)){
//					int[] array = {1,5};
//					map.put("array", array);
//				}else{
//					map.put("status", Integer.parseInt(status));
//				}
			}
//			else{
				int[] array = {1,3,5,7};
				map.put("array", array);
//			}
			pager = borrowTenderService.queryUncollectedDetailList(pager, map);
	
			
			UserTenderList utList = new UserTenderList();
			List<BorrowTender> btl = (List<BorrowTender>) pager.getResult();
			List<UserTender> utl = new ArrayList<UserTender>();
			for(BorrowTender bt : btl){
				UserTender ut = new UserTender();
				ut.setTenderid(bt.getId());
				ut.setApr(CommonUtil.setPriceScale(new BigDecimal(bt.getApr()).multiply(new BigDecimal(100))).toString());
				ut.setBorrowAccount(bt.getBorrowAccount().toString());
				ut.setBorrowName(bt.getTitle());
				ut.setBorrowStatus(Integer.parseInt(bt.getStatus()));
				ut.setCreateDate(bt.getRepaymentDate());
				ut.setTenderAccount(bt.getLoanAmountFee().toString());
				ut.setInterest(bt.getInterest());
				utl.add(ut);
			}
			PageBean pb = new PageBean();
			pb.setPageNumber(pager.getPageNumber());
			pb.setPageCount(pager.getPageCount());
			pb.setPageSize(pager.getPageSize());
			pb.setTotalCount(pager.getTotalCount());
			utList.setPageBean(pb);
			
			utList.setUserTenderList(utl);
			
			return ajax(JsonUtil.toJson(utList));
		}catch (Exception e) {
			e.printStackTrace();
			 return ajaxJson("S0001",ApiConstantUtil.S0001);
		}
	}


	/**
	 * 回款明细
	 * @return
	 */
	@Action(value = "/api/hkmx")
	public String getUserHkmxList() {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", getLoginUser().getId());
			map.put("orderBy", " d.repayment_date asc, d.status asc ");
			
			if (StringUtils.isNotEmpty(status)) {
				map.put("status", status);
			}
			pager = userRepaymentDetailService.queryUserRepaymentDetailPagerForIncome(pager, map);
			
			UserRepaymentList urList = new UserRepaymentList();
			List<UserRepaymentDetail> urdl = (List<UserRepaymentDetail>) pager.getResult();
			
			List<UserRepayment> utl = new ArrayList<UserRepayment>();
			
			for(UserRepaymentDetail urd :urdl){
				UserRepayment ur = new UserRepayment();
				ur.setBorrowName(urd.getBorrowName());
				ur.setPeriods(urd.getBorrowDivides());
				ur.setRepaymentDate(urd.getRepaymentDate());
				ur.setRepaymentPeriods(urd.getBorrowPeriods());
				ur.setRepaymentStatus(Integer.parseInt(urd.getStatus()));
				ur.setServiceCharge(urd.getServiceCharge());
				ur.setWaitAccount(urd.getWaitAccount());
				ur.setWaitInterest(urd.getWaitInterest());
				ur.setWaitTotal(urd.getRepaymentAccount());
				utl.add(ur);
			}
			
			
			PageBean pb = new PageBean();
			pb.setPageNumber(pager.getPageNumber());
			pb.setPageCount(pager.getPageCount());
			pb.setPageSize(pager.getPageSize());
			pb.setTotalCount(pager.getTotalCount());
			urList.setPageBean(pb);
			
			urList.setUserRepaymentList(utl);
			
			return ajax(JsonUtil.toJson(urList));
		}catch (Exception e) {
			e.printStackTrace();
			 return ajaxJson("S0001",ApiConstantUtil.S0001);
		}
	}

	

	/**
	 * 自动投资-载入页
	 * @return
	 */
	@Action(value = "/api/zdtzTo")
	public String getUserZdtzTo() {
		try{
			reloadUser();
			user = getLoginUser();
//			user = userService.getById(12, new User());
		if(StringUtils.isEmpty( user.getPayPassword())){
			return ajaxJson("S0004",ApiConstantUtil.S0004);
		}
//		Integer	tenderAutoWayOne = 0;// 1 按月付息，到期还本 (含一月标)
//		Integer	tenderAutoWayTwo = 0;//2按月分期还本息
//		Integer	tenderAutoWayThree = 0;// 3到期还本息
//			String ways = user.getAutoTenderRepayWay();
//			if (ways!=null) {
//				String way[] = ways.split(",");
//				if (way!=null && way.length ==3) {
//					for (int i =0;i<way.length;i++) {
//						if("1".equals(way[i])) {
//							if(i==0) {
//								tenderAutoWayOne = 1;
//							} else if(i==1){
//								tenderAutoWayTwo = 1;
//							} else if(i==2){
//								tenderAutoWayThree = 1;
//							}
//						}
//					}
//				}
//			}
			
			//	Integer	tenderAutoRank = userService.queryTenderAutoRank(user.getId());//排名
			
			UserAccount account = userAccountService.getUserAccountByUserId(user.getId());
			UserAuto ua = new UserAuto();
			ua.setAutoTenderStatus(user.getAutoTenderStatus());
			ua.setAbleMoney(account.getAbleMoney().toString());
			ua.setContinueTotal(account.getContinueTotal().toString());
			ua.setAutoTenderRule(user.getAutoTenderRule());
			ua.setAutoTenderMoneyTop(user.getAutoTenderMoneyTop());
//			ua.setAutoTenderMoneyLeave(user.getAutoTenderMoneyLeave());
//			
//			ua.setAutoTenderRateBegin(user.getAutoTenderRateBegin());
//			ua.setAutoTenderRateEnd(user.getAutoTenderRateEnd());
//			ua.setAutoTenderLimitBegin(user.getAutoTenderLimitBegin());
//			ua.setAutoTenderLimitEnd(user.getAutoTenderLimitEnd());
//			ua.setAutoTenderRewardBegin(user.getAutoTenderRewardBegin());
//			ua.setAutoTenderRewardEnd(user.getAutoTenderRewardEnd());
			
			
			
//			ua.setTenderAutoWayOne(tenderAutoWayOne);
//			ua.setTenderAutoWayTwo(tenderAutoWayTwo);
//			ua.setTenderAutoWayThree(tenderAutoWayThree);
			ua.setAutoTenderBorrowType(user.getAutoTenderBorrowType());
			
			
			return ajax(JsonUtil.toJson(ua));
		}catch (Exception e) {
			e.printStackTrace();
			 return ajaxJson("S0001",ApiConstantUtil.S0001);
		}
	}

	/**
	 * 自投投资保存
	 * @return
	 */

	@Action(value = "/api/zdtzSave")
	public String zdtzSave() {
		try{
			if(!userService.isPassword(getLoginUser().getUsername(), user.getPayPassword(), "1")){
				return ajaxJson("S0002",ApiConstantUtil.S0002);
			}
			
//			String ways = "";
//			if (userAuto.getTenderAutoWayOne()!= null && userAuto.getTenderAutoWayOne()==1) {
//				ways = ways + "1,";
//			} else {
//				ways = ways + "0,";
//			}
//			if (userAuto.getTenderAutoWayTwo()!= null && userAuto.getTenderAutoWayTwo()==1) {
//				ways = ways + "1,";
//			} else {
//				ways = ways + "0,";
//			}
//			if (userAuto.getTenderAutoWayThree()!= null && userAuto.getTenderAutoWayThree()==1) {
//				ways = ways + "1";
//			} else {
//				ways = ways + "0";
//			}
//			user.setAutoTenderRepayWay(ways);
			
			user.setId(getLoginUser().getId());
			user.setAutoTenderStatus(userAuto.getAutoTenderStatus());
			if(userAuto.getAutoTenderRule()!=null){
				if(userAuto.getAutoTenderRule()!=1){
					user.setAutoTenderRule(1);
				}else{
					user.setAutoTenderRule(userAuto.getAutoTenderRule());
				}
			}else{
				user.setAutoTenderRule(1);
			}
			if(StringUtils.isEmpty(userAuto.getAutoTenderBorrowType())){
				return ajaxJson("E0002",ApiConstantUtil.E0002);
			}
			if(userAuto.getAutoTenderMoneyTop()==null){
				return ajaxJson("E0002",ApiConstantUtil.E0002);
			}else if(userAuto.getAutoTenderMoneyTop().compareTo(BigDecimal.ZERO)==0){
				return ajaxJson("M00017",ApiConstantUtil.M00017);
			}
			user.setAutoTenderMoneyTop(userAuto.getAutoTenderMoneyTop());
			user.setAutoTenderBorrowType(userAuto.getAutoTenderBorrowType());
//			user.setAutoTenderMoneyLeave(userAuto.getAutoTenderMoneyLeave());
//			
//			user.setAutoTenderRateBegin(userAuto.getAutoTenderRateBegin());
//			user.setAutoTenderRateEnd(userAuto.getAutoTenderRateEnd());
//			user.setAutoTenderLimitBegin(userAuto.getAutoTenderLimitBegin());
//			user.setAutoTenderLimitEnd(userAuto.getAutoTenderLimitEnd());
//			user.setAutoTenderRewardBegin(userAuto.getAutoTenderRewardBegin());
//			user.setAutoTenderRewardEnd(userAuto.getAutoTenderRewardEnd());
			
			
			userService.updateTenderAuto(user);
//			return ajax("R0001","保存成功");
			 return ajaxJson("R0001","保存成功");
		}catch (Exception e) {
			e.printStackTrace();
			 return ajaxJson("S0001",ApiConstantUtil.S0001);
		}
	}
	@Action(value = "/api/borrowListingType")
	public String borrowListingType() {
		try {
			List<Listing> listingList = null;
			listingList = listingService.getChildListingBySignList("borrow_business_type");
			BorrowListingBean bean = new BorrowListingBean();
			List<BorrowListingItem> utl = new ArrayList<BorrowListingItem>();
			for(Listing listing:listingList ){
				BorrowListingItem bi = new BorrowListingItem();
				bi.setKeyValue(listing.getKeyValue());
				bi.setName(listing.getName());
				bi.setDescription(listing.getDescription());
				utl.add(bi);
			}
			bean.setBorlistingList(utl);
			return ajax(JsonUtil.toJson(bean));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return ajaxJson("S0001",ApiConstantUtil.S0001);
		}
	}
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public UserAuto getUserAuto() {
		return userAuto;
	}


	public void setUserAuto(UserAuto userAuto) {
		this.userAuto = userAuto;
	}
	
	
	

	
	
	
}
