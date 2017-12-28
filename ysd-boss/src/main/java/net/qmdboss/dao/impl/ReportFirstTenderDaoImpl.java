package net.qmdboss.dao.impl;

import net.qmdboss.bean.BorrowTenderItem;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.ReportFirstTenderDao;
import net.qmdboss.entity.ReportFirstTender;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository("reportFirstTenderDaoImpl")
public class ReportFirstTenderDaoImpl extends BaseDaoImpl<ReportFirstTender, Integer> implements ReportFirstTenderDao {

	public Pager findPagerByHsql(Pager pager,Map<String, Object> map){

		Session session = getSession();
		StringBuffer hqlcount = new StringBuffer();
		
		hqlcount.append("SELECT COUNT(*) FROM fy_report_first_tender AS t where 1=1 ");
		
		if(StringUtils.isNotEmpty(pager.getKeyword())){
			map.put("placeChilderName", pager.getKeyword());
		}
		if(map.get("beginDate") !=null){
			hqlcount.append(" and t.tender_date >= :beginDate");
		}
		if(map.get("endDate") !=null){
			hqlcount.append(" and t.tender_date <= :endDate");
		}
		
		if (map.get("placeChilderName") != null) {
			
			hqlcount.append(" AND t.place_childer_name like :placeChilderName ");
		}
		Query querycount = session.createSQLQuery(hqlcount.toString()).addScalar("count(*)", Hibernate.INTEGER);
		
		if (map.get("placeChilderName") != null) {
			querycount.setParameter("placeChilderName","%"+ map.get("placeChilderName") +"%");
		}
		if(map.get("beginDate") !=null){
			querycount.setParameter("beginDate", map.get("beginDate"));
		}
		if(map.get("endDate") !=null){
			querycount.setParameter("endDate", map.get("endDate"));
		}

		if (pager != null) {
			int pageBegin = (pager.getPageNumber() - 1) * pager.getPageSize();
			if (pageBegin < 0)
				pageBegin = 0;
			map.put("pageBegin", pageBegin);
			map.put("pageSize", pager.getPageSize());
		}

		pager.setTotalCount((Integer) querycount.uniqueResult());
		pager.setResult(getListByHsql(map));

		return pager;
	}
	
	public List<ReportFirstTender> getListByHsql(Map<String, Object> map){

		Session session = getSession();
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT t.hongbao_amount as hongbaoAmount, t.client_type as clientType ,t.user_id as userId,t.phone,t.tender_date as tenderDate,t.money,t.account as account ,t.borrow_id as borrowId,t.borrow_name as borrowName,b.time_limit as timeLimit,t.place_childer_id as placeChilderId,t.place_childer_name as placeChilderName from fy_report_first_tender t  ");
		hql.append(" left join fy_borrow b on b.id=t.borrow_id  where 1=1 ");

		if (map.get("placeChilderName") != null) {
			hql.append(" AND t.place_childer_name like :placeChilderName ");
		}
		if(map.get("beginDate") !=null){
			hql.append(" and t.tender_date >= :beginDate");
		}
		if(map.get("endDate") !=null){
			hql.append(" and t.tender_date <= :endDate");
		}
		hql.append(" order by t.id desc");
		if (map != null && map.get("pageBegin") != null && map.get("pageSize") !=null) {
			hql.append(" LIMIT :pageBegin,:pageSize");
		}
		Query query = session.createSQLQuery(hql.toString());

		query.setResultTransformer(Transformers.aliasToBean(ReportFirstTender.class));

		if (map.get("placeChilderName") != null) {
			query.setParameter("placeChilderName", "%"+ map.get("placeChilderName") +"%");
		}
		if(map.get("beginDate") !=null){
			query.setParameter("beginDate", map.get("beginDate"));
		}
		if(map.get("endDate") !=null){
			query.setParameter("endDate", map.get("endDate"));
		}
		
		if (map != null && map.get("pageBegin") != null && map.get("pageSize") !=null) {
			query.setParameter("pageBegin", map.get("pageBegin"), Hibernate.INTEGER);
			query.setParameter("pageSize", map.get("pageSize"), Hibernate.INTEGER);
		}

		return query.list();
	}
	
	// -------------------------------------------------------------------------------------------------------------------------
	

