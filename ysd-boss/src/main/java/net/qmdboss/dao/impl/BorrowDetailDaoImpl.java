package net.qmdboss.dao.impl;

import net.qmdboss.dao.BorrowDetailDao;
import net.qmdboss.entity.Borrow;
import net.qmdboss.entity.BorrowDetail;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * BorrowDetailDaoImpl 投标明细列表dao实现类
 * 
 * @author Administrator
 *
 */
@Repository("borrowDetailDaoImpl")
public class BorrowDetailDaoImpl extends BaseDaoImpl<BorrowDetail, Integer>
		implements BorrowDetailDao {


	public List<BorrowDetail> getBorrowDetailList(Borrow borrow,Map<String,String> map){
//		Criteria criteria = getSession().createCriteria(BorrowDetail.class);
//		criteria.createAlias("borrow", "borrow");
//		criteria.add(Restrictions.eq("borrow", borrow));
//		if(map != null ){
//			criteria.addOrder(Order.desc("account"));
//		}
//		criteria.addOrder(Order.asc("id"));
		Query query = null;
		if(map != null ){
			String hql = "from BorrowDetail bd where bd.borrow.id = :borrowId order by bd.account *1 desc, bd.id asc";
			query = getSession().createQuery(hql);
		}else{//手动投标
			String hql = "from BorrowDetail bd where bd.borrow.id = :borrowId and bd.autoTenderStatus = 0 order by bd.id asc";
			query = getSession().createQuery(hql);
		}
		query.setParameter("borrowId", borrow.getId());
		
		return query.list();
	}
}
