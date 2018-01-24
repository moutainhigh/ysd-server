package net.qmdboss.service;

import net.qmdboss.entity.UserAward;

public interface BorrowAwardCodeService extends BaseService<UserAward, Integer>{
    public void addAwardCode(UserAward borrowAward);
}
