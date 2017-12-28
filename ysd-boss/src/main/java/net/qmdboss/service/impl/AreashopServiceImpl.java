package net.qmdboss.service.impl;

import net.qmdboss.dao.AreashopDao;
import net.qmdboss.entity.Areashop;
import net.qmdboss.service.AreashopService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service实现类 - 地区
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX2B3A4FEF05A42E033E79040EB368B28E
 * ============================================================================
 */

@Service("areashopServiceImpl")
public class AreashopServiceImpl extends BaseServiceImpl<Areashop, Integer> implements AreashopService {
	
	@Resource(name = "areashopDaoImpl")
	private AreashopDao areashopDao;

	@Resource(name = "areashopDaoImpl")
	public void setBaseDao(AreashopDao areashopDao) {
		super.setBaseDao(areashopDao);
	}
	
	@Cacheable(modelId = "areaCaching")
	@Transactional(readOnly = true)
	public List<Areashop> getAreaList(Integer id) {
		List<Areashop> areaList = new ArrayList<Areashop>();
		if (id != null) {
			Areashop area = areashopDao.load(id);
			Set<Areashop> children = area.getChildren();
			if (children != null) {
				areaList = new ArrayList<Areashop>(children);
			}
		} else {
			areaList = areashopDao.getRootAreaList();
		}
		if (areaList != null) {
			for (Areashop area : areaList) {
				if (!Hibernate.isInitialized(area)) {
					Hibernate.initialize(area);
				}
			}
		}
		return areaList;
	}

	@Transactional(readOnly = true)
	public List<Areashop> getRootAreaList() {
		return areashopDao.getRootAreaList();
	}
	
	@Transactional(readOnly = true)
	public List<Areashop> getParentAreaList(Areashop area) {
		return areashopDao.getParentAreaList(area);
	}
	
	@Transactional(readOnly = true)
	public List<Areashop> getChildrenAreaList(Areashop area) {
		return areashopDao.getChildrenAreaList(area);
	}

	@Override
	@CacheFlush(modelId = "areaFlushing")
	public void delete(Areashop area) {
		areashopDao.delete(area);
	}

	@Override
	@CacheFlush(modelId = "areaFlushing")
	public void delete(Integer id) {
		areashopDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "areaFlushing")
	public void delete(Integer[] ids) {
		areashopDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "areaFlushing")
	public Integer save(Areashop area) {
		return areashopDao.save(area);
	}

	@Override
	@CacheFlush(modelId = "areaFlushing")
	public void update(Areashop area) {
		areashopDao.update(area);
	}

}