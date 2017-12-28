package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户详细资金属性类
 * 
 * @author zhanf
 * 
 */
@Entity
public class UserAccountDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String type;// 明细类型
	private BigDecimal total;// 总金额
	private BigDecimal money;// 操作金额
	private BigDecimal useMoney;// 可用金额
	private BigDecimal noUseMoney;// 冻结金额
	private BigDecimal collection;// 当前待收金额
	private Integer toUser;
	private String operatorer;// 操作人
	private String remark;// 明细描述
	private Date addTime;// 添加时间
	private String operatorIp;// 操作IP
	private User user;//

	private BigDecimal investorCollectionCapital;// 投资者待收本金
	private BigDecimal investorCollectionInterest;// 投资者待收利息
	private BigDecimal borrowerCollectionCapital;// 借款人待付本金
	private BigDecimal borrowerCollectionInterest;// 借款人待付利息
	private BigDecimal continueTotal;// 续投金额
	private BigDecimal tasteMoney; // 体验资金

	private Integer borrowId;// 标id

	// private Integer count;//记录条数
	// private BigDecimal money1;//金额
	// private String type1;//类型

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_userAccDetail_user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public BigDecimal getCollection() {
		return collection;
	}

	public void setCollection(BigDecimal collection) {
		this.collection = collection;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getToUser() {
		return toUser;
	}

	public void setToUser(Integer toUser) {
		this.toUser = toUser;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getOperatorIp() {
		return operatorIp;
	}

	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}

	public String getOperatorer() {
		return operatorer;
	}

	public void setOperatorer(String operatorer) {
		this.operatorer = operatorer;
	}

	// public Integer getCount() {
	// return count;
	// }
	// public void setCount(Integer count) {
	// this.count = count;
	// }
	// public BigDecimal getMoney1() {
	// return money1;
	// }
	// public void setMoney1(BigDecimal money1) {
	// this.money1 = money1;
	// }
	// public String getType1() {
	// return type1;
	// }
	// public void setType1(String type1) {
	// this.type1 = type1;
	// }
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

	public BigDecimal getBorrowerCollectionCapital() {
		return borrowerCollectionCapital;
	}

	public void setBorrowerCollectionCapital(BigDecimal borrowerCollectionCapital) {
		this.borrowerCollectionCapital = borrowerCollectionCapital;
	}

	public BigDecimal getBorrowerCollectionInterest() {
		return borrowerCollectionInterest;
	}

	public void setBorrowerCollectionInterest(BigDecimal borrowerCollectionInterest) {
		this.borrowerCollectionInterest = borrowerCollectionInterest;
	}

	public BigDecimal getContinueTotal() {
		return continueTotal;
	}

	public void setContinueTotal(BigDecimal continueTotal) {
		this.continueTotal = continueTotal;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public BigDecimal getTasteMoney() {
		return tasteMoney;
	}

	public void setTasteMoney(BigDecimal tasteMoney) {
		this.tasteMoney = tasteMoney;
	}

}
