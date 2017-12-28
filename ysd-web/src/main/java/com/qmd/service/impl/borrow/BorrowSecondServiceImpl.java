package com.qmd.service.impl.borrow;

import com.qmd.dao.borrow.BorrowDaoService;
import com.qmd.dao.borrow.BorrowTenderDaoService;
import com.qmd.dao.user.UserAccountDao;
import com.qmd.dao.user.UserAccountDetailDao;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountDetail;
import com.qmd.service.borrow.BorrowSecondService;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.util.AccountDetailUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConfigUtil;
import com.ysd.util.NewPayUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * BorrowSecondServiceImpl 秒标方法的Service实现类
 *
 */
@Repository("BorrowSecondService")
public class BorrowSecondServiceImpl extends BaseServiceImpl<BorrowTender,Integer> implements BorrowSecondService {
	Logger log = Logger.getLogger(BorrowSecondServiceImpl.class);
	
	private Logger logSecond = Logger.getLogger("userSecondLog");
	
	@Resource
	UserAccountDao userAccountDao;
	@Resource
	UserAccountDetailDao userAccountDetailDao;
	@Resource
	BorrowDaoService borrowDaoService;
	@Resource
	BorrowTenderDaoService borrowTenderDaoService;
	
	@Override
	public synchronized int borrowInvestDo(User user,BorrowTender borrowTender,String ip) {
		
		logSecond.debug("【秒标开始】"+user.getUsername()+",["+borrowTender.getBorrowId()+"] 可用["+borrowTender.getAbleAmount()+"] 续投["+borrowTender.getContinueAmount()+"]");
		long st = CommonUtil.getDateTimeLong();
		
		UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(user.getId());
		userAccount = this.userAccountDao.getForUpdate(userAccount.getId(), userAccount); //账户锁定
		Borrow borrow = this.borrowDaoService.getBorrowById(borrowTender.getBorrowId());
		borrow = this.borrowDaoService.getForUpdate(borrow.getId());//标锁定
		if (Double.parseDouble(borrow.getBalance())<=0) {
			
			logSecond.debug("【秒标结束】"+user.getUsername()+"投标失败,标已满["+borrow.getBalance()+"]");
			return 1;
		}
		
		// 账户可用金额少于投标金额
		if (userAccount.getAbleMoney().doubleValue() < Double.parseDouble(borrowTender.getAbleAmount())) {
			logSecond.debug("【秒标结束】"+user.getUsername()+"投标失败,余额不足["+userAccount.getAbleMoney()+"]");
			return 2;// 余额不足
		}
		
		// 账户可用金额少于投标金额
		if (userAccount.getContinueTotal().doubleValue() < Double.parseDouble(borrowTender.getContinueAmount())) {
			logSecond.debug("【秒标结束】"+user.getUsername()+"投标失败,续投余额不足["+userAccount.getContinueTotal()+"]");
			return 3;// 余额不足
		}
		
		//投标金额大于标剩余金额
		if(Double.parseDouble(borrowTender.getMoney()) > Double.parseDouble(borrow.getBalance())){
			//borrowTender.setMoney(borrow.getBalance());
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
		//修改实际投标金额`
		String interest = String.valueOf((Double.valueOf(borrowTender.getAccount()) * borrow.getApr()/100));
		//PaymentView pv = interestCalUtil.payback(Double.valueOf(borrowTender.getAccount()),Double.valueOf(borrow.getAccount()), borrow.getApr()/100, Integer.valueOf(borrow.getTimeLimit()),borrow.getBorStages());
		//String interest =interest;
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
			this.userAccountDao.update(userAccount);
			
			//资金记录
			UserAccountDetail userAccountDetail = AccountDetailUtil.fillUserAccountDetail("tender",
					BigDecimal.valueOf(Double.valueOf(borrowTender.getAbleAmount())),
					10000, "投标冻结资金["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+"]", ip, userAccount);
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
		borrow.setSchedule(shc);
		if(Double.valueOf(borrow.getBalance()) == 0){
			borrow.setStatus(5);
		}
		if(borrow.getTenderTimes()==null||"".equals(borrow.getTenderTimes())) {
			borrow.setTenderTimes("0");
		}
		borrow.setTenderTimes(String.valueOf(Integer.parseInt(borrow.getTenderTimes()) + 1));
		this.getBorrowDaoService().update(borrow);
		
		long ed = CommonUtil.getDateTimeLong();
		logSecond.debug("   投标金额["+borrowTender.getMoney()+"] 续投["+borrowTender.getContinueAmount()+"] 可用["+borrowTender.getAbleAmount()+"] 标余额["+borrow.getBalance()+"]");
		logSecond.debug("【秒标结束】"+user.getUsername()+" 时间["+(ed-st)+"]");
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
