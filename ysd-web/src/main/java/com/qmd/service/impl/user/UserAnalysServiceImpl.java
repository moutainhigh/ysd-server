package com.qmd.service.impl.user;

import com.qmd.dao.user.UserAnalysDao;
import com.qmd.mode.user.UserAnalys;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.UserAnalysService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service("userAnalysService")
public class UserAnalysServiceImpl extends BaseServiceImpl<UserAnalys,Integer> implements UserAnalysService {
@SuppressWarnings("unused")
@Resource
private UserAnalysDao userAnalysDao;
@Resource
public void setBaseDao(UserAnalysDao userAnalysDao) {
	super.setBaseDao(userAnalysDao);
}
}
