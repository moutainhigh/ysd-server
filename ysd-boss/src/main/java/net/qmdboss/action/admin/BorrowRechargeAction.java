package net.qmdboss.action.admin;

import net.qmdboss.entity.Admin;
import net.qmdboss.entity.BorrowRecharge;
import net.qmdboss.service.AdminService;
import net.qmdboss.service.BorrowRechargeService;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;

/**
 * 
 * @author Administrator
 *
 */
@ParentPackage("admin")
public class BorrowRechargeAction extends BaseAdminAction {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2529279059465463418L;

	private BorrowRecharge borrowRecharge;
	
	private Integer status;//状态 
	
	private Integer borrowId;
	private Integer borrowRepaymentDetailId;
	
	@Resource(name = "borrowRechargeServiceImpl")
	private BorrowRechargeService borrowRechargeService;
	@Resource(name = "adminServiceImpl")
	private AdminService  adminService;
	
	public String list(){
		
		pager = borrowRechargeService.getBorrowRechargePage(borrowRecharge,pager);		
		return LIST;
	}
	
	public String verify(){
		
		borrowRecharge = borrowRechargeService.get(id);
		
//		pager = borrowRechargeService.getBorrowRechargePage(borrowRecharge,pager);		
		return "verify";
	}
	
	public String update() {
		
		int flg = -1;
		
		Admin admin = adminService.getLoginAdmin();
		borrowRecharge.setVerifyAdmin(admin.getName());
		borrowRecharge.setVerifyPhone(admin.getPhone());
		if(status==1) {
			flg = borrowRechargeService.updateBorrowRechargeOK(id, borrowId, borrowRepaymentDetailId, borrowRecharge,admin.getLoginIp());
		} else if(status==2) {
			flg = borrowRechargeService.updateBorrowRechargeNG(id, borrowRepaymentDetailId, borrowRecharge);
		}  
		
		if (flg != 0) {
			return ERROR;
		}
		
		redirectUrl = "borrow_recharge!list.action";
		
		return SUCCESS;
	}
	
	
	
//	public String borrowRepaymentNopay() {
//		pager = borrowRepaymentDetailService.findRepaymentNot(pager);		
//		return "nopay";
//	}
//	
//	public String borrowRepayment() {
//		pager = borrowRepaymentDetailService.findRepayment(pager,status);		
//		return "all";
//	}
//
//	
//	
//	public BorrowRepaymentDetail getBorrowRepaymentDetail() {
//		return borrowRepaymentDetail;
//	}
//
//
//	public void setBorrowRepaymentDetail(BorrowRepaymentDetail borrowRepaymentDetail) {
//		this.borrowRepaymentDetail = borrowRepaymentDetail;
//	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BorrowRecharge getBorrowRecharge() {
		return borrowRecharge;
	}

	public void setBorrowRecharge(BorrowRecharge borrowRecharge) {
		this.borrowRecharge = borrowRecharge;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public Integer getBorrowRepaymentDetailId() {
		return borrowRepaymentDetailId;
	}

	public void setBorrowRepaymentDetailId(Integer borrowRepaymentDetailId) {
		this.borrowRepaymentDetailId = borrowRepaymentDetailId;
	}
	
	
	
	
}
