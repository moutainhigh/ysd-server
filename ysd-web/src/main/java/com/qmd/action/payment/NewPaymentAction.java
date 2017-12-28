package com.qmd.action.payment;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.action.user.UserCenterAction;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.*;
import com.qmd.mode.util.Listing;
import com.qmd.payment.BasePaymentProduct;
import com.qmd.service.payment.PayService;
import com.qmd.service.payment.PaymentService;
import com.qmd.service.user.AccountBankService;
import com.qmd.service.user.AccountCashService;
import com.qmd.service.user.UserService;
import com.qmd.service.util.ListingService;
import com.qmd.util.*;
import com.qmd.util.md5.PWDUtil;
import com.ysd.biz.TemplateConfig;
import com.ysd.biz.YueSmsUtils;
import com.ysd.ipyy.IPYYSMSResult;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 支付相关
 * @author yujian
 */
@Service("newPaymentAction")
@InterceptorRefs({
	@InterceptorRef(value = "userVerifyInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
public class NewPaymentAction extends BaseAction{

	private static final long serialVersionUID = -3829710486103154759L;
	private static Logger log = Logger.getLogger(NewPaymentAction.class);
	private static Logger logPayment = Logger.getLogger("userPaymentLog");

	
	@Resource
	private UserService userService;
	@Resource
	private ListingService listingService;
	@Resource
	private PaymentService paymentService;
	@Resource
	private AccountBankService accountBankService;
	
	@Resource
	private PayService payService;
	@Resource
	private AccountCashService accountCashService;
	
	
	/**
	 * 跳转到充值页面
	 * @return
	 */
	@Action(value="/payment/rechargeTo",results={
			@Result(name="success", location="/content/payment/payment_recharge.ftl", type="freemarker"),
			 @Result(name="binding", location="/userCenter/toBank.do", type="redirect")})
	public String rechargeTo(){
		User loginuser = getLoginUser();

		User user = userService.get(loginuser.getId());
		if(1!=user.getRealStatus().intValue() ){
			return "binding";
		}
		
		//充值金额
		 if(StringUtils.isBlank(money)){
			 money = "";
		 }
		 
		 //可用余额
		 ableMoney = getAbleMoney();
		 
		 //为绑卡就充值的处理
		 List<AccountBank> accountBankList=userService.queryAccountBank(loginuser.getId());
		 if(accountBankList==null || accountBankList.size() <=0){
			
			return "binding";
		 }else if(accountBankList.get(0).getStatus() == 0){
				 return "binding";
		 }
		 
		return "success";
	}
	
	/** 
	 * 充值提交
	 * @return
	 */
	@Action(value="/payment/submitRecharge",results={@Result(name="onLine", location="/content/payment/payment_submit.ftl", type="freemarker"),
											 /*@Result(name="success", location="/userCenter/payOrder.do", type="redirect")}*/
											@Result(name="success", location="/content/payment/payment_tl_recharge.ftl", type="freemarker")}
											 )
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String submitRecharge(){
		logPayment.info("开始充值");
		
		//验证图形验证码
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		msgUrl="/payment/rechargeTo.do";
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		if (mycode == null || !mycode.equalsIgnoreCase(random)) {
			msg = "验证码输入错误!";
			return "error_ftl";
		}
		User userLogin = getLoginUser();

		logPayment.info("【充值开始】"+userLogin.getUsername()+",["+payment+"]");
		
		UserAccountRecharge userRecharge = new UserAccountRecharge();
		Integer rechargeInterfaceId =80;//接口支付
		
		BigDecimal paymentFee = null;// 支付手续费
		BigDecimal amountPayable = null;// 应付金额【不包含手续费】
		
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
		
		userRecharge.setRealName(user.getRealName());
		userRecharge.setCardId(user.getCardId());
		userRecharge.setRegister( CommonUtil.getDate2String(user.getCreateDate(),"yyyyMMddHHmmss"));
		
		userRecharge.setBankNo(accountBank.getAccount().replaceAll("\\s*", ""));
		userRecharge.setBackUrl(ConstantUtil.WEB_SITE+"/userCenter/toBankInput.do");
		userRecharge.setMobile(accountBank.getPhone());
		userRecharge.setPayCode(accountBank.getBankId());
		
		 List<AccountBank> ll=userService.queryAccountBank(user.getId());//查询用户银行卡
		 if(ll.size()>0){//
			 		 
		 }else{
			 msg = "请先绑卡";
			 msg = "/userCenter/toBank.do";
			 return "error_ftl";	 
		 }
		 setSession(ConstantUtil.ORDER_NO, userRecharge.getTradeNo());



		 ///充值支付
		Map<String,String> pmap = new HashMap<String,String>();
		pmap.put("orderNo", userRecharge.getTradeNo());
		pmap.put("userId",userLogin.getId().toString());
		pmap.put("amount",  userRecharge.getMoney().toString() ) ;
		msg = com.ysd.util.HttpUtil.post(ConfigUtil.getConfigUtil().get("pay.url")+"/rechargeWg",pmap);

		if(msg == null || "".equals(msg)){
			msg="["+userRecharge.getTradeNo()+"]服务器无法访问,请重新尝试！";
			msgUrl="/payment/rechargeTo.do";
			return "error_ftl";
		}
		LOG.debug(msg);

		return "success";
	}
	
	
	/***
	 * 订单支付页面
	 * @return
	 */
	@Action(value="/userCenter/payOrder",results={
			@Result(name="success", location="/content/rongbao/pay_rongbao.ftl", type="freemarker")})
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String payOrder(){
		//验证订单是否存在
		String orderNo = (String)getSession(ConstantUtil.ORDER_NO);
		if(StringUtils.isBlank(orderNo)){
			msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
			removeSession(ConstantUtil.ORDER_NO);
			msg = "1.请求错误！";
			return "error_ftl";
		}
		userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(orderNo);
		if(userAccountRecharge == null){
			removeSession(ConstantUtil.ORDER_NO);
			msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
			msg = "2.请求错误！";
			return "error_ftl";
		}
		//验证订单与用户的一致性
		User user = getLoginUser();
		if(user==null || user.getId() == null ||!user.getId().equals(userAccountRecharge.getUserId())){
			removeSession(ConstantUtil.ORDER_NO);
			msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
			msg = "3.请求错误！";
			return "error_ftl";
		}
		//校验订单状态
		if(userAccountRecharge.getStatus() != 2){
			if(userAccountRecharge.getStatus() == 1){
				msg = "订单已支付成功！";
			}else if(userAccountRecharge.getStatus() == 0){
				msg = "订单支付失败！";
			}else{
				msg = "4.请求错误！";
			}
			removeSession(ConstantUtil.ORDER_NO);
			msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
			return "error_ftl";
		}
		//校验银行卡
		user = userService.get(user.getId());
		List<AccountBank> blist = accountBankService.getAccountBankList(user.getId());
		if(blist==null || blist.isEmpty() || blist.size() > 1 ){
			removeSession(ConstantUtil.ORDER_NO);
			msgUrl = "/account/detail.do?type=detail&recodeType=account_recharge";
			msg = "5.请求错误！";
			return "error_ftl";
		}
		String bankNo = blist.get(0).getAccount();
		
		//发送验证码
		String phoneCode = CommonUtil.getRandomString(6);// 6位短信验证码
		log.debug("phoneCode:"+phoneCode);
		System.out.println("----------------------------------------------------phoneCode:"+phoneCode);
		//临时保存验证码【以后替换手机发送验证码】
		setSession("smsCode", phoneCode);
		YueSmsUtils.sendForRecharge(user.getPhone(), phoneCode);
				
		jsonMap=new HashMap<String,Object>();
	    jsonMap.put("order_no", userAccountRecharge.getTradeNo());//订单号
	    jsonMap.put("money",userAccountRecharge.getMoney());
	    jsonMap.put("create_time",CommonUtil.getDate2String(userAccountRecharge.getCreateDate() ,"yyyy-MM-dd HH:mm:ss"));
	    jsonMap.put("bank_name",userAccountRecharge.getBankName());
	    jsonMap.put("bank_no", bankNo);
	    jsonMap.put("card_id", user.getCardId());
		jsonMap.put("name",user.getRealName());
		jsonMap.put("phone",blist.get(0).getPhone());
		setSession(ConstantUtil.PAY_TOKEN,Long.toString(new Date().getTime()));
		return "success";
	}
	
	/**
	 * 充值支付
	 * @return
	 * qxw
	 */
	@Action(value="/payment/toPayOrder",results={
			@Result(name="success", location="/content/payment/payment_result.ftl", type="freemarker"),
			@Result(name="success2", location="/userCenter/payResult.do", type="redirect")})
	public String toPayOrder(){
		String token = (String)getSession(ConstantUtil.PAY_TOKEN); 
		removeSession(ConstantUtil.PAY_TOKEN);
		if(token==null || !StringUtils.equals(token, pay_token)){
			msg="参数错误";
			msgUrl="/userCenter/payOrder.do";
			return "error_ftl";
		}
		if(StringUtils.isBlank(orderNo)){
			msg="订单号为空";
			msgUrl="/userCenter/payOrder.do";
			return "error_ftl";
		}
		if(StringUtils.isBlank(checkCode)){
			msg="手机验证码为空";
			msgUrl="/userCenter/payOrder.do";
			return "error_ftl";
		}
		
		String smsCode = (String)getSession("smsCode"); 
		removeSession("smsCode");
		if(!smsCode.equals(checkCode)){
			msg="手机验证码错误";
			msgUrl="/userCenter/payOrder.do";
			return "error_ftl";
		}
		
		User getuser = getLoginUser();
		userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(orderNo);//根据订单号 查询有无充值记录
		if(userAccountRecharge == null){
			msg="["+orderNo+"]订单不存在!";
			msgUrl="/userCenter/payOrder.do";
			return "error_ftl";
		}
		
		if(!userAccountRecharge.getUserId().equals(getuser.getId())){
			msg="["+orderNo+"]订单用户信息不一致!";
			msgUrl="/userCenter/payOrder.do";
			return "error_ftl";
		}
		
		if("1".equals(userAccountRecharge.getStatus().toString())){
			msg="["+orderNo+"]订单已支付成功，请勿重复操作！";
			msgUrl="/userCenter/payOrder.do";
			return "error_ftl";
		}

		Map<String,String> pmap = new HashMap<String,String>();
		pmap.put("orderNo",orderNo);
		pmap.put("userId",getuser.getId().toString());
		pmap.put("amount",  userAccountRecharge.getMoney().toString() ) ;
		msg = com.ysd.util.HttpUtil.post(ConfigUtil.getConfigUtil().get("pay.url")+"/recharge",pmap);

		if(msg == null || "".equals(msg)){
			msg="["+orderNo+"]存管服务器无法访问,请重新尝试！";
			msgUrl="/userCenter/payOrder.do";
			return "error_ftl";

		}

		JSONObject obj = JSONObject.fromObject(msg);


		String code =  obj.get("code").toString();
		if("0000".equals(code)){

			//充值成功
			return SUCCESS;
		}else{
			msg=obj.get("msg").toString();
			msgUrl="/userCenter/payOrder.do";
			return "error_ftl";
		}
	}



	/**
	 * 跳转到添加/编辑银行账户页面
	 * @return
	 */
	@Action(value="/userCenter/toBankInput",
			results={@Result(name="success", location="/content/user/bank_input.ftl", type="freemarker"),
					@Result(name="phone_code", location="/userCenter/bankSignPhoneCodeOri.do", type="redirect"),
					@Result(name="msg", location="/content/message_page.ftl", type="freemarker")})
	public String toBankInput(){
		User loginuser = getLoginUser();

		User user = userService.get(loginuser.getId());
		if(2==user.getRealStatus().intValue() && !StringUtils.isBlank(user.getBankCustNo())){

			return "phone_code";
		}


		if(getAccountBankList().size() >0){
			accountBank = getAccountBankList().get(0);
			if(accountBank.getStatus().compareTo(1) ==0){
				msg = "签约成功！";
				msgUrl ="toBank.do";
				return "msg";
			}
		}



		setSession(ConstantUtil.BIND_TOKEN,String.valueOf(new Date().getTime()));
		return SUCCESS;
	}
	
	/**
	 * 银行卡签约 提交 融宝
	 * qxw
	 * @return
	*/
	@Action(value="/userCenter/bankSignSaveHnew",results={
			@Result(name="success", location="/userCenter/bankSignPhoneCodeOri.do", type="redirect"),
			@Result(name="toBank", location="/userCenter/toBank.do", type="redirect")})
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String rechargeSaveHnew() {





		//防止重复提交
		String bind_token_session = (String)getSession(ConstantUtil.BIND_TOKEN);
		removeSession(ConstantUtil.BIND_TOKEN);
		if(bind_token_session == null||!StringUtils.equals(bind_token, bind_token_session)){
			msg="请重新输入信息";
			msgUrl="toBankInput.do";
			return "error_ftl";
		}

		try {
			log.info("开始签约");
			//验证：用户信息
			User userLogin = getLoginUser();
			User userDb = userService.getById(userLogin.getId(), new User());
			if (userDb == null) {
				msg="用户不存在";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}


			if(2==userDb.getRealStatus().intValue() && !StringUtils.isBlank(userDb.getBankCustNo())){

				return "phone_code";
			}else if( 1==userDb.getRealStatus().intValue() ){
				return "toBank";
			}

			List<AccountBank> userBankList = getAccountBankList();
			if(userBankList!=null && userBankList.size() > 1){
				msg="你绑定了"+userBankList.size()+"个银行账号(一个用户只能绑定一个银行卡),请联系客服处理！";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}

			if(userBankList!=null && userBankList.size() == 1 && userBankList.get(0).getStatus() == 1){
				msg="您已绑卡成功";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			if(userBankList != null && accountBank.getId()!=null && !accountBank.getId().equals(userBankList.get(0).getId())){
				msg="不可修改此银行卡";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			//姓名
			if(StringUtils.isEmpty(user.getRealName())){
				msg="请输入真实姓名!";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			//身份证号
			if(StringUtils.isEmpty(user.getCardId())){
				msg="请输入身份证号!";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}else if(StringUtils.equals("1", IDCard.IDCardValidate(user.getCardId()))){
				msg="请输入有效身份证号!";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			Map<String,Object> qMap = new HashMap<String,Object>();
			qMap.put("cardId", user.getCardId());
			qMap.put("realStatus", 1);
			User qUser = userService.getUser(qMap);
			if(qUser != null && qUser.getId().compareTo(userLogin.getId())!=0){
				msg="该身份证已被认证!";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			//银行卡号
			if(StringUtils.isBlank(accountBank.getAccount())){
				msg="请输入银行账号!";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			accountBank.setAccount(accountBank.getAccount().replaceAll("\\s*", ""));
			if(NumberUtil.isNotNumber(accountBank.getAccount())){
				msg="银行账号含非法字符!";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			//开户行
			if(StringUtils.isBlank(accountBank.getBankId())){
				msg="请选择开户银行!";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			Map<String,Object> queryMap = new HashMap<String,Object>();
			queryMap.put("keyValue", accountBank.getBankId());
			Listing listing = listingService.findListing(queryMap);
			if(listing == null){
				msg="不支持此银行";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}

			//预留手机号
			if(StringUtils.isBlank(accountBank.getPhone())){
				msg="请输入预留手机号!";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			if(NumberUtil.isNotNumber(accountBank.getPhone())){
				msg="预留手机号含非法字符!";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
			Matcher m = p.matcher(accountBank.getPhone());
			if (!m.matches()) {
				msg = "预留手机号格式不正确!";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
			//交易密码
			if(StringUtils.isBlank(safepwd)){
				msg="请设置交易密码!";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}
//				if (!safepwd.matches(".*[a-zA-Z]+.*$")) {
//					msg = "交易密码必须包含字母!";
//					msgUrl="toBankInput.do";
//					return "error_ftl";
//				}
			if (safepwd.length() < 6 || safepwd.length() > 16) {
				msg = "交易密码长度必须在6-16个字符之间!";
				msgUrl="toBankInput.do";
				return "error_ftl";
			}

			String pay_url = ConfigUtil.getConfigUtil().get("pay.url");
			log.info(pay_url);
			Map<String,String> pmap = new HashMap<String,String>();
			pmap.put("phone",userLogin.getPhone());
			pmap.put("account",accountBank.getAccount());
			pmap.put("realName",user.getRealName());
			pmap.put("cardId",user.getCardId());
			pmap.put("bankId",accountBank.getBankId());
			pmap.put("safePwd",PWDUtil.encode(safepwd,user.getRandomNum()));

			msg = com.ysd.util.HttpUtil.post(pay_url+"/binding",pmap);
			if(msg == null || "".equals(msg)){
				msg="服务器异常,请重新尝试！";
				msgUrl="/userCenter/toBankInput.do";
				return "error_ftl";
			}

			JSONObject obj = JSONObject.fromObject(msg);
			String code =  obj.get("code").toString();
			if("0000".equals(code)){

				//去验证手机号码
				return SUCCESS;
			}else{
				msg=obj.get("msg").toString();
				msgUrl="/userCenter/toBankInput.do";
				return "error_ftl";
			}


		} catch (Exception e) {
			addActionError("签约失败!");
			msgUrl="toBankInput.do";
			return "error_ftl";
		}
	}



	/**
	 * 实名认证输入银行验证码
	 * @return
	 */
	@Action(value="/userCenter/bankSignPhoneCodeOri",results={
			@Result(name="success", location="/content/payment/payment_bank_phone_code.ftl", type="freemarker"),
			@Result(name="success2", location="/userCenter/toBank.do", type="redirect")
	})
	public String bankSignPhoneCodeOri(){

		User loginUser = getLoginUser();
		User user = userService.get(loginUser.getId());
		if(1==user.getRealStatus().intValue() ){
			return "success2";
		}

		return SUCCESS;
	}

	/**
	 * 操作提现
	 * @return
	 */
	@Action(value = "/userCenter/bankSignPhoneCodeRepeat", results = { @Result(type = "json") })
	@InputConfig(resultName = "ajaxError")
	public String bankSignPhoneCodeRepeat(){


		User loginUser = getLoginUser();

		jsonMap=new HashMap<String, Object>();

		User user = userService.get(loginUser.getId());

		String pay_url = ConfigUtil.getConfigUtil().get("pay.url");
		log.info(pay_url);
		Map<String,String> pmap = new HashMap<String,String>();

		pmap.put("id",user.getId().toString());
		pmap.put("no",user.getBankCustNo());

		msg = com.ysd.util.HttpUtil.post(pay_url+"/bindingCode",pmap);
		if(msg == null || "".equals(msg)){
			jsonMap.put("code","1000");
			jsonMap.put("msg", "无法发送获取验证码！");
			return ajax(JsonUtil.toJson(jsonMap));
		}
		net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(msg);
		String code =  obj.get("code").toString();
		if("0000".equals(code)){
			//去验证手机号码
			jsonMap.put("code","0000");
			jsonMap.put("msg", "验证码发送成功");
			return ajax(JsonUtil.toJson(jsonMap));
		}else{
			jsonMap.put("code","M0009_1");
			jsonMap.put("msg", obj.get("msg").toString());
			return ajax(JsonUtil.toJson(jsonMap));
		}

	}

	/**
	 * 操作提现
	 * @return
	 */
	@Action(value="/userCenter/bankSignPhoneCode",results={
			@Result(name="success", location="/userCenter/toBank.do", type="redirect")
	})
	public String bankSignPhoneCode(){

		if(StringUtils.isBlank(checkCode)){
			msg = "请输入验证码";
			msgUrl = "/userCenter/bankSignPhoneCodeRepeat.do";
			return "error_ftl";
		}
		User loginUser = getLoginUser();
		User user = userService.get(loginUser.getId());

		String pay_url = ConfigUtil.getConfigUtil().get("pay.url");
		log.info(pay_url);
		Map<String,String> pmap = new HashMap<String,String>();
		pmap.put("code",checkCode);
		pmap.put("id",user.getId().toString());
		pmap.put("no",user.getBankCustNo());

		msg = com.ysd.util.HttpUtil.post(pay_url+"/binding2",pmap);
		if(msg == null || "".equals(msg)){
			msg="服务器异常,请重新尝试！";
			msgUrl="/userCenter/bankSignPhoneCodeOri.do";
			return "error_ftl";
		}

		JSONObject obj = JSONObject.fromObject(msg);
		String code =  obj.get("code").toString();
		if("0000".equals(code)){
			//去验证手机号码
			return SUCCESS;
		}else{
			msg=obj.get("msg").toString();
			msgUrl="/userCenter/bankSignPhoneCodeOri.do";
			return "error_ftl";
		}
	}


	/**
	 * 操作提现
	 * @return
	 */
	@Action(value="/userCenter/cash",results={
			@Result(name="success", location="/content/user/cashSuccess.ftl", type="freemarker")
	})
	public String cash(){

		return SUCCESS;
		/* yujian pc不支持提现
		reloadUser();
		User userLogin = getLoginUser();

		// 验证手机验证码
		if (StringUtils.isBlank(code)) {
			msg = "请输入验证码";
			msgUrl = "/userCenter/getMoney.do";
			return "error_ftl";
		}

		if (!code.equals(userLogin.getPhoneCode())) {
				msg = "验证码不正确";
				msgUrl = "/userCenter/getMoney.do";
				return "error_ftl";
			}else{
				userLogin.setPhoneCode("aeqjk1");
				userService.update(userLogin);
		}

		if(getAccountBankList().size() >0){
			accountBank = getAccountBankList().get(0);
		}else{
			msg = "请先添加银行账户！";
			msgUrl = "/userCenter/toBank.do";
			return "error_ftl";
		}



		Listing listing =this.listingService.getListing(ConstantUtil.MAX_CASH_MONEY);
		maxCashMoney= listing.getKeyValue();//最大提现金额
		Listing listing1 =this.listingService.getListing(ConstantUtil.MIN_CASH_MONEY);
		String minCashMoney= listing1.getKeyValue();//最小提现金额

		if(accountCash.getTotal().toString().matches("^(([1-9]\\d*)|\\d)(\\.\\d{1,2})?$")){
			if(accountCash.getTotal().compareTo(new BigDecimal(minCashMoney))<0){
				msg = "申请提现金额每次必须大于"+minCashMoney+"元";
				msgUrl = "/userCenter/getMoney.do";
				return "error_ftl";
			}else if(accountCash.getTotal().doubleValue()>Double.valueOf(maxCashMoney)){
				msg = "申请提现金额每次必须小于"+maxCashMoney+"元";
				msgUrl = "/userCenter/getMoney.do";
				return "error_ftl";
			}
		}else{
			msg = "申请提现金额必须为整数";
			msgUrl = "/userCenter/getMoney.do";
			return "error_ftl";
		}

		logPayment.debug("【操作提现】["+userLogin.getUsername()+"]提现金额["+accountCash.getTotal().doubleValue()+"]可提额度["+ableCashMoney+"]");
		accountCash.setName(accountBank.getName());
		if(accountCash.getTotal().doubleValue()>ableCashMoney){

			msg = "申请提现金额大于实际可提现额度,请刷新页面!";
			msgUrl = "/userCenter/getMoney.do";
			return "error_ftl";

		}else{
			accountCash = this.accountCashService.cashMoney(userLogin, accountCash, accountBank,cashType,getRequestRemoteAddr());
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, +1);    //得到前一天
			Date date = calendar.getTime();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
			lastTime = df.format(date);


			Map<String,String> pmap = new HashMap<String,String>();
			pmap.put("orderNo",accountCash.getTradeNo());
			pmap.put("userId",userLogin.getId().toString());
			pmap.put("amount",  accountCash.getTotal().toString() ) ;
			pmap.put("Fee",  accountCash.getFee().toString() ) ;
			msg = com.ysd.util.HttpUtil.post(ConfigUtil.getConfigUtil().get("pay.url")+"/cash",pmap);

			if(msg == null || "".equals(msg)){
				msg = "提现接口失败";
				msgUrl = "/userCenter/getMoney.do";
				return "error_ftl";
			}
			JSONObject obj = JSONObject.fromObject(msg);
			String code =  obj.get("code").toString();
			if("0000".equals(code)){
				return SUCCESS;
			}else{
				msg=obj.get("msg").toString();
				msgUrl="/userCenter/getMoney.do";
				return "error_ftl";
			}
		}*/

	}
	
	
	private List<AccountBank> getAccountBankList(){
		return this.userService.queryAccountBank(getLoginUser().getId());
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
	 * 获取手机验证码 融宝发 我们调
	 * 
	 * @return
	 */
	@Action(value = "/user/ajaxGetPhoneCodeRongbao", results = { @Result(type = "json") })
	@InputConfig(resultName = "ajaxError")
	public String ajaxGetPhoneCodeRongbao() {
		System.out.println("ajaxGetPhoneCodeRongbao.....");
		Map<String, Object> map = new HashMap<String, Object>();
		jsonMap=new HashMap<String, Object>();
		map.put("phone", phone);
//		User getuser = this.userService.getUser(map);
		User getuser = getLoginUser();
		if (getuser == null) {
			jsonMap.put("code","1000");
			jsonMap.put("msg", "用户不存在,无法获取验证码！");
			return ajax(JsonUtil.toJson(jsonMap));
		}
		
		UserAccountRecharge userAccountRecharge=new UserAccountRecharge();
		userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(orderNo);//根据订单号 查询有无充值记录
		if(userAccountRecharge == null){
			
			jsonMap.put("code","1000");
			jsonMap.put("msg", "充值记录不存在");
			return ajax(JsonUtil.toJson(jsonMap));
		}
		
		if(!userAccountRecharge.getUserId().equals(getuser.getId())){
			jsonMap.put("code","1000");
			jsonMap.put("msg", "充值记录异常");
			return ajax(JsonUtil.toJson(jsonMap));
		}
		
		if("1".equals(userAccountRecharge.getStatus().toString())){
			jsonMap.put("code","1000");
			jsonMap.put("msg", "充值记录异常");
			return ajax(JsonUtil.toJson(jsonMap));
		}
		
		
		User userLogin = getLoginUser();
		String phoneCode = CommonUtil.getRandomString(6);// 6位短信验证码
		//临时保存验证码【以后替换手机发送验证码】
		setSession("smsCode", phoneCode);
		System.out.println("----------------------------------------------------phoneCode:"+phoneCode);
		IPYYSMSResult ipyysmsResult =YueSmsUtils.sendForRecharge(userLogin.getPhone(), phoneCode);
		
		  if (!ipyysmsResult.ok()) {
        	  	jsonMap.put("code","1000");
  			jsonMap.put("msg", "验证码发送错误");
  			return ajax(JsonUtil.toJson(jsonMap));
          }
		
		jsonMap.put("code","0000");
		jsonMap.put("msg", "验证码发送成功");
		return ajax(JsonUtil.toJson(jsonMap));
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
	
	private String bind_token;//绑卡token 防止重复提交
	private String pay_token; //支付token 
	private String tradeNo; //订单号
	private BigDecimal ableMoney;
	
	private String rechargeMoney ;// 充值金额
	private String mycode;//充值图像验证码
	
	private UserInfo userInfo;
	private AccountBank accountBank;
	
	private Pager pager;
	private User user;
	
	private String jsonResult;//绑卡成功返回数据.
	private Map<String,Object> jsonMap;//绑卡成功返回展示数据

	private String orderNo;//商户订单号
	private String checkCode;//验证码
	private String keyNum;//看第几次支付
	
	private String safepwd;
	
	private String payment;

	private AccountCash accountCash;

	private double ableCashMoney;//可提现金额

	private String cashType;//体现方式类型

	private String code;// 验证码

	private String  lastTime;//预计到账时间

	private String maxCashMoney;//最大提现金额
	
	private String money;//充值金额 
	

	public void setAbleMoney(BigDecimal ableMoney) {
		this.ableMoney = ableMoney;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public AccountCash getAccountCash() {
		return accountCash;
	}

	public void setAccountCash(AccountCash accountCash) {
		this.accountCash = accountCash;
	}

	private String phone;
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	private UserAccountRecharge userAccountRecharge;
	public UserAccountRecharge getUserAccountRecharge() {
		return userAccountRecharge;
	}

	public void setUserAccountRecharge(UserAccountRecharge userAccountRecharge) {
		this.userAccountRecharge = userAccountRecharge;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getSafepwd() {
		return safepwd;
	}


	public void setSafepwd(String safepwd) {
		this.safepwd = safepwd;
	}


	public String getBind_token() {
		return bind_token;
	}


	public void setBind_token(String bind_token) {
		this.bind_token = bind_token;
	}


	public String getPay_token() {
		return pay_token;
	}


	public void setPay_token(String pay_token) {
		this.pay_token = pay_token;
	}


	public String getTradeNo() {
		return tradeNo;
	}


	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}


	public String getRechargeMoney() {
		return rechargeMoney;
	}


	public void setRechargeMoney(String rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}


	public String getMycode() {
		return mycode;
	}


	public void setMycode(String mycode) {
		this.mycode = mycode;
	}


	public UserInfo getUserInfo() {
		return userInfo;
	}


	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}


	public AccountBank getAccountBank() {
		return accountBank;
	}


	public void setAccountBank(AccountBank accountBank) {
		this.accountBank = accountBank;
	}


	public Pager getPager() {
		return pager;
	}


	public void setPager(Pager pager) {
		this.pager = pager;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getJsonResult() {
		return jsonResult;
	}


	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}


	public Map getJsonMap() {
		return jsonMap;
	}


	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}


	public String getOrderNo() {
		return orderNo;
	}

	public double getAbleCashMoney() {
		return ableCashMoney;
	}

	public void setAbleCashMoney(double ableCashMoney) {
		this.ableCashMoney = ableCashMoney;
	}

	public String getCashType() {
		return cashType;
	}

	public void setCashType(String cashType) {
		this.cashType = cashType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getMaxCashMoney() {
		return maxCashMoney;
	}

	public void setMaxCashMoney(String maxCashMoney) {
		this.maxCashMoney = maxCashMoney;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public String getCheckCode() {
		return checkCode;
	}


	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}


	public String getKeyNum() {
		return keyNum;
	}


	public void setKeyNum(String keyNum) {
		this.keyNum = keyNum;
	}
	
	
}
