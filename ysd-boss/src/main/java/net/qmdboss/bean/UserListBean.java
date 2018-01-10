package net.qmdboss.bean;

import java.util.Date;

public class UserListBean {

	private Integer id;
	private Integer typeId;
	private Integer memberType;
	private String appType;
	private String username;
	private String realName;
	private Date createDate;
	private String realStatusStr;
	private String placeName;// 渠道名称
	private Boolean isEnabled;
	private Boolean isLock;

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}
	
	


	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getRealName() {
		return realName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getRealStatusStr() {
		return realStatusStr;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setRealStatusStr(String realStatusStr) {
		this.realStatusStr = realStatusStr;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public Boolean getIsLock() {
		return isLock;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setIsLock(Boolean isLock) {
		this.isLock = isLock;
	}

}
