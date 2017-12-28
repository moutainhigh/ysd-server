package net.qmdboss.dao.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.AccountBankDao;
import net.qmdboss.entity.AccountBank;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("accountBankDaoImpl")
public class AccountBankDaoImpl extends BaseDaoImpl<AccountBank, Integer>
		implements AccountBankDao {

	@Override
	public Pager findPagerByMap(Pager pager,Map<String, Object> map) {
		Criteria criteria = getSession().createCriteria(AccountBank.class);
		if(map != null && !map.isEmpty()){
			
			if(map.get("phone")!=null){
				criteria.createAlias("user", "user");
				criteria.add(Restrictions.like("user.phone", "%"+ (String)map.get("phone") +"%"));
			}
			
		}
		criteria.addOrder(Order.desc("id"));
		return super.findPager(pager, criteria);
	}
}