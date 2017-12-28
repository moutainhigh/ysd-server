package net.qmdboss.dao.impl;

import net.qmdboss.DTO.UserAccountDetailDTO;
import net.qmdboss.bean.AccountDetail;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.UserAccountDetailDao;
import net.qmdboss.entity.UserAccountDetail;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Dao实现类 - 用户账户类
 * @author 詹锋
 *
 */

@Repository("userAccountDetailDaoImpl")
public class UserAccountDetailDaoImpl extends BaseDaoImpl<UserAccountDetail, Integer> implements UserAccountDetailDao {

	
	public Pager findPager(Pager pager, Map<String ,Object> map){//String type, Date startDate , Date endDate
		Criteria criteria = getSession().createCriteria(UserAccountDetail.class);
		
		if(map!= null){
			if(map.get("type") != null && !"".equals(map.get("type").toString())){
				criteria.add(Restrictions.eq("type", map.get("type").toString()));
			}
			
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
			Date startDate=null;
			Date endDate=null;
			try {
				startDate = map.get("startDate")!=null?dateformat.parse(map.get("startDate").toString()):null;
				endDate = map.get("endDate")!=null?dateformat.parse(map.get("endDate").toString()):null;
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			if(startDate != null && endDate != null){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endDate);
				calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
				endDate = calendar.getTime();
				criteria.add(Restrictions.between("createDate", startDate, endDate));
			}
			
			criteria.createAlias("user", "user");
			Object username = map.get("username");
			if(username != null && !"".equals(username.toString())){
				Object isExact = map.get("isExact");//是否精确查找
				if(isExact != null && (Boolean)isExact ){
					criteria.add(Restrictions.eq("user.username", username));
				}else{
					criteria.add(Restrictions.like("user.username", "%"+username+"%"));
				}
			}
			
		}
		criteria.add(Restrictions.gt("money", BigDecimal.ZERO));
		return super.findPager(pager, criteria);
	}
	
	
	
	
	
