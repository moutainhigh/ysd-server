package net.qmdboss.dao.impl;

import net.qmdboss.bean.CensusUserListBean;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.ReportUserAccountBackupDao;
import net.qmdboss.entity.ReportUserAccountBackup;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ReportUserAccountBackup 用户资金每日备份dao实现类
 * 
 */
@Repository("reportUserAccountBackupDaoImpl")
public class ReportUserAccountBackupDaoImpl extends
		BaseDaoImpl<ReportUserAccountBackup, Integer> implements
		ReportUserAccountBackupDao {

	public List queryReportStatisticsDailyList(int dateIntBegin, int dateIntEnd) {
		String hql = " SELECT new net.qmdboss.bean.ReportStatisticsDailyBean("//
				+ " SUM(b.total) ,"//
				+ " SUM(b.ableMoney) ,"//
				+ " SUM(b.continueTotal) ,"//
				+ " SUM(b.unableMoney),"//
				+ " SUM(b.investorCollectionCapital),"//
				+ " SUM(b.investorCollectionInterest) ,"//
				+ " b.dailyWorkDateInt)"//
				+ " FROM"//
				+ "  ReportUserAccountBackup b "//
				+ "  LEFT JOIN b.user u "//
				+ " WHERE u.typeId = 0 "//
				+ " AND b.dailyWorkDateInt >= :dateIntBegin  "//
				+ "  AND b.dailyWorkDateInt <= :dateIntEnd "//
				+ " GROUP BY b.dailyWorkDateInt";//
		Query query = getSession().createQuery(hql);
		query.setParameter("dateIntBegin", dateIntBegin, Hibernate.INTEGER);
		query.setParameter("dateIntEnd", dateIntEnd, Hibernate.INTEGER);
		List ll = query.list();
		return ll;
		// return query.list();

	}

	private List<CensusUserListBean> queryCensusUserDetail(Integer dateIntBegin, Integer dateIntEnd,
			String username, Integer pageBegin, Integer pageSize,Session session) {
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT ");
		hql.append("(SELECT IFNULL(SUM(d01.money),0) FROM fy_user_account_detail d01 WHERE d01.user_id = b.user_id AND d01.create_date< b.daily_create_date AND d01.type ='money_into') suma11,");
		hql.append("(SELECT IFNULL(SUM(d02.money),0) FROM fy_user_account_detail d02 WHERE d02.user_id = b.user_id AND d02.create_date< b.daily_create_date AND d02.type ='recharge') suma21,");
		hql.append("(SELECT IFNULL(SUM(d03.money),0) FROM fy_user_account_detail d03 WHERE d03.user_id = b.user_id AND d03.create_date< b.daily_create_date AND d03.type ='recharge_offline') suma22,");
		hql.append("(SELECT IFNULL(SUM(d04.money),0) FROM fy_user_account_detail d04 WHERE d04.user_id = b.user_id AND d04.create_date< b.daily_create_date AND d04.type ='interest_repayment') suma31,");
		hql.append("(SELECT IFNULL(SUM(d05.money),0) FROM fy_user_account_detail d05 WHERE d05.user_id = b.user_id AND d05.create_date< b.daily_create_date AND d05.type ='interest_repayment_continued') suma32,");
		hql.append("(SELECT IFNULL(SUM(d06.money),0) FROM fy_user_account_detail d06 WHERE d06.user_id = b.user_id AND d06.create_date< b.daily_create_date AND d06.type ='award_add') suma41,");
		hql.append("(SELECT IFNULL(SUM(d07.money),0) FROM fy_user_account_detail d07 WHERE d07.user_id = b.user_id AND d07.create_date< b.daily_create_date AND d07.type ='award_continued') suma42,");
		hql.append("(SELECT IFNULL(SUM(d08.money),0) FROM fy_user_account_detail d08 WHERE d08.user_id = b.user_id AND d08.create_date< b.daily_create_date AND d08.type ='recharge_offline_reward') suma43,");
		hql.append("(SELECT IFNULL(SUM(d09.money),0) FROM fy_user_account_detail d09 WHERE d09.user_id = b.user_id AND d09.create_date< b.daily_create_date AND d09.type ='offline_reward') suma44,");
		hql.append("(SELECT IFNULL(SUM(d10.money),0) FROM fy_user_account_detail d10 WHERE d10.user_id = b.user_id AND d10.create_date< b.daily_create_date AND d10.type ='recharge_success') sumb21,");
		hql.append("(SELECT IFNULL(SUM(d11.money),0) FROM fy_user_account_detail d11 WHERE d11.user_id = b.user_id AND d11.create_date< b.daily_create_date AND d11.type ='tender_mange') sumb31,");
		hql.append("(SELECT IFNULL(SUM(d12.money),0) FROM fy_user_account_detail d12 WHERE d12.user_id = b.user_id AND d12.create_date< b.daily_create_date AND d12.type ='offline_deduct') sumb41,");
		hql.append("b.total AS total0,");
		hql.append("(SELECT IFNULL(SUM(r.money),0) FROM fy_user_account_recharge r WHERE r.status = 1 AND r.user_id = b.user_id AND r.recharge_date < b.daily_create_date ) sumr01,");
		hql.append("(SELECT IFNULL(SUM(c.total),0) FROM fy_account_cash c WHERE c.status = 1 AND c.user_id = b.user_id AND c.verify_time < b.daily_create_date) sumc01,");
		hql.append("b.daily_work_date_int AS dailyWorkDateInt,");
		hql.append("b.user_id AS userId,");
		hql.append("u.username AS username,");
		hql.append("u.real_name AS realName");
		hql.append(" FROM fy_report_user_account_backup b ");
		hql.append(" LEFT JOIN fy_user u  ON b.user_id = u.id ");
		hql.append(" WHERE u.username =:username ");
		if (dateIntBegin != null && dateIntBegin > 0) {
			hql.append(" AND b.daily_work_date_int >=:dateIntBegin ");
		}
		if (dateIntEnd != null && dateIntEnd > 0) {
			hql.append(" AND b.daily_work_date_int <=:dateIntEnd ");
		}
		if (pageSize > 0) {
			hql.append(" LIMIT :pageBegin,:pageSize");
		}

		Query query = session.createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers
				.aliasToBean(CensusUserListBean.class));

		query.setParameter("username", username, Hibernate.STRING);

		if (dateIntBegin != null && dateIntBegin > 0) {
			query.setParameter("dateIntBegin", dateIntBegin, Hibernate.INTEGER);
		}
		if (dateIntEnd != null && dateIntEnd > 0) {
			query.setParameter("dateIntEnd", dateIntEnd, Hibernate.INTEGER);
		}

		if (pageSize > 0) {
			query.setParameter("pageBegin", pageBegin, Hibernate.INTEGER);
			query.setParameter("pageSize", pageSize, Hibernate.INTEGER);
		}
		return query.list();
	}
	@Override
	public List<CensusUserListBean> queryCensusUserDetail(Integer dateIntBegin, Integer dateIntEnd,String username) {
		Session session = getSession();
		return queryCensusUserDetail(dateIntBegin, dateIntEnd,username, -1, -1,session);
	}
	
	public Integer queryCensusUserCount(Integer dateIntBegin, Integer dateIntEnd,String username,Session session){
		
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT count(*)");
		hql.append(" FROM fy_report_user_account_backup b ");
		hql.append(" LEFT JOIN fy_user u  ON b.user_id = u.id ");
		hql.append(" WHERE u.username =:username ");
		if (dateIntBegin != null && dateIntBegin > 0) {
			hql.append(" AND b.daily_work_date_int >=:dateIntBegin ");
		}
		if (dateIntEnd != null && dateIntEnd > 0) {
			hql.append(" AND b.daily_work_date_int <=:dateIntEnd ");
		}
		
		Query query = session.createSQLQuery(hql.toString()).addScalar("count(*)",Hibernate.INTEGER);
		
		query.setParameter("username", username, Hibernate.STRING);
		if (dateIntBegin != null && dateIntBegin > 0) {
			query.setParameter("dateIntBegin", dateIntBegin, Hibernate.INTEGER);
		}
		if (dateIntEnd != null && dateIntEnd > 0) {
			query.setParameter("dateIntEnd", dateIntEnd, Hibernate.INTEGER);
		}
		
		return (Integer)query.uniqueResult();
		
	}
	
	@Override
	public Pager queryCensusUserPage(Integer dateIntBegin, Integer dateIntEnd, Pager pager) {
		Session session = getSession();
		int totalCount = queryCensusUserCount(dateIntBegin,dateIntEnd,pager.getKeyword(),session);
		pager.setTotalCount(totalCount);

		int pageBegin = (pager.getPageNumber() - 1) * pager.getPageSize();
		if (pageBegin < 0)
			pageBegin = 0;

		pager.setResult(queryCensusUserDetail(dateIntBegin, dateIntEnd,pager.getKeyword() , pageBegin,
				pager.getPageSize(),session));
		return pager;
	}

}
