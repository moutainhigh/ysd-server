package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Fangkuan  extends BaseEntity {

	private static final long serialVersionUID = -7627749886221997678L;
	private Integer type;//1:对项目放款；2:对保证金放款；3：对服务费放款
	
//	private Integer agencyId;//服务商ID
//	private Integer userId;//用户ID
//	private Integer borrowId;//项目ID
	
	private Agency agency;
	private User user;
	private Borrow borrow;
	
	private BigDecimal fangkuanMoney;//放款金额
	private String bankCard;//银行账号
	private String bankBranch;//银行支行
	private Integer status;//0:放款失败；1：放款成功；2：审核中
	private String ip;//申请IP
	private Date verifyTime;
	private String verifyAdmin;
	private String verifyPhone;
	private String verifyRemark;
	private String verifyIp;
	
	
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getFangkuanMoney() {
		return fangkuanMoney;
	}
	public void setFangkuanMoney(BigDecimal fangkuanMoney) {
		this.fangkuanMoney = fangkuanMoney;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getVerifyTime() {
		return verifyTime;
	}
	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}
	public String getVerifyAdmin() {
		return verifyAdmin;
	}
	public void setVerifyAdmin(String verifyAdmin) {
		this.verifyAdmin = verifyAdmin;
	}
	public String getVerifyPhone() {
		return verifyPhone;
	}
	public void setVerifyPhone(String verifyPhone) {
		this.verifyPhone = verifyPhone;
	}
	public String getVerifyRemark() {
		return verifyRemark;
	}
	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}
	public String getVerifyIp() {
		return verifyIp;
	}
	public void setVerifyIp(String verifyIp) {
		this.verifyIp = verifyIp;
	}

	@Transient
	public String getShowStatus(){
		String str="";
		if(status.compareTo(0)==0){
			str="放款失败";
		}else if(status.compareTo(1) ==0){
			str= "放款成功";
		}else if(status.compareTo(2) ==0){
			str= "审核中";
		}
		return str;
	}
	
	@Transient
	public String getShowType(){
		String str="";
		if(type.compareTo(1)==0){
			str="借款人放款";
		}else if(type.compareTo(2) ==0){
			str= "保证金放款";
		}else if(type.compareTo(3) ==0){
			str= "服务费放款";
		}
		return str;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_fangkuan_agency")
	public Agency getAgency() {
		return agency;
	}
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_fangkuan_user")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_fangkuan_borrow")
	public Borrow getBorrow() {
		return borrow;
	}
	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}
	
}
