package com.qmd.service.impl.borrow;

import com.qmd.dao.borrow.BorrowDaoService;
import com.qmd.dao.borrow.BorrowRepaymentDetailDaoService;
import com.qmd.dao.borrow.BorrowTenderDaoService;
import com.qmd.dao.user.UserAccountDao;
import com.qmd.dao.user.UserAccountDetailDao;
import com.qmd.dao.user.UserRepaymentDetailDao;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountDetail;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.util.*;
import com.ysd.util.NewPayUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

/**
 * BorrowServiceImpl 标方法的Service实现类
 * @author jafen
 * @time 2012-10-24
 *
 */
@Repository("borrowService")
public class BorrowServiceImpl extends BaseServiceImpl<BorrowTender,Integer> implements BorrowService {
	Logger log = Logger.getLogger(BorrowServiceImpl.class);
	
	private Logger logPledge = Logger.getLogger("userPledgeLog");
	
	@Resource
	BorrowDaoService borrowDaoService;
	@Resource
	UserAccountDao userAccountDao;
	@Resource
	BorrowTenderDaoService borrowTenderDaoService;
	@Resource
	UserAccountDetailDao userAccountDetailDao;
	@Resource
	ListingDao 	listingDao;
	@Resource
	BorrowRepaymentDetailDaoService borrowRepaymentDetailDao;
	@Resource
	BorrowRepaymentDetailDaoService  borrowRepaymentDetailDaoService;
	@Resource
	InterestCalUtil interestCalUtil;
	@Resource
	UserRepaymentDetailDao userRepaymentDetailDao;
	
	public BorrowTenderDaoService getBorrowTenderDaoService() {
		return borrowTenderDaoService;
	}
	public void setBorrowTenderDaoService(
			BorrowTenderDaoService borrowTenderDaoService) {
		this.borrowTenderDaoService = borrowTenderDaoService;
	}
	public BorrowRepaymentDetailDaoService getBorrowRepaymentDetailDao() {
		return borrowRepaymentDetailDao;
	}
	public void setBorrowRepaymentDetailDao(
			BorrowRepaymentDetailDaoService borrowRepaymentDetailDao) {
		this.borrowRepaymentDetailDao = borrowRepaymentDetailDao;
	}
	public UserAccountDetailDao getUserAccountDetailDao() {
		return userAccountDetailDao;
	}
	public void setUserAccountDetailDao(UserAccountDetailDao userAccountDetailDao) {
		this.userAccountDetailDao = userAccountDetailDao;
	}
	public BorrowRepaymentDetailDaoService getBorrowRepaymentDetailDaoService() {
		return borrowRepaymentDetailDaoService;
	}
	public void setBorrowRepaymentDetailDaoService(
			BorrowRepaymentDetailDaoService borrowRepaymentDetailDaoService) {
		this.borrowRepaymentDetailDaoService = borrowRepaymentDetailDaoService;
	}
	public ListingDao getListingDao() {
		return listingDao;
	}
	public void setListingDao(ListingDao listingDao) {
		this.listingDao = listingDao;
	}
	public UserAccountDao getUserAccountDao() {
		return userAccountDao;
	}
	public void setUserAccountDao(UserAccountDao userAccountDao) {
		this.userAccountDao = userAccountDao;
	}
	public BorrowDaoService getBorrowDaoService() {
		return borrowDaoService;
	}
	public void setBorrowDaoService(BorrowDaoService borrowDaoService) {
		this.borrowDaoService = borrowDaoService;
	}
	
