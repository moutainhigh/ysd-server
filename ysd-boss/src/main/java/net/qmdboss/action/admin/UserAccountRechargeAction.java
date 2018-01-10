package net.qmdboss.action.admin;

import net.qmdboss.entity.RechargeConfig;
import net.qmdboss.entity.User;
import net.qmdboss.entity.UserAccountRecharge;
import net.qmdboss.service.AdminService;
import net.qmdboss.service.RechargeConfigService;
import net.qmdboss.service.UserAccountRechargeService;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 充值记录
 * @author Xsf
 *
 */
@ParentPackage("admin")
public class UserAccountRechargeAction extends BaseAdminAction{

	private static final long serialVersionUID = 8755711807212617439L;
	private User user;
	private UserAccountRecharge userAccountRecharge;
	private Integer rechargeInterfaceId;//充值方式ID
	
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private String dateType;//提交时间【0】还是完成时间【1】
	private List<UserAccountRecharge> userAccountRechargeList;//充值记录
	private Integer status;//处理状态
	private String moneygt;//大于金额
	private String moneylt;//小于金额
	
	@Resource(name = "userAccountRechargeServiceImpl")
	private UserAccountRechargeService userAccountRechargeService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "rechargeConfigServiceImpl")
	private RechargeConfigService rechargeConfigService;
	
	
	public String list(){
			Map<String,Object> map = new HashMap<String,Object>();
			if(pager != null || !pager.getSearchBy().equals("tradeNo") ){
				SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
				
				if("".equals(moneygt) &&  !"".equals(moneylt)){
					map.put("moneylt", moneylt);
				}else if("".equals(moneylt) && !"".equals(moneygt)){
					map.put("moneygt", moneygt);
				}else if("".equals(moneylt)&& !"".equals(moneylt)){
					if(new BigDecimal(map.get("moneylt").toString()).compareTo(new BigDecimal(map.get("moneygt").toString()))<0){
						addActionError("金额参数错误!");
						return ERROR;
					}
					map.put("moneygt", moneygt);
					map.put("moneylt", moneylt);
				}
				
				map.put("dateType", dateType);
				map.put("startDate",startDate != null ? dateformat.format(startDate):null);
				map.put("endDate",endDate != null ? dateformat.format(endDate): null);
				map.put("rechargeInterfaceId",userAccountRecharge != null && userAccountRecharge.getRechargeInterface()!=null?userAccountRecharge.getRechargeInterface().getId():null);
				map.put("status", status);//交易状态
			}
//			if(rechargeInterfaceId != null && rechargeInterfaceId==0){//审核线下充值
//				map.put("rechargeInterfaceId",0);
//				map.put("status", 2);//交易状态
//			}
			pager = userAccountRechargeService.findPager(pager, map);
		
		return LIST;
	}
	
	//审核线下充值列表 ?userAccountRecharge.status=2&amp;rechargeInterfaceId=0
	public String verifyList(){
		Map<String,Object> map = new HashMap<String,Object>();
		if(pager != null || !pager.getSearchBy().equals("tradeNo") ){
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
			
			if("".equals(moneygt) &&  !"".equals(moneylt)){
				map.put("moneylt", moneylt);
			}else if("".equals(moneylt) && !"".equals(moneygt)){
				map.put("moneygt", moneygt);
			}else if("".equals(moneylt)&& !"".equals(moneylt)){
				if(new BigDecimal(map.get("moneylt").toString()).compareTo(new BigDecimal(map.get("moneygt").toString()))<0){
					addActionError("金额参数错误!");
					return ERROR;
				}
				map.put("moneygt", moneygt);
				map.put("moneylt", moneylt);
			}
			
			map.put("dateType", dateType);
			map.put("startDate",startDate != null ? dateformat.format(startDate):null);
			map.put("endDate",endDate != null ? dateformat.format(endDate): null);
			map.put("rechargeInterfaceId",userAccountRecharge != null && userAccountRecharge.getRechargeInterface()!=null?userAccountRecharge.getRechargeInterface().getId():null);
			map.put("status", status);//交易状态
		}
		//审核线下充值
		rechargeInterfaceId = 0;
		map.put("rechargeInterfaceId",rechargeInterfaceId);
		map.put("status", 2);//交易状态
		
		pager = userAccountRechargeService.findPager(pager, map);
		return LIST;
	}
	
	//审核线上充值列表 ?userAccountRecharge.status=2&amp;rechargeInterfaceId=0
	public String verifyListOnLine(){
		Map<String,Object> map = new HashMap<String,Object>();
		if(pager != null || !pager.getSearchBy().equals("tradeNo") ){
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
			
			if("".equals(moneygt) &&  !"".equals(moneylt)){
				map.put("moneylt", moneylt);
			}else if("".equals(moneylt) && !"".equals(moneygt)){
				map.put("moneygt", moneygt);
			}else if("".equals(moneylt)&& !"".equals(moneylt)){
				if(new BigDecimal(map.get("moneylt").toString()).compareTo(new BigDecimal(map.get("moneygt").toString()))<0){
					addActionError("金额参数错误!");
					return ERROR;
				}
				map.put("moneygt", moneygt);
				map.put("moneylt", moneylt);
			}
			
			map.put("dateType", dateType);
			map.put("startDate",startDate != null ? dateformat.format(startDate):null);
			map.put("endDate",endDate != null ? dateformat.format(endDate): null);
//			map.put("rechargeInterfaceId",userAccountRecharge != null && userAccountRecharge.getRechargeInterface()!=null?userAccountRecharge.getRechargeInterface().getId():null);
			map.put("status", status);//交易状态
		}
		
		map.put("rechargeInterfaceIdEq", "NOT");
		
		//审核线下充值
//		rechargeInterfaceId = 0;
//		map.put("rechargeInterfaceId",rechargeInterfaceId);
		map.put("status", 2);//交易状态
		
		pager = userAccountRechargeService.findPager(pager, map);
		return "online";
	}
	
	public String edit(){
		if(id == null){
			addActionError("审核参数错误!");
			return ERROR;
		}
		userAccountRecharge = userAccountRechargeService.load(id);
		return INPUT;
	}

	/**
	 * 审核线上、线下充值
	 * @return
	 */
	public String update(){
		StringBuffer logInfoStringBuffer = new StringBuffer();
		
		if (rechargeInterfaceId!=null && rechargeInterfaceId !=0) {//线上充值审核
			 logInfoStringBuffer.append("补单:");
			int status = userAccountRecharge.getStatus();//充值状态
			
			int ret = userAccountRechargeService.repairUpdateOnline(id,status,getRequest(),logInfoStringBuffer);
			logInfo = logInfoStringBuffer.toString();
			if(ret==1){
				addErrorMessages("订单已改,审核失败!");
				return ERROR;
			} else if(ret==0){
				redirectUrl="user_account_recharge!verifyListOnLine.action";
				return SUCCESS;
			} else if (ret ==2) {
				addErrorMessages("订单状态已改,审核失败!");
				return ERROR;
			}else if (ret ==3) {
				addErrorMessages("订单异常,请查实!");
				return ERROR;
			}
			addErrorMessages("审核失败!");
			return ERROR;
			
		}
		
		logInfoStringBuffer.append("线下充值审核：");
		userAccountRecharge.setIpVerify(getRequest().getRemoteAddr());
		int ret = userAccountRechargeService.updateApproveOffLine(id, userAccountRecharge, adminService.getLoginAdmin(), logInfoStringBuffer);
		logInfo = logInfoStringBuffer.toString();
		if (ret ==2) {
			addErrorMessages("订单状态已改,审核失败!");
			return ERROR;
		}
		redirectUrl="user_account_recharge!verifyListOnLine.action";
		return SUCCESS;
	
	}
	
	/**
	 * 跳转到补单页面
	 * @return
	 */
	public String toRepair(){
		return "repair";
	}
	
	/**
	 * 补单操作
	 * @return
	 */
	public String ajaxRepair(){
		StringBuffer logInfoStringBuffer = new StringBuffer("补单:");
		userAccountRecharge = userAccountRechargeService.get(id);
		if(userAccountRecharge.getStatus().equals(1)){
			return ajax(Status.error,"已经补过单");
		}else{
			userAccountRecharge.setStatus(1);
			userAccountRecharge.setReturned("后台补单");
			userAccountRecharge.setRechargeDate(new Date());
			userAccountRechargeService.repairUpdate(userAccountRecharge,getRequest());
			
			logInfoStringBuffer.append("充值记录ID:"+userAccountRecharge.getId());
			logInfoStringBuffer.append("金额:"+userAccountRecharge.getMoney());
			logInfoStringBuffer.append("手续费:"+userAccountRecharge.getFee());
		}
		return ajax(Status.success,"操作成功");
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserAccountRecharge getUserAccountRecharge() {
		return userAccountRecharge;
	}
	public void setUserAccountRecharge(UserAccountRecharge userAccountRecharge) {
		this.userAccountRecharge = userAccountRecharge;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getRechargeInterfaceId() {
		return rechargeInterfaceId;
	}
	public void setRechargeInterfaceId(Integer rechargeInterfaceId) {
		this.rechargeInterfaceId = rechargeInterfaceId;
	}
	//获取所有支付方式
	public List<RechargeConfig> getRechargeConfigList(){
		return rechargeConfigService.getRechargeConfigList();
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public List<UserAccountRecharge> getUserAccountRechargeList() {
		return userAccountRechargeList;
	}

	public void setUserAccountRechargeList(
			List<UserAccountRecharge> userAccountRechargeList) {
		this.userAccountRechargeList = userAccountRechargeList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMoneygt() {
		return moneygt;
	}

	public void setMoneygt(String moneygt) {
		this.moneygt = moneygt;
	}

	public String getMoneylt() {
		return moneylt;
	}

	public void setMoneylt(String moneylt) {
		this.moneylt = moneylt;
	}
}
