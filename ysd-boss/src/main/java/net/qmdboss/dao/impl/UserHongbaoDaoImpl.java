package net.qmdboss.dao.impl;

import net.qmdboss.DTO.UserHongbaoBossDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.UserHongbaoDao;
import net.qmdboss.entity.UserHongbao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("userHongbaoDaoImpl")
public class UserHongbaoDaoImpl extends BaseDaoImpl<UserHongbao, Integer> implements UserHongbaoDao {

	public List<UserHongbao> getUserHongbaoList(Map<String ,Object> map){
		Criteria criteria = getSession().createCriteria(UserHongbao.class);
		if(map!= null){
			
			Object userId = map.get("userId");
			if(userId!=null){
				criteria.add(Restrictions.eq("userId", Integer.parseInt(userId.toString())));
			}
			Object status = map.get("status");
			if(status!=null){
				criteria.add(Restrictions.eq("status", Integer.parseInt(status.toString())));
			}
			Object usedBorrowId = map.get("usedBorrowId");
			if(usedBorrowId!=null){
				criteria.add(Restrictions.eq("usedBorrowId", Integer.parseInt(usedBorrowId.toString())));
			}
			
			
			
		}
		
		return criteria.list();
	}
	
	

	public void saveLosts(List<UserHongbao> hbList){
		int i=0;
		for(UserHongbao s: hbList){
			super.save(s);
			if (i % 20 == 0) {
                flush();
                clear();
            }
			i++;
		}
	}



	public Pager userHongbaoPage(Pager pager, Map<String, Object> map) {
		

		Session session = getSession();
		
		StringBuffer hqlcount = new StringBuffer();
		StringBuffer hql = new StringBuffer();
		StringBuffer hqlCenter = new StringBuffer();
		
		hqlcount.append("SELECT COUNT(*) ");
		hql.append("select t.id as id,u.username as username,t.name as hongbaoName,t.money as hongbaoMoney,t.exp_date as expDate ,");
		hql.append(" t.end_time as endTime,t.limit_end as limitEnd,t.status as status,t.modify_date as modifyDate,t.limit_start as limitStart,  ");
		hql.append(" t.invest_full_momey as investFullMomey ,b.name as borrowName ");
		
		hqlCenter.append(" FROM fy_user_hongbao AS t  left join fy_user as u on u.id=t.user_id left join fy_borrow as b on t.used_borrow_id=b.id where 1=1");
	
		
		if(map.get("beginDate") != null && map.get("endDate")!= null){
			hqlCenter.append(" and t.modify_date >= :beginDate");
			hqlCenter.append(" and t.modify_date <= :endDate");
		}
		if(map.get("hongbaoName") != null && StringUtils.isNotBlank(map.get("hongbaoName").toString())){
			hqlCenter.append(" AND t.name like :hongbaoName");
		}
		
		if(map.get("username") != null && StringUtils.isNotBlank(map.get("username").toString())){
			hqlCenter.append(" AND u.username like :username");
		}
		if(map.get("status") != null && StringUtils.isNotBlank(map.get("status").toString())){
			hqlCenter.append(" AND t.status = :status");
		}
		
		hqlcount.append(hqlCenter.toString());
		
		System.out.println(hql.toString());
		Query querycount = session.createSQLQuery(hqlcount.toString()).addScalar("count(*)", Hibernate.INTEGER);
		
		hql.append(hqlCenter.toString());
		System.out.println(hql.toString());
		hql.append(" order by t.status asc,t.id desc  ");
		if (pager != null) {
			hql.append(" LIMIT :pageBegin,:pageSize");
		}
		Query query = session.createSQLQuery(hql.toString());
		
		if(map.get("beginDate") != null && map.get("endDate")!= null){
			querycount.setParameter("beginDate", map.get("beginDate"));
			querycount.setParameter("endDate", map.get("endDate"));
			System.out.println(map.get("beginDate")+"-------"+map.get("endDate"));
			
			query.setParameter("beginDate", map.get("beginDate"));
			query.setParameter("endDate", map.get("endDate"));
		}
		
		if(map.get("hongbaoName") != null && StringUtils.isNotBlank(map.get("hongbaoName").toString())){
			querycount.setParameter("hongbaoName","%"+ map.get("hongbaoName") +"%");
			query.setParameter("hongbaoName","%"+ map.get("hongbaoName") +"%");
		}

		if(map.get("username") != null && StringUtils.isNotBlank(map.get("username").toString())){
			querycount.setParameter("username","%"+ map.get("username") +"%");
			query.setParameter("username","%"+ map.get("username") +"%");
		}
		if(map.get("status") != null && StringUtils.isNotBlank(map.get("status").toString())){
			querycount.setParameter("status",map.get("status"));
			query.setParameter("status",map.get("status"));
		}
		
		
		
		if (pager != null) {
			pager.setTotalCount((Integer) querycount.uniqueResult());
			int pageBegin = (pager.getPageNumber() - 1) * pager.getPageSize();
			if (pageBegin < 0) pageBegin = 0;	
			query.setParameter("pageBegin", pageBegin, Hibernate.INTEGER);
			query.setParameter("pageSize", pager.getPageSize(), Hibernate.INTEGER);
		
			query.setResultTransformer(Transformers.aliasToBean(UserHongbaoBossDTO.class));
			
			pager.setResult(query.list());
			return pager;	
		
		}else{
			pager=new Pager();
			
			query.setResultTransformer(Transformers.aliasToBean(UserHongbaoBossDTO.class));
			
			pager.setResult(query.list());
			return pager;	
				
		}
	}




