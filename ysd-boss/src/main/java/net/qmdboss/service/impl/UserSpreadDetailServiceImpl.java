package net.qmdboss.service.impl;

import net.qmdboss.dao.UserSpreadDetailDao;
import net.qmdboss.entity.UserSpreadDetail;
import net.qmdboss.service.UserSpreadDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@Service("userSpreadDetailServiceImpl")
public class UserSpreadDetailServiceImpl  extends BaseServiceImpl<UserSpreadDetail, Integer> implements UserSpreadDetailService {

	@Resource(name = "userSpreadDetailDaoImpl")
	private UserSpreadDetailDao userSpreadDetailDao;
	

	@Resource(name = "userSpreadDetailDaoImpl")
	public void setBaseDao(UserSpreadDetailDao userSpreadDetailDao) {
		super.setBaseDao(userSpreadDetailDao);
	}
	
	public BigDecimal getTotalMoney(Map<String, Object> map){
		return userSpreadDetailDao.getTotalMoney(map);
	}
}
