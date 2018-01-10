package net.qmdboss.dao.impl;

import net.qmdboss.DTO.UserDTO;
import net.qmdboss.DTO.UserSpreadInviteAwardDTO;
import net.qmdboss.bean.CensusUserListBean;
import net.qmdboss.bean.Pager;
import net.qmdboss.bean.UserListBean;
import net.qmdboss.bean.UserSpreadBean;
import net.qmdboss.dao.UserDao;
import net.qmdboss.entity.User;
import net.qmdboss.entity.UserHongbao;
import net.qmdboss.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository("userDaoImpl")
public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao {

	private Logger rankLog = Logger.getLogger("bossRankLog");

	public User getUserByUsername(String username) {
		String hql = "from User as user where lower(user.username) = lower(:username)";
		return (User) getSession().createQuery(hql).setParameter("username", username).uniqueResult();
	}

	public User getUserByEmail(String email, Integer id) {
		String hql = "from User as user where lower(user.email) = lower(:email) and user.id != :id";
		return (User) getSession().createQuery(hql).setParameter("email", email).setParameter("id", id).uniqueResult();
	}

	public User getUserByPhone(String phone, Integer id) {
		String hql = "from User as user where lower(user.phone) = lower(:phone) and user.id != :id";
		return (User) getSession().createQuery(hql).setParameter("phone", phone).setParameter("id", id).uniqueResult();
	}

	public List<User> getByPhone(String phone) {
		String hql = "from User as user where lower(user.phone) = lower(:phone) ";
		// return (User) getSession().createQuery(hql)
		// .setParameter("phone", phone).uniqueResult();
		return getSession().createQuery(hql).setParameter("phone", phone).list();
	}

	@Override
	public Pager getUserPager(Integer verifyType, Pager pager) {
		return this.getUserPager(null, verifyType, pager);
	}

	public Pager getUserPager(Integer status, Integer verifyType, Pager pager) {
		Criteria criteria = getSession().createCriteria(User.class);
		// criteria.add(Restrictions.eq("isLock", false));//false：锁定
		if (verifyType == 1) {
			criteria.add(Restrictions.eq("memberType", 0));
			criteria.add(Restrictions.eq("realStatus", 2));
		} else if (verifyType == 2) {
			criteria.add(Restrictions.eq("typeId", 2));
		} else if (verifyType == 3) {
			criteria.add(Restrictions.eq("vipStatus", 2));
		} else if (verifyType == 4) {
			criteria.add(Restrictions.eq("memberType", 1));
			criteria.add(Restrictions.eq("realStatus", 2));
		} else if (verifyType == 5) {
			criteria.add(Restrictions.eq("typeId", 1));
			criteria.add(Restrictions.eq("memberType", 0));
		} else if (verifyType == 6) {
			criteria.add(Restrictions.eq("typeId", 1));
			criteria.add(Restrictions.eq("memberType", 1));
		} else if (verifyType == 7) {
			criteria.add(Restrictions.eq("phoneStatus", 2));
		}
		if (status != null && status != 0) {
			if (status == 1) {// 正常
				criteria.add(Restrictions.eq("isEnabled", true));
				criteria.add(Restrictions.eq("isLock", false));
			} else if (status == 2) {// 未启用
				criteria.add(Restrictions.eq("isEnabled", false));
			} else if (status == 3) {// 已锁定
				criteria.add(Restrictions.eq("isLock", true));
			}
		}
		return super.findPager(pager, criteria);
	}

	// 关联处理
	@Override
	public void delete(User user) {
		super.delete(user);
	}

	// 关联处理
	@Override
	public void delete(Integer id) {
		User user = load(id);
		this.delete(user);
	}

	// 关联处理
	@Override
	public void delete(Integer[] ids) {
		for (Integer id : ids) {
			User user = load(id);
			this.delete(user);
		}
	}

	public List<Object> getViewTest(int timelimit, BigDecimal rateYear, BigDecimal reward) {

		String hql = "SELECT ";
		hql += " u.id ";
		// hql+= ",u.autoTenderRule";
		// hql+= ",u.autoTenderMoneyTop";
		// hql+= ",u.autoTenderMoneyLeave";
		// hql+= ",a.continueTotal";
		// hql+= ",a.ableMoney";
		hql += " FROM";
		hql += "  ";
		hql += "    User u ";
		// hql+= "    LEFT JOIN u.account a ";
		// hql+= "      ON a.userId = u.id";
		// hql+= "   ";
		hql += " WHERE ";
		hql += "    u.typeId = 0 ";
		hql += "    AND u.autoTenderStatus = 1";
		// hql += " and u.autoTenderLimitBegin <=" + timelimit;
		// hql += " and u.autoTenderLimitEnd >=" + timelimit;
		// hql += " and u.autoTenderRateBegin <=" + rateYear;
		// hql += " and u.autoTenderRateEnd >=" + rateYear;
		// hql += " and u.autoTenderRewardBegin <=" + reward;
		// hql += " and u.autoTenderRewardEnd >=" + reward;
		// hql
		// +=" and ((  CAST((a.continueTotal/100) as int) + CAST((a.ableMoney/100) as int))*100) > 1000";
		hql += " order by u.autoTenderDate,u.id ";

		rankLog.debug("  hql[" + hql + "] ");

		List<Object> l = getSession().createQuery(hql).list();

		return l;
	}

	private List queryCensusUserList(Integer dateInt, Date dateEnd, int pageBegin, int pageSize, String username, Session session) {
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT");
		// hql.append(" new net.qmdboss.bean.CensusUserListBean(");
		hql.append(" IFNULL(p1.p1total,0) as total0,");// 总额
		hql.append(" IFNULL(r1.sumr01,0) as sumr01,");// 充值总额（充值表）
		hql.append(" IFNULL(c1.sumc01,0) as sumc01,");// 提现总额(提现表)
		hql.append(" IFNULL(a11.suma11,0) as suma11,");//
		hql.append(" IFNULL(a21.suma21,0) as suma21,");//
		hql.append(" IFNULL(a22.suma22,0) as suma22,");//
		hql.append(" IFNULL(a31.suma31,0) as suma31,");//
		hql.append(" IFNULL(a32.suma32,0) as suma32,");//
		hql.append(" IFNULL(a41.suma41,0) as suma41,");//
		hql.append(" IFNULL(a42.suma42,0) as suma42,");//
		hql.append(" IFNULL(a43.suma43,0) as suma43,");//
		hql.append(" IFNULL(a44.suma44,0) as suma44,");//
		hql.append(" IFNULL(b21.sumb21,0) as sumb21,");//
		hql.append(" IFNULL(b31.sumb31,0) as sumb31,");//
		hql.append(" IFNULL(b41.sumb41,0) as sumb41,");//
		hql.append(" u.id as userId,");
		hql.append(" u.username as username,");
		hql.append(" u.real_name as realName");
		// hql.append(" )");
		hql.append(" FROM fy_user u ");
		hql.append(" LEFT JOIN (SELECT p.total AS p1total,p.user_id FROM fy_report_user_account_backup p WHERE p.daily_work_date_int = :dateInt ) p1 ON u.id =p1.user_id");
		hql.append(" LEFT JOIN (SELECT SUM(r.money) AS sumr01,r.user_id FROM fy_user_account_recharge r WHERE r.status = 1 AND r.recharge_date <= :dateEnd GROUP BY r.user_id) r1 ON u.id = r1.user_id");
		hql.append(" LEFT JOIN (SELECT SUM(c.total) AS sumc01,c.user_id FROM fy_account_cash c WHERE c.status = 1 AND c.verify_time <= :dateEnd GROUP BY c.user_id) c1 ON u.id = c1.user_id");
		hql.append(" LEFT JOIN (SELECT SUM(d1.money) AS suma11,d1.user_id FROM fy_user_account_detail d1 WHERE d1.type ='money_into' AND d1.create_date <= :dateEnd GROUP BY d1.user_id) a11 ON u.id=a11.user_id");
		hql.append(" LEFT JOIN (SELECT SUM(d1.money) AS suma21,d1.user_id FROM fy_user_account_detail d1 WHERE d1.type ='recharge'  AND d1.create_date <= :dateEnd GROUP BY d1.user_id) a21 ON u.id=a21.user_id");
		hql.append(" LEFT JOIN (SELECT SUM(d1.money) AS suma22,d1.user_id FROM fy_user_account_detail d1 WHERE d1.type ='recharge_offline'  AND d1.create_date <= :dateEnd GROUP BY d1.user_id) a22 ON u.id=a22.user_id");
		hql.append(" LEFT JOIN (SELECT SUM(d1.money) AS suma31,d1.user_id FROM fy_user_account_detail d1 WHERE d1.type ='interest_repayment'  AND d1.create_date <= :dateEnd GROUP BY d1.user_id) a31 ON u.id=a31.user_id");
		hql.append(" LEFT JOIN (SELECT SUM(d1.money) AS suma32,d1.user_id FROM fy_user_account_detail d1 WHERE d1.type ='interest_repayment_continued'  AND d1.create_date <= :dateEnd GROUP BY d1.user_id) a32 ON u.id=a32.user_id");
		hql.append(" LEFT JOIN (SELECT SUM(d1.money) AS suma41,d1.user_id FROM fy_user_account_detail d1 WHERE d1.type ='award_add'  AND d1.create_date <= :dateEnd GROUP BY d1.user_id) a41 ON u.id=a41.user_id");
		hql.append(" LEFT JOIN (SELECT SUM(d1.money) AS suma42,d1.user_id FROM fy_user_account_detail d1 WHERE d1.type ='award_continued'  AND d1.create_date <= :dateEnd GROUP BY d1.user_id) a42 ON u.id=a42.user_id");
		hql.append(" LEFT JOIN (SELECT SUM(d1.money) AS suma43,d1.user_id FROM fy_user_account_detail d1 WHERE d1.type ='recharge_offline_reward'  AND d1.create_date <= :dateEnd GROUP BY d1.user_id) a43 ON u.id=a43.user_id");
		hql.append(" LEFT JOIN (SELECT SUM(d1.money) AS suma44,d1.user_id FROM fy_user_account_detail d1 WHERE d1.type ='offline_reward'  AND d1.create_date <= :dateEnd GROUP BY d1.user_id) a44 ON u.id=a44.user_id");
		hql.append(" LEFT JOIN (SELECT SUM(d1.money) AS sumb21,d1.user_id FROM fy_user_account_detail d1 WHERE d1.type ='recharge_success'  AND d1.create_date <= :dateEnd GROUP BY d1.user_id) b21 ON u.id=b21.user_id");
		hql.append(" LEFT JOIN (SELECT SUM(d1.money) AS sumb31,d1.user_id FROM fy_user_account_detail d1 WHERE d1.type ='tender_mange'  AND d1.create_date <= :dateEnd GROUP BY d1.user_id) b31 ON u.id=b31.user_id");
		hql.append(" LEFT JOIN (SELECT SUM(d1.money) AS sumb41,d1.user_id FROM fy_user_account_detail d1 WHERE d1.type ='offline_deduct'  AND d1.create_date <= :dateEnd GROUP BY d1.user_id) b41 ON u.id=b41.user_id");
		hql.append(" WHERE u.type_id = 0 ");
		if (StringUtils.isNotEmpty(username)) {
			hql.append(" AND u.username =:username");
		}
		hql.append(" ORDER BY u.id");
		if (pageSize > 0) {
			hql.append(" LIMIT :pageBegin,:pageSize");
		}

		Query query = session.createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.aliasToBean(CensusUserListBean.class));
		query.setParameter("dateInt", dateInt, Hibernate.INTEGER);
		query.setParameter("dateEnd", dateEnd, Hibernate.TIMESTAMP);
		if (StringUtils.isNotEmpty(username)) {
			query.setParameter("username", username, Hibernate.STRING);
		}
		if (pageSize > 0) {
			query.setParameter("pageBegin", pageBegin, Hibernate.INTEGER);
			query.setParameter("pageSize", pageSize, Hibernate.INTEGER);
		}
		List ll = query.list();
		return ll;
	}

	public List queryCensusUserList(Integer dateInt, Date dateEnd, String username) {
		Session session = getSession();
		return queryCensusUserList(dateInt, dateEnd, -1, -1, username, session);
	}

	private Integer queryCensusUserCount(String username, Session session) {
		String hql = "select count(*) from User as user where typeId = 0";
		if (StringUtils.isNotEmpty(username)) {
			hql += " and username = :username";
		}
		Query query = session.createQuery(hql);
		if (StringUtils.isNotEmpty(username)) {
			query.setParameter("username", username, Hibernate.STRING);
		}
		return ((Long) query.uniqueResult()).intValue();
	}

	public Pager queryCensusUserPage(Integer dateInt, Date dateEnd, Pager pager) {

		Session session = getSession();
		int totalCount = queryCensusUserCount(pager.getKeyword(), session);
		pager.setTotalCount(totalCount);

		int pageBegin = (pager.getPageNumber() - 1) * pager.getPageSize();
		if (pageBegin < 0)
			pageBegin = 0;

		pager.setResult(queryCensusUserList(dateInt, dateEnd, pageBegin, pager.getPageSize(), pager.getKeyword(), session));
		return pager;
	}

	public User getUserByAgency(Integer agencyid, Integer agencytype) {
		String hql = "from User as user where user.typeId=3 and user.agencyid =:agencyid and user.agencytype =:agencytype";
		return (User) getSession().createQuery(hql).setParameter("agencyid", agencyid).setParameter("agencytype", agencytype).uniqueResult();
	}

	@Override
	public Pager findPagerByMap(Pager pager, Map<String, Object> map) {
		Criteria criteria = getSession().createCriteria(User.class);
		if (map != null && !map.isEmpty()) {

			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (entry.getKey() != null && entry.getValue() != null) {
					if (entry.getKey().contains(",")) {
						String alias = StringUtils.substringBefore(entry.getKey(), ".");
						criteria.createAlias(alias, alias);
					}
					criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
				}
			}

		}
		return super.findPager(pager, criteria);
	}

	public Pager findPager(Pager pager, Map<String, Object> map) {
		Session session = getSession();
		StringBuffer hql = new StringBuffer();
		StringBuffer hqlcount = new StringBuffer();
		hql.append("SELECT u.id,u.username,u.real_name as realname,u.phone,u.create_date as createDate,spread.username as spreadUsername,spread.phone as spreadPhone,spread.real_name as spreadRealname from fy_user u left join fy_user spread on u.tg_one_level_user_id = spread.id where u.tg_one_level_user_id is not null");

		hqlcount.append("SELECT count(*) FROM fy_user u where u.tg_one_level_user_id is not null ");
		if (map.get("username") != null) {
			hql.append(" AND u.username =:username ");
			hqlcount.append(" AND u.username =:username ");
		}
		hql.append(" order by id desc");
		if (pager != null) {
			hql.append(" LIMIT :pageBegin,:pageSize");
		}
		Query query = session.createSQLQuery(hql.toString());
		Query querycount = session.createSQLQuery(hqlcount.toString()).addScalar("count(*)", Hibernate.INTEGER);
		query.setResultTransformer(Transformers.aliasToBean(UserSpreadBean.class));

		if (map.get("username") != null) {
			query.setParameter("username", map.get("username"));
			querycount.setParameter("username", map.get("username"));
		}

		if (pager != null) {
			int pageBegin = (pager.getPageNumber() - 1) * pager.getPageSize();
			if (pageBegin < 0)
				pageBegin = 0;
			query.setParameter("pageBegin", pageBegin, Hibernate.INTEGER);
			query.setParameter("pageSize", pager.getPageSize(), Hibernate.INTEGER);
		}

		pager.setTotalCount((Integer) querycount.uniqueResult());
		pager.setResult(query.list());

		return pager;
	}

	public Pager findPagerByHsql(Pager pager, Map<String, Object> map) {

		Session session = getSession();
		StringBuffer hqlcount = new StringBuffer();
		
		hqlcount.append("SELECT COUNT(*) FROM fy_user AS u LEFT JOIN (fy_user_analys AS a LEFT JOIN fy_place_childer AS c ON a.place_childer_id = c.id)  ON u.id = a.user_id where 1=1 ");
		
		if(StringUtils.isNotEmpty(pager.getKeyword())){
			map.put("username", pager.getKeyword());
		}
		
		if (map.get("username") != null) {	
			hqlcount.append(" AND u.username like :username ");
		}
		if (map.get("placeChilderName") != null) {	
			hqlcount.append(" AND c.name =:placeChilderName ");
		}
		
		if(map.get("beginDate") !=null){
			hqlcount.append(" and u.create_date >= :beginDate");
		}
		if(map.get("endDate") !=null){
			hqlcount.append(" and u.create_date <= :endDate");
		}
		Query querycount = session.createSQLQuery(hqlcount.toString()).addScalar("count(*)", Hibernate.INTEGER);
		
		if (map.get("username") != null) {
			querycount.setParameter("username","%"+ map.get("username") +"%");
		}
		if (map.get("placeChilderName") != null) {
			querycount.setParameter("placeChilderName",map.get("placeChilderName"));
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

	public List<UserListBean> getListByHsql(Map<String, Object> map) {

		Session session = getSession();
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT u.type_id as typeId ,u.member_type as memberType ,a.app_type as appType,u.id,u.username,u.real_name as realName,u.create_date as createDate,CASE WHEN (u.real_status=1) THEN '已认证' ELSE '未认证' END AS realStatusStr,c.`name` as placeName,u.is_enabled as isEnabled,u.is_lock as isLock from fy_user as u LEFT JOIN (fy_user_analys as a LEFT JOIN fy_place_childer as c ON a.place_childer_id=c.id) on u.id = a.user_id where 1=1 ");

		if (map.get("username") != null) {
			hql.append(" AND u.username like :username ");
		}
		if (map.get("placeChilderName") != null) {	
			hql.append(" AND c.name =:placeChilderName ");
		}
		if(map.get("beginDate") !=null){
			hql.append(" and u.create_date >= :beginDate");
		}
		if(map.get("endDate") !=null){
			hql.append(" and u.create_date <= :endDate");
		}
		hql.append(" order by u.id desc");
		if (map != null && map.get("pageBegin") != null && map.get("pageSize") !=null) {
			hql.append(" LIMIT :pageBegin,:pageSize");
		}
		Query query = session.createSQLQuery(hql.toString());

		query.setResultTransformer(Transformers.aliasToBean(UserListBean.class));

		if (map.get("username") != null) {
			query.setParameter("username", "%"+ map.get("username") +"%");
		}
		if (map.get("placeChilderName") != null) {
			query.setParameter("placeChilderName",map.get("placeChilderName"));
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
	
	
	
	public List<UserDTO> selectListByIdsOrUsername(Map<String, Object> map) {
		
		
		String sql=null;
		Session session = getSession();
		
		StringBuffer sb = new StringBuffer();
		List<String> l=(List<String>) map.get("listA");
		System.out.println("sql 查询列表="+l.size());
		for(int i=0;i <l.size();i++){
		    
				if(i == l.size()-1){
		                sb.append("'"+l.get(i)+"'");
		        }else{
		               sb.append("'"+l.get(i)+"'" + ",");
		        }
		 
		}
		 
		if(map.get("key").equals("ids")){
			 sql="select id,username from fy_user where 1=1 and id in ("+sb.toString()+")";
		}
		
		if(map.get("key").equals("username")){
			 sql="select id,username from fy_user where 1=1 and username in ("+sb.toString()+")";
		}
		
		
		Query query = session.createSQLQuery(sql);

		query.setResultTransformer(Transformers.aliasToBean(UserDTO.class));
		

		return query.list();
	

	
	}
	
	
	public String savePL(List<String> userIdList,List<String> hbMoneyList ,List<Integer> hbEndTimeList ,List<Integer> hbLimitMaxMoney ,List<Integer> hbLimitStartList,List<Integer> hbLimitEndList,String name) {
		
		
		   Session session1 = getSession();  
			 for(int i=0;i<hbMoneyList.size();i++){
				int n=0;
				 for(int j=0;j<userIdList.size();j++){
		 
					 UserHongbao hb = new UserHongbao();
					
					hb.setName(name);//红包标题
					hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")+CommonUtil.getRandomString(5));
					User uu=new User();
					uu.setId(Integer.valueOf(userIdList.get(j)));
					hb.setUser(uu);
					hb.setSource(5);
					hb.setStatus(-1);//待发放
					hb.setIsApp(1);
					hb.setIsHfive(1);
					hb.setIsPc(1);
					hb.setIsLooked(0);
					hb.setUsedMoney(new BigDecimal(0));
					hb.setExpDate(hbEndTimeList.get(i));
					hb.setInvestFullMomey(hbLimitMaxMoney.get(i));
					hb.setLimitStart(hbLimitStartList.get(i));
					hb.setLimitEnd(hbLimitEndList.get(i));
					hb.setMoney(new BigDecimal(hbMoneyList.get(i)));
					//hb.setSourceString(userHongbao.getSourceString());
				
					session1.save(hb); 

				}	
				 
					
			}
			
			 session1.flush();  
             session1.clear(); 


		return null;
		
	}


	

	
	public Pager findInviteAwardPagerBySql(Pager pager, Map<String, Object> map) {
		Session session = getSession();
		StringBuffer hqlcount = new StringBuffer();
		
		hqlcount.append("select count(*) from fy_user as u left join fy_borrow_detail as bd on  u.id=bd.user_id ");
		hqlcount.append(" left join fy_borrow as b on b.id=bd.borrow_id  ");
		hqlcount.append(" left join fy_user as u1 on u.tg_one_level_user_id=u1.id where u.tg_one_level_user_id is not null");
		if (map.get("username") != null) {
			
			hqlcount.append(" AND u.tg_one_level_user_id = :username ");
		}
		
		Query querycount = session.createSQLQuery(hqlcount.toString()).addScalar("count(*)", Hibernate.INTEGER);
		
		if (map.get("username") != null) {
			querycount.setParameter("username",map.get("username") );
		}
		

		if (pager != null) {
			int pageBegin = (pager.getPageNumber() - 1) * pager.getPageSize();
			if (pageBegin < 0)
				pageBegin = 0;
			map.put("pageBegin", pageBegin);
			map.put("pageSize", pager.getPageSize());
		}

		pager.setTotalCount((Integer) querycount.uniqueResult());
		pager.setResult(getListInviteAwardBySql(map));

		return pager;
	
		
	}
	
	
		public List<UserSpreadInviteAwardDTO> getListInviteAwardBySql(	Map<String, Object> map) {
				
			Session session = getSession();
			StringBuffer hql = new StringBuffer();
			hql.append("select u.username as username,b.name as borrowName,bd.interest as borrowInterest,b.status as borrowStatus, ");
			hql.append("u.real_name as realname,u1.username as inviteName ,u1.real_name as inviteRealName,bd.create_date as createDate  ");
			hql.append(" from fy_user as u left join fy_borrow_detail as bd on  u.id=bd.user_id ");
			hql.append(" left join fy_borrow as b on b.id=bd.borrow_id  ");
			hql.append(" left join fy_user as u1 on u.tg_one_level_user_id=u1.id where u.tg_one_level_user_id is not null");
			
			if (map.get("username") != null) {
				hql.append(" AND u.tg_one_level_user_id = :username ");
			}
			

			hql.append(" order by u.id desc");
			
			if (map != null && map.get("pageBegin") != null && map.get("pageSize") !=null) {
				hql.append(" LIMIT :pageBegin,:pageSize");
			}
			Query query = session.createSQLQuery(hql.toString());

			query.setResultTransformer(Transformers.aliasToBean(UserSpreadInviteAwardDTO.class));

			if (map.get("username") != null) {
				query.setParameter("username", map.get("username"));
			}
		
			
			if (map != null && map.get("pageBegin") != null && map.get("pageSize") !=null) {
				query.setParameter("pageBegin", map.get("pageBegin"), Hibernate.INTEGER);
				query.setParameter("pageSize", map.get("pageSize"), Hibernate.INTEGER);
			}

			return query.list();
		}
	
	
	

}
