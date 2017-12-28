package net.qmdboss.service.impl;

import com.ysd.biz.PushForBiz;
import net.qmdboss.DTO.UserAccountCashDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.*;
import net.qmdboss.entity.*;
import net.qmdboss.service.AccountCashService;
import net.qmdboss.util.CommonUtil;
import net.qmdboss.util.ConstantUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提现待审成功等列表Service实现类
 * @author zhanf
 *
 */
@Service("accountCashServiceImpl")
public class AccountCashServiceImpl extends
		BaseServiceImpl<AccountCash, Integer> implements AccountCashService {
	Logger log = Logger.getLogger(AccountCashServiceImpl.class);

	@Resource(name = "userAccountRechargeDaoImpl")
	private UserAccountRechargeDao userAccountRechargeDao;
	@Resource(name = "listingDaoImpl")
	private ListingDao listingDao;
	
	@Resource(name = "accountCashDaoImpl")
	private AccountCashDao accountCashDao;
	@Resource(name = "userAccountDaoImpl")
	private UserAccountDao userAccountDao;
	@Resource(name = "userAccountDetailDaoImpl")
	private UserAccountDetailDao userAccountDetailDao;
	@Resource(name = "accountCashDaoImpl")
	public void setBaseDao(AccountCashDao accountCashDao) {
		super.setBaseDao(accountCashDao);
	}
	
	public AccountCashDao getAccountCashDao() {
		return accountCashDao;
	}

	public void setAccountCashDao(AccountCashDao accountCashDao) {
		this.accountCashDao = accountCashDao;
	}

	
	@Override
	public Pager getCashPage(AccountCash accountCash, Pager pager,Date startDate,Date endDate) {
		// TODO Auto-generated method stub
		return accountCashDao.getCashPage(accountCash, pager,startDate,endDate);
	}
	
	@Override
	public void update(AccountCash accountCash) {
		accountCashDao.update(accountCash);
	}

	@Override
	public List<AccountCash> getAccountCashList(AccountCash accountCash,
			Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return accountCashDao.getCashList(accountCash,startDate,endDate);
	}

	@Override
	public List<AccountCash> queryAccountCashList(AccountCash accountCash
			) {
		// TODO Auto-generated method stub
		return accountCashDao.queryAccountCashList(accountCash);
	}

	@Override
	public synchronized int updateCashMoney(AccountCash accountCash, Integer id,Admin admin,StringBuffer logInfoStringBuffer) {
		AccountCash accountCash1 = accountCashDao.loadLock(id);
		if (accountCash1.getStatus() !=0 && accountCash1.getStatus() != 4) {
			return 2;//非申请中，非处理中的审核，返还状态错误
		}
		String adminId = String .valueOf(admin.getId());
		
		BigDecimal fee = getFee(accountCash1);
		
		if(accountCash1.getStatus()==0||accountCash1.getStatus()==4){
			accountCash1.setStatus(accountCash.getStatus());
			if(accountCash1.getStatus()==0){
				if(accountCash1.getUser().getTypeId()==0){
					int i = accountCash1.getFee().compareTo(fee);
					if(i==1){
						accountCash1.setFee(fee);
						accountCash1.setCredited(accountCash1.getTotal().subtract(fee));
					}
				}
			}
			accountCash1.setVerifyUserid(Integer.valueOf(adminId));
			accountCash1.setTotal(accountCash.getTotal());
			accountCash1.setCredited(accountCash.getCredited());
			accountCash1.setFee(accountCash.getFee());
			accountCash1.setVerifyRemark(accountCash.getVerifyRemark());
			accountCash1.setChangeNum(accountCash.getChangeNum());
			accountCash1.setChangeRemark(accountCash.getChangeRemark());
			accountCash1.setVerifyTime(new Date());
			accountCashDao.update(accountCash1);
			//UserAccount  userAccount = userAccountDao.load(accountCash1.getUser().getAccount().getId());
			//userAccount = userAccountDao.loadLock(userAccount.getId());
			UserAccount userAccount = userAccountDao.loadLockTable(accountCash1.getUser());// 锁表
			System.out.println(accountCash.getStatus());
			System.out.println(accountCash1.getStatus());
			if(accountCash.getStatus()==1){
					UserAccountDetail userAcDetail = new UserAccountDetail();
					userAccount.setUnableMoney(userAccount.getUnableMoney().subtract(accountCash1.getTotal()) );
					userAccount.setTotal(userAccount.getTotal().subtract(accountCash1.getTotal()));
					userAccountDao.update(userAccount);//修改资金表数据
					log.info("保存资金账户操作详细记录--提现成功写记录");
					userAcDetail.setType("recharge_success");
					userAcDetail.setMoney(accountCash1.getTotal());
					userAcDetail.setNoUseMoney(userAccount.getUnableMoney());
					userAcDetail.setUseMoney(userAccount.getAbleMoney());
					userAcDetail.setCollection(userAccount.getCollection());
					userAcDetail.setToUser(0);
					userAcDetail.setTotal(userAccount.getTotal());
					userAcDetail.setRemark("成功提现，账户扣除提现金额包括提现费用:"+accountCash1.getFee()+"实际到账金额:"+accountCash1.getCredited());
					userAcDetail.setUser(userAccount.getUser());
					userAcDetail.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital());
					userAcDetail.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest());
					userAcDetail.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
					userAcDetail.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest());
					userAccountDetailDao.save(userAcDetail);
					
					logInfoStringBuffer.append("用户名:" +admin.getName()+"，审核:" + accountCash1.getUser().getRealName());
					logInfoStringBuffer.append("的提现，ID为："+accountCash1.getId()+",金额为:"+accountCash1.getTotal()+",提现时间为："+accountCash1.getCreateDate()+",备注："+accountCash.getVerifyRemark()+";提现记录");
					
					return 1;
			}else if(accountCash.getStatus()==2){
				UserAccountDetail userAcDetail = new UserAccountDetail();
				userAccount.setAbleMoney(userAccount.getAbleMoney().add(accountCash1.getTotal()));
				userAccount.setUnableMoney(userAccount.getUnableMoney().subtract(accountCash1.getTotal()));
				userAccountDao.update(userAccount);//修改资金表数据
				log.info("保存资金账户操作详细记录--提现失败");
				userAcDetail.setType("cash_cancel");
				userAcDetail.setMoney(accountCash1.getTotal());
				userAcDetail.setNoUseMoney(userAccount.getUnableMoney());
				userAcDetail.setUseMoney(userAccount.getAbleMoney());
				userAcDetail.setCollection(userAccount.getCollection());
				userAcDetail.setToUser(0);
				userAcDetail.setTotal(userAccount.getTotal());
				userAcDetail.setRemark("返回提现账户，扣除提现金额包括提现费用:"+accountCash1.getFee()+"实际到账金额:"+accountCash1.getCredited());
				userAcDetail.setUser(userAccount.getUser());
				userAcDetail.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital());
				userAcDetail.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest());
				userAcDetail.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
				userAcDetail.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest());
				userAccountDetailDao.save(userAcDetail	);
				
				logInfoStringBuffer.append("用户名:" +admin.getName()+"，审核:" + accountCash1.getUser().getRealName());
				logInfoStringBuffer.append("的提现，ID为："+accountCash1.getId()+",金额为:"+accountCash1.getTotal()+",提现时间为："+accountCash1.getCreateDate()+",备注："+accountCash.getVerifyRemark()+";提现失败");
				
				return 0;
			}
			
			return 3;
		}
		return 2;
		
	}
	
	private BigDecimal getFee(AccountCash accountCash){
		Date endTime = new Date();
		Date startTime =  CommonUtil.getDateBefore(endTime,15);
		List <UserAccountRecharge> userAccRechargeList= userAccountRechargeDao.getUserAccountRechargeList(accountCash.getUser().getId(), 1, startTime, endTime);
		 BigDecimal sum = new BigDecimal(0.00);
		for(UserAccountRecharge u : userAccRechargeList){
			sum=sum.add(u.getMoney());
		}
		BigDecimal bestMoney;//建议提取的最佳金额
		BigDecimal applyMoney =new BigDecimal(0);//申请中的提现
		BigDecimal fee =new BigDecimal(0); //产生的费用
		BigDecimal compare = new BigDecimal(0);
		List<AccountCash> accountCashList  =accountCashDao.queryAccountCashList(accountCash);
		if(accountCashList.size()>0){
			for(AccountCash accountCash1:accountCashList){
				if(accountCash1.getId()!=accountCash.getId()){
					applyMoney= applyMoney.add(accountCash1.getTotal()) ;
				}
			}
		}
		bestMoney=(accountCash.getUser().getAccount().getTotal().subtract(applyMoney)).subtract(sum);
		if(compare.compareTo(bestMoney)==1){
			bestMoney=compare;
		}
		Listing listing = listingDao.load(1471);//固定提现手续费
		Listing listing1 = listingDao.load(1472);//额外提现手续费
		BigDecimal firstfee = BigDecimal.valueOf(Double.parseDouble(listing.getKeyValue()));//固定提现手续费
		BigDecimal feeValue = BigDecimal.valueOf(Double.parseDouble(listing1.getKeyValue()));//超额提现手续费
		int i=accountCash.getTotal().compareTo(bestMoney);
		if(i==1){
			fee =firstfee.add(( (accountCash.getTotal().subtract(bestMoney)).multiply(feeValue)));
		}else{
			fee=firstfee;
		}
		
		// 单笔手续费计算
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("userId", accountCash.getUser().getId());
		Integer[] statusArray = {1,4};//1成功，4处理中
		map1.put("status", statusArray);
		map1.put("dateStart", CommonUtil.date2begin(CommonUtil.getFirstDayOfMonth(new Date())));
		map1.put("dateStop", new Date());
		int userCashChargeTimes = accountCashDao.queryAccountCashListCount(map1);
		
		Listing val1 = listingDao.getListingBySign(ConstantUtil.CASH_CHARGE_TIMES);
		if (userCashChargeTimes > Integer.parseInt(val1.getKeyValue())) {
			Listing val2 = listingDao.getListingBySign(ConstantUtil.CASH_CHARGE_MONEY);
			BigDecimal userCashChargeMoney = new BigDecimal(val2.getKeyValue());
			fee = fee.add(userCashChargeMoney);
		}
				
		return fee;
		
	}
	
	
	public Integer queryAccountCashListCount(Map<String, Object> map) {
		return accountCashDao.queryAccountCashListCount(map);
	}

	/**modify by yujian**/
	@Override
	public synchronized boolean updateCashMoney(AccountCash accountCash, Integer id,Admin admin,BigDecimal fee) {

		AccountCash	accountCash1 = accountCashDao.load(id);
		String adminId = String .valueOf(admin.getId());
		
		if(accountCash1.getStatus()==0||accountCash1.getStatus()==4){
			//yujian ysd-pay更新 accountCash1.setStatus(accountCash.getStatus());

			accountCash1.setVerifyUserid(Integer.valueOf(adminId));
			accountCash1.setTotal(accountCash.getTotal());
			accountCash1.setCredited(accountCash.getCredited());
			accountCash1.setFee(accountCash.getFee());
			accountCash1.setVerifyRemark(accountCash.getVerifyRemark());
			accountCash1.setChangeNum(accountCash.getChangeNum());
			accountCash1.setChangeRemark(accountCash.getChangeRemark());
			accountCash1.setVerifyTime(new Date());
			accountCashDao.update(accountCash1);

			if(accountCash.getStatus()==1){
				return true;
			}else if(accountCash.getStatus()==2){
				return false;
			}
		}
		return false;
		
	}

	@Override
	public List<UserAccountCashDTO> getAccountCashDTOList(AccountCash accountCash, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return accountCashDao.getCashDTOList(accountCash,startDate,endDate);
	}
}
