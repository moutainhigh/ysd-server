package net.qmdboss.service;

import net.qmdboss.bean.BorrowTenderItem;
import net.qmdboss.bean.Pager;
import net.qmdboss.entity.ReportFirstTender;

import java.util.List;
import java.util.Map;

public interface ReportFirstTenderService extends BaseService<ReportFirstTender, Integer> {

	public Pager findPagerByHsql(Pager pager,Map<String, Object> map);
	
	public List<ReportFirstTender> getListByHsql(Map<String, Object> map);
	
	
	

	public Pager queryBorrowTenderByHsql(Pager pager,Map<String, Object> map);
	
	public List<BorrowTenderItem> getBorrowTenderByHsql(Map<String, Object> map);
	
	
	public void callProcedure(String procString,List<Object> params) throws Exception;
		
	
}
