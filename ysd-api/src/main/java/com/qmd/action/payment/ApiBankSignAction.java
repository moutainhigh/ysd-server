package com.qmd.action.payment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.bank.CashBank;
import com.qmd.bean.payment.Lianlian;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.mode.util.Listing;
import com.qmd.payment.BasePaymentProduct;
import com.qmd.service.payment.PaymentService;
import com.qmd.service.user.*;
import com.qmd.service.util.ListingService;
import com.qmd.util.*;
import com.qmd.util.md5.PWDUtil;
import com.qmd.util.redis.CacheUtil;
import com.qmd.util.rongbaoConfig.ReapalConfig;
import com.qmd.util.rongbaoUtils.Decipher;
import com.qmd.util.rongbaoUtils.Md5Utils;
import com.qmd.util.rongbaoUtils.RongbaoUtil;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 签约
 * 
 */
@InterceptorRefs({ @InterceptorRef(value = "apiUserInterceptor"), @InterceptorRef(value = "qmdDefaultStack") })
@Service("apiBankSignAction")
public class ApiBankSignAction extends ApiBaseAction {

	Logger log = Logger.getLogger(ApiBankSignAction.class);
	private Logger logPayment = Logger.getLogger("userPaymentLog");
	private Logger rongbaoPaymentLog = Logger.getLogger("rongbaoPaymentLog");
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
	private String branch;
	private String money;//充值金额
	private String phone;//银行预留手机号

	private String checkCode;//银行验证码

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

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
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
	



	public String getCardNo() {
		return cardNo;
	}





	public String getPhone() {
		return phone;
	}





