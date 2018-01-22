package net.qmdboss.dao;

import net.qmdboss.entity.UserAward;

public interface BorrowAwardCodeDao extends BaseDao<UserAward, Integer> {
    public void addAwardCode(UserAward borrowAward);
}
