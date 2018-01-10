package net.qmdboss.bean;

import java.math.BigDecimal;

public class AccountDetail {

	private Integer count;//记录条数
	private BigDecimal money;//金额
	private String type;//类型
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
