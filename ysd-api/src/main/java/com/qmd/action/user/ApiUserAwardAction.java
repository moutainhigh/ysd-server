package com.qmd.action.user;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.PageBean;
import com.qmd.bean.user.*;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.user.*;
import com.qmd.mode.util.Listing;
import com.qmd.mode.util.Setting;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.user.*;
import com.qmd.service.util.ListingService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.ConfigUtil;
import com.qmd.util.JsonUtil;
import com.qmd.util.bean.UserAward;
import net.qmdboss.util.RedPackUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service("apiUserAwardAction")
@InterceptorRefs({ @InterceptorRef(value = "apiUserInterceptor"), @InterceptorRef(value = "qmdDefaultStack") })
public class ApiUserAwardAction extends ApiBaseAction {

	private static final long serialVersionUID = -1143933158683010467L;

	private Integer status;// 红包状态0未使用1使用2已过期

	private BigDecimal hbMoney;// 红包余额
	private BigDecimal sumGetHbMoney;// 累计获得红包奖励
	private BigDecimal sumUseHbMoney;// 累计使用红包奖励

	private BigDecimal sumCashMoney;// 累计现金奖励
	private BigDecimal sumFriendsTzMoney;// 累计好友投资奖励
	private BigDecimal sumTzMoney;// 累计投资奖励

	private Integer sign;
	private String awardType;// 奖励分类
	private double  investFullMomeyMax;//用户投资金额
	
	private Integer borrowId;
	@Resource
	BorrowService borrowService;
	

	@Resource
	UserService userService;
	@Resource
	UserSpreadDetailService userSpreadDetailService;
	@Resource
	UserAwardDetailService userAwardDetailService;
	@Resource
	ListingService listingService;
	@Resource
	UserHongbaoService userHongbaoService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	UserAccountDetailService userAccountDetailService;


