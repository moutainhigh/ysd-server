package com.qmd.bean.user;

import com.qmd.bean.BaseBean;

public class CheckUserBean extends BaseBean {

	private static final long serialVersionUID = -7683603982464447706L;

	public CheckUserBean() {
		setRcd("R0001");
	}

	private String phoneState;
	private String codeState;
	private String cardState;

	public String getPhoneState() {
		return phoneState;
	}
	public void setPhoneState(String phoneState) {
		this.phoneState = phoneState;
	}
	public String getCodeState() {
		return codeState;
	}
	public void setCodeState(String codeState) {
		this.codeState = codeState;
	}
	public String getCardState() {
		return cardState;
	}
	public void setCardState(String cardState) {
		this.cardState = cardState;
	}

	
}
