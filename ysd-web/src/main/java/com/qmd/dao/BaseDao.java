package com.qmd.dao;

import com.qmd.util.Pager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * Dao接口 - Dao基接口 
 */

public interface BaseDao<T, PK extends Serializable> {
	
	/**
	 * 获取Session
	 */
//	public Session getSession();

	/**
	 * 根据ID获取实体对象
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T get(PK id);
	
	public T getForUpdate(PK id,T entity);
	
	public T getById(PK id,T entity);

	/**
	 * 根据ID获取实体对象
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T load(PK id);

	/**
	 * 获取所有实体对象集合
	 * 
	 * @return 实体对象集合
	 */
	public List<T> getAllList();
	
	/**
	 * 获取所有实体对象总数
	 * 
	 * @return 实体对象总数
	 */
	public Long getTotalCount();

	/**
	 * 保存实体对象
	 * 
	 * @param entity
	 *            对象
	 * @return ID
	 */
	public PK save(T entity);

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            对象
	 */
	public void update(T entity);
	
	/**
	 * 删除实体对象
	 * 
	 * @param entity
	 *            对象
	 * @return
	 */
	public void delete(T entity);

	/**
	 * 根据ID删除实体对象
	 * 
	 * @param id
	 *            记录ID
	 */
	public void delete(PK id);

	/**
	 * 根据ID数组删除实体对象
	 * 
	 * @param ids
	 *            ID数组
	 */
	public void delete(PK[] ids);

	/**
	 * 刷新session
	 * 
	 */
	public void flush();

	/**
	 * 清除对象
	 * 
	 * @param object
	 *            需要清除的对象
	 */
	public void evict(Object object);
	
	/**
	 * 清除Session
	 * 
	 */
	public void clear();
	
	/**
	 * 根据Pager进行查询(提供分页、查找、排序功能)
	 * 
	 * @param pager
	 *            Pager对象
	 * 
	 * @return Pager对象
	 */
//	public Pager findPager(Pager pager);
	
	/**
	 * 根据Pager、Criterion进行查询(提供分页、查找、排序功能)
	 * 
	 * @param pager
	 *            Pager对象
	 *            
	 * @param criterions
	 *            Criterion数组
	 * 
	 * @return Pager对象
	 */
//	public Pager findPager(Pager pager, Criterion... criterions);
	
	/**
	 * 根据Pager、Criterion进行查询(提供分页、查找、排序功能)
	 * 
	 * @param pager
	 *            Pager对象
	 *            
	 * @param orders
	 *            Order数组
	 * 
	 * @return Pager对象
	 */
//	public Pager findPager(Pager pager, Order... orders);
	
	/**
	 * 根据Pager、Criteria进行查询(提供分页、查找、排序功能)
	 * 
	 * @param pager
	 *            Pager对象
	 *            
	 * @param criteria
	 *            Criteria对象
	 * 
	 * @return Pager对象
	 */
//	public Pager findPager(Pager pager, Criteria criteria);
	public Object getByStatementId(String statementId,Map<String, Object> map);
	
	
	public List<T> baseList(Map<String ,Object> map);

	public List<T> queryListByObject(T obj);
	
	public  Pager queryPageByObject(T obj, Integer pageSize,
			Integer currentPage, String orderSql);

	public List<T> queryListByMap(Map<String, Object> map);
	public Integer queryCountByMap(Map<String, Object> map);
	public int baseInsert(T entity);
}

