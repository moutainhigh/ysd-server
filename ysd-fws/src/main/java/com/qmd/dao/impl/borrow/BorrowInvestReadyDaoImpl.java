package com.qmd.dao.impl.borrow;

import com.qmd.dao.borrow.BorrowInvestReadyDao;
import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.mode.borrow.BorrowInvestReady;
import org.springframework.stereotype.Repository;

@Repository("borrowInvestReadyDao")
public class BorrowInvestReadyDaoImpl extends
		BaseDaoImpl<BorrowInvestReady, Integer> implements BorrowInvestReadyDao {

}
