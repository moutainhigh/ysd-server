package net.qmdboss.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.qmdboss.bean.HongbaoDetail;
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
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;



/**
 * 发表待审Service实现类
 * 
 * @author Administrator
 * 
 */
@Service("borrowPromotePledgeServiceImpl")
public class BorrowPromotePledgeServiceImpl extends BaseServiceImpl<Borrow, Integer> implements BorrowPromoteService {
	Logger log = Logger.getLogger(BorrowPromotePledgeServiceImpl.class);

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
	@Resource
	private UserSpreadDetailDao userSpreadDetailDaoImpl;
	@Resource(name = "userAwardDetailDaoImpl")
	private UserAwardDetailDao userAwardDetailDao;
	@Resource(name = "userHongbaoDaoImpl")
	private UserHongbaoDao userHongbaoDao;
	@Resource(name = "hongbaoDaoImpl")
	private HongbaoDao hongbaoDao;

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
	public synchronized int updateBorrowPreliminary(Borrow borrow, Integer id, Admin admin) {

		// 锁标
		Borrow borrow1 = borrowDao.loadLock(id);

		if (borrow1.getStatus().compareTo(0) != 0) {
			return 1;// 标的状态不对
		}

		borrow1.setStatus(borrow.getStatus());
		borrow1.setVerifyRemark(borrow.getVerifyRemark());
		String admin1 = String.valueOf(admin.getId());
		borrow1.setVerifyUser(admin1);
		borrow1.setAccountYes(borrow1.getAccountYes());
		borrow1.setBalance(String.valueOf(borrow1.getAccount().doubleValue()));
		borrow1.setVerifyTime(new Date());
		borrow1.setSchedule("0");
		borrow1.setBorrowVerifyJson(borrow.getBorrowVerifyJson());
		borrow1.setIsDo(borrow.getIsDo());
		borrow1.setBaseApr(borrow.getBaseApr()!= null ?borrow.getBaseApr().divide(new BigDecimal(100)) : new BigDecimal(0));
		borrow1.setAwardApr(borrow.getAwardApr()!= null ?borrow.getAwardApr().divide(new BigDecimal(100)) : new BigDecimal(0));
		
		//pc首页展示
		borrow1.setRongXunFlg(borrow.getRongXunFlg());

		// 红包使用比例
		borrow1.setAwardScale(borrow.getAwardScale());

		borrow1.setTzxf(borrow.getTzxf()==null?new BigDecimal(0):borrow.getTzxf());
		borrow1.setTzth(borrow.getTzth()==null?new BigDecimal(0):borrow.getTzth());
		borrow1.setSgdg(borrow.getSgdg()==null?new BigDecimal(0):borrow.getSgdg());
		
		borrow1.setBorImageFirst(borrow.getBorImageFirst());
		if ("0".equals(borrow1.getIsday())) {
			borrow1.setFinalRepayDate(CommonUtil.getMonthAfter(borrow1.getVerifyTime(), Integer.parseInt(borrow1.getTimeLimit())));
			borrow1.setContinueAwardRate(borrow.getContinueAwardRate() == null ? BigDecimal.ZERO : borrow.getContinueAwardRate());
		} else if ("1".equals(borrow1.getIsday())) {
			borrow1.setFinalRepayDate(CommonUtil.getDateAfter(borrow1.getVerifyTime(), Integer.parseInt(borrow1.getTimeLimit())));
			borrow1.setContinueAwardRate(BigDecimal.ZERO);
		}

		borrow1.setFeeType(borrow.getFeeType());
		borrow1.setDepositMoney(borrow.getDepositMoney());
		if (borrow.getFeeType() == null || borrow.getFeeType() == 0) {
			borrow1.setFeeMoney(BigDecimal.ZERO);
			borrow1.setPartAccount(borrow.getPartAccount());
		} else if (borrow.getFeeType() == 1) {
			borrow1.setFeeMoney(borrow.getFeeMoney());
			borrow1.setPartAccount("0");
		}

		// 天标
		if ("1".equals(borrow1.getIsday())) {
			// // 计算收益率
			borrow1.setAutoTenderStatus(borrow.getAutoTenderStatus());
		if(!borrow1.getType().equals("17")&&!borrow1.getType().equals("16")){
				
			// 判断自动投标
			if (borrow.getAutoTenderStatus() != null && borrow.getAutoTenderStatus() == 1) {
				rankLog.debug("===自动投标开始===[" + borrow1.getId() + "] 还款方式[" + borrow1.getAutoTenderRepayWay() + "]");
				// 1 自动投标总额
				Listing autoListing = listingDao.getListingBySign("auto_tender_account_rate");
				BigDecimal autoBalance = borrow1.getAccount().multiply(new BigDecimal(autoListing.getKeyValue()));
				BigDecimal autoMoney = BigDecimal.ZERO;

				// 2 获取符合条件的投资人
				List<User> userList = new ArrayList<User>();
				BigDecimal reward = BigDecimal.ZERO;
				if ("1".equals(borrow1.getAward())) {
					reward = new BigDecimal(borrow1.getFunds());
				}

//				List<Object> list = userDao.getViewTest(Integer.parseInt(borrow1.getTimeLimit()), BigDecimal.valueOf(borrow1.getApr()), reward);
				List<Object> list = userDao.getViewTest(Integer.parseInt(borrow1.getTimeLimit()), borrow1.getVaryYearRate().multiply(new BigDecimal(100)), reward);//年利率
				StringBuffer sblog = null;
				int no = 0;
				rankLog.debug("---初选开始");
				for (Object obj : list) {
					Integer uid = (Integer) obj;
					User uu = userDao.loadLock(uid);
					sblog = new StringBuffer();
					no++;
					sblog.append("   [").append(no).append("][").append(uid).append("][").append(uu.getUsername()).append("]排名[")
							.append(CommonUtil.getDate2String(uu.getAutoTenderDate(), "yyyy-MM-dd hh:mm:ss")).append("]规则[")
							.append(uu.getAutoTenderRule()).append("]最高[").append(uu.getAutoTenderMoneyTop()).append("]余留[")
							.append(uu.getAutoTenderMoneyLeave()).append("]利率[").append(uu.getAutoTenderRateBegin()).append("][")
							.append(uu.getAutoTenderRateEnd()).append("]奖励[").append(uu.getAutoTenderRewardBegin()).append("][")
							.append(uu.getAutoTenderRateEnd()).append("]期限[").append(uu.getAutoTenderLimitBegin()).append("][")
							.append(uu.getAutoTenderLimitEnd()).append("]方式[").append(uu.getAutoTenderRepayWay()).append("]修改[")
							.append(CommonUtil.getDate2String(uu.getAutoTenderModifyDate(), "yyyy-MM-dd hh:mm:ss"));

					if ( StringUtils.isEmpty(uu.getAutoTenderBorrowType())) {
						sblog.append("[OUT_1][").append(uu.getAutoTenderBorrowType()).append("]");
						rankLog.debug(sblog.toString());
						continue;
					}
//					if (borrow1.getAutoTenderRepayWay() == null || uu.getAutoTenderRepayWay() == null) {
//						sblog.append("[OUT_1][").append(uu.getAutoTenderRepayWay()).append("]");
//						rankLog.debug(sblog.toString());
//						continue;
//					}
//					String[] way = uu.getAutoTenderRepayWay().split(",");
					String[] type = uu.getAutoTenderBorrowType().split(",");
//					if (way == null || way.length != 3) {
//						sblog.append("[OUT_2][").append(uu.getAutoTenderRepayWay()).append("][").append(way.length).append("]");
//						rankLog.debug(sblog.toString());
//						continue;
//					}
					if (type == null || type.length < 1) {
						sblog.append("[OUT_2][").append(uu.getAutoTenderBorrowType()).append("][").append(type.length).append("]");
						rankLog.debug(sblog.toString());
						continue;
					}
					int strnu =0;
					for(int i=0;i<type.length;i++){
						if((borrow1.getIsVouch().toString()).compareTo(type[i]) ==0){
							strnu =0;
							break;
						}else{
							strnu=1;
						}
					}
					if (strnu==1) {
						continue;
					}
					
//					if (borrow1.getAutoTenderRepayWay() == 1 && !"1".equals(way[0])) {
//						sblog.append("[OUT_3][").append(borrow1.getAutoTenderRepayWay()).append("][").append(way[0]).append("]");
//						rankLog.debug(sblog.toString());
//						continue;
//					} else if (borrow1.getAutoTenderRepayWay() == 2 && !"1".equals(way[1])) {
//						sblog.append("[OUT_4][").append(borrow1.getAutoTenderRepayWay()).append("][").append(way[1]).append("]");
//						rankLog.debug(sblog.toString());
//						continue;
//					} else if (borrow1.getAutoTenderRepayWay() == 3 && !"1".equals(way[2])) {
//						sblog.append("[OUT_5][").append(borrow1.getAutoTenderRepayWay()).append("][").append(way[2]).append("]");
//						rankLog.debug(sblog.toString());
//						continue;
//					}
					sblog.append("[INN]");
					rankLog.debug(sblog.toString());

					userList.add(uu);
				}
				rankLog.debug("---初选结束 原[" + no + "] 现[" + userList.size() + "]");

				Integer lowestAccount = null;
				if (StringUtils.isEmpty(borrow1.getLowestAccount()) || "0".equals(borrow1.getLowestAccount())) {
					lowestAccount = 100;// 最低100元
				} else {
					lowestAccount = Double.valueOf(borrow1.getLowestAccount()).intValue();
				}
				BigDecimal mostAccount = null;
				if (StringUtils.isEmpty(borrow1.getMostAccount()) || "0".equals(borrow1.getMostAccount())) {
					mostAccount = autoBalance;// 没有限制，就标的自投额度限制
				} else {
					mostAccount = BigDecimal.valueOf(Double.valueOf(borrow1.getMostAccount()));
				}

				// interestCalUtil = new InterestCalUtil();
				rankLog.debug("---复选开始");
				no = 0;
				for (User u : userList) {
					UserAccount ua = userAccountDao.loadLockTable(u);// 锁表

					sblog = new StringBuffer();
					no++;
					sblog.append("   [").append(no).append("][").append(CommonUtil.getDate2String(u.getAutoTenderDate(), "yyyy-MM-dd hh:mm:ss"))
							.append("][").append(u.getId()).append("][").append(u.getUsername()).append("]续投[").append(ua.getContinueTotal())
							.append("]可用[").append(ua.getAbleMoney());

					int money = (int) (ua.getAbleMoney().doubleValue() / 100) * 100 + (int) (ua.getContinueTotal().doubleValue() / 100) * 100;
					if (money < lowestAccount) {// 有效投资额少于最小投资额，跳过
						sblog.append("[OUT_6][").append(money).append("][").append(lowestAccount).append("][有效投资少于最小投资额]");
						rankLog.debug(sblog.toString());
						continue;
					}

					// 如果设置了投标金额限制，myMoney需要重新计算
					if (u.getAutoTenderRule() != null && u.getAutoTenderRule() == 1) {
						BigDecimal aa = ua.getAbleMoney().subtract(u.getAutoTenderMoneyLeave());
						if (aa.compareTo(BigDecimal.ZERO) != -1) {// aa >= 0
																	// 那么只扣除可用中的金额
							money = (int) (aa.doubleValue() / 100) * 100 + (int) (ua.getContinueTotal().doubleValue() / 100) * 100;
						} else {
							money = (int) ((ua.getContinueTotal().add(aa)).doubleValue() / 100) * 100;
						}
						if (BigDecimal.valueOf(money).compareTo(u.getAutoTenderMoneyTop()) == 1) {
							money = (int) (u.getAutoTenderMoneyTop().doubleValue() / 100) * 100;
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
						sblog.append("[OUT_7][").append(myMoney).append("][有效投资少于0]");
						rankLog.debug(sblog.toString());
						continue;
					}

					BorrowDetail borrowDetail = new BorrowDetail();
					borrowDetail.setAccount(myMoney);
					borrowDetail.setMoney(myMoney);
					if (myMoney.compareTo(BigDecimal.valueOf((int) (ua.getContinueTotal().doubleValue() / 100) * 100)) == 1) {
						borrowDetail.setContinueAmount(String.valueOf((int) (ua.getContinueTotal().doubleValue() / 100) * 100));
						borrowDetail.setAbleAmount((myMoney.subtract(BigDecimal.valueOf((int) (ua.getContinueTotal().doubleValue() / 100) * 100)))
								.toString());
					} else {
						borrowDetail.setContinueAmount(myMoney.toString());
						borrowDetail.setAbleAmount("0");
					}
					borrowDetail.setStatus("1");

					RepaymentInfo repaymentInfo = PromoteUtil.promotePlan(//
							Integer.parseInt(borrow1.getIsday()), //
							Integer.parseInt(borrow1.getStyle()), //
							Integer.parseInt(borrow1.getTimeLimit()), //
							borrow1.getDivides(),//
							myMoney, //
							new BigDecimal(borrow1.getApr())//
							);

					borrowDetail.setInterest(repaymentInfo.getInterest());
					borrowDetail.setRepaymentAccount(borrowDetail.getAccount().add(borrowDetail.getInterest()));
					borrowDetail.setRepaymentYesaccount(BigDecimal.ZERO);
					borrowDetail.setWaitAccount(borrowDetail.getAccount());
					borrowDetail.setRepaymentYesinterest(BigDecimal.ZERO);
					borrowDetail.setWaitInterest(borrowDetail.getInterest());
					borrowDetail.setUser(u);
					borrowDetail.setAddPersion(u.getUsername());//admin.getName()
					borrowDetail.setOperatorIp(admin.getLoginIp());
					borrowDetail.setAutoTenderStatus(1);
					borrowDetail.setBorrow(borrow1);
					borrowDetail.setBackStatus(0);
					borrowDetail.setCreateTime(new Date());

					borrowDetailDao.save(borrowDetail);

					u.setAutoTenderTimes((u.getAutoTenderTimes() == null ? 0 : u.getAutoTenderTimes()) + 1);
					u.setAutoTenderDate(new Date());
					u.setAutoTenderModifyDate(new Date());
					userDao.update(u);

					autoBalance = autoBalance.subtract(myMoney);
					autoMoney = autoMoney.add(myMoney);

					// 修改用户资金记录
					if (Double.valueOf(borrowDetail.getContinueAmount()) > 0) {
						// 修改用户续投余额
						ua.setContinueTotal(BigDecimal.valueOf(ua.getContinueTotal().doubleValue()
								- Double.parseDouble(borrowDetail.getContinueAmount())));
						// 修改用户冻结金额
						ua.setUnableMoney(BigDecimal.valueOf(ua.getUnableMoney().doubleValue() + Double.parseDouble(borrowDetail.getContinueAmount())));
						this.userAccountDao.update(ua);

						// 资金记录
						UserAccountDetail userAccountDetail = InterestCalUtil.saveAccountDetail("tender_continued",//
								BigDecimal.valueOf(Double.valueOf(borrowDetail.getContinueAmount())),//
								"续投冻结资金[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]", //
								u, ua, 0, admin.getName(), admin.getLoginIp());
						this.userAccountDetailDao.save(userAccountDetail);
					}
					// 可用资金修改及记录
					if (Double.valueOf(borrowDetail.getAbleAmount()) > 0) {
						// 修改用户可用金额
						ua.setAbleMoney(BigDecimal.valueOf(ua.getAbleMoney().doubleValue() - Double.parseDouble(borrowDetail.getAbleAmount())));
						// 修改用户冻结金额
						ua.setUnableMoney(BigDecimal.valueOf(ua.getUnableMoney().doubleValue() + Double.parseDouble(borrowDetail.getAbleAmount())));
						this.userAccountDao.update(ua);

						// 资金记录
						UserAccountDetail userAccountDetail = InterestCalUtil.saveAccountDetail(
						//
								"tender",//
								BigDecimal.valueOf(Double.valueOf(borrowDetail.getAbleAmount())),//
								"投标冻结资金[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]", //
								u,//
								ua,//
								0,//
								admin.getName(),//
								admin.getLoginIp());
						this.userAccountDetailDao.save(userAccountDetail);
					}

					/**
					// 好友投资奖励
					String totalMony_tg = listingDao.getListingBySign(ConstantUtil.SUM_TENDER_MONEY).getKeyValue();
					BigDecimal listingMoney = new BigDecimal(totalMony_tg);
					String key_t = listingDao.getListingBySign("tuijian_award1").getKeyValue();// 天标参数

					if (u.getTgOneLevelUserId() != null) {// 存在上级好友
						BigDecimal totalMoney_tg = u.getSumTenderMoney().add(borrowDetail.getAccount());// 总投资钱
						u.setSumTenderMoney(totalMoney_tg);
						userDao.update(u);// 更新总投资额

						if (listingMoney.compareTo(totalMoney_tg) == 0 || listingMoney.compareTo(totalMoney_tg) == -1) {// 投资总和大于等于设置门槛

							if (key_t != null && Double.valueOf(key_t) > 0) {
								BigDecimal award_t = new BigDecimal(key_t).multiply(borrowDetail.getAccount()).multiply(
										new BigDecimal(borrow.getTimeLimit()));
								User tgUser = userDao.get(u.getTgOneLevelUserId());
								UserAccount tgUserAccount = userAccountDao.loadLockTable(tgUser);// 锁表

								tgUserAccount.setTotal(tgUserAccount.getTotal().add(award_t));
								tgUserAccount.setAbleMoney(tgUserAccount.getAbleMoney().add(award_t));
								userAccountDao.update(tgUserAccount);

								// 资金记录
								UserAccountDetail tgAccountDetail = InterestCalUtil.saveAccountDetail("tui_detail_award",//
										award_t,//
										"好友投资[" + CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName()) + "]现金奖励", //
										tgUser,//
										tgUserAccount,//
										0,//
										admin.getName(),//
										admin.getLoginIp());
								this.userAccountDetailDao.save(tgAccountDetail);
								
								UserAwardDetail uad = new UserAwardDetail();
								uad.setUserId(tgUser.getId());// 用户ID
								uad.setType("tui_detail_award");// 类型（同资金记录）
								uad.setMoney(award_t);// 操作金额
								uad.setAwardMoney(tgUserAccount.getAwardMoney());// 奖励账户
								uad.setSignFlg(1);
								uad.setRemark("好友["+u.getUsername()+"]现金奖励");// 备注
								uad.setRelateKey("user_id");
								uad.setRelateTo(u.getId());
								userAwardDetailDao.save(uad);

							}
						}
					}
**/
					sblog.append("[INN]续投[").append(borrowDetail.getContinueAmount()).append("]可用[").append(borrowDetail.getAbleAmount())
							.append("]时间[").append(CommonUtil.getDate2String(u.getAutoTenderDate(), "yyyy-MM-dd hh:mm:ss")).append("]");
					rankLog.debug(sblog.toString());

					// 自动投标红包转可用----->开始

					// 自动投标红包转可用----->结束

					if (autoBalance.compareTo(BigDecimal.ZERO) != 1) {
						break;
					}

				}
				rankLog.debug("---复选结束 投中[" + no + "]人，自投金额[" + autoMoney + "]自投余额[" + autoBalance + "]");

				BigDecimal balance = new BigDecimal(borrow1.getBalance()).subtract(autoMoney);

				borrow1.setBalance(balance.toString());
				BigDecimal schedule = NumberUtil.setPriceScale(
						BigDecimal.ONE.subtract(NumberUtil.setPriceScale(balance.doubleValue() / borrow1.getAccount().doubleValue()))).multiply(
						BigDecimal.valueOf(100));
				borrow1.setSchedule(schedule.toString());

				if (balance.compareTo(BigDecimal.ZERO) == 0) {
					borrow1.setStatus(5);// 无剩余资金，状态为满标待审
				}

				rankLog.debug("===自动投标结束===[" + borrow1.getId() + "]");
			}
			}
		}

		borrowDao.update(borrow1);

		return 0;

	}
	
	/**
	 * 新的满标复审
	 * @author zdl
	 */
	@Override
	public synchronized int updateBorrowFullNew(Borrow borrow, Integer id, Admin admin) {
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
		List<User> userList = new ArrayList<User>();

		if (borrow.getStatus() == 3) { // 满标审核

			// ※※※※※积分功能 开始※※※※※
			Listing listingPointAble = listingDao.getListingBySign("user_points_able");
			String userPointsAble = "0";
			double userPointsRateTender = 0;
			if (listingPointAble != null && !StringUtils.isNotEmpty(listingPointAble.getKeyValue()) && "1".equals(listingPointAble.getKeyValue())) {
				userPointsAble = listingPointAble.getKeyValue();

				Listing listingPointsRateTender = listingDao.getListingBySign("user_points_rate_tender");
				if (listingPointsRateTender != null && !StringUtils.isNotEmpty(listingPointsRateTender.getKeyValue())) {
					userPointsRateTender = Double.valueOf(listingPointsRateTender.getKeyValue());
				}
			}
			// ※※※※※积分功能 结束※※※※※

			// 利息管理费
			Listing listing_t = listingDao.load(1473);
//			Listing listing_b = listingDao.load(1469);// 借款手续费比例

			// 修改标的信息
			borrow1.setStatus(borrow.getStatus());
			borrow1.setRepamyentRemark(borrow.getVerifyRemark());
			borrow1.setRepaymentUser(adminId);
			borrow1.setSuccessTime(new Date());
			
			if ("0".equals(borrow1.getIsday())) {
				borrow1.setFinalRepayDate(CommonUtil.getMonthAfter(borrow1.getSuccessTime(), Integer.parseInt(borrow1.getTimeLimit())));
			} else if ("1".equals(borrow1.getIsday())) {
				borrow1.setFinalRepayDate(CommonUtil.getDateAfter(borrow1.getSuccessTime(), Integer.parseInt(borrow1.getTimeLimit())));
			}
			

			// 计算标的每一期的收益
			RepaymentInfo repaymentInfo = PromoteUtil.promotePlan(Integer.parseInt(borrow1.getIsday()), //
					Integer.parseInt(borrow1.getStyle()),//
					Integer.parseInt(borrow1.getTimeLimit()),//
					borrow1.getDivides(), //
					borrow1.getAccount(), //
					new BigDecimal(borrow1.getApr())//
					);

			// 每一期的本金
			BigDecimal[] issueCapital = new BigDecimal[repaymentInfo.getOrderNum()];
			// 每一期的利息
			BigDecimal[] issueInterest = new BigDecimal[repaymentInfo.getOrderNum()];
			// 奖金总额
			BigDecimal rewardAll = new BigDecimal(0);

			// 将标分期
			Integer[] borPayIds = new Integer[repaymentInfo.getOrderNum()];
			BorrowRepaymentDetail[] borPayInfos = new BorrowRepaymentDetail[repaymentInfo.getOrderNum()];
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

				borRepayDetail.setRepaymentTime(repaymentInfo.getRepaymentDetailList().get(i).getRepaymentDate());
				borRepayDetail.setRepaymentDateInt(repaymentInfo.getRepaymentDetailList().get(i).getRepaymentDateInt());
				borRepayDetail.setInterest(issueInterest[i]);// 应还本金
				borRepayDetail.setCapital(issueCapital[i]);// 应还利息
				borRepayDetail.setBorrow(borrow1);
				borRepayDetail.setRepaymentAccount(borRepayDetail.getCapital().add(borRepayDetail.getInterest()));
				borRepayDetail.setRepaymentYesaccount(BigDecimal.ZERO);
				borRepayDetail.setReminderFee(BigDecimal.ZERO);// 奖励
				borRepayDetail.setRechargeStatus(0);
				borrowRepaymentDetailDao.save(borRepayDetail);

				borPayIds[i] = borRepayDetail.getId();
				borPayInfos[i] = borRepayDetail;

			}

			
			Listing listingQixain = listingDao.getListingBySign("tuijian_award2");// 红包有效期
			Integer day_qixian = Integer.parseInt(listingQixain.getKeyValue());
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
					userRepaymentDetail.setRepaymentAccount(retView.getRepaymentDetailList().get(i).getAccount());
					userRepaymentDetail.setInterest(retView.getRepaymentDetailList().get(i).getInterest());
					userRepaymentDetail.setWaitInterest(retView.getRepaymentDetailList().get(i).getInterest());
					userRepaymentDetail.setWaitAccount(retView.getRepaymentDetailList().get(i).getCapital());
					userRepaymentDetail.setAddPersion(String.valueOf(adminId));
					userRepaymentDetail.setOperatorIp(ServletActionContext.getRequest().getRemoteAddr());
					userRepaymentDetail.setBorrowPeriods(i);
					userRepaymentDetail.setUser(borrowDetail.getUser());
					userRepaymentDetail.setBorrow(borrowDetail.getBorrow());
					userRepaymentDetail.setBorrowRepaymentDetail(borPayInfos[i]);
					userRepaymentDetail.setRepaymentDate(borPayInfos[i].getRepaymentTime());
					userRepaymentDetail.setBorrowDivides(borrow1.getDivides());

					// 利息管理费
					// Listing listing_t = listingDao.load(1473);
					BigDecimal userserviceCharge = SettingUtil.setPriceScale(BigDecimal.valueOf((userRepaymentDetail.getInterest().doubleValue())
							* Double.parseDouble(listing_t.getKeyValue())));
					userRepaymentDetail.setServiceCharge(userserviceCharge);
					userRepaymentDetail.setProfit(userRepaymentDetail.getInterest().subtract(userRepaymentDetail.getServiceCharge()));
					userRepaymentDetail.setBorrowDetail(borrowDetail);

					userRepaymentDetailDao.save(userRepaymentDetail);

					// 所有投资人本期还款本金统计
					issueCapital[i] = issueCapital[i].add(userRepaymentDetail.getWaitAccount());
					// 所有投资人本期还款利息统计
					issueInterest[i] = issueInterest[i].add(userRepaymentDetail.getWaitInterest());
					// 投资人待收本金
					waitCapital = waitCapital.add(userRepaymentDetail.getWaitAccount());
					// 投资人待收利息
					waitInterest = waitInterest.add(userRepaymentDetail.getWaitInterest());
				}

				// ◆◆◆投资人资金修改&&日志 开始◆◆◆
				// 获取投标人账户资金
				UserAccount userAccount1 = userAccountDao.loadLockTable(borrowDetail.getUser());// 锁表
				//红包进入总额
				if(StringUtils.isNotEmpty(borrowDetail.getHongbaoAmount())){
					userAccount1.setTotal(userAccount1.getTotal().add(new BigDecimal(borrowDetail.getHongbaoAmount())));
				}else{
					userAccount1.setTotal(userAccount1.getTotal());
				}
				
				// 冻结金额 = 冻结金额-投资金额
				userAccount1.setUnableMoney(userAccount1.getUnableMoney().subtract(new BigDecimal(borrowDetail.getAbleAmount())));
				// 投资人待收本金 增加
				userAccount1.setInvestorCollectionCapital(userAccount1.getInvestorCollectionCapital().add(waitCapital));
				// 投资人待收利息 增加
				userAccount1.setInvestorCollectionInterest(userAccount1.getInvestorCollectionInterest().add(waitInterest));

				// ※※※ 积分发放记录 开始※※※※※※※※※
				boolean pointsFlg = false;
				int userPoints = 0;
				if ("1".equals(userPointsAble)) {
					userPoints = (int) (userPointsRateTender * waitCapital.doubleValue() * Integer.parseInt(borrow1.getTimeLimit()) * 30);// 月标以30天计算
					userAccount1.setUserPoints((userAccount1.getUserPoints() == null ? 0 : userAccount1.getUserPoints()) + userPoints);
					pointsFlg = true;
				}
				// ※※※ 积分发放记录 结束※※※※※※※※※

				userAccountDao.update(userAccount1);// 【完成修改投资人账户】

				// 记入账户日志
				UserAccountDetail userAccountDetail1 = InterestCalUtil.saveAccountDetail("invest", // 类型:投资
						borrowDetail.getAccount(), // 变动金额
						"投标成功费用扣除[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]", //
						userAccount1.getUser(), userAccount1, borrow1.getUser().getId(), //
						String.valueOf(adminId), //
						ServletActionContext.getRequest().getRemoteAddr());
				userAccountDetailDao.save(userAccountDetail1);
				// ◆◆◆投资人资金修改&&日志 结束◆◆◆

				// ※※※ 积分发放记录 开始※※※※※※※※※
				
				// ※※※ 积分发放记录 结束※※※※※※※※※

				// ★★★★投资人的奖金发放 开始★★★★
				if ("1".equals(borrow1.getAward())) {
					// 满标发奖励
					BigDecimal reward = new BigDecimal(0);
					if (0 == borrow1.getAwardType()) {
						// 奖励现金

						// 奖金金额
						double app = Double.parseDouble(borrow1.getFunds()) / 100;
						reward = borrowDetail.getAccount().multiply(BigDecimal.valueOf(app));

						userAccount1.setTotal(userAccount1.getTotal().add(reward));
						userAccount1.setAbleMoney(SettingUtil.setPriceScale(userAccount1.getAbleMoney().add(reward)));
						userAccountDao.update(userAccount1);// 【完成修改投资人账户】

						// 奖金日志记录
						UserAccountDetail userAccountDetail4 = InterestCalUtil.saveAccountDetail(
								"award_add", // 奖励
								reward, "借款[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]的奖励", userAccount1.getUser(),
								userAccount1, borrow1.getUser().getId(), String.valueOf(adminId), ServletActionContext.getRequest().getRemoteAddr());
						userAccountDetailDao.save(userAccountDetail4);
					} else {
						// 红包奖励

						// 奖金金额
						double app = Double.parseDouble(borrow1.getFunds()) / 100;
						reward = new BigDecimal( borrowDetail.getAccount().multiply(BigDecimal.valueOf(app)).toBigInteger());
						
						if(reward.compareTo(BigDecimal.ZERO) >0){
							userAccount1.setAwardMoney(SettingUtil.setPriceScale(userAccount1.getAwardMoney().add(reward)));// 添加红包账户奖励
							userAccountDao.update(userAccount1);// 【完成修改投资人账户】
	
							// 投资奖励
							UserHongbao hb = new UserHongbao();
//							hb.setUserId(userAccount1.getUser().getId());
							hb.setUser(userAccount1.getUser());
							hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd") + CommonUtil.getRandomString(5));
							hb.setName("投资奖励");
							hb.setMoney(reward);
							hb.setUsedMoney(BigDecimal.ZERO);
							hb.setStatus(0);
							Date d = new Date();
							hb.setStartTime(d);
							hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d, day_qixian)));
							hb.setSource(3);
							hb.setSourceString("投资奖励");
							hb.setSourceUserId(null);
							hb.setSourceBorrowId(borrow.getId());
							hb.setUsedBorrowId(null);
							userHongbaoDao.save(hb);
	
							UserAwardDetail uad = new UserAwardDetail();
							uad.setUser(userAccount1.getUser());// 用户ID
							uad.setType("award_toubiao");// 类型（同资金记录）
							uad.setMoney(reward);// 操作金额
							uad.setAwardMoney(userAccount1.getAwardMoney());// 奖励账户
							uad.setSignFlg(1);
							uad.setRemark("借款[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]的红包奖励");// 备注
							uad.setReserve1(hb.getHbNo());
							userAwardDetailDao.save(uad);
						}
					}

					// 统计发放的所有奖金
					rewardAll = rewardAll.add(reward);

				} else if ("2".equals(borrow1.getAward())) {
					// 还款发奖励
					// 奖金金额
					double app = Double.parseDouble(borrow1.getFunds()) / 100;
					BigDecimal reward = borrowDetail.getAccount().multiply(BigDecimal.valueOf(app));
					// 统计发放的所有奖金
					rewardAll = rewardAll.add(reward);

				}
				// ★★★★投资人的奖金发放 结束★★★★

				// ************************************************************************************************************************************
				// ★★★★投资人续投宝金额奖励 开始★★★★

				// ★★★★投资人续投宝金额奖励 开始★★★★
				if (StringUtils.isNotEmpty(borrowDetail.getContinueAmount()) && Double.valueOf(borrowDetail.getContinueAmount()) > 0) {
					if (borrowDetail.getContinueAmount() != "") {
						// 获取投标人账户资金
						UserAccount userAccount = userAccountDao.load(borrowDetail.getUser().getAccount().getId());

						userAccount = userAccountDao.loadLock(userAccount.getId());

						BigDecimal getaward = new BigDecimal(borrowDetail.getContinueAmount()).multiply(borrow1.getContinueAwardRate());

						userAccount.setTotal(userAccount1.getTotal().add(getaward));
						userAccount.setAbleMoney(SettingUtil.setPriceScale(userAccount1.getAbleMoney().add(getaward)));
						userAccountDao.update(userAccount);// 【完成修改投资人账户】

						// 奖金日志记录
						UserAccountDetail userAccountDetail4 = InterestCalUtil.saveAccountDetail(
								"award_continued", // 奖励
								getaward, "对借款[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]的续投奖励", userAccount.getUser(),
								userAccount, userAccount.getUser().getId(), String.valueOf(adminId), ServletActionContext.getRequest()
										.getRemoteAddr());
						userAccountDetailDao.save(userAccountDetail4);

					}
				}
				// ★★★★投资人的续投奖励发放 结束★★★★
				//好友奖励发放---投资人收益的 10%
				Integer tgUserId = borrowDetail.getUser().getTgOneLevelUserId();
				Listing jlbl = listingDao.getListingBySign("shouyijianglibili");//项目复审，给好友的收益奖励比例（keyValue:"0.1,1000" ;表示：可得到好友收益的10%，从单个好友累积得到奖励上限1000）
				BigDecimal syjlbl = null ;//收益百分比
				BigDecimal limit = null;	//上限
				String [] values = null;
				if(jlbl.getIsEnabled() != null && jlbl.getIsEnabled()){//开启状态
					if(StringUtils.contains(jlbl.getKeyValue(), ",")||StringUtils.contains(jlbl.getKeyValue(), "，")){
						values = jlbl.getKeyValue().split(",|，",2);
					}
					if(values != null && values.length ==2 && StringUtils.isNotBlank(values[0]) && StringUtils.isNotBlank(values[1])){
						syjlbl = new BigDecimal(values[0].trim());
						limit =  new BigDecimal(values[1].trim());
					}
				}
				Boolean isFriendAward = false;//是否发放好友奖励
				BigDecimal reward = BigDecimal.ZERO;//奖励金额
				if(syjlbl != null && limit!=null && tgUserId != null){
					reward = waitInterest.multiply(syjlbl);
					BigDecimal totalMoneyFriendAward = userAwardDetailDao.getTotalMoneyFriendAward(tgUserId, borrowDetail.getUser().getId());
					if((limit.subtract(totalMoneyFriendAward)).compareTo(reward) >= 0){
						isFriendAward = true;
					}else if(limit.compareTo(totalMoneyFriendAward) > 0 && (limit.subtract(totalMoneyFriendAward)).compareTo(reward) < 0   ){
						isFriendAward = true;
						reward = limit.subtract(totalMoneyFriendAward);
					}
				}
				if(isFriendAward){
					User tgUser = userDao.get(tgUserId);
					UserAccount tgUserAccount = userAccountDao.loadLockTable(tgUser);// 锁表
					tgUserAccount.setTotal(tgUserAccount.getTotal().add(reward));
					tgUserAccount.setAbleMoney(SettingUtil.setPriceScale(tgUserAccount.getAbleMoney().add(reward)));
					userAccountDao.update(tgUserAccount);// 【完成修改邀请人账户】

					// 奖金日志记录
					UserAccountDetail tgDetail = InterestCalUtil.saveAccountDetail(
							"tui_detail_award", // 奖励
							reward, "好友投资收益奖励", tgUser,
							tgUserAccount, tgUserId, String.valueOf(adminId), ServletActionContext.getRequest().getRemoteAddr());
					userAccountDetailDao.save(tgDetail);
					
					UserAwardDetail uad = new UserAwardDetail();
					uad.setUser(tgUser);// 用户ID
					uad.setType("tui_detail_award");// 类型（同资金记录）
					uad.setMoney(reward);// 操作金额
					uad.setAwardMoney(tgUserAccount.getAwardMoney());// 奖励账户
					uad.setSignFlg(1);
					uad.setRemark("好友["+borrowDetail.getUser().getUsername()+"]投资收益奖励");// 备注
					uad.setRelateKey("user_id");
					uad.setRelateTo(borrowDetail.getUser().getId());
					userAwardDetailDao.save(uad);
				}
				userList.add(borrowDetail.getUser());
			}

			//修改推广渠道数据
