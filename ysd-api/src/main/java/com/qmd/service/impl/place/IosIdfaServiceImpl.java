package com.qmd.service.impl.place;

import com.qmd.dao.place.IosIdfaDao;
import com.qmd.dao.place.PlaceChilderDao;
import com.qmd.dao.user.UserAnalysDao;
import com.qmd.mode.place.IosIdfa;
import com.qmd.mode.place.PlaceChilder;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.place.IosIdfaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("iosIdfaService")
public class IosIdfaServiceImpl extends BaseServiceImpl<IosIdfa, Integer>
		implements IosIdfaService {
	@SuppressWarnings("unused")
	@Resource
	private IosIdfaDao iosIdfaDao;
	@Resource
	private UserAnalysDao userAnalysDao;
	@Resource
	private PlaceChilderDao placeChilderDao;
	@Resource
	public void setBaseDao(IosIdfaDao iosIdfaDao) {
		super.setBaseDao(iosIdfaDao);
	}

	@Override
	public void add(IosIdfa entity) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(entity.getIdfa())){
			map.clear();
			map.put("idfa", entity.getIdfa());
			Integer n = iosIdfaDao.queryCountByMap(map);
			if(n !=null && n > 0){
				Integer nn = userAnalysDao.queryCountByMap(map);
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("imei", entity.getIdfa());
				Integer nn1 = userAnalysDao.queryCountByMap(map1);
				//List<UserAnalysDao> listUA=userAnalysDao.getListByImei(map);
				if(nn==0&&nn1==0){
					entity.setId(iosIdfaDao.queryListByMap(map).get(0).getId());
					map.clear();
					map.put("random", entity.getSource());
					List<PlaceChilder> list = placeChilderDao.queryListByMap(map);
					if(list != null && list.size() > 0){
						entity.setPlaceChilderId(list.get(0).getId());
					}
					iosIdfaDao.update(entity);
				}
				return;
			}
		}
		
		if(StringUtils.isNotBlank(entity.getMac())){	
			map.clear();
			map.put("mac", entity.getMac());
			Integer n = iosIdfaDao.queryCountByMap(map );
			if(n !=null && n > 0){
				return;
			}
		}
		
		map.clear();
		map.put("random", entity.getSource());
		List<PlaceChilder> list = placeChilderDao.queryListByMap(map);
		if(list != null && list.size() > 0){
			entity.setPlaceChilderId(list.get(0).getId());
		}
		iosIdfaDao.save(entity);
	}
}
