package com.qmd.service.impl.agency;

import com.qmd.dao.agency.AgencyDao;
import com.qmd.mode.agency.Agency;
import com.qmd.service.agency.AgencyService;
import com.qmd.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("agencyService")
public class AgencyServiceImpl extends BaseServiceImpl<Agency, Integer>
		implements AgencyService {
	@SuppressWarnings("unused")
	@Resource
	private AgencyDao agencyDao;

	@Resource
	public void setBaseDao(AgencyDao agencyDao) {
		super.setBaseDao(agencyDao);
	}
}
