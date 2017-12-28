package com.qmd.dao.impl.report;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.report.LogIdentityDao;
import com.qmd.mode.report.LogIdentity;
import org.springframework.stereotype.Repository;

@Repository("logIdentityDao")
public class LogIdentityDaoImpl extends BaseDaoImpl<LogIdentity, Integer>  implements LogIdentityDao {

}
