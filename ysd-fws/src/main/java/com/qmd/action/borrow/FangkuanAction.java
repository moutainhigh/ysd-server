package com.qmd.action.borrow;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.bean.BorrowFangkuanBean;
import com.qmd.common.BasePager;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.Fangkuan;
import com.qmd.mode.user.User;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.borrow.FangkuanService;
import com.qmd.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@InterceptorRefs({
	@InterceptorRef(value = "userVerifyInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
@Service("fangkuanAction")
public class FangkuanAction extends BaseAction {
	
	private static final long serialVersionUID = -6349863138233613126L;
	Logger log = Logger.getLogger(FangkuanAction.class);
	
	private Fangkuan fangkuan;
	private BorrowFangkuanBean borrowFangkuanBean;
	@Resource
	FangkuanService fangkuanService;
	@Resource
	BorrowService borrowService;

	Map<String, Object> root = new HashMap<String, Object>();
	private String keywords;
	private String searchBy;
	
	private String timeBy;
	private String minDate;//起始时间
	private String maxDate;//截止时间
	private Integer status;//状态
	
	private String paypwd;//安全密码
	/**
	 * 查询可放款项目列表
	 * @return
	 */
	@Action(value="/fangkuan/borrowList",results={@Result(name="success", location="/content/borrow/fangkuan/borrow_list.ftl", type="freemarker")})
	public String borrowList(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("borrowStatus", 3);
		map.put("borrowFangkuanStatus", 0);
		map.put("agencyid", getLoginUser().getAgencyid());
		if(StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(keywords)){
			map.put(searchBy, keywords);
		}
		pager = fangkuanService.findBorrowFangkuanPager(map, pager);
		return SUCCESS;
	}

	
	/**
	 * 查询放款记录列表
	 * @return
	 */
	@Action(value="/fangkuan/fangkuanList",results={@Result(name="success", location="/content/borrow/fangkuan/list.ftl", type="freemarker")})
	public String fangkuanList(){
		Fangkuan fk = new Fangkuan();
		fk.setAgencyId(getLoginUser().getAgencyid());
		fk.setType(1);
		if(StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(keywords)){
			if("name".equals(searchBy)){
				fk.setName(keywords);
			}else if("borrowerRealName".equals(searchBy)){
				fk.setBorrowerRealName(keywords);
			}
		}
		
		if(StringUtils.isNotEmpty(timeBy) && StringUtils.isNotEmpty(minDate)){
			if("sq".equals(timeBy)){
				fk.setSqMinDate(CommonUtil.date2begin(CommonUtil.getString2Date(minDate, "yyyy-MM-dd HH:mm:ss")));
			}else if("sh".equals(timeBy)){
				fk.setShMinDate(CommonUtil.date2begin(CommonUtil.getString2Date(minDate, "yyyy-MM-dd HH:mm:ss")));
			}
		}

		if(StringUtils.isNotEmpty(timeBy) && StringUtils.isNotEmpty(maxDate)){
			if("sq".equals(timeBy)){
				fk.setSqMaxDate(CommonUtil.date2end(CommonUtil.getString2Date(maxDate, "yyyy-MM-dd HH:mm:ss")));
			}else if("sh".equals(timeBy)){
				fk.setShMaxDate(CommonUtil.date2end(CommonUtil.getString2Date(maxDate, "yyyy-MM-dd HH:mm:ss")));
			}
		}
		if(status != null){
			fk.setStatus(status);
		}
		
		String orderBy = " t.create_date desc";
		BasePager<Fangkuan> basePager =  fangkuanService.queryPageByOjbect(fk, pager.getPageSize(), pager.getPageNumber(), orderBy);
		pager = CommonUtil.basePager2Pager(pager, basePager);
		return SUCCESS;
	}
	

	/**
	 * 查看详情
	 * @return
	 */
	@Action(value = "/fangkuan/poputAddFangkuanView",results={@Result(name="success", location="/content/borrow/fangkuan/poput_add_fangkuan.ftl", type="freemarker")})
	@InputConfig(resultName = "ajaxError")
	public String poputAddFangkuanView(){
		if(StringUtils.isEmpty(id)){
			return ajax(Status.error,"参数错误");
		}
		borrowFangkuanBean = fangkuanService.getBorrowFangkuan(Integer.parseInt(id));
		return SUCCESS;
	}


	/**
	 * 添加放款申请
	 * @return
	 */
	@Action(value="/fangkuan/ajaxAddFangkuan", results = {@Result(name = "success", params = {"root", "root" }, type = "json")})
	public String ajaxAddFangkuan(){
		if(StringUtils.isEmpty(id)){
			return ajax(Status.error,"参数错误");
		}
		Borrow b = borrowService.getBorrowById(Integer.parseInt(id));
		if(b == null ){
			return ajax(Status.error,"参数错误");
		}
		
		
		User userLogin = getLoginUser();
		if(userLogin.getAgencyid().compareTo(b.getAgencyId()) !=0){
			return ajax(Status.error,"参数错误");
		}

		if(!userService.isPassword(userLogin.getUsername(), paypwd, "1")){
			return ajax(Status.error,"安全密码输入错误");
		}
		
		
		int ret = fangkuanService.saveFangkuan(b.getId(),getRequestRemoteAddr());
		if(ret ==1){
			return ajax(Status.success,"放款成功!");
		}else{
			return ajax(Status.error,"放款失败!");
		}
	}
	
	
	@Action(value="/fangkuan/ajaxRecall", results = {@Result(name = "success", params = {"root", "root" }, type = "json")})
	public String ajaxRecallCashInfo(){
		if(StringUtils.isEmpty(id)){
			return ajax(Status.error,"参数错误");
		}
		fangkuan = fangkuanService.getById(Integer.parseInt(id));
		if(fangkuan == null ){
			return ajax(Status.error,"参数错误");
		}
		
		User userLogin = getLoginUser();
		if(userLogin.getAgencyid().compareTo(fangkuan.getAgencyId()) !=0){
			return ajax(Status.error,"参数错误");
		}
		
		int ret = fangkuanService.updateByAgency(fangkuan.getId(),getRequestRemoteAddr());
		if(ret ==1){
			root.put("errorMsg", "OK");
		}else{
			root.put("errorMsg", "操作失败");
		}
		ajax(root);
		return SUCCESS;
	}
	
	/**
	 * 查看详情
	 * @return
	 */
	@Action(value = "/fangkuan/poputFangkuanView",results={@Result(name="success", location="/content/borrow/fangkuan/poput_view.ftl", type="freemarker")})
	@InputConfig(resultName = "ajaxError")
	public String poputFangkuanView(){
		if(StringUtils.isEmpty(id)){
			return ajax(Status.error,"参数错误");
		}
		fangkuan = fangkuanService.getById(Integer.parseInt(id));
		return SUCCESS;
	}
	
	public Fangkuan getFangkuan() {
		return fangkuan;
	}


	public void setFangkuan(Fangkuan fangkuan) {
		this.fangkuan = fangkuan;
	}


	public String getKeywords() {
		return keywords;
	}


	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}


	public String getSearchBy() {
		return searchBy;
	}


	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}


	public Map<String, Object> getRoot() {
		return root;
	}


	public void setRoot(Map<String, Object> root) {
		this.root = root;
	}


	public BorrowFangkuanBean getBorrowFangkuanBean() {
		return borrowFangkuanBean;
	}


	public void setBorrowFangkuanBean(BorrowFangkuanBean borrowFangkuanBean) {
		this.borrowFangkuanBean = borrowFangkuanBean;
	}


	public String getTimeBy() {
		return timeBy;
	}


	public void setTimeBy(String timeBy) {
		this.timeBy = timeBy;
	}


	public String getMinDate() {
		return minDate;
	}


	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}


	public String getMaxDate() {
		return maxDate;
	}


	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getPaypwd() {
		return paypwd;
	}


	public void setPaypwd(String paypwd) {
		this.paypwd = paypwd;
	}
	
	
	
	
	
	
	
	
}
