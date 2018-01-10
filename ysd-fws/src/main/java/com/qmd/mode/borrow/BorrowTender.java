package com.qmd.mode.borrow;

import com.qmd.util.CommonUtil;
import org.codehaus.plexus.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * fy_borrow_detail表
 * @author 
 *
 */
public class BorrowTender {
	 private Integer id;

	 private Integer userId;//用户ID
	 private String status;//投标状态【1：全部通过;2：部分通过】
	 private Integer backStatus;//回滚状态值[0-表示没有回滚，1-表示撤销回滚】
	 private Integer borrowId;//标ID
	 private BigDecimal investApr;//投资利率
	 private String money;//预计投标金额
	 private String account;//实际成功金额
	 private String repaymentAccount;//应还款金额（借款金额+利息）
	 private String interest;//借款利息
	 private String repaymentYesaccount;//已还款金额【字段没用】
	 private String waitAccount;//待还款金额【字段没用】
	 private String repaymentYesinterest;//已还利息【字段没用】
	 private String waitInterest;//待还利息【字段没用】
	 private String addPersion;//添加人（投标用户ID）
	 private Date createTime;//创建时间
	 private String operatorIp;//操作IP
	 private String payPassword;//支付密码
	 private Date modifyDate;
	 private Date createDate;
	 private String username;
	 
	 private String wanderPiece;
	 
	 private String ableAmount;
	 private String continueAmount;
	 private String tasteAmount;//体验资金
	 private String directAmount;//直投资金
	 
	 private Date repaymentDate;
	 private BigDecimal loanAmountFee;//借出总额
	 private String name;
	 private BigDecimal borrowAccount;//借款人的借款金额
	 private double apr;//年利率 
	 private String timeLimit; // 标的期限
	 private String title;
	 private String type; //标类型
	 private String bStatus; //标状态
	 private String businessCode;//项目编号
	 
	 private Integer tenderWay;//投资方式 1线上，2线下
	 
	 private BigDecimal gain;//投资方获得佣金
	 
	 private Integer zqbid;//展期项目ID
	
	private String zqBusinessCode;//展期项目编号
	private String zqName;//展期标题
	private String zqTimeLimit;//展期期限
	private String zqApr;//展期利率
	private Date deferTime;//展期起始时间
	private Integer zqStatus;//展期状态
	private BigDecimal deferRate;//投资人展期投资的利率
	 
	 //一下用来标状态显示
	private Date borrowEndTime;
	private Date borrowVerifyTime;
	private String borrowValidTime;
	private String borrowBalance;
	private BigDecimal borrowWanderPieceMoney;
	
	private BigDecimal holdRate;//占有率  所投资金，在整个标中所占的比例
	private BigDecimal tiexiFee;//贴息金额
	private String interestPeriod;//每期利息
	
	private BigDecimal contractCharge;//费用
	
	private Integer zqzrStatus;//债权转让状态【0：不在转让中；1：成功转让；2：转让中（审核成功）；3：转让审核中】（投标默认为 0）
	 
	private Integer bisAssign;//是否可转让标【0：不可转让；1：可转让】发标初审时设置
	
	 private Integer autoTenderStatus; // 1自动，0手动，5展期

	//展期
	private Integer deferStatus;//是否同意展期【0：默认不同意；1：同意；2：拒绝；3：成功】 
	
	// 灵活宝
	private Integer borrowAgileId;//
	private Integer agileFlg; // 0定投宝1灵活宝2灵活宝完成
	private Integer agileStatus;//灵活宝状态： 0无，1进行中，2封闭期,3待定,9已完成 
	private String agileCode;
	private BigDecimal agileMoney; // 灵活宝在投金额'
	private BigDecimal agilePeriodYesinterest; // 周期内已获收益
	private Integer agileStepIntdate; // 下次阶梯变化日期
	private BigDecimal agileYesinterest;//
	private Date agileOpenDate;//可赎回开始日期（封闭期次日）
	private Date agileFinalDate;
	
	// 逾期相关字段
	private Integer lateStatus;//逾期状态：0正常1利息逾期2项目逾期
	private BigDecimal latePenalty;//逾期罚金
	private Integer lateDays;//逾期天数
	 
