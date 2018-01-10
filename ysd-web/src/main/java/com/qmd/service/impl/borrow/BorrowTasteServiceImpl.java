package com.qmd.service.impl.borrow;

import com.qmd.dao.borrow.BorrowDaoService;
import com.qmd.dao.borrow.BorrowTenderDaoService;
import com.qmd.dao.user.UserAccountDao;
import com.qmd.dao.user.UserAccountDetailDao;
import com.qmd.dao.user.UserAwardDetailDao;
import com.qmd.dao.user.UserDao;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountDetail;
import com.qmd.service.borrow.BorrowTasteService;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.util.AccountDetailUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConfigUtil;
import com.qmd.util.PromoteUtil;
import com.qmd.util.bean.RepaymentInfo;
import com.ysd.util.NewPayUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

@Repository("borrowTasteService")
public class BorrowTasteServiceImpl extends BaseServiceImpl<BorrowTender,Integer> implements BorrowTasteService {
	Logger log = Logger.getLogger(BorrowTasteServiceImpl.class);
	
	private Logger logPledge = Logger.getLogger("userPledgeLog");
	
	@Resource
	BorrowDaoService borrowDaoService;
	@Resource
	UserAccountDetailDao userAccountDetailDao;
	@Resource
	UserAccountDao userAccountDao;
	@Resource
	BorrowTenderDaoService borrowTenderDaoService;
	@Resource
	UserDao userDao;
	@Resource
	UserAwardDetailDao userAwardDetailDao;
	public int borrowInvestDo(User user,BorrowTender borrowTender,String ip){

		
		logPledge.debug("【体验标开始】"+user.getUsername()+",["+borrowTender.getBorrowId()+"] 体验金["+borrowTender.getTasteAmount()+"]");
		long st = CommonUtil.getDateTimeLong();
		
		
		UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(user.getId());
		userAccount = this.userAccountDao.getForUpdate(userAccount.getId(), userAccount); //账户锁定
		Borrow borrow = this.borrowDaoService.getBorrowById(borrowTender.getBorrowId());
		borrow = this.borrowDaoService.getForUpdate(borrow.getId());//标锁定

		
		
		if (borrow.getStatus() != 1) {
			logPledge.debug("【体验标结束】"+user.getUsername()+"投标失败,标状态不对["+borrow.getStatus()+"]");
			return 4;// 标已满
		}
		
		if (Double.parseDouble(borrow.getBalance()) <=0) {
			logPledge.debug("【体验标结束】"+user.getUsername()+"投标失败,标已满["+borrow.getBalance()+"]");
			return 1;// 标已满
		}
		
		// 账户可用金额少于投标金额
		if (userAccount.getTasteMoney().doubleValue() < Double.parseDouble(borrowTender.getTasteAmount())) {
			logPledge.debug("【体验标结束】"+user.getUsername()+"投标失败,体验金不足["+userAccount.getTasteMoney()+"]");
			return 2;// 余额不足
		}
		
		borrowTender.setAbleAmount("0");
		borrowTender.setContinueAmount("0");
		borrowTender.setHongbaoAmount("0");//使用红包金额
		
		//投标金额大于标剩余金额
		if(Double.parseDouble(borrowTender.getMoney()) > Double.parseDouble(borrow.getBalance())){
			borrowTender.setAccount(borrow.getBalance());
			borrowTender.setStatus("2");//部分通过
		}else{
			borrowTender.setAccount(borrowTender.getMoney());
			borrowTender.setStatus("1");//全部通过
			
		}
		borrowTender.setTasteAmount(borrowTender.getAccount());
		
		RepaymentInfo repaymentInfo = PromoteUtil.promotePlan(Integer.parseInt(borrow
				.getIsday()), Integer.parseInt(borrow.getStyle()),
				Integer.parseInt(borrow.getTimeLimit()), borrow
						.getDivides(),
				new BigDecimal(borrowTender.getAccount()), new BigDecimal(
						borrow.getApr()));
		
		String interest = repaymentInfo.getInterest().toString();
		borrowTender.setInterest(interest);
		borrowTender.setRepaymentAccount(String.valueOf(Double.valueOf(borrowTender.getAccount()) + Double.valueOf(interest)));
		borrowTender.setRepaymentYesaccount("0");
		borrowTender.setWaitAccount(borrowTender.getAccount());
		borrowTender.setRepaymentYesinterest("0");
		borrowTender.setWaitInterest(interest);
		borrowTender.setUserId(user.getId());
		borrowTender.setAddPersion(user.getUsername());
		borrowTender.setOperatorIp(ip);
		this.borrowTenderDaoService.save(borrowTender);
	
		// 体验金修改及记录
		if (Double.valueOf(borrowTender.getTasteAmount()) > 0) {
			BigDecimal tzMoney = new BigDecimal( borrowTender.getTasteAmount());
			
			//修改用户可用金额
			userAccount.setTasteMoney(userAccount.getTasteMoney().subtract(tzMoney));
			this.userAccountDao.update(userAccount);
			
			//资金记录
			UserAccountDetail userAccountDetail = AccountDetailUtil.fillUserAccountDetail("tender_taste",
					tzMoney,
					10000, "体验金投资["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+"]", ip, userAccount);
			this.userAccountDetailDao.save(userAccountDetail);
			
		}
		
		//修改标的数据 
		DecimalFormat df = new DecimalFormat("#");
		borrow.setBalance(df.format(Double.valueOf(borrow.getBalance()) - Double.valueOf(borrowTender.getAccount())));
		NumberFormat nf =NumberFormat.getInstance();
		nf.setMaximumFractionDigits(0);
		//nf.setMaximumFractionDigits(2);
		String shc = nf.format((1 - Double.valueOf(borrow.getBalance())/Double.valueOf(borrow.getAccount()))*100);
		//borrow.setSchedule(String.valueOf(1-(Double.valueOf(borrow.getBalance())/Double.valueOf(borrow.getAccount()))));
		if(shc.equals("100")&& Double.valueOf(borrow.getBalance()) != 0){
			borrow.setSchedule("99");
		}else{
			borrow.setSchedule(shc);
		}
		if(Double.valueOf(borrow.getBalance()) == 0){
			borrow.setStatus(5);
		}
		if(borrow.getTenderTimes()==null||"".equals(borrow.getTenderTimes())) {
			borrow.setTenderTimes("0");
		}
		borrow.setTenderTimes(String.valueOf(Integer.parseInt(borrow.getTenderTimes()) + 1));
		this.borrowDaoService.update(borrow);
		
		long ed = CommonUtil.getDateTimeLong();
		logPledge.debug("   投标金额["+borrowTender.getMoney()+"] 体验金投["+borrowTender.getTasteAmount()+"]");
		logPledge.debug("【体验标结束】"+user.getUsername()+" 时间["+(ed-st)+"]");
		return 0;
	
	}
}
