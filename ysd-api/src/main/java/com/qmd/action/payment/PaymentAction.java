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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.baofoo.util.JXMConvertUtil;
import com.qmd.baofoo.util.SecurityUtil;
import com.qmd.baofoo.util.rsa.RsaCodingUtil;
import com.qmd.bean.funds.UserRechargeBean;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.payment.BasePaymentProduct;
import com.qmd.service.payment.PayService;
import com.qmd.service.payment.PaymentService;
import com.qmd.service.user.AccountBankService;
import com.qmd.service.user.AccountCashService;
import com.qmd.service.user.UserAccountDetailService;
import com.qmd.service.user.UserAccountRechargeService;
import com.qmd.service.user.UserAccountService;
import com.qmd.service.user.UserService;
import com.qmd.service.util.ListingService;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;
import com.qmd.util.SerialNumberUtil;

/**
 * 充值
 * 
 */
@InterceptorRefs({ @InterceptorRef(value = "userVerifyInterceptor"), @InterceptorRef(value = "qmdDefaultStack") })
@Service("paymentAction")
public class PaymentAction extends BaseAction {

	Logger log = Logger.getLogger(PaymentAction.class);
	private Logger logPayment = Logger.getLogger("userPaymentLog");
	private static final long serialVersionUID = 6388112224388918414L;

	private User user;
	private UserAccountRecharge userAccountRecharge;

	private String type;// 充值条件-0:全部记录；1：充值成功；2：线上充值；3：线下充值
	private Map<String, String> parameterMap;// 支付参数
	private String paymentUrl;// 支付请求URL
	private String safepwd;// 安全密码

	private String tradeNo;//支付编号
	private String payReturnMessage;// 支付返回信息
	private String payNotifyMessage;// 支付通知信息
	private String idNo;//身份证号码
	private String realName;//真实姓名
	private String cardNo;//
	private UserRechargeBean urb;

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
	private ListingService listingService;
	@Resource
	PaymentService paymentService;
	@Resource
	PayService payService;
	/**
	 * 我要充值
	 * 
	 * @return
	 */
	@Action(value = "/payment/rechargeTo",results={@Result(name="success", location="/content/h5payment/payment_recharge.ftl", type="freemarker"),
			 									@Result(name="msg", location="/content/message_page.ftl", type="freemarker")})
	public String rechargeTo() {
		try {
			reloadUser();
			user = getLoginUser();
			//判断有无绑定银行卡
			List<AccountBank> ablist = accountBankService.getAccountBankList(user.getId());
			if(ablist == null || ablist.size()<1){
				msg="请绑定银行卡!";
				return "error_ftl";
			}

			if(ablist.get(0).getStatus().compareTo(1) !=0){
				msg="请绑定银行卡!";
				return "error_ftl";
			}
			
			UserAccount ua = new UserAccount();
			ua = userAccountService.getUserAccountByUserId(user.getId());

			RechargeConfig rechargeConfig = paymentService.getPaymentConfig(50);
			Integer paymentFeeType = rechargeConfig.getPaymentFeeType();
			urb = new UserRechargeBean();
			String card= ablist.get(0).getAccount();
			urb.setCardNo(card);
			cardNo = card.substring(card.length()-4, card.length());
			urb.setStatus(ablist.get(0).getStatus());
			urb.setBankId(ablist.get(0).getBankId());
			urb.setBankName(ablist.get(0).getName());
			urb.setPhone(user.getPhone());
			
			if (paymentFeeType == 0 || paymentFeeType == 2) {// 按比例收取
				urb.setFeeType("0");
			} else {
				urb.setFeeType("1");
			}

			if (paymentFeeType == 0 || paymentFeeType == 1) {
				if ("0".equals(urb.getFeeType())) {
					urb.setFee(rechargeConfig.getPaymentFee().divide(new BigDecimal(100)).toString());
				} else {
					urb.setFee(rechargeConfig.getPaymentFee().toString());
				}
			} else {
				if ("0".equals(urb.getFeeType())) {
					urb.setAward(rechargeConfig.getPaymentFee().divide(new BigDecimal(100)).toString());
				} else {
					urb.setAward(rechargeConfig.getPaymentFee().toString());
				}
			}
			urb.setAbleMoney(ua.getAbleMoney().toString());
			urb.setRealName(user.getRealName());
			urb.setCardId(user.getCardId());
			urb.setUserId(user.getId().toString());
			urb.setRegisterTime(CommonUtil.getDate2String(user.getCreateDate(), "yyyyMMddHHmmss"));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			msg="程序内部错误!";
			return "error_ftl";
		}
	}

