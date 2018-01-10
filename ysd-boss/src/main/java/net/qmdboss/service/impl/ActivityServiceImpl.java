package net.qmdboss.service.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.ActivityDao;
import net.qmdboss.entity.Activity;
import net.qmdboss.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("activityServiceImpl")
public class ActivityServiceImpl extends BaseServiceImpl<Activity, Integer>
		implements ActivityService {
	@Resource
	private ActivityDao activityDao;

	@Resource
	public void setBaseDao(ActivityDao activityDao) {
		super.setBaseDao(activityDao);
	}

	@Override
	public Pager findPager(Pager pager, Map<String, Object> map) {
		
		return activityDao.findPager(pager, map);
	}
}
