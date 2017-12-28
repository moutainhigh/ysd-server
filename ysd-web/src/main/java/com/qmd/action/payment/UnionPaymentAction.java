package com.qmd.action.payment;

import com.qmd.action.base.BaseAction;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.service.payment.PaymentService;
import com.qmd.service.user.UserAccountService;
import com.qmd.service.user.UserService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银行支付接口
 * @author Administrator
 *
 */
@InterceptorRefs({
	@InterceptorRef(value = "userVerifyInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
@Service("unionPaymentAction")
public class UnionPaymentAction  extends BaseAction {
	
	private static final long serialVersionUID = 973380849828389964L;

	Logger log = Logger.getLogger(UnionPaymentAction.class);
	
	private Logger logPayment = Logger.getLogger("userPaymentLog");
	
	private User user;
	private UserAccountRecharge userAccountRecharge;
	private RechargeConfig firstRechargeConfig;
	private Map<String, String> parameterMap;// 支付参数
	
	private String paymentUrl;// 支付请求URL
	private String tradeNo;//支付编号
	private String payReturnMessage;// 支付返回信息
	private String payNotifyMessage;// 支付通知信息
	
	private String payment;
	private String mycode;
	
	@Resource
	UserService userService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	PaymentService paymentService;
	
	/**
	 * 跳转到充值页面
	 * @return
	 
	@Action(value="/union/rechargeTo",results={@Result(name="success", location="/content/payment/payment_recharge.ftl", type="freemarker"),
												 @Result(name="msg", location="/content/message_page.ftl", type="freemarker")})
	public String rechargeTo(){
		User loginuser = getLoginUser();
		if (loginuser.getPayPassword()==null||"".equals(loginuser.getPayPassword())) {
			msg = "请先设置交易密码";
			msgUrl = "/userCenter/toPayPassword.do";
			return "msg";
		}
		 if (loginuser.getRealStatus()==null||loginuser.getRealStatus()!=1) {
			msg = "请先进行绑卡认证！";
			msgUrl = "/userCenter/toBank.do";
			return "msg";
		}
		List<RechargeConfig> firstRcList =  getRechargeConfigList();
		
		if (firstRcList!=null && firstRcList.size() > 0) {
			firstRechargeConfig = firstRcList.get(0);
		}
		
		return SUCCESS;
	}
*/
	private String smsCode;//短信验证码
	
	/**
	 * 充值提交
	 * @return
	 
	@Action(value="/union/ajaxSubmit",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String submit(){
		log.info("开始充值");
		msgUrl="/payment/rechargeTo.do";
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		msgUrl="/payment/rechargeTo.do";
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		if (mycode == null || !mycode.equalsIgnoreCase(random)) {
			msg = "验证码输入错误!";
			return ajax(Status.error, msg);
		}
		reloadUser();
		User userLogin = getLoginUser();
		// 验证手机验证码
		if (StringUtils.isBlank(smsCode)) {
			return ajax(Status.error, "请输入短信验证码");
		}

		if (!smsCode.equals(userLogin.getPhoneCode())) {
			return ajax(Status.error, "短信验证码不正确");
		}
		
		logPayment.debug("【充值开始】"+userLogin.getUsername());
		
		UserAccountRecharge userRecharge = new UserAccountRecharge();
		Integer rechargeInterfaceId =60;//默认银联商务支付
		
		RechargeConfig rechargeConfig = paymentService.getPaymentConfig(rechargeInterfaceId);
		if (rechargeConfig == null) {
			logPayment.debug("【充值结束】"+userLogin.getUsername()+" 支付方式不允许为空!"+"["+rechargeInterfaceId+"]");
			return ajax(Status.error, "请选择支付方式");
		}
		
		BigDecimal paymentFee = null;// 支付手续费
		BigDecimal amountPayable = null;// 应付金额【不包含手续费】
		BasePaymentProduct paymentProduct = null;
		try{
			BigDecimal rechargeAmount = userAccountRecharge.getMoney();//充值金额
			if (rechargeAmount == null) {
				msg = "请输入充值金额!";
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 请输入充值金额!"+"["+rechargeAmount+"]");
				return ajax(Status.error, msg);
			}
			if (rechargeAmount.compareTo(new BigDecimal(1)) < 0) {
				msg = "充值金额必须大于1!";
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须大于1!"+"["+rechargeAmount+"]");
				return ajax(Status.error, msg);
			}
			if(rechargeAmount.compareTo(new BigDecimal(100000))>=0){
				msg = "充值金额必须小于100000!";
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须小于100000!"+"["+rechargeAmount+"]");
				return ajax(Status.error, msg);
			}
			if (rechargeAmount.scale() > ConstantUtil.PRICESCALE) {
				msg = "充值金额小数位超出限制!";
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额小数位超出限制!"+"["+rechargeAmount+"]");
				return ajax(Status.error, msg);
			}
			amountPayable = rechargeAmount;//.add(paymentFee);
			paymentFee = rechargeConfig.getPaymentFee(rechargeAmount);
			
			
		}catch (Exception e) {
			msg = "充值金额有误，请重新输入!";
			logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额有误，请重新输入!"+"");
			return ajax(Status.error, msg);
		}
		
		
		userRecharge.setCreateDate(new Date());
		userRecharge.setModifyDate(new Date());
		userRecharge.setTradeNo(SerialNumberUtil.buildPaymentSn());
		userRecharge.setUserId(getLoginUser().getId());
		userRecharge.setStatus(2);//充值中
		userRecharge.setMoney(CommonUtil.setPriceScale(amountPayable, null) );
		userRecharge.setRechargeInterfaceId(rechargeInterfaceId);
		userRecharge.setReturned("");
		userRecharge.setType("1");
		userRecharge.setRemark("充值"+CommonUtil.setPriceScale(amountPayable, null)+"元");//getLoginUser().getUsername()+rechargeConfig.getName()+
		userRecharge.setFee(paymentFee);
		userRecharge.setReward(new BigDecimal(0));//奖励为0
		userRecharge.setIpUser(getRequestRemoteAddr());
		
		int ret = paymentService.unionPaymentRecharge(userRecharge,userLogin);
		
		if(ret ==1){
			return ajax(Status.success, "充值成功");
		}else if(ret ==2){
			return ajax(Status.error, "系统处理中");
		}else {
			return ajax(Status.error, "充值失败");
		}
		
		
		

	}
*/
	@Action(value="/payment/poputContent",results={@Result(name="success", location="/content/payment/poputContent.ftl", type="freemarker")})
	public String poputContent(){
		return "success";
	}
	
	/**
	 * 获取支付方式列表 原始记录source record
	 * @return
	 */
	public List<RechargeConfig> getRechargeConfigList(){
		return paymentService.getPaymentConfigList();
	}
	
	/**
	 * 获取可用余额
	 * @return
	 */
	public BigDecimal getAbleMoney(){
		Map<String,Object> userMap = new HashMap<String,Object>();
		userMap.put("id", getLoginUser().getId());
		return userService.getUser(userMap).getAbleMoney();
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

	public RechargeConfig getFirstRechargeConfig() {
		return firstRechargeConfig;
	}

	public void setFirstRechargeConfig(RechargeConfig firstRechargeConfig) {
		this.firstRechargeConfig = firstRechargeConfig;
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

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getMycode() {
		return mycode;
	}

	public void setMycode(String mycode) {
		this.mycode = mycode;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
}
