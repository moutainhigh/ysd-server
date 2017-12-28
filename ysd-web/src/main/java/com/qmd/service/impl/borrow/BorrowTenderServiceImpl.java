package com.qmd.service.impl.borrow;

import com.qmd.dao.borrow.BorrowDaoService;
import com.qmd.dao.borrow.BorrowRepaymentDetailDaoService;
import com.qmd.dao.borrow.BorrowTenderDaoService;
import com.qmd.dao.user.*;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.amount.Amount;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountDetail;
import com.qmd.mode.user.UserAwardDetail;
import com.qmd.mode.user.UserRepaymentDetail;
import com.qmd.mode.util.Listing;
import com.qmd.mode.util.MailRepayForInvestor;
import com.qmd.service.borrow.BorrowTenderService;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.util.AccountDetailUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;
import com.qmd.util.ConstantUtil.RoundType;
import com.qmd.util.Pager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("borrowTenderService")
@Transactional(rollbackFor=Exception.class)
public class BorrowTenderServiceImpl extends BaseServiceImpl<BorrowTender,Integer> implements BorrowTenderService {
	
	private Logger logRepayment = Logger.getLogger("userRepaymentLog");
	
	@Resource
	BorrowTenderDaoService borrowTenderDaoService;
	@Resource
	UserAccountDao userAccountDao;
	@Resource
	UserAccountDetailDao userAccountDetailDao;
	@Resource
	BorrowRepaymentDetailDaoService borrowRepaymentDetailDaoService;
	@Resource
	BorrowDaoService borrowDaoService;
	@Resource
	UserRepaymentDetailDao userRepaymentDetailDao;
	@Resource
	ListingDao listingDao;
	@Resource
	UserDao userDao;
	@Resource
	UserAwardDetailDao userAwardDetailDao;
	
	//CommonUtil commonUtil = new CommonUtil();
	
	
	public BorrowDaoService getBorrowDaoService() {
		return borrowDaoService;
	}
	public void setBorrowDaoService(BorrowDaoService borrowDaoService) {
		this.borrowDaoService = borrowDaoService;
	}
	public BorrowRepaymentDetailDaoService getBorrowRepaymentDetailDaoService() {
		return borrowRepaymentDetailDaoService;
	}
	public void setBorrowRepaymentDetailDaoService(
			BorrowRepaymentDetailDaoService borrowRepaymentDetailDaoService) {
		this.borrowRepaymentDetailDaoService = borrowRepaymentDetailDaoService;
	}
	public UserAccountDetailDao getUserAccountDetailDao() {
		return userAccountDetailDao;
	}
	public void setUserAccountDetailDao(UserAccountDetailDao userAccountDetailDao) {
		this.userAccountDetailDao = userAccountDetailDao;
	}
	public UserAccountDao getUserAccountDao() {
		return userAccountDao;
	}
	public void setUserAccountDao(UserAccountDao userAccountDao) {
		this.userAccountDao = userAccountDao;
	}
	public BorrowTenderDaoService getBorrowTenderDaoService() {
		return borrowTenderDaoService;
	}
	public void setBorrowTenderDaoService(BorrowTenderDaoService borrowTenderDaoService) {
		this.borrowTenderDaoService = borrowTenderDaoService;
	}
	@Override
	public List<BorrowTender> getBorrowTenderByBorrowId(Integer borrow_id) {
		// TODO Auto-generated method stub
		return this.getBorrowTenderDaoService().getBorrowTenderByBorrowId(borrow_id);
	}
	
