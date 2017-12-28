package net.qmdboss.dao.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.bean.PlaceBean;
import net.qmdboss.dao.PlaceDao;
import net.qmdboss.entity.Place;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("placeDaoImpl")
public class PlaceDaoImpl extends BaseDaoImpl<Place, Integer> implements PlaceDao {


	public Pager findPager(Pager pager,Map<String,Object> map){
		Session session = getSession();
		StringBuffer hql = new StringBuffer();
		StringBuffer hqlcount = new StringBuffer();
		hql.append("SELECT t.id,t.create_date as createDate,t.modify_date as modifyDate,t.name ,t.remark, c.size FROM fy_place t LEFT JOIN (SELECT pc.place_id, COUNT(*) AS size FROM fy_place_childer pc where pc.status=1 GROUP BY pc.place_id) c ON c.place_id = t.id");
		hql.append(" WHERE 1=1 ");

		hqlcount.append("SELECT count(*) FROM fy_place t ");
		hqlcount.append("where 1=1");
		if (map.get("name") != null) {
			hql.append(" AND t.name =:name ");
			hqlcount.append(" AND t.name =:name ");
			
		}
		if(pager != null){
			hql.append(" LIMIT :pageBegin,:pageSize");
		}
		Query query = session.createSQLQuery(hql.toString());
		Query querycount = session.createSQLQuery(hqlcount.toString()).addScalar("count(*)",Hibernate.INTEGER);
		query.setResultTransformer(Transformers.aliasToBean(PlaceBean.class));
		
		if (map.get("name") != null) {
			query.setParameter("name", map.get("name"));
		}
		
		if(pager != null){
			int pageBegin = (pager.getPageNumber() - 1) * pager.getPageSize();
			if (pageBegin < 0)
				pageBegin = 0;
			query.setParameter("pageBegin", pageBegin, Hibernate.INTEGER);
			query.setParameter("pageSize", pager.getPageSize(), Hibernate.INTEGER);
		}

		pager.setTotalCount((Integer)querycount.uniqueResult());
		pager.setResult(query.list());
		
		
		return pager;
		
	}
}
