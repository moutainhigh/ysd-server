package com.qmd.dao.impl.user;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.UserAccountDao;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountDetail;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.util.Pager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("userAccountDao")
public class UserAccountDaoImpl extends BaseDaoImpl<UserAccount,Integer> implements UserAccountDao {

	@Override
	public void addUserAccount(UserAccount userAccount){
		this.getSqlSession().insert("UserAccount.addUserAccount",userAccount);
	}
	
	@Override
	public void updateAll(UserAccount userAccount) {
		this.getSqlSession().update("UserAccount.updateALL",userAccount);
	}

	@Override
	public UserAccount getUserAccountByUserId(Integer userId) {
		UserAccount userAccount = this.getSqlSession().selectOne("UserAccount.getUserAccountByUserId", userId);
		return userAccount;
	}
	
	@Override
	public List<UserAccountRecharge> getAccountRechargeByNew(Integer maxs){
		List<UserAccountRecharge> userAccountRechargeList = this.getSqlSession().selectList("UserAccount.getAccountRechargeByNew",maxs);
		return userAccountRechargeList;
	}
	
	@Override
	public Pager getAccountRechargeByUserId(Pager pager,Map<String,Object> map){
		Integer count =Integer.parseInt(this.getSqlSession().selectOne("UserAccount.queryCountAccountRechargeByUserId",map).toString()) ;
		pager.setTotalCount(count);
		map.put("pager", pager);
		List<UserAccountRecharge> list = getUserAccountRechargeList(map);
		pager.setResult(list);
		return pager;
	}

	public List<UserAccountRecharge> getUserAccountRechargeList(Map<String,Object> map){
		List<UserAccountRecharge> list= this.getSqlSession().selectList("UserAccount.getAccountRechargeByUserId",map);
		return list;
	}
	
	
	public Pager getAccountDetailByUserId(Pager pager,Map<String,Object> map){
		Integer count =Integer.parseInt(this.getSqlSession().selectOne("UserAccount.queryCountAccountDetailByUserId",map).toString()) ;
		pager.setTotalCount(count);
		map.put("pager", pager);
		List<UserAccountDetail> list = getAccountDetailByUserIdList(map);
		pager.setResult(list);
		return pager;
	}
	
	public UserAccountDetail getAccountDetailByUserIdSingle(Map<String,Object> map){
		UserAccountDetail userAccountDetail = this.getSqlSession().selectOne("UserAccount.getAccountDetailByUserIdSingle", map);
		return userAccountDetail;
	}
	
	public List<UserAccountDetail> getAccountDetailByUserIdList(Map<String,Object> map){
		this.getSqlSession().update("UserAccount.updateToLook",map);
		List<UserAccountDetail> list = this.getSqlSession().selectList("UserAccount.getAccountDetailByUserId",map);
		return list;
	}
	
	
	public List<UserAccount> getUserAccountAllRecodeList(){
		List<UserAccount> list = this.getSqlSession().selectList("UserAccount.getUserAccountAllRecodeList");
		return list;
	}
		
}
