package net.qmdboss.service;

import net.qmdboss.bean.CensusUserListBean;
import net.qmdboss.bean.Pager;
import net.qmdboss.entity.ReportUserAccountBackup;

import java.util.List;

public interface ReportUserAccountBackupService extends
		BaseService<ReportUserAccountBackup, Integer> {

	public List queryReportStatisticsDailyList(int dateIntBegin, int dateIntEnd);
	
	public Pager queryCensusUserPage(Integer dateIntBegin, Integer dateIntEnd, Pager pager);
	
	public List<CensusUserListBean> queryCensusUserDetail(Integer dateIntBegin, Integer dateIntEnd,String username) ;

}
