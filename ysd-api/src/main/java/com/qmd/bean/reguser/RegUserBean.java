package com.qmd.bean.reguser;

import com.qmd.bean.BaseBean;

public class RegUserBean extends BaseBean {

	private static final long serialVersionUID = -7683603982464447706L;

	public RegUserBean() {
		setRcd("R0001");
	}

	private Integer userId;
	private String token;


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