	/**
	 * 我要充值 提交
	 * 
	 * @return
	 */
	@Action(value="/payment/rechargeSave",results={@Result(name="onLine", location="/content/payment/payment_submit.ftl", type="freemarker")})
	@InputConfig(resultName = "error_no_back_ftl,success_ftl")
	public String rechargeSave() {
		try {

			log.info("开始充值");
			User userLogin = getLoginUser();
			if (!userService.isPassword(userLogin.getUsername(), safepwd, "1")) {
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

			BigDecimal paymentFee = null;// 支付手续费
			BigDecimal amountPayable = null;// 应付金额【不包含手续费】
			BasePaymentProduct paymentProduct = null;
			try {
				BigDecimal rechargeAmount = userAccountRecharge.getMoney();// 充值金额
				if (rechargeAmount == null) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 请输入充值金额!"+"["+rechargeAmount+"]");
					msg="请输入充值金额!";
					return "error_ftl";
				}
//				if (rechargeAmount.compareTo(new BigDecimal(0)) < 100) {
//					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须大于0!"+"["+rechargeAmount+"]");
//					return ajaxJson("S0002", "充值金额不能小于100");
//				}
				if (rechargeAmount.compareTo(new BigDecimal(0)) < 0) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须大于0!"+"["+rechargeAmount+"]");
					msg="充值金额不能小于0!";
					return "error_ftl";
				}
				if (rechargeAmount.compareTo(new BigDecimal(100000000)) >= 0) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须小于100000000!"+"["+rechargeAmount+"]");
					msg="充值金额小于100000000!";
					return "error_ftl";
				}
				if (rechargeAmount.scale() > ConstantUtil.PRICESCALE) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额小数位超出限制!"+"["+rechargeAmount+"]");
					msg="充值金额小数位超出限制!";
					return "error_ftl";
				}
				amountPayable = rechargeAmount;// .add(paymentFee);
				paymentFee = rechargeConfig.getPaymentFee(rechargeAmount);