//			updateIOSIdfa(userList);
			
			// 总的借款金额
			BigDecimal borrowAllCapital = new BigDecimal(0);
			// 总的借款利息
			BigDecimal borrowAllInterest = new BigDecimal(0);

			// 借款人每期还款情况
			for (int i = 0; i < repaymentInfo.getOrderNum(); i++) {

				borRepayDetail = borPayInfos[i];
				borRepayDetail.setInterest(issueInterest[i]);
				borRepayDetail.setCapital(issueCapital[i]);
				if ("2".equals(borrow1.getAward()) && (i == repaymentInfo.getOrderNum() - 1)) {
					borRepayDetail.setReminderFee(rewardAll);
				}
				borRepayDetail.setRepaymentAccount(borRepayDetail.getCapital().add(borRepayDetail.getInterest()).add(rewardAll));
				
				
				borRepayDetail.setRechargeMoney(borRepayDetail.getCapital().add(borRepayDetail.getInterest()));
				
				if(i==(repaymentInfo.getOrderNum()-1)) {
					borRepayDetail.setRechargeMoney(borRepayDetail.getRechargeMoney());//插入还款充值金额
					//borRepayDetail.getRechargeMoney().subtract(borrow1.getDepositMoney()==null?BigDecimal.ZERO:borrow1.getDepositMoney())
				}
				
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
			
			borrow1.setBorrowMoney(borrow1.getAccount());//项目余额
			borrow1.setFangkuanStatus(0);
			borrow1.setNextRepayDate(borPayInfos[0].getRepaymentTime());//次回还款日
			
			// ▽▽▽增加借款人账户资金 开始▽▽▽
			// 取得借款人账户信息
			UserAccount userAccount = userAccountDao.load(borrow1.getUser().getAccount().getId());
			userAccount = userAccountDao.loadLock(userAccount.getId());// 锁表

			// 可用余额 = 原可用金额 + 本次的待还本金
			userAccount.setAbleMoney(userAccount.getAbleMoney().add(borrowAllCapital));
			// 待还本金
			userAccount.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital().add(borrowAllCapital));
			// 待还利息
			userAccount.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest().add(borrowAllInterest));

			userAccountDao.update(userAccount);// 修改资金表数据

