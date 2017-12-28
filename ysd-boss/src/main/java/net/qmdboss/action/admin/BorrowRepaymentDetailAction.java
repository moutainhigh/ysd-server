package net.qmdboss.action.admin;

import net.qmdboss.entity.BorrowRepaymentDetail;
import net.qmdboss.service.BorrowRepaymentDetailService;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;

/**
 * 
 * @author Administrator
 *
 */
@ParentPackage("admin")
public class BorrowRepaymentDetailAction extends BaseAdminAction {

	
	private static final long serialVersionUID = 296915731178152785L;
	
	private BorrowRepaymentDetail borrowRepaymentDetail;
	
	private Integer status;//状态 
	
	@Resource(name = "borrowRepaymentDetailServiceImpl")
	private BorrowRepaymentDetailService borrowRepaymentDetailService;
	
	
	public String list(){
		
		pager = borrowRepaymentDetailService.findLate(pager);		
		return LIST;
	}
	
	public String borrowRepaymentNopay() {
		pager = borrowRepaymentDetailService.findRepaymentNot(pager);		
		return "nopay";
	}
	
	public String borrowRepayment() {
		pager = borrowRepaymentDetailService.findRepayment(pager,status);		
		return "all";
	}

	
	
	public BorrowRepaymentDetail getBorrowRepaymentDetail() {
		return borrowRepaymentDetail;
	}


	public void setBorrowRepaymentDetail(BorrowRepaymentDetail borrowRepaymentDetail) {
		this.borrowRepaymentDetail = borrowRepaymentDetail;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
}
