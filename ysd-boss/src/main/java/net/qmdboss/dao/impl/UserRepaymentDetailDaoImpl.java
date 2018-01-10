package net.qmdboss.dao.impl;

import net.qmdboss.DTO.UserRepaymentDetailDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.UserRepaymentDetailDao;
import net.qmdboss.entity.UserRepaymentDetail;
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

/**
 * userRepaymentDetailDaoImpl 投资人获得还款明细dao实现类
 * @author zhanf
 *
 */
@Repository("userRepaymentDetailDaoImpl")
public class UserRepaymentDetailDaoImpl extends
		BaseDaoImpl<UserRepaymentDetail, Integer> implements
		UserRepaymentDetailDao {
	
	public Pager findUserRepaymentDetailPager(Pager pager,String status){
		
		Criteria criteria = getSession().createCriteria(UserRepaymentDetail.class);
//		if(StringUtils.isNotEmpty(pager.getKeyword()) &&  pager.getSearchBy().equals("user.username")){
//			criteria.createAlias("user", "user");
//			criteria.add(Restrictions.eq("user.username", pager.getKeyword()));
//			pager.setSearchBy("");
//		}
//		if(StringUtils.isNotEmpty(pager.getKeyword()) &&  pager.getSearchBy().equals("borrow.name")){
//			criteria.createAlias("borrow", "borrow");
//			criteria.add(Restrictions.eq("borrow.name", pager.getKeyword()));
//			pager.setSearchBy("");
//		}
		if (StringUtils.isNotEmpty(status)) {
			criteria.add(Restrictions.eq("status", status));
		}
		return super.findPager(pager, criteria);
	}
	
	public Pager findUserRepaymentDetailPager(Pager pager,
			Map<String, Object> map) {

		Criteria criteria = getSession().createCriteria(
				UserRepaymentDetail.class);

		if (map.get("startDate") != null) {
			criteria.add(Restrictions.ge("repaymentDate", map.get("startDate")));
		}
		if (map.get("endDate") != null) {
			criteria.add(Restrictions.le("repaymentDate", map.get("endDate")));
		}
		
		if (StringUtils.isNotEmpty((String)map.get("username"))) {
			criteria.add(Restrictions.like("user.username", "%"+(String)map.get("username")+"%"));
		}
		
		if (StringUtils.isNotEmpty((String)map.get("borrowName"))) {
			criteria.add(Restrictions.like("borrow.name", "%"+(String)map.get("borrowName")+"%"));
		}

		return super.findPager(pager, criteria);
	}
	
	public List<UserRepaymentDetail> findUserRepaymentDetailList(
			Map<String, Object> map) {

		Criteria criteria = getSession().createCriteria(
				UserRepaymentDetail.class);

		if (map.get("startDate") != null) {
			criteria.add(Restrictions.ge("repaymentDate", map.get("startDate")));
		}
		if (map.get("endDate") != null) {
			criteria.add(Restrictions.le("repaymentDate", map.get("endDate")));
		}
		
		if (StringUtils.isNotEmpty((String)map.get("username"))) {
			criteria.createAlias("user", "user");
			criteria.add(Restrictions.like("user.username", "%"+(String)map.get("username")+"%"));
		}
		
		if (StringUtils.isNotEmpty((String)map.get("borrowName"))) {
			criteria.createAlias("borrow", "borrow");
			criteria.add(Restrictions.like("borrow.name", "%"+(String)map.get("borrowName")+"%"));
		}

		//return super.findPager(pager, criteria);
		return criteria.list();
	}

	@Override
	public Pager findUserRepaymentDetailPagerByY(Pager pager,Map<String, Object> map) {
		// TODO Auto-generated method stub


		Session session = getSession();
		StringBuffer hql = new StringBuffer();
		StringBuffer hqlcount = new StringBuffer();
		StringBuffer hqlx= new StringBuffer();
		
		hqlcount.append("SELECT count(*)   ");

		hql.append("select t.id as id,u.username as username,b.name as borrowName,b.time_limit as timeLimit, ");
		hql.append(" t.borrow_divides as borrowDivides,t.repayment_account as repaymentAccount,t.repayment_date as repaymentDate, ");
		hql.append(" pc.name as placeName,t.borrow_periods as borrowPeriods,t.status as status  ");
		
		hqlx.append(" from `fy_user_repayment_detail` t  LEFT JOIN fy_borrow b  ON b.id = t.`borrow_id` ");  
		hqlx.append(" LEFT JOIN fy_user u          ON u.id = t.`user_id` ");
		hqlx.append(" LEFT JOIN fy_user_analys ua  ON ua.`user_id` = t.`user_id` ");
		hqlx.append(" LEFT JOIN fy_place_childer pc ON pc.id = ua.place_childer_id  where 1=1 ");
		
		hqlcount.append(hqlx);
		hql.append(hqlx);
		
		if (map.get("startDate") != null) {
			hql.append(" AND t.repayment_date >= :startDate ");
			hqlcount.append(" AND t.repayment_date  >= :startDate ");
		}
		if (map.get("endDate") != null) {
			hql.append(" AND t.repayment_date <= :endDate ");
			hqlcount.append(" AND t.repayment_date  <= :endDate ");
		}
		

		
		if (StringUtils.isNotEmpty((String)map.get("username"))) {
			hql.append(" AND u.username like '%"+map.get("username")+"%' ");
			hqlcount.append(" AND u.username like  '%"+map.get("username")+"%' ");
		}
		
		if (StringUtils.isNotEmpty((String)map.get("borrowName"))) {
			hql.append(" AND b.name like '%"+map.get("borrowName")+"%' ");
			hqlcount.append(" AND b.name like  '%"+map.get("borrowName")+"%' ");
		}
		hql.append(" order by t.id desc  ");
		if (pager != null) {
			hql.append(" LIMIT :pageBegin,:pageSize");
		}
		
		Query querycount = session.createSQLQuery(hqlcount.toString()).addScalar("count(*)", Hibernate.INTEGER);;
		Query query = session.createSQLQuery(hql.toString());
	
		
		if(map.get("startDate") !=null){
			query.setParameter("startDate", map.get("startDate"));
			querycount.setParameter("startDate", map.get("startDate"));
			
		}
		if(map.get("endDate") !=null){
			query.setParameter("endDate", map.get("endDate"));
			querycount.setParameter("endDate", map.get("endDate"));
			
		}
		

		if (pager != null) {
			pager.setTotalCount((Integer) querycount.uniqueResult());
			int pageBegin = (pager.getPageNumber() - 1) * pager.getPageSize();
			if (pageBegin < 0) pageBegin = 0;	
			query.setParameter("pageBegin", pageBegin, Hibernate.INTEGER);
			query.setParameter("pageSize", pager.getPageSize(), Hibernate.INTEGER);
		
			query.setResultTransformer(Transformers.aliasToBean(UserRepaymentDetailDTO.class));
			
			pager.setResult(query.list());
			return pager;	
		
		}else{
			pager=new Pager();
			
			query.setResultTransformer(Transformers.aliasToBean(UserRepaymentDetailDTO.class));
			
			pager.setResult(query.list());
			return pager;	
				
		}
		
		

	
		
	
		
	
	}

}
