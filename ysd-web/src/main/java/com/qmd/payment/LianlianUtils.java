package com.qmd.payment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qmd.mode.payment.PaymentInfo;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.util.ConstantUtil;
import com.qmd.util.llpay.LLPayUtil;
import com.qmd.util.llpay.RetBean;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 *  连连支付1.0 接口
 *
 */
public class LianlianUtils extends BasePaymentProduct{
	
	

     public static String gopay_gateway = "https://yintong.com.cn/payment/authpay.htm";
     
	
	//字符编码格式，目前支持 GBK 或 UTF-8
	public static String input_charset = "UTF-8";
	public static String returnString;
	
	public static Map<String,String> resMap = new HashMap<String,String>();
	
	static{
//		resMap.put("0000", "支付成功");
//		resMap.put("0001", "系统错误");
//		resMap.put("0002", "订单超时");
//		resMap.put("0011", "系统维护");
//		resMap.put("0012", "无效商户");
//		resMap.put("0013", "余额不足");
//		resMap.put("0014", "超过支付限额");
//		resMap.put("0015", "卡号或卡密错误");
//		resMap.put("0016", "不合法的IP地址");
//		resMap.put("0017", "重复订单金额不符");
//		resMap.put("0018", "卡密已被使用");
//		resMap.put("0019", "订单金额错误");
//		resMap.put("0020", "支付的类型错误");
//		resMap.put("0021", "卡类型有误");
//		resMap.put("0022", "卡信息不完整");
//		resMap.put("0023", "卡号，卡密，金额不正确");
//		resMap.put("0024", "不能用此卡继续做交易");
//		resMap.put("0025", "订单无效");
		resMap.put("0026", "充值卡无效 ");
		resMap.put("0026", "充值卡无效 ");
		resMap.put("0026", "充值卡无效 ");
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
			String merOrderNum = httpServletRequest.getParameter("no_order");
			
			if(StringUtils.isEmpty(merOrderNum)||"".equals(merOrderNum)||"null".equals(merOrderNum)){
				returnString = LLPayUtil.readReqStr(httpServletRequest);
				System.out.println("returnString====>>>"+returnString);
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
			String money_order = httpServletRequest.getParameter("money_order");
			System.out.println("money_order====>>."+money_order);
			System.out.println("returnString====>>."+returnString);
			if (StringUtils.isEmpty(returnString)&& !"".equals(money_order) ) {
				payAmount = new BigDecimal(money_order);
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

			String resData = httpServletRequest.getParameter("result_pay");
			if(StringUtils.isNotEmpty(resData) && "SUCCESS".equals(resData)){
				return true;
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
			String payResult = httpServletRequest.getParameter("res_data");
			
			if("".equals(payResult)&&StringUtils.isNotEmpty(returnString)){
				payResult = returnString;
			}
			return payResult;
		}
		
		 /**
	     * 根据连连支付风控部门要求的参数进行构造风控参数
	     * @return
	     */
	    private String createRiskItem()
	    {
	        JSONObject riskItemObj = new JSONObject();
	        riskItemObj.put("user_info_full_name", "你好");
	        riskItemObj.put("frms_ware_category", "2009");
	        return riskItemObj.toString();
	    }
		@Override
		public Map<String, String> getParameterMap(RechargeConfig rechargeConfig,UserAccountRecharge userAccountRecharge,HttpServletRequest httpServletRequest) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = simpleDateFormat.format(userAccountRecharge.getCreateDate());
			String dateString2 = simpleDateFormat2.format(userAccountRecharge.getCreateDate());
			String totalAmountString =  userAccountRecharge.getMoney().toString();//.multiply(new BigDecimal(100)).setScale(0).toString();
			 // 构造支付请求对象
	        PaymentInfo paymentInfo = new PaymentInfo();
	        paymentInfo.setVersion("1.0");
	        paymentInfo.setOid_partner(rechargeConfig.getBargainorId());
	        paymentInfo.setUser_id(userAccountRecharge.getUserId().toString());
	        paymentInfo.setSign_type("MD5");
	        paymentInfo.setBusi_partner("101001");
	        paymentInfo.setNo_order(userAccountRecharge.getTradeNo());
	        paymentInfo.setDt_order(dateString);
	        
//	        paymentInfo.setName_goods("llpay"+userAccountRecharge.getMoney()+"");
	        paymentInfo.setName_goods(userAccountRecharge.getRemark());
	        
	        paymentInfo.setInfo_order(userAccountRecharge.getRemark());
	        paymentInfo.setMoney_order(userAccountRecharge.getMoney().toString());
	        paymentInfo.setNotify_url(ConstantUtil.RECHARGE_BACK_SITE + userAccountRecharge.getTradeNo());
	        paymentInfo.setUrl_return(ConstantUtil.RECHARGE_SITE + userAccountRecharge.getTradeNo());
	        paymentInfo.setUserreq_ip(getIpAddr(httpServletRequest).replaceAll("\\.",
	                "_"));
	        paymentInfo.setUrl_order("");
	        paymentInfo.setValid_order("10080");// 单位分钟，可以为空，默认7天

	        paymentInfo.setAcct_name(userAccountRecharge.getRealName());
	        
	        if(StringUtils.isNotEmpty(userAccountRecharge.getBankNo())){
	        	paymentInfo.setCard_no(userAccountRecharge.getBankNo());
	        	paymentInfo.setBack_url(userAccountRecharge.getBackUrl());
	        }
	       
	        String frms_ware_category = "2009";// 商品类目-实名类 - P2P 小额贷款 
			String user_info_mercht_userno = userAccountRecharge.getUserId().toString();// 用户Id
			String user_info_dt_register = userAccountRecharge.getRegister();// 用户注册时间
			String user_info_full_name = userAccountRecharge.getRealName();// 实名
			String user_info_id_no = userAccountRecharge.getCardId() ;// 身份证号
			String user_info_identify_type = "1";
			String user_info_identify_state = "1";

			String riskItem = "{\"frms_ware_category\":\"" + frms_ware_category + "\",\"user_info_mercht_userno\":\"" + user_info_mercht_userno
					+ "\",\"user_info_dt_register\":\"" + user_info_dt_register + "\",\"user_info_full_name\":\"" + user_info_full_name
					+ "\",\"user_info_id_no\":\"" + user_info_id_no + "\",\"user_info_identify_type\":\"" + user_info_identify_type
					+ "\",\"user_info_identify_state\":\"" + user_info_identify_state + "\"}";
	
	        
	        paymentInfo.setRisk_item(riskItem);
	        paymentInfo.setTimestamp(getCurrentDateTimeStr());
	        // 商戶从自己系统中获取用户身份信息（认证支付必须将用户身份信息传输给连连，且修改标记flag_modify设置成1：不可修改）
	        paymentInfo.setId_type("0");
	        paymentInfo.setId_no(userAccountRecharge.getCardId());
	        paymentInfo.setFlag_modify("1");
	        String sign = LLPayUtil.addSign(JSON.parseObject(JSON
	                .toJSONString(paymentInfo)), rechargeConfig.getDescription(),
	                rechargeConfig.getBargainorKey());
	        paymentInfo.setSign(sign);
	        
	        Map<String, String> parameterMap = new HashMap<String, String>();
	        parameterMap.put("version", paymentInfo.getVersion());
	        parameterMap.put("oid_partner", paymentInfo.getOid_partner());
	        parameterMap.put("user_id", paymentInfo.getUser_id());
	        parameterMap.put("sign_type", paymentInfo.getSign_type());
	        parameterMap.put("busi_partner", paymentInfo.getBusi_partner());
	        parameterMap.put("no_order", paymentInfo.getNo_order());
	        parameterMap.put("dt_order", paymentInfo.getDt_order());
	        parameterMap.put("name_goods", paymentInfo.getName_goods());
	        parameterMap.put("info_order", paymentInfo.getInfo_order());
	        parameterMap.put("money_order", paymentInfo.getMoney_order());
	        parameterMap.put("notify_url", paymentInfo.getNotify_url());
	        parameterMap.put("url_return", paymentInfo.getUrl_return());
	        parameterMap.put("userreq_ip", paymentInfo.getUserreq_ip());
	        parameterMap.put("url_order", paymentInfo.getUrl_order());
	        parameterMap.put("valid_order", paymentInfo.getValid_order());
	        parameterMap.put("timestamp", paymentInfo.getTimestamp());
	        parameterMap.put("sign", paymentInfo.getSign());
	        parameterMap.put("risk_item", paymentInfo.getRisk_item());
	        parameterMap.put("id_type", paymentInfo.getId_type());
	        parameterMap.put("id_no", paymentInfo.getId_no());
	        parameterMap.put("acct_name", paymentInfo.getAcct_name());
	        parameterMap.put("flag_modify", paymentInfo.getFlag_modify());
	        if(StringUtils.isNotEmpty(paymentInfo.getCard_no())){
	        	  parameterMap.put("card_no", paymentInfo.getCard_no());
	        	  parameterMap.put("back_url", paymentInfo.getBack_url());
	        }
	        
	        
	        parameterMap.put("req_url", gopay_gateway); 
			return parameterMap;
		}

		@Override
		public boolean verifySign(RechargeConfig rechargeConfig,HttpServletRequest request) {
			System.out.println("returnString====>>."+returnString);
			 String reqStr = returnString;
			System.out.println("reqStr====>>."+reqStr);
			 if (LLPayUtil.isnull(reqStr))
			 	{
		            return false;
		        }
			 try
		        {
		            if (!LLPayUtil.checkSign(reqStr, rechargeConfig.getDescription(),
			                rechargeConfig.getBargainorKey()))
		            {
		                
		                return false;
		            }
		        } catch (Exception e)
		        {
		            
		            return false;
		        }
			 
			   return true;
		}
		
		@Override
		public String getPayReturnMessage(String tradeNo) {
			return "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" /><title>页面跳转中..</title></head><body onload=\"javascript: document.forms[0].submit();\"><form action=\"" + ConstantUtil.WEB_SITE + RESULT_URL + "\"><input type=\"hidden\" name=\"tradeNo\" value=\"" + tradeNo + "\" /></form></body></html>";
		}

		@Override
		public String getPayNotifyMessage() {
			RetBean info = new RetBean();
			info.setRet_code("0000");
			info.setRet_msg("交易成功");
			return JSON.toJSONString(info);
		}
	
		/**
	     * 获取当前时间str，格式yyyyMMddHHmmss
	     * @return
	     * @author guoyx
	     */
	    public static String getCurrentDateTimeStr()
	    {
	        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	        Date date = new Date();
	        String timeString = dataFormat.format(date);
	        return timeString;
	    }

		public static String getReturnString() {
			return returnString;
		}

		public static void setReturnString(String returnString) {
			LianlianUtils.returnString = returnString;
		}
	
}
