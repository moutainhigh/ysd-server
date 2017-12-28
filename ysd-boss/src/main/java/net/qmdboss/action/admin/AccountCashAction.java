package net.qmdboss.action.admin;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import net.qmdboss.entity.*;
import net.qmdboss.service.*;
import net.qmdboss.util.CommonUtil;
import net.qmdboss.util.SettingUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParentPackage("admin")
public class AccountCashAction extends BaseAdminAction {
	Logger log = Logger.getLogger(AccountCashAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AccountCash accountCash;
	String bankName;
	SettingUtil settingUtil;
	private List<Listing> listingList;
	private Admin admin;
	private Date startDate;
	private Date endDate;
	List <UserAccountRecharge>userAccRechargeList;
	
	@Resource(name = "accountCashServiceImpl")
	private AccountCashService accountCashService ;
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	@Resource(name = "listingServiceImpl")
	private ListingService listingService ;
	
	@Resource(name = "adminServiceImpl")
	private AdminService  adminService;
	
	
	
	@Resource(name = "userAccountRechargeServiceImpl")
	private UserAccountRechargeService userAccountRechargeService;

	
	public Admin getAdmin() {
		return admin;
	}



	public void setAdmin(Admin admin) {
		this.admin = admin;
	}



	public List<Listing> getListingList() {
		return listingList;
	}



	public void setListingList(List<Listing> listingList) {
		this.listingList = listingList;
	}



	public String getBankName() {
		return bankName;
	}



	public void setBankName(String bankName) {
		this.bankName = bankName;
	}



	public AccountCash getAccountCash() {
		return accountCash;
	}



	public void setAccountCash(AccountCash accountCash) {
		this.accountCash = accountCash;
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



	/**
	 * 按条件查询投资人提现列表
	 * @author zhanf
	 * @return
	 * time 2012-12-26
	 */
	public String investList() {
		if(startDate!=null){
			if(endDate == null){
				addActionError("截止时间必填!");
				return ERROR;
			}
		}

		if(endDate!=null){
			if(startDate == null){
				addActionError("起始时间必填!");
				return ERROR;
			}
		}
		try {
			if (accountCash==null) {
				accountCash = new AccountCash();
				accountCash.setStatus(0);
				User user = new User();
				user.setTypeId(0);
				accountCash.setUser(user);
			}
			pager = accountCashService.getCashPage(accountCash, pager,startDate,endDate);
			listingList = listingService.getChildListingBySignList("account_bank");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "investList";
	}
	/**
	 * 按条件查询借款人提现列表
	 * @author zhanf
	 * @return
	 * time 2012-12-26
	 */
	public String borrowCashList() {
		if(startDate!=null){
			if(endDate == null){
				addActionError("截止时间必填!");
				return ERROR;
			}
		}

		if(endDate!=null){
			if(startDate == null){
				addActionError("起始时间必填!");
				return ERROR;
			}
		}
		try {
			if (accountCash==null) {
				accountCash = new AccountCash();
				accountCash.setStatus(0);
				User user = new User();
				user.setTypeId(1);
				accountCash.setUser(user);
			}
			pager = accountCashService.getCashPage(accountCash, pager,startDate,endDate);
			listingList = listingService.getChildListingBySignList("account_bank");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "borCashList";
	}

	/**
	 * 按条件查询提现列表
	 * @author zhanf
	 * @return
	 * time 2012-12-26
	 */
	public String list() {
		if(startDate!=null){
			if(endDate == null){
				addActionError("截止时间必填!");
				return ERROR;
			}
		}

		if(endDate!=null){
			if(startDate == null){
				addActionError("起始时间必填!");
				return ERROR;
			}
		}
		pager = accountCashService.getCashPage(accountCash, pager,startDate,endDate);
		listingList = listingService.getChildListingBySignList("account_bank");
		return "list";
	}
	
	/**
	 * 	查询单个提现记录信息或审核
	 * @author zhanf
	 * @return
	 * time 2012-12-27
	 */
	public String edit() {
		accountCash = accountCashService.load(id);
		String sign = "account_bank";
		bankName = listingService.findChildListingByKeyValue(sign, accountCash.getBank());
//		if(accountCash.getStatus()==0){
//			if(accountCash.getUser().getTypeId()==0){
//				BigDecimal fee = getFee(accountCash);
//				int i = accountCash.getFee().compareTo(fee);
//				if(i==1){
//					accountCash.setFee(fee);
//					accountCash.setCredited(accountCash.getTotal().subtract(fee));
//				}
//			}
//		}		
		if(accountCash.getStatus()!=0 && accountCash.getVerifyUserid() != null){
				admin = adminService.get(accountCash.getVerifyUserid());
		}
		return INPUT;
	}
	
	public BigDecimal getFee(AccountCash accountCash){
		Date endTime = new Date();
		Date startTime =  CommonUtil.getDateBefore(endTime,15);
		userAccRechargeList= userAccountRechargeService.getUserAccountRechargeList(accountCash.getUser().getId(), 1, startTime, endTime);
		 BigDecimal sum = new BigDecimal(0.00);
		for(UserAccountRecharge u : userAccRechargeList){
			sum=sum.add(u.getMoney());
		}
		BigDecimal bestMoney;//建议提取的最佳金额
		BigDecimal applyMoney =new BigDecimal(0);//申请中的提现
		BigDecimal fee =new BigDecimal(0); //产生的费用
		BigDecimal compare = new BigDecimal(0);
		List<AccountCash> accountCashList  =accountCashService.queryAccountCashList(accountCash);
		if(accountCashList.size()>0){
			for(AccountCash accountCash1:accountCashList){
				if(accountCash1.getId()!=accountCash.getId()){
					applyMoney= applyMoney.add(accountCash1.getTotal()) ;
				}
			}
		}
		bestMoney=(accountCash.getUser().getAccount().getTotal().subtract(applyMoney)).subtract(sum);
		if(compare.compareTo(bestMoney)==1){
			bestMoney=compare;
		}
		Listing listing = listingService.load(1471);//固定提现手续费
		Listing listing1 = listingService.load(1472);//额外提现手续费
		BigDecimal firstfee = BigDecimal.valueOf(Double.parseDouble(listing.getKeyValue()));//固定提现手续费
		BigDecimal feeValue = BigDecimal.valueOf(Double.parseDouble(listing1.getKeyValue()));//超额提现手续费
		int i=accountCash.getTotal().compareTo(bestMoney);
		if(i==1){
			fee =firstfee.add(( (accountCash.getTotal().subtract(bestMoney)).multiply(feeValue)));
		}else{
			fee=firstfee;
		}
		return fee;
		
	}
	/**
	 * 修改提现记录
	 * 提现走存管接口
	 * @author zhanf
	 * @author yujian
	 *
	 * @return
	 * time 2012-12-27
	 */
	@InputConfig(resultName = "error")
	public String update() {
		/*AccountCash	accountCash1 = accountCashService.load(id);
		Admin admin = adminService.getLoginAdmin();
		BigDecimal fee = BigDecimal.ZERO;// getFee(accountCash1);
		boolean falg= accountCashService.updateCashMoney(accountCash, id, admin, fee);
		StringBuffer logInfoStringBuffer = new StringBuffer();
		if(falg){

			logInfoStringBuffer.append("用户名:" +admin.getName()+"，审核:" + accountCash1.getUser().getRealName());
			logInfoStringBuffer.append("的提现，ID为："+accountCash1.getId()+",金额为:"+accountCash1.getTotal()+",提现时间为："+accountCash1.getCreateDate()+",备注："+accountCash.getVerifyRemark()+";提现记录");
			logInfo = logInfoStringBuffer.toString();
*/
			//发起银行请求
			/*String pay_url = "";//ConfigUtil.getConfigUtil().get("pay.url");
			log.info(pay_url);
			Map<String,String> pmap = new HashMap<String,String>();
			pmap.put("orderNo",String.valueOf(id));
			pmap.put("amount",accountCash1.getTotal().toString());
			pmap.put("userId",String.valueOf( accountCash1.getUser().getId() ) );

			String msg = com.ysd.util.HttpUtil.post(pay_url+"/cash",pmap);
			if(msg == null || "".equals(msg)){
				logInfo="["+id+"]存管服务器无法访问,请重新尝试！";
				return "error";
			}*/



		/*}else{
			logInfoStringBuffer.append("用户名:" +adminService.getLoginAdmin().getName()+"，审核:" + accountCash1.getUser().getRealName());
			logInfoStringBuffer.append("的提现，ID为："+accountCash1.getId()+",金额为:"+accountCash1.getTotal()+",提现时间为："+accountCash1.getCreateDate()+",备注："+accountCash.getVerifyRemark()+";提现失败");
			logInfo = logInfoStringBuffer.toString();
		}*/
		redirectUrl = "account_cash!list.action";
		return SUCCESS;
	}
	
	
}
