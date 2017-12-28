package com.qmd.dao.user;

import com.qmd.dao.BaseDao;
import com.qmd.mode.user.UserAccountRecharge;

import java.util.List;
import java.util.Map;

public interface UserAccountRechargeDao extends BaseDao<UserAccountRecharge,Integer> {

	public List<UserAccountRecharge> getUserAccountRechargeList(Map<String, Object> map);
}
