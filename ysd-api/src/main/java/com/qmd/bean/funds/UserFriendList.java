package com.qmd.bean.funds;

import com.qmd.bean.BaseBean;
import com.qmd.bean.PageBean;

import java.util.List;

public class UserFriendList extends BaseBean  {


	private static final long serialVersionUID = -6891650427229705239L;
	public UserFriendList() {
		setRcd("R0001");
	}
	
	private PageBean pageBean;
	private List<UserFriend> userFriendList;

	private String awardTotalMoneyTj;//推荐注册红包
	private String awardTotalMoneyTz;//好友投资红包
	
	
	
	public PageBean getPageBean() {
		return pageBean;
	}
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
	public List<UserFriend> getUserFriendList() {
		return userFriendList;
	}
	public void setUserFriendList(List<UserFriend> userFriendList) {
		this.userFriendList = userFriendList;
	}
	public String getAwardTotalMoneyTj() {
		return awardTotalMoneyTj;
	}
	public void setAwardTotalMoneyTj(String awardTotalMoneyTj) {
		this.awardTotalMoneyTj = awardTotalMoneyTj;
	}
	public String getAwardTotalMoneyTz() {
		return awardTotalMoneyTz;
	}
	public void setAwardTotalMoneyTz(String awardTotalMoneyTz) {
		this.awardTotalMoneyTz = awardTotalMoneyTz;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
