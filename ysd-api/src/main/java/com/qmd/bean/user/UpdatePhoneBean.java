package com.qmd.bean.user;

import com.qmd.bean.BaseBean;

public class UpdatePhoneBean extends BaseBean {

	private static final long serialVersionUID = -7683603982464447706L;

	
	private String id;
	private String rcd;
	private String phone;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRcd() {
		return rcd;
	}
	public void setRcd(String rcd) {
		this.rcd = rcd;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
