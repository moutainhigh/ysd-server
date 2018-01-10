package net.qmdboss.action.admin;

import net.qmdboss.service.ReportUserAccountBackupService;
import net.qmdboss.service.UserService;
import net.qmdboss.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.Date;

@ParentPackage("admin")
public class CensusAction extends BaseAdminAction {

	private static final long serialVersionUID = 5287915291458478171L;

	@Resource(name = "userServiceImpl")
	private UserService userService;

	@Resource(name = "reportUserAccountBackupServiceImpl")
	private ReportUserAccountBackupService reportUserAccountBackupService;
	
	private Date dateBegin;
	private Date dateEnd;
	

	// private List<CensusUserListBean> dataList;

	public String userList() {

		if (dateEnd == null) {
			dateEnd = new Date();
		}

		pager = userService.queryCensusUserPage(dateEnd, pager);

		return "user_list";
	}
	public String userDetail() {
		
		if (dateBegin == null) {
			dateBegin = CommonUtil.getFirstDayOfMonth(new Date());
		}
		Integer dateIntBegin = CommonUtil.getIntDate(dateBegin);

		if (dateEnd == null) {
			dateEnd = new Date();
		}
		Integer dateIntEnd = CommonUtil.getIntDate(dateEnd);
		
		if (StringUtils.isEmpty(pager.getKeyword())) {
			return "user_detail";
		}
		pager.setKeyword(pager.getKeyword().trim());

		pager = reportUserAccountBackupService.queryCensusUserPage(dateIntBegin,dateIntEnd, pager);

		return "user_detail";
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	public Date getDateBegin() {
		return dateBegin;
	}
	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

}