package com.qmd.mode.payment;

import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类 - 支付配置
 * @author Xsf
 *
 */
public class RechargeConfig implements Serializable{

	private static final long serialVersionUID = -6038706895609445970L;

	private Integer id;
	private Date createDate;
	private Date modifyDate;
	private String paymentProductId;//支付产品标识
	private String name;//支付方式名称
	private String bargainorId;//支付平台Id
	private String bargainorKey;//支付平台私钥
	private Integer paymentFeeType;//支付手续费类型 scale, fixed,scalejl, fixedjl（按比例收费、固定费用、按比例奖励、固定费用奖励）
	private BigDecimal paymentFee;//支付费用
	private String description;//介绍
	private Integer orderList;//排序
	private Boolean isEnabled;//是否启用
	private String logoPath;//支付产品LOGO路径
	private String className;//支付类路径

	
	public String getPaymentProductId() {
		return paymentProductId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
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

	public Integer getPaymentFeeType() {
		return paymentFeeType;
	}

	public void setPaymentFeeType(Integer paymentFeeType) {
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
	
	
	/**
	 * 根据总金额计算支付费用
	 * 
	 * @return 支付费用
	 */
	public BigDecimal getPaymentFee(BigDecimal totalAmount) {
		BigDecimal paymentFee = new BigDecimal(0);
		if (paymentFeeType == 0 || paymentFeeType == 2){//按比例收取
			paymentFee = totalAmount.multiply(this.paymentFee.divide(new BigDecimal(100)));
		} else {
			paymentFee = this.paymentFee;
		}
//		BigDecimal maxFee = CommonUtil.setPriceScale(BigDecimal.valueOf(ConstantUtil.MAXFEE),ConstantUtil.RoundType.roundHalfUp);
		paymentFee = CommonUtil.setPriceScale(paymentFee,ConstantUtil.RoundType.roundHalfUp);
//		paymentFee= paymentFee.compareTo(maxFee)==1?maxFee:paymentFee;
		return paymentFee;
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
	
	
	
	
	
	
	
}
