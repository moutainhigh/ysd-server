package com.qmd.action.borrow;

import com.qmd.action.base.BaseAction;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

@Service("borCpyAction")
@InterceptorRefs({
	@InterceptorRef(value = "userVerifyInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
public class BorrowCpyAction extends BaseAction {

	private static final long serialVersionUID = -7490914437238146463L;
	Logger log = Logger.getLogger(BorrowCpyAction.class);
	/**
	 * 选择标类型
	 * @return
	 */
	@Action(value="/borrow/borrowChoose",
			results={@Result(name="success", location="/content/borrow/check_company.ftl", type="freemarker"),
					@Result(name="credit", location="/content/borrow/borrowSelect.ftl", type="freemarker"),
					@Result(name="fail", location="/content/borrow/toSuccess.ftl", type="freemarker")})
	public String borrowChoose() {
		
		if (getLoginUser().getTypeId().intValue() != 1) {
			addActionError("只有服务商才能访问！");
			return "error_ftl";
		}
		
		if (getLoginUser().getPayPassword()==null||"".equals(getLoginUser().getPayPassword())) {
			addActionError("请先设置安全密码！");
			redirectUrl = getContextPath()+"/userCenter/toPayPassword.do";
			return "error_ftl";
		}
		
		return "credit";
	}
	
	
}