//			UserAccountDetail userAccountDetail1 = InterestCalUtil.saveAccountDetail("borrow_success", // 借款成功
//					borrowAllCapital, // 借款的金额
//					"对标[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]借款成功", // 备注
//					userAccount.getUser(), userAccount, borrow1.getUser().getId(), String.valueOf(adminId), // 操作者
//					ServletActionContext.getRequest().getRemoteAddr());//
//			userAccountDetailDao.save(userAccountDetail1);
			// △△△增加借款人账户资金 结束△△△

			// ★★★★借款人的奖金发放 开始★★★★
			if ("1".equals(borrow1.getAward())) {

				if (0 == borrow1.getAwardType()) {

					// 扣除借款人的现金奖励
					userAccount.setAbleMoney(userAccount.getAbleMoney().subtract(rewardAll));
					userAccount.setTotal(userAccount.getTotal().subtract(rewardAll));
					userAccountDao.update(userAccount);

//					UserAccountDetail userAccountDetail3 = InterestCalUtil.saveAccountDetail("award_lower", //
//							rewardAll, //
//							"扣除借款[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]的奖励",//
//							userAccount.getUser(), userAccount, 0, String.valueOf(adminId),//
//							ServletActionContext.getRequest().getRemoteAddr());//
//					userAccountDetailDao.save(userAccountDetail3);
				} else {

					// 扣除借款人的红包奖励
					// userAccount.setAwardMoney(userAccount.getAwardMoney().subtract(rewardAll));//
					// 扣除借款人红包奖励
					// userAccount.setTotal(userAccount.getTotal().subtract(rewardAll));//
					// 扣除借款人总额
					// userAccountDao.update(userAccount);
					//
					// UserAccountDetail userAccountDetail3 =
					// InterestCalUtil.saveAccountDetail("award_lower", //
					// rewardAll, //
					// "扣除借款[" + CommonUtil.fillBorrowUrl(borrow1.getId(),
					// borrow1.getName()) + "]的红包奖励",//
					// userAccount.getUser(), userAccount, 0,
					// String.valueOf(adminId),//
					// ServletActionContext.getRequest().getRemoteAddr());//
					// userAccountDetailDao.save(userAccountDetail3);
				}

			}
			// ★★★★借款人的奖金发放 结束★★★★

			//截标红包(id=5)
			Hongbao hbaojibiao = hongbaoDao.get(5);
			List<BorrowDetail> bdList =	borrowDetailDao.getBorrowDetailList(borrow1, null);//id 正排序
			if(hbaojibiao != null && hbaojibiao.getIsEnabled().compareTo(1) == 0 && bdList != null && bdList.size() >0){//有截标奖&&开启着&&有投标详情
				//截标的投资用户
				BorrowDetail bd_end = bdList.get(bdList.size()-1);
				//截标奖的红包
				List<HongbaoDetail> hbDetailList = (List<HongbaoDetail>) new Gson().fromJson(hbaojibiao.getHongbaoDetail(),
						new TypeToken<List<HongbaoDetail>>() {
						}.getType());
				for (HongbaoDetail hbdetail : hbDetailList) {
					if( hbdetail != null && hbdetail.getMoney()!=null && hbdetail.getMoney().compareTo(BigDecimal.ZERO) > 0){
						if(bd_end.getAccount().compareTo(new BigDecimal(hbdetail.getInvestmentStart())) >= 0 && bd_end.getAccount().compareTo(new BigDecimal(hbdetail.getInvestmentEnd())) <= 0 ){
							
							BigDecimal award0 = hbdetail.getMoney();
							UserAccount userAccount0 = userAccountDao.loadLockTable(bd_end.getUser());// 锁表
							userAccount0.setAwardMoney(userAccount0.getAwardMoney().add(award0));
							userAccountDao.update(userAccount0);

							// 添加红包记录
							UserHongbao hb = new UserHongbao();
							hb.setUser(bd_end.getUser());
							hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd") + CommonUtil.getRandomString(5));
							hb.setName(borrow1.getName() +"截标奖");
							hb.setMoney(award0);
							hb.setUsedMoney(BigDecimal.ZERO);
							hb.setStatus(0);
							Date d = new Date();
							hb.setStartTime(d);
							hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d, hbdetail.getExpDate())));
							hb.setSource(6);
							hb.setSourceString("截标奖");
							hb.setSourceUserId(null);
							hb.setSourceBorrowId(borrow1.getId());
							hb.setUsedBorrowId(null);

							hb.setExpDate(hbdetail.getExpDate());
							hb.setLimitStart(hbdetail.getLimitStart());
							hb.setLimitEnd(hbdetail.getLimitEnd());
							hb.setIsPc(hbdetail.getIsPc());
							hb.setIsApp(hbdetail.getIsApp());
							hb.setIsHfive(hbdetail.getIsHfive());
							hb.setInvestFullMomey(hbdetail.getInvestFullMomey());
							hb.setIsLooked(0);
							userHongbaoDao.save(hb);
							// 奖励账户资金记录
							UserAwardDetail uad = new UserAwardDetail();
							uad.setUser(bd_end.getUser());// 用户ID
							uad.setType("award_jiebiao");// 类型（同资金记录）
							uad.setMoney(award0);// 操作金额
							uad.setSignFlg(1);
							uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
							uad.setRemark("[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]的截标红包奖励");// 备注
							uad.setReserve1(hb.getHbNo());
							userAwardDetailDao.save(uad);
							
							//收官大哥得到的钱，记录到项目中
							borrow1.setSgdg(award0);
						}
					}
				}
			}
			//土豪红包（id=4）
			Hongbao hbaoth = hongbaoDao.get(4);
			List<BorrowDetail> tuhaoList = new ArrayList<BorrowDetail>();//土豪投资们（投资额一样大）
			//找出土豪们
			List<BorrowDetail> bdListAll = borrowDetailDao.getBorrowDetailList(borrow1, new HashMap<String, String>());//投资金额倒排序
			if(bdListAll != null && bdListAll.size() > 0){
				BigDecimal max = bdListAll.get(0).getAccount();
				for(BorrowDetail borrowDetail : bdListAll){
					if(borrowDetail.getAccount().compareTo(max) == 0){
						tuhaoList.add(borrowDetail);//装入土豪集合
					}
				}
			}
			if(hbaoth != null && hbaoth.getIsEnabled().compareTo(1) == 0 && tuhaoList.size() >0){//有土豪奖&&开启着&&有土豪投标详情
				//找出要发的那个区间的红包
				HongbaoDetail hongbaoDetailAble = null; //土豪所对应的区间的红包
				List<HongbaoDetail> hbDetailList = (List<HongbaoDetail>) new Gson().fromJson(hbaoth.getHongbaoDetail(),
						new TypeToken<List<HongbaoDetail>>() {
						}.getType());
				for (HongbaoDetail hbdetail : hbDetailList) {
					if( hbdetail != null ){
						if(tuhaoList.get(0).getAccount().compareTo(new BigDecimal(hbdetail.getInvestmentStart())) >= 0 && tuhaoList.get(0).getAccount().compareTo(new BigDecimal(hbdetail.getInvestmentEnd())) <= 0 ){
							hongbaoDetailAble = hbdetail;
							//土豪得到的钱，记录到项目中
							borrow1.setTzth(hbdetail.getMoney());
						}
					}
				}
				if(hongbaoDetailAble != null && hongbaoDetailAble.getMoney()!=null && hongbaoDetailAble.getMoney().compareTo(BigDecimal.ZERO) > 0){//匹配到红包后，平分红包
					BigDecimal [] hongbaoMoneys = new BigDecimal[tuhaoList.size()];//平分的红包
					int one = hongbaoDetailAble.getMoney().intValue()/tuhaoList.size();
					for(int i=0 ; i < hongbaoMoneys.length ;i++ ){
						if(i == hongbaoMoneys.length - 1 ){
							hongbaoMoneys[i] = new BigDecimal(hongbaoDetailAble.getMoney().intValue() - (hongbaoMoneys.length - 1) * one);
						}else{
							hongbaoMoneys[i] = new BigDecimal(one);
						}
					}
					//给土豪们发红包
					for(int i=0 ;i<tuhaoList.size() ;i++){
						BorrowDetail tuhaoBorrowDetail = tuhaoList.get(i);//土豪投资
						BigDecimal award0 = hongbaoMoneys[i];//分到的钱
						UserAccount userAccount0 = userAccountDao.loadLockTable(tuhaoBorrowDetail.getUser());// 锁表
						userAccount0.setAwardMoney(userAccount0.getAwardMoney().add(award0));
						userAccountDao.update(userAccount0);

						// 添加红包记录
						UserHongbao hb = new UserHongbao();
						hb.setUser(tuhaoBorrowDetail.getUser());
						hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd") + CommonUtil.getRandomString(5));
						hb.setName(borrow1.getName() +"土豪奖");
						hb.setMoney(award0);
						hb.setUsedMoney(BigDecimal.ZERO);
						hb.setStatus(0);
						Date d = new Date();
						hb.setStartTime(d);
						hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d, hongbaoDetailAble.getExpDate())));
						hb.setSource(6);
						hb.setSourceString("土豪奖");
						hb.setSourceUserId(null);
						hb.setSourceBorrowId(borrow1.getId());
						hb.setUsedBorrowId(null);

						hb.setExpDate(hongbaoDetailAble.getExpDate());
						hb.setLimitStart(hongbaoDetailAble.getLimitStart());
						hb.setLimitEnd(hongbaoDetailAble.getLimitEnd());
						hb.setIsPc(hongbaoDetailAble.getIsPc());
						hb.setIsApp(hongbaoDetailAble.getIsApp());
						hb.setIsHfive(hongbaoDetailAble.getIsHfive());
						hb.setInvestFullMomey(hongbaoDetailAble.getInvestFullMomey());
						hb.setIsLooked(0);
						userHongbaoDao.save(hb);
						// 奖励账户资金记录
						UserAwardDetail uad = new UserAwardDetail();
						uad.setUser(tuhaoBorrowDetail.getUser());// 用户ID
						uad.setType("award_tuhao");// 类型（同资金记录）
						uad.setMoney(award0);// 操作金额
						uad.setSignFlg(1);
						uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
						uad.setRemark("[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]的土豪红包奖励");// 备注
						uad.setReserve1(hb.getHbNo());
						userAwardDetailDao.save(uad);
					}
				}
				
				
			}
		} else if (borrow.getStatus() == 4) {// 满标审核不通过

			// 审核不成功 回滚投资人投资金额
			for (BorrowDetail borrowDetail : borrowDetailSet) {
				UserAccount userAccount = userAccountDao.load(borrowDetail.getUser().getId());// 获取投标人账户资金
				userAccount = userAccountDao.loadLock(userAccount.getId());

				BigDecimal money = new BigDecimal(borrowDetail.getAbleAmount());// 现金投资金额
																				// borrowDetail.getAccount()

				userAccount.setUnableMoney(userAccount.getUnableMoney().subtract(money));
				userAccount.setAbleMoney(userAccount.getAbleMoney().add(money));
				userAccountDao.update(userAccount);

				log.info("保存资金账户操作详细记录--扣去投标金额");
				UserAccountDetail userAccountDetail1 = InterestCalUtil.saveAccountDetail("invest_false", //
						money, //
						"招标[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]失败返回的投标额", //
						userAccount.getUser(), userAccount, userAccount.getUser().getId(),//
						String.valueOf(adminId), //
						ServletActionContext.getRequest().getRemoteAddr());//
				userAccountDetailDao.save(userAccountDetail1);
			}

			// 已使用红包投资变 未使用
			Map<String, Object> hbMap = new HashMap<String, Object>();
			hbMap.put("usedBorrowId", borrow.getId());
			hbMap.put("status", 1);
			List<UserHongbao> hbList = userHongbaoDao.getUserHongbaoList(hbMap);

			if (hbList != null && hbList.size() > 0) {
				for (UserHongbao hb : hbList) {
					hb.setStatus(0);
					hb.setUsedBorrowId(null);
					hb.setUsedMoney(BigDecimal.ZERO);
					userHongbaoDao.update(hb);

					UserAwardDetail uad = new UserAwardDetail();
					uad.setUser(hb.getUser());// 用户ID
					uad.setType("hongbao_back");// 类型（同资金记录）
					uad.setMoney(hb.getMoney());// 操作金额
					uad.setAwardMoney(null);// 奖励账户
					uad.setSignFlg(1);
					uad.setRemark("招标[" + CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName()) + "]失败，红包返还");// 备注
					uad.setReserve1(hb.getHbNo());
					userAwardDetailDao.save(uad);

				}
			}

			// 修改标的信息
			borrowDao.update(borrow1);
		}
		return 0;
	}
	
	@Override
	public synchronized int updateBorrowFull(Borrow borrow, Integer id, Admin admin) {
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
		List<User> userList = new ArrayList<User>();

		if (borrow.getStatus() == 3) { // 满标审核

			// ※※※※※积分功能 开始※※※※※
			Listing listingPointAble = listingDao.getListingBySign("user_points_able");
			String userPointsAble = "0";
			double userPointsRateTender = 0;
			if (listingPointAble != null && !StringUtils.isNotEmpty(listingPointAble.getKeyValue()) && "1".equals(listingPointAble.getKeyValue())) {
				userPointsAble = listingPointAble.getKeyValue();

				Listing listingPointsRateTender = listingDao.getListingBySign("user_points_rate_tender");
				if (listingPointsRateTender != null && !StringUtils.isNotEmpty(listingPointsRateTender.getKeyValue())) {
					userPointsRateTender = Double.valueOf(listingPointsRateTender.getKeyValue());
				}
			}
			// ※※※※※积分功能 结束※※※※※

			// 利息管理费
			Listing listing_t = listingDao.load(1473);
//			Listing listing_b = listingDao.load(1469);// 借款手续费比例

			// 修改标的信息
			borrow1.setStatus(borrow.getStatus());
			borrow1.setRepamyentRemark(borrow.getVerifyRemark());
			borrow1.setRepaymentUser(adminId);
			borrow1.setSuccessTime(new Date());
			
			if ("0".equals(borrow1.getIsday())) {
				borrow1.setFinalRepayDate(CommonUtil.getMonthAfter(borrow1.getSuccessTime(), Integer.parseInt(borrow1.getTimeLimit())));
			} else if ("1".equals(borrow1.getIsday())) {
				borrow1.setFinalRepayDate(CommonUtil.getDateAfter(borrow1.getSuccessTime(), Integer.parseInt(borrow1.getTimeLimit())));
			}
			

			// 计算标的每一期的收益
			RepaymentInfo repaymentInfo = PromoteUtil.promotePlan(Integer.parseInt(borrow1.getIsday()), //
					Integer.parseInt(borrow1.getStyle()),//
					Integer.parseInt(borrow1.getTimeLimit()),//
					borrow1.getDivides(), //
					borrow1.getAccount(), //
					new BigDecimal(borrow1.getApr())//
					);

			// 每一期的本金
			BigDecimal[] issueCapital = new BigDecimal[repaymentInfo.getOrderNum()];
			// 每一期的利息
			BigDecimal[] issueInterest = new BigDecimal[repaymentInfo.getOrderNum()];
			// 奖金总额
			BigDecimal rewardAll = new BigDecimal(0);

			// 将标分期
			Integer[] borPayIds = new Integer[repaymentInfo.getOrderNum()];
			BorrowRepaymentDetail[] borPayInfos = new BorrowRepaymentDetail[repaymentInfo.getOrderNum()];
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

				borRepayDetail.setRepaymentTime(repaymentInfo.getRepaymentDetailList().get(i).getRepaymentDate());
				borRepayDetail.setRepaymentDateInt(repaymentInfo.getRepaymentDetailList().get(i).getRepaymentDateInt());
				borRepayDetail.setInterest(issueInterest[i]);// 应还本金
				borRepayDetail.setCapital(issueCapital[i]);// 应还利息
				borRepayDetail.setBorrow(borrow1);
				borRepayDetail.setRepaymentAccount(borRepayDetail.getCapital().add(borRepayDetail.getInterest()));
				borRepayDetail.setRepaymentYesaccount(BigDecimal.ZERO);
				borRepayDetail.setReminderFee(BigDecimal.ZERO);// 奖励
				borRepayDetail.setRechargeStatus(0);
				borrowRepaymentDetailDao.save(borRepayDetail);

				borPayIds[i] = borRepayDetail.getId();
				borPayInfos[i] = borRepayDetail;

			}

			//项目复审，给好友的收益奖励比例
			Listing jlbl = listingDao.getListingBySign("shouyijianglibili");//收益奖励比例
			BigDecimal syjlbl = new BigDecimal(jlbl.getKeyValue());
			
			
			Listing listingQixain = listingDao.getListingBySign("tuijian_award2");// 红包有效期
			Integer day_qixian = Integer.parseInt(listingQixain.getKeyValue());
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
					userRepaymentDetail.setRepaymentAccount(retView.getRepaymentDetailList().get(i).getAccount());
					userRepaymentDetail.setInterest(retView.getRepaymentDetailList().get(i).getInterest());
					userRepaymentDetail.setWaitInterest(retView.getRepaymentDetailList().get(i).getInterest());
					userRepaymentDetail.setWaitAccount(retView.getRepaymentDetailList().get(i).getCapital());
					userRepaymentDetail.setAddPersion(String.valueOf(adminId));
					userRepaymentDetail.setOperatorIp(ServletActionContext.getRequest().getRemoteAddr());
					userRepaymentDetail.setBorrowPeriods(i);
					userRepaymentDetail.setUser(borrowDetail.getUser());
					userRepaymentDetail.setBorrow(borrowDetail.getBorrow());
					userRepaymentDetail.setBorrowRepaymentDetail(borPayInfos[i]);
					userRepaymentDetail.setRepaymentDate(borPayInfos[i].getRepaymentTime());
					userRepaymentDetail.setBorrowDivides(borrow1.getDivides());

					// 利息管理费
					// Listing listing_t = listingDao.load(1473);
					BigDecimal userserviceCharge = SettingUtil.setPriceScale(BigDecimal.valueOf((userRepaymentDetail.getInterest().doubleValue())
							* Double.parseDouble(listing_t.getKeyValue())));
					userRepaymentDetail.setServiceCharge(userserviceCharge);
					userRepaymentDetail.setProfit(userRepaymentDetail.getInterest().subtract(userRepaymentDetail.getServiceCharge()));
					userRepaymentDetail.setBorrowDetail(borrowDetail);

					userRepaymentDetailDao.save(userRepaymentDetail);

					// 所有投资人本期还款本金统计
					issueCapital[i] = issueCapital[i].add(userRepaymentDetail.getWaitAccount());
					// 所有投资人本期还款利息统计
					issueInterest[i] = issueInterest[i].add(userRepaymentDetail.getWaitInterest());
					// 投资人待收本金
					waitCapital = waitCapital.add(userRepaymentDetail.getWaitAccount());
					// 投资人待收利息
					waitInterest = waitInterest.add(userRepaymentDetail.getWaitInterest());
				}

				// ◆◆◆投资人资金修改&&日志 开始◆◆◆
				// 获取投标人账户资金
				UserAccount userAccount1 = userAccountDao.loadLockTable(borrowDetail.getUser());// 锁表
				//红包进入总额
				if(StringUtils.isNotEmpty(borrowDetail.getHongbaoAmount())){
					userAccount1.setTotal(userAccount1.getTotal().add(new BigDecimal(borrowDetail.getHongbaoAmount())));
				}else{
					userAccount1.setTotal(userAccount1.getTotal());
				}
				
				// 冻结金额 = 冻结金额-投资金额
				userAccount1.setUnableMoney(userAccount1.getUnableMoney().subtract(new BigDecimal(borrowDetail.getAbleAmount())));
				// 投资人待收本金 增加
				userAccount1.setInvestorCollectionCapital(userAccount1.getInvestorCollectionCapital().add(waitCapital));
				// 投资人待收利息 增加
				userAccount1.setInvestorCollectionInterest(userAccount1.getInvestorCollectionInterest().add(waitInterest));

				// ※※※ 积分发放记录 开始※※※※※※※※※
				boolean pointsFlg = false;
				int userPoints = 0;
				if ("1".equals(userPointsAble)) {
					userPoints = (int) (userPointsRateTender * waitCapital.doubleValue() * Integer.parseInt(borrow1.getTimeLimit()) * 30);// 月标以30天计算
					userAccount1.setUserPoints((userAccount1.getUserPoints() == null ? 0 : userAccount1.getUserPoints()) + userPoints);
					pointsFlg = true;
				}
				// ※※※ 积分发放记录 结束※※※※※※※※※

				userAccountDao.update(userAccount1);// 【完成修改投资人账户】

				// 记入账户日志
				UserAccountDetail userAccountDetail1 = InterestCalUtil.saveAccountDetail("invest", // 类型:投资
						borrowDetail.getAccount(), // 变动金额
						"投标成功费用扣除[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]", //
						userAccount1.getUser(), userAccount1, borrow1.getUser().getId(), //
						String.valueOf(adminId), //
						ServletActionContext.getRequest().getRemoteAddr());
				userAccountDetailDao.save(userAccountDetail1);
				// ◆◆◆投资人资金修改&&日志 结束◆◆◆

				// ※※※ 积分发放记录 开始※※※※※※※※※
				
				// ※※※ 积分发放记录 结束※※※※※※※※※

				// ★★★★投资人的奖金发放 开始★★★★
				if ("1".equals(borrow1.getAward())) {
					// 满标发奖励
					BigDecimal reward = new BigDecimal(0);
					if (0 == borrow1.getAwardType()) {
						// 奖励现金

						// 奖金金额
						double app = Double.parseDouble(borrow1.getFunds()) / 100;
						reward = borrowDetail.getAccount().multiply(BigDecimal.valueOf(app));

						userAccount1.setTotal(userAccount1.getTotal().add(reward));
						userAccount1.setAbleMoney(SettingUtil.setPriceScale(userAccount1.getAbleMoney().add(reward)));
						userAccountDao.update(userAccount1);// 【完成修改投资人账户】

						// 奖金日志记录
						UserAccountDetail userAccountDetail4 = InterestCalUtil.saveAccountDetail(
								"award_add", // 奖励
								reward, "借款[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]的奖励", userAccount1.getUser(),
								userAccount1, borrow1.getUser().getId(), String.valueOf(adminId), ServletActionContext.getRequest().getRemoteAddr());
						userAccountDetailDao.save(userAccountDetail4);
					} else {
						// 红包奖励

						// 奖金金额
						double app = Double.parseDouble(borrow1.getFunds()) / 100;
						reward = new BigDecimal( borrowDetail.getAccount().multiply(BigDecimal.valueOf(app)).toBigInteger());
						
						if(reward.compareTo(BigDecimal.ZERO) >0){
							userAccount1.setAwardMoney(SettingUtil.setPriceScale(userAccount1.getAwardMoney().add(reward)));// 添加红包账户奖励
							userAccountDao.update(userAccount1);// 【完成修改投资人账户】
	
							// 投资奖励
							UserHongbao hb = new UserHongbao();
//							hb.setUserId(userAccount1.getUser().getId());
							hb.setUser(userAccount1.getUser());
							hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd") + CommonUtil.getRandomString(5));
							hb.setName("投资奖励");
							hb.setMoney(reward);
							hb.setUsedMoney(BigDecimal.ZERO);
							hb.setStatus(0);
							Date d = new Date();
							hb.setStartTime(d);
							hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d, day_qixian)));
							hb.setSource(3);
							hb.setSourceString("投资奖励");
							hb.setSourceUserId(null);
							hb.setSourceBorrowId(borrow.getId());
							hb.setUsedBorrowId(null);
							userHongbaoDao.save(hb);
	
							UserAwardDetail uad = new UserAwardDetail();
							uad.setUser(userAccount1.getUser());// 用户ID
							uad.setType("award_toubiao");// 类型（同资金记录）
							uad.setMoney(reward);// 操作金额
							uad.setAwardMoney(userAccount1.getAwardMoney());// 奖励账户
							uad.setSignFlg(1);
							uad.setRemark("借款[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]的红包奖励");// 备注
							uad.setReserve1(hb.getHbNo());
							userAwardDetailDao.save(uad);
						}
					}

					// 统计发放的所有奖金
					rewardAll = rewardAll.add(reward);

				} else if ("2".equals(borrow1.getAward())) {
					// 还款发奖励
					// 奖金金额
					double app = Double.parseDouble(borrow1.getFunds()) / 100;
					BigDecimal reward = borrowDetail.getAccount().multiply(BigDecimal.valueOf(app));
					// 统计发放的所有奖金
					rewardAll = rewardAll.add(reward);

				}
				// ★★★★投资人的奖金发放 结束★★★★

				// ************************************************************************************************************************************
				// ★★★★投资人续投宝金额奖励 开始★★★★

				// ★★★★投资人续投宝金额奖励 开始★★★★
				if (StringUtils.isNotEmpty(borrowDetail.getContinueAmount()) && Double.valueOf(borrowDetail.getContinueAmount()) > 0) {
					if (borrowDetail.getContinueAmount() != "") {
						// 获取投标人账户资金
						UserAccount userAccount = userAccountDao.load(borrowDetail.getUser().getAccount().getId());

						userAccount = userAccountDao.loadLock(userAccount.getId());

						BigDecimal getaward = new BigDecimal(borrowDetail.getContinueAmount()).multiply(borrow1.getContinueAwardRate());

						userAccount.setTotal(userAccount1.getTotal().add(getaward));
						userAccount.setAbleMoney(SettingUtil.setPriceScale(userAccount1.getAbleMoney().add(getaward)));
						userAccountDao.update(userAccount);// 【完成修改投资人账户】

						// 奖金日志记录
						UserAccountDetail userAccountDetail4 = InterestCalUtil.saveAccountDetail(
								"award_continued", // 奖励
								getaward, "对借款[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]的续投奖励", userAccount.getUser(),
								userAccount, userAccount.getUser().getId(), String.valueOf(adminId), ServletActionContext.getRequest()
										.getRemoteAddr());
						userAccountDetailDao.save(userAccountDetail4);

					}
				}
				// ★★★★投资人的续投奖励发放 结束★★★★
				//好友奖励发放---投资人收益的 10%
				Integer tgUserId = borrowDetail.getUser().getTgOneLevelUserId();
				if(tgUserId != null){
					User tgUser = userDao.get(tgUserId);
					UserAccount tgUserAccount = userAccountDao.loadLockTable(tgUser);// 锁表
					
					BigDecimal reward = waitInterest.multiply(syjlbl);
					tgUserAccount.setTotal(tgUserAccount.getTotal().add(reward));
					tgUserAccount.setAbleMoney(SettingUtil.setPriceScale(tgUserAccount.getAbleMoney().add(reward)));
					userAccountDao.update(tgUserAccount);// 【完成修改邀请人账户】

					// 奖金日志记录
					UserAccountDetail tgDetail = InterestCalUtil.saveAccountDetail(
							"tui_detail_award", // 奖励
							reward, "好友投资收益奖励", tgUser,
							tgUserAccount, tgUserId, String.valueOf(adminId), ServletActionContext.getRequest().getRemoteAddr());
					userAccountDetailDao.save(tgDetail);
					
					UserAwardDetail uad = new UserAwardDetail();
					uad.setUser(tgUser);// 用户ID
					uad.setType("tui_detail_award");// 类型（同资金记录）
					uad.setMoney(reward);// 操作金额
					uad.setAwardMoney(tgUserAccount.getAwardMoney());// 奖励账户
					uad.setSignFlg(1);
					uad.setRemark("好友["+borrowDetail.getUser().getUsername()+"]投资收益奖励");// 备注
					uad.setRelateKey("user_id");
					uad.setRelateTo(borrowDetail.getUser().getId());
					userAwardDetailDao.save(uad);
				}
				userList.add(borrowDetail.getUser());
			}

			//修改推广渠道数据
