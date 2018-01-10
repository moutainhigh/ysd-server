package com.qmd.service.impl.area;

import com.qmd.dao.area.AreaDao;
import com.qmd.mode.area.Area;
import com.qmd.service.area.AreaService;
import com.qmd.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Service实现类 - 地区
 * ============================================================================
 */

@Service("areaService")
public class AreaServiceImpl extends BaseServiceImpl<Area, String> implements AreaService {
	
	@Resource
	AreaDao areaDao;

//	@Resource(name = "areaDaoImpl")
//	public void setBaseDao(AreaDao areaDao) {
//		super.setBaseDao(areaDao);
//	}
	
//	@Cacheable(modelId = "areaCaching")
	@Transactional(readOnly = true)
	public List<Area> getAreaList(String id) {
		List<Area> areaList = new ArrayList<Area>();
		if (StringUtils.isNotEmpty(id)) {
			
//			Area area = areaDao.load(id);
//			Set<Area> children = area.getChildren();
//			if (children != null) {
//				areaList = new ArrayList<Area>(children);
//			}
			areaList = areaDao.getChildrenAreaList(id);
		} else {
			areaList = areaDao.getRootAreaList();
		}
//		areaList = areaDao.getRootAreaList();
//		if (areaList != null) {
//			for (Area area : areaList) {
//				if (!Hibernate.isInitialized(area)) {
//					Hibernate.initialize(area);
//				}
//			}
//		}
		return areaList;
	}

	@Transactional(readOnly = true)
	public List<Area> getRootAreaList() {
		return areaDao.getRootAreaList();
	}
	
	@Transactional(readOnly = true)
	public List<Area> getParentAreaList(Area area) {
		return areaDao.getParentAreaList(area);
	}
	
	@Transactional(readOnly = true)
	public List<Area> getChildrenAreaList(String id) {
		return areaDao.getChildrenAreaList(id);
	}

	public String getAreaDomain(String id){
		return areaDao.getAreaDomain(id);
	}
	
	public String getAreaName(String id){
		return areaDao.getAreaName(id);
	}
	/**
	@Override
	@CacheFlush(modelId = "areaFlushing")
	public void delete(Area area) {
		areaDao.delete(area);
	}

	@Override
	@CacheFlush(modelId = "areaFlushing")
	public void delete(String id) {
		areaDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "areaFlushing")
	public void delete(String[] ids) {
		areaDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "areaFlushing")
	public String save(Area area) {
		return areaDao.save(area);
	}

	@Override
	@CacheFlush(modelId = "areaFlushing")
	public void update(Area area) {
		areaDao.update(area);
	}

**/
	
}