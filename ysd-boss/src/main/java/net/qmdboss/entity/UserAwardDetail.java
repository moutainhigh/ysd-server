package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

/**
 *
 */
@Entity
public class UserAwardDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 用户ID
	private User user;
	// 类型（同资金记录）
	private String type;
	// 操作金额
	private BigDecimal money;
	// 奖励账户
	private BigDecimal awardMoney;
	private Integer signFlg;
	// 备注
	private String remark;
	// 资金记录ID
	private Integer userAccountDetailId;
	// 相关id
	private Integer relateTo;
	// 相关项目
	private String relateKey;
	// 后备字段
	private String reserve1;


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

	public BigDecimal getAwardMoney() {
		return awardMoney;
	}

	public void setAwardMoney(BigDecimal awardMoney) {
		this.awardMoney = awardMoney;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getUserAccountDetailId() {
		return userAccountDetailId;
	}

	public void setUserAccountDetailId(Integer userAccountDetailId) {
		this.userAccountDetailId = userAccountDetailId;
	}

	public Integer getRelateTo() {
		return relateTo;
	}

	public void setRelateTo(Integer relateTo) {
		this.relateTo = relateTo;
	}

	public String getRelateKey() {
		return relateKey;
	}

	public void setRelateKey(String relateKey) {
		this.relateKey = relateKey;
	}

	public String getReserve1() {
		return reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public Integer getSignFlg() {
		return signFlg;
	}

	public void setSignFlg(Integer signFlg) {
		this.signFlg = signFlg;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_userAwardDetail_user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
