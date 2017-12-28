package net.qmdboss.dao;

import net.qmdboss.entity.Agency;

import java.util.List;

public interface AgencyDao extends BaseDao<Agency, Integer> {
	
	public List<Agency> ListAgencyForUserid (Integer userid);

}