	public List<UserHongbaoBossDTO> findUserHongbaoList(Map<String, Object> map) {

		Session session = getSession();
		

		StringBuffer hql = new StringBuffer();
		StringBuffer hqlCenter = new StringBuffer();
		

		hql.append("select t.id as id,u.username as username,t.name as hongbaoName,t.money as hongbaoMoney,t.exp_date as expDate ,");
		hql.append(" t.end_time as endTime,t.limit_end as limitEnd,t.status as status,t.modify_date as modifyDate,t.limit_start as limitStart , ");
		hql.append(" t.invest_full_momey as investFullMomey ,b.name as borrowName");
		
		hqlCenter.append(" FROM fy_user_hongbao AS t  left join fy_user as u on u.id=t.user_id left join fy_borrow as b on t.used_borrow_id=b.id where 1=1");
	
		
		if(map.get("beginDate") != null && map.get("endDate")!= null){
			hqlCenter.append(" and t.modify_date >= :beginDate");
			hqlCenter.append(" and t.modify_date <= :endDate");
		}
		if(map.get("hongbaoName") != null && StringUtils.isNotBlank(map.get("hongbaoName").toString())){
			hqlCenter.append(" AND t.name like :hongbaoName");
		}
		
		if(map.get("username") != null && StringUtils.isNotBlank(map.get("username").toString())){
			hqlCenter.append(" AND u.username like :username");
		}
		if(map.get("status") != null && StringUtils.isNotBlank(map.get("status").toString())){
			hqlCenter.append(" AND t.status = :status");
		}
		

		hql.append(hqlCenter.toString());
		System.out.println(hql.toString());
		hql.append(" order by t.status asc,t.id desc  ");
	
		Query query = session.createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.aliasToBean(UserHongbaoBossDTO.class));
		
		if(map.get("beginDate") != null && map.get("endDate")!= null){

			query.setParameter("beginDate", map.get("beginDate"));
			query.setParameter("endDate", map.get("endDate"));
		}
		
		if(map.get("hongbaoName") != null && StringUtils.isNotBlank(map.get("hongbaoName").toString())){
			query.setParameter("hongbaoName","%"+ map.get("hongbaoName") +"%");
		}

		if(map.get("username") != null && StringUtils.isNotBlank(map.get("username").toString())){
			
			query.setParameter("username","%"+ map.get("username") +"%");
		}
		if(map.get("status") != null && StringUtils.isNotBlank(map.get("status").toString())){
			
			query.setParameter("status",map.get("status"));
		}
		
		
		
	
		return  query.list();
	}
	
	
		
		
		
		
		
		
	
}
