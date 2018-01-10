package net.qmdboss.entity;

import net.qmdboss.util.SettingUtil;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * 实体类 - 支付配置
 * @author Xsf
 *
 */
@Entity
public class RechargeConfig  extends BaseEntity{

	private static final long serialVersionUID = -6038706895609445970L;

	// 支付手续费类型（按比例收费、固定费用）
	public enum PaymentFeeType {
		scale, fixed,scalejl, fixedjl
	}

	private String paymentProductId;//支付产品标识
	private String name;//支付方式名称
	private String bargainorId;//支付平台Id
	private String bargainorKey;//支付平台私钥
	private PaymentFeeType paymentFeeType;//支付手续费类型
	private BigDecimal paymentFee;//支付费用
	private String logoPath;//支付产品LOGO路径
	private String className;//支付类路径
	private String description;//介绍
	private Integer orderList;//排序
	private Boolean isEnabled;//是否启用
	
	private Set<UserAccountRecharge> userAccountRechargeSet = new HashSet<UserAccountRecharge>();//账户充值表

	private Set<SourceRecord> sourceRecordSet = new HashSet<SourceRecord>();//充值记录保存【不可修改记录】

	
	public String getPaymentProductId() {
		return paymentProductId;
	}

	public void setPaymentProductId(String paymentProductId) {
		this.paymentProductId = paymentProductId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBargainorId() {
		return bargainorId;
	}

	public void setBargainorId(String bargainorId) {
		this.bargainorId = bargainorId;
	}

	public String getBargainorKey() {
		return bargainorKey;
	}

	public void setBargainorKey(String bargainorKey) {
		this.bargainorKey = bargainorKey;
	}

	public PaymentFeeType getPaymentFeeType() {
		return paymentFeeType;
	}

	public void setPaymentFeeType(PaymentFeeType paymentFeeType) {
		this.paymentFeeType = paymentFeeType;
	}

	public BigDecimal getPaymentFee() {
		return paymentFee;
	}

	public void setPaymentFee(BigDecimal paymentFee) {
		this.paymentFee = paymentFee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@OneToMany(mappedBy = "rechargeInterface", fetch = FetchType.LAZY)
	public Set<UserAccountRecharge> getUserAccountRechargeSet() {
		return userAccountRechargeSet;
	}

	public void setUserAccountRechargeSet(
			Set<UserAccountRecharge> userAccountRechargeSet) {
		this.userAccountRechargeSet = userAccountRechargeSet;
	}

	@OneToMany(mappedBy = "rechargeConfig", fetch = FetchType.LAZY)
	public Set<SourceRecord> getSourceRecordSet() {
		return sourceRecordSet;
	}

	public void setSourceRecordSet(
			Set<SourceRecord> sourceRecordSet) {
		this.sourceRecordSet = sourceRecordSet;
	}
	
	
	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	

	/**
	 * 根据总金额计算支付费用
	 * 
	 * @return 支付费用
	 */
	@Transient
	public BigDecimal getPaymentFee(BigDecimal totalAmount) {
		BigDecimal paymentFee = new BigDecimal(0);
		if (paymentFeeType == PaymentFeeType.scale || paymentFeeType == PaymentFeeType.scalejl){
			paymentFee = totalAmount.multiply(this.paymentFee.divide(new BigDecimal(100)));
		} else {
			paymentFee = this.paymentFee;
		}
		return SettingUtil.setPriceScale(paymentFee);
	}

	
}
