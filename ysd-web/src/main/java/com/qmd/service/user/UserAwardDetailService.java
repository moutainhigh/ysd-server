package com.qmd.service.user;

import com.qmd.mode.user.UserAwardDetail;
import com.qmd.service.BaseService;
import com.qmd.util.Pager;

import java.util.Map;

public interface UserAwardDetailService extends	BaseService<UserAwardDetail, Integer> {
	
	
	/**
	 * 分页查询 红包收跟支出 和奖励
	 * @param map
	 * @return qxw
	 */
	public Pager queryHbDetailList(Map<String,Object> map,Integer pageSize,Integer currentPage);
	
	
	
}