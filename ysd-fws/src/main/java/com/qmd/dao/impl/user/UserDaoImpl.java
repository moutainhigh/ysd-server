package com.qmd.dao.impl.user;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.UserDao;
import com.qmd.mode.admin.Admin;
import com.qmd.mode.agency.Agency;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.util.ConstantUtil;
import com.qmd.util.Pager;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao {

	@Override
	public User getUser(Map<String,Object> map) {
		// TODO Auto-generated method stub
		User user = this.getSqlSession().selectOne("User.selectByMap", map);
		return user;
	}
	
	@Override
	public List<User> getUserList(Map<String,Object> map) {
		return this.getSqlSession().selectList("User.selectByMap", map);
	}

	@Override
	public Pager queryUser(Pager pager,Map<String,Object> map) {
		// TODO Auto-generated method stub
		//List<User> userList = this.getSqlSession().selectList("User.queryUser");
		
		pager.setTotalCount(Integer.parseInt(this.getSqlSession().selectOne("User.queryCount").toString()));
		map.put("pager", pager);
		
		pager.setResult( this.getSqlSession().selectList("User.queryUser",map));
		return pager;
	}
	
	@Override
	public void addUser(User user){
//		user.setTypeId(0);//0：投资者;1：借款人;2:投资者申请借款人
		user.setEmailStatus(ConstantUtil.APPLY_STATUS_NO);
		user.setRealStatus(ConstantUtil.APPLY_STATUS_NO);
//		user.setPhoneStatus(ConstantUtil.APPLY_STATUS_NO);
		user.setPhoneStatus(ConstantUtil.APPLY_STATUS_YES);//注册时手机认证 通过
		user.setSceneStatus(ConstantUtil.APPLY_STATUS_NO);
		user.setInviteMoney(new BigDecimal(0));
		user.setStatus(0);
		user.setIsEnabled(true);
		user.setIsLock(false);
		user.setLoginTime(1);
		user.setLastTime(new Date());
		this.getSqlSession().insert("User.addUser",user);
	}
	
	@Override
	public void update(User user){
		this.getSqlSession().update("User.update", user);
	}
	@Override
	public void updateUserByLoginSuccess(User user){
		this.getSqlSession().update("User.updateUserByLoginSuccess", user);
	}
	
	public void updateProfile(User user){
		this.getSqlSession().update("User.updateProfile", user);
	}
	
	@Override
	public void updateRealName(User user){
		this.getSqlSession().update("User.updateRealName", user);
	}
	
	public void updateLitpic(User user){
		this.getSqlSession().update("User.updateLitpic",user);
	}
	
	
	@Override
	public void updateEmail(User user){
		this.getSqlSession().update("User.updateEmail", user);
	}
	
	@Override
	public void updatePhone(User user){
		this.getSqlSession().update("User.updatePhone", user);
	}
	
	@Override
	public void updateAvatar(User user){
		this.getSqlSession().update("User.updateAvatar", user);
	}
	@Override
	public void updateTypeId(User user){
		this.getSqlSession().update("User.updateTypeid", user);
		
	}
	
	
	@Override
	public void updateVipStatus(User user){
		this.getSqlSession().update("User.updateVipStatus", user);
	}

	@Override
	public void updatePassowrd(User user){
		this.getSqlSession().update("User.updatePassword",user);
	}
	@Override
	public void updateQuestion(User user){
		this.getSqlSession().update("User.updateQuestion", user);
	}


	public void addAccountBank(AccountBank accountBank){
		this.getSqlSession().insert("User.addAccountBank", accountBank);
	}
	
	public void updateAccountBank(AccountBank accountBank){
		this.getSqlSession().update("User.updateAccountBank", accountBank);
	}
	
	public List<AccountBank> queryAccountBank(Integer userId){
		List<AccountBank> accountBankList = this.getSqlSession().selectList("User.queryAccountBank",userId);
		return accountBankList;
	}
	
	public void deleteAccountBank(Integer id){
		this.getSqlSession().delete("User.deleteAccountBank", id);
	}
	
	public List<Admin> getKefuAdminList(){
		List<Admin> kefuAdminList = this.getSqlSession().selectList("User.getKefuAdminList");
		return kefuAdminList;
	}
	
	
	public Agency getAgencyByUserid(Integer userId){
		return this.getSqlSession().selectOne("User.getAgencyByUserid", userId);
	}
	
	public void updateAgency(Agency agency){
		this.getSqlSession().update("User.updateAgency", agency);
	}
	
	

	public Pager querySpreadListByMap(Pager pager,Map<String,Object> map){
		Integer count =Integer.parseInt(this.getSqlSession().selectOne("User.querySpreadCountByMap",map).toString()) ;
		pager.setTotalCount(count);
		map.put("pager", pager);
		
		List<User> uList = this.getSqlSession().selectList("User.querySpreadListByMap", map);
		pager.setResult(uList);
		return pager;
	}
	
	public int updateIdentify(User user){
		return this.getSqlSession().update("User.updateIdentify", user);
	}
	
	
	@Override
	public void updateTenderAuto(User user){
		this.getSqlSession().update("User.updateTenderAuto", user);
	}
	
	@Override
	public Integer queryTenderAutoRank(Integer id){
		return (Integer)this.getSqlSession().selectOne("User.queryTenderAutoRank", id);
	}
	

	public Pager queryJkrlb(Pager pager,Map<String,Object> map){
		pager.setTotalCount(Integer.parseInt(this.getSqlSession().selectOne("User.queryJkrlbCount",map).toString()));
		map.put("pager", pager);
		
		pager.setResult( this.getSqlSession().selectList("User.queryJkrlb",map));
		return pager;
	}

}
