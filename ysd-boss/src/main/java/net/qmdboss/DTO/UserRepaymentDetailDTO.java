package net.qmdboss.DTO;

import java.util.Date;

public class UserRepaymentDetailDTO {
	
	private int id;
	private String username;
	private String borrowName;
	private String timeLimit;
	private int borrowDivides;
	private String repaymentAccount;
	private Date repaymentDate;
	private String placeName;
	private int borrowPeriods;
	private String status;
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
	public String getBorrowName() {
		return borrowName;
	}
	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public int getBorrowDivides() {
		return borrowDivides;
	}
	public void setBorrowDivides(int borrowDivides) {
		this.borrowDivides = borrowDivides;
	}

	public int getBorrowPeriods() {
		return borrowPeriods;
	}
	public void setBorrowPeriods(int borrowPeriods) {
		this.borrowPeriods = borrowPeriods;
	}
	public String getRepaymentAccount() {
		return repaymentAccount;
	}
	public void setRepaymentAccount(String repaymentAccount) {
		this.repaymentAccount = repaymentAccount;
	}
	public Date getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	
	


}
