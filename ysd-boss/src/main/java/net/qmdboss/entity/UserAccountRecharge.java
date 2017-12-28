package net.qmdboss.entity;

import net.qmdboss.util.SerialNumberUtil;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户充值表
 * @author Xsf
 *
 */
@Entity
public class UserAccountRecharge extends BaseEntity{

	private static final long serialVersionUID = -8167593622552387010L;
	
	private Date rechargeDate;//充值完成时间
	private String tradeNo;//订单号
	private User user;
	private Integer status;//状态【0：失败;1：成功;2:充值中】
	private BigDecimal money;//金额
	private RechargeConfig rechargeInterface;//充值接口【国付宝，支付宝; 为0的表示后台充值】
	private String returned;//返回值【充值银行返回值】
	private String type;//类型【默认:0：线下充值 ;1:线上充值;】2:补单
	private String remark;//备注
	private BigDecimal fee;//费用
	private BigDecimal reward;//奖励
	private Admin adminOperator;//操作人 createDate操作时间
	private Admin adminVerify;//审核人 modifyDate 审核时间
	private String verifyRemark;//审核人备注
	private String ipUser;//用户ip
	private String ipOperator;//操作人IP
	private String ipVerify;//审核人IP
	
	
	
	
	/**比较后状态
	 * 0:补单(本地充值中，接口成功);
	 * 1:成功;
	 * 2:未完结成功(两边都是处理中);
	 * 3:异常数据(本地成功，接口非成功);
	 * 4:失败(本地有数据，接口无数据)
	**/
	private Integer compareStatus;
	
	private String portData;//接口数据  0：已接受  	1：处理中  2：处理成功  3：处理失败  4:数据异常
	
	
	
	public Date getRechargeDate() {
		return rechargeDate;
	}
	public void setRechargeDate(Date rechargeDate) {
		this.rechargeDate = rechargeDate;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_userAccountRecharge_user")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public RechargeConfig getRechargeInterface() {
		return rechargeInterface;
	}
	public void setRechargeInterface(RechargeConfig rechargeInterface) {
		this.rechargeInterface = rechargeInterface;
	}
	public String getReturned() {
		return returned;
	}
	public void setReturned(String returned) {
		this.returned = returned;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_userAccountRecharge_adminOperator")
	public Admin getAdminOperator() {
		return adminOperator;
	}
	public void setAdminOperator(Admin adminOperator) {
		this.adminOperator = adminOperator;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_userAccountRecharge_adminVerify")
	public Admin getAdminVerify() {
		return adminVerify;
	}
	public void setAdminVerify(Admin adminVerify) {
		this.adminVerify = adminVerify;
	}
	
	public String getVerifyRemark() {
		return verifyRemark;
	}
	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
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
	
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		tradeNo = SerialNumberUtil.buildPaymentSn();
	}
	public String getIpUser() {
		return ipUser;
	}
	public void setIpUser(String ipUser) {
		this.ipUser = ipUser;
	}
	public Integer getCompareStatus() {
		return compareStatus;
	}
	public void setCompareStatus(Integer compareStatus) {
		this.compareStatus = compareStatus;
	}
	public String getPortData() {
		return portData;
	}
	public void setPortData(String portData) {
		this.portData = portData;
	}
	
	public BigDecimal getReward() {
		return reward == null ? new BigDecimal(0) :reward;
	}
	public void setReward(BigDecimal reward) {
		this.reward = reward;
	}
	

	
}
