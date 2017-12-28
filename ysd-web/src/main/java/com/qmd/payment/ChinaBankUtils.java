package com.qmd.payment;

import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.util.ConstantUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 网银在线
 * 
 */
public class ChinaBankUtils extends BasePaymentProduct {

	private Logger logPayment = Logger.getLogger("userPaymentLog");

	public static String gopay_gateway = ConstantUtil.CHINA_BANK_WEB
			+ "/PayGate?encoding=UTF-8";

	/**
	 * 乱码问题 如果你们编码格式是utf-8，在send中，使用下面的代码
	 * <form action="https://pay3.chinabank.com.cn/PayGate?encoding=UTF-8"
	 *  method="POST" name="E_FORM">
	 * 乱码问题中java与c#语言格式比较常见，如果有其它语言格式乱码，请联系网银在线的技术支持 java：
	 * 先request.setCharacterEncoding("gbk")，再request.getparameter()
	 * 　　　　　或者new String(v_pmode.getBytes("iso-8859-1"),"GBK")
	 * 　　　　　返回中用了URLEncoder 商户用URLDecoder 试试
	 */

	// 字符编码格式，目前支持 GBK 或 UTF-8
	public static String input_charset = "UTF-8";
	public static Map<String, String> resMap = new HashMap<String, String>();

