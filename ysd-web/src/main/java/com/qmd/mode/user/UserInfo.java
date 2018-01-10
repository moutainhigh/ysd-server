package com.qmd.mode.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户详细信息表
 * @author xsf
 *
 */
public class UserInfo  implements Serializable{

	private static final long serialVersionUID = 1874669328694601703L;
	public static final String USER_INFO_ID_SESSION_NAME = "sessionUserInfoId";// 保存登录会员详情ID的Session名称
	
	private Integer id;//编号
	private Date createDate;//添加时间
    private Date modifyDate;//修改时间
	private Integer userId;//用户ID
	//个人资料
	private String address;//联系地址+++++
	private String others;//主营业务+++++++
	
	//新加 2016-03-30
	private Date clsj;//成立时间
	private Date yyqxStart;//营业期限-开始
	private Date yyqxEnd;//营业期限-结束
	//企业资料
	private String privateName;//企业名称 【新加】
	private String privateCharterImg;//营业执照【新加】
	private String privateTaxImg;//税务登记证【新加】
	private String privateOrganizationImg;//组织机构代码证【新加】
	private String registration;//企业登记号---
	private String taxRegistration;//税务登记号---
	private String organization;//组织机构号---
	private String registeredCapital;//注册资金----
	private String linkman;//联系人---
	private String privatePhone;//企业联系人电话---
	private String addIp;//添加IP
	
	public Integer getId() {
		return id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public Integer getUserId() {
		return userId;
	}
	public String getAddress() {
		return address;
	}
	public String getOthers() {
		return others;
	}
	public Date getClsj() {
		return clsj;
	}
	public Date getYyqxStart() {
		return yyqxStart;
	}
	public Date getYyqxEnd() {
		return yyqxEnd;
	}
	public String getPrivateName() {
		return privateName;
	}
	public String getPrivateCharterImg() {
		return privateCharterImg;
	}
	public String getPrivateTaxImg() {
		return privateTaxImg;
	}
	public String getPrivateOrganizationImg() {
		return privateOrganizationImg;
	}
	public String getRegistration() {
		return registration;
	}
	public String getTaxRegistration() {
		return taxRegistration;
	}
	public String getOrganization() {
		return organization;
	}
	public String getRegisteredCapital() {
		return registeredCapital;
	}
	public String getLinkman() {
		return linkman;
	}
	public String getPrivatePhone() {
		return privatePhone;
	}
	public String getAddIp() {
		return addIp;
	}
	public String getUpdateIp() {
		return updateIp;
	}
	public Integer getHits() {
		return hits;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	public void setClsj(Date clsj) {
		this.clsj = clsj;
	}
	public void setYyqxStart(Date yyqxStart) {
		this.yyqxStart = yyqxStart;
	}
	public void setYyqxEnd(Date yyqxEnd) {
		this.yyqxEnd = yyqxEnd;
	}
	public void setPrivateName(String privateName) {
		this.privateName = privateName;
	}
	public void setPrivateCharterImg(String privateCharterImg) {
		this.privateCharterImg = privateCharterImg;
	}
	public void setPrivateTaxImg(String privateTaxImg) {
		this.privateTaxImg = privateTaxImg;
	}
	public void setPrivateOrganizationImg(String privateOrganizationImg) {
		this.privateOrganizationImg = privateOrganizationImg;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	public void setTaxRegistration(String taxRegistration) {
		this.taxRegistration = taxRegistration;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public void setPrivatePhone(String privatePhone) {
		this.privatePhone = privatePhone;
	}
	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}
	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	private String updateIp;//修改IP
	private Integer hits;//点击次数
	
}
