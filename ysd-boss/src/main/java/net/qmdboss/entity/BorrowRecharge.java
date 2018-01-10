package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
@Entity
public class BorrowRecharge extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1471468108195941264L;
	
	
	private Integer type;
//	private Integer userId;// int(10) NULL 会员id
	private User user;
//	private Integer agencyId;// int(10) NULL 服务商id
	private Agency agency;
//	private Integer borrowId;// int(10) NULL 项目id
	private Borrow borrow;
//	private Integer borrowRepaymentId;// int(10) NULL 项目还款id
	private BorrowRepaymentDetail borrowRepayment;
	private Integer endFlg;
	private BigDecimal money;// decimal(15,2) NULL 金额
	private String rechargeName;// varchar(64) NULL 充值账户名
	private Date rechargeDate;// datetime NULL 充值时间
	private Integer rechargeType;// int(10) NULL 充值类型
	private String rechargeAccount;// varchar(64) NULL 充值卡号
	private String rechargeBank;// varchar(64) NULL 充值银行
	private String rechargeFile;// varchar(128) NULL 充值凭证
	private Date verifyTime;// datetime NULL 审核时间
	private String verifyRemark;// text NULL 审核备注
	private String verifyAdmin;// varchar(64) NULL 审核人
	private String verifyPhone;// 审核人手机
	
	private Integer status;// int(10) NULL 状态
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private Date createDate;// datetime NULL 创建日期
//	private Date modifyDate;// datetime NULL 修改日期
	
	
//	private Date rechargeDate;//充值完成时间
//	private String tradeNo;//订单号
//	
//	private Integer status;//状态【0：失败;1：成功;2:充值中】
//	private BigDecimal money;//金额
//	private RechargeConfig rechargeInterface;//充值接口【国付宝，支付宝; 为0的表示后台充值】
//	private String returned;//返回值【充值银行返回值】
//	private String type;//类型【默认:0：线下充值 ;1:线上充值;】2:补单
//	private String remark;//备注
//	private BigDecimal fee;//费用
//	private BigDecimal reward;//奖励
//	private Admin adminOperator;//操作人 createDate操作时间
//	private Admin adminVerify;//审核人 modifyDate 审核时间
//	private String verifyRemark;//审核人备注
//	private String ipUser;//用户ip
//	private String ipOperator;//操作人IP
//	private String ipVerify;//审核人IP
//	
//	private RechargeStatus rechargeStatus;//
//	private Temporary temporary;
	
//	private OffLineAccount offLineAccount;//线下充值账户表
	
	
//	/**比较后状态
//	 * 0:补单(本地充值中，接口成功);
//	 * 1:成功;
//	 * 2:未完结成功(两边都是处理中);
//	 * 3:异常数据(本地成功，接口非成功);
//	 * 4:失败(本地有数据，接口无数据)
//	**/
//	private Integer compareStatus;
//	
//	private String portData;//接口数据  0：已接受  	1：处理中  2：处理成功  3：处理失败  4:数据异常
//	
//	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_borrowRecharge_borrow")
	public Borrow getBorrow() {
		return borrow;
	}
	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_borrowRecharge_borrowRepayment")
	public BorrowRepaymentDetail getBorrowRepayment() {
		return borrowRepayment;
	}
	public void setBorrowRepayment(BorrowRepaymentDetail borrowRepayment) {
		this.borrowRepayment = borrowRepayment;
	}
	public String getRechargeName() {
		return rechargeName;
	}
	public void setRechargeName(String rechargeName) {
		this.rechargeName = rechargeName;
	}
	public Integer getRechargeType() {
		return rechargeType;
	}
	public void setRechargeType(Integer rechargeType) {
		this.rechargeType = rechargeType;
	}
	public String getRechargeAccount() {
		return rechargeAccount;
	}
	public void setRechargeAccount(String rechargeAccount) {
		this.rechargeAccount = rechargeAccount;
	}
	public String getRechargeBank() {
		return rechargeBank;
	}
	public void setRechargeBank(String rechargeBank) {
		this.rechargeBank = rechargeBank;
	}
	public String getRechargeFile() {
		return rechargeFile;
	}
	public void setRechargeFile(String rechargeFile) {
		this.rechargeFile = rechargeFile;
	}
	public Date getVerifyTime() {
		return verifyTime;
	}
	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}
	public Date getRechargeDate() {
		return rechargeDate;
	}
	public void setRechargeDate(Date rechargeDate) {
		this.rechargeDate = rechargeDate;
	}
