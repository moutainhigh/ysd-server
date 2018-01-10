package com.qmd.payment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;
import com.qmd.util.bean.PaymentInfo;
import com.qmd.wap.config.PartnerConfig;
import com.qmd.wap.security.Md5Algorithm;
import com.qmd.wap.security.RSAUtil;
import com.qmd.wap.util.DateUtil;
import com.qmd.wap.util.LLPayUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * 连连支付-wap
 * 
 * @author Administrator
 * 
 */
public class LianlianUtils extends BasePaymentProduct {

	private Logger logPayment = Logger.getLogger("userPaymentLog");

	public static String gopay_gateway = "https://yintong.com.cn/llpayh5/authpay.htm";

	public static String bankcard_url = "https://yintong.com.cn/traderapi/userbankcard.htm";
	// 字符编码格式，目前支持 GBK 或 UTF-8
	public static String input_charset = "UTF-8";
	
	public static String returnString;
	
	public static Map<String, String> resMap = new HashMap<String, String>();

	// static{
	// resMap.put("01", "支付成功");
	// resMap.put("0000", "支付失败");
	// resMap.put("0001", "系统错误");
	// resMap.put("0002", "订单超时");
	// resMap.put("0011", "系统维护");
	// resMap.put("0012", "无效商户");
	// resMap.put("0013", "余额不足");
	// resMap.put("0014", "超过支付限额");
	// resMap.put("0015", "卡号或卡密错误");
	// resMap.put("0016", "不合法的IP地址");
	// resMap.put("0017", "重复订单金额不符");
	// resMap.put("0018", "卡密已被使用");
	// resMap.put("0019", "订单金额错误");
	// resMap.put("0020", "支付的类型错误");
	// resMap.put("0021", "卡类型有误");
	// resMap.put("0022", "卡信息不完整");
	// resMap.put("0023", "卡号，卡密，金额不正确");
	// resMap.put("0024", "不能用此卡继续做交易");
	// resMap.put("0025", "订单无效");
	// resMap.put("0026", "充值卡无效 ");
	//
	// }

