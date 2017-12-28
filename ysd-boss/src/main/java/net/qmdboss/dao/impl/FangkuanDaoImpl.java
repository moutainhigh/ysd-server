package net.qmdboss.dao.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.FangkuanDao;
import net.qmdboss.entity.Fangkuan;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("fangkuanDaoImpl")
public class FangkuanDaoImpl  extends BaseDaoImpl<Fangkuan, Integer> implements FangkuanDao {
	
	public Pager findPager(Pager pager,Map<String,Object> map){
		Criteria criteria = getSession().createCriteria(Fangkuan.class);
		if(map!= null){
			if(map.get("type") != null){
				criteria.add(Restrictions.eq("type", Integer.parseInt(map.get("type").toString())));
			}
			
			if(map.get("status") != null){
				criteria.add(Restrictions.eq("status", Integer.parseInt(map.get("status").toString())));
			}else{
				criteria.add(Restrictions.eq("status", 1));
			}
			
			if(map.get("name") != null){
				criteria.createAlias("borrow", "borrow");
				criteria.add(Restrictions.like("borrow.name", "%"+map.get("name").toString()+"%"));//根据项目名查询
			}

			if(map.get("borrowRealname") != null){
				criteria.createAlias("user", "user");
				criteria.add(Restrictions.eq("user.real_name", map.get("borrowRealname").toString()));//根据借款人姓名查询
			}
		}
		criteria.addOrder(Order.desc("id"));
		return super.findPager(pager, criteria);
	}

}
