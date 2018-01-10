package com.qmd.payment;

import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.util.ConstantUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 环迅支付
 *
 */

public class IpsUtils extends BasePaymentProduct {

	public static String gopay_gateway = ConstantUtil.IPS_WEB
			+ "/ipayment.aspx";

	// 字符编码格式，目前支持 GBK 或 UTF-8
	public static String input_charset = "UTF-8";

	/**
	 * Convenience method to get the IP Address from client.
	 * 
	 * @param request
	 *            the current request
	 * @return IP to application
	 */
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

	@Override
	public String getPaymentUrl() {
		return gopay_gateway;
	}

	@Override
	public String getPaymentSn(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return null;
		}
		String merOrderNum = httpServletRequest.getParameter("billno");
		if (StringUtils.isEmpty(merOrderNum)) {
			return null;
		}
		return merOrderNum;
	}

	@Override
	public BigDecimal getPaymentAmount(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return null;
		}
		String payAmount = httpServletRequest.getParameter("amount");
		if (StringUtils.isEmpty(payAmount)) {
			return null;
		}
		return new BigDecimal(payAmount).setScale(2);
	}

	@Override
	public boolean isPaySuccess(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return false;
		}
		String payResult = httpServletRequest.getParameter("succ");
		if (StringUtils.equals(payResult, "Y")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回参数结果，环迅支付用
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	@Override
	public String returnResult(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return "";
		}
		// String payResult = httpServletRequest.getParameter("respCode");
		String result = "支付失败";
		// for(String s:resMap.keySet()){
		// if(payResult.equals(s)){
		// result= resMap.get(s);
		// break;
		// }
		// }
		return result;
	}

	@Override
	public Map<String, String> getParameterMap(RechargeConfig rechargeConfig,
			UserAccountRecharge userAccountRecharge,
			HttpServletRequest httpServletRequest) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateString = simpleDateFormat.format(userAccountRecharge
				.getCreateDate());

		String totalAmountString = userAccountRecharge.getMoney().setScale(2)
				.toString();// .multiply(new
							// BigDecimal(100)).setScale(0).toString();

		String Lang = "GB";// 语言
		String ErrorUrl = "";// 支付结果错误返回的商户URL 建议传空
		String Currency_Type = "RMB"; // 币种 RMB 人民币

		String Mer_code = rechargeConfig.getBargainorId();// 商户号
		String Mer_key = rechargeConfig.getBargainorKey();// 商户证书
		String Billno = userAccountRecharge.getTradeNo();// 订单号
		String Amount = totalAmountString;
		String Date = dateString;
		String Gateway_Type = "01";// 【01:借记卡；04:IPS账户支付；08：IB支付；16：电话支付；64：储值卡支付】
		String Merchanturl = ConstantUtil.RECHARGE_SITE + Billno;// 支付成功返回URL
		String FailUrl = "";// 支付失败返回URL
		String Attach = "";// 商户附加数据包
		/**
		 * OrderEncodeType设置为5，且在订单支付接口的Signmd5字段中存放MD5摘要认证信息。
		 * 交易提交接口MD5摘要认证的明文按照指定参数名与值的内容连接起来，将证书同时拼接到
		 * 参数字符串尾部进行md5加密之后再转换成小写，明文信息如下： billno+ 【订单编号】+ currencytype + 【币种】+
		 * amount + 【订单金额】+ date + 【订单日期】 + orderencodetype + 【订单支付接口加密方式】+
		 * 【商户内部证书字符串】
		 */
		String OrderEncodeType = "5";// 订单支付加密方式
		String RetEncodeType = "17";// 交易返回加密方式【16：md5withRsa；17：md5摘要】
		String Rettype = "1";// 是否提供Server返回方式【0：无Server to Server；1：有Server to
								// Server】
		String ServerUrl = Merchanturl;// Server to Server返回页面

		cryptix.jce.provider.MD5 b = new cryptix.jce.provider.MD5();
		// 订单加密的明文 billno+【订单编号】+ currencytype +【币种】+ amount +【订单金额】+ date
		// +【订单日期】+ orderencodetype +【订单支付接口加密方式】+【商户内部证书字符串】
		String SignMD5 = b.toMD5(
				"billno" + Billno + "currencytype" + Currency_Type + "amount"
						+ Amount + "date" + Date + "orderencodetype"
						+ OrderEncodeType + Mer_key).toLowerCase();
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("Mer_code", Mer_code);
		parameterMap.put("Billno", Billno);
		parameterMap.put("Amount", Amount);
		parameterMap.put("Date", Date);
		parameterMap.put("Currency_Type", Currency_Type);
		parameterMap.put("Gateway_Type", Gateway_Type);
		parameterMap.put("Lang", Lang);
		parameterMap.put("Merchanturl", Merchanturl);
		parameterMap.put("FailUrl", FailUrl);
		parameterMap.put("ErrorUrl", ErrorUrl);
		parameterMap.put("Attach", Attach);
		parameterMap.put("OrderEncodeType", OrderEncodeType);
		parameterMap.put("RetEncodeType", RetEncodeType);
		parameterMap.put("Rettype", Rettype);
		parameterMap.put("ServerUrl", ServerUrl);
		parameterMap.put("SignMD5", SignMD5);

		return parameterMap;
	}

	@Override
	public boolean verifySign(RechargeConfig rechargeConfig,
			HttpServletRequest request) {
		// payAmount, orderID, tradeNo, remark, acquiringTime, signType,
		// charset, stateCode, orderNo, orderAmount, signMsg, resultCode,
		// partnerID, completeTime
		// 验签
		String billno = request.getParameter("billno");
		String currency_type = request.getParameter("Currency_type");
		String amount = request.getParameter("amount");
		String mydate = request.getParameter("date");
		String succ = request.getParameter("succ");
		String msg = request.getParameter("msg");
		String attach = request.getParameter("attach");
		String ipsbillno = request.getParameter("ipsbillno");
		String retEncodeType = request.getParameter("retencodetype");
		String signature = request.getParameter("signature");

		// 返回订单加密的明文:billno+【订单编号】+currencytype+【币种】+amount+【订单金额】+date+【订单日期】+succ+【成功标志】+ipsbillno+【IPS订单编号】+retencodetype
		// +【交易返回签名方式】+【商户内部证书】
		String content = "billno" + billno + "currencytype" + currency_type
				+ "amount" + amount + "date" + mydate + "succ" + succ
				+ "ipsbillno" + ipsbillno + "retencodetype" + retEncodeType; // 明文：订单编号+订单金额+订单日期+成功标志+IPS订单编号+币种

		Boolean result = null;
		if (retEncodeType.equals("16")) {
			cryptix.jce.provider.MD5WithRSA a = new cryptix.jce.provider.MD5WithRSA();
			a.verifysignature(content, signature, "c:\\PubKey\\publickey.txt");

			// Md5withRSA验证返回代码含义
			// -99 未处理
			// -1 公钥路径错
			// -2 公钥路径为空
			// -3 读取公钥失败
			// -4 验证失败，格式错误
			// 1： 验证失败
			// 0: 成功
			if (a.getresult() == 0) {
				result = true;
			}
		} else if (retEncodeType.equals("17")) {
			// 登陆http://merchant.ips.com.cn/商户后台下载的商户证书内容
			String md5Key = rechargeConfig.getBargainorKey();
			cryptix.jce.provider.MD5 b = new cryptix.jce.provider.MD5();
			String SignMD5 = b.toMD5(content + md5Key).toLowerCase();

			if (SignMD5.equals(signature)) {
				result = true;
			}
		}

		return result;
	}

	@Override
	public String getPayReturnMessage(String tradeNo) {
		return "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" /><title>页面跳转中..</title></head><body onload=\"javascript: document.forms[0].submit();\"><form action=\""
				+ ConstantUtil.WEB_SITE
				+ RESULT_URL
				+ "\"><input type=\"hidden\" name=\"tradeNo\" value=\""
				+ tradeNo + "\" /></form></body></html>";
	}

	@Override
	public String getPayNotifyMessage() {
		return null;
	}
}
