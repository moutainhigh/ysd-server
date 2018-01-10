package net.qmdboss.dao;

import net.qmdboss.entity.UserSpreadDetail;

import java.math.BigDecimal;
import java.util.Map;

public interface UserSpreadDetailDao extends BaseDao<UserSpreadDetail, Integer> {
	
	
	public BigDecimal getTotalMoney(Map<String, Object> map);
}
