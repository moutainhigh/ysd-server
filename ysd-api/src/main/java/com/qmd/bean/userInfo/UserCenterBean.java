package com.qmd.bean.userInfo;

import com.qmd.bean.BaseBean;
import com.qmd.mode.user.User;

import java.math.BigDecimal;
import java.util.Date;

public class UserCenterBean extends BaseBean {

	private static final long serialVersionUID = -4204234226360031325L;

	public UserCenterBean() {
		setRcd("R0001");
	}

	public UserCenterBean(User user) {
		setRcd("R0001");
		this.litpic = user.getLitpic();
		this.username = user.getUsername();
		this.realStatus = user.getRealStatus();
		this.realName = user.getRealName();
		this.ableMoney=user.getAbleMoney();// 可用金额，可提现金额
		this.awardMoney = user.getAwardMoney();// 奖励
//		this.tasteMoney = user.getTasteMoney();//体验金
		this.investorCollectionCapital = user.getInvestorCollectionCapital();// 投资者待收本金
		this.investorCollectionInterest = user.getInvestorCollectionInterest();// 投资者待收利息
		this.gesture = user.getGesture();
		this.total = user.getTotal();// 用户总金额（可用金额+冻结金额+待收金额）
		this.autoTenderStatus = user.getAutoTenderStatus();
	}

	
	private String username;// 会员用户名
	private String litpic;
	// 实名认证是否通过
	private Integer realStatus;
	// 会员真实姓名
	private String realName;

	private BigDecimal ableMoney;// 可用金额，可提现金额
//	private BigDecimal tasteMoney;// 体验资金 -- 不计入总额
	private BigDecimal awardMoney;// 奖励
	private BigDecimal investorCollectionCapital;// 投资者待收本金
	private BigDecimal investorCollectionInterest;// 投资者待收利息

	private BigDecimal total;// 用户总金额（可用金额+冻结金额+待收金额）
	private BigDecimal totalIncome;// 累计收益
	private String gesture;//手势密码
	
	private Date tenderTime;//最近投资日期
	private Date repaymentTime;//最近回款日期
	
	private BigDecimal tenderMoney;//投资金额
	private BigDecimal repaymentMoney;//回款金额
	private Integer autoTenderStatus;// 自投状态1启用0关闭

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRealStatus() {
		return realStatus;
	}

	public void setRealStatus(Integer realStatus) {
		this.realStatus = realStatus;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public BigDecimal getAbleMoney() {
		return ableMoney;
	}

	public void setAbleMoney(BigDecimal ableMoney) {
		this.ableMoney = ableMoney;
	}

	public BigDecimal getAwardMoney() {
		return awardMoney;
	}

	public void setAwardMoney(BigDecimal awardMoney) {
		this.awardMoney = awardMoney;
	}

	public BigDecimal getInvestorCollectionCapital() {
		return investorCollectionCapital;
	}

	public void setInvestorCollectionCapital(
			BigDecimal investorCollectionCapital) {
		this.investorCollectionCapital = investorCollectionCapital;
	}

	public BigDecimal getInvestorCollectionInterest() {
		return investorCollectionInterest;
	}

	public void setInvestorCollectionInterest(
			BigDecimal investorCollectionInterest) {
		this.investorCollectionInterest = investorCollectionInterest;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

//	public BigDecimal getTasteMoney() {
//		return tasteMoney;
//	}
//
//	public void setTasteMoney(BigDecimal tasteMoney) {
//		this.tasteMoney = tasteMoney;
//	}

	public String getGesture() {
		return gesture;
	}

	public void setGesture(String gesture) {
		this.gesture = gesture;
	}

	public Date getTenderTime() {
		return tenderTime;
	}

	public void setTenderTime(Date tenderTime) {
		this.tenderTime = tenderTime;
	}

	public Date getRepaymentTime() {
		return repaymentTime;
	}

	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}


	public Integer getAutoTenderStatus() {
		return autoTenderStatus;
	}

	public void setAutoTenderStatus(Integer autoTenderStatus) {
		this.autoTenderStatus = autoTenderStatus;
	}

	public BigDecimal getTenderMoney() {
		return tenderMoney;
	}

	public void setTenderMoney(BigDecimal tenderMoney) {
		this.tenderMoney = tenderMoney;
	}

	public BigDecimal getRepaymentMoney() {
		return repaymentMoney;
	}

	public void setRepaymentMoney(BigDecimal repaymentMoney) {
		this.repaymentMoney = repaymentMoney;
	}

	public String getLitpic() {
		return litpic;
	}

	public void setLitpic(String litpic) {
		this.litpic = litpic;
	}

}
