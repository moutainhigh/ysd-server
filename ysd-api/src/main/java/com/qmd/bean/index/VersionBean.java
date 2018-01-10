package com.qmd.bean.index;

import com.qmd.bean.BaseBean;

public class VersionBean extends BaseBean {

	private static final long serialVersionUID = 4457162693528931372L;

	public VersionBean() {
		setRcd("R0001");
	}

	public Integer versionCode;//版本更新次数
	public String versionName;//版本号
	public String url;//下载地址
	
	public Integer getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