	@Override
	public synchronized int updateBorrowDetail(List<BorrowTender> borrowTenderList,BorrowRepaymentDetail borrRepaymentDetail,Borrow borrow,List<MailRepayForInvestor> mailList,String ip) {
		
		logRepayment.debug("【还款开始】 ["+borrow.getId()+"]");
		long st = CommonUtil.getDateTimeLong();
		
		BorrowRepaymentDetail borrowRepaymentDetail = borrowRepaymentDetailDaoService.getForUpdate(borrRepaymentDetail.getId(), borrRepaymentDetail);
		
		BigDecimal mangeMoney;
		BigDecimal mangeMoneyTotal = new BigDecimal(0);
		// 奖金总额
		BigDecimal rewardAll = new BigDecimal(0);
		
		if (borrowRepaymentDetail.getStatus() != 0) {
			logRepayment.debug("【还款结束】本期已还 ["+borrow.getId()+"]");
			return 2;
		}
		
		// 修改 - 标还款明细表
		borrowRepaymentDetail.setStatus(1);
		borrowRepaymentDetail.setRepaymentYesaccount(String.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getCapital()) + CommonUtil.string2double(borrowRepaymentDetail.getInterest())));
		borrowRepaymentDetail.setRepaymentYestime(new Date());
		UserAccount userAccount1 = this.userAccountDao.getUserAccountByUserId(borrow.getUserId());
		userAccount1 = this.userAccountDao.getForUpdate(userAccount1.getId(), userAccount1); //账户锁定
		borrow = borrowDaoService.getForUpdate(borrow.getId());//标锁定
		
		if(userAccount1.getAbleMoney().doubleValue()>=(Double.parseDouble(borrowRepaymentDetail.getCapital())+Double.parseDouble(borrowRepaymentDetail.getInterest())+CommonUtil.string2double(borrowRepaymentDetail.getReminderFee()))){
			this.borrowRepaymentDetailDaoService.update(borrowRepaymentDetail);
			
			// 借款人：减少总额，减去可用金额   ，待付金额减少，待付利息减少
			
			userAccount1.setAbleMoney(BigDecimal.valueOf(userAccount1.getAbleMoney().doubleValue()-CommonUtil.string2double(borrowRepaymentDetail.getCapital())));
			//userAccount1.setTotal(BigDecimal.valueOf(userAccount1.getTotal().doubleValue()-CommonUtil.string2double(borrowRepaymentDetail.getRepaymentYesaccount())));
			userAccount1.setBorrowerCollectionCapital(userAccount1.getBorrowerCollectionCapital().subtract(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getCapital()))));
			//userAccount1.setBorrowerCollectionInterest(userAccount1.getBorrowerCollectionInterest().subtract(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getInterest()))));
			this.userAccountDao.update(userAccount1);
			
			// 资金记录---还款之本金
			UserAccountDetail userAccountDetail = AccountDetailUtil.fillUserAccountDetail("repayment_capital",
					BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getCapital())), 
					borrow.getUserId(), 
					"客户对["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
							+ "]第"+(borrowRepaymentDetail.getOrderNum()+1)+"期借款的还款(本金)", 
							ip, 
							userAccount1);
			this.userAccountDetailDao.save(userAccountDetail);
			
			userAccount1.setAbleMoney(BigDecimal.valueOf(userAccount1.getAbleMoney().doubleValue()-CommonUtil.string2double(borrowRepaymentDetail.getInterest())));
			userAccount1.setTotal(BigDecimal.valueOf(userAccount1.getTotal().doubleValue()-CommonUtil.string2double(borrowRepaymentDetail.getInterest())));
			//userAccount1.setBorrowerCollectionCapital(userAccount1.getBorrowerCollectionCapital().subtract(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getCapital()))));
			userAccount1.setBorrowerCollectionInterest(userAccount1.getBorrowerCollectionInterest().subtract(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getInterest()))));
			this.userAccountDao.update(userAccount1);
			
			// 资金记录---还款之利息
			UserAccountDetail userAccountDetail9 = AccountDetailUtil.fillUserAccountDetail("repayment_interest",
					BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getInterest())), 
					borrow.getUserId(), 
					"客户对["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
							+ "]第"+(borrowRepaymentDetail.getOrderNum()+1)+"期借款的还款(利息)", 
							ip, 
							userAccount1);
			this.userAccountDetailDao.save(userAccountDetail9);
	
			//修改BORROW表中的已还金额
			borrow.setRepaymentYesaccount(String.valueOf(CommonUtil.setPriceScale(BigDecimal.valueOf(CommonUtil.string2double(borrow.getRepaymentYesaccount()) + CommonUtil.string2double(borrowRepaymentDetail.getRepaymentYesaccount())),RoundType.roundHalfUp)));
			borrow.setUpdateTime(new Date());
			borrow.setUpdatePersion(String.valueOf(borrow.getUserId()));
			borrow.setOperatorIp(ip);
			borrow.setRepayCapital(borrow.getRepayCapital().subtract(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getCapital()))));//未还本金
			borrow.setRepayInterest(borrow.getRepayInterest().subtract(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getInterest()))));// 未还利息
			borrow.setPayedCapital(borrow.getPayedCapital().add(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getCapital()))));// 已还本金
			borrow.setPayedInterest(borrow.getPayedInterest().add(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getInterest()))));// 已还利息
			
			if ("2".equals(borrow.getType())) {// 将可投金额+还款本金
				borrow.setBalance(String.valueOf(CommonUtil.setPriceScale(BigDecimal.valueOf(CommonUtil.string2double(borrow.getBalance())).add(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getCapital()))))));
				
				//修改标的数据 
				//DecimalFormat df = new DecimalFormat("#");
				NumberFormat nf =NumberFormat.getInstance();
				nf.setMaximumFractionDigits(0);
				String shc = nf.format((1 - Double.valueOf(borrow.getBalance())/Double.valueOf(borrow.getAccount()))*100);
				borrow.setSchedule(shc);
			}
			
			boolean finishFlg = false;// 最后一次还款
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userId", borrow.getUserId());
			map.put("status", 0);
			map.put("borrowId", borrow.getId());
			List<BorrowRepaymentDetail> list = borrowRepaymentDetailDaoService.getBorrowerDetailList(map);
			//判断是否为最后一次还款:不存在代付分期
			if (list!=null && list.size() ==0) {
				borrow.setStatus(7);
				finishFlg = true;
			}
			
			this.borrowDaoService.update(borrow); // 标修改
			
			//--------------------------
			// 将还款发放给投资人
			// 1.1 根据borrowRepaymentDetail的ID，查询 user_repayment_detail 取得投资人列表
			UserRepaymentDetail userRepaymentDetail = new UserRepaymentDetail();
			userRepaymentDetail.setBorrowRepaymentId(borrowRepaymentDetail.getId());
			List<UserRepaymentDetail> repayUserList = userRepaymentDetailDao.queryUserRepaymentDetail(userRepaymentDetail);
			
			//Listing listing_lixi = listingDao.getListing(ConstantUtil.INTEREST_CHARGES);
			Listing listing_lixi = null;
			if ("1".equals(borrow.getType())) {
				listing_lixi = listingDao.getListingBysign(ConstantUtil.DEDUCTION_INTEREST_FEE_DAY);
			} else {
				listing_lixi = listingDao.getListing(ConstantUtil.INTEREST_CHARGES);
			}
			
			for (int i =0;i<repayUserList.size();i++) {
				
				// 投资人信息
				
				UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(repayUserList.get(i).getUserId());
				userAccount = this.userAccountDao.getForUpdate(userAccount.getId(), userAccount); //账户锁定
				// 增加可用金额，减少待收本金
				String invest="invest_repayment";
				String interestRe="interest_repayment";
				if(!"17".equals(borrow.getType())){
					if(repayUserList.get(i).getApplyContinueTotal()!=null){
						if("1".equals(repayUserList.get(i).getApplyContinueTotal())||Double.parseDouble(repayUserList.get(i).getApplyContinueTotal())==1){//判断是否需要返还进入续投宝
							userAccount.setContinueTotal(CommonUtil.setPriceScale(BigDecimal.valueOf(userAccount.getContinueTotal().doubleValue()+CommonUtil.string2double(repayUserList.get(i).getWaitAccount())),RoundType.roundHalfUp));
							invest="invest_repayment_continued";
						}else{
							userAccount.setAbleMoney(CommonUtil.setPriceScale(BigDecimal.valueOf(userAccount.getAbleMoney().doubleValue()+CommonUtil.string2double(repayUserList.get(i).getWaitAccount())),RoundType.roundHalfUp));
						}
					}else{
						userAccount.setAbleMoney(CommonUtil.setPriceScale(BigDecimal.valueOf(userAccount.getAbleMoney().doubleValue()+CommonUtil.string2double(repayUserList.get(i).getWaitAccount())),RoundType.roundHalfUp));
					}
					//userAccount.setTotal(CommonUtil.setPriceScale(BigDecimal.valueOf(userAccount.getTotal().doubleValue()+Double.parseDouble(repayUserList.get(i).getWaitAccount())),RoundType.roundHalfUp));
					userAccount.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital().subtract(BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitAccount()))));
					
					this.userAccountDao.update(userAccount);
				}
				// 资金记录---还款之本金
				UserAccountDetail userAccountDetail2 = AccountDetailUtil.fillUserAccountDetail(invest, 
						BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitAccount())), 
						repayUserList.get(i).getUserId(), "客户对["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
							+ "]第"+(repayUserList.get(i).getBorrowPeriods()+1)+"期借款的还款(本金)",
								ip, userAccount);
				this.userAccountDetailDao.save(userAccountDetail2);
				
				// 增加利息
				if(repayUserList.get(i).getApplyContinueTotal()!=null){
					if("1".equals(repayUserList.get(i).getApplyContinueTotal())||Double.parseDouble(repayUserList.get(i).getApplyContinueTotal())==1){//判断是否需要返还进入续投宝
						userAccount.setContinueTotal(CommonUtil.setPriceScale(BigDecimal.valueOf(userAccount.getContinueTotal().doubleValue()+CommonUtil.string2double(repayUserList.get(i).getWaitInterest())),RoundType.roundHalfUp));
						interestRe="interest_repayment_continued";
					}else{
						userAccount.setAbleMoney(CommonUtil.setPriceScale(BigDecimal.valueOf(userAccount.getAbleMoney().doubleValue()+CommonUtil.string2double(repayUserList.get(i).getWaitInterest())),RoundType.roundHalfUp));
					}
				}else{
					userAccount.setAbleMoney(CommonUtil.setPriceScale(BigDecimal.valueOf(userAccount.getAbleMoney().doubleValue()+CommonUtil.string2double(repayUserList.get(i).getWaitInterest())),RoundType.roundHalfUp));
				}
				userAccount.setTotal(CommonUtil.setPriceScale(BigDecimal.valueOf(userAccount.getTotal().doubleValue()+CommonUtil.string2double(repayUserList.get(i).getWaitInterest())),RoundType.roundHalfUp));
				userAccount.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest().subtract(BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitInterest()))));
				this.userAccountDao.update(userAccount);
				
				// 资金记录---还款之利息
				UserAccountDetail userAccountDetail4 = AccountDetailUtil.fillUserAccountDetail(interestRe,
						BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitInterest())),
						repayUserList.get(i).getUserId(), 
						"客户对["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
							+ "]第"+(repayUserList.get(i).getBorrowPeriods()+1)+"期借款的还款(利息)", 
								ip, userAccount);
				this.userAccountDetailDao.save(userAccountDetail4);
				
				// 扣除利息管理费
				mangeMoney =CommonUtil.setPriceScale( BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitInterest())*Double.valueOf(listing_lixi.getKeyValue())),RoundType.roundHalfUp);
				mangeMoneyTotal = mangeMoneyTotal.add(mangeMoney);
				if(repayUserList.get(i).getApplyContinueTotal()!=null){
					if("1".equals(repayUserList.get(i).getApplyContinueTotal())||Double.parseDouble(repayUserList.get(i).getApplyContinueTotal())==1){//判断是否需要返还进入续投宝
						userAccount.setContinueTotal(CommonUtil.setPriceScale(BigDecimal.valueOf(userAccount.getContinueTotal().doubleValue()-mangeMoney.doubleValue()),RoundType.roundHalfUp));
					}else{
						userAccount.setAbleMoney(CommonUtil.setPriceScale(BigDecimal.valueOf(userAccount.getAbleMoney().doubleValue()-mangeMoney.doubleValue()),RoundType.roundHalfUp));
					}
				}else{
					userAccount.setAbleMoney(CommonUtil.setPriceScale(BigDecimal.valueOf(userAccount.getAbleMoney().doubleValue()-mangeMoney.doubleValue()),RoundType.roundHalfUp));
				}
				userAccount.setTotal(CommonUtil.setPriceScale(BigDecimal.valueOf(userAccount.getTotal().doubleValue()+-mangeMoney.doubleValue()),RoundType.roundHalfUp));
				this.userAccountDao.update(userAccount);
				
				//资金记录--利息管理费
				UserAccountDetail userAccountDetail3 = AccountDetailUtil.fillUserAccountDetail("tender_mange",
						mangeMoney,
						ConstantUtil.ADMIN_USER_ID,
						"客户对["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
							+ "]第"+(repayUserList.get(i).getBorrowPeriods()+1)+"期借款利息收入的管理费",
						ip,
						userAccount);
				
				this.userAccountDetailDao.save(userAccountDetail3);
				
				// 对用户投标分期还款明细表做对应的变更
				UserRepaymentDetail upDetail = repayUserList.get(i);
				
				UserRepaymentDetail userPaymentDetail = userRepaymentDetailDao.getForUpdate(upDetail.getId(), upDetail);
				
				
				userPaymentDetail.setRepaymentYesaccount(repayUserList.get(i).getWaitAccount());
				userPaymentDetail.setRepaymentYesinterest(repayUserList.get(i).getWaitInterest());
				userPaymentDetail.setStatus("1");
				
				BigDecimal lastaccount = CommonUtil.setPriceScale(BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitAccount()) + CommonUtil.string2double(repayUserList.get(i).getWaitInterest()) - mangeMoney.doubleValue()),RoundType.roundHalfUp);
				
				userPaymentDetail.setAccount(lastaccount.toString());
				
				userRepaymentDetailDao.update(userPaymentDetail);
				
				// 【质押标】还款完成，发放还款完成奖金
				// 【流转标】本期完成，发放本期还款完成奖金
				if("2".equals(borrow.getAward()) && ("5".equals(borrow.getType()) || (!"5".equals(borrow.getType())&& finishFlg ))){
				// 奖金金额
					double app = Double.parseDouble(borrow.getFunds()) / 100;
					
					BigDecimal reward = BigDecimal.valueOf(0);
					
					// 流转标的奖励  奖励乘上借款天数
					if ("2".equals(borrow.getType())) {
						// 借款日期与还款日期的天数
						Long day = CommonUtil.getDateSubtractDay(CommonUtil.date2begin(repayUserList.get(i).getCreateDate()),CommonUtil.date2begin(repayUserList.get(i).getRepaymentDate()));
						reward = CommonUtil.setPriceScale(BigDecimal.valueOf(Double.parseDouble(repayUserList.get(i).getWaitAccount()) * app * day));
					} else {
						// 质押标的奖励，是整个投资本金的奖励
						Integer borrowDetailId = repayUserList.get(i).getBorrowDetailId();
						BorrowTender borrowTender = (BorrowTender)borrowTenderDaoService.getBorrowDetailById(borrowDetailId);
						
						reward =CommonUtil.setPriceScale(BigDecimal.valueOf( Double.parseDouble(borrowTender.getAccount())*app),RoundType.roundHalfUp);
					}
					
					UserAccountDetail userAccountDetail1 =new UserAccountDetail();
					
				//-------------------投标奖励红包跟现金-----------------------
					if(0==borrow.getAwardType()){
						userAccount.setTotal(userAccount.getTotal().add(reward));
						//奖励现金
					userAccount.setAbleMoney(userAccount.getAbleMoney().add(
												reward));
					userAccountDao.update(userAccount);// 【完成修改投资人账户】
					// 现金奖金日志记录
					userAccountDetail1 = AccountDetailUtil.fillUserAccountDetail("award_add",
							reward, 
							ConstantUtil.ADMIN_USER_ID,
							"借款["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
								+ "]的奖励", 
									ip, 
									userAccount);
					}else{
						
						//奖励红包
						userAccount.setAwardMoney(userAccount.getAwardMoney().add(reward));
						userAccountDao.update(userAccount);// 【完成修改投资人账户】
						// 红包奖金日志记录
//						userAccountDetail1 = AccountDetailUtil.fillUserAccountDetail("award_toubiao",
//								reward, 
//								ConstantUtil.ADMIN_USER_ID,
//								"借款["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
//									+ "]的红包奖励", 
//										ip, 
//										userAccount);
						UserAwardDetail uad = new UserAwardDetail();
						uad.setUserId(userAccount.getUserId());// 用户ID
						uad.setType("award_toubiao");// 类型（同资金记录）
						uad.setMoney(reward);// 操作金额
						uad.setAwardMoney(userAccount.getAwardMoney());// 奖励账户
						uad.setSignFlg(1);
						uad.setRemark("借款["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+ "]的红包奖励");// 备注
						userAwardDetailDao.save(uad);
						
						
					}
				//-------------------投标奖励红包跟现金 end-----------------------
					
					this.userAccountDetailDao.save(userAccountDetail1);
		
					// 统计发放的所有奖金
					rewardAll = rewardAll.add(reward);
				}
				
				Map<String,Object> userMap = new HashMap<String,Object>();
				userMap.put("id",repayUserList.get(i).getUserId());
				
