package com.qmd.service.impl.user;

import com.qmd.dao.borrow.BorrowDaoService;
import com.qmd.dao.user.AccountCashDao;
import com.qmd.dao.user.UserAccountDao;
import com.qmd.dao.user.UserAccountDetailDao;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.AccountCash;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccount;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.AccountCashService;
import com.qmd.util.CalculationUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;
import com.qmd.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询提现记录Service实现类
 * @author zhanf
 *
 */
@Service("accountCashService")
public class AccountCashServiceImpl extends
		BaseServiceImpl<AccountCash, Integer> implements AccountCashService {
	@Resource
	 AccountCashDao accountCashDao;
	@Resource
	 UserAccountDao userAccountDao;
	@Resource
	 ListingDao listingDao;
	@Resource
	UserAccountDetailDao userAccountDetaildao;
	
	@Resource
	BorrowDaoService borrowdao;
	 
	
	@Resource
	public void setBaseDao(AccountCashDao accountCashDao) {
		super.setBaseDao(accountCashDao);
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

	public AccountCashDao getAccountCashDao() {
		return accountCashDao;
	}

	public void setAccountCashDao(AccountCashDao accountCashDao) {
		this.accountCashDao = accountCashDao;
	}

	
	@Override
	public int reCallCashByAgency(Integer id) {
		AccountCash ac = this.accountCashDao.getForUpdate(id);
		
		int ret =0;
		if(ac.getStatus() != 0 ){
			return ret;
		}
//		Borrow borrow = this.borrowdao.getForUpdate(ac.getBorrowid()); 
//		if( borrow.getIsfangkuan() !=2){
//			return ret;
//		}
		ac.setStatus(3);
		this.accountCashDao.updateCash(ac);
		
		UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(ac.getAgencyUserid());
		userAccount = this.userAccountDao.getForUpdate(userAccount.getId(), userAccount);
		
		userAccount.setAbleMoney(CommonUtil.add(userAccount.getAbleMoney(),ac.getTotal()));
		userAccount.setCashMoney(CommonUtil.sub(userAccount.getCashMoney(),ac.getTotal()));
		
		this.userAccountDao.updateAll(userAccount);
		
//		borrowdao.update(borrow);
		ret =1;
		return ret;
	}
	
	
	
	

	/**
	 * 服务商 【月标】放款
	 */
	@Override
	public  AccountCash cashMoneyByAgencyMonth(User user, AccountCash accountCash,Borrow borrow,AccountBank accountBank,String cashType,String ip) {
				
		UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(user.getId());
		userAccount = this.userAccountDao.getForUpdate(userAccount.getId(), userAccount);
		
		BigDecimal total = new BigDecimal(borrow.getAccountYes());//放款金额
		BigDecimal fee = new BigDecimal(0); //手续费
		
		userAccount.setAbleMoney(CommonUtil.sub(userAccount.getAbleMoney(),total));
		userAccount.setCashMoney(CommonUtil.add(userAccount.getCashMoney(),total));
		userAccount.setUserId(user.getId());
		this.userAccountDao.updateAll(userAccount);//服务商资金账户
		
		
		Map<String,Object> tMap = new HashMap<String,Object>();
		tMap.put("sign", ConstantUtil.PERCENT_CASH_FEE);//按百分比收手续费
		String percent  = listingDao.getByStatementId("Listing.getKeyValue", tMap).toString();//获取百分比  eg:0.01
		BigDecimal per = new BigDecimal(0);
		if(StringUtils.isNotEmpty(percent) && CalculationUtil.isNumeric(percent) ){
			per = new BigDecimal(percent);
		}
		
		fee = total.multiply(per).divide(new BigDecimal(100));
		
		accountCash.setStatus(0);
		accountCash.setAccount(accountBank.getAccount());
		accountCash.setBank(accountBank.getBankId());
		accountCash.setBranch(accountBank.getBranch());
		accountCash.setTotal(total);//提现金额
		accountCash.setCredited(total.subtract(fee));//到账金额
		accountCash.setFee(fee);//手续费
		accountCash.setAbleCashMoney(accountCash.getCredited());//本次可提现金额
		accountCash.setFreeCashMoney(accountCash.getCredited());//本次免费提现金额  
		accountCash.setAddip(ip);
		accountCash.setCreateDate(new Date());
		accountCash.setModifyDate(new Date());
		this.accountCashDao.save(accountCash);
		
		borrow = borrowdao.getForUpdate(borrow.getId());
		//修改月标状态
//		borrow.setIsfangkuan(2);
		borrowdao.update(borrow);
		
		return accountCash;
	}


	/**
	 * 服务商 【流转标】放款
	 */
	@Override
	public  AccountCash cashMoneyByAgencyFlow(User user, AccountCash accountCash,Borrow borrow,AccountBank accountBank,String cashType,String ip) {
				
		UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(user.getId());
		userAccount = this.userAccountDao.getForUpdate(userAccount.getId(), userAccount);
		
//		BigDecimal total = borrow.getFkMoney();//放款金额
		BigDecimal total = accountCash.getTotal();//放款金额
		BigDecimal fee = new BigDecimal(0); //手续费
		
		userAccount.setAbleMoney(CommonUtil.sub(userAccount.getAbleMoney(),total));
		userAccount.setCashMoney(CommonUtil.add(userAccount.getCashMoney(),total));
		userAccount.setUserId(user.getId());
		this.userAccountDao.updateAll(userAccount);//服务商资金账户
		
		
		Map<String,Object> tMap = new HashMap<String,Object>();
		tMap.put("sign", ConstantUtil.PERCENT_CASH_FEE);//按百分比收手续费
		String percent  = listingDao.getByStatementId("Listing.getKeyValue", tMap).toString();//获取百分比  eg:0.01
		BigDecimal per = new BigDecimal(0);
		if(StringUtils.isNotEmpty(percent) && CalculationUtil.isNumeric(percent) ){
			per = new BigDecimal(percent);
		}
		
		fee = total.multiply(per).divide(new BigDecimal(100));
		
		accountCash.setStatus(0);
		accountCash.setAccount(accountBank.getAccount());
		accountCash.setBank(accountBank.getBankId());
		accountCash.setBranch(accountBank.getBranch());
//		accountCash.setTotal(total);//提现金额
		accountCash.setCredited(total.subtract(fee));//到账金额
		accountCash.setFee(fee);//手续费
		accountCash.setAbleCashMoney(accountCash.getCredited());//本次可提现金额
		accountCash.setFreeCashMoney(accountCash.getCredited());//本次免费提现金额  
		accountCash.setAddip(ip);
		accountCash.setCreateDate(new Date());
		accountCash.setModifyDate(new Date());
		this.accountCashDao.save(accountCash);
		
		borrow = borrowdao.getForUpdate(borrow.getId());
		//修改流转标状态
//		borrow.setIsfangkuan(2);
		borrowdao.update(borrow);
		
		return accountCash;
	}

	
	
	
	
	/**
	 * 获取提现记录列表
	 */
	@Override
	public Pager getCashList( Pager page,Map<String,Object> map) {
		// TODO Auto-generated method stub
		
		return this.accountCashDao.getAccountCashList(page,map);
	}

	
	
	@Override
	public AccountCash get( Integer id) {
		// TODO Auto-generated method stub
		
		return this.accountCashDao.get(id);
	}

	/**
	 * 获取提现列表
	 */
	@Override
	public List<AccountCash> gainCashLish(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.accountCashDao.gainCashLish( map);
	}

}
