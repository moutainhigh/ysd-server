package com.qmd.service.user;

import com.qmd.mode.user.UserAwardDetail;
import com.qmd.service.BaseService;
import com.qmd.util.Pager;

import java.util.Map;

public interface UserAwardDetailService extends BaseService<UserAwardDetail, Integer> {
	
	public Pager queryHbDetailList(Map<String,Object> map,Integer pageSize,Integer currentPage);
}