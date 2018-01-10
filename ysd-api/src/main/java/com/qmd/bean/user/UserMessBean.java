package com.qmd.bean.user;

import com.qmd.bean.BaseBean;

public class UserMessBean extends BaseBean {

	private static final long serialVersionUID = -7683603982464447706L;

	public UserMessBean() {
		setRcd("R0001");
	}

	private String id;
	private String username;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
