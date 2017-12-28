package net.qmdboss.service.impl;

import net.qmdboss.bean.RepaymentInfo;
import net.qmdboss.dao.*;
import net.qmdboss.entity.*;
import net.qmdboss.service.BorrowPromoteService;
import net.qmdboss.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 发表待审Service实现类
 * 
 * @author Administrator
 * 
 */
@Service("borrowPromoteFlowServiceImpl")
public class BorrowPromoteFlowServiceImpl extends BaseServiceImpl<Borrow, Integer>
		implements BorrowPromoteService {
	Logger log = Logger.getLogger(BorrowPromoteFlowServiceImpl.class);

	private Logger rankLog = Logger.getLogger("bossRankLog");

	@Resource(name = "borrowDaoImpl")
	private BorrowDao borrowDao;
	@Resource(name = "listingDaoImpl")
	private ListingDao listingDao;
	@Resource(name = "userAccountDaoImpl")
	private UserAccountDao userAccountDao;
	@Resource(name = "userAccountDetailDaoImpl")
	private UserAccountDetailDao userAccountDetailDao;
	@Resource(name = "borrowRepaymentDetailDaoImpl")
	private BorrowRepaymentDetailDao borrowRepaymentDetailDao;
	@Resource(name = "userRepaymentDetailDaoImpl")
	private UserRepaymentDetailDao userRepaymentDetailDao;
	@Resource(name = "borrowDetailDaoImpl")
	private BorrowDetailDao borrowDetailDao;
	@Resource(name = "userDaoImpl")
	private UserDao userDao;

	@Resource(name = "borrowDaoImpl")
	public void setBaseDao(BorrowDao borrowDao) {
		super.setBaseDao(borrowDao);
	}

	/**
	 * 初审
	 * 
	 * @param borrow
	 * @param id
	 * @param admin
	 * @return 0 审核成功,1状态不对
	 */
	public int updateBorrowPreliminary(Borrow borrow, Integer id, Admin admin) {

		// 锁标
		Borrow borrow1 = borrowDao.loadLock(id);

		if (borrow1.getStatus() != 0) {
			return 1;// 标的状态不对
		}

		borrow1.setStatus(borrow.getStatus());
		borrow1.setVerifyRemark(borrow.getVerifyRemark());
		String admin1 = String.valueOf(admin.getId());
		borrow1.setVerifyUser(admin1);
		borrow1.setAccountYes(borrow1.getAccount());
		borrow1.setBalance(String.valueOf(borrow1.getAccount().doubleValue()));
		borrow1.setVerifyTime(new Date());
		borrow1.setSchedule("0");

		//红包使用比例
		borrow1.setAwardScale(borrow.getAwardScale());
		
		if ("0".equals(borrow1.getIsday())) {
			borrow1.setFinalRepayDate(CommonUtil.getMonthAfter(
					borrow1.getVerifyTime(),
					Integer.parseInt(borrow1.getTimeLimit())));
			borrow1.setContinueAwardRate(borrow.getContinueAwardRate() == null ? BigDecimal.ZERO
					: borrow.getContinueAwardRate());
		} else if ("1".equals(borrow1.getIsday())) {
			borrow1.setFinalRepayDate(CommonUtil.getDateAfter(
					borrow1.getVerifyTime(),
					Integer.parseInt(borrow1.getTimeLimit())));
			borrow1.setContinueAwardRate(BigDecimal.ZERO);
		}

		// 月标
		if ("0".equals(borrow1.getIsday())) {
			borrow1.setFinalRateYear(CalculationUtil.monthRealAprYear(borrow1)); // 计算收益率
			borrow1.setAutoTenderStatus(borrow.getAutoTenderStatus());

			// 判断自动投标
			if (borrow.getAutoTenderStatus() != null
					&& borrow.getAutoTenderStatus() == 1) {
				rankLog.debug("===自动投标开始===[" + borrow1.getId() + "] 还款方式["
						+ borrow1.getAutoTenderRepayWay() + "]");
				// 1 自动投标总额
				Listing autoListing = listingDao
						.getListingBySign("auto_tender_account_rate");
				BigDecimal autoBalance = borrow1.getAccount().multiply(
						new BigDecimal(autoListing.getKeyValue()));
				BigDecimal autoMoney = BigDecimal.ZERO;

				// 2 获取符合条件的投资人
				List<User> userList = new ArrayList<User>();
				BigDecimal reward = BigDecimal.ZERO;
				if ("1".equals(borrow1.getAward())) {
					reward = new BigDecimal(borrow1.getFunds());
				}
				
				List<Object> list = userDao.getViewTest(
						Integer.parseInt(borrow1.getTimeLimit()),
						BigDecimal.valueOf(borrow1.getApr()), reward);
				StringBuffer sblog = null;
				int no = 0;
				rankLog.debug("---初选开始");
				for (Object obj : list) {
					Integer uid = (Integer) obj;
					User uu = userDao.loadLock(uid);
					sblog = new StringBuffer();
					no++;
					sblog.append("   [")
							.append(no)
							.append("][")
							.append(uid)
							.append("][")
							.append(uu.getUsername())
							.append("]排名[")
							.append(CommonUtil.getDate2String(
									uu.getAutoTenderDate(),
									"yyyy-MM-dd hh:mm:ss"))
							.append("]规则[")
							.append(uu.getAutoTenderRule())
							.append("]最高[")
							.append(uu.getAutoTenderMoneyTop())
							.append("]余留[")
							.append(uu.getAutoTenderMoneyLeave())
							.append("]利率[")
							.append(uu.getAutoTenderRateBegin())
							.append("][")
							.append(uu.getAutoTenderRateEnd())
							.append("]奖励[")
							.append(uu.getAutoTenderRewardBegin())
							.append("][")
							.append(uu.getAutoTenderRateEnd())
							.append("]期限[")
							.append(uu.getAutoTenderLimitBegin())
							.append("][")
							.append(uu.getAutoTenderLimitEnd())
							.append("]方式[")
							.append(uu.getAutoTenderRepayWay())
							.append("]修改[")
							.append(CommonUtil.getDate2String(
									uu.getAutoTenderModifyDate(),
									"yyyy-MM-dd hh:mm:ss"));

					if (borrow1.getAutoTenderRepayWay() == null
							|| uu.getAutoTenderRepayWay() == null) {
						sblog.append("[OUT_1][")
								.append(uu.getAutoTenderRepayWay()).append("]");
						rankLog.debug(sblog.toString());
						continue;
					}
					String[] way = uu.getAutoTenderRepayWay().split(",");
					if (way == null || way.length != 3) {
						sblog.append("[OUT_2][")
								.append(uu.getAutoTenderRepayWay())
								.append("][").append(way.length).append("]");
						rankLog.debug(sblog.toString());
						continue;
					}

					if (borrow1.getAutoTenderRepayWay() == 1
							&& !"1".equals(way[0])) {
						sblog.append("[OUT_3][")
								.append(borrow1.getAutoTenderRepayWay())
								.append("][").append(way[0]).append("]");
						rankLog.debug(sblog.toString());
						continue;
					} else if (borrow1.getAutoTenderRepayWay() == 2
							&& !"1".equals(way[1])) {
						sblog.append("[OUT_4][")
								.append(borrow1.getAutoTenderRepayWay())
								.append("][").append(way[1]).append("]");
						rankLog.debug(sblog.toString());
						continue;
					} else if (borrow1.getAutoTenderRepayWay() == 3
							&& !"1".equals(way[2])) {
						sblog.append("[OUT_5][")
								.append(borrow1.getAutoTenderRepayWay())
								.append("][").append(way[2]).append("]");
						rankLog.debug(sblog.toString());
						continue;
					}
					sblog.append("[INN]");
					rankLog.debug(sblog.toString());

					userList.add(uu);
				}
				rankLog.debug("---初选结束 原[" + no + "] 现[" + userList.size()
						+ "]");

				Integer lowestAccount = null;
				if (StringUtils.isEmpty(borrow1.getLowestAccount())
						|| "0".equals(borrow1.getLowestAccount())) {
					lowestAccount = 100;// 最低100元
				} else {
					lowestAccount = Double.valueOf(borrow1.getLowestAccount())
							.intValue();
				}
				BigDecimal mostAccount = null;
				if (StringUtils.isEmpty(borrow1.getMostAccount())
						|| "0".equals(borrow1.getMostAccount())) {
					mostAccount = autoBalance;// 没有限制，就标的自投额度限制
				} else {
					mostAccount = BigDecimal.valueOf(Double.valueOf(borrow1
							.getMostAccount()));
				}

				// interestCalUtil = new InterestCalUtil();
				rankLog.debug("---复选开始");
				no = 0;
				for (User u : userList) {
					UserAccount ua = userAccountDao.loadLockTable(u);// 锁表

					sblog = new StringBuffer();
					no++;
					sblog.append("   [")
							.append(no)
							.append("][")
							.append(CommonUtil.getDate2String(
									u.getAutoTenderDate(),
									"yyyy-MM-dd hh:mm:ss")).append("][")
							.append(u.getId()).append("][")
							.append(u.getUsername()).append("]续投[")
							.append(ua.getContinueTotal()).append("]可用[")
							.append(ua.getAbleMoney());

					int money = (int) (ua.getAbleMoney().doubleValue() / 100)
							* 100
							+ (int) (ua.getContinueTotal().doubleValue() / 100)
							* 100;
					if (money < lowestAccount) {// 有效投资额少于最小投资额，跳过
						sblog.append("[OUT_6][").append(money).append("][")
								.append(lowestAccount).append("][有效投资少于最小投资额]");
						rankLog.debug(sblog.toString());
						continue;
					}

					// 如果设置了投标金额限制，myMoney需要重新计算
					if (u.getAutoTenderRule() != null
							&& u.getAutoTenderRule() == 1) {
						BigDecimal aa = ua.getAbleMoney().subtract(
								u.getAutoTenderMoneyLeave());
						if (aa.compareTo(BigDecimal.ZERO) != -1) {// aa >= 0
																	// 那么只扣除可用中的金额
							money = (int) (aa.doubleValue() / 100)
									* 100
									+ (int) (ua.getContinueTotal()
											.doubleValue() / 100) * 100;
						} else {
							money = (int) ((ua.getContinueTotal().add(aa))
									.doubleValue() / 100) * 100;
						}
						if (BigDecimal.valueOf(money).compareTo(
								u.getAutoTenderMoneyTop()) == 1) {
							money = (int) (u.getAutoTenderMoneyTop()
									.doubleValue() / 100) * 100;
						}
					}

					// 判断投资金额
					BigDecimal myMoney = mostAccount;
					BigDecimal tenderMoney = new BigDecimal(money);

					// 标的最大投资限额，标的自投总额，用户拥有的金额 取最小值
					if (mostAccount.compareTo(autoBalance) == 1) {
						myMoney = autoBalance;
					}
					if (myMoney.compareTo(tenderMoney) == 1) {
						myMoney = tenderMoney;
					}

					if (myMoney.compareTo(BigDecimal.ZERO) != 1) {// myMoney <=0
																	// 少于等于0 不投资
						sblog.append("[OUT_7][").append(myMoney)
								.append("][有效投资少于0]");
						rankLog.debug(sblog.toString());
						continue;
					}

					BorrowDetail borrowDetail = new BorrowDetail();
					borrowDetail.setAccount(myMoney);
					borrowDetail.setMoney(myMoney);
					if (myMoney.compareTo(BigDecimal.valueOf((int) (ua
							.getContinueTotal().doubleValue() / 100) * 100)) == 1) {
						borrowDetail
								.setContinueAmount(String
										.valueOf((int) (ua.getContinueTotal()
												.doubleValue() / 100) * 100));
						borrowDetail.setAbleAmount((myMoney.subtract(BigDecimal
								.valueOf((int) (ua.getContinueTotal()
										.doubleValue() / 100) * 100)))
								.toString());
					} else {
						borrowDetail.setContinueAmount(myMoney.toString());
						borrowDetail.setAbleAmount("0");
					}
					borrowDetail.setStatus("1");

					RepaymentInfo repaymentInfo = PromoteUtil.promotePlan(
							Integer.parseInt(borrow1.getIsday()), Integer
									.parseInt(borrow1.getStyle()), Integer
									.parseInt(borrow1.getTimeLimit()), borrow1
									.getDivides(), myMoney, new BigDecimal(
									borrow.getApr()));

					borrowDetail.setInterest(repaymentInfo.getInterest());
					borrowDetail.setRepaymentAccount(borrowDetail.getAccount()
							.add(borrowDetail.getInterest()));
					borrowDetail.setRepaymentYesaccount(BigDecimal.ZERO);
					borrowDetail.setWaitAccount(borrowDetail.getAccount());
					borrowDetail.setRepaymentYesinterest(BigDecimal.ZERO);
					borrowDetail.setWaitInterest(borrowDetail.getInterest());
					borrowDetail.setUser(u);
					borrowDetail.setAddPersion(admin.getName());
					borrowDetail.setOperatorIp(admin.getLoginIp());
					borrowDetail.setAutoTenderStatus(1);
					borrowDetail.setBorrow(borrow1);
					borrowDetail.setBackStatus(0);
					borrowDetail.setCreateTime(new Date());

					borrowDetailDao.save(borrowDetail);

					u.setAutoTenderTimes((u.getAutoTenderTimes() == null ? 0
							: u.getAutoTenderTimes()) + 1);
					u.setAutoTenderDate(new Date());
					u.setAutoTenderModifyDate(new Date());
					userDao.update(u);

					autoBalance = autoBalance.subtract(myMoney);
					autoMoney = autoMoney.add(myMoney);

					// 修改用户资金记录
					if (Double.valueOf(borrowDetail.getContinueAmount()) > 0) {
						// 修改用户续投余额
						ua.setContinueTotal(BigDecimal.valueOf(ua
								.getContinueTotal().doubleValue()
								- Double.parseDouble(borrowDetail
										.getContinueAmount())));
						// 修改用户冻结金额
						ua.setUnableMoney(BigDecimal.valueOf(ua
								.getUnableMoney().doubleValue()
								+ Double.parseDouble(borrowDetail
										.getContinueAmount())));
						this.userAccountDao.update(ua);

						// 资金记录
						UserAccountDetail userAccountDetail = InterestCalUtil
								.saveAccountDetail(
										//
										"tender_continued",//
										BigDecimal.valueOf(Double
												.valueOf(borrowDetail
														.getContinueAmount())),//
										"续投冻结资金["
												+ CommonUtil.fillBorrowUrl(
														borrow1.getId(),
														borrow1.getName())
												+ "]", //
										u, ua, 0, admin.getName(), admin
												.getLoginIp());
						this.userAccountDetailDao.save(userAccountDetail);
					}
					// 可用资金修改及记录
					if (Double.valueOf(borrowDetail.getAbleAmount()) > 0) {
						// 修改用户可用金额
						ua.setAbleMoney(BigDecimal.valueOf(ua.getAbleMoney()
								.doubleValue()
								- Double.parseDouble(borrowDetail
										.getAbleAmount())));
						// 修改用户冻结金额
						ua.setUnableMoney(BigDecimal.valueOf(ua
								.getUnableMoney().doubleValue()
								+ Double.parseDouble(borrowDetail
										.getAbleAmount())));
						this.userAccountDao.update(ua);

						// 资金记录
						UserAccountDetail userAccountDetail = InterestCalUtil
								.saveAccountDetail(
										//
										"tender",//
										BigDecimal.valueOf(Double
												.valueOf(borrowDetail
														.getAbleAmount())),//
										"投标冻结资金["
												+ CommonUtil.fillBorrowUrl(
														borrow1.getId(),
														borrow1.getName())
												+ "]", //
										u,//
										ua,//
										0,//
										admin.getName(),//
										admin.getLoginIp());
						this.userAccountDetailDao.save(userAccountDetail);
					}

					sblog.append("[INN]续投[")
							.append(borrowDetail.getContinueAmount())
							.append("]可用[")
							.append(borrowDetail.getAbleAmount())
							.append("]时间[")
							.append(CommonUtil.getDate2String(
									u.getAutoTenderDate(),
									"yyyy-MM-dd hh:mm:ss")).append("]");
					rankLog.debug(sblog.toString());
					
				//自动投标红包转可用----->开始
					Listing sale = listingDao.load(ConstantUtil.BONUS_SCALE);
					if(sale.getIsEnabled()){
						BigDecimal bonus_award = new BigDecimal(0);
						bonus_award =(borrowDetail.getAccount().multiply( BigDecimal.valueOf( Double.parseDouble(sale.getKeyValue())))).multiply(BigDecimal.valueOf(Double.parseDouble(borrow1.getTimeLimit())));
						if(ua.getAwardMoney().compareTo(bonus_award)==-1){
							bonus_award=ua.getAwardMoney();
						}
						if(bonus_award.compareTo(new BigDecimal(0))==1){
							ua.setAbleMoney(ua.getAbleMoney().add(bonus_award));
							ua.setAwardMoney(ua.getAwardMoney().subtract(bonus_award));
							this.userAccountDao.update(ua);
							
							// 资金记录
							UserAccountDetail userAccountDetaiAward = InterestCalUtil
									.saveAccountDetail(
											//
											"money_for_bonus",//
											bonus_award,//
											"系统将"+bonus_award+"红包变成可用金额", //
											u,//
											ua,//
											0,//
											admin.getName(),//
											admin.getLoginIp());
							
							this.userAccountDetailDao.save(userAccountDetaiAward);
						}
					}
				//自动投标红包转可用----->结束

					if (autoBalance.compareTo(BigDecimal.ZERO) != 1) {
						break;
					}

				}
				rankLog.debug("---复选结束 投中[" + no + "]人，自投金额[" + autoMoney
						+ "]自投余额[" + autoBalance + "]");

				BigDecimal balance = new BigDecimal(borrow1.getBalance())
						.subtract(autoMoney);

				borrow1.setBalance(balance.toString());
				BigDecimal schedule = NumberUtil.setPriceScale(
						BigDecimal.ONE.subtract(balance.divide(borrow1
								.getAccount()))).multiply(
						BigDecimal.valueOf(100));
				borrow1.setSchedule(schedule.toString());

				rankLog.debug("===自动投标结束===[" + borrow1.getId() + "]");
			}
		}

		borrowDao.update(borrow1);

		return 0;

	}

	@Override
	public synchronized int updateBorrowFull(Borrow borrow, Integer id,
			Admin admin) {
		// 取得标的信息
		Borrow borrow1 = borrowDao.loadLock(id);

		// 判断标的状态
		if (borrow1.getStatus() != 5) {
			return 1;
		}

		if (borrow1.getContinueAwardRate() == null) {
			borrow1.setContinueAwardRate(BigDecimal.ZERO);
		}

		// 取得操作者信息（管理员）
		Integer adminId = admin.getId();

		// 标的投资者信息
		Set<BorrowDetail> borrowDetailSet = borrow1.getBorrowDetailSet();

		if (borrow.getStatus() == 3) { // 满标审核

			// ※※※※※积分功能 开始※※※※※
			Listing listingPointAble = listingDao
					.getListingBySign("user_points_able");
			String userPointsAble = "0";
			double userPointsRateTender = 0;
			if (listingPointAble != null
					&& !StringUtils.isNotEmpty(listingPointAble.getKeyValue())
					&& "1".equals(listingPointAble.getKeyValue())) {
				userPointsAble = listingPointAble.getKeyValue();

				Listing listingPointsRateTender = listingDao
						.getListingBySign("user_points_rate_tender");
				if (listingPointsRateTender != null
						&& !StringUtils.isNotEmpty(listingPointsRateTender
								.getKeyValue())) {
					userPointsRateTender = Double
							.valueOf(listingPointsRateTender.getKeyValue());
				}
			}
			// ※※※※※积分功能 结束※※※※※
			
			// 利息管理费
			Listing listing_t = listingDao.load(1473);
			Listing listing_b = listingDao.load(1469);// 借款手续费比例

			// 修改标的信息
			borrow1.setStatus(borrow.getStatus());
			borrow1.setRepamyentRemark(borrow.getVerifyRemark());
			borrow1.setRepaymentUser(adminId);
			borrow1.setSuccessTime(new Date());

			// 计算标的每一期的收益
			RepaymentInfo repaymentInfo = PromoteUtil.promotePlan(
					Integer.parseInt(borrow1.getIsday()), //
					Integer.parseInt(borrow1.getStyle()),//
					Integer.parseInt(borrow1.getTimeLimit()),//
					borrow1.getDivides(), //
					borrow1.getAccount(), //
					new BigDecimal(borrow1.getApr())//
					);

			// 每一期的本金
			BigDecimal[] issueCapital = new BigDecimal[repaymentInfo
					.getOrderNum()];
			// 每一期的利息
			BigDecimal[] issueInterest = new BigDecimal[repaymentInfo
					.getOrderNum()];
			// 奖金总额
			BigDecimal rewardAll = new BigDecimal(0);

			// 将标分期
			Integer[] borPayIds = new Integer[repaymentInfo.getOrderNum()];
			BorrowRepaymentDetail[] borPayInfos = new BorrowRepaymentDetail[repaymentInfo
					.getOrderNum()];
			BorrowRepaymentDetail borRepayDetail = null;

			for (int i = 0; i < repaymentInfo.getOrderNum(); i++) {
				if (issueInterest[i] == null) {
					issueInterest[i] = BigDecimal.ZERO;
				}
				if (issueCapital[i] == null) {
					issueCapital[i] = BigDecimal.ZERO;
				}

				borRepayDetail = new BorrowRepaymentDetail();
				borRepayDetail.setStatus(0);
				borRepayDetail.setOrderNum(i);

				borRepayDetail.setRepaymentTime(repaymentInfo
						.getRepaymentDetailList().get(i).getRepaymentDate());
				borRepayDetail.setRepaymentDateInt(repaymentInfo
						.getRepaymentDetailList().get(i).getRepaymentDateInt());
				borRepayDetail.setInterest(issueInterest[i]);// 应还本金
				borRepayDetail.setCapital(issueCapital[i]);// 应还利息
				borRepayDetail.setBorrow(borrow1);
				borRepayDetail.setRepaymentAccount(borRepayDetail.getCapital()
						.add(borRepayDetail.getInterest()));
				borRepayDetail.setRepaymentYesaccount(BigDecimal.ZERO);
				borRepayDetail.setReminderFee(BigDecimal.ZERO);// 奖励
				borrowRepaymentDetailDao.save(borRepayDetail);

				borPayIds[i] = borRepayDetail.getId();
				borPayInfos[i] = borRepayDetail;

			}

			// 添加投资人收益明细
			// 投资人的借款金额扣除 同时增加待还本金，待还利息
			for (BorrowDetail borrowDetail : borrowDetailSet) {
				// 计算投资人每一期的收益
				RepaymentInfo retView = PromoteUtil.promotePlan(//
						Integer.parseInt(borrow1.getIsday()),//
						Integer.parseInt(borrow1.getStyle()),//
						Integer.parseInt(borrow1.getTimeLimit()),//
						borrow1.getDivides(), //
						borrowDetail.getAccount(),//
						new BigDecimal(borrow1.getApr())//
						);

				// 投资人待收本金
				BigDecimal waitCapital = new BigDecimal(0);
				// 投资人待收利息
				BigDecimal waitInterest = new BigDecimal(0);

				// 投资人，每期的投资收益明细
				UserRepaymentDetail userRepaymentDetail;
				for (int i = 0; i < repaymentInfo.getOrderNum(); i++) {

					userRepaymentDetail = new UserRepaymentDetail();
					userRepaymentDetail.setStatus("0");
					userRepaymentDetail.setAccount(borrowDetail.getAccount());
					userRepaymentDetail.setRepaymentAccount(retView
							.getRepaymentDetailList().get(i).getAccount());
					userRepaymentDetail.setInterest(retView
							.getRepaymentDetailList().get(i).getInterest());
					userRepaymentDetail.setWaitInterest(retView
							.getRepaymentDetailList().get(i).getInterest());
					userRepaymentDetail.setWaitAccount(retView
							.getRepaymentDetailList().get(i).getCapital());
					userRepaymentDetail.setAddPersion(String.valueOf(adminId));
					userRepaymentDetail.setOperatorIp(ServletActionContext
							.getRequest().getRemoteAddr());
					userRepaymentDetail.setBorrowPeriods(i);
					userRepaymentDetail.setUser(borrowDetail.getUser());
					userRepaymentDetail.setBorrow(borrowDetail.getBorrow());
					userRepaymentDetail
							.setBorrowRepaymentDetail(borPayInfos[i]);
					userRepaymentDetail.setRepaymentDate(borPayInfos[i]
							.getRepaymentTime());
					userRepaymentDetail.setBorrowDivides(borrow1.getDivides());

					// 利息管理费
//					Listing listing_t = listingDao.load(1473);
					BigDecimal userserviceCharge = SettingUtil
							.setPriceScale(BigDecimal
									.valueOf((userRepaymentDetail.getInterest()
											.doubleValue())
											* Double.parseDouble(listing_t
													.getKeyValue())));
					userRepaymentDetail.setServiceCharge(userserviceCharge);
					userRepaymentDetail.setProfit(userRepaymentDetail
							.getInterest().subtract(
									userRepaymentDetail.getServiceCharge()));
					userRepaymentDetail.setBorrowDetail(borrowDetail);

					userRepaymentDetailDao.save(userRepaymentDetail);

					// 所有投资人本期还款本金统计
					issueCapital[i] = issueCapital[i].add(userRepaymentDetail
							.getWaitAccount());
					// 所有投资人本期还款利息统计
					issueInterest[i] = issueInterest[i].add(userRepaymentDetail
							.getWaitInterest());
					// 投资人待收本金
					waitCapital = waitCapital.add(userRepaymentDetail
							.getWaitAccount());
					// 投资人待收利息
					waitInterest = waitInterest.add(userRepaymentDetail
							.getWaitInterest());
				}

				// ◆◆◆投资人资金修改&&日志 开始◆◆◆
				// 获取投标人账户资金
				UserAccount userAccount1 = userAccountDao
						.loadLockTable(borrowDetail.getUser());// 锁表

				// 冻结金额 = 冻结金额-投资金额
				userAccount1.setUnableMoney(userAccount1.getUnableMoney()
						.subtract(borrowDetail.getAccount()));
				// 投资人待收本金 增加
				userAccount1.setInvestorCollectionCapital(userAccount1
						.getInvestorCollectionCapital().add(waitCapital));
				// 投资人待收利息 增加
				userAccount1.setInvestorCollectionInterest(userAccount1
						.getInvestorCollectionInterest().add(waitInterest));

				// ※※※ 积分发放记录 开始※※※※※※※※※
				boolean pointsFlg = false;
				int userPoints = 0;
				if ("1".equals(userPointsAble)) {
					userPoints = (int) (userPointsRateTender
							* waitCapital.doubleValue()
							* Integer.parseInt(borrow1.getTimeLimit()) * 30);// 月标以30天计算
					userAccount1
							.setUserPoints((userAccount1.getUserPoints() == null ? 0
									: userAccount1.getUserPoints())
									+ userPoints);
					pointsFlg = true;
				}
				// ※※※ 积分发放记录 结束※※※※※※※※※

				userAccountDao.update(userAccount1);// 【完成修改投资人账户】

				// 记入账户日志
				UserAccountDetail userAccountDetail1 = InterestCalUtil
						.saveAccountDetail(
								"invest", // 类型:投资
								borrowDetail.getAccount(), // 变动金额
								"投标成功费用扣除["
										+ CommonUtil.fillBorrowUrl(
												borrow1.getId(),
												borrow1.getName()) + "]", //
								userAccount1.getUser(), userAccount1, borrow1
										.getUser().getId(), //
								String.valueOf(adminId), //
								ServletActionContext.getRequest()
										.getRemoteAddr());
				userAccountDetailDao.save(userAccountDetail1);
				// ◆◆◆投资人资金修改&&日志 结束◆◆◆

				// ※※※ 积分发放记录 开始※※※※※※※※※
				
				// ※※※ 积分发放记录 结束※※※※※※※※※

				// ★★★★投资人的奖金发放 开始★★★★
				if ("1".equals(borrow1.getAward())) {

					// 奖金金额
					double app = Double.parseDouble(borrow1.getFunds()) / 100;
					BigDecimal reward = borrowDetail.getAccount().multiply(
							BigDecimal.valueOf(app));

					userAccount1.setTotal(userAccount1.getTotal().add(reward));
					userAccount1.setAbleMoney(SettingUtil
							.setPriceScale(userAccount1.getAbleMoney().add(
									reward)));
					userAccountDao.update(userAccount1);// 【完成修改投资人账户】

					// 奖金日志记录
					UserAccountDetail userAccountDetail4 = InterestCalUtil
							.saveAccountDetail(
									"award_add", // 奖励
									reward,
									"借款["
											+ CommonUtil.fillBorrowUrl(
													borrow1.getId(),
													borrow1.getName()) + "]的奖励",
									userAccount1.getUser(), userAccount1,
									borrow1.getUser().getId(), String
											.valueOf(adminId),
									ServletActionContext.getRequest()
											.getRemoteAddr());
					userAccountDetailDao.save(userAccountDetail4);

					// 统计发放的所有奖金
					rewardAll = rewardAll.add(reward);

				} else if ("2".equals(borrow1.getAward())) {
					// 奖金金额
					double app = Double.parseDouble(borrow1.getFunds()) / 100;
					BigDecimal reward = borrowDetail.getAccount().multiply(
							BigDecimal.valueOf(app));
					// 统计发放的所有奖金
					rewardAll = rewardAll.add(reward);

				}
				// ★★★★投资人的奖金发放 结束★★★★
				// ★★★★投资人续投宝金额奖励 开始★★★★
				if (StringUtils.isNotEmpty(borrowDetail.getContinueAmount())
						&& Double.valueOf(borrowDetail.getContinueAmount()) > 0) {
					if (borrowDetail.getContinueAmount() != "") {
						// 获取投标人账户资金
						UserAccount userAccount = userAccountDao
								.load(borrowDetail.getUser().getAccount()
										.getId());

						userAccount = userAccountDao.loadLock(userAccount
								.getId());

						BigDecimal getaward = new BigDecimal(
								borrowDetail.getContinueAmount())
								.multiply(borrow1.getContinueAwardRate());

						userAccount.setTotal(userAccount1.getTotal().add(
								getaward));
						userAccount.setAbleMoney(SettingUtil
								.setPriceScale(userAccount1.getAbleMoney().add(
										getaward)));
						userAccountDao.update(userAccount);// 【完成修改投资人账户】

						// 奖金日志记录
						UserAccountDetail userAccountDetail4 = InterestCalUtil
								.saveAccountDetail(
										"award_continued", // 奖励
										getaward,
										"对借款["
												+ CommonUtil.fillBorrowUrl(
														borrow1.getId(),
														borrow1.getName())
												+ "]的续投奖励", userAccount
												.getUser(), userAccount,
										userAccount.getUser().getId(), String
												.valueOf(adminId),
										ServletActionContext.getRequest()
												.getRemoteAddr());
						userAccountDetailDao.save(userAccountDetail4);

					}
				}
				// ★★★★投资人的续投奖励发放 结束★★★★
			}

			// 总的借款金额
			BigDecimal borrowAllCapital = new BigDecimal(0);
			// 总的借款利息
			BigDecimal borrowAllInterest = new BigDecimal(0);

			// 借款人每期还款情况
			for (int i = 0; i < repaymentInfo.getOrderNum(); i++) {

				borRepayDetail = borPayInfos[i];
				borRepayDetail.setInterest(issueInterest[i]);
				borRepayDetail.setCapital(issueCapital[i]);
				if ("2".equals(borrow1.getAward())
						&& (i == repaymentInfo.getOrderNum() - 1)) {
					borRepayDetail.setReminderFee(rewardAll);
				}
				borRepayDetail.setRepaymentAccount(borRepayDetail.getCapital()
						.add(borRepayDetail.getInterest()).add(rewardAll));
				borrowRepaymentDetailDao.update(borRepayDetail);

				borrowAllCapital = borrowAllCapital.add(issueCapital[i]);
				borrowAllInterest = borrowAllInterest.add(issueInterest[i]);
			}

			borrow1.setRepaymentAccount(borrowAllCapital.add(borrowAllInterest));// 重新统计，计算应付金额（本金+利息）

			borrow1.setInterestTotal(borrowAllInterest);// 标的总利息
			borrow1.setRepayCapital(borrowAllCapital);// 未还本金
			borrow1.setRepayInterest(borrowAllInterest);// 未还利息
			borrow1.setPayedCapital(BigDecimal.ZERO);// 已还本金
			borrow1.setPayedInterest(BigDecimal.ZERO);// 已还利息

			// ▽▽▽增加借款人账户资金 开始▽▽▽
			// 取得借款人账户信息
			UserAccount userAccount = userAccountDao.load(borrow1.getUser()
					.getAccount().getId());
			userAccount = userAccountDao.loadLock(userAccount.getId());// 锁表

			// 可用余额 = 原可用金额 + 本次的待还本金
			userAccount.setAbleMoney(userAccount.getAbleMoney().add(
					borrowAllCapital));
			// 待还本金
			userAccount.setBorrowerCollectionCapital(userAccount
					.getBorrowerCollectionCapital().add(borrowAllCapital));
			// 待还利息
			userAccount.setBorrowerCollectionInterest(userAccount
					.getBorrowerCollectionInterest().add(borrowAllInterest));

			userAccountDao.update(userAccount);// 修改资金表数据

			UserAccountDetail userAccountDetail1 = InterestCalUtil
					.saveAccountDetail(
							"borrow_success", // 借款成功
							borrowAllCapital, // 借款的金额
							"对标["
									+ CommonUtil.fillBorrowUrl(borrow1.getId(),
											borrow1.getName()) + "]借款成功", // 备注
							userAccount.getUser(), userAccount, borrow1
									.getUser().getId(),
							String.valueOf(adminId), // 操作者
							ServletActionContext.getRequest().getRemoteAddr());//
			userAccountDetailDao.save(userAccountDetail1);
			// △△△增加借款人账户资金 结束△△△

			// ☆☆☆扣除借款手续费 开始☆☆☆
//			Listing listing_b = listingDao.load(1469);// 借款手续费比例
			// 手续费 = 百分比 * 借款金额
			double deduction = Double.parseDouble(listing_b.getKeyValue())
					* (borrow1.getAccount().doubleValue());
			// 账户总金额，账户可用金额 ：扣去借款手续费
			userAccount.setAbleMoney(userAccount.getAbleMoney().subtract(
					BigDecimal.valueOf(deduction)));
			userAccount.setTotal(userAccount.getTotal().subtract(
					BigDecimal.valueOf(deduction)));
			userAccountDao.update(userAccount);

			UserAccountDetail userAccountDetail2 = InterestCalUtil
					.saveAccountDetail(
							"borrow_fee", // 借款手续费
							BigDecimal.valueOf(deduction),// 操作金额
							"对标["
									+ CommonUtil.fillBorrowUrl(borrow1.getId(),
											borrow1.getName()) + "]借款手续费",// 备注
							userAccount.getUser(), userAccount, 0,
							String.valueOf(adminId),// 操作人
							ServletActionContext.getRequest().getRemoteAddr());
			userAccountDetailDao.save(userAccountDetail2);
			// ☆☆☆扣除借款手续费 结束☆☆☆

			// ★★★★借款人的奖金发放 开始★★★★
			if ("1".equals(borrow1.getAward())) {
				// 扣除借款人的奖金
				userAccount.setAbleMoney(userAccount.getAbleMoney().subtract(
						rewardAll));
				userAccount
						.setTotal(userAccount.getTotal().subtract(rewardAll));
				userAccountDao.update(userAccount);

				UserAccountDetail userAccountDetail3 = InterestCalUtil
						.saveAccountDetail(
								"award_lower", //
								rewardAll, //
								"扣除借款["
										+ CommonUtil.fillBorrowUrl(
												borrow1.getId(),
												borrow1.getName()) + "]的奖励",//
								userAccount.getUser(), userAccount, 0, String
										.valueOf(adminId),//
								ServletActionContext.getRequest()
										.getRemoteAddr());//
				userAccountDetailDao.save(userAccountDetail3);

			}
			// ★★★★借款人的奖金发放 结束★★★★

		} else if (borrow.getStatus() == 4) {// 满标审核不通过

			// 审核不成功 回滚投资人投资金额
			for (BorrowDetail borrowDetail : borrowDetailSet) {
				UserAccount userAccount = userAccountDao.load(borrowDetail
						.getUser().getId());// 获取投标人账户资金
				userAccount = userAccountDao.loadLock(userAccount.getId());

				userAccount.setUnableMoney(userAccount.getUnableMoney()
						.subtract(borrowDetail.getAccount()));
				userAccount.setAbleMoney(userAccount.getAbleMoney().add(
						borrowDetail.getAccount()));
				userAccountDao.update(userAccount);

				log.info("保存资金账户操作详细记录--扣去投标金额");
				UserAccountDetail userAccountDetail1 = InterestCalUtil
						.saveAccountDetail(
								"invest_false", //
								borrowDetail.getAccount(), //
								"招标["
										+ CommonUtil.fillBorrowUrl(
												borrow1.getId(),
												borrow1.getName())
										+ "]失败返回的投标额", //
								userAccount.getUser(), userAccount, userAccount
										.getUser().getId(),//
								String.valueOf(adminId), //
								ServletActionContext.getRequest()
										.getRemoteAddr());//
				userAccountDetailDao.save(userAccountDetail1);
			}

			// 修改标的信息
			borrowDao.update(borrow1);
		}
		return 0;
	}

	@Override
	public int updateTiYanBorrowFull(Borrow borrow, Integer id, Admin admin) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBorrowFullNew(Borrow borrow, Integer id, Admin admin) {
		// TODO Auto-generated method stub
		return 0;
	}
}
