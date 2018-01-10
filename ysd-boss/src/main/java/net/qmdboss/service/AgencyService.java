package net.qmdboss.service;

import net.qmdboss.entity.Agency;

import java.util.List;

public interface AgencyService extends BaseService<Agency, Integer>{
	

	public void updateAgency(Agency agency);
	
	public List<Agency> ListAgencyForUserid(Integer userid);
}
