package net.qmdboss.service.impl;

import net.qmdboss.dao.*;
import net.qmdboss.entity.*;
import net.qmdboss.service.BorrowSecondService;
import net.qmdboss.util.CommonUtil;
import net.qmdboss.util.ConstantUtil;
import net.qmdboss.util.InterestCalUtil;
import net.qmdboss.util.SettingUtil;
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
 * 秒标Service实现类
 */
@Service("borrowSecondServiceImpl")
public class BorrowSecondServiceImpl extends BaseServiceImpl<Borrow, Integer>
		implements BorrowSecondService {

	Logger log = Logger.getLogger(BorrowSecondServiceImpl.class);

	@Resource(name = "borrowDaoImpl")
	private BorrowDao borrowDao;

	// @Resource(name = "listingDaoImpl")
	// private ListingDao listingDao;

	@Resource(name = "userAccountDaoImpl")
	private UserAccountDao userAccountDao;

	@Resource(name = "userAccountDetailDaoImpl")
	private UserAccountDetailDao userAccountDetailDao;

	@Resource(name = "borrowRepaymentDetailDaoImpl")
	private BorrowRepaymentDetailDao borrowRepaymentDetailDao;

	@Resource(name = "userRepaymentDetailDaoImpl")
	private UserRepaymentDetailDao userRepaymentDetailDao;
	
	@Resource(name = "listingDaoImpl")
	private ListingDao listingDao;

	@Resource(name = "borrowDaoImpl")
	public void setBaseDao(BorrowDao borrowDao) {
		super.setBaseDao(borrowDao);
	}

	InterestCalUtil interestCalUtil;

	/**
	 * 满标审核通过(秒标)
	 */
	@Override
	public synchronized int updateBorrowSecondFull(Borrow borrow, Integer id, Admin admin) {
		// 取得标的信息
		Borrow borrow1 = borrowDao.loadLock(id);

		// 判断标的状态
		if (borrow1.getStatus() != 5) {
			return 1;
		}

		// 取得操作者信息（管理员）
		Integer adminId = admin.getId();

		// 标的投资者信息
		Set<BorrowDetail> borrowDetailSet = borrow1.getBorrowDetailSet();

		interestCalUtil = new InterestCalUtil();

		if (borrow.getStatus() == 3) { // 满标审核

			// -------------------------------------------------------------------
			// 借款人账户校验 【开始】

			double aprDay = borrow1.getApr().doubleValue() / 1000;

			// 本期的本金
			BigDecimal issueCapital = new BigDecimal(0);
			// 本期的利息
			BigDecimal issueInterest = new BigDecimal(0);
			// 本期总金额
			BigDecimal issueTotal = new BigDecimal(0);
			// 手续费
			BigDecimal charges = new BigDecimal(0);

			BigDecimal[] userInterest = new BigDecimal[borrowDetailSet.size()];

			// 投资人，每期的投资收益明细
			List<UserRepaymentDetail> userRepaymentDetailList = new ArrayList<UserRepaymentDetail>();
			UserRepaymentDetail userRepaymentDetail;
			int ct = 0;
			for (BorrowDetail borrowDetail : borrowDetailSet) {
				userRepaymentDetail = new UserRepaymentDetail();

				userRepaymentDetail.setStatus("1");
				userRepaymentDetail.setAccount(borrowDetail.getAccount());
				userRepaymentDetail.setInterest(SettingUtil
						.setPriceScale(BigDecimal.valueOf(aprDay
								* borrowDetail.getAccount().doubleValue())));
				userRepaymentDetail.setRepaymentAccount(userRepaymentDetail
						.getAccount().add(userRepaymentDetail.getInterest()));

				userRepaymentDetail.setWaitInterest(userRepaymentDetail
						.getInterest());
				userRepaymentDetail.setWaitAccount(userRepaymentDetail
						.getAccount());
				userRepaymentDetail.setAddPersion(String.valueOf(adminId));
				userRepaymentDetail.setOperatorIp(ServletActionContext
						.getRequest().getRemoteAddr());
				userRepaymentDetail.setBorrowPeriods(1);
				userRepaymentDetail.setUser(borrowDetail.getUser());
				userRepaymentDetail.setBorrow(borrowDetail.getBorrow());
				
				// 利息管理费
				BigDecimal userserviceCharge = SettingUtil.setPriceScale(BigDecimal
						.valueOf((userRepaymentDetail.getInterest().doubleValue()) * ConstantUtil.INTEREST_CHARGES));
				userRepaymentDetail.setServiceCharge(userserviceCharge);
				userRepaymentDetail.setProfit(userRepaymentDetail.getInterest().subtract(userRepaymentDetail.getServiceCharge()));

				issueCapital = issueCapital.add(userRepaymentDetail
						.getAccount());
				issueInterest = issueInterest.add(userRepaymentDetail
						.getInterest());

				userInterest[ct] = userRepaymentDetail.getInterest();
				ct++;

				userRepaymentDetailList.add(userRepaymentDetail);
				
				userRepaymentDetail.setBorrowDetail(borrowDetail);
			}
			// 本期总金额 = 本期的本金 + 本期的利息
			issueTotal = issueCapital.add(issueInterest);

			// 取得借款人账户信息
//			UserAccount userAccount = userAccountDao.load(borrow1.getUser()
//					.getAccount().getId());
//			
//			userAccount = userAccountDao.loadLock(userAccount.getId());
			UserAccount userAccount = userAccountDao.loadLockTable(borrow1.getUser());// 锁表

			// 判断还款账户是否有足够的可用金额
			if (issueInterest.compareTo(userAccount.getAbleMoney()) > 0) {
				log.info("还款利息大于借款人可用余额，满标审核失败！");
				return 2;
			}

			// --------------------------------------------------------
			// 添加标的还款信息，添加投资人的还款信息 【开始】

			// 添加标的还款信息
			BorrowRepaymentDetail borRepayDetail = new BorrowRepaymentDetail();
			borRepayDetail.setStatus(1);
			borRepayDetail.setOrderNum(1);
			borRepayDetail.setRepaymentTime(new Date());
			borRepayDetail.setRepaymentAccount(issueTotal);
			borRepayDetail.setInterest(issueInterest);
			borRepayDetail.setCapital(issueCapital);
			borRepayDetail.setBorrow(borrow1);
			borrowRepaymentDetailDao.save(borRepayDetail);

			// 添加投资人还款信息
			for (UserRepaymentDetail userRepaymentInfo : userRepaymentDetailList) {
				// 设置关联的标的还款信息
				userRepaymentInfo.setBorrowRepaymentDetail(borRepayDetail);

				userRepaymentInfo.setRepaymentYesaccount(userRepaymentInfo
						.getAccount());
				userRepaymentInfo.setRepaymentYesinterest(userRepaymentInfo
						.getInterest());
				userRepaymentInfo.setRepaymentDate(borRepayDetail.getRepaymentTime());
				
				userRepaymentDetailDao.save(userRepaymentInfo);
			}

			// -----------------------------------------------------------
			// 扣除投资人的投资金额，增加借款人的借款金额 【开始】

			// 1，【投资】扣除投资人的冻结金额，增加待收本金，待收利息
			// 2，【还款-本金】增加投资人的可用金额，减少待收金额
			// 3，【还款-利息】增加投资人的可用金额，增加总金额
			// 4，【还款-手续费】减少投资人的可用金额，减少总金额
			ct = 0;
			for (BorrowDetail borrowDetail : borrowDetailSet) {

				// 1，【投资】扣除投资人的冻结金额，增加待收本金，待收利息
				// 获取投标人账户资金
				UserAccount userAccount1 = userAccountDao.load(borrowDetail
						.getUser().getAccount().getId());
				
				userAccount1 = userAccountDao.loadLock(userAccount1.getId());

				// 冻结金额 = 冻结金额-投资金额
				userAccount1.setUnableMoney(userAccount1.getUnableMoney()
						.subtract(borrowDetail.getAccount()));

				// 投资人待收本金 增加
				userAccount1.setInvestorCollectionCapital(userAccount1
						.getInvestorCollectionCapital().add(
								borrowDetail.getAccount()));
				// 投资人待收利息 增加
				BigDecimal waitInterest = BigDecimal.valueOf(aprDay
						* borrowDetail.getAccount().doubleValue());
				userAccount1.setInvestorCollectionInterest(userAccount1
						.getInvestorCollectionInterest().add(waitInterest));

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
								userAccount1.getUser(), userAccount1
										.getInvestorCollectionCapital(),
								userAccount1.getInvestorCollectionInterest(),
								userAccount1.getBorrowerCollectionCapital(),
								userAccount1.getBorrowerCollectionInterest(),
								userAccount1.getContinueTotal());
				userAccountDetailDao.save(userAccountDetail1);

				// ◆◆◆【还款-本金】2，增加投资人的可用金额，减少待收金额
				// 可用金额= 可用金额+还款本金
				userAccount1.setAbleMoney(userAccount1.getAbleMoney().add(
						borrowDetail.getAccount()));
				// 待收本金 = 待收本金-还款本金
				userAccount1.setInvestorCollectionCapital(userAccount1
						.getInvestorCollectionCapital().subtract(
								borrowDetail.getAccount()));
				userAccountDao.update(userAccount1);
				// 记入账户日志
				UserAccountDetail userAccountDetail2 = interestCalUtil
						.saveAccountDetail(
								"invest_repayment", // 类型:投资
								userAccount1.getTotal(),// 账户总金额
								borrowDetail.getAccount(), // 变动金额
								userAccount1.getAbleMoney(), // 账户可用金额
								userAccount1.getUnableMoney(),// 账户冻结金额
								borrow1.getUser().getAccount().getCollection(),//
								borrow1.getUser().getId(), //
								String.valueOf(adminId), //
								"客户对["+CommonUtil.fillBorrowUrl(borrow1.getId(),borrow1.getName()) + "]借款的还款(本金)", //
								ServletActionContext.getRequest()
										.getRemoteAddr(), //
								userAccount1.getUser(), userAccount1
										.getInvestorCollectionCapital(),
								userAccount1.getInvestorCollectionInterest(),
								userAccount1.getBorrowerCollectionCapital(),
								userAccount1.getBorrowerCollectionInterest(),
								userAccount1.getContinueTotal());
				userAccountDetailDao.save(userAccountDetail2);

				// ◆◆◆3，【还款-利息】增加投资人的可用金额，增加总金额，减少待收利息
				userAccount1.setAbleMoney(userAccount1.getAbleMoney().add(
						userInterest[ct]));
				userAccount1.setTotal(userAccount1.getTotal().add(
						userInterest[ct]));
				userAccount1.setInvestorCollectionInterest(userAccount1
						.getInvestorCollectionInterest().subtract(
								userInterest[ct]));
				// 记入账户日志
				UserAccountDetail userAccountDetail3 = interestCalUtil
						.saveAccountDetail(
								"interest_repayment", // 类型:投资
								userAccount1.getTotal(),// 账户总金额
								userInterest[ct], // 变动金额
								userAccount1.getAbleMoney(), // 账户可用金额
								userAccount1.getUnableMoney(),// 账户冻结金额
								borrow1.getUser().getAccount().getCollection(),//
								borrow1.getUser().getId(), //
								String.valueOf(adminId), //
								"客户对["+CommonUtil.fillBorrowUrl(borrow1.getId(),borrow1.getName())
									+ "]借款的还款(利息)", //
								ServletActionContext.getRequest()
										.getRemoteAddr(), //
								userAccount1.getUser(), userAccount1
										.getInvestorCollectionCapital(),
								userAccount1.getInvestorCollectionInterest(),
								userAccount1.getBorrowerCollectionCapital(),
								userAccount1.getBorrowerCollectionInterest(),
								userAccount1.getContinueTotal());
				userAccountDetailDao.save(userAccountDetail3);

				// ◆◆◆4，【还款-手续费】减少投资人的可用金额，减少总金额
				Listing listing = listingDao.load(1473);//获取利息管理费值
				BigDecimal mangeMoney = SettingUtil.setPriceScale(BigDecimal
						.valueOf(userInterest[ct].doubleValue() * Double.parseDouble(listing.getKeyValue())));

				charges = charges.add(mangeMoney);// 增加利息管理费用

				userAccount1.setAbleMoney(userAccount1.getAbleMoney().subtract(
						mangeMoney));
				userAccount1.setTotal(userAccount1.getTotal().subtract(
						mangeMoney));
				// 记入账户日志
				UserAccountDetail userAccountDetail4 = interestCalUtil
						.saveAccountDetail(
								"tender_mange", // 类型:利息管理费用
								userAccount1.getTotal(),// 账户总金额
								mangeMoney, // 变动金额
								userAccount1.getAbleMoney(), // 账户可用金额
								userAccount1.getUnableMoney(),// 账户冻结金额
								borrow1.getUser().getAccount().getCollection(),//
								ConstantUtil.ADMIN_USER_ID, // 利息管理费用：系统管理员接收
								String.valueOf(adminId), //
								"用户成功还款扣除利息的管理费["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]", //
								ServletActionContext.getRequest()
										.getRemoteAddr(), //
								userAccount1.getUser(), //
								userAccount1.getInvestorCollectionCapital(),//
								userAccount1.getInvestorCollectionInterest(),//
								userAccount1.getBorrowerCollectionCapital(),//
								userAccount1.getBorrowerCollectionInterest(),
								userAccount1.getContinueTotal());
				userAccountDetailDao.save(userAccountDetail4);

				ct++;
			}

			// 【借款人账户】
			// 取得借款人账户信息
			UserAccount userAccount2 = userAccountDao.load(borrow1.getUser()
					.getAccount().getId());
			
			userAccount2 = userAccountDao.loadLock(userAccount2.getId());

			// 【收款】
			// 可用余额 = 原可用金额 + 本次的待还本金
			userAccount2.setAbleMoney(userAccount2.getAbleMoney().add(
					issueCapital));
			// 待还本金
			userAccount2.setBorrowerCollectionCapital(userAccount2
					.getBorrowerCollectionCapital().add(issueCapital));
			// 待还利息
			userAccount2.setBorrowerCollectionInterest(userAccount2
					.getBorrowerCollectionInterest().add(issueInterest));

			userAccountDao.update(userAccount2);// 修改资金表数据

			UserAccountDetail userAccountDetail5 = interestCalUtil
					.saveAccountDetail(
							"borrow_success", // 借款成功
							userAccount2.getTotal(), // 借款人账户总金额
							issueCapital, // 借款的金额
							userAccount2.getAbleMoney(), // 可用金额
							userAccount2.getUnableMoney(), // 冻结金额
							userAccount2.getCollection(), // 待收金额（不用）
							borrow1.getUser().getId(), // 对于用户
							String.valueOf(adminId), // 操作者
							"对标["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]借款成功", // 备注
							ServletActionContext.getRequest().getRemoteAddr(),// IP
							userAccount2.getUser(),
							userAccount2.getInvestorCollectionCapital(),
							userAccount2.getInvestorCollectionInterest(),
							userAccount2.getBorrowerCollectionCapital(),
							userAccount2.getBorrowerCollectionInterest(),
							userAccount2.getContinueTotal());//
			userAccountDetailDao.save(userAccountDetail5);

			// 【还款-本金&利息】减少总金额， 减少可用金额，减少待付金额，减少待付利息
			userAccount2.setTotal(userAccount2.getTotal().subtract(
					issueInterest));
			userAccount2.setAbleMoney(userAccount2.getAbleMoney().subtract(
					issueTotal));
			userAccount2.setBorrowerCollectionCapital(userAccount2
					.getBorrowerCollectionCapital().subtract(issueCapital));
			userAccount2.setBorrowerCollectionInterest(userAccount2
					.getBorrowerCollectionInterest().subtract(issueInterest));
			UserAccountDetail userAccountDetail6 = interestCalUtil
					.saveAccountDetail(
							"repayment", // 还款本金&利息
							userAccount2.getTotal(), // 借款人账户总金额
							issueTotal, // 还款本金&利息
							userAccount2.getAbleMoney(), // 可用金额
							userAccount2.getUnableMoney(), // 冻结金额
							userAccount2.getCollection(), // 待收金额（不用）
							borrow1.getUser().getId(), // 对于用户
							String.valueOf(adminId), // 操作者
							"客户对[" + CommonUtil.fillBorrowUrl(borrow1.getId(),borrow1.getName())
								+ "]借款的还款(总额)", // 备注
							ServletActionContext.getRequest().getRemoteAddr(),// IP
							userAccount2.getUser(),
							userAccount2.getInvestorCollectionCapital(),
							userAccount2.getInvestorCollectionInterest(),
							userAccount2.getBorrowerCollectionCapital(),
							userAccount2.getBorrowerCollectionInterest(),
							userAccount2.getContinueTotal());//
			userAccountDetailDao.save(userAccountDetail6);

			// 【管理费】管理员账户 增加总额，可用金额
