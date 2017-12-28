package net.qmdboss.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 充值记录保存【不可修改记录】
 * @author Xsf
 *
 */
@Entity
public class SourceRecord extends BaseEntity{

	private static final long serialVersionUID = 2953764970041368968L;
	
	private Date addDate;//提交时间
	private Date completionDate;		//完成时间
	private String transactionNumber;	//交易号
	private String tradeState;//交易状态【成功，失败】
	private String treatmentState;//处理状态
	private String tradeType;//交易类型
	private String balanceType;//收支类型
	private String payment;//支付方式
	private String tradingFloor;//交易场所
	private String counterparty;//交易对方
	private String associatedBank;//关联银行
	private String bankOrderNumber;//银行订单号
	private String orderNumber;//订单号
	private BigDecimal actualAmount;//实际收(付)金额(元)
	private BigDecimal transactionAmount;//交易金额（元）
	private BigDecimal handlingCharge ;//手续费（元）
	private String remark;//备注
	
	private RechargeConfig rechargeConfig;//支付配置
	
	
	private Integer comparisonState;//跟本地数据"状态"比对（0：失败；1：成功；2：本地没有此条数据）【不存入数据库】
	private Integer moneyState;//跟本地数据"充值金额"比对（0：失败；1：成功；）【不存入数据库】
	
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public Date getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}
	public String getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public String getTradeState() {
		return tradeState;
	}
	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}
	public String getTreatmentState() {
		return treatmentState;
	}
	public void setTreatmentState(String treatmentState) {
		this.treatmentState = treatmentState;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getBalanceType() {
		return balanceType;
	}
	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getTradingFloor() {
		return tradingFloor;
	}
	public void setTradingFloor(String tradingFloor) {
		this.tradingFloor = tradingFloor;
	}
	public String getCounterparty() {
		return counterparty;
	}
	public void setCounterparty(String counterparty) {
		this.counterparty = counterparty;
	}
	public String getAssociatedBank() {
		return associatedBank;
	}
	public void setAssociatedBank(String associatedBank) {
		this.associatedBank = associatedBank;
	}
	public String getBankOrderNumber() {
		return bankOrderNumber;
	}
	public void setBankOrderNumber(String bankOrderNumber) {
		this.bankOrderNumber = bankOrderNumber;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public BigDecimal getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public BigDecimal getHandlingCharge() {
		return handlingCharge;
	}
	public void setHandlingCharge(BigDecimal handlingCharge) {
		this.handlingCharge = handlingCharge;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public RechargeConfig getRechargeConfig() {
		return rechargeConfig;
	}
	public void setRechargeConfig(RechargeConfig rechargeConfig) {
		this.rechargeConfig = rechargeConfig;
	}
	public Integer getComparisonState() {
		return comparisonState;
	}
	public void setComparisonState(Integer comparisonState) {
		this.comparisonState = comparisonState;
	}
	public Integer getMoneyState() {
		return moneyState;
	}
	public void setMoneyState(Integer moneyState) {
		this.moneyState = moneyState;
	}
}
