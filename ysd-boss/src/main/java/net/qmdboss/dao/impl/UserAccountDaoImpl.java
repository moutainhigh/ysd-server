package net.qmdboss.dao.impl;

import net.qmdboss.DTO.UserAccountDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.UserAccountDao;
import net.qmdboss.entity.User;
import net.qmdboss.entity.UserAccount;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Dao实现类 - 用户账户类
 * @author 詹锋
 *
 */

@Repository("userAccountDaoImpl")
public class UserAccountDaoImpl extends BaseDaoImpl<UserAccount, Integer> implements UserAccountDao {
	
	public UserAccount loadLockTable(User user) {
		String hql = " from UserAccount as ua where user.id = "+user.getId();
		Query qy = getSession().createQuery(hql);
		qy.setLockMode("ua", LockMode.UPGRADE);
		List<UserAccount> list = qy.list();
		if (list.size() <=0) {
			return null;
		}
		return list.get(0);
	}

	
	public Pager findPagerByMoney(Pager pager,String type,String amount){
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.createAlias("account", "account");
		criteria.add(Restrictions.isNotNull("account.id"));
		if(StringUtils.isNotEmpty(type)){
			BigDecimal account = BigDecimal.valueOf(Double.valueOf(amount));
			if(type.equals("totalgt")){
				criteria.add(Restrictions.gt("account.total", account));
			}else if(type.equals("totallt")){
				criteria.add(Restrictions.lt("account.total", account));
			}else if(type.equals("canusegt")){
				criteria.add(Restrictions.gt("account.ableMoney", account));
			}else if(type.equals("canuselt")){
				criteria.add(Restrictions.lt("account.ableMoney", account));
			}else if(type.equals("nousegt")){
				criteria.add(Restrictions.gt("account.unableMoney", account));
			}else if(type.equals("nouselt")){
				criteria.add(Restrictions.lt("account.unableMoney", account));
			}
		}
		return super.findPager(pager, criteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAccountList(String username,String type,String amount){
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.createAlias("account", "account");
		criteria.add(Restrictions.isNotNull("account.id"));
		if(StringUtils.isNotEmpty(username)){
			criteria.add(Restrictions.like("username", "%"+username+"%"));
		}
		if(StringUtils.isNotEmpty(type)){
			BigDecimal account = BigDecimal.valueOf(Double.valueOf(amount));
			if(type.equals("totalgt")){
				criteria.add(Restrictions.gt("account.total", account));
			}else if(type.equals("totallt")){
				criteria.add(Restrictions.lt("account.total", account));
			}else if(type.equals("canusegt")){
				criteria.add(Restrictions.gt("account.ableMoney", account));
			}else if(type.equals("canuselt")){
				criteria.add(Restrictions.lt("account.ableMoney", account));
			}else if(type.equals("nousegt")){
				criteria.add(Restrictions.gt("account.unableMoney", account));
			}else if(type.equals("nouselt")){
				criteria.add(Restrictions.lt("account.unableMoney", account));
			}
		}
		return criteria.list();
	}
	
	
	public Pager findPagerByUser(Pager pager,Map<String,Object> map){
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.createAlias("account", "account");
		criteria.add(Restrictions.isNotNull("account.id"));
		
		if(map != null){
			Object type = map.get("type");
			Object amount = map.get("amount");
			if(type!=null && amount!=null){
				BigDecimal account =new BigDecimal(amount.toString());
				if(type.equals("totalgt")){
					criteria.add(Restrictions.gt("account.total", account));
				}else if(type.equals("totallt")){
					criteria.add(Restrictions.lt("account.total", account));
				}else if(type.equals("canusegt")){
					criteria.add(Restrictions.gt("account.ableMoney", account));
				}else if(type.equals("canuselt")){
					criteria.add(Restrictions.lt("account.ableMoney", account));
				}else if(type.equals("nousegt")){
					criteria.add(Restrictions.gt("account.unableMoney", account));
				}else if(type.equals("nouselt")){
					criteria.add(Restrictions.lt("account.unableMoney", account));
				}
			}
			Object memberType = map.get("memberType");
			if(memberType!=null){
				criteria.add(Restrictions.eq("memberType", Integer.parseInt(memberType.toString())));
			}
			Object typeId = map.get("typeId");
			if(typeId!=null){
				criteria.add(Restrictions.eq("typeId", Integer.parseInt(typeId.toString())));
			}
		}
		
		return super.findPager(pager, criteria);
	}
	
	/**
	 * 根据用户条件查询用户账户信息【导出数据用】
	 * @param pager
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> getPagerByUser(Map<String,Object> map){
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.createAlias("account", "account");
		criteria.add(Restrictions.isNotNull("account.id"));
		
		if(map != null){
			Object type = map.get("type");
			Object amount = map.get("amount");
			if(type!=null && amount!=null){
				BigDecimal account =new BigDecimal(amount.toString());
				if(type.equals("totalgt")){
					criteria.add(Restrictions.gt("account.total", account));
				}else if(type.equals("totallt")){
					criteria.add(Restrictions.lt("account.total", account));
				}else if(type.equals("canusegt")){
					criteria.add(Restrictions.gt("account.ableMoney", account));
				}else if(type.equals("canuselt")){
					criteria.add(Restrictions.lt("account.ableMoney", account));
				}else if(type.equals("nousegt")){
					criteria.add(Restrictions.gt("account.unableMoney", account));
				}else if(type.equals("nouselt")){
					criteria.add(Restrictions.lt("account.unableMoney", account));
				}
			}
			Object memberType = map.get("memberType");
			if(memberType!=null){
				criteria.add(Restrictions.eq("memberType", Integer.parseInt(memberType.toString())));
			}
			Object typeId = map.get("typeId");
			if(typeId!=null){
				criteria.add(Restrictions.eq("typeId", Integer.parseInt(typeId.toString())));
			}
			Object username = map.get("username");
			if(username != null && !"".equals(username.toString())){
				criteria.add(Restrictions.like("username", "%"+username+"%"));
			}
			criteria.addOrder(Order.desc("createDate"));
		}
		
		return criteria.list();
	}
	
	public BigDecimal getAbleMoneyAll(Integer userTypeId){
		Criteria criteria = getSession().createCriteria(UserAccount.class);
		if (userTypeId!=null) {
			criteria.createAlias("user", "user");
			criteria.add(Restrictions.eq("user.typeId", userTypeId));
		}
		criteria.setProjection( Projections.sum("ableMoney"));
		
		BigDecimal allmoney= (BigDecimal) criteria.uniqueResult();
		if (allmoney==null) {
			allmoney = BigDecimal.valueOf(0);
		}
		return allmoney;
	}
	
	public BigDecimal getUserAccountSum(Integer userTypeId,String sumName) {
		Criteria criteria = getSession().createCriteria(UserAccount.class);
		if (userTypeId!=null) {
			criteria.createAlias("user", "user");
			criteria.add(Restrictions.eq("user.typeId", userTypeId));
		}
		criteria.setProjection( Projections.sum(sumName));
		
		BigDecimal allmoney= (BigDecimal) criteria.uniqueResult();
		if (allmoney==null) {
			allmoney = BigDecimal.valueOf(0);
		}
		return allmoney;
	}
	
	/**
	 * 根据用户条件查询用户账户信息【导出数据用】原生sql
	 * @param pager
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserAccountDTO> findAccountDTOByUser(Map<String,Object> map){
		
	
		Session session = getSession();
		StringBuffer hql = new StringBuffer();
		String dateSql="";
		String dateSql2="";
	
		if(map != null){
			Object type = map.get("type");
			Object amount = map.get("amount");
			if(type!=null && amount!=null){
				if(type.equals("totalgt")){
					dateSql=" and a.total  > :accountVal ";
				}else if(type.equals("totallt")){
					dateSql=" and a.total  < :accountVal ";
				}else if(type.equals("canusegt")){
					dateSql=" and a.able_money  > :accountVal ";
				}else if(type.equals("canuselt")){
					dateSql=" and a.able_money  < :accountVal ";
				}else if(type.equals("nousegt")){
					dateSql=" and a.unableMoney  > :accountVal ";
				}else if(type.equals("nouselt")){
					dateSql=" and a.unableMoney  < :accountVal ";
				}
			}
			
			if (map.get("username") != null && StringUtils.isNotBlank(map.get("username").toString())){
				dateSql2=" and username like :username";
			}
		}
		hql.append("select a.id,username,real_name as realName,a.total,a.able_money as ableMoney, a.unable_money as unableMoney, ");
		hql.append("a.award_money as awardMoney ,a.investor_collection_capital as investorCollectionCapital,a.investor_collection_interest as investorCollectionInterest "); 
		hql.append(" from (select * from fy_user where 1=1 and fy_user.type_id=0 ");//服务商账号type_id=1
		hql.append(dateSql2);
		hql.append(" ) as u left join fy_user_account as a on a.user_id=u.id where 1=1 ");
		hql.append(dateSql);
		hql.append(" order by a.id desc");
		
		
		Query query = session.createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.aliasToBean(UserAccountDTO.class));
		
		if(map!=null){
			if ( map.get("type")!=null && map.get("amount")!=null) {
				BigDecimal accountVal =new BigDecimal(map.get("amount").toString());
				query.setParameter("accountVal",accountVal );
			}
			if (map.get("username") != null && StringUtils.isNotBlank(map.get("username").toString())) {
				query.setParameter("username", "%"+ map.get("username") +"%");
			}
		}
		
		
		System.out.println("hql============="+hql.toString());
		
		return query.list();
		
	}
	

	
}
