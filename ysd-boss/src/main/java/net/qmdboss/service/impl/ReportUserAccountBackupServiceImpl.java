package net.qmdboss.service.impl;

import net.qmdboss.bean.CensusUserListBean;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.ReportUserAccountBackupDao;
import net.qmdboss.entity.ReportUserAccountBackup;
import net.qmdboss.service.ReportUserAccountBackupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 发表待审Service实现类
 * 
 * @author Administrator
 * 
 */
@Service("reportUserAccountBackupServiceImpl")
public class ReportUserAccountBackupServiceImpl extends
		BaseServiceImpl<ReportUserAccountBackup, Integer> implements
		ReportUserAccountBackupService {

	@Resource(name = "reportUserAccountBackupDaoImpl")
	private ReportUserAccountBackupDao reportUserAccountBackupDao;

	@Resource(name = "reportUserAccountBackupDaoImpl")
	public void setBaseDao(ReportUserAccountBackupDao reportUserAccountBackupDao) {
		super.setBaseDao(reportUserAccountBackupDao);
	}

	@Override
	public List queryReportStatisticsDailyList(int dateIntBegin, int dateIntEnd) {
		List list = reportUserAccountBackupDao.queryReportStatisticsDailyList(
				dateIntBegin, dateIntEnd);
		return list;
	}
	@Override
	@Transactional(readOnly = true)
	public Pager queryCensusUserPage(Integer dateIntBegin, Integer dateIntEnd, Pager pager) {
		return reportUserAccountBackupDao.queryCensusUserPage(dateIntBegin, dateIntEnd, pager);
	}
	@Override
	@Transactional(readOnly = true)
	public List<CensusUserListBean> queryCensusUserDetail(Integer dateIntBegin, Integer dateIntEnd,String username)  {
		return reportUserAccountBackupDao.queryCensusUserDetail(dateIntBegin, dateIntEnd, username);
	}
	

}
