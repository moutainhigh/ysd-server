package com.qmd.util;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class ConstantUtil {
	
	/** 网址 例:http://127.0.0.1:8080/qmd/ */
	public static final String WEB_SITE =  ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_BASEURL);
	/**
	 * 会员后台地址
	 */
	public static final String WEB_MEMBER_SITE =  ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_MEMBERURL);
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

	public static final String RECHARGE_SITE = WEB_SITE+"/payment/payreturn.do?tradeNo="; //商户返回页面地址
	
	public static final String RECHARGE_BACK_SITE = WEB_SITE+"/payment/paynotify.do?tradeNo="; //商户后台通知地址
	/**
	 * 图片上传路径(获取服务器绝对路径 不存数据库)
	 * 
	 */
	public static final String IMAGE_UPLOAD_PATH = "/data/upfiles/images/{date(yyyyMM)}";
	
	public static final int DAYS_OF_YEAR = 365;
	
	public static final String AGENCY_WEB_NAME = "乐商贷服务商业务管理平台";
	
	/**
	 * 【验证邮件模版】
	 */
	public static final String SENDEMAIL_TEMPLATE ="content/mail/mail_email_recover.ftl";

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
	/**借款手续费比例ID*/
	public static final Integer BORROW_FREE_APR = 1469;
	/**15天内的充值提现手续费费率ID*/
	public static final Integer OTHER_CASH_FEE = 1472;
	/**获取配置的提现类型值ID*/
	public static final Integer CASH_TYPE_NUMBER=1508;
	/**固定提现手续费ID*/
	public static final Integer FIXED_CASH_FEE = 1471;
	
	/**按百分比提现手续费 标识*/
	public static final String PERCENT_CASH_FEE = "withdrawal_fee";
	
	
	
	
	/**配置的最大提现金额ID*/
	public static final Integer MAX_CASH_MONEY = 1539;
	/**配置的最小提现金额ID*/
	public static final Integer MIN_CASH_MONEY = 2065;
	/** 续投奖励(月标)ID*/
	public static final Integer CONTINUE_REWARD = 1544;
	/** 续投奖励(流转标)ID*/
	public static final Integer CONTINUE_REWARD_WANDER = 1560;
	
	
	public static final String RECENT_TENDER_DAYS="recent_tender_days";
	public static final String RECENT_REPAY_DAYS="recent_repay_days";
	public static final String RECENT_TENDER_MONEY="recent_tender_money";
	public static final String RECENT_FEE_MONEY="recent_fee_money";
	public static final String BORROW_TENDER_SUBJECT_DAYS  ="borrow_tender_subject_days";
	
	
	public static final int USER_ANALYSIS_DAILY_SIZE = 15;
	public static final int HALF_MONTH_CASH_DATE = 10;//设置提现时需要查询充值的天数
	
	public static final MathContext getMathContent() {
		
		return new MathContext(2, RoundingMode.HALF_DOWN);
	}
	
	
	/**
	 * 保存登录会员用户名的Cookie名称
	 */
//	public static final String USER_USERNAME_COOKIE_NAME = "cookieUsername";

	/**
	 * 手机验证码的Cookie名称
	 */
	public static final String USER_PHONE_COOKIE_NAME = "agency_phoneCodeCookie";
	/**
	 * 保存验证码名字
	 */
	public static final String RANDOM_COOKIE_NAME ="agency_random";

	/**
	 * Memcached保存用户基本信息名称
	 */
	public static final String USER_NAME ="agency_user";
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
	
	public static final List<String> ATTACKWORD = AttackWordUtil.initWord();
	
	/**
	 * 价格精确方式
	 */
