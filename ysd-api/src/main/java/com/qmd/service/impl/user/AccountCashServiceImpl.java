package com.qmd.service.impl.user;

import com.qmd.dao.user.AccountCashDao;
import com.qmd.dao.user.UserAccountDao;
import com.qmd.dao.user.UserAccountDetailDao;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.user.*;
import com.qmd.mode.util.Listing;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.AccountCashService;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;
import com.qmd.util.Pager;
import com.qmd.util.SerialNumberUtil;
import org.apache.log4j.Logger;
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
	
	private Logger moneyLog = Logger.getLogger("userWithdrawMoneyLog");
	
	@Resource
	 AccountCashDao accountCashDao;
	@Resource
	 UserAccountDao userAccountDao;
	@Resource
	 ListingDao listingDao;
	@Resource
	UserAccountDetailDao userAccountDetaildao;
	
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

	/**
	 * 提现插入记录和冻结提现资金
	 */
	@Override
	public synchronized AccountCash cashMoney(User user, AccountCash accountCash,AccountBank accountBank,String cashType,String ip) {
		
		moneyLog.debug("   【开始提现操作】["+user.getUsername()+"]");
		
		UserAccount userAccount =this.userAccountDao.getUserAccountByUserId(user.getId());
		if(accountCash.getTotal().compareTo(userAccount.getAbleMoney())<=0){
			
			userAccount = this.userAccountDao.getForUpdate(userAccount.getId(), userAccount);
			BigDecimal bestMoney;//建议提取的最佳金额
			BigDecimal fee =new BigDecimal(0); //产生的费用
			BigDecimal credited;//实际到金额
			userAccount.setAbleMoney(CommonUtil .sub(userAccount.getAbleMoney(),accountCash.getTotal()));
			if(userAccount.getAbleMoney().compareTo(BigDecimal.ZERO)>=0){
				
			
				userAccount.setUnableMoney(CommonUtil.add(userAccount.getUnableMoney(),accountCash.getTotal()));
				userAccount.setUserId(user.getId());
				this.userAccountDao.updateAll(userAccount);
				UserAccountDetail userAcDetail = new UserAccountDetail();
				//*************************************
				//保存资金账户操作详细记录--提现冻结"
				userAcDetail.setType("cash_frost");
				userAcDetail.setMoney(accountCash.getTotal());
				userAcDetail.setNoUseMoney(userAccount.getUnableMoney());
				userAcDetail.setUseMoney(userAccount.getAbleMoney());
				userAcDetail.setCollection(userAccount.getCollection());
				userAcDetail.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital());
				userAcDetail.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest());
				userAcDetail.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
				userAcDetail.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest());
				userAcDetail.setToUser(0);
				userAcDetail.setTotal(userAccount.getTotal());
				userAcDetail.setRemark("用户提现申请");
				userAcDetail.setUserId(userAccount.getUserId());
				userAccountDetaildao.update(userAcDetail);
				if(user.getTypeId()==0){
						
						 Map<String,Object> map1 = new HashMap<String,Object>();
						 map1.put("userId" , user.getId());
						 int[] cashStatusArray =  {0,1,4};//状态码【0-审核中；1-审核成功；2-审核失败；3-用户取消；4-处理中】
						 map1.put("cashStatusArray" , cashStatusArray);
						 map1.put("minDate" ,  CommonUtil.date2begin(CommonUtil.getFirstDayOfMonth(new Date())));
						 map1.put("maxDate" , new Date());
						 
						int userCashChargeTimes =  accountCashDao.queryAccountCashListCount(map1);//当月提现次数
						
						 BigDecimal userCashChargeMoney = BigDecimal.ZERO;
						 
						 String val1 = listingDao.getKeyValue(ConstantUtil.CASH_CHARGE_TIMES);
						 int val1_i = Integer.parseInt(val1);
						 if (userCashChargeTimes>=val1_i) {
							 String val2 = listingDao.getKeyValue(ConstantUtil.CASH_CHARGE_MONEY);//提现手续费
							 userCashChargeMoney = new BigDecimal(val2);
							 fee = userCashChargeMoney.multiply(accountCash.getTotal());
							 Listing listing2 =listingDao.getListing(ConstantUtil.FIXED_CASH_FEE);//固定手续费
							 String fixedFee = listing2.getKeyValue();
							 fee = fee.add(new BigDecimal(fixedFee));


						 } 
						
						moneyLog.debug("   ===账户总额["+user.getTotal()+"]提现["+accountCash.getTotal()+"]手续费["+fee+"]单月提现次数["+userCashChargeTimes+"]");
				}
				credited=CommonUtil.sub(accountCash.getTotal(), fee);
				accountCash.setUserId(user.getId());
				accountCash.setStatus(0);
				accountCash.setAccount(accountBank.getAccount());
				accountCash.setBank(accountBank.getBankId());
				accountCash.setBranch(accountBank.getBranch());
				accountCash.setCredited(credited);
				accountCash.setFee(fee);
				accountCash.setAddip(ip);
				accountCash.setCreateDate(new Date());
				accountCash.setName(accountBank.getName());
				accountCash.setTradeNo(SerialNumberUtil.buildPaymentSn());//提现订单号
				this.accountCashDao.save(accountCash);
			}
		}
		moneyLog.debug("   【结束提现操作】["+user.getUsername()+"]提现["+accountCash.getTotal()+"]实到["+accountCash.getCredited()+"]手续["+accountCash.getFee()+"]");
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
	public void updateCash(AccountCash accountCash, Integer userId) {
		// TODO Auto-generated method stub
		this.accountCashDao.updateCash(accountCash);
		UserAccount userAccount =this.userAccountDao.getUserAccountByUserId(userId);
		userAccount.setAbleMoney(CommonUtil .add(userAccount.getAbleMoney(),accountCash.getTotal()));
		userAccount.setUnableMoney(CommonUtil.sub(userAccount.getUnableMoney(),accountCash.getTotal()));
		
		this.userAccountDao.updateAll(userAccount);
		UserAccountDetail userAcDetail = new UserAccountDetail();
		//*************************************
		//保存资金账户操作详细记录--取消提现解冻"
		userAcDetail.setType("cash_cancel");
		userAcDetail.setMoney(accountCash.getTotal());
		userAcDetail.setNoUseMoney(userAccount.getUnableMoney());
		userAcDetail.setUseMoney(userAccount.getAbleMoney());
		userAcDetail.setCollection(userAccount.getCollection());
		userAcDetail.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital());
		userAcDetail.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest());
		userAcDetail.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
		userAcDetail.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest());
		userAcDetail.setToUser(0);
		userAcDetail.setTotal(userAccount.getTotal());
		userAcDetail.setRemark("用户取消提现申请");
		userAcDetail.setUserId(userAccount.getUserId());
		userAccountDetaildao.update(userAcDetail);
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
	
	@Override
	public Integer queryAccountCashListCount(Map<String,Object> map) {
		return accountCashDao.queryAccountCashListCount(map);
	}

}
