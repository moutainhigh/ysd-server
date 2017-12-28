package com.qmd.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class ConstantUtil {
	
	/** 网址 例:http://127.0.0.1:8080/qmd/ */
	public static final String WEB_SITE =  ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_BASEURL);
	/** /** 图片发布 例 :/qmd/img/ (要与tomcat中的context一致) */
	public static final String WEB_IMG_CONTEXT_PATH = ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_IMG_CONTEXT_PATH);//"/qmd/img";
	/** 网址 例:http://127.0.0.1:8080/qmd/img */
	public static final String WEB_IMG = ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_IMG_BASEURL);
	
	public static final String WEB_SITE_IMAGE_ENCODE_URL = WEB_SITE + "/index.img?image=";
	
	public static final String QMD_INTERCEPT_SENDREDIRECT = ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_INTERCEPT_SENDREDIRECT);
	
	public static final String GFB_WEB = "https://www.gopay.com.cn";//国付宝正式地址【  https://www.gopay.com.cn】; 测试【https://211.88.7.30】
	
	public static final String XSZF_WEB = "http://www.hnapay.com";//新生支付正式地址 【http://www.hnapay.com】;测试【http://qaapp.hnapay.com】
	
	public static final String BAOFOO_WEB = "http://paygate.baofoo.com";//宝付支付正式地址【 http://paygate.baofoo.com】; 测试【http://paytest.baofoo.com】
	
	public static final String IPS_WEB = "https://pay.ips.com.cn";//环迅支付正式地址【https://pay.ips.com.cn】;测试【https://pay.ips.net.cn】
	
	public static final String CHINA_BANK_WEB = "https://pay3.chinabank.com.cn";//网银在线正式地址【】;测试【https://pay3.chinabank.com.cn】
	
	public static final String keyPath =  ConfigUtil.getConfigUtil().get(ConfigUtil.PFX_PATH);
	public static final String keyPwd =  ConfigUtil.getConfigUtil().get(ConfigUtil.PFX_PWD);
	public static final String cerPath =  ConfigUtil.getConfigUtil().get(ConfigUtil.CER_PATH);
	public static final String dataType =  ConfigUtil.getConfigUtil().get(ConfigUtil.DATA_TYPE);
	
	
	
	public static final String RECHARGE_SITE = WEB_SITE+"/payment/payreturn.do?tradeNo="; //商户返回页面地址
	
	public static final String RECHARGE_BACK_SITE = WEB_SITE+"/payment/paynotify.do?tradeNo="; //商户后台通知地址
	
	
	/**
	 * 图片上传路径
	 * data/upfiles/images/2012-06/28/581_user_13408502796.jpg
	 */
	public static final String IMAGE_UPLOAD_PATH = "/data/upfiles/images/{date(yyyyMM)}";
	
	
	/**
	 * 【验证邮件模版】
	 */
	public static final String SENDEMAIL_TEMPLATE ="content/mail/mail_email_recover.ftl";
	
	/**
	 * 【注册验证邮箱模版】
	 */
	public static final String SENDEMAIL_TEMPLATE_REGISTER ="content/mail/mail_email_register.ftl";

	/**
	 * 【找回登录密码模版】
	 */
	public static final String SENDPASSWORD_TEMPLATE ="content/mail/mail_password_recover.ftl";
	/**
	 * 【找回安全密码模版】
	 */
	public static final String SENDPAY_PASSWORD_TEMPLATE ="content/mail/mail_pay_password_recover.ftl";
	/**
	 * 【发送还款信息给投资者】
	 */
	public static final String SENDREFUND_TEMPLATE ="content/mail/mail_repay_for_investor.ftl";
	
	/**
	 * 【借款协议发送给投资者】
	 */
	public static final String SENDAGREEMENT_TEMPLATE="content/mail/mail_borAgreement_for_investor.ftl";

	/**
	 * 【生成静态页面前所需的 header.ftl地址】
	 */
	public static final String HEADER_FTL_TEMPLATE ="content/common/header.ftl";
	/**
	 * 【生成静态页面后header.html地址】
	 */
	public static final String HEADER_HTML_TEMPLATE ="content/common/header.html";
	

	/**
	 * 【生成静态页面前所需的 footer.ftl地址】
	 */
	public static final String FOOTER_FTL_TEMPLATE ="content/common/footer.ftl";
	/**
	 * 【生成静态页面后footer.html地址】
	 */
	public static final String FOOTER_HTML_TEMPLATE ="content/common/footer.html";
	
	/**
	 * 【生成静态页面前所需的 footer.ftl地址】
	 */
	public static final String FTL_404_TEMPLATE ="content/common/404.ftl";
	/**
	 * 【生成静态页面后footer.html地址】
	 */
	public static final String HTML_404_TEMPLATE ="/404.html";
	
	/**
	 * 图片浏览路径
	 */
	public static final String  IMAGE_BROWSE_PATH = "/data/upfiles/images";
	
	/** 管理员ID */
	public static final Integer ADMIN_USER_ID = 1;// 管理员ID
	/** 管理员账户ID */
	public static final Integer ADMIN_USER_ACCOUNT_ID = 1;// 管理员账户ID
	/** 利息管理手续费比例ID */
	public static final Integer INTEREST_CHARGES = 1473;
	/** 利息管理费(天标)*/
	public static final String DEDUCTION_INTEREST_FEE_DAY = "deduction_interest_fee_day";
	/**借款手续费比例ID*/
	public static final Integer BORROW_FREE_APR = 1469;
	/**15天内的充值提现手续费费率ID*/
	public static final Integer OTHER_CASH_FEE = 1472;
	/**获取配置的提现类型值ID*/
	public static final Integer CASH_TYPE_NUMBER=1508;
	/**固定提现手续费ID*/
	public static final Integer FIXED_CASH_FEE = 1471;
	/**配置的最大提现金额ID*/
	public static final Integer MAX_CASH_MONEY = 1539;
	/**配置的最小提现金额ID*/
	public static final Integer MIN_CASH_MONEY = 2065;
	/** 续投奖励(月标)ID*/
	public static final Integer CONTINUE_REWARD = 1544;
	/** 续投奖励(流转标)ID*/
	public static final Integer CONTINUE_REWARD_WANDER = 1560;
	
	/** 推荐投资奖励比例（月标） */
	public static final Integer TUI_JIAN_AWARD = 1586;
	
	public static final int USER_ANALYSIS_DAILY_SIZE = 15;
	public static final int HALF_MONTH_CASH_DATE = 15;//设置提现时需要查询充值的天数
	
	//---------推广加------
	/** 活动：实名，邮箱认证，投资  的奖励（给好友）*/
	public static final String CAMPAIGN_NAME_EMAIL_TENDER_AWARD ="campaign_friend_name_email_tender_award";
	/** 推广奖励需要达到的投资 资金*/
	public static final String SUM_TENDER_MONEY="sum_tender_money";
	/** 资金记录：推广奖励：实名，邮箱认证，投资  的奖励类型*/
	public static final String MONEY_LOG_NAME_EMAIL_TENDER_AWARD ="money_log_friend_name_email_tender_award";
	/** 活动：邮箱认证-会员奖励金额*/
	public static final String CAMPAIGN_EMAIL_AWARD="campaign_email_award";
	/** 资金记录：邮箱认认证奖励*/
	public static final String MONEY_LOG_EMAIL_AWARD="money_log_email_award";
	/** 活动：注册-会员奖励金额*/
	public static final String CAMPAIGN_REGISTER_AWARD="campaign_register_award";
	/** 资金记录：注册奖励*/
	public static final String MONEY_LOG_REGISTER_AWARD="money_log_register_award";
	
	/** 投资转出红包比例（月标）*/
	public static final int BONUS_SCALE =1597;
	
	/** 推荐投资奖励比例（流转标） */
	public static final Integer TUI_JIAN_LIU = 1602;
	
	/** 红包转可用比例（流转标） */
	public static final Integer BONUS_SCALE_LIU = 1603;
	
	
	//---------推广加end------
	
	/** 5分钟发短信限制  **/
	public static final String PHONE_LIMIT  = "phone_limit";
	
	
	public static final MathContext getMathContent() {
		
		return new MathContext(2, RoundingMode.HALF_DOWN);
	}
	
	public static final int[][] LIST_QUERY_ACCOUNT = { { 0, 0 },
			{ 0, 100000 }, { 100000, 500000 }, { 500000, 1000000 },
			{ 1000000, 9000000 } };

	public static final  BigDecimal [][]LIST_QUERY_Rate={
    		{BigDecimal.ZERO,BigDecimal.ZERO},
    		{BigDecimal.ZERO,BigDecimal.valueOf(0.10)},
    		{BigDecimal.valueOf(0.10),BigDecimal.valueOf(0.13)},
    		{BigDecimal.valueOf(0.13),BigDecimal.valueOf(0.16)},
    		{BigDecimal.valueOf(0.16),BigDecimal.valueOf(0.20)},
    		{BigDecimal.valueOf(0.20),BigDecimal.valueOf(2.00)}
    	};
	
	public static final int[][] LIST_QUERY_LIMIT = { { 0, 0 }, { 0, 30 },
			{ 30, 90 }, { 90, 180 }, { 180, 360 },{ 360, 9999 } };

	public static final int[][] LIST_QUERY_STATUS = { { 0, 0 }, { 1, 3, 5 },
			{ 4, 7 } };
	
	//public static final BigDecimal test[2][5]={{BigDecimal.ZERO,BigDecimal.ZERO},{BigDecimal.ZERO,BigDecimal.ZERO},{BigDecimal.ZERO,BigDecimal.ZERO},{BigDecimal.ZERO,BigDecimal.ZERO},{BigDecimal.ZERO,BigDecimal.ZERO}};
	
	
	/**
	 * 保存登录会员用户名的Cookie名称
	 */
