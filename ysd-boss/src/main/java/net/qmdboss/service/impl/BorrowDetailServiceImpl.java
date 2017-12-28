package net.qmdboss.service.impl;

import net.qmdboss.dao.BorrowDetailDao;
import net.qmdboss.entity.BorrowDetail;
import net.qmdboss.service.BorrowDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 投标列表Service实现类
 * @author zhanf
 *
 */
@Service("borrowDetailServiceImpl")
public class BorrowDetailServiceImpl extends
		BaseServiceImpl<BorrowDetail, Integer> implements BorrowDetailService {
	@Resource(name = "borrowDetailDaoImpl")
	private BorrowDetailDao  borrowDetailDao;
	@Resource(name = "borrowDetailDaoImpl")
	public void setBaseDao(BorrowDetailDao borrowDetailDao) {
		super.setBaseDao(borrowDetailDao);
	}
}
