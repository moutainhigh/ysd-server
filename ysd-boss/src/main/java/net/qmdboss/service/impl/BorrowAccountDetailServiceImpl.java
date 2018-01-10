package net.qmdboss.service.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.BorrowAccountDetailDao;
import net.qmdboss.entity.BorrowAccountDetail;
import net.qmdboss.service.BorrowAccountDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("borrowAccountDetailServiceImpl")
public class BorrowAccountDetailServiceImpl  extends BaseServiceImpl<BorrowAccountDetail, Integer> implements BorrowAccountDetailService {
	
	@Resource(name = "borrowAccountDetailDaoImpl")
	private BorrowAccountDetailDao borrowAccountDetailDao;
	
	@Resource(name = "borrowAccountDetailDaoImpl")
	public void setBaseDao(BorrowAccountDetailDao borrowAccountDetailDao) {
		super.setBaseDao(borrowAccountDetailDao);
	}
	

	public Pager findPager(Pager pager,Map<String,Object> map){
		return borrowAccountDetailDao.findPager(pager, map);
	}
}
