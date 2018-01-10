package com.qmd.action.borrow;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowRecharge;
import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.mode.user.User;
import com.qmd.service.borrow.BorrowRechargeService;
import com.qmd.service.borrow.BorrowRepaymentDetailService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.util.CommonUtil;
import com.qmd.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InterceptorRefs({ @InterceptorRef(value = "userVerifyInterceptor"),
		@InterceptorRef(value = "qmdDefaultStack") })
@Service("borrowRechargeAction")
public class BorrowRechargeAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(BorrowRechargeAction.class);
	
	private String searchBy;
	private String keywords;
	private String safepwd;
	
	private Borrow borrow;
	
	private String minDate;
	private String maxDate;
	
	private List<BorrowRepaymentDetail> borrowRepaymentDetailList;
	
	private BorrowRecharge borrowRecharge;
	
	@Resource
	BorrowService borrowService;
	@Resource
	BorrowRepaymentDetailService borrowRepaymentDetailService;
	@Resource
	BorrowRechargeService borrowRechargeService;
	
	/**
	 * 进入项目发布页面
	 */
	@Action(value="/borrowRecharge/borrowList",
			results={@Result(name="success", location="/content/borrowRecharge/borrow_list.ftl", type="freemarker")})
	public String borrowList(){
//		if (getLoginUser().getTypeId().intValue() != 3) {
//			addActionError("只有服务商才能访问！");
//			return "error_ftl";
//		}
		
		if(pager == null){
			pager = new Pager();
		}
		User user = this.getLoginUser();
		Map<String,Object> qMap = new HashMap<String,Object>();
		
		if(StringUtils.isNotEmpty(minDate)){
			qMap.put("minDate", CommonUtil.date2begin(CommonUtil.getString2Date(minDate, "yyyy-MM-dd")));
		}
		if(StringUtils.isNotEmpty(maxDate)){
			qMap.put("maxDate", CommonUtil.date2end(CommonUtil.getString2Date(maxDate, "yyyy-MM-dd")));
		}
		
		qMap.put("agencyId", user.getAgencyid());
//		qMap.put("keywordsCode", keywords);
		if(StringUtils.isNotEmpty(keywords)){
			qMap.put("borrowName", keywords);
		}
		qMap.put("fangkuanStatus", 1);
		qMap.put("orderBy", " b.verify_time desc");
		
		pager = borrowService.queryBorrowList2(pager,qMap);
		return SUCCESS;
		
	}
	
	/**
	 * 进入项目发布页面
	 */
	@Action(value="/borrowRecharge/borrowInfo",
			results={@Result(name="success", location="/content/borrowRecharge/borrow_info.ftl", type="freemarker")})
	public String borrowInfo(){
//		if (getLoginUser().getTypeId().intValue() != 3) {
//			addActionError("只有服务商才能访问！");
//			return "error_ftl";
//		}
		
		
		borrow = borrowService.getBorrowById(Integer.parseInt(id));
		
//		if(pager == null){
//			pager = new Pager();
//		}
//		User user = this.getLoginUser();
//		Map<String,Object> qMap = new HashMap<String,Object>();
//		
////		if(StringUtils.isNotEmpty(minDate)){
////			qMap.put("minDate", CommonUtil.date2begin(CommonUtil.getString2Date(minDate, "yyyy-MM-dd HH:mm:ss")));
////		}
////		if(StringUtils.isNotEmpty(maxDate)){
////			qMap.put("maxDate", CommonUtil.date2end(CommonUtil.getString2Date(maxDate, "yyyy-MM-dd HH:mm:ss")));
////		}
//		
//		qMap.put("agencyId", user.getAgencyid());
////		qMap.put("keywordsCode", keywords);
//		if(StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(keywords)){
//			qMap.put(searchBy, keywords);
//		}
//		qMap.put("orderBy", " b.verify_time desc");
		
		borrowRepaymentDetailList = borrowRepaymentDetailService.queryUserBorrowList(Integer.parseInt(id));
		
		for(BorrowRepaymentDetail item:borrowRepaymentDetailList) {
			if(item.getStatus()==0) {
				if(item.getRechargeStatus()==null||item.getRechargeStatus()==0) {
					borrowRecharge = new BorrowRecharge();
					borrowRecharge.setBorrowRepaymentId(item.getId());
					borrowRecharge.setMoney(item.getRechargeMoney());
					break;
				} else if(item.getRechargeStatus()>0) {
					
					Map<String,Object> mm = new HashMap<String,Object>();
					mm.put("borrowRepaymentId", item.getId());
					int[] arrayStatus={1,2,3};
					mm.put("arrayStatus", arrayStatus);
					mm.put("orderBy", "t.id desc");
					List<BorrowRecharge> brlist = borrowRechargeService.queryListByMap(mm);
					if(brlist!=null && brlist.size()>0) {
						borrowRecharge = brlist.get(0);
					}
					break;
				}
				
			}
		}
			
		
		
		//pager = borrowService.queryBorrowList2(pager,qMap);
		return SUCCESS;
		
	}
	
	/**
	 * 进入项目发布页面
	 */
