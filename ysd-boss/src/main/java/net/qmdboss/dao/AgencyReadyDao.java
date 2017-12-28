package net.qmdboss.dao;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.AgencyReady;

public interface AgencyReadyDao extends BaseDao<AgencyReady, Integer> {

	public Pager findAgencyReadyPager(Pager pager,AgencyReady ad);
}
