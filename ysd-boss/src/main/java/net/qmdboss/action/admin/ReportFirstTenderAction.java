package net.qmdboss.action.admin;

import net.qmdboss.service.AdminService;
import net.qmdboss.service.ListingService;
import net.qmdboss.service.ReportFirstTenderService;
import net.qmdboss.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 因先做首次记录统计，所以所有投资记录也统一放在这里
 * @author Administrator
 *
 */
@ParentPackage("admin")
public class ReportFirstTenderAction extends BaseAdminAction {

	private static final long serialVersionUID = 4798756470725305063L;
	
	@Resource(name = "reportFirstTenderServiceImpl")
	private ReportFirstTenderService reportFirstTenderService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "listingServiceImpl")
	private ListingService listingService;

	private Date beginDate;
	private Date endDate;
	private String placeChilderName;//渠道来源
	private String username;//用户名
	
	/**
	 * 首次投资统计
	 * @return
	 */
	public String list(){
		
		String adminPlaceName=adminService.getLoginAdmin().getUsername();
		System.out.println(adminPlaceName);
		String placeNameOn=listingService.findChildListingByKeyValue("place_admin", adminPlaceName);
		if(StringUtils.isNotBlank(placeNameOn)){
			placeChilderName=placeNameOn;
		}
		
		/**
		if("yufeng".equals(adminPlaceName)){
			placeChilderName="浴风";
		}
		if("miaodian".equals(adminPlaceName)){
			placeChilderName="秒点";
		}
		if("qutou".equals(adminPlaceName)){
			placeChilderName="趣投";
		}
		if("guanghui".equals(adminPlaceName)){
			placeChilderName="光辉";
		}
		if("fuqun".equals(adminPlaceName)){
			placeChilderName="富群";
		}
		**/
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(beginDate != null && endDate != null){
			map.put("beginDate", CommonUtil.getDate2String(CommonUtil.date2begin(beginDate), "yyyy-MM-dd HH:mm:ss"));
			
			map.put("endDate", CommonUtil.getDate2String(CommonUtil.date2end(endDate), "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotEmpty(placeChilderName)){
			map.put("placeChilderName", placeChilderName);
		}
		pager = reportFirstTenderService.findPagerByHsql(pager, map);
		
		return LIST;
	}
	

	public String refresh(){
		
		System.out.println("====刷新首投统计数据===");
		
		//无参数调用存储过程
			
		try {
			reportFirstTenderService.callProcedure("{call procs_report_first_tender}", null);
			 } catch (Exception e) {
				 System.out.println("===调用失败===");
			   return null;
		}
		
		
		
		
		
		return list();
	}
	/**
	 * 投资记录统计
	 * @return
	 */
	public String allList(){
		
		String adminPlaceName=adminService.getLoginAdmin().getUsername();
		System.out.println(adminPlaceName);
		
		String placeNameOn=listingService.findChildListingByKeyValue("place_admin", adminPlaceName);
		if(StringUtils.isNotBlank(placeNameOn)){
			placeChilderName=placeNameOn;
		}
		/**
		if("yufeng".equals(adminPlaceName)){
			placeChilderName="浴风";
		}
		if("miaodian".equals(adminPlaceName)){
			placeChilderName="秒点";
		}
		if("qutou".equals(adminPlaceName)){
			placeChilderName="趣投";
		}
		if("guanghui".equals(adminPlaceName)){
			placeChilderName="光辉";
		}
		if("fuqun".equals(adminPlaceName)){
			placeChilderName="富群";
		}
		**/
		
	

		Map<String, Object> map = new HashMap<String, Object>();
		if(beginDate != null && endDate != null){
			map.put("beginDate", CommonUtil.getDate2String(CommonUtil.date2begin(beginDate), "yyyy-MM-dd HH:mm:ss"));
			
			map.put("endDate", CommonUtil.getDate2String(CommonUtil.date2end(endDate), "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotEmpty(placeChilderName)){
			map.put("placeChilderName", placeChilderName);
		}

		if(StringUtils.isNotEmpty(username)){
			map.put("username", username);
		}
		
		pager = reportFirstTenderService.queryBorrowTenderByHsql(pager, map);
		
		return "all_list";
	}
	


	public Date getBeginDate() {
		return beginDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public String getPlaceChilderName() {
		return placeChilderName;
	}


	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public void setPlaceChilderName(String placeChilderName) {
		this.placeChilderName = placeChilderName;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}

}