//	public String getTradeNo() {
//		return tradeNo;
//	}
//	public void setTradeNo(String tradeNo) {
//		this.tradeNo = tradeNo;
//	}
//	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_borrowRecharge_user")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_borrowRecharge_agency")
	public Agency getAgency() {
		return agency;
	}
	public void setAgency(Agency agency) {
		this.agency = agency;
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
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	public RechargeConfig getRechargeInterface() {
//		return rechargeInterface;
//	}
//	public void setRechargeInterface(RechargeConfig rechargeInterface) {
//		this.rechargeInterface = rechargeInterface;
//	}
//	public String getReturned() {
//		return returned;
//	}
//	public void setReturned(String returned) {
//		this.returned = returned;
//	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
//	public String getRemark() {
//		return remark;
//	}
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
//	public BigDecimal getFee() {
//		return fee;
//	}
//	public void setFee(BigDecimal fee) {
//		this.fee = fee;
//	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@ForeignKey(name = "fk_userAccountRecharge_adminOperator")
//	public Admin getAdminOperator() {
//		return adminOperator;
//	}
//	public void setAdminOperator(Admin adminOperator) {
//		this.adminOperator = adminOperator;
//	}
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@ForeignKey(name = "fk_userAccountRecharge_adminVerify")
//	public Admin getAdminVerify() {
//		return adminVerify;
//	}
//	public void setAdminVerify(Admin adminVerify) {
//		this.adminVerify = adminVerify;
//	}
	
	public String getVerifyRemark() {
		return verifyRemark;
	}
	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}
	public Integer getEndFlg() {
		return endFlg;
	}
	public void setEndFlg(Integer endFlg) {
		this.endFlg = endFlg;
	}
	public String getVerifyAdmin() {
		return verifyAdmin;
	}
	public String getVerifyPhone() {
		return verifyPhone;
	}
	public void setVerifyAdmin(String verifyAdmin) {
		this.verifyAdmin = verifyAdmin;
	}
	public void setVerifyPhone(String verifyPhone) {
		this.verifyPhone = verifyPhone;
	}
	
	
//	public String getIpOperator() {
//		return ipOperator;
//	}
//	public void setIpOperator(String ipOperator) {
//		this.ipOperator = ipOperator;
//	}
//	public String getIpVerify() {
//		return ipVerify;
//	}
//	public void setIpVerify(String ipVerify) {
//		this.ipVerify = ipVerify;
//	}
	
	
//	// 保存处理
//	@Override
//	@Transient
//	public void onSave() {
//		tradeNo = SerialNumberUtil.buildPaymentSn();
//	}
//	public String getIpUser() {
//		return ipUser;
//	}
//	public void setIpUser(String ipUser) {
//		this.ipUser = ipUser;
//	}
//	public Integer getCompareStatus() {
//		return compareStatus;
//	}
//	public void setCompareStatus(Integer compareStatus) {
//		this.compareStatus = compareStatus;
//	}
//	public String getPortData() {
//		return portData;
//	}
//	public void setPortData(String portData) {
//		this.portData = portData;
//	}
//	
//	@OneToOne(mappedBy = "userAccountRecharge", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
//	@ForeignKey(name = "fk_userAccountRecharge_rechargeStatus")
//	public RechargeStatus getRechargeStatus() {
//		return rechargeStatus;
//	}
//	public void setRechargeStatus(RechargeStatus rechargeStatus) {
//		this.rechargeStatus = rechargeStatus;
//	}
//	
//	@OneToOne(mappedBy = "userAccountRecharge", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
//	@ForeignKey(name = "fk_userAccountRecharge_temporary")
//	public Temporary getTemporary() {
//		return temporary;
//	}
//	public void setTemporary(Temporary temporary) {
//		this.temporary = temporary;
//	}
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@ForeignKey(name = "fk_userAccountRecharge_offLineAccount")
//	public OffLineAccount getOffLineAccount() {
//		return offLineAccount;
//	}
//	public void setOffLineAccount(OffLineAccount offLineAccount) {
//		this.offLineAccount = offLineAccount;
//	}
//	
//	@Transient
//	public String getComDate() {
//		String str = ",";
//		String m =",,";
//		if(rechargeInterface.getId()!=0){
//			if(status!=null && temporary==null){
//				str="2,平台有数据、第三方没数据";
//			}else if(status==1 && temporary!= null && money.compareTo(temporary.getMoney())==0){
//				str="1,成功";
//			}else if(status==1 && temporary!= null && money.compareTo(temporary.getMoney())!=0){
//				str="5,交易金额不一致";
//			}
//			else if(status==2 && temporary!= null){
//				str="3,补单";
//			}
//			
//			if(temporary != null && money.compareTo(temporary.getMoney())!=0){
//				m=",0,金额异常";
//			}else if(temporary != null && money.compareTo(temporary.getMoney())==0){
//				m=",1,金额一致";
//			}
//		}else if(rechargeInterface.getId()==0 && status==1 ){
//			str="6,线下充值";
//			if(rechargeStatus!= null && rechargeStatus.getSaveState()==3){
//				m=",1,金额一致";
//			}
//		}
//		
//		return str + m;
//	}
//	public BigDecimal getReward() {
//		return reward == null ? new BigDecimal(0) :reward;
//	}
//	public void setReward(BigDecimal reward) {
//		this.reward = reward;
//	}
//	

	
}
