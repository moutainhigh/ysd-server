package com.qmd.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类 - 编号生成
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX787FFCC36E180DE4F1B69B523E9FA2EE
 * ============================================================================
 */

public class SerialNumberUtil {
	
	
	
	public static final String PAYMENT_SN_PREFIX = "";// 支付编号前缀
	public static final long PAYMENT_SN_FIRST = 100000L;// 支付编号起始数
	public static final long PAYMENT_SN_STEP = 1L;// 支付编号步长
	
	public static final String REFUND_SN_PREFIX = "";// 退款编号前缀
	public static final long REFUND_SN_FIRST = 100000L;// 退款编号起始数
	public static final long REFUND_SN_STEP = 1L;// 退款编号步长
	
	public static final String ORDER_ITEM_SN_PREFIX = "GB";// 订单项编号前缀
	public static final long ORDER_ITEM_SN_FIRST = 100000L;// 订单项编号起始数
	public static final long ORDER_ITEM_SN_STEP = 1L;// 订单项编号步长
	
	public static final String WEB_SITE_SN_PREFIX = "index_WB_";// 订单项编号前缀
	public static final long WEB_SITE_SN_FIRST = 100000L;// 订单项编号起始数
	public static final long WEB_SITE_SN_STEP = 1L;// 订单项编号步长
	
	public static Long lastOrderSnNumber;
	public static Long lastOrderItemSnNumber;
	public static Long lastPaymentSnNumber;
	public static Long lastRefundSnNumber;
	public static Long lastShippingSnNumber;
	public static Long lastReshipSnNumber;
	public static Long lastWebSiteSnNumber;

	static {
		
		// 支付编号
//		PaymentService paymentService = (PaymentService) SpringUtil.getBean("paymentService");
//		String lastPaymentSn = paymentService.getLastPaymentSn();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(new Date());
//		String nowDate =""+calendar.get(calendar.YEAR)+(calendar.get(calendar.MONTH)+1)+calendar.get(calendar.DAY_OF_MONTH);
//		System.out.println(nowDate);
//		if (StringUtils.isNotEmpty(lastPaymentSn)) {
//			lastPaymentSnNumber = Long.parseLong(StringUtils.removeStartIgnoreCase(lastPaymentSn, PAYMENT_SN_PREFIX));
//			if(lastPaymentSnNumber.toString().length() < 8 || !lastPaymentSnNumber.toString().substring(0, 8).equals(nowDate)){
//				Long temp =PAYMENT_SN_FIRST;
//				lastPaymentSnNumber = Long.parseLong(nowDate + temp.toString());
//			}
//		} else {
//			Long temp =PAYMENT_SN_FIRST;
//			lastPaymentSnNumber = Long.parseLong(nowDate + temp.toString());
//		}
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddHHmmss");
		lastPaymentSnNumber =Long.parseLong( simple.format(new Date()) + CommonUtil.getRandomString(5));
		// 退款编号
//		RefundService refundService = (RefundService) SpringUtil.getBean("refundServiceImpl");
//		String lastRefundSn = refundService.getLastRefundSn();
//		if (StringUtils.isNotEmpty(lastRefundSn)) {
//			lastRefundSnNumber = Long.parseLong(StringUtils.removeStartIgnoreCase(lastRefundSn, REFUND_SN_PREFIX));
//		} else {
//			lastRefundSnNumber = REFUND_SN_FIRST;
//		}
	}
	/**
	 * 生成订单项编号
	 * 
	 * @return 订单项编号
	 */
	public synchronized static String buildOrderItemSn() {
		lastOrderItemSnNumber += ORDER_ITEM_SN_STEP;
		return ORDER_ITEM_SN_PREFIX + lastOrderItemSnNumber;
	}
	
	/**
	 * 生成支付编号 - 13位
	 * 
	 * @return 支付编号
	 */
	public synchronized static String buildPaymentSn() {
		
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(new Date());
//		String nowDate =""+calendar.get(calendar.YEAR)+(calendar.get(calendar.MONTH)+1)+calendar.get(calendar.DAY_OF_MONTH);
//		
//		PaymentService paymentService = (PaymentService) SpringUtil.getBean("paymentService");
//		String lastPaymentSn = paymentService.getLastPaymentSn();
//		System.out.println("lastPaymentSn="+lastPaymentSn+" lastPaymentSnNumber"+lastPaymentSnNumber);
//		if(lastPaymentSnNumber.toString().length() < 8 || !lastPaymentSnNumber.toString().substring(0, 8).equals(nowDate)){
//			Long temp =PAYMENT_SN_FIRST;
//			lastPaymentSnNumber = Long.parseLong(nowDate + temp.toString());
//		}else{ 
//			if(Long.parseLong(lastPaymentSn) >= lastPaymentSnNumber){
//				lastPaymentSnNumber = Long.parseLong(lastPaymentSn);
//			}else{
//				Long temp =PAYMENT_SN_FIRST;
//				lastPaymentSnNumber = Long.parseLong(nowDate + temp.toString());
//			}
//		}
		
//		lastPaymentSnNumber += PAYMENT_SN_STEP;			yyyyMMddHHmmss 14位
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
		lastPaymentSnNumber = Long.parseLong( simple.format(new Date()) + CommonUtil.getRandomString(5));
		System.out.println("订单号="+lastPaymentSnNumber);
		return PAYMENT_SN_PREFIX + lastPaymentSnNumber;
	}
	
	/**
	 * 生成退款编号
	 * 
	 * @return 退款编号
	 */
//	public synchronized static String buildRefundSn() {
//		lastRefundSnNumber += REFUND_SN_STEP;
//		return REFUND_SN_PREFIX + lastRefundSnNumber;
//	}
	
	public static String fff(){
		return "fff";
	}
	
}