	/**
	 * queryBorrowList 分页获取标的列表
	 * return Pager
	 */
	@Override
	public Pager queryBorrowList(Pager page,Map<String,Object> qMap) {
		// TODO Auto-generated method stub
		return this.borrowDaoService.queryBorrowList(page,qMap);
	}
	@Override
	public Pager queryBorrowListCopy(Pager page,Map<String,Object> qMap) {
		// TODO Auto-generated method stub
		return this.borrowDaoService.queryBorrowListCopy(page,qMap);
	}
	@Override
	public Pager queryBorrowListForRepay(Pager page,Map<String,Object> qMap) {
		// TODO Auto-generated method stub
		return this.borrowDaoService.queryBorrowListForRepay(page,qMap);
	}
	@Override
	public Pager queryUserTenderBorrowList(Pager page,Map<String,Object> qMap) {
		// TODO Auto-generated method stub
		return this.borrowDaoService.queryUserTenderBorrowList(page,qMap);
	}
	@Override
	public Borrow getBorrowById(Integer id) {
		// TODO Auto-generated method stub
		Borrow borrow = this.borrowDaoService.getBorrowById(id);
		return borrow;
	}
	@Override
	public List<Borrow> queryUserBorrowList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getBorrowDaoService().queryUserBorrowList(map);
	}
	public void doSomething() {
	    // something that should execute on weekdays only
		//System.out.println("a start at :" + System.currentTimeMillis());
	}
	public void doSomethings() {
	    // something that should execute on weekdays only
		//System.out.println("b start at :" + System.currentTimeMillis());
	}
	
	@Override
	public Double queryBorrowAccountSum(Map<String,Object> map) {
		return borrowDaoService.queryBorrowAccountSum(map);
	}
	
	/**
	 * addBorrow 添加标
	 * return 
	 */
	@Override
	public void addBorrow(Borrow borrow) {
		this.borrowDaoService.add(borrow);
		if (borrow.getParentFlg()!=null && borrow.getParentFlg()==1) {//首标
			Borrow b = new Borrow();
			b.setId(borrow.getId());
			//b.setParentId(borrow.getId());
			borrowDaoService.updateParent(b);
		}
	}
	
	@Override
	public synchronized int borrowInvestDo(User user,BorrowTender borrowTender,String ip) {
		
		logPledge.debug("【质押标开始】"+user.getUsername()+",["+borrowTender.getBorrowId()+"] 可用["+borrowTender.getAbleAmount()+"] 续投["+borrowTender.getContinueAmount()+"]");
		long st = CommonUtil.getDateTimeLong();
		
		UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(user.getId());
		userAccount = this.userAccountDao.getForUpdate(userAccount.getId(), userAccount); //账户锁定
		Borrow borrow = this.borrowDaoService.getBorrowById(borrowTender.getBorrowId());
		borrow = this.borrowDaoService.getForUpdate(borrow.getId());//标锁定
		
		if (borrow.getStatus() != 1) {
			logPledge.debug("【质押标结束】"+user.getUsername()+"投标失败,标状态不对["+borrow.getStatus()+"]");
			return 4;// 标已满
		}
		
		if (Double.parseDouble(borrow.getBalance()) <=0) {
			logPledge.debug("【质押标结束】"+user.getUsername()+"投标失败,标已满["+borrow.getBalance()+"]");
			return 1;// 标已满
		}
		
		// 账户可用金额少于投标金额
		if (userAccount.getAbleMoney().doubleValue() < Double.parseDouble(borrowTender.getAbleAmount())) {
			logPledge.debug("【质押标结束】"+user.getUsername()+"投标失败,可用余额不足["+userAccount.getAbleMoney()+"]");
			return 2;// 余额不足
		}
		// 账户可用金额少于投标金额
		if (userAccount.getContinueTotal().doubleValue() < Double.parseDouble(borrowTender.getContinueAmount())) {
			logPledge.debug("【质押标结束】"+user.getUsername()+"投标失败,续投余额不足["+userAccount.getContinueTotal()+"]");
			return 3;// 余额不足
		}
		
		//borrow = this.borrowDaoService.getForUpdate(borrow.getId(), borrow);
		//投标金额大于标剩余金额
		if(Double.parseDouble(borrowTender.getMoney()) > Double.parseDouble(borrow.getBalance())){
			// 部分通过
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
			// 全部通过
			borrowTender.setAccount(borrowTender.getMoney());
			borrowTender.setStatus("1");
		}
		//修改实际投标金额`
		PaymentView pv = interestCalUtil.payback(Double.valueOf(borrowTender.getAccount()),Double.valueOf(borrow.getAccount()), borrow.getApr(), Integer.valueOf(borrow.getTimeLimit()),borrow.getBorStages(),2);
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
		this.getBorrowDaoService().update(borrow);
		
		long ed = CommonUtil.getDateTimeLong();
		logPledge.debug("   投标金额["+borrowTender.getMoney()+"] 续投["+borrowTender.getContinueAmount()+"] 可用["+borrowTender.getAbleAmount()+"] 标余额["+borrow.getBalance()+"]");
		logPledge.debug("【质押标结束】"+user.getUsername()+" 时间["+(ed-st)+"]");
		return 0;
	}
	
	//月标
	@Override
	public synchronized int monthBorrowInvestDo(User user,BorrowTender borrowTender,String ip) {
		
		logPledge.debug("【月标开始】"+user.getUsername()+",["+borrowTender.getBorrowId()+"] 可用["+borrowTender.getAbleAmount()+"] 续投["+borrowTender.getContinueAmount()+"]");
		long st = CommonUtil.getDateTimeLong();
		
		UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(user.getId());
		userAccount = this.userAccountDao.getForUpdate(userAccount.getId(), userAccount); //账户锁定
		Borrow borrow = this.borrowDaoService.getBorrowById(borrowTender.getBorrowId());
		borrow = this.borrowDaoService.getForUpdate(borrow.getId());//标锁定
		
		if (borrow.getStatus() != 1) {
			logPledge.debug("【月标结束】"+user.getUsername()+"投标失败,标状态不对["+borrow.getStatus()+"]");
			return 4;// 标已满
		}
		
		if (Double.parseDouble(borrow.getBalance()) <=0) {
			logPledge.debug("【月标结束】"+user.getUsername()+"投标失败,标已满["+borrow.getBalance()+"]");
			return 1;// 标已满
		}
		
		// 账户可用金额少于投标金额
		if (userAccount.getAbleMoney().doubleValue() < Double.parseDouble(borrowTender.getAbleAmount())) {
			logPledge.debug("【月标结束】"+user.getUsername()+"投标失败,余额不足["+userAccount.getAbleMoney()+"]");
			return 2;// 余额不足
		}
		
		// 账户可用金额少于投标金额
		if (userAccount.getContinueTotal().doubleValue() < Double.parseDouble(borrowTender.getContinueAmount())) {
			logPledge.debug("【月标结束】"+user.getUsername()+"投标失败,续投余额不足["+userAccount.getContinueTotal()+"]");
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
		PaymentView pv = interestCalUtil.payback(Double.valueOf(borrowTender.getAccount()),Double.valueOf(borrow.getAccount()), borrow.getApr(), Integer.valueOf(borrow.getTimeLimit()),borrow.getBorStages(),1);
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
		this.getBorrowDaoService().update(borrow);
		
		long ed = CommonUtil.getDateTimeLong();
		logPledge.debug("   投标金额["+borrowTender.getMoney()+"] 续投["+borrowTender.getContinueAmount()+"] 可用["+borrowTender.getAbleAmount()+"] 标余额["+borrow.getBalance()+"]");
		logPledge.debug("【月标结束】"+user.getUsername()+" 时间["+(ed-st)+"]");
		return 0;
	}

	/**
	 * 修改未审核的标
	 */
	@Override
	public void updateBorrowMess(Borrow borrow) {
		// TODO Auto-generated method stub
		this.borrowDaoService.update(borrow);
	}
	
	/**
	 * 删除标
	 */
	@Override
	public void delectBorrow(Integer id) {
		// TODO Auto-generated method stub
		this.borrowDaoService.delect(id);
	}
	
	
}
