package com.qmd.dao.impl.borrow;

import com.qmd.dao.borrow.BorrowAccountDetailDao;
import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.mode.borrow.BorrowAccountDetail;
import org.springframework.stereotype.Repository;

@Repository("borrowAccountDetailDao")
public class BorrowAccountDetailDaoImpl  extends BaseDaoImpl<BorrowAccountDetail, Integer> implements BorrowAccountDetailDao{

}
