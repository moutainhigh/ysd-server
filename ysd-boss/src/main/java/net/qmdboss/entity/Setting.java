package net.qmdboss.entity;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * 网站基本参数设置
 * @author Xsf
 *
 */
@Entity
public class Setting extends BaseEntity{
	
	
	private static final long serialVersionUID = 7130589286205971916L;
	private String name;//网站名称
	private String companyName;//公司名称
	private String metaKeywords;//页面关键词
	private String metaDescription;//页面描述
	private String url;//网址
	private String logoPath;//LOGO
	private String phone;//服务电话
	private String qq;//qq号用“,”号隔开
	private String officialQq;//官方qq群号用“,”号隔开
	private String address;//公司地址
	private String email;//公司邮箱
	private String zipCode;//邮编
	private String certtext;//备案号
	
	private String sensitiveKeyWord;//过滤关键词","
	
	private String watermarkImagePath;//水印图片
	private String watermarkPosition;//水印位置
	private Integer watermarkAlpha;//水印透明度
	
	private Boolean isLoginFailureLock;//是否自动锁定账号
	private Integer loginFailureLockCount;//连续登录失败最大次数
	private Integer loginFailureLockTime;//自动解锁时间【分钟】
	
	private String smtpFromMail;//发件人邮箱
	private String smtpHost;// SMTP服务器地址
	private Integer smtpPort;//SMTP服务器端口
	private String smtpUsername;//SMTP用户名
	private String smtpPassword;//SMTP密码
	
	private String miniMoney;//最低金额
	private String offLineRechargeDes;//线下充值描述
	
	private BigDecimal depositMoney;//保证金账户
	private BigDecimal feeMoney;//收益金账户
	
	private String xkz;//增值业务电信经营许可证
	private String copyright;//版权所有
	private String ywPhone;//业务电话
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getMetaKeywords() {
		return metaKeywords;
	}
	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}
	public String getMetaDescription() {
		return metaDescription;
	}
	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getOfficialQq() {
		return officialQq;
	}
	public void setOfficialQq(String officialQq) {
		this.officialQq = officialQq;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCerttext() {
		return certtext;
	}
	public void setCerttext(String certtext) {
		this.certtext = certtext;
	}
	public String getWatermarkImagePath() {
		return watermarkImagePath;
	}
	public void setWatermarkImagePath(String watermarkImagePath) {
		this.watermarkImagePath = watermarkImagePath;
	}
	public String getWatermarkPosition() {
		return watermarkPosition;
	}
	public void setWatermarkPosition(String watermarkPosition) {
		this.watermarkPosition = watermarkPosition;
	}
	public Integer getWatermarkAlpha() {
		return watermarkAlpha;
	}
	public void setWatermarkAlpha(Integer watermarkAlpha) {
		this.watermarkAlpha = watermarkAlpha;
	}
	public Boolean getIsLoginFailureLock() {
		return isLoginFailureLock;
	}
	public void setIsLoginFailureLock(Boolean isLoginFailureLock) {
		this.isLoginFailureLock = isLoginFailureLock;
	}
	public Integer getLoginFailureLockCount() {
		return loginFailureLockCount;
	}
	public void setLoginFailureLockCount(Integer loginFailureLockCount) {
		this.loginFailureLockCount = loginFailureLockCount;
	}
	public Integer getLoginFailureLockTime() {
		return loginFailureLockTime;
	}
	public void setLoginFailureLockTime(Integer loginFailureLockTime) {
		this.loginFailureLockTime = loginFailureLockTime;
	}
	public String getSmtpFromMail() {
		return smtpFromMail;
	}
	public void setSmtpFromMail(String smtpFromMail) {
		this.smtpFromMail = smtpFromMail;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public Integer getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}
	public String getSmtpUsername() {
		return smtpUsername;
	}
	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}
	public String getSmtpPassword() {
		return smtpPassword;
	}
	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSensitiveKeyWord() {
		return sensitiveKeyWord;
	}
	public void setSensitiveKeyWord(String sensitiveKeyWord) {
		this.sensitiveKeyWord = sensitiveKeyWord;
	}
	public String getMiniMoney() {
		return miniMoney;
	}
	public void setMiniMoney(String miniMoney) {
		this.miniMoney = miniMoney;
	}
	public String getOffLineRechargeDes() {
		return offLineRechargeDes;
	}
	public void setOffLineRechargeDes(String offLineRechargeDes) {
		this.offLineRechargeDes = offLineRechargeDes;
	}
	public BigDecimal getDepositMoney() {
		return depositMoney;
	}
	public void setDepositMoney(BigDecimal depositMoney) {
		this.depositMoney = depositMoney;
	}
	public BigDecimal getFeeMoney() {
		return feeMoney;
	}
	public void setFeeMoney(BigDecimal feeMoney) {
		this.feeMoney = feeMoney;
	}
	public String getXkz() {
		return xkz;
	}
	public String getCopyright() {
		return copyright;
	}
	public String getYwPhone() {
		return ywPhone;
	}
	public void setXkz(String xkz) {
		this.xkz = xkz;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public void setYwPhone(String ywPhone) {
		this.ywPhone = ywPhone;
	}
	
	
	
	
	
	
	
	
	
	
}
