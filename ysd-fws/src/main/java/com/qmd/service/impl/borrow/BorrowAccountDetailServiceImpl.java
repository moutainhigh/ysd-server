package com.qmd.service.impl.borrow;

import com.qmd.dao.borrow.BorrowAccountDetailDao;
import com.qmd.mode.borrow.BorrowAccountDetail;
import com.qmd.service.borrow.BorrowAccountDetailService;
import com.qmd.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository("borrowAccountDetailService")
public class BorrowAccountDetailServiceImpl extends BaseServiceImpl<BorrowAccountDetail,Integer> implements BorrowAccountDetailService  {


	@Resource
	BorrowAccountDetailDao borrowAccountDetailDao;

	@Resource
	public void setBaseDao(BorrowAccountDetailDao borrowAccountDetailDao) {
		super.setBaseDao(borrowAccountDetailDao);
	}
	
	
	
}
