package com.qmd.payment;

import com.hnapay.gateway.client.enums.CharsetTypeEnum;
import com.hnapay.gateway.client.java.ClientSignature;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.util.ConstantUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 新生支付 
 * @author Xsf
 *
 */
public class HnapayUtils extends BasePaymentProduct{
	
    public static String gopay_gateway = ConstantUtil.XSZF_WEB + "/website/pay.htm";
	
	//字符编码格式，目前支持 GBK 或 UTF-8
	public static String input_charset = "UTF-8";
	
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
		return gopay_gateway;
	}

	@Override
	public String getPaymentSn(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return null;
		}
		String merOrderNum = httpServletRequest.getParameter("orderID");
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
		String payAmount = httpServletRequest.getParameter("payAmount");
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
		String payResult = httpServletRequest.getParameter("stateCode");
		if (StringUtils.equals(payResult, "2")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回参数结果，新生支付用
	 * @param httpServletRequest
	 * @return
	 */
	@Override
	public String returnResult(HttpServletRequest httpServletRequest){
		if (httpServletRequest == null) {
			return "";
		}
//		String payResult = httpServletRequest.getParameter("respCode");
		String result="支付失败";
//		for(String s:resMap.keySet()){
//			if(payResult.equals(s)){
//				result= resMap.get(s);
//				break;
//			}
//		}
		return result;
	}
	
	@Override
	public Map<String, String> getParameterMap(RechargeConfig rechargeConfig,UserAccountRecharge userAccountRecharge,HttpServletRequest httpServletRequest) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = simpleDateFormat.format(userAccountRecharge.getCreateDate());
		String totalAmountString =  userAccountRecharge.getMoney().multiply(new BigDecimal(100)).setScale(0).toString();//.multiply(new BigDecimal(100)).setScale(0).toString();

		String version = "2.6";		// 版本号(version)
		String serialID = userAccountRecharge.getTradeNo(); //请求流水号 serialID：
		String submitTime = dateString; //订单提交时间
		String failureTime = "" ;//默认时间为90 天
		String customerIP = ConstantUtil.WEB_SITE +"["+ httpServletRequest.getRemoteAddr()+"]";//客户下单域名及IP
		String orderDetails ="" ; //订单明细信息
		String totalAmount = totalAmountString;//订单总金额
		String type = "1000";//即时支付
		String buyerMarked = "";//付款方新生账户号
		String payType = "ALL";//付款方式	 ALL：全部（默认），ACCT_RMB：账户支付，BANK_B2C：网银B2C支付，BANK_B2B：网银B2B支付
		String orgCode="";//目标资金机构代码	
		String currencyCode="1";//交易币种	
		String directFlag = "0";//是否直连	0：非直连 （默认）,1：直连
/**
	 	当以直连方式下单(即 directFlag = 1)时，请注意以下参数与接入规范pdf文档中的定义有所不同：
		=========================================================================================================================================================
		付款方新生账户号 (buyerMarked)   必填
		目标资金机构代码 (orgCode)       必填
		付款方支付方式   (payType)       直连时必须明确指定，不能为默认值(ALL)。目前这里只能填 BANK_B2C 及、或 BANK_B2B 及、或 LARGE_CREDIT_CARD
*/
		String borrowingMarked="0";//资金来源借贷标识 0：无特殊要求（默认）,1：只借记,2：只贷记
		String couponFlag = "1";//优惠券标识 1：可用 （默认）,0：不可用
		String platformID=""; //平台商ID	 
		String returnUrl= ConstantUtil.RECHARGE_SITE + serialID;//商户回调地址	【必填 】
		String noticeUrl = ConstantUtil.RECHARGE_BACK_SITE + serialID; ;//商户通知地址	【必填 】
		String partnerID = rechargeConfig.getBargainorId();//商户ID
		String remark = "kuozhanzhiduan010203040506";//扩展字段	
		String charset = "1";//编码方式	
		String signType = "2";//签名类型	1：RSA 方式（推荐）,2：MD5 方式
		String signMsg = "";//签名字符串 
		
		StringBuffer od = new StringBuffer();
		od.append(serialID).append(",");
		od.append(totalAmountString).append(",");
		od.append("xinshengzhifu").append(",");
		od.append("chongzhi"+userAccountRecharge.getMoney()+"yuan").append(",");
		od.append("1");
		orderDetails = od.toString();
		// 生成签名
		StringBuffer signStringBuffer = new StringBuffer();
		signStringBuffer.append("version="+version);
		signStringBuffer.append("&serialID="+serialID);
		signStringBuffer.append("&submitTime="+submitTime);
		signStringBuffer.append("&failureTime="+failureTime);
		signStringBuffer.append("&customerIP="+customerIP);
		signStringBuffer.append("&orderDetails="+orderDetails);
		signStringBuffer.append("&totalAmount="+totalAmount);
		signStringBuffer.append("&type="+type);
		signStringBuffer.append("&buyerMarked="+buyerMarked);
		signStringBuffer.append("&payType="+payType);
		signStringBuffer.append("&orgCode="+orgCode);
		signStringBuffer.append("&currencyCode="+currencyCode);
		signStringBuffer.append("&directFlag="+directFlag);
		signStringBuffer.append("&borrowingMarked="+borrowingMarked);
		signStringBuffer.append("&couponFlag="+couponFlag);
		signStringBuffer.append("&platformID="+platformID);
		signStringBuffer.append("&returnUrl="+returnUrl);
		signStringBuffer.append("&noticeUrl="+noticeUrl);
		signStringBuffer.append("&partnerID="+partnerID);
		signStringBuffer.append("&remark="+remark);
		signStringBuffer.append("&charset="+charset);
		signStringBuffer.append("&signType="+signType);
		System.out.println("加密串:"+signStringBuffer.toString());
		
		signMsg = signStringBuffer.toString();
		if(signType.equals("2")){
			String pkey = rechargeConfig.getBargainorKey();
			signMsg = signMsg+"&pkey="+pkey;
			try {
				signMsg = ClientSignature.genSignByMD5(signMsg,CharsetTypeEnum.UTF8);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(signType.equals("1")){
			try {
				signMsg = ClientSignature.genSignByRSA(signMsg, CharsetTypeEnum.UTF8);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("商户的RSA公钥:"+signMsg);
		Map<String, String> parameterMap = new HashMap<String, String>();	
		
		parameterMap.put("version", version);
		parameterMap.put("serialID", serialID);
		parameterMap.put("submitTime", submitTime);
		parameterMap.put("failureTime", failureTime);
		parameterMap.put("customerIP", customerIP);
		parameterMap.put("orderDetails", orderDetails);
		parameterMap.put("totalAmount", totalAmount);
		parameterMap.put("type", type);
		parameterMap.put("buyerMarked", buyerMarked);
		parameterMap.put("payType", payType);
		parameterMap.put("orgCode", orgCode);
		parameterMap.put("currencyCode", currencyCode);
		parameterMap.put("directFlag", directFlag);
		parameterMap.put("borrowingMarked",borrowingMarked);
		parameterMap.put("couponFlag", couponFlag);
		parameterMap.put("platformID", platformID);
		parameterMap.put("returnUrl", returnUrl);
		parameterMap.put("noticeUrl", noticeUrl);
		parameterMap.put("partnerID", partnerID);
		parameterMap.put("remark", remark);
		parameterMap.put("charset", charset);
		parameterMap.put("signType", signType);
		parameterMap.put("signMsg", signMsg);
		return parameterMap;
	}

	@Override
	public boolean verifySign(RechargeConfig rechargeConfig,HttpServletRequest request) {
		//payAmount, orderID, tradeNo, remark, acquiringTime, signType, charset, stateCode, orderNo, orderAmount, signMsg, resultCode, partnerID, completeTime
		//验签
		   String orderID = request.getParameter("orderID");
		   String resultCode = request.getParameter("resultCode");
		   String stateCode = request.getParameter("stateCode");
		   String orderAmount = request.getParameter("orderAmount");
		   String payAmount = request.getParameter("payAmount");
		   String acquiringTime = request.getParameter("acquiringTime");
		   String completeTime = request.getParameter("completeTime");
		   String orderNo = request.getParameter("orderNo");
		   String partnerID = request.getParameter("partnerID");
		   String remark = request.getParameter("remark");
		   String charset = request.getParameter("charset");
		   String signType = request.getParameter("signType");
		   String signMsg = request.getParameter("signMsg");
		   
		   Boolean result = null;
		   StringBuffer signStringBuffer = new StringBuffer();
		   signStringBuffer.append("orderID="+orderID);
			signStringBuffer.append("&resultCode="+resultCode);
			signStringBuffer.append("&stateCode="+stateCode);
			signStringBuffer.append("&orderAmount="+orderAmount);
			signStringBuffer.append("&payAmount="+payAmount);
			signStringBuffer.append("&acquiringTime="+acquiringTime);
			signStringBuffer.append("&completeTime="+completeTime);
			signStringBuffer.append("&orderNo="+orderNo);
			signStringBuffer.append("&partnerID="+partnerID);
			signStringBuffer.append("&remark="+remark);
			signStringBuffer.append("&charset="+charset);
			signStringBuffer.append("&signType="+signType);
		   if(signType.equals("2")){
//			   	MD5：pkey为商户的RSA公钥
				String pkey = rechargeConfig.getBargainorKey();
				signStringBuffer.append("&pkey="+pkey);
			    try {
				   result = ClientSignature.verifySignatureByMD5(signStringBuffer.toString(), signMsg, CharsetTypeEnum.UTF8);
				} catch (Exception e) {
					e.printStackTrace();
				}
		   }else if(signType.equals("1")){
//			   RSA：网关公钥钥解签
			   try {
					result = ClientSignature.verifySignatureByRSA(signStringBuffer.toString(), signMsg, CharsetTypeEnum.UTF8);
				} catch (Exception e) {
					e.printStackTrace();
				}
		   }
		   
		   return result;
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
