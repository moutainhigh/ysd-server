package com.qmd.action.payment;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.ApiBaseAction;
import com.qmd.baofoo.util.JXMConvertUtil;
import com.qmd.baofoo.util.SecurityUtil;
import com.qmd.baofoo.util.rsa.RsaCodingUtil;
import com.qmd.bean.PageBean;
import com.qmd.bean.funds.UserRecharge;
import com.qmd.bean.funds.UserRechargeBean;
import com.qmd.bean.funds.UserRechargeList;
import com.qmd.bean.payment.Lianlian;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.mode.util.Listing;
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
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConfigUtil;
import com.qmd.util.ConstantUtil;
import com.qmd.util.JsonUtil;
import com.qmd.util.SerialNumberUtil;
import com.qmd.util.redis.CacheUtil;
import com.qmd.util.rongbaoConfig.ReapalConfig;
import com.qmd.util.rongbaoUtils.Decipher;
import com.qmd.util.rongbaoUtils.Md5Utils;
import com.qmd.util.rongbaoUtils.RongbaoUtil;
import com.qmd.wap.util.DateUtil;
import com.ysd.biz.YueSmsUtils;

/**
 * 充值
 * 
 */
@InterceptorRefs({ @InterceptorRef(value = "apiUserInterceptor"), @InterceptorRef(value = "qmdDefaultStack") })
@Service("apiPaymentAction")
public class ApiPaymentAction extends ApiBaseAction {

	Logger log = Logger.getLogger(ApiPaymentAction.class);
	private Logger logPayment = Logger.getLogger("userPaymentLog");
	private Logger rongbaoPaymentLog = Logger.getLogger("rongbaoPaymentLog");
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
	private PayService payService;



	/**
	 * 我要充值
	 * 
	 * @return
	 */
	@Action(value = "/api/rechargeTo")
	public String rechargeTo() {
		try {
			reloadUser();
			user = getLoginUser();
			//判断有无绑定银行卡
			
			
			List<AccountBank> ablist = accountBankService.getAccountBankList(user.getId());
			if(ablist == null || ablist.size()<1){
				//return ajaxJson("M00010", ApiConstantUtil.M00010);
				return toBind(user);
			}
			
			if(ablist.get(0).getStatus() !=1){
				//return ajaxJson("M00010", ApiConstantUtil.M00010);
				return toBind(user);
			}
			
			UserAccount ua = new UserAccount();
			ua = userAccountService.getUserAccountByUserId(user.getId());

			RechargeConfig rechargeConfig = paymentService.getPaymentConfig(50);
			Integer paymentFeeType = rechargeConfig.getPaymentFeeType();
			UserRechargeBean urb = new UserRechargeBean();
			
			urb.setCardNo(ablist.get(0).getAccount());
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
			return ajax(JsonUtil.toJson(urb));
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJson("S0001", ApiConstantUtil.S0001);
		}
	}

