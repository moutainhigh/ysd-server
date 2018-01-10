package com.qmd.dao.impl.user;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.UserAwardDetailDao;
import com.qmd.mode.user.UserAwardDetail;
import org.springframework.stereotype.Repository;

@Repository("userAwardDetailDao")
public class UserAwardDetailDaoImpl extends
		BaseDaoImpl<UserAwardDetail, Integer> implements UserAwardDetailDao {
}