package com.qmd.dao.impl.user;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.UserStartingAppDao;
import com.qmd.mode.user.UserStartingApp;
import org.springframework.stereotype.Repository;
@Repository("userStartingAppDao")
public class UserStartingAppDaoImpl extends BaseDaoImpl<UserStartingApp,Integer> implements UserStartingAppDao {
}