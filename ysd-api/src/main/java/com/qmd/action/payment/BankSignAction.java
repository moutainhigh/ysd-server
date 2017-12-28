package com.qmd.action.payment;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.bean.bank.CashBank;
import com.qmd.bean.bank.UserBank;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.mode.util.Listing;
import com.qmd.payment.BasePaymentProduct;
import com.qmd.service.payment.PaymentService;
import com.qmd.service.user.*;
import com.qmd.service.util.ListingService;
import com.qmd.util.CommonUtil;
import com.qmd.util.IDCard;
import com.qmd.util.SerialNumberUtil;
import com.qmd.util.md5.PWDUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签约
 * 
 */
@InterceptorRefs({ @InterceptorRef(value = "userVerifyInterceptor"), @InterceptorRef(value = "qmdDefaultStack") })
@Service("bankSignAction")
public class BankSignAction extends BaseAction {

	Logger log = Logger.getLogger(BankSignAction.class);
	private Logger logPayment = Logger.getLogger("userPaymentLog");
	private static final long serialVersionUID = 6388112224388918414L;

	private User user;
	private UserAccountRecharge userAccountRecharge;
	private CashBank cashBank;
	private AccountBank accountBank;
	private String btype;

	private String type;// 充值条件-0:全部记录；1：充值成功；2：线上充值；3：线下充值
	private Map<String, String> parameterMap;// 支付参数
	private String paymentUrl;// 支付请求URL
	private String safepwd;// 安全密码

	private String tradeNo;//支付编号
	private String payReturnMessage;// 支付返回信息
	private String payNotifyMessage;// 支付通知信息
	private String realName;//真实姓名
	private String idNo;//身份证号
	private String bankId;//
	private String cardNo;

	private UserBank userBank;
	@Resource
	UserService userService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	UserAccountDetailService userAccountDetailService;
	@Resource
	UserAccountRechargeService userAccountRechargeService;
	@Resource
	AccountBankService accountBankService;
	@Resource
	AccountCashService accountCashService;
	@Resource
	PaymentService paymentService;
	@Resource
	ListingService listingService;

	/**
	 * 签约信息查询
	 * @return
	 */
//	@Action(value="/api/bankSignApi",results={@Result(type="json")})
//	@InputConfig(resultName = "error_ftl,success_ftl")
//	public String bankSignApi(){
//		BasePaymentProduct paymentProduct = null;
//		RechargeConfig rechargeConfig = paymentService.getPaymentConfig(50);
//		paymentProduct = paymentService.getPaymentProduct(50);
//		User user = getLoginUser();
//		paymentProduct.signApi( rechargeConfig, user, getRequest());
//		
//		
//	}
	
	/**
	 * 跳转银行卡绑定
	 * @return
	 */
	@Action(value="/bank/bankTo",results = { @Result(name = "success", location = "/content/h5bank/bankSign.ftl", type = "freemarker") })
	public String ajaxBankTo(){
		reloadUser();
		List<Listing> listingList  = listingService.getChildListingBySignList("account_bank");
//		User loginUser = getLoginUser();
		Map<String,Object> map= new HashMap<String ,Object>();
		map.put("id", getLoginUser().getId());
		User userLogin = this.userService.getUser(map);
		userBank = new UserBank();
		List<AccountBank> accountBankList = getAccountBankList();
		btype ="0";
		if(accountBankList.size()>0){
			userBank.setBankId(accountBankList.get(0).getBankId());
			userBank.setCardNo(accountBankList.get(0).getAccount());
			userBank.setBranch(accountBankList.get(0).getBranch());
			userBank.setStatus(accountBankList.get(0).getStatus().toString());
			if(accountBankList.get(0).getStatus().compareTo(0)==0){
				btype ="1";
			}
		}
		
		userBank.setRealName(userLogin.getRealName());
		userBank.setId(userLogin.getId().toString());
		userBank.setPhone(userLogin.getPhone());
		
		userBank.setIdNo(userLogin.getCardId());
		userBank.setRegisterTime(CommonUtil.getDate2String(userLogin.getCreateDate(), "yyyyMMddHHmmss"));
		return SUCCESS;		
		
	}
	
