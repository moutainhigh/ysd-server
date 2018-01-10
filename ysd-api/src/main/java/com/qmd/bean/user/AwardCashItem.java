package com.qmd.bean.user;

import java.util.Date;


public class AwardCashItem {

	private Date createDate;
	private String type;
	private String typeShow;
	private String money;
	private String remark;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTypeShow() {
		return typeShow;
	}

	public void setTypeShow(String typeShow) {
		this.typeShow = typeShow;
	}
}
