package net.qmdboss.action.admin;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import net.qmdboss.bean.AccountDetail;
import net.qmdboss.entity.*;
import net.qmdboss.service.*;
import net.qmdboss.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @author Xsf
 *
 */
@ParentPackage("admin")
public class AccountAction extends BaseAdminAction {
	private static final long serialVersionUID = 5686527201079251565L;
	
	
	private User user;
	private UserAccount userAccount;
	private UserAccountDetail userAccountDetail;
	private List<UserAccountDetail> userAccountDetailList;
	private List<AccountDetail> accountDetailList;
	private UserAccountRecharge userAccountRecharge;
	private Boolean isExact;//是否精确查找
	
	private String type;
	private String amount;
	private Date startDate;
	private Date endDate;
	private List<Listing> listingList;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "userAccountServiceImpl")
	private UserAccountService userAccountService;
	@Resource(name = "userAccountDetailServiceImpl")
	private UserAccountDetailService userAccountDetailService;
	@Resource(name = "userAccountRechargeServiceImpl")
	private UserAccountRechargeService userAccountRechargeService;
	@Resource(name = "rechargeConfigServiceImpl")
	private RechargeConfigService rechargeConfigService;
	@Resource
	private ListingService listingService;
	
	/**
	 * 查询所有用户账户
	 * @return
	 */
	public String list() {
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(amount)){
			Pattern numberPattern = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?");
			Matcher matcher = numberPattern.matcher(amount);
			if (!matcher.matches()) {
				addActionError("额度只允许输入数字!");
				return ERROR;
			}
			map.put("type", type);
			map.put("amount", amount);
			
		}
		map.put("typeId", 0);
		if(user!=null){
			map.put("memberType", user.getMemberType());
		}
//		pager = userAccountService.findPagerByMoney(pager, type, amount);
		
		pager = userAccountService.findPagerByUser(pager,map);
		return LIST;
	}

	
	/**
	 * 查询所有资金使用记录
	 * @return
	 */
	public String accountDetailList(){
		Map<String,Object> map = new HashMap<String, Object>();

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
		if(startDate != null && endDate != null){
			
			map.put("startDate", CommonUtil.getDate2String(CommonUtil.date2begin(startDate), "yyyy-MM-dd HH:mm:ss"));
			map.put("endDate", CommonUtil.getDate2String(CommonUtil.date2end(endDate), "yyyy-MM-dd HH:mm:ss"));
			
		}
		
		if(pager != null && !"".equals(pager.getKeyword())){
			map.put("username", pager.getKeyword());
		}
		map.put("isExact", isExact);
		map.put("type", type);
		
		//pager = userAccountDetailService.findPager(pager,map);//
		pager = userAccountDetailService.findPagerByHql(pager,map);//原生sql;
		return "detaillist";
	}
	/**
	 * 跳转到会员费用扣除页面
	 * @return
	 */
//	public String deduct(){
//		return "deduct";
//	}
	
	/**
	 * 执行会员费用扣除功能
	 * @return
	 */
