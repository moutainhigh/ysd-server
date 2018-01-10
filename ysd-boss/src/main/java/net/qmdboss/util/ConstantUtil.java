package net.qmdboss.util;

/**
 * 常量类 - 公用
 */
public class ConstantUtil {

	/** 管理员ID */
	public static final Integer ADMIN_USER_ID = 1;// 管理员ID
	/** 管理员账户ID */
	public static final Integer ADMIN_USER_ACCOUNT_ID = 1;// 管理员账户ID
	/** 利息管理手续费比例 */
	public static final double INTEREST_CHARGES = 0.1;
	/** 续投奖励（月标）/月 ID */
	public static final Integer DEDUCTION_AWARD_WANDER_ID = 1544;
	/** 推荐投资奖励比例 */
	public static final Integer TUI_JIAN_AWARD = 1586;
	
	/**借款手续费比例ID*/
	public static final Integer BORROW_FREE_APR = 1469;
	
	/** 利息管理费(天标)*/
	public static final String DEDUCTION_INTEREST_FEE_DAY = "deduction_interest_fee_day";
	
	/** 月标投标获得积分*/
	public static final String POINTS_TENDER_MONTH ="points_tender_Month";
	/** 天标投标获得积分*/
	public static final String POINTS_TENDER_DAY ="points_tender_day";
	
	/** 加收手续费次数 */
	public static final String CASH_CHARGE_TIMES ="cash_charge_times";
	/** 加收手续费金额 */
	public static final String CASH_CHARGE_MONEY ="cash_charge_money";
	
	/** 续投奖励设置列表 */
	public static final String CONTINUE_AWARD_RATE_LIST ="continue_award_rate_list";
	//-----------推广加--------
		/** 活动：实名认证-会员奖励金额*/
		public static final String CAMPAIGN_REALIDENTIFY_AWARD_ALL="campaign_realidentify_award_all";
		/** 资金记录：实名认证奖励*/
		public static final String MONEY_LOG_REALIDENTIFY_ALL="money_log_realidentify_all";
		/** 推广奖励需要达到的投资 资金*/
		public static final String SUM_TENDER_MONEY="sum_tender_money";
		/** 活动：实名，邮箱认证，投资  的奖励（给好友）*/
		public static final String CAMPAIGN_NAME_EMAIL_TENDER_AWARD ="campaign_friend_name_email_tender_award";
		/** 资金记录：推广奖励：实名，邮箱认证，投资  的奖励类型*/
		public static final String MONEY_LOG_NAME_EMAIL_TENDER_AWARD ="money_log_friend_name_email_tender_award";
		/** 投资转出红包比例*/
		public static final int BONUS_SCALE =1597;
	
	//-----------推广加end--------

}