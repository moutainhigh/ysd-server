package net.qmdboss.service.impl;

import net.qmdboss.DTO.UserAccountDetailDTO;
import net.qmdboss.bean.AccountDetail;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.*;
import net.qmdboss.entity.Listing;
import net.qmdboss.entity.UserAccount;
import net.qmdboss.entity.UserAccountDetail;
import net.qmdboss.entity.UserHongbao;
import net.qmdboss.service.AdminService;
import net.qmdboss.service.RechargeConfigService;
import net.qmdboss.service.UserAccountDetailService;
import net.qmdboss.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service实现类 - 用户账户日志记录类
 * ============================================================================
 */
@Service("userAccountDetailServiceImpl")
public class UserAccountDetailServiceImpl extends BaseServiceImpl<UserAccountDetail, Integer>
		implements UserAccountDetailService {

	@Resource(name = "userAccountDetailDaoImpl")
	private UserAccountDetailDao userAccountDetailDao;
	@Resource(name = "userAccountDaoImpl")
	private UserAccountDao userAccountDao;
	@Resource(name = "userAccountRechargeDaoImpl")
	private UserAccountRechargeDao userAccountRechargeDao;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "rechargeConfigServiceImpl")
	private RechargeConfigService rechargeConfigService;
	
	@Resource(name = "userHongbaoDaoImpl")
	private UserHongbaoDao userHongbaoDao;
	@Resource(name = "listingDaoImpl")
	private ListingDao listingDao;
	
	@Resource(name = "userAccountDetailDaoImpl")
	public void setBaseDao(UserAccountDetailDao UserAccountDetailDao) {
		super.setBaseDao(UserAccountDetailDao);
	}
	
