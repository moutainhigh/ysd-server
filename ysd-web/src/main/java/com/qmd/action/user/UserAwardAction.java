package com.qmd.action.user;

import com.qmd.action.base.BaseAction;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAwardDetail;
import com.qmd.mode.user.UserHongbao;
import com.qmd.mode.util.Hongbao;
import com.qmd.mode.util.Setting;
import com.qmd.service.user.*;
import com.qmd.service.util.ListingService;
import com.qmd.util.CommonUtil;
import com.qmd.util.Pager;
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

@Service("userAwardAction")
@InterceptorRefs({ @InterceptorRef(value = "userVerifyInterceptor"), @InterceptorRef(value = "qmdDefaultStack") })
public class UserAwardAction extends BaseAction {

	private static final long serialVersionUID = -1143933158683010467L;

	private Integer status;// 红包状态
	private List<UserHongbao> noList;
	private List<UserHongbao> yesList;
	private List<UserHongbao> overList;

	private BigDecimal hbMoney;// 红包余额
	private BigDecimal sumGetHbMoney;// 累计获得红包奖励
	private BigDecimal sumUseHbMoney;// 累计使用红包奖励

	private BigDecimal sumCashMoney;// 累计现金奖励
	private BigDecimal sumFriendsTzMoney;// 累计好友投资奖励
	private BigDecimal sumTzMoney;// 累计投资奖励

	private Integer sign;
	private String awardType;//奖励分类
	
	private Integer keyHb;// 奖励为2
	private Integer keyHbType;// type 1 好友邀请奖励 2 现金奖励 后台发放

	public Integer getKeyHb() {
		return keyHb;
	}

	public void setKeyHb(Integer keyHb) {
		this.keyHb = keyHb;
	}

	public Integer getKeyHbType() {
		return keyHbType;
	}

	public void setKeyHbType(Integer keyHbType) {
		this.keyHbType = keyHbType;
	}

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

	private Setting setting;
	
	/**
	 * 跳转到‘邀请有奖’ 页面
	 * 
	 * @return
	 */
	@Action(value = "/award/toGetLink", results = { @Result(name = "success", location = "/content/spread/tg/friends_link.ftl", type = "freemarker"),
			@Result(name = "msg", location = "/content/message_page.ftl", type = "freemarker") })
	public String toGetLink() {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = getLoginUser();
		Integer userId = user.getId();
		map.put("tgOneLevelUserId", userId);
		map.put("orderBy", " u.create_date desc ");
		pager = userService.queryMyFriendsListByMap(pager, map);
		
		//设置邀请码
		if(StringUtils.isEmpty(user.getTgNo())){
			user.setTgNo(CommonUtil.getRandomNumAndLetter(6));// 随机生成数

			User checkUser = new User();
			checkUser.setTgNo(user.getTgNo());
			List<User> uList = userService.queryListByObject(checkUser);
			if (uList != null && uList.size() > 0) {
				user.setTgNo(CommonUtil.getRandomNumAndLetter(7));
			}
			userService.update(user);
		}
		setting = listingService.getSetting();
		//获取实名认证红包金额
		hongbao = listingService.getHongbao(3);
		return SUCCESS;
	}

	private Hongbao hongbao;
	/**
	 * 跳转到‘我的好友’ 页面-分页
	 * 
	 * @return
	 */
	@Action(value = "/award/myFriend", results = { @Result(name = "success", location = "/content/spread/tg/friends_my.ftl", type = "freemarker"),
			@Result(name = "msg", location = "/content/message_page.ftl", type = "freemarker") })
	public String myFriend() {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer userId = getLoginUser().getId();
		map.put("tgOneLevelUserId", userId);
		map.put("orderBy", " u.create_date desc ");
		pager = userService.queryMyFriendsListByMap(pager, map);
		return SUCCESS;
	}

	/**
	 * 跳转到‘红包奖励’ 页面-分页
	 * 
	 * @return
	 */
	@Action(value = "/award/hongbao", results = { @Result(name = "success", location = "/content/spread/tg/hongbao.ftl", type = "freemarker"),
			@Result(name = "msg", location = "/content/message_page.ftl", type = "freemarker") })
	public String hongbao() {
		reloadUser();
		User u = getLoginUser();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("orderBy", " t.end_time asc ");
		UserHongbao obj = new UserHongbao();
		obj.setUserId(u.getId());
		if(status == null){
			obj.setStatus(0);
		}else if (status.compareTo(1) ==0){
			obj.setStatus(1);
		}else if (status.compareTo(2) ==0){
			obj.setStatus(2);
		}
		if(pager== null){
			pager = new Pager();
		}
		pager.setPageSize(6);
		pager = userHongbaoService.queryPageByOjbect(obj, pager.getPageSize(), pager.getPageNumber() , "t.create_date desc");
		

		return SUCCESS;
	}
	
