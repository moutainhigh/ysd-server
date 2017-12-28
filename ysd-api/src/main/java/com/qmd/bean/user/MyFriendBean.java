package com.qmd.bean.user;

import com.qmd.bean.BaseBean;
import com.qmd.bean.PageBean;
import com.qmd.util.bean.UserAward;

import java.util.List;

public class MyFriendBean extends BaseBean {

	private static final long serialVersionUID = 2046325044996790451L;

	public MyFriendBean() {
		setRcd("R0001");
	}
	private String awardTotalMoneyTj;//累计推荐注册红包
	private String tgNo;//推广链接
	
	private String spreadText;//邀请好友文字
	private String spreadTextarea;//邀请好友奖励说明
	
	private List<UserAward> friendList;
	private PageBean pageBean;

	public List<UserAward> getFriendList() {
		return friendList;
	}

	public void setFriendList(List<UserAward> friendList) {
		this.friendList = friendList;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public String getAwardTotalMoneyTj() {
		return awardTotalMoneyTj;
	}

	public void setAwardTotalMoneyTj(String awardTotalMoneyTj) {
		this.awardTotalMoneyTj = awardTotalMoneyTj;
	}

	public String getTgNo() {
		return tgNo;
	}

	public void setTgNo(String tgNo) {
		this.tgNo = tgNo;
	}

	public String getSpreadText() {
		return spreadText;
	}

	public void setSpreadText(String spreadText) {
		this.spreadText = spreadText;
	}

	public String getSpreadTextarea() {
		return spreadTextarea;
	}

	public void setSpreadTextarea(String spreadTextarea) {
		this.spreadTextarea = spreadTextarea;
	}
	
	
	
}
