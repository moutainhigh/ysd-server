package net.qmdboss.DTO;

import java.math.BigDecimal;

public class UserAccountDTO {
	private Integer id;
	private String username;
	private String realName;
	private BigDecimal total;//用户总金额（可用金额+冻结金额+待收金额）
	private BigDecimal ableMoney;//可用金额，可提现金额
	private BigDecimal unableMoney;//冻结金额
	//private BigDecimal collection;//待收金额
	private BigDecimal awardMoney;//奖励
	private BigDecimal investorCollectionCapital;//投资者待收本金
	private BigDecimal investorCollectionInterest;//投资者待收利息
	/*
	private BigDecimal	borrowerCollectionCapital;//借款人待付本金
	private BigDecimal borrowerCollectionInterest;//借款人待付利息
	private BigDecimal continueTotal;//用户续投总额
	private Integer userPoints;
	private BigDecimal awardMoney;//奖励
	
	private BigDecimal tasteMoney;// 体验资金
	*/
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public BigDecimal getAwardMoney() {
		return awardMoney;
	}
	public void setAwardMoney(BigDecimal awardMoney) {
		this.awardMoney = awardMoney;
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
	
	
	
	
	
	
}