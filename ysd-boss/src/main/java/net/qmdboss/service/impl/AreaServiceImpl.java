package net.qmdboss.service.impl;

import net.qmdboss.dao.AreaDao;
import net.qmdboss.entity.Area;
import net.qmdboss.service.AreaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Service实现类 - 地区
 * ============================================================================
 */

@Service("areaServiceImpl")
public class AreaServiceImpl extends BaseServiceImpl<Area, Integer> implements AreaService {
	
	@Resource(name = "areaDaoImpl")
	AreaDao areaDao;

	@Resource(name = "areaDaoImpl")
	public void setBaseDao(AreaDao areaDao) {
		super.setBaseDao(areaDao);
	}
	
//	@Cacheable(modelId = "areaCaching")
	@Transactional(readOnly = true)
	public List<Area> getAreaList(Integer id) {
		List<Area> areaList = new ArrayList<Area>();
		if (id != null) {
			areaList = areaDao.getChildrenAreaList(id);
		} else {
			areaList = areaDao.getRootAreaList();
		}
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
	public List<Area> getChildrenAreaList(Integer id) {
		return areaDao.getChildrenAreaList(id);
	}


//	public String getAreaDomain(String id){
//		return areaDao.getAreaDomain(id);
//	}
//	
//	public String getAreaName(String id){
//		return areaDao.getAreaName(id);
//	}
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