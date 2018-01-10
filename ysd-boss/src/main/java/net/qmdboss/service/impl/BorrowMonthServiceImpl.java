package net.qmdboss.service.impl;

import net.qmdboss.dao.*;
import net.qmdboss.entity.*;
import net.qmdboss.service.BorrowMonthService;
import net.qmdboss.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * 发表待审Service实现类
 * 
 * @author Administrator
 * 
 */
@Service("borrowMonthServiceImpl")
public class BorrowMonthServiceImpl extends BaseServiceImpl<Borrow, Integer>
		implements BorrowMonthService {

	Logger log = Logger.getLogger(BorrowMonthServiceImpl.class);

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

	@Resource(name = "borrowDaoImpl")
	public void setBaseDao(BorrowDao borrowDao) {
		super.setBaseDao(borrowDao);
	}

	InterestCalUtil interestCalUtil;


	@Override
	public synchronized int updateBorrowMonthFull(Borrow borrow, Integer id, Admin admin) {
		// TODO Auto-generated method stub
		// 取得标的信息
		Borrow borrow1 = borrowDao.loadLock(id);

		// 判断标的状态
		if (borrow1.getStatus() != 5) {
			return 1;
		}
		
		if(borrow1.getContinueAwardRate()==null) {
			borrow1.setContinueAwardRate(BigDecimal.ZERO);
		}

		// 取得操作者信息（管理员）
		Integer adminId = admin.getId();

		// 标的投资者信息
		Set<BorrowDetail> borrowDetailSet = borrow1.getBorrowDetailSet();

		interestCalUtil = new InterestCalUtil();

		if (borrow.getStatus() == 3) { // 满标审核
			
			//※※※※※积分功能 开始※※※※※
			Listing listingPointAble = listingDao.getListingBySign("user_points_able");
			String userPointsAble = "0";
			double userPointsRateTender = 0;
			if (listingPointAble!=null && !StringUtils.isNotEmpty(listingPointAble.getKeyValue()) &&"1".equals(listingPointAble.getKeyValue())) {
				userPointsAble = listingPointAble.getKeyValue();
				
				Listing listingPointsRateTender = listingDao.getListingBySign("user_points_rate_tender");
				if (listingPointsRateTender!=null && !StringUtils.isNotEmpty(listingPointsRateTender.getKeyValue())) {
					userPointsRateTender = Double.valueOf(listingPointsRateTender.getKeyValue());
				}
			}
			//※※※※※积分功能 结束※※※※※
			
			// 修改标的信息
			borrow1.setStatus(borrow.getStatus());
			borrow1.setRepamyentRemark(borrow.getVerifyRemark());
			borrow1.setRepaymentUser(adminId);
			borrow1.setSuccessTime(new Date());
			// borrow1.setEachTime("每月"+new Date().getDay());

			// 计算标的每一期的收益
			PaymentView retBorrow = interestCalUtil.payback(borrow1
					.getAccount().doubleValue(), borrow1.getAccount()
					.doubleValue(), borrow1.getApr(), Integer
					.valueOf(borrow1.getTimeLimit()), borrow1.getBorStages(),1);

			// 每一期的本金
			BigDecimal[] issueCapital = new BigDecimal[retBorrow
					.getPaymentDetail().length];
			// 每一期的利息
			BigDecimal[] issueInterest = new BigDecimal[retBorrow
					.getPaymentDetail().length];
			// 奖金总额
			BigDecimal rewardAll = new BigDecimal(0);
			
			// 将标分期
			Integer[] borPayIds = new Integer[retBorrow.getPaymentDetail().length];
			BorrowRepaymentDetail[] borPayInfos = new BorrowRepaymentDetail[retBorrow.getPaymentDetail().length];
			BorrowRepaymentDetail borRepayDetail = null;
			
			for (int i = retBorrow.getPaymentDetail().length; i > 0; i--) {
				if (issueInterest[i-1]==null) {
					issueInterest[i-1] = new BigDecimal(0);
				}
				if (issueCapital[i-1]==null) {
					issueCapital[i-1] = new BigDecimal(0);
				}

				borRepayDetail = new BorrowRepaymentDetail();
				borRepayDetail.setStatus(0);
				borRepayDetail.setOrderNum(i);
				//System.out.println(retBorrow.getPaymentDetail()[i-1].getDateNum());
				Date time = null;
				SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try {
					time = simple.parse(retBorrow.getPaymentDetail()[i-1].getDateNum());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				borRepayDetail.setRepaymentTime(time);
				borRepayDetail.setInterest(issueInterest[i-1]);// 应还本金
				borRepayDetail.setCapital(issueCapital[i-1]);//应还利息
				borRepayDetail.setBorrow(borrow1);
				borRepayDetail.setRepaymentAccount(borRepayDetail.getCapital().add(borRepayDetail.getInterest()));
				borRepayDetail.setRepaymentYesaccount(new BigDecimal(0));
				borRepayDetail.setReminderFee(new BigDecimal(0));// 奖励
				borrowRepaymentDetailDao.save(borRepayDetail);
				
				borPayIds[i-1] = borRepayDetail.getId();
				borPayInfos[i-1] = borRepayDetail;

			}
			

			// 添加投资人收益明细
			// 投资人的借款金额扣除 同时增加待还本金，待还利息
			for (BorrowDetail borrowDetail : borrowDetailSet) {
				// 计算投资人每一期的收益
				PaymentView retView = interestCalUtil.payback(borrowDetail
						.getAccount().doubleValue(), borrow1.getAccount()
						.doubleValue(), borrow1.getApr(), Integer
						.valueOf(borrow1.getTimeLimit()), borrow1
						.getBorStages(),1);
				// 投资人待收本金
				BigDecimal waitCapital = new BigDecimal(0);
				// 投资人待收利息
				BigDecimal waitInterest = new BigDecimal(0);

				// 投资人，每期的投资收益明细
				UserRepaymentDetail userRepaymentDetail;
				for (int i = retView.getPaymentDetail().length; i > 0; i--) {
					MonthPaymentDetail monthPayment = retView
							.getPaymentDetail()[i-1];
					userRepaymentDetail = new UserRepaymentDetail();
					userRepaymentDetail.setStatus("0");
					userRepaymentDetail.setAccount(borrowDetail.getAccount());
					userRepaymentDetail.setRepaymentAccount(BigDecimal
							.valueOf(Double.parseDouble(monthPayment
									.getMonthPaymentAmount())));
					userRepaymentDetail.setInterest(BigDecimal.valueOf(Double
							.parseDouble(monthPayment.getLixiM())));
					userRepaymentDetail
							.setWaitInterest(BigDecimal.valueOf(Double
									.parseDouble(monthPayment.getLixiM())));
					userRepaymentDetail.setWaitAccount(BigDecimal
							.valueOf(Double.parseDouble(monthPayment
									.getBenjinM())));
					userRepaymentDetail.setAddPersion(String.valueOf(adminId));
					userRepaymentDetail.setOperatorIp(ServletActionContext
							.getRequest().getRemoteAddr());
					userRepaymentDetail.setBorrowPeriods(i);
					userRepaymentDetail.setUser(borrowDetail.getUser());
					userRepaymentDetail.setBorrow(borrowDetail.getBorrow());
					userRepaymentDetail.setBorrowRepaymentDetail(borPayInfos[i-1]);
					userRepaymentDetail.setRepaymentDate(borPayInfos[i-1].getRepaymentTime());
					
					// 利息管理费
					Listing listing = listingDao.load(1473);
					BigDecimal userserviceCharge = SettingUtil.setPriceScale(BigDecimal
							.valueOf((userRepaymentDetail.getInterest().doubleValue()) * Double.parseDouble(listing.getKeyValue())));
					userRepaymentDetail.setServiceCharge(userserviceCharge);
					userRepaymentDetail.setProfit(userRepaymentDetail.getInterest().subtract(userRepaymentDetail.getServiceCharge()));
					userRepaymentDetail.setBorrowDetail(borrowDetail);
					
					userRepaymentDetailDao.save(userRepaymentDetail);

					// 所有投资人本期还款本金统计
					issueCapital[i-1] = issueCapital[i-1].add(userRepaymentDetail.getWaitAccount());
					// 所有投资人本期还款利息统计
					issueInterest[i-1] = issueInterest[i-1].add(userRepaymentDetail
							.getWaitInterest());
					// 投资人待收本金
					waitCapital = waitCapital.add(userRepaymentDetail.getWaitAccount());
					// 投资人待收利息
					waitInterest = waitInterest.add(userRepaymentDetail.getWaitInterest());
				}

				// ◆◆◆投资人资金修改&&日志 开始◆◆◆
				// 获取投标人账户资金
//				UserAccount userAccount1 = userAccountDao.load(borrowDetail
//						.getUser().getAccount().getId());
//				
//				userAccount1 = userAccountDao.loadLock(userAccount1.getId());//锁表
				
				UserAccount userAccount1 = userAccountDao.loadLockTable(borrowDetail.getUser());// 锁表

				// 冻结金额 = 冻结金额-投资金额
				userAccount1.setUnableMoney(userAccount1.getUnableMoney()
						.subtract(borrowDetail.getAccount()));

				// 投资人待收本金 增加
				userAccount1.setInvestorCollectionCapital(userAccount1
						.getInvestorCollectionCapital().add(waitCapital));
				// 投资人待收利息 增加
				userAccount1.setInvestorCollectionInterest(userAccount1
						.getInvestorCollectionInterest().add(waitInterest));
				
				//※※※ 积分发放记录 开始※※※※※※※※※
				boolean pointsFlg = false;
				int userPoints = 0;
				if("1".equals(userPointsAble)) {
					userPoints = (int)(userPointsRateTender*waitCapital.doubleValue()*Integer.parseInt(borrow1.getTimeLimit())*30);//月标以30天计算
					userAccount1.setUserPoints((userAccount1.getUserPoints()==null?0:userAccount1.getUserPoints()) + userPoints);
					pointsFlg = true;
				}
				//※※※ 积分发放记录 结束※※※※※※※※※

				userAccountDao.update(userAccount1);// 【完成修改投资人账户】

				// 记入账户日志
				UserAccountDetail userAccountDetail1 = interestCalUtil
						.saveAccountDetail(
								"invest", // 类型:投资
								userAccount1.getTotal(),// 账户总金额
								borrowDetail.getAccount(), // 变动金额
								userAccount1.getAbleMoney(), // 账户可用金额
								userAccount1.getUnableMoney(),// 账户冻结金额
								borrow1.getUser().getAccount().getCollection(),//
								borrow1.getUser().getId(), //
								String.valueOf(adminId), //
								"投标成功费用扣除["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]", //
								ServletActionContext.getRequest()
										.getRemoteAddr(), //
								userAccount1.getUser(),
								userAccount1.getInvestorCollectionCapital(),
								userAccount1.getInvestorCollectionInterest(),
								userAccount1.getBorrowerCollectionCapital(),
								userAccount1.getBorrowerCollectionInterest(),
								userAccount1.getContinueTotal());
				userAccountDetailDao.save(userAccountDetail1);
				// ◆◆◆投资人资金修改&&日志 结束◆◆◆
				
				//※※※ 积分发放记录 开始※※※※※※※※※

				//※※※ 积分发放记录 结束※※※※※※※※※

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
					UserAccountDetail userAccountDetail4 = interestCalUtil
							.saveAccountDetail(
									"award_add", // 奖励
									userAccount1.getTotal(),
									reward,
									userAccount1.getAbleMoney(),
									userAccount1.getUnableMoney(),
									borrow1.getUser().getAccount()
											.getCollection(),
									borrow1.getUser().getId(),
									String.valueOf(adminId),
									"借款["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]的奖励",
									ServletActionContext.getRequest()
											.getRemoteAddr(), userAccount1
											.getUser(),
									userAccount1.getInvestorCollectionCapital(),
									userAccount1.getInvestorCollectionInterest(),
									userAccount1.getBorrowerCollectionCapital(),
									userAccount1.getBorrowerCollectionInterest(),
									userAccount1.getContinueTotal());
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
				if(StringUtils.isNotEmpty(borrowDetail.getContinueAmount()) && Double.valueOf(borrowDetail.getContinueAmount()) > 0){
					if(borrowDetail.getContinueAmount()!=""){
						// 获取投标人账户资金
						UserAccount userAccount = userAccountDao.load(borrowDetail
								.getUser().getAccount().getId());
						
						userAccount = userAccountDao.loadLock(userAccount.getId());
						
//						//获取续投奖励参数  奖励金额=利率* 投标金额 * 月数
//						Listing listing = listingDao.load(ConstantUtil.DEDUCTION_AWARD_WANDER_ID);
//						
//						// 续投奖励 = 续投金额 * 每月奖励率 * 月数
//						int limitmonty = Integer.parseInt(borrow1.getTimeLimit());
//						BigDecimal getaward=BigDecimal.valueOf(Double.parseDouble(borrowDetail.getContinueAmount())*Double.parseDouble(listing.getKeyValue())*limitmonty);
						
						BigDecimal getaward = new BigDecimal(borrowDetail.getContinueAmount()).multiply(borrow1.getContinueAwardRate());
						
						userAccount.setTotal(userAccount1.getTotal().add(getaward));
						userAccount.setAbleMoney(SettingUtil
								.setPriceScale(userAccount1.getAbleMoney().add(
										getaward)));
						userAccountDao.update(userAccount);// 【完成修改投资人账户】
	
						// 奖金日志记录
						UserAccountDetail userAccountDetail4 = interestCalUtil
								.saveAccountDetail(
										"award_continued", // 奖励
										userAccount.getTotal(),
										getaward,
										userAccount.getAbleMoney(),
										userAccount.getUnableMoney(),
										borrow1.getUser().getAccount()
												.getCollection(),
										borrow1.getUser().getId(),
										String.valueOf(adminId),
										"对借款["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]的续投奖励",
										ServletActionContext.getRequest()
												.getRemoteAddr(), userAccount1
												.getUser(),
										userAccount.getInvestorCollectionCapital(),
										userAccount.getInvestorCollectionInterest(),
										userAccount.getBorrowerCollectionCapital(),
										userAccount.getBorrowerCollectionInterest(),
										userAccount.getContinueTotal());
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
			for (int i = retBorrow.getPaymentDetail().length; i > 0; i--) {

				borRepayDetail = borPayInfos[i-1];
				borRepayDetail.setInterest(issueInterest[i-1]);
				borRepayDetail.setCapital(issueCapital[i-1]);
				if ("2".equals(borrow1.getAward()) 
						&& i == retBorrow.getPaymentDetail().length) {
					borRepayDetail.setReminderFee(rewardAll);
				}
				borRepayDetail.setRepaymentAccount(borRepayDetail.getCapital().add(borRepayDetail.getInterest()).add(rewardAll));
				//borRepayDetail.setRepaymentAccount(repaymentAccount)
				borrowRepaymentDetailDao.update(borRepayDetail);

				borrowAllCapital = borrowAllCapital.add(issueCapital[i-1]);
				borrowAllInterest = borrowAllInterest.add(issueInterest[i-1]);
			}
			
			borrow1.setRepaymentAccount(borrowAllCapital.add(borrowAllInterest));//重新统计，计算应付金额（本金+利息）
			
			borrow1.setInterestTotal(borrowAllInterest);//标的总利息
			borrow1.setRepayCapital(borrowAllCapital);//未还本金
			borrow1.setRepayInterest(borrowAllInterest);//未还利息
			borrow1.setPayedCapital(new BigDecimal(0));//已还本金
			borrow1.setPayedInterest(new BigDecimal(0));//已还利息

			// ▽▽▽增加借款人账户资金 开始▽▽▽
			// 取得借款人账户信息
			UserAccount userAccount = userAccountDao.load(borrow1.getUser()
					.getAccount().getId());
			userAccount = userAccountDao.loadLock(userAccount.getId());//锁表

			// 可用余额 = 原可用金额 + 本次的待还本金
			userAccount.setAbleMoney(userAccount.getAbleMoney().add(
					borrowAllCapital));
			// 待还本金
			userAccount.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital().add(borrowAllCapital));
			// 待还利息
			userAccount.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest().add(borrowAllInterest));

			userAccountDao.update(userAccount);// 修改资金表数据

			UserAccountDetail userAccountDetail1 = interestCalUtil
					.saveAccountDetail(
							"borrow_success", // 借款成功
							userAccount.getTotal(), // 借款人账户总金额
							borrowAllCapital, // 借款的金额
							userAccount.getAbleMoney(), // 可用金额
							userAccount.getUnableMoney(), // 冻结金额
							userAccount.getCollection(), // 待收金额（不用）
							borrow1.getUser().getId(), // 对于用户
							String.valueOf(adminId), // 操作者
							"对标["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]借款成功", // 备注
							ServletActionContext.getRequest().getRemoteAddr(),// IP
							userAccount.getUser(),
							userAccount.getInvestorCollectionCapital(),
							userAccount.getInvestorCollectionInterest(),
							userAccount.getBorrowerCollectionCapital(),
							userAccount.getBorrowerCollectionInterest(),
							userAccount.getContinueTotal());//
			userAccountDetailDao.save(userAccountDetail1);
			// △△△增加借款人账户资金 结束△△△

			// ☆☆☆扣除借款手续费 开始☆☆☆
			Listing listing = listingDao.load(1469);// 借款手续费比例
			// 手续费 = 百分比 * 借款金额
			double deduction = Double.parseDouble(listing.getKeyValue())
					* (borrow1.getAccount().doubleValue());
			// 账户总金额，账户可用金额 ：扣去借款手续费
			userAccount.setAbleMoney(userAccount.getAbleMoney().subtract(
					BigDecimal.valueOf(deduction)));
			userAccount.setTotal(userAccount.getTotal().subtract(
					BigDecimal.valueOf(deduction)));
			userAccountDao.update(userAccount);

			UserAccountDetail userAccountDetail2 = interestCalUtil
					.saveAccountDetail("borrow_fee", // 借款手续费
							userAccount.getTotal(), // 账户总金额
							BigDecimal.valueOf(deduction),// 操作金额
							userAccount.getAbleMoney(), // 可用金额
							userAccount.getUnableMoney(),// 冻结金额
							userAccount.getCollection(), 0, // 对象ID
							String.valueOf(adminId),// 操作人
							"对标["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]借款手续费",// 备注
							ServletActionContext.getRequest().getRemoteAddr(),// IP
							borrow1.getUser(),
							userAccount.getInvestorCollectionCapital(),
							userAccount.getInvestorCollectionInterest(),
							userAccount.getBorrowerCollectionCapital(),
							userAccount.getBorrowerCollectionInterest(),
							userAccount.getContinueTotal());
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

				UserAccountDetail userAccountDetail3 = interestCalUtil
						.saveAccountDetail(
								"award_lower", //
								userAccount.getTotal(), //
								rewardAll, //
								userAccount.getAbleMoney(),//
								userAccount.getUnableMoney(),//
								userAccount.getCollection(),//
								0, //
								String.valueOf(adminId),//
								"扣除借款["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]的奖励",//
								ServletActionContext.getRequest()
										.getRemoteAddr(),//
								borrow1.getUser(),
								userAccount.getInvestorCollectionCapital(),
								userAccount.getInvestorCollectionInterest(),
								userAccount.getBorrowerCollectionCapital(),
								userAccount.getBorrowerCollectionInterest(),
								userAccount.getContinueTotal());//
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
				UserAccountDetail userAccountDetail1 = interestCalUtil
						.saveAccountDetail(
								"invest_false", //
								userAccount.getTotal(), //
								borrowDetail.getAccount(), //
								userAccount.getAbleMoney(), //
								userAccount.getUnableMoney(),//
								userAccount.getCollection(), //
								userAccount.getUser().getId(),//
								String.valueOf(adminId), //
								"招标["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]失败返回的投标额", //
								ServletActionContext.getRequest()
										.getRemoteAddr(),//
								userAccount.getUser(),
								userAccount.getInvestorCollectionCapital(),
								userAccount.getInvestorCollectionInterest(),
								userAccount.getBorrowerCollectionCapital(),
								userAccount.getBorrowerCollectionInterest(),
								userAccount.getContinueTotal());//
				userAccountDetailDao.save(userAccountDetail1);
			}

			// 修改标的信息
			borrowDao.update(borrow1);
		}
		return 0;
	}
	
}

