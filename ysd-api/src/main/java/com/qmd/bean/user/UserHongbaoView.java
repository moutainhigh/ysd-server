/**
 * 
 */
package com.qmd.bean.user;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户红包视图：展示数据 
 * @author zdl
 */
public class UserHongbaoView {
	
	private BigDecimal money;// 原始面额
	private Integer status;// 状态 0未使用1使用2已过期
	private String name;//红包名字
	private Date endTime;// 到期时间
	private String endTimeStr;//到期时间 格式化：xxxx年xx月xx日
	private Integer limitStart;// 项目期限
	private Integer investFullMomey;//红包满多少可用
	
	
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		if(endTime!=null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
			this.endTimeStr = format.format(endTime);
		}
		this.endTime = endTime;
	}
	public String getEndTimeStr() {
		if(this.endTime!=null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
			this.endTimeStr = format.format(endTime);
		}
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public Integer getLimitStart() {
		return limitStart;
	}
	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}
	public Integer getInvestFullMomey() {
		return investFullMomey;
	}
	public void setInvestFullMomey(Integer investFullMomey) {
		this.investFullMomey = investFullMomey;
	}
}
