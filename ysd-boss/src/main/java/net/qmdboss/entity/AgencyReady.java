package net.qmdboss.entity;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 平台服务商信息
 * @author Xsf
 *
 */
@Entity
public class AgencyReady extends BaseEntity {

	private static final long serialVersionUID = 3755356427108866107L;

	
	 //基本信息
    private String companyName;//公司名称【服务商名称】
    
    private String province;//省
    private String city;//市
    private String area;//区
    private String areaStore;//所在地区
    private String contactPhone;//联系电话【基本信息】
    private String contactAddress;//联系地址【基本信息】
    private String uemail;

	private String linkman;//联系人
	private String linkmanMobile;//联系人手机
	
    private String servicePromise;//服务承诺(checkbox)
    private String mainBusiness;//主营业务
    private String remark;//备注
    
    //工商注册信息
    private String urealname;//法人姓名
    private String cardPic1;//身份证正面图片
    private String cardPic2;//身份证反面图片
    
    private String privateCharter;//营业执照号码
    private String privateCharterImg;//营业执照

	private String taxRegistration;//税务登记号
	private String privateTaxImg;//税务登记证

	private String organization;//机构代码证号
	private String privateOrganizationImg;//机构代码证
	
	private String accountLicenceImg;//开户许可证
	

    private Date establishDate;//成立日期
    private Date businessStart;//营业期限开始+
    private Date businessEnd;//营业期限结束+
    
    private String address;//注册地址
    private BigDecimal capital;//注册资本
    private String scope;//经营范围+
    
    private Integer status;//状态【0：失败；1：成功；2：审核中】
    
    //网站账号信息
    private String uusername;
    private String upassword;
//    private String upaypassword;
    private String urandom ;//保存密码的随机数
    

    private Integer agencydbid;//担保服务商ID
    private Integer tasteRule;//体验 1可用
    private Integer flowRule;//债权流转 1可用
    private Integer creditRule;//信用 1可用
    private Integer pledgeRule;//抵押质押 1可用
    private String signImage;//合同签章上传
	private BigDecimal tasteMoney;//注册送体验金


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getAreaStore() {
		return areaStore;
	}


	public void setAreaStore(String areaStore) {
		this.areaStore = areaStore;
	}


	public String getContactPhone() {
		return contactPhone;
	}


	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}


	public String getContactAddress() {
		return contactAddress;
	}


	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}


	public String getUemail() {
		return uemail;
	}


	public void setUemail(String uemail) {
		this.uemail = uemail;
	}


	public String getLinkman() {
		return linkman;
	}


	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}


	public String getLinkmanMobile() {
		return linkmanMobile;
	}


	public void setLinkmanMobile(String linkmanMobile) {
		this.linkmanMobile = linkmanMobile;
	}


	public String getServicePromise() {
		return servicePromise;
	}


	public void setServicePromise(String servicePromise) {
		this.servicePromise = servicePromise;
	}


	public String getMainBusiness() {
		return mainBusiness;
	}


	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getUrealname() {
		return urealname;
	}


	public void setUrealname(String urealname) {
		this.urealname = urealname;
	}


	public String getCardPic1() {
		return cardPic1;
	}


	public void setCardPic1(String cardPic1) {
		this.cardPic1 = cardPic1;
	}


	public String getCardPic2() {
		return cardPic2;
	}


	public void setCardPic2(String cardPic2) {
		this.cardPic2 = cardPic2;
	}


	public String getPrivateCharter() {
		return privateCharter;
	}


	public void setPrivateCharter(String privateCharter) {
		this.privateCharter = privateCharter;
	}


	public String getPrivateCharterImg() {
		return privateCharterImg;
	}


	public void setPrivateCharterImg(String privateCharterImg) {
		this.privateCharterImg = privateCharterImg;
	}


	public String getTaxRegistration() {
		return taxRegistration;
	}


	public void setTaxRegistration(String taxRegistration) {
		this.taxRegistration = taxRegistration;
	}


	public String getPrivateTaxImg() {
		return privateTaxImg;
	}


	public void setPrivateTaxImg(String privateTaxImg) {
		this.privateTaxImg = privateTaxImg;
	}


	public String getOrganization() {
		return organization;
	}


	public void setOrganization(String organization) {
		this.organization = organization;
	}


	public String getPrivateOrganizationImg() {
		return privateOrganizationImg;
	}


	public void setPrivateOrganizationImg(String privateOrganizationImg) {
		this.privateOrganizationImg = privateOrganizationImg;
	}


	public String getAccountLicenceImg() {
		return accountLicenceImg;
	}


	public void setAccountLicenceImg(String accountLicenceImg) {
		this.accountLicenceImg = accountLicenceImg;
	}


	public Date getEstablishDate() {
		return establishDate;
	}


	public void setEstablishDate(Date establishDate) {
		this.establishDate = establishDate;
	}


	public Date getBusinessStart() {
		return businessStart;
	}


	public void setBusinessStart(Date businessStart) {
		this.businessStart = businessStart;
	}


	public Date getBusinessEnd() {
		return businessEnd;
	}


	public void setBusinessEnd(Date businessEnd) {
		this.businessEnd = businessEnd;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public BigDecimal getCapital() {
		return capital;
	}


	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}


	public String getScope() {
		return scope;
	}


	public void setScope(String scope) {
		this.scope = scope;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getUusername() {
		return uusername;
	}


	public void setUusername(String uusername) {
		this.uusername = uusername;
	}


	public String getUpassword() {
		return upassword;
	}


	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}


	public String getUrandom() {
		return urandom;
	}


	public void setUrandom(String urandom) {
		this.urandom = urandom;
	}


	public Integer getAgencydbid() {
		return agencydbid;
	}


	public void setAgencydbid(Integer agencydbid) {
		this.agencydbid = agencydbid;
	}


	public Integer getTasteRule() {
		return tasteRule;
	}


	public void setTasteRule(Integer tasteRule) {
		this.tasteRule = tasteRule;
	}


	public BigDecimal getTasteMoney() {
		return tasteMoney;
	}


	public void setTasteMoney(BigDecimal tasteMoney) {
		this.tasteMoney = tasteMoney;
	}


	public Integer getFlowRule() {
		return flowRule;
	}


	public void setFlowRule(Integer flowRule) {
		this.flowRule = flowRule;
	}


	public Integer getCreditRule() {
		return creditRule;
	}


	public void setCreditRule(Integer creditRule) {
		this.creditRule = creditRule;
	}


	public Integer getPledgeRule() {
		return pledgeRule;
	}


	public void setPledgeRule(Integer pledgeRule) {
		this.pledgeRule = pledgeRule;
	}


	public String getSignImage() {
		return signImage;
	}


	public void setSignImage(String signImage) {
		this.signImage = signImage;
	}
	
	
	    
}
