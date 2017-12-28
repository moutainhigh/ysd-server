package net.qmdboss.action.admin;

import net.qmdboss.bean.Pager.Order;
import net.qmdboss.service.UserAwardDetailService;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;

@ParentPackage("admin")
public class UserAwardDetailAction extends BaseAdminAction {

	private static final long serialVersionUID = 5116774804419742211L;
	
	@Resource(name = "userAwardDetailServiceImpl")
	private UserAwardDetailService userAwardDetailService;
	
	public String list() {
		pager.setOrder(Order.desc);
		pager.setOrderBy("createDate");
		pager = userAwardDetailService.findPager(pager);
		return LIST;
	}

}
