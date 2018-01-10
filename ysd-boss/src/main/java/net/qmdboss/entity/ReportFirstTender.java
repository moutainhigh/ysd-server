package net.qmdboss.entity;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class ReportFirstTender extends BaseEntity {

	private static final long serialVersionUID = 1002246509403593476L;
	private Integer userId;//投资人ID
	private String phone;//投资人手机号
	private Date tenderDate;//投资时间
	private BigDecimal money;//投资金额
	private String account;//实际投资金额
	
	private Integer borrowId;//项目ID
	private String borrowName;//项目名称
	private Integer placeChilderId;//渠道活动ID
	private String placeChilderName;//渠道活动名称
	private String timeLimit;
	private int clientType;
	private String  hongbaoAmount;
	
	

	public String getHongbaoAmount() {
		return hongbaoAmount;
	}



	public void setHongbaoAmount(String hongbaoAmount) {
		this.hongbaoAmount = hongbaoAmount;
	}



	public String getTimeLimit() {
		return timeLimit;
	}
	
	

	public int getClientType() {
		return clientType;
	}



	public void setClientType(int clientType) {
		this.clientType = clientType;
	}



	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getPhone() {
		return phone;
	}

	public Date getTenderDate() {
		return tenderDate;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public Integer getPlaceChilderId() {
		return placeChilderId;
	}

	public String getPlaceChilderName() {
		return placeChilderName;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setTenderDate(Date tenderDate) {
		this.tenderDate = tenderDate;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public void setPlaceChilderId(Integer placeChilderId) {
		this.placeChilderId = placeChilderId;
	}

	public void setPlaceChilderName(String placeChilderName) {
		this.placeChilderName = placeChilderName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}


	
	

}
