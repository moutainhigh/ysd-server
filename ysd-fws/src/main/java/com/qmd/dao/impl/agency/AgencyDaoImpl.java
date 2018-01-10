package com.qmd.dao.impl.agency;

import com.qmd.dao.agency.AgencyDao;
import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.mode.agency.Agency;
import org.springframework.stereotype.Repository;

@Repository("agencyDao")
public class AgencyDaoImpl extends BaseDaoImpl<Agency, Integer> implements
		AgencyDao {
}