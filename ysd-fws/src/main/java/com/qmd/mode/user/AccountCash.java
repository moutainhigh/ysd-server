package com.qmd.mode.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountCash implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7327462985256374737L;
	private int id ;
	private Integer userId;//用户ID
	private int status;//状态码【0-审核中；1-审核成功；2-审核失败；3-用户取消；4-处理中】
	private String account; //银行账号
	private String bank; //所属银行      
	private String branch;//支行
	private  BigDecimal total;//提现金额
	private BigDecimal credited;//到账总额
	private BigDecimal fee;//手续费
	private Integer verifyUserid;//审核ID
	private Date verifyTime; //审核时间
	private String verifyRemark;//审核备注
	private String addip;//添加人IP
	private Date modifyDate;//
	private Date createDate;//创建时间
	private String name;//对应查出的银行名称
	
	private BigDecimal ableCashMoney;//本次可提现金额
	private BigDecimal freeCashMoney;//本次免费提现金额  
	

	private Integer borrowid;//标ID【用于放款】
	private Integer agencyUserid;//服务商userId【用于放款】
	
	//仅查询用
	private String businessCode;//项目编号
	private String bname;//项目标题
	private String type;//项目类型
	private String username;//借款人用户名
	private String realName;//借款人实名
	private BigDecimal jkTotal;//借款总金额
//	private BigDecimal beforeFkSumMoney;//放款总金额
	
	private int retCode;// 1 提现成功 2 表示提现金额大于可提现金额 3 到账金额少于0（提现金额少于提现手续费）
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getCredited() {
		return credited;
	}
	public void setCredited(BigDecimal credited) {
		this.credited = credited;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public Integer getVerifyUserid() {
		return verifyUserid;
	}
	public void setVerifyUserid(Integer verifyUserid) {
		this.verifyUserid = verifyUserid;
	}
	public Date getVerifyTime() {
		return verifyTime;
	}
	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}
	public String getVerifyRemark() {
		return verifyRemark;
	}
	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}
	public String getAddip() {
		return addip;
	}
	public void setAddip(String addip) {
		this.addip = addip;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public BigDecimal getAbleCashMoney() {
		return ableCashMoney;
	}
	public void setAbleCashMoney(BigDecimal ableCashMoney) {
		this.ableCashMoney = ableCashMoney;
	}
	public BigDecimal getFreeCashMoney() {
		return freeCashMoney;
	}
	public void setFreeCashMoney(BigDecimal freeCashMoney) {
		this.freeCashMoney = freeCashMoney;
	}
	public Integer getBorrowid() {
		return borrowid;
	}
	public void setBorrowid(Integer borrowid) {
		this.borrowid = borrowid;
	}

	public Integer getAgencyUserid() {
		return agencyUserid;
	}
	public void setAgencyUserid(Integer agencyUserid) {
		this.agencyUserid = agencyUserid;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public BigDecimal getJkTotal() {
		return jkTotal;
	}
	public void setJkTotal(BigDecimal jkTotal) {
		this.jkTotal = jkTotal;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public int getRetCode() {
		return retCode;
	}
	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}
	
	public String getBtype() {
		String str = "";
		if("01".equals(type)){
			str = "房产抵押";
		}else if("02".equals(type)){
			str = "汽车质押";
		}else if("03".equals(type)){
			str = "动产质押";
		}
		return str;
		
	}

	//【0-审核中；1-审核成功；2-审核失败；3-用户取消；4-处理中】
	public String getFkStatus() {
		String str = "";
		if(status == 0){
			str = "审核中";
		}else if(status ==1){
			str = "放款成功";
		}else if(status ==2){
			str = "放款失败";
		}else if(status ==3){
			str = "申请人取消";
		}else if(status ==4){
			str = "处理中";
		}
		return str;
	}
	

	//【0-审核中；1-审核成功；2-审核失败；3-用户取消；4-处理中】
	public String getBstatus() {
		String str = "";
		if(status == 0){
			str = "审核中";
		}else if(status ==1){
			str = "审核成功";
		}else if(status ==2){
			str = "审核失败";
		}else if(status ==3){
			str = "申请人取消";
		}else if(status ==4){
			str = "处理中";
		}
		return str;
	}
	
	
//	public BigDecimal getBeforeFkSumMoney() {
//		return beforeFkSumMoney;
//	}
//	public void setBeforeFkSumMoney(BigDecimal beforeFkSumMoney) {
//		this.beforeFkSumMoney = beforeFkSumMoney;
//	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
}