	public Date getBorrowEndTime() {
		return borrowEndTime;
	}
	public void setBorrowEndTime(Date borrowEndTime) {
		this.borrowEndTime = borrowEndTime;
	}
	public Date getBorrowVerifyTime() {
		return borrowVerifyTime;
	}
	public void setBorrowVerifyTime(Date borrowVerifyTime) {
		this.borrowVerifyTime = borrowVerifyTime;
	}
	public String getBorrowValidTime() {
		return borrowValidTime;
	}
	public void setBorrowValidTime(String borrowValidTime) {
		this.borrowValidTime = borrowValidTime;
	}
	public String getBorrowBalance() {
		return borrowBalance;
	}
	public void setBorrowBalance(String borrowBalance) {
		this.borrowBalance = borrowBalance;
	}
	public BigDecimal getBorrowWanderPieceMoney() {
		return borrowWanderPieceMoney;
	}
	public void setBorrowWanderPieceMoney(BigDecimal borrowWanderPieceMoney) {
		this.borrowWanderPieceMoney = borrowWanderPieceMoney;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}
	
	public BigDecimal getInvestApr() {
		return investApr;
	}
	public void setInvestApr(BigDecimal investApr) {
		this.investApr = investApr;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getRepaymentAccount() {
		return repaymentAccount;
	}
	public void setRepaymentAccount(String repaymentAccount) {
		this.repaymentAccount = repaymentAccount;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getRepaymentYesaccount() {
		return repaymentYesaccount;
	}
	public void setRepaymentYesaccount(String repaymentYesaccount) {
		this.repaymentYesaccount = repaymentYesaccount;
	}
	
	public String getWaitAccount() {
		return waitAccount;
	}
	public void setWaitAccount(String waitAccount) {
		this.waitAccount = waitAccount;
	}
	public String getRepaymentYesinterest() {
		return repaymentYesinterest;
	}
	public void setRepaymentYesinterest(String repaymentYesinterest) {
		this.repaymentYesinterest = repaymentYesinterest;
	}
	public String getWaitInterest() {
		return waitInterest;
	}
	public void setWaitInterest(String waitInterest) {
		this.waitInterest = waitInterest;
	}
	public String getAddPersion() {
		return addPersion;
	}
	public void setAddPersion(String addPersion) {
		this.addPersion = addPersion;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOperatorIp() {
		return operatorIp;
	}
	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}
	public String getWanderPiece() {
		return wanderPiece;
	}
	public void setWanderPiece(String wanderPiece) {
		this.wanderPiece = wanderPiece;
	}
	public String getAbleAmount() {
		return ableAmount;
	}
	public void setAbleAmount(String ableAmount) {
		this.ableAmount = ableAmount;
	}
	public String getContinueAmount() {
		return continueAmount;
	}
	public void setContinueAmount(String continueAmount) {
		this.continueAmount = continueAmount;
	}
	public Integer getBackStatus() {
		return backStatus;
	}
	public void setBackStatus(Integer backStatus) {
		this.backStatus = backStatus;
	}
	public Date getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public BigDecimal getLoanAmountFee() {
		return loanAmountFee;
	}
	public void setLoanAmountFee(BigDecimal loanAmountFee) {
		this.loanAmountFee = loanAmountFee;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getBorrowAccount() {
		return borrowAccount;
	}
	public void setBorrowAccount(BigDecimal borrowAccount) {
		this.borrowAccount = borrowAccount;
	}
	public double getApr() {
		return apr;
	}
	public void setApr(double apr) {
		this.apr = apr;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public String getbStatus() {
		return bStatus;
	}
	public void setbStatus(String bStatus) {
		this.bStatus = bStatus;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getTasteAmount() {
		return tasteAmount;
	}
	public void setTasteAmount(String tasteAmount) {
		this.tasteAmount = tasteAmount;
	}
	public String getDirectAmount() {
		return directAmount;
	}
	public void setDirectAmount(String directAmount) {
		this.directAmount = directAmount;
	}
	public Integer getTenderWay() {
		return tenderWay;
	}
	public void setTenderWay(Integer tenderWay) {
		this.tenderWay = tenderWay;
	}
	
	public BigDecimal getHoldRate() {
		if (holdRate==null) {
			return BigDecimal.ZERO;
		}
		return holdRate;
	}
	public void setHoldRate(BigDecimal holdRate) {
		this.holdRate = holdRate;
	}
	public String getShowChineseAccount() {
		double d = Double.valueOf(StringUtils.isEmpty(account)?"0":account);
		return CommonUtil.digitUppercase(d);
	}
	
	public BigDecimal getTiexiFee() {
		if (tiexiFee==null) {
			return BigDecimal.ZERO;
		}
		return tiexiFee;
	}
	public void setTiexiFee(BigDecimal tiexiFee) {
		this.tiexiFee = tiexiFee;
	}
	
	public String getInterestPeriod() {
		return interestPeriod;
	}
	public void setInterestPeriod(String interestPeriod) {
		this.interestPeriod = interestPeriod;
	}
	
	
	public BigDecimal getContractCharge() {
		return contractCharge;
	}
	public void setContractCharge(BigDecimal contractCharge) {
		this.contractCharge = contractCharge;
	}
	
	/**
	 * 违约金
	 * @return
	 */
	public BigDecimal getShowPenalty() {
		if(StringUtils.isEmpty(this.account)) {
			return BigDecimal.ZERO;
		}
		return CommonUtil.setPriceScale2BigDecimal(Double.valueOf(this.account)*0.2);
	}
	
	
	/**
	 * 未过期：1，过期0,空 -2
	 * @return
	 */
	public int getEndFlg() {
		if (borrowEndTime==null) {
			return -2;
		}
		
		if(CommonUtil.isEndDate(borrowEndTime) > 0) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * 显示余下的份数
	 * @return
	 */
	public BigDecimal getShowBalanceSize() {
		if (borrowBalance==null) return BigDecimal.valueOf(0);
		if (borrowWanderPieceMoney==null||borrowWanderPieceMoney.longValue()==0)return BigDecimal.valueOf(0);
		
		return BigDecimal.valueOf(Double.valueOf(borrowBalance)/borrowWanderPieceMoney.longValue());
	}
	
	public String getShowBorrowStatus() {
		
		String ret = "";
		if ("0".equals(status)) {
			ret = "待审中";
		} else if ("1".equals(status)) {
			
			ret = "招标中";
			if("5".equals(type)) {
				
				if (getEndFlg() !=1) {// 已过期
					return "还款中";
				}
				
				if (getShowBalanceSize().longValue() > 0) {
					ret = "募集中";
				} else {
					ret = "募集完成";
				}
			} else if("6".equals(type)){				
				if(borrowBalance!=null && Double.valueOf(borrowBalance) ==0){
					ret = "募集完成";
				}else{
					ret = "募集中";
				}
				
			}
		} else if ("2".equals(status)) {
			ret = "审核拒绝";
		} else if ("3".equals(status)) {
			ret = "还款中";
		} else if ("4".equals(status)) {
			ret = "审核失败";
		} else if ("5".equals(status)) {
			ret = "待复审";
		} else if ("6".equals(status)) {
			ret = "失效";
		} else if ("7".equals(status)) {
			ret = "已还完";
		}

		if (!"2".equals(type) && "1".equals(status)) {

			Date end = CommonUtil.getDateAfter(borrowVerifyTime,
					Integer.parseInt((borrowValidTime==null||"".equals(borrowValidTime))?"0":borrowValidTime));
			Date now = new Date();
			if (end.before(now)) {
				ret = "已过期";
			}

		}
		
		return ret;
	}
	public Integer getZqzrStatus() {
		return zqzrStatus;
	}
	public void setZqzrStatus(Integer zqzrStatus) {
		this.zqzrStatus = zqzrStatus;
	}
	public Integer getBisAssign() {
		return bisAssign;
	}
	public void setBisAssign(Integer bisAssign) {
		this.bisAssign = bisAssign;
	}
	public BigDecimal getGain() {
		return gain;
	}
	public void setGain(BigDecimal gain) {
		this.gain = gain;
	}
	public Integer getAutoTenderStatus() {
		return autoTenderStatus;
	}
	public void setAutoTenderStatus(Integer autoTenderStatus) {
		this.autoTenderStatus = autoTenderStatus;
	}
	public Integer getDeferStatus() {
		return deferStatus;
	}
	public void setDeferStatus(Integer deferStatus) {
		this.deferStatus = deferStatus;
	}
	public String getZqBusinessCode() {
		return zqBusinessCode;
	}
	public void setZqBusinessCode(String zqBusinessCode) {
		this.zqBusinessCode = zqBusinessCode;
	}
	public String getZqTimeLimit() {
		return zqTimeLimit;
	}
	public void setZqTimeLimit(String zqTimeLimit) {
		this.zqTimeLimit = zqTimeLimit;
	}
	public String getZqApr() {
		return zqApr;
	}
	public void setZqApr(String zqApr) {
		this.zqApr = zqApr;
	}
	public Date getDeferTime() {
		return deferTime;
	}
	public void setDeferTime(Date deferTime) {
		this.deferTime = deferTime;
	}
	public Integer getZqStatus() {
		return zqStatus;
	}
	public void setZqStatus(Integer zqStatus) {
		this.zqStatus = zqStatus;
	}
	public String getZqName() {
		return zqName;
	}
	public void setZqName(String zqName) {
		this.zqName = zqName;
	}
	
	public Integer getBorrowAgileId() {
		return borrowAgileId;
	}
	public void setBorrowAgileId(Integer borrowAgileId) {
		this.borrowAgileId = borrowAgileId;
	}
	public Integer getAgileFlg() {
		return agileFlg;
	}
	public void setAgileFlg(Integer agileFlg) {
		this.agileFlg = agileFlg;
	}
	public Integer getAgileStatus() {
		return agileStatus;
	}
	public void setAgileStatus(Integer agileStatus) {
		this.agileStatus = agileStatus;
	}
	public String getAgileCode() {
		return agileCode;
	}
	public void setAgileCode(String agileCode) {
		this.agileCode = agileCode;
	}
	public BigDecimal getAgileMoney() {
		return agileMoney;
	}
	public void setAgileMoney(BigDecimal agileMoney) {
		this.agileMoney = agileMoney;
	}
	public BigDecimal getAgilePeriodYesinterest() {
		return agilePeriodYesinterest;
	}
	public void setAgilePeriodYesinterest(BigDecimal agilePeriodYesinterest) {
		this.agilePeriodYesinterest = agilePeriodYesinterest;
	}
	public Integer getAgileStepIntdate() {
		return agileStepIntdate;
	}
	public void setAgileStepIntdate(Integer agileStepIntdate) {
		this.agileStepIntdate = agileStepIntdate;
	}
	public BigDecimal getAgileYesinterest() {
		return agileYesinterest;
	}
	public void setAgileYesinterest(BigDecimal agileYesinterest) {
		this.agileYesinterest = agileYesinterest;
	}
	public Date getAgileOpenDate() {
		return agileOpenDate;
	}
	public void setAgileOpenDate(Date agileOpenDate) {
		this.agileOpenDate = agileOpenDate;
	}
	public Date getAgileFinalDate() {
		return agileFinalDate;
	}
	public void setAgileFinalDate(Date agileFinalDate) {
		this.agileFinalDate = agileFinalDate;
	}
	public Integer getLateStatus() {
		return lateStatus;
	}
	public void setLateStatus(Integer lateStatus) {
		this.lateStatus = lateStatus;
	}
	public BigDecimal getLatePenalty() {
		return latePenalty;
	}
	public void setLatePenalty(BigDecimal latePenalty) {
		this.latePenalty = latePenalty;
	}
	public String getZstatus(){
		String ret = "等待审核";
//		if(zqStatus ==1){
//			ret = "已申请展期";
//		}else if(zqStatus ==2){
//			ret = "展期申请中";
//		}else if(zqStatus ==3){
//			ret = "申请展期未通过";
//		}else if(zqStatus ==4){
//			ret = "展期募集中";
//		}else if(zqStatus ==5){
//			ret = "展期发布失败";
//		}else if(zqStatus ==6){
//			ret = "已发布";
//		}
		return ret;		
	}
	public Integer getZqbid() {
		return zqbid;
	}
	public void setZqbid(Integer zqbid) {
		this.zqbid = zqbid;
	}
	
	public BigDecimal getDeferRate() {
		return deferRate;
	}
	public void setDeferRate(BigDecimal deferRate) {
		this.deferRate = deferRate;
	}
	public Integer getLateDays() {
		return lateDays;
	}
	public void setLateDays(Integer lateDays) {
		this.lateDays = lateDays;
	}
	public boolean getFlagTender(){
		if( deferTime != null && CommonUtil.compareDate(deferTime, CommonUtil.date2begin(new Date()) ) > 0){
			return true;
		}else{
			return false;
		}
	}
	
	
	
}
