package com.qmd.action.payment;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.action.user.UserAction;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.service.payment.PaymentService;
import com.qmd.service.user.UserAccountService;
import com.qmd.service.user.UserService;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;
import com.qmd.util.SerialNumberUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InterceptorRefs({
	@InterceptorRef(value = "userVerifyInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
@Service("paymentAction")
public class PaymentAction extends BaseAction {
	
	private static final long serialVersionUID = 3812790719313252342L;
	Logger log = Logger.getLogger(UserAction.class);
	
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
	
	private String tempStr;//充值方式名称-主要用于线下充值类别
	
//	private String mycode;//验证码
	
	private String flag;//标记-充值完成跳转页面
	
	@Resource
	UserService userService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	PaymentService paymentService;
	
	/**
	 * 跳转到'在线'充值页面
	 * @return
	 */
	@Action(value="/payment/rechargeTo",results={@Result(name="success", location="/content/payment/payment_recharge.ftl", type="freemarker"),
												 @Result(name="msg", location="/content/message_page.ftl", type="freemarker")})
	public String rechargeTo(){
		User loginuser = getLoginUser();
		if (loginuser.getPayPassword()==null||"".equals(loginuser.getPayPassword())) {
			msg = "请先设置安全密码";
			msgUrl = "/userCenter/toPayPassword.do";
			return "msg";
		}
//充值无需实名认证		
//		else if (loginuser.getRealStatus()==null||loginuser.getRealStatus()!=1) {
//			msg = "请先进行账户认证！";
//			msgUrl = "/userCenter/realname.do";
//			return "msg";
//		}
		
		List<RechargeConfig> firstRcList =  getRechargeConfigList();
		
		if (firstRcList!=null && firstRcList.size() > 0) {
			firstRechargeConfig = firstRcList.get(0);
		}
		
		return SUCCESS;
	}	
	

	/**
	 * 跳转到'门店'充值页面
	 * @return
	 */
//	@Action(value="/payment/rechargeToStore",results={@Result(name="success", location="/content/payment/payment_recharge_store.ftl", type="freemarker"),
//												 @Result(name="msg", location="/content/message_page.ftl", type="freemarker")})
//	public String rechargeToStore(){
//		User loginuser = getLoginUser();
//		if (loginuser.getPayPassword()==null||"".equals(loginuser.getPayPassword())) {
//			msg = "请先设置安全密码";
//			msgUrl = "/userCenter/toPayPassword.do";
//			return "msg";
//		}else if (loginuser.getRealStatus()==null||loginuser.getRealStatus()!=1) {
//			msg = "请先进行账户认证！";
//			msgUrl = "/userCenter/realname.do";
//			return "msg";
//		}
//		
//		List<RechargeConfig> firstRcList =  getRechargeConfigList();
//		
//		if (firstRcList!=null && firstRcList.size() > 0) {
//			firstRechargeConfig = firstRcList.get(0);
//		}
//		
//		return SUCCESS;
//	}	
	
	/**
	 * 跳转到'在线'充值页面
	 * @return
	 */
	@Action(value="/payment/rechargeToAgency",results={@Result(name="success", location="/content/payment/payment_recharge_agency.ftl", type="freemarker"),
												 @Result(name="msg", location="/content/message_page.ftl", type="freemarker")})
	public String rechargeToAgency(){
		User loginuser = getLoginUser();
		if (loginuser.getPayPassword()==null||"".equals(loginuser.getPayPassword())) {
			msg = "请先设置安全密码";
			msgUrl =getContextPath()+ "/userCenter/toPayPassword.do";
			return "msg";
		}else if (loginuser.getRealStatus()==null||loginuser.getRealStatus()!=1) {
			msg = "请先进行账户认证！";
			msgUrl =getContextPath()+ "/userCenter/realname.do";
			return "msg";
		}
		
		List<RechargeConfig> firstRcList =  getRechargeConfigList();
		
		if (firstRcList!=null && firstRcList.size() > 0) {
			firstRechargeConfig = firstRcList.get(0);
		}
		
		return SUCCESS;
	}	
	
	
	
	@Action(value="/payment/ajaxPaymentFee",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String ajaxPaymentFee(){
		try{
			Integer rechargeInterfaceId =null;
			if(!this.isNumeric(payment)){//直联支付【字母(ICBC)】
				rechargeInterfaceId = 32;//默认国付宝
				userAccountRecharge.setBankName(payment);
			}else{//接口支付
				rechargeInterfaceId = Integer.parseInt(payment);
			}
			
			BigDecimal rechargeAmount = userAccountRecharge.getMoney();//充值金额
			
			RechargeConfig rechargeConfig = paymentService.getPaymentConfig(rechargeInterfaceId);
			if (rechargeConfig == null) {
				return ajax(Status.warn,"支付方式不存在!");
			}
			BigDecimal paymentFee = null;// 支付手续费
			paymentFee = rechargeConfig.getPaymentFee(rechargeAmount);
			BigDecimal actual =null;
			if(rechargeInterfaceId == 0){ //转账充值 有奖励
				 actual = rechargeAmount.add(paymentFee);//到账金额
			}else{
				 actual = rechargeAmount.subtract(paymentFee);//到账金额
			}
			if(actual.compareTo(new BigDecimal(0)) <= 0){
				return ajax(Status.warn,"充值金额太小");
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(STATUS_PARAMETER_NAME, Status.success);
			map.put("paymentFee",paymentFee.toString());
			map.put("actualToAccount",actual.toString());//rechargeAmount.subtract(paymentFee)
			return ajax(map);
		}catch (Exception e) {
			return ajax(Status.warn,"发生错误");
		}
	}
	
	
	/**
	 * 门店充值提交
	 * @return
	 */
	@Action(value="/payment/ajaxSubmitAgency",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String submitAgency(){
		log.info("开始门店充值");
		
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		if (!this.getMycode().equals(random)) {
			return ajax(Status.warn,"验证码输入错误!");
		}
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		
		User userLogin = getLoginUser();
		
		if (userLogin.getTypeId()!=1||(!"1".equals(userLogin.getAgencytype()) && !"9".equals(userLogin.getAgencytype()))) {
			return ajax(Status.warn,"门店充值只有特定机构才能操作!");
		}
		
		if(!userService.isPassword(userLogin.getUsername(), user.getPayPassword(), "1")){
			return ajax(Status.warn,"安全密码输入错误!");
		}
		
		String name = userAccountRecharge.getUsername();
		if (StringUtils.isEmpty(name)) {
			return ajax(Status.warn,"请输入充值用户名!");
		}
		
		Map<String,Object> map= new HashMap<String ,Object>();
//		if(HasKeyWord.hasKeyWord(getServletContext(),user.getUsername())){
//			log.info("含敏感字符");
//			return ajax("false");
//		}
		map.put("username", name);
		User customUser = userService.getUser(map);
		if(customUser==null){
			addActionError("用户不存在!");
			return ajax(Status.warn,"用户不存在!");
		}
		
		userAccountRecharge.setRechargeSource(2);
		
		logPayment.debug("【机构充值开始】"+userLogin.getUsername()+",["+payment+"]");
		
		UserAccountRecharge userRecharge = new UserAccountRecharge();
		Integer rechargeInterfaceId =0;//默认转账充值
		
		rechargeInterfaceId = Integer.parseInt(payment);
		
		if(rechargeInterfaceId != 1 && rechargeInterfaceId != 2 && rechargeInterfaceId != 3){
			addActionError("机构充值不支持此支付方式!");
			logPayment.debug("【机构充值结束】"+userLogin.getUsername()+" 机构充值不支持此支付方式!"+"["+rechargeInterfaceId+"]");
			return ajax(Status.warn,"机构充值不支持此支付方式!");
		}
		
		RechargeConfig rechargeConfig = paymentService.getPaymentConfig(rechargeInterfaceId);
		if (rechargeConfig == null) {
			logPayment.debug("【机构充值结束】"+userLogin.getUsername()+" 支付方式不允许为空!"+"["+rechargeInterfaceId+"]");
			return ajax(Status.warn,"支付方式不允许为空!");
		}
		
		BigDecimal paymentFee = null;// 支付手续费
		BigDecimal amountPayable = null;// 应付金额【不包含手续费】
		//BasePaymentProduct paymentProduct = null;
		
		//【邮件/短信通知】-充值成功
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("sign", ConstantUtil.M_P_CZCG);
		
		try{
			BigDecimal rechargeAmount = userAccountRecharge.getMoney();//充值金额
			if (rechargeAmount == null) {
				addActionError("请输入充值金额!");
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 请输入充值金额!"+"["+rechargeAmount+"]");
				return ajax(Status.warn,"请输入充值金额!");
			}
			if (rechargeAmount.compareTo(new BigDecimal(0)) <= 0) {
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须大于0!"+"["+rechargeAmount+"]");
				return ajax(Status.warn,"充值金额必须大于0!");
			}
			if(rechargeAmount.compareTo(new BigDecimal(100000000))>=0){
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须小于100000000!"+"["+rechargeAmount+"]");
				return ajax(Status.warn,"充值金额必须小于100000000!");
			}
			if (rechargeAmount.scale() > ConstantUtil.PRICESCALE) {
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额小数位超出限制!"+"["+rechargeAmount+"]");
				return ajax(Status.warn,"充值金额小数位超出限制!");
			}
			amountPayable = rechargeAmount;//.add(paymentFee);
			paymentFee = rechargeConfig.getPaymentFee(rechargeAmount);
			
			//rechargeInterfaceId【1:POS充值;2:现金充值,3:直投充值】
			if( rechargeInterfaceId == 1 || rechargeInterfaceId == 2 || rechargeInterfaceId == 3){
				
				if(rechargeInterfaceId == 1){
					tempStr = "POS充值";
					data.put("rechargeType", "31");//POS充值
				}else if(rechargeInterfaceId == 2){
					tempStr = "现金充值";
					data.put("rechargeType", "32");//现金充值
				}else if(rechargeInterfaceId == 3){
					tempStr = "直投充值";
					data.put("rechargeType", "33");//直投充值
				}
				
			}
		}catch (Exception e) {
			
			addActionError(tempStr+"金额有误，请重新输入!");
			logPayment.debug("【"+tempStr+"结束】"+userLogin.getUsername()+" 充值金额有误，请重新输入!"+"");
			e.printStackTrace();
			return ajax(Status.warn,tempStr+"金额有误，请重新输入!");
		}

		String trade_no = SerialNumberUtil.buildPaymentSn()+"_"+userLogin.getId();
		userRecharge.setCreateDate(new Date());
		userRecharge.setModifyDate(new Date());
		userRecharge.setTradeNo(trade_no);
		userRecharge.setUserId(customUser.getId());
		userRecharge.setStatus(1);//2:充值中,1:充值完成
		userRecharge.setMoney(CommonUtil.setPriceScale(amountPayable, null) );
		userRecharge.setRechargeInterfaceId(rechargeInterfaceId);
		userRecharge.setReturned("");
		
		userRecharge.setType("0");//POS充值和现金充值 统一为"转账充值类型"
		userRecharge.setOffLineAccountId(userAccountRecharge.getOffLineAccountId());
		userRecharge.setRemark(userAccountRecharge.getRemark());
		if(StringUtils.isEmpty(userRecharge.getRemark())) {
			userRecharge.setRemark("机构["+userLogin.getUsername()+"]对["+userAccountRecharge.getUsername()+"]充值"+userRecharge.getMoney()+"元");
		}
	
		userRecharge.setIpOperator(getRequestRemoteAddr());
		userRecharge.setFee(paymentFee);
		userRecharge.setReward(new BigDecimal(0));//奖励为0
		
		userRecharge.setIpUser(getRequestRemoteAddr());
		userRecharge.setRechargeSource(2);//1:自己，2:机构
		userRecharge.setRechargeRequesterId(userLogin.getId());//user对应的ID
		
		userRecharge.setAgencyFlag(1);
		userRecharge.setAgencyId(userLogin.getAgencyid());
		userRecharge.setAgencyType(Integer.parseInt(userLogin.getAgencytype()));
		
		paymentService.addUserAccountRechargeAgence(userRecharge);
		
		
		log.info("跳转到充值页面");
		logPayment.debug("【充值跳转】"+userLogin.getUsername()+",单号:"+userRecharge.getTradeNo()+",金额:"+userRecharge.getMoney()+",充值方式:"+rechargeConfig.getName());

		return ajax(Status.success,"充值成功!");
	
	}
	
	
	/**
	 * 还款充值	【对接服务商对自己充值】
	 * @return
	 */
	@Action(value="/payment/ajaxRepaymentSubmit",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String repaymentSubmit(){
		log.info("平台服务商开始'还款充值	'");
		
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		if (!this.getMycode().equals(random)) {
			return ajax(Status.warn,"验证码输入错误!");
		}
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		
		User userLogin = getLoginUser();
		
		if (userLogin.getTypeId()!=1||(!"1".equals(userLogin.getAgencytype()) )) {
			return ajax(Status.warn,"还款充值只有特定服务商才能操作!");
		}
		
		if(!userService.isPassword(userLogin.getUsername(), user.getPayPassword(), "1")){
			return ajax(Status.warn,"安全密码输入错误!");
		}
		
		
		userAccountRecharge.setRechargeSource(3);//还款充值
		
		logPayment.debug("【还款充值开始】"+userLogin.getUsername());
		
		UserAccountRecharge userRecharge = new UserAccountRecharge();
		Integer rechargeInterfaceId =4;//默认还款充值
		
		
		RechargeConfig rechargeConfig = paymentService.getPaymentConfig(rechargeInterfaceId);
		if (rechargeConfig == null) {
			logPayment.debug("【还款充值结束】"+userLogin.getUsername()+" 支付方式不允许为空!"+"["+rechargeInterfaceId+"]");
			return ajax(Status.warn,"支付方式不允许为空!");
		}
		
		BigDecimal paymentFee = null;// 支付手续费
		BigDecimal amountPayable = null;// 应付金额【不包含手续费】
		try{
			BigDecimal rechargeAmount = userAccountRecharge.getMoney();//充值金额
			if (rechargeAmount == null) {
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 请输入充值金额!"+"["+rechargeAmount+"]");
				return ajax(Status.warn,"请输入充值金额!");
			}
			if (rechargeAmount.compareTo(new BigDecimal(0)) <= 0) {
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须大于0!"+"["+rechargeAmount+"]");
				return ajax(Status.warn,"充值金额必须大于0!");
			}
			if(rechargeAmount.compareTo(new BigDecimal(100000000))>=0){
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须小于100000000!"+"["+rechargeAmount+"]");
				return ajax(Status.warn,"充值金额必须小于100000000!");
			}
			if (rechargeAmount.scale() > ConstantUtil.PRICESCALE) {
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额小数位超出限制!"+"["+rechargeAmount+"]");
				return ajax(Status.warn,"充值金额小数位超出限制!");
			}
			amountPayable = rechargeAmount;
			paymentFee = rechargeConfig.getPaymentFee(rechargeAmount);
			
			
		}catch (Exception e) {
			logPayment.debug("【还款充值结束】用户名:"+userLogin.getUsername()+"， 充值金额有误，请重新输入!"+"");
			e.printStackTrace();
			return ajax(Status.warn,"还款充值金额有误，请重新输入!");
		}

		String trade_no = SerialNumberUtil.buildPaymentSn()+"_"+userLogin.getId();
		
		userRecharge.setCreateDate(new Date());
		userRecharge.setModifyDate(new Date());
		userRecharge.setTradeNo(trade_no);
		userRecharge.setUserId(userLogin.getId());
		userRecharge.setStatus(1);//2:充值中,1:充值完成
		userRecharge.setMoney(CommonUtil.setPriceScale(amountPayable, null) );
		userRecharge.setRechargeInterfaceId(rechargeInterfaceId);
		userRecharge.setReturned("");
		
		userRecharge.setType("0");//"还款充值" 统一为"转账充值类型"
		userRecharge.setOffLineAccountId(userAccountRecharge.getOffLineAccountId());
		userRecharge.setRemark(userAccountRecharge.getRemark());
		if(StringUtils.isEmpty(userRecharge.getRemark())) {
			userRecharge.setRemark("机构["+userLogin.getUsername()+"]对["+userAccountRecharge.getUsername()+"]充值"+userRecharge.getMoney()+"元");
		}
	
		userRecharge.setIpOperator(getRequestRemoteAddr());
		userRecharge.setFee(paymentFee);
		userRecharge.setReward(new BigDecimal(0));//奖励为0
		
		userRecharge.setIpUser(getRequestRemoteAddr());
		userRecharge.setRechargeSource(3);//1:自己，2:机构
		userRecharge.setRechargeRequesterId(userLogin.getId());//user对应的ID
		
		userRecharge.setAgencyFlag(1);
		userRecharge.setAgencyId(userLogin.getAgencyid());
		userRecharge.setAgencyType(Integer.parseInt(userLogin.getAgencytype()));
		
		paymentService.addUserAccountRechargeRepaymentByAgency(userRecharge);
		
		
		reloadUser();//刷新用户信息


		
		log.info("跳转到充值页面");
		logPayment.debug("【充值跳转】"+userLogin.getUsername()+",单号:"+userRecharge.getTradeNo()+",金额:"+userRecharge.getMoney()+",充值方式:"+rechargeConfig.getName());
		
		return ajax(Status.success,"还款充值成功");
		
	}
	
	
	
	
	
	/**
	 * 充值回调处理
	 * @return
	 */
	@Action(value="/payment/result",results={@Result(name="success", location="/content/payment/payment_result.ftl", type="freemarker")})
	public String result() {
		log.info("充值回调处理");
		userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(tradeNo);
		if (userAccountRecharge == null || userAccountRecharge.getStatus() != 1) {
			addActionError("参数错误!");
			return "error_no_back_ftl";
		}
		return SUCCESS;
	}
	
	@Action(value="/payment/poputContent",results={@Result(name="success", location="/content/payment/poputContent.ftl", type="freemarker")})
	public String poputContent(){
		return "success";
	}
	
	/**
	 * 获取支付方式列表（用户）
	 * @return
	 */
	public List<RechargeConfig> getRechargeConfigList(){
		List<RechargeConfig> thisList = getRechargeConfigListAll();
		
		if (thisList!=null && thisList.size()>0) {
			for(int i =0;i<thisList.size();i++) {
				if ("direct_recharge".equals(thisList.get(i).getPaymentProductId())) {//过滤直投
					thisList.remove(i);
					break;
				}
			}
		}
		
		return thisList;
	}
	
	/**
	 * 获取支付方式列表（机构）【获取：POS充值、现金充值、直投充值】
	 * @return
	 */
	public List<RechargeConfig> getRechargeConfigListAgency(){
		List<RechargeConfig> thisList = getRechargeConfigListAll();
		
		if (thisList!=null && thisList.size()>0) {
			
			int i =0;
			while(i<thisList.size()) {
				if (!"pos".equals(thisList.get(i).getPaymentProductId())&&
						!"cash_recharge".equals(thisList.get(i).getPaymentProductId())&&
						!"direct_recharge".equals(thisList.get(i).getPaymentProductId())) {
					thisList.remove(i);
				} else {
					i++;
				}
			}
			
//			for(int i =0;i<thisList.size();i++) {
//				// 只使用 POS充值，现金充值，直投充值
//				if (!"pos".equals(thisList.get(i).getPaymentProductId())&&
//						!"cash_recharge".equals(thisList.get(i).getPaymentProductId())&&
//						!"direct_recharge".equals(thisList.get(i).getPaymentProductId())) {
//					thisList.remove(i);
//				}
//			}
		}
		
		return thisList;
	}
	
	/**
	 * 获取支付方式列表 原始记录source record
	 * @return
	 */
	public List<RechargeConfig> getRechargeConfigListAll(){
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

	public String getTempStr() {
		return tempStr;
	}

	public void setTempStr(String tempStr) {
		this.tempStr = tempStr;
	}


//	public String getMycode() {
//		return mycode.toUpperCase();
//	}
//
//
//	public void setMycode(String mycode) {
//		this.mycode = mycode;
//	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
