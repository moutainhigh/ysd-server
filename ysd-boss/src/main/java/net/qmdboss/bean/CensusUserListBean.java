package net.qmdboss.bean;

import java.math.BigDecimal;

public class CensusUserListBean {
	private BigDecimal total0;// 用户总金额
	private BigDecimal sumr01;// 充值总额（充值表）
	private BigDecimal sumc01;// 提现总额(提现表)
	private BigDecimal suma11;// money_into 资金转入
	private BigDecimal suma21;// recharge 线上充值
	private BigDecimal suma22;// recharge_offline 线下充值
	private BigDecimal suma31;// interest_repayment 利息入账
	private BigDecimal suma32;// interest_repayment_continued 利息入续投
	private BigDecimal suma41;// award_add 投标奖励
	private BigDecimal suma42;// award_continued 续投奖励
	private BigDecimal suma43;// recharge_offline_reward 线下充值奖励
	private BigDecimal suma44;// offline_reward 奖励
	private BigDecimal sumb21;// recharge_success 提现成功
	private BigDecimal sumb31;// tender_mange 利息管理费用
	private BigDecimal sumb41;// offline_deduct 费用扣除

	// private BigDecimal suma2;// 充值金额：线上充值+线上充值
	// private BigDecimal suma3;// 已收净利息 ：利息入账+利息入续投 - 利息管理费用
	// private BigDecimal suma4;// 奖励 ：投标奖励+续投奖励+线下充值奖励+奖励
	// private BigDecimal sum0;//

	private Integer userId;
	private String username;
	private String realName;
	
	private Integer dailyWorkDateInt;

	public CensusUserListBean() {
	}

	// public CensusUserListBean(BigDecimal total0, BigDecimal sumr01,
	// BigDecimal sumc01, BigDecimal suma11, BigDecimal suma21,
	// BigDecimal suma22, BigDecimal suma31, BigDecimal suma32,
	// BigDecimal suma41, BigDecimal suma42, BigDecimal suma43,
	// BigDecimal suma44, BigDecimal sumb21, BigDecimal sumb31,
	// BigDecimal sumb41, Integer userId, String username, String realName) {
	// this.total0 = total0;
	// this.sumr01 = sumr01;
	// this.sumc01 = sumc01;
	// this.suma11 = suma11;
	// this.suma21 = suma21;
	// this.suma22 = suma22;
	// this.suma31 = suma31;
	// this.suma32 = suma32;
	// this.suma41 = suma41;
	// this.suma42 = suma42;
	// this.suma43 = suma43;
	// this.suma44 = suma44;
	// this.sumb21 = sumb21;
	// this.sumb31 = sumb31;
	// this.sumb41 = sumb41;
	// this.userId = userId;
	// this.username = username;
	// this.realName = realName;
	//
	// this.suma2 = suma21.add(suma22);
	// this.suma3 = suma31.add(suma32).subtract(sumb31);
	// this.suma4 = suma41.add(suma42).add(suma43).add(suma44);
	//
	// this.sum0 =
	// suma11.add(suma21).add(suma22).add(suma31).add(suma32).add(suma41).add(suma42).add(suma43).add(suma44).subtract(sumb21).subtract(sumb31).subtract(sumb41);
	//
	// }

	public BigDecimal getSuma2() {
		return getSuma21().add(getSuma22());
	}

	public BigDecimal getSuma3() {
		return getSuma31().add(getSuma32()).subtract(getSumb31());
	}

	public BigDecimal getSuma4() {
		return getSuma41().add(getSuma42()).add(getSuma43()).add(getSuma44());
	}

	public BigDecimal getSum0() {
		return getSuma11().add(getSuma21()).add(getSuma22()).add(getSuma31()).add(getSuma32())
				.add(getSuma41()).add(getSuma42()).add(getSuma43()).add(getSuma44())
				.subtract(getSumb21()).subtract(getSumb31()).subtract(getSumb41());
	}

	public BigDecimal getTotal0() {
		return total0;
	}

	public void setTotal0(BigDecimal total0) {
		this.total0 = total0;
	}

	public BigDecimal getSumr01() {
		return sumr01;
	}

	public void setSumr01(BigDecimal sumr01) {
		this.sumr01 = sumr01;
	}

	public BigDecimal getSumc01() {
		return sumc01;
	}

	public void setSumc01(BigDecimal sumc01) {
		this.sumc01 = sumc01;
	}

	public BigDecimal getSuma11() {
		return suma11;
	}

	public void setSuma11(BigDecimal suma11) {
		this.suma11 = suma11;
	}

	public BigDecimal getSuma21() {
		return suma21;
	}

	public void setSuma21(BigDecimal suma21) {
		this.suma21 = suma21;
	}

	public BigDecimal getSuma22() {
		return suma22;
	}

	public void setSuma22(BigDecimal suma22) {
		this.suma22 = suma22;
	}

	public BigDecimal getSuma31() {
		return suma31;
	}

	public void setSuma31(BigDecimal suma31) {
		this.suma31 = suma31;
	}

	public BigDecimal getSuma32() {
		return suma32;
	}

	public void setSuma32(BigDecimal suma32) {
		this.suma32 = suma32;
	}

	public BigDecimal getSuma41() {
		return suma41;
	}

	public void setSuma41(BigDecimal suma41) {
		this.suma41 = suma41;
	}

	public BigDecimal getSuma42() {
		return suma42;
	}

	public void setSuma42(BigDecimal suma42) {
		this.suma42 = suma42;
	}

	public BigDecimal getSuma43() {
		return suma43;
	}

	public void setSuma43(BigDecimal suma43) {
		this.suma43 = suma43;
	}

	public BigDecimal getSuma44() {
		return suma44;
	}

	public void setSuma44(BigDecimal suma44) {
		this.suma44 = suma44;
	}

	public BigDecimal getSumb21() {
		return sumb21;
	}

	public void setSumb21(BigDecimal sumb21) {
		this.sumb21 = sumb21;
	}

	public BigDecimal getSumb31() {
		return sumb31;
	}

	public void setSumb31(BigDecimal sumb31) {
		this.sumb31 = sumb31;
	}

	public BigDecimal getSumb41() {
		return sumb41;
	}

	public void setSumb41(BigDecimal sumb41) {
		this.sumb41 = sumb41;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getDailyWorkDateInt() {
		return dailyWorkDateInt;
	}

	public void setDailyWorkDateInt(Integer dailyWorkDateInt) {
		this.dailyWorkDateInt = dailyWorkDateInt;
	}

}
