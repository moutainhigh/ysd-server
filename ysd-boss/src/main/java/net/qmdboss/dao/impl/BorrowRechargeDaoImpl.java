package net.qmdboss.dao.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.BorrowRechargeDao;
import net.qmdboss.entity.BorrowRecharge;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
/**
 * borrowRecharge 标dao实现类
 * @author Administrator
 *
 */
@Repository("borrowRechargeDaoImpl")
public class BorrowRechargeDaoImpl extends BaseDaoImpl<BorrowRecharge, Integer> implements
		BorrowRechargeDao {

	private static final String ORDER_LIST_PROPERTY_NAME = "orderList";// "排序"属性名称
	private static final String CREATE_DATE_PROPERTY_NAME = "createDate";// "创建日期"属性名称
	
	/**
	 * 通过条件查询分页
	 */
	@Override
	public Pager getBorrowRechargePager(BorrowRecharge borrowRecharge, Pager pager) {
		Criteria criteria = getSession().createCriteria(BorrowRecharge.class);
		if(borrowRecharge != null && borrowRecharge.getUser()!=null){
			criteria.createAlias("user", "user");
			criteria.add(Restrictions.like("user.username", "%"+borrowRecharge.getUser().getUsername()+"%"));
			
		}
		if(borrowRecharge != null && borrowRecharge.getType()!=null&& !borrowRecharge.getType().equals("")){
			criteria.add(Restrictions.eq("type",borrowRecharge.getType()));
			
		}
		if(borrowRecharge != null && borrowRecharge.getStatus()!=null){
			
				criteria.add(Restrictions.eq("status",borrowRecharge.getStatus()));	
		}
		
			criteria.addOrder(Order.desc("id"));
		return super.findPager(pager, criteria);
	}
	


	

}