//			updateIOSIdfa(userList);
			
			// 总的借款金额
			BigDecimal borrowAllCapital = new BigDecimal(0);
			// 总的借款利息
			BigDecimal borrowAllInterest = new BigDecimal(0);

			// 借款人每期还款情况
			for (int i = 0; i < repaymentInfo.getOrderNum(); i++) {

				borRepayDetail = borPayInfos[i];
				borRepayDetail.setInterest(issueInterest[i]);
				borRepayDetail.setCapital(issueCapital[i]);
				if ("2".equals(borrow1.getAward()) && (i == repaymentInfo.getOrderNum() - 1)) {
					borRepayDetail.setReminderFee(rewardAll);
				}
				borRepayDetail.setRepaymentAccount(borRepayDetail.getCapital().add(borRepayDetail.getInterest()).add(rewardAll));
				
				
				borRepayDetail.setRechargeMoney(borRepayDetail.getCapital().add(borRepayDetail.getInterest()));
				
				if(i==(repaymentInfo.getOrderNum()-1)) {
					borRepayDetail.setRechargeMoney(borRepayDetail.getRechargeMoney());//插入还款充值金额
					//borRepayDetail.getRechargeMoney().subtract(borrow1.getDepositMoney()==null?BigDecimal.ZERO:borrow1.getDepositMoney())
				}
				
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
			
			borrow1.setBorrowMoney(borrow1.getAccount());//项目余额
			borrow1.setFangkuanStatus(0);
			borrow1.setNextRepayDate(borPayInfos[0].getRepaymentTime());//次回还款日
			
			// ▽▽▽增加借款人账户资金 开始▽▽▽
			// 取得借款人账户信息
			UserAccount userAccount = userAccountDao.load(borrow1.getUser().getAccount().getId());
			userAccount = userAccountDao.loadLock(userAccount.getId());// 锁表

			// 可用余额 = 原可用金额 + 本次的待还本金
			userAccount.setAbleMoney(userAccount.getAbleMoney().add(borrowAllCapital));
			// 待还本金
			userAccount.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital().add(borrowAllCapital));
			// 待还利息
			userAccount.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest().add(borrowAllInterest));

			userAccountDao.update(userAccount);// 修改资金表数据

