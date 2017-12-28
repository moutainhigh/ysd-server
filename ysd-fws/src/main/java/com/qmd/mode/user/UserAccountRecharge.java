package com.qmd.mode.user;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户充值表
 * @author Xsf
 *
 */
public class UserAccountRecharge implements Serializable{

	private static final long serialVersionUID = -8167593622552387010L;
	
	private Integer id;
	private Date createDate;
	private Date modifyDate;
	private Date rechargeDate;//充值时间
	private String tradeNo;//订单号【19位】 tradeNo = SerialNumberUtil.buildPaymentSn();
	private Integer userId;
	private Integer status;//状态【0：失败;1：成功;2：充值中】
	private BigDecimal money;//金额
	private Integer rechargeInterfaceId;//充值接口【国付宝；为0的表示后台充值】
	private String bankName;//如果是直联充值，保存银行标识【标识,银行简称】
	private String returned;//返回值【充值银行返回值】
	private String type;//类型【默认:0：转账充值 ;1:线上充值;2:补单】
	private String remark;//备注
	private BigDecimal fee;//费用
	private BigDecimal reward;//奖励
	private String ipUser;//用户ip
	private Integer adminOperatorId;//操作人 createDate操作时间
	private Integer adminVerifyId;//审核人 modifyDate 审核时间
	private String verifyRemark;//审核人备注
	private String ipOperator;//操作人IP
	private String ipVerify;//审核人IP
	private Integer offLineAccountId;
	
	
	private String username;//用户名 -仅用于查询
	private String name;//充值名称-查询用
	
	private Integer rechargeSource;//充值来源：1用户自己，2机构代充，3还款充值
	private Integer rechargeRequesterId;//充值发起者ID 注：用户自己充值时，就是用户userid；机构代充时，为机构的ID
	
	private String spreadNo;//推广号
	
	private Integer agencyFlag;
	private Integer agencyId;
	private Integer agencyType;
	
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
	public Date getRechargeDate() {
		return rechargeDate;
	}
	public void setRechargeDate(Date rechargeDate) {
		this.rechargeDate = rechargeDate;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getAdminOperatorId() {
		return adminOperatorId;
	}
	public void setAdminOperatorId(Integer adminOperatorId) {
		this.adminOperatorId = adminOperatorId;
	}
	public Integer getAdminVerifyId() {
		return adminVerifyId;
	}
	public void setAdminVerifyId(Integer adminVerifyId) {
		this.adminVerifyId = adminVerifyId;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getMoney() {
		return money==null ? new BigDecimal(0):money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Integer getRechargeInterfaceId() {
		return rechargeInterfaceId;
	}
	public void setRechargeInterfaceId(Integer rechargeInterfaceId) {
		this.rechargeInterfaceId = rechargeInterfaceId;
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
		return fee==null ? new BigDecimal(0):fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getIpUser() {
		return ipUser;
	}
	public void setIpUser(String ipUser) {
		this.ipUser = ipUser;
	}
	public Integer getOffLineAccountId() {
		return offLineAccountId;
	}
	public void setOffLineAccountId(Integer offLineAccountId) {
		this.offLineAccountId = offLineAccountId;
	}
	public BigDecimal getReward() {
		return reward==null?new BigDecimal(0):reward;
	}
	public void setReward(BigDecimal reward) {
		this.reward = reward;
	}
	public Integer getRechargeSource() {
		return rechargeSource;
	}
	public void setRechargeSource(Integer rechargeSource) {
		this.rechargeSource = rechargeSource;
	}
	public Integer getRechargeRequesterId() {
		return rechargeRequesterId;
	}
	public void setRechargeRequesterId(Integer rechargeRequesterId) {
		this.rechargeRequesterId = rechargeRequesterId;
	}
	public String getSpreadNo() {
		return spreadNo;
	}
	public void setSpreadNo(String spreadNo) {
		this.spreadNo = spreadNo;
	}
	public Integer getAgencyFlag() {
		return agencyFlag;
	}
	public void setAgencyFlag(Integer agencyFlag) {
		this.agencyFlag = agencyFlag;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public Integer getAgencyType() {
		return agencyType;
	}
	public void setAgencyType(Integer agencyType) {
		this.agencyType = agencyType;
	}
	
}
