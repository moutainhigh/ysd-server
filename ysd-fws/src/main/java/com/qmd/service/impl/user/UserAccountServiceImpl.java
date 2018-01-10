package com.qmd.service.impl.user;


import com.qmd.dao.user.UserAccountDao;
import com.qmd.dao.user.UserAccountDetailDao;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.user.UserAccount;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.UserAccountService;
import com.qmd.util.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
/**
 * Service实现类 - 用户账户总表信息
 * ============================================================================
 */
@Service("userAccountService")
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccount, Integer> implements UserAccountService {
	
	@Resource
	UserAccountDao userAccountDao;
	
	@Resource
	UserAccountDetailDao userAccountDetailDao;
	
	@Resource
	ListingDao listingDao;
	
	@Resource
	public void setBaseDao(UserAccountDao userAccountDao) {
		super.setBaseDao(userAccountDao);
	}

	@Override
	public UserAccount getUserAccountByUserId(Integer userId) {
		UserAccount u = userAccountDao.getUserAccountByUserId(userId);
		return u == null ? new UserAccount():u;
	}
	
	public Pager getAccountRechargeByUserId(Pager pager ,Map<String,Object> map){
		return userAccountDao.getAccountRechargeByUserId(pager,map);
	}
	

	public Pager getAccountDetailByUserId(Pager pager,Map<String,Object> map){
		return userAccountDao.getAccountDetailByUserId(pager, map);
	}
	

	

	public UserAccountDetailDao getUserAccountDetailDao() {
		return userAccountDetailDao;
	}

	public void setUserAccountDetailDao(UserAccountDetailDao userAccountDetailDao) {
		this.userAccountDetailDao = userAccountDetailDao;
	}

	public BigDecimal getSumDepositMoney(Map<String,Object> map){
		return userAccountDao.getSumDepositMoney(map);
	}
}
