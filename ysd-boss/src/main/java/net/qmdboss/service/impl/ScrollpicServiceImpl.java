package net.qmdboss.service.impl;

import net.qmdboss.dao.ScrollpicDao;
import net.qmdboss.entity.Scrollpic;
import net.qmdboss.service.ScrollpicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
 * service实现类-首页图片滚动
 * @author Xsf
 *
 */

@Service("scrollpicServiceImpl")
public class ScrollpicServiceImpl extends BaseServiceImpl<Scrollpic, Integer> implements ScrollpicService{
	
	@Resource(name = "scrollpicDaoImpl")
	private ScrollpicDao scrollpicDao;

	@Resource(name = "scrollpicDaoImpl")
	public void setBaseDao(ScrollpicDao scrollpicDao) {
		super.setBaseDao(scrollpicDao);
	}
	
	@Override
	public void delete(Scrollpic scrollpic) {
		scrollpicDao.delete(scrollpic);
	}

	@Override
	public void delete(Integer id) {
		scrollpicDao.delete(id);
	}

	@Override
	public void delete(Integer[] ids) {
		scrollpicDao.delete(ids);
	}

	@Override
	public Integer save(Scrollpic scrollpic) {
		return scrollpicDao.save(scrollpic);
	}

	@Override
	public void update(Scrollpic scrollpic) {
		scrollpicDao.update(scrollpic);
	}
}
