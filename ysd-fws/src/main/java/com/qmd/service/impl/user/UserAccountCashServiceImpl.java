package com.qmd.service.impl.user;

import com.qmd.dao.borrow.BorrowTenderDaoService;
import com.qmd.dao.user.*;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.user.UserAccount;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.UserAccountCashService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Service实现类 - 用户提现信息
 * ============================================================================
 */
@Service("userAccountCashService")
public class UserAccountCashServiceImpl extends
		BaseServiceImpl<UserAccount, Integer> implements UserAccountCashService {

	Logger log = Logger.getLogger(UserAccountCashServiceImpl.class);
	
	private Logger cashLog = Logger.getLogger("userCashLog");

	@Resource
	private ListingDao listingDao;

	@Resource
	UserRepaymentDetailDao userRepaymentDetailDao;
	@Resource
	BorrowTenderDaoService borrowTenderDaoService;
	
	@Resource
	AccountCashDao accountCashDao;
	@Resource
	UserAccountDetailDao userAccountDetailDao;
	@Resource
	UserAccountRechargeDao userAccountRechargeDao;
	@Resource
	UserAccountDao userAccountDao;

//	public CashOutBean checkUserCash(User user) {
//		CashOutBean ret = new CashOutBean();
//
//		Listing listing1 = listingDao.getListing(ConstantUtil.MAX_CASH_MONEY);
//		String maxCashMoney = listing1.getKeyValue();// 最大提现金额
//		Listing listing2 = this.listingDao
//				.getListing(ConstantUtil.MIN_CASH_MONEY);
//		String minCashMoney = listing2.getKeyValue();// 最小提现金额
//		Listing listing_11 = listingDao.getListing(ConstantUtil.OTHER_CASH_FEE);
//		String feeValue = listing_11.getKeyValue();
//		Listing listing_12 = listingDao
//				.getListing(ConstantUtil.CASH_TYPE_NUMBER);
//		String cashType = listing_12.getKeyValue();
//		Listing listing_13 = listingDao.getListing(ConstantUtil.FIXED_CASH_FEE);
//		String fixedFee = listing_13.getKeyValue();
//		
//		ret.setMaxCashMoney(maxCashMoney);// //最大提现金额
//		ret.setMinCashMoney(minCashMoney);// 最小提现金额
//
//		ret.setFeeValue(feeValue);
//		ret.setCashType(cashType);
//		ret.setFixedFee(fixedFee);
//		
//		
//		String str1 =listingDao.getKeyValue(ConstantUtil.RECENT_TENDER_DAYS);
//		ret.setRecentTenderDays(Integer.parseInt(str1));
//		String str2 =listingDao.getKeyValue(ConstantUtil.RECENT_REPAY_DAYS);
//		ret.setRecentRepayDays(Integer.parseInt(str2));
//		String str3 =listingDao.getKeyValue(ConstantUtil.RECENT_TENDER_MONEY);
//		ret.setRecentTenderMoney(new BigDecimal(str3));
//		String str4 =listingDao.getKeyValue(ConstantUtil.RECENT_FEE_MONEY);
//		ret.setRecentFeeMoney(new BigDecimal(str4));
//
//		int dateInt = CommonUtil.getIntDate(CommonUtil.getDateBefore(
//				new Date(), 1));
//		Date todayBegin = CommonUtil.date2begin(new Date());
//		Date todayEnd = CommonUtil.date2end(new Date());
//
//		// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
//		// *********获取的用户昨天可用额******************
//		BigDecimal yestodayAbleMoney = BigDecimal.ZERO;// 昨日用户可用金额
//		Map<String, Object> map1 = new HashMap<String, Object>();
//		map1.put("userId", user.getId());
//		map1.put("dailyDate", dateInt);
//		ReportUserAccountBackup reportAccount = reportUserAccountBackupDao
//				.queryOneByMap(map1);
//		if (reportAccount != null) {
//			yestodayAbleMoney = reportAccount.getAbleMoney();// 获取的用户昨天可用额
//		}
//
//		// *********获取提现当日还款******************
//		double repayAccount = 0;
//		Map<String, Object> map2 = new HashMap<String, Object>();
//		map2.put("userId", user.getId());
//		map2.put("status", 1);
//		map2.put("startDate", todayBegin);
//		map2.put("finishDate", new Date());
//
//		List<UserRepaymentDetail> reportAccountList = userRepaymentDetailDao
//				.queryUserRepaymentDetailAnalysis(map2);
//		if (reportAccountList.size() > 0) {
//			for (UserRepaymentDetail userRepaymentDetail : reportAccountList) {
////				if (!"1".equals(userRepaymentDetail.getApplyContinueTotal())
////						|| Integer.valueOf(userRepaymentDetail
////								.getApplyContinueTotal()) != 1) {
//					repayAccount += Double.parseDouble(userRepaymentDetail.getWaitCapitalNormal()) + Double.parseDouble(userRepaymentDetail.getWaitInterest());
////					repayAccount += Double.parseDouble(userRepaymentDetail
////							.getRepaymentAccount());
////				}
//			}
//		}
//		
//		//*********当日贴息*************
//		Map<String, Object> map01 = new HashMap<String, Object>();
//		map01.put("userId", user.getId());
//		map01.put("minDate", todayBegin);
//		map01.put("maxDate", new Date());
//		
//		List<BorrowTender> borrowTenderList = borrowTenderDaoService.getTenderDetailByUserid(map01);
//		if (borrowTenderList!=null && borrowTenderList.size() > 0) {
//			for (BorrowTender bt:borrowTenderList) {
//				repayAccount +=(bt.getTiexiFee()==null?0:bt.getTiexiFee().doubleValue());
//			}
//		}
//
//		// ***********今日申请中和提现成功以及处理中的提现记录******************
//		double todayCashAccount = 0;
//		Map<String, Object> map3 = new HashMap<String, Object>();
//		map3.put("userId", user.getId());
//		map3.put("beginDate", todayBegin);
//		map3.put("endDate", new Date());
//		int[] array3 = new int[] { 0, 1, 4 };
//		map3.put("array", array3);
//		List<AccountCash> accountCashList3 = accountCashDao.gainCashLish(map3);// 提现成功的
//		if (accountCashList3.size() > 0) {
//			for (AccountCash accountCash : accountCashList3) {
//				todayCashAccount += CommonUtil.bigDecimal2Double(accountCash
//						.getTotal());
//			}
//		}
//
//		// ******************查询用户撤销的或后台审核不通过的操作撤销日前一天申请提现记录*******************************
//		Map<String, Object> map4 = new HashMap<String, Object>();
//		map4.put("userId", user.getId());
//		map4.put("firstDate", todayBegin);
//		map4.put("lastDate", new Date());
//		map4.put("backDate", todayBegin);
//		int[] array4 = new int[] { 2, 3 };
//		map4.put("array", array4);
//		double recallAccount = 0;
//		List<AccountCash> accountCashList4 = accountCashDao.gainCashLish(map4);// 今天0点之前申请的并现在取消的提现。
//		if (accountCashList4.size() > 0) {
//			for (AccountCash accountCash : accountCashList4) {
//				recallAccount += CommonUtil.bigDecimal2Double(accountCash
//						.getTotal());
//			}
//		}
//
//		// *******************************************奖励***********************************************
//		// ***投标奖励***
//		BigDecimal today_award_add = userAccountDetailDao.getSumMoney(
//				user.getId(), "award_add", todayBegin, todayEnd);
//		if (today_award_add == null) {
//			today_award_add = BigDecimal.valueOf(0);
//		}
//		// ***转账充值奖励***
//		BigDecimal today_recharge_offline_reward = userAccountDetailDao
//				.getSumMoney(user.getId(), "recharge_offline_reward",
//						todayBegin, todayEnd);
//		if (today_recharge_offline_reward == null) {
//			today_recharge_offline_reward = BigDecimal.valueOf(0);
//		}
//		// ***大后台奖励***
//		BigDecimal today_offline_reward = userAccountDetailDao.getSumMoney(
//				user.getId(), "offline_reward", todayBegin, todayEnd);
//		if (today_offline_reward == null) {
//			today_offline_reward = BigDecimal.valueOf(0);
//		}
//		// ***续单奖励***
//		BigDecimal today_award_continued = userAccountDetailDao.getSumMoney(
//				user.getId(), "award_continued", todayBegin, todayEnd);
//		if (today_award_continued == null) {
//			today_award_continued = BigDecimal.valueOf(0);
//		}
//
//		// *****续单转出金额***
//		BigDecimal today_roll_out_money = userAccountDetailDao.getSumMoney(
//				user.getId(), "roll_out_money", todayBegin, todayEnd);
//		if (today_roll_out_money == null) {
//			today_roll_out_money = BigDecimal.valueOf(0);
//		}
//
//		//*****债权相关金额*****
//		
//		//债权出让金转入
//		BigDecimal today_seller_bj_in = userAccountDetailDao.getSumMoney(
//				user.getId(), "seller_bj_in ", todayBegin, todayEnd);
//		if (today_seller_bj_in == null) {
//			today_seller_bj_in = BigDecimal.valueOf(0);
//		}
//		
//		//扣除预付利息
//		BigDecimal today_seller_lx_out_to_buyer = userAccountDetailDao.getSumMoney(
//				user.getId(), "seller_lx_out_to_buyer ", todayBegin, todayEnd);
//		if (today_seller_lx_out_to_buyer == null) {
//			today_seller_lx_out_to_buyer = BigDecimal.valueOf(0);
//		}
//
//		//已赚利息转入
//		BigDecimal today_seller_lx_in_by_buyer = userAccountDetailDao.getSumMoney(
//				user.getId(), "seller_lx_in_by_buyer ", todayBegin, todayEnd);
//		if (today_seller_lx_in_by_buyer == null) {
//			today_seller_lx_in_by_buyer = BigDecimal.valueOf(0);
//		}
//
//		//预付利息转入
//		BigDecimal today_buyer_lx_in_to_buyer = userAccountDetailDao.getSumMoney(
//				user.getId(), "buyer_lx_in_to_buyer ", todayBegin, todayEnd);
//		if (today_buyer_lx_in_to_buyer == null) {
//			today_buyer_lx_in_to_buyer = BigDecimal.valueOf(0);
//		}
//
//		//已赚利息转出
//		BigDecimal today_buyer_lx_out_to_seller = userAccountDetailDao.getSumMoney(
//				user.getId(), "buyer_lx_out_to_seller ", todayBegin, todayEnd);
//		if (today_buyer_lx_out_to_seller == null) {
//			today_buyer_lx_out_to_seller = BigDecimal.valueOf(0);
//		}
//
//		//转让手续费
//		BigDecimal today_zqzr_fee = userAccountDetailDao.getSumMoney(
//				user.getId(), "zqzr_fee", todayBegin, todayEnd);
//		if (today_zqzr_fee == null) {
//			today_zqzr_fee = BigDecimal.valueOf(0);
//		}
//
//		//转让奖励
//		BigDecimal today_zqzr_jiangli = userAccountDetailDao.getSumMoney(
//				user.getId(), "zqzr_jiangli ", todayBegin, todayEnd);
//		if (today_zqzr_jiangli == null) {
//			today_zqzr_jiangli = BigDecimal.valueOf(0);
//		}
//		//红包转出
//		BigDecimal today_award_move = userAccountDetailDao.getSumMoney(
//				user.getId(), ConstantUtil.MONEY_LOG_AWARD_MONEY_TENDER, todayBegin, todayEnd);
//		if (today_award_move == null) {
//			today_award_move = BigDecimal.ZERO;
//		}
//		
//		BigDecimal tg_one = userAccountDetailDao.getSumMoney(
//				user.getId(), ConstantUtil.ZJLB_YJFC, todayBegin, todayEnd);
//		if (tg_one == null) {
//			tg_one = BigDecimal.valueOf(0);
//		}
//		
//		BigDecimal tg_two = userAccountDetailDao.getSumMoney(
//				user.getId(),  ConstantUtil.ZJLB_EJFC, todayBegin, todayEnd);
//		if (tg_two == null) {
//			tg_two = BigDecimal.valueOf(0);
//		}
//		
//		
///*		//好友用户实名认证奖励
//		BigDecimal today_realIdentify_user = userAccountDetailDao.getSumMoney(
//				user.getId(), ConstantUtil.MONEY_LOG_REALIDENTIFY_USER, todayBegin, todayEnd);
//		if (today_realIdentify_user == null) {
//			today_realIdentify_user = BigDecimal.ZERO;
//		}
//		//推荐好友实名认证好友奖励
//		BigDecimal today_realIdentify_firend = userAccountDetailDao.getSumMoney(
//				user.getId(), ConstantUtil.MONEY_LOG_REALIDENTIFY_FIREND, todayBegin, todayEnd);
//		if (today_realIdentify_firend == null) {
//			today_realIdentify_firend = BigDecimal.ZERO;
//		}*/
//		//首次投资奖励
//		BigDecimal today_first_tender_user = userAccountDetailDao.getSumMoney(
//				user.getId(), ConstantUtil.MONEY_LOG_FIRST_TENDER_USER, todayBegin, todayEnd);
//		if (today_first_tender_user == null) {
//			today_first_tender_user = BigDecimal.ZERO;
//		}
//		//首次投资好友奖励
//		BigDecimal today_first_tender_firend = userAccountDetailDao.getSumMoney(
//				user.getId(), ConstantUtil.MONEY_LOG_FIRST_TENDER_FIREND, todayBegin, todayEnd);
//		if (today_first_tender_firend == null) {
//			today_first_tender_firend = BigDecimal.ZERO;
//		}
//		
//		BigDecimal zqzr_able_cash_account = today_seller_bj_in.subtract(today_seller_lx_out_to_buyer).add(today_seller_lx_in_by_buyer)
//											.add(today_buyer_lx_in_to_buyer).subtract(today_buyer_lx_out_to_seller)
//											.subtract(today_zqzr_fee).add(today_zqzr_jiangli);
//		
////		BigDecimal zqzr_fee_cash_account = today_seller_lx_in_by_buyer.add(today_buyer_lx_out_to_seller).subtract(today_zqzr_fee).add(today_zqzr_jiangli);
//		
//		
//		
//		// ※※※※※※※※※※※※※※ ableCashAccount 可提现金额
//		double ableCashAccount = yestodayAbleMoney.doubleValue() + repayAccount
//				+ recallAccount - todayCashAccount
//				+ today_award_add.doubleValue()
//				+ today_recharge_offline_reward.doubleValue()
//				+ today_offline_reward.doubleValue()
//				+ today_award_continued.doubleValue()
//				+ today_roll_out_money.doubleValue()
//				+ zqzr_able_cash_account.doubleValue()// 最大提现额
//				+ today_award_move.doubleValue()
///*				+ today_realIdentify_user.doubleValue()
//				+ today_realIdentify_firend.doubleValue()*/
//				+ today_first_tender_user.doubleValue()
//				+ today_first_tender_firend.doubleValue()
//				+ tg_one.doubleValue()
//				+ tg_two.doubleValue();
//		
//		log.info("[" + user.getUsername() + "]最大可提现：" + ableCashAccount + "="
//				+ yestodayAbleMoney.doubleValue() + "+" + repayAccount + "+"
//				+ recallAccount + "-" + todayCashAccount + "+"
//				+ today_award_add.doubleValue() + "+"
//				+ today_recharge_offline_reward.doubleValue() + "+"
//				+ today_offline_reward.doubleValue() + "+"
//				+ today_award_continued.doubleValue() + "+"
//				+ today_roll_out_money.doubleValue()+ "+"
//				+ today_award_move.doubleValue()+ "+"
///*				+ today_realIdentify_user.doubleValue()+ "+"
//				+ today_realIdentify_firend.doubleValue()+ "+"*/
//				+ today_first_tender_user.doubleValue()+ "+"
//				+ today_first_tender_firend.doubleValue()+ "+"
//				+ tg_one.doubleValue()+"+"
//				+ tg_two.doubleValue());
//
//		if (ableCashAccount < 0) {
//			ableCashAccount = 0.00;
//		} else if (ableCashAccount > user.getAbleMoney().doubleValue()) {
//			ableCashAccount = user.getAbleMoney().doubleValue();
//		}
//		// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
//
//		// ※※※※※获取用户免费金额※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
//		Map<String, Object> map5 = new HashMap<String, Object>();
//		map5.put("userId", user.getId());
//		int[] array5 = { 0, 4 };
//		map5.put("array", array5);
//		List<AccountCash> accountCashList5 = accountCashDao.gainCashLish(map5);// 申请中的和处理中的
//		double handlingCashMoney = 0;// 申请中或者处理中的提现金额
//		if (accountCashList5.size() > 0) {
//			for (AccountCash accountCash : accountCashList5) {
//				handlingCashMoney += CommonUtil.bigDecimal2Double(accountCash
//						.getTotal());
//			}
//		}
//
//		BigDecimal sum = new BigDecimal(0.00);
//		Date endTime = new Date();
//		Date startTime = CommonUtil.getDateBefore(endTime,
//				ConstantUtil.HALF_MONTH_CASH_DATE);
//		Map<String, Object> map6 = new HashMap<String, Object>();
//		map6.put("id", user.getId());
//		map6.put("startTime", startTime);
//		map6.put("endTime", endTime);
//		map6.put("status", 1);
//		List<UserAccountRecharge> userAccRechargeList = userAccountRechargeDao
//				.getUserAccountRechargeList(map6);
//
//		// *************15天充值金额************
//		for (UserAccountRecharge u : userAccRechargeList) {
//			sum = sum.add(u.getMoney());
//		}
//		user.setHalfMonthyMoney(sum);
//
//		double freeCashMoney = 0;// 免费提现金额
//		// ******************************************
//		freeCashMoney = user.getTotal().doubleValue() - sum.doubleValue()
//				- handlingCashMoney;// 总免费提现额
//
//		if (freeCashMoney < 0) {
//			freeCashMoney = 0;
//		}
//		
//		
//
//		// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
//		double ableRecharge = 0;
////		if ("0".equals(cashType)) {
//			if (user.getAbleMoney().doubleValue() < ableCashAccount) {
//				ableRecharge = user.getAbleMoney().doubleValue();
//			} else {
//				ableRecharge = ableCashAccount;
//			}
////		}
//
//		ret.setAbleCashMoney(ableRecharge);// 可提现金额
//		ret.setAbleRecharge(freeCashMoney);// 免费提现总额
//		
//		//※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
//		// 180天内投资
//		
//		Map<String,Object> map7 = new HashMap<String,Object>();
//		map7.put("userId", user.getId());
//		map7.put("beginDate", CommonUtil.getDateBefore(new Date(), (ret.getRecentTenderDays()-1)));
//		BigDecimal userTenderMoney = borrowTenderDaoService.querySumTenderMoney(map7);
//		ret.setUserTenderMoney(userTenderMoney);
//		
//		Map<String,Object> map8 = new HashMap<String,Object>();
//		map8.put("userId", user.getId());
//		map8.put("beginDate", CommonUtil.getDateBefore(new Date(), (ret.getRecentTenderDays()-1)));
//		BigDecimal userRepayMoney = userRepaymentDetailDao.querySumRepayMoney(map7);
//		ret.setUserRepayMoney(userRepayMoney);
//		
//		if(userTenderMoney.compareTo(ret.getRecentTenderMoney())<0 && userRepayMoney.compareTo(BigDecimal.ZERO)<=0) {
//			ret.setUserRecentFee(ret.getRecentFeeMoney());
//		} else {
//			ret.setUserRecentFee(BigDecimal.ZERO);
//		}
//		
//		cashLog.info("[" + user.getUsername() + "]最大可提现：" + ableCashAccount + "="
//				+ yestodayAbleMoney.doubleValue() + "+" + repayAccount + "+"
//				+ recallAccount + "-" + todayCashAccount + "+"
//				+ today_award_add.doubleValue() + "+"
//				+ today_recharge_offline_reward.doubleValue() + "+"
//				+ today_offline_reward.doubleValue() + "+"
//				+ today_award_continued.doubleValue() + "+"
//				+ today_roll_out_money.doubleValue()+ "+"
//				+ today_award_move.doubleValue()+ "+"
///*				+ today_realIdentify_user.doubleValue()+ "+"
//				+ today_realIdentify_firend.doubleValue()+ "+"*/
//				+ today_first_tender_user.doubleValue()+ "+"
//				+ today_first_tender_user.doubleValue()+ "+"
//				+ tg_one.doubleValue()+ "+"
//				+ tg_two.doubleValue());
//		cashLog.info("[" + user.getUsername() + "] " + ret.getAbleCashMoney()+"+" +ret.getAbleRecharge()+"+"+ret.getFeeValue());
//
//		return ret;
//
//	}

	/**
	 * 提现插入记录和冻结提现资金
	 */
//	public AccountCash cashMoney(User user, AccountCash accountCash,
//			AccountBank accountBank, String ip) {
//		UserAccount userAccount = this.userAccountDao
//				.getUserAccountByUserId(user.getId());
//		userAccount = this.userAccountDao.getForUpdate(userAccount.getId());
//		
//		user.setAbleMoney(userAccount.getAbleMoney());
//		
//		CashOutBean cob = checkUserCash(user);
//
//		if (user.getTypeId() == 1) {
//			accountCash.setAbleCashMoney(user.getAbleMoney());
//			accountCash.setFreeCashMoney(user.getAbleMoney());
//		} else if (user.getTypeId() == 0) {
//
//			if (accountCash.getTotal().doubleValue() > cob.getAbleCashMoney()) {// 提现金额大于可提现金额
//				accountCash.setRetCode(2);//2 表示提现金额大于可提现金额
//				return accountCash;
//			}
//
//			accountCash.setAbleCashMoney(BigDecimal.valueOf(cob
//					.getAbleCashMoney()));
//			accountCash.setFreeCashMoney(BigDecimal.valueOf(cob
//					.getAbleRecharge()));
//		}
//
//		BigDecimal bestMoney = BigDecimal.valueOf(cob.getAbleRecharge());// 建议提取的最佳金额(免费的额度)
//		BigDecimal fee = BigDecimal.ZERO; // 产生的费用
//		BigDecimal credited;// 实际到金额
//
//		
//		String cashType = cob.getCashType();
//
//		if (user.getTypeId() == 0) {
//
//			BigDecimal feeValue = BigDecimal.valueOf(Double.parseDouble(cob
//					.getFeeValue()));
//
//			BigDecimal firstfee = BigDecimal.valueOf(Double.parseDouble(cob
//					.getFixedFee()));
//
//			if (cashType.equals("1")) {
//				int i = accountCash.getTotal().compareTo(bestMoney);
//				if (i == 1) {
//					fee = CommonUtil.add(firstfee,
//							(CommonUtil.mul((CommonUtil.sub(
//									accountCash.getTotal(), bestMoney)),
//									feeValue)));
//				} else {
//					fee = firstfee;
//				}
//			} else if (cashType.equals("0")) {
//				fee = firstfee;
//			}
//		} else {
//			cob.setUserRecentFee(BigDecimal.ZERO);
//		}
//		
//		fee = fee.add(cob.getUserRecentFee());
//
//		credited = CommonUtil.sub(accountCash.getTotal(), fee);
//		
//		if (credited.doubleValue() <=0) {
//			accountCash.setRetCode(3); //3 到账金额少于0（提现金额少于提现手续费）
//			return accountCash;
//		}
//
//		accountCash.setUserId(user.getId());
//		accountCash.setStatus(0);
//		accountCash.setAccount(accountBank.getAccount());
//		accountCash.setBank(accountBank.getBankId());
//		accountCash.setBranch(accountBank.getBranch());
//		accountCash.setCredited(credited);
//		accountCash.setFee(fee);
//		accountCash.setAddip(ip);
//		accountCash.setCreateDate(new Date());
//		accountCash.setName(accountBank.getName());
//		this.accountCashDao.save(accountCash);
//		
//		userAccount.setAbleMoney(CommonUtil.sub(userAccount.getAbleMoney(),
//				accountCash.getTotal()));
//		userAccount.setCashMoney(CommonUtil.add(userAccount.getCashMoney(),
//				accountCash.getTotal()));
//		userAccount.setUserId(user.getId());
//		this.userAccountDao.updateAll(userAccount);
//
//		// 保存资金账户操作详细记录--提现冻结"
//		UserAccountDetail userAccountDetail = AccountDetailUtil
//				.fillUserAccountDetail("cash_frost", accountCash.getTotal(),
//						10000, "用户提现申请：" + accountCash.getTotal() + "元", ip,
//						userAccount);
//		userAccountDetailDao.save(userAccountDetail);
//		
//		accountCash.setRetCode(1); // 提现成功
//		
//		return accountCash;
//	}

}
