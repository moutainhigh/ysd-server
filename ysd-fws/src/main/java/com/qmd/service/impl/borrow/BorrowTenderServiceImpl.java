package com.qmd.service.impl.borrow;

import com.qmd.dao.borrow.BorrowDaoService;
import com.qmd.dao.borrow.BorrowRepaymentDetailDaoService;
import com.qmd.dao.borrow.BorrowTenderDaoService;
import com.qmd.dao.user.UserAccountDao;
import com.qmd.dao.user.UserAccountDetailDao;
import com.qmd.dao.user.UserDao;
import com.qmd.dao.user.UserRepaymentDetailDao;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.mode.borrow.BorrowTemp;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountDetail;
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
import com.ysd.biz.PushForBiz;
import com.ysd.ipyy.IPYYSMS;
import com.ysd.ipyy.IPYYSMSResult;
import org.apache.commons.lang3.StringUtils;
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
	
	
//	BorrowDetailAgileStep
	
	
	
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
		
		if(borrow.getBorrowMoney().doubleValue()>=(Double.parseDouble(borrowRepaymentDetail.getCapital())+Double.parseDouble(borrowRepaymentDetail.getInterest())+CommonUtil.string2double(borrowRepaymentDetail.getReminderFee()))){
			this.borrowRepaymentDetailDaoService.update(borrowRepaymentDetail);
			
			// 借款人：减少总额，减去可用金额   ，待付金额减少，待付利息减少
			
			userAccount1.setAbleMoney(BigDecimal.valueOf(userAccount1.getAbleMoney().doubleValue()-CommonUtil.string2double(borrowRepaymentDetail.getCapital())));
			//userAccount1.setTotal(BigDecimal.valueOf(userAccount1.getTotal().doubleValue()-CommonUtil.string2double(borrowRepaymentDetail.getRepaymentYesaccount())));
			userAccount1.setBorrowerCollectionCapital(userAccount1.getBorrowerCollectionCapital().subtract(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getCapital()))));
			//userAccount1.setBorrowerCollectionInterest(userAccount1.getBorrowerCollectionInterest().subtract(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getInterest()))));
			this.userAccountDao.update(userAccount1);
			
			
			userAccount1.setAbleMoney(BigDecimal.valueOf(userAccount1.getAbleMoney().doubleValue()-CommonUtil.string2double(borrowRepaymentDetail.getInterest())));
			userAccount1.setTotal(BigDecimal.valueOf(userAccount1.getTotal().doubleValue()-CommonUtil.string2double(borrowRepaymentDetail.getInterest())));
			//userAccount1.setBorrowerCollectionCapital(userAccount1.getBorrowerCollectionCapital().subtract(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getCapital()))));
			userAccount1.setBorrowerCollectionInterest(userAccount1.getBorrowerCollectionInterest().subtract(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getInterest()))));
			this.userAccountDao.update(userAccount1);
			
	
			//修改BORROW表中的已还金额
			borrow.setRepaymentYesaccount(String.valueOf(CommonUtil.setPriceScale(BigDecimal.valueOf(CommonUtil.string2double(borrow.getRepaymentYesaccount()) + CommonUtil.string2double(borrowRepaymentDetail.getRepaymentYesaccount())),RoundType.roundHalfUp)));
			borrow.setUpdateTime(new Date());
			borrow.setUpdatePersion(String.valueOf(borrow.getUserId()));
			borrow.setOperatorIp(ip);
			borrow.setRepayCapital(borrow.getRepayCapital().subtract(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getCapital()))));//未还本金
			borrow.setRepayInterest(borrow.getRepayInterest().subtract(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getInterest()))));// 未还利息
			borrow.setPayedCapital(borrow.getPayedCapital().add(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getCapital()))));// 已还本金
			borrow.setPayedInterest(borrow.getPayedInterest().add(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getInterest()))));// 已还利息
			borrow.setBorrowMoney( CommonUtil.setPriceScale(borrow.getBorrowMoney().subtract(new BigDecimal(Double.parseDouble(borrowRepaymentDetail.getCapital())+Double.parseDouble(borrowRepaymentDetail.getInterest())+CommonUtil.string2double(borrowRepaymentDetail.getReminderFee())))));
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
//				listing_lixi = listingDao.getListingBysign(ConstantUtil.DEDUCTION_INTEREST_FEE_DAY);
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
				
				// 资金记录---还款之本金
				UserAccountDetail userAccountDetail2 = AccountDetailUtil.fillUserAccountDetail(invest, 
						BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitAccount())), 
						repayUserList.get(i).getUserId(), "客户对["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
							+ "]第"+(repayUserList.get(i).getBorrowPeriods()+1)+"期借款的还款(本金)",
								ip, userAccount);
				this.userAccountDetailDao.save(userAccountDetail2);
				
				Map<String,Object> userMap = new HashMap<String,Object>();
				userMap.put("id",userAccount.getUserId());
				
				
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
				
				String time = CommonUtil.getDate2String(new Date(), "yyyy-MM-dd");
				User investor  = this.userDao.getUser(userMap);
				if(StringUtils.isNotEmpty(investor.getDeviceToken())){
//					try {PushUtil push = new PushUtil();
//					String message="项目还款第"+(repayUserList.get(i).getBorrowPeriods()+1)+"期：本金："+BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitAccount()))+"元，利息："+BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitInterest()))+"元。";
					
					/*String message="您投资的\""+borrow.getName()+"\"项目已于"+time+"还款，还款本金为"+BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitAccount()))+"元，利息为"+BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitInterest()))+"元。";
					if(investor.getDeviceToken().length() ==44){
							push.pushAndoridUnicast(investor.getDeviceToken(), "还款通知", "还款通知", message,"com.rongxun.JingChuBao.Activities.RepayMentActivity");
						}else if(investor.getDeviceToken().length() ==64){
							push.pushIosUnicast(investor.getDeviceToken(), message, "","hk");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
                    UserRepaymentDetail userRepaymentDetail1 = repayUserList.get(i);
                    BigDecimal waitMoney = BigDecimal.valueOf(CommonUtil.string2double(userRepaymentDetail1.getWaitAccount()));
                    BigDecimal waitInterest = BigDecimal.valueOf(CommonUtil.string2double(userRepaymentDetail1.getWaitInterest()));
                    PushForBiz.pushWhenBorrow(String.valueOf(investor.getId()), borrow.getName(), time, waitMoney, waitInterest);
                }
				//短信提醒
				String message="您投 资的\""+borrow.getName()+"\"项目已于"+time+"还款，还款本金为"+BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitAccount()))+"元，利息为"+BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitInterest()))+"元。";
                String mobile = repayUserList.get(i).getUsername();

                IPYYSMSResult ipyysmsResult = IPYYSMS.sendSMS(mobile, message);
                if (!ipyysmsResult.ok()) {
                    logRepayment.error("还款短信提醒发送失败:" + ipyysmsResult.getMessage());
                } else {
                    logRepayment.info("还款短信提醒成功");
                }

                /*HttpUtil sms = new HttpUtil();
				try {
					sms.sendSms(message, repayUserList.get(i).getUsername());
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				
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
					
		
					// 统计发放的所有奖金
					rewardAll = rewardAll.add(reward);
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
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 灵活宝还款  
	 * @param borrowRepaymentDetail_id
	 * @param mailList
	 * @param ip
	 * @return 0 还款成功，1资金不足，2本期已还
	 */
	@Override
	public synchronized int updateBorrowAgile(Integer borrowRepaymentDetail_id,List<MailRepayForInvestor> mailList,String ip) {
		
		long st = CommonUtil.getDateTimeLong();
		
		BorrowRepaymentDetail borrowRepaymentDetail = borrowRepaymentDetailDaoService.getForUpdate(borrowRepaymentDetail_id);
		
		logRepayment.debug("【灵活宝最终回购开始】 ["+borrowRepaymentDetail.getBorrowId()+"]");
		
		if (borrowRepaymentDetail==null || borrowRepaymentDetail.getStatus() != 0) {
			logRepayment.debug("【还款结束】本期已还 ["+borrowRepaymentDetail.getBorrowId()+"]");
			return 2;
		}
		
		Borrow borrow = borrowDaoService.getForUpdate(borrowRepaymentDetail.getBorrowId());
		
		if (borrow.getStatus()!=1 && borrow.getStatus()!=3) {
			logRepayment.debug("【还款结束】本期已还 bid=["+borrow.getId()+"] stu=["+borrow.getStatus()+"]");
			return 2;
		}
		
//		List<BorrowRepaymentDetail> borrowRepaymentDetailList = borrowRepaymentDetailDaoService.queryUserBorrowList(bid);
//		if (borrowRepaymentDetailList==null ||borrowRepaymentDetailList.size()<1) {
//			logRepayment.debug("【还款结束】本期已还 bid=["+borrow.getId()+"] stu=["+borrow.getStatus()+"]");
//			return 2;
//		}
//		
//		BorrowRepaymentDetail borrowRepaymentDetail = borrowRepaymentDetailList.get(0);// 灵活宝只有一期，不会有多期
//		
//		if (borrowRepaymentDetail.getStatus() != 0) {
//			logRepayment.debug("【还款结束】本期已还 ["+borrow.getId()+"]");
//			return 2;
//		}
		
		// 修改 - 标还款明细表
		borrowRepaymentDetail.setStatus(1);
		borrowRepaymentDetail.setRepaymentYesaccount(String.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getCapital()) + CommonUtil.string2double(borrowRepaymentDetail.getInterest())));
		borrowRepaymentDetail.setRepaymentYestime(new Date());
		UserAccount userAccount1 = this.userAccountDao.getUserAccountByUserId(borrow.getUserId());// 借款人账户
		userAccount1 = this.userAccountDao.getForUpdate(userAccount1.getId(), userAccount1); //账户锁定
		
		if (userAccount1.getAbleMoney().doubleValue() < Double.parseDouble(borrowRepaymentDetail.getCapitalNormal())) {
			return 1;
		}

		//修改还款记录
		this.borrowRepaymentDetailDaoService.update(borrowRepaymentDetail);
		
		// 借款人：减少总额，减去可用金额   ，待付金额减少，待付利息减少
		userAccount1.setAbleMoney(BigDecimal.valueOf(userAccount1.getAbleMoney().doubleValue()-(CommonUtil.string2double(borrowRepaymentDetail.getCapitalNormal()) -CommonUtil.string2double(borrowRepaymentDetail.getCapitalTaste()))));
		userAccount1.setBorrowerCollectionCapital(userAccount1.getBorrowerCollectionCapital().subtract(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getCapitalNormal()))));
		this.userAccountDao.update(userAccount1);
		
		
		//修改BORROW表中的已还金额
		borrow.setRepaymentYesaccount(String.valueOf(CommonUtil.setPriceScale(BigDecimal.valueOf(CommonUtil.string2double(borrow.getRepaymentYesaccount()) + CommonUtil.string2double(borrowRepaymentDetail.getRepaymentYesaccount())),RoundType.roundHalfUp)));
		borrow.setUpdateTime(new Date());
		borrow.setUpdatePersion(String.valueOf(borrow.getUserId()));
		borrow.setOperatorIp(ip);
		borrow.setRepayCapital(borrow.getRepayCapital().subtract(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getCapital()))));//未还本金
		borrow.setRepayInterest(borrow.getRepayInterest().subtract(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getInterest()))));// 未还利息
		borrow.setPayedCapital(borrow.getPayedCapital().add(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getCapital()))));// 已还本金
		borrow.setPayedInterest(borrow.getPayedInterest().add(BigDecimal.valueOf(CommonUtil.string2double(borrowRepaymentDetail.getInterest()))));// 已还利息
		borrow.setStatus(7);// 还款后，项目结束
		
		this.borrowDaoService.update(borrow); // 标修改
		
		// 将还款发放给投资人
		// 1.1 根据borrowRepaymentDetail的ID，查询 user_repayment_detail 取得投资人列表
		UserRepaymentDetail userRepaymentDetail = new UserRepaymentDetail();
		userRepaymentDetail.setBorrowRepaymentId(borrowRepaymentDetail.getId());
		userRepaymentDetail.setZqzrStatus(1);// 转让成功-sqlmap 查询‘非转让成功’的-sf
		List<UserRepaymentDetail> repayUserList = userRepaymentDetailDao.queryUserRepaymentDetail(userRepaymentDetail);
		
		for (int i =0;i<repayUserList.size();i++) {
			
			UserRepaymentDetail upDetail = repayUserList.get(i);
			
			if(CommonUtil.string2double(upDetail.getWaitCapitalNormal())<=0) {
				continue;
			}
			
			// 投资人信息
			UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(repayUserList.get(i).getUserId());
			userAccount = this.userAccountDao.getForUpdate(userAccount.getId(), userAccount); //账户锁定
			
			// 增加可用金额，减少待收本金
			String invest="invest_repayment";
			
			userAccount.setAbleMoney(CommonUtil.setPriceScale(BigDecimal.valueOf(userAccount.getAbleMoney().doubleValue()+CommonUtil.string2double(repayUserList.get(i).getWaitCapitalNormal())),RoundType.roundHalfUp));
			
			userAccount.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital().subtract(BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitCapitalNormal()))));
			
			userAccount.setDirectMoney(CommonUtil.defaultBigDecimal(userAccount.getDirectMoney()).add(BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitCapitalDirect()))));
			userAccount.setInvestorCollectionDirect(CommonUtil.defaultBigDecimal(userAccount.getInvestorCollectionDirect()).subtract(BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitCapitalDirect()))));
			
			userAccount.setTasteMoney(CommonUtil.defaultBigDecimal(userAccount.getTasteMoney()).add(BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitCapitalTaste()))));
			userAccount.setInvestorCollectionTaste(CommonUtil.defaultBigDecimal(userAccount.getInvestorCollectionTaste()).subtract(BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitCapitalTaste()))));
			
			this.userAccountDao.update(userAccount);
			
			// 资金记录---还款之本金
			UserAccountDetail userAccountDetail2 = AccountDetailUtil.fillUserAccountDetail(invest, 
					BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitAccount())), 
					repayUserList.get(i).getUserId(), "客户对["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
						+ "]第"+(repayUserList.get(i).getBorrowPeriods())+"期借款的还款(本金)",
							ip, userAccount);
			this.userAccountDetailDao.save(userAccountDetail2);
			
			//###灵活宝只还本金，利息每天自动还款（利息收入还款时无记录）
			
			// 对用户投标分期还款明细表做对应的变更
			
			UserRepaymentDetail userPaymentDetail = userRepaymentDetailDao.getForUpdate(upDetail.getId(), upDetail);
			
			userPaymentDetail.setRepaymentYesaccount(repayUserList.get(i).getWaitAccount());
			userPaymentDetail.setRepaymentYesinterest(repayUserList.get(i).getWaitInterest());
			userPaymentDetail.setStatus("1");
			BigDecimal lastaccount = CommonUtil.setPriceScale(BigDecimal.valueOf(CommonUtil.string2double(repayUserList.get(i).getWaitAccount()) + CommonUtil.string2double(repayUserList.get(i).getWaitInterest())),RoundType.roundHalfUp);
			
			userPaymentDetail.setAccount(lastaccount.toString());
			
			userRepaymentDetailDao.update(userPaymentDetail);
			
			Map<String,Object> userMap = new HashMap<String,Object>();
			userMap.put("id",repayUserList.get(i).getUserId());
			
			User investor  = this.userDao.getUser(userMap);
			
			if (investor.getEmailStatus()==1) {//邮箱认证用户
				MailRepayForInvestor mail = new MailRepayForInvestor();
				mail.setUserMail(investor.getEmail());
				mail.setUserName(investor.getUsername());
				mail.setRepayMoney(repayUserList.get(i).getAccount());
				mail.setRepayCapital(repayUserList.get(i).getWaitAccount());
				mail.setRepayInterest(repayUserList.get(i).getInterest());
				mailList.add(mail);
			}
			
			//--灵活宝信息更新----
			
			BigDecimal aMoney = new BigDecimal(upDetail.getWaitCapitalNormal());
			
			BorrowTender bt = borrowTenderDaoService.getById(upDetail.getBorrowDetailId());
			bt.setAgileMoney(bt.getAgileMoney().subtract(aMoney));
			Map<String, Object> btmap = new HashMap<String, Object>();
			btmap.put("agileMoney", bt.getAgileMoney());
			btmap.put("agileFinalDate", new Date());
			btmap.put("agileStatus", 9);
			borrowTenderDaoService.upateBorrowDetailAgile(btmap);
			
			
		}
		
		long ed = CommonUtil.getDateTimeLong();
		logRepayment.debug("【还款结束】 ["+borrow.getId()+"] 时间["+(ed-st)+"]");
		
		return 0;
	}
	
	/**
	 * 逾期项目还款  
	 * @param borrowId
	 * @param  lateAccountPaying null足额还款，非空为有损还款
	 * @param mailList
	 * @param ip
	 * @return 0 还款成功，1资金不足，2本期已还 3 状态不对
	 */
	@Override
	public synchronized int updateBorrowLate(Integer borrowId,BigDecimal lateAccountPaying,List<MailRepayForInvestor> mailList,String ip) {
		
		logRepayment.debug("【逾期还款开始】");		
		
		long st = CommonUtil.getDateTimeLong();
		
		Borrow borrow = borrowDaoService.getForUpdate(borrowId);
//		if (borrow.getStatus() !=3 || borrow.getLateStatus()!=2) {
//			return 3;
//		}
		double lostRate = 0;
		boolean isFullPaid = false;//全额还款：true有损 ，false全额
//		if (lateAccountPaying==null) {
//			isFullPaid = true;
//			borrow.setLateAccountPaid(borrow.getLateAccount());
//		} else {
//			borrow.setLateAccountPaid(lateAccountPaying);
//			lostRate = lateAccountPaying.doubleValue()/borrow.getLateAccount().doubleValue();
//		}
//		borrow.setLateDateEnd(new Date());
		
		UserAccount userAccountBorrower = this.userAccountDao.getUserAccountByUserId(borrow.getUserId());// 借款人账户
		userAccountBorrower = this.userAccountDao.getForUpdate(userAccountBorrower.getId()); //账户锁定
		
//		BigDecimal payMoney = borrow.getLateAccountPaid().add(borrow.getLatePenalty());
		
//		if (userAccountBorrower.getAbleMoney().compareTo(payMoney) == -1) {
//			return 1;
//		}
//		
//		borrow.setRepaymentYesaccount(String.valueOf(CommonUtil.setPriceScale((BigDecimal.valueOf(CommonUtil.string2double(borrow.getRepaymentYesaccount())).add(payMoney)),RoundType.roundHalfUp)));
		borrow.setUpdateTime(new Date());
		borrow.setUpdatePersion(String.valueOf(borrow.getUserId()));
		borrow.setOperatorIp(ip);
//		borrow.setRepayCapital(borrow.getRepayCapital().subtract(borrow.getLateAccount()));	//未还本金
//		borrow.setPayedCapital(borrow.getPayedCapital().add(borrow.getLateAccount()));	// 已还本金
		borrow.setStatus(7);	// 还款后，项目结束
		
		//---投资人收款------------------------------------------------------------------------------------------------------------------------------------------
		Map<String,Object> map5 = new HashMap<String,Object>();
		map5.put("userId", borrow.getUserId());
		int[] array = {3};
		map5.put("status", array);
		map5.put("lateStatus", 2);
		List<BorrowTemp> tenderList = borrowTenderDaoService.getJkmxByUserid(map5);
		
		BigDecimal tenderPaidTotal = BigDecimal.ZERO;
		
		for (BorrowTemp tender:tenderList) {
			
			//全额还款：true全额 ，false有损
			if (isFullPaid) {
				tender.setLateAccountPaid(tender.getLateAccount());
			} else {
				tender.setLateAccountPaid(CommonUtil.setPriceScale2BigDecimal(lostRate * tender.getLateAccount().doubleValue()));
			}
			tenderPaidTotal = tenderPaidTotal.add(tender.getLateAccountPaid());
			
			borrowTenderDaoService.updateBorrowTemp(tender);
				
			UserAccount userAccountTender = this.userAccountDao.getUserAccountByUserId(tender.getUserId());// 投资人账户
			userAccountTender = this.userAccountDao.getForUpdate(userAccountTender.getId()); //账户锁定
			
			userAccountTender.setTotal(userAccountTender.getTotal().add(tender.getLatePenalty()));
			userAccountTender.setAbleMoney(userAccountTender.getAbleMoney().add(tender.getLatePenalty()));
			this.userAccountDao.update(userAccountTender);
			
			// 资金记录---罚息
			UserAccountDetail userAccountDetail5 = AccountDetailUtil.fillUserAccountDetail("tender_repayment_late_penalty",
					tender.getLatePenalty(), 
					tender.getUserId(), 
					"客户对["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
							+ "]逾期罚息入账", 
							ip, 
							userAccountTender);
			this.userAccountDetailDao.save(userAccountDetail5);
			
			if (!isFullPaid) {//有损还款
				userAccountTender.setTotal(userAccountTender.getTotal().subtract(tender.getLateAccount().subtract(tender.getLateAccountPaid())));
			}
			userAccountTender.setAbleMoney(userAccountTender.getAbleMoney().add(tender.getLateAccountPaid()));
			userAccountTender.setInvestorCollectionCapital(userAccountTender.getInvestorCollectionCapital().subtract(tender.getLateAccount()));
			this.userAccountDao.update(userAccountTender);
			
			// 资金记录---本金
			UserAccountDetail userAccountDetail6 = AccountDetailUtil.fillUserAccountDetail("tender_repayment_late_capital",
					tender.getLateAccountPaid(), 
					tender.getUserId(), 
					"客户对["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
							+ "]逾期本金还款入账", 
							ip, 
							userAccountTender);
			this.userAccountDetailDao.save(userAccountDetail6);
			
			
			Map<String,Object> userMap = new HashMap<String,Object>();
			userMap.put("id",tender.getUserId());
			
			User investor  = this.userDao.getUser(userMap);
			
			if (investor.getEmailStatus()==1) {//邮箱认证用户
				MailRepayForInvestor mail = new MailRepayForInvestor();
				mail.setUserMail(investor.getEmail());
				mail.setUserName(investor.getUsername());
				mail.setRepayMoney(tender.getLateAccountPaid().add(tender.getLatePenalty()).toString());
				mail.setRepayCapital(tender.getLateAccountPaid().toString());
				mail.setRepayInterest(tender.getLatePenalty().toEngineeringString());
				mailList.add(mail);
			}
		}
		
		//---借款人付款--------------------------------------------------------------------------------------------------------
		
		// 借款人：减少总额，减去可用金额   ，待付金额减少，待付利息减少
//		userAccountBorrower.setTotal(userAccountBorrower.getTotal().subtract(borrow.getLatePenalty()));//扣除罚息
//		userAccountBorrower.setAbleMoney(userAccountBorrower.getAbleMoney().subtract(borrow.getLatePenalty()));
		this.userAccountDao.update(userAccountBorrower);
				
//		// 资金记录---罚息
//		UserAccountDetailAgency userAccountDetail1 = AccountDetailAgencyUtil.fillUserAccountDetail("borrow_repayment_late_penalty",
//				borrow.getLatePenalty(), 
//				borrow.getUserId(), 
//				"客户对["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
//						+ "]逾期罚息扣除", 
//						ip, 
//						userAccountBorrower);
//		this.userAccountDetailAgencyDao.save(userAccountDetail1);
//		
		if (!isFullPaid) {//有损还款
//			userAccountBorrower.setTotal(userAccountBorrower.getTotal().add(borrow.getLateAccount().subtract(borrow.getLateAccountPaid())));
		}
//		userAccountBorrower.setAbleMoney(userAccountBorrower.getAbleMoney().subtract(borrow.getLateAccountPaid())); //还本金
//		userAccountBorrower.setBorrowerCollectionCapital(userAccountBorrower.getBorrowerCollectionCapital().subtract(borrow.getLateAccount())); //
		this.userAccountDao.update(userAccountBorrower);
				
		// 资金记录---逾期本金
//		UserAccountDetailAgency userAccountDetail2 = AccountDetailAgencyUtil.fillUserAccountDetail("borrow_repayment_late_capital",
//				borrow.getLateAccountPaid(), 
//				borrow.getUserId(), 
//				"客户对["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())
//						+ "]逾期本金还款扣除", 
//						ip, 
//						userAccountBorrower);
//		this.userAccountDetailAgencyDao.save(userAccountDetail2);
		
		borrowDaoService.update(borrow);
		
		//------------------------------------------------------------------------------------------------------
		
		long ed = CommonUtil.getDateTimeLong();
		logRepayment.debug("【逾期还款结束】 ["+borrow.getId()+"] 时间["+(ed-st)+"]");
		
		return 0;
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public Pager queryUncollectedDetailList(Pager pager,Map<String,Object> map){
		return borrowTenderDaoService.queryUncollectedDetailList(pager,map);
	}
	
	public List<BorrowTemp> getJkmxByUserid(Map<String, Object> map){
		return borrowTenderDaoService.getJkmxByUserid(map);
	}
	
	public Pager queryJkmxList(Pager pager,Map<String,Object> map){
		return borrowTenderDaoService.queryJkmxList(pager, map);
	}

	@Resource
	public void setBaseDao(BorrowTenderDaoService borrowTenderDaoService) {
		super.setBaseDao(borrowTenderDaoService);
	}

}
