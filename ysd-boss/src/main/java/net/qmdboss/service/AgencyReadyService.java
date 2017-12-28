package net.qmdboss.service;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.AgencyReady;

public interface AgencyReadyService extends BaseService<AgencyReady, Integer>{
	
	public Pager findAgencyReadyPager(Pager pager,AgencyReady ad);
	
	public void copyAgency(AgencyReady ar);

}
