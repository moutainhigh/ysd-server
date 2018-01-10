package com.qmd.bean.borrow;

import com.qmd.bean.BaseBean;
import com.qmd.bean.user.UserHongbaoItem;

import java.util.List;

public class BorrowPoputMessage extends BaseBean{

	private static final long serialVersionUID = -360158109477419687L;

	public BorrowPoputMessage() {
		setRcd("R0001");
	}
	
	private Integer id;//项目ID
	private String tenderMoney;//投资金额
	
	private String awardScale;//使用红包比例

	private List<UserHongbaoItem> userHongbaoItem;//红包列表

	private String ableMoney;//账户可用金额
	private String awardMoney;//账户可用红包
	private String tasteMoney;//体验金账户
	private String isDxb;// 是否是定向标
	
	public String getAwardMoney() {
		return awardMoney;
	}
	public void setAwardMoney(String awardMoney) {
		this.awardMoney = awardMoney;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTenderMoney() {
		return tenderMoney;
	}
	public void setTenderMoney(String tenderMoney) {
		this.tenderMoney = tenderMoney;
	}
	public String getAwardScale() {
		return awardScale;
	}
	public void setAwardScale(String awardScale) {
		this.awardScale = awardScale;
	}
	public List<UserHongbaoItem> getUserHongbaoItem() {
		return userHongbaoItem;
	}
	public void setUserHongbaoItem(List<UserHongbaoItem> userHongbaoItem) {
		this.userHongbaoItem = userHongbaoItem;
	}
	public String getAbleMoney() {
		return ableMoney;
	}
	public void setAbleMoney(String ableMoney) {
		this.ableMoney = ableMoney;
	}
	public String getIsDxb() {
		return isDxb;
	}
	public void setIsDxb(String isDxb) {
		this.isDxb = isDxb;
	}
	public String getTasteMoney() {
		return tasteMoney;
	}
	public void setTasteMoney(String tasteMoney) {
		this.tasteMoney = tasteMoney;
	}
	
	
	
	
}
