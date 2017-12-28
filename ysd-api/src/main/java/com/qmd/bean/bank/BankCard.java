package com.qmd.bean.bank;

import java.io.Serializable;


public class BankCard implements Serializable  {

	private static final long serialVersionUID = -7683603982464447706L;

	private String bankId;
	private String bankName;
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	
	
}
