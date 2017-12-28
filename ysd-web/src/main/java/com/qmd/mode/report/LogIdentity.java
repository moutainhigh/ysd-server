package com.qmd.mode.report;

import java.io.Serializable;
import java.util.Date;

/**
 * 实名认证记录表
 * @author Administrator
 *
 */
public class LogIdentity implements Serializable {

	private static final long serialVersionUID = -3558090867606834187L;

	private Integer id;
	private Date createDate;
	private Date modifyDate;

	private String msgStatus;
	private String msgValue;
	private String msgQuerySeq;

	private String policeStatus;
	private String policeValue;

	private String name;
	private String identitycard;
	private String compStatus;
	private String compResult;
	private String region;
	private String birthday;
	private String sex;
	private String returnXml;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}

	public String getMsgValue() {
		return msgValue;
	}

	public void setMsgValue(String msgValue) {
		this.msgValue = msgValue;
	}

	public String getMsgQuerySeq() {
		return msgQuerySeq;
	}

	public void setMsgQuerySeq(String msgQuerySeq) {
		this.msgQuerySeq = msgQuerySeq;
	}

	public String getPoliceStatus() {
		return policeStatus;
	}

	public void setPoliceStatus(String policeStatus) {
		this.policeStatus = policeStatus;
	}

	public String getPoliceValue() {
		return policeValue;
	}

	public void setPoliceValue(String policeValue) {
		this.policeValue = policeValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentitycard() {
		return identitycard;
	}

	public void setIdentitycard(String identitycard) {
		this.identitycard = identitycard;
	}

	public String getCompStatus() {
		return compStatus;
	}

	public void setCompStatus(String compStatus) {
		this.compStatus = compStatus;
	}

	public String getCompResult() {
		return compResult;
	}

	public void setCompResult(String compResult) {
		this.compResult = compResult;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getReturnXml() {
		return returnXml;
	}

	public void setReturnXml(String returnXml) {
		this.returnXml = returnXml;
	}

}