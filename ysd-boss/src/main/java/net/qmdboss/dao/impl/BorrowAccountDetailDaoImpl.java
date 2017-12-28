package net.qmdboss.dao.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.BorrowAccountDetailDao;
import net.qmdboss.entity.BorrowAccountDetail;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("borrowAccountDetailDaoImpl")
public class BorrowAccountDetailDaoImpl  extends BaseDaoImpl<BorrowAccountDetail, Integer> implements BorrowAccountDetailDao {

	public Pager findPager(Pager pager,Map<String,Object> map){
		Criteria criteria = getSession().createCriteria(BorrowAccountDetail.class);
		if(map!= null){
			
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
