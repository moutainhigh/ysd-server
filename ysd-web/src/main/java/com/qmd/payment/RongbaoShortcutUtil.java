package com.qmd.payment;

import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.util.rongbaoConfig.ReapalConfig;
import com.qmd.util.rongbaoUtils.Decipher;
import com.qmd.util.rongbaoUtils.ReapalSubmit;
import com.qmd.util.rongbaoUtils.ReapalUtil;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RongbaoShortcutUtil {

	private Logger rongbao = Logger.getLogger("rongbaoPaymentLog");
	
	/**
	 * 融宝 用户签约绑卡 第一次
	 * @param accountBank
	 * @param user
	 * @param money
	 * @return
	 */
	public static String RongbaoPay(UserAccountRecharge userRecharge ,AccountBank accountBank,User user){
		
		  //银行卡卡号
		  String card_no = accountBank.getAccount().trim();
	  	  //姓名
	      String owner=user.getRealName().trim();
		
	  	  //会员id
	  	  String member_id = String.valueOf(user.getId()).trim();
	  	  //证件号
	  	  String cert_no =user.getCardId().trim();
	  	  //手机号
	  	  String phone = user.getPhone().trim();
	  	  //订单金额
	  	  String total_fee = userRecharge.getMoney().toString();
	  	  //商户生成的订单号
	  	  String order_no =  userRecharge.getTradeNo();
	  	  //用户ip
		  String member_ip = userRecharge.getIpUser();	  
	  	  	
	  	  	card_no="6214242710498301"; 
		  	owner="韩梅梅";
		  	member_id="12345";
		  	cert_no="210302196001012114";
		  	phone="13220482188";
		  	total_fee="100";
		  	member_ip= "192.168.1.83";
	  	  

	  	Map<String,String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.merchant_id);
		map.put("version", ReapalConfig.version);
		  map.put("card_no", card_no);
		  map.put("owner", owner);
		  map.put("cert_type", "01");//证件类型
		  map.put("cert_no", cert_no);
		  map.put("phone", phone);
		  map.put("order_no",order_no);
		  map.put("transtime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		  map.put("currency", "156");//币种，人民币
		  map.put("title", userRecharge.getRemark());//商品名称
		  map.put("body", "附加信息");//商品描述
		  map.put("member_id", member_id);
		  map.put("terminal_type", "web");//终端类型
		  map.put("terminal_info", ReapalUtil.getUUID());
		  map.put("notify_url", ReapalConfig.notify_url);
		  map.put("member_ip", member_ip);
		  map.put("seller_email", ReapalConfig.seller_email);
	      map.put("token_id", ReapalUtil.getUUID());
		  BigDecimal amount = new BigDecimal(total_fee.toString()).movePointRight(2);			
		  map.put("total_fee", amount.toString());
		  String url = "/fast/debit/portal";
		  
		String post;
		try {
			post = ReapalSubmit.buildSubmit(map, url);
			 String res = Decipher.decryptData(post);
			 System.out.println("返回结果res==========>" + res);
			 return res;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	
	/**
	 * 融宝 用户绑卡签约 (用户已绑卡 )
	 * @param accountBank
	 * @param user
	 * @param money
	 * @return
	 */
	public static String RongbaoPayAgain(UserAccountRecharge userRecharge, User user,AccountBank accountBank){
		
		String member_id =user.getId().toString().trim();
		String order_no=userRecharge.getTradeNo().trim();
		
	    //绑卡id
		String bind_id =accountBank.getBankId();
	    //金额
		String total_fee =userRecharge.getMoney().toString();
		
		member_id="12345";
		bind_id="343545";
		

		Map<String, String> map = new HashMap<String, String>();
		  map.put("merchant_id", ReapalConfig.merchant_id);
		  map.put("member_id", member_id);
	      map.put("bind_id", bind_id);
		  map.put("order_no", order_no);//充值订单号
		  map.put("transtime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		  map.put("currency", "156");
		  map.put("title", "yyyyy");
		  map.put("body", "yyyy");
		  map.put("member_id", member_id);
		  map.put("terminal_type", "mobile");
		  map.put("terminal_info", "dddss-daddd");
		  map.put("notify_url", ReapalConfig.notify_url);
		  map.put("member_ip", "192.168.1.83");
		  map.put("seller_email", ReapalConfig.seller_email);
		  map.put("total_fee", "1");
		  map.put("token_id", ReapalUtil.getUUID());

		String url = "/fast/bindcard/portal";

		String post;
		try {
			post = ReapalSubmit.buildSubmit(map, url);
			String res = Decipher.decryptData(post);
			System.out.println("返回结果res==========>" + res);
			return res;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	
	
	
	/**
	 * 融宝 支付
	 * @param orderNo
	 * @param checkCode
	 * @return
	 */
	public static String RongbaoToPay(String orderNo,String checkCode){
		
		 Map<String, String> map = new HashMap<String, String>();
		    map.put("merchant_id", ReapalConfig.merchant_id);
		    map.put("version", ReapalConfig.version);
			map.put("order_no", orderNo);
			map.put("check_code", checkCode);
			 
			String url = "/fast/pay";
	    
			String post=null;
			try {
				post = ReapalSubmit.buildSubmit(map, url);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	    System.out.println("返回结果post==========>" + post);
	    //解密返回的数据
	    try {
			String res = Decipher.decryptData(post);
			 System.out.println("返回结果res==========>" + res);
			return res;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		return null;

	}
	
	
	
	
	/**
	 * 融宝 查询用户是否绑卡了
	 * @param orderNo
	 * @param checkCode
	 * @return
	 */
	public static String RongbaoToSelectIsbindCard(UserAccountRecharge userAccountRecharge){
		
		
		
		
	    String bank_card_type = "0";//0表示储蓄卡
	  	
	    Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.merchant_id);
		map.put("version", ReapalConfig.version);
		map.put("member_id", userAccountRecharge.getUserId().toString());
		//map.put("member_id","12345");//先用测试id号
		
	
		map.put("bank_card_type", bank_card_type);
		
		String url = "/fast/bindcard/list";
		
		String post;
		try {
			post = ReapalSubmit.buildSubmit(map, url);
   
			    //解密返回的数据
			    String res = Decipher.decryptData(post);
			    System.out.println("返回结果res==========>" + res);
			
			return res;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	 
	
		return null;

	}
	
	
	
	
	/**
	 * 融宝 重新发送验证码
	 * @param orderNo
	 * @param checkCode
	 * @return
	 */
	public static String RongbaoToSendSms(String order_no){
		

		    Map<String, String> map = new HashMap<String, String>();
			map.put("merchant_id", ReapalConfig.merchant_id);
			map.put("version", ReapalConfig.version);
			map.put("order_no", order_no);
			
			String url = "/fast/sms";

			String post;
			try {
				post = ReapalSubmit.buildSubmit(map, url);
			
				    
				    //解密返回的数据
				    String res = Decipher.decryptData(post);
				    System.out.println("返回结果res==========>" + res);
				    return res;
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
			return null;
		
	}

	
	
}
