package com.qmd.action.payment;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.baofoo.util.JXMConvertUtil;
import com.qmd.baofoo.util.SecurityUtil;
import com.qmd.baofoo.util.rsa.RsaCodingUtil;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.mode.util.Listing;
import com.qmd.payment.BasePaymentProduct;
import com.qmd.service.payment.PayService;
import com.qmd.service.payment.PaymentService;
import com.qmd.service.user.UserAccountService;
import com.qmd.service.user.UserService;
import com.qmd.service.util.ListingService;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;
import com.qmd.util.SerialNumberUtil;
import com.qmd.util.llpay.LLPayUtil;
import com.qmd.util.llpay.PayDataBean;
import com.qmd.util.rongbaoConfig.ReapalConfig;
import com.qmd.util.rongbaoUtils.AgeLimitUtils;
import com.qmd.util.rongbaoUtils.Decipher;
import com.qmd.util.rongbaoUtils.Md5Utils;
import com.qmd.util.rongbaoUtils.RongbaoUtil;

@InterceptorRefs({
	@InterceptorRef(value = "userVerifyInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
@Service("paymentAction")
public class PaymentAction extends BaseAction {
	
	private static final long serialVersionUID = 3812790719313252342L;
	Logger log = Logger.getLogger(PaymentAction.class);
	
	private Logger logPayment = Logger.getLogger("userPaymentLog");
	private Logger rongbaoPaymentLog = Logger.getLogger("rongbaoPaymentLog");
	
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
	
	private Map jsonMap;
	private String merchantId;
	
	private String money;//充值金额 
	
	@Resource
	UserService userService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	PaymentService paymentService;
	@Resource
	private PayService payService;
	@Resource
	private ListingService listingService;
	
		
	
	@Action(value="/payment/ajaxPaymentFee",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String ajaxPaymentFee(){
		try{
			Integer rechargeInterfaceId =null;
			if(!this.isNumeric(payment)){//直联支付【字母(ICBC)】
	//			userRecharge.setBankName(payment);
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
			if(rechargeInterfaceId == 0){ //线下充值 有奖励
				 actual = rechargeAmount.add(paymentFee);//到账金额
			}else{
				if(rechargeConfig.getPaymentFeeType().compareTo(0)==0||rechargeConfig.getPaymentFeeType().compareTo(1)==0){
					actual = rechargeAmount.subtract(paymentFee);//到账金额
				}else{
					actual = rechargeAmount.add(paymentFee);//到账金额
				}
			}
			if(actual.compareTo(new BigDecimal(100)) < 0){
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
	 * 充值提交
	 * @return
	 */
	@Action(value="/payment/submit",results={@Result(name="onLine", location="/content/payment/payment_submit.ftl", type="freemarker"),
											 @Result(name="offLine", location="/content/payment/payment_off_line.ftl", type="freemarker")})
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String submit(){
		log.info("开始充值");
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		msgUrl="/payment/rechargeTo.do";
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		if (mycode == null || !mycode.equalsIgnoreCase(random)) {
			msg = "验证码输入错误!";
			return "error_ftl";
		}
		User userLogin = getLoginUser();
//		if(!userService.isPassword(userLogin.getUsername(), user.getPayPassword(), "1")){
//			addActionError("交易密码输入错误!");
//			return "error_ftl";
//		}
		
		logPayment.debug("【充值开始】"+userLogin.getUsername()+",["+payment+"]");
		
		UserAccountRecharge userRecharge = new UserAccountRecharge();
		Integer rechargeInterfaceId =0;//默认线下充值
		if(!this.isNumeric(payment)){//直联支付【字母(ICBC)】
			userRecharge.setBankName(payment);
		}else{//接口支付
			rechargeInterfaceId = Integer.parseInt(payment);
		}
		
		RechargeConfig rechargeConfig = paymentService.getPaymentConfig(rechargeInterfaceId);
		if (rechargeConfig == null) {
			msg = "请选择支付方式!";
			logPayment.debug("【充值结束】"+userLogin.getUsername()+" 支付方式不允许为空!"+"["+rechargeInterfaceId+"]");
			return "error_ftl";
		}
		
		BigDecimal paymentFee = null;// 支付手续费
		BigDecimal amountPayable = null;// 应付金额【不包含手续费】
		BasePaymentProduct paymentProduct = null;
		try{
			BigDecimal rechargeAmount = userAccountRecharge.getMoney();//充值金额
			if (rechargeAmount == null) {
				msg = "请输入充值金额!";
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 请输入充值金额!"+"["+rechargeAmount+"]");
				return "error_ftl";
			}
			if (rechargeAmount.compareTo(new BigDecimal(1)) < 0) {
				msg = "充值金额必须大于1!";
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须大于1!"+"["+rechargeAmount+"]");
				return "error_ftl";
			}
			if(rechargeAmount.compareTo(new BigDecimal(100000))>=0){
				msg = "充值金额必须小于100000!";
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须小于100000!"+"["+rechargeAmount+"]");
				return "error_ftl";
			}
			if (rechargeAmount.scale() > ConstantUtil.PRICESCALE) {
				msg = "充值金额小数位超出限制!";
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额小数位超出限制!"+"["+rechargeAmount+"]");
				return "error_ftl";
			}
			amountPayable = rechargeAmount;//.add(paymentFee);
			paymentFee = rechargeConfig.getPaymentFee(rechargeAmount);
			
			if(rechargeInterfaceId != 0){//线上充值
				paymentProduct = paymentService.getPaymentProduct(rechargeInterfaceId);
				paymentUrl = paymentProduct.getPaymentUrl();
			}else{
				String miniMoney = getServletContext().getAttribute("qmd.setting.miniMoney").toString();
				if(rechargeAmount.compareTo(new BigDecimal(miniMoney)) < 0 ){
					msg = "线下充值金额有误，请重新输入!";
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 线下充值金额小于:"+miniMoney);
					return "error_ftl";
				}
			}
		}catch (Exception e) {
			msg = "充值金额有误，请重新输入!";
			logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额有误，请重新输入!"+"");
			return "error_ftl";
		}
		
		
		userRecharge.setCreateDate(new Date());
		userRecharge.setModifyDate(new Date());
		userRecharge.setTradeNo(SerialNumberUtil.buildPaymentSn());
		userRecharge.setUserId(getLoginUser().getId());
		userRecharge.setStatus(2);//充值中
		userRecharge.setMoney(CommonUtil.setPriceScale(amountPayable, null) );
		userRecharge.setRechargeInterfaceId(rechargeInterfaceId);
		userRecharge.setReturned("");
		if(rechargeInterfaceId != 0){//线上充值
			userRecharge.setType("1");
			userRecharge.setRemark("充值"+CommonUtil.setPriceScale(amountPayable, null)+"元");//getLoginUser().getUsername()+rechargeConfig.getName()+
			userRecharge.setFee(paymentFee);
			userRecharge.setReward(new BigDecimal(0));//奖励为0
		}else{
			userRecharge.setType("0");
			userRecharge.setOffLineAccountId(userAccountRecharge.getOffLineAccountId());
			userRecharge.setRemark(userAccountRecharge.getRemark());
			userRecharge.setFee(new BigDecimal(0));//手续费为0
			userRecharge.setReward(paymentFee);
		}
		userRecharge.setIpUser(getRequestRemoteAddr());
		paymentService.addUserAccountRecharge(userRecharge);
		
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", userLogin.getId());
		 user = this.userService.getUser(map);
		log.info("跳转到充值页面");
		logPayment.debug("【充值跳转】"+userLogin.getUsername()+",单号:"+userRecharge.getTradeNo()+",金额:"+userRecharge.getMoney()+",充值方式:"+rechargeConfig.getName());
		if(rechargeInterfaceId != 0){//线上充值
			userRecharge.setRealName(user.getRealName());
			userRecharge.setCardId(user.getCardId());
			userRecharge.setRegister( CommonUtil.getDate2String(user.getCreateDate(),"yyyyMMddHHmmss"));
			
			 AccountBank accountBank =userService.queryAccountBank(getLoginUser().getId()).get(0);
			userRecharge.setBankNo(accountBank.getAccount().replaceAll("\\s*", ""));
			userRecharge.setBackUrl(ConstantUtil.WEB_SITE+"/userCenter/toBankInput.do");
			userRecharge.setMobile(user.getPhone());
			userRecharge.setPayCode(accountBank.getBankId());
			parameterMap = paymentProduct.getParameterMap(rechargeConfig, userRecharge, getRequest());
			return "onLine";
		}else{
			return "offLine";
		}
	}
	
	
	
	/**
	 * 充值回调处理-前台
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@Action(value="/payment/payreturn",
			results={@Result(name="success", location="/content/payment/payment_payreturn.ftl", type="freemarker"),
					 @Result(name="result", location="/content/payment/payment_result.ftl", type="freemarker"),
					 @Result(name="sign", location="/content/payment/payment_sign.ftl", type="freemarker")})
	public String payreturn() throws UnsupportedEncodingException{
		log.info("充值回调-前台");
		logPayment.debug("【充值回调开始-前台】");
		StringBuffer strBuffer = new StringBuffer();
		for(Object s:getRequest().getParameterMap().keySet()){
			strBuffer.append(s.toString()+"=" + getRequest().getParameter(s.toString()) + "&");
		}
		log.info("参数="+strBuffer.toString());
		logPayment.debug("     参数:"+strBuffer.toString()+"");
		userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(tradeNo);
		if(userAccountRecharge == null){
			addActionError("充值记录不存在!");
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]充值记录不存在!");
			return "error_ftl";
		}
		
		BasePaymentProduct paymentProduct = paymentService.getPaymentProduct(userAccountRecharge.getRechargeInterfaceId());
		if(paymentProduct == null){
			addActionError("支付产品不存在!");
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]支付产品不存在!");
			return "error_ftl";
		}
		paymentProduct.getPaymentSn(getRequest());
		String data_content=null;
		
		Boolean isSuccess=false;
		BigDecimal totalAmount = new BigDecimal(0) ;
		if(userAccountRecharge.getRechargeInterfaceId()==50){
			String payAmount =getRequest().getParameter("money_order");
			if (StringUtils.isEmpty(payAmount)) {
				addActionError("返回金额不正确!");
				return "error_ftl";
			}
			System.out.println(payAmount);
			totalAmount = new BigDecimal(payAmount).setScale(2);
			String payResult = getRequest().getParameter("result_pay");
			if (StringUtils.equals(payResult, "SUCCESS")) {
				isSuccess=true;
			} else {
				isSuccess=false;
			} 
			
			if(!verifyReturSign(paymentService.getPaymentConfig(userAccountRecharge.getRechargeInterfaceId()), getRequest())){
				addActionError("支付签名错误!");
				logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]支付签名错误!");
				return "error_ftl";
			}
		}else if(userAccountRecharge.getRechargeInterfaceId()==40){
			try {
				data_content = getRequest().getParameter("data_content");//回调参数
				if(data_content.isEmpty()){//判断参数是否为空
					
					msg ="返回数据为空";
					return "error_ftl";
				}
				String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
				String  cerpath =path+ConstantUtil.cerPath;//宝付公钥
				String data_type=ConstantUtil.dataType; //加密报文的数据类型（xml/json）
				
				File cerfile=new File(cerpath);
				if(!cerfile.exists()){//判断宝付公钥是否为空
					System.out.println("宝付公钥文件不存在！");
					
					msg ="公钥文件不存在！";
					return "error_ftl";
				}
				
				 data_content = RsaCodingUtil.decryptByPubCerFile(data_content,cerpath);
				 if(data_content.isEmpty()){//判断解密是否正确。如果为空则宝付公钥不正确
					 msg ="检查解密公钥是否正确！";
					 return "error_ftl";
				 }
				 data_content = SecurityUtil.Base64Decode(data_content);		 
				 
				if(data_type.equals("xml")){
					data_content = JXMConvertUtil.XmlConvertJson(data_content);		    
					
				}
				
				Map<String,Object> ArrayData = JXMConvertUtil.JsonConvertHashMap((Object)data_content);//将JSON转化为Map对象。
				
				if(!ArrayData.containsKey("resp_code")){
					 msg ="订单异常！";
					 return "error_ftl";
				}else{
					String resp_code = ArrayData.get("resp_code").toString();
					 totalAmount = BigDecimal.valueOf(Double.valueOf(ArrayData.get("succ_amt").toString()));
					String tradeNo = ArrayData.get("trans_id").toString();
					BigDecimal  succ_amt = totalAmount;
					String bargainorIdName = ArrayData.get("member_id").toString();
					//这里根据ArrayData 对象里的值去写本地服务器端数据
					//商户自行写入自已的数据。。。。。。。。
					
					if(resp_code.equals("0000")&&paymentProduct.getBargainorIdName().equals(bargainorIdName)&&userAccountRecharge.getTradeNo().equals(tradeNo)){
						User u = getLoginUser();
						u.setRealStatus(1);
						userService.update(u);
						isSuccess=true;
					}else{
						isSuccess=false;
					}
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else{
			
		
			 totalAmount = paymentProduct.getPaymentAmount(getRequest());
			
			 isSuccess = paymentProduct.isPaySuccess(getRequest());
			if(!paymentProduct.verifySign(paymentService.getPaymentConfig(userAccountRecharge.getRechargeInterfaceId()), getRequest())){
				addActionError("支付签名错误!");
				logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]支付签名错误!");
				return "error_ftl";
			}
		}
		payReturnMessage = paymentProduct.getPayReturnMessage(tradeNo);
		if(!isSuccess){
			addActionError(paymentProduct.returnResult(getRequest()));
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]"+paymentProduct.returnResult(getRequest())+"");
			return "error_ftl";
		}
		if(userAccountRecharge.getStatus().equals(1)){//充值成功
			
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]充值已经成功===");
			if("银行卡签约".equals(userAccountRecharge.getRemark())){
				return "sign";
			}else{
				return "result";
			}
		}
		if(!userAccountRecharge.getStatus().equals(2)){//充值中
			addActionError("交易状态错误!");
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]交易状态错误 《不是充值中的状态》");
			return "error_ftl";
		}
		if(totalAmount.compareTo(userAccountRecharge.getMoney()) != 0){
			addActionError("交易金额错误!");
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]交易金额错误!");
			return "error_ftl";
		}
		
		String feeAmt = userAccountRecharge.getFee().toString();
		log.info("交易金额:"+totalAmount+";商户提取佣金金额:"+feeAmt+"-前台");
		
		RechargeConfig rechargeConfig = paymentService.getPaymentConfig(userAccountRecharge.getRechargeInterfaceId());
		
		Map<String,Object> detailMap = new HashMap<String,Object>();
		detailMap.put("feeType",rechargeConfig.getPaymentFeeType());
		//detailMap.put("userId", getLoginUser().getId());
		detailMap.put("tranAmt",totalAmount);
		detailMap.put("feeAmt", feeAmt);
		detailMap.put("operatorIp", getRequestRemoteAddr());
		
		
		Map<String,Object> map= new HashMap<String ,Object>();
		map.put("modifyDate", new Date());
		map.put("status", 1);
		if(userAccountRecharge.getRechargeInterfaceId()==40){
			map.put("returned", data_content);
		}else if(userAccountRecharge.getRechargeInterfaceId()==50){
			map.put("returned", paymentProduct.returnResult(getRequest()));
		}
		map.put("tradeNo", tradeNo);
		map.put("rechargeId", userAccountRecharge.getId());
		//paymentService.rechargeSuccess(map,detailMap);
		int ret = payService.rechargeSuccess(map,detailMap);
		System.out.println("----------------------------------------"+ret+"--------------------------------------------------");
		if (ret==1) {
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]充值已经成功===");
			return "error_ftl";
		} else if (ret ==2) {
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]交易状态错误 《不是充值中的状态》");
			payNotifyMessage="单号"+tradeNo+"交易状态错误 ！";
			return "error_ftl";
		} else if(ret ==3){
			return "sign";//签约成功页面
		}
		log.info("充值回调成功-前台");
		logPayment.debug("【充值回调结束-前台】交易成功 ["+tradeNo+"]金额["+totalAmount+"]费用["+feeAmt+"]");
		return SUCCESS;
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
			return "error_ftl";
		}
		return SUCCESS;
	}
	
	
	/**
	 * 融宝快 卡密鉴权 异步通知处理
	 * zdl
	 * <p>
	 * 卡密鉴权只是针对招商首次绑卡 签约成功后需再完成卡密认证，才发送验证码，进而才可完成之后的支付 
	 * <p>
	 * 至于卡密鉴权结果，系统并不需记录或修改任何信息，所以，此通知直接返回success
	 * @return
	 * @throws Exception 
	 */
	@Action(value="/payment/rbkmnotify",results={@Result(name="paynotify", location="/content/payment/payment_rb_paynotify.ftl", type="freemarker")})
	public String rbkmnotify() throws Exception{
		log.info("卡密鉴权异步通知-后台");
		rongbaoPaymentLog.debug("【卡密鉴权异步通知-开始】");
		payNotifyMessage = "success";
		
		HashMap<String, String> param = new HashMap<String, String>();
		for(Object s:getRequest().getParameterMap().keySet()){
			param.put(s.toString(), getRequest().getParameter(s.toString()));
		}
		String paramStr = JSONObject.toJSONString(param);
		rongbaoPaymentLog.debug("【卡密鉴权异步通知-获取通知参数】"+paramStr);
		
		if(StringUtils.isBlank(paramStr)){
			payNotifyMessage ="【卡密鉴权异步通知:参数为空】"+paramStr;
			rongbaoPaymentLog.debug(payNotifyMessage);
			return "paynotify";
		}
		//解析密文数据
		String decryData = null;
		try {
			decryData = Decipher.decryptData(paramStr);
			rongbaoPaymentLog.debug("【卡密鉴权异步通知，获取通知参数解密后】"+decryData);
		} catch (Exception e) {
			payNotifyMessage = "【卡密鉴权异步通知，参数解密异常】解密前："+paramStr+"，解密后："+decryData ;
			rongbaoPaymentLog.debug(payNotifyMessage);
			return "paynotify";
		}
		rongbaoPaymentLog.debug("【卡密鉴权异步通知-结束】");
		return "paynotify";
	}
	
	
	
	/**
	 * 融宝快 捷充 值通知处理
	 * zdl
	 * @return
	 * @throws Exception 
	 */
	@Action(value="/payment/rbknotify",results={@Result(name="paynotify", location="/content/payment/payment_rb_paynotify.ftl", type="freemarker")})
	public String rbknotify() throws Exception{
		log.info("充值回调-后台");
		rongbaoPaymentLog.debug("【充值回调开始】");
		payNotifyMessage = "fail";//返回融宝的信息，默认是失败
		
		HashMap<String, String> param = new HashMap<String, String>();
		for(Object s:getRequest().getParameterMap().keySet()){
			param.put(s.toString(), getRequest().getParameter(s.toString()));
		}
		String paramStr = JSONObject.toJSONString(param);
		rongbaoPaymentLog.debug("【获取回调参数】"+paramStr);
		
		//解析密文数据
		String decryData = null;
		try {
			decryData = Decipher.decryptData(paramStr);
			rongbaoPaymentLog.debug("【获取回调参数解密后】"+decryData);
		} catch (Exception e) {
			payNotifyMessage = "【参数解密异常】解密前："+paramStr+"，解密后："+decryData ;
			rongbaoPaymentLog.debug(payNotifyMessage);
			return "paynotify";
		}
		if(StringUtils.isBlank(decryData)){
			payNotifyMessage = "【参数解密后为空】解密前："+paramStr+"，解密后："+decryData ;
			rongbaoPaymentLog.debug(payNotifyMessage);
			return "paynotify";
		}
		
		//获取融宝支付的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		JSONObject jsonObject = null;
		try{	
			jsonObject= JSON.parseObject(decryData);
			rongbaoPaymentLog.debug("【获取回调参数解密后，解析为JSONObject】"+jsonObject.toJSONString());
		}catch(Exception e){
			payNotifyMessage  = "【获取回调参数解密后，解析为JSONObject异常】原数据："+decryData ;
			rongbaoPaymentLog.debug(payNotifyMessage);
			return "paynotify";
		}
		if(jsonObject==null){
			payNotifyMessage = "【获取回调参数解密后，解析为JSONObject为空】原数据："+decryData ;
			rongbaoPaymentLog.debug(payNotifyMessage);
			return "paynotify";
		}
		String merchant_id = param.get("merchant_id");
		String trade_no = jsonObject.getString("trade_no");
		String order_no = jsonObject.getString("order_no");
		String total_fee = jsonObject.getString("total_fee");
		String status = jsonObject.getString("status");
		String result_code = jsonObject.getString("result_code");
		String result_msg = jsonObject.getString("result_msg");
		String sign = jsonObject.getString("sign");
		String notify_id = jsonObject.getString("notify_id");
		
		Map<String, String> map2 = new HashMap<String, String>();
//		map2.put("merchant_id", merchant_id);
		map2.put("trade_no", trade_no);
		map2.put("order_no", order_no);
		map2.put("total_fee", total_fee);
		map2.put("status", status);
		map2.put("result_code", result_code);
		map2.put("result_msg", result_msg);
		map2.put("notify_id", notify_id);
		//将返回的参数进行验签
		String mysign = Md5Utils.BuildMysign(map2, ReapalConfig.key);
		rongbaoPaymentLog.debug("【解密后的参数验签结果】订单号："+order_no+"，需要签名的参数："+JSONObject.toJSONString(map2)+"，密钥："+ReapalConfig.key+"，结果："+mysign);
		
		//校验：判读该返回结果是否由融宝发出
		if(mysign == null || !StringUtils.equals(mysign, sign)){
			payNotifyMessage = "【解密后的参数验签结果，校验失败】订单号："+order_no+"，本地验签结果："+mysign+"，接受的验签："+sign ;
			rongbaoPaymentLog.debug(payNotifyMessage);	
			return "paynotify";
		}
		//校验：订单号是否为空，商户号一致
		if(StringUtils.isBlank(order_no) || merchant_id==null || !StringUtils.equals(merchant_id, ReapalConfig.merchant_id)){
			payNotifyMessage = "【订单号是否为空，或商户号一致】订单号："+order_no+"，商户号："+merchant_id;
			rongbaoPaymentLog.debug(payNotifyMessage);	
			return "paynotify";
		}
		//校验：订单号是否存在
		userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(order_no);
		rongbaoPaymentLog.debug("【系统订单信息】"+JSONObject.toJSONString(userAccountRecharge));
		if(userAccountRecharge == null){
			payNotifyMessage ="【此订单号不存在】订单号："+order_no;
			rongbaoPaymentLog.debug(payNotifyMessage);	
			return "paynotify";
		}
		//校验：订单的支付方式为：融宝快捷 即80
		if(!Integer.valueOf(80).equals(userAccountRecharge.getRechargeInterfaceId()) && !Integer.valueOf(81).equals(userAccountRecharge.getRechargeInterfaceId())){
			payNotifyMessage = "【此订单不是融宝快捷支付】订单号："+order_no+"，支付方式id："+userAccountRecharge.getRechargeInterfaceId();
			rongbaoPaymentLog.debug(payNotifyMessage);	
			return "paynotify";
		}
		//校验：交易金额是否一致
		if(StringUtils.isBlank(total_fee) || new BigDecimal(total_fee).compareTo(userAccountRecharge.getMoney().movePointRight(2)) != 0){
			payNotifyMessage = "【订单金额不一致】订单号："+order_no+"，订单金额："+total_fee+"(分)，返回金额："+userAccountRecharge.getMoney().movePointRight(2).toString()+"(分)";
			rongbaoPaymentLog.debug(payNotifyMessage);	
			return "paynotify";
		}
		//校验：重复回调（单子已经支付成功）
		if(userAccountRecharge.getStatus().equals(1)){//充值成功
			rongbaoPaymentLog.debug("【此订单已支付完成】订单号："+order_no);	
			payNotifyMessage = "success";
			return "paynotify";
		}
		//校验：非充值中状态，不可修改订单状态
		if(!userAccountRecharge.getStatus().equals(2)){//充值中
			rongbaoPaymentLog.debug("【此订单状态已不是充值中】订单号："+order_no);	
			payNotifyMessage = "success";
			return "paynotify";
		}
		String totalMoney =  new BigDecimal(total_fee).movePointRight(-2).toString();
		String feeAmt = userAccountRecharge.getFee().toString();
		rongbaoPaymentLog.debug("【订单资金情况】订单号："+order_no+"，交易金额:"+totalMoney+"（元），商户提取佣金金额:"+feeAmt+"（元）");	
		
		if(StringUtils.equals(status, "TRADE_FINISHED")){ //支付成功
			rongbaoPaymentLog.debug("【回调返回订单支付成功】订单号："+order_no+"，状态:"+status);	
			
			rongbaoPaymentLog.debug("【执行订单业务-开始】订单号："+order_no);	
			Map<String,Object> detailMap = new HashMap<String,Object>();
			detailMap.put("feeType","0");//服务费类型
			detailMap.put("tranAmt",totalMoney);
			detailMap.put("feeAmt", feeAmt);
			detailMap.put("operatorIp", getRequestRemoteAddr());
			
			Map<String,Object> map= new HashMap<String ,Object>();
			map.put("modifyDate", new Date());
			map.put("status", 1);
			map.put("returned", decryData);
			map.put("tradeNo", order_no);
			map.put("rechargeId", userAccountRecharge.getId());
			
			int ret = payService.rechargeSuccess(map,detailMap);
			
			if (ret==1) {
				rongbaoPaymentLog.debug("【充值成功】订单号："+order_no);
			} else if (ret ==2) {
				rongbaoPaymentLog.debug("【充值失败】订单号："+order_no+",错误原因： 订单不是充值中的状态");
			} else if(ret ==3){
				rongbaoPaymentLog.debug("【签约成功】订单号："+order_no);
			}
			rongbaoPaymentLog.debug("【执行订单业务-结束】订单号："+order_no);
		}
		payNotifyMessage = "success";
		rongbaoPaymentLog.debug("【充值回调结束】");
		return "paynotify";
	}
	
	
	
	/**
	 * 充值通知处理
	 * zdl:修改 返回都为OK
	 * @return
	 * @throws Exception 
	 */
	@Action(value="/payment/paynotify",results={@Result(name="paynotify", location="/content/payment/payment_paynotify.ftl", type="freemarker")
												,@Result(name="sign", location="/content/payment/payment_sign.ftl", type="freemarker")})
	public String paynotify() throws Exception{
		log.info("充值回调-后台");
		
		logPayment.debug("【充值回调开始-前台】");
		StringBuffer strBuffer = new StringBuffer();
		for(Object s:getRequest().getParameterMap().keySet()){
			strBuffer.append(s.toString()+"=" + getRequest().getParameter(s.toString()) + "&");
		}
		log.info("参数="+strBuffer.toString());
		logPayment.debug("     参数:"+strBuffer.toString()+"");
		userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(tradeNo);
		if(userAccountRecharge == null){
			addActionError("充值记录不存在!");
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]充值记录不存在!");
//			return "error_ftl";
			return "paynotify";
		}
		
		BasePaymentProduct paymentProduct = paymentService.getPaymentProduct(userAccountRecharge.getRechargeInterfaceId());
		if(paymentProduct == null){
			addActionError("支付产品不存在!");
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]支付产品不存在!");
//			return "error_ftl";
			return "paynotify";
		}
		paymentProduct.getPaymentSn(getRequest());
		String data_content = null;
		
		Boolean isSuccess=false;
		BigDecimal totalAmount = new BigDecimal(0) ;
		if(userAccountRecharge.getRechargeInterfaceId()==50){
			String payAmount =getRequest().getParameter("money_order");
			if (StringUtils.isEmpty(payAmount)) {
				addActionError("返回金额不正确!");
//				return "error_ftl";
				return "paynotify";
			}
			System.out.println(payAmount);
			totalAmount = new BigDecimal(payAmount).setScale(2);
			String payResult = getRequest().getParameter("result_pay");
			if (StringUtils.equals(payResult, "SUCCESS")) {
				isSuccess=true;
			} else {
				isSuccess=false;
			} 
			
			if(!verifyReturSign(paymentService.getPaymentConfig(userAccountRecharge.getRechargeInterfaceId()), getRequest())){
				addActionError("支付签名错误!");
				logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]支付签名错误!");
//				return "error_ftl";
				return "paynotify";
			}
		}else if(userAccountRecharge.getRechargeInterfaceId()==40){
			try {
				 data_content = getRequest().getParameter("data_content");//回调参数
				if(data_content.isEmpty()){//判断参数是否为空
					
					msg ="返回数据为空";
//					return "error_ftl";
					return "paynotify";
				}
				String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
				String  cerpath =path+ConstantUtil.cerPath;//宝付公钥
				String data_type=ConstantUtil.dataType; //加密报文的数据类型（xml/json）
				
				File cerfile=new File(cerpath);
				if(!cerfile.exists()){//判断宝付公钥是否为空
					System.out.println("宝付公钥文件不存在！");
					
					msg ="公钥文件不存在！";
//					return "error_ftl";
					return "paynotify";
				}
				
				 data_content = RsaCodingUtil.decryptByPubCerFile(data_content,cerpath);
				 if(data_content.isEmpty()){//判断解密是否正确。如果为空则宝付公钥不正确
					 msg ="检查解密公钥是否正确！";
//					 return "error_ftl";
					 return "paynotify";
				 }
				 data_content = SecurityUtil.Base64Decode(data_content);		 
				 
				if(data_type.equals("xml")){
					data_content = JXMConvertUtil.XmlConvertJson(data_content);		    
					
				}
				
				Map<String,Object> ArrayData = JXMConvertUtil.JsonConvertHashMap((Object)data_content);//将JSON转化为Map对象。
				
				if(!ArrayData.containsKey("resp_code")){
					 msg ="订单异常！";
//					 return "error_ftl";
					 return "paynotify";
				}else{
					String resp_code = ArrayData.get("resp_code").toString();
					 totalAmount = BigDecimal.valueOf(Double.valueOf(ArrayData.get("succ_amt").toString()));
					String tradeNo = ArrayData.get("trans_id").toString();
					BigDecimal  succ_amt = totalAmount;
					String bargainorIdName = ArrayData.get("member_id").toString();
					//这里根据ArrayData 对象里的值去写本地服务器端数据
					//商户自行写入自已的数据。。。。。。。。
					
					if(resp_code.equals("0000")&&paymentProduct.getBargainorIdName().equals(bargainorIdName)&&userAccountRecharge.getTradeNo().equals(tradeNo)){
						isSuccess=true;
					}else{
						isSuccess=false;
					}
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else{
			
		
			 totalAmount = paymentProduct.getPaymentAmount(getRequest());
			
			 isSuccess = paymentProduct.isPaySuccess(getRequest());
			if(!paymentProduct.verifySign(paymentService.getPaymentConfig(userAccountRecharge.getRechargeInterfaceId()), getRequest())){
				addActionError("支付签名错误!");
				logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]支付签名错误!");
//				return "error_ftl";
				return "paynotify";
			}
		}
		payReturnMessage = paymentProduct.getPayReturnMessage(tradeNo);
		if(!isSuccess){
			addActionError(paymentProduct.returnResult(getRequest()));
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]"+paymentProduct.returnResult(getRequest())+"");
//			return "error_ftl";
			return "paynotify";
		}
		if(userAccountRecharge.getStatus().equals(1)){//充值成功
			
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]充值已经成功===");
			if("银行卡签约".equals(userAccountRecharge.getRemark())){
				return "sign";
			}else{
				return "paynotify";
			}
		}
		if(!userAccountRecharge.getStatus().equals(2)){//充值中
			addActionError("交易状态错误!");
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]交易状态错误 《不是充值中的状态》");
//			return "error_ftl";
			return "paynotify";
		}
		if(totalAmount.compareTo(userAccountRecharge.getMoney()) != 0){
			addActionError("交易金额错误!");
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]交易金额错误!");
//			return "error_ftl";
			return "paynotify";
		}
		
		String feeAmt = userAccountRecharge.getFee().toString();
		log.info("交易金额:"+totalAmount+";商户提取佣金金额:"+feeAmt+"-前台");
		
		RechargeConfig rechargeConfig = paymentService.getPaymentConfig(userAccountRecharge.getRechargeInterfaceId());
		
		Map<String,Object> detailMap = new HashMap<String,Object>();
		detailMap.put("feeType",rechargeConfig.getPaymentFeeType());
		//detailMap.put("userId", getLoginUser().getId());
		detailMap.put("tranAmt",totalAmount);
		detailMap.put("feeAmt", feeAmt);
		detailMap.put("operatorIp", getRequestRemoteAddr());
		
		
		Map<String,Object> map= new HashMap<String ,Object>();
		map.put("modifyDate", new Date());
		map.put("status", 1);
		if(userAccountRecharge.getRechargeInterfaceId()==40){
			map.put("returned", data_content);
		}else if(userAccountRecharge.getRechargeInterfaceId()==50){
			map.put("returned", paymentProduct.returnResult(getRequest()));
		}
		map.put("tradeNo", tradeNo);
		map.put("rechargeId", userAccountRecharge.getId());
		//paymentService.rechargeSuccess(map,detailMap);
		int ret = payService.rechargeSuccess(map,detailMap);
		System.out.println("----------------------------------------"+ret+"--------------------------------------------------");
		if (ret==1) {
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]充值已经成功===");
			payNotifyMessage = paymentProduct.getPayNotifyMessage();
			return "paynotify";
		} else if (ret ==2) {
			logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]交易状态错误 《不是充值中的状态》");
			payNotifyMessage="单号"+tradeNo+"交易状态错误 ！";
//			return "error_ftl";
			return "paynotify";
		} else if(ret ==3){
			payNotifyMessage = paymentProduct.getPayNotifyMessage();
			return "paynotify";//签约成功页面
		}
		log.info("充值回调成功-前台");
		logPayment.debug("【充值回调结束-前台】交易成功 ["+tradeNo+"]金额["+totalAmount+"]费用["+feeAmt+"]");
		return "paynotify";
		
//		logPayment.debug("【充值回调开始-后台】");
//		try{
//		StringBuffer strBuffer = new StringBuffer();
//		for(Object s:getRequest().getParameterMap().keySet()){
//			strBuffer.append(s.toString()+"=" + getRequest().getParameter(s.toString()) + "&");
//		}
//		log.info("参数="+strBuffer.toString());
//		
//		userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(tradeNo);
//		if(userAccountRecharge == null){
//			logPayment.debug("【充值回调结束-后台】NG["+tradeNo+"]充值记录不存在!");
//			payNotifyMessage="单号"+tradeNo+"不存在！";
//			return "paynotify";
//		}
//		
//		BasePaymentProduct paymentProduct = paymentService.getPaymentProduct(userAccountRecharge.getRechargeInterfaceId());
//		if(paymentProduct == null){
//			logPayment.debug("【充值回调结束-后台】NG["+tradeNo+"]支付产品不存在!");
//			payNotifyMessage="单号"+tradeNo+"支付产品不存在！";
//			return "paynotify";
//		}
//	
//		paymentProduct.getPaymentSn(getRequest());
//		BigDecimal totalAmount = paymentProduct.getPaymentAmount(getRequest());
//		boolean isSuccess = paymentProduct.isPaySuccess(getRequest());
//		payReturnMessage = paymentProduct.getPayReturnMessage(tradeNo);
//		
//		if(!paymentProduct.verifySign(paymentService.getPaymentConfig(userAccountRecharge.getRechargeInterfaceId()), getRequest())){
//			logPayment.debug("【充值回调结束-后台】NG["+tradeNo+"]支付签名错误!");
//			payNotifyMessage="单号"+tradeNo+"支付签名错误！";
//			return "paynotify";
//		}
//		if(!isSuccess){
//			logPayment.debug("【充值回调结束-后台】NG["+tradeNo+"]支付不成功"+paymentProduct.returnResult(getRequest())+"");
//			payNotifyMessage="单号"+tradeNo+"支付不成功！";
//			return "paynotify";
//		}
////		if(userAccountRecharge.getStatus().equals(1)){//充值成功
////			logPayment.debug("【充值回调结束-后台】NG["+tradeNo+"]充值已经成功===");
////			payNotifyMessage="单号"+tradeNo+"充值已经成功，不再重复充值！";
////			return "paynotify";
////		}
////		if(!userAccountRecharge.getStatus().equals(2)){//充值中
////			logPayment.debug("【充值回调结束-后台】NG["+tradeNo+"]交易状态错误 《不是充值中的状态》");
////			payNotifyMessage="单号"+tradeNo+"交易状态错误 ！";
////			return "paynotify";
////		}
//		if(totalAmount.compareTo(userAccountRecharge.getMoney()) != 0){
//			logPayment.debug("【充值回调结束-后台】NG["+tradeNo+"]交易金额错误!");
//			return "paynotify";
//		}
//		
//		String feeAmt = userAccountRecharge.getFee().toString();
//		log.info("交易金额:"+totalAmount+";商户提取佣金金额:"+feeAmt+"-后台");
//		Map<String,Object> detailMap = new HashMap<String,Object>();
//		//detailMap.put("userId", getLoginUser().getId());
//		detailMap.put("tranAmt",totalAmount);
//		detailMap.put("feeAmt", feeAmt);
//		detailMap.put("operatorIp", getRequestRemoteAddr());
//		
//		Map<String,Object> map= new HashMap<String ,Object>();
//		map.put("modifyDate", new Date());
//		map.put("status", 1);
//		map.put("returned",paymentProduct.returnResult(getRequest()));
//		map.put("tradeNo", tradeNo);
//		map.put("rechargeId", userAccountRecharge.getId());
//		int ret = paymentService.rechargeSuccess(map,detailMap);
//		if (ret==1) {
//			logPayment.debug("【充值回调结束-后台】NG["+tradeNo+"]充值已经成功===");
//			payNotifyMessage = paymentProduct.getPayNotifyMessage();
//			return "paynotify";
//		} else if (ret ==2) {
//			logPayment.debug("【充值回调结束-后台】NG["+tradeNo+"]交易状态错误 《不是充值中的状态》");
//			payNotifyMessage="单号"+tradeNo+"交易状态错误 ！";
//			return "paynotify";
//		}
//		log.info("充值通知处理成功-后台");
//		logPayment.debug("【充值回调结束-后台】交易成功 ["+tradeNo+"]金额["+totalAmount+"]费用["+feeAmt+"]");
//		} catch(Exception e) {
//			e.printStackTrace();
//			logPayment.debug("【充值回调结束-后台】NG["+tradeNo+"]系统异常!");
//			payNotifyMessage="单号"+tradeNo+"支付出现系统错误！";
//			throw(e);
//		}
//		payNotifyMessage="单号"+tradeNo+"支付完成！";
//		return "paynotify";
	}
	
	/**
	 * 
	 * @return
	
	@Action(value="/payment/examineVerify",results={@Result(name="verify", location="/content/payment/payment_verify.ftl", type="freemarker")})
	public String examineVerify(){
		userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(tradeNo);
		if(userAccountRecharge == null){
			addActionError("充值记录不存在!");
			return "error_ftl";
		}
		
		BasePaymentProduct paymentProduct = paymentService.getPaymentProduct(userAccountRecharge.getRechargeInterfaceId());
		if(paymentProduct == null){
			addActionError("支付产品不存在!");
			return "error_ftl";
		}
		
		payReturnMessage = paymentProduct.getExamineVerify(userAccountRecharge);
		
		return "verify";
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
	
	public boolean verifyReturSign(RechargeConfig rechargeConfig,HttpServletRequest request) {
		
		PayDataBean payDataBean= new  PayDataBean();
		payDataBean.setDt_order(request.getParameter("dt_order"));
		payDataBean.setBank_code(request.getParameter("bank_code"));
		payDataBean.setInfo_order(request.getParameter("info_order"));
		payDataBean.setMoney_order(request.getParameter("money_order"));
		payDataBean.setNo_order(request.getParameter("no_order"));
		payDataBean.setOid_partner(request.getParameter("oid_partner"));
		payDataBean.setOid_paybill(request.getParameter("oid_paybill"));
		payDataBean.setPay_type(request.getParameter("pay_type"));
		payDataBean.setResult_pay(request.getParameter("result_pay"));
		payDataBean.setSettle_date(request.getParameter("settle_date"));
		payDataBean.setSign_type(request.getParameter("sign_type"));
		payDataBean.setBank_code(request.getParameter("bank_code"));
		
		  String sign = LLPayUtil.addSign(JSON.parseObject(JSON
	                .toJSONString(payDataBean)), rechargeConfig.getDescription(),
	                rechargeConfig.getBargainorKey());
		  Boolean result = null;	
	
			if(sign.compareTo(request.getParameter("sign"))==0){
					result = true;
			}else{
					result = false;
			}
			 return result;
		 
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
	
	
	
	
	
	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	/**qxw 
	 * 充值提交  新
	 * modify by yujian
	 * @return
	 */
	

	@Action(value="/payment/submitHnewBAK",results={@Result(name="onLine", location="/content/payment/payment_submit.ftl", type="freemarker"),
											 @Result(name="offLine", location="/content/payment/payment_off_line.ftl", type="freemarker"),
											 @Result(name="rongbao", location="/userCenter/payOrder.do", type="redirect")})
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String submitHnew(){
		log.info("开始充值");
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		msgUrl="/payment/rechargeTo.do";
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		if (mycode == null || !mycode.equalsIgnoreCase(random)) {
			msg = "验证码输入错误!";
			return "error_ftl";
		}
		User userLogin = getLoginUser();

		
		logPayment.debug("【充值开始】"+userLogin.getUsername()+",["+payment+"]");
		
		UserAccountRecharge userRecharge = new UserAccountRecharge();
		Integer rechargeInterfaceId =0;//默认线下充值
		if(!this.isNumeric(payment)){//直联支付【字母(ICBC)】
			userRecharge.setBankName(payment);
		}else{//接口支付
			rechargeInterfaceId = Integer.parseInt(payment);
		}
		
		BigDecimal paymentFee = null;// 支付手续费
		BigDecimal amountPayable = null;// 应付金额【不包含手续费】
		BasePaymentProduct paymentProduct = null;
		try{
			BigDecimal rechargeAmount = userAccountRecharge.getMoney();//充值金额
			if (rechargeAmount == null) {
				msg = "请输入充值金额!";
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 请输入充值金额!"+"["+rechargeAmount+"]");
				return "error_ftl";
			}
			if (rechargeAmount.compareTo(new BigDecimal(1)) < 0) {
				msg = "充值金额必须大于1!";
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须大于1!"+"["+rechargeAmount+"]");
				return "error_ftl";
			}
			if(rechargeAmount.compareTo(new BigDecimal(100000))>=0){
				msg = "充值金额必须小于100000!";
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须小于100000!"+"["+rechargeAmount+"]");
				return "error_ftl";
			}
			if (rechargeAmount.scale() > ConstantUtil.PRICESCALE) {
				msg = "充值金额小数位超出限制!";
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额小数位超出限制!"+"["+rechargeAmount+"]");
				return "error_ftl";
			}
			
			
			
			amountPayable = rechargeAmount;//.add(paymentFee);
			
			
			if(rechargeInterfaceId != 0){//线上充值
				//paymentProduct = paymentService.getPaymentProduct(rechargeInterfaceId);
				//paymentUrl = paymentProduct.getPaymentUrl();
			

			
			}else{
				String miniMoney = getServletContext().getAttribute("qmd.setting.miniMoney").toString();
				if(rechargeAmount.compareTo(new BigDecimal(miniMoney)) < 0 ){
					msg = "线下充值金额有误，请重新输入!";
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 线下充值金额小于:"+miniMoney);
					return "error_ftl";
				}
			}
		}catch (Exception e) {
			msg = "充值金额有误，请重新输入!";
			logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额有误，请重新输入!"+"");
			return "error_ftl";
		}
		
		userRecharge.setCreateDate(new Date());
		userRecharge.setModifyDate(new Date());
		userRecharge.setTradeNo(SerialNumberUtil.buildPaymentSn());//充值订单号
		userRecharge.setUserId(getLoginUser().getId());
		userRecharge.setStatus(2);//充值中
		userRecharge.setMoney(CommonUtil.setPriceScale(amountPayable, null) );
		userRecharge.setRechargeInterfaceId(rechargeInterfaceId);
		userRecharge.setReturned("");
		if(rechargeInterfaceId != 0){//线上充值
			userRecharge.setType("1");
			userRecharge.setRemark("充值"+CommonUtil.setPriceScale(amountPayable, null)+"元");//getLoginUser().getUsername()+rechargeConfig.getName()+
			userRecharge.setFee(paymentFee);
			userRecharge.setReward(new BigDecimal(0));//奖励为0
		}else{
			userRecharge.setType("0");
			userRecharge.setOffLineAccountId(userAccountRecharge.getOffLineAccountId());
			userRecharge.setRemark(userAccountRecharge.getRemark());
			userRecharge.setFee(new BigDecimal(0));//手续费为0
			userRecharge.setReward(paymentFee);
		}
		userRecharge.setIpUser(getRequestRemoteAddr());
		
		 AccountBank accountBank =userService.queryAccountBank(getLoginUser().getId()).get(0);
		 Map<String,Object> mapx= new HashMap<String ,Object>();
		    mapx.put("keyValue", accountBank.getBankId());
			Listing listing = listingService.findListing(mapx);
			if(listing != null){
				userRecharge.setBankName(listing.getName());
		}

		
		paymentService.addUserAccountRecharge(userRecharge);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", userLogin.getId());
		 user = this.userService.getUser(map);//获取改用户的资料
		log.info("跳转到充值页面");
		//logPayment.debug("【充值跳转】"+userLogin.getUsername()+",单号:"+userRecharge.getTradeNo()+",金额:"+userRecharge.getMoney()+",充值方式:"+rechargeConfig.getName());
		if(rechargeInterfaceId != 0){//线上充值
			userRecharge.setRealName(user.getRealName());
			userRecharge.setCardId(user.getCardId());
			userRecharge.setRegister( CommonUtil.getDate2String(user.getCreateDate(),"yyyyMMddHHmmss"));
			
	
			userRecharge.setBankNo(accountBank.getAccount().replaceAll("\\s*", ""));
			userRecharge.setBackUrl(ConstantUtil.WEB_SITE+"/userCenter/toBankInput.do");
			userRecharge.setMobile(accountBank.getPhone());
			userRecharge.setPayCode(accountBank.getBankId());
		
			jsonMap=new HashMap();//输出数据
			
			 List<AccountBank> ll=userService.queryAccountBank(user.getId());//查询用户银行卡
			 AccountBank accounb =new AccountBank();
			 if(ll.size()>0){//
				  accounb =ll.get(0);		 
			 }else{
				 msg = "请先绑卡";
				 msg = "/userCenter/toBank.do";
				 return "error_ftl";	 
			 }
			 //旧用户预留手机号在用户表中，现在存到银行卡表
			 if(StringUtils.isBlank(accounb.getPhone())){
				 accounb.setPhone(user.getPhone());
				 userService.updateAccountBank(accounb); 
			 }
			
			 // 用户年龄[18,70]周岁方可使用融宝充值，
			 if(!AgeLimitUtils.isInLimit(user.getCardId())){
				 log.debug("-------用户年龄不在[18,70]内，使用宝付充值-开始");
			    		Integer baofookj = 40;//宝付快捷
			    		RechargeConfig rechargeConfig = paymentService.getPaymentConfig(baofookj);
						if (rechargeConfig == null) {
							msg="无法绑卡，请联系客服";
							msgUrl="toBankInput.do";
							return "error_ftl";
						}
						
						//更新充值方式
						HashMap<String, Object> mapUpdate = new HashMap<String, Object>();
						mapUpdate.put("modifyDate", new Date());
						mapUpdate.put("rechargeId", baofookj);
						mapUpdate.put("tradeNo", userRecharge.getTradeNo());
						paymentService.updateRechargeId(mapUpdate);
						
						paymentProduct = paymentService.getPaymentProduct(baofookj);
						paymentUrl = paymentProduct.getPaymentUrl();
						
						userRecharge.setRealName(userLogin.getRealName());
						userRecharge.setCardId(userLogin.getCardId());
						userRecharge.setRegister( CommonUtil.getDate2String(userLogin.getCreateDate(),"yyyyMMddHHmmss"));
						
						userRecharge.setBankNo(accountBank.getAccount().replaceAll("\\s*", ""));
						userRecharge.setBackUrl(ConstantUtil.WEB_SITE+"/userCenter/toBankInput.do");
						userRecharge.setMobile(userLogin.getPhone());
						userRecharge.setPayCode(accountBank.getBankId());
						
						parameterMap = paymentProduct.getParameterMap(rechargeConfig, userRecharge, getRequest());
						
						log.debug("-------用户年龄不在[18,70]内，使用宝付充值-结束");
			    		return "onLine";//宝付页面
			 }

			//先去查下用户有没有在融宝 绑卡过.没绑卡默认给他绑卡充值.
			Boolean isBind=false;
			String jsonIsBindCard=RongbaoUtil.RongbaoToSelectIsbindCard(userRecharge.getUserId());
			if(StringUtils.isBlank(jsonIsBindCard)){
				msg = "请换种支付方式，重新尝试，或联系客服";
				return "error_ftl";
			}
			JSONObject jsonObj = JSONObject.parseObject(jsonIsBindCard);
			JSONArray array = jsonObj.getJSONArray("bind_card_list");
			if(array!=null&& !array.isEmpty()){
				isBind=true;
			}
		    Map mapIsBindCard = (Map)JSON.parse(jsonIsBindCard);  //json转map
		    rongbaoPaymentLog.debug("查询用户是否在融宝绑卡过---"+mapIsBindCard.get("bind_card_list")+"-若前面[]表示未绑卡--");
			
			 if(isBind && StringUtils.isNotBlank(accounb.getBindId())){
				 rongbaoPaymentLog.debug("用户已融宝绑卡过----调绑卡签约接口--");
				 Map resMap = new HashMap();
				try{
					resMap=paymentService.postRongbaoPay(userRecharge,user,accounb);
				}catch(Exception e){
					msg="请换种支付方式，重新尝试，或联系客服";
					return "error_ftl";
				}
				if(resMap==null||resMap.isEmpty()){
					msg="请换种支付方式，重新尝试，或联系客服";
					return "error_ftl";
				}
				if(!resMap.get("result_code").equals("0000")){
			    	msg=resMap.get("result_msg").toString();
					return "error_ftl";
			    }
			    //成功后，将订单号保存在session中
			    setSession(ConstantUtil.ORDER_NO, userRecharge.getTradeNo());	
				
			    rongbaoPaymentLog.debug("---融宝已绑卡----jsonMap="+jsonMap);
				return "rongbao";
			 }else{
				 rongbaoPaymentLog.debug("-用户没有在融宝绑卡过---查询用户卡信息 给其在融宝先签约---");
					 String jsonResult= RongbaoUtil.RongbaoPay(userRecharge,accounb, user);//调用银行
					 rongbaoPaymentLog.debug("-------调用融宝先签约返回结果="+jsonResult);
					 //验证返回信息
						if(StringUtils.isBlank(jsonResult)){
							msg="请换种支付方式，重新尝试，或联系客服";
							return "error_ftl";
						}
						Map map1 = null;
						try{
							map1 = (Map)JSON.parse(jsonResult);  //json转map
						}catch(Exception e){
							msg="请换种支付方式，重新尝试，或联系客服";
							return "error_ftl";
						}
						if(map1 == null || map1.isEmpty()){
							msg="请换种支付方式，重新尝试，或联系客服";
							return "error_ftl";
						}
						
					    if(!map1.get("result_code").equals("0000")){
					    	if(map1.get("result_code").equals("2011")){//手机号不对：属于老用户手机号在宝付换掉了
					    		//解绑
					    		user.setRealStatus(0);
					    		accounb.setStatus(0);
					    		userService.update(user);
					    		userService.updateAccountBank(accounb);	
					    		//跳转到绑卡页面
					    		msg=map1.get("result_msg").toString()+",需重新绑卡！";
					    		msgUrl = "/userCenter/toBankInput.do";
								return "error_ftl";
					    	}else{
						    	msg=map1.get("result_msg").toString();
								return "error_ftl";
					    	}
					    }
					    //成功后，将订单号保存在session中
					   setSession(ConstantUtil.ORDER_NO, userRecharge.getTradeNo());

						rongbaoPaymentLog.debug("--调融宝默认绑卡-----jsonMap="+jsonMap);
					 
					 return "rongbao";				 
				 }
 
				
			 
	

		}else{
			return "offLine";
		}
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	
}
