package net.qmdboss.dao.impl;

import net.qmdboss.dao.BorrowAwardCodeDao;
import net.qmdboss.entity.UserAward;
import org.springframework.stereotype.Repository;

@Repository("borrowAwardCodeDaoImpl")
public class BorrowAwardCodeDaoImpl extends BaseDaoImpl<UserAward, Integer> implements BorrowAwardCodeDao{
    /**
     * 插入抽奖码
     *
     * sjc20180117
     */
    public void addAwardCode(UserAward borrowAward){
		save(borrowAward);
    }
}
