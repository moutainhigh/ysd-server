package net.qmdboss.service.impl;

import net.qmdboss.dao.BorrowAwardCodeDao;
import net.qmdboss.dao.impl.BorrowAwardCodeDaoImpl;
import net.qmdboss.entity.UserAward;
import net.qmdboss.service.BorrowAwardCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("borrowAwardCodeServiceImpl")
public class BorrowAwardCodeServiceImpl extends BaseServiceImpl<UserAward,Integer> implements BorrowAwardCodeService {

    @Resource(name = "borrowAwardCodeDaoImpl")
    private BorrowAwardCodeDaoImpl borrowAwardCodeDao;

    @Resource(name = "borrowAwardCodeDaoImpl")
    public void setBaseDao(BorrowAwardCodeDao borrowAwardCodeDao) {
        super.setBaseDao(borrowAwardCodeDao);
    }

    public void addAwardCode(UserAward borrowAward){
        borrowAwardCodeDao.addAwardCode(borrowAward);
    }
}
