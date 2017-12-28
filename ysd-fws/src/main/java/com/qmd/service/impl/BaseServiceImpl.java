package com.qmd.service.impl;

import com.qmd.common.BasePager;
import com.qmd.dao.BaseDao;
import com.qmd.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Service实现类 - Service实现类基类
 */

@Transactional
public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {

	private BaseDao<T, PK> baseDao;

	public BaseDao<T, PK> getBaseDao() {
		return baseDao; 
	}

	public void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}
	
	@Transactional
	public int baseInsert(T entity) {
		return baseDao.baseInsert(entity);
	}
	
	@Transactional
	public void baseUpdate(T entity) {
		baseDao.baseUpdate(entity);
	}
	
	@Transactional(readOnly = true)
	public T baseLoad(PK id) {
		return baseDao.baseLoad(id);
	}
	
	@Transactional(readOnly = true)
	public List<T> baseList(Map<String ,Object> map){
		return baseDao.baseList(map);
	}
	
	@Transactional(readOnly = true)
	public T get(PK id) {
		return baseDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public T getById(PK id,T entity) {
		return baseDao.getById(id, entity);
	}
	@Transactional(readOnly = true)
	public T getById(PK id) {
		return baseDao.getById(id);
	}
	
	@Transactional
	public T getForUpdate(PK id,T entity) {
		return baseDao.getForUpdate(id, entity);
	}

	@Transactional(readOnly = true)
	public T load(PK id) {
		return baseDao.load(id);
	}

	@Transactional(readOnly = true)
	public List<T> getAllList() {
		return baseDao.getAllList();
	}
	
	@Transactional(readOnly = true)
	public Long getTotalCount() {
		return baseDao.getTotalCount();
	}

	@Transactional
	public PK save(T entity) {
		return baseDao.save(entity);
	}

	@Transactional
	public void update(T entity) {
		baseDao.update(entity);
	}
	
	@Transactional
	public void delete(T entity) {
		baseDao.delete(entity);
	}

	@Transactional
	public void delete(PK id) {
		baseDao.delete(id);
	}

	@Transactional
	public void delete(PK[] ids) {
		baseDao.delete(ids);
	}

	@Transactional(readOnly = true)
	public void flush() {
		baseDao.flush();
	}

	@Transactional(readOnly = true)
	public void evict(Object object) {
		baseDao.evict(object);
	}
	
	@Transactional(readOnly = true)
	public void clear() {
		baseDao.clear();
	}
	
	/**
	@Transactional(readOnly = true)
	public Pager findPager(Pager pager) {
		return baseDao.findPager(pager);
	}
	
	@Transactional(readOnly = true)
	public Pager findPager(Pager pager, Criterion... criterions) {
		return baseDao.findPager(pager, criterions);
	}
	
	@Transactional(readOnly = true)
	public Pager findPager(Pager pager, Order... orders) {
		return baseDao.findPager(pager, orders);
	}
	
	@Transactional(readOnly = true)
	public Pager findPager(Pager pager, Criteria criteria) {
		return baseDao.findPager(pager, criteria);
	}
**/
	public Object getByStatementId(String statementId,Map<String, Object> map){
		return this.baseDao.getByStatementId(statementId,map);
	}
	
	@Transactional(readOnly = true)
	public BasePager<T> queryPageByOjbect(T obj, Integer pageSize,
			Integer currentPage, String orderSql) {
		return baseDao.queryPageByObject(obj, pageSize, currentPage, orderSql);
	}
	@Transactional(readOnly = true)
	public List<T> queryListByObject(T obj) {
		return baseDao.queryListByObject(obj);
	}
	@Transactional(readOnly = true)
	public List<T> queryListByMap(Map<String, Object> map) {
		return baseDao.queryListByMap(map);
	}
	@Transactional
	public T getForUpdate(PK id) {
		return baseDao.getForUpdate(id);
	}
}