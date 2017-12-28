package net.qmdboss.dao.impl;

import net.qmdboss.dao.UserSpreadDetailDao;
import net.qmdboss.entity.UserSpreadDetail;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Repository("userSpreadDetailDaoImpl")
public class UserSpreadDetailDaoImpl  extends BaseDaoImpl<UserSpreadDetail, Integer> implements UserSpreadDetailDao{

	public BigDecimal getTotalMoney(Map<String, Object> map){
		Criteria criteria = getSession().createCriteria(UserSpreadDetail.class);
		Date startDate = (Date) map.get("startDate");
		Date endDate =(Date) map.get("endDate");
		if(startDate != null && endDate != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			endDate = calendar.getTime();
			criteria.add(Restrictions.between("createDate", startDate, endDate));
		}
		Object tgUserId = map.get("tgUserId");
		if(tgUserId!=null){
			criteria.add(Restrictions.eq("tgUserId",tgUserId));
		}
		Object statusCode = map.get("statusCode");
		if(statusCode!=null){
			criteria.add(Restrictions.eq("statusCode",statusCode));
		}
		criteria.add(Restrictions.gt("tenderMoney",new BigDecimal(0)));
		criteria.setProjection( Projections.sum("tgMoney"));
		
		BigDecimal money= (BigDecimal) criteria.uniqueResult();
		return money;
	}
}