//	@InputConfig(resultName = "error")
//	public String deductDo(){
//		user = userService.getUserByUsername(user.getUsername());
//		if(user== null){
//			addActionError("用户名不存在，请重新填写!");
//			return ERROR;
//		}
//		Pattern numberPattern = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?");
//		Matcher matcher = numberPattern.matcher(userAccountDetail.getMoney().toString());
//		if (!matcher.matches()) {
//			addActionError("金额只允许输入数字!");
//			return ERROR;
//		}
//		if(userAccountDetail.getType()==null || userAccountDetail.getType().split(",").length < 1){
//			addActionError("参数错误!");
//			return ERROR;
//		}
////		userAccountDetail.setToUser(0);
//		String type_d = userAccountDetail.getType();
//		userAccountDetail.setAddTime(new Date());
//		userAccountDetail.setType(type_d.split(",")[0].toString());
//		userAccountDetail.setOperatorIp(getRequest().getRemoteAddr());
//		userAccountDetail.setUser(user);
//		userAccountDetailService.saveUserAccountDetailByDebits(userAccountDetail,type);
//		StringBuffer logInfoStringBuffer = new StringBuffer();
//		logInfoStringBuffer.append("用户名:" + user.getUsername() + ";操作类型:" + type_d.split(",")[1]+"，扣除金额:" + userAccountDetail.getMoney());
//		logInfoStringBuffer.append(";备注:"+userAccountDetail.getRemark());
//		logInfo = logInfoStringBuffer.toString();
//		redirectUrl="account!deduct.action?verifyType=2&type="+type;
//		return SUCCESS;
//	}
	
	
	/**
	 * 跳转到线下充值页面
	 * @return
	 */
	public String recharge(){
		return "recharge";
	}
	
	
	/**
	 * 添加线下充值进入待审状态
	 * @return
	 */
	@InputConfig(resultName = "error")
	public String rechargeDo(){
		user = userService.getUserByUsername(user.getUsername());
		if(user== null){
			addActionError("用户名不存在，请重新填写");
			return ERROR;
		}
		Pattern numberPattern = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?");
		Matcher matcher = numberPattern.matcher(userAccountRecharge.getMoney().toString());
		if (!matcher.matches()) {
			addActionError("充值金额只允许输入数字!");
			return ERROR;
		}

		Matcher matcherjl = numberPattern.matcher(userAccountRecharge.getReward().toString());
		if (!matcherjl.matches()) {
			addActionError("奖励金额只允许输入数字!");
			return ERROR;
		}
		
		if(userAccountRecharge.getType()==null || userAccountRecharge.getType().split(",").length < 1){
			addActionError("参数错误!");
			return ERROR;
		}
		String type=userAccountRecharge.getType();

		userAccountRecharge.setUser(user);
		userAccountRecharge.setIpOperator(getRequest().getRemoteAddr());
		
		userAccountRechargeService.saveUserAccountRechargeByOffLine(userAccountRecharge);
		
		StringBuffer logInfoStringBuffer = new StringBuffer();
		logInfoStringBuffer.append("用户名:" + user.getUsername() + ";操作类型:" + type.split(",")[1]+",充值金额:" + userAccountRecharge.getMoney());
		logInfoStringBuffer.append(";备注:"+userAccountRecharge.getRemark());
		logInfo = logInfoStringBuffer.toString();
		redirectUrl="account!recharge.action";
		return SUCCESS;
	}
	
	/**
	 * 获取奖励金额
	 * @return
	 */
	public String ajaxOffLinejl(){
		RechargeConfig rechargeConfig = rechargeConfigService.find("background");//获取线下充值方式
		BigDecimal money = userAccountDetail.getMoney();
		return ajax(Status.success, rechargeConfig.getPaymentFee(money).toString());
	}
	
	
	/**
	 * 查询所有资金明细分类记录总和
	 * @return
	 */
	public String accountRechargeDetailTotal(){
		
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
		accountDetailList = userAccountDetailService.getUserAccountDetailTotalByType(type,startDate,endDate);
		if(StringUtils.isEmpty(type)){
			listingList = listingService.getChildListingBySignList("account_type");
			List<Listing> tempList = new ArrayList<Listing>();
			for(Listing l: listingList){
				for(AccountDetail a:accountDetailList){
					if(l.getKeyValue().equals(a.getType())){
						tempList.add(l);
					}
				}
			}
			listingList.removeAll(tempList);
		}
		return "total";
	}
	
	/**
	 * 获取最近五个月日期
	 */
	public Map<String,String> getLatelyData(){
		Map<String,String> map = new TreeMap<String,String>();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		for(int i=0;i<5;i++){
			calendar.set(Calendar.MONTH,calendar.get(calendar.MONTH)-1);
			map.put(simple.format(calendar.getTime()), calendar.get(calendar.YEAR)+"年"+(calendar.get(calendar.MONTH)+1)+"月");
		}
		return map;
	}
	
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public UserAccountDetail getUserAccountDetail() {
		return userAccountDetail;
	}

	public void setUserAccountDetail(UserAccountDetail userAccountDetail) {
		this.userAccountDetail = userAccountDetail;
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


	public List<UserAccountDetail> getUserAccountDetailList() {
		return userAccountDetailList;
	}


	public void setUserAccountDetailList(
			List<UserAccountDetail> userAccountDetailList) {
		this.userAccountDetailList = userAccountDetailList;
	}


	public List<AccountDetail> getAccountDetailList() {
		return accountDetailList;
	}


	public void setAccountDetailList(List<AccountDetail> accountDetailList) {
		this.accountDetailList = accountDetailList;
	}


	public List<Listing> getListingList() {
		return listingList;
	}


	public void setListingList(List<Listing> listingList) {
		this.listingList = listingList;
	}


	public UserAccountRecharge getUserAccountRecharge() {
		return userAccountRecharge;
	}


	public void setUserAccountRecharge(UserAccountRecharge userAccountRecharge) {
		this.userAccountRecharge = userAccountRecharge;
	}
	


	public Boolean getIsExact() {
		return isExact;
	}


	public void setIsExact(Boolean isExact) {
		this.isExact = isExact;
	}
	
}
