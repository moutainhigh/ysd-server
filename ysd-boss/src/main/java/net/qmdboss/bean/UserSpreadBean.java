package net.qmdboss.bean;

import java.util.Date;

public class UserSpreadBean {

	private Integer id;
	private String username;
	private String phone;
	private String realname;
	private Date createDate;
	private String spreadUsername;
	private String spreadPhone;
	private String spreadRealname;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSpreadUsername() {
		return spreadUsername;
	}

	public void setSpreadUsername(String spreadUsername) {
		this.spreadUsername = spreadUsername;
	}

	public String getSpreadPhone() {
		return spreadPhone;
	}

	public void setSpreadPhone(String spreadPhone) {
		this.spreadPhone = spreadPhone;
	}

	public String getSpreadRealname() {
		return spreadRealname;
	}

	public void setSpreadRealname(String spreadRealname) {
		this.spreadRealname = spreadRealname;
	}

}
