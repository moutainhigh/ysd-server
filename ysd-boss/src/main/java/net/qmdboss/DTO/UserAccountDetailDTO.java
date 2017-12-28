package net.qmdboss.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class UserAccountDetailDTO {
	private Integer id;// id
	private Integer userid;//用户id
	private String username;// 登录账户
	private String realName;//正式姓名
	private String type;// 明细类型
	private String typeValue;//
	private Date createDate;//时间
	private BigDecimal total;// 总金额
	private BigDecimal money;// 操作金额
	private BigDecimal useMoney;// 可用金额
	private BigDecimal noUseMoney;// 冻结金额
		
	private BigDecimal investorCollectionCapital;// 投资者待收本金
	private BigDecimal investorCollectionInterest;// 投资者待收利息
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getUseMoney() {
		return useMoney;
	}
	public void setUseMoney(BigDecimal useMoney) {
		this.useMoney = useMoney;
	}
	public BigDecimal getNoUseMoney() {
		return noUseMoney;
	}
	public void setNoUseMoney(BigDecimal noUseMoney) {
		this.noUseMoney = noUseMoney;
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
