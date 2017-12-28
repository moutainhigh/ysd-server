package com.qmd.payment;

import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.util.ConstantUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
/**
 * 国付宝支付
 * @author Xsf
 *
 */
public class GopayUtils extends BasePaymentProduct{
	/**
     * 网关地址 
     */
	public static String  gopay_gateway = ConstantUtil.GFB_WEB+"/PGServer/Trans/WebClientAction.do";
	 /**
     * 国付宝服务器时间，反钓鱼时使用
     */
	public static String gopay_server_time_url = ConstantUtil.GFB_WEB+"/PGServer/time";
	
	/**
     * 字符编码格式，目前支持 GBK 或 UTF-8
     */
	public static String input_charset = "UTF-8";
	
	public static Map<String,String> resMap = new HashMap<String,String>();
	
	static{
		resMap.put("0000", "交易成功");
		resMap.put("GT01", "系统内部错误");
		resMap.put("GT03", "通讯失败");
		resMap.put("GT11", "虚拟账户不存在");
		resMap.put("GT12", "用户没有注册国付宝账户");
		resMap.put("GT14", "该笔记录不存在");
		resMap.put("GT16", "缺少字段");
		resMap.put("MR01", "验证签名失败");
		resMap.put("MR02", "报文某字段格式错误");
		resMap.put("MR03", "报文某字段超出允许范围");
		resMap.put("MR04", "报文某必填字段为空");
		resMap.put("MR05", "交易类型不存在");
		resMap.put("MR06", "证书验证失败或商户校验信息验证失败");
		resMap.put("MN01", "超过今日消费上限");
		resMap.put("MN02", "超过单笔消费上限");
		resMap.put("MN03", "超过今日提现上限");
		resMap.put("MN04", "超过单笔提现上限");
		resMap.put("MN07", "余额不足");
		resMap.put("MN011", "商户上送的单笔订单交易金额超限");
		resMap.put("SM01", "商户不存在 ");
		resMap.put("SM02", "商户状态非正常");
		resMap.put("SM03", "商户不允许支付");
		resMap.put("SM04", "商户不具有该权限");
		resMap.put("SC10", "支付密码错误");
		resMap.put("SC11", "卡状态非正常");
		resMap.put("SC13", "国付宝号不正确");
		resMap.put("ST07", "支付类交易代码匹配失败");
		resMap.put("ST10", "交易重复，订单号已存在");
		resMap.put("ST12", "不允许（商户||企业）给个人转账");
		resMap.put("ST13", "不允许自己给自己支付或转帐");
		resMap.put("SU05", "不存在该用户");
		resMap.put("SU10", "用户状态非正常");
		resMap.put("SM07", "商铺不能做为交易平台提供服务");
		resMap.put("AN01", "商户域名校验失败");
		resMap.put("9999", "订单处理中");
		resMap.put("其他", "订单失败");
	}
	
