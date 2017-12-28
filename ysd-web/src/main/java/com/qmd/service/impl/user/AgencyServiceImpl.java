package com.qmd.service.impl.user;

import com.qmd.dao.user.AgencyDao;
import com.qmd.mode.user.Agency;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.AgencyService;
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
