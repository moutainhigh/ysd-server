package com.qmd.dao.impl.user;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.UserDao;
import com.qmd.mode.admin.Admin;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.util.ConstantUtil;
import com.qmd.util.Pager;
import com.qmd.util.bean.UserAward;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

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
	public User getByUserId(Integer id) {
		// TODO Auto-generated method stub
		User user = this.getSqlSession().selectOne("User.getById", id);
		return user;
	}
	
	@Override
	public List<User> getUserList(Map<String,Object> map) {
		
		if(map != null){
			if(map.get("pageStart") != null && StringUtils.isNotEmpty(map.get("pageStart").toString())){
				map.put("pageStart", map.get("pageStart").toString());
			}else{
				map.put("pageStart", 0);
			}
			
			if(map.get("pageSize") != null && StringUtils.isNotEmpty(map.get("pageSize").toString())){
				map.put("pageSize", map.get("pageSize").toString());
			}else{
				map.put("pageSize", 10);
			}
		}
		
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
		user.setPhoneStatus(ConstantUtil.APPLY_STATUS_NO);
		user.setSceneStatus(ConstantUtil.APPLY_STATUS_NO);
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
	public void updatePhone(User user){
		this.getSqlSession().update("User.updatePhone", user);
	}
	
	@Override
	public void updateTypeId(User user){
		this.getSqlSession().update("User.updateTypeid", user);
		
	}
	
	@Override
	public void updatePassowrd(User user){
		this.getSqlSession().update("User.updatePassword",user);
	}
	
	@Override
	public void updateTenderAuto(User user){
		this.getSqlSession().update("User.updateTenderAuto", user);
	}
	
	@Override
	public Integer queryTenderAutoRank(Integer id){
		return (Integer)this.getSqlSession().selectOne("User.queryTenderAutoRank", id);
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
	
	
	public List<User> queryByRanking(Map<String ,Object> map){
		return this.getSqlSession().selectList("User.queryByRanking", map);
	}
	
	public Pager querySpreadListByMap(Pager pager,Map<String,Object> map){
		Integer count =Integer.parseInt(this.getSqlSession().selectOne("User.querySpreadCountByMap",map).toString()) ;
		pager.setTotalCount(count);
		map.put("pager", pager);
		
		List<User> uList = this.getSqlSession().selectList("User.querySpreadListByMap", map);
		pager.setResult(uList);
		return pager;
	}
	/**修改手机验证码**/
	public void updatePhoneCode(User user){
		this.getSqlSession().update("User.updatePhoneCode", user);
	}


	public Pager queryMyFriendsListByMap(Pager pager,Map<String,Object> map){
		Integer count =Integer.parseInt(this.getSqlSession().selectOne("User.queryMyFriendsCountByMap",map).toString()) ;
		pager.setTotalCount(count);
		map.put("pager", pager);
		List<UserAward> uList = this.getSqlSession().selectList("User.queryMyFriendsListByMap", map);
		pager.setResult(uList);
		return pager;
	}
	@Override
	public void updateToken(User user) {
		this.getSqlSession().update("User.updateToken", user);
	}
	@Override
	public User getByToken(String token) {
		return this.getSqlSession().selectOne("User.getByToken", token);
	}
	@Override
	public void setNullToken(Integer id) {
		this.getSqlSession().update("User.setNullToken", id);
	}

}
