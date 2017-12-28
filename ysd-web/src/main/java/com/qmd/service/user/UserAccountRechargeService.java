package com.qmd.service.user;

import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.service.BaseService;

import java.util.List;
import java.util.Map;
/**
 * service 接口 充值记录
 * @author zhanf
 *
 */
public interface UserAccountRechargeService extends
		BaseService<UserAccountRecharge, Integer> {

	public List<UserAccountRecharge> getUserAccountRecharge(Map<String,Object> map);
}