/* ---管理员账户不存记录
			UserAccount userAccount3 = userAccountDao
					.load(ConstantUtil.ADMIN_USER_ACCOUNT_ID);

			userAccount3.setTotal(userAccount3.getTotal().add(charges));
			userAccount3.setAbleMoney(userAccount3.getAbleMoney().add(charges));

			UserAccountDetail userAccountDetail7 = interestCalUtil
					.saveAccountDetail(
							"tender_mange_sys", // 还款本金&利息
							userAccount3.getTotal(), // 借款人账户总金额
							charges, // 还款本金&利息
							userAccount3.getAbleMoney(), // 可用金额
							userAccount3.getUnableMoney(), // 冻结金额
							userAccount3.getCollection(), // 待收金额（不用）
							ConstantUtil.ADMIN_USER_ID, // 对于用户
							String.valueOf(adminId), // 操作者
							"对标["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]还款利息的管理费", // 备注
							ServletActionContext.getRequest().getRemoteAddr(),// IP
							userAccount3.getUser(),
							userAccount3.getInvestorCollectionCapital(),
							userAccount3.getInvestorCollectionInterest(),
							userAccount3.getBorrowerCollectionCapital(),
							userAccount3.getBorrowerCollectionInterest());//
			userAccountDetailDao.save(userAccountDetail7);
*/
			// △△△增加借款人账户资金 结束△△△

			// 修改标的信息
			borrow1.setStatus(7);// 秒标，审核后，直接完成
			borrow1.setRepamyentRemark(borrow.getVerifyRemark());
			borrow1.setRepaymentUser(adminId);
			borrow1.setSuccessTime(new Date());
			borrow1.setRepaymentYesaccount(issueTotal);
			borrow1.setUpdatePersion(String.valueOf(adminId));
			borrow1.setOperatorIp(ServletActionContext.getRequest()
					.getRemoteAddr());
			// 修改标的信息
			borrowDao.update(borrow1);

		}
		return 0;
	}
}
