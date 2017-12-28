package com.qmd.service.borrow;

import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.User;
import com.qmd.service.BaseService;

public interface BorrowSecondService extends BaseService<BorrowTender,Integer> {

	public int borrowInvestDo(User user,BorrowTender borrowTender,String ip);//用户投标
	
}
