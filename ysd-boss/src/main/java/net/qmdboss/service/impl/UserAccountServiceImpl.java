package net.qmdboss.service.impl;

import net.qmdboss.DTO.UserAccountDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.UserAccountDao;
import net.qmdboss.entity.User;
import net.qmdboss.entity.UserAccount;
import net.qmdboss.service.UserAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Service实现类 - 用户账户类
 * ============================================================================
 */
@Service("userAccountServiceImpl")
public class UserAccountServiceImpl extends
		BaseServiceImpl<UserAccount, Integer> implements UserAccountService {

	@Resource(name = "userAccountDaoImpl")
	private UserAccountDao userAccountDao;
	
	@Resource(name = "userAccountDaoImpl")
	public void setBaseDao(UserAccountDao UserAccountDao) {
		super.setBaseDao(UserAccountDao);
	}
	
	public Pager findPagerByMoney(Pager pager,String type,String amount){
		return userAccountDao.findPagerByMoney(pager, type, amount);
	}

	
	public List<User> findAccountList(String username,String type,String amount){
		return userAccountDao.findAccountList(username,type,amount);
	}

	
	public Pager findPagerByUser(Pager pager,Map<String,Object> map){
		return userAccountDao.findPagerByUser(pager, map);
	}
	
	public List<User> getPagerByUser(Map<String,Object> map){
		return userAccountDao.getPagerByUser( map);
	}
	public List<UserAccountDTO> findAccountDTOByUser(Map<String,Object> map){
		return userAccountDao.findAccountDTOByUser( map);
	}
	
	public BigDecimal getAbleMoneyAll(Integer userTypeId){
		return userAccountDao.getAbleMoneyAll(userTypeId);
	}
	
	public BigDecimal getUserAccountSum(Integer userTypeId,String sumName) {
		return userAccountDao.getUserAccountSum(userTypeId, sumName);
	}
	
}
