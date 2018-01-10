package com.qmd.service.impl.borrow;

import com.qmd.dao.borrow.BorrowDaoService;
import com.qmd.dao.borrow.BorrowRepaymentDetailDaoService;
import com.qmd.dao.borrow.BorrowTenderDaoService;
import com.qmd.dao.user.UserAccountDao;
import com.qmd.dao.user.UserAccountDetailDao;
import com.qmd.dao.user.UserRepaymentDetailDao;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountDetail;
import com.qmd.mode.user.UserRepaymentDetail;
import com.qmd.mode.util.Listing;
import com.qmd.service.borrow.BorrowWanderService;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.mail.MailService;
import com.qmd.util.AccountDetailUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

/**
 * BorrowWanderServiceImpl 秒标方法的Service实现类
 *
 */
@Repository("borrowWanderService")
public class BorrowWanderServiceImpl extends BaseServiceImpl<BorrowTender,Integer> implements BorrowWanderService {
	Logger log = Logger.getLogger(BorrowWanderServiceImpl.class);
	
	private Logger logWander = Logger.getLogger("userWanderLog");
	
	@Resource
	UserAccountDao userAccountDao;
	@Resource
	UserAccountDetailDao userAccountDetailDao;
	@Resource
	BorrowDaoService borrowDaoService;
	@Resource
	MailService mailService;
	@Resource
	BorrowTenderDaoService borrowTenderDaoService;
	@Resource
	UserRepaymentDetailDao userRepaymentDetailDao;
	@Resource
	BorrowRepaymentDetailDaoService borrowRepaymentDetailDaoService;
	@Resource
	ListingDao listingDao;
	
