package com.qmd.action.util;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.user.BorrowListingBean;
import com.qmd.bean.user.BorrowListingItem;
import com.qmd.mode.util.Listing;
import com.qmd.service.util.ListingService;
import com.qmd.util.JsonUtil;
import org.apache.struts2.convention.annotation.Action;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class ApiListingAction extends ApiBaseAction {

	private static final long serialVersionUID = 6968140663340736339L;

	@Resource
	private ListingService listingService;
	
	@Action(value="/api/getChildListingBySign/detail")
	public String getChildListingBySignList(){
		List<Listing> llist = listingService.getChildListingBySignList(id);
		BorrowListingBean bean = new BorrowListingBean();
		List<BorrowListingItem> result = new ArrayList<BorrowListingItem>();
		for(Listing l : llist){
			BorrowListingItem item = new BorrowListingItem();
			item.setName(l.getName());
			item.setDescription(l.getDescription());
			item.setKeyValue(l.getKeyValue());
			result.add(item);
		}
		bean.setBorlistingList(result);
		return ajax(JsonUtil.toJson(bean));
	}
}
