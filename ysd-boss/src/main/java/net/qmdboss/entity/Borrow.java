package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Borrow 属相类
 * @author 詹锋
 * time 2012-11-05
 */
@Entity
public class Borrow extends BaseEntity {

	
	private static final long serialVersionUID = 1L;
	
//	private Integer id ;//标ID
	private Integer maxborrowId;//标ID
	//	private Integer userId;//用户ID
	private String  name;//标题
	private Integer status;//状态码0-发表未审核；1-审核通过；2-审核未通过；3-满标审核通过；4-满标审核未通过；5-等待满标审核；6-为过期撤回的标；6-撤回；7-已还完
	private Integer orderNum;//排序号
	private Integer divides;// 总的期数
	private Integer isVouch;//是否为担保标 //0 无抵押无担保，1有担保无抵押，2无担保有抵押，3有担保有抵押
	private Integer hits;//点击次数
	private String type;//类型【0-秒标，1-质押标，2-流转标，3-信用标，4-月标】
	private String verifyUser;//审核人
	private Date verifyTime;//审核时间
	private String verifyRemark;//审核备注
	private Integer repaymentUser;//瞒标审核人
	private String forstAccount;//第一账户
	private BigDecimal repaymentAccount;//应还金额
	private BigDecimal monthlyRepayment;//每月应还金额
	private BigDecimal repaymentYesaccount;//已还款金额
	private Date repaymentTime;//借款人收款时间
	private String repamyentRemark;//瞒标审核备注
	private Date successTime;//满标审核时间
	private Date endTime;//标结束时间
	private BigDecimal paymentAccount;//每月还款金额
	private String eachTime;//每月还款日
	private String useReason;//借款用途
	private String timeLimit;//借款月数
	private String style;//还款方式
	private BigDecimal account;//借款总金额
	private BigDecimal accountYes;//实际借款金额--
	private String tenderTimes;//投标次数
	private Double apr;//年利率
	private String lowestAccount;//最低投标额
	private String mostAccount;//最高投标额
	private String validTime;//有效时间
	private String award;//投标奖励方式【1-固定奖励；2百分比奖励】
	private String partAccount;//固定金额分摊奖励
	private String funds;//投标金额比例奖励
	private String isFalse;//标的失败时也同样奖励
	private String openAccount;//公开我的帐户资金情况
	private String openBorrow;//公开我的借款资金情况
	private String openTender;//公开我的投标资金情况
	private String openCredit;//公开我的信用额度情况
	private String content;//标详细描述
	private String isDo;//是否完成
	private String isMb;//是否秒标
	private String isFast;//是否给力
	private String isDxb;//是否是定向标
	private String pwd;//定向标密码
	private String isday;//是否天标
	private String timeLimitDay;//天标的天数
	private String addPersion;//添加操作的人
	private String updatePersion;//修改人
	private String operatorIp;//操作者IP
	private String schedule; //投标的百分比
	private String balance;//投标剩余金额若为0则表示此标投满
	private String borImage; // 标的图片
	private String borImage2; // 汽车的图片
	private String borStages;//还款日期和金额,以天数逗号加金额再分号组成(10,300:20,100:)
	private String borImageFirst; // 标的图片（首张）
	private User user;//发标人人信息
	
	private Date finalRepayDate; //最终还款日
	private Date nextRepayDate; //次回还款日
	private BigDecimal bonus;// 标的奖励金额
	private BigDecimal interestTotal;// 标的总利息
	private BigDecimal repayCapital;// 未还本金
	private BigDecimal repayInterest;// 未还利息
	private BigDecimal payedCapital;// 已还本金
	private BigDecimal payedInterest;// 已还利息
	
	// ---流转标使用
	private Integer wanderTenderTimes;// int(11)每人认购次数
	private Integer wanderPieceSize;// int(11)总认购数
	private BigDecimal wanderPieceMoney;// decimal(18,2)每份金额
	private Integer wanderRedeemTimes;// int(11)赎回次数
	private String wanderStages;// text 流转标还款信息
	
	private Integer repaymentPeriod;//还款周期
	
