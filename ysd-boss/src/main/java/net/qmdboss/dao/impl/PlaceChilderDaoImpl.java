package net.qmdboss.dao.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.PlaceChilderDao;
import net.qmdboss.entity.PlaceChilder;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("placeChilderDaoImpl")
public class PlaceChilderDaoImpl  extends BaseDaoImpl<PlaceChilder, Integer> implements PlaceChilderDao {

	public Pager findPager(Pager pager,Map<String,Object> map){
		
		Criteria criteria = getSession().createCriteria(PlaceChilder.class);

		if(map.get("placeId") != null){
			criteria.createAlias("place", "place");
			criteria.add(Restrictions.eq("place.id", map.get("placeId")));
		}
		
		if(map.get("name") != null){
			criteria.add(Restrictions.like("name", "%" +map.get("name").toString() +"%"));
		}
		if(map.get("status") != null){
			criteria.add(Restrictions.eq("status", map.get("status")));
		}
		
		if(map.get("dateStart") != null){
			criteria.add(Restrictions.gt("createDate", map.get("dateStart")));
		}
		
		if(map.get("dateEnd") != null){
			criteria.add(Restrictions.le("createDate", map.get("dateEnd")));
		}
		
		return super.findPager(pager, criteria);
	}
}
