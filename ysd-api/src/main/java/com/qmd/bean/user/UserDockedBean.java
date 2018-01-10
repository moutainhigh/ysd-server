package com.qmd.bean.user;

import java.io.Serializable;
import java.util.Date;

public class UserDockedBean implements Serializable {

	private static final long serialVersionUID = -4685847152264946796L;

	private Boolean success; // success参数，取值有true和false两种情况，分别代表成功与失败
	private String msg; // msg参数是错误具体描述
	private String phone;
	private String username;
	private String email;
	private Date regTime;
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}


	
}
