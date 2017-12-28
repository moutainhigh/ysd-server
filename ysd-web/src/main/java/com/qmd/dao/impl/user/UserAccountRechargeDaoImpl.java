package com.qmd.dao.impl.user;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.UserAccountRechargeDao;
import com.qmd.mode.user.UserAccountRecharge;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository("userAccountRechargeDao")
public class UserAccountRechargeDaoImpl extends BaseDaoImpl<UserAccountRecharge,Integer> implements UserAccountRechargeDao {

	@Override
	public UserAccountRecharge get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserAccountRecharge load(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserAccountRecharge> getAllList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getTotalCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer save(UserAccountRecharge entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(UserAccountRecharge entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(UserAccountRecharge entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer[] ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public void evict(Object object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UserAccountRecharge> getUserAccountRechargeList(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<UserAccountRecharge> userAccountRechargeList =  this.getSqlSession().selectList("UserAccountRecharge.getUserAccountRechargeList", map);
		 return userAccountRechargeList;
	}

}
