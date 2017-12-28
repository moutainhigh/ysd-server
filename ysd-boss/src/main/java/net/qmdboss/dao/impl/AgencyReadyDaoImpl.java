package net.qmdboss.dao.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.AgencyReadyDao;
import net.qmdboss.entity.AgencyReady;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("agencyReadyDaoImpl")
public class AgencyReadyDaoImpl extends BaseDaoImpl<AgencyReady, Integer> implements AgencyReadyDao {

	public Pager findAgencyReadyPager(Pager pager,AgencyReady ad){
		Criteria criteria = getSession().createCriteria(AgencyReady.class);
		if(ad != null){
			criteria.add(Restrictions.eq("status",ad.getStatus()));
		}
		return super.findPager(pager, criteria);
	}
}
