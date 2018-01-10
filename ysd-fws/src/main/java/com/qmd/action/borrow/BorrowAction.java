package com.qmd.action.borrow;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.mode.agency.Agency;
import com.qmd.mode.borrow.*;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserInfo;
import com.qmd.mode.user.UserRepaymentDetail;
import com.qmd.mode.util.MailRepayForInvestor;
import com.qmd.mode.util.NodeBean;
import com.qmd.service.agency.AgencyService;
import com.qmd.service.borrow.BorrowRepaymentDetailService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.borrow.BorrowTenderService;
import com.qmd.service.user.UserAccountService;
import com.qmd.service.user.UserInfoService;
import com.qmd.service.user.UserRepaymentDetailService;
import com.qmd.service.user.UserService;
import com.qmd.service.util.ListingService;
import com.qmd.util.*;
import com.qmd.util.bean.NoteImg;
import com.qmd.util.bean.RateStep;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@InterceptorRefs({
	@InterceptorRef(value = "userVerifyInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
@Service("borrowAction")
public class BorrowAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(BorrowAction.class);
	
	private String flag;//标记
	private Map<String, String> parameterMap;//参数
	private String minDate;//起始时间
	private String maxDate;//截止时间
	private String keywords;
	private String status;//借款还款状态
	private String useReason;
	private String timeLimit;
	private String accountStart;
	private String accountEnd;
	private String fundsStart;
	private String fundsEnd;
	private String orderBy;
	private String searchBy;
	public String bType;
	public String bnType;
	public String bnsType;
	public String bStarType;
	private String timeLimitStart;
	private String timeLimitEnd;
	private String aprStart;
	private String aprEnd;
	private String yearLimitStart;
	private String yearaprStart;
	private String yearaprEnd;
	private Borrow borrow;
	public Integer bId;
	private String errorMsg;
	private String errorCode;
	private String interest;
	public String[] bStatus;
	private File[] borImagesFile;
	private String[] borImageList;
	private String typeName;//获取的标类型名称
	private String  effectiveTime;//获取的截止日名称
	private String repayMes;//还款设置的天数和每期还款金额
	private Integer borrStatus;
	private String strMess="" ;//修改标时返回的还款记录
	private BigDecimal userAbleMoney;
	private BigDecimal continueTotal;
	private BigDecimal userTasteMoney;
	private int showAblePiece;
	private int showTastePiece;
	private int showContinueTotal;
	private int maxWanderPiece;
	private int minWanderPiece;
	private int maxWanderPieceContinue;
	private int minWanderPieceContinue;
	private Integer wanderPlan;
	private List<NodeBean> nodeList;
	Map<String,Object> root = new HashMap<String,Object>();
	List<BorrowRepaymentDetail> borrowRepDetailList;
	private MonthPaymentDetail[] monthPaymentList;
	private String dxpwd;// 定向密码
	private String safepwd;//安全密码
	private Date nowDate;
	private int countDown;
	private String agreementId;//协议号
	private String lastRdate;//最后还款标完成日期
	private String dayFlg;
	private PledgeRepayPlanDetail pledgeRepayPlanDetail;
	private MonthRepayPlanDetail monthRepayPlanDetail;
	private WanderRepayPlanDetail wanderRepayPlanDetail;
	private UserInfo userInfo;
	private String agreement;//判断是否出现借款协议
	private String lowestMoney;//最小投资金额
	
	private String tenderMoney;//投标份数
	private String tenderMoneyContinue;
	
	private Double wanderPieceInterest;
	
	
	private BorrowInvestReady borrowInvestReady;
	private Date thisDate;
	private Date backDate;
	private String accountUpper;
	
	private RateStep[] rateSteps;
	private String subjectdays;
	private String bty;
	
	User user;
	BorrowRepaymentDetail borrowRepaymentDetail;
	List<BorrowTender> borrowTenderList;
	List<Borrow> userBorrowList;
	List<UserRepaymentDetail> userRepayDetailList;
	BorCompany borCompany;
	BorrowTender borrowTender;
	private PaymentView retBorrow;
	private String isDxb1;
	

	private String borImageFirst;// 标的图片地址

	private String[] vouchers;// 借款凭证图片地址
	private String[] vouchersTitle;// 借款凭证标题
	private NoteImg[]  voucherImgList;//借款凭证显示
	
	private Agency agency;//对接服务商
	private Date fbendDate;//封闭期结束日期
	private Date zropenDate;//转让开放日时间
	
	@Resource
	BorrowService borrowService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	BorrowTenderService borrowTenderService;
	@Resource
	UserService userService;
	@Resource
	BorrowRepaymentDetailService borrowRepaymentDetailService;
	@Resource
	InterestCalUtil interestCalUtil;
	@Resource
	UserRepaymentDetailService userRepaymentDetailService;
	@Resource
	ListingService listingService;
	@Resource
	UserInfoService userinfoService;
	@Resource
	AgencyService agencyService;
	
	public String getRepayMes() {
		return repayMes;
	}
	public void setRepayMes(String repayMes) {
		this.repayMes = repayMes;
	}
	public BorrowTender getBorrowTender() {
		return borrowTender;
	}
	public void setBorrowTender(BorrowTender borrowTender) {
		this.borrowTender = borrowTender;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Map<String, Object> getRoot() {
		return root;
	}
	public void setRoot(Map<String, Object> root) {
		this.root = root;
	}

	public BorrowService getBorrowService() {
		return borrowService;
	}
	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}
	public BorCompany getBorCompany() {
		return borCompany;
	}
	public void setBorCompany(BorCompany borCompany) {
		this.borCompany = borCompany;
	}
	public String[] getbStatus() {
		return bStatus;
	}
	public void setbStatus(String[] bStatus) {
		this.bStatus = bStatus;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public BorrowRepaymentDetail getBorrowRepaymentDetail() {
		return borrowRepaymentDetail;
	}
	public void setBorrowRepaymentDetail(BorrowRepaymentDetail borrowRepaymentDetail) {
		this.borrowRepaymentDetail = borrowRepaymentDetail;
	}
	public List<BorrowRepaymentDetail> getBorrowRepDetailList() {
		return borrowRepDetailList;
	}
	public void setBorrowRepDetailList(
			List<BorrowRepaymentDetail> borrowRepDetailList) {
		this.borrowRepDetailList = borrowRepDetailList;
	}
	public BorrowRepaymentDetailService getBorrowRepaymentDetailService() {
		return borrowRepaymentDetailService;
	}
	public void setBorrowRepaymentDetailService(
			BorrowRepaymentDetailService borrowRepaymentDetailService) {
		this.borrowRepaymentDetailService = borrowRepaymentDetailService;
	}
	public String getTimeLimitStart() {
		return timeLimitStart;
	}
	public void setTimeLimitStart(String timeLimitStart) {
		this.timeLimitStart = timeLimitStart;
	}
	public String getTimeLimitEnd() {
		return timeLimitEnd;
	}
	public void setTimeLimitEnd(String timeLimitEnd) {
		this.timeLimitEnd = timeLimitEnd;
	}
	public String getAprStart() {
		return aprStart;
	}
	public void setAprStart(String aprStart) {
		this.aprStart = aprStart;
	}
	public String getAprEnd() {
		return aprEnd;
	}
	public void setAprEnd(String aprEnd) {
		this.aprEnd = aprEnd;
	}
	
	public String getYearLimitStart() {
		return yearLimitStart;
	}
	public void setYearLimitStart(String yearLimitStart) {
		this.yearLimitStart = yearLimitStart;
	}
	public String getYearaprStart() {
		return yearaprStart;
	}
	public void setYearaprStart(String yearaprStart) {
		this.yearaprStart = yearaprStart;
	}
	public String getYearaprEnd() {
		return yearaprEnd;
	}
	public void setYearaprEnd(String yearaprEnd) {
		this.yearaprEnd = yearaprEnd;
	}
	public String getbType() {
		return bType;
	}
	public void setbType(String bType) {
		this.bType = bType;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getUseReason() {
		return useReason;
	}
	public void setUseReason(String useReason) {
		this.useReason = useReason;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getAccountStart() {
		return accountStart;
	}
	public void setAccountStart(String accountStart) {
		this.accountStart = accountStart;
	}
	public String getAccountEnd() {
		return accountEnd;
	}
	public void setAccountEnd(String accountEnd) {
		this.accountEnd = accountEnd;
	}
	public String getFundsStart() {
		return fundsStart;
	}
	public void setFundsStart(String fundsStart) {
		this.fundsStart = fundsStart;
	}
	public String getFundsEnd() {
		return fundsEnd;
	}
	public void setFundsEnd(String fundsEnd) {
		this.fundsEnd = fundsEnd;
	}
	public Integer getbId() {
		return bId;
	}
	public void setbId(Integer bId) {
		this.bId = bId;
	}
	@JSON(serialize=true) 
	public Borrow getBorrow() {
		return borrow;
	}
	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Borrow> getUserBorrowList() {
		return userBorrowList;
	}
	public void setUserBorrowList(List<Borrow> userBorrowList) {
		this.userBorrowList = userBorrowList;
	}
	
	public List<BorrowTender> getBorrowTenderList() {
		return borrowTenderList;
	}
	public void setBorrowTenderList(List<BorrowTender> borrowTenderList) {
		this.borrowTenderList = borrowTenderList;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Map<String, String> getParameterMap() {
		return parameterMap;
	}
	public void setParameterMap(Map<String, String> parameterMap) {
		this.parameterMap = parameterMap;
	}
	public String getMinDate() {
		return minDate;
	}
	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}
	public String getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}
	public File[] getBorImagesFile() {
		return borImagesFile;
	}
	public void setBorImagesFile(File[] borImagesFile) {
		this.borImagesFile = borImagesFile;
	}
	public String[] getBorImageList() {
		return borImageList;
	}
	public void setBorImageList(String[] borImageList) {
		this.borImageList = borImageList;
	}
	public boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	} 
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public MonthPaymentDetail[] getMonthPaymentList() {
		return monthPaymentList;
	}
	public void setMonthPaymentList(MonthPaymentDetail[] monthPaymentList) {
		this.monthPaymentList = monthPaymentList;
	}
	public PaymentView getRetBorrow() {
		return retBorrow;
	}
	public void setRetBorrow(PaymentView retBorrow) {
		this.retBorrow = retBorrow;
	}
	public Integer getBorrStatus() {
		return borrStatus;
	}
	public void setBorrStatus(Integer borrStatus) {
		this.borrStatus = borrStatus;
	}
	public String getStrMess() {
		return strMess;
	}
	public void setStrMess(String strMess) {
		this.strMess = strMess;
	}
	
	public BigDecimal getUserAbleMoney() {
		return userAbleMoney;
	}
	public void setUserAbleMoney(BigDecimal userAbleMoney) {
		this.userAbleMoney = userAbleMoney;
	}
	public List<NodeBean> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<NodeBean> nodeList) {
		this.nodeList = nodeList;
	}
	public int getMaxWanderPiece() {
		return maxWanderPiece;
	}
	public void setMaxWanderPiece(int maxWanderPiece) {
		this.maxWanderPiece = maxWanderPiece;
	}
	public String getDxpwd() {
		return dxpwd;
	}
	public void setDxpwd(String dxpwd) {
		this.dxpwd = dxpwd;
	}
	public String getSafepwd() {
		return safepwd;
	}
	public void setSafepwd(String safepwd) {
		this.safepwd = safepwd;
	}
	public PledgeRepayPlanDetail getPledgeRepayPlanDetail() {
		return pledgeRepayPlanDetail;
	}
	public void setPledgeRepayPlanDetail(PledgeRepayPlanDetail pledgeRepayPlanDetail) {
		this.pledgeRepayPlanDetail = pledgeRepayPlanDetail;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public Date getNowDate() {
		return nowDate;
	}
	public void setNowDate(Date nowDate) {
		this.nowDate = nowDate;
	}
	public int getCountDown() {
		return countDown;
	}
	public void setCountDown(int countDown) {
		this.countDown = countDown;
	}
	public WanderRepayPlanDetail getWanderRepayPlanDetail() {
		return wanderRepayPlanDetail;
	}
	public void setWanderRepayPlanDetail(WanderRepayPlanDetail wanderRepayPlanDetail) {
		this.wanderRepayPlanDetail = wanderRepayPlanDetail;
	}
	public Integer getWanderPlan() {
		return wanderPlan;
	}
	public void setWanderPlan(Integer wanderPlan) {
		this.wanderPlan = wanderPlan;
	}
	public String getTenderMoney() {
		return tenderMoney;
	}
	public void setTenderMoney(String tenderMoney) {
		this.tenderMoney = tenderMoney;
	}
	public String getTenderMoneyContinue() {
		return tenderMoneyContinue;
	}
	public void setTenderMoneyContinue(String tenderMoneyContinue) {
		this.tenderMoneyContinue = tenderMoneyContinue;
	}
	public int getMinWanderPiece() {
		return minWanderPiece;
	}
	public void setMinWanderPiece(int minWanderPiece) {
		this.minWanderPiece = minWanderPiece;
	}
	public int getMaxWanderPieceContinue() {
		return maxWanderPieceContinue;
	}
	public void setMaxWanderPieceContinue(int maxWanderPieceContinue) {
		this.maxWanderPieceContinue = maxWanderPieceContinue;
	}
	public int getMinWanderPieceContinue() {
		return minWanderPieceContinue;
	}
	public void setMinWanderPieceContinue(int minWanderPieceContinue) {
		this.minWanderPieceContinue = minWanderPieceContinue;
	}
	public String getIsDxb1() {
		return isDxb1;
	}
	public void setIsDxb1(String isDxb1) {
		this.isDxb1 = isDxb1;
	}
	public String getAgreementId() {
		return agreementId;
	}
	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}
	public String getLastRdate() {
		return lastRdate;
	}
	public void setLastRdate(String lastRdate) {
		this.lastRdate = lastRdate;
	}
	public String getAgreement() {
		return agreement;
	}
	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}
	public List<UserRepaymentDetail> getUserRepayDetailList() {
		return userRepayDetailList;
	}
	public void setUserRepayDetailList(List<UserRepaymentDetail> userRepayDetailList) {
		this.userRepayDetailList = userRepayDetailList;
	}
	public String getbStarType() {
		return bStarType;
	}
	public void setbStarType(String bStarType) {
		this.bStarType = bStarType;
	}
	
	public String getDayFlg() {
		return dayFlg;
	}
	public void setDayFlg(String dayFlg) {
		this.dayFlg = dayFlg;
	}
	public MonthRepayPlanDetail getMonthRepayPlanDetail() {
		return monthRepayPlanDetail;
	}
	public void setMonthRepayPlanDetail(MonthRepayPlanDetail monthRepayPlanDetail) {
		this.monthRepayPlanDetail = monthRepayPlanDetail;
	}
	
	public BigDecimal getContinueTotal() {
		return continueTotal;
	}
	public void setContinueTotal(BigDecimal continueTotal) {
		this.continueTotal = continueTotal;
	}
	
	public int getShowContinueTotal() {
		return showContinueTotal;
	}
	public void setShowContinueTotal(int showContinueTotal) {
		this.showContinueTotal = showContinueTotal;
	}
	

	/**
	 *borrowRepayment 查询出还款列表
	 *@author zhanf 
	 * @return
	 */
	@Action(value="/borrow/borrowRepayList",results={@Result(name="success", location="/content/borrow/repaymentView.ftl", type="freemarker")})
	public String borrowRepayment(){
		
		if (getLoginUser().getTypeId().intValue() != 3) {
			addActionError("只有借款人才能访问！");
			return "error_ftl";
		}
		
		borrow = this.getBorrowService().getBorrowById(borrow.getId());
		
		if (borrow.getAgencyId().intValue() != getLoginUser().getAgencyid().intValue()) {
			addActionError("只能查看自己的标！");
			return "error_ftl";
		}
		
		
		borrowRepDetailList= this.borrowRepaymentDetailService.queryUserBorrowList(borrow.getId());
		borrow.setDivides(borrowRepDetailList.size());
		
		if (borrow.getStatus()==7) {
			borrStatus = 7;
		} else {
			borrStatus = 3;
		}
		
		return "success";
	}
	
	/**
	 * borrowRepayDetail 标还款同时扣去资金Action
	 * @author zhanf
	 * @return
	 */
	@Action(value="/borrow/borrowRepayDetail",results={
			@Result(name="success", location="userBorrowList.do?bStatus=3", type="redirectAction"),
			@Result(name="hkxq", location="/content/borrow/repaymentView.ftl", type="freemarker"),
			@Result(name="hkmx", location="hkmx.do", type="redirectAction")
	})
	public String borrowRepayDetail(){
		try{
		borrowRepaymentDetail =  this.borrowRepaymentDetailService.get(borrowRepaymentDetail.getId());
		borrowTenderList = this.borrowTenderService.getBorrowTenderByBorrowId(borrowRepaymentDetail.getBorrowId());
		borrow = borrowService.getBorrowById(borrowRepaymentDetail.getBorrowId());
		
		// 本期状态判断
		if (borrowRepaymentDetail.getStatus()==1) {
			return "hkmx";
		}
		if(borrowRepaymentDetail.getOrderNum()!=1){
			Map<String,Object> map = new HashMap<String,Object>();
			int orderNum =borrowRepaymentDetail.getOrderNum()-1;
			map.put("orderNum", orderNum);
			map.put("borrowId", borrowRepaymentDetail.getBorrowId());
			BorrowRepaymentDetail borrowRepaymentDetail1=	this.borrowRepaymentDetailService.queryBorrowRepayment(map);
			if(borrowRepaymentDetail1.getStatus()==0){
				addActionError("上期需还款金额未还，请依次还款!");
				return "error_ftl";
			}
		}
		List<MailRepayForInvestor> mailList = new ArrayList<MailRepayForInvestor>();
		int number = this.borrowTenderService.updateBorrowDetail(borrowTenderList, borrowRepaymentDetail, borrow,mailList,getRequestRemoteAddr());
		if(number==1){
			if("1".equals(flag)){
				return "hkmx";
			} else if("2".equals(flag)) {
				 borrowRepayment();
				return "hkxq";
			} else {
				return "success";
			}
		}else{
			addActionError("您账户可用金额不足，请充值!");
			return "error_ftl";
		}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * 借款人还款
	 * @return
	 */
	@Action(value="/borrow/ajaxPayBack",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String ajaxPayBack(){
		try {
			borrowRepaymentDetail = this.borrowRepaymentDetailService.get(borrowRepaymentDetail.getId());
			borrowTenderList = this.borrowTenderService.getBorrowTenderByBorrowId(borrowRepaymentDetail.getBorrowId());
			borrow = borrowService.getBorrowById(borrowRepaymentDetail.getBorrowId());

			if (borrow.getAgencyId().intValue() != this.getLoginUser().getAgencyid().intValue()) {
				return ajax(Status.error, "请确认服务商是否对应!");
			}
			// 本期状态判断
			if (borrowRepaymentDetail.getStatus() == 1) {
				return ajax(Status.error, "本期已还款!");
			}
			if (borrowRepaymentDetail.getOrderNum() != 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderNum", borrowRepaymentDetail.getOrderNum());
				map.put("borrowId", borrowRepaymentDetail.getBorrowId());
				map.put("status", 0);
				Integer npcount = borrowRepaymentDetailService.queryRepaymentNotCount(map);
			
				if (npcount > 0) {
					return ajax(Status.error, "上期需还款金额未还，请依次还款!");
				}
			}

			List<MailRepayForInvestor> mailList = new ArrayList<MailRepayForInvestor>();
			int number = this.borrowTenderService.updateBorrowDetail(
					borrowTenderList, borrowRepaymentDetail, borrow, mailList,
					getRequestRemoteAddr());

			if (number == 1) {
				return ajax(Status.error, "项目金额不足，请充值!");
			} else if (number == 2) {
				return ajax(Status.error, "本期已还完!");
			}


		} catch (Exception e) {
			e.printStackTrace();
			return ajax(Status.error, "系统错误，请联系管理员!");
		}

		return ajax(Status.success, "还款成功!");
	}
	
	public String borrowVerify(){
		 borrow = borrowService.getBorrowById(borrow.getId());
		 borrow.setStatus(3);
		return "success";
	}
	
	
	/**
	 * 借款人-未还借款
	 * @return
	 */
	@Action(value="/borrow/userBorrowNoDone",
			results={@Result(name="success", location="/content/borrow/user_borrow_list.ftl", type="freemarker"),
					@Result(name="fail", location="/content/user/login.ftl", type="freemarker")})
	public String userBorrowNoDone() {
		
		if (getLoginUser().getTypeId().intValue() != 3) {
			addActionError("只有服务商才能访问！");
			return "error_ftl";
		}
		
		if(pager == null){
			pager = new Pager();
		}
		borrStatus = 3;
		User user = this.getLoginUser();
		Map<String,Object> qMap = new HashMap<String,Object>();
		
		if(StringUtils.isNotEmpty(minDate)){
			qMap.put("minDate", CommonUtil.date2begin(CommonUtil.getString2Date(minDate, "yyyy-MM-dd HH:mm:ss")));
		}
		if(StringUtils.isNotEmpty(maxDate)){
			qMap.put("maxDate", CommonUtil.date2end(CommonUtil.getString2Date(maxDate, "yyyy-MM-dd HH:mm:ss")));
		}
		
		qMap.put("agencyId", user.getAgencyid());
//		qMap.put("keywordsCode", keywords);
		if(StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(keywords)){
			qMap.put(searchBy, keywords);
		}
		qMap.put("orderBy", " b.verify_time desc");
		
		pager = this.getBorrowService().queryBorrowListForRepay(pager,qMap);
		
		List<Borrow> borrowList = (List<Borrow>) pager.getResult();
		List<Borrow> blist = new ArrayList<Borrow>();
		for(Borrow borrow:borrowList){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("borrowId", borrow.getId());
			map.put("status", 0);
			borrowRepDetailList= this.borrowRepaymentDetailService.queryBorrowRepaymentList(map);
			BigDecimal benRepayCapital= BigDecimal.ZERO;
			BigDecimal benRepayInterest= BigDecimal.ZERO;
			if(borrowRepDetailList.size()>0){
				benRepayCapital = BigDecimal.valueOf(Double.valueOf(borrowRepDetailList.get(0).getCapital()));
				benRepayInterest = BigDecimal.valueOf(Double.valueOf(borrowRepDetailList.get(0).getInterest()));
			}
			borrow.setBenRepayCapital(benRepayCapital);
			borrow.setBenRepayInterest(benRepayInterest);
			blist.add(borrow);
		}
		pager.setResult(blist);
		return SUCCESS;
		
	}
	
	/**
	 * 借款人-还款完成
	 * @return
	 */
	@Action(value="/borrow/userBorrowDone",
			results={@Result(name="success", location="/content/borrow/user_borrow_finish_list.ftl", type="freemarker"),
					@Result(name="fail", location="/content/user/login.ftl", type="freemarker")})

	public String userBorrowDone() {
		
		if (getLoginUser().getTypeId().intValue() == 0) {
			addActionError("只有借款人才能访问！");
			return "error_ftl";
		}
		
		if(pager == null){
			pager = new Pager();
		}
		borrStatus = 7;
		bStatus = new String[]{"7"};
		User user = this.getLoginUser();
		Map<String,Object> qMap = new HashMap<String,Object>();
		//int[] array = {1,3};

		if(StringUtils.isNotEmpty(minDate)){
			qMap.put("finalMinDate", CommonUtil.date2begin(CommonUtil.getString2Date(minDate, "yyyy-MM-dd HH:mm:ss")));
		}
		if(StringUtils.isNotEmpty(maxDate)){
			qMap.put("finalMaxDate", CommonUtil.date2end(CommonUtil.getString2Date(maxDate, "yyyy-MM-dd HH:mm:ss")));
		}
		
		qMap.put("agencyId", user.getAgencyid());
//		qMap.put("keywords", keywords);
		if(StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(keywords)){
			qMap.put(searchBy, keywords);
		}
		qMap.put("array", bStatus);
		qMap.put("orderBy", " verify_time desc");
		
		pager = this.getBorrowService().queryBorrowList(pager,qMap);
		
		return SUCCESS;
		
	}
	
	/**
	 * 服务商 借款管理
	 * @return
	 */
	@Action(value="/borrow/userBorrowMgmt",results={@Result(name="success", location="/content/borrow/user_borrow_mgmt.ftl", type="freemarker"),@Result(name="fail", location="/content/user/login.ftl", type="freemarker")})
	public String userBorrowMgmt(){
		try{
			if (getLoginUser().getTypeId().intValue() == 0) {
				addActionError("只有借款人才能访问！");
				return "error_ftl";
			}
			
		if(pager == null){
			pager = new Pager();
		}
		User user = this.getLoginUser();
		Map<String,Object> qMap = new HashMap<String,Object>();
		parameterMap = new HashMap<String, String> ();
		if(StringUtils.isNotEmpty(minDate) && StringUtils.isNotEmpty(maxDate)){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			Date min;
			Date max; 
			try {
				min = simple.parse(minDate);
				max = simple.parse(maxDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				addActionError("参数错误");
				return "error_ftl";
			}
			minDate = simple.format(min);
			maxDate = simple.format(max);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(max);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)-1);
			parameterMap.put("minDate", minDate);
			parameterMap.put("maxDate", maxDate);
			qMap.put("minDate", min);
			qMap.put("maxDate", calendar.getTime());
		}
		if(StringUtils.isNotEmpty(keywords)){
			String str=CommonUtil.changeChar(keywords);
			
			parameterMap.put("keywords", str);
			qMap.put("keywords", str);
		}
		//int[] array = {1,3};
		//qMap.put("array", bStatus);
		qMap.put("aguserId", user.getAgencyid());
		//qMap.put("keywords", keywords);
		//qMap.put("useReason", useReason);
		//qMap.put("timeLimit", timeLimit);
		//qMap.put("accountStart", accountStart);
		//qMap.put("accountEnd", accountEnd);
		//qMap.put("fundsStart", fundsStart);
		//qMap.put("fundsEnd", fundsEnd);
		qMap.put("aprStart", this.getAprStart());
		qMap.put("aprEnd", this.getAprEnd());
		qMap.put("timeLimitStart", this.getTimeLimitStart());
		qMap.put("timeLimitEnd", this.getTimeLimitEnd());
		qMap.put("bType", this.getbType());
		
		if (borrStatus==null) {
			borrStatus=111;
		}
		if(borrStatus!=111){
			qMap.put("borrStatus", this.getBorrStatus());
		}else{
			if(bty.compareTo("1")==0){
				int[] array = {1};
				qMap.put("array", array);
			}else if(bty.compareTo("2")==0){
				int[] array = {2,4,6};
				qMap.put("array", array);
			}else{
				bty ="0";
				int[] array = {0,1,2,3,4,5,6,7};
				qMap.put("array", array);
			}
			
		}
		qMap.put("orderBy", "create_date desc");
		
		if(this.getOrderBy() != null){
			if("account_up".equals(this.getOrderBy())){
				qMap.put("orderBy", "account");
			}
			if("account_down".equals(this.getOrderBy())){
				qMap.put("orderBy", "account");
			}
			if("apr_up".equals(this.getOrderBy())){
				qMap.put("orderBy", "apr");
			}
			if("apr_down".equals(this.getOrderBy())){
				qMap.put("orderBy", "apr");
			}
			if("jindu_up".equals(this.getOrderBy())){
				qMap.put("orderBy", "jindu");
			}
			if("jindu_down".equals(this.getOrderBy())){
				qMap.put("orderBy", "jindu");
			}
		} 
		pager = this.getBorrowService().queryBorrowList(pager,qMap);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	
	
	
	
	/**
	 * 投资者收益明细
	 * @return
	 */
	@Action(value="/borrow/receivedDetailList",results={@Result(name="success", location="/content/borrow/borrowReceivedDetailList.ftl", type="freemarker")})
	public String getReceivedDetailList(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", getLoginUser().getId());
		parameterMap = new HashMap<String, String> ();
		if(StringUtils.isNotEmpty(minDate) && StringUtils.isNotEmpty(maxDate)){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			Date min;
			Date max; 
			try {
				min = simple.parse(minDate);
				max = simple.parse(maxDate);
			} catch (ParseException e) {
				addActionError("参数错误");
				return "error_ftl";
			}
			minDate = simple.format(min);
			maxDate = simple.format(max);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(max);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)-1);
			parameterMap.put("minDate", minDate);
			parameterMap.put("maxDate", maxDate);
			map.put("minDate", min);
			map.put("maxDate", calendar.getTime());
		}
		
		if(StringUtils.isNotEmpty(keywords)){
			parameterMap.put("keywords", keywords);
			map.put("keywords", keywords);
		}
		if(StringUtils.isNotEmpty(status)){
			parameterMap.put("status", status);
			map.put("status", status);
		}
		pager = userRepaymentDetailService.queryUserRepaymentDetailPager(pager, map);
		return SUCCESS;
	}
	
	
	/**
	 * 借款人 未还款明细
	 * @return
	 */
	@Action(value="/borrow/hkmx",results={@Result(name="success", location="/content/borrow/borrower_hkmx_list.ftl", type="freemarker")})
	public String getBorrowHkmxList(){
			try {
				if (getLoginUser().getTypeId().intValue() == 0) {
					addActionError("只有借款人才能访问！");
					return "error_ftl";
				}
				
				if(pager == null){
					pager = new Pager();
				}
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("userId", getLoginUser().getId());
				map.put("status",0);
				if(StringUtils.isNotEmpty(minDate) && StringUtils.isNotEmpty(maxDate)){
					SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
					Date min;
					Date max;
					try {
						min = simple.parse(minDate);
						max = simple.parse(maxDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						addActionError("参数错误");
						return "error_ftl";
					}
					minDate = simple.format(min);
					maxDate = simple.format(max);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(max);
					calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
					calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)-1);
					map.put("minDate", min);
					map.put("maxDate", calendar.getTime());
			}
			if(StringUtils.isNotEmpty(keywords)){
				String str = null;
				if(StringUtils.isNotEmpty(keywords)){
					 str=CommonUtil.changeChar(keywords);
				}
//				map.put("keywords", str);
				map.put("keywordsCode", str);
			}
			pager = borrowRepaymentDetailService.queryBorrowerDetailList(pager, map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return SUCCESS;
	}
	
	/**
	 * 借款人-逾期项目
	 * @return
	 */
	@Action(value="/borrow/userBorrowLate",
			results={@Result(name="success", location="/content/borrow/user_borrow_late_list.ftl", type="freemarker"),
					@Result(name="fail", location="/content/user/login.ftl", type="freemarker")})
	public String userBorrowLate() {
		
		if (getLoginUser().getTypeId().intValue() == 0) {
			addActionError("只有借款人才能访问！");
			return "error_ftl";
		}
		
		if(pager == null){
			pager = new Pager();
		}
		borrStatus = 3;
		User user = this.getLoginUser();
		Map<String,Object> qMap = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(minDate) && StringUtils.isNotEmpty(maxDate)){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			Date min;
			Date max; 
			try {
				min = simple.parse(minDate);
				max = simple.parse(maxDate);
			} catch (ParseException e) {
				addActionError("参数错误");
				return "error_ftl";
			}
			minDate = simple.format(min);
			maxDate = simple.format(max);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(max);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)-1);
			qMap.put("minDate", min);
			qMap.put("maxDate", calendar.getTime());
		}
		qMap.put("userId", user.getId());
		qMap.put("keywordsCode", keywords);	
		qMap.put("orderBy", " verify_time desc");
		qMap.put("lateStatus", 2);
		
		
		pager = this.getBorrowService().queryBorrowListForRepay(pager,qMap);
		
		return SUCCESS;
		
	}
	
	/**
	 *borrowRepayment 查询出还款列表
	 *@author zhanf 
	 * @return
	 */
	@Action(value="/borrow/userBorrowLateDetail",results={@Result(name="success", location="/content/borrow/user_borrow_late_detail.ftl", type="freemarker")})
	public String userBorrowLateDetail(){
		
		if (getLoginUser().getTypeId().intValue() == 0) {
			addActionError("只有借款人才能访问！");
			return "error_ftl";
		}
		
		borrow = this.getBorrowService().getBorrowById(borrow.getId());
//		if (borrow.getLateStatus()==null || borrow.getLateStatus()!=2) {
//			addActionError("只有项目逾期才能访问！");
//			return "error_ftl";
//		}
		
		if (borrow.getUserId().intValue() != getLoginUser().getId().intValue()) {
			addActionError("只能查看自己的标！");
			return "error_ftl";
		}
		
		
		borrowRepDetailList= this.borrowRepaymentDetailService.queryUserBorrowList(borrow.getId());
		
		countDown = 0;
		for(BorrowRepaymentDetail bean :borrowRepDetailList) {
			if ( bean.getStatus()==-1) {
				countDown ++;
			}
		}
		
		if (borrow.getStatus()==7) {
			borrStatus = 7;
		} else {
			borrStatus = 3;
		}
		
		return "success";
	}
	
	
	/**
	 * 撤回未审核发布的标
	 * @return
	 */
	@Action(value="/borrow/ajaxBorrowRecall",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String ajaxBorrowRecall(){
		log.info("撤回未审核发布的标");
		try{
			
		Boolean flag=false;
		Borrow  borrow1 = borrowService.getBorrowById(borrow.getId());
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("agencyid", borrow1.getAgencyId());
		List<User> ulist = userService.queryListByMap(map);
		if(ulist.size()<1){
			return ajax(Status.error,"程序错误!");
		}
		if (ulist.get(0).getId().intValue()  != getLoginUser().getId().intValue()) {
		
			return ajax(Status.error,"非借款者本人!");
		}
		
		if(borrow1.getStatus()==0){
			flag=true;
		}
		if(flag){
			borrow1.setStatus(6);
			this.borrowService.updateBorrowMess(borrow1);
			return ajax(Status.success,"撤回成功!");
		}else{
			return ajax(Status.error,"此借款状态发生改变!");
		}
		}catch(Exception e) {
			e.printStackTrace();
			return ajax(Status.error,"参数错误!");
		}
	}
	
	
	/**
	 * 删除不成功的标
	 * @return
	 */
	@Action(value="/borrow/ajaxDelectBorrow",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String ajaxDelectBorrow(){
		log.info("删除不成功的标");
		Boolean flag=false;
		Borrow  borrow1 = borrowService.getBorrowById(borrow.getId());
		if(borrow1.getStatus()==6){
			flag=true;
		}
		if(flag){
			borrow1.setStatus(8);
			this.borrowService.updateBorrowMess(borrow1);
//			this.borrowService.delectBorrow(borrow.getId());
			return ajax(Status.success,"删除成功!");
		}else{
			return ajax(Status.error,"参数错误!");
		}
	}
	
	
	
	
	public BorrowInvestReady getBorrowInvestReady() {
		return borrowInvestReady;
	}
	public void setBorrowInvestReady(BorrowInvestReady borrowInvestReady) {
		this.borrowInvestReady = borrowInvestReady;
	}
	public String getBnType() {
		return bnType;
	}
	public void setBnType(String bnType) {
		this.bnType = bnType;
	}
	public String getBnsType() {
		return bnsType;
	}
	public void setBnsType(String bnsType) {
		this.bnsType = bnsType;
	}
	public Double getWanderPieceInterest() {
		return wanderPieceInterest;
	}
	public void setWanderPieceInterest(Double wanderPieceInterest) {
		this.wanderPieceInterest = wanderPieceInterest;
	}
	public Date getThisDate() {
		return thisDate;
	}
	public void setThisDate(Date thisDate) {
		this.thisDate = thisDate;
	}
	public Date getBackDate() {
		return backDate;
	}
	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}
	public String getAccountUpper() {
		return accountUpper;
	}
	public void setAccountUpper(String accountUpper) {
		this.accountUpper = accountUpper;
	}
	public String getLowestMoney() {
		return lowestMoney;
	}
	public void setLowestMoney(String lowestMoney) {
		this.lowestMoney = lowestMoney;
	}
	public String getBorImageFirst() {
		return borImageFirst;
	}
	public void setBorImageFirst(String borImageFirst) {
		this.borImageFirst = borImageFirst;
	}
	public String[] getVouchers() {
		return vouchers;
	}
	public void setVouchers(String[] vouchers) {
		this.vouchers = vouchers;
	}
	public String[] getVouchersTitle() {
		return vouchersTitle;
	}
	public void setVouchersTitle(String[] vouchersTitle) {
		this.vouchersTitle = vouchersTitle;
	}
	public RateStep[] getRateSteps() {
		return rateSteps;
	}
	public void setRateSteps(RateStep[] rateSteps) {
		this.rateSteps = rateSteps;
	}

	public Agency getAgency() {
		return agency;
	}
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	public NoteImg[] getVoucherImgList() {
		return voucherImgList;
	}
	public void setVoucherImgList(NoteImg[] voucherImgList) {
		this.voucherImgList = voucherImgList;
	}
	public BigDecimal getUserTasteMoney() {
		return userTasteMoney;
	}
	public void setUserTasteMoney(BigDecimal userTasteMoney) {
		this.userTasteMoney = userTasteMoney;
	}
	public int getShowAblePiece() {
		return showAblePiece;
	}
	public void setShowAblePiece(int showAblePiece) {
		this.showAblePiece = showAblePiece;
	}
	public int getShowTastePiece() {
		return showTastePiece;
	}
	public void setShowTastePiece(int showTastePiece) {
		this.showTastePiece = showTastePiece;
	}
	public Date getFbendDate() {
		return fbendDate;
	}
	public void setFbendDate(Date fbendDate) {
		this.fbendDate = fbendDate;
	}
	public Date getZropenDate() {
		return zropenDate;
	}
	public void setZropenDate(Date zropenDate) {
		this.zropenDate = zropenDate;
	}
	public String getSubjectdays() {
		return subjectdays;
	}
	public void setSubjectdays(String subjectdays) {
		this.subjectdays = subjectdays;
	}
	public String getSearchBy() {
		return searchBy;
	}
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	public String getBty() {
		return bty;
	}
	public void setBty(String bty) {
		this.bty = bty;
	}
	
	
}