//			UserAccountDetail userAccountDetail1 = InterestCalUtil.saveAccountDetail("borrow_success", // 借款成功
//					borrowAllCapital, // 借款的金额
//					"对标[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]借款成功", // 备注
//					userAccount.getUser(), userAccount, borrow1.getUser().getId(), String.valueOf(adminId), // 操作者
//					ServletActionContext.getRequest().getRemoteAddr());//
//			userAccountDetailDao.save(userAccountDetail1);
			// △△△增加借款人账户资金 结束△△△

			// ★★★★借款人的奖金发放 开始★★★★
			if ("1".equals(borrow1.getAward())) {

				if (0 == borrow1.getAwardType()) {

					// 扣除借款人的现金奖励
					userAccount.setAbleMoney(userAccount.getAbleMoney().subtract(rewardAll));
					userAccount.setTotal(userAccount.getTotal().subtract(rewardAll));
					userAccountDao.update(userAccount);

//					UserAccountDetail userAccountDetail3 = InterestCalUtil.saveAccountDetail("award_lower", //
//							rewardAll, //
//							"扣除借款[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]的奖励",//
//							userAccount.getUser(), userAccount, 0, String.valueOf(adminId),//
//							ServletActionContext.getRequest().getRemoteAddr());//
//					userAccountDetailDao.save(userAccountDetail3);
				} else {

					// 扣除借款人的红包奖励
					// userAccount.setAwardMoney(userAccount.getAwardMoney().subtract(rewardAll));//
					// 扣除借款人红包奖励
					// userAccount.setTotal(userAccount.getTotal().subtract(rewardAll));//
					// 扣除借款人总额
					// userAccountDao.update(userAccount);
					//
					// UserAccountDetail userAccountDetail3 =
					// InterestCalUtil.saveAccountDetail("award_lower", //
					// rewardAll, //
					// "扣除借款[" + CommonUtil.fillBorrowUrl(borrow1.getId(),
					// borrow1.getName()) + "]的红包奖励",//
					// userAccount.getUser(), userAccount, 0,
					// String.valueOf(adminId),//
					// ServletActionContext.getRequest().getRemoteAddr());//
					// userAccountDetailDao.save(userAccountDetail3);
				}

			}
			// ★★★★借款人的奖金发放 结束★★★★

			//投资排名
			//投资先锋奖励金额