	public static String getIpAddr(HttpServletRequest request) {
		if (request == null)
			return "";
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 对字符串进行MD5签名
	 * 
	 * @param text
	 *            明文
	 * 
	 * @return 密文
	 */
	public static String md5(String text) {
		return DigestUtils.md5Hex(getContentBytes(text, input_charset));
	}

	/**
	 * 对字符串进行SHA签名
	 * 
	 * @param text
	 *            明文
	 * 
	 * @return 密文
	 */
	public static String sha(String text) {
		return DigestUtils.shaHex(getContentBytes(text, input_charset));
	}

	/**
	 * @param content
	 * @param charset
	 * @return
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}

	@Override
	public String getPaymentUrl() {
		return gopay_gateway;
	}

	@Override
	public String getPaymentSn(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return null;
		}
		String merOrderNum = "";
		String resData = httpServletRequest.getParameter("res_data");
		if (!"".equals(resData)) {
			PaymentInfo info = JSON.parseObject(resData, PaymentInfo.class);
			if (info != null) {
				merOrderNum = info.getNo_order();
			}
		}
		
		if("".equals(merOrderNum)){
			returnString = LLPayUtil.readReqStr(httpServletRequest);
			 JSONObject reqObj = JSON.parseObject(returnString);
		        if (reqObj == null)
		        {
		            return "";
		        }
		        merOrderNum = reqObj.getString("no_order");
		}
		
		return merOrderNum;
	}

	@Override
	public BigDecimal getPaymentAmount(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return null;
		}
		BigDecimal payAmount = null;
		String resData = httpServletRequest.getParameter("res_data");
		if (!"".equals(resData) &&  StringUtils.isEmpty(returnString)) {
			PaymentInfo info = JSON.parseObject(resData, PaymentInfo.class);
			if (info != null && !"".equals(info.getMoney_order())) {
				payAmount = new BigDecimal(info.getMoney_order());
			}
		}
		
		if(payAmount == null &&  StringUtils.isNotEmpty(returnString)){
			JSONObject reqObj = JSON.parseObject(returnString);
		        if (reqObj == null)
		        {
		            return null;
		        }
		        payAmount = new BigDecimal( reqObj.getString("money_order") );
		}
		
		return payAmount;
	}

	@Override
	public boolean isPaySuccess(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return false;
		}

		String resData = httpServletRequest.getParameter("res_data");
		if (!"".equals(resData) &&  StringUtils.isEmpty(returnString)) {
			PaymentInfo info = JSON.parseObject(resData, PaymentInfo.class);
			String result_pay = info.getResult_pay();
			if ("SUCCESS".equals(result_pay)) {
				return true;
			} else {
				return false;
			}
		}
		
		if( StringUtils.isNotEmpty(returnString)){
			JSONObject reqObj = JSON.parseObject(returnString);
	        String result_pay = reqObj.getString("result_pay");
	        if ("SUCCESS".equals(result_pay)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public String returnResult(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return "";
		}
		String payResult = httpServletRequest.getParameter("res_data");
		if(StringUtils.isNotEmpty(returnString)){
			payResult = returnString;
		}
		return payResult;
	}

	@Override
	public Map<String, String> getParameterMap(RechargeConfig rechargeConfig, UserAccountRecharge userAccountRecharge, HttpServletRequest request) {
		// --------------------------------------------------------------------------

		String cardNo = request.getParameter("cardNo").replaceAll("\\s*", "");// 银行卡号
		String idNo = request.getParameter("idNo");// 身份证号
		// String userId = request.getParameter("userId");
		String userId = idNo;
		String acctName = request.getParameter("realName");// 真实姓名
		String infoOrder = userAccountRecharge.getRemark();// 订单描述
		String nameGoods = userAccountRecharge.getRemark();// 商品名称
		String noAgree = "";// 协议号
		String noOrder = userAccountRecharge.getTradeNo();// 商户唯一订单号

		String urlReturn = PartnerConfig.URL_RETURN;// 支付结束回显url
		String notifyUrl = PartnerConfig.NOTIFY_URL;// 服务器异步通知地址
		String oidPartner = rechargeConfig.getBargainorId();// PartnerConfig.OID_PARTNER;

		String appRequest = "3";
		String busiPartner = PartnerConfig.BUSI_PARTNER;
		String bgColor = "ff5001";
		String frms_ware_category = "2009";// 商品类目-实名类 - P2P 小额贷款 
		String user_info_mercht_userno = userId;// 用户Id
		String user_info_dt_register = request.getParameter("registerTime");// 用户注册时间
		String user_info_full_name = acctName;// 实名
		String user_info_id_no = idNo;// 身份证号
		String user_info_identify_type = "1";
		String user_info_identify_state = "1";

		String riskItem = "{\"frms_ware_category\":\"" + frms_ware_category + "\",\"user_info_mercht_userno\":\"" + user_info_mercht_userno
				+ "\",\"user_info_dt_register\":\"" + user_info_dt_register + "\",\"user_info_full_name\":\"" + user_info_full_name
				+ "\",\"user_info_id_no\":\"" + user_info_id_no + "\",\"user_info_identify_type\":\"" + user_info_identify_type
				+ "\",\"user_info_identify_state\":\"" + user_info_identify_state + "\"}";

		// String riskItem =
		// "{\"user_info_bind_phone\":\"13958069593\",\"user_info_dt_register\":\"20131030122130\",\"risk_state\":\"1\",\"frms_ware_category \":\"1009\"}";
		String moneyOrder = CommonUtil.setPriceScale(userAccountRecharge.getMoney()).toString();// 交易金额-元

		String signType = PartnerConfig.SIGN_TYPE;
		String validOrder = "30";// 订单有效时间--分钟为单位，默认为10080分钟（7天）
		String dtOrder = DateUtil.getCurrentDateTimeStr1();
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("acct_name=");
		strBuf.append(acctName);
		strBuf.append("&app_request=" + appRequest);
		strBuf.append("&bg_color=");
		strBuf.append(bgColor);
		strBuf.append("&busi_partner=");
		strBuf.append(busiPartner);
		strBuf.append("&card_no=");
		strBuf.append(cardNo);
		strBuf.append("&dt_order=");
		strBuf.append(dtOrder);
		strBuf.append("&id_no=");
		strBuf.append(idNo);
		strBuf.append("&info_order=");
		strBuf.append(infoOrder);
		strBuf.append("&money_order=");
		strBuf.append(moneyOrder);
		strBuf.append("&name_goods=");
		strBuf.append(nameGoods);
		// strBuf.append("&no_agree=");
		// strBuf.append(noAgree);
		strBuf.append("&no_order=");
		strBuf.append(noOrder);
		strBuf.append("&notify_url=");
		strBuf.append(notifyUrl);
		strBuf.append("&oid_partner=");
		strBuf.append(oidPartner);
		strBuf.append("&risk_item=");
		strBuf.append(riskItem);
		strBuf.append("&sign_type=");
		strBuf.append(signType);
		strBuf.append("&url_return=");
		strBuf.append(urlReturn);
		strBuf.append("&user_id=");
		strBuf.append(userId);
		strBuf.append("&valid_order=");
		strBuf.append(validOrder);

		String sign_src = strBuf.toString();
		if (sign_src.startsWith("&")) {
			sign_src = sign_src.substring(1);
		}
		String sign = "";
		if ("RSA".equals(request.getParameter("sign_type"))) {
			// sign = RSAUtil.sign(PartnerConfig.TRADER_PRI_KEY, sign_src);
			sign = RSAUtil.sign(rechargeConfig.getDescription(), sign_src);
		} else {
			sign_src += "&key=" + rechargeConfig.getBargainorKey();// PartnerConfig.MD5_KEY;
			try {
				sign = Md5Algorithm.getInstance().md5Digest(sign_src.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("加密前=" + sign_src);
		System.out.println("加密后=" + sign);

		PaymentInfo payInfo = new PaymentInfo();
		payInfo.setApp_request(appRequest);
		payInfo.setBg_color(bgColor);
		payInfo.setBusi_partner(busiPartner);
		payInfo.setCard_no(cardNo);
		payInfo.setDt_order(dtOrder);
		payInfo.setId_no(idNo);
		payInfo.setInfo_order(infoOrder);
		payInfo.setMoney_order(moneyOrder);
		payInfo.setName_goods(nameGoods);
		// payInfo.setNo_agree(noAgree);
		payInfo.setNo_order(noOrder);
		payInfo.setNotify_url(notifyUrl);
		payInfo.setOid_partner(oidPartner);
		payInfo.setAcct_name(acctName);
		payInfo.setRisk_item(riskItem);
		payInfo.setSign_type(signType);
		payInfo.setUrl_return(urlReturn);
		payInfo.setUser_id(userId);
		payInfo.setValid_order(validOrder);
		payInfo.setSign(sign);
		String reqData = JSON.toJSONString(payInfo);
		System.out.println("req_data==" + reqData);
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("req_data", reqData);

		return parameterMap;
	}

	@Override
	public boolean verifySign(RechargeConfig rechargeConfig, HttpServletRequest request) {
		
		if(StringUtils.isEmpty(returnString)){
			Boolean result = null;
			String resData=request.getParameter("res_data");
			PaymentInfo info = JSON.parseObject(resData, PaymentInfo.class);
			if(info == null){
				return false;
			}
			
			String result_pay = info.getResult_pay();
			if("SUCCESS".equals(result_pay)){
				result = true;
			}else{
				result = false;
			}
			return result;
		}else{
			String reqStr = returnString;
			if (LLPayUtil.isnull(reqStr)) {
				
				return false;
			}
			try{
				if (!LLPayUtil.checkSign(reqStr, rechargeConfig.getDescription(), rechargeConfig.getBargainorKey())) {
					return false;
				}
			}catch (Exception e) {
				return false;
			}
			return true;
		}
	}

	@Override
	public String getPayReturnMessage(String paymentSn) {
		return "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" /><title>页面跳转中..</title></head><body onload=\"javascript: document.forms[0].submit();\"><form action=\""
				+ ConstantUtil.WEB_SITE
				+ RESULT_URL
				+ "\"><input type=\"hidden\" name=\"tradeNo\" value=\""
				+ paymentSn
				+ "\" /></form></body></html>";

	}

	@Override
	public String getPayNotifyMessage() {
		PaymentInfo info = new PaymentInfo();
		info.setRet_code("0000");
		info.setRet_msg("交易成功");
		return JSON.toJSONString(info);
	}

	/**
	 * 签约查询
	 * 
	 * @param httpServletRequest
	 *            httpServletRequest
	 * 
	 * @return 是否验证通过
	 */
	/**
	 * @Override public boolean signApi(RechargeConfig rechargeConfig, User
	 *           user, HttpServletRequest httpServletRequest){ String
	 *           oid_partner = rechargeConfig.getBargainorId();//
	 *           PartnerConfig.OID_PARTNER; String user_id = user.getCardId();
	 *           String offset = "0"; String sign_type =
	 *           PartnerConfig.SIGN_TYPE;
	 * 
	 *           StringBuffer strBuf = new StringBuffer();
	 *           strBuf.append("offset="); strBuf.append(offset);
	 *           strBuf.append("&oid_partner="); strBuf.append(oid_partner);
	 *           strBuf.append("&sign_type="); strBuf.append(sign_type);
	 *           strBuf.append("&user_id="); strBuf.append(user_id);
	 * 
	 *           String sign_src = strBuf.toString();
	 * 
	 * 
	 *           String sign = ""; if ("RSA".equals(sign_type)) { // sign =
	 *           RSAUtil.sign(PartnerConfig.TRADER_PRI_KEY, sign_src); sign =
	 *           RSAUtil.sign(rechargeConfig.getDescription(), sign_src); } else
	 *           { sign_src += "&key=" +rechargeConfig.getBargainorKey();//
	 *           PartnerConfig.MD5_KEY; try { sign =
	 *           Md5Algorithm.getInstance().md5Digest
	 *           (sign_src.getBytes("utf-8")); } catch
	 *           (UnsupportedEncodingException e) { // TODO Auto-generated catch
	 *           block e.printStackTrace(); } } }
	 **/
}
