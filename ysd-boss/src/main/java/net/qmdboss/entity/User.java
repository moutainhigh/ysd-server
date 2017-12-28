package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends BaseEntity{
	
	private static final long serialVersionUID = -2923681584402309880L;
	
//		private Integer id;//编号-用户ID
	    private Integer typeId;//用户类型 常量类【0:投资者;1:借款人;2:申请成为借款人中...... 3:服务商】
	    private Integer memberType;//会员类型【0:个人; 1:企业】
	    private String username; //用户名
	    private String password; //密码
	    private String payPassword; //支付密码
	    private String randomNum ;//随机8位数
	    private Boolean isEnabled;//账号是否启用【0:不启用；1：启用】
	    private Boolean isLock;//账号是否锁定【1:锁定；0：正常】

		private Integer loginFailureCount;// 连续登录失败的次数
		private Date lockedDate;// 账号锁定日期
	    
	    private Integer inviteUserid;//邀请者
	    private BigDecimal inviteMoney;//邀请注册提成
	    private String cardType;//证件类型
	    private String cardId;//证件号
	    private String cardPic1;//身份证正面图片
	    private String cardPic2;//身份证正面图片
	    private String realName;//真实姓名
	    private Integer status;//状态
	    private Integer realStatus;//是否实名认证（默认为’0’）
	    private Integer emailStatus;//是否邮件认证
	    private Integer phoneStatus;//是否手机认证
	    private Integer sceneStatus;//是否现场认证
	    private String email;//邮箱
	    private String sex;//性别 1:男，2：女
	    private String litpic;//头像
	    private String phone;//用户手机号码
	    private Date birthday;//生日
	    private String province;//省
	    private String city;//市
	    private String area;//区
	    private String address;//住址
	    private Integer loginTime;//登录次数
	    private String addIp;//注册IP
	    private Date lastTime;//最后登录时间
	    private String lastIp;//最后登录IP
	    
	    private String emailCertificationKey; //邮件注册认证key【 通过后清空】
		private String passwordRecoverKey;	  //登录密码找回Key【 找回后清空】
		private String payPasswordRecoverKey; //支付密码找回Key【 找回后清空】
		
	    private String areaStore;//省市区中文
		
		private Set<Borrow> borrowSet=new HashSet<Borrow>(); //投标属性类
		private Set<BorrowDetail> borrowDetailSet = new HashSet<BorrowDetail>();
		private UserInfo userInfo;//用户详细信息
		
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
		private BigDecimal autoTenderRewardBegin;//奖励开始
		private BigDecimal autoTenderRewardEnd;//奖励结束
		private String autoTenderRepayWay;//还款方式： 1XX:按月付息，到期还本 (含一月标)，X1X:按月分期还本息，XX1:到期还本息
		private String autoTenderBorrowType;//自投标类型；
		
		private Date autoTenderModifyDate;
		//---------推广加字段-------------------
		
			private String tgNo;//投资人-推广编号
		    private Integer tgOneLevelUserId;//一级推广人ID
		    private BigDecimal sumTenderMoney;// 注册奖励——投资资金合计（注册奖励发送完改为发送的红包）
			private Integer tgStatus;//推广奖励发送状态【0：没发放（默认），1：已发放】
			private Integer emailFreq;//email认证次数

			private Integer tasteFlag;//体验金是否领取【0：未领取；1：已领取；2：不能领取】
		//---------推广加字段end-------------------
			
		private Integer agencyid;//服务商ID
		private Integer agencytype;//1：对接服务商

		private String bankCustNo ;//存管返回客户号

		private String deviceToken;//消息推送对设备的唯一标识
		
		private Set<UserAccountDetail> userAccDetailSet = new HashSet<UserAccountDetail>();//user与用户账户记录一对多关系
		private UserAccount account; //用户帐户
		private  Set<UserAccountRecharge> userAccountRechargeSet = new HashSet<UserAccountRecharge>();;//用户账户充值
		
		private Set<Rewards> rewardsSet = new HashSet<Rewards>();
		
		private Set<Fangkuan> fangkuanSet = new HashSet<Fangkuan>();
		private Set<BorrowAccountDetail> borrowAccountDetailSet = new HashSet<BorrowAccountDetail>();

		private Set<UserHongbao> userHongbaoSet = new HashSet<UserHongbao>();
		private Set<UserAwardDetail> userAwardDetailSet = new HashSet<UserAwardDetail>();
		
		private Set<AccountBank> accountBankSet = new HashSet<AccountBank>();
		
		@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
		public Set<UserAccountDetail> getUserAccDetailSet() {
			return userAccDetailSet;
		}
		public void setUserAccDetailSet(Set<UserAccountDetail> userAccDetailSet) {
			this.userAccDetailSet = userAccDetailSet;
		}
		
		
		
		@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
		@ForeignKey(name = "fk_user_account")
		public UserAccount getAccount() {
			return account;
		}
		public void setAccount(UserAccount account) {
			this.account = account;
		}
		
		
		@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
		public Set<BorrowDetail> getBorrowDetailSet() {
			return borrowDetailSet;
		}
		public void setBorrowDetailSet(Set<BorrowDetail> borrowDetailSet) {
			this.borrowDetailSet = borrowDetailSet;
		}
		@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
		public Set<Borrow> getBorrowSet() {
			return borrowSet;
		}
		public void setBorrowSet(Set<Borrow> borrowSet) {
			this.borrowSet = borrowSet;
		}
		
		public Integer getTypeId() {
			return typeId;
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
		public String getRealName() {
			return realName;
		}
		public void setRealName(String realName) {
			this.realName = realName;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public Integer getRealStatus() {
			return realStatus;
		}
		public void setRealStatus(Integer realStatus) {
			this.realStatus = realStatus;
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
		public String getEmailCertificationKey() {
			return emailCertificationKey;
		}
		public void setEmailCertificationKey(String emailCertificationKey) {
			this.emailCertificationKey = emailCertificationKey;
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
		
		public String getAreaStore() {
			return areaStore;
		}
		public void setAreaStore(String areaStore) {
			this.areaStore = areaStore;
		}
		
		
		@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
		@ForeignKey(name = "fk_user_userInfo")
		public UserInfo getUserInfo() {
			return userInfo;
		}
		public void setUserInfo(UserInfo userInfo) {
			this.userInfo = userInfo;
		}
		
		@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
		public Set<UserAccountRecharge> getUserAccountRechargeSet() {
			return userAccountRechargeSet;
		}
		public void setUserAccountRechargeSet(Set<UserAccountRecharge> userAccountRechargeSet) {
			this.userAccountRechargeSet = userAccountRechargeSet;
		}
		public Integer getMemberType() {
			return memberType;
		}
		public void setMemberType(Integer memberType) {
			this.memberType = memberType;
		}
		public String getRandomNum() {
			return randomNum;
		}
		public void setRandomNum(String randomNum) {
			this.randomNum = randomNum;
		}
		
		@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
		public Set<Rewards> getRewardsSet() {
			return rewardsSet;
		}
		public void setRewardsSet(Set<Rewards> rewardsSet) {
			this.rewardsSet = rewardsSet;
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
		public BigDecimal getSumTenderMoney() {
			return sumTenderMoney;
		}
		public void setSumTenderMoney(BigDecimal sumTenderMoney) {
			this.sumTenderMoney = sumTenderMoney;
		}
		public Integer getTgStatus() {
			return tgStatus;
		}
		public void setTgStatus(Integer tgStatus) {
			this.tgStatus = tgStatus;
		}
		public Integer getEmailFreq() {
			return emailFreq;
		}
		public void setEmailFreq(Integer emailFreq) {
			this.emailFreq = emailFreq;
		}
		public Integer getAgencyid() {
			return agencyid;
		}
		public void setAgencyid(Integer agencyid) {
			this.agencyid = agencyid;
		}
		public Integer getAgencytype() {
			return agencytype;
		}
		public void setAgencytype(Integer agencytype) {
			this.agencytype = agencytype;
		}

		public String getAutoTenderBorrowType() {
			return autoTenderBorrowType;
		}
		public void setAutoTenderBorrowType(String autoTenderBorrowType) {
			this.autoTenderBorrowType = autoTenderBorrowType;
		}
		@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
		public Set<Fangkuan> getFangkuanSet() {
			return fangkuanSet;
		}
		public void setFangkuanSet(Set<Fangkuan> fangkuanSet) {
			this.fangkuanSet = fangkuanSet;
		}
		
		@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
		public Set<BorrowAccountDetail> getBorrowAccountDetailSet() {
			return borrowAccountDetailSet;
		}
		public void setBorrowAccountDetailSet(Set<BorrowAccountDetail> borrowAccountDetailSet) {
			this.borrowAccountDetailSet = borrowAccountDetailSet;
		}
		public Integer getTasteFlag() {
			return tasteFlag;
		}
		public void setTasteFlag(Integer tasteFlag) {
			this.tasteFlag = tasteFlag;
		}

		@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
		public Set<UserHongbao> getUserHongbaoSet() {
			return userHongbaoSet;
		}
		public void setUserHongbaoSet(Set<UserHongbao> userHongbaoSet) {
			this.userHongbaoSet = userHongbaoSet;
		}
		
		@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})		
		public Set<UserAwardDetail> getUserAwardDetailSet() {
			return userAwardDetailSet;
		}
		public void setUserAwardDetailSet(Set<UserAwardDetail> userAwardDetailSet) {
			this.userAwardDetailSet = userAwardDetailSet;
		}
		
		@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})	
		public Set<AccountBank> getAccountBankSet() {
			return accountBankSet;
		}
		public void setAccountBankSet(Set<AccountBank> accountBankSet) {
			this.accountBankSet = accountBankSet;
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