	/**
	 * 获取国付宝服务器时间 用于时间戳
	 * @return 格式YYYYMMDDHHMMSS
	 */
	public static String getGopayServerTime() {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
		httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 10000); 
		GetMethod getMethod = new GetMethod(gopay_server_time_url);
		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK");  
		// 执行getMethod
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK){
				String respString = StringUtils.trim((new String(getMethod.getResponseBody(),"GBK")));
				return respString;
			}			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		return null;
	}
	
	/**
     * Convenience method to get the IP Address from client.
     * 
     * @param request the current request
     * @return IP to application
     */
    public static String getIpAddr(HttpServletRequest request) { 
    	if (request == null) return "";
    	
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
		// TODO Auto-generated method stub
		return gopay_gateway;
	}

	@Override
	public String getPaymentSn(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return null;
		}
		String merOrderNum = httpServletRequest.getParameter("merOrderNum");
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
		String tranAmt = httpServletRequest.getParameter("tranAmt");
		if (StringUtils.isEmpty(tranAmt)) {
			return null;
		}
		return new BigDecimal(tranAmt);
	}

	@Override
	public boolean isPaySuccess(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return false;
		}
		String payResult = httpServletRequest.getParameter("respCode");
		if (StringUtils.equals(payResult, "0000")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回参数结果，国付宝用
	 * @param httpServletRequest
	 * @return
	 */
	@Override
	public String returnResult(HttpServletRequest httpServletRequest){
		if (httpServletRequest == null) {
			return "";
		}
		String payResult = httpServletRequest.getParameter("respCode");
		String result="";
		for(String s:resMap.keySet()){
			if(payResult.equals(s)){
				result= resMap.get(s);
				break;
			}
		}
		return result;
	}
	
	@Override
	public Map<String, String> getParameterMap(RechargeConfig rechargeConfig,UserAccountRecharge userAccountRecharge,HttpServletRequest httpServletRequest) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = simpleDateFormat.format(userAccountRecharge.getCreateDate());
		String totalAmountString = userAccountRecharge.getMoney().toString();//.multiply(new BigDecimal(100)).setScale(0).toString();
		String version = "2.1";		// 版本号(version)
		String charset = "2";		// 字符集(charset) 1:GBK,2:UTF-8 (默认为1)
		String language = "1";		// 语言种类(language)1:ZH,2:EN(默认为1)
		String signType = "1";		// 加密方式(signType)
		String tranCode = "8888";	//交易代码(tranCode)
		String merOrderNum = userAccountRecharge.getTradeNo();							// 订单号(merOrderNum)
		String tranAmt = totalAmountString;						// 交易金额(tranAmt)
		String feeAmt = userAccountRecharge.getFee().toString();									// 手续费(feeAmt)
		String currencyType = "156";							// 币种(currencyType)
//		String frontMerUrl = ConstantUtil.WEB_SITE + "/payment/payreturn.do?tradeNo=" + tradeNo;	// 商户返回页面地址 (frontMerUrl)
		String frontMerUrl = ConstantUtil.RECHARGE_SITE + merOrderNum;// 商户返回页面地址 (frontMerUrl)
//		String backgroundMerUrl = "";//ConstantUtil.WEB_SITE + "/payment/paynotify.do"; 					//商户后台通知地址(backgroundMerUrl)
		String backgroundMerUrl = ConstantUtil.RECHARGE_BACK_SITE + merOrderNum; 					//商户后台通知地址(backgroundMerUrl)
		String tranDateTime = dateString;					//交易时间(tranDateTime)
		String merchantID = rechargeConfig.getPaymentProductId();	//测试:0000003358 rechargeConfig.getBargainorId();// 商户ID(merchantID)
		String virCardNoIn = rechargeConfig.getBargainorId();		//测试:0000000001000000584 国付宝转入账户（virCardNoIn）
		String VerficationCode = rechargeConfig.getBargainorKey();	//测试:12345678 商户校验码(VerficationCode)
		String tranIP = httpServletRequest.getRemoteAddr();  //用户浏览器IP（tranIP）
		String isRepeatSubmit="0";							//订单是否允许重复提交（isRepeatSubmit）0不允许重复	1 允许重复 默认
		String goodsName = "yonghuchongzhi";						//商品名称（goodsName）
		String goodsDetail = "yonghucongzhi"+tranAmt+"yuan";		//商品详情（goodsDetail）
		String buyerName="";			//买方姓名（buyerName）
		String buyerContact="";			//买方联系方式（buyerContact）
		String merRemark1 = "";			//商户备用信息字段1（merRemark1）
		String merRemark2 = "";			//商户备用信息字段2（merRemark2）
		String bankCode = "";			//银行代码（bankCode）
		String userType = "1";			//用户类型（userType）1（为个人支付）；2（为企业支付）。
		String gopayServerTime =getGopayServerTime();//国付宝服务器时间(时间戳防钓鱼)
		System.out.println("国付宝时间:"+gopayServerTime);
		
		//直联充值
		if(StringUtils.isNotEmpty(userAccountRecharge.getBankName())){
			bankCode=userAccountRecharge.getBankName().split(",")[1].toUpperCase();
		}
		
		// 生成签名
		StringBuffer signStringBuffer = new StringBuffer();
		signStringBuffer.append("version=["+version+"]");
		signStringBuffer.append("tranCode=["+tranCode+"]");
		signStringBuffer.append("merchantID=["+merchantID+"]");
		signStringBuffer.append("merOrderNum=["+merOrderNum+"]");
		signStringBuffer.append("tranAmt=["+tranAmt+"]");
		signStringBuffer.append("feeAmt=["+feeAmt+"]");
		signStringBuffer.append("tranDateTime=["+tranDateTime+"]");
		signStringBuffer.append("frontMerUrl=["+frontMerUrl+"]");
		signStringBuffer.append("backgroundMerUrl=["+backgroundMerUrl+"]");
		signStringBuffer.append("orderId=[]");
		signStringBuffer.append("gopayOutOrderId=[]");
		signStringBuffer.append("tranIP=["+tranIP+"]");
		signStringBuffer.append("respCode=[]");
		signStringBuffer.append("gopayServerTime=["+gopayServerTime+"]");
		signStringBuffer.append("VerficationCode=["+VerficationCode+"]");
		System.out.println("signStringBuffer="+signStringBuffer.toString());
		//signStringBuffer=version[2.1]tranCode[8888]merchantID[0000044224]merOrderNum[20121213100006]tranAmt[1]feeAmt[0]tranDateTime[20121213034940]frontMerUrl[http://localhost:8080/qmd/payment/payreturn.do?tradeNo20121213100006]backgroundMerUrl[]orderId[]gopayOutOrderId[]tranIP[127.0.0.1]respCode[]gopayServerTime[]VerficationCode[qmdai2012]

		String signValue = md5(signStringBuffer.toString());//MD5加密报文(signValue)
		System.out.println("signValue=="+signValue);
		// 参数处理
		Map<String, String> parameterMap = new HashMap<String, String>();	
		
		parameterMap.put("version", version);
		parameterMap.put("charset", charset);
		parameterMap.put("language", language);
		parameterMap.put("signType", signType);
		parameterMap.put("tranCode", tranCode);
		parameterMap.put("merchantID", merchantID);
		parameterMap.put("merOrderNum", merOrderNum);
		parameterMap.put("tranAmt", tranAmt);
		parameterMap.put("feeAmt", feeAmt);
		parameterMap.put("currencyType", currencyType);
		parameterMap.put("frontMerUrl", frontMerUrl);
		parameterMap.put("backgroundMerUrl", backgroundMerUrl);
		parameterMap.put("tranDateTime", tranDateTime);
		parameterMap.put("virCardNoIn", virCardNoIn);
		parameterMap.put("tranIP", tranIP);
		parameterMap.put("isRepeatSubmit", isRepeatSubmit);
		parameterMap.put("goodsName", goodsName);
		parameterMap.put("goodsDetail", goodsDetail);
		parameterMap.put("buyerName", buyerName);
		parameterMap.put("buyerContact", buyerContact);
		parameterMap.put("merRemark1", merRemark1);
		parameterMap.put("merRemark2", merRemark2);
		parameterMap.put("bankCode", bankCode);
		parameterMap.put("userType", userType);
		parameterMap.put("signValue", signValue);
		parameterMap.put("gopayServerTime", gopayServerTime);
		
		return parameterMap;
	}

	@Override
	public boolean verifySign(RechargeConfig rechargeConfig,HttpServletRequest request) {
		   String version = request.getParameter("version");
		   String tranCode = request.getParameter("tranCode");
		   String merchantID = request.getParameter("merchantID");
		   String merOrderNum = request.getParameter("merOrderNum");
		   String tranAmt = request.getParameter("tranAmt");
		   String feeAmt = request.getParameter("feeAmt");
		   String frontMerUrl = request.getParameter("frontMerUrl");
		   String backgroundMerUrl = request.getParameter("backgroundMerUrl");
		   String tranDateTime = request.getParameter("tranDateTime");
		   String tranIP = request.getParameter("tranIP");
		   String respCode = request.getParameter("respCode");
		   String orderId = request.getParameter("orderId");
		   String gopayOutOrderId = request.getParameter("gopayOutOrderId");
		   String VerficationCode =  rechargeConfig.getBargainorKey();
		   String signValueFromGopay = request.getParameter("signValue");
		   
		   StringBuffer plainStringBuffer = new StringBuffer();
		   plainStringBuffer.append("version=["+version+"]");
		   plainStringBuffer.append("tranCode=["+tranCode+"]");
		   plainStringBuffer.append("merchantID=["+merchantID+"]");
		   plainStringBuffer.append("merOrderNum=["+merOrderNum+"]");
		   plainStringBuffer.append("tranAmt=["+tranAmt+"]");
		   plainStringBuffer.append("feeAmt=["+feeAmt+"]");
		   plainStringBuffer.append("tranDateTime=["+tranDateTime+"]");
		   plainStringBuffer.append("frontMerUrl=["+frontMerUrl+"]");
		   plainStringBuffer.append("backgroundMerUrl=["+backgroundMerUrl+"]");
		   plainStringBuffer.append("orderId=["+orderId+"]");
		   plainStringBuffer.append("gopayOutOrderId=["+gopayOutOrderId+"]");
		   plainStringBuffer.append("tranIP=["+tranIP+"]");
		   plainStringBuffer.append("respCode=["+respCode+"]");
		   plainStringBuffer.append("gopayServerTime[]");
		   plainStringBuffer.append("VerficationCode=["+VerficationCode+"]");
		   
		   String signValue = md5(plainStringBuffer.toString()); 
		   if(signValue.equals(signValueFromGopay)){
			   return true;
			}else{
				return false;
			}
	}
	
	@Override
	public String getPayReturnMessage(String tradeNo) {
		return "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" /><title>页面跳转中..</title></head><body onload=\"javascript: document.forms[0].submit();\"><form action=\"" + ConstantUtil.WEB_SITE + RESULT_URL + "\"><input type=\"hidden\" name=\"tradeNo\" value=\"" + tradeNo + "\" /></form></body></html>";
	}

	@Override
	public String getPayNotifyMessage() {
		return null;
	}
}
