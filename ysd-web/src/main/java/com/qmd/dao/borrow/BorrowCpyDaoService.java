package com.qmd.dao.borrow;


import com.qmd.mode.borrow.BorCompany;

import java.util.Map;

public interface BorrowCpyDaoService {

public void add(BorCompany borCompany);
	
	
public  void updateBorCpy(Map<String,Object> map);
}
