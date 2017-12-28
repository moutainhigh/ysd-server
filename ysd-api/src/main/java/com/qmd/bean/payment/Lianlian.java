package com.qmd.bean.payment;

import com.qmd.bean.BaseBean;

public class Lianlian extends BaseBean  {

	private static final long serialVersionUID = -5318105169746259482L;

	public Lianlian() {
		setRcd("R0001");
	}
	
	private String postUrl;//提交地址
	private String reqData;//组合参数

	public String getPostUrl() {
		return postUrl;
	}
	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}
	public String getReqData() {
		return reqData;
	}
	public void setReqData(String reqData) {
		this.reqData = reqData;
	}
	
	
	
}
