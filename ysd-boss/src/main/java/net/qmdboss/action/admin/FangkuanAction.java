package net.qmdboss.action.admin;

import net.qmdboss.entity.Fangkuan;
import net.qmdboss.service.FangkuanService;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@ParentPackage("admin")
public class FangkuanAction  extends BaseAdminAction {
	
	private static final long serialVersionUID = 1L;
	
	private Fangkuan fangkuan;
	private Integer status;
	private String keywords;
	private String searchBy;
	private String method;
	
	@Resource(name = "fangkuanServiceImpl")
	private FangkuanService fangkuanService;
	/**
	 * 借款人放款申请列表
	 * @return
	 */
	public String borrowerListIng() {
		method = "borrowerListIng";
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(keywords)){
			map.put(searchBy, keywords);
		}
		map.put("type", 1);
		map.put("status", 2);
		
		pager = fangkuanService.findPager(pager,map);
		return LIST;
	}
	
	/**
	 * 保证金放款申请列表
	 * @return
	 */
	public String depositListIng() {
		method = "depositListIng";
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(keywords)){
			map.put(searchBy, keywords);
		}
		map.put("type", 2);
		map.put("status", 2);
		
		pager = fangkuanService.findPager(pager,map);
		return LIST;
	}
	
	/**
	 * 服务费放款申请列表
	 * @return
	 */
	public String feeListIng() {
		method = "feeListIng";
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(keywords)){
			map.put(searchBy, keywords);
		}
		map.put("type", 3);
		map.put("status", 2);
		
		pager = fangkuanService.findPager(pager,map);
		return LIST;
	}
	
	
	/**
	 * 借款人放款成功列表
	 * @return
	 */
	public String borrowerListSuccess() {
		method = "borrowerListSuccess";
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(keywords)){
			map.put(searchBy, keywords);
		}
		map.put("type", 1);
		map.put("status", status);
		
		pager = fangkuanService.findPager(pager,map);
		return "finish_list";
	}
	
	/**
	 * 保证金放款成功列表
	 * @return
	 */
	public String depositListSuccess() {
		method = "depositListSuccess";
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(keywords)){
			map.put(searchBy, keywords);
		}
		map.put("type", 2);
		map.put("status", status);
		
		pager = fangkuanService.findPager(pager,map);
		return "finish_list";
	}
	
	/**
	 * 服务费放款成功列表
	 * @return
	 */
	public String feeListSuccess() {
		method = "feeListSuccess";
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(keywords)){
			map.put(searchBy, keywords);
		}
		map.put("type", 3);
		map.put("status", status);
		
		pager = fangkuanService.findPager(pager,map);
		return "finish_list";
	}
	
	public String input(){
		fangkuan = fangkuanService.get(id);
		return INPUT;
	}
	
	
	/**
	 * 审核
	 * @return
	 */
	public String verify(){
		method="borrowerListIng";
		
		Integer ret = fangkuanService.verify(fangkuan,getRequest().getRemoteAddr());
		/**
		if(fangkuan != null && fangkuan.getStatus() != null){
			if(fangkuan.getStatus().compareTo(0)==0){
				if(fangkuan.getType().compareTo(1) ==0){
					method="borrowerListIng";
				}else if(fangkuan.getType().compareTo(2) ==0){
					method = "depositListIng";
				}else if(fangkuan.getType().compareTo(3) ==0){
					method = "feeListIng";
				}
			}else if(fangkuan.getStatus().compareTo(1)==0){
				if(fangkuan.getType().compareTo(1) ==0){
					method="borrowerListSuccess";
				}else if(fangkuan.getType().compareTo(2) ==0){
					method = "depositListSuccess";
				}else if(fangkuan.getType().compareTo(3) ==0){
					method = "feeListSuccess";
				}
			}
		}
		**/
		if(ret == 0){
			addErrorMessages("审核失败");
			return ERROR;
		}else if(ret ==2){
			addErrorMessages("请先审核借款人放款申请");
			return ERROR;
		}
		if(fangkuan.getType().compareTo(1) ==0){
			method="borrowerListIng";
		}else if(fangkuan.getType().compareTo(2) ==0){
			method = "depositListIng";
		}else if(fangkuan.getType().compareTo(3) ==0){
			method = "feeListIng";
		}
		redirectUrl="fangkuan!"+method+".action";
		return SUCCESS;
	}

	public Fangkuan getFangkuan() {
		return fangkuan;
	}

	public void setFangkuan(Fangkuan fangkuan) {
		this.fangkuan = fangkuan;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	
	
	
	
}
