package com.qmd.service.impl.borrow;

import com.qmd.dao.borrow.BorrowCpyDaoService;
import com.qmd.mode.borrow.BorCompany;
import com.qmd.service.borrow.BorrowCpyService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("borCpyService")
public class BorrowCpyServiceImpl implements BorrowCpyService {

	Logger log = Logger.getLogger(BorrowCpyServiceImpl.class);
	@Resource
	BorrowCpyDaoService borCpyDaoService;
	public BorrowCpyDaoService getBorCpyDaoService() {
		return borCpyDaoService;
	}
	public void setBorCpyDaoService(BorrowCpyDaoService borCpyDaoService) {
		this.borCpyDaoService = borCpyDaoService;
	}
	@Override
	public void add(BorCompany borCompany) {
		// TODO Auto-generated method stub
		this.borCpyDaoService.add(borCompany);
	}

}
