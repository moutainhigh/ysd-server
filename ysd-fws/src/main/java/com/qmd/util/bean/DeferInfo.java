package com.qmd.util.bean;

public class DeferInfo {

	private Integer status;//【0：不可申请；1：可申请；2未知问题】
	private String title;//提示信息
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
