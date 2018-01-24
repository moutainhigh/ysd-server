package net.qmdboss.service.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.*;
import net.qmdboss.entity.*;
import net.qmdboss.service.BorrowService;
import net.qmdboss.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 发表待审Service实现类
 * 
 * @author Administrator
 * 
 */
@Service("borrowServiceImpl")
public class BorrowServiceImpl extends BaseServiceImpl<Borrow, Integer>
		implements BorrowService {
	Logger log = Logger.getLogger(BorrowServiceImpl.class);
	
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
	@Resource(name = "userHongbaoDaoImpl")
	private UserHongbaoDao userHongbaoDao;
	@Resource(name = "userAwardDetailDaoImpl")
	private UserAwardDetailDao userAwardDetailDao;

	InterestCalUtil interestCalUtil;

	@Resource(name = "borrowDaoImpl")
	public void setBaseDao(BorrowDao borrowDao) {
		super.setBaseDao(borrowDao);
	}

	@Override
	public boolean isExistByUsername(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Borrow getBorrowByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pager getBorrowPage(Borrow borrow,  Pager pager) {
		// TODO Auto-generated method stub
		return borrowDao.getBorrowPager(borrow, pager);
	}

	private Criteria getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Borrow borrow) {
		borrowDao.update(borrow);
	}

	
	/**
	 * 初审
	 * @param borrow
	 * @param id
	 * @param admin
	 * @return 0 审核成功,1状态不对
	 */
	public int updateBorrowPreliminary(Borrow borrow,Integer id, Admin admin) {
		
		
		
		Borrow borrow1 = borrowDao.loadLock(id);
		
		if (borrow1.getStatus() != 0) {
			return 1;//标的状态不对
		}
		
		borrow1.setStatus(borrow.getStatus());
		borrow1.setVerifyRemark(borrow.getVerifyRemark());
		String admin1 = String .valueOf(admin.getId());
		borrow1.setVerifyUser(admin1);
		borrow1.setAccountYes(borrow1.getAccount());
		borrow1.setBalance(String.valueOf(borrow1.getAccount().doubleValue()));
		borrow1.setVerifyTime(new Date());
		borrow1.setSchedule("0");
		if("4".equals(borrow1.getType())){
			borrow1.setFinalRepayDate(CommonUtil.getMonthAfter(borrow1.getVerifyTime(),Integer.parseInt(borrow1.getTimeLimit())));
			borrow1.setContinueAwardRate(borrow.getContinueAwardRate()==null?BigDecimal.ZERO:borrow.getContinueAwardRate());
		}else{
			borrow1.setFinalRepayDate(CommonUtil.getDateAfter(borrow1.getVerifyTime(),Integer.parseInt(borrow1.getTimeLimit())));
			borrow1.setContinueAwardRate(BigDecimal.ZERO);
		}
		
		// 月标
		if("4".equals(borrow1.getType())){
			borrow1.setFinalRateYear(CalculationUtil.monthRealAprYear(borrow1)); //计算收益率
			borrow1.setAutoTenderStatus(borrow.getAutoTenderStatus());
			
			// 判断自动投标
			if (borrow.getAutoTenderStatus() !=null && borrow.getAutoTenderStatus() ==1) {
				rankLog.debug("===自动投标开始===["+borrow1.getId()+"] 还款方式["+borrow1.getAutoTenderRepayWay()+"]");
				// 1 自动投标总额
				Listing autoListing=listingDao.getListingBySign("auto_tender_account_rate");
				BigDecimal autoBalance = borrow1.getAccount().multiply(new BigDecimal(autoListing.getKeyValue()));
				BigDecimal autoMoney = BigDecimal.ZERO;
				
				// 2 获取符合条件的投资人
				List<User> userList = new ArrayList<User>();
				BigDecimal reward = BigDecimal.ZERO;
				if ("1".equals(borrow1.getAward())) {
					reward = new BigDecimal(borrow1.getFunds());
				}
				//List<Object> list = userDao.getViewTest(Integer.parseInt(borrow1.getTimeLimit()), borrow1.getFinalRateYear(),reward);
				List<Object> list = userDao.getViewTest(Integer.parseInt(borrow1.getTimeLimit()), BigDecimal.valueOf(borrow1.getApr()),reward);
				StringBuffer sblog = null;
				int no = 0;
				rankLog.debug("---初选开始");
				for(Object obj:list) {
					Integer uid =(Integer)obj;
					User uu = userDao.loadLock(uid);
					sblog = new StringBuffer();
					no++;
					sblog.append("   [").append(no).append("][").append(uid).append("][").append(uu.getUsername())
						.append("]排名[").append(CommonUtil.getDate2String(uu.getAutoTenderDate(),"yyyy-MM-dd hh:mm:ss"))
						.append("]规则[").append(uu.getAutoTenderRule()).append("]最高[").append(uu.getAutoTenderMoneyTop()).append("]余留[").append(uu.getAutoTenderMoneyLeave())
						.append("]利率[").append(uu.getAutoTenderRateBegin()).append("][").append(uu.getAutoTenderRateEnd())
						.append("]奖励[").append(uu.getAutoTenderRewardBegin()).append("][").append(uu.getAutoTenderRateEnd())
						.append("]期限[").append(uu.getAutoTenderLimitBegin()).append("][").append(uu.getAutoTenderLimitEnd())
						.append("]方式[").append(uu.getAutoTenderRepayWay())
						.append("]修改[").append(CommonUtil.getDate2String(uu.getAutoTenderModifyDate(),"yyyy-MM-dd hh:mm:ss"));
						
					
					if (borrow1.getAutoTenderRepayWay() ==null || uu.getAutoTenderRepayWay() ==null) {
						sblog.append("[OUT_1][").append(uu.getAutoTenderRepayWay()).append("]");
						rankLog.debug(sblog.toString());
						continue;
					}
					String[] way = uu.getAutoTenderRepayWay().split(",");
					if (way==null|| way.length!=3) {
						sblog.append("[OUT_2][").append(uu.getAutoTenderRepayWay()).append("][").append(way.length).append("]");
						rankLog.debug(sblog.toString());
						continue;
					}
					
					if(borrow1.getAutoTenderRepayWay()==1 && !"1".equals(way[0])) {
						sblog.append("[OUT_3][").append(borrow1.getAutoTenderRepayWay()).append("][").append(way[0]).append("]");
						rankLog.debug(sblog.toString());
						continue;
					} else if(borrow1.getAutoTenderRepayWay()==2 && !"1".equals(way[1])) {
						sblog.append("[OUT_4][").append(borrow1.getAutoTenderRepayWay()).append("][").append(way[1]).append("]");
						rankLog.debug(sblog.toString());
						continue;
					} else if(borrow1.getAutoTenderRepayWay()==3 && !"1".equals(way[2])) {
						sblog.append("[OUT_5][").append(borrow1.getAutoTenderRepayWay()).append("][").append(way[2]).append("]");
						rankLog.debug(sblog.toString());
						continue;
					}
					sblog.append("[INN]");
					rankLog.debug(sblog.toString());
					
					userList.add(uu);
				}
				rankLog.debug("---初选结束 原["+no+"] 现["+userList.size()+"]");
				
				Integer lowestAccount = null;
				if (StringUtils.isEmpty(borrow1.getLowestAccount()) ||"0".equals(borrow1.getLowestAccount())) {
					lowestAccount = 100;//最低100元
				} else {
					lowestAccount = Double.valueOf(borrow1.getLowestAccount()).intValue();
				}
				BigDecimal mostAccount = null;
				if (StringUtils.isEmpty(borrow1.getMostAccount()) ||"0".equals(borrow1.getMostAccount())) {
					mostAccount = autoBalance;//没有限制，就标的自投额度限制
				} else {
					mostAccount = BigDecimal.valueOf(Double.valueOf(borrow1.getMostAccount()));
				}
				
				interestCalUtil = new InterestCalUtil();
				rankLog.debug("---复选开始");
				no = 0;
				for(User u:userList) {
					UserAccount ua = userAccountDao.loadLockTable(u);// 锁表

					sblog = new StringBuffer();
					no++;
					sblog.append("   [").append(no).append("][").append(CommonUtil.getDate2String(u.getAutoTenderDate(),"yyyy-MM-dd hh:mm:ss"))
						.append("][").append(u.getId()).append("][").append(u.getUsername())
						.append("]续投[").append(ua.getContinueTotal()).append("]可用[").append(ua.getAbleMoney());
					
					int money = (int)(ua.getAbleMoney().doubleValue()/100)*100 + (int)(ua.getContinueTotal().doubleValue()/100)*100;
					if (money < lowestAccount) {//有效投资额少于最小投资额，跳过
						sblog.append("[OUT_6][").append(money).append("][").append(lowestAccount).append("][有效投资少于最小投资额]");
						rankLog.debug(sblog.toString());
						continue;
					}
				
					//如果设置了投标金额限制，myMoney需要重新计算
					if (u.getAutoTenderRule()!=null && u.getAutoTenderRule()==1) {
						BigDecimal aa = ua.getAbleMoney().subtract(u.getAutoTenderMoneyLeave());
						if (aa.compareTo(BigDecimal.ZERO)!=-1) {//aa >= 0  那么只扣除可用中的金额
							money = (int)(aa.doubleValue()/100)*100 + (int)(ua.getContinueTotal().doubleValue()/100)*100;
						} else {
							money =  (int)(( ua.getContinueTotal().add(aa)).doubleValue()/100)*100;
						}
						if (BigDecimal.valueOf(money).compareTo(u.getAutoTenderMoneyTop())==1) {
							money = (int)(u.getAutoTenderMoneyTop().doubleValue()/100)*100;
						}
					}
					
					// 判断投资金额
					BigDecimal myMoney = mostAccount;
					BigDecimal tenderMoney = new BigDecimal(money);
					
					// 标的最大投资限额，标的自投总额，用户拥有的金额 取最小值
					if (mostAccount.compareTo(autoBalance) ==1) { 
						myMoney = autoBalance;
					}
					if (myMoney.compareTo(tenderMoney)==1) {
						myMoney = tenderMoney;
					}
					
					if (myMoney.compareTo(BigDecimal.ZERO)!=1) {// myMoney <=0  少于等于0 不投资
						sblog.append("[OUT_7][").append(myMoney).append("][有效投资少于0]");
						rankLog.debug(sblog.toString());
						continue;
					}
					
					BorrowDetail borrowDetail = new BorrowDetail();
					borrowDetail.setAccount(myMoney);
					borrowDetail.setMoney(myMoney);
					if(myMoney.compareTo(BigDecimal.valueOf((int)(ua.getContinueTotal().doubleValue()/100)*100))==1) {
						borrowDetail.setContinueAmount(String.valueOf((int)(ua.getContinueTotal().doubleValue()/100)*100));
						borrowDetail.setAbleAmount((myMoney.subtract(BigDecimal.valueOf((int)(ua.getContinueTotal().doubleValue()/100)*100))).toString());
					} else {
						borrowDetail.setContinueAmount(myMoney.toString());
						borrowDetail.setAbleAmount("0");
					}
					borrowDetail.setStatus("1");
					
					PaymentView ret = interestCalUtil.payback(//
							myMoney.doubleValue(), //
							borrow1.getAccount().doubleValue(),//
							borrow1.getApr() , //
							Integer.valueOf(borrow1.getTimeLimit()),//
							borrow1.getBorStages(),//
							1);
					
					borrowDetail.setInterest(new BigDecimal(ret.getTotalLiXi()));
					borrowDetail.setRepaymentAccount(borrowDetail.getAccount().add(borrowDetail.getInterest()));
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
					
					u.setAutoTenderTimes((u.getAutoTenderTimes()==null?0:u.getAutoTenderTimes())+1);
					u.setAutoTenderDate(new Date());
					u.setAutoTenderModifyDate(new Date());
					userDao.update(u);
					
					autoBalance = autoBalance.subtract(myMoney);
					autoMoney = autoMoney.add(myMoney);
					
					// 修改用户资金记录
					if (Double.valueOf(borrowDetail.getContinueAmount()) > 0) {
						//修改用户续投余额
						ua.setContinueTotal(BigDecimal.valueOf(ua.getContinueTotal().doubleValue() - Double.parseDouble(borrowDetail.getContinueAmount())));
						//修改用户冻结金额
						ua.setUnableMoney(BigDecimal.valueOf(ua.getUnableMoney().doubleValue() + Double.parseDouble(borrowDetail.getContinueAmount())));
						this.userAccountDao.update(ua);
						
						//资金记录
						UserAccountDetail userAccountDetail = InterestCalUtil.saveAccountDetail(//
								"tender_continued",//
								BigDecimal.valueOf(Double.valueOf(borrowDetail.getContinueAmount())),//
								"续投冻结资金["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]", //
								u,
								ua,
								0,
								admin.getName(),
								admin.getLoginIp());
						this.userAccountDetailDao.save(userAccountDetail);
					}
					// 可用资金修改及记录
					if (Double.valueOf(borrowDetail.getAbleAmount()) > 0) {
						//修改用户可用金额
						ua.setAbleMoney(BigDecimal.valueOf(ua.getAbleMoney().doubleValue() - Double.parseDouble(borrowDetail.getAbleAmount())));
						//修改用户冻结金额
						ua.setUnableMoney(BigDecimal.valueOf(ua.getUnableMoney().doubleValue() + Double.parseDouble(borrowDetail.getAbleAmount())));
						this.userAccountDao.update(ua);
						
						//资金记录
						UserAccountDetail userAccountDetail = InterestCalUtil.saveAccountDetail(//
								"tender",//
								BigDecimal.valueOf(Double.valueOf(borrowDetail.getAbleAmount())),//
								"投标冻结资金["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]", //
								u,//
								ua,//
								0,//
								admin.getName(),//
								admin.getLoginIp());
						this.userAccountDetailDao.save(userAccountDetail);
					}
					
					sblog.append("[INN]续投[").append(borrowDetail.getContinueAmount()).append("]可用[").append(borrowDetail.getAbleAmount()).append("]时间[").append(CommonUtil.getDate2String(u.getAutoTenderDate(),"yyyy-MM-dd hh:mm:ss")).append("]");
					rankLog.debug(sblog.toString());
					
					if(autoBalance.compareTo(BigDecimal.ZERO) != 1) {
						break;
					}
					
				}
				rankLog.debug("---复选结束 投中["+no+"]人，自投金额["+autoMoney+"]自投余额["+autoBalance+"]");
				
				BigDecimal balance = new BigDecimal(borrow1.getBalance()).subtract(autoMoney);
				
				borrow1.setBalance(balance.toString());
				BigDecimal schedule = NumberUtil.setPriceScale(BigDecimal.ONE.subtract(balance.divide(borrow1.getAccount()))).multiply(BigDecimal.valueOf(100));
				borrow1.setSchedule(schedule.toString());
				
				rankLog.debug("===自动投标结束===["+borrow1.getId()+"]");
			}
		}
		
		borrowDao.update(borrow1);
		
		return 0;
		
	}

	@Override
	public void updateBorrow(Borrow borrow, Integer id, Admin admin) {
		Borrow borrow1 = borrowDao.load(id);
		Integer adminId = admin.getId();
		SettingUtil settingUtil = new SettingUtil();
		UserAccount userAccount;
		Integer limit;
		Double deduction;
		Double bond;
		Set<BorrowDetail> borrowDetailSet = borrow1.getBorrowDetailSet();
		interestCalUtil = new InterestCalUtil();
		PaymentView ret = interestCalUtil.payback(borrow1.getAccount()
				.doubleValue(), borrow1.getAccount().doubleValue(), borrow1
				.getApr() , Integer.valueOf(borrow1.getTimeLimit()),
				borrow.getBorStages(),2);
		BorrowRepaymentDetail borRepayDetail;
		UserRepaymentDetail userRepaymentDetail;
		borrow1.setStatus(borrow.getStatus());
		borrow1.setRepamyentRemark(borrow.getVerifyRemark());
		borrow1.setRepaymentUser(adminId);
		if (borrow.getStatus() == 3) {
			borrow1.setSuccessTime(new Date());
		}
		Double account = borrow1.getAccount().doubleValue();
		Listing listing = listingDao.load(1469);
		UserAccountDetail userAcDetail = new UserAccountDetail();
		UserAccountDetail userAcDetail1 = new UserAccountDetail();
		userAccount = userAccountDao.load(borrow1.getUser().getAccount()
				.getId());
		borrowDao.update(borrow1);
		log.info("开始计算保证金---目前没有扣保证金");
		bond = account * 0.1;
		BigDecimal bond1 = settingUtil.setPriceScale(BigDecimal.valueOf(bond));// 保证金
		if (borrow.getStatus() == 3) {
			// userAccount.setUnableMoney(settingUtil.add(userAccount.getUnableMoney(),bond1));
			limit = Integer.valueOf(borrow1.getTimeLimit());
			log.info("开始计算借款手续费");
			deduction = (Double.parseDouble(listing.getKeyValue()) / 100)
					* (borrow1.getAccount().doubleValue());
			// if(limit>2){
			// log.info("开始计算超过2个月借款手续费");
			// Listing listing1 = listingDao.load(1470);
			// deduction
			// +=(Double.parseDouble(listing1.getKeyValue())/100)*account*(limit-2);
			// }
			log.info("扣去投标人冻结金额");
			for (BorrowDetail borrowDetail : borrowDetailSet) {
				PaymentView retView = interestCalUtil.payback(borrowDetail
						.getAccount().doubleValue(), borrow1.getAccount()
						.doubleValue(), borrow1.getApr(), Integer
						.valueOf(borrow1.getTimeLimit()), borrow1
						.getBorStages(),2);
				UserAccount userAccount1 = userAccountDao.load(borrowDetail
						.getUser().getAccount().getId());// 获取投标人账户资金
				// userAccount1.setTotal(settingUtil.sub(userAccount1.getTotal(),borrowDetail.getAccount()));
				// userAccount1.setInvestorCollectionCapital(BigDecimal.valueOf(Double.parseDouble(retView.getTotalAmount())));
				// userAccount1.setInvestorCollectionInterest(BigDecimal.valueOf(Double.parseDouble(retView.getTotalLiXi())));
				userAccount1.setUnableMoney(settingUtil.sub(
						userAccount1.getUnableMoney(),
						borrowDetail.getAccount()));
				userAccountDao.update(userAccount1);// 【完成修改投资人账户】
				log.info("保存资金账户操作详细记录--扣去投标金额");
				UserAccountDetail userAccountDetail1 = interestCalUtil
						.saveAccountDetail("invest", userAccount1.getTotal(),
								borrowDetail.getAccount(), userAccount1
										.getAbleMoney(), userAccount1
										.getUnableMoney(), borrow1.getUser()
										.getAccount().getCollection(), borrow1
										.getUser().getId(), String
										.valueOf(adminId), "投标成功费用扣除["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]",
								ServletActionContext.getRequest()
										.getRemoteAddr(), userAccount1
										.getUser(), userAccount1
										.getInvestorCollectionCapital(),
								userAccount1.getInvestorCollectionInterest(),
								userAccount1.getBorrowerCollectionCapital(),
								userAccount1.getBorrowerCollectionInterest(),
								userAccount1.getContinueTotal());
				userAccountDetailDao.save(userAccountDetail1);
			}
			log.info("开始计算还款明细");
			Double lastInterest = 0.00;
			Double borrowInterest = 0.00;
			double reward1 = 0;// 奖励
			double app;// 固定奖励分摊比例
			// int num = Integer.parseInt(borrow1.getTimeLimit());
			for (int i = 0; i < ret.getPaymentDetail().length; i++) {
				CalculationUtil calculation = new CalculationUtil();
				// int j=num-i;
				MonthPaymentDetail monthPaymentDetail = ret.getPaymentDetail()[i];
				borRepayDetail = new BorrowRepaymentDetail();
				BigDecimal monInterest = BigDecimal.valueOf(Double
						.parseDouble(monthPaymentDetail.getLixiM()));
				BigDecimal monAccount = BigDecimal.valueOf(Double
						.parseDouble(monthPaymentDetail.getMonthAmount()));
				BigDecimal capital = BigDecimal.valueOf(Double
						.parseDouble(monthPaymentDetail.getBenjinM()));
				borRepayDetail.setStatus(0);
				borRepayDetail.setOrderNum(i);
				CommonUtil commonUtil = new CommonUtil();
				borRepayDetail.setRepaymentTime(commonUtil.getDateAfter(
						new Date(),
						Integer.parseInt(monthPaymentDetail.getDateNum())));
				borRepayDetail.setRepaymentAccount(borrow1
						.getMonthlyRepayment());
				borRepayDetail.setInterest(monInterest);
				borRepayDetail.setCapital(capital);
				borRepayDetail.setBorrow(borrow1);
				borrowRepaymentDetailDao.save(borRepayDetail);
				if (borrow1.getAward().equals("1")) {
					app = Double.parseDouble(borrow1.getFunds()) / 100;// 计算奖励的实际基数
				} else {
					app = 0;
				}
				log.info("开始插入投资人收益明细");
				for (BorrowDetail borrowDetail : borrowDetailSet) {
					PaymentView retView = interestCalUtil.payback(borrowDetail
							.getAccount().doubleValue(), borrow1.getAccount()
							.doubleValue(), borrow1.getApr(), Integer
							.valueOf(borrow1.getTimeLimit()), borrow1
							.getBorStages(),2);
					MonthPaymentDetail monthPayment = retView
							.getPaymentDetail()[i];
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
									.getMonthPaymentAmount())));
					userRepaymentDetail.setAddPersion(String.valueOf(adminId));
					userRepaymentDetail.setOperatorIp(ServletActionContext
							.getRequest().getRemoteAddr());
					userRepaymentDetail.setBorrowPeriods(i);
					userRepaymentDetail.setUser(borrowDetail.getUser());
					userRepaymentDetail.setBorrow(borrowDetail.getBorrow());
					userRepaymentDetail
							.setBorrowRepaymentDetail(borRepayDetail);
					userRepaymentDetail.setRepaymentDate(borRepayDetail.getRepaymentTime());
					userRepaymentDetailDao.save(userRepaymentDetail);
					reward1 += settingUtil.setPriceScale(
							BigDecimal.valueOf(app
									* borrowDetail.getAccount().doubleValue()))
							.doubleValue();
					System.out.println("===reward1奖励====>" + reward1);
					lastInterest += Double.parseDouble(monthPayment.getLixiM());
				}
				BorrowRepaymentDetail borRepayDetail1 = borrowRepaymentDetailDao
						.load(borRepayDetail.getId());

				borRepayDetail1.setInterest(BigDecimal.valueOf(lastInterest));
				borRepayDetail1.setRepaymentAccount(settingUtil.add(
						borRepayDetail1.getCapital(),
						BigDecimal.valueOf(lastInterest)));
				borrowRepaymentDetailDao.update(borRepayDetail1);
				borrowInterest += lastInterest;// 累加每位投资者利息成标总利息
			}

			log.info("还款明细计算结束");

			log.info("借款手续费计算结束！");
			// 增加借款人的资金
			BigDecimal total;
			BigDecimal ableMoney;
			userAccount.setBorrowerCollectionCapital(borrow1.getAccountYes());
			userAccount.setBorrowerCollectionInterest(BigDecimal
					.valueOf(borrowInterest));
			total = settingUtil.sub(settingUtil.add(
					settingUtil.add(userAccount.getAbleMoney(),
							userAccount.getUnableMoney()),
					userAccount.getInvestorCollectionCapital()), userAccount
					.getBorrowerCollectionCapital());
			ableMoney = settingUtil.add(userAccount.getAbleMoney(),
					borrow1.getAccountYes());
			userAccount.setTotal(total);
			userAccount.setAbleMoney(ableMoney);
			log.info("修改账户资金");
			userAccountDao.update(userAccount);// 修改资金表数据
			log.info("保存资金账户操作详细记录--借款入账");
			UserAccountDetail userAccountDetail1 = interestCalUtil
					.saveAccountDetail(
							"borrow_success",
							userAccount.getTotal(),
							BigDecimal.valueOf(account),
							userAccount.getAbleMoney(),
							userAccount.getUnableMoney(),
							userAccount.getCollection(),
							borrow1.getUser().getId(),
							String.valueOf(adminId),
							"对标["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]借款成功",
							ServletActionContext.getRequest().getRemoteAddr(),
							userAccount.getUser(), userAccount
									.getInvestorCollectionCapital(),
							userAccount.getInvestorCollectionInterest(),
							userAccount.getBorrowerCollectionCapital(),
							userAccount.getBorrowerCollectionInterest(),
							userAccount.getContinueTotal());
			userAccountDetailDao.save(userAccountDetail1);
			// 不为秒标时扣去借款手续费
			if (!borrow1.getType().equals("0")) {
				// 扣去借款手续费
				userAccount.setAbleMoney(settingUtil.sub(
						userAccount.getAbleMoney(),
						BigDecimal.valueOf(deduction)));
				userAccountDao.update(userAccount);
				log.info("保存资金账户操作详细记录--借款手续费");
				UserAccountDetail userAccountDetail2 = interestCalUtil
						.saveAccountDetail(
								"borrow_fee",
								userAccount.getTotal(),
								BigDecimal.valueOf(deduction),
								userAccount.getAbleMoney(),
								userAccount.getUnableMoney(),
								userAccount.getCollection(),
								0,
								String.valueOf(adminId),
								"对标["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]借款手续费",
								ServletActionContext.getRequest()
										.getRemoteAddr(), borrow1.getUser(),
								userAccount.getInvestorCollectionCapital(),
								userAccount.getInvestorCollectionInterest(),
								userAccount.getBorrowerCollectionCapital(),
								userAccount.getBorrowerCollectionInterest(),
								userAccount.getContinueTotal());
				userAccountDetailDao.save(userAccountDetail2);
			}
			// 投资人待收金额增加
			for (BorrowDetail borrowDetail : borrowDetailSet) {
				PaymentView retView = interestCalUtil.payback(borrowDetail
						.getAccount().doubleValue(), borrow1.getAccount()
						.doubleValue(), borrow1.getApr(), Integer
						.valueOf(borrow1.getTimeLimit()), borrow1
						.getBorStages(),2);
				UserAccount userAccount1 = userAccountDao.load(borrowDetail
						.getUser().getAccount().getId());// 获取投标人账户资金
				UserAccountDetail userAcDetail2 = new UserAccountDetail();
				// userAccount1.setTotal(settingUtil.sub(userAccount1.getTotal(),borrowDetail.getAccount()));
				userAccount1.setInvestorCollectionCapital(BigDecimal
						.valueOf(Double.parseDouble(retView.getTotalAmount())));
				userAccount1.setInvestorCollectionInterest(BigDecimal
						.valueOf(Double.parseDouble(retView.getTotalLiXi())));
				userAccountDao.update(userAccount1);// 【完成修改投资人账户】
				log.info("保存资金账户操作详细记录--增加投资人代收本金");
				UserAccountDetail userAccountDetail4 = interestCalUtil
						.saveAccountDetail(
								"investor_capital",
								userAccount1.getTotal(),
								borrowDetail.getAccount(),
								userAccount1.getAbleMoney(),
								userAccount1.getUnableMoney(),
								borrow1.getUser().getAccount().getCollection(),
								borrow1.getUser().getId(),
								String.valueOf(adminId),
								"增加对标["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]投资人的账户代收本金",
								ServletActionContext.getRequest()
										.getRemoteAddr(), userAccount1
										.getUser(), userAccount1
										.getInvestorCollectionCapital(),
								userAccount1.getInvestorCollectionInterest(),
								userAccount1.getBorrowerCollectionCapital(),
								userAccount1.getBorrowerCollectionInterest(),
								userAccount1.getContinueTotal());
				userAccountDetailDao.save(userAccountDetail4);
				log.info("保存资金账户操作详细记录--增加投资人待收利息");
				UserAccountDetail userAccountDetail5 = interestCalUtil
						.saveAccountDetail(
								"investor_interest",
								userAccount1.getTotal(),
								BigDecimal.valueOf(Double.parseDouble(retView
										.getTotalLiXi())),
								userAccount1.getAbleMoney(),
								userAccount1.getUnableMoney(),
								borrow1.getUser().getAccount().getCollection(),
								borrow1.getUser().getId(),
								String.valueOf(adminId),
								"增加对标["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]投资人的待收利息",
								ServletActionContext.getRequest()
										.getRemoteAddr(), userAccount1
										.getUser(), userAccount1
										.getInvestorCollectionCapital(),
								userAccount1.getInvestorCollectionInterest(),
								userAccount1.getBorrowerCollectionCapital(),
								userAccount1.getBorrowerCollectionInterest(),
								userAccount1.getContinueTotal());
				userAccountDetailDao.save(userAccountDetail5);
			}
			// 借款人发放奖励给投资人
			BigDecimal reward;
			if (reward1 > 0) {
				reward = settingUtil.setPriceScale(BigDecimal.valueOf(reward1));
				userAccount.setAbleMoney(settingUtil.setPriceScale(settingUtil
						.sub(userAccount.getAbleMoney(), reward)));
				userAccountDao.update(userAccount);// 修改资金表数据-借款人
				log.info("保存资金账户操作详细记录--发放奖励");
				UserAccountDetail userAccountDetail3 = interestCalUtil
						.saveAccountDetail(
								"award_lower",
								userAccount.getTotal(),
								reward,
								userAccount.getAbleMoney(),
								userAccount.getUnableMoney(),
								userAccount.getCollection(),
								0,
								String.valueOf(adminId),
								"扣除借款["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]的奖励",
								ServletActionContext.getRequest()
										.getRemoteAddr(), borrow1.getUser(),
								userAccount.getInvestorCollectionCapital(),
								userAccount.getInvestorCollectionInterest(),
								userAccount.getBorrowerCollectionCapital(),
								userAccount.getBorrowerCollectionInterest(),
								userAccount.getContinueTotal());
				userAccountDetailDao.save(userAccountDetail3);
				// 投资人增加奖励资金
				for (BorrowDetail borrowDetail : borrowDetailSet) {
					UserAccount userAccount1 = userAccountDao.load(borrowDetail
							.getUser().getAccount().getId());// 获取投标人账户资金
					if (borrow1.getAward().equals(1)) {
						reward = settingUtil.setPriceScale(settingUtil.mul(
								BigDecimal.valueOf(Double.parseDouble(borrow1
										.getFunds()) / 100), borrowDetail
										.getAccount()));
					}
					userAccount1.setAbleMoney(settingUtil.add(
							userAccount1.getAbleMoney(), reward));
					userAccountDao.update(userAccount1);// 【完成修改投资人账户】
					log.info("保存资金账户操作详细记录--增加奖励资金");
					UserAccountDetail userAccountDetail4 = interestCalUtil
							.saveAccountDetail(
									"award_add",
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
											.getRemoteAddr(),
									userAccount1.getUser(),
									userAccount1.getInvestorCollectionCapital(),
									userAccount1
											.getInvestorCollectionInterest(),
									userAccount1.getBorrowerCollectionCapital(),
									userAccount1
											.getBorrowerCollectionInterest(),
									userAccount1.getContinueTotal());
					userAccountDetailDao.save(userAccountDetail4);
				}
			}
			// type=0为秒标。开始自动还款
			if (borrow1.getType().equals("0")) {
				BigDecimal interest;
				BigDecimal mangeMoney;
				BigDecimal number = BigDecimal.valueOf(0);
				System.out.println(borrow1.getBorrRepayDetailSet().size());
				Set<BorrowRepaymentDetail> borrRepayDetailSet = borrow1
						.getBorrRepayDetailSet();
				// 查询标还款记录表
				for (BorrowRepaymentDetail borRepayDetail1 : borrRepayDetailSet) {
					System.out.println(borRepayDetail1
							.getUserRepaymentDetailSet().size());
					Set<UserRepaymentDetail> userRepaymentDetailSet = borRepayDetail1
							.getUserRepaymentDetailSet();
					borRepayDetail1.setStatus(1);
					borRepayDetail1.setRepaymentYesaccount(borRepayDetail1
							.getRepaymentAccount());
					borRepayDetail1.setRepaymentYestime(new Date());
					// 查询借款人账户
					UserAccount userAccount2 = userAccountDao.load(borrow1
							.getUser().getAccount().getId());
					System.out.println(userAccount2.getAbleMoney());
					System.out.println(borRepayDetail1.getRepaymentAccount());
					System.out.println(settingUtil.sub(
							userAccount2.getAbleMoney(),
							borRepayDetail1.getRepaymentAccount()));
					userAccount2.setAbleMoney(settingUtil.sub(
							userAccount2.getAbleMoney(),
							borRepayDetail1.getRepaymentAccount()));
					// userAccount2.setCollection(settingUtil.sub(userAccount2.getCollection(),bond1));
					userAccount2.setTotal(settingUtil.sub(
							userAccount2.getTotal(),
							borRepayDetail1.getInterest()));
					userAccount2.setBorrowerCollectionCapital(settingUtil.sub(
							userAccount2.getBorrowerCollectionCapital(),
							borRepayDetail1.getCapital()));
					userAccount2.setBorrowerCollectionInterest(settingUtil.sub(
							userAccount2.getBorrowerCollectionInterest(),
							borRepayDetail1.getInterest()));
					userAccountDao.update(userAccount2);// 【修改借款人账户】
					log.info("保存资金账户操作详细记录--还款");
					UserAccountDetail userAccountDetail2 = interestCalUtil
							.saveAccountDetail(
									"repayment",
									userAccount2.getTotal(),
									borRepayDetail1.getRepaymentYesaccount(),
									userAccount2.getAbleMoney(),
									userAccount2.getUnableMoney(),
									borrow1.getUser().getAccount()
											.getCollection(),
									0,
									String.valueOf(adminId),
									"对标["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]还款",
									ServletActionContext.getRequest()
											.getRemoteAddr(),
									userAccount2.getUser(),
									userAccount2.getInvestorCollectionCapital(),
									userAccount2
											.getInvestorCollectionInterest(),
									userAccount2.getBorrowerCollectionCapital(),
									userAccount2
											.getBorrowerCollectionInterest(),
									userAccount2.getContinueTotal());
					userAccountDetailDao.save(userAccountDetail2);

					// 修改BORROW表中的已还金额
					borrow1.setRepaymentYesaccount(borRepayDetail1
							.getRepaymentYesaccount());
					borrow1.setUpdatePersion(String.valueOf(adminId));
					borrow1.setOperatorIp(ServletActionContext.getRequest()
							.getRemoteAddr());
					borrow1.setStatus(7); // 修改标状态为成功
					borrowDao.update(borrow1); // 标修改标

					// 修改标还款记录表使为还款状态
					borrowRepaymentDetailDao.update(borRepayDetail1);
					for (UserRepaymentDetail userRepayDetail : userRepaymentDetailSet) {
						userRepayDetail.setStatus("1");
						userRepayDetail.setRepaymentYesaccount(userRepayDetail
								.getRepaymentAccount());
						userRepayDetail.setRepaymentYesinterest(userRepayDetail
								.getWaitInterest());
						userRepayDetail.setOperatorIp(ServletActionContext
								.getRequest().getRemoteAddr());
						userRepaymentDetailDao.update(userRepayDetail);
					}

					for (BorrowDetail borrowDetail : borrowDetailSet) {
						userAccount = userAccountDao.load(borrowDetail
								.getUser().getAccount().getId());// 获取投标人账户资金
						CalculationUtil cal = new CalculationUtil();
						borrowDetail.setStatus("1");
						// interest=cal.getInterest(borrowDetail.getAccount(),borrow1.getApr(),borrow1.getTimeLimit());
						borrowDetail.setRepaymentYesaccount(borrowDetail
								.getRepaymentAccount());
						// borrowDetail.setInterest(settingUtil.setPriceScale(interest));
						borrowDetail.setWaitAccount(number);
						borrowDetail.setWaitInterest(number);
						borrowDetail.setRepaymentYesinterest(borrowDetail
								.getInterest());
						borrowDetail.setOperatorIp(ServletActionContext
								.getRequest().getRemoteAddr());
						// Listing Inlisting = listingService.load(1473);
						// String keyValue =
						// listingDao.findChildListingByname("deduction",
						// "利息管理费");
						Listing listing1 = listingDao.load(1473);
						// 扣去利息管理费
						mangeMoney = settingUtil.mul(
								borrowDetail.getInterest(), BigDecimal
										.valueOf(Double.valueOf(listing1
												.getKeyValue())));
						BigDecimal totalMoney = settingUtil
								.setPriceScale(settingUtil.sub(
										borrowDetail.getRepaymentAccount(),
										mangeMoney));
						userAccount.setTotal(settingUtil.add(
								userAccount.getTotal(), totalMoney));
						userAccount.setAbleMoney(settingUtil.add(
								userAccount.getAbleMoney(), totalMoney));
						userAccount
								.setInvestorCollectionCapital(settingUtil.sub(
										userAccount
												.getInvestorCollectionCapital(),
										borrowDetail.getAccount()));
						userAccount.setInvestorCollectionInterest(settingUtil
								.sub(userAccount
										.getInvestorCollectionInterest(),
										borrowDetail.getInterest()));

						log.info("保存资金账户操作详细记录--秒标扣除利息管理费");

						UserAccountDetail userAccountDetail4 = interestCalUtil
								.saveAccountDetail(
										"tender_mange",
										userAccount.getTotal(),
										mangeMoney,
										userAccount.getAbleMoney(),
										userAccount.getUnableMoney(),
										borrow1.getUser().getAccount()
												.getCollection(),
										0,
										String.valueOf(adminId),
										"投标["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]成功扣除利息管理费",
										ServletActionContext.getRequest()
												.getRemoteAddr(),
										userAccount.getUser(),
										userAccount
												.getInvestorCollectionCapital(),
										userAccount
												.getInvestorCollectionInterest(),
										userAccount
												.getBorrowerCollectionCapital(),
										userAccount
												.getBorrowerCollectionInterest(),
										userAccount.getContinueTotal());
						userAccountDetailDao.save(userAccountDetail4);

						borrowDetailDao.update(borrowDetail);// 【修改投标列表内值】
						userAccountDao.update(userAccount);// 【秒标完成修改投资人账户】
						UserAccountDetail userAccountDetail5 = interestCalUtil
								.saveAccountDetail(
										"invest_repayment",
										userAccount.getTotal(),
										totalMoney,
										userAccount.getAbleMoney(),
										userAccount.getUnableMoney(),
										borrow1.getUser().getAccount()
												.getCollection(),
										0,
										String.valueOf(adminId),
										"客户对[" +CommonUtil.fillBorrowUrl(borrow1.getId(),borrow1.getName())
												+ "]借款的还款"+borrow1.getId(),
										ServletActionContext.getRequest()
												.getRemoteAddr(),
										userAccount.getUser(),
										userAccount
												.getInvestorCollectionCapital(),
										userAccount
												.getInvestorCollectionInterest(),
										userAccount
												.getBorrowerCollectionCapital(),
										userAccount
												.getBorrowerCollectionInterest(),
										userAccount.getContinueTotal());
						userAccountDetailDao.save(userAccountDetail5);

					}
				}
				// log.info("保存资金账户操作详细记录--解冻借款担保金");
				// UserAccount userAccount3
				// =userAccountService.load(borrow1.getUser().getId());
				// UserAccountDetail userAcDetail4 = new UserAccountDetail();
				// userAcDetail4.setType("borrow_frost");
				// userAcDetail4.setMoney(bond1);
				// userAcDetail4.setNoUseMoney(userAccount3.getUnableMoney());
				// userAcDetail4.setUseMoney(userAccount3.getAbleMoney());
				// userAcDetail4.setCollection(userAccount3.getCollection());
				// userAcDetail4.setToUser(0);
				// userAcDetail4.setTotal(userAccount2.getTotal());
				// userAcDetail4.setRemark("对标[<a href='......html' target=_blank>]["+borrow1.getName()+"</a>]借款的解冻");
				// userAcDetail4.setUser(userAccount3.getUser());
				// userAccountDetailService.save(userAcDetail4 );
			} else {
			}

		} else if (borrow.getStatus() == 4) {
			// 审核不成功退回
			log.info("回滚投资人投资金额");
			for (BorrowDetail borrowDetail : borrowDetailSet) {
				userAccount = userAccountDao.load(borrowDetail.getUser()
						.getId());// 获取投标人账户资金
				userAccount
						.setUnableMoney(settingUtil.sub(
								userAccount.getUnableMoney(),
								borrowDetail.getAccount()));
				userAccount.setAbleMoney(settingUtil.add(
						userAccount.getAbleMoney(), borrowDetail.getAccount()));
				userAccountDao.update(userAccount);// 【秒标完成修改投资人账户】
				log.info("保存资金账户操作详细记录--扣去投标金额");
				UserAccountDetail userAccountDetail1 = interestCalUtil
						.saveAccountDetail("invest_false", userAccount
								.getTotal(), borrowDetail.getAccount(),
								userAccount.getAbleMoney(), userAccount
										.getUnableMoney(), userAccount
										.getCollection(), userAccount.getUser()
										.getId(), String.valueOf(adminId),
								"招标["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]失败返回的投标额",
								ServletActionContext.getRequest()
										.getRemoteAddr(),
								userAccount.getUser(), userAccount
										.getInvestorCollectionCapital(),
								userAccount.getInvestorCollectionInterest(),
								userAccount.getBorrowerCollectionCapital(),
								userAccount.getBorrowerCollectionInterest(),
								userAccount.getContinueTotal());
				userAccountDetailDao.save(userAccountDetail1);
				// userAcDetail2.setType("invest_false");
				// userAcDetail2.setMoney(borrowDetail.getAccount());
				// userAcDetail2.setNoUseMoney(userAccount.getUnableMoney());
				// userAcDetail2.setUseMoney(userAccount.getAbleMoney());
				// userAcDetail2.setCollection(borrow1.getUser().getAccount().getCollection());
				// userAcDetail2.setToUser(userAccount.getUser().getId());
				// userAcDetail2.setTotal(userAccount.getTotal());
				// userAcDetail2.setRemark("投标成功费用扣除");
				// userAcDetail2.setUser(userAccount.getUser());
				// userAccountDetailService.save(userAcDetail2 );
			}
		}
	}

	/**
	 * 满标审核不通过,回滚投资人投资金额
	 * 
	 * @return 0复审通过,1状态不对
	 */
	@Override
	public synchronized int updateBorrowBack(Borrow borrow, Integer id, Admin admin) {

		// 取得标的信息
		Borrow borrow1 = borrowDao.loadLock(id);// 标的状态取得并锁定

		// 判断标的状态
		if (borrow1.getStatus() != 5) {
			return 1;
		}
		
		interestCalUtil = new InterestCalUtil();

		Set<BorrowDetail> borrowDetailSet = borrow1.getBorrowDetailSet();
		// 审核不成功退回
		log.info("回滚投资人投资金额");
		for (BorrowDetail borrowDetail : borrowDetailSet) {
			UserAccount userAccount = userAccountDao.loadLockTable(borrowDetail.getUser());// 锁表
			
			
			log.info("保存资金账户操作详细记录--扣去投标金额");
			
			// 回滚可用金额
			if(borrowDetail.getAbleAmount()!=null&&!"".equals(borrowDetail.getAbleAmount()) && Double.valueOf(borrowDetail.getAbleAmount())>0) {
				
				BigDecimal backMoney = BigDecimal.valueOf(Double.valueOf(borrowDetail.getAbleAmount()));
				userAccount.setUnableMoney(
						userAccount.getUnableMoney().subtract(backMoney));
				userAccount.setAbleMoney(userAccount.getAbleMoney().add(backMoney));
				userAccountDao.update(userAccount);// 【修改投资人账户】
				
				UserAccountDetail userAccountDetail = InterestCalUtil.saveAccountDetail("invest_false",//
						backMoney,//
						"招标["+CommonUtil.fillBorrowUrl(borrow1.getId(), borrow1.getName())+"]失败返回的投标额",
						userAccount.getUser(),//
						userAccount,//
						0,//
						admin.getName(),//
						admin.getLoginIp());
				
				userAccountDetailDao.save(userAccountDetail);
			}
			
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
		

		//已使用红包投资变 未使用
		Map<String, Object> hbMap = new HashMap<String, Object>();
		hbMap.put("usedBorrowId", borrow.getId());
		hbMap.put("status", 1);
		List<UserHongbao> hbList = userHongbaoDao.getUserHongbaoList(hbMap);
		
		if(hbList != null && hbList.size() > 0){
			for(UserHongbao hb : hbList){
				hb.setStatus(0);
				hb.setEndTime(CommonUtil.getDateAfter(hb.getEndTime(), 30));//红包有效期增加 30天
				hb.setUsedBorrowId(null);
				hb.setUsedMoney(BigDecimal.ZERO);
				userHongbaoDao.update(hb);
				
				User u = userDao.load(hb.getUser().getId());
				UserAccount userAccountHongBao = userAccountDao.loadLockTable(u);
				//增加红包
				userAccountHongBao.setAwardMoney(userAccountHongBao.getAwardMoney().add(hb.getMoney()));
				userAccountDao.update(userAccountHongBao);
				
				UserAwardDetail uad = new UserAwardDetail();
				uad.setUser(hb.getUser());// 用户ID
				uad.setType("hongbao_back");// 类型（同资金记录）
				uad.setMoney(hb.getMoney());// 操作金额
				uad.setAwardMoney(null);// 奖励账户
				uad.setSignFlg(1);
				uad.setRemark("招标["
						+ CommonUtil.fillBorrowUrl(
								borrow.getId(),
								borrow.getName()) + "]失败，红包返还");// 备注
				uad.setReserve1(hb.getHbNo());
				userAwardDetailDao.save(uad);
				
			}
		}
		
		
	//*****************回滚投资红包end********************************************************
		
		
		
		borrow1.setStatus(4);// 满标审核不通过
		
		borrowDao.update(borrow1);//标的状态保存

		return 0;

	}

	/**
	 * 查询借款总金额 -xsf
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public BigDecimal getAccountYesTotal(Date startDate, Date endDate) {
		BigDecimal ret = borrowDao.getAccountYesTotal(startDate, endDate);
		if (ret==null) {
			ret = BigDecimal.valueOf(0);
		}
		return ret;
	}

	@Override
	public Pager getOutDateBorrowPage(Borrow borrow, Integer borrowId,
			Pager pager) {
		// TODO Auto-generated method stub
		return borrowDao.getOutDateBorrowPager(borrow, borrowId, pager);
	}

	@Override
	public boolean withdrawBorrow(Integer id, Admin admin) {
		
		Borrow borrow = borrowDao.load(id);
		System.out.println(borrow.getName());
		if(borrow.getStatus()!=6){
			Set<BorrowDetail> borrowDetailSet=borrow.getBorrowDetailSet();
			borrow.setStatus(6);
			borrow.setVerifyRemark("撤回标");
			String admin1 = String .valueOf(admin.getId());
			borrow.setVerifyUser(admin1);
			borrow.setVerifyTime(new Date());
			//***************************处理流转标（状态为5）撤标情况********************************************************************//
			if(borrow.getType().equals("5")){
				
				Set<BorrowRepaymentDetail> borrowRepyDetailSet=borrow.getBorrRepayDetailSet();
				Set<UserRepaymentDetail> userRepyDetailSet=borrow.getUserRepayDetailSet();
				UserAccount boruserAccount = userAccountDao.load(borrow.getUser().getAccount().getId());
				boruserAccount =userAccountDao.loadLock(borrow.getUser().getAccount().getId());
					
			//==============设置奖金=============================
					// 奖金
					
				if(borrow.getBorrowDetailSet().size()>0){

				for(BorrowDetail borrowDetail:borrowDetailSet){
					//***************************************减少投资人奖金************************************************************//
					String str ="现金奖励";
					UserAccount userAccount = this.userAccountDao.load(borrowDetail.getUser().getId());
					userAccount = this.userAccountDao.loadLock(borrowDetail.getUser().getId()); //账户锁定
					UserAccountDetail userAcDetail1 = new UserAccountDetail();
					userAccount.setTotal(userAccount.getTotal().subtract(borrowDetail.getReward()));
					if(borrow.getAwardType().compareTo(0)==1||borrow.getAwardType()==1){
						str ="红包奖励";
						userAccount.setAwardMoney(userAccount.getAwardMoney().subtract(borrowDetail.getReward()));
					}else{
					
						userAccount.setAbleMoney(SettingUtil.setPriceScale(userAccount.getAbleMoney().subtract(
							borrowDetail.getReward())));
					}
						
						userAccountDao.update(userAccount);// 【完成修改投资人账户】
						
						//资金记录
						log.info("保存资金账户操作详细记录--减少投资人奖金");
						userAcDetail1.setType("award_lower");
						userAcDetail1.setMoney(borrowDetail.getReward());
						userAcDetail1.setNoUseMoney(userAccount.getUnableMoney());
						userAcDetail1.setUseMoney(userAccount.getAbleMoney());
						userAcDetail1.setCollection(userAccount.getCollection());
						userAcDetail1.setToUser(userAccount.getUser().getId());
						userAcDetail1.setTotal(userAccount.getTotal());
						userAcDetail1.setContinueTotal(userAccount.getContinueTotal());
						
						userAcDetail1.setRemark("撤回[<a href='/borrow/detail.do?bId="+ borrow.getId()+ "' target=_blank>"+ borrow.getName() + "</a>]投资"+str);
						userAcDetail1.setUser(userAccount.getUser());
						userAcDetail1.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital());
						userAcDetail1.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest());
						userAcDetail1.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
						userAcDetail1.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest());
						userAccountDetailDao.save(userAcDetail1);
						
					//***************************************增加借款人的奖金************************************************************//	// 
						if(borrow.getAwardType().compareTo(0)==1||borrow.getAwardType()==1){
							str ="红包奖励";
							boruserAccount.setAwardMoney(userAccount.getAwardMoney().subtract(borrowDetail.getReward()));
						}else{
							boruserAccount.setAbleMoney(boruserAccount.getAbleMoney().add(
								borrowDetail.getReward()));
						}
						boruserAccount
								.setTotal(boruserAccount.getTotal().add(borrowDetail.getReward()));
						
						userAccountDao.update(boruserAccount);
						UserAccountDetail userAcDetail2 = new UserAccountDetail();
						userAcDetail2.setType("award_backadd");
						userAcDetail2.setMoney(borrowDetail.getReward());
						userAcDetail2.setNoUseMoney(boruserAccount.getUnableMoney());
						userAcDetail2.setUseMoney(boruserAccount.getAbleMoney());
						userAcDetail2.setCollection(boruserAccount.getCollection());
						userAcDetail2.setToUser(boruserAccount.getUser().getId());
						userAcDetail2.setTotal(boruserAccount.getTotal());
						userAcDetail2.setContinueTotal(boruserAccount.getContinueTotal());
						userAcDetail2.setRemark("撤回借款[<a href='/borrow/detail.do?bId="+ borrow.getId()+ "' target=_blank>"+ borrow.getName() + "</a>]发放的投资"+str);
						userAcDetail2.setUser(boruserAccount.getUser());
						userAcDetail2.setInvestorCollectionCapital(boruserAccount.getInvestorCollectionCapital());
						userAcDetail2.setInvestorCollectionInterest(boruserAccount.getInvestorCollectionInterest());
						userAcDetail2.setBorrowerCollectionCapital(boruserAccount.getBorrowerCollectionCapital());
						userAcDetail2.setBorrowerCollectionInterest(boruserAccount.getBorrowerCollectionInterest());
						userAccountDetailDao.save(userAcDetail2);

					//====增加借款人手续费===========================================================================
					 
					Listing listing = listingDao.load(ConstantUtil.BORROW_FREE_APR);// 借款手续费比例
					// 手续费 = 百分比/100 * 借款金额
					double deduction = (Double.parseDouble(listing.getKeyValue()))
							* (borrowDetail.getAccount().doubleValue());
					// 账户总金额，账户可用金额 ：增加借款手续费
					boruserAccount.setAbleMoney(boruserAccount.getAbleMoney().add(
							BigDecimal.valueOf(deduction)));
					boruserAccount.setTotal(boruserAccount.getTotal().add(
							BigDecimal.valueOf(deduction)));
					
//					boruserAccount.setCashMoneyFrozen(boruserAccount.getCashMoneyFrozen().subtract(BigDecimal.valueOf(deduction)));
					
					userAccountDao.update(boruserAccount);

					UserAccountDetail userAcDetail3 = new UserAccountDetail();
					userAcDetail3.setType("borrow_fee_false");
					userAcDetail3.setMoney(BigDecimal.valueOf(deduction));
					userAcDetail3.setNoUseMoney(boruserAccount.getUnableMoney());
					userAcDetail3.setUseMoney(boruserAccount.getAbleMoney());
					userAcDetail3.setCollection(boruserAccount.getCollection());
					userAcDetail3.setToUser(boruserAccount.getUser().getId());
					userAcDetail3.setTotal(boruserAccount.getTotal());
					userAcDetail3.setContinueTotal(boruserAccount.getContinueTotal());
					userAcDetail3.setRemark("撤回借款[<a href='/borrow/detail.do?bId="+ borrow.getId()+ "' target=_blank>"+ borrow.getName() + "</a>]手续费");
					userAcDetail3.setUser(boruserAccount.getUser());
					userAcDetail3.setInvestorCollectionCapital(boruserAccount.getInvestorCollectionCapital());
					userAcDetail3.setInvestorCollectionInterest(boruserAccount.getInvestorCollectionInterest());
					userAcDetail3.setBorrowerCollectionCapital(boruserAccount.getBorrowerCollectionCapital());
					userAcDetail3.setBorrowerCollectionInterest(boruserAccount.getBorrowerCollectionInterest());
					userAccountDetailDao.save(userAcDetail3);
					
					//------减少借款人账户-------------------------------------
					
					boruserAccount.setTotal(boruserAccount.getTotal().subtract(borrowDetail.getAccount()));
					// 可用余额 = 原可用金额 -本次的待还本金
					boruserAccount.setAbleMoney(boruserAccount.getAbleMoney().subtract(borrowDetail.getAccount()));
					// 待还本金
					boruserAccount.setBorrowerCollectionCapital(boruserAccount.getBorrowerCollectionCapital().subtract(borrowDetail.getAccount()));
					// 待还利息
					boruserAccount.setBorrowerCollectionInterest(boruserAccount.getBorrowerCollectionInterest().subtract(borrowDetail.getInterest()));

					// 可提现金额增加
//					boruserAccount.setCashMoneyFrozen(boruserAccount.getCashMoneyFrozen().add(tenderCapital));
					userAccountDao.update(boruserAccount);// 修改资金表数据
					UserAccountDetail userAcDetail4 = new UserAccountDetail();
					userAcDetail4.setType("borrow_false");
					userAcDetail4.setMoney(borrowDetail.getAccount());
					userAcDetail4.setNoUseMoney(boruserAccount.getUnableMoney());
					userAcDetail4.setUseMoney(boruserAccount.getAbleMoney());
					userAcDetail4.setCollection(boruserAccount.getCollection());
					userAcDetail4.setToUser(boruserAccount.getUser().getId());
					userAcDetail4.setTotal(boruserAccount.getTotal());
					userAcDetail4.setContinueTotal(boruserAccount.getContinueTotal());
					userAcDetail4.setRemark("撤回借款[<a href='/borrow/detail.do?bId="+ borrow.getId()+ "' target=_blank>"+ borrow.getName() + "</a>]资金");
					userAcDetail4.setUser(boruserAccount.getUser());
					userAcDetail4.setInvestorCollectionCapital(boruserAccount.getInvestorCollectionCapital());
					userAcDetail4.setInvestorCollectionInterest(boruserAccount.getInvestorCollectionInterest());
					userAcDetail4.setBorrowerCollectionCapital(boruserAccount.getBorrowerCollectionCapital());
					userAcDetail4.setBorrowerCollectionInterest(boruserAccount.getBorrowerCollectionInterest());
					userAccountDetailDao.save(userAcDetail4);
			//***************************增加投资人投资金额*************************************************************
					UserAccountDetail userAcDetail5 = new UserAccountDetail();
//					 userAccount.setUnableMoney(userAccount.getUnableMoney().subtract(borrowDetail.getAccount()));
					 userAccount.setAbleMoney(userAccount.getAbleMoney().add(SettingUtil.strToBigDec(borrowDetail.getAbleAmount())));
					 userAccount.setContinueTotal(userAccount.getContinueTotal().add(SettingUtil.strToBigDec(borrowDetail.getContinueAmount())));
					 userAccountDao.update(userAccount);//【修改投资人账户】
					log.info("保存资金账户操作详细记录--扣去投标金额");
					userAcDetail5.setType("invest_false");
					userAcDetail5.setMoney(borrowDetail.getAccount());
					userAcDetail5.setNoUseMoney(userAccount.getUnableMoney());
					userAcDetail5.setUseMoney(userAccount.getAbleMoney());
					userAcDetail5.setCollection(userAccount.getCollection());
					userAcDetail5.setToUser(userAccount.getUser().getId());
					userAcDetail5.setTotal(userAccount.getTotal());
					userAcDetail5.setContinueTotal(userAccount.getContinueTotal());
					userAcDetail5.setRemark("招标[<a href='/borrow/detail.do?bId="+ borrow.getId()+ "' target=_blank>"+ borrow.getName() + "</a>]失败返回的投标额");
					userAcDetail5.setUser(userAccount.getUser());
					userAcDetail5.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital());
					userAcDetail5.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest());
					userAcDetail5.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
					userAcDetail5.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest());
					userAccountDetailDao.save(userAcDetail5);
		//***************************************************************************************************************		
				
		//*************************************************************************************************************//f
					
					// ★★★★扣去续单金额奖励 开始★★★★
							
						UserAccountDetail userAcDetail = new UserAccountDetail();
							// 续单奖励=续单金额 * 续单奖励率 
