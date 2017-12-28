package net.qmdboss.dao;

import net.qmdboss.entity.UserAwardDetail;

import java.math.BigDecimal;

/**
 *
 */
public interface UserAwardDetailDao extends BaseDao<UserAwardDetail, Integer> {
	
	/**
	 * 查询用户从好友那里得到的收益奖励总和
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public BigDecimal getTotalMoneyFriendAward(Integer userId,Integer friendId );
}
