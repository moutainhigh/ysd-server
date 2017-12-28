package com.qmd.mode.user;

import com.qmd.mode.agency.Agency;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * 用户基本信息表
 * 
 * @author xsf
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String USER_ID_SESSION_NAME = "agency_sessionUserId";// 保存登录会员ID的Session名称

	public static final Integer SPREAD_COOKIE_AGE = 604800;// 秒【七天】

	public static final Integer USER_COOKIE_AGE = 86400;// 秒【一天】

	public static final Integer USER_CENTER_COOKIE_AGE = 43200;// 秒【12小时】

	private Integer id;// 编号-用户ID
	private Date createDate;
	private Date modifyDate;
	private Integer typeId;// 会员属性 常量类[0,投资者;1,机构服务商;3,借款人]
	private Integer memberType;// 会员类型【0:个人;1:企业】

	private String username; // 用户名
	private String password; // 密码
	private String payPassword; // 支付密码
	private String randomNum;// 随机8位数
	private Boolean isEnabled;// 账号是否启用【0:不启用；1：启用】
	private Boolean isLock;// 账号是否锁定【1:锁定；0：正常】

	private Integer loginFailureCount;// 连续登录失败的次数
	private Date lockedDate;// 账号锁定日期

	private Integer inviteUserid;// 邀请者
	private BigDecimal inviteMoney;// 邀请注册提成
	private String cardType;// 证件类型
	private String cardId;// 证件号
	private String cardPic1;// 身份证正面图片
	private String cardPic2;// 身份证正面图片
	private String realName;// 真实姓名
	private Integer status;// 状态
	private Integer realStatus;// 是否实名认证（默认为’0’）企业和个人
	private Integer emailStatus;// 是否邮件认证
	private Integer phoneStatus;// 是否手机认证
	private Integer sceneStatus;// 是否现场认证 认证：0无认证1-9
								// 认证失败次数，11自动认证通过，12手动认证通过(旧：现场认证状态)
	private String email;// 邮箱
	private String sex;// 性别 1:男，2：女
	private String litpic;// 头像
	private String phone;// 用户手机号码
	private Date birthday;// 生日
	private String province;// 省
	private String city;// 市
	private String area;// 区
	private String address;// 住址
	private Integer loginTime;// 登录次数
	private String addIp;// 注册IP
	private Date lastTime;// 最后登录时间
	private String lastIp;// 最后登录IP

	private String emailCertificationKey;
	private String passwordRecoverKey; // 登录密码找回Key【 找回后清空】
	private String payPasswordRecoverKey; // 支付密码找回Key【 找回后清空】
	private String areaStore;// 省市区中文

	private BigDecimal total;// 用户总金额（可用金额+冻结金额+待收金额）
	private BigDecimal ableMoney;// 可用金额，可提现金额

	private BigDecimal unableMoney;// 冻结金额
	private BigDecimal collection; // 待收金额


	private BigDecimal investorCollectionCapital;// 投资者待收本金
	private BigDecimal investorCollectionInterest;// 投资者待收利息
	private BigDecimal borrowerCollectionCapital;// 借款人待付本金
	private BigDecimal borrowerCollectionInterest;// 借款人待付利息

	private Integer agencyid;// 服务商ID
	private String agencytype;// 服务商类型【1：平台服务商；2：担保服务商；3：保险服务商；9:推荐机构】

	private String deviceToken;//消息推送对设备的唯一标识
	
	private Agency agency;


	private String bankCustNo ;//存管返回客户号


	private String tgNo;// 投资人-推广编号
	private Integer tgOneLevelUserId;// 一级推广人ID

	private Integer sourceFrom;// 注册来源【0：PC；1：微信；2：Android；3：IOS】

	private Integer autoTenderStatus;// 自投状态1启用0关闭
	private Date autoTenderDate;// 自投修改日期
	private Integer autoTenderTimes;// 自投次数
	private Integer autoTenderRule;// 自投规则0全投，1设置
	private BigDecimal autoTenderMoneyTop;// 自投上限金额
	private BigDecimal autoTenderMoneyLeave;// 自投保留金额
	private BigDecimal autoTenderRateBegin;// 自投利率开始
	private BigDecimal autoTenderRateEnd;// 自投利率结束
	private Integer autoTenderLimitBegin;// 自投期限开始
	private Integer autoTenderLimitEnd;// 自投期限结束
	private BigDecimal autoTenderRewardBegin;// 奖励开始
	private BigDecimal autoTenderRewardEnd;// 奖励结束
	private String autoTenderRepayWay;// 还款方式： 1XX:按月付息，到期还本
										// (含一月标)，X1X:按月分期还本息，XX1:到期还本息
	private Date autoTenderModifyDate;

	public Integer getTypeId() {
		return typeId;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getInviteUserid() {
		return inviteUserid;
	}

	public void setInviteUserid(Integer inviteUserid) {
		this.inviteUserid = inviteUserid;
	}

	public BigDecimal getInviteMoney() {
		return inviteMoney;
	}

	public void setInviteMoney(BigDecimal inviteMoney) {
		this.inviteMoney = inviteMoney;
	}

	public Integer getRealStatus() {
		return realStatus;
	}

	public void setRealStatus(Integer realStatus) {
		this.realStatus = realStatus;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(Integer emailStatus) {
		this.emailStatus = emailStatus;
	}

	public Integer getPhoneStatus() {
		return phoneStatus;
	}

	public void setPhoneStatus(Integer phoneStatus) {
		this.phoneStatus = phoneStatus;
	}


	public Integer getSceneStatus() {
		return sceneStatus;
	}

	public void setSceneStatus(Integer sceneStatus) {
		this.sceneStatus = sceneStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLitpic() {
		return litpic;
	}

	public void setLitpic(String litpic) {
		this.litpic = litpic;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public Boolean getIsLock() {
		return isLock;
	}

	public void setIsLock(Boolean isLock) {
		this.isLock = isLock;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}


	public Integer getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Integer loginTime) {
		this.loginTime = loginTime;
	}


	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}


	public String getPasswordRecoverKey() {
		return passwordRecoverKey;
	}

	public void setPasswordRecoverKey(String passwordRecoverKey) {
		this.passwordRecoverKey = passwordRecoverKey;
	}

	public String getPayPasswordRecoverKey() {
		return payPasswordRecoverKey;
	}

	public void setPayPasswordRecoverKey(String payPasswordRecoverKey) {
		this.payPasswordRecoverKey = payPasswordRecoverKey;
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

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getAbleMoney() {
		return ableMoney;
	}

	public void setAbleMoney(BigDecimal ableMoney) {
		this.ableMoney = ableMoney;
	}

	public BigDecimal getUnableMoney() {
		return unableMoney;
	}

	public void setUnableMoney(BigDecimal unableMoney) {
		this.unableMoney = unableMoney;
	}

	public BigDecimal getCollection() {
		return collection;
	}

	public void setCollection(BigDecimal collection) {
		this.collection = collection;
	}


	public String getAreaStore() {
		return areaStore;
	}

	public void setAreaStore(String areaStore) {
		this.areaStore = areaStore;
	}

	public BigDecimal getInvestorCollectionCapital() {
		return investorCollectionCapital;
	}

	public void setInvestorCollectionCapital(BigDecimal investorCollectionCapital) {
		this.investorCollectionCapital = investorCollectionCapital;
	}

	public BigDecimal getInvestorCollectionInterest() {
		return investorCollectionInterest;
	}

	public void setInvestorCollectionInterest(BigDecimal investorCollectionInterest) {
		this.investorCollectionInterest = investorCollectionInterest;
	}

	public BigDecimal getBorrowerCollectionCapital() {
		return borrowerCollectionCapital;
	}

	public void setBorrowerCollectionCapital(BigDecimal borrowerCollectionCapital) {
		this.borrowerCollectionCapital = borrowerCollectionCapital;
	}

	public BigDecimal getBorrowerCollectionInterest() {
		return borrowerCollectionInterest;
	}

	public void setBorrowerCollectionInterest(BigDecimal borrowerCollectionInterest) {
		this.borrowerCollectionInterest = borrowerCollectionInterest;
	}

	public String getRandomNum() {
		return randomNum;
	}

	public void setRandomNum(String randomNum) {
		this.randomNum = randomNum;
	}


	public Integer getAge() {
		Calendar cal = Calendar.getInstance();
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		int age = 0;
		if (birthday != null) {
			cal.setTime(birthday);
			int yearBirth = cal.get(Calendar.YEAR);
			int monthBirth = cal.get(Calendar.MONTH);
			int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
			age = yearNow - yearBirth;

			if (monthNow <= monthBirth) {
				if (monthNow == monthBirth) {
					// monthNow==monthBirth
					if (dayOfMonthNow < dayOfMonthBirth) {
						age--;
					} else {
						// do nothing
					}
				} else {
					// monthNow>monthBirth
					age--;
				}
			} else {
				// monthNow<monthBirth
				// donothing
			}
		}
		return age;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public String getShowRealName() {
		if (realName == null || "".equals(realName.trim())) {
			return "";
		}
		if (realName.length() > 0) {
			return realName.substring(0, 1) + "***";
		}

		return "";
	}

	public Integer getAgencyid() {
		return agencyid;
	}

	public void setAgencyid(Integer agencyid) {
		this.agencyid = agencyid;
	}

	public String getAgencytype() {
		return agencytype;
	}

	public void setAgencytype(String agencytype) {
		this.agencytype = agencytype;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}


	public String getTgNo() {
		return tgNo;
	}

	public void setTgNo(String tgNo) {
		this.tgNo = tgNo;
	}

	public Integer getTgOneLevelUserId() {
		return tgOneLevelUserId;
	}

	public void setTgOneLevelUserId(Integer tgOneLevelUserId) {
		this.tgOneLevelUserId = tgOneLevelUserId;
	}


	public Integer getAutoTenderStatus() {
		return autoTenderStatus;
	}

	public void setAutoTenderStatus(Integer autoTenderStatus) {
		this.autoTenderStatus = autoTenderStatus;
	}

	public Date getAutoTenderDate() {
		return autoTenderDate;
	}

	public void setAutoTenderDate(Date autoTenderDate) {
		this.autoTenderDate = autoTenderDate;
	}

	public Integer getAutoTenderTimes() {
		return autoTenderTimes;
	}

	public void setAutoTenderTimes(Integer autoTenderTimes) {
		this.autoTenderTimes = autoTenderTimes;
	}

	public Integer getAutoTenderRule() {
		return autoTenderRule;
	}

	public void setAutoTenderRule(Integer autoTenderRule) {
		this.autoTenderRule = autoTenderRule;
	}

	public BigDecimal getAutoTenderMoneyTop() {
		return autoTenderMoneyTop;
	}

	public void setAutoTenderMoneyTop(BigDecimal autoTenderMoneyTop) {
		this.autoTenderMoneyTop = autoTenderMoneyTop;
	}

	public BigDecimal getAutoTenderMoneyLeave() {
		return autoTenderMoneyLeave;
	}

	public void setAutoTenderMoneyLeave(BigDecimal autoTenderMoneyLeave) {
		this.autoTenderMoneyLeave = autoTenderMoneyLeave;
	}

	public BigDecimal getAutoTenderRateBegin() {
		return autoTenderRateBegin;
	}

	public void setAutoTenderRateBegin(BigDecimal autoTenderRateBegin) {
		this.autoTenderRateBegin = autoTenderRateBegin;
	}

	public BigDecimal getAutoTenderRateEnd() {
		return autoTenderRateEnd;
	}

	public void setAutoTenderRateEnd(BigDecimal autoTenderRateEnd) {
		this.autoTenderRateEnd = autoTenderRateEnd;
	}

	public Integer getAutoTenderLimitBegin() {
		return autoTenderLimitBegin;
	}

	public void setAutoTenderLimitBegin(Integer autoTenderLimitBegin) {
		this.autoTenderLimitBegin = autoTenderLimitBegin;
	}

	public Integer getAutoTenderLimitEnd() {
		return autoTenderLimitEnd;
	}

	public void setAutoTenderLimitEnd(Integer autoTenderLimitEnd) {
		this.autoTenderLimitEnd = autoTenderLimitEnd;
	}

	public BigDecimal getAutoTenderRewardBegin() {
		return autoTenderRewardBegin;
	}

	public void setAutoTenderRewardBegin(BigDecimal autoTenderRewardBegin) {
		this.autoTenderRewardBegin = autoTenderRewardBegin;
	}

	public BigDecimal getAutoTenderRewardEnd() {
		return autoTenderRewardEnd;
	}

	public void setAutoTenderRewardEnd(BigDecimal autoTenderRewardEnd) {
		this.autoTenderRewardEnd = autoTenderRewardEnd;
	}

	public String getAutoTenderRepayWay() {
		return autoTenderRepayWay;
	}

	public void setAutoTenderRepayWay(String autoTenderRepayWay) {
		this.autoTenderRepayWay = autoTenderRepayWay;
	}

	public Date getAutoTenderModifyDate() {
		return autoTenderModifyDate;
	}

	public void setAutoTenderModifyDate(Date autoTenderModifyDate) {
		this.autoTenderModifyDate = autoTenderModifyDate;
	}

	public Integer getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(Integer sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getEmailCertificationKey() {
		return emailCertificationKey;
	}

	public void setEmailCertificationKey(String emailCertificationKey) {
		this.emailCertificationKey = emailCertificationKey;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getBankCustNo() {
		return bankCustNo;
	}

	public void setBankCustNo(String bankCustNo) {
		this.bankCustNo = bankCustNo;
	}
}