//			Listing tzxfjl = listingDao.getListingBySign("tzxf_award");
//			BigDecimal frist_award = new BigDecimal(tzxfjl.getKeyValue());
			BigDecimal frist_award = borrow1.getTzxf();
			//收官大哥奖励金额
//			Listing sgdgjl = listingDao.getListingBySign("sgdg_award");
//			BigDecimal end_award = new BigDecimal(sgdgjl.getKeyValue());
			BigDecimal end_award = borrow1.getSgdg();
			
			//投资土壕奖励金额
//			Listing tzthjl = listingDao.getListingBySign("tzth_award");
//			BigDecimal max_award = new BigDecimal(tzthjl.getKeyValue());
			BigDecimal max_award = borrow1.getTzth();
			
			List<BorrowDetail> bdList =	borrowDetailDao.getBorrowDetailList(borrow1, null);
			if(bdList != null && bdList.size() >0){
				//投资先锋
				BorrowDetail bd_first = bdList.get(0);
				
				UserAccount frist_account = userAccountDao.loadLockTable( bd_first.getUser() );

				frist_account.setTotal(frist_account.getTotal().add(frist_award));
				frist_account.setAbleMoney(SettingUtil.setPriceScale(frist_account.getAbleMoney().add(frist_award)));
				userAccountDao.update(frist_account);// 【完成修改投资人账户】

				// 奖金日志记录
				UserAccountDetail first_detail = InterestCalUtil.saveAccountDetail(
						"award_add", // 奖励
						frist_award, "借款[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]的投资先锋奖励", frist_account.getUser(),
						frist_account, borrow1.getUser().getId(), String.valueOf(adminId), ServletActionContext.getRequest().getRemoteAddr());
				userAccountDetailDao.save(first_detail);
				
				//收官大哥
				BorrowDetail bd_end = bdList.get(bdList.size()-1);
				
				UserAccount end_account = userAccountDao.loadLockTable( bd_end.getUser() );

				end_account.setTotal(end_account.getTotal().add(end_award));
				end_account.setAbleMoney(SettingUtil.setPriceScale(end_account.getAbleMoney().add(end_award)));
				userAccountDao.update(end_account);// 【完成修改投资人账户】

				// 奖金日志记录
				UserAccountDetail end_detail = InterestCalUtil.saveAccountDetail(
						"award_add", // 奖励
						end_award, "借款[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]的收官大哥奖励", end_account.getUser(),
						end_account, borrow1.getUser().getId(), String.valueOf(adminId), ServletActionContext.getRequest().getRemoteAddr());
				userAccountDetailDao.save(end_detail);
			}
			
			Map<String, String> bdMap = new HashMap<String, String>();
			bdMap.put("maxMoney", "true");
			bdList = borrowDetailDao.getBorrowDetailList(borrow1, bdMap);
			if(bdList != null && bdList.size() >0){
				//土壕
				BorrowDetail bd_max = bdList.get(0);
				
				UserAccount max_account = userAccountDao.loadLockTable( bd_max.getUser() );

				max_account.setTotal(max_account.getTotal().add(max_award));
				max_account.setAbleMoney(SettingUtil.setPriceScale(max_account.getAbleMoney().add(max_award)));
				userAccountDao.update(max_account);// 【完成修改投资人账户】

				// 奖金日志记录
				UserAccountDetail max_detail = InterestCalUtil.saveAccountDetail(
						"award_add", // 奖励
						max_award, "借款[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]的投资土壕奖励", max_account.getUser(),
						max_account, borrow1.getUser().getId(), String.valueOf(adminId), ServletActionContext.getRequest().getRemoteAddr());
				userAccountDetailDao.save(max_detail);
			}
			
		} else if (borrow.getStatus() == 4) {// 满标审核不通过

			// 审核不成功 回滚投资人投资金额
			for (BorrowDetail borrowDetail : borrowDetailSet) {
				UserAccount userAccount = userAccountDao.load(borrowDetail.getUser().getId());// 获取投标人账户资金
				userAccount = userAccountDao.loadLock(userAccount.getId());

				BigDecimal money = new BigDecimal(borrowDetail.getAbleAmount());// 现金投资金额
																				// borrowDetail.getAccount()

				userAccount.setUnableMoney(userAccount.getUnableMoney().subtract(money));
				userAccount.setAbleMoney(userAccount.getAbleMoney().add(money));
				userAccountDao.update(userAccount);

				log.info("保存资金账户操作详细记录--扣去投标金额");
				UserAccountDetail userAccountDetail1 = InterestCalUtil.saveAccountDetail("invest_false", //
						money, //
						"招标[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]失败返回的投标额", //
						userAccount.getUser(), userAccount, userAccount.getUser().getId(),//
						String.valueOf(adminId), //
						ServletActionContext.getRequest().getRemoteAddr());//
				userAccountDetailDao.save(userAccountDetail1);
			}

			// 已使用红包投资变 未使用
			Map<String, Object> hbMap = new HashMap<String, Object>();
			hbMap.put("usedBorrowId", borrow.getId());
			hbMap.put("status", 1);
			List<UserHongbao> hbList = userHongbaoDao.getUserHongbaoList(hbMap);

			if (hbList != null && hbList.size() > 0) {
				for (UserHongbao hb : hbList) {
					hb.setStatus(0);
					hb.setUsedBorrowId(null);
					hb.setUsedMoney(BigDecimal.ZERO);
					userHongbaoDao.update(hb);

					UserAwardDetail uad = new UserAwardDetail();
					uad.setUser(hb.getUser());// 用户ID
					uad.setType("hongbao_back");// 类型（同资金记录）
					uad.setMoney(hb.getMoney());// 操作金额
					uad.setAwardMoney(null);// 奖励账户
					uad.setSignFlg(1);
					uad.setRemark("招标[" + CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName()) + "]失败，红包返还");// 备注
					uad.setReserve1(hb.getHbNo());
					userAwardDetailDao.save(uad);

				}
			}

			// 修改标的信息
			borrowDao.update(borrow1);
		}
		return 0;
	}

	@Override
	public int updateTiYanBorrowFull(Borrow borrow, Integer id, Admin admin) {
		// TODO Auto-generated method stub
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

					// 利息管理费
					Listing listing_t = listingDao.load(1473);

					// 修改标的信息
					borrow1.setStatus(borrow.getStatus());
					borrow1.setRepamyentRemark(borrow.getVerifyRemark());
					borrow1.setRepaymentUser(adminId);
					borrow1.setSuccessTime(new Date());
					if ("0".equals(borrow1.getIsday())) {
						borrow1.setFinalRepayDate(CommonUtil.getMonthAfter(borrow1.getSuccessTime(), Integer.parseInt(borrow1.getTimeLimit())));
					} else if ("1".equals(borrow1.getIsday())) {
						borrow1.setFinalRepayDate(CommonUtil.getDateAfter(borrow1.getSuccessTime(), Integer.parseInt(borrow1.getTimeLimit())));
					}
					

					// 计算标的每一期的收益
					RepaymentInfo repaymentInfo = PromoteUtil.promotePlan(Integer.parseInt(borrow1.getIsday()), //
							Integer.parseInt(borrow1.getStyle()),//
							Integer.parseInt(borrow1.getTimeLimit()),//
							borrow1.getDivides(), //
							borrow1.getAccount(), //
							new BigDecimal(borrow1.getApr())//
							);

					// 每一期的本金
					BigDecimal[] issueCapital = new BigDecimal[repaymentInfo.getOrderNum()];
					// 每一期的利息
					BigDecimal[] issueInterest = new BigDecimal[repaymentInfo.getOrderNum()];
					// 奖金总额
					BigDecimal rewardAll = new BigDecimal(0);

					// 将标分期
					Integer[] borPayIds = new Integer[repaymentInfo.getOrderNum()];
					BorrowRepaymentDetail[] borPayInfos = new BorrowRepaymentDetail[repaymentInfo.getOrderNum()];
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

						borRepayDetail.setRepaymentTime(repaymentInfo.getRepaymentDetailList().get(i).getRepaymentDate());
						borRepayDetail.setRepaymentDateInt(repaymentInfo.getRepaymentDetailList().get(i).getRepaymentDateInt());
						borRepayDetail.setInterest(issueInterest[i]);// 应还利息
						borRepayDetail.setCapital(BigDecimal.ZERO);// 应还本金
						borRepayDetail.setBorrow(borrow1);
						borRepayDetail.setRepaymentAccount(borRepayDetail.getInterest());
						borRepayDetail.setRepaymentYesaccount(BigDecimal.ZERO);
						borRepayDetail.setReminderFee(BigDecimal.ZERO);// 奖励
						borRepayDetail.setRechargeStatus(0);
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
							userRepaymentDetail.setRepaymentAccount(retView.getRepaymentDetailList().get(i).getInterest());
							userRepaymentDetail.setInterest(retView.getRepaymentDetailList().get(i).getInterest());
							userRepaymentDetail.setWaitInterest(retView.getRepaymentDetailList().get(i).getInterest());
							userRepaymentDetail.setWaitAccount(BigDecimal.ZERO);
							userRepaymentDetail.setAddPersion(String.valueOf(adminId));
							userRepaymentDetail.setOperatorIp(ServletActionContext.getRequest().getRemoteAddr());
							userRepaymentDetail.setBorrowPeriods(i);
							userRepaymentDetail.setUser(borrowDetail.getUser());
							userRepaymentDetail.setBorrow(borrowDetail.getBorrow());
							userRepaymentDetail.setBorrowRepaymentDetail(borPayInfos[i]);
							userRepaymentDetail.setRepaymentDate(borPayInfos[i].getRepaymentTime());
							userRepaymentDetail.setBorrowDivides(borrow1.getDivides());

							// 利息管理费
							// Listing listing_t = listingDao.load(1473);
							BigDecimal userserviceCharge = SettingUtil.setPriceScale(BigDecimal.valueOf((userRepaymentDetail.getInterest().doubleValue())
									* Double.parseDouble(listing_t.getKeyValue())));
							userRepaymentDetail.setServiceCharge(userserviceCharge);
							userRepaymentDetail.setProfit(userRepaymentDetail.getInterest().subtract(userRepaymentDetail.getServiceCharge()));
							userRepaymentDetail.setBorrowDetail(borrowDetail);

							userRepaymentDetailDao.save(userRepaymentDetail);

							// 所有投资人本期还款本金统计
							issueCapital[i] = issueCapital[i].add(userRepaymentDetail.getWaitAccount());
							// 所有投资人本期还款利息统计
							issueInterest[i] = issueInterest[i].add(userRepaymentDetail.getWaitInterest());
							// 投资人待收本金
							waitCapital = waitCapital.add(BigDecimal.ZERO);
							// 投资人待收利息
							waitInterest = waitInterest.add(userRepaymentDetail.getWaitInterest());
						}

						// ◆◆◆投资人资金修改&&日志 开始◆◆◆
						// 获取投标人账户资金
						UserAccount userAccount1 = userAccountDao.loadLockTable(borrowDetail.getUser());// 锁表
						//红包进入总额
						userAccount1.setTotal(userAccount1.getTotal().add(new BigDecimal(borrowDetail.getHongbaoAmount())));
						// 投资人待收利息 增加
						userAccount1.setInvestorCollectionInterest(userAccount1.getInvestorCollectionInterest().add(waitInterest));


						userAccountDao.update(userAccount1);// 【完成修改投资人账户】