	public Pager findPagerByHql(Pager pager, Map<String ,Object> map){//String type, Date startDate , Date endDate

		Session session = getSession();
		StringBuffer hql = new StringBuffer();
		StringBuffer hqlcount = new StringBuffer();
		
		hql.append(" select ad.id as id,u.id as userid,u.username as username,u.real_name as realName,ad.type as type,ad.create_date as createDate, ");
		hql.append(" ad.total as total,ad.money as money,ad.use_money as useMoney,ad.no_use_money as noUseMoney, ");
		hql.append(" ad.investor_collection_capital as investorCollectionCapital ,ad.investor_collection_interest as investorCollectionInterest ");
		
		hql.append(" from fy_user_account_detail as ad inner join fy_user as u on ad.user_id=u.id where ad.money >0 " );
		
		hqlcount.append("select count(*) from fy_user_account_detail as ad inner join fy_user as u on ad.user_id=u.id where ad.money >0 ");
		

		if(map!= null){
			if(map.get("type") != null && !"".equals(map.get("type").toString())){
				hql.append(" and ad.type = :type");
				hqlcount.append(" and ad.type = :type");
			}
			
			if(map.get("startDate") != null && map.get("endDate")!= null){
				if(map.get("startDate") !=null){
					hql.append(" and ad.create_date >= :startDate");
					hqlcount.append(" and ad.create_date >= :startDate");
				}
				if(map.get("endDate") !=null){
					hql.append(" and ad.create_date <= :endDate");
					hqlcount.append(" and ad.create_date <= :endDate");
		
				}	
			}
				
			Object username = map.get("username");
			if(username != null && !"".equals(username.toString())){
				Object isExact = map.get("isExact");//是否精确查找
				if(isExact != null && (Boolean)isExact ){
					hql.append(" and  u.username= :username");
					hqlcount.append(" and  u.username= :username");
				}else{
					hql.append(" and  u.username like :username");
					hqlcount.append(" and  u.username like :username");
				}
			}
			
		}
		
		
		

		if (pager != null) {
			hql.append(" order by id desc LIMIT :pageBegin,:pageSize");
		}
			
		//重新组装hql
		//
		StringBuffer hqlNew = new StringBuffer();
		hqlNew.append("select tt.id as id, userid,username,realName,l.name as typeValue,createDate,total, " );
		hqlNew.append("money,useMoney,noUseMoney,investorCollectionCapital,investorCollectionInterest  " );
		hqlNew.append("from ("+hql.toString()+") as tt ");
		hqlNew.append("left join fy_listing as l on l.key_value=tt.type order by tt.id desc");
		
		
		Query query = session.createSQLQuery(hqlNew.toString());
		Query querycount = session.createSQLQuery(hqlcount.toString()).addScalar("count(*)", Hibernate.INTEGER);
		
		
		
		query.setResultTransformer(Transformers.aliasToBean(UserAccountDetailDTO.class));

		if(map!= null){
			if(map.get("type") != null && !"".equals(map.get("type").toString())){
				query.setParameter("type", map.get("type"));
				querycount.setParameter("type", map.get("type"));
			}
			
		
			if(map.get("endDate") != null && map.get("endDate") != null){
				if(map.get("startDate") !=null){
					query.setParameter("startDate", map.get("startDate"));
					querycount.setParameter("startDate", map.get("startDate"));	
				}
				if(map.get("endDate") !=null){
					query.setParameter("endDate", map.get("endDate"));
					querycount.setParameter("endDate", map.get("endDate"));	
					
				}
		
			}
			
			
			Object username = map.get("username");
			if(username != null && !"".equals(username.toString())){
				Object isExact = map.get("isExact");//是否精确查找
				if(isExact != null && (Boolean)isExact ){
					query.setParameter("username", username);
					querycount.setParameter("username", username);
				}else{
					query.setParameter("username", "%"+ username +"%");
					querycount.setParameter("username", "%"+ username +"%");
				}
			}
			
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public BigDecimal getFeeByType(String type,Date startDate, Date endDate){
		Criteria criteria = getSession().createCriteria(UserAccountDetail.class);
		if(StringUtils.isNotEmpty(type)){
			criteria.add(Restrictions.eq("type", type));
		}
		criteria.setProjection( Projections.sum("money"));
		if(startDate != null && endDate != null){
			criteria.add(Restrictions.between("createDate", startDate, endDate));
		}
		
		BigDecimal fee= (BigDecimal) criteria.uniqueResult();
		return fee;
	}
	
	@SuppressWarnings("unchecked")
	public List<AccountDetail> getUserAccountDetailTotalByType(String type,Date startDate,Date endDate){
		Criteria criteria = getSession().createCriteria(UserAccountDetail.class);
		if(StringUtils.isNotEmpty(type)){
			criteria.add(Restrictions.eq("type", type));
		}
		if(startDate != null && endDate != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
			endDate = calendar.getTime();
			criteria.add(Restrictions.between("createDate", startDate, endDate));
		}
		ProjectionList proList=Projections.projectionList();  
		proList.add(Projections.rowCount());//个数  
		proList.add(Projections.sum("money"));  
		proList.add(Projections.groupProperty("type"));  
		criteria.setProjection(proList);
		if(startDate != null && endDate != null){
			criteria.add(Restrictions.between("createDate", startDate, endDate));
		}
		
		
		List<UserAccountDetail> l = criteria.list();
		AccountDetail c;
		Object[] o;
		List<AccountDetail> ul = new ArrayList<AccountDetail>();

		Iterator i = l.listIterator();
		while(i.hasNext()){
			c = new AccountDetail();
			o = (Object[])i.next();
			c.setCount(Integer.valueOf(o[0].toString()));
			c.setMoney(BigDecimal.valueOf(Double.valueOf(o[1].toString())));
			c.setType(String.valueOf(o[2].toString()));
			ul.add(c);
		}
		return ul;
	}
	

	public List<UserAccountDetail> getAccountDetailList(Map<String ,Object> map){
		Criteria criteria = getSession().createCriteria(UserAccountDetail.class);
		if(map!= null){
			Object keyword = map.get("keyword"); 
			criteria.createAlias("user", "user");
			if( keyword != null && !"".equals(keyword.toString())){
				Object isExact = map.get("isExact");//是否精确查找
				if(isExact != null && (Boolean)isExact ){
					criteria.add(Restrictions.eq("user.username", keyword.toString()));
				}else{
					criteria.add(Restrictions.like("user.username", "%"+keyword.toString()+"%"));
				}
			}
			Object memberType = map.get("memberType");
			if(memberType!=null){
				criteria.add(Restrictions.eq("user.memberType", Integer.parseInt(memberType.toString())));
			}
			Object typeId = map.get("typeId");
			if(typeId!=null){
				criteria.add(Restrictions.eq("user.typeId", Integer.parseInt(typeId.toString())));
			}
			
			Object type = map.get("type");
			if(type != null && !"".equals(type.toString())){
				criteria.add(Restrictions.eq("type", type.toString()));//根据资金类型查询
			}
			
			Object borrowId = map.get("borrowId");
			if(borrowId != null && !"".equals(borrowId.toString())){
				criteria.add(Restrictions.eq("borrowId", borrowId));//根据标id类型查询
			}
			
			
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
			Date startDate=null;
			Date endDate=null;
			try {
				startDate = map.get("startDate")!=null?dateformat.parse(map.get("startDate").toString()):null;
				endDate = map.get("endDate")!=null?dateformat.parse(map.get("endDate").toString()):null;
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//按提交时间
			if(startDate!= null && endDate!= null ){
				criteria.add(Restrictions.between("createDate", startDate, endDate));//添加时间
			}
			
		}
		
		return criteria.list();
	}
	
	
	@Override
	public Integer save(UserAccountDetail userAccountDetail) {
		if(userAccountDetail!= null && userAccountDetail.getMoney() != null && userAccountDetail.getMoney().compareTo(BigDecimal.ZERO) >0 ){
			
			super.save(userAccountDetail);
			return userAccountDetail.getId();
		}
		return 0;
	}
	
	
	
	
	public List<UserAccountDetailDTO> getAccountDetailListByHql(Map<String ,Object> map){
	
		Session session = getSession();
		StringBuffer hql = new StringBuffer();

		hql.append(" select ad.id as id,u.id as userid,u.username as username,u.real_name as realName,ad.type as type,ad.create_date as createDate, ");
		hql.append(" ad.total as total,ad.money as money,ad.use_money as useMoney,ad.no_use_money as noUseMoney, ");
		hql.append(" ad.investor_collection_capital as investorCollectionCapital ,ad.investor_collection_interest as investorCollectionInterest ");
		
		hql.append(" from fy_user_account_detail as ad inner join fy_user as u on ad.user_id=u.id where ad.money >0 " );
		
	
		if(map!= null){
			if(map.get("type") != null && !"".equals(map.get("type").toString())){
				hql.append(" and ad.type = :type");			
			}
			
			if(map.get("startDate") != null && map.get("endDate")!= null){
				if(map.get("startDate") !=null){
					hql.append(" and ad.create_date >= :startDate");
				}
				if(map.get("endDate") !=null){
					hql.append(" and ad.create_date <= :endDate");
				}	
			}
				
			Object username = map.get("username");
			if(username != null && !"".equals(username.toString())){
				Object isExact = map.get("isExact");//是否精确查找
				if(isExact != null && (Boolean)isExact ){
					hql.append(" and  u.username= :username");
				}else{
					hql.append(" and  u.username like :username");
				}
			}
			
		}
		
	
		//重新组装hql
		//
		StringBuffer hqlNew = new StringBuffer();
		hqlNew.append("select tt.id as id, userid,username,realName,l.name as typeValue,createDate,total, " );
		hqlNew.append("money,useMoney,noUseMoney,investorCollectionCapital,investorCollectionInterest  " );
		hqlNew.append("from ("+hql.toString()+") as tt ");
		hqlNew.append("left join fy_listing as l on l.key_value=tt.type order by tt.id desc");
		
		
		Query query = session.createSQLQuery(hqlNew.toString());
	

		
		query.setResultTransformer(Transformers.aliasToBean(UserAccountDetailDTO.class));

		if(map!= null){
			if(map.get("type") != null && !"".equals(map.get("type").toString())){
				query.setParameter("type", map.get("type"));
			}
			
			if(map.get("startDate") != null && map.get("endDate") != null){
				if(map.get("startDate") !=null){
					query.setParameter("startDate", map.get("startDate"));
				}
				if(map.get("endDate") !=null){
					query.setParameter("endDate", map.get("endDate"));			
				}
		
			}
			
			Object username = map.get("username");
			if(username != null && !"".equals(username.toString())){
				Object isExact = map.get("isExact");//是否精确查找
				if(isExact != null && (Boolean)isExact ){
					query.setParameter("username", username);
					
				}else{
					query.setParameter("username", "%"+ username +"%");
				}
			}
			
		}
		
		return query.list();


	}
	
}
