package com.qmd.dao.impl;

import com.qmd.dao.BaseDao;
import com.qmd.util.ModelToMap;
import com.qmd.util.Pager;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;



/**
 * Dao实现类 - 基类
 */

public class BaseDaoImpl<T, PK extends Serializable> extends SqlSessionDaoSupport implements BaseDao<T, PK> {

	private static final String ORDER_LIST_PROPERTY_NAME = "orderList";// "排序"属性名称
	private static final String CREATE_DATE_PROPERTY_NAME = "createDate";// "创建日期"属性名称
	
	private Class<T> entityClass;
//	protected SessionFactory sessionFactory;
	
//	public Session getSession() {
//		return sessionFactory.getCurrentSession();
//	}
	private String namespase;
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
        Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
	}
	public String getNameSpace(T entity){
		String cName = entity.getClass().getName();
		int start = cName.lastIndexOf(".");
		String namespace = cName.substring(start + 1);
		return namespace;
	}
	@Override
	public Integer queryCountByMap(Map<String, Object> map) {
		Integer totalSize = (Integer) getSqlSession().selectOne(
				getClassNameSpace() + ".queryCountByMap", map);
		return totalSize;
	}
	
	@Override
	public T get(PK id) {
		return this.getSqlSession().selectOne(getClassNameSpace() + ".getById", id);
	}
	
	@Override
	public T getForUpdate(PK id,T entity) {
		
		return this.getSqlSession().selectOne(getNameSpace(entity) + ".getForUpdate", id);
	}
	
	@Override
	public T getById(PK id,T entity) {
		
		return this.getSqlSession().selectOne(getNameSpace(entity) + ".getById", id);
	}

	@Override
	public T load(PK id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getAllList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getTotalCount() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PK save(T entity) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(getNameSpace(entity) + ".insert",entity);
		return null;
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		this.getSqlSession().update(getNameSpace(entity) + ".update", entity);
	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(PK id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(PK[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void evict(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public Object getByStatementId(String statementId,Map<String, Object> map){
		return this.getSqlSession().selectOne(statementId,map);
	}
	
	
	//*******************/
	public String getClassNameSpace() {
		String cName = entityClass.getName();
		int start = cName.lastIndexOf(".");
		String namespace = cName.substring(start + 1);
		return namespace;
	}
	public List<T> baseList(Map<String ,Object> map){
		return  this.getSqlSession().selectList(getClassNameSpace() + ".baseList", map);
	}
	
	public List<T> queryListByObject(T obj) {
		Map<String, Object> map = ModelToMap.model2Map(obj);
		return queryListByMap(map);

	}
	public List<T> queryListByMap(Map<String, Object> map) {
//		if(map != null){
//			if(map.get("pageStart") != null && StringUtils.isNotEmpty(map.get("pageStart").toString())){
//				map.put("pageStart", map.get("pageStart").toString());
//			}else{
//				map.put("pageStart", 0);
//			}
//			
//			if(map.get("pageSize") != null && StringUtils.isNotEmpty(map.get("pageSize").toString())){
//				map.put("pageSize", map.get("pageSize").toString());
//			}else{
//				map.put("pageSize", 10);
//			}
//		}
		return getSqlSession().selectList(
				getClassNameSpace() + ".queryListByMap", map);
	}
	@Override
	public Pager queryPageByObject(T obj, Integer pageSize,
			Integer currentPage, String orderSql) {
		// TODO Auto-generated method stub
		List<T> list = null;
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(currentPage == null ? 1 : currentPage);

		Map<String, Object> map = ModelToMap.model2Map(obj);

		Integer totalSize = (Integer) getSqlSession().selectOne(
				getClassNameSpace() + ".queryCountByMap", map);

		pager.setTotalCount(totalSize);

		map.put("orderBy", orderSql);
		map.put("pageStart", (pager.getPageNumber() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());

		list = getSqlSession().selectList(
				getClassNameSpace() + ".queryListByMap", map);

		pager.setResult(list);
		return pager;
	}
	public int baseInsert(T entity) {
		return this.getSqlSession().insert(getClassNameSpace() + ".baseInsert",
				entity);
	}
}