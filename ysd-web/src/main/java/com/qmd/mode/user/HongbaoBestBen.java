package com.qmd.mode.user;

import java.util.List;



public class HongbaoBestBen  {

	private List<net.qmdboss.beans.UserHongbao>  userHongbaoList;//红包列表

	private String rcd;// 返回编码 return code
	private String rmg;// 返回信息 return message
	public String bestNum;
	public String bestHbMoney;

	public String getRcd() {
		return rcd;
	}

	public void setRcd(String rcd) {
		this.rcd = rcd;
	}

	public String getRmg() {
		return rmg;
	}

	public void setRmg(String rmg) {
		this.rmg = rmg;
	}

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
