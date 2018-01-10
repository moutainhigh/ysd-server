package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 奖励、扣款、资金转入审核
 * @author Xsf
 *
 */
@Entity
public class Rewards extends BaseEntity{

	private static final long serialVersionUID = -6500779296683946128L;

	
	private Integer type;//【0：扣除；1：现金奖励；2:数据转入；3：红包奖励；4：红包扣除;5:添加体验金;6:扣除体验金】
	private User user;//操作对象
	private BigDecimal money;//操作金额
	private Integer status;//操作状态【0:失败；1：成功；2：审核中】
	private String operator;//申请人
	private String remark;//申请人备注
	private String ipOperator;//申请人IP
	private String verify;//审核人
	private String verifyRemark;//审核人备注
	private String ipVerify;//审核人IP
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_rewards_user")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getVerify() {
		return verify;
	}
	public void setVerify(String verify) {
		this.verify = verify;
	}
	public String getIpOperator() {
		return ipOperator;
	}
	public void setIpOperator(String ipOperator) {
		this.ipOperator = ipOperator;
	}
	public String getIpVerify() {
		return ipVerify;
	}
	public void setIpVerify(String ipVerify) {
		this.ipVerify = ipVerify;
	}
	public String getVerifyRemark() {
		return verifyRemark;
	}
	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}
	
	@Transient
	public Date getModifyTime(){
		if(modifyDate!= null && createDate.compareTo(modifyDate)!=0){
			return modifyDate;
		}else{
			return null;
		}
	}
	
}
