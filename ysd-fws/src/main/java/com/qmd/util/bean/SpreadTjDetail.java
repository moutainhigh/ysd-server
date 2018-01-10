package com.qmd.util.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 统计明细
 * @author xsf
 *
 */
public class SpreadTjDetail implements Serializable {

	private static final long serialVersionUID = 261208807159804577L;
	
	private Integer userid;//用户ID
	private String username;//用户名
	private String realName;//姓名
	
	private String phone;//手机号
	private Date registerDate;//注册时间
	
	private String cardNo;//卡号
	private String spreadNo;//邀请码
	private BigDecimal accountTotal;//投资总额
	private BigDecimal feeTotal;//佣金总额
	
	
	
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getSpreadNo() {
		return spreadNo;
	}
	public void setSpreadNo(String spreadNo) {
		this.spreadNo = spreadNo;
	}
	public BigDecimal getAccountTotal() {
		return accountTotal;
	}
	public void setAccountTotal(BigDecimal accountTotal) {
		this.accountTotal = accountTotal;
	}
	public BigDecimal getFeeTotal() {
		return feeTotal;
	}
	public void setFeeTotal(BigDecimal feeTotal) {
		this.feeTotal = feeTotal;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
