package net.qmdboss.service.impl;

import net.qmdboss.bean.BorrowTenderItem;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.ReportFirstTenderDao;
import net.qmdboss.entity.ReportFirstTender;
import net.qmdboss.service.ReportFirstTenderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("reportFirstTenderServiceImpl")
public class ReportFirstTenderServiceImpl extends BaseServiceImpl<ReportFirstTender, Integer> implements ReportFirstTenderService {

	@Resource(name = "reportFirstTenderDaoImpl")
	private ReportFirstTenderDao reportFirstTenderDao;

	@Resource(name = "reportFirstTenderDaoImpl")
	public void setBaseDao(ReportFirstTenderDao reportFirstTenderDao) {
		super.setBaseDao(reportFirstTenderDao);
	}
	

	public Pager findPagerByHsql(Pager pager,Map<String, Object> map){
		return reportFirstTenderDao.findPagerByHsql(pager, map);
	}
	
	public List<ReportFirstTender> getListByHsql(Map<String, Object> map){
		return reportFirstTenderDao.getListByHsql(map);
	}
	

	public Pager queryBorrowTenderByHsql(Pager pager,Map<String, Object> map){
		return reportFirstTenderDao.queryBorrowTenderByHsql(pager, map);
	}
	
	public List<BorrowTenderItem> getBorrowTenderByHsql(Map<String, Object> map){
		return reportFirstTenderDao.getBorrowTenderByHsql(map);
	}
	
	public void callProcedure(String procString,List<Object> params) throws Exception {
		 reportFirstTenderDao.callProcedure(procString, params);
	}
	
	
	
	
	
	
	
	
}