//	@Override
//	public Integer save(UserAccountDetail userAccountDetail){
//		//修改账户信息
//		UserAccount userAccount = userAccountDetail.getUser().getAccount();
//		userAccount.setTotal(userAccount.getTotal().subtract(userAccountDetail.getMoney()));
//		userAccount.setAbleMoney(userAccount.getAbleMoney().subtract(userAccountDetail.getMoney()));
//		userAccountDao.update(userAccount);
//		userAccountDetail.setTotal(userAccount.getTotal());
//		userAccountDetail.setUseMoney(userAccount.getAbleMoney());
//		userAccountDetail.setNoUseMoney(userAccount.getUnableMoney());
//		userAccountDetail.setCollection(userAccount.getCollection());
//		userAccountDetail.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital());
//		userAccountDetail.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest());
//		userAccountDetail.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
//		userAccountDetail.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest());
//		
//		return userAccountDetailDao.save(userAccountDetail);
//		
//	}
	
	@Override
	public Integer saveUserAccountDetail(UserAccountDetail userAccountDetail){
		return userAccountDetailDao.save(userAccountDetail);
	}
	/**
	@Override
	public Integer saveUserAccountDetailByRecharge(UserAccountDetail userAccountDetail){
			
			
			BigDecimal recharge = userAccountDetail.getMoney();
			//添加一条充值记录
			UserAccountRecharge userAccountRecharge= new UserAccountRecharge();
			userAccountRecharge.setUser(userAccountDetail.getUser());
//			userAccountRecharge.setRechargeDate(new Date());//添加线下充值时间
			userAccountRecharge.setStatus(2);//【0：失败;1：成功;2:后台审核中;】
			userAccountRecharge.setMoney(recharge);
			RechargeConfig rechargeConfig = rechargeConfigService.find("background");
			userAccountRecharge.setRechargeInterface(rechargeConfig);//后台充值
			userAccountRecharge.setReturned("");
			userAccountRecharge.setType("0");
			userAccountRecharge.setRemark(userAccountDetail.getRemark());
			userAccountRecharge.setFee(new BigDecimal(0));
			userAccountRecharge.setReward(rechargeConfig.getPaymentFee(recharge));
			
			userAccountRecharge.setAdminOperator(adminService.getLoginAdmin());
			userAccountRecharge.setIpOperator(userAccountDetail.getOperatorIp());
			userAccountRechargeDao.save(userAccountRecharge);

			//添加充值状态
			RechargeStatus rs = new RechargeStatus();
			rs.setSaveState(0);
			rs.setComparisonState(0);
			rs.setUserAccountRecharge(userAccountRecharge);
			userAccountRecharge.setRechargeStatus(rs);
			return rechargeStatusDao.save(rs);
			
		}
		**/

	@Override
	public Integer saveUserAccountDetailByDebits(UserAccountDetail userAccountDetail,String type){
		
		BigDecimal	debitsMoney = userAccountDetail.getMoney();//操作金额
		//修改账户信息
		UserAccount userAccount = userAccountDao.loadLockTable(userAccountDetail.getUser());// 锁表
		
		if("0".equals(type)){
			userAccount.setTotal(userAccount.getTotal().subtract(debitsMoney));
			userAccount.setAbleMoney(userAccount.getAbleMoney().subtract(debitsMoney));
		}else if("1".equals(type)){
			userAccount.setTotal(userAccount.getTotal().add(debitsMoney));
			userAccount.setAbleMoney(userAccount.getAbleMoney().add(debitsMoney));
		}else if("2".equals(type)){
			userAccount.setTotal(userAccount.getTotal().add(debitsMoney));
			userAccount.setAbleMoney(userAccount.getAbleMoney().add(debitsMoney));
		}else if("3".equals(type)){
			userAccount.setTotal(userAccount.getTotal().add(debitsMoney));
			userAccount.setAwardMoney(userAccount.getAwardMoney().add(debitsMoney));//增加红包
		}else if("4".equals(type)){
			userAccount.setTotal(userAccount.getTotal().subtract(debitsMoney));
			userAccount.setAwardMoney(userAccount.getAwardMoney().subtract(debitsMoney));//扣除红包
		}else if("5".equals(type)){
			userAccount.setTasteMoney(userAccount.getTasteMoney().add(debitsMoney));//增加体验金
		}else if("6".equals(type)){
			userAccount.setTasteMoney(userAccount.getTasteMoney().subtract(debitsMoney));//扣除体验金
		}
		//userAccount.setUnableMoney(userAccount.getUnableMoney().subtract(debitsMoney));
		userAccountDao.update(userAccount);
		

		if("0".equals(type) || "1".equals(type) || "2".equals(type) || "5".equals(type) || "6".equals(type)){
			userAccountDetail.setTotal(userAccount.getTotal());
			userAccountDetail.setUseMoney(userAccount.getAbleMoney());
			userAccountDetail.setNoUseMoney(userAccount.getUnableMoney());
			userAccountDetail.setCollection(userAccount.getCollection());
			userAccountDetail.setOperatorer("管理员:"+adminService.getLoginAdmin().getUsername());
			userAccountDetail.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital());
			userAccountDetail.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest());
			userAccountDetail.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
			userAccountDetail.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest());
			userAccountDetail.setTasteMoney(userAccount.getTasteMoney());
			userAccountDetailDao.save(userAccountDetail);
		}
		
		
		if("3".equals(type)){
			//hongbao表增加数据
			UserHongbao hb = new UserHongbao();
			hb.setUser(userAccount.getUser());
			hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")+CommonUtil.getRandomString(5));
			hb.setName("赠送红包");
			hb.setMoney(debitsMoney);
			hb.setUsedMoney(BigDecimal.ZERO);
			hb.setStatus(0);
			Date d = new Date();
			hb.setStartTime(d);
			Listing listingQixain = listingDao.getListingBySign("tuijian_award2");//红包有效期
			Integer day_qixian = Integer.parseInt(listingQixain.getKeyValue());
			
			hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d, day_qixian)));
			hb.setSource(5);
			hb.setSourceString("赠送红包");
			hb.setSourceUserId(null);
			hb.setSourceBorrowId(null);
			hb.setUsedBorrowId(null);
			userHongbaoDao.save(hb);
		}
		return 0;
	}
	
	@Override
	public Pager findPager(Pager pager,Map<String ,Object> map){
		return userAccountDetailDao.findPager(pager,map);
	}
	
	
	public BigDecimal getFeeByType(String type,Date stareDate, Date endDate){
		BigDecimal ret = userAccountDetailDao.getFeeByType(type, stareDate, endDate);
		if (ret==null) {
			ret = BigDecimal.valueOf(0);
		}
		return ret;
	}
	
	public List<AccountDetail> getUserAccountDetailTotalByType(String type,Date startDate,Date endDate){
		return userAccountDetailDao.getUserAccountDetailTotalByType(type,startDate, endDate);
	}
	

	public List<UserAccountDetail> getAccountDetailList(Map<String ,Object> map){
		return userAccountDetailDao.getAccountDetailList(map);
	}

	
	public Pager findPagerByHql(Pager pager,Map<String ,Object> map){
		return userAccountDetailDao.findPagerByHql(pager,map);
	}
	
	public List<UserAccountDetailDTO> getAccountDetailListByHql(Map<String ,Object> map){
		return userAccountDetailDao.getAccountDetailListByHql(map);
	}
}
