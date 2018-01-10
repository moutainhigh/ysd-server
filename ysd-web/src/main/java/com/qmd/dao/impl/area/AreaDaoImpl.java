package com.qmd.dao.impl.area;

import com.qmd.dao.area.AreaDao;
import com.qmd.mode.area.Area;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Dao实现类 - 地区
 */

@Repository("areaDao")
public class AreaDaoImpl  extends SqlSessionDaoSupport  implements AreaDao {
	
	@Override
	public List<Area> getRootAreaList() {
		List<Area> areaList = this.getSqlSession().selectList("Area.getRootAreaList");
		return areaList;
	}
	
	@Override
	public List<Area> getParentAreaList(Area area) {
		return null;
//		if (area.getParent() == null) {
//			return null;
//		}
//		String parentPath = StringUtils.substringBeforeLast(area.getPath(), Area.PATH_SEPARATOR);
//		String[] ids = parentPath.split(Area.PATH_SEPARATOR);
//		String hql = "from Area as area where area.id in(:ids) order by area.grade asc, area.orderList asc";
//		return getSession().createQuery(hql).setParameterList("ids", ids).list();
	}
	
	@Override
	public List<Area> getChildrenAreaList(String pid) {
		List<Area> areaList = this.getSqlSession().selectList("Area.getChildrenAreaList",pid);
		return areaList;
//		String hql = "from Area as area where area.path like :path order by area.grade asc, area.orderList asc";
//		return getSession().createQuery(hql).setParameter("path", area.getPath() + Area.PATH_SEPARATOR + "%").list();
	}

	@Override
	public String getAreaDomain(String id){
		String domain = this.getSqlSession().selectOne("Area.getAreaDomain",id);
		return domain;
	}
	
	public String getAreaName(String id){
		String name = this.getSqlSession().selectOne("Area.getAreaName",id);
		return name;
	}
	/**
	// 自动设置displayName、path、grade
	@Override
	public String save(Area area) {
		area.setDisplayName(area.getName());
		area.setPath(area.getName());
		area.setGrade(0);
		super.save(area);
		fillArea(area);
		super.update(area);
		return area.getId();
	}
	
	// 自动设置displayName、path、grade
	@Override
	public void update(Area area) {
		fillArea(area);
		super.update(area);
		List<Area> childrenAreaList = getChildrenAreaList(area);
		if (childrenAreaList != null) {
			for (int i = 0; i < childrenAreaList.size(); i ++) {
				Area childrenArea = childrenAreaList.get(i);
				fillArea(childrenArea);
				super.update(childrenArea);
				if(i % 20 == 0) {
					flush();
					clear();
				}
			}
		}
	}
	
	private void fillArea(Area area) {
		Area parent = area.getParent();
		if (parent == null) {
			area.setPath(area.getId());
		} else {
			area.setPath(parent.getPath() + Area.PATH_SEPARATOR + area.getId());
		}
		area.setGrade(area.getPath().split(Area.PATH_SEPARATOR).length - 1);
		
		StringBuilder displayName = new StringBuilder();
		List<Area> parentAreaList = getParentAreaList(area);
		if (parentAreaList != null) {
			for (Area parentArea : parentAreaList) {
				displayName.append(parentArea.getName());
			}
		}
		displayName.append(area.getName());
		area.setDisplayName(displayName.toString());
	}
**/
}