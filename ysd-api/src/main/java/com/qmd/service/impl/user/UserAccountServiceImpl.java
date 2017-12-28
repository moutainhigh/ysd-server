package com.qmd.service.impl.user;


import com.qmd.dao.user.UserAccountDao;
import com.qmd.dao.user.UserAccountDetailDao;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountDetail;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.UserAccountService;
import com.qmd.util.AccountDetailUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil.RoundType;
import com.qmd.util.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
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
	public void setBaseDao(UserAccountDao userAccountDao) {
		super.setBaseDao(userAccountDao);
	}

	@Override
	public void addUserAccount(UserAccount userAccount){
		this.userAccountDao.addUserAccount(userAccount);
	}
	@Override
	public UserAccount getUserAccountByUserId(Integer userId) {
		UserAccount u = userAccountDao.getUserAccountByUserId(userId);
		return u == null ? new UserAccount():u;
	}
	
	public List<UserAccountRecharge>  getAccountRechargeByNew(Integer maxs){
		return userAccountDao.getAccountRechargeByNew(maxs); 
	}
	public Pager getAccountRechargeByUserId(Pager pager ,Map<String,Object> map){
		return userAccountDao.getAccountRechargeByUserId(pager,map);
	}
	

	public List<UserAccountRecharge> getUserAccountRechargeList(Map<String,Object> map){
		return userAccountDao.getUserAccountRechargeList(map);
	}

	public Pager getAccountDetailByUserId(Pager pager,Map<String,Object> map){
		return userAccountDao.getAccountDetailByUserId(pager, map);
	}
	
	public UserAccountDetail getAccountDetailByUserIdSingle(Map<String,Object> map) {
		return userAccountDao.getAccountDetailByUserIdSingle(map);
	}

	public List<UserAccountDetail> getAccountDetailByUserIdList(Map<String,Object> map){
		return userAccountDao.getAccountDetailByUserIdList(map);
	}
	
	public List<UserAccount> getUserAccountAllRecodeList() {
		return userAccountDao.getUserAccountAllRecodeList();
	}
	

	public UserAccountDetailDao getUserAccountDetailDao() {
		return userAccountDetailDao;
	}

	public void setUserAccountDetailDao(UserAccountDetailDao userAccountDetailDao) {
		this.userAccountDetailDao = userAccountDetailDao;
	}

	@Override
	public boolean rollOutMoney(Integer id, BigDecimal rollTotal,String ip) {
		// TODO Auto-generated method stub
		boolean flg;
		UserAccount userAccount = userAccountDao.getUserAccountByUserId(id);
		if(userAccount!=null){
			userAccount=userAccountDao.getForUpdate(userAccount.getId(), userAccount);
			
			// 续投资金不足，不操作
			if (userAccount.getContinueTotal().doubleValue() <=0 || userAccount.getContinueTotal().compareTo(rollTotal) == -1) {
				return false;
			}
			
			userAccount.setAbleMoney(CommonUtil.setPriceScale(userAccount.getAbleMoney().add(rollTotal),RoundType.roundHalfUp));
			userAccount.setContinueTotal(CommonUtil.setPriceScale(userAccount.getContinueTotal().subtract(rollTotal),RoundType.roundHalfUp));
			userAccountDao.update(userAccount);
			// 资金记录---还款之本金
			UserAccountDetail userAccountDetail = AccountDetailUtil.fillUserAccountDetail("roll_out_money", 
					rollTotal,id, "客户从续投内转出"+rollTotal+"元",ip, userAccount);
			this.userAccountDetailDao.save(userAccountDetail);
			flg=true;
		}else{
			flg=false;
		}
		return flg;
	}
	
}
