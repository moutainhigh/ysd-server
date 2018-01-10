package net.qmdboss.dao.impl;

import net.qmdboss.dao.AgencyDao;
import net.qmdboss.entity.Agency;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("agencyDaoImpl")
public class AgencyDaoImpl extends BaseDaoImpl<Agency, Integer> implements AgencyDao {

	public List<Agency> ListAgencyForUserid(Integer userid){
		Criteria criteria = getSession().createCriteria(Agency.class);
		criteria.add(Restrictions.eq("userid",  userid));
		return criteria.list();
	} 
}