	// 置顶 排序
	private Integer showTop;
	private Integer showSort;
	
	private BigDecimal finalRateYear; //最终年利率
	private Integer autoTenderStatus; // 自投状态0关闭1启用
	private Integer autoTenderRepayWay; // 1 按月付息，到期还本 (含一月标)，2按月分期还本息，3到期还本息
	
	private BigDecimal continueAwardRate;
	
	private BigDecimal varyYearRate;
	private BigDecimal varyMonthLimit;
	
	private Integer awardType;//奖励类型：0：现金奖励，1：红包奖励 ->funds
	private BigDecimal awardScale;//使用红包比例(5%)
	private String borrowVerifyJson ;//各种证明组成的json
	
	private Set<BorrowDetail> borrowDetailSet=new LinkedHashSet<BorrowDetail>();//投标详细记录
	private Set<BorrowRepaymentDetail> borrRepayDetailSet = new LinkedHashSet<BorrowRepaymentDetail>();//还款记录明细
	private Set<UserRepaymentDetail> userRepayDetailSet = new LinkedHashSet<UserRepaymentDetail>();//还款记录明细
	

	private Set<Fangkuan> fangkuanSet = new HashSet<Fangkuan>();
	private Set<BorrowAccountDetail> borrowAccountDetailSet = new HashSet<BorrowAccountDetail>();
	
	private BigDecimal depositMoney;//保证金
	private Integer feeType;//手续费方式0比例1固定
	private BigDecimal feeMoney;//手续费固定金额
	

	private BigDecimal borrowMoney;//项目余额
	private Integer fangkuanStatus;//0:未放款；1：已放款; 2:放款申请中
	

	private BigDecimal tzxf;//投资先锋奖励金额
	private BigDecimal tzth;//投资土豪奖励金额
	private BigDecimal sgdg;//收官大哥奖励金额
	private Integer    rongXunFlg;//pc首页展示数据
	
