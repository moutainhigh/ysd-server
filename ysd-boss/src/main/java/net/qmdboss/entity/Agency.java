package net.qmdboss.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
/**
 * 平台服务商信息
 * @author Xsf
 *
 */
@Entity
public class Agency extends BaseEntity {

	private static final long serialVersionUID = 3755356427108866107L;
	//基本信息
    private String companyName;//【服务商名称】

	private String linkman;//联系人
	private String linkmanMobile;//联系人手机
	
    private String servicePromise;//服务承诺(checkbox)
    private String mainBusiness;//主营业务
    private String remark;//备注
    
    //工商注册信息
    
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
    
    //子页面管理
	  private String logo;//公司LOGO+
	  private String logo1;//LOGO1-顶部广告图+
	  private String logo2;//LOGO2
	  private String logo3;//LOGO3
	  private String img;//批量上传图片
	  private String introduction;//公司介绍
	  
	  private String subpagePhone;//联系电话【子页面管理】+
	  private String subpageAddress;//联系地址【子页面管理】+
  
  
  private Integer userid;//用户ID
  private Integer orderList;//排序
    
    private Integer agencydbid;//担保服务商ID
    
    private Integer tasteRule;//体验 1可用
    private Integer flowRule;//债权流转 1可用
    private Integer creditRule;//信用 1可用
    private Integer pledgeRule;//抵押质押 1可用
    private String signImage;//合同签章上传
	private BigDecimal tasteMoney;//注册送体验金
	

	private Set<Fangkuan> fangkuanSet = new HashSet<Fangkuan>();
	private Set<BorrowAccountDetail> borrowAccountDetailSet = new HashSet<BorrowAccountDetail>();
	private Set<BorrowRecharge> borrowRechargeSet = new HashSet<BorrowRecharge>();

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLogo1() {
		return logo1;
	}

	public void setLogo1(String logo1) {
		this.logo1 = logo1;
	}

	public String getLogo2() {
		return logo2;
	}

	public void setLogo2(String logo2) {
		this.logo2 = logo2;
	}

	public String getLogo3() {
		return logo3;
	}

	public void setLogo3(String logo3) {
		this.logo3 = logo3;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getSubpagePhone() {
		return subpagePhone;
	}

	public void setSubpagePhone(String subpagePhone) {
		this.subpagePhone = subpagePhone;
	}

	public String getSubpageAddress() {
		return subpageAddress;
	}

	public void setSubpageAddress(String subpageAddress) {
		this.subpageAddress = subpageAddress;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
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

	@OneToMany(mappedBy = "agency", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Set<Fangkuan> getFangkuanSet() {
		return fangkuanSet;
	}

	public void setFangkuanSet(Set<Fangkuan> fangkuanSet) {
		this.fangkuanSet = fangkuanSet;
	}
	
	@OneToMany(mappedBy = "agency", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Set<BorrowAccountDetail> getBorrowAccountDetailSet() {
		return borrowAccountDetailSet;
	}

	public void setBorrowAccountDetailSet(Set<BorrowAccountDetail> borrowAccountDetailSet) {
		this.borrowAccountDetailSet = borrowAccountDetailSet;
	}

	@OneToMany(mappedBy = "agency", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Set<BorrowRecharge> getBorrowRechargeSet() {
		return borrowRechargeSet;
	}

	public void setBorrowRechargeSet(Set<BorrowRecharge> borrowRechargeSet) {
		this.borrowRechargeSet = borrowRechargeSet;
	}
	    
}
