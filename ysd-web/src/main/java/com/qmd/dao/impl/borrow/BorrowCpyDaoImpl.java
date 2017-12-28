package com.qmd.dao.impl.borrow;

import com.qmd.dao.borrow.BorrowCpyDaoService;
import com.qmd.mode.borrow.BorCompany;
import org.apache.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("borrowCpyDaoService")
public class BorrowCpyDaoImpl extends SqlSessionDaoSupport implements
		BorrowCpyDaoService {
	Logger log = Logger.getLogger(BorrowCpyDaoImpl.class);
	@Override
	public void add(BorCompany borCompany) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert("BorrowCompany.addMes", borCompany);
	}
	
	/**
	 * updateBorCpy 修改borrowCompany表中的borrowId值
	 * return 
	 */
	@Override
	public void updateBorCpy(Map<String,Object> map) {
		// TODO Auto-generated method stub

		 this.getSqlSession().update("BorrowCompany.updateId", map);
		
	}

	

}
