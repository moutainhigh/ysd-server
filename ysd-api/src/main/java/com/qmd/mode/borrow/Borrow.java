package com.qmd.mode.borrow;

import com.qmd.util.CommonUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Borrow 表属性类
 * 
 * @author Administrator
 * 
 */
public class Borrow implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2138723257256669054L;
	private Integer id;// 标ID
	private Integer maxborrowId;// 标ID
	private Integer userId;// 用户ID
	private String name;// 标题
	private int status;// 状态码0-发表未审核；1-审核通过；2-审核未通过；3-满标审核通过；4-满标审核未通过；5-等待满标审核；6-过期或撤回；7-已还完;8-删除状态
	private int orderNum;// 排序号
	private int divides;// 总期数
	private String isVouch;// 标的业务类型如：车辆标
	private int hits;// 点击次数
	private String type;// 类型【0-秒标，1-质押标，2-流转标，3-信用标，4-月标,5 流转标】新标类型【11天标 12 月标 13流转标】
	private String verifyUser;// 审核人
	private Date verifyTime;// 审核时间
	private String verifyRemark;// 审核备注
	private int repaymentUser;// 瞒标审核人
	private String forstAccount;// 第一账户
	private String repaymentAccount;// 应还金额
	private String monthlyRepayment;// 每月应还金额
	private String repaymentYesaccount;// 已还款金额
	private Date repaymentTime;// 借款人收款时间
	private String repamyentRemark;// 瞒标审核备注
	private Date successTime;// 满标审核时间
	private Date endTime;// 标结束时间
	private String paymentAccount;// 每月还款金额 (已去掉）
	private String eachTime;// 债务人信息
	private String useReason;// 借款用途
	private String timeLimit;// 借款天数
	private String style;// 还款方式 【1分期付息，到期本息  2到期付本息，3等额本金】
	private Integer repaymentPeriod;//还款周期（分期付息时用）
	private String account;// 借款总金额
	private String accountYes;// 实际借款金额
	private String tenderTimes;// 投标次数
	private double apr;// 年利率
	private String lowestAccount;// 最低投标额
	private String mostAccount;// 最高投标额
	private String validTime;// 有效时间
	private String award;// 投标奖励方式
	private String partAccount;//借款手续费
	private String funds;// 投标金额比例奖励
	private String isFalse;// 标的失败时也同样奖励 (已去掉）
	private String openAccount;// 公开我的帐户资金情况
	private String openBorrow;// 公开我的借款资金情况
	private String openTender;// 公开我的投标资金情况
	private String openCredit;// 公开我的信用额度情况
	private String content;// 标详细描述
	private String isDo;// 是否完成 (已去掉）
	private String isMb;// 是否秒标 (已去掉）
	private String isFast;// 是否给力 (已去掉）
	private String isDxb;// 是否是定向标
	private String pwd;// 定向标密码
	private String isday;// 是否天标
	private String timeLimitDay;// 天标的天数 (已去掉）
	private String addPersion;// 添加操作的人
	private Date addTime;// 添加时间 (已去掉）
	private Date createDate;// 创建时间
	private String updatePersion;// 修改人
	private Date updateTime;// 修改时间
	private Date modifyDate;// 修改时间
	private String operatorIp;// 操作者IP
	private String schedule; // 投标的百分比
	private String balance; // 剩余金额
	private String female; // 标识是企业还是个人
	private Integer tenderUserId; // 投标人ID
	private Date overDate; // 投标有效期
	private String borImage; // 标的图片
	private String borStages;// 还款来源
	private String borImageFirst; // 标的图片（首张）
	private String orderList;

	// ---流转标使用
	private Integer wanderTenderTimes;// int(11)每人认购次数
	private Integer wanderPieceSize;// int(11)总认购数
	private BigDecimal wanderPieceMoney;// decimal(18,2)每份金额
	private Integer wanderRedeemTimes;// int(11)赎回次数
	private String wanderStages;// text 流转标还款信息

	private Date finalRepayDate; // 最终还款日
	private Date nextRepayDate; // 次回还款日

	private BigDecimal bonus;// 标的奖励金额
	private BigDecimal interestTotal;// 标的总利息
	private BigDecimal repayCapital;// 未还本金
	private BigDecimal repayInterest;// 未还利息
	private BigDecimal payedCapital;// 已还本金
	private BigDecimal payedInterest;// 已还利息
	
	private BigDecimal finalRateYear; //最终年利率
	private Integer autoTenderStatus; // 自投状态0关闭1启用
	
	private Integer autoTenderRepayWay; // 1 按月付息，到期还本 (含一月标)，2按月分期还本息，3到期还本息
	
	private BigDecimal continueAwardRate;
	
	private BigDecimal varyYearRate;
	private BigDecimal varyMonthLimit;
	
	private Integer parentFlg;
	private Integer parentId;
	private Integer parentMoney;
	
	private Integer childMoneyWait;
	private Integer childMoneyFinish;
	private Integer childMoneyReady;
	
	private Integer awardType;//奖励类型：0：现金奖励，1：红包奖励
	private BigDecimal awardScale;//使用红包比例
	

	private BigDecimal baseApr;//基本利息
	private BigDecimal awardApr;//奖励利息
	
	//JSON类型数据
	private String borrowInfoJson;
	private String borImage2; // 车辆照片
	private String businessCode;//项目编号
	private String borrowVerifyJson;
	
	public String getStyleName() {
		if ("1".equals(style)) {
			return "分期付息，到期本息";
		} else if ("2".equals(style)) {
			return "到期付本息";
		} else if ("3".equals(style)) {
			return "等额本金";
		} 
		return "";
	}

	public Date getOverDate() {
		return overDate;
	}

	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}

	public Integer getTenderUserId() {
		return tenderUserId;
	}

	public void setTenderUserId(Integer tenderUserId) {
		this.tenderUserId = tenderUserId;
	}

	public String getFemale() {
		return female;
	}

	public void setFemale(String female) {
		this.female = female;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMaxborrowId() {
		return maxborrowId;
	}

	public void setMaxborrowId(Integer maxborrowId) {
		this.maxborrowId = maxborrowId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setIsday(String isday) {
		this.isday = isday;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public Date getRepaymentTime() {
		return repaymentTime;
	}

	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
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

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdatePersion() {
		return updatePersion;
	}

	public void setUpdatePersion(String updatePersion) {
		this.updatePersion = updatePersion;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public String getIsVouch() {
		return isVouch;
	}

	public void setIsVouch(String isVouch) {
		this.isVouch = isVouch;
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

	public void setVerifyTime(Timestamp verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getVerifyRemark() {
		return verifyRemark;
	}

	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}

	public int getRepaymentUser() {
		return repaymentUser;
	}

	public void setRepaymentUser(int repaymentUser) {
		this.repaymentUser = repaymentUser;
	}

	public String getForstAccount() {
		return forstAccount;
	}

	public void setForstAccount(String forstAccount) {
		this.forstAccount = forstAccount;
	}

	public String getRepaymentAccount() {
		return repaymentAccount;
	}

	public void setRepaymentAccount(String repaymentAccount) {
		this.repaymentAccount = repaymentAccount;
	}

	public String getMonthlyRepayment() {
		return monthlyRepayment;
	}

	public void setMonthlyRepayment(String monthlyRepayment) {
		this.monthlyRepayment = monthlyRepayment;
	}

	public String getRepaymentYesaccount() {
		return repaymentYesaccount;
	}

	public void setRepaymentYesaccount(String repaymentYesaccount) {
		this.repaymentYesaccount = repaymentYesaccount;
	}

	public String getRepamyentRemark() {
		return repamyentRemark;
	}

	public void setRepamyentRemark(String repamyentRemark) {
		this.repamyentRemark = repamyentRemark;
	}

	public void setSuccessTime(Timestamp successTime) {
		this.successTime = successTime;
	}

	public String getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(String paymentAccount) {
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

	public Integer getRepaymentPeriod() {
		return repaymentPeriod;
	}

	public void setRepaymentPeriod(Integer repaymentPeriod) {
		this.repaymentPeriod = repaymentPeriod;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountYes() {
		return accountYes;
	}

	public void setAccountYes(String accountYes) {
		this.accountYes = accountYes;
	}

	public String getTenderTimes() {
		return tenderTimes;
	}

	public void setTenderTimes(String tenderTimes) {
		this.tenderTimes = tenderTimes;
	}

	public double getApr() {
		return apr;
	}

	public void setApr(double apr) {
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

	public void setIsdaty(String isday) {
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

	public String getOperatorIp() {
		return operatorIp;
	}

	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	// add
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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


	public String getBorImage() {
		return borImage;
	}

	public void setBorImage(String borImage) {
		this.borImage = borImage;
	}

	public String getBorImageFirst() {
		return borImageFirst;
	}

	public void setBorImageFirst(String borImageFirst) {
		this.borImageFirst = borImageFirst;
	}

	public String getBorStages() {
		return borStages;
	}

	public void setBorStages(String borStages) {
		this.borStages = borStages;
	}

	public String getOrderList() {
		return orderList;
	}

	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}

	public int getDivides() {
		return divides;
	}

	public void setDivides(int divides) {
		this.divides = divides;
	}

	private String province;
	private String city;
	private String area;

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

	public Integer getParentFlg() {
		return parentFlg;
	}

	public void setParentFlg(Integer parentFlg) {
		this.parentFlg = parentFlg;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getParentMoney() {
		return parentMoney;
	}

	public void setParentMoney(Integer parentMoney) {
		this.parentMoney = parentMoney;
	}

	public Integer getChildMoneyWait() {
		return childMoneyWait;
	}

	public void setChildMoneyWait(Integer childMoneyWait) {
		this.childMoneyWait = childMoneyWait;
	}

	public Integer getChildMoneyFinish() {
		return childMoneyFinish;
	}

	public void setChildMoneyFinish(Integer childMoneyFinish) {
		this.childMoneyFinish = childMoneyFinish;
	}

	public Integer getChildMoneyReady() {
		return childMoneyReady;
	}

	public void setChildMoneyReady(Integer childMoneyReady) {
		this.childMoneyReady = childMoneyReady;
	}

	public BigDecimal getShowRepayCapital() {
		if (repayCapital==null) {
			return new BigDecimal(0);
		}
		return repayCapital;
	}
	
	public BigDecimal getShowRepayInterest() {
		if (repayInterest==null) {
			return new BigDecimal(0);
		}
		return repayInterest;
	}
	
	public BigDecimal getShowPayedCapital() {
		if (payedCapital==null) {
			return new BigDecimal(0);
		}
		return payedCapital;
	}
	
	public BigDecimal getShowPayedInterest() {
		if (payedInterest==null) {
			return new BigDecimal(0);
		}
		return payedInterest;
	}
	
	public BigDecimal getShowBalanceSize() {
		if (balance==null) return BigDecimal.valueOf(0);
		if (wanderPieceMoney==null||wanderPieceMoney.longValue()==0)return BigDecimal.valueOf(0);
		
		return BigDecimal.valueOf(Double.valueOf(balance)/wanderPieceMoney.longValue());
	}
	
	public int getShowSchedule() {
		if(schedule==null||"".equals(schedule)) return 0;
		return Double.valueOf(schedule).intValue();
	}
	
	public String getShowBorrowStatus() {
		
		String ret = "";
		if (status == 0) {
			ret = "待审中";
		} else if (status == 1) {
			ret = "募集中";
			if("2".equals(type)) {
				if (getShowBalanceSize().longValue() > 0) {
					ret = "募集中";
				} else {
					ret = "募集完成";
				}
			}
		} else if (status == 2) {
			ret = "审核拒绝";
		} else if (status == 3) {
			ret = "收益中";
		} else if (status == 4) {
			ret = "审核失败";
		} else if (status == 5) {
			ret = "待复审";
		} else if (status == 6) {
			ret = "失效";
		} else if (status == 7) {
			ret = "已还完";
		}

		if (!"2".equals(type) && status == 1) {

			Date end = CommonUtil.getDateAfter(verifyTime,
					Integer.parseInt((validTime==null||"".equals(validTime))?"0":validTime));
			Date now = new Date();
			if (end.before(now)) {
				ret = "已过期";
			}

		}
		
		return ret;
	}
	
	public String getShowButtonName() {
		
		String ret = "";
		if (status == 0) {
			ret = "待审中";
		} else if (status == 1) {
			ret = "立即投标";
			if("2".equals(type)) {
				if (getShowBalanceSize().longValue() > 0) {
					ret = "立即投标";
				} else {
					ret = "募集完成";
				}
			}
		} else if (status == 2) {
			ret = "审核拒绝";
		} else if (status == 3) {
			ret = "收益中";
		} else if (status == 4) {
			ret = "审核失败";
		} else if (status == 5) {
			ret = "待复审";
		} else if (status == 6) {
			ret = "失效";
		} else if (status == 7) {
			ret = "已还完";
		}
		if("5".equals(type) && status == 1 && (0<Double.valueOf(balance)&& Double.valueOf(balance)< Double.valueOf(account))){
			Date end = CommonUtil.getDateAfter(verifyTime,
					Integer.parseInt((validTime==null||"".equals(validTime))?"0":validTime));
			Date now = new Date();
			if (end.before(now)) {
				ret = "处理中";
			}
		}else if (!"2".equals(type) && status == 1) {

			Date end = CommonUtil.getDateAfter(verifyTime,
					Integer.parseInt((validTime==null||"".equals(validTime))?"0":validTime));
			Date now = new Date();
			if (end.before(now)) {
				ret = "已过期";
			}

		}
		
		return ret;
	}
	
	public int getShowButtonNameFlg() {
			
			int ret = 0;
			if (status == 0) {
				ret = 0;
			} else if (status == 1) {
				ret = 1;
				if("2".equals(type)) {
					if (getShowBalanceSize().longValue() > 0) {
						ret = 1;
					} else {
						ret =0;
					}
				} else {
					Date end = CommonUtil.getDateAfter(verifyTime,
							Integer.parseInt((validTime==null||"".equals(validTime))?"0":validTime));
					Date now = new Date();
					if (end.before(now)) {
						ret = 0;
					}
				}
			} 
			
			return ret;
		}
	
	public int getShowChildMoneyWait() {
		return (getParentMoney()==null?0:getParentMoney()) - (getChildMoneyFinish()==null?0:getChildMoneyFinish())- (getChildMoneyReady()==null?0:getChildMoneyReady());
	}

	public Integer getAwardType() {
		return awardType;
	}

	public void setAwardType(Integer awardType) {
		this.awardType = awardType;
	}

	public String getBorrowInfoJson() {
		return borrowInfoJson;
	}

	public void setBorrowInfoJson(String borrowInfoJson) {
		this.borrowInfoJson = borrowInfoJson;
	}

	public String getBorImage2() {
		return borImage2;
	}

	public void setBorImage2(String borImage2) {
		this.borImage2 = borImage2;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getBorrowVerifyJson() {
		return borrowVerifyJson;
	}

	public void setBorrowVerifyJson(String borrowVerifyJson) {
		this.borrowVerifyJson = borrowVerifyJson;
	}

	public BigDecimal getAwardScale() {
		return awardScale;
	}

	public void setAwardScale(BigDecimal awardScale) {
		this.awardScale = awardScale;
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
	
	
	
	}