//	@Action(value="/borrowRecharge/rechargeSave",
//			results={@Result(name="success", location="/content/borrowRecharge/borrow_info.ftl", type="freemarker")})
	@Action(value="/borrowRecharge/rechargeSave",results={@Result(type="json")})
	public String rechargeSave(){
//		if (getLoginUser().getTypeId().intValue() != 3) {
//			addActionError("只有服务商才能访问！");
//			return "error_ftl";
//		}
		
		User user = getLoginUser();
		if (StringUtils.isEmpty(safepwd)||!userService.isPassword(user.getUsername(), safepwd, "1")) {
//			return ajax(Status.warn,"安全密码输入错误!");
			//addActionError("安全密码输入错误!");
			//return "error_ftl";
			return ajax(Status.error, "安全密码输入错误!");
		}
		
		
		borrowRecharge.setAgencyId(getLoginUser().getAgencyid());
		int ret = borrowRechargeService.saveBorrowRecharge(borrowRecharge);
		if(ret!=0) {
			if(ret ==1) {
				return ajax(Status.error, "状态不正确");
			}
		}
		
//		borrow = borrowService.getBorrowById(Integer.parseInt(id));
//		
//		
//		borrowRepaymentDetailList = borrowRepaymentDetailService.queryUserBorrowList(Integer.parseInt(id));
//		
//		for(BorrowRepaymentDetail item:borrowRepaymentDetailList) {
//			if(item.getStatus()==0) {
//				if(item.getRechargeStatus()==null||item.getRechargeStatus()==0) {
//					borrowRecharge = new BorrowRecharge();
//					borrowRecharge.setMoney(item.getRechargeMoney());
//					break;
//				} else if(item.getRechargeStatus()>0) {
//					
//					Map<String,Object> mm = new HashMap<String,Object>();
//					mm.put("borrowRepaymentId", item.getId());
//					int[] arrayStatus={1,2,3};
//					mm.put("arrayStatus", arrayStatus);
//					mm.put("orderBy", "t.id desc");
//					List<BorrowRecharge> brlist = borrowRechargeService.queryListByMap(mm);
//					if(brlist!=null && brlist.size()>0) {
//						borrowRecharge = brlist.get(0);
//					}
//					
//				}
//			}
//		}
//			
		
		
		//pager = borrowService.queryBorrowList2(pager,qMap);
//		return SUCCESS;
		return ajax(Status.success, "充值申请成功!");
		
	}
	
	
	
	/**
	 * 进入项目发布页面
	 */
	@Action(value="/borrowRecharge/rechargeList",
			results={@Result(name="success", location="/content/borrowRecharge/recharge_list.ftl", type="freemarker")})
	public String rechargeList(){
//		if (getLoginUser().getTypeId().intValue() != 3) {
//			addActionError("只有服务商才能访问！");
//			return "error_ftl";
//		}
		
		if(pager == null){
			pager = new Pager();
		}
		User user = this.getLoginUser();
		Map<String,Object> qMap = new HashMap<String,Object>();
		
		if(StringUtils.isNotEmpty(minDate)){
			qMap.put("minDate", CommonUtil.date2begin(CommonUtil.getString2Date(minDate, "yyyy-MM-dd")));
		}
		if(StringUtils.isNotEmpty(maxDate)){
			qMap.put("maxDate", CommonUtil.date2end(CommonUtil.getString2Date(maxDate, "yyyy-MM-dd")));
		}
		
		qMap.put("agencyId", user.getAgencyid());
		if(StringUtils.isNotEmpty(keywords)){
			qMap.put("borrowName", keywords);
		}
		
		pager = borrowRechargeService.queryPagerBorrowRecharge(pager,qMap);
		return SUCCESS;
		
	}
	
	

	Map<String, Object> root = new HashMap<String, Object>();
	
	@Action(value="/borrowRecharge/ajaxRecall", results = {@Result(name = "success", params = {"root", "root" }, type = "json")})
	public String ajaxRecallCashInfo(){
		if(StringUtils.isEmpty(id)){
			return ajax(Status.error,"参数错误");
		}
		borrowRecharge = borrowRechargeService.getById(Integer.parseInt(id));
		if(borrowRecharge == null ){
			return ajax(Status.error,"参数错误");
		}
		
		User userLogin = getLoginUser();
		if(userLogin.getAgencyid().compareTo(borrowRecharge.getAgencyId()) !=0){
			return ajax(Status.error,"参数错误");
		}
		
		int ret = borrowRechargeService.updateByAgency(borrowRecharge.getId(),getRequestRemoteAddr());
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
	@Action(value = "/borrowRecharge/poputBorrowRechargeView",results={@Result(name="success", location="/content/agency/poput_view.ftl", type="freemarker")})
	@InputConfig(resultName = "ajaxError")
	public String poputFangkuanView(){
		if(StringUtils.isEmpty(id)){
			return ajax(Status.error,"参数错误");
		}
		borrowRecharge = borrowRechargeService.getById(Integer.parseInt(id));
		return SUCCESS;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	public List<BorrowRepaymentDetail> getBorrowRepaymentDetailList() {
		return borrowRepaymentDetailList;
	}

	public void setBorrowRepaymentDetailList(
			List<BorrowRepaymentDetail> borrowRepaymentDetailList) {
		this.borrowRepaymentDetailList = borrowRepaymentDetailList;
	}

	public BorrowRecharge getBorrowRecharge() {
		return borrowRecharge;
	}

	public void setBorrowRecharge(BorrowRecharge borrowRecharge) {
		this.borrowRecharge = borrowRecharge;
	}

	public String getSafepwd() {
		return safepwd;
	}

	public void setSafepwd(String safepwd) {
		this.safepwd = safepwd;
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

	
	
}