//							Listing listing2 = listingDao.load(ConstantUtil.CONTINUE_REWARD_WANDER);
//							BigDecimal getaward=BigDecimal.valueOf(Double.parseDouble(borrowDetail.getContinueAmount())*Double.parseDouble(listing2.getKeyValue())*Integer.parseInt(borrow.getTimeLimit()));
//							BigDecimal getaward=BigDecimal.valueOf(Double.parseDouble(borrowDetail.getContinueAmount())*Double.parseDouble(listing2.getKeyValue()));
							BigDecimal getaward = new BigDecimal(borrowDetail.getContinueAmount()).multiply(borrow.getContinueAwardRate());
							userAccount.setTotal(userAccount.getTotal().subtract(getaward));
							userAccount.setAbleMoney(SettingUtil.setPriceScale(userAccount.getAbleMoney().subtract(getaward)));
							userAccountDao.update(userAccount);// 【完成修改投资人账户】
							
							log.info("保存资金账户操作详细记录--扣去续投奖励金额");
							userAcDetail.setType("award_false");
							userAcDetail.setMoney(getaward);
							userAcDetail.setNoUseMoney(userAccount.getUnableMoney());
							userAcDetail.setUseMoney(userAccount.getAbleMoney());
							userAcDetail.setCollection(userAccount.getCollection());
							userAcDetail.setToUser(userAccount.getUser().getId());
							userAcDetail.setTotal(userAccount.getTotal());
							userAcDetail.setContinueTotal(userAccount.getContinueTotal());
							userAcDetail.setRemark("扣除[<a href='/borrow/detail.do?bId="+ borrow.getId()+ "' target=_blank>流转标"+ borrow.getName() + "</a>]续投奖励");
							userAcDetail.setUser(userAccount.getUser());
							userAcDetail.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital());
							userAcDetail.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest());
							userAcDetail.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
							userAcDetail.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest());
							userAccountDetailDao.save(userAcDetail);
							
						}
					}
					if(borrow.getBorrRepayDetailSet().size()>0){
						log.info("修改还款记录状态");
						System.out.println(borrowRepyDetailSet.size());
						for(BorrowRepaymentDetail borrowRepyDetail:borrowRepyDetailSet){
							if(borrowRepyDetail.getStatus().equals("0")||borrowRepyDetail.getStatus()==0){
								borrowRepyDetail.setStatus(3);
								borrowRepaymentDetailDao.update(borrowRepyDetail);
							}else{
								return false;
							}
								
						}
					}
				if(borrow.getUserRepayDetailSet().size()>0){
						log.info("修改用户还款记录状态");
						System.out.println(userRepyDetailSet.size());
						for(UserRepaymentDetail userRepyDetail:userRepyDetailSet){
							if(userRepyDetail.getStatus().equals("0")){
								userRepyDetail.setStatus("3");
								userRepaymentDetailDao.update(userRepyDetail);
							}else{
								return false;
							}
								
						}
					}
	
				
			}else{
					if(borrow.getBorrowDetailSet().size()>0){
						log.info("回滚投资人投资金额");
						System.out.println(borrowDetailSet.size());
							for(BorrowDetail borrowDetail:borrowDetailSet){
								UserAccountDetail userAcDetail = new UserAccountDetail();
								UserAccount userAccount = userAccountDao.load(borrowDetail.getUser().getAccount().getId());//获取投标人账户资金
								userAccount= userAccountDao.loadLock(userAccount.getId());
								 userAccount.setUnableMoney(userAccount.getUnableMoney().subtract(borrowDetail.getAccount()));
								 userAccount.setAbleMoney(userAccount.getAbleMoney().add(SettingUtil.strToBigDec(borrowDetail.getAbleAmount())));
								 userAccount.setContinueTotal(userAccount.getContinueTotal().add(SettingUtil.strToBigDec(borrowDetail.getContinueAmount())));
								 userAccountDao.update(userAccount);//【修改投资人账户】
								log.info("保存资金账户操作详细记录--扣去投标金额");
								userAcDetail.setType("invest_false");
								userAcDetail.setMoney(borrowDetail.getAccount());
								userAcDetail.setNoUseMoney(userAccount.getUnableMoney());
								userAcDetail.setUseMoney(userAccount.getAbleMoney());
								userAcDetail.setCollection(userAccount.getCollection());
								userAcDetail.setToUser(userAccount.getUser().getId());
								userAcDetail.setTotal(userAccount.getTotal());
								userAcDetail.setContinueTotal(userAccount.getContinueTotal());
								userAcDetail.setRemark("招标[<a href='/borrow/detail.do?bId="+ borrow.getId()+ "' target=_blank>"+ borrow.getName() + "</a>]失败返回的投标额");
								userAcDetail.setUser(userAccount.getUser());
								userAcDetail.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital());
								userAcDetail.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest());
								userAcDetail.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
								userAcDetail.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest());
								userAccountDetailDao.save(userAcDetail);
							}
					}
			}
			
			
		//*****************回滚投资红包********************************************************
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("borrowId", borrow.getId());
			map.put("type", "money_for_bonus");
			
			//已使用红包投资变 未使用
			Map<String, Object> hbMap = new HashMap<String, Object>();
			hbMap.put("usedBorrowId", borrow.getId());
			hbMap.put("status", 1);
			List<UserHongbao> hbList = userHongbaoDao.getUserHongbaoList(hbMap);
			
			if(hbList != null && hbList.size() > 0){
				for(UserHongbao hb : hbList){
					hb.setStatus(0);
					hb.setEndTime(CommonUtil.getDateAfter(hb.getEndTime(), 30));//红包有效期增加 30天
					hb.setUsedBorrowId(null);
					hb.setUsedMoney(BigDecimal.ZERO);
					userHongbaoDao.update(hb);
					
					User u = userDao.load(hb.getUser().getId());
					UserAccount userAccountHongBao = userAccountDao.loadLockTable(u);
					//增加红包
					userAccountHongBao.setAwardMoney(userAccountHongBao.getAwardMoney().add(hb.getMoney()));
					userAccountDao.update(userAccountHongBao);
					
					UserAwardDetail uad = new UserAwardDetail();
					uad.setUser(hb.getUser());// 用户ID
					uad.setType("hongbao_back");// 类型（同资金记录）
					uad.setMoney(hb.getMoney());// 操作金额
					uad.setAwardMoney(null);// 奖励账户
					uad.setSignFlg(1);
					uad.setRemark("招标["
							+ CommonUtil.fillBorrowUrl(
									borrow.getId(),
									borrow.getName()) + "]失败，红包返还");// 备注
					uad.setReserve1(hb.getHbNo());
					userAwardDetailDao.save(uad);
					
				}
			}
			
			
			
			
			
			
			
		//*****************回滚投资红包end********************************************************
			
		//*****************回滚流转标的推荐奖励********************************************************
		 if(borrow.getType().equals("5")){
			 
			 Map<String, Object> map1=new HashMap<String, Object>();
				map1.put("borrowId", borrow.getId());
				map1.put("type", "tui_detail_award");
		
				List<UserAccountDetail> userAccountDeLsit= userAccountDetailDao.getAccountDetailList(map);
				for(UserAccountDetail userAccountDetail:userAccountDeLsit){
					User  usertui =userDao.load(userAccountDetail.getUser().getId());
					UserAccount userAccountTui = userAccountDao.load(usertui.getAccount().getId());//获取投标人账户资金
					userAccountTui= userAccountDao.loadLock(userAccountTui.getId());
					//减少可用
					userAccountTui.setAbleMoney(userAccountTui.getAbleMoney().subtract(userAccountDetail.getMoney()));
					//增加红包
					userAccountTui.setAwardMoney(userAccountTui.getAwardMoney().add(userAccountDetail.getMoney()));
					userAccountDao.update(userAccountTui);
					//回滚记录
					UserAccountDetail userAcDetailBack = new UserAccountDetail();
					userAcDetailBack.setType("invest_false_tuijian");
					userAcDetailBack.setMoney(userAccountDetail.getMoney());
					userAcDetailBack.setNoUseMoney(userAccountTui.getUnableMoney());
					userAcDetailBack.setUseMoney(userAccountTui.getAbleMoney());
					userAcDetailBack.setCollection(userAccountTui.getCollection());
					userAcDetailBack.setToUser(userAccountTui.getUser().getId());
					userAcDetailBack.setTotal(userAccountTui.getTotal());
					userAcDetailBack.setRemark("招标[<a href='/borrow/detail.do?bId="+ borrow.getId()+ "' target=_blank>"+ borrow.getName() + "</a>]失败返回推荐奖励");
					userAcDetailBack.setUser(userAccountTui.getUser());
					userAcDetailBack.setInvestorCollectionCapital(userAccountTui.getInvestorCollectionCapital());
					userAcDetailBack.setInvestorCollectionInterest(userAccountTui.getInvestorCollectionInterest());
					userAcDetailBack.setBorrowerCollectionCapital(userAccountTui.getBorrowerCollectionCapital());
					userAcDetailBack.setBorrowerCollectionInterest(userAccountTui.getBorrowerCollectionInterest());
					userAccountDetailDao.save(userAcDetailBack);
		 }
		 }
			
			borrow.setStatus(6);
			borrowDao.update(borrow);
			return true;
		}
		return false;
	}

}
