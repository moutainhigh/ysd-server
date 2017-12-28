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
import com.qmd.service.borrow.BorrowPledgeService;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.util.AccountDetailUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.InterestCalUtil;
import com.qmd.util.PaymentView;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

@Repository("borrowPledgeService")
public class BorrowPledgeServiceImpl extends BaseServiceImpl<Borrow, Integer>
		implements BorrowPledgeService {
	Logger log = Logger.getLogger(BorrowPledgeServiceImpl.class);
	
	private Logger logPledge = Logger.getLogger("userPledgeLog");
	
	@Resource
	BorrowDaoService borrowDaoService;
	@Resource
	UserAccountDao userAccountDao;
	@Resource
	UserAccountDetailDao userAccountDetailDao;
	@Resource
	BorrowTenderDaoService borrowTenderDaoService;

	public BorrowDaoService getBorrowDaoService() {
		return borrowDaoService;
	}

	public void setBorrowDaoService(BorrowDaoService borrowDaoService) {
		this.borrowDaoService = borrowDaoService;
	}

	/**
	 * addBorrow 添加标 return
	 */
	@Override
	public void insertBorrow(Borrow borrow) {
		// TODO Auto-generated method stub
		this.borrowDaoService.insertBorrow(borrow);
	}
	
	/**
	 * 投标
	 */
	@Override
	public synchronized int insertBorrowInvest(User user, String investMoney, Integer bId,String ip) {
		
		logPledge.debug("【质押标开始】"+user.getUsername()+",["+bId+"]");
		
		long st = CommonUtil.getDateTimeLong();
		
		UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(user.getId());
		Borrow borrow = this.borrowDaoService.getBorrowById(bId);
		
		BorrowTender borrowTender = new BorrowTender();
		borrowTender.setMoney(investMoney);
		
		//投标金额大于标剩余金额
		if(Double.parseDouble(borrowTender.getMoney()) > Double.parseDouble(borrow.getBalance())){
			borrowTender.setAccount(borrow.getBalance());
			borrowTender.setStatus("2");
		}else{
			borrowTender.setAccount(borrowTender.getMoney());
			borrowTender.setStatus("1");
		}
		
		InterestCalUtil interestCalUtil = new InterestCalUtil();
		
		//修改实际投标金额`
		PaymentView pv = interestCalUtil.payback(Double.valueOf(borrowTender.getAccount()),Double.valueOf(borrow.getAccount()), borrow.getApr(), Integer.valueOf(borrow.getTimeLimit()),borrow.getBorStages(),2);
		// 总利息
		String interest = pv.getTotalLiXi();
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
		
		//修改用户可用金额 = 原用户可用金额-用户实际投资金额
		userAccount.setAbleMoney(BigDecimal.valueOf(userAccount.getAbleMoney().doubleValue() - Double.parseDouble(borrowTender.getAccount())));
		//修改用户冻结金额 = 原用户冻结金额+用户实际投资金额
		userAccount.setUnableMoney(BigDecimal.valueOf(userAccount.getUnableMoney().doubleValue() + Double.parseDouble(borrowTender.getAccount())));
		this.userAccountDao.update(userAccount);
		
		//资金记录
		UserAccountDetail userAccountDetail = AccountDetailUtil.fillUserAccountDetail("tender", 
				BigDecimal.valueOf(Double.valueOf(borrowTender.getAccount())),
				user.getId(), "投标冻结资金["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+"]", ip, userAccount);
		
		this.userAccountDetailDao.save(userAccountDetail);
		
		// 修改标的数据 
		DecimalFormat df = new DecimalFormat("#");
		// 剩余金额 = 原剩余金额 - 用户投资金额
		borrow.setBalance(df.format(Double.valueOf(borrow.getBalance()) - Double.valueOf(borrowTender.getAccount())));
		NumberFormat nf =NumberFormat.getInstance();
		nf.setMaximumFractionDigits(0);
		//进度 = (1-剩余金额/标总金额) * 100
		String shc = nf.format((1 - Double.valueOf(borrow.getBalance())/Double.valueOf(borrow.getAccount()))*100);
		borrow.setSchedule(shc);
		if (Double.valueOf(borrow.getBalance()) == 0
				|| Double.valueOf(borrow.getBalance()) < 0) {
			borrow.setStatus(5);
		}
		// 修改投标次数
		if (borrow.getTenderTimes()==null) borrow.setTenderTimes("0");
		borrow.setTenderTimes(String.valueOf(Integer.parseInt(borrow.getTenderTimes()) + 1));
		
		this.getBorrowDaoService().update(borrow);
		
		long ed = CommonUtil.getDateTimeLong();
		
		logPledge.debug("   投标金额["+userAccountDetail.getMoney()+"] 标余额["+borrow.getBalance()+"]");
		logPledge.debug("【质押标结束】"+user.getUsername()+" 时间["+(ed-st)+"]");
		
		return 0;
	}

}
