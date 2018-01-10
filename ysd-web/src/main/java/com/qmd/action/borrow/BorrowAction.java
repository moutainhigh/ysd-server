package com.qmd.action.borrow;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.mode.borrow.BorCompany;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.*;
import com.qmd.mode.util.Listing;
import com.qmd.mode.util.MailRepayForInvestor;
import com.qmd.mode.util.NodeBean;
import com.qmd.service.borrow.*;
import com.qmd.service.user.*;
import com.qmd.service.util.ListingService;
import com.qmd.util.*;
import com.qmd.util.bean.CarInfoJson;
import com.qmd.util.bean.RepaymentInfo;
import net.qmdboss.util.RedPackUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
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

@InterceptorRefs({ @InterceptorRef(value = "userVerifyInterceptor"),
		@InterceptorRef(value = "qmdDefaultStack") })
@Service("borrowAction")
public class BorrowAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(BorrowAction.class);

	private Logger tenderLog = Logger.getLogger("userTenderLog");
	/**
	 * 获取标列表
	 * 
	 */
	// Pager page;
	private String mycode;// 验证码
	private String flag;// 标记
	private Map<String, String> parameterMap;// 参数
	private String minDate;// 起始时间
	private String maxDate;// 截止时间
	private String keywords;
	private String status;// 借款还款状态
	private String useReason;
	private String timeLimit;
	private String accountStart;
	private String accountEnd;
	private String fundsStart;
	private String fundsEnd;
	private String orderBy;
	public String bType;
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
	private List<NoteImgList> voucherImgList;
	private List<NoteImg> carImgList;
	private CarInfoJson carInfo;
	private VerifyMessJson verifyMessJson;
	private String borImageFirst;
	private String typeName;// 获取的标类型名称
	private String effectiveTime;// 获取的截止日名称
	private String repayMes;// 还款设置的天数和每期还款金额
	private Integer borrStatus;
	private String strMess = "";// 修改标时返回的还款记录
	private BigDecimal userRechargeMoney = BigDecimal.ZERO;//用户应充值的钱
	private BigDecimal userAbleMoney;
	private BigDecimal userTasteMoney;
	private BigDecimal continueTotal;
	private int borrowType;
	private int borrowIsday;
	private List<NoteImg> borrowImgList;
	private int showContinueTotal;
	private int showUserAbleMoney;
	private int showUserTasteMoney;
	private int maxWanderPiece;
	private int minWanderPiece;
	private int maxWanderPieceContinue;
	private int minWanderPieceContinue;
	private Integer wanderPlan;
	private List<NodeBean> nodeList;
	Map<String, Object> root = new HashMap<String, Object>();
	List<BorrowRepaymentDetail> borrowRepDetailList;
	private MonthPaymentDetail[] monthPaymentList;
	private String dxpwd;// 定向密码
	private String safepwd;// 交易密码
	private Date nowDate;
	private int countDown;
	private String agreementId;// 协议号
	private String lastRdate;// 最后还款标完成日期
	private String dayFlg;
	private PledgeRepayPlanDetail pledgeRepayPlanDetail;
	private MonthRepayPlanDetail monthRepayPlanDetail;
	private WanderRepayPlanDetail wanderRepayPlanDetail;
	private UserInfo userInfo;
	private String agreement;// 判断是否出现借款协议
	private String lowestMoney;// 最小投资金额

	private String tenderMoney;
	private String tenderMoneyContinue;
	private BigDecimal dqsy;//到期收益
	private Integer[] hongbao;//红包ID数组
	
	private String maxUsername;//投资土豪-用户名
	private String maxMoney;//投资土豪-投资金额
	
	User user;
	BorrowRepaymentDetail borrowRepaymentDetail;
	List <UserRepaymentDetail> userRepDetailList;
	private List<BorrowRepaymentDetail> borrowRepaymentDetailList;
	List<BorrowTender> borrowTenderList;
	List<Borrow> userBorrowList;
	List<UserRepaymentDetail> userRepayDetailList;
	BorCompany borCompany;
	BorrowTender borrowTender;
	private PaymentView retBorrow;
	private String isDxb1;

	private String[] vouchers;
	private String[] vouchersTitle;

	private BigDecimal tasteMoney;
	private BigDecimal tenderAbleMoney;
	private BigDecimal tenderContinueMoney;

	private RepaymentInfo repaymentInfo;
	private CarInfoJson carInfoJson;
	private List<AccountBank> accountBankList;
	
	private Integer t;
	private Integer r;
	private Integer l;
	private Integer p;
	private String bq;
	private Date reTime;
	private String type;
	private Integer tenderNum;
	private Integer realStatus;
	private Integer noPayPsw;
	private BigDecimal gains;//总收益
	private User borrowUser;
	private HongbaoBestBen hbbb;
	
	public HongbaoBestBen getHbbb() {
		return hbbb;
	}

	public void setHbbb(HongbaoBestBen hbbb) {
		this.hbbb = hbbb;
	}

	/** @author zdl -start- 项目中心查询条件  **/
	private String qbtype = "0";//	项目类型(0为全部)：14 普通标（以前叫天标），16 新手标
	private String qbstatus ="0";// 项目状态(0为全部)：1-投标中{1,5}； 2-还款中{3}； 3-项目结束{7}
	private String qblimit = "0";// 项目期限(0为全部) 1：0~1个月(<=30)；2：1~3个月(>=30&&<=90)；3：3~6个月(>=90&&<=180)；4：6~12个月(>=180&&<=360)；5：12个月以上(>=360)
	/** @author zdl -end- 项目中心查询条件 **/
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Resource
	UserHongbaoService userHongbaoService;
	@Resource
	BorrowService borrowService;
	@Resource
	BorrowSecondService borrowSecondService;
	@Resource
	BorrowWanderService borrowWanderService;
	// CalculationUtil cal;
	@Resource
	UserAccountService userAccountService;
	@Resource
	BorrowTenderService borrowTenderService;
	@Resource
	UserService userService;
	@Resource
	UserAccountDetailService userAccountDetailService;
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
	BorrowPromoteService borrowPromoteService;
	@Resource
	BorrowTasteService borrowTasteService;
	
	@Action(value = "/borrow/list", results = {
			@Result(name = "success", location = "/content/borrow/list.ftl", type = "freemarker"),
			@Result(name = "fail", location = "/content/user/login.ftl", type = "freemarker") })
	public String newBorrowList() throws Exception {
		if (pager == null) {
			pager = new Pager();
		}
		try {
			Map<String, Object> qMap = new HashMap<String, Object>();

			// qMap.put("userId", 447);
//			String str;
//			if (StringUtils.isNotEmpty(keywords)) {
//				str = CommonUtil.changeChar(keywords);
//			} else {
//				str = keywords;
//			}
//
//			qMap.put("keywords", str);
			
			bq = "";
			
//			if (t!=null && t>0 && t< ConstantUtil.LIST_QUERY_ACCOUNT.length) {
//				qMap.put("varyAccountStart", ConstantUtil.LIST_QUERY_ACCOUNT[t][0]);
//				qMap.put("varyAccountEnd",  ConstantUtil.LIST_QUERY_ACCOUNT[t][1]);
//				
//				bq+="t="+t+"&";
//			}
//			
//			if (r!=null &&r>0 && r< ConstantUtil.LIST_QUERY_Rate.length) {
//				qMap.put("varyYearRateStart", ConstantUtil.LIST_QUERY_Rate[r][0]);
//				qMap.put("varyYearRateEnd",  ConstantUtil.LIST_QUERY_Rate[r][1]);
//				bq+="r="+r+"&";
//			}
//			
//			if (l!=null &&l>0 && l< ConstantUtil.LIST_QUERY_LIMIT.length) {
//				qMap.put("varyMonthLimitStart", ConstantUtil.LIST_QUERY_LIMIT[l][0]);
//				qMap.put("varyMonthLimitEnd",  ConstantUtil.LIST_QUERY_LIMIT[l][1]);
//				bq+="l="+l+"&";
//			}
			
			if (p!=null &&p>0) {
				if(p.compareTo(17)==0){
					qMap.put("bType", p);
				}else{
					qMap.put("isVouch", p);
				}
				bq+="p="+p+"&";
			} 
//				int[] array ={1, 3, 4 ,5, 7};
				int[] array ={1, 3, 5, 7};
				qMap.put("array", array);
			
//			
//			if ("1".equals(getbStarType())) {// 进行中：1初审通过，3满标审核通过，5等待满标审核
//				int[] array = { 1, 3, 5 };
//				qMap.put("array", array);
//			} else if ("2".equals(getbStarType())) {// 已完成：4满标审核未通过，6过期或撤回，7已完成
//				int[] array = { 4, 7 };
//				qMap.put("array", array);
//			}

			// qMap.put("bType", this.getbType());
			qMap.put("orderBy", " b.status asc,b.type desc, CAST(b.schedule as SIGNED) asc ,b.modify_date desc "); 
			pager.setPageSize(8);
			pager = this.getBorrowService().queryBorrowList(pager, qMap);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", 0);
			map.put("end", 20);
			borrowTenderList = this.borrowTenderService.queryListByMap(map);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw e;
		}

		return SUCCESS;
	}

	/**
	 * 新的 项目列表
	 * @return
	 * @throws Exception
	 * @author zdl
	 */
	@Action(value = "/borrow/listNew", results = {
			@Result(name = "success", location = "/content/borrow/list.ftl", type = "freemarker"),
			@Result(name = "fail", location = "/content/user/login.ftl", type = "freemarker") })
	public String xBorrowList() throws Exception {
		if (pager == null) {
			pager = new Pager();
		}
		try {
			Map<String, Object> qMap = new HashMap<String, Object>();

			//项目类型
			if(StringUtils.equals(qbtype, "14")){
				qMap.put("bType", "14");
			}else if(StringUtils.equals(qbtype, "16")){
				qMap.put("bType", "16");
			}
			//项目状态
			int[] array = {1, 3, 5, 7};
			if(StringUtils.equals(qbstatus, "1")){
				array =  new int[]{1,5};
			}else if(StringUtils.equals(qbstatus, "2")){
				array = new int[]{3};
			}else if(StringUtils.equals(qbstatus, "3")){
				array = new int[]{7};
			}
			qMap.put("array", array);
			//项目期限(空为全部) 1：0~1个月(<=30)；2：1~3个月(>=30&&<=90)；3：3~6个月(>=90&&<=180)；4：6~12个月(>=180&&<=360)；5：12个月以上(>=360)
			if(StringUtils.equals(qblimit, "1")){
				qMap.put("timeLimitEnd", 30);
			}else if(StringUtils.equals(qblimit, "2")){
				qMap.put("timeLimitStart", 30);
				qMap.put("timeLimitEnd", 90);
			}else if(StringUtils.equals(qblimit, "3")){
				qMap.put("timeLimitStart", 90);
				qMap.put("timeLimitEnd", 180);
			}else if(StringUtils.equals(qblimit, "4")){
				qMap.put("timeLimitStart", 180);
				qMap.put("timeLimitEnd", 360);
			}else if(StringUtils.equals(qblimit, "5")){
				qMap.put("timeLimitStart", 360);
			}
//			qMap.put("orderBy", " b.status asc,b.type desc, CAST(b.schedule as SIGNED) asc ,b.modify_date desc "); 
			qMap.put("orderBy", " b.status <> 1 , b.type desc, b.verify_time desc"); 
			pager.setPageSize(9);
			pager = this.getBorrowService().queryBorrowList(pager, qMap);
			//投资记录
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", 0);
			map.put("end", 20);
			borrowTenderList = this.borrowTenderService.queryListByMap(map);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw e;
		}

		return SUCCESS;
	}

	@Action(value = "/borrow/detail", results = {
			@Result(name = "success", location = "/content/borrow/borrowDetail.ftl", type = "freemarker"),
			@Result(name = "wander", location = "/content/borrow/borrowDetailWander.ftl", type = "freemarker"),
			@Result(name = "flow", location = "/content/borrow/borrowDetailFlow.ftl", type = "freemarker"),
			@Result(name = "promote", location = "/content/borrow/borrowDetailPromote.ftl", type = "freemarker"),
			@Result(name = "raise", location = "/content/borrow/borrowDetailRaise.ftl", type = "freemarker"),
			@Result(name = "noBorrowExist", location = "/content/message_page.ftl", type = "freemarker") })
	// @Action(value="/borrow/detail",results={@Result(name="success",
	// location="invest.do?bId=${bId}",
	// type="redirect"),@Result(name="noBorrowExist",
	// location="/content/borrow/noBorrowExist.ftl", type="freemarker")})
	public String borrowDetail() throws Exception {

		try {
			borrow = this.borrowService.getBorrowById(this.getbId());
			if (borrow == null) {
				msg = "此标不存在！";
				return "noBorrowExist";
			}
			if (borrow.getStatus() == 0) {
				msg = "此标未发布！";
				return "noBorrowExist";
			}
			if (borrow.getValidTime() == null
					|| "".equals(borrow.getValidTime())) {
				borrow.setValidTime("1");// 有效天数不存在是默认设为一天
			}

			nowDate = new Date();

			// 设置过期时间
			if(borrow.getVerifyTime()!=null){
				Calendar c = Calendar.getInstance();
				c.setTime(borrow.getVerifyTime());
				c.set(Calendar.DAY_OF_MONTH,
						c.get(Calendar.DAY_OF_MONTH)
								+ Integer.valueOf(borrow.getValidTime()));
				borrow.setOverDate(c.getTime());
			}


			if (nowDate.getTime() > borrow.getOverDate().getTime()) {// 已经过期
				countDown = -1;
			} else if (borrow.getBalance() != null
					&& Double.valueOf(borrow.getBalance()) == 0) {// 没有剩余金额（满标）
				countDown = 0;
			} else {// 需要显示倒计时
				countDown = 1;
			}

			if (borrow.getStatus() != 1) {
				countDown = 0;
			}

			Map<String, Object> uMap = new HashMap<String, Object>();
			uMap.put("id", borrow.getUserId());
			user = userService.getUser(uMap);// 借款者信息取得
			Map<String, Object> bMap = new HashMap<String, Object>();
			bMap.put("userId", user.getId());
			int[] array = { 3 };
			bMap.put("array", array);

			userInfo = userinfoService.findByUserId(borrow.getUserId());// 借款人详情信息取得

			userBorrowList = this.getBorrowService().queryUserBorrowList(bMap);// 借款者待还记录取得
			boolean flag = false;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("borrowId", borrow.getId());
			pager = this.borrowTenderService.queryPagerListByMap(pager, map);
			
			User user1 = getLoginUser();
		
			borrowTenderList = this.borrowTenderService
					.getBorrowTenderByBorrowId(borrow.getId());// 当前标的投标记录取得
			tenderNum = 0;
			if(borrowTenderList != null && borrowTenderList.size() >0){
				tenderNum=borrowTenderList.size();
				Map<String, Object> maxMap = new HashMap<String, Object>();
				maxMap.put("borrowId", borrow.getId());
				List<BorrowTender> bt = this.borrowTenderService.getTenderDetailByMaxMoney(maxMap);
				maxUsername=bt.get(0).getUsername();
				maxMoney=bt.get(0).getAccount();
			}
			
//			if (user1 != null && borrowTenderList.size() > 0) {
//				if ((borrow.getStatus() == 3 || borrow.getStatus() == 7)
//						&& ("1".equals(borrow.getType()) || "4".equals(borrow
//								.getType()))) {
//					flag = true;
//				} else if (!borrow.getShowBalanceSize().equals(
//						borrow.getWanderPieceSize())
//						&& "2".equals(borrow.getType())) {
//					flag = true;
//				}
//				if (flag) {
//					for (BorrowTender borrowTender : borrowTenderList) {
//						if (user1.getId().equals(borrowTender.getUserId())
//								|| user1.getId().equals(borrow.getUserId())) {
//							agreement = "0";
//						}
//					}
//				}
//			}
//			if ("0".equals(borrow.getType())) {// 秒标 情况下，利息收益为投资金额的一天利息
//				this.setInterest(String.valueOf(borrow.getApr() * 10));
//			} else if ("1".equals(borrow.getType())) {// 质押标情况下，利息为100
//				this.setInterest(interestCalUtil.payback(100d,
//						Double.valueOf(borrow.getAccount()), borrow.getApr(),
//						30, borrow.getBorStages(), 2).getTotalLiXi());
//			} else if ("4".equals(borrow.getType())) {// 质押标情况下，利息为100
//				this.setInterest(interestCalUtil.payback(100d,
//						Double.valueOf(borrow.getAccount()), borrow.getApr(),
//						30, borrow.getBorStages(), 1).getTotalLiXi());
//			} else if ("2".equals(borrow.getType())) {
//				this.setInterest(String.valueOf(borrow.getApr() * 30));
//			}

			carImgList = new ArrayList<NoteImg>();

			String carImg = borrow.getBorImage();
			if (carImg != null && !"".equals(carImg.trim())) {

				try {
					JSONArray jsonarray = JSONArray.fromObject(carImg);
					// System.out.println(jsonarray);
					carImgList = (List<NoteImg>) JSONArray.toList(
							jsonarray, NoteImg.class);
				} catch (JSONException je) {
					NoteImg ni = new NoteImg();
					ni.setName("");
					ni.setUrl(carImg);
					carImgList.add(ni);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			if (user1 != null && user1.getUsername() != null
					&& user1.getTypeId() != null && user1.getTypeId() == 0) {
				UserAccount userAccount = this.userAccountService
						.getUserAccountByUserId(user1.getId());
				userAbleMoney = userAccount.getAbleMoney();
				continueTotal = userAccount.getContinueTotal();
				tasteMoney = userAccount.getTasteMoney();
				if (userAbleMoney != null)
					showUserAbleMoney = ((int) userAbleMoney.intValue() / 100) * 100;
				else
					showUserAbleMoney = 0;
				showContinueTotal = 0;
				if ("0".equals(borrow.getIsday()) && continueTotal != null
						&& continueTotal.longValue() > 100) {
					showContinueTotal = (int) (continueTotal.longValue() / 100);
					showContinueTotal = showContinueTotal * 100;
					if (Double.valueOf(borrow.getBalance())<showContinueTotal) {
						showContinueTotal = Double.valueOf(borrow.getBalance()).intValue();
						showContinueTotal = (int) (showContinueTotal/ 100);
						showContinueTotal = showContinueTotal * 100;
					}
					if (StringUtils.isNotEmpty(borrow.getMostAccount())&&!"0".equals(borrow.getMostAccount())&&Double.valueOf(borrow.getMostAccount()) < showContinueTotal) {
						showContinueTotal=Double.valueOf(borrow.getMostAccount()).intValue();
						showContinueTotal = (int) (showContinueTotal/100);
						showContinueTotal = showContinueTotal * 100;
					}
				}
				if ((Double.valueOf(borrow.getBalance())-showContinueTotal)<showUserAbleMoney) {
					showUserAbleMoney =(int)(Double.valueOf(borrow.getBalance())-showContinueTotal);
					showUserAbleMoney = (int) (showUserAbleMoney/ 100);
					showUserAbleMoney = showUserAbleMoney * 100;
				}
				
				if (StringUtils.isNotEmpty(borrow.getMostAccount())&&!"0".equals(borrow.getMostAccount())&&(Double.valueOf(borrow.getMostAccount())-showContinueTotal) < showUserAbleMoney) {
					showUserAbleMoney=(int)(Double.valueOf(borrow.getMostAccount())-showContinueTotal);
					showUserAbleMoney = (int) (showUserAbleMoney/100);
					showUserAbleMoney = showUserAbleMoney * 100;
				}
				
			} else {
				userAbleMoney = BigDecimal.ZERO;
				continueTotal = BigDecimal.ZERO;
				showContinueTotal = 0;
				showContinueTotal = 0;
			}
//
//			if ("1".equals(borrow.getType())) {
//				InterestCalUtil rp = new InterestCalUtil();
//				retBorrow = rp.payback(Double.valueOf(borrow.getAccount()), // 计算值
//						Double.valueOf(borrow.getAccount()), // 标的总额
//						borrow.getApr(), // 天利率
//						Integer.valueOf(borrow.getTimeLimit()), // 借款期限（天）
//						borrow.getBorStages(), 2);// 还款计划
//				pledgeRepayPlanDetail = new PledgeRepayPlanDetail(borrow);
//			} else if ("4".equals(borrow.getType())) {
//				InterestCalUtil rp = new InterestCalUtil();
//				retBorrow = rp.payback(Double.valueOf(borrow.getAccount()), // 计算值
//						Double.valueOf(borrow.getAccount()), // 标的总额
//						borrow.getApr(), // 年利率
//						Integer.valueOf(borrow.getTimeLimit()), // 借款期限（月）
//						borrow.getBorStages(), 1);// 还款计划
//				monthRepayPlanDetail = new MonthRepayPlanDetail(borrow);
//			} else if ("2".equals(borrow.getType())) {
//				wanderRepayPlanDetail = new WanderRepayPlanDetail(borrow);
//
//				for (BorrowTender borrowTender : borrowTenderList) {
//					double dd = borrow.getWanderPieceMoney() == null ? 0
//							: borrow.getWanderPieceMoney().doubleValue();
//					if (dd == 0) {
//						dd = 1;
//					}
//
//					int wp = (int) (Double.valueOf(borrowTender.getAccount()) / dd);
//
//					borrowTender.setWanderPiece(String.valueOf(wp));
//				}
//
//				return "wander";
//			} else if ("5".equals(borrow.getType())) {
//
//				for (BorrowTender borrowTender : borrowTenderList) {
//					double dd = borrow.getWanderPieceMoney() == null ? 0
//							: borrow.getWanderPieceMoney().doubleValue();
//					if (dd == 0) {
//						dd = 1;
//					}
//
//					int wp = (int) (Double.valueOf(borrowTender.getAccount()) / dd);
//
//					borrowTender.setWanderPiece(String.valueOf(wp));
//				}
//
//				lowestMoney = CommonUtil.setPriceScale(Integer.parseInt(borrow
//						.getLowestAccount())
//						* borrow.getWanderPieceMoney().doubleValue());// 最小投资金额
//
//				return "flow";
//			} else 
			if ("11".equals(borrow.getType())
					|| "12".equals(borrow.getType())
					|| "13".equals(borrow.getType())) {

				repaymentInfo = PromoteUtil.promotePlan(Integer.parseInt(borrow
						.getIsday()), Integer.parseInt(borrow.getStyle()),
						Integer.parseInt(borrow.getTimeLimit()), borrow
								.getDivides(),
						new BigDecimal(borrow.getAccount()), new BigDecimal(
								borrow.getApr()));

				// borrowRepaymentDetailList =
				// borrowRepaymentDetail.getBorrowRepaymentDetailList();

				return "promote";
			}else if ("14".equals(borrow.getType())
					|| "15".equals(borrow.getType())|| "16".equals(borrow.getType())|| "17".equals(borrow.getType())
					) {

				repaymentInfo = PromoteUtil.promotePlan(Integer.parseInt(borrow
						.getIsday()), Integer.parseInt(borrow.getStyle()),
						Integer.parseInt(borrow.getTimeLimit()), borrow
								.getDivides(),
						new BigDecimal(borrow.getAccount()), new BigDecimal(
								borrow.getApr()));
				if (borrow.getIsday()!= "0") {
						gains =new BigDecimal( Double.valueOf(borrow.getAccount())*(borrow.getApr()/1000)*Double.valueOf(borrow.getTimeLimit()));
				}
				// borrowRepaymentDetailList =
				// borrowRepaymentDetail.getBorrowRepaymentDetailList();
				String carInfoMes = borrow.getBorrowInfoJson();
				if (carInfoMes != null && !"".equals(carInfoMes.trim())) {

					try {
						JSONObject  jsonObject = JSONObject.fromObject(carInfoMes);
						carInfo = (CarInfoJson) JSONObject.toBean(jsonObject, CarInfoJson.class);
					} catch (JSONException je) {
						carInfo  = new CarInfoJson();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				String verifyInfo = borrow.getBorrowVerifyJson();
				if (verifyInfo != null && !"".equals(verifyInfo.trim())) {

					try {
						JSONObject  jsonObject = JSONObject.fromObject(verifyInfo);
						verifyMessJson = (VerifyMessJson) JSONObject.toBean(jsonObject, VerifyMessJson.class);
					} catch (JSONException je) {
						verifyMessJson  = new VerifyMessJson();
					} catch (Exception e) {
						e.printStackTrace();
					}

					// borImageList = tmp.split(",");
					// for(String vcimg:borImageList) {
					// //voucherImgList.add(vcimg);
					// }
				}
				
				return "raise";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;

	}

	@Action(value = "/borrow/wanderPlan", results = {
			@Result(name = "success", location = "/content/borrow/poputWanderPlan.ftl", type = "freemarker"),
			@Result(name = "error", location = "/content/borrow/noBorrowExist.ftl", type = "freemarker") })
	public String poputWanderPlan() {

		borrow = this.borrowService.getBorrowById(this.getbId());
		if (borrow == null) {
			return ERROR;
		}

		// nodeList = WanderUtil.fillWanderPlan(borrow.getWanderStages());
		wanderRepayPlanDetail = new WanderRepayPlanDetail(borrow);
		List<WanderRepayPlanEach> eachList = new ArrayList<WanderRepayPlanEach>();
		for (WanderRepayPlanEach each : wanderRepayPlanDetail
				.getWanderRepayPlanEach()) {
			if (each.isClickFlg()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderNum", each.getIssue());
				map.put("borrowId", borrow.getId());
				BorrowRepaymentDetail borrowRepaymentDetail1 = this.borrowRepaymentDetailService
						.queryBorrowRepayment(map);
				if (borrowRepaymentDetail1.getStatus() == 1) {
					each.setClickFlg(false);
				}
			}
			eachList.add(each);
		}
		wanderRepayPlanDetail.setWanderRepayPlanEach(eachList);

		user = this.getLoginUser();
		UserAccount userAccount = this.userAccountService
				.getUserAccountByUserId(user.getId());
		userAbleMoney = userAccount.getAbleMoney();
		continueTotal = userAccount.getContinueTotal();
		// minWanderPiece =
		// borrow.getLowestAccount()==null?0:Integer.valueOf(borrow.getLowestAccount());
		minWanderPiece = 0;
		maxWanderPiece = WanderUtil.maxWanderPiece(borrow, userAbleMoney);
		if (maxWanderPiece < minWanderPiece) {
			maxWanderPiece = minWanderPiece;
		}
		minWanderPieceContinue = 0;
		maxWanderPieceContinue = WanderUtil.maxWanderPiece(borrow,
				continueTotal);
		if (maxWanderPieceContinue < minWanderPieceContinue) {
			maxWanderPieceContinue = minWanderPieceContinue;
		}

		return SUCCESS;
	}

	/**
	 * 投标按钮按下（流转标）
	 * 
	 * @return
	 */
	@Action(value = "/borrow/poputInvestWanderDo", results = { @Result(name = "success", params = {
			"root", "root" }, type = "json") })
	public String poputInvestWanderDo() {

		try {
			if (tenderMoney == null || "".equals(tenderMoney.trim())) {
				this.setErrorMsg("认购份数不能空");
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}
			if (!this.isNumeric(tenderMoney)) {
				this.setErrorMsg("认购份数必须为整数数字");
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}
			if (tenderMoneyContinue == null
					|| "".equals(tenderMoneyContinue.trim())) {
				this.setErrorMsg("续投份数不能空");
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}
			if (!this.isNumeric(tenderMoneyContinue)) {
				this.setErrorMsg("续投份数必须为整数数字");
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}

			user = this.getLoginUser();
			if (user == null) {
				this.setErrorMsg("请登录！");
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}

			String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
			removeSession(ConstantUtil.RANDOM_COOKIE_NAME);
			if (this.getMycode() == null || !this.getMycode().equalsIgnoreCase(random)) {
				this.setErrorMsg("验证码输入错误!");
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}

			UserAccount userAccount = this.userAccountService
					.getUserAccountByUserId(user.getId());
			borrow = this.borrowService.getBorrowById(bId);

			if ("1".equals(borrow.getIsDxb())) {
				if (dxpwd == null || "".equals(dxpwd)) {
					errorMsg = "请输入定向密码";
					root.put("errorMsg", this.getErrorMsg());
					return SUCCESS;
				}
				if (!dxpwd.equals(borrow.getPwd())) {
					errorMsg = "定向密码不正确";
					root.put("errorMsg", this.getErrorMsg());
					return SUCCESS;
				}
			}

			if (safepwd == null || "".equals(safepwd)) {
				errorMsg = "请输入交易密码";
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}

			// 验证交易密码
			boolean isSavePwd = userService.isPassword(user.getUsername(),
					safepwd, "1");
			if (!isSavePwd) {
				errorMsg = "交易密码不正确";
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}

			// 验证是否是借款人自己投标
			if (user.getId().intValue() == borrow.getUserId().intValue()) {
				errorMsg = "你是借款人，不能自己投标";
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}

			double thisTenderMoney = borrow.getWanderPieceMoney().doubleValue()
					* Double.valueOf(tenderMoney);
			double thisTenderMoneyContinue = borrow.getWanderPieceMoney()
					.doubleValue() * Double.valueOf(tenderMoneyContinue);

			// 投标金额大于可用金额
			if (userAccount.getAbleMoney().doubleValue() < thisTenderMoney) {
				errorMsg = "可用余额不足";
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}
			// 续投金额大于可用金额
			if (userAccount.getContinueTotal().doubleValue() < thisTenderMoneyContinue) {
				errorMsg = "续投余额不足";
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}
			// 投标额度是否小于最小投资金额
			if ((Double.valueOf(tenderMoney) + Double
					.valueOf(tenderMoneyContinue)) < Double.valueOf(borrow
					.getLowestAccount())) {
				errorMsg = "投标份数小于最小投标份数";
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}
			// 投标总额大于最大投标额
			if (borrow.getMostAccount() != null
					&& !"".equals(borrow.getMostAccount())
					&& Double.valueOf(borrow.getMostAccount()) > 0) {
				Map<String, Object> tMap = new HashMap<String, Object>();
				tMap.put("userId", user.getId());
				tMap.put("borrowId", borrow.getId());
				Object totalAmount = this.borrowTenderService.getByStatementId(
						"BorrowTender.selectAllAccountByUserid", tMap);
				if (totalAmount != null) {
					double tAmount = Double.valueOf(totalAmount.toString());
					tAmount = tAmount
							/ borrow.getWanderPieceMoney().doubleValue();// 已投标份数
					if ((tAmount + Double.valueOf(tenderMoney) + Double
							.valueOf(tenderMoneyContinue)) > Double
							.valueOf(borrow.getMostAccount())) {
						errorMsg = "投标总份数大于最大投标份数";
						root.put("errorMsg", this.getErrorMsg());
						return SUCCESS;
					}
				} else if (borrow.getMostAccount() != null
						&& !"".equals(borrow.getMostAccount())
						&& Integer.parseInt(borrow.getMostAccount()) < Integer
								.parseInt(tenderMoney)) {
					errorMsg = "投标总份数大于最大投标份数";
					root.put("errorMsg", this.getErrorMsg());
					return SUCCESS;
				}
			}
			if (Double.valueOf(borrow.getBalance()) <= 0) {
				errorMsg = "此标已满";
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}

			int inret = 1;// 0 投标成功

			if ("0".equals(borrow.getType())) {// 秒标
				inret = this.borrowSecondService.borrowInvestDo(user,
						borrowTender, this.obtainUserIp());
			} else if ("1".equals(borrow.getType())) {// 质押标
				inret = this.borrowService.borrowInvestDo(user, borrowTender,
						this.obtainUserIp());
			} else if ("2".equals(borrow.getType())) {// 流转标
				borrowTender = new BorrowTender();
				borrowTender.setBorrowId(bId);

				// wanderRepayPlanDetail = new WanderRepayPlanDetail(borrow);
				borrowTender.setAbleAmount(CommonUtil
						.setPriceScale(thisTenderMoney));
				borrowTender.setContinueAmount(CommonUtil
						.setPriceScale(thisTenderMoneyContinue));

				borrowTender.setMoney(String.valueOf((Double
						.valueOf(tenderMoney) + Double
						.valueOf(tenderMoneyContinue))
						* borrow.getWanderPieceMoney().doubleValue()));

				inret = this.borrowWanderService.borrowInvestDo(user,
						borrowTender, this.obtainUserIp(), wanderPlan);
			}

			if (inret == 0) {
				root.put("errorMsg", "OK");
			} else if (inret == 1) {
				root.put("errorMsg", "标已满");
			} else if (inret == 2) {
				root.put("errorMsg", "账户余额不足");
			} else {
				root.put("errorMsg", "投标未成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 投标页面弹出
	 * 
	 * @return
	 */
	@Action(value = "/borrow/poputInvest", results = {
			@Result(name = "success", location = "/content/borrow/poputBorrowInvest.ftl", type = "freemarker"),
			@Result(name = "noBorrowExist", location = "/content/borrow/noBorrowExist.ftl", type = "freemarker") })
	public String poputInvest() {

		borrow = this.borrowService.getBorrowById(this.getbId());
		Calendar c = Calendar.getInstance();
		c.setTime(borrow.getVerifyTime());
		c.set(Calendar.DAY_OF_MONTH,
				c.get(Calendar.DAY_OF_MONTH)
						+ Integer.valueOf(borrow.getValidTime()));
		borrow.setOverDate(c.getTime());
		if (borrow == null) {
			return "noBorrowExist";
		}

		if ("0".equals(borrow.getType())) {
			this.setInterest(String.valueOf(Double.valueOf(borrow.getAccount())
					* borrow.getApr() / 36500));
		} else if ("1".equals(borrow.getType())) {
			this.setInterest(interestCalUtil.payback(100d,
					Double.valueOf(borrow.getAccount()), borrow.getApr(), 30,
					borrow.getBorStages(), 2).getTotalLiXi());
		}

		user = this.getLoginUser();
		UserAccount userAccount = this.userAccountService
				.getUserAccountByUserId(user.getId());
		userAbleMoney = userAccount.getAbleMoney();
		continueTotal = userAccount.getContinueTotal();

		if (userAbleMoney != null)
			showUserAbleMoney = userAbleMoney.intValue();
		else
			showUserAbleMoney = 0;
		showContinueTotal = 0;
		if ("4".equals(borrow.getType()) && continueTotal != null
				&& continueTotal.longValue() > 100) {
			showContinueTotal = (int) (continueTotal.longValue() / 100);
			showContinueTotal = showContinueTotal * 100;

		}

		if (borrow.getMostAccount() != null
				&& !"".equals(borrow.getMostAccount())
				&& !"0".equals(borrow.getMostAccount())) {
			int mostMoney = Integer.parseInt(borrow.getMostAccount());

			if (mostMoney > 0) {
				if (mostMoney < showContinueTotal) {
					showContinueTotal = mostMoney;
				}
				mostMoney = mostMoney - showContinueTotal;
			}

			if (showUserAbleMoney > mostMoney) {
				showUserAbleMoney = mostMoney;
			}

		}

		if (showUserAbleMoney < 0) {
			showUserAbleMoney = 0;
		}

		showUserAbleMoney = (int) (showUserAbleMoney / 100);
		showUserAbleMoney = showUserAbleMoney * 100;

		return SUCCESS;
	}

	/**
	 * 点击投资跳转到投资页面-----------------------------【新版】
	 * @return
	 */
	@Action(value = "/borrow/toInvest", results = {
			@Result(name = "success", location = "/content/borrow/borrowDetailRaiseToInvest.ftl", type = "freemarker"),
			@Result(name = "noBorrowExist", location = "/content/message_page.ftl", type = "freemarker") })
	public String toInvest(){

		borrow = this.borrowService.getBorrowById(this.getbId());
		Calendar c = Calendar.getInstance();
		c.setTime(borrow.getVerifyTime());
		c.set(Calendar.DAY_OF_MONTH,
				c.get(Calendar.DAY_OF_MONTH)
						+ Integer.valueOf(borrow.getValidTime()));
		borrow.setOverDate(c.getTime());
		msgUrl="/borrow/detail.do?bId="+borrow.getId();
		if (borrow == null) {
			msg = "此标不存在！";
			return "noBorrowExist";
		}

		if (borrow.getStatus() != 1) {
			msg = "标的状态不正确！";
			return "noBorrowExist";
		}

		if ("0".equals(borrow.getType())) {
			this.setInterest(String.valueOf(Double.valueOf(borrow.getAccount())
					* borrow.getApr() / 36500));
		} else if ("1".equals(borrow.getType())) {
			this.setInterest(interestCalUtil.payback(100d,
					Double.valueOf(borrow.getAccount()), borrow.getApr(), 30,
					borrow.getBorStages(), 2).getTotalLiXi());
		}

		user = this.getLoginUser();
		UserAccount userAccount = this.userAccountService
				.getUserAccountByUserId(user.getId());
		userAbleMoney = userAccount.getAbleMoney();
		userTasteMoney = userAccount.getTasteMoney();
		continueTotal = userAccount.getContinueTotal();

		if (userAbleMoney != null)
			showUserAbleMoney = userAbleMoney.intValue();
		else
			showUserAbleMoney = 0;
		
		if(userTasteMoney != null){
			showUserTasteMoney = userTasteMoney.intValue();
		}else{
			showUserTasteMoney = 0;
		}
		
		showContinueTotal = 0;
		if ("4".equals(borrow.getType()) && continueTotal != null
				&& continueTotal.longValue() > 100) {
			showContinueTotal = (int) (continueTotal.longValue() / 100);
			showContinueTotal = showContinueTotal * 100;

		}

		if (borrow.getMostAccount() != null
				&& !"".equals(borrow.getMostAccount())
				&& !"0".equals(borrow.getMostAccount())) {
			int mostMoney = Integer.parseInt(borrow.getMostAccount());

			if (mostMoney > 0) {
				if (mostMoney < showContinueTotal) {
					showContinueTotal = mostMoney;
				}
				mostMoney = mostMoney - showContinueTotal;
			}

			if (showUserAbleMoney > mostMoney) {
				showUserAbleMoney = mostMoney;
			}
		}
		
		if (tenderAbleMoney != null) {
			if (!this.isNumeric(tenderAbleMoney.toString())) {
				msg = "投标金额必须为整数数字！";
				return "noBorrowExist";
			}
			if (tenderAbleMoney.doubleValue() % 100 != 0) {
				msg = "投标金额必须是100的整数倍！";
				return "noBorrowExist";
			}
			if (tenderAbleMoney.doubleValue() < 0) {
				msg = "投标金额不能小于0！";
				return "noBorrowExist";
			}
		}
		

		// 投标额度是否小于最小投资金额
		if (tenderAbleMoney.doubleValue() < Double.valueOf(borrow
				.getLowestAccount())) {
			msg = "投标金额小于最小投标额";
			return "noBorrowExist";
		}
		
		// 投标总额大于最大投标额
		if (borrow.getMostAccount() != null
				&& !"".equals(borrow.getMostAccount().trim())
				&& !"0".equals(borrow.getMostAccount())) {
			Map<String, Object> tMap = new HashMap<String, Object>();
			tMap.put("userId", user.getId());
			tMap.put("borrowId", borrow.getId());
			Object totalAmount = this.borrowTenderService.getByStatementId(
					"BorrowTender.selectAllAccountByUserid", tMap);
			if (totalAmount != null) {
				double tAmount = Double.valueOf(totalAmount.toString());
				if ((tAmount + tenderAbleMoney.doubleValue()) > Double
						.valueOf(borrow.getMostAccount())) {
					msg = "投标总额大于最大投标额！"+Double.valueOf(borrow.getMostAccount());
					return "noBorrowExist";
				}
			} else if (borrow.getMostAccount() != null
					&& !"".equals(borrow.getMostAccount())
					&& Integer.parseInt(borrow.getMostAccount()) < tenderAbleMoney.doubleValue()) {
				msg = "投标总额大于最大投标额！"+Double.valueOf(borrow.getMostAccount());
				return "noBorrowExist";
			}
		}
		
		if (Double.valueOf(borrow.getBalance()) <= 0) {
			msg = "此标已满！";
			return "noBorrowExist";
		}
		
		// 投标金额大于可用金额
		if(!"17".equals(borrow.getType())){
			if (userAbleMoney.doubleValue() < tenderAbleMoney.doubleValue()) {
				msg = "可用余额不足！";
				return "noBorrowExist";
			}
			
			
			if("16".equals(borrow.getType())){
				Map<String, Object> tMap = new HashMap<String, Object>();
				tMap.put("userId", user.getId());
				tMap.put("backStatus", 0);
				tMap.put("noBorrowType", "17");
				List<BorrowTender> btList = borrowTenderService.getTenderDetailByUserid(tMap);
				if(btList.size() > 0){
					msg = "您已投资过，不符合新手条件！";
					return "noBorrowExist";
				}
			}
			//红包列表-最多12个
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", user.getId());
			map.put("status", 0);
			map.put("isPc", 1);
			map.put("limitStart", Integer.parseInt(borrow.getTimeLimit()));
			map.put("limitEnd", Integer.parseInt(borrow.getTimeLimit()));
			map.put("orderBy", " t.end_time asc ");
			map.put("pageStart", 0);
			map.put("pageSize", 12);
			
			hongbaoList = userHongbaoService.queryListByMap(map);
		}else{
			if (userTasteMoney.doubleValue() < tenderAbleMoney.doubleValue()) {
				msg = "体验金不足！";
				return "noBorrowExist";
			}
		}
		
		//到期收益
		dqsy = BigDecimal.ZERO;
		BigDecimal money = tenderAbleMoney.add(tenderContinueMoney);
		
		if("15".equals(borrow.getType())){
			dqsy = CommonUtil.setPriceScale( money.multiply(new BigDecimal(borrow.getApr())).divide(new BigDecimal(12),2).multiply(new BigDecimal(borrow.getTimeLimit()))) ;
		}else{
			dqsy = CommonUtil.setPriceScale( money.multiply(new BigDecimal(borrow.getApr())).divide(new BigDecimal(1000)).multiply(new BigDecimal(borrow.getTimeLimit())));
		}
		return SUCCESS;
	}
	
	private List<UserHongbao> hongbaoList;
	
	
	/**
	 * 验证码验证
	 * */
	@Action(value = "/borrow/verifyCodeAjax", results = { @Result(name = "success", params = {
			"root", "root" }, type = "json") })
	public String verifyCodeAjax() {
		try {
			String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);

			if (this.getMycode() == null || !this.getMycode().equalsIgnoreCase(random)) {
				this.setErrorMsg("fail");
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}
			this.setErrorMsg("ok");
			root.put("errorMsg", this.getErrorMsg());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * 投标按钮按下
	 * 
	 * @return
	 */
	@Action(value = "/borrow/poputInvestDo", results = { @Result(name = "success", params = {
			"root", "root" }, type = "json") })
	public String poputInvestDo() {

		try {

			user = this.getLoginUser();
			tenderLog.debug("开始[" + user.getUsername() + "]["
					+ getRequest().getSession().getId() + "][" + obtainUserIp()
					+ "][" + getRequestRemoteAddr() + "]["
					+ getRequest().getHeader("user-agent") + "]["
					+ getRequest().getHeader("referer") + "]");

			if (user.getTypeId() != 0) {
				this.setErrorMsg("只有投资者才能投标");
				root.put("errorMsg", this.getErrorMsg());
				tenderLog.debug("结束[" + user.getUsername() + "]["
						+ getErrorMsg() + "]");
				return SUCCESS;
			}

			String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
			removeSession(ConstantUtil.RANDOM_COOKIE_NAME);
			if (this.getMycode() == null || !this.getMycode().equalsIgnoreCase(random)) {
				this.setErrorMsg("验证码输入错误!");
				root.put("errorMsg", this.getErrorMsg());
				tenderLog.debug("结束[" + user.getUsername() + "]["
						+ getErrorMsg() + "]");
				return SUCCESS;
			}

			if (borrowTender.getAbleAmount() == null
					|| "".equals(borrowTender.getAbleAmount().trim())) {
				borrowTender.setAbleAmount("0");
			}
			if (borrowTender.getContinueAmount() == null
					|| "".equals(borrowTender.getContinueAmount().trim())) {
				borrowTender.setContinueAmount("0");
			}

			if (borrowTender.getTasteAmount() == null
					|| "".equals(borrowTender.getTasteAmount().trim())) {
				borrowTender.setTasteAmount("0");
			}
			
			if (borrowTender.getAbleAmount() != null
					&& !"".equals(borrowTender.getAbleAmount())) {
				if (!this.isNumeric(borrowTender.getAbleAmount())) {
					this.setErrorMsg("投标金额必须为整数数字");
					root.put("errorMsg", this.getErrorMsg());
					tenderLog.debug("结束[" + user.getUsername() + "]["
							+ getErrorMsg() + "]["
							+ borrowTender.getContinueAmount() + "]["
							+ borrowTender.getAbleAmount() + "]");
					return SUCCESS;
				}
				if(user.getId() != 1613){//@author zdl-测试小号(133*****911)可以不是整数倍：用于解决剩余可投金额不足100 
					if (Double.valueOf(borrowTender.getAbleAmount()) % 100 != 0) {
						this.setErrorMsg("投标金额必须是100的整数倍!");
						root.put("errorMsg", this.getErrorMsg());
						tenderLog.debug("结束[" + user.getUsername() + "]["
								+ getErrorMsg() + "]["
								+ borrowTender.getContinueAmount() + "]["
								+ borrowTender.getAbleAmount() + "]");
						return SUCCESS;
					}
				}
				if (Double.valueOf(borrowTender.getAbleAmount()) < 0) {
					this.setErrorMsg("投标金额不能小于0");
					root.put("errorMsg", this.getErrorMsg());
					tenderLog.debug("结束[" + user.getUsername() + "]["
							+ getErrorMsg() + "]["
							+ borrowTender.getContinueAmount() + "]["
							+ borrowTender.getAbleAmount() + "]");
					return SUCCESS;
				}

			}

			if (borrowTender.getContinueAmount() != null
					&& !"".equals(borrowTender.getContinueAmount())) {
				if (!this.isNumeric(borrowTender.getContinueAmount())) {
					this.setErrorMsg("续投金额必须为整数数字");
					root.put("errorMsg", this.getErrorMsg());
					tenderLog.debug("结束[" + user.getUsername() + "]["
							+ getErrorMsg() + "]["
							+ borrowTender.getContinueAmount() + "]["
							+ borrowTender.getAbleAmount() + "]");
					return SUCCESS;
				}
				if (Double.valueOf(borrowTender.getContinueAmount()) % 100 != 0) {
					this.setErrorMsg("续投金额必须是100的整数倍!");
					root.put("errorMsg", this.getErrorMsg());
					tenderLog.debug("结束[" + user.getUsername() + "]["
							+ getErrorMsg() + "]["
							+ borrowTender.getContinueAmount() + "]["
							+ borrowTender.getAbleAmount() + "]");
					return SUCCESS;
				}
				if (Double.valueOf(borrowTender.getContinueAmount()) < 0) {
					this.setErrorMsg("续投金额不能小于0");
					root.put("errorMsg", this.getErrorMsg());
					tenderLog.debug("结束[" + user.getUsername() + "]["
							+ getErrorMsg() + "]["
							+ borrowTender.getContinueAmount() + "]["
							+ borrowTender.getAbleAmount() + "]");
					return SUCCESS;
				}
			}
			
			//体验金
			if (borrowTender.getTasteAmount() == null
					|| "".equals(borrowTender.getTasteAmount().trim())) {
				if (!this.isNumeric(borrowTender.getTasteAmount())) {
					this.setErrorMsg("体验金必须为整数数字");
					root.put("errorMsg", this.getErrorMsg());
					tenderLog.debug("结束[" + user.getUsername() + "]["
							+ getErrorMsg() + "]["
							+ borrowTender.getTasteAmount() + "]");
					return SUCCESS;
				}
				if (Double.valueOf(borrowTender.getTasteAmount()) % 100 != 0) {
					this.setErrorMsg("体验金必须是100的整数倍!");
					root.put("errorMsg", this.getErrorMsg());
					tenderLog.debug("结束[" + user.getUsername() + "]["
							+ getErrorMsg() + "]["
							+ borrowTender.getTasteAmount() + "]");
					return SUCCESS;
				}
				if (Double.valueOf(borrowTender.getTasteAmount()) < 0) {
					this.setErrorMsg("体验金不能小于0");
					root.put("errorMsg", this.getErrorMsg());
					tenderLog.debug("结束[" + user.getUsername() + "]["
							+ getErrorMsg() + "]["
							+ borrowTender.getTasteAmount() + "]");
					return SUCCESS;
				}
			}

			double money = Double.valueOf(borrowTender.getAbleAmount())
					+ Double.valueOf(borrowTender.getContinueAmount())
					+ Double.valueOf(borrowTender.getTasteAmount())
					;
			if (money <= 0) {
				this.setErrorMsg("投标金额不能小于等于0");
				root.put("errorMsg", this.getErrorMsg());
				tenderLog.debug("结束[" + user.getUsername() + "]["
						+ getErrorMsg() + "]["
						+ borrowTender.getContinueAmount() + "]["
						+ borrowTender.getAbleAmount() + "]");
				return SUCCESS;
			}

			borrowTender.setMoney(String.valueOf(money));

			UserAccount userAccount = this.userAccountService
					.getUserAccountByUserId(user.getId());
			borrow = this.borrowService.getBorrowById(borrowTender
					.getBorrowId());

			if (borrow.getStatus() != 1) {
				this.setErrorMsg("标的状态不正确！");
				root.put("errorMsg", this.getErrorMsg());
				tenderLog.debug("结束[" + user.getUsername() + "]["
						+ getErrorMsg() + "]");
				return SUCCESS;
			}

			if (!("4".equals(borrow.getType()) || "12".equals(borrow.getType())|| "15".equals(borrow.getType()))
					&& Double.valueOf(borrowTender.getContinueAmount()) > 0) {
				this.setErrorMsg("只有月标才能续投");
				root.put("errorMsg", this.getErrorMsg());
				tenderLog.debug("结束[" + user.getUsername() + "]["
						+ getErrorMsg() + "]");
				return SUCCESS;
			}

			if ("1".equals(borrow.getIsDxb())) {
				if (dxpwd == null || "".equals(dxpwd)) {
					errorMsg = "请输入定向密码";
					root.put("errorMsg", this.getErrorMsg());
					tenderLog.debug("结束[" + user.getUsername() + "]["
							+ getErrorMsg() + "]");
					return SUCCESS;
				}
				if (!dxpwd.equals(borrow.getPwd())) {
					errorMsg = "定向密码不正确";
					root.put("errorMsg", this.getErrorMsg());
					tenderLog.debug("结束[" + user.getUsername() + "]["
							+ getErrorMsg() + "]");
					return SUCCESS;
				}
			}

			if (safepwd == null || "".equals(safepwd)) {
				errorMsg = "请输入交易密码";
				root.put("errorMsg", this.getErrorMsg());
				tenderLog.debug("结束[" + user.getUsername() + "]["
						+ getErrorMsg() + "]");
				return SUCCESS;
			}

			// 验证交易密码
			boolean isSavePwd = userService.isPassword(user.getUsername(),
					safepwd, "1");
			if (!isSavePwd) {
				errorMsg = "交易密码不正确";
				root.put("errorMsg", this.getErrorMsg());
				tenderLog.debug("结束[" + user.getUsername() + "]["
						+ getErrorMsg() + "]");
				return SUCCESS;
			}

			// 验证是否是借款人自己投标
			if (user.getId().intValue() == borrow.getUserId().intValue()) {
				errorMsg = "你是借款人，不能自己投标";
				root.put("errorMsg", this.getErrorMsg());
				tenderLog.debug("结束[" + user.getUsername() + "]["
						+ getErrorMsg() + "]");
				return SUCCESS;
			}
			// 续投金额大于可用续投金额
			if (userAccount.getContinueTotal().doubleValue() < Double
					.parseDouble(borrowTender.getContinueAmount())) {
				errorMsg = "续投余额不足";
				root.put("errorMsg", this.getErrorMsg());
				tenderLog.debug("结束[" + user.getUsername() + "]["
						+ getErrorMsg() + "][" + userAccount.getContinueTotal()
						+ "][" + borrowTender.getContinueAmount() + "]");
				return SUCCESS;
			}
			if(user.getId() != 1613){//@author zdl-测试小号(133*****911)可以小于最小投资额限制：用于解决剩余可投金额不足100 
				// 投标额度是否小于最小投资金额
				if (Double.valueOf(borrowTender.getMoney()) < Double.valueOf(borrow
						.getLowestAccount())) {
					errorMsg = "投标金额小于最小投标额";
					root.put("errorMsg", this.getErrorMsg());
					tenderLog.debug("结束[" + user.getUsername() + "]["
							+ getErrorMsg() + "][" + borrow.getLowestAccount()
							+ "][" + borrowTender.getMoney() + "]");
					return SUCCESS;
				}
			}
			// 投标总额大于最大投标额
			if (borrow.getMostAccount() != null
					&& !"".equals(borrow.getMostAccount().trim())
					&& !"0".equals(borrow.getMostAccount())) {
				Map<String, Object> tMap = new HashMap<String, Object>();
				tMap.put("userId", user.getId());
				tMap.put("borrowId", borrow.getId());
				Object totalAmount = this.borrowTenderService.getByStatementId(
						"BorrowTender.selectAllAccountByUserid", tMap);
				if (totalAmount != null) {
					double tAmount = Double.valueOf(totalAmount.toString());
					if ((tAmount + Double.valueOf(borrowTender.getMoney())) > Double
							.valueOf(borrow.getMostAccount())) {
						errorMsg = "投标总额大于最大投标额";
						root.put("errorMsg", this.getErrorMsg());
						tenderLog.debug("结束[" + user.getUsername() + "]["
								+ getErrorMsg() + "]["
								+ borrow.getMostAccount() + "]["
								+ borrowTender.getMoney() + "]");
						return SUCCESS;
					}
				} else if (borrow.getMostAccount() != null
						&& !"".equals(borrow.getMostAccount())
						&& Integer.parseInt(borrow.getMostAccount()) < Double
								.valueOf(borrowTender.getMoney())) {
					errorMsg = "投标总额大于最大投标额";
					root.put("errorMsg", this.getErrorMsg());
					tenderLog.debug("结束[" + user.getUsername() + "]["
							+ getErrorMsg() + "][" + borrow.getMostAccount()
							+ "][" + borrowTender.getMoney() + "]");
					return SUCCESS;
				}
			}
			if (Double.valueOf(borrow.getBalance()) <= 0) {
				errorMsg = "此标已满";
				root.put("errorMsg", this.getErrorMsg());
				tenderLog.debug("结束[" + user.getUsername() + "]["
						+ getErrorMsg() + "][" + borrow.getBalance() + "]");
				return SUCCESS;
			}

			int inret = 1;// 0 投标成功

			if ("0".equals(borrow.getType())) {// 秒标
				inret = this.borrowSecondService.borrowInvestDo(user,
						borrowTender, this.obtainUserIp());
			} else if ("4".equals(borrow.getType())) {// 月标
				inret = this.borrowService.monthBorrowInvestDo(user,
						borrowTender, this.obtainUserIp());
			} else if ("11".equals(borrow.getType())
					|| "12".equals(borrow.getType())
					|| "13".equals(borrow.getType())
					|| "14".equals(borrow.getType())
					|| "15".equals(borrow.getType())
					|| "16".equals(borrow.getType())) {
				//修改新的投资
				//inret = this.borrowPromoteService.borrowInvestDo(user,borrowTender, this.obtainUserIp(),hongbao);
				inret = this.borrowPromoteService.borrowInvestDoHNew(user,borrowTender, this.obtainUserIp(),hongbao);
			} else if("17".equals(borrow.getType())){
				inret = this.borrowTasteService.borrowInvestDo(user,
						borrowTender, this.obtainUserIp());
			}else {// 质押标
				inret = this.borrowService.borrowInvestDo(user, borrowTender,
						this.obtainUserIp());
			}
			tenderLog.debug("结束[" + user.getUsername() + "][" + inret + "]");
			if (inret == 0) {
				root.put("errorMsg", "OK");
			} else if (inret == 1) {
				root.put("errorMsg", "标已满");
			} else if (inret == 2) {
				root.put("errorMsg", "账户可用余额不足");
			} else if (inret == 3) {
				root.put("errorMsg", "账户续投余额不足");
			} else if (inret == 4) {
				root.put("errorMsg", "标的状态不正确！");
			} else if(inret == 5){
				root.put("errorMsg", "新手项目只能未投资过的用户才能参加！");	
			}else if(inret == 122) {
                root.put("errorMsg", "标剩余金额不足！");
            } else if (inret == 999) {
                root.put("errorMsg", "存管处理失败");
            }else {
				root.put("errorMsg", "投标未成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@Action(value = "/borrow/invest", results = {
			@Result(name = "success", location = "/content/borrow/borrowInvest.ftl", type = "freemarker"),
			@Result(name = "noBorrowExist", location = "/content/borrow/noBorrowExist.ftl", type = "freemarker") })
	public String borrowInvest() {

		borrow = this.borrowService.getBorrowById(this.getbId());
		Calendar c = Calendar.getInstance();
		c.setTime(borrow.getVerifyTime());
		c.set(Calendar.DAY_OF_MONTH,
				c.get(Calendar.DAY_OF_MONTH)
						+ Integer.valueOf(borrow.getValidTime()));
		borrow.setOverDate(c.getTime());
		if (borrow == null) {
			return "noBorrowExist";
		}
		/**
		 * String sId = this.getCookie(ConstantUtil.USER_ID_COOKIE_NAME);
		 * Map<String,Object> uMap =
		 * (Map<String,Object>)this.getMemcachedClient().get(sId); user =
		 * (User)uMap.get("user"); Map<String,Object> map = new
		 * HashMap<String,Object>(); uMap.put("id", user.getId()); user =
		 * userService.getUser(uMap); // String sId =
		 * this.getCookie(ConstantUtil.USER_ID_COOKIE_NAME); //
		 * Map<String,Object> uMap =
		 * (Map<String,Object>)this.getMemcachedClient().get(sId); // user =
		 * (User)uMap.get("user"); // Map<String,Object> map = new
		 * HashMap<String,Object>(); // uMap.put("id", user.getId()); // user =
		 * userService.getUser(uMap); user = getLoginUser();
		 * 
		 */
		user = this.getLoginUser();

		if ("0".equals(borrow.getType())) {
			this.setInterest(String.valueOf(Double.valueOf(borrow.getAccount())
					* borrow.getApr() / 36500));
		} else if ("1".equals(borrow.getType())) {
			this.setInterest(interestCalUtil.payback(100d,
					Double.valueOf(borrow.getAccount()), borrow.getApr(), 30,
					borrow.getBorStages(), 2).getTotalLiXi());
		}

		return SUCCESS;
	}

	@Action(value = "/borrow/investDo", results = { @Result(name = "success", params = {
			"root", "root" }, type = "json") })
	public String borrowInvestDo() {
		// 获取验证码
		// String random =
		// this.getMemcachedByCookie(ConstantUtil.RANDOM_COOKIE_NAME).toString();
		// String random = (String)getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		// if(!this.getMycode().equals(random)){
		// this.setErrorMsg("验证码错误");
		// root.put("errorMsg", this.getErrorMsg());
		// return SUCCESS;
		// }
		if (borrowTender.getMoney() == null
				|| "".equals(borrowTender.getMoney())) {
			this.setErrorMsg("金额不需为空");
			root.put("errorMsg", this.getErrorMsg());
			return SUCCESS;
		}
		if (borrowTender.getMoney() != null
				&& !"".equals(borrowTender.getMoney())) {
			if (!this.isNumeric(borrowTender.getMoney())) {
				this.setErrorMsg("金额必须为数字");
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}
			if (Double.valueOf(borrowTender.getMoney()) % 100 != 0) {
				this.setErrorMsg("金额必须为100整数倍");
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}
		}
		user = this.getLoginUser();
		UserAccount userAccount = this.userAccountService
				.getUserAccountByUserId(user.getId());
		borrow = this.borrowService.getBorrowById(borrowTender.getBorrowId());
		// 验证是否是借款人自己投标
		// if(user.getId().intValue() == borrow.getUserId().intValue()){
		// errorMsg = "你是借款人，不能自己投标";
		// root.put("errorMsg", this.getErrorMsg());
		// return SUCCESS;
		// }
		// 投标金额大于可用金额
		if (userAccount.getAbleMoney().doubleValue() < Double
				.parseDouble(borrowTender.getMoney())) {
			errorMsg = "余额不足";
			root.put("errorMsg", this.getErrorMsg());
			return SUCCESS;
		}
		// 投标额度是否小于最小投资金额
		if (Double.valueOf(borrowTender.getMoney()) < Double.valueOf(borrow
				.getLowestAccount())) {
			errorMsg = "投标金额小于最小投标额";
			root.put("errorMsg", this.getErrorMsg());
			return SUCCESS;
		}
		// 投标总额大于最大投标额
		if (borrow.getMostAccount() != null) {
			Map<String, Object> tMap = new HashMap<String, Object>();
			tMap.put("userId", user.getId());
			tMap.put("borrowId", borrow.getId());
			Object totalAmount = this.borrowTenderService.getByStatementId(
					"BorrowTender.selectAllAccountByUserid", tMap);
			if (totalAmount != null) {
				double tAmount = Double.valueOf(totalAmount.toString());
				if ((tAmount + Double.valueOf(borrowTender.getMoney())) > Double
						.valueOf(borrow.getMostAccount())) {
					errorMsg = "投标总额大于最大投标额";
					root.put("errorMsg", this.getErrorMsg());
					return SUCCESS;
				}
			} else if (Integer.parseInt(borrow.getMostAccount()) < Integer
					.parseInt(borrowTender.getMoney())) {
				errorMsg = "投标总额大于最大投标额";
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}
		}
		if (Double.valueOf(borrow.getBalance()) <= 0) {
			errorMsg = "此标已满";
			root.put("errorMsg", this.getErrorMsg());
			return SUCCESS;
		}
		if ("0".equals(borrow.getType())) {
			this.borrowSecondService.borrowInvestDo(user, borrowTender,
					this.obtainUserIp());
		} else {
			this.borrowService.borrowInvestDo(user, borrowTender,
					this.obtainUserIp());
		}
		return SUCCESS;
	}

	/**
	 * addBor 添加标
	 * 
	 * @author zhanf
	 * @return
	 */
	@Action(value = "/borrow/addBor", results = {
			@Result(name = "success", location = "/content/borrow/success.ftl", type = "freemarker"),
			@Result(name = "input", location = "/content/borrow/setborrow.ftl", type = "freemarker") })
	public String addBor() {

		// Map<String,User> map = (Map<String, User>)
		// this.getMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME);
		// User user = map.get(ConstantUtil.USER_NAME);
		User user = getLoginUser();
		// String random =
		// this.getMemcachedByCookie(ConstantUtil.RANDOM_COOKIE_NAME).toString();
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		if (!mycode.equals(random)) {
			addActionError("验证码输入错误!");
			return "error_ftl";
		}
		if (borrow.getName().equals("") || borrow.getName() == null) {
			addActionError("标题不能为空，请重新输入!");
			return "error_ftl";
		} else if (borrow.getAccount() == null
				|| borrow.getAccount().equals("")) {
			addActionError("借款金额不能为空，请重新输入!");
			return "error_ftl";
		} else if (borrow.getContent() == null
				|| borrow.getContent().equals("")) {
			addActionError("标内容不能为空，请重新输入!");
			return "error_ftl";
		} else if (borrow.getApr() <= 0 || borrow.getApr() > 1) {
			addActionError("请输入正确的借款利率!");
			return "error_ftl";
		} else if (borrow.getTimeLimit().equals("")
				|| borrow.getTimeLimit() == null) {
			addActionError("请输入正确的借款天数!");
			return "error_ftl";
		} else if ((Integer.parseInt(borrow.getTimeLimit()) > 30 && Integer
				.parseInt(borrow.getTimeLimit()) % 10 != 0)) {
			addActionError("请输入10的倍数的借款天数!");
			return "error_ftl";
		} else if (borrow.getBorStages().equals("0")) {
			addActionError("请设置正确的还款计划!");
			return "error_ftl";
		} else if (!borrow.getAward().equals("0")) {
			if (borrow.getFunds().equals("") || borrow.getFunds().equals("0")) {
				addActionError("请输入正确的奖励!");
				return "error_ftl";
			}
		} else if (isDxb1 != null
				&& (borrow.getPwd() == null || "".equals(borrow.getPwd()))) {
			addActionError("请设置定向密码!");
			return "error_ftl";
		}
		if (borrow.getIsDxb().equals("1") && borrow.getPwd().equals("")) {
			addActionError("请输入定向密码!");
			return "error_ftl";
		}
		// ****
		int j = 0;
		Map<String, Object> bMap = new HashMap<String, Object>();
		bMap.put("userId", user.getId());
		int[] array = { 0 };
		bMap.put("array", array);
		// List<Borrow> userBorrowList =
		// this.getBorrowService().queryUserBorrowList(bMap);//借款者招标中记录取得
		// for (Borrow borrow : userBorrowList){
		// if((!"".equals(borrow.getType()))&&Integer.parseInt(borrow.getType())==1){
		// j++;
		// }
		// }
		// if(j>0){
		// addActionError("您已经有发布中的标,请自行撤销或等审核后再发布!");
		// return "error_ftl";
		// }
		double borTotal = Double.parseDouble(borrow.getAccount());
		double money = 0;
		String str[] = borrow.getBorStages().split(":");
		int num = str.length;
		String dayStr[] = new String[num];
		String moneyStr[] = new String[num];
		for (int i = 0; i < str.length; i++) {
			String str1[] = str[i].split(",");
			dayStr[i] = str1[0];
			moneyStr[i] = String
					.valueOf((Double.parseDouble(str1[1]) / borTotal)
							* borTotal);// 按比例计算每期还款金额
			money += Double.parseDouble(moneyStr[i]);
		}
		if (Double.parseDouble(borrow.getAccount()) != money) {
			addActionError("请选择正确的还款期限和还款金额!");
			return "error_ftl";
		}
		String total = CalculationUtil.totalMoney(borrow);
		// DecimalFormat df=new java.text.DecimalFormat("#0.00");
		// String totalAmount =df.format(total);
		// borrow.setAward("0");
		borrow.setSchedule("0");
		borrow.setRepaymentAccount(total);
		borrow.setUserId(user.getId());
		borrow.setOperatorIp(getRequestRemoteAddr());
		borrow.setUpdatePersion(String.valueOf(user.getId()));
		borrow.setModifyDate(new Date());
		borrow.setCreateDate(new Date());
		borrow.setAddTime(new Date());
		borrow.setBalance(borrow.getAccount());
		borrow.setTenderTimes("0");// 投标次数

		if (borrow.getBorStages() != null
				&& !"".equals(borrow.getBorStages().trim())) {

			String stg = borrow.getBorStages();
			if (stg.endsWith(":")) {
				stg = stg.substring(0, stg.length() - 1);
			}

			String[] tgs = stg.split(":");
			borrow.setDivides(tgs.length);
		}

		String firstImage = null;
		if (borImagesFile != null && borImagesFile.length > 0) {
			String tmp = null;
			for (File fl : borImagesFile) {
				if (fl != null) {
					String flPath = ImageUtil.copyImageFile(
							getServletContext(), fl);
					if (flPath != null && !"".equals(flPath)) {
						if (tmp == null) {
							tmp = flPath;
							firstImage = flPath;
						} else {
							tmp += "," + flPath;
						}
					}
				}
			}
			borrow.setBorImage(tmp);
			borrow.setBorImageFirst(firstImage);
		}

		log.info("添加标");
		this.getBorrowService().addBorrow(borrow);
		// if(borCompany.getType().equals(1)){
		// Map<String,Object> map1 = new HashMap<String,Object>();
		// map1.put("borrowId",borrow.getId());
		// map1.put("companyId", borCompany.getId());
		// log.info("修改company中borreoID");
		// this.getBorrowService().updateBorCpy(map1);
		// }else{}
		return "success";
	}

	/**
	 * addBorMonth 添加月标
	 * 
	 * @author zhanf
	 * @return
	 */
	@Action(value = "/borrow/addBorMonth", results = { @Result(name = "success", location = "/content/borrow/success.ftl", type = "freemarker") })
	public String addBorMonth() {

		// Map<String,User> map = (Map<String, User>)
		// this.getMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME);
		// User user = map.get(ConstantUtil.USER_NAME);
		User user = getLoginUser();
		// String random =
		// this.getMemcachedByCookie(ConstantUtil.RANDOM_COOKIE_NAME).toString();
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		if (!mycode.equals(random)) {
			addActionError("验证码输入错误!");
			return "error_ftl";
		}
		if (borrow.getName().equals("") || borrow.getName() == null) {
			addActionError("标题不能为空，请重新输入!");
			return "error_ftl";
			// } else if(StringUtil.isEmpty(borImageFirst)) {
			// addActionError("请上传图片!");
			// return "error_ftl";
		} else if (borrow.getAccount() == null
				|| borrow.getAccount().equals("")) {
			addActionError("借款金额不能为空，请重新输入!");
			return "error_ftl";
		} else if (borrow.getContent() == null
				|| borrow.getContent().equals("")) {
			addActionError("标内容不能为空，请重新输入!");
			return "error_ftl";
		} else if (borrow.getApr() <= 0 || borrow.getApr() >= 30) {
			addActionError("请输入正确的借款利率!");
			return "error_ftl";
		} else if (borrow.getTimeLimit().equals("")
				|| borrow.getTimeLimit() == null) {
			addActionError("请输入正确的借款月数!");
			return "error_ftl";
		} else if (Integer.parseInt(borrow.getTimeLimit()) > 12
				|| Integer.parseInt(borrow.getTimeLimit()) % 1 != 0) {
			addActionError("请输入正确的借款月数!");
			return "error_ftl";
		} else if (borrow.getBorStages().equals("0")) {
			addActionError("请设置正确的还款计划!");
			return "error_ftl";
		} else if (!borrow.getAward().equals("0")) {
			if (borrow.getFunds().equals("") || borrow.getFunds().equals("0")) {
				addActionError("请输入正确的奖励!");
				return "error_ftl";
			}
		} else if (isDxb1 != null
				&& (borrow.getPwd() == null || "".equals(borrow.getPwd()))) {
			addActionError("请设置定向密码!");
			return "error_ftl";
		}
		if (borrow.getIsDxb().equals("1") && borrow.getPwd().equals("")) {
			addActionError("请输入定向密码!");
			return "error_ftl";
		}
		double borTotal = Double.parseDouble(borrow.getAccount());
		double money = 0;
		String str[] = borrow.getBorStages().split(":");
		int num = str.length;
		String dayStr[] = new String[num];
		String moneyStr[] = new String[num];
		boolean zeroFlg = false;
		for (int i = 0; i < str.length; i++) {
			String str1[] = str[i].split(",");
			dayStr[i] = str1[0];
			moneyStr[i] = String
					.valueOf((Double.parseDouble(str1[1]) / borTotal)
							* borTotal);// 按比例计算每期还款金额
			money += Double.parseDouble(moneyStr[i]);
			if (Double.parseDouble(str1[1]) == 0) {
				zeroFlg = true;
			}
		}
		if (Double.parseDouble(borrow.getAccount()) != money) {
			addActionError("请选择正确的还款期限和还款金额!");
			return "error_ftl";
		}
		String total = CalculationUtil.totalMonthMoney(borrow);// 计算月标预计还款总额（不计算奖励）
		// DecimalFormat df=new java.text.DecimalFormat("#0.00");
		// String totalAmount =df.format(total);
		// borrow.setAward("0");
		borrow.setSchedule("0");
		borrow.setRepaymentAccount(total);
		borrow.setUserId(user.getId());
		borrow.setOperatorIp(getRequestRemoteAddr());
		borrow.setUpdatePersion(String.valueOf(user.getId()));
		borrow.setModifyDate(new Date());
		borrow.setCreateDate(new Date());
		borrow.setAddTime(new Date());
		borrow.setBalance(borrow.getAccount());
		borrow.setTenderTimes("0");// 投标次数

		if (borrow.getBorStages() != null
				&& !"".equals(borrow.getBorStages().trim())) {

			String stg = borrow.getBorStages();
			if (stg.endsWith(":")) {
				stg = stg.substring(0, stg.length() - 1);
			}

			String[] tgs = stg.split(":");
			borrow.setDivides(tgs.length);
		}

		if ("1".equals(borrow.getTimeLimit())) {
			// 还款方式为1
			borrow.setAutoTenderRepayWay(1);
		} else {
			if (borrow.getDivides() == 1) {
				// 还款方式为3：到期还本息
				borrow.setAutoTenderRepayWay(3);
			} else {
				if (zeroFlg) {
					// 还款方式为1
					borrow.setAutoTenderRepayWay(1);
				} else {
					// 还款方式为2
					borrow.setAutoTenderRepayWay(2);
				}
			}

		}

		borrow.setBorImageFirst(CommonUtil.decodeUrl(borImageFirst));

//		if (vouchers != null && vouchers.length > 0 && vouchersTitle != null
//				&& vouchersTitle.length > 0) {
//			List<NoteImg> noteImgList = new ArrayList<NoteImg>();
//			for (int i = 0; i < vouchers.length; i++) {
//				String devc = CommonUtil.decodeUrl(vouchers[i]);
//				NoteImg nt = new NoteImg();
//				nt.setUrl(devc);
//				nt.setName(vouchersTitle[i]);
//				noteImgList.add(nt);
//			}
//
//			String tempVcs = JsonUtil.listToJson(noteImgList);
//
//			borrow.setBorImage(tempVcs);
//		}

		log.info("添加标");
		this.getBorrowService().addBorrow(borrow);

		return "success";
	}

	/**
	 * addBorSecond 添加标(秒标)
	 * 
	 * @author zhanf
	 * @return
	 */
	@Action(value = "/borrow/addBorSecond", results = {
			@Result(name = "success", location = "/content/borrow/success.ftl", type = "freemarker"),
			@Result(name = "input", location = "/content/borrow/borrowInputSecond.ftl", type = "freemarker") })
	public String addBorSecond() {

		User user = getLoginUser();
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		if (!mycode.equals(random)) {
			addActionError("验证码输入错误!");
			return "error_ftl";
		}
		if (borrow.getName().equals("") || borrow.getName() == null) {
			addActionError("标题不能为空，请重新输入!");
			return "error_ftl";
		} else if (borrow.getAccount() == null
				|| borrow.getAccount().equals("")) {
			addActionError("借款金额不能为空，请重新输入!");
			return "error_ftl";
		} else if (borrow.getContent() == null
				|| borrow.getContent().equals("")) {
			addActionError("标内容不能为空，请重新输入!");
			return "error_ftl";
		} else if (borrow.getApr() <= 0 || borrow.getApr() > 1) {
			addActionError("请输入正确的借款利率!");
			return "error_ftl";
		} else if (!borrow.getAward().equals("0")) {
			if (borrow.getFunds().equals("") || borrow.getFunds().equals("0")) {
				addActionError("请输入正确的奖励!");
				return "error_ftl";
			}
		} else if (borrow.getIsDxb().equals("1")) {
			if (borrow.getPwd().equals("")) {
				addActionError("请输入定向密码!");
				return "error_ftl";
			}
		}
		String total = CalculationUtil.totalMoney(borrow);
		borrow.setSchedule("0");
		borrow.setRepaymentAccount(total);
		borrow.setUserId(user.getId());
		borrow.setOperatorIp(getRequestRemoteAddr());
		borrow.setUpdatePersion(String.valueOf(user.getId()));
		borrow.setModifyDate(new Date());
		borrow.setCreateDate(new Date());
		borrow.setBalance(borrow.getAccount());
		borrow.setTenderTimes("0");// 投标次数
		borrow.setAward("0");
		// borrow.setIsDxb("0");
		borrow.setTimeLimit("1");
		borrow.setType("0");
		borrow.setDivides(1);
		borrow.setBorStages("1," + borrow.getAccount());

		String firstImage = null;
		if (borImagesFile != null && borImagesFile.length > 0) {
			String tmp = null;
			for (File fl : borImagesFile) {
				if (fl != null) {
					String flPath = ImageUtil.copyImageFile(
							getServletContext(), fl);
					if (flPath != null && !"".equals(flPath)) {
						if (tmp == null) {
							tmp = flPath;
							firstImage = flPath;
						} else {
							tmp += "," + flPath;
						}
					}
				}
			}
			borrow.setBorImage(tmp);
			borrow.setBorImageFirst(firstImage);
		}

		log.info("添加标");
		this.getBorrowService().addBorrow(borrow);

		return "success";
	}

	/**
	 * addBorWander 添加流转标
	 * 
	 * @return
	 */
	@Action(value = "/borrow/addBorWander", results = {
			@Result(name = "success", location = "/content/borrow/success.ftl", type = "freemarker"),
			@Result(name = "input", location = "/content/borrow/borrowInputWander.ftl", type = "freemarker") })
	public String addBorWander() {

		boolean addFlg = false;
		if (borrow.getId() == null) {
			addFlg = true;
		}

		User user = getLoginUser();
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		if (!mycode.equals(random)) {
			addActionError("验证码输入错误!");
			return "error_ftl";
		}
		if (borrow.getName() == null || "".equals(borrow.getName())) {
			addActionError("标题不能为空，请重新输入!");
			return "error_ftl";
		} else if (addFlg
				&& (borImagesFile == null || borImagesFile.length < 1)) {
			addActionError("图片不能为空，请重新输入!");
			return "error_ftl";
		} else if (borrow.getAccount() == null
				|| "".equals(borrow.getAccount().trim())) {
			addActionError("借款金额不能为空，请重新输入!");
			return "error_ftl";
		} else if (!CommonUtil.checkNumberIntUpZero(borrow.getAccount())) {
			addActionError("借款金额必须是正整数，请重新输入!");
			return "error_ftl";
		} else if (borrow.getApr() <= 0 || borrow.getApr() >= 1) {
			addActionError("请输入正确的借款利率!");
			return "error_ftl";
		} else if (borrow.getWanderPieceSize() == null
				|| borrow.getWanderPieceSize() <= 0) {
			addActionError("请输入正确的认购总份数!");
			return "error_ftl";
		} else if (!WanderUtil.isMod(borrow.getAccount(),
				String.valueOf(borrow.getWanderPieceSize()))) {
			addActionError("请输入正确的认购总份数和金额!");
			return "error_ftl";
		} else if (!borrow.getAward().equals("0")
				&& (borrow.getFunds().equals("") || borrow.getFunds().equals(
						"0"))) {
			addActionError("请输入正确的奖励!");
			return "error_ftl";
		} else if (borrow.getTimeLimit() == null
				|| "".equals(borrow.getTimeLimit())
				|| !this.isNumeric(borrow.getTimeLimit())) {
			addActionError("请输入正确的借款时长!");
			return "error_ftl";
		} else if (!WanderUtil.isMod(borrow.getTimeLimit(),
				String.valueOf(borrow.getWanderRedeemTimes()))) {
			addActionError("请输入正确的借款时长和借款分期!");
			return "error_ftl";
		} else if (borrow.getWanderStages() == null
				|| borrow.getWanderStages().equals("")) {
			addActionError("请设置回购计划!");
			return "error_ftl";
		} else if (isDxb1 != null
				&& (borrow.getPwd() == null || "".equals(borrow.getPwd()))) {
			addActionError("请设置定向密码!");
			return "error_ftl";
		} else if (borrow.getIsDxb().equals("1") && borrow.getPwd().equals("")) {
			addActionError("请输入定向密码!");
			return "error_ftl";
		} else if (borrow.getContent() == null
				|| borrow.getContent().equals("")) {
			addActionError("标内容不能为空，请重新输入!");
			return "error_ftl";
		}

		borrow.setSchedule("0");
		borrow.setRepaymentAccount(borrow.getAccount());
		borrow.setUserId(user.getId());
		borrow.setOperatorIp(getRequestRemoteAddr());
		borrow.setUpdatePersion(String.valueOf(user.getId()));
		borrow.setModifyDate(new Date());
		borrow.setCreateDate(new Date());
		borrow.setAddTime(new Date());
		borrow.setBalance(borrow.getAccount());
		borrow.setTenderTimes("0");// 投标次数
		borrow.setType("2");

		if (borrow.getWanderStages() != null
				&& !"".equals(borrow.getWanderStages().trim())) {

			String stg = borrow.getWanderStages();
			if (stg.endsWith(":")) {
				stg = stg.substring(0, stg.length() - 1);
			}

			String[] tgs = stg.split(":");
			borrow.setDivides(tgs.length);

		}
		borrow.setWanderPieceMoney(WanderUtil.moneyForWanderPiece(borrow));

		String firstImage = null;
		if (borImagesFile != null && borImagesFile.length > 0) {
			String tmp = null;
			for (File fl : borImagesFile) {
				if (fl != null) {
					String flPath = ImageUtil.copyImageFile(
							getServletContext(), fl);
					if (flPath != null && !"".equals(flPath)) {
						if (tmp == null) {
							tmp = flPath;
							firstImage = flPath;
						} else {
							tmp += "," + flPath;
						}
					}
				}
			}
			borrow.setBorImage(tmp);
			borrow.setBorImageFirst(firstImage);
		}

		log.info("添加标");
		if (addFlg) {
			this.getBorrowService().addBorrow(borrow);
		} else {
			this.getBorrowService().updateBorrowMess(borrow);
		}

		return "success";
	}

	/**
	 * queryBorPublish 按条件状态查询标列表【如：正在进行的借款 已完成的借款】
	 * 
	 * @author zhanf
	 * @return
	 */
	@Action(value = "/borrow/borrowList", results = {
			@Result(name = "publish", location = "/content/borrow/publish.ftl", type = "freemarker"),
			@Result(name = "unpublish", location = "/content/borrow/unpublish.ftl", type = "freemarker"),
			@Result(name = "repayment", location = "/content/borrow/repayment.ftl", type = "freemarker") })
	public String queryBorPublish() {

		if (pager == null) {
			pager = new Pager();
		}
		// Map<String,User> map = (Map<String, User>)
		// this.getMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME);
		// User user = map.get(ConstantUtil.USER_NAME);
		User user = getLoginUser();
		Map<String, Object> qMap = new HashMap<String, Object>();
		if (borrow.getStatus() == 0) {
			int[] array = { 0 };
			qMap.put("array", array);
			qMap.put("userId", user.getId());
			pager = this.getBorrowService().queryBorrowList(pager, qMap);
			return "unpublish";
		} else if (borrow.getStatus() == 3 || borrow.getStatus() == 7) {
			int[] array = { borrow.getStatus() };
			qMap.put("array", array);
			qMap.put("balance", "0");
			qMap.put("userId", user.getId());
			pager = this.getBorrowService().queryBorrowList(pager, qMap);
			return "repayment";
		} else {
			int[] array = { 0, 1, 2, 4, 5 };
			qMap.put("array", array);
			qMap.put("userId", user.getId());
			pager = this.getBorrowService().queryBorrowList(pager, qMap);
			return "publish";
		}
	}

	/**
	 * borrowRepayment 查询出还款列表
	 * 
	 * @author zhanf
	 * @return
	 */
	@Action(value = "/borrow/borrowRepayList", results = { @Result(name = "success", location = "/content/borrow/repaymentView.ftl", type = "freemarker") })
	public String borrowRepayment() {

		if (getLoginUser().getTypeId().intValue() == 0) {
			addActionError("只有借款人才能访问！");
			return "error_ftl";
		}

		borrow = this.getBorrowService().getBorrowById(borrow.getId());

		if (borrow.getUserId().intValue() != getLoginUser().getId().intValue()) {
			addActionError("只能查看自己的标！");
			return "error_ftl";
		}

		borrowRepDetailList = this.borrowRepaymentDetailService
				.queryUserBorrowList(borrow.getId());

		if (borrow.getStatus() == 7) {
			borrStatus = 7;
		} else {
			borrStatus = 3;
		}

		return "success";
	}

	/**
	 * borrowRepayDetail 标还款同时扣去资金Action
	 * 
	 * @author zhanf
	 * @return
	 */
	@Action(value = "/borrow/borrowRepayDetail", results = {
			@Result(name = "success", location = "userBorrowList.do?bStatus=3", type = "redirectAction"),
			@Result(name = "hkxq", location = "/content/borrow/repaymentView.ftl", type = "freemarker"),
			@Result(name = "hkmx", location = "hkmx.do", type = "redirectAction") })
	public String borrowRepayDetail() {
		try {
			borrowRepaymentDetail = this.borrowRepaymentDetailService
					.get(borrowRepaymentDetail.getId());
			borrowTenderList = this.borrowTenderService
					.getBorrowTenderByBorrowId(borrowRepaymentDetail
							.getBorrowId());
			borrow = borrowService.getBorrowById(borrowRepaymentDetail
					.getBorrowId());

			// 本期状态判断
			if (borrowRepaymentDetail.getStatus() == 1) {
				return "hkmx";
			}
			if (borrowRepaymentDetail.getOrderNum() != 1) {
				Map<String, Object> map = new HashMap<String, Object>();
				int orderNum = borrowRepaymentDetail.getOrderNum() - 1;
				map.put("orderNum", orderNum);
				map.put("borrowId", borrowRepaymentDetail.getBorrowId());
				BorrowRepaymentDetail borrowRepaymentDetail1 = this.borrowRepaymentDetailService
						.queryBorrowRepayment(map);
				if (borrowRepaymentDetail1.getStatus() == 0) {
					addActionError("上期需还款金额未还，请依次还款!");
					return "error_ftl";
				}
			}
			List<MailRepayForInvestor> mailList = new ArrayList<MailRepayForInvestor>();
			int number = this.borrowTenderService.updateBorrowDetail(
					borrowTenderList, borrowRepaymentDetail, borrow, mailList,
					getRequestRemoteAddr());
			if (number == 1) {
				if ("1".equals(flag)) {
					return "hkmx";
				} else if ("2".equals(flag)) {
					borrowRepayment();
					return "hkxq";
				} else {
					return "success";
				}
			} else {
				addActionError("您账户可用金额不足，请充值!");
				return "error_ftl";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 借款人还款
	 * 
	 * @return
	 */
	@Action(value = "/borrow/ajaxPayBack", results = { @Result(type = "json") })
	@InputConfig(resultName = "ajaxError")
	public String ajaxPayBack() {
		try {
			borrowRepaymentDetail = this.borrowRepaymentDetailService
					.get(borrowRepaymentDetail.getId());
			borrowTenderList = this.borrowTenderService
					.getBorrowTenderByBorrowId(borrowRepaymentDetail
							.getBorrowId());
			borrow = borrowService.getBorrowById(borrowRepaymentDetail
					.getBorrowId());

			if (borrow.getUserId().intValue() != this.getLoginUser().getId()
					.intValue()) {
				return ajax(Status.error, "请确认借款者是否对应!");
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
				return ajax(Status.error, "您账户可用金额不足，请充值!");
			} else if (number == 2) {
				return ajax(Status.error, "本期已还完!");
			}

//			if (mailList != null && mailList.size() > 0) {
//				Date today = new Date();
//				String todayStr = CommonUtil.getDate2String(today,
//						"yyyy-MM-dd HH:mm:ss");
//				for (MailRepayForInvestor mail : mailList) {
//					try {
//						mail.setBorrowName(borrow.getName());
//						mail.setBorrowId(borrow.getId());
//						// mail.setRepayMoney(borrowRepaymentDetail.getRepaymentAccount());
//						mail.setRepayDate(todayStr);
//						mailService.sendRepayForInvestor(mail);
//					} catch (Exception ee) {
//						ee.printStackTrace();
//					}
//				}
//			}

		} catch (Exception e) {
			e.printStackTrace();
			return ajax(Status.error, "系统错误，请联系管理员!");
		}

		return ajax(Status.success, "还款成功!");
	}

	public String borrowVerify() {
		borrow = borrowService.getBorrowById(borrow.getId());
		borrow.setStatus(3);
		return "success";
	}

	/**
	 * 用户中心用户投资列表
	 * 
	 * @return
	 */
	@Action(value = "/borrow/userTenderBorrowList", results = { @Result(name = "success", location = "/content/borrow/userTenderBorrowList.ftl", type = "freemarker") })
	public String userTenderBorrowList() {
		if (pager == null) {
			pager = new Pager();
		}
		User user = this.getLoginUser();
		Map<String, Object> qMap = new HashMap<String, Object>();
		// int[] array = {1,3};
		qMap.put("array", bStatus);
		qMap.put("userId", user.getId());
		qMap.put("keywords", keywords);
		// qMap.put("useReason", useReason);
		// qMap.put("timeLimit", timeLimit);
		// qMap.put("accountStart", accountStart);
		// qMap.put("accountEnd", accountEnd);
		// qMap.put("fundsStart", fundsStart);
		// qMap.put("fundsEnd", fundsEnd);
		qMap.put("aprStart", this.getAprStart());
		qMap.put("aprEnd", this.getAprEnd());
		qMap.put("timeLimitStart", this.getTimeLimitStart());
		qMap.put("timeLimitEnd", this.getTimeLimitEnd());
		qMap.put("bType", this.getbType());
		if (this.getOrderBy() != null) {
			if ("account_up".equals(this.getOrderBy())) {
				qMap.put("orderBy", "account");
			}
			if ("account_down".equals(this.getOrderBy())) {
				qMap.put("orderBy", "account");
			}
			if ("apr_up".equals(this.getOrderBy())) {
				qMap.put("orderBy", "apr");
			}
			if ("apr_down".equals(this.getOrderBy())) {
				qMap.put("orderBy", "apr");
			}
			if ("jindu_up".equals(this.getOrderBy())) {
				qMap.put("orderBy", "jindu");
			}
			if ("jindu_down".equals(this.getOrderBy())) {
				qMap.put("orderBy", "jindu");
			}
		}
		pager = this.getBorrowService().queryUserTenderBorrowList(pager, qMap);
		return SUCCESS;
	}

	/**
	 * 借款人-未还借款
	 * 
	 * @return
	 */
	@Action(value = "/borrow/userBorrowNoDone", results = {
			@Result(name = "success", location = "/content/borrow/user_borrow_list.ftl", type = "freemarker"),
			@Result(name = "fail", location = "/content/user/login.ftl", type = "freemarker") })
	public String userBorrowNoDone() {

		if (getLoginUser().getTypeId().intValue() == 0) {
			addActionError("只有借款人才能访问！");
			return "error_ftl";
		}

		if (pager == null) {
			pager = new Pager();
		}
		borrStatus = 3;
		User user = this.getLoginUser();
		Map<String, Object> qMap = new HashMap<String, Object>();
		// int[] array = {1,3};
		if (StringUtils.isNotEmpty(minDate) && StringUtils.isNotEmpty(maxDate)) {
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
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 1);
			qMap.put("minDate", min);
			qMap.put("maxDate", calendar.getTime());
		}
		qMap.put("userId", user.getId());
		qMap.put("keywords", keywords);

		qMap.put("orderBy", " verify_time desc");

		pager = this.getBorrowService().queryBorrowListForRepay(pager, qMap);

		return SUCCESS;

	}

	/**
	 * 借款人-未还借款
	 * 
	 * @return
	 */
	@Action(value = "/borrow/userBorrowDone", results = {
			@Result(name = "success", location = "/content/borrow/user_borrow_list.ftl", type = "freemarker"),
			@Result(name = "fail", location = "/content/user/login.ftl", type = "freemarker") })
	public String userBorrowDone() {

		if (getLoginUser().getTypeId().intValue() == 0) {
			addActionError("只有借款人才能访问！");
			return "error_ftl";
		}

		if (pager == null) {
			pager = new Pager();
		}
		borrStatus = 7;
		bStatus = new String[] { "7" };
		User user = this.getLoginUser();
		Map<String, Object> qMap = new HashMap<String, Object>();
		// int[] array = {1,3};
		if (StringUtils.isNotEmpty(minDate) && StringUtils.isNotEmpty(maxDate)) {
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
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 1);
			qMap.put("minDate", min);
			qMap.put("maxDate", calendar.getTime());
		}
		qMap.put("userId", user.getId());
		qMap.put("keywords", keywords);
		qMap.put("array", bStatus);
		qMap.put("orderBy", " verify_time desc");

		pager = this.getBorrowService().queryBorrowList(pager, qMap);

		return SUCCESS;

	}

	/**
	 * 用户中心用户借款列表
	 * 
	 * @return
	 */
	@Action(value = "/borrow/userBorrowList", results = {
			@Result(name = "success", location = "/content/borrow/user_borrow_list.ftl", type = "freemarker"),
			@Result(name = "fail", location = "/content/user/login.ftl", type = "freemarker"),
			@Result(name = "amend", location = "/content/borrow/borrow_amend_list.ftl", type = "freemarker") })
	public String userBorrowList() {
		if (pager == null) {
			pager = new Pager();
		}
		User user = this.getLoginUser();
		Map<String, Object> qMap = new HashMap<String, Object>();
		// int[] array = {1,3};
		if (StringUtils.isNotEmpty(minDate) && StringUtils.isNotEmpty(maxDate)) {
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
			parameterMap = new HashMap<String, String>();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(max);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 1);
			parameterMap.put("minDate", minDate);
			parameterMap.put("maxDate", maxDate);
			qMap.put("minDate", min);
			qMap.put("maxDate", calendar.getTime());
		}
		qMap.put("array", bStatus);
		if (bStatus.length == 1) {
			borrStatus = Integer.valueOf(bStatus[0]);
		}
		qMap.put("userId", user.getId());
		qMap.put("keywords", keywords);
		// qMap.put("useReason", useReason);
		// qMap.put("timeLimit", timeLimit);
		// qMap.put("accountStart", accountStart);
		// qMap.put("accountEnd", accountEnd);
		// qMap.put("fundsStart", fundsStart);
		// qMap.put("fundsEnd", fundsEnd);
		qMap.put("aprStart", this.getAprStart());
		qMap.put("aprEnd", this.getAprEnd());
		qMap.put("timeLimitStart", this.getTimeLimitStart());
		qMap.put("timeLimitEnd", this.getTimeLimitEnd());
		qMap.put("bType", this.getbType());
		if (this.getOrderBy() != null) {
			if ("account_up".equals(this.getOrderBy())) {
				qMap.put("orderBy", "account");
			}
			if ("account_down".equals(this.getOrderBy())) {
				qMap.put("orderBy", "account");
			}
			if ("apr_up".equals(this.getOrderBy())) {
				qMap.put("orderBy", "apr");
			}
			if ("apr_down".equals(this.getOrderBy())) {
				qMap.put("orderBy", "apr");
			}
			if ("jindu_up".equals(this.getOrderBy())) {
				qMap.put("orderBy", "jindu");
			}
			if ("jindu_down".equals(this.getOrderBy())) {
				qMap.put("orderBy", "jindu");
			}
		}
		pager = this.getBorrowService().queryBorrowList(pager, qMap);
		if (bStatus.length == 1 && bStatus[0].equals("0")) {
			return "amend";
		} else {
			return SUCCESS;
		}

	}

	/**
	 * 用户中心 借款管理
	 * 
	 * @return
	 */
	@Action(value = "/borrow/userBorrowMgmt", results = {
			@Result(name = "success", location = "/content/borrow/user_borrow_mgmt.ftl", type = "freemarker"),
			@Result(name = "fail", location = "/content/user/login.ftl", type = "freemarker") })
	public String userBorrowMgmt() {

		try {

			if (getLoginUser().getTypeId().intValue() == 0) {
				addActionError("只有借款人才能访问！");
				return "error_ftl";
			}

			if (pager == null) {
				pager = new Pager();
			}
			User user = this.getLoginUser();
			Map<String, Object> qMap = new HashMap<String, Object>();
			parameterMap = new HashMap<String, String>();
			if (StringUtils.isNotEmpty(minDate)
					&& StringUtils.isNotEmpty(maxDate)) {
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
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
				calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 1);
				parameterMap.put("minDate", minDate);
				parameterMap.put("maxDate", maxDate);
				qMap.put("minDate", min);
				qMap.put("maxDate", calendar.getTime());
			}
			if (StringUtils.isNotEmpty(keywords)) {
				String str = CommonUtil.changeChar(keywords);

				parameterMap.put("keywords", str);
				qMap.put("keywords", str);
			}
			// int[] array = {1,3};
			// qMap.put("array", bStatus);
			qMap.put("userId", user.getId());
			// qMap.put("keywords", keywords);
			// qMap.put("useReason", useReason);
			// qMap.put("timeLimit", timeLimit);
			// qMap.put("accountStart", accountStart);
			// qMap.put("accountEnd", accountEnd);
			// qMap.put("fundsStart", fundsStart);
			// qMap.put("fundsEnd", fundsEnd);
			qMap.put("aprStart", this.getAprStart());
			qMap.put("aprEnd", this.getAprEnd());
			qMap.put("timeLimitStart", this.getTimeLimitStart());
			qMap.put("timeLimitEnd", this.getTimeLimitEnd());
			qMap.put("bType", this.getbType());
			if (borrStatus == null) {
				borrStatus = 111;
			}
			if (borrStatus != 111) {
				qMap.put("borrStatus", this.getBorrStatus());
			}
			qMap.put("orderBy", "create_date desc");

			if (this.getOrderBy() != null) {
				if ("account_up".equals(this.getOrderBy())) {
					qMap.put("orderBy", "account");
				}
				if ("account_down".equals(this.getOrderBy())) {
					qMap.put("orderBy", "account");
				}
				if ("apr_up".equals(this.getOrderBy())) {
					qMap.put("orderBy", "apr");
				}
				if ("apr_down".equals(this.getOrderBy())) {
					qMap.put("orderBy", "apr");
				}
				if ("jindu_up".equals(this.getOrderBy())) {
					qMap.put("orderBy", "jindu");
				}
				if ("jindu_down".equals(this.getOrderBy())) {
					qMap.put("orderBy", "jindu");
				}
			}
			pager = this.getBorrowService().queryBorrowList(pager, qMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 投标明细
	 * 
	 * @return
	 */
	@Action(value = "/borrow/bidBorrowDetailList", results = { @Result(name = "success", location = "/content/borrow/bidBorrowDetailList.ftl", type = "freemarker") })
	public String getBidBorrowDetailList() {
		try {

			if (getLoginUser().getTypeId().intValue() != 0) {
				addActionError("只有投资人才能访问！");
				return "error_ftl";
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", getLoginUser().getId());
			map.put("orderBy", " d.repayment_date ");
			parameterMap = new HashMap<String, String>();

			if (StringUtils.isNotEmpty(minDate)) {
				Date min = CommonUtil.getString2Date(minDate, "yyyy-MM-dd");
				if (min == null) {
					addActionError("参数错误");
					return "error_ftl";
				}
				parameterMap.put("minDate", minDate);
				map.put("minDate", min);
			}

			if (StringUtils.isNotEmpty(maxDate)) {
				Date max = CommonUtil.getString2Date(maxDate, "yyyy-MM-dd");
				if (max == null) {
					addActionError("参数错误");
					return "error_ftl";
				}
				parameterMap.put("maxDate", maxDate);
				max = CommonUtil.date2end(max);
				map.put("maxDate", max);
			}

			if (StringUtils.isNotEmpty(keywords)) {
				parameterMap.put("keywords", keywords);
				map.put("keywords", keywords);
			}
			if (StringUtils.isNotEmpty(status)) {
				parameterMap.put("status", status);
				map.put("status", status);
			}
			// pager =
			// borrowRepaymentDetailService.queryUncollectedDetailList(pager,
			// map);
			pager = userRepaymentDetailService
					.queryUserRepaymentDetailPagerForIncome(pager, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 续投设置列表
	 * 
	 * @return
	 */
	@Action(value = "/borrow/bidSetRollList", results = { @Result(name = "success", location = "/content/borrow/roll_outList.ftl", type = "freemarker") })
	public String getBidSetRollList() {
		try {

			if (getLoginUser().getTypeId().intValue() != 0) {
				addActionError("只有投资人才能访问！");
				return "error_ftl";
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", getLoginUser().getId());
			map.put("orderBy", " d.repayment_date ");
			parameterMap = new HashMap<String, String>();

			if (StringUtils.isNotEmpty(minDate)) {
				Date min = CommonUtil.getString2Date(minDate, "yyyy-MM-dd");
				if (min == null) {
					addActionError("参数错误");
					return "error_ftl";
				}
				parameterMap.put("minDate", minDate);
				map.put("minDate", min);
			}

			if (StringUtils.isNotEmpty(maxDate)) {
				Date max = CommonUtil.getString2Date(maxDate, "yyyy-MM-dd");
				if (max == null) {
					addActionError("参数错误");
					return "error_ftl";
				}
				parameterMap.put("maxDate", maxDate);
				max = CommonUtil.date2end(max);
				map.put("maxDate", max);
			}

			if (StringUtils.isNotEmpty(keywords)) {
				parameterMap.put("keywords", keywords);
				map.put("keywords", keywords);
			}

			map.put("status", 0);
			// pager =
			// borrowRepaymentDetailService.queryUncollectedDetailList(pager,
			// map);
			pager = userRepaymentDetailService
					.queryUserRepaymentDetailPagerForIncome(pager, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 未收细账列表
	 * 
	 * @return
	 */
	@Action(value = "/borrow/uncollectedDetailList", results = { @Result(name = "success", location = "/content/borrow/borrowUncollectedDetailList.ftl", type = "freemarker") })
	public String getUncollectedDetailList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", getLoginUser().getId());
		map.put("status", 0);
		pager = userRepaymentDetailService.queryUserRepaymentDetailPager(pager,	map);

		return SUCCESS;
	}

	/**
	 * 投资者收益明细
	 * 
	 * @return
	 */
	@Action(value = "/borrow/receivedDetailList", results = { @Result(name = "success", location = "/content/borrow/borrowReceivedDetailList.ftl", type = "freemarker") })
	public String getReceivedDetailList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", getLoginUser().getId());
		parameterMap = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(minDate) && StringUtils.isNotEmpty(maxDate)) {
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
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 1);
			parameterMap.put("minDate", minDate);
			parameterMap.put("maxDate", maxDate);
			map.put("minDate", min);
			map.put("maxDate", calendar.getTime());
		}

		if (StringUtils.isNotEmpty(keywords)) {
			parameterMap.put("keywords", keywords);
			map.put("keywords", keywords);
		}
		if (StringUtils.isNotEmpty(status)) {
			parameterMap.put("status", status);
			map.put("status", status);
		}
		pager = userRepaymentDetailService.queryUserRepaymentDetailPager(pager,
				map);
		return SUCCESS;
	}

	/**
	 * 投资者投资记录
	 * 
	 * @return
	 */
	@Action(value = "/userCenter/borrowDetailList", results = { @Result(name = "success", location = "/content/user/usercenter.ftl", type = "freemarker") })
	public String getBorrowDetailList() {

		User user = getLoginUser();
		if (user==null) {
			msg="请登录！";
			msgUrl="/user/login.do";
			return "error_ftl";
		}
		
		user = userService.get(user.getId());
		if(StringUtils.isBlank(user.getPayPassword())){
			noPayPsw = 1;
		}
		realStatus = user.getRealStatus();
		if(StringUtils.isEmpty(type)){
			type="0";
		}
		pager.setPageSize(5);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", getLoginUser().getId());
		if(type.compareTo("0")==0||type.compareTo("1")==0){
			map.put("orderBy", " d.repayment_date ");
			parameterMap = new HashMap<String, String>();

				parameterMap.put("status", type);
				map.put("status", type);
			pager = userRepaymentDetailService
					.queryUserRepaymentDetailPagerForIncome(pager, map);
		}else if(type.compareTo("2")==0){
			
			
			pager = borrowTenderService.queryUncollectedDetailList(pager, map);
		}
		
		return SUCCESS;
	}
	/**
	 * 借款人 未还款明细
	 * 
	 * @return
	 */
	@Action(value = "/borrow/tzborrowDetail", results = { @Result(name = "success", location = "/content/borrow/borrow_tzdetail.ftl", type = "freemarker") })
	public String tzborrowDetail() {
		BorrowTender bt = new BorrowTender();
		borrowTender = borrowTenderService.getById(Integer.valueOf(id), bt );
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", getLoginUser().getId());
		map.put("borrowId", borrowTender.getBorrowId());
		userRepDetailList =userRepaymentDetailService.queryUserRepaymentDetailList(map);
		reTime = userRepDetailList.get(0).getCreateDate();
		return SUCCESS;
	}
		
	

	/**
	 * 借款人 未还款明细
	 * 
	 * @return
	 */
	@Action(value = "/borrow/hkmx", results = { @Result(name = "success", location = "/content/borrow/borrower_hkmx_list.ftl", type = "freemarker") })
	public String getBorrowHkmxList() {

		try {

			if (getLoginUser().getTypeId().intValue() == 0) {
				addActionError("只有借款人才能访问！");
				return "error_ftl";
			}

			if (pager == null) {
				pager = new Pager();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", getLoginUser().getId());
			map.put("status", 0);
			if (StringUtils.isNotEmpty(minDate)
					&& StringUtils.isNotEmpty(maxDate)) {
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
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
				calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 1);
				map.put("minDate", min);
				map.put("maxDate", calendar.getTime());
			}
			if (StringUtils.isNotEmpty(keywords)) {
				String str = null;
				if (StringUtils.isNotEmpty(keywords)) {
					str = CommonUtil.changeChar(keywords);
				}
				map.put("keywords", str);
			}
			pager = borrowRepaymentDetailService.queryBorrowerDetailList(pager,
					map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 借款人 已还款明细
	 * 
	 * @return
	 */
	@Action(value = "/borrow/hkmxyet", results = { @Result(name = "success", location = "/content/borrow/borrower_hkmx_list.ftl", type = "freemarker") })
	public String getBorrowHkmxYetList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", getLoginUser().getId());
		map.put("status", 0);
		pager = borrowRepaymentDetailService
				.queryBorrowerDetailList(pager, map);
		return SUCCESS;
	}

	/**
	 * 借款人 借款明细
	 * 
	 * @return
	 */
	// @Validations(
	// requiredStrings = {
	// @RequiredStringValidator(fieldName = "sign", message = "参数错误!")
	// }
	// )
	@Action(value = "/borrow/jkmx", results = { @Result(name = "success", location = "/content/borrow/borrower_jkmx_list.ftl", type = "freemarker") })
	@InputConfig(resultName = "error_ftl")
	public String getBorrowJkmxList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", getLoginUser().getId());
		int status[] = { 3, 7 };
		map.put("status", status);
		if (id != null && id.matches("[0-9]+")) {
			map.put("borrowId", id);
			borrow = borrowService.getBorrowById(Integer.parseInt(id));
		}
		if (StringUtils.isNotEmpty(minDate) && StringUtils.isNotEmpty(maxDate)) {
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
			parameterMap = new HashMap<String, String>();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(max);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 1);
			parameterMap.put("minDate", minDate);
			parameterMap.put("maxDate", maxDate);
			map.put("minDate", min);
			map.put("maxDate", calendar.getTime());
		}

		pager = borrowTenderService.queryJkmxList(pager, map);
		return SUCCESS;
	}

	/**
	 * 查询要修改标的详细信息
	 * 
	 * @return
	 */
	@Action(value = "/borrow/borrowMessage", results = {
			@Result(name = "Pledge", location = "/content/borrow/borrow_mess.ftl", type = "freemarker"),
			@Result(name = "second", location = "/content/borrow/borrow_Second.ftl", type = "freemarker"),
			@Result(name = "month", location = "/content/borrow/updateMonthBorrow.ftl", type = "freemarker"),
			@Result(name = "wander", location = "/content/borrow/updateInputWander.ftl", type = "freemarker") })
	public String getBorrowMessage() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> timeMap = new HashMap<String, Object>();
		borrow = borrowService.getBorrowById(borrow.getId());

		if (borrow.getUserId().intValue() != getLoginUser().getId().intValue()) {
			addActionError("非借款者本人!");
			return "error_ftl";
		}

		// map.put("sign", "borrowtype");
		// map.put("keyValue", borrow.getType());
		// typeName =listingService.findListing(map).getName();
		if ("1".equals(borrow.getType()) || "4".equals(borrow.getType())) {
			timeMap.put("sign", "borrow_valid_time");
			timeMap.put("description", borrow.getValidTime());
			effectiveTime = listingService.findListing(timeMap).getName();
		}
		if ("1".equals(borrow.getType())) {
			PaymentView retView = interestCalUtil.payback(
					Double.parseDouble(borrow.getAccount()),
					Double.parseDouble(borrow.getAccount()), borrow.getApr(),
					Integer.valueOf(borrow.getTimeLimit()),
					borrow.getBorStages(), 2);
			monthPaymentList = retView.getPaymentDetail();
			for (MonthPaymentDetail monthPayment : monthPaymentList) {
				Double strAccount = Double.parseDouble(monthPayment
						.getBenjinM())
						+ Double.parseDouble(monthPayment.getLixiM());
				strMess += monthPayment.getDateNum() + "," + strAccount + ","
						+ monthPayment.getBenjinM() + ","
						+ monthPayment.getLixiM() + ";";
			}
			return "Pledge";
		} else if ("0".equals(borrow.getType())) {
			return "second";
		} else if ("2".equals(borrow.getType())) {
			return "wander";
		} else if ("4".equals(borrow.getType())) {
			PaymentView retView = interestCalUtil.payback(
					Double.parseDouble(borrow.getAccount()),
					Double.parseDouble(borrow.getAccount()), borrow.getApr(),
					Integer.valueOf(borrow.getTimeLimit()),
					borrow.getBorStages(), 1);
			monthPaymentList = retView.getPaymentDetail();
			for (MonthPaymentDetail monthPayment : monthPaymentList) {
				Double strAccount = Double.parseDouble(monthPayment
						.getBenjinM())
						+ Double.parseDouble(monthPayment.getLixiM());
				strMess += monthPayment.getDateNum() + "," + strAccount + ","
						+ monthPayment.getBenjinM() + ","
						+ monthPayment.getLixiM() + ";";
			}
			return "month";
		} else if ("11".equals(borrow.getType())||"12".equals(borrow.getType())||"14".equals(borrow.getType())||"15".equals(borrow.getType())) {
			
			User user = getLoginUser();
			if (user.getTypeId() != 12&&user.getTypeId() != 1) {
				addActionError("确认借款人!");
				return "error_ftl";
			}
			borrow = borrowService.getBorrowById(borrow.getId());
			if (user.getId().intValue() != borrow.getUserId().intValue()) {
				addActionError("续标人必须是原借款人!");
				return "error_ftl";
			}
			 borrowType =Integer.parseInt( borrow.getType());
			if (borrowType == 12||borrowType == 14) {
				borrowIsday = 0;
			} else {
				borrowIsday = 1;
			}
			borrowImgList = new ArrayList<NoteImg>();
			if (StringUtils.isNotEmpty(borrow.getBorImage())) {
				NoteImg[] items = net.arnx.jsonic.JSON.decode(borrow.getBorImage(), NoteImg[].class);
				for(NoteImg ig:items) {
					ig.setUrl(CommonUtil.encodeUrl(ig.getUrl()));
					borrowImgList.add(ig);
				}
			}
			String carInfoMes = borrow.getBorrowInfoJson();
			if (carInfoMes != null && !"".equals(carInfoMes.trim())) {

				try {
					JSONObject  jsonObject = JSONObject.fromObject(carInfoMes);
					carInfo = (CarInfoJson) JSONObject.toBean(jsonObject, CarInfoJson.class);
				} catch (JSONException je) {
					carInfo  = new CarInfoJson();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			return "month";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 修改未审核的质押标信息
	 * 
	 * @return
	 */
	@Action(value = "/borrow/updateBorrow", results = { @Result(name = "success", location = "userBorrowMgmt.do", type = "redirectAction") })
	public String updateBorrow() {
		User user = getLoginUser();
		Borrow borrow1 = borrowService.getBorrowById(borrow.getId());

		if (borrow1.getUserId().intValue() != getLoginUser().getId().intValue()) {
			addActionError("非借款者本人!");
			return "error_ftl";
		}
		if (borrow1.getStatus() == 0) {
			String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
			setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
			if (!mycode.equals(random)) {
				addActionError("验证码输入错误!");
				return "error_ftl";
			}
			if (borrow.getName().equals("") || borrow.getName() == null) {
				addActionError("标题不能为空，请重新输入!");
				return "error_ftl";
			} else if (borrow.getAccount() == null
					|| borrow.getAccount().equals("")) {
				addActionError("借款金额不能为空，请重新输入!");
				return "error_ftl";
			} else if (borrow.getContent() == null
					|| borrow.getContent().equals("")) {
				addActionError("标内容不能为空，请重新输入!");
				return "error_ftl";
			} else if (borrow.getApr() <= 0) {
				addActionError("请输入正确的借款利率!");
				return "error_ftl";
			} else if (!borrow.getAward().equals("0")) {
				if (borrow.getFunds().equals("")
						|| borrow.getFunds().equals("0")) {
					addActionError("请输入正确的奖励!");
					return "error_ftl";
				}
			} else if (isDxb1 != null
					&& (borrow.getPwd() == null || "".equals(borrow.getPwd()))) {
				addActionError("请设置定向密码!");
				return "error_ftl";
			}
			if (borrow.getIsDxb().equals("1") && borrow.getPwd().equals("")) {
				addActionError("请输入定向密码!");
				return "error_ftl";
			}
//			String total = CalculationUtil.totalMoney(borrow);
			borrow.setSchedule("0");
//			borrow.setRepaymentAccount(total);
			borrow.setUserId(user.getId());
			borrow.setOperatorIp(getRequestRemoteAddr());
			borrow.setUpdatePersion(String.valueOf(user.getId()));
			borrow.setModifyDate(new Date());
			borrow.setBalance(borrow.getAccount());
			borrow.setTenderTimes("0");// 投标次数
			borrow.setStatus(0);
			if (borrow.getBorStages() != null
					&& !"".equals(borrow.getBorStages().trim())) {

				String stg = borrow.getBorStages();
				if (stg.endsWith(":")) {
					stg = stg.substring(0, stg.length() - 1);
				}

				String[] tgs = stg.split(":");
				borrow.setDivides(tgs.length);
			}
			String firstImage = null;
			if (borImagesFile != null && borImagesFile.length > 0) {
				String tmp = null;
				for (File fl : borImagesFile) {
					if (fl != null) {
						String flPath = ImageUtil.copyImageFile(
								getServletContext(), fl);
						if (flPath != null && !"".equals(flPath)) {
							if (tmp == null) {
								tmp = flPath;
								firstImage = flPath;
							} else {
								tmp += "," + flPath;
							}
						}
					}
				}
				borrow.setBorImage(tmp);
				borrow.setBorImageFirst(firstImage);
			} else {
				borrow.setBorImage(borrow1.getBorImage());
				borrow.setBorImageFirst(borrow1.getBorImageFirst());
			}

			borrowService.updateBorrowMess(borrow);
			return SUCCESS;
		} else {
			addActionError("此标状态发生改变不能进行修改，请联系网站管理员");
			return "error_ftl";
		}
	}

	/**
	 * updateBorMonth 修改月标
	 * 
	 * @author zhanf
	 * @return
	 */
	@Action(value = "/borrow/updateBorMonth", results = { @Result(name = "success", location = "/content/borrow/success.ftl", type = "freemarker") })
	public String updateBorMonth() {

		// Map<String,User> map = (Map<String, User>)
		// this.getMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME);
		// User user = map.get(ConstantUtil.USER_NAME);
		User user = getLoginUser();
		Borrow borrow1 = borrowService.getBorrowById(borrow.getId());
		if (borrow1.getUserId().intValue() != getLoginUser().getId().intValue()) {
			addActionError("非借款者本人!");
			return "error_ftl";
		}
		if (borrow1.getStatus() == 0) {
			String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
			setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
			if (!mycode.equals(random)) {
				addActionError("验证码输入错误!");
				return "error_ftl";
			}
			if (borrow.getName().equals("") || borrow.getName() == null) {
				addActionError("标题不能为空，请重新输入!");
				return "error_ftl";
			} else if (borrow.getAccount() == null
					|| borrow.getAccount().equals("")) {
				addActionError("借款金额不能为空，请重新输入!");
				return "error_ftl";
			} else if (borrow.getContent() == null
					|| borrow.getContent().equals("")) {
				addActionError("标内容不能为空，请重新输入!");
				return "error_ftl";
			} else if (borrow.getApr() <= 0) {
				addActionError("请输入正确的借款利率!");
				return "error_ftl";
			} else if (borrow.getTimeLimit().equals("")
					|| borrow.getTimeLimit() == null) {
				addActionError("请输入正确的借款天数!");
				return "error_ftl";
			} else if (Integer.parseInt(borrow.getTimeLimit()) > 12
					|| Integer.parseInt(borrow.getTimeLimit()) % 1 != 0) {
				addActionError("请输入正确的借款月数!");
				return "error_ftl";
			} else if (borrow.getBorStages().equals("0")) {
				addActionError("请设置正确的还款计划!");
				return "error_ftl";
			} else if (!borrow.getAward().equals("0")) {
				if (borrow.getFunds().equals("")
						|| borrow.getFunds().equals("0")) {
					addActionError("请输入正确的奖励!");
					return "error_ftl";
				}
			} else if (isDxb1 != null
					&& (borrow.getPwd() == null || "".equals(borrow.getPwd()))) {
				addActionError("请设置定向密码!");
				return "error_ftl";
			}
			if (borrow.getIsDxb().equals("1") && borrow.getPwd().equals("")) {
				addActionError("请输入定向密码!");
				return "error_ftl";
			}
			double borTotal = Double.parseDouble(borrow.getAccount());
			double money = 0;
			String str[] = borrow.getBorStages().split(":");
			int num = str.length;
			String dayStr[] = new String[num];
			String moneyStr[] = new String[num];
			for (int i = 0; i < str.length; i++) {
				String str1[] = str[i].split(",");
				dayStr[i] = str1[0];
				moneyStr[i] = String
						.valueOf((Double.parseDouble(str1[1]) / borTotal)
								* borTotal);// 按比例计算每期还款金额
				money += Double.parseDouble(moneyStr[i]);
			}
			if (Double.parseDouble(borrow.getAccount()) != money) {
				addActionError("请选择正确的还款期限和还款金额!");
				return "error_ftl";
			}
			String total = CalculationUtil.totalMonthMoney(borrow);// 计算月标预计还款总额（不计算奖励)
			borrow.setSchedule("0");
			borrow.setRepaymentAccount(total);
			borrow.setUserId(user.getId());
			borrow.setOperatorIp(getRequestRemoteAddr());
			borrow.setUpdatePersion(String.valueOf(user.getId()));
			borrow.setModifyDate(new Date());
			borrow.setBalance(borrow.getAccount());
			borrow.setTenderTimes("0");// 投标次数
			borrow.setStatus(0);

			if (borrow.getBorStages() != null
					&& !"".equals(borrow.getBorStages().trim())) {

				String stg = borrow.getBorStages();
				if (stg.endsWith(":")) {
					stg = stg.substring(0, stg.length() - 1);
				}

				String[] tgs = stg.split(":");
				borrow.setDivides(tgs.length);
			}

			String firstImage = null;
			if (borImagesFile != null && borImagesFile.length > 0) {
				String tmp = null;
				for (File fl : borImagesFile) {
					if (fl != null) {
						String flPath = ImageUtil.copyImageFile(
								getServletContext(), fl);
						if (flPath != null && !"".equals(flPath)) {
							if (tmp == null) {
								tmp = flPath;
								firstImage = flPath;
							} else {
								tmp += "," + flPath;
							}
						}
					}
				}
				borrow.setBorImage(tmp);
				borrow.setBorImageFirst(firstImage);
			} else {
				borrow.setBorImage(borrow1.getBorImage());
				borrow.setBorImageFirst(borrow1.getBorImageFirst());
			}

			log.info("添加标");
			this.getBorrowService().addBorrow(borrow);

			return "success";
		} else {
			addActionError("此标状态发生改变不能进行修改，请联系网站管理员");
			return "error_ftl";
		}
	}

	/**
	 * 修改未审核的质押标信息
	 * 
	 * @return
	 */
	@Action(value = "/borrow/updateSecondBorrow", results = { @Result(name = "success", location = "userBorrowMgmt.do", type = "redirectAction") })
	public String updateSecondBorrow() {

		User user = getLoginUser();
		Borrow borrow1 = borrowService.getBorrowById(borrow.getId());

		if (borrow1.getUserId().intValue() != getLoginUser().getId().intValue()) {
			addActionError("非借款者本人!");
			return "error_ftl";
		}

		if (borrow1.getStatus() == 0) {
			String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
			setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
			if (!mycode.equals(random)) {
				addActionError("验证码输入错误!");
				return "error_ftl";
			}
			if (borrow.getName().equals("") || borrow.getName() == null) {
				addActionError("标题不能为空，请重新输入!");
				return "error_ftl";
			} else if (borrow.getAccount() == null
					|| borrow.getAccount().equals("")) {
				addActionError("借款金额不能为空，请重新输入!");
				return "error_ftl";
			} else if (borrow.getContent() == null
					|| borrow.getContent().equals("")) {
				addActionError("标内容不能为空，请重新输入!");
				return "error_ftl";
			} else if (borrow.getApr() <= 0) {
				addActionError("请输入正确的借款利率!");
				return "error_ftl";
			} else if (isDxb1 != null
					&& (borrow.getPwd() == null || "".equals(borrow.getPwd()))) {
				addActionError("请设置定向密码!");
				return "error_ftl";
			} else if (borrow.getIsDxb().equals("1")
					&& borrow.getPwd().equals("")) {
				addActionError("请输入定向密码!");
				return "error_ftl";
			}
			String total = CalculationUtil.totalMoney(borrow);
			borrow1.setSchedule("0");
			borrow1.setRepaymentAccount(total);
			borrow1.setUserId(user.getId());
			borrow1.setOperatorIp(getRequestRemoteAddr());
			borrow1.setUpdatePersion(String.valueOf(user.getId()));
			borrow1.setModifyDate(new Date());
			borrow1.setBalance(borrow.getAccount());
			borrow1.setTenderTimes("0");// 投标次数
			borrow1.setAward("0");
			// borrow.setIsDxb("0");
			borrow1.setTimeLimit("1");
			borrow1.setType("0");
			borrow1.setDivides(1);
			borrow1.setBorStages("1," + borrow.getAccount());

			borrow1.setAccount(borrow.getAccount());
			borrow1.setName(borrow.getName());
			borrow1.setApr(borrow.getApr());
			borrow1.setLowestAccount(borrow.getLowestAccount());
			borrow1.setMostAccount(borrow.getMostAccount());
			borrow1.setContent(borrow.getContent());
			borrow1.setIsDxb(borrow.getIsDxb());
			if ("1".equals(borrow.getIsDxb())) {
				borrow1.setPwd(borrow.getPwd());
			}

			String firstImage = null;
			if (borImagesFile != null && borImagesFile.length > 0) {
				String tmp = null;
				for (File fl : borImagesFile) {
					if (fl != null) {
						String flPath = ImageUtil.copyImageFile(
								getServletContext(), fl);
						if (flPath != null && !"".equals(flPath)) {
							if (tmp == null) {
								tmp = flPath;
								firstImage = flPath;
							} else {
								tmp += "," + flPath;
							}
						}
					}
				}
				borrow1.setBorImage(tmp);
				borrow1.setBorImageFirst(firstImage);
			} else {
				borrow1.setBorImage(borrow1.getBorImage());
				borrow1.setBorImageFirst(borrow1.getBorImageFirst());
			}
			borrowService.updateBorrowMess(borrow1);
			return SUCCESS;
		} else {
			addActionError("此标状态发生改变不能进行修改，请联系网站管理员");
			return "error_ftl";
		}
	}

	/**
	 * updateBorWander 修改流转标
	 * 
	 * @return
	 */
	@Action(value = "/borrow/updateBorWander", results = { @Result(name = "success", location = "userBorrowMgmt.do", type = "redirectAction") })
	public String updateBorWander() {

		User user = getLoginUser();
		Borrow borrow1 = borrowService.getBorrowById(borrow.getId());

		if (borrow1.getUserId().intValue() != getLoginUser().getId().intValue()) {
			addActionError("非借款者本人!");
			return "error_ftl";
		}

		if (borrow1.getStatus() == 0) {
			String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
			setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
			if (!mycode.equals(random)) {
				addActionError("验证码输入错误!");
				return "error_ftl";
			}
			if (borrow.getName() == null || "".equals(borrow.getName())) {
				addActionError("标题不能为空，请重新输入!");
				return "error_ftl";
			} else if (borrow.getAccount() == null
					|| borrow.getAccount().equals("")) {
				addActionError("借款金额不能为空，请重新输入!");
				return "error_ftl";
			} else if (borrow.getApr() <= 0) {
				addActionError("请输入正确的借款利率!");
				return "error_ftl";
			} else if (borrow.getWanderPieceSize() == null
					|| borrow.getWanderPieceSize() <= 0) {
				addActionError("请输入正确的认购总份数!");
				return "error_ftl";
			} else if (!WanderUtil.isMod(borrow.getAccount(),
					String.valueOf(borrow.getWanderPieceSize()))) {
				addActionError("请输入正确的认购总份数和金额!");
				return "error_ftl";
			} else if (!borrow.getAward().equals("0")
					&& (borrow.getFunds().equals("") || borrow.getFunds()
							.equals("0"))) {
				addActionError("请输入正确的奖励!");
				return "error_ftl";
			} else if (borrow.getTimeLimit() == null
					|| "".equals(borrow.getTimeLimit())
					|| !this.isNumeric(borrow.getTimeLimit())) {
				addActionError("请输入正确的借款时长!");
				return "error_ftl";
			} else if (!WanderUtil.isMod(borrow.getTimeLimit(),
					String.valueOf(borrow.getWanderRedeemTimes()))) {
				addActionError("请输入正确的借款时长和借款分期!");
				return "error_ftl";
			} else if (borrow.getWanderStages() == null
					|| borrow.getWanderStages().equals("")) {
				addActionError("请设置回购计划!");
				return "error_ftl";
			} else if (isDxb1 != null
					&& (borrow.getPwd() == null || "".equals(borrow.getPwd()))) {
				addActionError("请设置定向密码!");
				return "error_ftl";
			} else if (borrow.getIsDxb().equals("1")
					&& borrow.getPwd().equals("")) {
				addActionError("请输入定向密码!");
				return "error_ftl";
			} else if (borrow.getContent() == null
					|| borrow.getContent().equals("")) {
				addActionError("标内容不能为空，请重新输入!");
				return "error_ftl";
			}

			borrow.setSchedule("0");
			borrow.setRepaymentAccount(borrow.getAccount());
			borrow.setUserId(user.getId());
			borrow.setOperatorIp(getRequestRemoteAddr());
			borrow.setUpdatePersion(String.valueOf(user.getId()));
			borrow.setModifyDate(new Date());
			borrow.setCreateDate(new Date());
			borrow.setAddTime(new Date());
			borrow.setBalance(borrow.getAccount());
			borrow.setTenderTimes("0");// 投标次数
			borrow.setType("2");

			if (borrow.getWanderStages() != null
					&& !"".equals(borrow.getWanderStages().trim())) {

				String stg = borrow.getWanderStages();
				if (stg.endsWith(":")) {
					stg = stg.substring(0, stg.length() - 1);
				}

				String[] tgs = stg.split(":");
				borrow.setDivides(tgs.length);

			}
			borrow.setWanderPieceMoney(WanderUtil.moneyForWanderPiece(borrow));

			String firstImage = null;
			if (borImagesFile != null && borImagesFile.length > 0) {
				String tmp = null;
				for (File fl : borImagesFile) {
					if (fl != null) {
						String flPath = ImageUtil.copyImageFile(
								getServletContext(), fl);
						if (flPath != null && !"".equals(flPath)) {
							if (tmp == null) {
								tmp = flPath;
								firstImage = flPath;
							} else {
								tmp += "," + flPath;
							}
						}
					}
				}
				borrow.setBorImage(tmp);
				borrow.setBorImageFirst(firstImage);
			} else {
				borrow.setBorImage(borrow1.getBorImage());
				borrow.setBorImageFirst(borrow1.getBorImageFirst());
			}
			borrowService.updateBorrowMess(borrow);
			return SUCCESS;
		} else {
			addActionError("此标状态发生改变不能进行修改，请联系网站管理员");
			return "error_ftl";
		}
	}

	/**
	 * 撤回未审核发布的标
	 * 
	 * @return
	 */
	@Action(value = "/borrow/ajaxBorrowRecall", results = { @Result(type = "json") })
	@InputConfig(resultName = "ajaxError")
	public String ajaxBorrowRecall() {
		log.info("撤回未审核发布的标");
		try {

			Boolean flag = false;
			Borrow borrow1 = borrowService.getBorrowById(borrow.getId());

			if (borrow1.getUserId().intValue() != getLoginUser().getId()
					.intValue()) {
				addActionError("非借款者本人!");
				return "error_ftl";
			}

			if (borrow1.getStatus() == 0) {
				flag = true;
			}
			if (flag) {
				borrow1.setStatus(6);
				this.borrowService.updateBorrowMess(borrow1);
				return ajax(Status.success, "撤回成功!");
			} else {
				return ajax(Status.error, "此借款状态发生改变!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajax(Status.error, "参数错误!");
		}
	}

	/**
	 * 删除不成功的标
	 * 
	 * @return
	 */
	@Action(value = "/borrow/ajaxDelectBorrow", results = { @Result(type = "json") })
	@InputConfig(resultName = "ajaxError")
	public String ajaxDelectBorrow() {
		log.info("删除不成功的标");
		Boolean flag = false;
		Borrow borrow1 = borrowService.getBorrowById(borrow.getId());
		if (borrow1.getStatus() == 6) {
			flag = true;
		}
		if (flag) {
			borrow1.setStatus(8);
			this.borrowService.updateBorrowMess(borrow1);
			// this.borrowService.delectBorrow(borrow.getId());
			return ajax(Status.success, "删除成功!");
		} else {
			return ajax(Status.error, "参数错误!");
		}
	}

	/**
	 * 借款协议
	 * 
	 * @return
	 */
	@Action(value = "/borrow/borrowAgreement", results = { @Result(name = "agree1", location = "/content/borrow/borrowAgreement.ftl", type = "freemarker") ,
			 @Result(name = "agree2", location = "/content/borrow/borrowAgreement2.ftl", type = "freemarker") })
	public String borrowAgreement() {
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
		borrowTenderList = this.borrowTenderService
				.getBorrowTenderByBorrowId(borrow.getId());// 当前标的投标记录取得
		borrow = borrowService.getBorrowById(borrow.getId());
		Map<String, Object> uMap = new HashMap<String, Object>();
		uMap.put("id", borrow.getUserId());
		borrowUser = userService.getUser(uMap);// 借款者信息取得
		userInfo = userinfoService.findByUserId(borrowUser.getId());// 借款人详情信息取得
		
		if(StringUtils.isNotEmpty(borrow.getBorrowInfoJson())){
			carInfoJson = JsonUtil.toObject(borrow.getBorrowInfoJson(), CarInfoJson.class);
		}
		Date succTime = new Date();
		String time = null;
		Calendar calendar = Calendar.getInstance();
		borrowTender = borrowTenderService.getById(Integer.parseInt(id), new BorrowTender());
		
		if (("11").equals(borrow.getType()) || ("12").equals(borrow.getType())||("14").equals(borrow.getType()) || ("15").equals(borrow.getType())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("borrowId", borrow.getId());
			map.put("userId", getLoginUser().getId());
			userRepayDetailList = userRepaymentDetailService
					.queryUserRepaymentDetailList(map);
			if (userRepayDetailList.size() > 0) {
				for (UserRepaymentDetail userRepayDetail : userRepayDetailList) {
					if (userRepayDetail.getUserId() == getLoginUser().getId()) {
						succTime = userRepayDetail.getCreateDate();
					}
				}
				calendar.setTime(succTime);
				SimpleDateFormat simple1 = new SimpleDateFormat("yyyy-MM-dd");
				lastRdate = simple1.format(calendar.getTime());

				time = simple.format(succTime);
			}
			time = simple.format(borrow.getSuccessTime());
			
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("id", userRepayDetailList.get(0).getUserId());
			user = userService.getUser(map1);// 投资人信息取得
			
		} else {
			time = simple.format(borrow.getSuccessTime());
		}

		agreementId = time + borrow.getId();
		calendar.setTime(borrow.getSuccessTime());
		calendar.set(
				Calendar.DATE,
				calendar.get(Calendar.DATE)
						+ Integer.parseInt(borrow.getTimeLimit()));
		SimpleDateFormat simple1 = new SimpleDateFormat("yyyy-MM-dd");
		if ("12".equals(borrow.getType())||"15".equals(borrow.getType())) {
			Date lasTime = CommonUtil.getMonthAfter(new Date(),
					Integer.parseInt(borrow.getTimeLimit()));
			lastRdate = simple1.format(lasTime);
		} else {
			lastRdate = simple1.format(calendar.getTime());
		}
		
//		if(borrow.getIsDo().equals("2")){
			return "agree1";//债权转让
//		}else{
//			accountBankList = userService.queryAccountBank(getLoginUser().getId());
//			return "agree2";//直接出让
//		}
	}

	public String getShowButtonName() {
		if (borrow == null)
			return "立即投资";
		if (borrow.getStatus() == 1) {
			if (countDown == -1) {
				return "已过期";
			} else if (countDown == 0) {
				return "等待复审";
			} else {
				return "立即投标";
			}
		} else if (borrow.getStatus() == 3) {
			return "收益中";
		} else if (borrow.getStatus() == 5) {
			return "等待复审";
		} else if (borrow.getStatus() == 7) {
			return "已还完";
		}
		return "立即投资";

	}

	public int getShowButtonNameFlg() {
		if (borrow == null)
			return 1;
		if (borrow.getStatus() == 1) {
			if (countDown == -1) {
				return 0;
			} else if (countDown == 0) {
				return 0;
			} else {
				return 1;
			}
		} else if (borrow.getStatus() == 3) {
			return 0;
		} else if (borrow.getStatus() == 5) {
			return 0;
		} else if (borrow.getStatus() == 7) {
			return 0;
		}
		return 1;

	}

	public String[] getVouchers() {
		return vouchers;
	}

	public void setVouchers(String[] vouchers) {
		this.vouchers = vouchers;
	}

	public List<NoteImgList> getVoucherImgList() {
		return voucherImgList;
	}

	public void setVoucherImgList(List<NoteImgList> voucherImgList) {
		this.voucherImgList = voucherImgList;
	}

	public String getBorImageFirst() {
		return borImageFirst;
	}

	public void setBorImageFirst(String borImageFirst) {
		this.borImageFirst = borImageFirst;
	}

	public String[] getVouchersTitle() {
		return vouchersTitle;
	}

	public void setVouchersTitle(String[] vouchersTitle) {
		this.vouchersTitle = vouchersTitle;
	}

	public String getLowestMoney() {
		return lowestMoney;
	}

	public void setLowestMoney(String lowestMoney) {
		this.lowestMoney = lowestMoney;
	}

	public int getShowUserAbleMoney() {
		return showUserAbleMoney;
	}

	public void setShowUserAbleMoney(int showUserAbleMoney) {
		this.showUserAbleMoney = showUserAbleMoney;
	}

	public BigDecimal getTenderAbleMoney() {
		return tenderAbleMoney;
	}

	public void setTenderAbleMoney(BigDecimal tenderAbleMoney) {
		this.tenderAbleMoney = tenderAbleMoney;
	}

	public BigDecimal getTenderContinueMoney() {
		return tenderContinueMoney;
	}

	public void setTenderContinueMoney(BigDecimal tenderContinueMoney) {
		this.tenderContinueMoney = tenderContinueMoney;
	}

	public List<BorrowRepaymentDetail> getBorrowRepaymentDetailList() {
		return borrowRepaymentDetailList;
	}

	public void setBorrowRepaymentDetailList(
			List<BorrowRepaymentDetail> borrowRepaymentDetailList) {
		this.borrowRepaymentDetailList = borrowRepaymentDetailList;
	}

	public RepaymentInfo getRepaymentInfo() {
		return repaymentInfo;
	}

	public void setRepaymentInfo(RepaymentInfo repaymentInfo) {
		this.repaymentInfo = repaymentInfo;
	}
	
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

	public String getMycode() {
		return mycode.toLowerCase();
	}

	public void setMycode(String mycode) {
		this.mycode = mycode;
	}

	// public Pager getPage() {
	// return page;
	// }
	// public void setPage(Pager page) {
	// this.page = page;
	// }
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

	public void setBorrowRepaymentDetail(
			BorrowRepaymentDetail borrowRepaymentDetail) {
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

	@JSON(serialize = true)
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

	public boolean isNumeric(String str) {
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

	public void setPledgeRepayPlanDetail(
			PledgeRepayPlanDetail pledgeRepayPlanDetail) {
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

	public void setWanderRepayPlanDetail(
			WanderRepayPlanDetail wanderRepayPlanDetail) {
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

	public void setUserRepayDetailList(
			List<UserRepaymentDetail> userRepayDetailList) {
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

	public void setMonthRepayPlanDetail(
			MonthRepayPlanDetail monthRepayPlanDetail) {
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

	public Integer getT() {
		return t;
	}

	public void setT(Integer t) {
		this.t = t;
	}

	public Integer getR() {
		return r;
	}

	public void setR(Integer r) {
		this.r = r;
	}

	public Integer getL() {
		return l;
	}

	public void setL(Integer l) {
		this.l = l;
	}

	public Integer getP() {
		return p;
	}

	public void setP(Integer p) {
		this.p = p;
	}

	public String getBq() {
		return bq;
	}

	public void setBq(String bq) {
		this.bq = bq;
	}

	public int getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(int borrowType) {
		this.borrowType = borrowType;
	}

	public int getBorrowIsday() {
		return borrowIsday;
	}

	public void setBorrowIsday(int borrowIsday) {
		this.borrowIsday = borrowIsday;
	}

	public List<NoteImg> getBorrowImgList() {
		return borrowImgList;
	}

	public void setBorrowImgList(List<NoteImg> borrowImgList) {
		this.borrowImgList = borrowImgList;
	}

	public CarInfoJson getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(CarInfoJson carInfo) {
		this.carInfo = carInfo;
	}

	public List<NoteImg> getCarImgList() {
		return carImgList;
	}

	public void setCarImgList(List<NoteImg> carImgList) {
		this.carImgList = carImgList;
	}

	public VerifyMessJson getVerifyMessJson() {
		return verifyMessJson;
	}

	public void setVerifyMessJson(VerifyMessJson verifyMessJson) {
		this.verifyMessJson = verifyMessJson;
	}

	public CarInfoJson getCarInfoJson() {
		return carInfoJson;
	}

	public void setCarInfoJson(CarInfoJson carInfoJson) {
		this.carInfoJson = carInfoJson;
	}

	public List<AccountBank> getAccountBankList() {
		return accountBankList;
	}

	public void setAccountBankList(List<AccountBank> accountBankList) {
		this.accountBankList = accountBankList;
	}

	public List<UserRepaymentDetail> getUserRepDetailList() {
		return userRepDetailList;
	}

	public void setUserRepDetailList(List<UserRepaymentDetail> userRepDetailList) {
		this.userRepDetailList = userRepDetailList;
	}

	public Date getReTime() {
		return reTime;
	}

	public void setReTime(Date reTime) {
		this.reTime = reTime;
	}

	public BigDecimal getDqsy() {
		return dqsy;
	}

	public void setDqsy(BigDecimal dqsy) {
		this.dqsy = dqsy;
	}

	public List<UserHongbao> getHongbaoList() {
		return hongbaoList;
	}

	public void setHongbaoList(List<UserHongbao> hongbaoList) {
		this.hongbaoList = hongbaoList;
	}

	public Integer[] getHongbao() {
		return hongbao;
	}

	public void setHongbao(Integer[] hongbao) {
		this.hongbao = hongbao;
	}

	public String getMaxUsername() {
		return maxUsername;
	}

	public void setMaxUsername(String maxUsername) {
		this.maxUsername = maxUsername;
	}

	public String getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(String maxMoney) {
		this.maxMoney = maxMoney;
	}

	public BigDecimal getTasteMoney() {
		return tasteMoney;
	}

	public void setTasteMoney(BigDecimal tasteMoney) {
		this.tasteMoney = tasteMoney;
	}

	public BigDecimal getUserTasteMoney() {
		return userTasteMoney;
	}

	public void setUserTasteMoney(BigDecimal userTasteMoney) {
		this.userTasteMoney = userTasteMoney;
	}

	public int getShowUserTasteMoney() {
		return showUserTasteMoney;
	}

	public void setShowUserTasteMoney(int showUserTasteMoney) {
		this.showUserTasteMoney = showUserTasteMoney;
	}

	public Integer getTenderNum() {
		return tenderNum;
	}

	public void setTenderNum(Integer tenderNum) {
		this.tenderNum = tenderNum;
	}

	public Integer getRealStatus() {
		return realStatus;
	}

	public void setRealStatus(Integer realStatus) {
		this.realStatus = realStatus;
	}

	public Integer getNoPayPsw() {
		return noPayPsw;
	}

	public void setNoPayPsw(Integer noPayPsw) {
		this.noPayPsw = noPayPsw;
	}

	public BigDecimal getGains() {
		return gains;
	}

	public void setGains(BigDecimal gains) {
		this.gains = gains;
	}
	public User getBorrowUser() {
		return borrowUser;
	}

	public void setBorrowUser(User borrowUser) {
		this.borrowUser = borrowUser;
	}

	public String getQbtype() {
		return qbtype;
	}

	public void setQbtype(String qbtype) {
		this.qbtype = qbtype;
	}

	public String getQbstatus() {
		return qbstatus;
	}

	public void setQbstatus(String qbstatus) {
		this.qbstatus = qbstatus;
	}

	public String getQblimit() {
		return qblimit;
	}

	public void setQblimit(String qblimit) {
		this.qblimit = qblimit;
	}
	
	
	
	/**
	 * 点击投资跳转到投资页面-----------------------------【新版】
	 * @return
	 */
	@Action(value = "/borrow/toInvestH", results = {
			@Result(name = "success", location = "/content/borrow/borrowDetailRaiseToInvest.ftl", type = "freemarker"),
			@Result(name = "noBorrowExist", location = "/content/message_page.ftl", type = "freemarker") })
	public String toInvestH(){

		borrow = this.borrowService.getBorrowById(this.getbId());
		Calendar c = Calendar.getInstance();
		c.setTime(borrow.getVerifyTime());
		c.set(Calendar.DAY_OF_MONTH,
				c.get(Calendar.DAY_OF_MONTH)
						+ Integer.valueOf(borrow.getValidTime()));
		borrow.setOverDate(c.getTime());
		msgUrl="/borrow/detail.do?bId="+borrow.getId();
		if (borrow == null) {
			msg = "此标不存在！";
			return "noBorrowExist";
		}

		if (borrow.getStatus() != 1) {
			msg = "标的状态不正确！";
			return "noBorrowExist";
		}

		if ("0".equals(borrow.getType())) {
			this.setInterest(String.valueOf(Double.valueOf(borrow.getAccount())
					* borrow.getApr() / 36500));
		} else if ("1".equals(borrow.getType())) {
			this.setInterest(interestCalUtil.payback(100d,
					Double.valueOf(borrow.getAccount()), borrow.getApr(), 30,
					borrow.getBorStages(), 2).getTotalLiXi());
		}

		user = this.getLoginUser();
		UserAccount userAccount = this.userAccountService
				.getUserAccountByUserId(user.getId());
		userAbleMoney = userAccount.getAbleMoney();
		userTasteMoney = userAccount.getTasteMoney();
		continueTotal = userAccount.getContinueTotal();

		if (userAbleMoney != null)
			showUserAbleMoney = userAbleMoney.intValue();
		else
			showUserAbleMoney = 0;
		
		if(userTasteMoney != null){
			showUserTasteMoney = userTasteMoney.intValue();
		}else{
			showUserTasteMoney = 0;
		}
		
		showContinueTotal = 0;
		if ("4".equals(borrow.getType()) && continueTotal != null
				&& continueTotal.longValue() > 100) {
			showContinueTotal = (int) (continueTotal.longValue() / 100);
			showContinueTotal = showContinueTotal * 100;

		}

		if (borrow.getMostAccount() != null
				&& !"".equals(borrow.getMostAccount())
				&& !"0".equals(borrow.getMostAccount())) {
			int mostMoney = Integer.parseInt(borrow.getMostAccount());

			if (mostMoney > 0) {
				if (mostMoney < showContinueTotal) {
					showContinueTotal = mostMoney;
				}
				mostMoney = mostMoney - showContinueTotal;
			}

			if (showUserAbleMoney > mostMoney) {
				showUserAbleMoney = mostMoney;
			}
		}
		
		if (tenderAbleMoney != null) {
			if (!this.isNumeric(tenderAbleMoney.toString())) {
				msg = "投标金额必须为整数数字！";
				return "noBorrowExist";
			}
			if(user.getId() != 1613){//@author zdl-测试小号(133*****911)可以不是整数倍：用于解决剩余可投金额不足100 
				if (tenderAbleMoney.doubleValue() % 100 != 0) {
					msg = "投标金额必须是100的整数倍！";
					return "noBorrowExist";
				}
			}
			if (tenderAbleMoney.doubleValue() < 0) {
				msg = "投标金额不能小于0！";
				return "noBorrowExist";
			}
		}
		
		if(user.getId() != 1613){//@author zdl-测试小号(133*****911)可以小于最小投资额限制：用于解决剩余可投金额不足100 
			// 投标额度是否小于最小投资金额
			if (tenderAbleMoney.doubleValue() < Double.valueOf(borrow.getLowestAccount())) {
				msg = "投标金额小于最小投标额";
				return "noBorrowExist";
			}
		}
		// 投标总额大于最大投标额
		if (borrow.getMostAccount() != null
				&& !"".equals(borrow.getMostAccount().trim())
				&& !"0".equals(borrow.getMostAccount())) {
			Map<String, Object> tMap = new HashMap<String, Object>();
			tMap.put("userId", user.getId());
			tMap.put("borrowId", borrow.getId());
			Object totalAmount = this.borrowTenderService.getByStatementId(
					"BorrowTender.selectAllAccountByUserid", tMap);
			if (totalAmount != null) {
				double tAmount = Double.valueOf(totalAmount.toString());
				if ((tAmount + tenderAbleMoney.doubleValue()) > Double
						.valueOf(borrow.getMostAccount())) {
					msg = "该项目限购金额"+ Double.valueOf(borrow.getMostAccount())+"元";
					return "noBorrowExist";
				}
			} else if (borrow.getMostAccount() != null
					&& !"".equals(borrow.getMostAccount())
					&& Integer.parseInt(borrow.getMostAccount()) < tenderAbleMoney.doubleValue()) {
				msg = "该项目限购金额"+ Double.valueOf(borrow.getMostAccount())+"元";
				return "noBorrowExist";
			}
		}
		
		if (Double.valueOf(borrow.getBalance()) <= 0) {
			msg = "此标已满！";
			return "noBorrowExist";
		}
		// zdl:投资金额大于标剩余可投金额
		if( tenderAbleMoney.doubleValue() > Double.parseDouble(borrow.getBalance()) ){
			msg = "标剩余可投金额不足！";
			return "noBorrowExist";
		}
		// 投标金额大于可用金额
		if(!"17".equals(borrow.getType())){
//			zdl：不在校验
//			if (userAbleMoney.doubleValue() < tenderAbleMoney.doubleValue()) {
//				msg = "可用余额不足！";
//				return "noBorrowExist";
//			}
//			zdl:还需充值
			if (userAbleMoney.doubleValue() < tenderAbleMoney.doubleValue()) {
				userRechargeMoney = tenderAbleMoney.subtract(userAbleMoney);
			}
			
			
			if("16".equals(borrow.getType())){
				Map<String, Object> tMap = new HashMap<String, Object>();
				tMap.put("userId", user.getId());
				tMap.put("backStatus", 0);
				tMap.put("noBorrowType", "17");
				List<BorrowTender> btList = borrowTenderService.getTenderDetailByUserid(tMap);
				if(btList.size() > 0){
					msg = "您已投资过，不符合新手条件！";
					return "noBorrowExist";
				}
			}
			//红包列表-最多12个
			/**
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", user.getId());
			map.put("status", 0);
			map.put("isPc", 1);
			map.put("limitStart", Integer.parseInt(borrow.getTimeLimit()));
			map.put("limitEnd", Integer.parseInt(borrow.getTimeLimit()));
			map.put("orderBy", " t.end_time asc ");
			map.put("pageStart", 0);
			map.put("pageSize", 12);
			
			hongbaoList = userHongbaoService.queryListByMap(map);
			*/
		
			//到期收益
			dqsy = BigDecimal.ZERO;
			BigDecimal money = tenderAbleMoney.add(tenderContinueMoney);
			
			if("15".equals(borrow.getType())){
				dqsy = CommonUtil.setPriceScale( money.multiply(new BigDecimal(borrow.getApr())).divide(new BigDecimal(12),2).multiply(new BigDecimal(borrow.getTimeLimit()))) ;
			}else{
				dqsy = CommonUtil.setPriceScale( money.multiply(new BigDecimal(borrow.getApr())).divide(new BigDecimal(1000)).multiply(new BigDecimal(borrow.getTimeLimit())));
			}

			String i=borrow.getTimeLimit();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", user.getId());
			map.put("status", 0);
			map.put("limitStart", i);
			map.put("orderBy", " money desc ,end_time asc ");
			
			
			net.qmdboss.beans.UserHongbao  uhbn=new net.qmdboss.beans.UserHongbao();
			List<net.qmdboss.beans.UserHongbao> list = userHongbaoService.queryHbListByMapNew(map);
			
			int hbsize=25;
			Listing ll=listingService.getListing(2063);//系统配置项的红包上限
			if(ll!=null){
				hbsize=Integer.valueOf(ll.getKeyValue());
			}
			
			
			if(list.size()>hbsize){
				String bestHbMoney="0";
				for(net.qmdboss.beans.UserHongbao  uhbmax :list){
					if(uhbmax.getInvestFullMomey()<=tenderAbleMoney.doubleValue()){
						uhbmax.setOn(true);
						bestHbMoney=String.valueOf(uhbmax.getMoney());
						break;
					}
				}
				
				
				
				hbbb=new HongbaoBestBen();
				hbbb.setBestNum("1");
				hbbb.setBestHbMoney(bestHbMoney);
				hbbb.setRcd("R0001");
				hbbb.setRmg("成功");
				hbbb.setUserHongbaoList(list);
				return SUCCESS;
				
			}
			
			
			
			
			Map<Integer,double[]> mapx = new HashMap<Integer,double[]>();

			for(net.qmdboss.beans.UserHongbao uhb:list){
				double[] xhong=new double[2];
				xhong[0]=uhb.getInvestFullMomey();
				xhong[1]=uhb.getMoney().doubleValue();;
				mapx.put(uhb.getId(), xhong);	
			}
			for (int key : mapx.keySet()) {
				   System.out.println("key= "+ key + " and value= " + Arrays.toString(mapx.get(key)));
			}

			Map<String, Object> maphb = new HashMap<String, Object>();//最优红包
		
			hbbb=new HongbaoBestBen();
			hbbb.setBestNum("0");
			hbbb.setBestHbMoney("0");
			hbbb.setRcd("R0001");
			hbbb.setRmg("成功");
			
			if(mapx.size()>1){
				String hongbaoIds=RedPackUtil.getPackAmount(mapx,tenderAbleMoney.doubleValue());//返回最优红包组
				System.out.println("====hongbaoIds"+hongbaoIds);
				if(hongbaoIds!=null){
					String[] strs=hongbaoIds.split(",");
					hbbb=sortList(strs,list);//调最优算法
				}else{
					hbbb.setUserHongbaoList(list);
				}
			
			}
			
			if(mapx.size()==1){
				if(list.get(0).getInvestFullMomey().doubleValue()<=tenderAbleMoney.doubleValue()){//能用这个红包
					list.get(0).setOn(true);
					hbbb.setBestNum("1");
					hbbb.setBestHbMoney(list.get(0).getMoney().toString());
					
				}else {
					list.get(0).setOn(false);
					hbbb.setBestNum("0");
					hbbb.setBestHbMoney("0");
					
				}
				
				
		
				hbbb.setUserHongbaoList(list);
			}
			
			//需充金额扣除最佳红包总额
			if(hbbb!=null && StringUtils.isNotBlank(hbbb.getBestHbMoney()) && userRechargeMoney!=null){
				if(BigDecimal.ZERO.compareTo(new BigDecimal(hbbb.getBestHbMoney()))<0 && userRechargeMoney.compareTo(BigDecimal.ZERO)>0){
					userRechargeMoney = userRechargeMoney.subtract(new BigDecimal(hbbb.getBestHbMoney()));
					if(userRechargeMoney.compareTo(BigDecimal.ZERO) < 0){
						userRechargeMoney = BigDecimal.ZERO;
					}
				}
			}	
			
			
		
		
		
		
		}else{
			if (userTasteMoney.doubleValue() < tenderAbleMoney.doubleValue()) {
				msg = "体验金不足！";
				return "noBorrowExist";
			}
		}
		
	
		return SUCCESS;
	}

	
	//根据最优的进行排序
		public  HongbaoBestBen  sortList(String[] strs,List<net.qmdboss.beans.UserHongbao> list){
			List<net.qmdboss.beans.UserHongbao> listL=new ArrayList();//剩余红包组
			List<net.qmdboss.beans.UserHongbao> listBestReturn=new ArrayList();//返回数据g
			Boolean xif=true;//用于组合list
			BigDecimal bestHbMoney=new BigDecimal(0);
			
			for(net.qmdboss.beans.UserHongbao ii:list){
				for(int j=0;j<strs.length;j++){
					if(ii.getId()==Integer.parseInt(strs[j])){
						ii.setOn(true);
						listBestReturn.add(ii);
						bestHbMoney=bestHbMoney.add(ii.getMoney());
						xif=false;
					}
				}
				if(xif==true){
					ii.setOn(false);
					listL.add(ii);
				}
				xif=true;
				
			}
		
			for(net.qmdboss.beans.UserHongbao iii:listL){
				listBestReturn.add(iii);
			}
			
			HongbaoBestBen hongbaobestB = new HongbaoBestBen();
		    hongbaobestB.setRcd("R0001");
		    hongbaobestB.setBestNum(String.valueOf(strs.length));
		    hongbaobestB.setBestHbMoney(String.valueOf(bestHbMoney));
			
			hongbaobestB.setUserHongbaoList(listBestReturn);
			
			return hongbaobestB;
		}

		public BigDecimal getUserRechargeMoney() {
			return userRechargeMoney;
		}

		public void setUserRechargeMoney(BigDecimal userRechargeMoney) {
			this.userRechargeMoney = userRechargeMoney;
		}
	
		/**
		 * zdl
		 * 投资中，当前投资额最大的可以得到的土豪奖金额 
		 */
		public BigDecimal getThMoneyCurrentMostInvest(){
			if(StringUtils.isBlank(maxMoney)){//当前最大投资额不能为空
				return BigDecimal.ZERO;
			}
			BigDecimal most=null;
			try{
				most = new BigDecimal(maxMoney);
			}catch(Exception e){
				log.debug("当前最大投资额为："+maxMoney);
			}
			if(most == null || BigDecimal.ZERO.compareTo(most)>=0){//当前最大投资额必须大于零
				return BigDecimal.ZERO;
			}
			return userHongbaoService.getThMoneyCurrentMostInvest(most);
		}
		
		/**
		 * 得到最大截标红包金额
		 * @author zdl
		 */
		public BigDecimal getJbMoneyMost(){
			return userHongbaoService.getJbMoneyMost();
		}
	
}