	/**
	 * 跳转到‘红包奖励’ 页面-分页
	 * 
	 * @return
	 */
	@Action(value = "/award/hongbaoDetail", results = { @Result(name = "success", location = "/content/spread/tg/hongbao_detail.ftl", type = "freemarker"),
			@Result(name = "msg", location = "/content/message_page.ftl", type = "freemarker") })
	public String hongbaoDetail() {
		reloadUser();
		User u = getLoginUser();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("orderBy", " t.end_time asc ");
		UserAwardDetail obj = new UserAwardDetail();
		obj.setUserId(u.getId());
		obj.setRelateKey("hongbao");
		if (sign != null) {
			if (sign.compareTo(1) == 0) {
				obj.setSignFlg(1);
			} else {
				obj.setSignFlg(-1);
			}
		}
		if(pager== null){
			pager = new Pager();
		}
		pager.setPageSize(8);
		pager = userAwardDetailService.queryPageByOjbect(obj, pager.getPageSize(), pager.getPageNumber() , "t.create_date desc");

		return SUCCESS;
	}
	
	
	/**
	 * 红包详情 红包获取与支出
	 * @return
	 */
	@Action(value = "/award/hongbaoDetailH", results = { @Result(name = "success", location = "/content/spread/tg/hongbao_detail.ftl", type = "freemarker"),
			@Result(name = "msg", location = "/content/message_page.ftl", type = "freemarker") ,
			@Result(name = "new_success", location = "/content/spread/tg/cash.ftl", type = "freemarker")})
	public String hongbaoDetailH() {
		reloadUser();
		User u = getLoginUser();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", u.getId());
		
		Pager pager1 = new Pager();
		pager1.setPageSize(pager.getPageSize());
		pager1.setPageNumber(Integer.valueOf(pager.getPageNumber()) == null ? 1 : pager.getPageNumber());
		
		
		if (sign != null) {
			if (sign.compareTo(1) == 0) {
				map.put("signFlg", 1);
			} else {
				map.put("signFlg", -1);
			}
		}
		if(pager== null){
			pager = new Pager();
		}
		
		if(keyHb==null){
			map.put("keyHb", 1);//对原先的红包页面进行分类.1(money_log_friend_name_email_tender_award    ,money_log_register_award,   offline_reward_hong_bao  ,use_hongbao,)
							//award_jiebiao  award_tuhao)这些属于红包  奖励的 老新一样(tui_detail_award)
		}else{
			map.put("keyHb", 2);
			if(keyHbType==null){
				map.put("keyHbType", 0); //所有奖励
			}else{
				map.put("keyHbType", keyHbType);//分好友邀请奖励 还是后台直接发放
			}
			
			
			
		}
		
		pager.setPageSize(8);
		pager = userAwardDetailService.queryHbDetailList(map, pager.getPageSize(), pager.getPageNumber() );

		if(keyHb==null){
			return SUCCESS;
		}else{
			return "new_success";
		}
		
		
	}
	
	

	/**
	 * 跳转到‘现金奖励’ 页面-分页
	 * 
	 * @return
	 */
	@Action(value = "/award/cash", results = { @Result(name = "success", location = "/content/spread/tg/cash.ftl", type = "freemarker"),
			@Result(name = "msg", location = "/content/message_page.ftl", type = "freemarker") })
	public String cash() {
		User user = getLoginUser();
//		sumTzMoney = userAccountDetailService.getSumMoney(user.getId(), "award_add",null,null);
//		sumFriendsTzMoney = userAccountDetailService.getSumMoney(user.getId(), "tui_detail_award",null,null);
//		
//		sumCashMoney = sumTzMoney.add(sumFriendsTzMoney);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId",  user.getId());
		if (sign != null) {
			if (sign.compareTo(-1) == 0) {
				map.put("type", "award_add");
			}else{
				map.put("type", "tui_detail_award");
			}
		} else {
			String[] strarray = {"award_add","tui_detail_award"};
			map.put("strarray", strarray);
		}
		pager = this.userAccountService.getAccountDetailByUserId(pager, map);
		return SUCCESS;
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

	public List<UserHongbao> getNoList() {
		return noList;
	}

	public void setNoList(List<UserHongbao> noList) {
		this.noList = noList;
	}

	public List<UserHongbao> getYesList() {
		return yesList;
	}

	public void setYesList(List<UserHongbao> yesList) {
		this.yesList = yesList;
	}

	public List<UserHongbao> getOverList() {
		return overList;
	}

	public void setOverList(List<UserHongbao> overList) {
		this.overList = overList;
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

	public Hongbao getHongbao() {
		return hongbao;
	}

	public void setHongbao(Hongbao hongbao) {
		this.hongbao = hongbao;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

}
