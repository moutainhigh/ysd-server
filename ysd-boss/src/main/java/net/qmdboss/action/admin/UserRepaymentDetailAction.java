package net.qmdboss.action.admin;

import net.qmdboss.entity.User;
import net.qmdboss.service.UserRepaymentDetailService;
import net.qmdboss.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户待收记录
 *
 */
@ParentPackage("admin")
public class UserRepaymentDetailAction extends BaseAdminAction{

	private static final long serialVersionUID = -4672586460290856202L;
	private User user;
	private String status;
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private String statusCode;//提交时间【0】还是完成时间【1】
	
	@Resource(name = "userRepaymentDetailServiceImpl")
	private UserRepaymentDetailService userRepaymentDetailService;
	
	public String list(){
		Map<String,Object> map = new HashMap<String, Object>();
		if(startDate != null) {
			map.put("startDate",  CommonUtil.date2begin(startDate));
		}
		
		if(endDate != null) {
			map.put("endDate",  CommonUtil.date2end(endDate));
		}
		
		if(pager != null&&"user.username".equals( pager.getSearchBy()) ){
			if(StringUtils.isNotEmpty(pager.getKeyword())){
				map.put("username", pager.getKeyword());
			}
		}
		if(pager != null&&"borrow.name".equals(pager.getSearchBy()) ){
			if(StringUtils.isNotEmpty(pager.getKeyword())){
				map.put("borrowName", pager.getKeyword());
			}
		}
		
		//pager = userRepaymentDetailService.findUserRepaymentDetailPager(pager, map);
		//优化
		pager = userRepaymentDetailService.findUserRepaymentDetailPagerByY(pager, map);
		return LIST;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
