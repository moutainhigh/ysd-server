package net.qmdboss.dao;

import net.qmdboss.bean.CensusUserListBean;
import net.qmdboss.bean.Pager;
import net.qmdboss.entity.ReportUserAccountBackup;

import java.util.List;

/**
 * Dao接口 ReportUserAccountBackup
 * 
 */
public interface ReportUserAccountBackupDao extends
		BaseDao<ReportUserAccountBackup, Integer> {
	
//	public List queryReportStatisticsDailyList(int dateIntBegin, int dateIntEnd);
	
	public List<Object[]> queryReportStatisticsDailyList(int dateIntBegin, int dateIntEnd) ;
	
	public Pager queryCensusUserPage(Integer dateIntBegin, Integer dateIntEnd, Pager pager);
	
	public List<CensusUserListBean> queryCensusUserDetail(Integer dateIntBegin, Integer dateIntEnd,String username) ;

}
