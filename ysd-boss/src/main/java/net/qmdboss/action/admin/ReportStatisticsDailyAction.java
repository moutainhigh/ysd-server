package net.qmdboss.action.admin;

import net.qmdboss.bean.ReportStatisticsDailyBean;
import net.qmdboss.service.ReportUserAccountBackupService;
import net.qmdboss.util.CommonUtil;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@ParentPackage("admin")
public class ReportStatisticsDailyAction extends BaseAdminAction {

	private static final long serialVersionUID = 5287915291458478171L;

	@Resource(name = "reportUserAccountBackupServiceImpl")
	private ReportUserAccountBackupService ReportUserAccountBackupService;

	private Date dateBegin;
	private Date dateEnd;

	private List<ReportStatisticsDailyBean> dataList;

	// 后台主页面
	public String index() {

		if (dateBegin == null) {
			dateBegin = new Date();
		}
		if (dateEnd == null) {
			dateEnd = new Date();
		}
		int dateIntBegin = CommonUtil.getIntDate(dateBegin);
		int dateIntEnd = CommonUtil.getIntDate(dateEnd);

		dataList = ReportUserAccountBackupService
				.queryReportStatisticsDailyList(dateIntBegin, dateIntEnd);

		return "index";
	}

	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public List<ReportStatisticsDailyBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<ReportStatisticsDailyBean> dataList) {
		this.dataList = dataList;
	}

}