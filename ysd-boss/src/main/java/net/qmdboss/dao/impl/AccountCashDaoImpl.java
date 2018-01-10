package net.qmdboss.dao.impl;

import net.qmdboss.DTO.UserAccountCashDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.AccountCashDao;
import net.qmdboss.entity.AccountCash;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * AccountCash 提现dao实现类
 * @author zhanf
 *
 */
@Repository("accountCashDaoImpl")
public class AccountCashDaoImpl extends BaseDaoImpl<AccountCash , Integer>
		implements AccountCashDao {

	@Override
	public Pager getCashPage(AccountCash accountCash, Pager pager,Date startDate,Date endDate) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(AccountCash.class);
		if(startDate != null && endDate != null){
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(endDate);
//			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
//			endDate = calendar.getTime();
			criteria.add(Restrictions.between("createDate", startDate, endDate));
		}
		if(accountCash != null && accountCash.getUser()!=null){
			criteria.createAlias("user", "user");
			if(accountCash.getUser().getTypeId()!=null){
				criteria.add(Restrictions.eq("user.typeId",accountCash.getUser().getTypeId()));
			}
			if(StringUtils.isNotEmpty(accountCash.getUser().getUsername())){
				criteria.add(Restrictions.like("user.username", "%"+accountCash.getUser().getUsername()+"%"));
			}
		}
		if(accountCash != null && accountCash.getStatus()!=null){
			if(accountCash.getStatus().compareTo(0) ==0){
				criteria.add(Restrictions.or(Restrictions.eq("status", 0), Restrictions.eq("status", 4)));
			}else{
				criteria.add(Restrictions.eq("status",accountCash.getStatus()));
			}
		}
		criteria.addOrder(Order.desc("id"));
		return super.findPager(pager, criteria);
	}

	@Override
	public List<AccountCash> getCashList(AccountCash accountCash,
			Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(AccountCash.class);
		if(startDate != null && endDate != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
			endDate = calendar.getTime();
			criteria.add(Restrictions.between("createDate", startDate, endDate));
		}
		if(accountCash.getUser()!=null){
			criteria.createAlias("user", "user");
			if(accountCash.getUser().getTypeId()!=null){
				criteria.add(Restrictions.eq("user.typeId",accountCash.getUser().getTypeId()));
			}
			if(StringUtils.isNotEmpty(accountCash.getUser().getUsername())){
				criteria.add(Restrictions.like("user.username", "%"+accountCash.getUser().getUsername()+"%"));
			}
		}
		if(accountCash.getStatus()!=null && accountCash.getStatus()!=9){
			criteria.add(Restrictions.eq("status",accountCash.getStatus()));
		}
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}

	@Override
	public List<AccountCash> queryAccountCashList(AccountCash accountCash) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(AccountCash.class);
		criteria.add(Restrictions.eq("status",accountCash.getStatus()));
		criteria.createAlias("user", "user");
		criteria.add(Restrictions.eq("user.id",accountCash.getUser().getId()));
		return  criteria.list();
	}
	
	public Integer queryAccountCashListCount(Map<String, Object> map){
		String hql = "select  count(*) as cashCount  from  fy_account_cash a where a.user_id =:userId and a.status in (:status) and a.create_date >=  :dateStart and a.create_date <= :dateStop ";
		return (Integer)getSession().createSQLQuery(hql)
				.addScalar("cashCount",Hibernate.INTEGER)
				.setInteger("userId", (Integer)map.get("userId"))
				.setParameterList("status", (Integer[])map.get("status"))
				.setTimestamp("dateStart", (Date)map.get("dateStart"))
				.setTimestamp("dateStop", (Date)map.get("dateStop")).uniqueResult();

	}

	
	public List<UserAccountCashDTO> getCashDTOList(AccountCash accountCash,
			Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		
		Session session = getSession();
		StringBuffer hql = new StringBuffer();
		
		String dateSql="";
		
		
		
		if(startDate != null && endDate != null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			String str1=sdf.format(startDate);  
			String str2=sdf.format(endDate); 
			
			dateSql=" and fy_account_cash.create_date between '" +str1+"' and '"+str2+" ' ";
		}
		
		if(accountCash.getStatus()!=null && accountCash.getStatus()!=9){
			dateSql+=" and fy_account_cash.status ='"+accountCash.getStatus()+"' ";
		}
		
		
	    hql.append("select c.id,u.username,u.real_name as realName,u.type_id as typeId,c.account,B.name as bankName,c.branch,c.total,c.credited,c.fee,c.change_num as changeNum ,c.create_date as createDate ,c.status ");
	    hql.append(" from (select * from fy_account_cash where 1=1 "+dateSql+" ) c ");
	    hql.append("  left join fy_user as u on u.id=c.user_id ");
	    hql.append(" left join (select key_value,name from fy_listing where parent_id in (SELECT id  FROM fy_listing where sign='account_bank' )) B ");
	    hql.append(" on B.key_value=c.bank where 1=1" );


	
		if(accountCash.getUser()!=null){
			
			if(accountCash.getUser().getTypeId()!=null){
				//criteria.add(Restrictions.eq("user.typeId",accountCash.getUser().getTypeId()));
			}
			if(StringUtils.isNotEmpty(accountCash.getUser().getUsername())){
				//criteria.add(Restrictions.like("user.username", "%"+accountCash.getUser().getUsername()+"%"));
				hql.append(" and u.username like '%"+accountCash.getUser().getUsername()+"%' ");
			}
		}
		
		hql.append(" order by c.id desc");
		
		
		Query query = session.createSQLQuery(hql.toString());

		query.setResultTransformer(Transformers.aliasToBean(UserAccountCashDTO.class));
		
		System.out.println("hql============="+hql.toString());
		
		return query.list();
		
	}
	

	

}
