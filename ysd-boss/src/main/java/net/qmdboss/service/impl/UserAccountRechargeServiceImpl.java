package net.qmdboss.service.impl;

import net.qmdboss.DTO.UserAccountRechargeDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.*;
import net.qmdboss.entity.*;
import net.qmdboss.service.AdminService;
import net.qmdboss.service.UserAccountRechargeService;
import net.qmdboss.util.InterestCalUtil;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("userAccountRechargeServiceImpl")
public class UserAccountRechargeServiceImpl extends BaseServiceImpl<UserAccountRecharge, Integer>
implements UserAccountRechargeService{

	@Resource(name = "userAccountRechargeDaoImpl")
	private UserAccountRechargeDao userAccountRechargeDao;
	@Resource(name = "userAccountDaoImpl")
	private UserAccountDao userAccountDao;
	@Resource(name = "userAccountDetailDaoImpl")
	private UserAccountDetailDao userAccountDetailDao;
	@Resource(name = "userAwardDetailDaoImpl")
	private UserAwardDetailDao userAwardDetailDao;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "rechargeConfigDaoImpl")
	private RechargeConfigDao rechargeConfigDao;
	@Resource(name = "listingDaoImpl")
	private ListingDao listingDao;
	@Resource(name = "userDaoImpl")
	private UserDao userDao;
	
	@Resource(name = "userAccountRechargeDaoImpl")
	public void setBaseDao(UserAccountRechargeDao userAccountRechargeDao) {
		super.setBaseDao(userAccountRechargeDao);
	}

	public Pager findPager(Pager pager,Map<String,Object> map){
		return userAccountRechargeDao.findPager(pager, map);
	}
	
	public List<UserAccountRechargeDTO> getRechargeList(Map<String,Object> map){
		return userAccountRechargeDao.getRechargeList(map);
	}
	
	
	public List<UserAccountRecharge> findUserAccountRechargeList(String username,Integer status, RechargeConfig rechargeInterface,Date startDate,Date endDate){
		return userAccountRechargeDao.findUserAccountRechargeList(username, status, rechargeInterface, startDate, endDate);
	}
	
	public Integer saveUserAccountRechargeByOffLine(UserAccountRecharge userAccountRecharge){
		RechargeConfig rechargeConfig = rechargeConfigDao.find("background");
		userAccountRecharge.setRechargeInterface(rechargeConfig);//后台充值
		userAccountRecharge.setAdminOperator(adminService.getLoginAdmin());
		userAccountRecharge.setFee(new BigDecimal(0));
		userAccountRecharge.setReturned("");
		userAccountRecharge.setType("0");
		userAccountRecharge.setStatus(2);//【0：失败;1：成功;2:后台审核中;】
		return userAccountRechargeDao.save(userAccountRecharge);
	}
	
	public UserAccountRecharge getUserAccountRecharge(String tradeNo){
		return userAccountRechargeDao.getUserAccountRecharge(tradeNo);
	}
	@Override
	@CacheFlush(modelId = "userAccountRechargeFlushing")
	public void delete(UserAccountRecharge userAccountRecharge) {
		userAccountRechargeDao.delete(userAccountRecharge);
	}

	@Override
	@CacheFlush(modelId = "userAccountRechargeFlushing")
	public void delete(Integer id) {
		userAccountRechargeDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "userAccountRechargeFlushing")
	public void delete(Integer[] ids) {
		userAccountRechargeDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "userAccountRechargeFlushing")
	public Integer save(UserAccountRecharge userAccountRecharge) {
		return userAccountRechargeDao.save(userAccountRecharge);
	}

	@Override
	@CacheFlush(modelId = "userAccountRechargeFlushing")
	public int updateApproveOffLine(Integer id,UserAccountRecharge userAccountRecharge,Admin admin,StringBuffer logInfoStringBuffer) {
		
		UserAccountRecharge entity = userAccountRechargeDao.loadLock(id);
		if(entity.getStatus()!=2){//状态：非待审，返还2：状态不对
			return 2;
		}
		entity.setIpVerify(userAccountRecharge.getIpVerify());
		entity.setVerifyRemark(userAccountRecharge.getVerifyRemark());
		entity.setStatus(userAccountRecharge.getStatus());
		entity.setAdminVerify(admin);
		entity.setRechargeDate(new Date());
		userAccountRechargeDao.update(entity);
		
		logInfoStringBuffer.append("充值记录ID:"+entity.getId());
		logInfoStringBuffer.append("状态:"+ (userAccountRecharge.getStatus()==1? "成功":"失败") );
		logInfoStringBuffer.append("金额:"+entity.getMoney());
		logInfoStringBuffer.append("费用:"+entity.getFee());
		
		BigDecimal money = entity.getMoney();//操作充值金额
		//修改账户信息 
		
		//UserAccount userAccount = userAccountRecharge.getUser().getAccount();
		//userAccount = userAccountDao.loadLock(userAccount.getId());
		UserAccount userAccount = userAccountDao.loadLockTable(entity.getUser());// 锁表
		
		if(userAccountRecharge.getStatus()==1){//审核成功
			//添加线下充值金额
			userAccount.setAbleMoney(userAccount.getAbleMoney().add(money));
			userAccount.setTotal(userAccount.getTotal().add(money));
			userAccountDao.update(userAccount);
			
			//添加线下充值金额流水
			UserAccountDetail userAccountDetail = new UserAccountDetail();
			userAccountDetail.setType("recharge_offline");//线下充值
			userAccountDetail.setTotal(userAccount.getTotal());
			userAccountDetail.setUseMoney(userAccount.getAbleMoney());
			userAccountDetail.setNoUseMoney(userAccount.getUnableMoney());
			userAccountDetail.setCollection(userAccount.getCollection());
			userAccountDetail.setMoney(money);
			userAccountDetail.setUser(entity.getUser());
			//userAccountDetail.setRemark("线下充值金额:"+entity.getMoney());
			userAccountDetail.setRemark(entity.getRemark());
			userAccountDetail.setOperatorer(admin.getUsername());
			userAccountDetail.setOperatorIp(userAccountRecharge.getIpVerify());
			userAccountDetail.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital());
			userAccountDetail.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest());
			userAccountDetail.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
			userAccountDetail.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest());
			userAccountDetail.setTasteMoney(userAccount.getTasteMoney());
			userAccountDetailDao.save(userAccountDetail);
		
			//添加线下充值奖励金额
			BigDecimal reward = entity.getReward();//操作奖励金额
			userAccount.setAbleMoney(userAccount.getAbleMoney().add(reward));
			userAccount.setTotal(userAccount.getTotal().add(reward));
			userAccountDao.update(userAccount);
			
			//添加线下充值奖励流水
			UserAccountDetail uad = new UserAccountDetail();
			uad.setType("recharge_offline_reward");//线下充值奖励
			uad.setTotal(userAccount.getTotal());
			uad.setUseMoney(userAccount.getAbleMoney());
			uad.setNoUseMoney(userAccount.getUnableMoney());
			uad.setCollection(userAccount.getCollection());
			uad.setMoney(reward);
			uad.setUser(entity.getUser());
			//uad.setRemark("线下充值奖励:"+reward);
			uad.setRemark(entity.getRemark());
			uad.setOperatorer(admin.getUsername());
			uad.setOperatorIp(userAccountRecharge.getIpVerify());
			uad.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital());
			uad.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest());
			uad.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
			uad.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest());
			uad.setTasteMoney(userAccount.getTasteMoney());
			userAccountDetailDao.save(uad);
			
			
			UserAwardDetail uad1 = new UserAwardDetail();
			uad1.setUser(userAccount.getUser());// 用户ID
			uad1.setType("tui_detail_award_ht");// 类型（同资金记录）
			uad1.setMoney(reward);// 操作金额
			uad1.setAwardMoney(userAccount.getAwardMoney());// 奖励账户
			uad1.setSignFlg(1);
			uad1.setRemark(entity.getRemark());// 备注
		
			userAwardDetailDao.save(uad1);
			
			
			
			
			
			
			User u = userAccount.getUser();
			if(u.getTasteFlag().compareTo(0) == 0){//充值送体验金
				
				//送体验金
				Listing val2 = listingDao.getListingBySign("recharge_taste");
				if(val2.getIsEnabled()){
					BigDecimal key_t = new BigDecimal(val2.getKeyValue());
					BigDecimal tasteMoney = money.multiply(key_t).divide(new BigDecimal(100));
					
					userAccount.setModifyDate(new Date());
					userAccount.setTasteMoney(userAccount.getTasteMoney().add(tasteMoney));
					this.userAccountDao.update(userAccount);
	
					UserAccountDetail adTaste = InterestCalUtil.saveAccountDetail("first_recharge_taste",//
							tasteMoney,//
							"首次充值送体验金:"+tasteMoney+"元",
							userAccount.getUser(),//
							userAccount,//
							0,//
							admin.getName(),//
							admin.getLoginIp());
					userAccountDetailDao.save(adTaste);
					
					u.setTasteFlag(1);
					userDao.update(u);
				}
			}
			
		}
		
		//userAccountRechargeDao.update(userAccountRecharge);
		
		return 1;
	}
	
	@Override
	@CacheFlush(modelId = "userAccountRechargeFlushing")
	public void update(List<UserAccountRecharge> userAccountRechargeList) {
		userAccountRechargeDao.update(userAccountRechargeList);
	}
	
	@Override
	@CacheFlush(modelId = "userAccountRechargeFlushing")
	public void repairUpdate(UserAccountRecharge userAccountRecharge ,HttpServletRequest request){
		BigDecimal tranAmt = userAccountRecharge.getMoney();//交易金额
		BigDecimal feeAmt = userAccountRecharge.getFee();//手续费
		User user = userAccountRecharge.getUser();
		
		//修改总账户记录-充值金额
		UserAccount userAccount = user.getAccount();// userAccountDao.getUserAccountByUserId(Integer.parseInt(detailMap.get("userId").toString()));
		userAccount = userAccountDao.loadLock(userAccount.getId());//锁表
		userAccount.setModifyDate(new Date());
		userAccount.setTotal(userAccount.getTotal().add(tranAmt));
		userAccount.setAbleMoney(userAccount.getAbleMoney().add(tranAmt));
		userAccountDao.update(userAccount);
		
		//添加流水记录-充值金额
		UserAccountDetail userAccountDetail = new UserAccountDetail();
		userAccountDetail.setUser(user);
		userAccountDetail.setCreateDate(new Date());
		userAccountDetail.setModifyDate(new Date());
		userAccountDetail.setType("recharge");
		userAccountDetail.setTotal(userAccount.getTotal());
		userAccountDetail.setMoney(tranAmt);
		userAccountDetail.setUseMoney(userAccount.getAbleMoney());
		userAccountDetail.setNoUseMoney(userAccount.getUnableMoney());
		userAccountDetail.setCollection(userAccount.getCollection());
		userAccountDetail.setToUser(10000);
		userAccountDetail.setRemark("线上充值:"+tranAmt+"元");
		userAccountDetail.setAddTime(new Date());
		userAccountDetail.setOperatorer(adminService.getLoginAdmin().getUsername());
		userAccountDetail.setOperatorIp(request.getLocalAddr());
		userAccountDetail.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital());
		userAccountDetail.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest());
		userAccountDetail.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
		userAccountDetail.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest());
		userAccountDetailDao.save(userAccountDetail);
		
		//修改总账户记录-扣除手续费金额
		UserAccount userAccount_fee = userAccount;
		userAccount_fee = userAccountDao.loadLock(userAccount_fee.getId());//锁表
		userAccount_fee.setTotal(userAccount_fee.getTotal().subtract(feeAmt));
		userAccount_fee.setAbleMoney(userAccount_fee.getAbleMoney().subtract(feeAmt));
		userAccountDao.update(userAccount_fee);
		
		//添加流水记录-扣除手续费金额
		UserAccountDetail userAccountDetail_fee = new UserAccountDetail();
		userAccountDetail_fee.setUser(user);
		userAccountDetail_fee.setType("fee");
		userAccountDetail_fee.setTotal(userAccount_fee.getTotal());
		userAccountDetail_fee.setMoney(feeAmt);
		userAccountDetail_fee.setUseMoney(userAccount_fee.getAbleMoney());
		userAccountDetail_fee.setNoUseMoney(userAccount_fee.getUnableMoney());
		userAccountDetail_fee.setCollection(userAccount_fee.getCollection());
		userAccountDetail_fee.setToUser(10000);
		userAccountDetail_fee.setRemark("扣除手续费:"+feeAmt+"元");
		userAccountDetail_fee.setAddTime(new Date());
		userAccountDetail_fee.setOperatorer(adminService.getLoginAdmin().getUsername());
		userAccountDetail_fee.setOperatorIp(request.getLocalAddr());
		userAccountDetail_fee.setInvestorCollectionCapital(userAccount_fee.getInvestorCollectionCapital());
		userAccountDetail_fee.setInvestorCollectionInterest(userAccount_fee.getInvestorCollectionInterest());
		userAccountDetail_fee.setBorrowerCollectionCapital(userAccount_fee.getBorrowerCollectionCapital());
		userAccountDetail_fee.setBorrowerCollectionInterest(userAccount_fee.getBorrowerCollectionInterest());
		userAccountDetailDao.save(userAccountDetail_fee);
				
		userAccountRechargeDao.update(userAccountRecharge);
			
	}
	
	@Override
	@CacheFlush(modelId = "userAccountRechargeFlushing")
	public int repairUpdateOnline(Integer id ,Integer status,HttpServletRequest request,StringBuffer logInfoStringBuffer){
		UserAccountRecharge userAccountRecharge = userAccountRechargeDao.loadLock(id);
		
		if (userAccountRecharge.getStatus()==1) {
			return 1;
		}
		
		userAccountRecharge.setStatus(status);
		userAccountRecharge.setReturned("后台补单");
		userAccountRecharge.setRechargeDate(new Date());
		
		try {
			//补单成功
			if(status==1){
				BigDecimal tranAmt = userAccountRecharge.getMoney();//交易金额
				BigDecimal feeAmt = userAccountRecharge.getFee();//手续费
				User user = userAccountRecharge.getUser();
				
				//修改总账户记录-充值金额
				//UserAccount userAccount = user.getAccount();// userAccountDao.getUserAccountByUserId(Integer.parseInt(detailMap.get("userId").toString()));
				//userAccount = userAccountDao.loadLock(userAccount.getId());//锁表
				UserAccount userAccount = userAccountDao.loadLockTable(user);
				userAccount.setModifyDate(new Date());
				userAccount.setTotal(userAccount.getTotal().add(tranAmt));
				userAccount.setAbleMoney(userAccount.getAbleMoney().add(tranAmt));
				userAccountDao.update(userAccount);
				
				//添加流水记录-充值金额
				UserAccountDetail userAccountDetail = new UserAccountDetail();
				userAccountDetail.setUser(user);
				userAccountDetail.setCreateDate(new Date());
				userAccountDetail.setModifyDate(new Date());
				userAccountDetail.setType("recharge");
				userAccountDetail.setTotal(userAccount.getTotal());
				userAccountDetail.setMoney(tranAmt);
				userAccountDetail.setUseMoney(userAccount.getAbleMoney());
				userAccountDetail.setNoUseMoney(userAccount.getUnableMoney());
				userAccountDetail.setCollection(userAccount.getCollection());
				userAccountDetail.setToUser(10000);
				userAccountDetail.setRemark("线上充值:"+tranAmt+"元");
				userAccountDetail.setAddTime(new Date());
				userAccountDetail.setOperatorer(adminService.getLoginAdmin().getUsername());
				userAccountDetail.setOperatorIp(request.getLocalAddr());
				userAccountDetail.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital());
				userAccountDetail.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest());
				userAccountDetail.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
				userAccountDetail.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest());
				userAccountDetailDao.save(userAccountDetail);
				
				if (feeAmt != null && feeAmt.doubleValue() > 0) {
				
				//修改总账户记录-扣除手续费金额
				UserAccount userAccount_fee = userAccount;
				userAccount_fee = userAccountDao.loadLock(userAccount_fee.getId());//锁表
				userAccount_fee.setTotal(userAccount_fee.getTotal().subtract(feeAmt));
				userAccount_fee.setAbleMoney(userAccount_fee.getAbleMoney().subtract(feeAmt));
				userAccountDao.update(userAccount_fee);
				
				//添加流水记录-扣除手续费金额
				UserAccountDetail userAccountDetail_fee = new UserAccountDetail();
				userAccountDetail_fee.setUser(user);
				userAccountDetail_fee.setType("fee");
				userAccountDetail_fee.setTotal(userAccount_fee.getTotal());
				userAccountDetail_fee.setMoney(feeAmt);
				userAccountDetail_fee.setUseMoney(userAccount_fee.getAbleMoney());
				userAccountDetail_fee.setNoUseMoney(userAccount_fee.getUnableMoney());
				userAccountDetail_fee.setCollection(userAccount_fee.getCollection());
				userAccountDetail_fee.setToUser(10000);
				userAccountDetail_fee.setRemark("扣除手续费:"+feeAmt+"元");
				userAccountDetail_fee.setAddTime(new Date());
				userAccountDetail_fee.setOperatorer(adminService.getLoginAdmin().getUsername());
				userAccountDetail_fee.setOperatorIp(request.getLocalAddr());
				userAccountDetail_fee.setInvestorCollectionCapital(userAccount_fee.getInvestorCollectionCapital());
				userAccountDetail_fee.setInvestorCollectionInterest(userAccount_fee.getInvestorCollectionInterest());
				userAccountDetail_fee.setBorrowerCollectionCapital(userAccount_fee.getBorrowerCollectionCapital());
				userAccountDetail_fee.setBorrowerCollectionInterest(userAccount_fee.getBorrowerCollectionInterest());
				userAccountDetailDao.save(userAccountDetail_fee);
				}
				
			}else{
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return 3;
		}
	
		
		userAccountRechargeDao.update(userAccountRecharge);
		
		
		logInfoStringBuffer.append("充值记录ID:"+userAccountRecharge.getId());
		logInfoStringBuffer.append("金额:"+userAccountRecharge.getMoney());
		logInfoStringBuffer.append("手续费:"+userAccountRecharge.getFee());
		
		return 0;
			
	}
	
	@Override
	@CacheFlush(modelId = "userAccountRechargeFlushing")
	public void updateCompare(UserAccountRecharge userAccountRecharge) {
		userAccountRechargeDao.update(userAccountRecharge);
	}

	@Override
	public List<UserAccountRecharge> getUserAccountRechargeList(Integer userId,
			Integer status, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return userAccountRechargeDao.getUserAccountRechargeList(userId, status, startDate, endDate);
	}
	
	@Override
	public Pager findComRechargePager(Pager pager){
		return userAccountRechargeDao.findComRechargePager(pager);
	}
	
	
	public List<UserAccountRecharge> getComUserAccountRecharge(){
		return userAccountRechargeDao.getComUserAccountRecharge();
	}
	
	public List<UserAccountRecharge> getUserAccountRecharge(){
		return userAccountRechargeDao.getUserAccountRecharge();
	}
	
	public void update(UserAccountRecharge uar,Boolean flag,HttpServletRequest request){
		if(flag){
			uar.setStatus(1);
			uar.setReturned("后台补单");
			uar.setRechargeDate(new Date());
			repairUpdate(uar,request);
		}else{
			userAccountRechargeDao.update(uar);
		}
	}
	
	/**
	 * 获取充值总金额
	 * @param uar
	 * @return
	 */
	public BigDecimal getMoneyCount(UserAccountRecharge uar){
		BigDecimal ret = userAccountRechargeDao.getMoneyCount(uar);
		
		if (ret ==null) {
			ret = BigDecimal.valueOf(0);
		}
		return ret;
	}
	
//	public int approve(Integer id,UserAccountRecharge userAccountRecharge,String ip,Admin admin) {
//		UserAccountRecharge entity = userAccountRechargeDao.loadLock(id);
//		if(entity.getStatus()!=2){//状态：非待审，返还2：状态不对
//			return 2;
//		}
//		entity.setIpVerify(ip);
//		entity.setVerifyRemark(userAccountRecharge.getVerifyRemark());
//		entity.setStatus(userAccountRecharge.getStatus());
//		entity.setAdminVerify(admin);
//		entity.setRechargeDate(new Date());
//		userAccountRechargeDao.update(entity);
//		return 1;
//	}
	
	public Pager findNeedVerifyPagerByType(Pager pager,String type){
		return userAccountRechargeDao.findNeedVerifyPagerByType(pager,type);
	}
	
	@Override
	public void updateRechargeStatus(UserAccountRecharge userAccountRecharge){
		userAccountRechargeDao.update(userAccountRecharge);
	}
}
