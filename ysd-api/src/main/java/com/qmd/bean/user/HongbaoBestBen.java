package com.qmd.bean.user;

import com.qmd.bean.BaseBean;

import java.util.List;

public class HongbaoBestBen extends BaseBean {

	private List<net.qmdboss.beans.UserHongbao>  userHongbaoList;//红包列表

	
	public String bestNum;
	public String bestHbMoney;

	public String getBestNum() {
		return bestNum;
	}

	public void setBestNum(String bestNum) {
		this.bestNum = bestNum;
	}

	public String getBestHbMoney() {
		return bestHbMoney;
	}

	public void setBestHbMoney(String bestHbMoney) {
		this.bestHbMoney = bestHbMoney;
	}

	public List<net.qmdboss.beans.UserHongbao> getUserHongbaoList() {
		return userHongbaoList;
	}

	public void setUserHongbaoList(
			List<net.qmdboss.beans.UserHongbao> userHongbaoList) {
		this.userHongbaoList = userHongbaoList;
	}

	

	
	
	
}
