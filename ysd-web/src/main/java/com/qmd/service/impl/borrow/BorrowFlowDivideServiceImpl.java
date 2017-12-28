package com.qmd.service.impl.borrow;

import com.qmd.dao.borrow.BorrowDaoService;
import com.qmd.dao.borrow.BorrowRepaymentDetailDaoService;
import com.qmd.dao.borrow.BorrowTenderDaoService;
import com.qmd.dao.user.UserAccountDao;
import com.qmd.dao.user.UserAccountDetailDao;
import com.qmd.dao.user.UserAwardDetailDao;
import com.qmd.dao.user.UserRepaymentDetailDao;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.*;
import com.qmd.mode.util.Listing;
import com.qmd.service.borrow.BorrowFlowDivideService;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.util.AccountDetailUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BorrowWanderServiceImpl 秒标方法的Service实现类
 *
 */
@Repository("borrowFlowDivideService")
public class BorrowFlowDivideServiceImpl extends BaseServiceImpl<BorrowTender,Integer> implements BorrowFlowDivideService {
	Logger log = Logger.getLogger(BorrowFlowDivideServiceImpl.class);
	
	private Logger logWander = Logger.getLogger("userWanderLog");
	
	@Resource
	UserAccountDao userAccountDao;
	@Resource
	UserAccountDetailDao userAccountDetailDao;
	@Resource
	BorrowDaoService borrowDaoService;
	@Resource
	BorrowTenderDaoService borrowTenderDaoService;
	@Resource
	UserRepaymentDetailDao userRepaymentDetailDao;
	@Resource
	BorrowRepaymentDetailDaoService borrowRepaymentDetailDaoService;
	@Resource
	ListingDao listingDao;
	@Resource
	UserAwardDetailDao userAwardDetailDao;
	
