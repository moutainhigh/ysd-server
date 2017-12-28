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
 * 宝付
 *
 */
public class BaofooUtils extends BasePaymentProduct{
	
	private Logger logPayment = Logger.getLogger("userPaymentLog");
	
    public static String gopay_gateway = ConstantUtil.BAOFOO_WEB + "/PayReceive/bankpay.aspx";
	
	//字符编码格式，目前支持 GBK 或 UTF-8
	public static String input_charset = "UTF-8";
	public static Map<String,String> resMap = new HashMap<String,String>();
	
	static{
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
	    
	    private static String transformISO2UTF(String str) {
	    	if (str==null) {
	    		return null;
	    	}
	    	try {
				return new String(str.getBytes("iso-8859-1"),"utf-8");
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
			String payAmount = httpServletRequest.getParameter("factMoney");
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
		 * @param httpServletRequest
		 * @return
		 */
		@Override
		public String returnResult(HttpServletRequest httpServletRequest){
			if (httpServletRequest == null) {
				return "";
			}
			String payResult = httpServletRequest.getParameter("resultDesc");
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
			SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = simpleDateFormat.format(userAccountRecharge.getCreateDate());
			String dateString2 = simpleDateFormat2.format(userAccountRecharge.getCreateDate());
			String totalAmountString =  userAccountRecharge.getMoney().multiply(new BigDecimal(100)).setScale(0).toString();//.multiply(new BigDecimal(100)).setScale(0).toString();
			
			String MerchantID = rechargeConfig.getBargainorId();//商户号
			String PayID = "1000";//支付方式 【手机卡支付：PayID=1 	网上银行支付：PayID=1000】
			String TradeDate = dateString;//交易时间 yyyyMMddHHmmss 格式
			String webdate = dateString2;//交易时间 yyyy-MM-dd HH:mm:ss 格式
			String TransID =  userAccountRecharge.getTradeNo();//商户流水号    合作商户的订单号，8-20位：必须唯一，推荐：前6位为商户号，后10位为用户流水号，string类型
			String OrderMoney = totalAmountString;//订单金额 【订单总金额 以分为单位，至少大于等于1】
			String ProductName = "baofoo"+userAccountRecharge.getMoney()+"";//商品名称 限制长度为不超过50位
			String Amount="1";//商品数量 
			String ProductLogo="";//产品logo
			String Username="";//支付用户名
			String Email="";//Email
			String Mobile="";//Mobile
			String AdditionalInfo=ProductName;//订单附加消息
			String Merchant_url=ConstantUtil.RECHARGE_SITE + TransID;//页面跳转地址
			String Return_url= ConstantUtil.RECHARGE_BACK_SITE + TransID;//底层访问地址
			String NoticeType="1";//1：服务器通知和页面通知。支付成功后，自动重定向到“通知商户地址” 0：只发服务器端通知，不跳转
			String Md5key = rechargeConfig.getBargainorKey();//md5密钥（KEY）
			String Md5Sign = "";
			// 生成签名
			StringBuffer signStringBuffer = new StringBuffer();
			signStringBuffer.append(MerchantID);
			signStringBuffer.append(PayID);
			signStringBuffer.append(TradeDate);
			signStringBuffer.append(TransID);
			signStringBuffer.append(OrderMoney);
			signStringBuffer.append(Merchant_url);
			signStringBuffer.append(Return_url);
			signStringBuffer.append(NoticeType);
			signStringBuffer.append(Md5key);
			
			Md5Sign = md5(signStringBuffer.toString());
			
			Map<String, String> parameterMap = new HashMap<String, String>();	
			parameterMap.put("MerchantID", MerchantID);
			parameterMap.put("PayID", PayID);
			parameterMap.put("TradeDate", TradeDate);
			parameterMap.put("TransID", TransID);
			parameterMap.put("OrderMoney", OrderMoney);
			parameterMap.put("ProductName", ProductName);
			parameterMap.put("Amount", Amount);
			parameterMap.put("ProductLogo", ProductLogo);
			parameterMap.put("Username", Username);
			parameterMap.put("Email", Email);
			parameterMap.put("Mobile", Mobile);
			parameterMap.put("AdditionalInfo", AdditionalInfo);
			parameterMap.put("Merchant_url", Merchant_url);
			parameterMap.put("Return_url",Return_url);
			parameterMap.put("Md5Sign", Md5Sign);
			parameterMap.put("NoticeType", NoticeType);
			
			return parameterMap;
		}

		@Override
		public boolean verifySign(RechargeConfig rechargeConfig,HttpServletRequest request) {
			   //验签
			   String MerchantID = request.getParameter("MerchantID");//商户号
			   String TransID = request.getParameter("TransID");//商户流水号
			   String Result = request.getParameter("Result");//支付结果
			   String resultDesc = request.getParameter("resultDesc");//支付结果描述
			   String factMoney = request.getParameter("factMoney");//实际成功金额
			   String additionalInfo = request.getParameter("additionalInfo");//订单附加消息	
			   String SuccTime = request.getParameter("SuccTime");//支付完成时间
			   String Md5Sign = request.getParameter("Md5Sign");//MD5签名
			   String Md5key = rechargeConfig.getBargainorKey();//md5密钥（KEY）
			   
			   additionalInfo = transformISO2UTF(additionalInfo);
			   
			   logPayment.debug("  签名验证："+MerchantID+"|"+TransID+"|"+Result+"|"+resultDesc+"|"+factMoney+"|"+additionalInfo+"|"+SuccTime+"|"+Md5Sign+"|"+Md5key);
			   
			    Boolean result = null;
			    StringBuffer signStringBuffer = new StringBuffer();
				signStringBuffer.append(MerchantID);
				signStringBuffer.append(TransID);
				signStringBuffer.append(Result);
				signStringBuffer.append(resultDesc);
				signStringBuffer.append(factMoney);
				signStringBuffer.append(additionalInfo);
				signStringBuffer.append(SuccTime);
				signStringBuffer.append(Md5key);
				String WaitSign = md5(signStringBuffer.toString());//计算MD5值
				if(WaitSign.compareTo(Md5Sign)==0){
					result = true;
				}else{
					result = false;
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
