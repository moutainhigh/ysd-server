package net.qmdboss.dao.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.BaseDao;
import net.qmdboss.entity.BaseEntity;
import net.qmdboss.util.ReflectionUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.OrderEntry;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao实现类 - 基类
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXCED32589174C8D5905B5D0A02A2F33DF
 * ============================================================================
 */

public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

	private static final String ORDER_LIST_PROPERTY_NAME = "orderList";// "排序"属性名称
	private static final String CREATE_DATE_PROPERTY_NAME = "createDate";// "创建日期"属性名称
	
	private Class<T> entityClass;
	protected SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
        Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
	}

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public T get(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public T load(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().load(entityClass, id);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public T loadLock(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().load(entityClass, id,LockMode.UPGRADE);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllList() {
		ClassMetadata classMetadata = sessionFactory.getClassMetadata(entityClass);
		String hql;
		if (ArrayUtils.contains(classMetadata.getPropertyNames(), ORDER_LIST_PROPERTY_NAME)) {
			hql = "from " + entityClass.getName() + " as entity order by entity." + ORDER_LIST_PROPERTY_NAME + " asc";
		} else {
			hql = "from " + entityClass.getName();
		}
		return getSession().createQuery(hql).list();
	}
	
	public Long getTotalCount() {
		String hql = "select count(*) from " + entityClass.getName();
		return (Long) getSession().createQuery(hql).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public PK save(T entity) {
		Assert.notNull(entity, "entity is required");
		if (entity instanceof BaseEntity) {
			try {
				Method method = entity.getClass().getMethod(BaseEntity.ON_SAVE_METHOD_NAME);
				method.invoke(entity);
				return (PK) getSession().save(entity);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return (PK) getSession().save(entity);
		}
	}

	public void update(T entity) {
		Assert.notNull(entity, "entity is required");
		if (entity instanceof BaseEntity) {
			try {
				Method method = entity.getClass().getMethod(BaseEntity.ON_UPDATE_METHOD_NAME);
				method.invoke(entity);
				getSession().update(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			getSession().update(entity);
		}
	}

	public void delete(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public void delete(PK id) {
		Assert.notNull(id, "id is required");
		T entity = (T) getSession().load(entityClass, id);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public void delete(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		for (PK id : ids) {
			T entity = (T) getSession().load(entityClass, id);
			getSession().delete(entity);
		}
	}

	public void flush() {
		getSession().flush();
	}

	public void evict(Object object) {
		Assert.notNull(object, "object is required");
		getSession().evict(object);
	}

	public void clear() {
		getSession().clear();
	}
	
	public Pager findPager(Pager pager) {
		Criteria criteria = getSession().createCriteria(entityClass);
		return findPager(pager, criteria);
	}
	
	public Pager findPager(Pager pager, Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		return findPager(pager, criteria);
	}
	
	public Pager findPager(Pager pager, Order... orders) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Order order : orders) {
			criteria.addOrder(order);
		}
		return findPager(pager, criteria);
	}
	
	public Pager findPager(Pager pager, Criteria criteria) {
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
		}else{
			criteria.addOrder(Order.desc("id"));
		}
		
		ClassMetadata classMetadata = sessionFactory.getClassMetadata(entityClass);
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
		
		pager.setResult(criteria.list());
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

}