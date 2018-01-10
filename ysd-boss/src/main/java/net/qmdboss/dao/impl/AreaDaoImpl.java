package net.qmdboss.dao.impl;

import net.qmdboss.dao.AreaDao;
import net.qmdboss.entity.Area;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Dao实现类 - 地区
 */

@Repository("areaDaoImpl")
public class AreaDaoImpl extends BaseDaoImpl<Area, Integer> implements AreaDao {
	
	@SuppressWarnings("unchecked")
	public List<Area> getRootAreaList() {
		String hql = "from Area as area where area.pid is null order by area.orderList asc";
		return getSession().createQuery(hql).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Area> getParentAreaList(Area area) {
		return null;
	}
	
	@Override
	public List<Area> getChildrenAreaList(Integer pid) {
		String hql = "from Area as area where pid =:pid order by area.orderList asc";
		return getSession().createQuery(hql).setParameter("pid", pid).list();
	}

//	@Override
//	public String getAreaDomain(String id){
//		String domain = this.getSqlSession().selectOne("Area.getAreaDomain",id);
//		return domain;
//		String hql = "from Area as area where id =:id, area.orderList asc";
//		return getSession().createQuery(hql).setParameter("pid", pid).list();
//	}
//	
//	public String getAreaName(String id){
//		String name = this.getSqlSession().selectOne("Area.getAreaName",id);
//		return name;
//	}

}