	/**
	 * 我要充值 提交
	 * 
	 * @return
	 */
	@Action(value="/api/rechargeSave")
	@InputConfig(resultName = "error_no_back_ftl,success_ftl")
	public String rechargeSave() {
		try {

			log.info("开始充值");
			User userLogin = getLoginUser();
			if (!userService.isPassword(userLogin.getUsername(), safepwd, "1")) {
				return ajaxJson("S0002", ApiConstantUtil.S0002);
			}

			UserAccountRecharge userRecharge = new UserAccountRecharge();
			Integer rechargeInterfaceId = 40;

			RechargeConfig rechargeConfig = paymentService.getPaymentConfig(rechargeInterfaceId);
			if (rechargeConfig == null) {
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 支付方式不允许为空!"+"["+rechargeInterfaceId+"]");
				return ajaxJson("S0002", "支付方式不存在");
			}

			BigDecimal paymentFee = null;// 支付手续费
			BigDecimal amountPayable = null;// 应付金额【不包含手续费】
			BasePaymentProduct paymentProduct = null;
			try {
				BigDecimal rechargeAmount = userAccountRecharge.getMoney();// 充值金额
				if (rechargeAmount == null) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 请输入充值金额!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "请输入充值金额");
				}
//				if (rechargeAmount.compareTo(new BigDecimal(0)) < 100) {
//					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须大于0!"+"["+rechargeAmount+"]");
//					return ajaxJson("S0002", "充值金额不能小于100");
//				}
				if (rechargeAmount.compareTo(new BigDecimal(0)) < 0) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须大于0!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "充值金额不能小于0");
				}
				if (rechargeAmount.compareTo(new BigDecimal(100000000)) >= 0) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须小于100000000!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "充值金额小于100000000");
				}
				if (rechargeAmount.scale() > ConstantUtil.PRICESCALE) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额小数位超出限制!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "充值金额小数位超出限制!");
				}
				amountPayable = rechargeAmount;// .add(paymentFee);
				paymentFee = rechargeConfig.getPaymentFee(rechargeAmount);

				if (rechargeInterfaceId != 0) {// 线上充值
					paymentProduct = paymentService.getPaymentProduct(rechargeInterfaceId);
					paymentUrl = paymentProduct.getPaymentUrl();
				}
			} catch (Exception e) {
				addActionError("充值金额有误，请重新输入!");
				return ajaxJson("S0002", "充值金额有误，请重新输入");
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

			Lianlian ll = new Lianlian();
			ll.setPostUrl(paymentUrl);
			ll.setReqData(parameterMap.get("req_data"));
			return ajax(JsonUtil.toJson(ll));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJson("S0001", ApiConstantUtil.S0001);
		}
	}

	/**
	 * 充值记录
	 * 
	 * @return
	 */
	@Action(value = "/api/rechargeList")
	public String rechargeList() {
		try {
			initPage();
			reloadUser();// 重新载入用户信息
			Map<String, Object> map = new HashMap<String, Object>();
			user = getLoginUser();
			map.put("userId", user.getId());

			if (StringUtils.isEmpty(type)) {
				type = "0";
			}

			if ("1".equals(type)) {
				map.put("status", 1);
			} else if ("2".equals(type)) {
				map.put("type", "1");
			} else if ("3".equals(type)) {
				map.put("type", "0");
			}

			Object sum = userAccountService.getByStatementId("UserAccountRecharge.getUserAccountRechargeSum", map);
			BigDecimal sumRecharge = BigDecimal.ZERO;
			if (sum != null) {
				sumRecharge = new BigDecimal(sum.toString());
			}
			pager = this.userAccountService.getAccountRechargeByUserId(pager, map);

			List<UserAccountRecharge> list = (List<UserAccountRecharge>) pager.getResult();
			List<UserRecharge> urList = new ArrayList<UserRecharge>();
			for (UserAccountRecharge uar : list) {

				UserRecharge ur = new UserRecharge();
				ur.setCreateDate(uar.getCreateDate());
				ur.setId(uar.getId());
				ur.setMoney(uar.getMoney().toString());
				ur.setRechargeDate(uar.getRechargeDate());
				ur.setRechargeInterfaceId(uar.getRechargeInterfaceId());
				ur.setName(uar.getName());
				ur.setReward(uar.getReward().toString());
				ur.setStatus(uar.getStatus());
				ur.setTradeNo(uar.getTradeNo());
				ur.setType(uar.getType());// 0：线下充值 ;1:线上充值

				urList.add(ur);
			}

			UserRechargeList url = new UserRechargeList();
			PageBean pb = new PageBean();
			pb.setPageNumber(pager.getPageNumber());
			pb.setPageCount(pager.getPageCount());
			pb.setPageSize(pager.getPageSize());
			pb.setTotalCount(pager.getTotalCount());
			url.setPageBean(pb);

			url.setRechargeMoneySum(sumRecharge.toString());

			url.setUserRechargesList(urList);

			return ajax(JsonUtil.toJson(url));
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJson("S0001", ApiConstantUtil.S0001);
		}
	}

	


	/**
	 * 我要充值 提交H5
	 * 
	 * @return
	 */
	@Action(value="/api/rechargeSaveWeb",results={@Result(name="onLine", location="/content/payment/payment_submit.ftl", type="freemarker")})
	@InputConfig(resultName = "error_no_back_ftl,success_ftl")
	public String rechargeSave2() {
		try {

			log.info("开始充值");
			User userLogin = getLoginUser();
			if (!userService.isPassword(userLogin.getUsername(), user.getPayPassword(), "1")) {
				return ajaxJson("S0002", ApiConstantUtil.S0002);
			}

			UserAccountRecharge userRecharge = new UserAccountRecharge();
			Integer rechargeInterfaceId = 50;

			RechargeConfig rechargeConfig = paymentService.getPaymentConfig(rechargeInterfaceId);
			if (rechargeConfig == null) {
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 支付方式不允许为空!"+"["+rechargeInterfaceId+"]");
				return ajaxJson("S0002", "支付方式不存在");
			}

			BigDecimal paymentFee = null;// 支付手续费
			BigDecimal amountPayable = null;// 应付金额【不包含手续费】
			BasePaymentProduct paymentProduct = null;
			try {
				BigDecimal rechargeAmount = userAccountRecharge.getMoney();// 充值金额
				if (rechargeAmount == null) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 请输入充值金额!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "请输入充值金额");
				}
				if (rechargeAmount.compareTo(new BigDecimal(0)) <= 0) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须大于0!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "充值金额大于0");
				}
				if (rechargeAmount.compareTo(new BigDecimal(100000000)) >= 0) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须小于100000000!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "充值金额小于100000000");
				}
				if (rechargeAmount.scale() > ConstantUtil.PRICESCALE) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额小数位超出限制!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "充值金额小数位超出限制!");
				}
				amountPayable = rechargeAmount;// .add(paymentFee);
				paymentFee = rechargeConfig.getPaymentFee(rechargeAmount);

				if (rechargeInterfaceId != 0) {// 线上充值
					paymentProduct = paymentService.getPaymentProduct(rechargeInterfaceId);
					paymentUrl = paymentProduct.getPaymentUrl();
				}
			} catch (Exception e) {
				addActionError("充值金额有误，请重新输入!");
				return ajaxJson("S0002", "充值金额有误，请重新输入");
			}

			userRecharge.setCreateDate(new Date());
			userRecharge.setModifyDate(new Date());
			userRecharge.setTradeNo(DateUtil.getCurrentDateTimeStr1());
			userRecharge.setUserId(getLoginUser().getId());
			userRecharge.setStatus(2);// 充值中
			userRecharge.setMoney(CommonUtil.setPriceScale(amountPayable, null));
			userRecharge.setRechargeInterfaceId(rechargeInterfaceId);
			userRecharge.setReturned("");
			if (rechargeInterfaceId != 0) {// 线上充值
				userRecharge.setType("1");
				userRecharge.setRemark("充值" + CommonUtil.setPriceScale(amountPayable, null) + "元");// getLoginUser().getUsername()+rechargeConfig.getName()+
				userRecharge.setFee(paymentFee);
				userRecharge.setReward(new BigDecimal(0));// 奖励为0
			}
			userRecharge.setIpUser(getRequestRemoteAddr());
			paymentService.addUserAccountRecharge(userRecharge);

			log.info("跳转到充值页面");
			logPayment.debug("【充值跳转】" + userLogin.getUsername() + ",单号:" + userRecharge.getTradeNo() + ",金额:" + userRecharge.getMoney() + ",充值方式:"
					+ rechargeConfig.getName());

			parameterMap = paymentProduct.getParameterMap(rechargeConfig, userRecharge, getRequest());

			
			return "onLine";
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJson("S0001", ApiConstantUtil.S0001);
		}
	}
	

	
	/**
	 * 充值回调处理-前台
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@Action(value="/api/payreturn",
			results={@Result(name="success", location="/content/payment/payment_payreturn.ftl", type="freemarker"),
					 @Result(name="result", location="/content/payment/payment_result.ftl", type="freemarker"),
					 @Result(name="pay_fail", location="/content/payment/payment_fail.ftl", type="freemarker"),
					 @Result(name="sign", location="/content/payment/payment_sign.ftl", type="freemarker")
					 })
	public String payreturn() throws UnsupportedEncodingException{
		log.info("充值回调-前台");
		logPayment.debug("【充值回调开始-前台】");
		

		BasePaymentProduct paymentProduct = paymentService.getPaymentProduct(40);
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
	 * 充值回调处理
	 * @return
	 */
	@Action(value="/api/result",results={@Result(name="success", location="/content/payment/payment_result.ftl", type="freemarker"),
			 								 @Result(name="pay_fail", location="/content/payment/payment_fail.ftl", type="freemarker")})
	public String result() {
		log.info("充值回调处理");
		userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(tradeNo);
		if (userAccountRecharge == null || userAccountRecharge.getStatus() != 1) {
			addActionError("参数错误!");
			return "pay_fail";
		}
		return SUCCESS;
	}
	
	/**
	 * 充值通知处理
	 * @return
	 * @throws Exception 
	 */
	@Action(value="/api/paynotify",results={@Result(name="paynotify", location="/content/payment/payment_paynotify.ftl", type="freemarker")})
	public String paynotify() throws Exception{
		log.info("充值回调-后台");
		logPayment.debug("【充值回调开始-后台】");
//		try{
//		StringBuffer strBuffer = new StringBuffer();
//		for(Object s:getRequest().getParameterMap().keySet()){
//			strBuffer.append(s.toString()+"=" + getRequest().getParameter(s.toString()) + "&");
//		}
//		log.info("参数="+strBuffer.toString());
		
		
		BasePaymentProduct paymentProduct = paymentService.getPaymentProduct(40);
		if(paymentProduct == null){
			logPayment.debug("【充值回调结束-后台】NG["+tradeNo+"]支付产品不存在!");
			payNotifyMessage="单号"+tradeNo+"支付产品不存在！";
			return "paynotify";
		}
		
		String data_content = getRequest().getParameter("data_content");//回调参数
		if(data_content.isEmpty()){//判断参数是否为空
			
			msg ="返回数据为空";
			return "paynotify";
		}
		try {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String  cerpath =path+ConstantUtil.cerPath;//宝付公钥
			String data_type=ConstantUtil.dataType; //加密报文的数据类型（xml/json）
			
			File cerfile=new File(cerpath);
			if(!cerfile.exists()){//判断宝付公钥是否为空
				System.out.println("宝付公钥文件不存在！");
				
				msg ="公钥文件不存在！";
				return "paynotify";
			}
			
			 data_content = RsaCodingUtil.decryptByPubCerFile(data_content,cerpath);
			 if(data_content.isEmpty()){//判断解密是否正确。如果为空则宝付公钥不正确
				 msg ="检查解密公钥是否正确！";
				 return "paynotify";
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
			 return "paynotify";
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
	
	
	/**
	 * 我要充值 提交
	 * 
	 * @return
	 */
	@Action(value="/api/rechargeSaveWebH",results={@Result(name="onLine", location="/content/payment/payment_submit.ftl", type="freemarker")})
	public String rechargeSaveWebH() {
		try {

			log.info("开始充值");
			User userLogin = getLoginUser();
			if (!userService.isPassword(userLogin.getUsername(), safepwd, "1")) {
				return ajaxJson("S0002", ApiConstantUtil.S0002);
			}

			UserAccountRecharge userRecharge = new UserAccountRecharge();
			Integer rechargeInterfaceId = 70;

			RechargeConfig rechargeConfig = paymentService.getPaymentConfig(rechargeInterfaceId);
			if (rechargeConfig == null) {
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 支付方式不允许为空!"+"["+rechargeInterfaceId+"]");
				return ajaxJson("S0002", "支付方式不存在");
			}

			BigDecimal paymentFee = null;// 支付手续费
			BigDecimal amountPayable = null;// 应付金额【不包含手续费】
			BasePaymentProduct paymentProduct = null;
			try {
				BigDecimal rechargeAmount = userAccountRecharge.getMoney();// 充值金额
				if (rechargeAmount == null) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 请输入充值金额!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "请输入充值金额");
				}
//				if (rechargeAmount.compareTo(new BigDecimal(0)) < 100) {
//					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须大于0!"+"["+rechargeAmount+"]");
//					return ajaxJson("S0002", "充值金额不能小于100");
//				}
				if (rechargeAmount.compareTo(new BigDecimal(0)) < 0) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须大于0!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "充值金额不能小于0");
				}
				if (rechargeAmount.compareTo(new BigDecimal(100000000)) >= 0) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须小于100000000!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "充值金额小于100000000");
				}
				if (rechargeAmount.scale() > ConstantUtil.PRICESCALE) {
					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额小数位超出限制!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "充值金额小数位超出限制!");
				}
				amountPayable = rechargeAmount;// .add(paymentFee);
				paymentFee = rechargeConfig.getPaymentFee(rechargeAmount);

				if (rechargeInterfaceId != 0) {// 线上充值
					paymentProduct = paymentService.getPaymentProduct(rechargeInterfaceId);
					paymentUrl = paymentProduct.getPaymentUrl();
				}
			} catch (Exception e) {
				return ajaxJson("S0002", "充值金额有误，请重新输入");
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
			parameterMap.put("rcd", "R0001");
			parameterMap.put("rmg", "成功");
			parameterMap.put("paymentUrl", paymentUrl);
			return ajax(JsonUtil.toJson(parameterMap));
//			return "onLine";
			
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJson("S0001", ApiConstantUtil.S0001);
		}
	}
	
	
	private String money;
	
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	/**
	 * 我要充值 提交 融宝  
	 * 
	 * @return
	 */
	
	@Action(value="/api/rechargeSaveHnew")
	@InputConfig(resultName = "error_no_back_ftl,success_ftl")
	public String rechargeSaveHnew() {
		try {
			log.info("开始充值");
			rongbaoPaymentLog.debug("开始充值");
			User userLogin = getLoginUser();
			
			/*yujian if (!userService.isPassword(userLogin.getUsername(), safepwd, "1")) {
				return ajaxJson("S0002", ApiConstantUtil.S0002);
			}*/

			UserAccountRecharge userRecharge = new UserAccountRecharge();
			Integer rechargeInterfaceId = 80;

			/**
			RechargeConfig rechargeConfig = paymentService.getPaymentConfig(rechargeInterfaceId);
			if (rechargeConfig == null) {
				logPayment.debug("【充值结束】"+userLogin.getUsername()+" 支付方式不允许为空!"+"["+rechargeInterfaceId+"]");
				return ajaxJson("S0002", "支付方式不存在");
			}
		**/
			
			BigDecimal paymentFee = null;// 支付手续费
			BigDecimal amountPayable = null;// 应付金额【不包含手续费】
			BasePaymentProduct paymentProduct = null;
			try {
				
				BigDecimal rechargeAmount = userAccountRecharge.getMoney();// 充值金额
				if (rechargeAmount == null) {
					rongbaoPaymentLog.debug("【充值结束】"+userLogin.getUsername()+" 请输入充值金额!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "请输入充值金额");
				}
//				if (rechargeAmount.compareTo(new BigDecimal(0)) < 100) {
//					logPayment.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须大于0!"+"["+rechargeAmount+"]");
//					return ajaxJson("S0002", "充值金额不能小于100");
//				}
				if (rechargeAmount.compareTo(new BigDecimal(0)) < 0) {
					rongbaoPaymentLog.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须大于0!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "充值金额不能小于0");
				}
				if (rechargeAmount.compareTo(new BigDecimal(100000000)) >= 0) {
					rongbaoPaymentLog.debug("【充值结束】"+userLogin.getUsername()+" 充值金额必须小于100000000!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "充值金额小于100000000");
				}
				if (rechargeAmount.scale() > ConstantUtil.PRICESCALE) {
					rongbaoPaymentLog.debug("【充值结束】"+userLogin.getUsername()+" 充值金额小数位超出限制!"+"["+rechargeAmount+"]");
					return ajaxJson("S0002", "充值金额小数位超出限制!");
				}
				amountPayable = rechargeAmount;// .add(paymentFee);
			//	paymentFee = rechargeConfig.getPaymentFee(rechargeAmount);

				if (rechargeInterfaceId != 0) {// 线上充值
					//paymentProduct = paymentService.getPaymentProduct(rechargeInterfaceId);
					//paymentUrl = paymentProduct.getPaymentUrl();
				}
			} catch (Exception e) {
				addActionError("充值金额有误，请重新输入!");
				return ajaxJson("S0002", "充值金额有误，请重新输入");
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
				userRecharge.setRemark("app充值"+amountPayable);// getLoginUser().getUsername()+rechargeConfig.getName()+
				userRecharge.setFee(paymentFee);
				userRecharge.setReward(new BigDecimal(0));// 奖励为0
			}
			userRecharge.setIpUser(getRequestRemoteAddr());
			
			
		     AccountBank accountBank =userService.queryAccountBank(getLoginUser().getId()).get(0);
			 Map<String,Object> mapx= new HashMap<String ,Object>();

			 String bankId  = accountBank.getBankId();
			 if(StringUtils.isNumeric(bankId) ){
			 	bankId = "ICBC";
			 }
			    mapx.put("keyValue", bankId);
				Listing listing = listingService.findListing(mapx);
				if(listing != null){
					userRecharge.setBankName(listing.getName());
				}

			
			 userRecharge.setPayCode(accountBank.getBankId());
		
			 paymentService.addUserAccountRecharge(userRecharge);
			
			 rongbaoPaymentLog.info("跳转到充值页面");
			//logPayment.debug("【充值跳转】" + userLogin.getUsername() + ",单号:" + userRecharge.getTradeNo() + ",金额:" + userRecharge.getMoney() + ",充值方式:"rechargeConfig.getName());
			userRecharge.setPhone(userLogin.getPhone());
			//parameterMap = paymentProduct.getParameterMap(rechargeConfig, userRecharge, getRequest());
			//Lianlian ll = new Lianlian();
			//ll.setPostUrl(paymentUrl);
			//ll.setReqData(parameterMap.get("req_data"));
			//return ajax(JsonUtil.toJson(ll));
			
			
			

			Map jsonMap=new HashMap();//输出数据
			
			 List<AccountBank> ll=userService.queryAccountBank(userLogin.getId());//查询用户银行卡
			 AccountBank accounb =new AccountBank();
			 if(ll.size()>0){//
				  accounb =ll.get(0);
				  if(accounb.getStatus()!=1){
					  return ajaxJson("S00010", "请先绑卡"); 
				  }
			 }else{
				 return ajaxJson("S00010", "请先绑卡"); 
			 }
					 
			 if(StringUtils.isBlank(accounb.getPhone())){
				 accounb.setPhone(userLogin.getPhone());
				// accountBankService.update(accounb);
				 userService.updateAccountBank(accounb);
			 }
			 
			 
		
			rongbaoPaymentLog.debug("-用户没有在融宝绑卡过---查询用户卡信息 给其在融宝先签约---");
		    //String jsonResult= RongbaoUtil.RongbaoPay(userRecharge,accounb, userLogin);//调用银行
			// rongbaoPaymentLog.debug("-------调用融宝先签约返回结果="+jsonResult);
					 
					
					   
			//成功后，将订单号保存在session中 yujian
			CacheUtil.setOrderId(token,  userRecharge.getTradeNo());
		  
			rongbaoPaymentLog.debug("--调融宝默认绑卡-----jsonMap="+jsonMap);
		 
			 Map mapxx=new HashMap();//输出
			   mapxx.put("rcd", "R0001");
			   mapxx.put("rmg", "成功");
			   mapxx.put("order_no",userRecharge.getTradeNo() );
	
			   return ajax(JsonUtil.toJson(mapxx));		 
				
 
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJson("S0001", ApiConstantUtil.S0001);
		}
	}
	
	
	
	private String orderNo;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 融宝查询支付结果
	 * @rturn
	 *
	@Action(value = "/api/payResult", results = { @Result(type = "json") })
	@InputConfig(resultName = "ajaxError")
	public String queryPayResult(){
		String payOrderNo = null; //订单号
		//Object orderNo = getSession(ConstantUtil.QUERY_PAY_ORDER_NO);
		//removeSession(ConstantUtil.QUERY_PAY_ORDER_NO);
		//验证订单是否存在
		if(orderNo == null){
			msg="订单不存在";
			return ajaxJson("S00010", msg);
		}else{
			payOrderNo = orderNo;
		}
		if(StringUtils.isBlank(payOrderNo)){
			msg="参数错误";
			return ajaxJson("S00010", msg);
		}
		userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(payOrderNo);
		if(userAccountRecharge == null){
			//removeSession(ConstantUtil.ORDER_NO);
			msg = "订单不存在！";
			return ajaxJson("S00010", msg);
		}
		//验证订单与用户的一致性
		User user = getLoginUser();
		if(user==null || user.getId() == null ||!user.getId().equals(userAccountRecharge.getUserId())){
			//removeSession(ConstantUtil.ORDER_NO);
			msg = "订单跟用户不一样！";
			return ajaxJson("S00010", msg);
		}
		//验证订单状态
		if(userAccountRecharge.getStatus() != 1){//未成功状态
			if(userAccountRecharge.getStatus() == 2){//充值中
				msg = "请稍等片刻，点击返回，查看【"+userAccountRecharge.getTradeNo()+"】订单状态，如有疑问请及时联系客服。";
			}else{//充值失败
				msg = "【"+userAccountRecharge.getTradeNo()+"】订单【支付失败】，请及时联系客服。";
			}
			return ajaxJson("R00010", msg);
		}
		
		
		
		   Map mapxx=new HashMap();//输出
		   
		   mapxx.put("order_
", userAccountRecharge.getMoney());
		   mapxx.put("order_no",userAccountRecharge.getTradeNo() );
		   mapxx.put("rcd", "R0001");
		   mapxx.put("rmg", "成功");
		   return ajax(JsonUtil.toJson(mapxx));	
	}
	
	
	**/
	
	
	
	
	private String checkCode;
	
	
	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	/**
	 * 融宝支付
	 * @return
	 * qxw
	 */

	@Action(value = "/api/toPayRongbao", results = { @Result(type = "json") })
	@InputConfig(resultName = "ajaxError")
	public String toPayRongbao(){
		//String token = (String)getSession(ConstantUtil.PAY_TOKEN);
		//removeSession(ConstantUtil.PAY_TOKEN);
		rongbaoPaymentLog.debug("支付开始");
		/**
		if(token==null || !StringUtils.equals(token, pay_token)){
			msg="参数错误";
			msgUrl="/userCenter/payOrder.do";
			return "error_ftl";
		}
		**/
		User user = getLoginUser();

		List<AccountBank> ablist = accountBankService.getAccountBankList(user.getId());
		if(ablist == null || ablist.size()<1){
			//return ajaxJson("M00010", ApiConstantUtil.M00010);
			return toBind(user);
		}

		if(ablist.get(0).getStatus() !=1){
			//return ajaxJson("M00010", ApiConstantUtil.M00010);
			return toBind(user);
		}


		//yujian add
		if(orderNo == null) {
			orderNo = CacheUtil.getOrderId(token);
			 log.debug("__-_orderNo:---------------"+orderNo);
		}



		if(StringUtils.isBlank(orderNo)||StringUtils.isBlank(checkCode)){
			rongbaoPaymentLog.debug("订单号或验证码不存在");
			msg="订单号或验证码不存在";
			return ajaxJson("S00010", msg);
		}

		//验证码验证 yujian
        if (!ConfigUtil.isTestSmsCode(checkCode)) {
            String redis_sms_code = CacheUtil.getObjValue("sms:code:"+token).toString();
            if(!checkCode.equals(redis_sms_code)){
                rongbaoPaymentLog.debug("验证码验证错误");
                msg="验证码验证错误";
                return ajaxJson("S00010", msg);
            }
        } else {
            log.debug("使用了万能短信验证码:---------------"+orderNo);

        }


		User getuser = getLoginUser();
		UserAccountRecharge userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(orderNo);//根据订单号 查询有无充值记录
		if(userAccountRecharge == null){
			rongbaoPaymentLog.debug("充值记录不存在!");
			msg="充值记录不存在!";
			return ajaxJson("S00010", msg);
		}


		if(!userAccountRecharge.getUserId().equals(getuser.getId())){
			rongbaoPaymentLog.debug("充值记录异常，用户不一致");
			msg="充值记录异常";
			return ajaxJson("S00010", msg);
		}

		if("1".equals(userAccountRecharge.getStatus().toString())){
			rongbaoPaymentLog.debug("充值记录异常，["+orderNo+"]订单状态不对");
			msg="充值记录异常";
			return ajaxJson("S00010", msg);
		}

		//yujian 支付发起
		Map<String,String> pmap = new HashMap<String,String>();
		pmap.put("orderNo",orderNo);
		pmap.put("userId",getuser.getId().toString());
		pmap.put("amount",  userAccountRecharge.getMoney().toString() ) ;
		msg = com.ysd.util.HttpUtil.post(ConfigUtil.getConfigUtil().get("pay.url")+"/app/buy/product",pmap);

		if(msg == null || "".equals(msg)){
			msg="服务器无法访问,请重新尝试！";
			return ajaxJson("S00010", msg);
		}

		userAccountRecharge.setIpOperator(getRequestRemoteAddr());


		Map mapxx=new HashMap();//输出
		net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(msg);
		String code =  obj.get("code").toString();
		if("0000".equals(code)){
			rongbaoPaymentLog.debug("充值完成，["+orderNo+"]订单");
			mapxx.put("rcd", "R0001");
			mapxx.put("rmg", "充值成功");
			return ajax(JsonUtil.toJson(mapxx));
		}else{
			msg=obj.get("msg").toString();
			return ajaxJson("M0009_1", msg);
		}




	}
	
	
	
	
	
	/***
	 * 融宝
	 * 订单支付页面
	 * @return
	 */

	@Action(value = "/api/payOrder", results = { @Result(type = "json") })
	@InputConfig(resultName = "ajaxError")
	public String payOrder(){
		//验证订单是否存在 yujian 临时测试用
		if(orderNo == null) {
			orderNo = CacheUtil.getOrderId(token);
			log.debug("__-_orderNo:---------------"+orderNo);
		}
		log.debug("orderNo:---------------"+orderNo);
		
		
		if(StringUtils.isBlank(orderNo)){
			msg = "订单号不存在！";
			return ajaxJson("S00010", msg);
		}
		userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(orderNo);
		if(userAccountRecharge == null){
			msg = "订单不存在！";
			return ajaxJson("S00010", msg);
		}
		//验证订单与用户的一致性
		User user = getLoginUser();
		if(user==null || user.getId() == null ||!user.getId().equals(userAccountRecharge.getUserId())){
			
			msg = "订单与用户不匹配！";
			return ajaxJson("S00010", msg);
		}
		//校验订单状态
		if(userAccountRecharge.getStatus() != 2){
			if(userAccountRecharge.getStatus() == 1){
				msg = "订单已支付成功！";
			}else if(userAccountRecharge.getStatus() == 0){
				msg = "订单支付失败！";
			}else{
				msg = "请求错误！";
			}
			return ajaxJson("S00010", msg);
		}
		//校验银行卡
		user = userService.get(user.getId());
		List<AccountBank> blist = accountBankService.getAccountBankList(user.getId());
		if(blist==null || blist.isEmpty() || blist.size() > 1 ){
			msg = "请求错误！";
			return ajaxJson("S00010", msg);
		}
		String bankNo = blist.get(0).getAccount();
//		Map<String,Object> qMap = new HashMap<String, Object>();
//		qMap.put("keyValue", accountBank.getBankId());
//		Listing listing = listingService.findListing(qMap);
		
		Map jsonMap=new HashMap();
	    jsonMap.put("order_no", userAccountRecharge.getTradeNo());//订单号
	    jsonMap.put("money",userAccountRecharge.getMoney());
	    jsonMap.put("create_time",CommonUtil.getDate2String(userAccountRecharge.getCreateDate() ,"yyyy-MM-dd HH:mm:ss"));
	    jsonMap.put("bank_name",userAccountRecharge.getBankName());
	    jsonMap.put("bank_no", bankNo);
	    jsonMap.put("card_id", user.getCardId());
		jsonMap.put("name",user.getRealName());
		jsonMap.put("phone",blist.get(0).getPhone());
		//setSession(ConstantUtil.PAY_TOKEN,Long.toString(new Date().getTime()));
		
		jsonMap.put("rcd","R0001");
		jsonMap.put("rmg","成功");
		
		
		 //发送验证码
		String phoneCode = CommonUtil.getRandomString(6);// 6位短信验证码
		log.debug("phoneCode:"+phoneCode);
		System.out.println("----------------------------------------------------phoneCode:"+phoneCode);
		//临时保存验证码【以后替换手机发送验证码】
		//setSession("smsCode", phoneCode);
		CacheUtil.setObjValue("sms:code:"+token, phoneCode);
		// 过期时间
		CacheUtil.setExpire("sms:code:"+token, Integer.parseInt(ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_REDIS_TOKEN_EXPIRE)));
		
		YueSmsUtils.sendForRecharge(user.getPhone(), phoneCode);
		
		return ajax(JsonUtil.toJson(jsonMap));	
	
	}
	
	
	
	
	/**
	 * 融宝快 捷充 值通知处理
	 * zdl
	 * @return
	 * @throws Exception 
	 */
	@Action(value="/api/rbknotify",results={@Result(name="paynotify", location="/content/payment/payment_rb_paynotify.ftl", type="freemarker")})
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
			payNotifyMessage = "【参数解密异常】解密前："+paramStr+"，解密后："+decryData;
			rongbaoPaymentLog.debug(payNotifyMessage);
			return "paynotify";
		}
		if(StringUtils.isBlank(decryData)){
			payNotifyMessage = "【参数解密后为空】解密前："+paramStr+"，解密后："+decryData;
			rongbaoPaymentLog.debug(payNotifyMessage);
			return "paynotify";
		}
		
		//获取融宝支付的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		JSONObject jsonObject = null;
		try{	
			jsonObject= JSON.parseObject(decryData);
			rongbaoPaymentLog.debug("【获取回调参数解密后，解析为JSONObject】"+jsonObject.toJSONString());
		}catch(Exception e){
			payNotifyMessage = "【获取回调参数解密后，解析为JSONObject异常】原数据："+decryData;
			rongbaoPaymentLog.debug(payNotifyMessage);
			return "paynotify";
		}
		if(jsonObject==null){
			payNotifyMessage = "【获取回调参数解密后，解析为JSONObject为空】原数据："+decryData;
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
			payNotifyMessage = "【解密后的参数验签结果，校验失败】订单号："+order_no+"，本地验签结果："+mysign+"，接受的验签："+sign;
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
			payNotifyMessage = "【此订单号不存在】订单号："+order_no;
			rongbaoPaymentLog.debug(payNotifyMessage);	
			return "paynotify";
		}
		//校验：订单的支付方式为：融宝快捷 即80
		if(userAccountRecharge.getRechargeInterfaceId() != 80){
			payNotifyMessage = "【此订单不是融宝快捷支付】订单号："+order_no;
			rongbaoPaymentLog.debug(payNotifyMessage);	
			return "paynotify";
		}
		//校验：交易金额是否一致
		if(StringUtils.isBlank(total_fee) || new BigDecimal(total_fee).compareTo(userAccountRecharge.getMoney().movePointRight(2)) != 0){
			payNotifyMessage = "【订单金额不一致】订单号："+order_no+"，订单金额："+total_fee+"(分)，返回金额："+userAccountRecharge.getMoney().movePointRight(2).toString()+"(分)" ;
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
				return "paynotify";
			} else if (ret ==2) {
				rongbaoPaymentLog.debug("【充值失败】订单号："+order_no+",错误原因： 订单不是充值中的状态");
				return "paynotify";
			} else if(ret ==3){
				rongbaoPaymentLog.debug("【签约成功】订单号："+order_no);
				return "paynotify";
			}
			rongbaoPaymentLog.debug("【执行订单业务-结束】订单号："+order_no);
		}
		payNotifyMessage = "success";
		rongbaoPaymentLog.debug("【充值回调结束】");
		return "paynotify";
	}
	
	
	
	/**
	 * 融宝查询支付结果 json
	 * @return
	 */
	@Action(value = "/api/payResult", results = { @Result(type = "json") })
	@InputConfig(resultName = "ajaxError")
	public String queryPayResultJson(){
	
		//验证订单是否存在
		Map m=new HashMap();
		
		if(orderNo==null) {
			orderNo = CacheUtil.getOrderId(token);
		}
		
		if(orderNo == null){
			msg="请传订单号";
			m.put("rcd", "R0003");
			m.put("rmg", msg);
			return ajax(JsonUtil.toJson(m));
		}
		
		userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(orderNo);
		if(userAccountRecharge == null){
			msg = "订单不存在！";
			m.put("rcd", "R0003");
			m.put("rmg", msg);
			return ajax(JsonUtil.toJson(m));
		}
		//验证订单与用户的一致性
		User user = getLoginUser();
		if(user==null || user.getId() == null ||!user.getId().equals(userAccountRecharge.getUserId())){
			//msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
			msg = "用户跟订单不匹配！";
			m.put("rcd", "R0003");
			m.put("rmg", msg);
			return ajax(JsonUtil.toJson(m));
		}
		//验证订单状态
		
		if(userAccountRecharge.getStatus() == 1){//成功
			//拿出订单号，和金额
			String order_money = userAccountRecharge.getMoney().toString();
			String order_no =userAccountRecharge.getTradeNo();
			m.put("rcd", "R0001");
			m.put("order_money", order_money);
			m.put("order_no", order_no);
			m.put("rmg", "充值成功");
			return ajax(JsonUtil.toJson(m));
		}else if(userAccountRecharge.getStatus() == 2){//充值中
			String result =	RongbaoUtil.RongbaoPayResult(orderNo);
			if(StringUtils.isBlank(result)){
                String order_money = userAccountRecharge.getMoney().toString();
                String order_no =userAccountRecharge.getTradeNo();
                m.put("rcd", "R0001");
                m.put("order_money", order_money);
                m.put("order_no", order_no);
                m.put("rmg", "充值申请提交成功,请稍等");
//				msg="充值申请已提交,请稍等";
				//msgUrl="/account/detail.do?type=detail&recodeType=account_recharge";
//				m.put("rcd", "R0003");
//				m.put("rmg", msg);
				return ajax(JsonUtil.toJson(m));
			}
			Map map = (Map)JSON.parse(result);  //json转map 融宝返回参数
			if("completed".equals(map.get("status"))){//支付成功
				String order_money = map.get("total_fee").toString();
				String order_no = map.get("order_no").toString();
				
				userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(orderNo);
				if(userAccountRecharge.getStatus() == 1){//成功
					m.put("rcd", "R0001");
					m.put("order_money", order_money);
					m.put("order_no", order_no);
					m.put("rmg", "充值成功");
					return ajax(JsonUtil.toJson(m));
					//return SUCCESS;
				}else{
					msg= "交易处理中....";
					msgUrl="/account/detail.do?type=detail&recodeType=account_recharge";
					//return "error_ftl";
					m.put("rcd", "R0002");
					m.put("rmg", msg);
					return ajax(JsonUtil.toJson(m));
					
				}
	
			}else if("processing".equals(map.get("status"))){//等待中
				msg= "交易处理中....";
				//msgUrl="/account/detail.do?type=detail&recodeType=account_recharge";
				m.put("rcd", "R0002");
				m.put("rmg", msg);
				return ajax(JsonUtil.toJson(m));
			}else{
				msg = "【"+userAccountRecharge.getTradeNo()+"】订单【支付失败】，如有疑问请及时联系客服。";
				//msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";	
				
				m.put("rcd", "R0003");
				m.put("rmg", msg);
				return ajax(JsonUtil.toJson(m));
			}
			
		}else{//充值失败
			msg = "【"+userAccountRecharge.getTradeNo()+"】订单【支付失败】，如有疑问请及时联系客服。";
			//msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
			m.put("rcd", "R0003");
			m.put("rmg", msg);
			return ajax(JsonUtil.toJson(m));
		
		}
	
	
		
	}
	
	
	
	
	
	

	

}
