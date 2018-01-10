package com.qmd.action.index;

import com.qmd.action.base.BaseAction;
import com.qmd.bean.BorrowBean;
import com.qmd.mode.borrow.Borrow;
import com.qmd.service.borrow.BorrowService;
import com.qmd.util.JsonUtil;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("indexAction")
@InterceptorRefs({
	@InterceptorRef(value = "qmdDefaultStack")
})
public class IndexAction extends BaseAction {
	/**
	 * 	
	 */
	private static final long serialVersionUID = -8977624206621602690L;
	
	
	@Resource
	BorrowService borrowService;
	
	private Integer bid;
	private Date begin;
	private Date end;
	
	@Action(value="/wsi/borrow")
	public String wsiBorrow() {
		Borrow borrow = borrowService.getBorrowById(bid);
		
		return ajax(JsonUtil.toJson(borrow));
	}
	
	@Action(value="/wsi/borrowList")
	public String wsiBorrowList() {
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("status", 1);
//		map.put("orderBy", " id desc");
//		List<Borrow> list = borrowService.queryUserBorrowList(map);
		//Borrow borrow = borrowService.getBorrowById(bid);
		
		HttpServletRequest req = getRequest();
		Enumeration headerNames = req.getHeaderNames();
	    while (headerNames.hasMoreElements()) {
	        String key = (String) headerNames.nextElement();
	        String value = req.getHeader(key);
	        System.out.println("["+key+"="+value+"]");
	    }
		
		pager.setPageSize(15);
		Map<String, Object> map = new HashMap<String, Object>();

		int[] array = null;
		array = new int[5];
		array[0] = 1;
		array[1] = 3;
		array[2] = 4;
		array[3] = 5;
		array[4] = 7;
		map.put("array", array);// 显示产品的状态
	

		// map.put("noType", "20");//不显示债权转让 项目

		// productType="1";

		// map.put("code", code);
		// map.put("name", name);
		String order = "b.show_sort desc,b.verify_time desc";
		map.put("orderBy", order);
		pager = borrowService.queryBorrowList(pager, map);
		
List<BorrowBean> list = new ArrayList<BorrowBean>();
if(pager.getResult()!=null && pager.getResult().size() > 0) {
	//List<Borrow> bList = (List<Borrow>) pager.getResult();
	for(Borrow borrow:(List<Borrow>) pager.getResult()) {
		BorrowBean b = new BorrowBean();
		b.setId(borrow.getId());
		b.setName(borrow.getName());
		b.setType(borrow.getType());
		b.setShowBorrowStatus(borrow.getShowBorrowStatus());
		list.add(b);
	}
}
		
		return ajax(JsonUtil.listToJson(list));
	}

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

}