//				User investor  = this.userDao.getUser(userMap);
//				
//				if (investor.getEmailStatus()==1) {//邮箱认证用户
//					MailRepayForInvestor mail = new MailRepayForInvestor();
//					mail.setUserMail(investor.getEmail());
//					mail.setUserName(investor.getUsername());
//					mail.setRepayMoney(lastaccount.toString());// 邮件中，显示时间到账的金额
//					mail.setRepayCapital(repayUserList.get(i).getWaitAccount());
//					mail.setRepayInterest(repayUserList.get(i).getInterest());
//					mailList.add(mail);
//				}
				
				
			}
			
			// ★★★★【质押标】最后一次还款，借款人的奖金发放 开始★★★★
			// ★★★★【流转标】本期完成，发放本期还款完成奖金★★★★
			if("2".equals(borrow.getAward()) && ("5".equals(borrow.getType()) || (!"5".equals(borrow.getType()) && finishFlg ))){
				if(0==borrow.getAwardType()){
				
					// 扣除借款人的现金奖励
					
					userAccount1.setAbleMoney(userAccount1.getAbleMoney().subtract(
							rewardAll));
					userAccount1.setTotal(userAccount1.getTotal().subtract(rewardAll));
					
					UserAccountDetail userAccountDetail1 = AccountDetailUtil.fillUserAccountDetail("award_lower", rewardAll, ConstantUtil.ADMIN_USER_ID,
							"借款["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
								+ "]的奖励", 
							ip, userAccount1);
					
					this.userAccountDetailDao.save(userAccountDetail1);
					this.userAccountDao.update(userAccount1);
				}else{
					// 扣除借款人的红包奖励
					
					userAccount1.setAwardMoney(userAccount1.getAwardMoney().subtract(rewardAll));//扣除红包奖励
//					userAccount1.setTotal(userAccount1.getTotal().subtract(rewardAll));//扣除总额
					
//					UserAccountDetail userAccountDetail1 = AccountDetailUtil.fillUserAccountDetail("award_lower", rewardAll, ConstantUtil.ADMIN_USER_ID,
//							"借款["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
//								+ "]的红包奖励", 
//							ip, userAccount1);
					
//					this.userAccountDetailDao.save(userAccountDetail1);
					
					UserAwardDetail uad = new UserAwardDetail();
					uad.setUserId(userAccount1.getUserId());// 用户ID
					uad.setType("money_for_bonus");// 类型（同资金记录）
					uad.setMoney(rewardAll);// 操作金额
					uad.setAwardMoney(userAccount1.getAwardMoney());// 奖励账户
					uad.setSignFlg(-1);
					uad.setRemark("借款["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+ "]的红包奖励");// 备注
					userAwardDetailDao.save(uad);
					
					
					this.userAccountDao.update(userAccount1);
					
				}
				
			}
			long ed = CommonUtil.getDateTimeLong();
			logRepayment.debug("【还款结束】 ["+borrow.getId()+"] 时间["+(ed-st)+"]");
			
			return 0;
		}else{
			logRepayment.debug("【还款结束】资金不足 ["+borrow.getId()+"]");
			return 1;
		}
		//---------------------------
		
	}
	@Override
	public void updateUserAccount(UserAccount userAccount) {
		// TODO Auto-generated method stub
		this.getUserAccountDao().update(userAccount);
	}
	@Override
	public void saveUserAccountDetail(UserAccountDetail userAccountDetail) {
		// TODO Auto-generated method stub
		this.getUserAccountDetailDao().update(userAccountDetail);
	}
	@Override
	public void updateBorrowwRepayDetail(
			BorrowRepaymentDetail borrowRepaymentDetail) {
		// TODO Auto-generated method stub
		this.getBorrowRepaymentDetailDaoService().update(borrowRepaymentDetail);
	}

	public Amount selectAllrepaymentYesinterestByUserid(Map<String,Object> map){
		return this.getBorrowTenderDaoService().selectAllrepaymentYesinterestByUserid(map);
	}
	
	public List<BorrowTender> getTenderDetailByUserid(Map<String, Object> map){
		return borrowTenderDaoService.getTenderDetailByUserid(map);
	}
	
	
	public Pager queryUncollectedDetailList(Pager pager,Map<String,Object> map){
		return borrowTenderDaoService.queryUncollectedDetailList(pager,map);
	}
	
	public List<BorrowTender> getJkmxByUserid(Map<String, Object> map){
		return borrowTenderDaoService.getJkmxByUserid(map);
	}
	
	public Pager queryJkmxList(Pager pager,Map<String,Object> map){
		return borrowTenderDaoService.queryJkmxList(pager, map);
	}

	public List<BorrowTender> getTenderDetailByMaxMoney(Map<String, Object> map){
		return borrowTenderDaoService.getTenderDetailByMaxMoney(map);
	}
	@Resource
	public void setBaseDao(BorrowTenderDaoService borrowTenderDaoService) {
		super.setBaseDao(borrowTenderDaoService);
	}
	@Override
	public Pager queryPagerListByMap(Pager pager, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return borrowTenderDaoService.queryPagerListByMap(pager,map);
	}

}