	private BigDecimal baseApr;//基本利息
	private BigDecimal awardApr;//奖励利息
	
	
	@OneToMany(mappedBy = "borrow", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Set<BorrowRepaymentDetail> getBorrRepayDetailSet() {
		return borrRepayDetailSet;
	}

	public void setBorrRepayDetailSet(Set<BorrowRepaymentDetail> borrRepayDetailSet) {
		this.borrRepayDetailSet = borrRepayDetailSet;
	}
	
	@OneToMany(mappedBy = "borrow", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Set<UserRepaymentDetail> getUserRepayDetailSet() {
		return userRepayDetailSet;
	}
	public void setUserRepayDetailSet(Set<UserRepaymentDetail> userRepayDetailSet) {
		this.userRepayDetailSet = userRepayDetailSet;
	}

	@OneToMany(mappedBy = "borrow", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Set<BorrowDetail> getBorrowDetailSet() {
		return borrowDetailSet;
	}

	public void setBorrowDetailSet(Set<BorrowDetail> borrowDetailSet) {
		this.borrowDetailSet = borrowDetailSet;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_borrow_user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	public Integer getMaxborrowId() {
		return maxborrowId;
	}

	public void setMaxborrowId(Integer maxborrowId) {
		this.maxborrowId = maxborrowId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getIsVouch() {
		return isVouch;
	}

	public void setIsVouch(Integer isVouch) {
		this.isVouch = isVouch;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVerifyUser() {
		return verifyUser;
	}

	public void setVerifyUser(String verifyUser) {
		this.verifyUser = verifyUser;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getVerifyRemark() {
		return verifyRemark;
	}

	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}

	public Integer getRepaymentUser() {
		return repaymentUser;
	}

	public void setRepaymentUser(Integer repaymentUser) {
		this.repaymentUser = repaymentUser;
	}

	public String getForstAccount() {
		return forstAccount;
	}

	public void setForstAccount(String forstAccount) {
		this.forstAccount = forstAccount;
	}

	public BigDecimal getRepaymentAccount() {
		return repaymentAccount;
	}

	public void setRepaymentAccount(BigDecimal repaymentAccount) {
		this.repaymentAccount = repaymentAccount;
	}

	public BigDecimal getMonthlyRepayment() {
		return monthlyRepayment;
	}

	public void setMonthlyRepayment(BigDecimal monthlyRepayment) {
		this.monthlyRepayment = monthlyRepayment;
	}

	public BigDecimal getRepaymentYesaccount() {
		return repaymentYesaccount;
	}

	public void setRepaymentYesaccount(BigDecimal repaymentYesaccount) {
		this.repaymentYesaccount = repaymentYesaccount;
	}

	public Date getRepaymentTime() {
		return repaymentTime;
	}

	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}

	public String getRepamyentRemark() {
		return repamyentRemark;
	}

	public void setRepamyentRemark(String repamyentRemark) {
		this.repamyentRemark = repamyentRemark;
	}

	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(BigDecimal paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public String getEachTime() {
		return eachTime;
	}

	public void setEachTime(String eachTime) {
		this.eachTime = eachTime;
	}

	public String getUseReason() {
		return useReason;
	}

	public void setUseReason(String useReason) {
		this.useReason = useReason;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public BigDecimal getAccountYes() {
		return accountYes;
	}

	public void setAccountYes(BigDecimal accountYes) {
		this.accountYes = accountYes;
	}

	public String getTenderTimes() {
		return tenderTimes;
	}

	public void setTenderTimes(String tenderTimes) {
		this.tenderTimes = tenderTimes;
	}

	public Double getApr() {
		return apr;
	}

	public void setApr(Double apr) {
		this.apr = apr;
	}

	public String getLowestAccount() {
		return lowestAccount;
	}

	public void setLowestAccount(String lowestAccount) {
		this.lowestAccount = lowestAccount;
	}

	public String getMostAccount() {
		return mostAccount;
	}

	public void setMostAccount(String mostAccount) {
		this.mostAccount = mostAccount;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getPartAccount() {
		return partAccount;
	}

	public void setPartAccount(String partAccount) {
		this.partAccount = partAccount;
	}

	public String getFunds() {
		return funds;
	}

	public void setFunds(String funds) {
		this.funds = funds;
	}

	public String getIsFalse() {
		return isFalse;
	}

	public void setIsFalse(String isFalse) {
		this.isFalse = isFalse;
	}

	public String getOpenAccount() {
		return openAccount;
	}

	public void setOpenAccount(String openAccount) {
		this.openAccount = openAccount;
	}

	public String getOpenBorrow() {
		return openBorrow;
	}

	public void setOpenBorrow(String openBorrow) {
		this.openBorrow = openBorrow;
	}

	public String getOpenTender() {
		return openTender;
	}

	public void setOpenTender(String openTender) {
		this.openTender = openTender;
	}

	public String getOpenCredit() {
		return openCredit;
	}

	public void setOpenCredit(String openCredit) {
		this.openCredit = openCredit;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIsDo() {
		return isDo;
	}

	public void setIsDo(String isDo) {
		this.isDo = isDo;
	}

	public String getIsMb() {
		return isMb;
	}

	public void setIsMb(String isMb) {
		this.isMb = isMb;
	}

	public String getIsFast() {
		return isFast;
	}

	public void setIsFast(String isFast) {
		this.isFast = isFast;
	}

	public String getIsDxb() {
		return isDxb;
	}

	public void setIsDxb(String isDxb) {
		this.isDxb = isDxb;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getIsday() {
		return isday;
	}

	public void setIsday(String isday) {
		this.isday = isday;
	}

	public String getTimeLimitDay() {
		return timeLimitDay;
	}

	public void setTimeLimitDay(String timeLimitDay) {
		this.timeLimitDay = timeLimitDay;
	}

	public String getAddPersion() {
		return addPersion;
	}

	public void setAddPersion(String addPersion) {
		this.addPersion = addPersion;
	}

	public String getUpdatePersion() {
		return updatePersion;
	}

	public void setUpdatePersion(String updatePersion) {
		this.updatePersion = updatePersion;
	}
	public String getOperatorIp() {
		return operatorIp;
	}

	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getBorImage() {
		return borImage;
	}

	public void setBorImage(String borImage) {
		this.borImage = borImage;
	}

	public String getBorStages() {
		return borStages;
	}

	public void setBorStages(String borStages) {
		this.borStages = borStages;
	}

	public String getBorImageFirst() {
		return borImageFirst;
	}

	public void setBorImageFirst(String borImageFirst) {
		this.borImageFirst = borImageFirst;
	}

	public Date getFinalRepayDate() {
		return finalRepayDate;
	}

	public void setFinalRepayDate(Date finalRepayDate) {
		this.finalRepayDate = finalRepayDate;
	}

	public Date getNextRepayDate() {
		return nextRepayDate;
	}

	public void setNextRepayDate(Date nextRepayDate) {
		this.nextRepayDate = nextRepayDate;
	}

	public BigDecimal getBonus() {
		return bonus;
	}

	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}

	public BigDecimal getInterestTotal() {
		return interestTotal;
	}

	public void setInterestTotal(BigDecimal interestTotal) {
		this.interestTotal = interestTotal;
	}

	public BigDecimal getRepayCapital() {
		return repayCapital;
	}

	public void setRepayCapital(BigDecimal repayCapital) {
		this.repayCapital = repayCapital;
	}

	public BigDecimal getRepayInterest() {
		return repayInterest;
	}

	public void setRepayInterest(BigDecimal repayInterest) {
		this.repayInterest = repayInterest;
	}

	public BigDecimal getPayedCapital() {
		return payedCapital;
	}

	public void setPayedCapital(BigDecimal payedCapital) {
		this.payedCapital = payedCapital;
	}

	public BigDecimal getPayedInterest() {
		return payedInterest;
	}

	public void setPayedInterest(BigDecimal payedInterest) {
		this.payedInterest = payedInterest;
	}

	public Integer getWanderTenderTimes() {
		return wanderTenderTimes;
	}

	public void setWanderTenderTimes(Integer wanderTenderTimes) {
		this.wanderTenderTimes = wanderTenderTimes;
	}

	public Integer getWanderPieceSize() {
		return wanderPieceSize;
	}

	public void setWanderPieceSize(Integer wanderPieceSize) {
		this.wanderPieceSize = wanderPieceSize;
	}

	public BigDecimal getWanderPieceMoney() {
		return wanderPieceMoney;
	}

	public void setWanderPieceMoney(BigDecimal wanderPieceMoney) {
		this.wanderPieceMoney = wanderPieceMoney;
	}

	public Integer getWanderRedeemTimes() {
		return wanderRedeemTimes;
	}

	public void setWanderRedeemTimes(Integer wanderRedeemTimes) {
		this.wanderRedeemTimes = wanderRedeemTimes;
	}

	public String getWanderStages() {
		return wanderStages;
	}

	public void setWanderStages(String wanderStages) {
		this.wanderStages = wanderStages;
	}

	public Integer getDivides() {
		return divides;
	}

	public void setDivides(Integer divides) {
		this.divides = divides;
	}

	public Integer getShowTop() {
		return showTop;
	}

	public void setShowTop(Integer showTop) {
		this.showTop = showTop;
	}

	public Integer getShowSort() {
		return showSort;
	}

	public void setShowSort(Integer showSort) {
		this.showSort = showSort;
	}

	public Integer getRepaymentPeriod() {
		return repaymentPeriod;
	}

	public void setRepaymentPeriod(Integer repaymentPeriod) {
		this.repaymentPeriod = repaymentPeriod;
	}

	public BigDecimal getFinalRateYear() {
		return finalRateYear;
	}

	public void setFinalRateYear(BigDecimal finalRateYear) {
		this.finalRateYear = finalRateYear;
	}

	public Integer getAutoTenderStatus() {
		return autoTenderStatus;
	}

	public void setAutoTenderStatus(Integer autoTenderStatus) {
		this.autoTenderStatus = autoTenderStatus;
	}

	public Integer getAutoTenderRepayWay() {
		return autoTenderRepayWay;
	}

	public void setAutoTenderRepayWay(Integer autoTenderRepayWay) {
		this.autoTenderRepayWay = autoTenderRepayWay;
	}

	public BigDecimal getContinueAwardRate() {
		return continueAwardRate;
	}

	public void setContinueAwardRate(BigDecimal continueAwardRate) {
		this.continueAwardRate = continueAwardRate;
	}

	public BigDecimal getVaryYearRate() {
		return varyYearRate;
	}

	public void setVaryYearRate(BigDecimal varyYearRate) {
		this.varyYearRate = varyYearRate;
	}

	public BigDecimal getVaryMonthLimit() {
		return varyMonthLimit;
	}

	public void setVaryMonthLimit(BigDecimal varyMonthLimit) {
		this.varyMonthLimit = varyMonthLimit;
	}

	public Integer getAwardType() {
		return awardType;
	}

	public void setAwardType(Integer awardType) {
		this.awardType = awardType;
	}

	public String getBorrowVerifyJson() {
		return borrowVerifyJson;
	}

	public void setBorrowVerifyJson(String borrowVerifyJson) {
		this.borrowVerifyJson = borrowVerifyJson;
	}

	public BigDecimal getDepositMoney() {
		return depositMoney;
	}

	public void setDepositMoney(BigDecimal depositMoney) {
		this.depositMoney = depositMoney;
	}

	public Integer getFeeType() {
		return feeType;
	}

	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}

	public BigDecimal getFeeMoney() {
		return feeMoney;
	}

	public void setFeeMoney(BigDecimal feeMoney) {
		this.feeMoney = feeMoney;
	}

	public String getBorImage2() {
		return borImage2;
	}

	public void setBorImage2(String borImage2) {
		this.borImage2 = borImage2;
	}

	public BigDecimal getAwardScale() {
		return awardScale;
	}

	public void setAwardScale(BigDecimal awardScale) {
		this.awardScale = awardScale;
	}


	@OneToMany(mappedBy = "borrow", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Set<Fangkuan> getFangkuanSet() {
		return fangkuanSet;
	}

	public void setFangkuanSet(Set<Fangkuan> fangkuanSet) {
		this.fangkuanSet = fangkuanSet;
	}
	
	@OneToMany(mappedBy = "borrow", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Set<BorrowAccountDetail> getBorrowAccountDetailSet() {
		return borrowAccountDetailSet;
	}

	public void setBorrowAccountDetailSet(Set<BorrowAccountDetail> borrowAccountDetailSet) {
		this.borrowAccountDetailSet = borrowAccountDetailSet;
	}

	public BigDecimal getBorrowMoney() {
		return borrowMoney;
	}

	public void setBorrowMoney(BigDecimal borrowMoney) {
		this.borrowMoney = borrowMoney;
	}

	public Integer getFangkuanStatus() {
		return fangkuanStatus;
	}

	public void setFangkuanStatus(Integer fangkuanStatus) {
		this.fangkuanStatus = fangkuanStatus;
	}

	public BigDecimal getTzxf() {
		return tzxf;
	}

	public void setTzxf(BigDecimal tzxf) {
		this.tzxf = tzxf;
	}

	public BigDecimal getTzth() {
		return tzth;
	}

	public void setTzth(BigDecimal tzth) {
		this.tzth = tzth;
	}

	public BigDecimal getSgdg() {
		return sgdg;
	}

	public void setSgdg(BigDecimal sgdg) {
		this.sgdg = sgdg;
	}

	public BigDecimal getBaseApr() {
		return baseApr;
	}

	public void setBaseApr(BigDecimal baseApr) {
		this.baseApr = baseApr;
	}

	public BigDecimal getAwardApr() {
		return awardApr;
	}

	public void setAwardApr(BigDecimal awardApr) {
		this.awardApr = awardApr;
	}

	public Integer getRongXunFlg() {
		return rongXunFlg;
	}

	public void setRongXunFlg(Integer rongXunFlg) {
		this.rongXunFlg = rongXunFlg;
	}
	
	
	
}
