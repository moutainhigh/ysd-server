package net.qmdboss.dao.impl;

import net.qmdboss.dao.UserAwardDetailDao;
import net.qmdboss.entity.UserAwardDetail;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


/**
 *
 */

@Repository("userAwardDetailDaoImpl")
public class UserAwardDetailDaoImpl extends
		BaseDaoImpl<UserAwardDetail, Integer> implements UserAwardDetailDao {

	@Override
	public BigDecimal getTotalMoneyFriendAward(Integer userId,Integer friendId ){
		BigDecimal totalMoney = BigDecimal.valueOf(0);
		if(userId != null && friendId != null ){
			Criteria criteria = getSession().createCriteria(UserAwardDetail.class);	
			criteria.add(Restrictions.eq("relateTo", friendId));
			criteria.add(Restrictions.eq("type", "tui_detail_award"));
			criteria.createAlias("user", "user");
			criteria.add(Restrictions.eq("user.id", userId));
			criteria.setProjection( Projections.sum("money"));
			totalMoney= (BigDecimal) criteria.uniqueResult();
			if(totalMoney == null){
				totalMoney = BigDecimal.valueOf(0);
			}
		}
		return totalMoney;
	}
}