				if (rechargeInterfaceId != 0) {// 线上充值
					paymentProduct = paymentService.getPaymentProduct(rechargeInterfaceId);
					paymentUrl = paymentProduct.getPaymentUrl();
				}
			} catch (Exception e) {
				msg="充值金额有误，请重新输入!";
				return "error_ftl";
			}

			userRecharge.setCreateDate(new Date());
			userRecharge.setModifyDate(new Date());
			userRecharge.setTradeNo(SerialNumberUtil.buildPaymentSn());
			userRecharge.setUserId(getLoginUser().getId());
			userRecharge.setStatus(2);// 充值中
			userRecharge.setMoney(CommonUtil.setPriceScale(amountPayable, null));
			userRecharge.setRechargeInterfaceId(rechargeInterfaceId);
			userRecharge.setReturned("");
			if (rechargeInterfaceId != 0) {// 线上充值
				userRecharge.setType("1");
				userRecharge.setRemark("账户充值");// getLoginUser().getUsername()+rechargeConfig.getName()+
				userRecharge.setFee(paymentFee);
				userRecharge.setReward(new BigDecimal(0));// 奖励为0
			}
			userRecharge.setIpUser(getRequestRemoteAddr());
			paymentService.addUserAccountRecharge(userRecharge);
			 AccountBank accountBank =userService.queryAccountBank(getLoginUser().getId()).get(0);
			 userRecharge.setPayCode(accountBank.getBankId());
			
			log.info("跳转到充值页面");
			logPayment.debug("【充值跳转】" + userLogin.getUsername() + ",单号:" + userRecharge.getTradeNo() + ",金额:" + userRecharge.getMoney() + ",充值方式:"
					+ rechargeConfig.getName());
			userRecharge.setPhone(userLogin.getPhone());
			parameterMap = paymentProduct.getParameterMap(rechargeConfig, userRecharge, getRequest());

			return "onLine";
			
		} catch (Exception e) {
			e.printStackTrace();
			msg="程序内部错误!";
			return "error_ftl";
		}
	}
	
	/**
	 * 充值回调处理-前台
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@Action(value="/payreturn",
			results={@Result(name="success", location="/content/payment/payment_payreturn.ftl", type="freemarker"),
					 @Result(name="result", location="/content/payment/payment_result.ftl", type="freemarker"),
					 @Result(name="pay_fail", location="/content/payment/payment_fail.ftl", type="freemarker"),
					 @Result(name="sign", location="/content/payment/payment_sign.ftl", type="freemarker")
					 })
	public String payreturn() throws UnsupportedEncodingException{
		log.info("充值回调-前台");
		logPayment.debug("【充值回调开始-前台】");
		

		BasePaymentProduct paymentProduct = paymentService.getPaymentProduct(70);
		if(paymentProduct == null){
			addActionError("支付产品不存在!");
			logPayment.debug("【充值回调结束-前台】NG,支付产品不存在!");
			return "pay_fail";
		}
		String data_content = getRequest().getParameter("data_content");//回调参数
		if(data_content.isEmpty()){//判断参数是否为空
			
			msg ="返回数据为空";
			return "error_ftl";
		}
		try {
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
			 System.out.println("=====>>"+data_content);
			 if(data_content.isEmpty()){//判断解密是否正确。如果为空则宝付公钥不正确
				 msg ="检查解密公钥是否正确！";
				 return "error_ftl";
			 }
			 data_content = SecurityUtil.Base64Decode(data_content);		 
			 System.out.println("==jiemi===>>"+data_content);
			 System.out.println("==data_type===>>"+data_type);
//			if(data_type.equals("xml")){
//				data_content = JXMConvertUtil.XmlConvertJson(data_content);		    
//				
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String,Object> ArrayData = JXMConvertUtil.JsonConvertHashMap((Object)data_content);//将JSON转化为Map对象。
		
		if(!ArrayData.containsKey("resp_code")){
			 msg ="订单异常！";
			 return "error_ftl";
		}else{
			String resp_code = ArrayData.get("resp_code").toString();
			BigDecimal  totalAmount = BigDecimal.valueOf(Double.valueOf(ArrayData.get("succ_amt").toString()));
			String tradeNo = ArrayData.get("trans_id").toString();
			String bargainorIdName = ArrayData.get("member_id").toString();
			//这里根据ArrayData 对象里的值去写本地服务器端数据
			//商户自行写入自已的数据。。。。。。。。
			if(StringUtils.isNotEmpty(tradeNo)){
				userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(tradeNo);
				if(userAccountRecharge == null){
					addActionError("充值记录不存在!");
					logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]充值记录不存在!");
					return "pay_fail";
				}
				if(resp_code.equals("0000")){
					
//						payReturnMessage = paymentProduct.getPayReturnMessage(tradeNo);
					
						if(userAccountRecharge.getStatus().equals(1)){//充值成功
							logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]充值已经成功===");
							return "result";
						}
						if(!userAccountRecharge.getStatus().equals(2)){//充值中
							addActionError("交易状态错误!");
							logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]交易状态错误 《不是充值中的状态》");
							return "pay_fail";
						}
						if(totalAmount.compareTo(userAccountRecharge.getMoney()) != 0){
							addActionError("交易金额错误!");
							logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]交易金额错误!");
							return "pay_fail";
						}
						if(!(paymentProduct.getBargainorIdName().equals(bargainorIdName))){
							addActionError("商户号错误!");
							logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]商户号错误!");
							return "pay_fail";
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
		//				map.put("returned", strBuffer.toString());
						map.put("returned", data_content);
						map.put("tradeNo", tradeNo);
						map.put("rechargeId", userAccountRecharge.getId());
//						map.put("bank_code",  getRequest().getParameter("bank_code"));//银行编号
						//paymentService.rechargeSuccess(map,detailMap);
						int ret = payService.rechargeSuccess(map,detailMap);
						System.out.println("----------"+ret+"------------------");
						if (ret==1) {
							logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]充值已经成功===");
							return "pay_fail";
						} else if (ret ==2) {
							logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]交易状态错误 《不是充值中的状态》");
							payNotifyMessage="单号"+tradeNo+"交易状态错误 ！";
							return "pay_fail";
						} else if(ret ==3){
							return "sign";//签约成功页面
						}
						log.info("充值回调成功-前台");
						logPayment.debug("【充值回调结束-前台】交易成功 ["+tradeNo+"]金额["+totalAmount+"]费用["+feeAmt+"]");
						return SUCCESS;
				}
			}
		}
		addActionError("充值异常!");
		return "pay_fail";
	}
	/**
	 * 充值通知处理
	 * @return
	 * @throws Exception 
	 */
	@Action(value="/paynotify",results={@Result(name="paynotify", location="/content/payment/payment_paynotify.ftl", type="freemarker")})
	public String paynotify() throws Exception{
		log.info("充值回调-后台");
		logPayment.debug("【充值回调开始-后台】");
//		try{
//		StringBuffer strBuffer = new StringBuffer();
//		for(Object s:getRequest().getParameterMap().keySet()){
//			strBuffer.append(s.toString()+"=" + getRequest().getParameter(s.toString()) + "&");
//		}
//		log.info("参数="+strBuffer.toString());
		
		
		BasePaymentProduct paymentProduct = paymentService.getPaymentProduct(70);
		if(paymentProduct == null){
			logPayment.debug("【充值回调结束-后台】NG["+tradeNo+"]支付产品不存在!");
			payNotifyMessage="单号"+tradeNo+"支付产品不存在！";
			return "paynotify";
		}
		
		String data_content = getRequest().getParameter("data_content");//回调参数
		if(data_content.isEmpty()){//判断参数是否为空
			
			msg ="返回数据为空";
			return "error_ftl";
		}
		try {
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
			 System.out.println("==jie===>>"+data_content); 
			 System.out.println("==data_type===>>"+data_type);
//			if(data_type.equals("xml")){
//				data_content = JXMConvertUtil.XmlConvertJson(data_content);		    
//				
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String,Object> ArrayData = JXMConvertUtil.JsonConvertHashMap((Object)data_content);//将JSON转化为Map对象。
		
		if(!ArrayData.containsKey("resp_code")){
			 msg ="订单异常！";
			 return "error_ftl";
		}else{
			String resp_code = ArrayData.get("resp_code").toString();
			BigDecimal  totalAmount = BigDecimal.valueOf(Double.valueOf(ArrayData.get("succ_amt").toString()));
			String tradeNo = ArrayData.get("trans_id").toString();
			String bargainorIdName = ArrayData.get("member_id").toString();
			//这里根据ArrayData 对象里的值去写本地服务器端数据
			//商户自行写入自已的数据。。。。。。。。
			if(StringUtils.isNotEmpty(tradeNo)){
				userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(tradeNo);
				
//				payReturnMessage = paymentProduct.getPayReturnMessage(tradeNo);
				if(userAccountRecharge == null){
					addActionError("充值记录不存在!");
					logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]充值记录不存在!");
					return "paynotify";
				}
				if(resp_code.equals("0000")){
					
//						payReturnMessage = paymentProduct.getPayReturnMessage(tradeNo);
					
//						if(userAccountRecharge.getStatus().equals(1)){//充值成功
//							logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]充值已经成功===");
//							return "result";
//						}
//						if(!userAccountRecharge.getStatus().equals(2)){//充值中
//							addActionError("交易状态错误!");
//							logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]交易状态错误 《不是充值中的状态》");
//							return "pay_fail";
//						}
						if(totalAmount.compareTo(userAccountRecharge.getMoney()) != 0){
							addActionError("交易金额错误!");
							logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]交易金额错误!");
							return "paynotify";
						}
						if(!(paymentProduct.getBargainorIdName().equals(bargainorIdName))){
							addActionError("商户号错误!");
							logPayment.debug("【充值回调结束-前台】NG["+tradeNo+"]商户号错误!");
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
		//				map.put("returned", strBuffer.toString());
						map.put("returned", data_content);
						map.put("tradeNo", tradeNo);
						map.put("rechargeId", userAccountRecharge.getId());
//						map.put("bank_code",  getRequest().getParameter("bank_code"));//银行编号
						//paymentService.rechargeSuccess(map,detailMap);
						int ret = payService.rechargeSuccess(map,detailMap);
						System.out.println("----------"+ret+"------------------");
						if (ret==1 ||ret==3) {
							logPayment.debug("【充值回调结束-后台】NG["+tradeNo+"]充值已经成功===");
							payNotifyMessage = paymentProduct.getPayNotifyMessage();
							return "paynotify";
						} else if (ret ==2) {
							logPayment.debug("【充值回调结束-后台】NG["+tradeNo+"]交易状态错误 《不是充值中的状态》");
							payNotifyMessage="单号"+tradeNo+"交易状态错误 ！";
							return "paynotify";
						}
						log.info("充值回调成功-前台");
						logPayment.debug("【充值回调结束-前台】交易成功 ["+tradeNo+"]金额["+totalAmount+"]费用["+feeAmt+"]");
						return "paynotify";
				}
			}
		}
		payNotifyMessage="单号"+tradeNo+"支付完成！";
		return "paynotify";
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

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public UserRechargeBean getUrb() {
		return urb;
	}

	public void setUrb(UserRechargeBean urb) {
		this.urb = urb;
	}

}