	public void setPhone(String phone) {
		this.phone = phone;
	}





	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}





	public String getBranch() {
		return branch;
	}


	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}





	public void setBranch(String branch) {
		this.branch = branch;
	}



	/**
	 * 银行卡签约 提交  融宝
	 *
	 * @return
	 */
	@Action(value="/api/bankSignSaveHnew")
	@InputConfig(resultName = "error_no_back_ftl,success_ftl")
	public String bankSignSaveHnew() {
		try {
			rongbaoPaymentLog.info("开始签约");
			//验证用户信息
			User userLogin = getLoginUser();
			User user = userService.getById(userLogin.getId(), new User());
			if (user == null) {
				return ajaxJson("E0002", ApiConstantUtil.E0002);
			}
			List<AccountBank> userBankList = getAccountBankList();
			if(userBankList!=null && userBankList.size() > 1){
				return ajaxJson("S0002", "你绑定了"+userBankList.size()+"个银行账号(一个用户只能绑定一个银行卡),请联系客服处理！");
			}
			if(userBankList!=null && userBankList.size() == 1 && userBankList.get(0).getStatus() == 1){
				msgUrl="toBankInput.do";
				return ajaxJson("S0002", "您已绑卡成功");
			}
			//姓名
			if(StringUtils.isEmpty(realName)){
				return ajaxJson("M0008_18", ApiConstantUtil.M0008_18);
			}
			//身份证号
			if(StringUtils.isEmpty(idNo)){
				return ajaxJson("M0008_19", ApiConstantUtil.M0008_19);
			}else if(StringUtils.equals("1", IDCard.IDCardValidate(idNo))){
				return ajaxJson("M0008_20", ApiConstantUtil.M0008_20);
			}
			Map<String,Object> qMap = new HashMap<String,Object>();
			qMap.put("cardId", idNo);
			qMap.put("realStatus", 1);
			User qUser = userService.getUser(qMap);
			if(qUser != null && qUser.getId().compareTo(userLogin.getId())!=0){
				return ajaxJson("M0009_1", ApiConstantUtil.M0009_1);
			}
			//银行卡
			if(StringUtils.isBlank(cardNo)){
				return ajaxJson("S0002", "银行卡号为空");
			}
			cardNo = cardNo.replaceAll("\\s*", "");
			if(NumberUtil.isNotNumber(cardNo)){
				return ajaxJson("S0002", "银行账号含非法字符!");
			}


			//开户行
			if(StringUtils.isEmpty(bankId)){
				return ajaxJson("S0002", "请选择银行类型");
			}
			Map<String,Object> queryMap = new HashMap<String,Object>();
			queryMap.put("keyValue", bankId);
			Listing listing = listingService.findListing(queryMap);
			if(listing == null){
				return ajaxJson("S0002", "不支持此银行");
			}

			//预留手机号
			if (StringUtils.isBlank(phone)) {
				return ajaxJson("S0002", "手机号不能为空");
			}
			if(NumberUtil.isNotNumber(phone)){
				return ajaxJson("S0002", "手机号含非法字符!");
			}
			Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
			Matcher m = p.matcher(phone);
			if (!m.matches()) {
				return ajaxJson("S0002", "手机号格式不正确!");
			}
			//交易密码
			if(StringUtils.isBlank(safepwd)){
				return ajaxJson("S0004", ApiConstantUtil.S0004);
			}
//			if (!safepwd.matches(".*[a-zA-Z]+.*$")) {
//				return ajaxJson("S0002", "交易密码必须包含字母!");
//			}
			/*if (safepwd.length() < 6 || safepwd.length() > 16) {
				return ajaxJson("S0002", "交易密码长度必须在6-16个字符之间!");
			}*/


			String pay_url = ConfigUtil.getConfigUtil().get("pay.url");
			log.info(pay_url);
            log.debug("*************bankId:" + bankId);
			Map<String,String> pmap = new HashMap<String,String>();
			pmap.put("phone",userLogin.getPhone());
			pmap.put("account",cardNo);
			pmap.put("realName",realName);
			pmap.put("cardId",idNo);
			pmap.put("bankId",bankId);
			pmap.put("safePwd",PWDUtil.encode(safepwd,user.getRandomNum()));

            log.debug("*************pmap:" + pmap);
			msg = com.ysd.util.HttpUtil.post(pay_url+"/app/binding",pmap);
			if(msg == null || "".equals(msg)){
				return ajaxJson("M0009_1", "服务器异常,请重新尝试！");
			}

			net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(msg);
			String code =  obj.get("code").toString();
			if("0000".equals(code)){

				//去验证手机号码
				Map mapxx=new HashMap();//输出
				mapxx.put("rcd", "R0001");
				mapxx.put("rmg", "成功");
				return ajax(JsonUtil.toJson(mapxx));
			}else{
				return ajaxJson("M0009_1", obj.get("msg").toString());
			}


		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJson("S0001", ApiConstantUtil.S0001);
		}
	}



	/**
	 * 实名认证重新发送验证码
	 * @return
	 */
	@Action(value = "/api/bankSignPhoneCodeRepeat", results = { @Result(type = "json") })
	@InputConfig(resultName = "ajaxError")
	public String bankSignPhoneCodeRepeat(){

		User loginUser = getLoginUser();
		User user = userService.get(loginUser.getId());

		String pay_url = ConfigUtil.getConfigUtil().get("pay.url");
		log.info(pay_url);
		Map<String,String> pmap = new HashMap<String,String>();
		pmap.put("id",user.getId().toString());
		pmap.put("no",user.getBankCustNo());

		msg = com.ysd.util.HttpUtil.post(pay_url+"/app/bindingCode",pmap);
		if(msg == null || "".equals(msg)){
			return ajaxJson("0002", "无法发送验证码！");
		}
		net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(msg);
		String code =  obj.get("code").toString();
		if("0000".equals(code)){
			//去验证手机号码
			return ajaxJson("R0001", "验证码发送成功");
		}else{
			return ajaxJson("M0009_1", obj.get("msg").toString());
		}

	}

	/**
	 * 实名认证第二步提交
	 * @return
	 */
	@Action(value="/api/bankSignPhoneCode",results={
			@Result(name="success", location="/userCenter/toBank.do", type="redirect")
	})
	public String bankSignPhoneCode(){

		if(org.codehaus.plexus.util.StringUtils.isBlank(checkCode)){

			return ajaxJson("M0009_1", "请输入验证码");
		}
		User loginUser = getLoginUser();
		User user = userService.get(loginUser.getId());

		String pay_url = ConfigUtil.getConfigUtil().get("pay.url");
		log.info(pay_url);
		Map<String,String> pmap = new HashMap<String,String>();
		pmap.put("code",checkCode);
		pmap.put("id",user.getId().toString());
		pmap.put("no",user.getBankCustNo());

		msg = com.ysd.util.HttpUtil.post(pay_url+"/app/binding2",pmap);
		if(msg == null || "".equals(msg)){

			return ajaxJson("M0009_1", "服务器异常,请重新尝试！");
		}

		net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(msg);
		String code =  obj.get("code").toString();
		if("0000".equals(code)){
			//去验证手机号码
			return ajaxJson("R0001", "绑定成功");
		}else{
			msg=obj.get("msg").toString();
			return ajaxJson("M0009_1", msg);
		}
	}





	/**
	 * 是首次用招商绑卡
	 * @param bandId 银行卡简称
	 * @param userId 用户id
	 * @param bankCode 银行卡号
	 * @return 
	 */
	private boolean isCMBFirstBindToRB(String bandId,Integer userId, String bankCode){
		//非空校验
		if(userId == null || StringUtils.isBlank(bankCode) || StringUtils.isBlank(bandId)){
			return false;
		}
		// 银行卡号长度不得小于6位
		if(bankCode.length() < 6){
			return false;
		}
		//必须是招商卡
		if(!StringUtils.equalsIgnoreCase(bandId, "CMB")){
			return false;
		}
		
		//查找用户是否已绑过输入的这张招商卡
		String card_last = bankCode.substring(bankCode.length() - 4, bankCode.length()); //银行卡后4位数字
		String card_top = bankCode.substring(0, 6); // 银行卡前6位数字
		
		String rest = RongbaoUtil.RongbaoToSelectIsbindCard(userId);//查询用户已绑银行卡列表
		
		if(StringUtils.isBlank(rest)){
			return true;
		}
		
		JSONObject jsonObj = null;
		try{
			jsonObj = JSONObject.parseObject(rest);
		}catch(Exception e){
			
		}
		if(jsonObj == null){
			return true;
		}
		
		JSONArray array = null;
		try{
			array = jsonObj.getJSONArray("bind_card_list");
		}catch(Exception e){
			
		}
		if(array == null || array.isEmpty()){
			return true;
		}
		
		for(int i=0; i < array.size() ;i++){
			JSONObject json = array.getJSONObject(i);
			if(!StringUtils.equalsIgnoreCase(json.getString("bank_code"), "CMB")){
				break;
			}
			if(!StringUtils.equals(json.getString("card_last"), card_last)){
				break;
			}
			if(!StringUtils.equals(json.getString("card_top"), card_top)){
				break;
			}
			return false;//匹配到已有此卡
		}
		
		return true;
	}
	

	/**
	 * 检测银行卡类别是否一致
	 * <p>解决：“融宝”和“宝付”银行卡简称不一致问题
	 * @param xBank 选择银行卡类别
	 * @param sBank 融宝返回的银行类别
	 * @return
	 */
	private boolean checkBankIsSame(String xBank,String sBank){
		if(StringUtils.isBlank(xBank)||StringUtils.isBlank(sBank)){
			return false;
		}
		//平安银行: 宝付简称，融宝简称
		if((StringUtils.equals(xBank, "PAB") && StringUtils.equals(sBank, "PAYH"))){
			return true;
		}
		//交通银行：宝付简称，融宝简称
		if(StringUtils.equals(xBank, "BCOM") && StringUtils.equals(sBank, "BOCM")){
			return true;
		}
		//广发银行：宝付简称，融宝简称
		if(StringUtils.equals(xBank, "GDB") && StringUtils.equals(sBank, "CGB")){
			return true;
		}
		if(StringUtils.equalsIgnoreCase(xBank, sBank)){
			return true;
		}
		return false;
	}
	
	/**
	 * 融宝快，卡密鉴权（招商绑卡使用，调完签约接口后，需再调此接口）
	 * zdl
	 * @return
	 * @throws Exception 
	 */
	@Action(value="/api/verifycmb",results={@Result(name="success", location="/content/payment/verifycmb.ftl", type="freemarker")})
	public String rbknotify() throws Exception{
		rongbaoPaymentLog.info("招商卡密鉴权-开始");
		try{
			User loginUser = getLoginUser();
			if(loginUser == null||loginUser.getId() == null){
				msg="用户未登录";
				return "error_ftl";
			}
			String bind_id = getRequest().getParameter("rmg");
			if(StringUtils.isBlank(bind_id)){
				msg="参数错误";
				return "error_ftl";
			}
			String order_no = getRequest().getParameter("order_no");
			if(StringUtils.isBlank(order_no)){
				msg="参数错误";
				return "error_ftl";
			}
			//校验：订单号是否存在
			userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(order_no);
			rongbaoPaymentLog.debug("【系统订单信息】"+JSONObject.toJSONString(userAccountRecharge));
			if(userAccountRecharge == null){
				rongbaoPaymentLog.debug("【此订单号不存在】订单号："+order_no);	
				msg="【此订单号不存在】订单号："+order_no;
				return "error_ftl";
			}
			//校验：订单的支付方式为：融宝快捷 即80
			if(userAccountRecharge.getRechargeInterfaceId() != 80){
				rongbaoPaymentLog.debug("【此订单不是融宝快捷支付】订单号："+order_no);	
				msg="【此订单不是融宝快捷支付】订单号："+order_no;
				return "error_ftl";
			}
			//校验：重复回调（单子已经支付成功）
			if(userAccountRecharge.getStatus().equals(1)){//充值成功
				rongbaoPaymentLog.debug("【此订单已支付完成】订单号："+order_no);	
				msg="【此订单已支付完成】订单号："+order_no;
				return "error_ftl";
			}
			//校验：非充值中状态，不可修改订单状态
			if(!userAccountRecharge.getStatus().equals(2)){//充值中
				rongbaoPaymentLog.debug("【此订单状态已不是充值中】订单号："+order_no);	
				msg="【此订单状态已不是充值中】订单号："+order_no;
				return "error_ftl";
			}
			
			//准备:卡密鉴权参数
			msg =	RongbaoUtil.buildBank(order_no, bind_id, loginUser.getId().toString(), "mobile");
	    	if(StringUtils.isBlank(msg)){
	    		rongbaoPaymentLog.debug("卡密鉴权异常");	
				msg="卡密鉴权异常";
				return "error_ftl";
	    	}
//	        paymentUrl = ReapalConfig.certify_url; 
		}catch(Exception e){
			rongbaoPaymentLog.debug("卡密鉴权异常");	
			msg="卡密鉴权异常";
			return "error_ftl";
		}
		rongbaoPaymentLog.info("招商卡密鉴权-结束");
		return "success";
	}
	
	
	/**
	 * 融宝支付卡密鉴权前端通知（即，点击返回商户请求接口）
	 * @return
	 * qxw
	 */
	@Action(value="/api/rbkmreturn",results={
			@Result(name="success", location="/content/payment/verifycmb_result.ftl", type="freemarker")})
	public String rbkmreturn(){
		//默认失败
		msg="km_fail";
		log.debug("招商卡密鉴权-前端通知-开始");
		HashMap<String, String> param = new HashMap<String, String>();
		for(Object s:getRequest().getParameterMap().keySet()){
			param.put(s.toString(), getRequest().getParameter(s.toString()));
		}
		String paramStr = JSONObject.toJSONString(param);
		log.debug("【获取通知参数】"+paramStr);
		
		if(StringUtils.isBlank(paramStr)){
			return SUCCESS;
		}
		//解析密文数据
		String decryData = null;
		try {
			decryData = Decipher.decryptData(paramStr);
			log.debug("【获取通知参数解密后】"+decryData);
		} catch (Exception e) {
			log.debug("【参数解密异常】解密前："+paramStr+"，解密后："+decryData);
			return SUCCESS;
		}
		if(StringUtils.isBlank(decryData)){
			log.debug("【参数解密后为空】解密前："+paramStr+"，解密后："+decryData);
			return SUCCESS;
		}
		//获取融宝支付的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		JSONObject jsonObject = null;
		try{	
			jsonObject= JSON.parseObject(decryData);
			log.debug("【获取回调参数解密后，解析为JSONObject】"+jsonObject.toJSONString());
		}catch(Exception e){
			log.debug("【获取回调参数解密后，解析为JSONObject异常】原数据："+decryData);
			return SUCCESS;
		}
		if(jsonObject==null){
			log.debug("【获取回调参数解密后，解析为JSONObject为空】原数据："+decryData);
			return SUCCESS;
		}
		
		String merchant_id = param.get("merchant_id");
		
		String bank_card_type = jsonObject.getString("bank_card_type");
		String bank_code = jsonObject.getString("bank_code");
		String bank_name = jsonObject.getString("bank_name");
		String bind_id = jsonObject.getString("bind_id");
		String card_last = jsonObject.getString("card_last");
		String member_id = jsonObject.getString("member_id");
		String order_no = jsonObject.getString("order_no");
		String phone = jsonObject.getString("phone");
		String result_code = jsonObject.getString("result_code");
		String result_msg = jsonObject.getString("result_msg");
		String total_fee = jsonObject.getString("total_fee");
		String trade_no = jsonObject.getString("trade_no");
		String sign = jsonObject.getString("sign");
		
		
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("merchant_id", merchant_id);
		map2.put("bank_card_type", bank_card_type);
		map2.put("bank_code", bank_code);
		map2.put("bank_name", bank_name);
		map2.put("bind_id", bind_id);
		map2.put("card_last", card_last);
		map2.put("member_id", member_id);
		map2.put("order_no", order_no);
		map2.put("phone", phone);
		map2.put("result_code", result_code);
		map2.put("result_msg", result_msg);
		map2.put("total_fee", total_fee);
		map2.put("trade_no", trade_no);
		
		//将返回的参数进行验签
		String mysign = Md5Utils.BuildMysign(map2, ReapalConfig.key);
		log.debug("【解密后的参数验签结果】订单号："+order_no+"，需要签名的参数："+JSONObject.toJSONString(map2)+"，密钥："+ReapalConfig.key+"，结果："+mysign);
		
		//校验：判读该返回结果是否由融宝发出
		if(mysign == null || !StringUtils.equals(mysign, sign)){
			log.debug("【解密后的参数验签结果，校验失败】订单号："+order_no+"，本地验签结果："+mysign+"，接受的验签："+sign);	
			return SUCCESS;
		}
		//校验：订单号是否为空，商户号一致
		if(StringUtils.isBlank(order_no) || merchant_id==null || !StringUtils.equals(merchant_id, ReapalConfig.merchant_id)){
			log.debug("【订单号是否为空，或商户号一致】订单号："+order_no+"，商户号："+merchant_id);	
			return SUCCESS;
		}
		if(!StringUtils.equals("0000", result_code)){
			log.debug("【卡密鉴权失败】："+jsonObject.toString());
			msg="km_success="+order_no;
			return SUCCESS;
		}
		//成功
		msg="km_success="+order_no;
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
	@Action(value="/api/rbkmnotify",results={@Result(name="paynotify", location="/content/payment/payment_rb_paynotify.ftl", type="freemarker")})
	public String rbkmnotify() throws Exception{
		log.info("卡密鉴权异步通知-后台");
		rongbaoPaymentLog.debug("【卡密鉴权异步通知-开始】");
		payNotifyMessage = "success";//返回融宝的信息，默认是成功
		
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
	
}