//						// 记入账户日志
//						UserAccountDetail userAccountDetail1 = InterestCalUtil.saveAccountDetail("invest", // 类型:投资
//								BigDecimal.valueOf(Double.valueOf(borrowDetail.getTasteAmount())), // 变动金额
//								"体验标投标成功[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]", //
//								userAccount1.getUser(), userAccount1, borrow1.getUser().getId(), //
//								String.valueOf(adminId), //
//								ServletActionContext.getRequest().getRemoteAddr());
//						userAccountDetailDao.save(userAccountDetail1);
						// ◆◆◆投资人资金修改&&日志 结束◆◆◆

						
					}

					// 总的借款利息
					BigDecimal borrowAllInterest = new BigDecimal(0);

					// 借款人每期还款情况
					for (int i = 0; i < repaymentInfo.getOrderNum(); i++) {

						borRepayDetail = borPayInfos[i];
						borRepayDetail.setInterest(issueInterest[i]);
						borRepayDetail.setCapital(issueCapital[i]);
						if ("2".equals(borrow1.getAward()) && (i == repaymentInfo.getOrderNum() - 1)) {
							borRepayDetail.setReminderFee(rewardAll);
						}
						borRepayDetail.setRepaymentAccount(borRepayDetail.getInterest());
						
						
						borRepayDetail.setRechargeMoney(borRepayDetail.getInterest());
						
						if(i==(repaymentInfo.getOrderNum()-1)) {
							borRepayDetail.setRechargeMoney(borRepayDetail.getRechargeMoney());
						}
						
						borrowRepaymentDetailDao.update(borRepayDetail);

						borrowAllInterest = borrowAllInterest.add(issueInterest[i]);
					}

					borrow1.setRepaymentAccount(borrowAllInterest);// 重新统计，计算应付金额（本金+利息）

					borrow1.setInterestTotal(borrowAllInterest);// 标的总利息
					borrow1.setRepayCapital(BigDecimal.ZERO);// 未还本金
					borrow1.setRepayInterest(borrowAllInterest);// 未还利息
					borrow1.setPayedCapital(BigDecimal.ZERO);// 已还本金
					borrow1.setPayedInterest(BigDecimal.ZERO);// 已还利息
					
					borrow1.setBorrowMoney(borrow1.getAccount());//项目余额
					borrow1.setFangkuanStatus(0);
					borrow1.setNextRepayDate(borPayInfos[0].getRepaymentTime());//次回还款日
					
					// ▽▽▽增加借款人账户资金 开始▽▽▽
					// 取得借款人账户信息
					UserAccount userAccount = userAccountDao.load(borrow1.getUser().getAccount().getId());
					userAccount = userAccountDao.loadLock(userAccount.getId());// 锁表

					// 可用余额 = 原可用金额 + 本次的待还本金
					userAccount.setAbleMoney(userAccount.getAbleMoney());
					// 待还本金
					userAccount.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
					// 待还利息
					userAccount.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest().add(borrowAllInterest));

					userAccountDao.update(userAccount);// 修改资金表数据