	@Override
	public synchronized int borrowInvestDo(User user,BorrowTender borrowTender,String ip) {
		
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
		// 账户续单余额少于投标金额
		if (userAccount.getContinueTotal().doubleValue() < Double.parseDouble(borrowTender.getContinueAmount())) {
			logWander.debug("【流转标结束】"+user.getUsername()+"投标失败,续单余额不足["+userAccount.getContinueTotal()+"]");
			return 3;// 余额不足
		}
		
		//投标金额大于标剩余金额
		if(Double.parseDouble(borrowTender.getMoney()) > Double.parseDouble(borrow.getBalance())){
			borrowTender.setAccount(borrow.getBalance());
			borrowTender.setStatus("2");
			
			//>>>部分投资，计算扣除续单金额，可用金额
			//1,续单金额 大于等于 投资金额  
			if (Double.parseDouble(borrowTender.getContinueAmount()) >= Double.parseDouble(borrowTender.getAccount())) {
				borrowTender.setContinueAmount(borrowTender.getAccount());// 实际投标金额
				borrowTender.setAbleAmount("0");//不适用可用金额
			} else {
				//2,续单金额 小于 投资金额；续单全额，可用金额需变更
				double ableFact = Double.valueOf(borrowTender.getAccount()) - Double.valueOf(borrowTender.getContinueAmount());
				borrowTender.setAbleAmount(String.valueOf(ableFact));
			}
		}else{
			borrowTender.setAccount(borrowTender.getMoney());
			borrowTender.setStatus("1");
		}
		
//		int repayTime = Integer.parseInt(borrow.getTimeLimit()) + 1;
//		
//		Date repayDT = CommonUtil.getDateAfter(new Date(), repayTime);
//		String datestr = CommonUtil.getDate2String(repayDT,"yyyyMMdd");
//		int dateint = Integer.parseInt(datestr);
//
//		// 取得对应回购期数的标的回购信息
//		BorrowRepaymentDetail borRepDetail = null;
//		List<BorrowRepaymentDetail> borRepList = borrowRepaymentDetailDaoService.queryUserBorrowList(borrow.getId());
//		for (int i =0;i<borRepList.size();i++) {
//			if (borRepList.get(i).getRepaymentDateInt().equals(dateint)) {
//				borRepDetail = borRepList.get(i);
//				break;
//			}
//		}
//
//		Date beginDate = CommonUtil.date2begin(new Date());
//		Date repayDate = CommonUtil.date2begin(borRepDetail.getRepaymentTime());// 还款日期
//		Long borrowDay = CommonUtil.getDateSubtractDay(beginDate,repayDate);

		//修改实际投标金额
		//String interest = String.valueOf((Double.valueOf(borrowTender.getAccount()) * borrow.getApr()/100));
		//利息
		double timeLimitInterest = CommonUtil.getInterest(Double.valueOf(borrowTender.getAccount()),  Integer.parseInt(borrow.getTimeLimit()), borrow.getApr());
		
		String interest = CommonUtil.setPriceScale(timeLimitInterest);
		
		borrowTender.setInterest(interest);
		borrowTender.setRepaymentAccount(CommonUtil.setPriceScale(Double.valueOf(borrowTender.getAccount()) + Double.valueOf(interest)));
		borrowTender.setRepaymentYesaccount("0");
		borrowTender.setWaitAccount(borrowTender.getAccount());
		borrowTender.setRepaymentYesinterest("0");
		borrowTender.setWaitInterest(interest);
		borrowTender.setUserId(user.getId());
		borrowTender.setAddPersion(user.getUsername());
		borrowTender.setOperatorIp(ip);
		// 奖金
		BigDecimal reward = new BigDecimal(0);
		// 奖金
		if ("1".equals(borrow.getAward())) {
			// 发放投资人奖金
			double app = Double.parseDouble(borrow.getFunds()) / 100;
			reward = new BigDecimal(borrowTender.getAccount()).multiply(BigDecimal.valueOf(app));
			
			reward = CommonUtil.setPriceScale(BigDecimal.valueOf(reward.doubleValue() * Integer.parseInt(borrow.getTimeLimit())));
		}
		
		borrowTender.setReward(reward.toString());
		
		this.borrowTenderDaoService.save(borrowTender);
		
		// 续单资金修改及记录
		if (Double.valueOf(borrowTender.getContinueAmount()) > 0) {
			//修改用户续单余额
			userAccount.setContinueTotal(BigDecimal.valueOf(userAccount.getContinueTotal().doubleValue() - Double.parseDouble(borrowTender.getContinueAmount())));
			//修改用户冻结金额
			userAccount.setUnableMoney(BigDecimal.valueOf(userAccount.getUnableMoney().doubleValue() + Double.parseDouble(borrowTender.getContinueAmount())));
			this.userAccountDao.update(userAccount);
			
			//资金记录
			UserAccountDetail userAccountDetail = AccountDetailUtil.fillUserAccountDetail("tender_continued",
					BigDecimal.valueOf(Double.valueOf(borrowTender.getContinueAmount())),
					10000, "续单冻结资金["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+"]", ip, userAccount);
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
		borrow.setSchedule(String.valueOf(1-(Double.valueOf(borrow.getBalance())/Double.valueOf(borrow.getAccount()))));
		borrow.setSchedule(shc);
		if(Double.valueOf(borrow.getBalance()) == 0){
			//borrow.setStatus(5); 流转标没有满标审核
		}
		if(borrow.getTenderTimes()==null||"".equals(borrow.getTenderTimes())) {
			borrow.setTenderTimes("0");
		}
		borrow.setTenderTimes(String.valueOf(Integer.parseInt(borrow.getTenderTimes()) + 1));
		
		if (borrow.getRepayCapital()==null) {
			borrow.setRepayCapital(BigDecimal.valueOf(0));
		}
		if (borrow.getRepayInterest()==null) {
			borrow.setRepayInterest(BigDecimal.valueOf(0));
		}
		
		borrow.setRepayCapital(borrow.getRepayCapital().add(BigDecimal.valueOf(Double.valueOf(borrowTender.getAccount()))));
		borrow.setRepayInterest(borrow.getRepayInterest().add(BigDecimal.valueOf(Double.valueOf(borrowTender.getInterest()))));
		borrow.setRepaymentAccount(String.valueOf(borrow.getRepayCapital().add(borrow.getRepayInterest())));
		
		this.getBorrowDaoService().update(borrow);
		
		logWander.debug("   投标金额["+borrowTender.getAccount()+"] 标余额["+borrow.getBalance()+"]");
		
		//--------直接投标成功，扣除投标金额
		
		int repaymentTime = Integer.parseInt(borrow.getTimeLimit()) / borrow.getRepaymentPeriod();

		// 取得对应回购期数的标的回购信息
		BorrowRepaymentDetail borRepDetail = null;
		List<BorrowRepaymentDetail> borRepList = borrowRepaymentDetailDaoService.queryUserBorrowList(borrow.getId());
		
		//利息
		double periodInterest = CommonUtil.getInterest(Double.valueOf(borrowTender.getAccount()),  borrow.getRepaymentPeriod(), borrow.getApr());
		
		List<BorrowRepaymentDetail> borrowRepaymentList = new ArrayList<BorrowRepaymentDetail>();
		
		int repayTime = borrow.getRepaymentPeriod();
		
		for (int r =0;r<repaymentTime;r++) {
			
			
			Date repayDT = CommonUtil.getDateAfter(new Date(), repayTime);
			String datestr = CommonUtil.getDate2String(repayDT,"yyyyMMdd");
			int dateint = Integer.parseInt(datestr);
			
			for (int i =0;i<borRepList.size();i++) {
				if (borRepList.get(i).getRepaymentDateInt().equals(dateint)) {
					borRepDetail = borRepList.get(i);
					
//					Date beginDate = CommonUtil.date2begin(new Date());
//					Date repayDate = CommonUtil.date2begin(borRepDetail.getRepaymentTime());// 还款日期
//					Long borrowDay = CommonUtil.getDateSubtractDay(beginDate,repayDate);
					
					Double payCapital = 0D;
					if (r ==(repaymentTime-1) ) {// 最后一期,还本金
						payCapital = Double.valueOf(borrowTender.getAccount());
					}
					
					borRepDetail.setCapital(String.valueOf(Double.valueOf(borRepDetail.getCapital()) + payCapital));
					BigDecimal userInterest = CommonUtil.setPriceScale(new BigDecimal(periodInterest));
					borRepDetail.setInterest(String.valueOf(Double.valueOf(borRepDetail.getInterest()) + userInterest.doubleValue()));
					borRepDetail.setRepaymentAccount(String.valueOf(Double.valueOf(borRepDetail.getCapital()) + Double.valueOf(borRepDetail.getInterest())));
					
					borrowRepaymentDetailDaoService.update(borRepDetail);
					break;
				}
			}
			borrowRepaymentList.add(borRepDetail);
			
			repayTime =  borrow.getRepaymentPeriod() + repayTime;
		}
		
		List<UserRepaymentDetail> userRepaymentDetailList = new ArrayList<UserRepaymentDetail>();
		// 投资人，每期的投资收益明细
		Listing listing_lixi = listingDao.getListing(ConstantUtil.INTEREST_CHARGES);
		for (int r =0;r<repaymentTime;r++) {
			
			BorrowRepaymentDetail borRepTemp = borrowRepaymentList.get(r);
			
			UserRepaymentDetail userRepaymentDetail = new UserRepaymentDetail();
	
			userRepaymentDetail.setUserId(user.getId());
			userRepaymentDetail.setStatus("0");// 未还款
			
			String payCapital = "0.00";
			if (r ==(repaymentTime-1) ) {// 最后一期,还本金
				payCapital = borrowTender.getAccount();
			}
			
			userRepaymentDetail.setAccount(payCapital);
			userRepaymentDetail.setInterest(CommonUtil.setPriceScale(periodInterest));
			userRepaymentDetail.setRepaymentAccount(CommonUtil.setPriceScale(Double.valueOf(payCapital) + periodInterest));
	
			userRepaymentDetail.setWaitInterest(userRepaymentDetail
					.getInterest());
			userRepaymentDetail.setWaitAccount(userRepaymentDetail
					.getAccount());
			userRepaymentDetail.setAddPersion(String.valueOf(user.getId()));
			userRepaymentDetail.setOperatorIp(ip);
			userRepaymentDetail.setBorrowPeriods(r);
			userRepaymentDetail.setBorrowId(borrow.getId());
			userRepaymentDetail.setBorrowRepaymentId(borRepTemp.getId());
			userRepaymentDetail.setRepaymentDate(borRepTemp.getRepaymentTime());
			userRepaymentDetail.setBorrowDivides(repaymentTime);
		
			
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
			
			userRepaymentDetailList.add(userRepaymentDetail);
		}
		
		//---------------------------------------------
		// 投资额
		BigDecimal tenderCapital = new BigDecimal(borrowTender.getAccount());
		BigDecimal tenderInterest = new BigDecimal(borrowTender.getInterest());
		
		
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
				
				if(0==borrow1.getAwardType()){
					//奖励现金
					
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
				}else{
					//红包奖励
					
//					userAccount.setTotal(userAccount.getTotal().add(reward));//增加总额
					userAccount.setAwardMoney(CommonUtil.setPriceScale(userAccount.getAwardMoney().add(reward)));//增加红包
					userAccountDao.updateAll(userAccount);// 【完成修改投资人账户】
					//资金记录
//					UserAccountDetail userAccountDetail11 = AccountDetailUtil.fillUserAccountDetail("award_toubiao",
//							reward, 
//							10000,
//							"借款["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
//							+ "]的红包奖励",
//							ip, userAccount);
//					
//					this.userAccountDetailDao.save(userAccountDetail11);
					
					
					UserAwardDetail uad = new UserAwardDetail();
					uad.setUserId(userAccount.getUserId());// 用户ID
					uad.setType("award_toubiao");// 类型（同资金记录）
					uad.setMoney(reward);// 操作金额
					uad.setAwardMoney(userAccount.getAwardMoney());// 奖励账户
					uad.setSignFlg(1);
					uad.setRemark("借款["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+ "]的红包奖励");// 备注
					userAwardDetailDao.save(uad);
				}
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
		String str ="现金奖励";
		if ("1".equals(borrow.getAward())) {
			// 扣除借款人的奖金
			if(0==borrow1.getAwardType()){
				boruserAccount.setAbleMoney(boruserAccount.getAbleMoney().subtract(
						reward));

				boruserAccount.setTotal(boruserAccount.getTotal().subtract(reward));
				userAccountDao.updateAll(boruserAccount);
				
				UserAccountDetail userAccountDetail5 = AccountDetailUtil.fillUserAccountDetail("award_lower", 
						reward, 
						10000, 
						"扣除借款["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
						+ "]的"+str, ip, boruserAccount);
				this.userAccountDetailDao.save(userAccountDetail5);
				
			}else{
				str="红包奖励";
				boruserAccount.setAwardMoney(boruserAccount.getAwardMoney().subtract(reward));
				userAccountDao.updateAll(boruserAccount);
				// 红包奖励账户  资金记录
				UserAwardDetail uad = new UserAwardDetail();
				uad.setUserId(boruserAccount.getUserId());// 用户ID
				uad.setType("award_lower");// 类型（同资金记录）
				uad.setMoney(reward);// 操作金额
				uad.setAwardMoney(boruserAccount.getAwardMoney());// 奖励账户
				uad.setSignFlg(-1);
				uad.setRemark("扣除借款["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+ "]的"+str);// 备注
				userAwardDetailDao.save(uad);
			}
				
			
			
			
		}
		
		// ★★★★投资人续单金额奖励 开始★★★★
		if(borrowTender.getContinueAmount() != null && !"".equals(borrowTender.getContinueAmount().trim()) && Double.valueOf(borrowTender.getContinueAmount()) > 0){
			//BigDecimal continueMoney=BigDecimal.valueOf(Double.valueOf(borrowTender.getContinueAmount()));//获取续单金额
			// 续单奖励=续单金额 * 续单奖励率 * 投资天数
//			Listing listing2 = listingDao.getListing(ConstantUtil.CONTINUE_REWARD_WANDER);
//			BigDecimal getaward=BigDecimal.valueOf(Double.parseDouble(borrowTender.getContinueAmount())*Double.parseDouble(listing2.getKeyValue())*Integer.parseInt(borrow.getTimeLimit()));
//			userAccount.setTotal(userAccount.getTotal().add(getaward));
//			userAccount.setAbleMoney(CommonUtil.setPriceScale(userAccount.getAbleMoney().add(getaward)));
			BigDecimal getaward = new BigDecimal(borrowTender.getContinueAmount()).multiply(borrow1.getContinueAwardRate());
			userAccount.setTotal(userAccount.getTotal().add(getaward));
			userAccount.setAbleMoney(CommonUtil.setPriceScale(userAccount.getAbleMoney().add(getaward)));
			userAccountDao.updateAll(userAccount);// 【完成修改投资人账户】
			
			UserAccountDetail userAccountDetail6 = AccountDetailUtil.fillUserAccountDetail("award_continued", 
					getaward, 
					10000, 
					"对借款["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+"]的续单奖励", ip, userAccount);
			this.userAccountDetailDao.save(userAccountDetail6);
		}
		
	//★★★★★★★★★★★★★★★★ 按投资比例将红包转可用★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		
		Listing listing_zhuan = listingDao.getListing(ConstantUtil.BONUS_SCALE_LIU);
		BigDecimal bonus = new BigDecimal(0);
		if(listing_zhuan.getIsEnabled()){
			bonus = CommonUtil.mul(BigDecimal.valueOf (Double.parseDouble(listing_zhuan.getKeyValue())*Double.parseDouble(borrowTender.getAccount())),BigDecimal.valueOf(Double.parseDouble(borrow.getTimeLimit())));
			if(userAccount.getAwardMoney().compareTo(bonus)==-1){
				bonus=userAccount.getAwardMoney();
			}
			if(bonus.compareTo(new BigDecimal(0))==1){
				userAccount.setAbleMoney(userAccount.getAbleMoney().add(bonus));
				userAccount.setAwardMoney(userAccount.getAwardMoney().subtract(bonus));
				this.userAccountDao.update(userAccount);
//				UserAccountDetail userAccountDetail1 = AccountDetailUtil.fillUserAccountDetail(
//						"money_for_bonus",bonus,user.getId(), "系统将标"+borrow.getName()+"获得的"+bonus+"红包变成可用金额",ip, userAccount);
//				
//				userAccountDetail1.setBorrowId(borrow.getId());//标id
//				this.userAccountDetailDao.save(userAccountDetail1);
				// 红包奖励账户  资金记录
				UserAwardDetail uad = new UserAwardDetail();
				uad.setUserId(userAccount.getUserId());// 用户ID
				uad.setType("money_for_bonus");// 类型（同资金记录）
				uad.setMoney(bonus);// 操作金额
				uad.setAwardMoney(userAccount.getAwardMoney());// 奖励账户
				uad.setSignFlg(-1);
				uad.setRemark("系统将标"+borrow.getName()+"获得的"+bonus+"红包变成可用金额");// 备注
				userAwardDetailDao.save(uad);
				
				
				
			}
		}
		//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		
		//★★★★★★★★★★★★★★★★ 发放投资人上级推荐奖励★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
			Listing listing_tui = listingDao.getListing(ConstantUtil.TUI_JIAN_LIU);
			BigDecimal tuijian = new BigDecimal(0);
			if(listing_tui.getIsEnabled()){
				UserAccount  userAccTui = this.userAccountDao.getUserAccountByUserId(user.getTgOneLevelUserId());
				userAccTui = this.userAccountDao.getForUpdate(userAccTui.getId(), userAccTui); //账户锁定
				tuijian=CommonUtil.mul(BigDecimal.valueOf (Double.parseDouble(listing_tui.getKeyValue())*Double.parseDouble(borrowTender.getAccount())),BigDecimal.valueOf(Double.parseDouble(borrow.getTimeLimit())));
				
					userAccTui.setAbleMoney(userAccTui.getAbleMoney().add(tuijian));
					userAccTui.setTotal(userAccTui.getTotal().add(tuijian));
					this.userAccountDao.update(userAccTui);
					UserAccountDetail userAccountDetail1 = AccountDetailUtil.fillUserAccountDetail(
							"tui_detail_award",bonus,user.getTgOneLevelUserId(), "获得好友"+user.getUsername()+"投资标"+borrow.getName()+"后得到奖励"+bonus,ip, userAccTui);
					
					userAccountDetail1.setBorrowId(borrow.getId());//标id
					this.userAccountDetailDao.save(userAccountDetail1);
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
