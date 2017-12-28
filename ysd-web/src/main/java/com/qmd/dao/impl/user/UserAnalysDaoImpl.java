package com.qmd.dao.impl.user;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.UserAnalysDao;
import com.qmd.mode.user.UserAnalys;
import org.springframework.stereotype.Repository;
@Repository("userAnalysDao")
public class UserAnalysDaoImpl extends BaseDaoImpl<UserAnalys,Integer> implements UserAnalysDao {
}