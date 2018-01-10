package com.qmd.payment;

import com.qmd.baofoo.util.MapToXMLString;
import com.qmd.baofoo.util.SecurityUtil;
import com.qmd.baofoo.util.rsa.RsaCodingUtil;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.service.user.UserService;
import com.qmd.util.ConstantUtil;
import com.qmd.wap.config.PartnerConfig;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 宝付 签约认证 接口
 * 
 */
public class BaofooSignH5Utils extends BasePaymentProduct {

	private Logger logPayment = Logger.getLogger("userPaymentLog");

	@Resource
	UserService userService;
	public static String gopay_gateway = "https://gw.baofoo.com/apipay/wap"; //"https://tgw.baofoo.com/apipay/wap";测试
	String txn_sub_type = "01";// 交易子类

	String data_type = ConstantUtil.dataType;

	// 字符编码格式，目前支持 GBK 或 UTF-8
	public static String input_charset = "UTF-8";
	public static Map<String, String> resMap = new HashMap<String, String>();

	static {
		resMap.put("01", "支付成功");
		resMap.put("0000", "支付失败");
		resMap.put("0001", "系统错误");
		resMap.put("0002", "订单超时");
		resMap.put("0011", "系统维护");
		resMap.put("0012", "无效商户");
		resMap.put("0013", "余额不足");
		resMap.put("0014", "超过支付限额");
		resMap.put("0015", "卡号或卡密错误");
		resMap.put("0016", "不合法的IP地址");
		resMap.put("0017", "重复订单金额不符");
		resMap.put("0018", "卡密已被使用");
		resMap.put("0019", "订单金额错误");
		resMap.put("0020", "支付的类型错误");
		resMap.put("0021", "卡类型有误");
		resMap.put("0022", "卡信息不完整");
		resMap.put("0023", "卡号，卡密，金额不正确");
		resMap.put("0024", "不能用此卡继续做交易");
		resMap.put("0025", "订单无效");
		resMap.put("0026", "充值卡无效 ");

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
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}

	private static String transformISO2UTF(String str) {
		if (str == null) {
			return null;
		}
		try {
			return new String(str.getBytes("iso-8859-1"), "utf-8");
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
		String merOrderNum = httpServletRequest.getParameter("TransID");
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
		String payAmount = httpServletRequest.getParameter("FactMoney");
		if (StringUtils.isEmpty(payAmount)) {
			return null;
		}
		return new BigDecimal(payAmount).divide(new BigDecimal(100)).setScale(2);
	}

	@Override
	public boolean isPaySuccess(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return false;
		}
		String payResult = httpServletRequest.getParameter("Result");
		if (StringUtils.equals(payResult, "1")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回参数结果 宝付用
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	@Override
	public String returnResult(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return "";
		}
		String payResult = httpServletRequest.getParameter("resultDesc");
		String result = "";
		for (String s : resMap.keySet()) {
			if (payResult.equals(s)) {
				result = resMap.get(s);
				break;
			}
		}
		return result;
	}

	@Override
	public Map<String, String> getParameterMap(RechargeConfig rechargeConfig, UserAccountRecharge userAccountRecharge, HttpServletRequest request) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = simpleDateFormat.format(userAccountRecharge.getCreateDate());
		String dateString2 = simpleDateFormat2.format(userAccountRecharge.getCreateDate());
		String totalAmountString = userAccountRecharge.getMoney().multiply(new BigDecimal(100)).setScale(0).toString();// .multiply(new
																														// BigDecimal(100)).setScale(0).toString();
		String trade_date = simpleDateFormat.format(new Date());
		String acctName = request.getParameter("realName");// 真实姓名
		String idNo = request.getParameter("idNo");// 身份证号
		String cardNo = request.getParameter("cardNo").replaceAll("\\s*", "");// 银行卡号
		String bankId = request.getParameter("bankId");// 银行编号
		// String phone = request.getParameter("phone");// 银行卡号
		String phone = userAccountRecharge.getPhone();
		if (StringUtils.isEmpty(bankId)) {
			bankId = userAccountRecharge.getPayCode();
		}

		String version = "4.0.0.0"; // 接口版本号
		String txn_type = "03311";// 交易类型
		String input_charset = "1";// 字符集
		String language = "1";
		String TransID = userAccountRecharge.getTradeNo();// 商户流水号
															// 合作商户的订单号，8-20位：必须唯一，推荐：前6位为商户号，后10位为用户流水号，string类型
		String PageUrl = PartnerConfig.URL_PRETURN;// 页面跳转地址
		String ReturnUrl = PartnerConfig.NOTIFY_PURL;// 底层访问地址
		String TerminalID = rechargeConfig.getDescription();// "10000001"; //终端号
		String MemberID = rechargeConfig.getBargainorId();// 商户号 "100000178";//
		String XmlOrJson = sendSMS(rechargeConfig, userAccountRecharge, PageUrl, ReturnUrl, trade_date, acctName, idNo, cardNo, bankId, phone);
		Map<String, String> parameterMap = new HashMap<String, String>();
		try {
			System.out.println("[加密报文前]：" + XmlOrJson);
			// 商户私钥加密。先base64 加密，然后 RSA私钥加密
			String base64str = SecurityUtil.Base64Encode(XmlOrJson);
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			System.out.println("==--====>>>" + path + ConstantUtil.keyPath);
			String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, path + ConstantUtil.keyPath, ConstantUtil.keyPwd);
			System.out.println("[加密报文后]：" + data_content);
			parameterMap.put("version", version);
			parameterMap.put("input_charset", input_charset);
			parameterMap.put("language", language);
			parameterMap.put("data_type", data_type);
			parameterMap.put("member_id", MemberID);
			parameterMap.put("terminal_id", TerminalID);
			parameterMap.put("txn_type", txn_type);
			parameterMap.put("txn_sub_type", txn_sub_type);
			parameterMap.put("data_content", data_content);
			//parameterMap.put("back_url", ConstantUtil.WEB_SITE + "/bank/bankTo.do");
			parameterMap.put("back_url", ConstantUtil.WEB_SITE + "/jcb/#/bindCard");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return parameterMap;
	}