//	private static final RoundType priceRoundType;
	
	/** 机构类型：9-推广机构*/
	public static final String AGENCY_TYPE_SPREAD = "9";
	
	/** 机构类型：1-对接（配对）机构*/
	public static final String AGENCY_TYPE_PAIR = "1";
	
	
	
	
	
	
	//邮件-短信 模版名称
	/**
	 * 注册成功
	 */
	public static final String M_P_ZCCG = "zccg";
	
	
	/**
	 * 实名认证通过
	 */
	public static final String M_P_SMRZTG = "smrztg";
	
	/**
	 * 手机认证
	 */
	public static final String M_P_SJRZ = "sjrz";
	
	
	/**
	 * 重置登陆密码成功
	 */
	public static final String M_P_CZDLMMCG = "czdlmmcg";
	
	
	/**
	 * 重置安全密码成功
	 */
	public static final String M_P_CZAQMMCG = "czaqmmcg";
	
	
	/**
	 * 【月标】满标审核通过
	 */
	public static final String M_P_MBSHTG = "mbshtg";
	
	
	/**
	 * 【月标】满标审核撤销
	 */
	public static final String M_P_MBSHCX = "mbshcx";
	
	
	/**
	 * 【流转标】投标成功
	 */
	public static final String M_P_TBCG = "tbcg";
	
	/**
	 * 【投资人】【理财产品-待确认产品列表】预约完成
	 */
	public static final String M_P_YYWC = "yywc";
	
	/**
	 * 【投资人】【理财产品-待确认产品列表】预约取消
	 */
	public static final String M_P_YYQX = "yyqx";
	
	/**
	 * 【投资人】【理财产品-预约产品列表】预约撤回
	 */
	public static final String M_P_YYCH = "yych";
	
	
	/**
	 * 还款到账
	 */
	public static final String M_P_HKDZ = "hkdz";
	
	
	/**
	 * 充值成功       参数:rechargeType【   1：线上充值；
	 * 								11：线上充值（转账充值）；
	 * 								 2：【对接服务商】还款充值；
	 * 								31：【门店】POS充值；
	 * 								32：【门店】现金充值；
	 * 								33：【门店】直投充值；
	 * 							      】
	 */
	public static final String M_P_CZCG = "czcg";
	
	
	/**
	 * 充值审核不通过
	 */
	public static final String M_P_CZSHBTG = "czshbtg";
	
	
	/**
	 * 提现成功
	 */
	public static final String M_P_TXCG = "txcg";
	
	
	/**
	 * 提现审核不通过
	 */
	public static final String M_P_TXSHBTG = "txshbtg";
	
	
	/**
	 * 放款审核通过
	 */
	public static final String M_P_FKSHTG = "fkshtg";
	

	/**
	 *	【保险服务商】【理财产品】关闭预约
	 */
	public static final String M_P_LCGBYY = "lcgbyy";
	
	
	/**
	 * 【保险服务商】【理财产品】确定完成
	 */
	public static final String M_P_LCQDWC = "lcqdwc";
	
	
	/**
	 * 【保险服务商】【理财产品】撤回
	 */
	public static final String M_P_LCCH = "lcch";
	

	/**
	 * 推广用户投标获得奖励
	 */
	public static final String M_P_TGYH = "tgyh";
	
	/**
	 * 一级分成比例  百分比
	 */
	public static final String TUIGUANG_YJBL_ONE = "tuiguang_yjbl_one";
	
	/**
	 * 二级分成比例 百分比
	 */
	public static final String TUIGUANG_YJBL_TWO = "tuiguang_yjbl_two"; 
	

	/**
	 * 一级分成
	 */
	public static final String ZJLB_YJFC = "zjlb_yjfc";
	
	/**
	 * 二级分成
	 */
	public static final String ZJLB_EJFC = "zjlb_ejfc"; 
	
	/** 开关(1可用 0关闭)：注册后领取体验金，投资满1W后再领取  */
	public static  int REGISTER_TASTE_MONEY_SWITCH = 0;
	
	/** 活动：注册-会员奖励金额*/
	public static final String CAMPAIGN_REGISTER_AWARD="campaign_register_award";
	/** 资金记录：注册奖励*/
	public static final String MONEY_LOG_REGISTER_AWARD="money_log_register_award";
	/** 活动：实名认证-会员奖励金额*/
	public static final String CAMPAIGN_REALIDENTIFY_AWARD_ALL="campaign_realidentify_award_all";
	/** 资金记录：实名认证奖励*/
	public static final String MONEY_LOG_REALIDENTIFY_ALL="money_log_realidentify_all";
	/** 活动：邮箱认证-会员奖励金额*/
	public static final String CAMPAIGN_EMAIL_AWARD="campaign_email_award";
	/** 资金记录：邮箱认认证奖励*/
	public static final String MONEY_LOG_EMAIL_AWARD="money_log_email_award";
	
	/** 活动：好友实名认证-会员奖励金额*/
	public static final String CAMPAIGN_REALIDENTIFY_AWARD_USER="campaign_realIdentify_award_user";
	/** 活动：好友实名认证-好友奖励金额*/
	public static final String CAMPAIGN_REALIDENTIFY_AWARD_FIREND ="campaign_realIdentify_award_firend";
	/** 资金记录：实名认证奖励*/
	public static final String MONEY_LOG_REALIDENTIFY_USER="money_log_realIdentify_user";
	/** 资金记录：实名认证好友奖励*/
	public static final String MONEY_LOG_REALIDENTIFY_FIREND="money_log_realIdentify_firend";
	
	/** 活动：首次投资奖励-比例*/
	public static final String CAMPAIGN_FIRST_TENDER_RATIO_USER ="campaign_first_tender_ratio_user";
	/** 活动：首次投资奖励-最高额*/
	public static final String CAMPAIGN_FIRST_TENDER_TOP_USER ="campaign_first_tender_top_user";
	/** 活动：首次投资好友奖励-比例*/
	public static final String CAMPAIGN_FIRST_TENDER_RATIO_FIREND ="campaign_first_tender_ratio_firend";
	/** 活动：首次投资好友奖励-最高额*/
	public static final String CAMPAIGN_FIRST_TENDER_TOP_FIREND ="campaign_first_tender_top_firend";
	/** 资金记录：首次投资奖励*/
	public static final String MONEY_LOG_FIRST_TENDER_USER="money_log_first_tender_user";
	/** 资金记录：首次投资好友奖励*/
	public static final String MONEY_LOG_FIRST_TENDER_FIREND="money_log_first_tender_firend";
	
	/** 活动：投资后奖励解冻-比例*/
	public static final String AWARD_MONEY_TENDER_RATIO ="award_money_tender_ratio";
	/** 资金记录：投资后奖励解冻金额*/
	public static final String MONEY_LOG_AWARD_MONEY_TENDER="money_log_award_money_tender";
	
	/** 活动：首次好友认证奖励金额*/
	public static final String CAMPAIGN_REALIDENTIFY_AWARD_FIREND_FIRST ="campaign_realidentify_award_firend_first";
	/** 资金记录：首次好友认证奖励金额*/
	public static final String MONEY_LOG_REALIDENTIFY_AWARD_FIREND_FIRST ="money_log_realidentify_award_firend_first";
	
	/** 活动：绑定会员卡奖励金额*/
	public static final String CAMPAIGN_BINDING_CARD_AWARD ="campaign_binding_card_award";
	/** 资金记录：绑定会员卡奖励金额*/
	public static final String MONEY_LOG_BINDING_CARD_AWARD ="money_log_binding_card_award";
	
	/** 活动：实名，邮箱认证，投资  的奖励（给好友）*/
	public static final String CAMPAIGN_NAME_EMAIL_TENDER_AWARD ="campaign_friend_name_email_tender_award";
	/** 资金记录：推广奖励：实名，邮箱认证，投资  的奖励*/
	public static final String MONEY_LOG_NAME_EMAIL_TENDER_AWARD ="money_log_friend_name_email_tender_award";
	
}
