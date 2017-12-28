package net.qmdboss.DTO;

import java.util.Date;

public class UserSpreadInviteAwardDTO {
	private String username;
	private String realname;
	private String borrowName;
	private Integer borrowStatus;
	private String borrowInterest;
	private String inviteName;
	private String inviteRealName;
	private Date createDate;
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
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getInviteName() {
		return inviteName;
	}
	public void setInviteName(String inviteName) {
		this.inviteName = inviteName;
	}
	public Integer getBorrowStatus() {
		return borrowStatus;
	}
	public void setBorrowStatus(Integer borrowStatus) {
		this.borrowStatus = borrowStatus;
	}
	public String getBorrowInterest() {
		return borrowInterest;
	}
	public void setBorrowInterest(String borrowInterest) {
		this.borrowInterest = borrowInterest;
	}
	public String getInviteRealName() {
		return inviteRealName;
	}
	public void setInviteRealName(String inviteRealName) {
		this.inviteRealName = inviteRealName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
	
	
	
	
	

}
