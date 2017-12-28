package net.qmdboss.action.admin;

import net.qmdboss.service.BorrowAccountDetailService;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@ParentPackage("admin")
public class BorrowAccountDetailAction  extends BaseAdminAction {


	private static final long serialVersionUID = -1113683842307627668L;

	private String keywords;
	private String searchBy;
	
	@Resource(name = "borrowAccountDetailServiceImpl")
	private BorrowAccountDetailService borrowAccountDetailService;
	
	public String list(){
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(keywords)){
			map.put(searchBy, keywords);
		}
		pager = borrowAccountDetailService.findPager(pager,map);
		return LIST;
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
	
	
}
