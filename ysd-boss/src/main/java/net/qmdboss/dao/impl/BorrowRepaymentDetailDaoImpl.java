package net.qmdboss.dao.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.BorrowRepaymentDetailDao;
import net.qmdboss.entity.BorrowRepaymentDetail;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * BorrowRepaymentDetailDaoImpl 标还款明细dao实现类
 * 
 * @author Administrator
 *
 */
@Repository("borrowRepaymentDetailDaoImpl")
public class BorrowRepaymentDetailDaoImpl extends BaseDaoImpl<BorrowRepaymentDetail, Integer> implements BorrowRepaymentDetailDao {


	/**
	 * 查询逾期标
	 * @param pager
	 * @return
	 */
	public Pager findLate(Pager pager){
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
		Criteria criteria = getSession().createCriteria(BorrowRepaymentDetail.class);
		if(StringUtils.isNotEmpty(pager.getKeyword()) &&  pager.getSearchBy().equals("borrow.user.username")){
			criteria.createAlias("borrow", "borrow");
			criteria.createAlias("borrow.user", "user");
			criteria.add(Restrictions.like("user.username", "%"+pager.getKeyword() +"%"));
			pager.setSearchBy("");
		}
		criteria.add(Restrictions.or(Restrictions.eq("status", 0), Restrictions.eq("webstatus", 1)));
		try {
			criteria.add(Restrictions.lt("repaymentTime", dateformat.parse(dateformat.format(new Date()))));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.findPager(pager, criteria);
	}
	
	/**
	 * 未还款
	 * @param pager
	 * @return
	 */
	public Pager findRepaymentNot(Pager pager){
		
		pager.setOrderBy("repaymentTime");
		pager.setOrder(Pager.Order.asc);
		
		//SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
		Criteria criteria = getSession().createCriteria(BorrowRepaymentDetail.class);
		if(StringUtils.isNotEmpty(pager.getKeyword()) &&  pager.getSearchBy().equals("borrow.user.username")){
			criteria.createAlias("borrow", "borrow");
			criteria.createAlias("borrow.user", "user");
			criteria.add(Restrictions.like("user.username", "%"+pager.getKeyword() +"%"));
			pager.setSearchBy("");
		}
		criteria.add(Restrictions.eq("status", 0));
//		try {
//			criteria.add(Restrictions.lt("repaymentTime", dateformat.parse(dateformat.format(new Date()))));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return super.findPager(pager, criteria);
	}
	
	/**
	 * 未还款
	 * @param pager
	 * @return
	 */
	public Pager findRepayment(Pager pager,Integer status){
		//SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
		Criteria criteria = getSession().createCriteria(BorrowRepaymentDetail.class);
		if(StringUtils.isNotEmpty(pager.getKeyword()) &&  pager.getSearchBy().equals("borrow.user.username")){
			criteria.createAlias("borrow", "borrow");
			criteria.createAlias("borrow.user", "user");
			criteria.add(Restrictions.like("user.username", "%"+pager.getKeyword() +"%"));
			pager.setSearchBy("");
		}
		if (status != null && status.intValue() >= 0) {
			criteria.add(Restrictions.eq("status", status));
		}
//		criteria.add(Restrictions.eq("status", 0));
//		try {
//			criteria.add(Restrictions.lt("repaymentTime", dateformat.parse(dateformat.format(new Date()))));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return super.findPager(pager, criteria);
	}
	
	public BigDecimal getRepaymentAccountTotal(Integer status,Date startDate,Date endDate){
		Criteria criteria = getSession().createCriteria(BorrowRepaymentDetail.class);
		criteria.add(Restrictions.eq("status",  status));
		if(status != null && status == 0){
			criteria.setProjection(Projections.sum("repaymentAccount"));
			if( startDate != null && endDate != null){
				criteria.add(Restrictions.between("repaymentTime", startDate, endDate));
			}
		}else{
			criteria.setProjection(Projections.sum("repaymentYesaccount"));
			if( startDate != null && endDate != null){
				criteria.add(Restrictions.between("repaymentYestime", startDate, endDate));
			}
		}
		BigDecimal repaymentAccountTotal= (BigDecimal) criteria.uniqueResult();
		return repaymentAccountTotal;
	}

	@Override
	public List<BorrowRepaymentDetail> getByBorrowId(Integer borrowId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(BorrowRepaymentDetail.class);
		
			criteria.createAlias("borrow", "borrow");
			criteria.add(Restrictions.eq("borrow.id",  borrowId));
			criteria.addOrder(Order.desc("orderNum"));
			return criteria.list();
	}
	
	
	
	public void deleteByBorrowId(Integer borrowId){


		getSession().delete("from BorrowRepaymentDetail as c where c.borrow.id=?",borrowId);
	}
	
	
	
	
	
	
	
	
	
	
}