//	public static final String USER_USERNAME_COOKIE_NAME = "cookieUsername";

	/**
	 * 手机验证码的Cookie名称
	 */
	public static final String USER_PHONE_COOKIE_NAME = "phoneCodeCookie";
	/**
	 * 保存验证码名字
	 */
	public static final String RANDOM_COOKIE_NAME ="random";

	/**
	 * Memcached保存用户基本信息名称
	 */
	public static final String USER_NAME ="user";
	/**
	 * Memcached保存用户详细信息名称
	 */
//	public static final String USERINFO_NAME ="userinfo";
	
	/**
	 * 没提提交申请状态:0
	 */
	public static final int APPLY_STATUS_NO=0;
	
	/**
	 * 申请通过状态:1
	 */
	public static final int APPLY_STATUS_YES=1;
	
	/**
	 * 提交申请中状态:2
	 */
	public static final int APPLY_STATUS_ING=2;
	
	/**
	 * 找回Key分隔符
	 */
	public static final String RECOVER_KEY_SEPARATOR="_";
	
	/**
	 * Key有效时间（单位：分钟）24小时
	 */
	public static final int RECOVER_KEY_PERIOD = 1440;

	/**
	 * 限制图片大小
	 */
	public static final long IMAGE_SIZE = 2097152;

	
	/**
	 *  小数位精确方式(四舍五入、向上取整、向下取整)
	 */
	public static enum RoundType {
		roundHalfUp, roundUp, roundDown
	}
	
	/**
	 * 货币符号
	 */
	public static final String CURRENCYSIGN="￥";
	
	/**
	 * 货币单位
	 */
	public static final String CURRENCYUNIT=" ";
	
	/**
	 * 价格精确位数
	 */
	public static final Integer PRICESCALE=2;
	
	/**
	 * 最大手续费金额
	 */
	public static final double MAXFEE = 50.00;
	
	/**
	 * 价格精确方式
	 */
//	private static final RoundType priceRoundType;
	
	/** 加收手续费次数 */
	public static final String CASH_CHARGE_TIMES ="cash_charge_times";
	/** 加收手续费金额 */
	public static final String CASH_CHARGE_MONEY ="cash_charge_money";
	
}
