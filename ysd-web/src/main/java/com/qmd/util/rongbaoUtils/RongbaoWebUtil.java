package com.qmd.util.rongbaoUtils;

import com.alibaba.fastjson.JSON;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.util.rongbaoConfig.ReapalWebConfig;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class RongbaoWebUtil {

	/**
	 * 融宝 跳转网银 默认直接提交数据
	 * @param accountBank
	 * @param user
	 * @param money
	 * @return
	 */
	public static Map<String,String> RongbaoToWY(UserAccountRecharge userRecharge){
		
		
		String merchant_id=ReapalWebConfig.merchant_id;
		String seller_email = ReapalWebConfig.seller_email;
		String notify_url = ReapalWebConfig.notify_url;
		String return_url = ReapalWebConfig.return_url;
		String transtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());//交易时间
		String currency = "156";
		
		String member_ip = userRecharge.getIpUser();
		String terminal_type = "web";
		String terminal_info = ReapalUtil.getUUID();
		String key = ReapalWebConfig.key;
		String sign_type=ReapalWebConfig.sign_type;
		
		///////////////////////////////////////////////////////////////////////////////////
		
		//以下参数是需要通过下单时的订单数据传入进来获得
		//必填参数
		//请与贵网站订单系统中的唯一订单号匹配
		 String order_no =userRecharge.getTradeNo() ;
		 
		        
		//订单名称，显示在融宝支付收银台里的“商品名称”里，显示在融宝支付的交易管理的“商品名称”的列表里。
		String title ="网银充值";        
		//订单描述、订单详细、订单备注，显示在融宝支付收银台里的“商品描述”里
		String body ="网银充值";
        //支付方式 跳网银
        String pay_method="bankPay";        
        //默认网银代码（银行直列时有效）
        String default_bank="";
   
		 String member_id=userRecharge.getUserId().toString(); 

//		测试账号
//		 member_id="12345";
//		member_ip ="192.168.0.252";
		 
		//订单总金额，显示在融宝支付收银台里的“应付总额”里
        BigDecimal amount = new BigDecimal(userRecharge.getMoney().toString()).movePointRight(2);
     
		
		////构造函数，生成请求URL
       
        

		Map<String, String> sPara = new HashMap<String, String>();
		sPara.put("seller_email",seller_email);
		sPara.put("merchant_id",merchant_id);
		sPara.put("notify_url", notify_url);
		sPara.put("return_url", return_url);
		sPara.put("transtime", transtime);
		sPara.put("currency", currency);
		sPara.put("member_id", member_id);
		sPara.put("member_ip", member_ip);
        //sPara.put("terminal_type", terminal_type);
		sPara.put("terminal_info", terminal_info);
		sPara.put("sign_type", sign_type);
		sPara.put("order_no", order_no);
		sPara.put("title", title);
		sPara.put("body", body);
		sPara.put("total_fee", amount.toString());
		sPara.put("payment_type", "1");
		sPara.put("default_bank", default_bank);
		sPara.put("pay_method", pay_method);
		
		String mysign = Md5Utils.BuildMysign(sPara, key);//生成签名结果
		
		sPara.put("sign", mysign);
	      
	    String json = JSON.toJSONString(sPara);

	    Map<String, String> maps =new HashMap<String, String>();
	    try {
			 maps = DecipherWeb.encryptData(json);
			 maps.put("merchant_id", merchant_id);
			 return maps;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return maps;
      
	}	
	
}
