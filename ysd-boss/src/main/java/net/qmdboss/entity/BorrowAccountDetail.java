package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class BorrowAccountDetail  extends BaseEntity {

	
	private static final long serialVersionUID = -7689328798540201040L;
//	private Integer borrowId;
//	private Integer agencyId;
//	private Integer userId;
	

	private Agency agency;
	private User user;
	private Borrow borrow;
	
	private String type;
	private BigDecimal money;
	private BigDecimal borrowTotal;
	private BigDecimal borrowCapitalYes;//已还本金
	private BigDecimal borrowInterestYes;//已还利息
	private BigDecimal borrowCapitalNo;//未还本金
	private BigDecimal borrowInterestNo;//未还利息
	
	private String ip;
	private String remark;
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getBorrowTotal() {
		return borrowTotal;
	}
	public void setBorrowTotal(BigDecimal borrowTotal) {
		this.borrowTotal = borrowTotal;
	}
	public BigDecimal getBorrowCapitalYes() {
		return borrowCapitalYes;
	}
	public void setBorrowCapitalYes(BigDecimal borrowCapitalYes) {
		this.borrowCapitalYes = borrowCapitalYes;
	}
	public BigDecimal getBorrowInterestYes() {
		return borrowInterestYes;
	}
	public void setBorrowInterestYes(BigDecimal borrowInterestYes) {
		this.borrowInterestYes = borrowInterestYes;
	}
	public BigDecimal getBorrowCapitalNo() {
		return borrowCapitalNo;
	}
	public void setBorrowCapitalNo(BigDecimal borrowCapitalNo) {
		this.borrowCapitalNo = borrowCapitalNo;
	}
	public BigDecimal getBorrowInterestNo() {
		return borrowInterestNo;
	}
	public void setBorrowInterestNo(BigDecimal borrowInterestNo) {
		this.borrowInterestNo = borrowInterestNo;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_borrowAccountDetail_agency")
	public Agency getAgency() {
		return agency;
	}
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_borrowAccountDetail_user")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_borrowAccountDetail_borrow")
	public Borrow getBorrow() {
		return borrow;
	}
	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}
	
}
