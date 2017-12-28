package net.qmdboss.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class UserHongbaoBossDTO {

	 private int id;
	 private String username;
	 private String hongbaoName;
	 private BigDecimal hongbaoMoney;
	 private  int expDate ;

	 private Date endTime;
	 private int limitEnd;
	 private int limitStart;
	 private int status;
	 private Date modifyDate;
	 
	 private int investFullMomey;
	 private String borrowName ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHongbaoName() {
		return hongbaoName;
	}
	public void setHongbaoName(String hongbaoName) {
		this.hongbaoName = hongbaoName;
	}
	public BigDecimal getHongbaoMoney() {
		return hongbaoMoney;
	}
	public void setHongbaoMoney(BigDecimal hongbaoMoney) {
		this.hongbaoMoney = hongbaoMoney;
	}
	public int getExpDate() {
		return expDate;
	}
	public void setExpDate(int expDate) {
		this.expDate = expDate;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	
	public int getLimitStart() {
		return limitStart;
	}
	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}
	public int getLimitEnd() {
		return limitEnd;
	}
	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public int getInvestFullMomey() {
		return investFullMomey;
	}
	public void setInvestFullMomey(int investFullMomey) {
		this.investFullMomey = investFullMomey;
	}
	public String getBorrowName() {
		return borrowName;
	}
	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	} 
	
	
	
	
}