	/**
	 * 银行卡签约 提交
	 * 
	 * @return
	 */
	@Action(value="/bankSignSave",results={@Result(name="onLine", location="/content/payment/payment_submit.ftl", type="freemarker")})
	@InputConfig(resultName = "error_no_back_ftl,success_ftl")
	public String rechargeSave() {
		try {

			log.info("开始签约");
			User userLogin = getLoginUser();
			User user = userService.getById(userLogin.getId(), new User());
			if (user == null) {
				msg="请登录!";
				return "error_ftl";
			}
			if(StringUtils.isEmpty(user.getPayPassword())) {
				user.setPayPassword(PWDUtil.encode(safepwd,user.getRandomNum()));

			}else if(!userService.isPassword(user.getUsername(), safepwd, "1")) {
				msg="安全密码输入错误!";
				return "error_ftl";
			}

			UserAccountRecharge userRecharge = new UserAccountRecharge();
			Integer rechargeInterfaceId = 70;

			RechargeConfig rechargeConfig = paymentService.getPaymentConfig(rechargeInterfaceId);
			if (rechargeConfig == null) {
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 支付方式不允许为空!"+"["+rechargeInterfaceId+"]");
				msg="支付方式不存在!";
				return "error_ftl";
			}

			if(StringUtils.isEmpty(bankId)){
				msg="请选择银行类型!";
				return "error_ftl";
			}

			BasePaymentProduct paymentProduct = null;
			BigDecimal rechargeAmount = CommonUtil.setPriceScale(new BigDecimal(3));// 充值金额
			userRecharge.setCreateDate(new Date());
			userRecharge.setModifyDate(new Date());
//			userRecharge.setTradeNo(DateUtil.getCurrentDateTimeStr1());
			userRecharge.setTradeNo(SerialNumberUtil.buildPaymentSn());
			userRecharge.setUserId(getLoginUser().getId());
			userRecharge.setPhone(getLoginUser().getPhone());
			userRecharge.setStatus(2);// 充值中
			userRecharge.setMoney(rechargeAmount);
			userRecharge.setRechargeInterfaceId(rechargeInterfaceId);
			userRecharge.setReturned("");
			userRecharge.setType("1");
			userRecharge.setRemark("银行卡签约");
			userRecharge.setFee(BigDecimal.ZERO);
			userRecharge.setReward(BigDecimal.ZERO);// 奖励为0
			userRecharge.setIpUser(getRequestRemoteAddr());

			//银行卡信息
			List<AccountBank> accountBankList = getAccountBankList();
			if(btype.equals("0")){
				if(accountBankList.size()>=1){
						msg="同一账户只能添加一张提现银行卡!";
						return "error_ftl";
				}
				accountBank = new AccountBank();
			}else if(btype.equals("1")){
				accountBank= accountBankList.get(0);
			}
			accountBank.setBtype(btype);
			accountBank.setCreateDate(new Date());
			accountBank.setModifyDate(new Date());
			accountBank.setAddIp(getRequestRemoteAddr());
			accountBank.setBankId(bankId);
			accountBank.setAccount(cardNo);
			accountBank.setBranch(cashBank.getBranch());
			accountBank.setUserId(userLogin.getId());

			if(StringUtils.isEmpty(realName)){
				msg="真实姓名不能为空!";
				return "error_ftl";
			}
			if(StringUtils.isEmpty(idNo)){
				msg="身份证号不能为空!";
				return "error_ftl";
			}else{
				 IDCard cc = new IDCard();
				 String str = cc.IDCardValidate(idNo.trim());
				 if(str.equals("1")){
					 msg="身份证号不正确!";
					 return "error_ftl";
				 }
			}
			if(idNo.length()<18){
				msg="身份证号不正确!";
				return "error_ftl";
			}
			user.setRealName(realName);
			Map<String,Object> qMap = new HashMap<String,Object>();
			qMap.put("cardId", idNo);
			User qUser = userService.getUser(qMap);
			if(qUser != null){
				if(qUser.getId().compareTo(userLogin.getId())!=0){
					msg="此身份证已认证!";
					return "error_ftl";
				}

			}else{
				user.setCardId(idNo);
				user.setCardType("1");
				String birthday = user.getCardId().substring(6, 14);
				user.setBirthday(CommonUtil.getString2Date(birthday, "yyyyMMdd"));
				String sex ="1";
				String str= user.getCardId().substring(16, 17);
				if((Integer.valueOf(str)/2)==0){
					sex = "2";
				}
				user.setSex(sex);
			}
		
			paymentService.addUserAccountRecharge(accountBank,user);
			reloadUser();

			log.info("跳转到充值页面");
			logPayment.debug("【充值跳转】" + userLogin.getUsername() + ",单号:" + userRecharge.getTradeNo() + ",金额:" + userRecharge.getMoney() + ",充值方式:"
					+ rechargeConfig.getName());
			paymentProduct = paymentService.getPaymentProduct(rechargeInterfaceId);
			paymentUrl = paymentProduct.getPaymentUrl();
			userRecharge.setPhone(userLogin.getPhone());
			rechargeConfig.setClassName("com.qmd.payment.BaofooSignH5Utils");
			parameterMap = paymentProduct.getParameterMap(rechargeConfig, userRecharge,getRequest());

			return "onLine";
			
		} catch (Exception e) {
			e.printStackTrace();
			msg="程序内部错误!";
			return "error_ftl";
		}
	}

	

	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserAccountRecharge getUserAccountRecharge() {
		return userAccountRecharge;
	}

	public void setUserAccountRecharge(UserAccountRecharge userAccountRecharge) {
		this.userAccountRecharge = userAccountRecharge;
	}

	public Map<String, String> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, String> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public String getPaymentUrl() {
		return paymentUrl;
	}

	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}

	public String getSafepwd() {
		return safepwd;
	}

	public void setSafepwd(String safepwd) {
		this.safepwd = safepwd;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPayReturnMessage() {
		return payReturnMessage;
	}

	public void setPayReturnMessage(String payReturnMessage) {
		this.payReturnMessage = payReturnMessage;
	}

	public String getPayNotifyMessage() {
		return payNotifyMessage;
	}

	public void setPayNotifyMessage(String payNotifyMessage) {
		this.payNotifyMessage = payNotifyMessage;
	}

	public List<AccountBank> getAccountBankList(){
		return this.userService.queryAccountBank(getLoginUser().getId());
	}



	public CashBank getCashBank() {
		return cashBank;
	}



	public void setCashBank(CashBank cashBank) {
		this.cashBank = cashBank;
	}



	public AccountBank getAccountBank() {
		return accountBank;
	}



	public void setAccountBank(AccountBank accountBank) {
		this.accountBank = accountBank;
	}



	public String getBtype() {
		return btype;
	}



	public void setBtype(String btype) {
		this.btype = btype;
	}


	public String getRealName() {
		return realName;
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}





	public String getBankId() {
		return bankId;
	}





	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public UserBank getUserBank() {
		return userBank;
	}

	public void setUserBank(UserBank userBank) {
		this.userBank = userBank;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	
}
