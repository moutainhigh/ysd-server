package net.qmdboss.dao.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.BorrowDao;
import net.qmdboss.entity.Borrow;
import net.qmdboss.entity.UserAward;
import net.qmdboss.util.ReflectionUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.OrderEntry;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * borrow 标dao实现类
 * @author Administrator
 *
 */
@Repository("borrowDaoImpl")
public class BorrowDaoImpl extends BaseDaoImpl<Borrow, Integer> implements
		BorrowDao {

	private static final String ORDER_LIST_PROPERTY_NAME = "orderList";// "排序"属性名称
	private static final String CREATE_DATE_PROPERTY_NAME = "createDate";// "创建日期"属性名称
	@Override
	public boolean isExistByUsername(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Borrow getBorrowByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 通过条件查询分页
	 */
	@Override
	public Pager getBorrowPager(Borrow borrow, Pager pager) {
		Criteria criteria = getSession().createCriteria(Borrow.class);
		if(borrow != null && borrow.getUser()!=null){
			criteria.createAlias("user", "user");
			criteria.add(Restrictions.like("user.username", "%"+borrow.getUser().getUsername()+"%"));
			
		}
		if(borrow != null && borrow.getName()!=null){
			criteria.add(Restrictions.like("name", "%"+borrow.getName()+"%"));
			
		}
		
		
		if(borrow != null && borrow.getType()!=null&& !borrow.getType().equals("")){
			criteria.add(Restrictions.eq("type",borrow.getType()));
			
		}
		if(borrow != null && borrow.getStatus()!=null){
			
				criteria.add(Restrictions.eq("status",borrow.getStatus()));	
		}
		
			criteria.addOrder(Order.desc("id"));
		return super.findPager(pager, criteria);
	}
	
	@Override
	public Pager getOutDateBorrowPager(Borrow borrow, Integer borrowId,
			Pager pager) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Borrow.class);
		if(borrowId!=null){
			criteria.add(Restrictions.eq("borrow.id",borrowId));
		}
		if(borrow !=null){
			if(borrow.getUser()!=null){
				criteria.createAlias("user", "user");
				criteria.add(Restrictions.like("user.username", "%"+borrow.getUser().getUsername()+"%"));
				
			}
			if(borrow.getType()!=null&& !borrow.getType().equals("")&& borrow.getType()!="2"){
				criteria.add(Restrictions.eq("type",borrow.getType()));
				
			}
		}
		String types[] = {"0","1","3","4","5","6","7","8","9","10"};
		criteria.add(Restrictions.in("type", types));
		criteria.add(Restrictions.eq("status",1));
		criteria.addOrder(Order.desc("id"));
		return findBorrowPager(pager, criteria);  
	}

	/**
	 * 更新实体对象
	 * 
	 * @param borrow
	 *            对象
	 */
	public void update(Borrow borrow){
		
		super.update(borrow);
//		flush();
	}

	
	public BigDecimal getAccountYesTotal(Date startDate,Date endDate){
		Criteria criteria = getSession().createCriteria(Borrow.class);
		criteria.add(Restrictions.in("status",  new Object[]{3,7}));
		criteria.setProjection( Projections.sum("accountYes"));
		if(startDate != null && endDate != null){
			criteria.add(Restrictions.between("successTime", startDate, endDate));
		}
		BigDecimal accountYesTotal= (BigDecimal) criteria.uniqueResult();
		return accountYesTotal;
	}
	
	
	
	
	public Pager findBorrowPager(Pager pager, Criteria criteria) {
		Assert.notNull(pager, "pager is required");
		Assert.notNull(criteria, "criteria is required");
		
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
		String searchBy = pager.getSearchBy();
		String keyword = pager.getKeyword();
		String orderBy = pager.getOrderBy();
		Pager.Order order = pager.getOrder();
		
		if (StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(keyword)) {
			if (searchBy.contains(".")) {
				String alias = StringUtils.substringBefore(searchBy, ".");
				criteria.createAlias(alias, alias);
			}
			criteria.add(Restrictions.like(searchBy, "%" + keyword + "%"));
		}
		
		pager.setTotalCount(criteriaResultTotalCount(criteria));
		
		if (StringUtils.isNotEmpty(orderBy) && order != null) {
			if (order == Pager.Order.asc) {
				criteria.addOrder(Order.asc(orderBy));
			} else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		
		ClassMetadata classMetadata = sessionFactory.getClassMetadata(Borrow.class);
		if (!StringUtils.equals(orderBy, ORDER_LIST_PROPERTY_NAME) && ArrayUtils.contains(classMetadata.getPropertyNames(), ORDER_LIST_PROPERTY_NAME)) {
			criteria.addOrder(Order.asc(ORDER_LIST_PROPERTY_NAME));
			criteria.addOrder(Order.desc(CREATE_DATE_PROPERTY_NAME));
			if (StringUtils.isEmpty(orderBy) || order == null) {
				pager.setOrderBy(ORDER_LIST_PROPERTY_NAME);
				pager.setOrder(Pager.Order.asc);
			}
		} else if (!StringUtils.equals(orderBy, CREATE_DATE_PROPERTY_NAME) && ArrayUtils.contains(classMetadata.getPropertyNames(), CREATE_DATE_PROPERTY_NAME)) {
			criteria.addOrder(Order.desc(CREATE_DATE_PROPERTY_NAME));
			if (StringUtils.isEmpty(orderBy) || order == null) {
				pager.setOrderBy(CREATE_DATE_PROPERTY_NAME);
				pager.setOrder(Pager.Order.desc);
			}
		}
		
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		List<Borrow> borrowList = criteria.list();
		List<Borrow> setBorrowList= new ArrayList();
		System.out.println(borrowList.size());
		for( Borrow bor:borrowList){
			if(bor.getValidTime()==null||bor.getCreateDate()==null){
				System.out.println("wei null");
			}else{
				if(bor.getCreateDate().before(getDateTime(bor.getValidTime()))){
					setBorrowList.add(bor);
				}
			}
		}
		pager.setResult(setBorrowList);
		return pager;
	}
	
	// 获取Criteria查询数量
	@SuppressWarnings("unchecked")
	private int criteriaResultTotalCount(Criteria criteria) {
		Assert.notNull(criteria, "criteria is required");
		
		int criteriaResultTotalCount = 0;
		try {
			CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
			
			Projection projection = criteriaImpl.getProjection();
			ResultTransformer resultTransformer = criteriaImpl.getResultTransformer();
			List<OrderEntry> orderEntries = (List) ReflectionUtil.getFieldValue(criteriaImpl, "orderEntries");
			ReflectionUtil.setFieldValue(criteriaImpl, "orderEntries", new ArrayList());
			
			Integer totalCount = ((Long) criteriaImpl.setProjection(Projections.rowCount()).uniqueResult()).intValue();
			if (totalCount != null) {
				criteriaResultTotalCount = totalCount;
			}
			
			criteriaImpl.setProjection(projection);
			if (projection == null) {
				criteriaImpl.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
			}
			if (resultTransformer != null) {
				criteriaImpl.setResultTransformer(resultTransformer);
			}
			ReflectionUtil.setFieldValue(criteriaImpl, "orderEntries", orderEntries);
		} catch (Exception e) {
			
		}
		return criteriaResultTotalCount;
	}
	
	
	public Date getDateTime(String num){
		
		Date endTime = new Date();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date lastTime = null;
		int number = Integer.parseInt(num);
		Calendar calendar;
		try {
			Date max  = simple.parse(simple.format(endTime));
			calendar = Calendar.getInstance();
			calendar.setTime(max);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-number);
			lastTime= calendar.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lastTime;
		
	}

	
	
	
	
	
	
	
	
	
			

}
