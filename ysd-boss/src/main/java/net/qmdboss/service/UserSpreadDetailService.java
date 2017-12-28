package net.qmdboss.service;

import net.qmdboss.entity.UserSpreadDetail;

import java.math.BigDecimal;
import java.util.Map;

public interface UserSpreadDetailService extends BaseService<UserSpreadDetail, Integer>{

	public BigDecimal getTotalMoney(Map<String, Object> map);
}