//					UserAccountDetail userAccountDetail1 = InterestCalUtil.saveAccountDetail("borrow_success", // 借款成功
//							BigDecimal.ZERO, // 借款的金额
//							"对体验标[" + CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName()) + "]借款成功", // 备注
//							userAccount.getUser(), userAccount, borrow1.getUser().getId(), String.valueOf(adminId), // 操作者
//							ServletActionContext.getRequest().getRemoteAddr());//
//					userAccountDetailDao.save(userAccountDetail1);
					// △△△增加借款人账户资金 结束△△△
					
				} else if (borrow.getStatus() == 4) {// 满标审核不通过

					// 审核不成功 回滚投资人投资金额
					for (BorrowDetail borrowDetail : borrowDetailSet) {
						UserAccount userAccount = userAccountDao.load(borrowDetail.getUser().getId());// 获取投标人账户资金
						userAccount = userAccountDao.loadLock(userAccount.getId());

						
					// 回滚体验金
					if(borrowDetail.getTasteAmount()!=null&&!"".equals(borrowDetail.getTasteAmount()) && Double.valueOf(borrowDetail.getTasteAmount())>0) {
						
							BigDecimal backMoney = BigDecimal.valueOf(Double.valueOf(borrowDetail.getTasteAmount()));
							userAccount.setTasteMoney(userAccount.getTasteMoney().add(backMoney));
							userAccountDao.update(userAccount);// 【修改投资人账户】
							UserAccountDetail userAccountDetail = InterestCalUtil.saveAccountDetail("invest_false",//
									backMoney,//
									"招标["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]失败返回的体验金额",
									userAccount.getUser(),//
									userAccount,//
									0,//
									admin.getName(),//
									admin.getLoginIp());
							userAccountDetailDao.save(userAccountDetail);
						}
						borrowDetail.setBackStatus(1);
						borrowDetailDao.update(borrowDetail);
				}

				}
		return 0;
	}
	

	@Resource(name = "userAnalysDaoImpl")
	private UserAnalysDao userAnalysDao;

	@Resource(name = "iosIdfaDaoImpl")
	private IosIdfaDao iosIdfaDao;
	
	//修改IOS渠道信息
	public void updateIOSIdfa(List<User> list){
		for(User user :list){
			UserAnalys analys = userAnalysDao.getUserAnalysByUserId(user.getId());
			if(analys != null){
				IosIdfa ios = iosIdfaDao.getIosIdfa(analys.getIdfa(), analys.getMac());
				if(ios != null && StringUtils.isNotEmpty(ios.getCallback()) && StringUtils.isNotEmpty(ios.getSource()) && !"02951618".equals(ios.getSource())){
					ios.setStatus(1);
					iosIdfaDao.update(ios);
					//回调方法
					try {
						HttpUtil.sendGet(URLDecoder.decode(ios.getCallback(), "utf-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
