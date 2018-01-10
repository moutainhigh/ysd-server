package com.qmd.dao.impl.user;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.AgencyDao;
import com.qmd.mode.user.Agency;
import org.springframework.stereotype.Repository;

@Repository("agencyDao")
public class AgencyDaoImpl extends BaseDaoImpl<Agency, Integer> implements
		AgencyDao {
}