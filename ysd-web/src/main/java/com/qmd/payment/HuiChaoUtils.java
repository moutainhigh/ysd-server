package com.qmd.payment;

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

public class HuiChaoUtils  extends BasePaymentProduct{

	/**
     * 网关地址 
     */
	public static String  gopay_gateway = "https://pay.ecpss.com/sslpayment";
	

	/**
     * 字符编码格式，目前支持 GBK 或 UTF-8
     */
	public static String input_charset = "UTF-8";
	
	public static Map<String,String> resMap = new HashMap<String,String>();
	
	static{
		resMap.put("1", "银行代码错误");
		resMap.put("10", "商户信息不存在");
		resMap.put("11", "加密参数MD5KEY 值有误");
		resMap.put("13", "签名验证错误");
		resMap.put("15", "商户号未开通");
		resMap.put("16", "商户通道未开通");
		resMap.put("22", "交易网址未绑定或审核通过");
		resMap.put("24", "交易流水号重复");
		resMap.put("25", "商户交易流水号重复（一天内）");
		resMap.put("26", "参数有空值");
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
		return gopay_gateway;
	}

	@Override
	public String getPaymentSn(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return null;
		}
		String order_no = httpServletRequest.getParameter("BillNo");
		if (StringUtils.isEmpty(order_no)) {
			return null;
		}
		return order_no;
	}

	@Override
	public BigDecimal getPaymentAmount(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return null;
		}
		String tranAmt = httpServletRequest.getParameter("Amount");
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
		String payResult = httpServletRequest.getParameter("Succeed");
		if (StringUtils.equals(payResult, "88")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回参数结果，
	 * @param httpServletRequest
	 * @return
	 */
	@Override
	public String returnResult(HttpServletRequest httpServletRequest){
		if (httpServletRequest == null) {
			return "";
		}
		String payResult = httpServletRequest.getParameter("Succeed");
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
		String totalAmountString = userAccountRecharge.getMoney().toString();//.multiply(new BigDecimal(100)).setScale(0).toString();
		String dateString = simpleDateFormat.format(userAccountRecharge.getCreateDate());
		
		 String MerNo=  rechargeConfig.getPaymentProductId();;   //商户ID
		   
		 String BillNo = userAccountRecharge.getTradeNo();;  //订单编号
		
		 String Amount = totalAmountString;  //支付金额
		 String MD5key = rechargeConfig.getBargainorId(); //MD5key值
		 String ReturnURL = ConstantUtil.RECHARGE_SITE+BillNo;   //页面返回地址
		
		 String AdviceURL = ConstantUtil.RECHARGE_BACK_SITE+BillNo;    //后台返回地址
		
		 String md5src;  //加密字符串    
		 md5src = MerNo +"&"+ BillNo +"&"+ Amount +"&"+ ReturnURL +"&"+ MD5key ;
		 System.out.println("加密串--"+md5src);
		 String SignInfo; //MD5加密后的字符串
		 
		 // 生成签名
		 SignInfo = md5(md5src).toUpperCase();//MD5检验结果-转大写
		
		 String  defaultBankNumber="";		//[选填]银行代码
		 String  orderTime =dateString; //[必填]交易时间：YYYYMMDDHHMMSS
		
		 String products="线上充值";// '------------------物品信息 
		 String Remark = "";
		 
	   
	    Map<String, String> sPara = new HashMap<String, String>();	
 		sPara.put("MerNo",MerNo);
		sPara.put("BillNo",BillNo);
		sPara.put("Amount", Amount);
		sPara.put("ReturnURL", ReturnURL);
		sPara.put("AdviceURL", AdviceURL);
		sPara.put("SignInfo", SignInfo);
		sPara.put("Remark", Remark);
		sPara.put("defaultBankNumber", defaultBankNumber);
		sPara.put("orderTime", orderTime);
		sPara.put("products", products);
		System.out.println("报文=="+sPara.toString());
		return sPara;
	}

	@Override
	public boolean verifySign(RechargeConfig rechargeConfig,HttpServletRequest request){
		 
		String MD5key = rechargeConfig.getBargainorId();
		
		String BillNo = request.getParameter("BillNo");
	    String Amount = request.getParameter("Amount");
	    String Succeed = request.getParameter("Succeed");
	    String md5src = BillNo+"&"+Amount+"&"+Succeed+"&"+MD5key;
	    String md5sign; //MD5加密后的字符串
	    md5sign = md5(md5src).toUpperCase();//MD5检验结果-转大写
		String SignMD5info = request.getParameter("SignMD5info");
		if(SignMD5info.equals(md5sign)){
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
