package com.qmd.service.impl.user;

import com.qmd.dao.user.UserAwardDetailDao;
import com.qmd.mode.user.UserAwardDetail;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.UserAwardDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userAwardDetailService")
public class UserAwardDetailServiceImpl extends
		BaseServiceImpl<UserAwardDetail, Integer> implements
		UserAwardDetailService {
	@SuppressWarnings("unused")
	@Resource
	private UserAwardDetailDao userAwardDetailDao;

	@Resource
	public void setBaseDao(UserAwardDetailDao userAwardDetailDao) {
		super.setBaseDao(userAwardDetailDao);
	}
}