	@Override
	public synchronized int borrowInvestDo(User user,BorrowTender borrowTender,String ip,int orderNum) {
		
		logWander.debug("【流转标开始】"+user.getUsername()+",["+borrowTender.getBorrowId()+"]");
		long st = CommonUtil.getDateTimeLong();
		
		UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(user.getId());
		userAccount = this.userAccountDao.getForUpdate(userAccount.getId(), userAccount); //账户锁定
		Borrow borrow1 = this.borrowDaoService.getBorrowById(borrowTender.getBorrowId());
		Borrow borrow = this.borrowDaoService.getForUpdate(borrow1.getId());//标锁定
		borrow.setUsername(borrow1.getUsername());
		borrow.setCity(borrow1.getCity());
		borrow.setProvince(borrow1.getProvince());
		borrow.setArea(borrow1.getArea());
		
		if (Double.parseDouble(borrow.getBalance())<=0) {
			
			logWander.debug("【流转标结束】"+user.getUsername()+"投标失败,标已满["+borrow.getBalance()+"]");
			return 1;
		}
		
		// 账户可用金额少于投标金额
		if (userAccount.getAbleMoney().doubleValue() < Double.parseDouble(borrowTender.getAbleAmount())) {
			logWander.debug("【流转标结束】"+user.getUsername()+"投标失败,可用余额不足["+userAccount.getAbleMoney()+"]");
			return 2;// 余额不足
		}
		// 账户可用金额少于投标金额
		if (userAccount.getContinueTotal().doubleValue() < Double.parseDouble(borrowTender.getContinueAmount())) {
			logWander.debug("【流转标结束】"+user.getUsername()+"投标失败,续投余额不足["+userAccount.getContinueTotal()+"]");
			return 3;// 余额不足
		}
		
		//投标金额大于标剩余金额
		if(Double.parseDouble(borrowTender.getMoney()) > Double.parseDouble(borrow.getBalance())){
			borrowTender.setAccount(borrow.getBalance());
			borrowTender.setStatus("2");
			
			//>>>部分投资，计算扣除续投金额，可用金额
			//1,续投金额 大于等于 投资金额  
			if (Double.parseDouble(borrowTender.getContinueAmount()) >= Double.parseDouble(borrowTender.getAccount())) {
				borrowTender.setContinueAmount(borrowTender.getAccount());// 实际投标金额
				borrowTender.setAbleAmount("0");//不适用可用金额
			} else {
				//2,续投金额 小于 投资金额；续投全额，可用金额需变更
				double ableFact = Double.valueOf(borrowTender.getAccount()) - Double.valueOf(borrowTender.getContinueAmount());
				borrowTender.setAbleAmount(String.valueOf(ableFact));
			}
		}else{
			borrowTender.setAccount(borrowTender.getMoney());
			borrowTender.setStatus("1");
		}
		
		
		// 取得对应回购期数的标的回购信息
		BorrowRepaymentDetail borRepDetail = null;
		List<BorrowRepaymentDetail> borRepList = borrowRepaymentDetailDaoService.queryUserBorrowList(borrow.getId());
		for (int i =0;i<borRepList.size();i++) {
			if (borRepList.get(i).getOrderNum()==orderNum ) {
				borRepDetail = borRepList.get(i);
				break;
			}
		}
		
		Date beginDate = CommonUtil.date2begin(new Date());
		Date repayDate = CommonUtil.date2begin(borRepDetail.getRepaymentTime());// 还款日期
		Long borrowDay = CommonUtil.getDateSubtractDay(beginDate,repayDate);
		
		//修改实际投标金额
		//String interest = String.valueOf((Double.valueOf(borrowTender.getAccount()) * borrow.getApr()/100));
		String interest = CommonUtil.setPriceScale(CommonUtil.getInterest(Double.valueOf(borrowTender.getAccount()), borrowDay.intValue(), borrow.getApr()));
		borrowTender.setInterest(interest);
		borrowTender.setRepaymentAccount(CommonUtil.setPriceScale(Double.valueOf(borrowTender.getAccount()) + Double.valueOf(interest)));
		borrowTender.setRepaymentYesaccount("0");
		borrowTender.setWaitAccount(borrowTender.getAccount());
		borrowTender.setRepaymentYesinterest("0");
		borrowTender.setWaitInterest(interest);
		borrowTender.setUserId(user.getId());
		borrowTender.setAddPersion(user.getUsername());
		borrowTender.setOperatorIp(ip);
		this.borrowTenderDaoService.save(borrowTender);
		
		// 续投资金修改及记录
		if (Double.valueOf(borrowTender.getContinueAmount()) > 0) {
			//修改用户续投余额
			userAccount.setContinueTotal(BigDecimal.valueOf(userAccount.getContinueTotal().doubleValue() - Double.parseDouble(borrowTender.getContinueAmount())));
			//修改用户冻结金额
			userAccount.setUnableMoney(BigDecimal.valueOf(userAccount.getUnableMoney().doubleValue() + Double.parseDouble(borrowTender.getContinueAmount())));
			this.userAccountDao.update(userAccount);
			
			//资金记录
			UserAccountDetail userAccountDetail = AccountDetailUtil.fillUserAccountDetail("tender_continued",
					BigDecimal.valueOf(Double.valueOf(borrowTender.getContinueAmount())),
					10000, "续投冻结资金["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+"]", ip, userAccount);
			this.userAccountDetailDao.save(userAccountDetail);
		} 
		
		// 可用资金修改及记录
		if (Double.valueOf(borrowTender.getAbleAmount()) > 0) {
			//修改用户可用金额
			userAccount.setAbleMoney(BigDecimal.valueOf(userAccount.getAbleMoney().doubleValue() - Double.parseDouble(borrowTender.getAbleAmount())));
			//修改用户冻结金额
			userAccount.setUnableMoney(BigDecimal.valueOf(userAccount.getUnableMoney().doubleValue() + Double.parseDouble(borrowTender.getAbleAmount())));
			this.userAccountDao.updateAll(userAccount);
			
			//资金记录
			UserAccountDetail userAccountDetail = AccountDetailUtil.fillUserAccountDetail("tender",
					BigDecimal.valueOf(Double.valueOf(borrowTender.getAbleAmount())),
					10000, "投标冻结资金["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+"]", ip, userAccount);
			this.userAccountDetailDao.save(userAccountDetail);
		}
					
		//修改标的数据 
		//DecimalFormat df = new DecimalFormat("#");
		borrow.setBalance(CommonUtil.setPriceScale(Double.valueOf(borrow.getBalance()) - Double.valueOf(borrowTender.getAccount())));
		NumberFormat nf =NumberFormat.getInstance();
		nf.setMaximumFractionDigits(0);
		//nf.setMaximumFractionDigits(2);
		String shc = nf.format((1 - Double.valueOf(borrow.getBalance())/Double.valueOf(borrow.getAccount()))*100);
		//borrow.setSchedule(String.valueOf(1-(Double.valueOf(borrow.getBalance())/Double.valueOf(borrow.getAccount()))));
		borrow.setSchedule(shc);
		if(Double.valueOf(borrow.getBalance()) == 0){
			//borrow.setStatus(5); 流转标没有满标审核
		}
		if(borrow.getTenderTimes()==null||"".equals(borrow.getTenderTimes())) {
			borrow.setTenderTimes("0");
		}
		borrow.setTenderTimes(String.valueOf(Integer.parseInt(borrow.getTenderTimes()) + 1));
		
		borrow.setRepayCapital(borrow.getRepayCapital().add(BigDecimal.valueOf(Double.valueOf(borrowTender.getAccount()))));
		borrow.setRepayInterest(borrow.getRepayInterest().add(BigDecimal.valueOf(Double.valueOf(borrowTender.getInterest()))));
		borrow.setRepaymentAccount(String.valueOf(borrow.getRepayCapital().add(borrow.getRepayInterest())));
		
		this.getBorrowDaoService().update(borrow);
		
		logWander.debug("   投标金额["+borrowTender.getAccount()+"] 标余额["+borrow.getBalance()+"]");
		
		//--------直接投标成功，扣除投标金额
		
		borRepDetail.setCapital(String.valueOf(Double.valueOf(borRepDetail.getCapital()) + Double.valueOf(borrowTender.getAccount())));
		BigDecimal userInterest = CommonUtil.setPriceScale(new BigDecimal(Double.valueOf( borrowTender.getInterest())));
		borRepDetail.setInterest(String.valueOf(Double.valueOf(borRepDetail.getInterest()) + userInterest.doubleValue()));
		borRepDetail.setRepaymentAccount(String.valueOf(Double.valueOf(borRepDetail.getCapital()) + Double.valueOf(borRepDetail.getInterest())));
		
		borrowRepaymentDetailDaoService.update(borRepDetail);
		
		// 投资人，每期的投资收益明细
		
		UserRepaymentDetail userRepaymentDetail = new UserRepaymentDetail();

		userRepaymentDetail.setUserId(user.getId());
		userRepaymentDetail.setStatus("0");// 未还款
		userRepaymentDetail.setAccount(borrowTender.getAccount());
		userRepaymentDetail.setInterest(borrowTender.getInterest());
		userRepaymentDetail.setRepaymentAccount(borrowTender.getRepaymentAccount());

		userRepaymentDetail.setWaitInterest(userRepaymentDetail
				.getInterest());
		userRepaymentDetail.setWaitAccount(userRepaymentDetail
				.getAccount());
		userRepaymentDetail.setAddPersion(String.valueOf(user.getId()));
		userRepaymentDetail.setOperatorIp(ip);
		userRepaymentDetail.setBorrowPeriods(1);
		userRepaymentDetail.setBorrowId(borrow.getId());
		userRepaymentDetail.setBorrowRepaymentId(borRepDetail.getId());
		userRepaymentDetail.setRepaymentDate(borRepDetail.getRepaymentTime());
		
		Listing listing_lixi = listingDao.getListing(ConstantUtil.INTEREST_CHARGES);
		
		// 利息管理费
		BigDecimal userserviceCharge = CommonUtil.setPriceScale(BigDecimal
				.valueOf((Double.valueOf(userRepaymentDetail.getInterest()).doubleValue() * Double.valueOf(listing_lixi.getKeyValue()))),ConstantUtil.RoundType.roundHalfUp);
		userRepaymentDetail.setServiceCharge(String.valueOf(userserviceCharge));
		userRepaymentDetail.setProfit(String.valueOf(Double.valueOf(userRepaymentDetail.getInterest()) - Double.valueOf(userRepaymentDetail.getServiceCharge())));

		userRepaymentDetail.setRepaymentYesaccount(userRepaymentDetail
				.getAccount());
		userRepaymentDetail.setRepaymentYesinterest(userRepaymentDetail
				.getInterest());
		
		userRepaymentDetail.setBorrowDetailId(borrowTender.getId());
		
		userRepaymentDetail.setCreateDate(new Date());
		
		userRepaymentDetailDao.save(userRepaymentDetail);
		
		//---------------------------------------------
		// 投资额
		BigDecimal tenderCapital = new BigDecimal(borrowTender.getAccount());
		BigDecimal tenderInterest = new BigDecimal(borrowTender.getInterest());
		
		// 奖金
		BigDecimal reward = null;
		if ("1".equals(borrow.getAward())) {
			// 发放投资人奖金
			double app = Double.parseDouble(borrow.getFunds()) / 100;
			reward = tenderCapital.multiply(BigDecimal.valueOf(app));
			
			reward = CommonUtil.setPriceScale(BigDecimal.valueOf(reward.doubleValue() * borrowDay));
		}
		
		// 冻结金额 = 冻结金额-投资金额
		userAccount.setUnableMoney(userAccount.getUnableMoney()
				.subtract(tenderCapital));
		// 投资人待收本金 增加
		userAccount.setInvestorCollectionCapital(userAccount
				.getInvestorCollectionCapital().add(tenderCapital));
		// 投资人待收利息 增加
		userAccount.setInvestorCollectionInterest(userAccount
				.getInvestorCollectionInterest().add(tenderInterest));
		
		this.userAccountDao.updateAll(userAccount);
		
		//资金记录
		UserAccountDetail userAccountDetail2 = AccountDetailUtil.fillUserAccountDetail("invest", 
				BigDecimal.valueOf(Double.valueOf(borrowTender.getAccount())),
				10000, "投标成功费用扣除["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+"]", ip, userAccount);
		
		this.userAccountDetailDao.save(userAccountDetail2);
		
		if ("1".equals(borrow.getAward())) {
			
			userAccount.setTotal(userAccount.getTotal().add(reward));
			userAccount.setAbleMoney(CommonUtil.setPriceScale(userAccount.getAbleMoney().add(
							reward)));
			userAccountDao.updateAll(userAccount);// 【完成修改投资人账户】
			
			//资金记录
			UserAccountDetail userAccountDetail11 = AccountDetailUtil.fillUserAccountDetail("award_add",
					reward, 
					10000,
					"借款["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
					+ "]的奖励",
					ip, userAccount);
			
			this.userAccountDetailDao.save(userAccountDetail11);
			
		}
		
		//------增加借款人账户-------------------------------------
		
		UserAccount boruserAccount = this.userAccountDao.getUserAccountByUserId(borrow.getUserId());
		// 可用余额 = 原可用金额 + 本次的待还本金
		boruserAccount.setAbleMoney(boruserAccount.getAbleMoney().add(tenderCapital));
		// 待还本金
		boruserAccount.setBorrowerCollectionCapital(boruserAccount.getBorrowerCollectionCapital().add(tenderCapital));
		// 待还利息
		boruserAccount.setBorrowerCollectionInterest(boruserAccount.getBorrowerCollectionInterest().add(tenderInterest));

		userAccountDao.updateAll(boruserAccount);// 修改资金表数据
		
		//资金记录
		UserAccountDetail userAccountDetail3 = AccountDetailUtil.fillUserAccountDetail("borrow_success",
				BigDecimal.valueOf(Double.valueOf(borrowTender.getAccount())), 
				10000, 
				"对标["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
					+ "]借款成功",
				ip, boruserAccount);
		
		this.userAccountDetailDao.save(userAccountDetail3);
		
		//====扣除借款人手续费
		
		Listing listing = listingDao.getListing(ConstantUtil.BORROW_FREE_APR);// 借款手续费比例
		// 手续费 = 百分比/100 * 借款金额
		double deduction = (Double.parseDouble(listing.getKeyValue()))
				* (tenderCapital.doubleValue());
		// 账户总金额，账户可用金额 ：扣去借款手续费
		boruserAccount.setAbleMoney(boruserAccount.getAbleMoney().subtract(
				BigDecimal.valueOf(deduction)));
		boruserAccount.setTotal(boruserAccount.getTotal().subtract(
				BigDecimal.valueOf(deduction)));
		userAccountDao.updateAll(boruserAccount);

		UserAccountDetail userAccountDetail4 = AccountDetailUtil.fillUserAccountDetail("borrow_fee",
				BigDecimal.valueOf(deduction), 
				10000, 
				"对标["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
					+ "]借款手续费",
						ip, boruserAccount);
		this.userAccountDetailDao.save(userAccountDetail4);
		
		//====设置奖金=======================
		
		if ("1".equals(borrow.getAward())) {
			// 扣除借款人的奖金
			boruserAccount.setAbleMoney(boruserAccount.getAbleMoney().subtract(
					reward));
			boruserAccount
					.setTotal(boruserAccount.getTotal().subtract(reward));
			userAccountDao.updateAll(boruserAccount);
			
			UserAccountDetail userAccountDetail5 = AccountDetailUtil.fillUserAccountDetail("award_lower", 
					reward, 
					10000, 
					"扣除借款["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
					+ "]的奖励", ip, boruserAccount);
			this.userAccountDetailDao.save(userAccountDetail5);
			
		}
		
		// ★★★★投资人续投宝金额奖励 开始★★★★
		if(borrowTender.getContinueAmount() != null && !"".equals(borrowTender.getContinueAmount().trim()) && Double.valueOf(borrowTender.getContinueAmount()) > 0){
			//BigDecimal continueMoney=BigDecimal.valueOf(Double.valueOf(borrowTender.getContinueAmount()));//获取续投金额
			// 续投奖励=续投金额 * 续投奖励率 * 投资天数
			Listing listing2 = listingDao.getListing(ConstantUtil.CONTINUE_REWARD_WANDER);
			BigDecimal getaward=BigDecimal.valueOf(Double.parseDouble(borrowTender.getContinueAmount())*Double.parseDouble(listing2.getKeyValue())*borrowDay.intValue());
			userAccount.setTotal(userAccount.getTotal().add(getaward));
			userAccount.setAbleMoney(CommonUtil.setPriceScale(userAccount.getAbleMoney().add(getaward)));
			userAccountDao.updateAll(userAccount);// 【完成修改投资人账户】
			
			UserAccountDetail userAccountDetail6 = AccountDetailUtil.fillUserAccountDetail("award_continued", 
					getaward, 
					10000, 
					"对借款["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+"]的续投奖励", ip, userAccount);
			this.userAccountDetailDao.save(userAccountDetail6);
		}
		
		try {
			if(user.getEmailStatus()!=null && user.getEmailStatus().equals(1)){
				mailService.sendAgreementForInvestor(borrow, userRepaymentDetail, user);//发送借款协议邮件
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long ed = CommonUtil.getDateTimeLong();
		logWander.debug("【流转标结束】"+user.getUsername()+" 时间["+(ed-st)+"]");
		
		return 0;
	}

	public UserAccountDao getUserAccountDao() {
		return userAccountDao;
	}

	public void setUserAccountDao(UserAccountDao userAccountDao) {
		this.userAccountDao = userAccountDao;
	}

	public UserAccountDetailDao getUserAccountDetailDao() {
		return userAccountDetailDao;
	}

	public void setUserAccountDetailDao(UserAccountDetailDao userAccountDetailDao) {
		this.userAccountDetailDao = userAccountDetailDao;
	}

	public BorrowDaoService getBorrowDaoService() {
		return borrowDaoService;
	}

	public void setBorrowDaoService(BorrowDaoService borrowDaoService) {
		this.borrowDaoService = borrowDaoService;
	}

	public BorrowTenderDaoService getBorrowTenderDaoService() {
		return borrowTenderDaoService;
	}

	public void setBorrowTenderDaoService(
			BorrowTenderDaoService borrowTenderDaoService) {
		this.borrowTenderDaoService = borrowTenderDaoService;
	}
	
	
}