	@Override
	public boolean verifySign(RechargeConfig rechargeConfig, HttpServletRequest request) {
		// 验签
		String MARK = "~|~";
		String MemberID = request.getParameter("MemberID");// 商户号
		String TransID = request.getParameter("TransID");// 商户流水号
		String TerminalID = request.getParameter("TerminalID");// 商户终端号
		String Result = request.getParameter("Result");// 支付结果
		String ResultDesc = request.getParameter("ResultDesc");// 支付结果描述
		String FactMoney = request.getParameter("FactMoney");// 实际成功金额
		String AdditionalInfo = request.getParameter("AdditionalInfo");// 订单附加消息
		String SuccTime = request.getParameter("SuccTime");// 支付完成时间
		String Md5Sign = request.getParameter("Md5Sign");// MD5签名
		String Md5key = rechargeConfig.getBargainorKey();// md5密钥（KEY）"abcdefg";//

		AdditionalInfo = transformISO2UTF(AdditionalInfo);

		String md5 = "MemberID=" + MemberID + MARK + "TerminalID=" + TerminalID + MARK + "TransID=" + TransID + MARK + "Result=" + Result + MARK
				+ "ResultDesc=" + ResultDesc + MARK + "FactMoney=" + FactMoney + MARK + "AdditionalInfo=" + AdditionalInfo + MARK + "SuccTime="
				+ SuccTime + MARK + "Md5Sign=" + Md5key;

		logPayment.debug("  签名验证：" + md5);

		Boolean result = null;

		String WaitSign = md5(md5);// 计算MD5值
		if (WaitSign.compareTo(Md5Sign) == 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public String getPayReturnMessage(String tradeNo) {
		return "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" /><title>页面跳转中..</title></head><body onload=\"javascript: document.forms[0].submit();\"><form action=\""
				+ ConstantUtil.WEB_SITE + RESULT_URL + "\"><input type=\"hidden\" name=\"tradeNo\" value=\"" + tradeNo + "\" /></form></body></html>";
	}

	@Override
	public String getPayNotifyMessage() {
		return "OK";
	}

	/**
	 * 获取验证码
	 * 
	 * @param trans_id
	 *            唯一订单号，8-20
	 *            位字母和数字，同一天内不可重复；如果商户开通“发送短信类交易”，该订单号从发送短信类交易到当前交易都有效
	 * @param acc_no
	 *            绑定的卡号
	 * @param mobile
	 *            银行卡绑定手机号
	 * @param bind_id
	 *            绑定标识号
	 * @param txn_amt
	 *            交易金额 单位：例：1元则提交为100
	 * @param next_txn_sub_type
	 *            下一步进行的交易子类
	 * @param additional_info
	 *            附加字段
	 * @param req_reserved
	 *            请求方保留域 长度不超过 128 位
	 */
	private String sendSMS(RechargeConfig rechargeConfig, UserAccountRecharge userAccountRecharge, String PageUrl, String ReturnUrl,
			String trade_date, String acctName, String idNo, String cardNo, String bankId, String phone) {

		String biz_type = "0000";
		StringBuilder sb = new StringBuilder();
		System.out.println("[trans_id]:" + userAccountRecharge.getTradeNo());
		BigDecimal txn_amt_num = userAccountRecharge.getMoney().multiply(BigDecimal.valueOf(100));// 金额转换成分
		String txn_amt = String.valueOf(txn_amt_num.setScale(0));// 支付金额
		String req_reserved = "";

		Map<String, String> ArrayData = new HashMap<String, String>();

		ArrayData.put("txn_sub_type", txn_sub_type);
		ArrayData.put("biz_type", biz_type);
		ArrayData.put("terminal_id", rechargeConfig.getDescription().trim());
		ArrayData.put("member_id", rechargeConfig.getBargainorId());
		ArrayData.put("pay_code", bankId);
		ArrayData.put("acc_no", cardNo);
		ArrayData.put("id_card_type", "01");
		ArrayData.put("id_card", idNo);
		ArrayData.put("id_holder", acctName);
		ArrayData.put("mobile", phone);
		ArrayData.put("trans_id", userAccountRecharge.getTradeNo());
		ArrayData.put("txn_amt", txn_amt);
		ArrayData.put("trade_date", trade_date);
		ArrayData.put("commodity_name", "baofoo交易" + userAccountRecharge.getMoney());
		ArrayData.put("commodity_amount", "1");// 商品数量（默认为1）
		ArrayData.put("user_name", "用户名");
		ArrayData.put("page_url", PageUrl);
		ArrayData.put("return_url", ReturnUrl);
		ArrayData.put("additional_info", "附加信息");
		ArrayData.put("req_reserved", "保留");
		Map<Object, Object> ArrayData1 = new HashMap<Object, Object>();
		String XmlOrJson = "";
		if (data_type.equals("xml")) {
			ArrayData1.putAll(ArrayData);
			XmlOrJson = MapToXMLString.converter(ArrayData1, "data_content");
		} else {
			JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
			XmlOrJson = jsonObjectFromMap.toString();
		}

		return XmlOrJson;
	}

}
