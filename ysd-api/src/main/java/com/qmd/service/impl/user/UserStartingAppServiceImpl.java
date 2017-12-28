package com.qmd.service.impl.user;

import com.qmd.dao.user.UserStartingAppDao;
import com.qmd.mode.user.UserStartingApp;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.UserStartingAppService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service("userStartingAppService")
public class UserStartingAppServiceImpl extends BaseServiceImpl<UserStartingApp,Integer> implements UserStartingAppService {
@SuppressWarnings("unused")
@Resource
private UserStartingAppDao userStartingAppDao;
@Resource
public void setBaseDao(UserStartingAppDao userStartingAppDao) {
	super.setBaseDao(userStartingAppDao);
}
}