	public Pager queryBorrowTenderByHsql(Pager pager,Map<String, Object> map){

		Session session = getSession();
		StringBuffer hqlcount = new StringBuffer();
		
		hqlcount.append("SELECT   COUNT(*) FROM   `fy_borrow_detail` t   LEFT JOIN fy_borrow b     ON b.id = t.`borrow_id`   LEFT JOIN fy_user u     ON u.id = t.`user_id`   LEFT JOIN fy_user_analys ua     ON ua.`user_id` = t.`user_id`   LEFT JOIN fy_place_childer pc     ON pc.id = ua.place_childer_id  where 1=1 ");
		
		if(StringUtils.isNotEmpty(pager.getKeyword())){
			map.put("placeChilderName", pager.getKeyword());
		}

		if (map.get("username") != null) {
			hqlcount.append(" AND u.username like :username ");
		}
		if(map.get("beginDate") !=null){
			hqlcount.append(" and t.create_date >= :beginDate");
		}
		if(map.get("endDate") !=null){
			hqlcount.append(" and t.create_date <= :endDate");
		}
		
		if (map.get("placeChilderName") != null) {
			
			hqlcount.append(" AND pc.name like :placeChilderName ");
		}
		Query querycount = session.createSQLQuery(hqlcount.toString()).addScalar("count(*)", Hibernate.INTEGER);
		
		if (map.get("placeChilderName") != null) {
			querycount.setParameter("placeChilderName","%"+ map.get("placeChilderName") +"%");
		}

		if (map.get("username") != null) {
			querycount.setParameter("username","%"+ map.get("username") +"%");
		}
		if(map.get("beginDate") !=null){
			querycount.setParameter("beginDate", map.get("beginDate"));
		}
		if(map.get("endDate") !=null){
			querycount.setParameter("endDate", map.get("endDate"));
		}

		if (pager != null) {
			int pageBegin = (pager.getPageNumber() - 1) * pager.getPageSize();
			if (pageBegin < 0)
				pageBegin = 0;
			map.put("pageBegin", pageBegin);
			map.put("pageSize", pager.getPageSize());
		}

		pager.setTotalCount((Integer) querycount.uniqueResult());
		pager.setResult(getBorrowTenderByHsql(map));

		return pager;
	}
	
	public List<BorrowTenderItem> getBorrowTenderByHsql(Map<String, Object> map){

		Session session = getSession();
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT t.hongbao_amount as hongbaoAmount, t.client_type as clientType,  u.`username` AS username,   u.`real_name` AS realname,   t.`create_date` AS createDate, t.money as money,t.account as account,  b.`name` AS borrowName,   b.`time_limit` AS timeLimit,   pc.`name` AS placeChilderName FROM   `fy_borrow_detail` t   LEFT JOIN fy_borrow b     ON b.id = t.`borrow_id`   LEFT JOIN fy_user u     ON u.id = t.`user_id`   LEFT JOIN fy_user_analys ua     ON ua.`user_id` = t.`user_id`   LEFT JOIN fy_place_childer pc     ON pc.id = ua.place_childer_id  where 1=1 ");

		if (map.get("placeChilderName") != null) {
			hql.append(" AND pc.name like :placeChilderName ");
		}
		if (map.get("username") != null) {
			hql.append(" AND u.username like :username ");
		}
		if(map.get("beginDate") !=null){
			hql.append(" and t.create_date >= :beginDate");
		}
		if(map.get("endDate") !=null){
			hql.append(" and t.create_date <= :endDate");
		}
		hql.append(" order by t.id desc");
		if (map != null && map.get("pageBegin") != null && map.get("pageSize") !=null) {
			hql.append(" LIMIT :pageBegin,:pageSize");
		}
		Query query = session.createSQLQuery(hql.toString());

		query.setResultTransformer(Transformers.aliasToBean(BorrowTenderItem.class));

		if (map.get("placeChilderName") != null) {
			query.setParameter("placeChilderName", "%"+ map.get("placeChilderName") +"%");
		}
		if (map.get("username") != null) {
			query.setParameter("username", "%"+ map.get("username") +"%");
		}
		if(map.get("beginDate") !=null){
			query.setParameter("beginDate", map.get("beginDate"));
		}
		if(map.get("endDate") !=null){
			query.setParameter("endDate", map.get("endDate"));
		}
		
		if (map != null && map.get("pageBegin") != null && map.get("pageSize") !=null) {
			query.setParameter("pageBegin", map.get("pageBegin"), Hibernate.INTEGER);
			query.setParameter("pageSize", map.get("pageSize"), Hibernate.INTEGER);
		}

		return query.list();
	}
	
	
	public void callProcedure(String procString,List<Object> params) throws Exception {
		     CallableStatement stmt = null;
		     try {
		         stmt = this.getSession().connection().prepareCall(procString);
		         if (params != null){
		             int idx = 1;
		             for (Object obj : params) {
		                 if (obj != null) {
		                    stmt.setObject(idx, obj);
		                 } else {
		                     stmt.setNull(idx, Types.NULL);
		                }
		                idx++;
		             }
		         }
		         stmt.execute();
		     } catch (SQLException e) {
		         e.printStackTrace();
		        throw new Exception("调用存储过程的时候发生错误[sql = " + procString + "]", e);
		         
		     }
		 }
	
	
	
	
	
	
	
	
	
	
	
	
	
}
