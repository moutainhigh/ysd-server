package net.qmdboss.action.admin;

import net.qmdboss.entity.*;
import net.qmdboss.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台Action类 - 后台页面
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXE5A2C60595A4C64534D70AC9A5B94FDD
 * ============================================================================
 */

@ParentPackage("admin")
public class PageAction extends BaseAdminAction {

	private static final long serialVersionUID = 3148667965663281403L;
/**
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "messageServiceImpl")
	private MessageService messageService;
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	@Resource(name = "goodsNotifyServiceImpl")
	private GoodsNotifyService goodsNotifyService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
**/
		
	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "userAccountRechargeServiceImpl")
	private UserAccountRechargeService userAccountRechargeService;
	@Resource(name = "userAccountServiceImpl")
	private UserAccountService userAccountService;
	@Resource(name = "userAccountDetailServiceImpl")
	private UserAccountDetailService userAccountDetailService;
	@Resource(name = "borrowServiceImpl")
	private BorrowService borrowService ;
	@Resource(name = "borrowRepaymentDetailServiceImpl")
	private BorrowRepaymentDetailService borrowRepaymentDetailService ;
	@Resource(name = "accountCashServiceImpl")
	private AccountCashService accountCashService ;
	@Resource(name = "rewardsServiceImpl")
	private RewardsService rewardsService;
	
	// 后台主页面
	public String main() {
		return "main";
	}
	
	// 后台Header
	public String header() {
		return "header";
	}
	
	// 后台菜单
	public String menu() {
		return "menu";
	}
	
	// 后台中间(显示/隐藏菜单)
	public String middle() {
		return "middle";
	}
	
	// 后台首页
	public String index() {
		return "index";
	}
	
	//个人实名认证待审核
	public Integer getRealName0Count(){
		return userService.getUserPager(1, pager).getTotalCount();
	}
	
	//企业实名认证待审核
	public Integer getRealName1Count(){
		return userService.getUserPager(4, pager).getTotalCount();
	}	
	//个人借款者资质认证待审核
	public Integer getBorrower0Count(){
		return userService.getUserPager(5, pager).getTotalCount();
	}	
	//企业借款者资质认证待审核
	public Integer getBorrower1Count(){
		return userService.getUserPager(6, pager).getTotalCount();
	}
	
	//VIP待审核
	public Integer getVipStatusCount(){
		return userService.getUserPager(3, pager).getTotalCount();
	}

	//借款人待审核
	public Integer getTypeStatusCount(){
		return userService.getUserPager(2, pager).getTotalCount();
	}
	
	//借款额度申请
	public Integer getBorrowingLimitCount(){
		return 0;
	}
	
	//逾期待处理
	public Integer getOverdueCount(){
		return   borrowRepaymentDetailService.findLate(pager).getTotalCount();
	}
	
	//发标待审核
	public Integer getTenderCount(){
		Borrow borrow = new Borrow();
		borrow.setStatus(0);
		return borrowService.getBorrowPage(borrow, pager).getTotalCount();
	}
	
	//满标待审核
	public Integer getFullScaleCount(){
		Borrow borrow = new Borrow();
		borrow.setStatus(5);
		borrow.setBalance("0");
		return borrowService.getBorrowPage(borrow,pager).getTotalCount();
	}	
		
	//线下充值待审核
	public Integer getRechargeCount(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dateType", "0");
		map.put("startDate",null);
		map.put("endDate",null);
		map.put("rechargeInterfaceId",0);
		map.put("status", 2);
		
		return userAccountRechargeService.findPager(pager,map).getTotalCount();
	}
	
	//费用扣除待审核	 
	public Integer getDeductCount(){
		Rewards re = new Rewards();
		re.setType(0);
		re.setStatus(2);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", re.getType());//类型
		map.put("status", re.getStatus());//状态
		return rewardsService.findPagerByType(pager, map).getTotalCount();
	}
	
	//奖励待审核	 
	public Integer getRewardCount(){
		Rewards re = new Rewards();
		re.setType(1);
		re.setStatus(2);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", re.getType());//类型
		map.put("status", re.getStatus());//状态
		return rewardsService.findPagerByType(pager, map).getTotalCount();
	}

	//资金转入待审核	 
	public Integer getMoneyIntoCount(){
		Rewards re = new Rewards();
		re.setType(2);
		re.setStatus(2);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", re.getType());//类型
		map.put("status", re.getStatus());//状态
		return rewardsService.findPagerByType(pager, map).getTotalCount();
	}
	//投资人提现待审核
	public Integer getTzWithdrawCount(){
		AccountCash a = new AccountCash();
		a.setStatus(0);
		User u= new User();
		u.setTypeId(0);
		a.setUser(u);
		return accountCashService.getCashPage(a, pager, null, null).getTotalCount();
	}
	
	//借款人提现待审核
	public Integer getJkWithdrawCount(){
		AccountCash a = new AccountCash();
		a.setStatus(0);
		User u= new User();
		u.setTypeId(1);
		a.setUser(u);
		return accountCashService.getCashPage(a, pager, null, null).getTotalCount();
	}
	//充值手续费
	public BigDecimal getRechargeableCommissionFee(){
		return userAccountDetailService.getFeeByType("fee", null, null);
	}

	//实名认证费用
	public BigDecimal getRealNameFee(){
		return userAccountDetailService.getFeeByType("realname", null, null);
	}
	
	//利息管理费用
	public BigDecimal getInterestManagementFee(){
		return userAccountDetailService.getFeeByType("tender_mange", null, null);
	}
	//VIP会员费
	public BigDecimal getVipMemberFee(){
		return userAccountDetailService.getFeeByType("vip", null, null);
	}
	
	//线上充值总额
	public BigDecimal getRechargeOnLineSuccessFee(){
		UserAccountRecharge uar = new UserAccountRecharge();
		uar.setType("1");
		uar.setStatus(1);
		return userAccountRechargeService.getMoneyCount(uar);
//		return userAccountDetailService.getFeeByType("recharge", null, null);
	}
	
	//线下充值总额
	public BigDecimal getRechargeOffLineSuccessFee(){
		UserAccountRecharge uar = new UserAccountRecharge();
		uar.setType("0");
		uar.setStatus(1);
		return userAccountRechargeService.getMoneyCount(uar);
//		return userAccountDetailService.getFeeByType("recharge_offline", null, null);
	}
	
	//借款手续费
	public BigDecimal getLoanProceduresFee(){
		return userAccountDetailService.getFeeByType("borrow_fee", null, null);
	}
	
	//成功借出总额
	public BigDecimal getLoanAccountTotalFee(){
		return borrowService.getAccountYesTotal(null,null);
//		return new BigDecimal(0);
	}
	//已还款总额
	public BigDecimal getRepaymentAccountTotalFee(){
		return borrowRepaymentDetailService.getRepaymentAccountTotal(1, null, null);
//		return new BigDecimal(0);
	}
	//未还款总额
	public BigDecimal getNoRepaymentAccountTotalFee(){
		return borrowRepaymentDetailService.getRepaymentAccountTotal(0, null, null);
		
//		return new BigDecimal(0);
	}
	//逾期总额
	public BigDecimal getOverdueTotalFee(){
		return new BigDecimal(0);
	}
	//逾期已还款总额
	public BigDecimal getOverdueYesTotalFee(){
		return new BigDecimal(0);
	}
	//逾期未还款总额
	public BigDecimal getOverdueNoTotalFee(){
		return new BigDecimal(0);
	}
	
	// 所有账户可用资金总和
	public BigDecimal getUserAccountAbleMoneyAll() {
		return userAccountService.getAbleMoneyAll(null);
	}
	//投资者可用资金总和
	public BigDecimal getUserAccountAbleMoneyAllInvestor () {
		return userAccountService.getAbleMoneyAll(0);
	}
	// 投资者续投总额
	public BigDecimal getUserAccountContinueMoneyInvestor () {
		return userAccountService.getUserAccountSum(0,"continueTotal");
	} 
//	//续投总额
//	public BigDecimal getContinueAll(){
//		return userAccountService.getContinueAll("continueTotal");
//	}
	// 获取会员总数
	public Long getUserTotalCount() {
		return userService.getTotalCount();
	}
	
	// 获取文章总数
	public Long getArticleTotalCount() {
		return articleService.getTotalCount();
	}
	
	// 获取JAVA版本
	public String getJavaVersion() {
		return System.getProperty("java.version");
	}
	
	// 获取系统名称
	public String getOsName() {
		return System.getProperty("os.name");
	}
	
	// 获取系统构架
	public String getOsArch() {
		return System.getProperty("os.arch");
	}
	
	// 获取系统版本
	public String getOsVersion() {
		return System.getProperty("os.version");
	}
	
	// 获取Server信息
	public String getServerInfo() {
		return StringUtils.substring(getServletContext().getServerInfo(), 0, 30);
	}
	
	// 获取Servlet版本
	public String getServletVersion() {
		return getServletContext().getMajorVersion() + "." + getServletContext().getMinorVersion();
	}


}