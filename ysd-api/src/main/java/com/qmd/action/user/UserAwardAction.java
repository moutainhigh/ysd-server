package com.qmd.action.user;

import com.qmd.action.base.BaseAction;
import com.qmd.bean.PageBean;
import com.qmd.bean.user.*;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccountDetail;
import com.qmd.mode.user.UserHongbao;
import com.qmd.mode.user.UserSpreadDetail;
import com.qmd.mode.util.Setting;
import com.qmd.service.user.*;
import com.qmd.service.util.ListingService;
import com.qmd.util.ConstantUtil;
import com.qmd.util.JsonUtil;
import com.qmd.util.bean.UserAward;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userAwardAction")
@InterceptorRefs({ @InterceptorRef(value = "userVerifyInterceptor"), @InterceptorRef(value = "qmdDefaultStack") })
public class UserAwardAction extends BaseAction {
	private static final long serialVersionUID = -1143933158683010467L;
	private Integer status;// 红包状态
	private BigDecimal hbMoney;// 红包余额
	private BigDecimal sumGetHbMoney;// 累计获得红包奖励
	private BigDecimal sumUseHbMoney;// 累计使用红包奖励
	private BigDecimal sumCashMoney;// 累计现金奖励
	private BigDecimal sumFriendsTzMoney;// 累计好友投资奖励
	private BigDecimal sumTzMoney;// 累计投资奖励
	private Integer sign;
	private String awardType;// 奖励分类
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
	@Action(value = "/award/hbList",results={@Result(name="success", location="/content/h5user/hongbaoList.ftl", type="freemarker")})
	public String hongbao() {
		reloadUser();
		initPage();
		User u = getLoginUser();
		hbMoney = u.getAwardMoney();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", u.getId());
		if (status != null) {
			map.put("status", status);
		}
		UserHongbaoBean bean = new UserHongbaoBean();
		PageBean pb = new PageBean();
		pb.setPageNumber(pager.getPageNumber());
		pb.setPageCount(pager.getPageCount());
		pb.setPageSize(pager.getPageSize());
		pb.setTotalCount(pager.getTotalCount());
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
		bean.setUserHongbaoItem(userHongbaoItem);
		return ajax(JsonUtil.toJson(bean));
	}
	
	private MyFriendBean friendbean ;
	/**
	 * 跳转到‘我的好友’ 页面-分页
	 * 
	 * @return
	 */
	@Action(value = "/award/friendsList",results={@Result(name="success", location="/content/h5user/friendsList.ftl", type="freemarker")})
	public String myFriend() {
		pageNumber=1;
		pageSize = 30;
		initPage();
		Map<String, Object> map = new HashMap<String, Object>();
		MyFriendBean bean = new MyFriendBean();
		Setting setting = listingService.getSetting();
		bean.setSpreadText(setting.getMiniMoney());
		bean.setSpreadTextarea(setting.getOffLineRechargeDes());
		User user = getLoginUser();
		if(user != null){
			map.put("tgOneLevelUserId", user.getId());
			map.put("orderBy", " u.create_date desc ");
			UserSpreadDetail userSpreadDetail = new UserSpreadDetail();
			userSpreadDetail.setTgUserId(user.getId());
			//推荐注册奖励
			BigDecimal totalRegisterMoney = userSpreadDetailService.getRegisterSumMoney(userSpreadDetail);
			bean.setAwardTotalMoneyTj(totalRegisterMoney.toString());
			pager = userService.queryMyFriendsListByMap(pager, map);
			PageBean pb = new PageBean();
			pb.setPageNumber(pager.getPageNumber());
			pb.setPageCount(pager.getPageCount());
			pb.setPageSize(pager.getPageSize());
			pb.setTotalCount(pager.getTotalCount());
			bean.setPageBean(pb);
			List<UserAward> friendList = (List<UserAward>) pager.getResult();
			String tgNo=ConstantUtil.WEB_SITE+"/fd/"+getLoginUser().getTgNo();
			bean.setFriendList(friendList);
			bean.setTgNo(tgNo);
		}
		friendbean = bean;
		return SUCCESS;
	}


	/**
	 * 跳转到‘现金奖励’ 页面
	 * 
	 * @return
	 */
	@Action(value = "/award/awardCash",results={@Result(name="success", location="/content/h5user/awardCashList.ftl", type="freemarker")})
	public String cash() {
		User user = getLoginUser();
		initPage();
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

	public MyFriendBean getFriendbean() {
		return friendbean;
	}

	public void setFriendbean(MyFriendBean friendbean) {
		this.friendbean = friendbean;
	}

}