	/**
	 * 我的红包
	 * @return
	 */
	@Action(value = "/api/hongbaoTo")
	public String hongbaoTo(){
		try{
			UserSpreadDetail userSpreadDetail = new UserSpreadDetail();
			Integer userId = getLoginUser().getId();
			userSpreadDetail.setTgUserId(userId);
			
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

			uhb.setSumGetHbMoney(sumGetHbMoney.toString());//累计获得红包奖励
			uhb.setSumUseHbMoney(sumUseHbMoney.toString());//累计使用红包奖励
			
			uhb.setFriendsCount(totalCount);//好友数
			uhb.setTgNo( ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_SITE_URL) +"/p_"+getLoginUser().getTgNo());//推广链接
			
			return ajax(JsonUtil.toJson(uhb));
		}catch (Exception e) {
			e.printStackTrace();
			 return ajaxJson("S0001",ApiConstantUtil.S0001);
		}
	}
	

	/**
	 * 用户未读红包个数
	 * @author zdl
	 */
	@Action(value = "/api/hbNotLookCount")
	public String notLookHongbao() {
		Integer count=0;
		User user = getLoginUser();
		if(user != null && user.getId() != null){
			count = userHongbaoService.queryCountNotLookHB(user.getId());
		}else{
			ajaxJson("E0002","用户未登录!");
		}
		return ajaxJson("R0001",count.toString());
	}
	
	/**
	 * 查看红包列表
	 * 红包状态改为已读
	 * @author zdl
	 */
	@Action(value = "/api/hbListLooked")
	public String hongbaoLooked() {
		reloadUser();
		initPage();
		User u = getLoginUser();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", u.getId());

	    if(StringUtils.isEmpty(orderBy) || StringUtils.isEmpty(orderSort)){
	    	map.put("orderBy", " t.end_time asc ");
	    }else {
	    	map.put("orderBy", " t."+orderBy+" "+orderSort+" ");
		}
		if (status != null) {
			map.put("status", status);
		}
		//记录数
		Integer count =userHongbaoService.queryCountByMap(map);

		UserHongbaoPage page = new UserHongbaoPage();
		PageBean pb = new PageBean();
		pb.setPageNumber(pager.getPageNumber());//当前页号
		pb.setPageSize(pager.getPageSize());//页宽
		pb.setTotalCount(count == null? 0:count);//总记录数
		pager.setTotalCount(pb.getTotalCount());
		pb.setPageCount(pager.getPageCount());//总页数
		page.setPageBean(pb);
		
		//加载用户红包数据
		if(pb.getTotalCount()!=0){
			map.put("pageStart", (pb.getPageNumber() - 1) * pb.getPageSize());
			map.put("pageSize", pb.getPageSize());
			List<net.qmdboss.beans.UserHongbao> hbList = userHongbaoService.queryHBListByMap(map);
			List<UserHongbaoView> viewList = new ArrayList<UserHongbaoView>();
			if(hbList!=null && !hbList.isEmpty()){
				for (net.qmdboss.beans.UserHongbao hb : hbList) {
					UserHongbaoView view = new UserHongbaoView();
					BeanUtils.copyProperties(hb, view);
					viewList.add(view);
				}
			}
			page.setUserHongbaoViews(viewList);
		}
		
		//修改红包为已读
		if(u!=null && u.getId()!=null){
			userHongbaoService.updateHBIsLooked(u.getId());
		}
		return ajax(JsonUtil.toJson(page));
	}
	
	/**
	 * 红包列表
	 * 
	 * @return
	 */
	@Action(value = "/api/hbList")
	public String hongbao() {
		reloadUser();
		initPage();
		User u = getLoginUser();
		hbMoney = u.getAwardMoney();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", u.getId());
//		sumGetHbMoney = userHongbaoService.getSelectSumMoney(map);
//
//		map.put("status", 1);
//		sumUseHbMoney = userHongbaoService.getSelectSumMoney(map);

	    if(StringUtils.isEmpty(orderBy) || StringUtils.isEmpty(orderSort)){
	    	map.put("orderBy", " t.end_time asc ");
	    }else {
	    	map.put("orderBy", " t."+orderBy+" "+orderSort+" ");
		}
		if (status != null) {
			map.put("status", status);
		}

		UserHongbaoBean bean = new UserHongbaoBean();
		PageBean pb = new PageBean();
		pb.setPageNumber(pager.getPageNumber());
		pb.setPageCount(pager.getPageCount());
		pb.setPageSize(pager.getPageSize());
		pb.setTotalCount(userHongbaoService.queryCountByMap(map));
		bean.setPageBean(pb);

		map.put("pageStart", (pb.getPageNumber() - 1) * pb.getPageSize());
		map.put("pageSize", pb.getPageSize());

		
		List<UserHongbao> list = userHongbaoService.queryListByMap(map);

		List<UserHongbaoItem> userHongbaoItem = new ArrayList<UserHongbaoItem>();
		for (UserHongbao hb : list) {
			UserHongbaoItem hbItem = new UserHongbaoItem();
			BeanUtils.copyProperties(hb, hbItem);
			userHongbaoItem.add(hbItem);
		}

//		bean.setHbMoney(hbMoney.toString());
//		bean.setSumGetHbMoney(sumGetHbMoney.toString());
//		bean.setSumUseHbMoney(sumUseHbMoney.toString());
		bean.setUserHongbaoItem(userHongbaoItem);
		return ajax(JsonUtil.toJson(bean));
	}
	
	
	
	
	/**
	 * 红包列表新 仅适用于计算最优方案
	 * 
	 * @return
	 */
	@Action(value = "/api/hbListBestH")
	public String hbListBestH() {
		
	
		Borrow borrow = this.borrowService.getBorrowById(borrowId);
		if (borrow == null) {
			ajaxJson("M0010",ApiConstantUtil.M0010);
		}

		if (Double.valueOf(borrow.getBalance()) <= 0) {
			return ajaxJson("M0007_6",ApiConstantUtil.M0007_6);
		}
		
		String i=borrow.getTimeLimit();
		User u = getLoginUser();
		hbMoney = u.getAwardMoney();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", u.getId());
		map.put("status", 0);
		map.put("limitStart", i);
		map.put("orderBy", " money desc ,end_time asc ");
		
		
		net.qmdboss.beans.UserHongbao  uhbn=new net.qmdboss.beans.UserHongbao();
		List<net.qmdboss.beans.UserHongbao> list = userHongbaoService.queryHbListByMapNew(map);
	
		
		Map<String, Object> maphb = new HashMap<String, Object>();//最优红包
		HongbaoBestBen  hbbb=new HongbaoBestBen();//
		
		int hbsize=25;
		Listing ll=listingService.getListing(2063);//系统配置项的红包上限
		if(ll!=null){
			hbsize=Integer.valueOf(ll.getKeyValue());
		}
		
		if(list.size()>hbsize){
			String bestHbMoney="0";
			for(net.qmdboss.beans.UserHongbao  uhbmax :list){
				if(uhbmax.getInvestFullMomey()<=investFullMomeyMax){
					uhbmax.setOn(true);
					bestHbMoney=String.valueOf(uhbmax.getMoney());
					break;
				}
			}
			
			hbbb=new HongbaoBestBen();
			hbbb.setBestNum("1");
			hbbb.setBestHbMoney(bestHbMoney);
			hbbb.setRcd("R0001");
			hbbb.setRmg("成功");
			hbbb.setUserHongbaoList(list);
			return ajax(JsonUtil.toJson(hbbb));
		}
		
		
		Map<Integer,double[]> mapx = new HashMap<Integer,double[]>();

		for(net.qmdboss.beans.UserHongbao uhb:list){
			double[] xhong=new double[2];
			xhong[0]=uhb.getInvestFullMomey();
			xhong[1]=uhb.getMoney().doubleValue();;
			mapx.put(uhb.getId(), xhong);	
		}
		
		for (int key : mapx.keySet()) {
			   System.out.println("key= "+ key + " and value= " + Arrays.toString(mapx.get(key)));
		}

	
		
		hbbb.setBestNum("0");
		hbbb.setBestHbMoney("0");
		hbbb.setRcd("R0001");
		hbbb.setRmg("成功");
		
		if(mapx.size()>1){
			String hongbaoIds=RedPackUtil.getPackAmount(mapx,investFullMomeyMax);//返回最优红包组
			System.out.println("====hongbaoIds"+hongbaoIds);
			if(hongbaoIds==null){
				hbbb.setUserHongbaoList(list);
				return ajax(JsonUtil.toJson(hbbb));
			}
			String[] strs=hongbaoIds.split(",");
			hbbb=sortList(strs,list);//调最优算法
			
		}
		
	
			
		if(mapx.size()==1){
			if(list.get(0).getInvestFullMomey().doubleValue()<=investFullMomeyMax){//能用这个红包
				list.get(0).setOn(true);
				hbbb.setBestNum("1");
				hbbb.setBestHbMoney(list.get(0).getMoney().toString());
				
			}else {
				list.get(0).setOn(false);
				hbbb.setBestNum("0");
				hbbb.setBestHbMoney("0");
				
			}

			hbbb.setUserHongbaoList(list);
		}
		

		return ajax(JsonUtil.toJson(hbbb));
	}
	
	
	
	
	
	
	/**
	 * 跳转到‘我的好友’ 页面-分页
	 * 
	 * @return
	 */
	@Action(value = "/api/friendsList")
	public String myFriend() {
		initPage();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer userId = getLoginUser().getId();
		map.put("tgOneLevelUserId", userId);
		map.put("orderBy", " u.create_date desc ");
		MyFriendBean bean = new MyFriendBean();
		
		UserSpreadDetail userSpreadDetail = new UserSpreadDetail();
		userSpreadDetail.setTgUserId(userId);
		
		//推荐注册奖励
		BigDecimal totalRegisterMoney = userSpreadDetailService.getRegisterSumMoney(userSpreadDetail);
		bean.setAwardTotalMoneyTj(totalRegisterMoney.toString());

		Setting setting = listingService.getSetting();
		bean.setSpreadText(setting.getMiniMoney());
		bean.setSpreadTextarea(setting.getOffLineRechargeDes());

		pager = userService.queryMyFriendsListByMap(pager, map);
		List<UserAward> friendList = (List<UserAward>) pager.getResult();
		String tgNo = ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_SITE_URL) +"/fd/"+getLoginUser().getTgNo();
		
		PageBean pb = new PageBean();
		pb.setPageNumber(pager.getPageNumber());
		pb.setPageCount(pager.getPageCount());
		pb.setPageSize(pager.getPageSize());
		pb.setTotalCount(pager.getTotalCount());
		bean.setPageBean(pb);
		
		bean.setFriendList(friendList);
		bean.setTgNo(tgNo);
		return ajax(JsonUtil.toJson(bean));
	}


	/**
	 * 跳转到‘现金奖励’ 页面
	 * 
	 * @return
	 */
	@Action(value = "/api/awardCash")
	public String cash() {
		initPage();
		User user = getLoginUser();
//		sumTzMoney = userAccountDetailService.getSumMoney(user.getId(), "award_add", null, null);
//		sumFriendsTzMoney = userAccountDetailService.getSumMoney(user.getId(), "tui_detail_award", null, null);
//
//		sumCashMoney = sumTzMoney.add(sumFriendsTzMoney);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getId());
		if (sign != null) {
			if (sign.compareTo(-1) == 0) {
				map.put("type", "award_add");
			} else {
				map.put("type", "tui_detail_award");
			}
		} else {
			String[] strarray = { "award_add", "tui_detail_award" };
			map.put("strarray", strarray);
		}
		pager = this.userAccountService.getAccountDetailByUserId(pager, map);
		
		List<UserAccountDetail> list = (List<UserAccountDetail>) pager.getResult();
		List<AwardCashItem> cashList = new ArrayList<AwardCashItem>();
		for(UserAccountDetail uad : list){
			
			AwardCashItem aci = new AwardCashItem();
			aci.setCreateDate(uad.getCreateDate());
			aci.setMoney(uad.getMoney().toString());
			aci.setRemark(uad.getRemark());
			aci.setType(uad.getType());
			
			
			if(StringUtils.isNotEmpty(uad.getType())){
				Map<String,Object> map_type= new HashMap<String ,Object>();
				map_type.put("sign", "account_link");
				map_type.put("keyValue", uad.getType());
				String typeShow = listingService.findChildListingByKeyValue(map_type);
				aci.setTypeShow(typeShow);
			}
			
			cashList.add(aci);
		}
		
		AwawdCashBean bean = new AwawdCashBean();
		
		PageBean pb = new PageBean();
		pb.setPageNumber(pager.getPageNumber());
		pb.setPageCount(pager.getPageCount());
		pb.setPageSize(pager.getPageSize());
		pb.setTotalCount(pager.getTotalCount());
		bean.setPageBean(pb);
		
//		bean.setSumCashMoney(sumCashMoney.toString());
//		bean.setSumFriendsTzMoney(sumFriendsTzMoney.toString());
//		bean.setSumTzMoney(sumTzMoney.toString());
		bean.setCashList(cashList);
		
		return ajax(JsonUtil.toJson(bean));
	}

	public BigDecimal getHbMoney() {
		return hbMoney;
	}

	public void setHbMoney(BigDecimal hbMoney) {
		this.hbMoney = hbMoney;
	}

	public BigDecimal getSumGetHbMoney() {
		return sumGetHbMoney;
	}

	public void setSumGetHbMoney(BigDecimal sumGetHbMoney) {
		this.sumGetHbMoney = sumGetHbMoney;
	}

	public BigDecimal getSumUseHbMoney() {
		return sumUseHbMoney;
	}

	public void setSumUseHbMoney(BigDecimal sumUseHbMoney) {
		this.sumUseHbMoney = sumUseHbMoney;
	}

	public BigDecimal getSumCashMoney() {
		return sumCashMoney;
	}

	public void setSumCashMoney(BigDecimal sumCashMoney) {
		this.sumCashMoney = sumCashMoney;
	}

	public BigDecimal getSumFriendsTzMoney() {
		return sumFriendsTzMoney;
	}

	public void setSumFriendsTzMoney(BigDecimal sumFriendsTzMoney) {
		this.sumFriendsTzMoney = sumFriendsTzMoney;
	}

	public BigDecimal getSumTzMoney() {
		return sumTzMoney;
	}

	public void setSumTzMoney(BigDecimal sumTzMoney) {
		this.sumTzMoney = sumTzMoney;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public String getAwardType() {
		return awardType;
	}

	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}


	public double getInvestFullMomeyMax() {
		return investFullMomeyMax;
	}


	public void setInvestFullMomeyMax(double investFullMomeyMax) {
		this.investFullMomeyMax = investFullMomeyMax;
	}
	
	
	public Integer getBorrowId() {
		return borrowId;
	}


	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}


	//根据最优的进行排序
	public  HongbaoBestBen  sortList(String[] strs,List<net.qmdboss.beans.UserHongbao> list){
		List<net.qmdboss.beans.UserHongbao> listL=new ArrayList();//剩余红包组
		List<net.qmdboss.beans.UserHongbao> listBestReturn=new ArrayList();//返回数据g
		Boolean xif=true;//用于组合list
		BigDecimal bestHbMoney=new BigDecimal(0);
		
		for(net.qmdboss.beans.UserHongbao ii:list){
			for(int j=0;j<strs.length;j++){
				if(ii.getId()==Integer.parseInt(strs[j])){
					ii.setOn(true);
					listBestReturn.add(ii);
					bestHbMoney=bestHbMoney.add(ii.getMoney());
					xif=false;
				}
			}
			if(xif==true){
				ii.setOn(false);
				listL.add(ii);
			}
			xif=true;
			
		}
	
		for(net.qmdboss.beans.UserHongbao iii:listL){
			listBestReturn.add(iii);
		}
		
		HongbaoBestBen hongbaobestB = new HongbaoBestBen();
	    hongbaobestB.setRcd("R0001");
	    hongbaobestB.setBestNum(String.valueOf(strs.length));
	    hongbaobestB.setBestHbMoney(String.valueOf(bestHbMoney));
		
		hongbaobestB.setUserHongbaoList(listBestReturn);
		
	
		

		return hongbaobestB;
	}
	
	
	public static void main(String[] args){
		
		System.out.println("121");
		
	}
}