	static {
		resMap.put("100100", "错误异常");
		resMap.put("100101", "服务资源未找到,请稍后重试");
		resMap.put("100900", "抱歉,出现未预期的异常");
		resMap.put("200901", "不支持的安全方式,参数有误");
		resMap.put("200902", "安全验证,加密出现异常");
		resMap.put("200903", "数据签名验证未通过");
		resMap.put("201001", "工行验证返回签名未通过,可疑银行交易");
		resMap.put("201002", "安全验证,中行MD5验证出现错误");
		resMap.put("201005", "服务拒绝,CCB签名验证错误");
		resMap.put("201006", "服务拒绝,CBP签名验证错误");
		resMap.put("201007", "服务拒绝,CMB签名验证错误");
		resMap.put("201008", "服务拒绝,CMBC签名验证错误");
		resMap.put("201009", "服务拒绝,COMM签名验证错误");
		resMap.put("201010", "服务拒绝,广发行签名验证错误");
		resMap.put("201011", "服务拒绝,广银联签名验证错误");
		resMap.put("201012", "服务拒绝,深发展签名验证错误");
		resMap.put("201101", "MASTER验证");
		resMap.put("201102", "VISA订单数据验证错误");
		resMap.put("201103", "JCB外卡验证错误");
		resMap.put("201104", "安全验证VE ,验证失败");
		resMap.put("201105", "安全验证失败");
		resMap.put("201106", "服务暂停,该号列入风险控制,交易存在风险");
		resMap.put("201107", "商户外卡支付要求3D验证");
		resMap.put("201108", "存在未知风险,或风险未明确定义");
		resMap.put("201109", "验证异常，存在风险,请稍后重试");
		resMap.put("201110", "支付超出日限额,受风险控制");
		resMap.put("201111", "支付次数过多,已列入风险控制中,请改日继续支付");
		resMap.put("201112", "支付单笔超出限额");
		resMap.put("201113", "超出单一IP支付次数");
		resMap.put("201114", "商户已列入黑名单控制");
		resMap.put("201300", "安全验证,VE验证失败");
		resMap.put("300800", "缺少必要的服务参数");
		resMap.put("500700", "参数错误,订单不能保存");
		resMap.put("500701", "请求非法,订单不存在");
		resMap.put("500702", "服务拒绝,该服务未提供");
		resMap.put("500703", "商户找不到,请确认已注册");
		resMap.put("500704", "非正常商户状态");
		resMap.put("500705", "订单支付信息未找到");
		resMap.put("500706", "操作失败,状态更新失败");
		resMap.put("500708", "参数错误,该商户ID不存在");
		resMap.put("500709", "商户未开通外卡支付");
		resMap.put("500710", "该商户未启用支付直通服务,错误的服务编码");
		resMap.put("500800", "错误的数据格式,不能解析");
		resMap.put("500902", "参数错误,缺少订单号");
		resMap.put("500903", "参数错误,订单号过长");
		resMap.put("501101", "服务拒绝,重复的服务请求");
		resMap.put("501102", "重复的服务请求");
		resMap.put("501103", "参数错误,订单号不能空");
		resMap.put("501104", "参数错误,请填写金额");
		resMap.put("501105", "参数错误,支付结果处理失败");
		resMap.put("501106", "服务可疑,支付结果金额不一致");
		resMap.put("501107", "服务超时中止,订单不存在");
		resMap.put("501108", "提交的参数有误");
		resMap.put("501109", "缺少必要的参数或参数不能识别");
		resMap.put("501110", "币种参数有误,不支持该币种");
		resMap.put("501111", "缺少必要返回参数idx,服务不能识别");
		resMap.put("501112", "参数错误,非法金额参数");
		resMap.put("501113", "参数错误,金额超出范围");
		resMap.put("501114", "参数错误,金额不能为0");
		resMap.put("501115", "返回地址过长");
		resMap.put("501116", "错误的返回地址格式");
		resMap.put("501117", "订单名称过长");
		resMap.put("501118", "订单域信息超出定义长度");
		resMap.put("501119", "订单自定义服务项过长");
		resMap.put("501120", "EMAIL地址不能空");
		resMap.put("501121", "无效的EMAIL地址");
		resMap.put("501122", "无效的手机号码");
		resMap.put("501123", "提供的消息内容过长");
		resMap.put("501124", "该订单未支付");
		resMap.put("501125", "该订单支付状态失败");
		resMap.put("501126", "无效订单状态,服务拒绝");
		resMap.put("501127", "该订单已经支付成功,请勿重复支付");
		resMap.put("501128", "该订单已被禁止支付");
		resMap.put("501129", "邮政编码格式错误");
		resMap.put("501130", "购买的商品列表过多,请分次购买");
		resMap.put("501131", "无效订单,或者订单参数不能识别");
		resMap.put("501132", "该支付不支持此支付金额,无效金额");
		resMap.put("501133", "不能识别的订单版本号");
		resMap.put("501604", "服务拒绝,服务未开通");
		resMap.put("511101", "安全验证,订单验证失败");
		resMap.put("511601", "服务拒绝,错误服务编号或者该服务未开通");
		resMap.put("511602", "服务拒绝,请求的服务已经中止");
		resMap.put("511603", "服务拒绝,服务未激活");
		resMap.put("601000", "中行网络故障,服务不能提供");
		resMap.put("601001", "请求银行数据签名失败，服务中止");
		resMap.put("601002", "银行网络故障,服务不能提供");
		resMap.put("601003", "请求交通银行数据签名失败，服务中止");
		resMap.put("601300", "通信超时或服务拒绝");
		resMap.put("601301", "服务拒绝");
		resMap.put("601302", "该信用卡未在发卡行开通3D验证服务");
		resMap.put("601303", "发卡行无法对该卡认证");
		resMap.put("601304", "支付验证失败，支付取消");
		resMap.put("601305", "支付验证失败,请稍后重试");
		resMap.put("601306", "持卡人认证失败");
		resMap.put("601307", "持卡人未能成功认证或认证存在风险");
		resMap.put("601308", "与收单行通信失败,稍后重试");
		resMap.put("601500", "商户支付服务已经停止使用");
		resMap.put("601600", "请求数据时网络故障,稍后重试");
		resMap.put("700000", "您的操作超时,请重新选择支付服务");
		resMap.put("700001", "该订单多次提交,已禁止继续支付");
		resMap.put("700002", "支付结果处理失败,请与客服联系");

	}

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
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"
					+ charset);
		}
	}

	private static String transformISO2UTF(String str) {
		if (str == null) {
			return null;
		}
		try {
			return new String(str.getBytes("iso-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
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
		String v_oid = httpServletRequest.getParameter("v_oid");
		if (StringUtils.isEmpty(v_oid)) {
			return null;
		}
		return v_oid;
	}

	@Override
	public BigDecimal getPaymentAmount(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return null;
		}
		String v_amount = httpServletRequest.getParameter("v_amount");
		if (StringUtils.isEmpty(v_amount)) {
			return null;
		}
		return new BigDecimal(v_amount).divide(new BigDecimal(1)).setScale(2);
	}

	@Override
	public boolean isPaySuccess(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return false;
		}
		String v_pstatus = httpServletRequest.getParameter("v_pstatus");
		if (StringUtils.equals(v_pstatus, "20")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回参数结果 网银在线用
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	@Override
	public String returnResult(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return "";
		}
		String v_pstring = httpServletRequest.getParameter("v_pstring");
		return v_pstring;
	}

	@Override
	public Map<String, String> getParameterMap(RechargeConfig rechargeConfig,
			UserAccountRecharge userAccountRecharge,
			HttpServletRequest httpServletRequest) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String dateString = simpleDateFormat.format(userAccountRecharge
				.getCreateDate());
		String dateString2 = simpleDateFormat2.format(userAccountRecharge
				.getCreateDate());
		String totalAmountString = userAccountRecharge.getMoney()
				.multiply(new BigDecimal(1)).setScale(2).toString();// .multiply(new
																	// BigDecimal(100)).setScale(0).toString();

		String v_oid = userAccountRecharge.getTradeNo();// 商户流水号
														// 合作商户的订单号，8-20位：必须唯一，推荐：前6位为商户号，后10位为用户流水号，string类型

		String v_mid = rechargeConfig.getBargainorId();// 商户号
		String v_url = ConstantUtil.RECHARGE_SITE + v_oid;
		String key = rechargeConfig.getBargainorKey();// md5密钥（KEY）
		String remark2 = "[url:=" + ConstantUtil.RECHARGE_BACK_SITE + v_oid
				+ "]";// 底层访问地址

		String v_amount = totalAmountString;// 订单金额 【不可为空，单位：元，小数点后保留两位。】
		String v_moneytype = "CNY";// 币种
		String v_md5info = ""; // 对拼凑串MD5私钥加密后的值

		/**
		 * 
		 //************以下几项为非必填项************** String
		 * v_rcvname,v_rcvaddr,v_rcvtel,v_rcvpost,v_rcvemail,v_rcvmobile;
		 * //定义非必需参数
		 * 
		 * v_rcvname = request.getParameter("v_rcvname"); // 收货人 v_rcvaddr =
		 * request.getParameter("v_rcvaddr"); // 收货地址 v_rcvtel =
		 * request.getParameter("v_rcvtel"); // 收货人电话 v_rcvpost =
		 * request.getParameter("v_rcvpost"); // 收货人邮编 v_rcvemail =
		 * request.getParameter("v_rcvemail"); // 收货人邮件 v_rcvmobile =
		 * request.getParameter("v_rcvmobile"); // 收货人手机号
		 * 
		 * String v_ordername,v_orderaddr,v_ordertel,v_orderpost,v_orderemail,
		 * v_ordermobile;
		 * 
		 * v_ordername = request.getParameter("v_ordername"); // 订货人姓名
		 * v_orderaddr = request.getParameter("v_orderaddr"); // 订货人地址
		 * v_ordertel = request.getParameter("v_ordertel"); // 订货人电话 v_orderpost
		 * = request.getParameter("v_orderpost"); // 订货人邮编 v_orderemail =
		 * request.getParameter("v_orderemail"); // 订货人邮件 v_ordermobile =
		 * request.getParameter("v_ordermobile"); // 订货人手机号
		 * 
		 * String remark1 = request.getParameter("remark1"); //备注字段1
		 */

		StringBuffer signStringBuffer = new StringBuffer();
		signStringBuffer.append(v_amount);
		signStringBuffer.append(v_moneytype);
		signStringBuffer.append(v_oid);
		signStringBuffer.append(v_mid);
		signStringBuffer.append(v_url);
		signStringBuffer.append(key);
		System.out.println("加密前：" + signStringBuffer.toString());
		logPayment.debug("加密前：" + signStringBuffer.toString());
		v_md5info = md5(signStringBuffer.toString()).toUpperCase();// 得出的32位MD5值需转化为大写
		System.out.println("加密后：" + v_md5info);
		logPayment.debug("加密后：" + v_md5info);

		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("v_md5info", v_md5info);
		parameterMap.put("v_mid", v_mid);
		parameterMap.put("v_oid", v_oid);
		parameterMap.put("v_amount", v_amount);
		parameterMap.put("v_moneytype", v_moneytype);
		parameterMap.put("v_url", v_url);
		parameterMap.put("remark2", remark2);

		return parameterMap;
	}

	@Override
	public boolean verifySign(RechargeConfig rechargeConfig,
			HttpServletRequest request) {
		String key = rechargeConfig.getBargainorKey();// md5密钥（KEY）

		String v_oid = request.getParameter("v_oid"); // 订单号
		String v_pmode = request.getParameter("v_pmode"); // 支付方式中文说明，如"中行长城信用卡"
		String v_pstatus = request.getParameter("v_pstatus"); // 支付结果，20支付完成；30支付失败；
		String v_pstring = request.getParameter("v_pstring"); // 对支付结果的说明，成功时（v_pstatus=20）为"支付成功"，支付失败时（v_pstatus=30）为"支付失败"
		String v_amount = request.getParameter("v_amount"); // 订单实际支付金额
		String v_moneytype = request.getParameter("v_moneytype"); // 币种
		String v_md5str = request.getParameter("v_md5str"); // MD5校验码
		String remark1 = request.getParameter("remark1"); // 备注1
		String remark2 = request.getParameter("remark2"); // 备注2

		v_md5str = transformISO2UTF(v_md5str);
		logPayment.debug("解密前：" + v_md5str);
		StringBuffer signStringBuffer = new StringBuffer();
		signStringBuffer.append(v_oid);
		signStringBuffer.append(v_pstatus);
		signStringBuffer.append(v_amount);
		signStringBuffer.append(v_moneytype);
		signStringBuffer.append(key);
		String v_md5text = md5(signStringBuffer.toString()).toUpperCase();// 计算MD5值

		logPayment.debug("解密后：" + v_md5text);
		Boolean result = null;
		if (v_md5str.compareTo(v_md5text) == 0) {
			if ("30".equals(v_pstatus)) {
				result = false;
			} else if ("20".equals(v_pstatus)) {
				result = true;
			}
		} else {
			result = false;
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
		return "ok";
	}

}
