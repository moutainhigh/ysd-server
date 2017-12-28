package com.qmd.action.borrow;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.mode.agency.Agency;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.mode.borrow.BorrowTemp;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.User;
import com.qmd.mode.util.MailRepayForInvestor;
import com.qmd.service.agency.AgencyService;
import com.qmd.service.area.AreaService;
import com.qmd.service.borrow.BorrowRepaymentDetailService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.borrow.BorrowTenderService;
import com.qmd.service.user.UserAccountService;
import com.qmd.service.util.ListingService;
import com.qmd.util.*;
import com.qmd.util.bean.*;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 卡趣新标：投标时，发放当日到有效日的利息，之后发放期首发利息，末期发本金
 */
@InterceptorRefs({ @InterceptorRef(value = "userVerifyInterceptor"),
		@InterceptorRef(value = "qmdDefaultStack") })
@Service("borrowRaiseAction")
public class BorrowRaiseAction extends BaseAction {
	
	private static final long serialVersionUID = -3864290482000300058L;

	Logger log = Logger.getLogger(BorrowRaiseAction.class);

	private Borrow borrow;
	private String borImageFirst;// 标的图片地址

	private String[] vouchers;// 借款凭证图片地址
	private String[] vouchersTitle;// 借款凭证标题
	private String isDxb1;// 是否有定向密码
	private String safepwd;// 安全密码
	
	Map<String, Object> root = new HashMap<String, Object>();
	private String errorMsg;

	private String tenderMoney;// 投标份数
	private String tenderMoneyContinue;
	private Integer bid;
	private Integer btp;//标类型【7:-卡趣新标(债权流转)，8:-卡趣新标（抵押质押），9:-卡趣新标（信用）】
	
	
	private RateStep rateStep;

	private User user;
	private BorrowTender borrowTender;
	
	private String dxpwd;// 定向密码
	
	private BigDecimal userAbleMoney;//可用资金
	private BigDecimal continueTotal;//续投资金
	private int showUserAbleMoney;//显示可用
	private int showContinueTotal;//显示续投
	
	private BigDecimal showMoneyTender;
	
	private Integer pieceAble;// 可用份数
	private Integer pieceContinue;//续投份数
	private Integer pieceTaste;//体验金份数
	private Integer pieceTender;//投资份数
	private Integer autoRate;//部分投资时，1：自动选择利率截图，2：不处理部分投资
	
	private String areaId;
	
	private String[] directUserNames;// 直投用户名
	private BigDecimal[] directTenderMoney;// 直投金额
	
	
	private List<BorrowTemp> borrowTempList;
	
	private ContractParamBase contractParam;
	private ContractPledgeBase[] contractPledge;
	
	private BorrowRepaymentDetail borrowRepaymentDetail;
	private Integer brdId;
	
	private BigDecimal backMoney;//还款金额

	private BorrowContent borrowContent;
	
	private int[] auditStatus;// 审核内容-审核状态
	
	@Resource
	BorrowService borrowService;
	@Resource
	BorrowTenderService borrowTenderService;
	@Resource
	BorrowRepaymentDetailService borrowRepaymentDetailService;
	
	@Resource
	UserAccountService userAccountService;
	@Resource
	ListingService listingService;
	
	@Resource
	AreaService areaService;
	
	
	private Agency agency;//对接服务商
	
	private List<User> borrowerList; //对接服务商下属借款人
	
	private List<RaiseTypeBean> raiseTypeList;
	private String raiseTypeCode;
	
	@Resource
	AgencyService  agencyService;
	
	/**
	 * 进入项目发布页面
	 */
	@Action(value="/borrow/borrowInputRaise",
			results={@Result(name="success", location="/content/borrow/borrowInputRaise.ftl", type="freemarker"),
					@Result(name="agile", location="/content/borrow/borrowInputRaiseAgile.ftl", type="freemarker"),
					@Result(name="fail", location="/content/borrow/toSuccess.ftl", type="freemarker")})
	public String borrowInputRaise(){
		log.info("跳转到添加卡趣新标页面===============");
		User user = getLoginUser();
		if(user.getTypeId()!=3){
			return "fail";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", user.getId());
		User user1 = userService.getUser(map);
		
		//获取对接服务商信息、担保服务商信息
		agency = agencyService.baseLoad(user1.getAgencyid());

		
		// 设定初始标的信息
		borrow = new Borrow();
		borrow.setType(String.valueOf(btp));

		// 取得对接服务商下属的借款人信息
		Map<String,Object> uMap = new HashMap<String,Object>();
		uMap.put("inviteUserid", user.getId());
		borrowerList = userService.getUserList(uMap);
		
		raiseTypeList = RaiseUtil.getRaiseTypeBeanList();
		
		if (btp!=null && btp==11) {
			return "agile";
		}
		
		return SUCCESS;
		
	}
	
	/**
	 * 添加标页面ht03
	 */
	@Action(value="/borrow/ajaxBorrowInputRaiseContract",
			results={@Result(name="flow_all", location="/content/borrow/borrowInputRaise_flow_all.ftl", type="freemarker"),
					@Result(name="flow_luxury", location="/content/borrow/borrowInputRaise_flow_luxury.ftl", type="freemarker"),
					@Result(name="moon_house", location="/content/borrow/borrowInputRaise_moon_house.ftl", type="freemarker"),
					@Result(name="moon_car", location="/content/borrow/borrowInputRaise_moon_car.ftl", type="freemarker"),
					@Result(name="moon_cargo", location="/content/borrow/borrowInputRaise_moon_cargo.ftl", type="freemarker"),
					@Result(name="credit", location="/content/borrow/borrowInputRaise_credit_all.ftl", type="freemarker"),
					@Result(name="moon_bao", location="/content/borrow/borrowInputRaise_moon_bao.ftl", type="freemarker"),
					@Result(name="moon_zhuandaibao", location="/content/borrow/borrowInputRaise_moon_zhuandaibao.ftl", type="freemarker"),
					@Result(name="moon_securities", location="/content/borrow/borrowInputRaise_moon_securities.ftl", type="freemarker"),
//					@Result(name="moon_bao_person", location="/content/borrow/borrowInputRaise_moon_bao_person.ftl", type="freemarker"),
//					@Result(name="moon_agile", location="/content/borrow/borrowInputRaise_moon_agile.ftl", type="freemarker"),
//					@Result(name="moon_agile_person", location="/content/borrow/borrowInputRaise_moon_agile_person.ftl", type="freemarker"),
					@Result(name="flow_sxbao_wjx", location="/content/borrow/borrowInputRaise_flow_sxbao_wjx.ftl", type="freemarker"),
					@Result(name="flow_sxbao_jjx", location="/content/borrow/borrowInputRaise_flow_sxbao_jjx.ftl", type="freemarker"),
					@Result(name="flow_fund", location="/content/borrow/borrowInputRaise_flow_fund.ftl", type="freemarker"),
					@Result(name="none", location="/content/borrow/borrowInputRaise_none.ftl", type="freemarker")})
	public String ajaxBorrowInputRaiseContract(){
		User user = getLoginUser();
		
		borrow = new Borrow();
		if(bid != null){
			borrow = this.borrowService.getBorrowById(bid);
//			String contractCode = RaiseUtil.getSignByBtypeBBtype(
//					Integer.parseInt(borrow.getType()), borrow.getBusinessType());
//
//			if ("flow_all".equals(contractCode)) {// 债权流转  
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractFlow.class);
//			}else if ("flow_luxury".equals(contractCode)) {// 债权流转----奢品
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractFlowLuxury.class);
//			}else if ("flow_sxbao_wjx".equals(contractCode)) {// 债权流转----随心宝--稳健型
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractFlowSxbaoWjx.class);
//			}else if ("flow_sxbao_jjx".equals(contractCode)) {// 债权流转----随心宝--积极型
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractFlowSxbaoJjx.class);
//			}else if ("flow_sxbao_jjx".equals(contractCode)) {// 债权流转----基金
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractFlowFund.class);
//			}else if ("moon_house".equals(contractCode)) {// 房产抵押
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractMoonBao.class);
////				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractMoonHouse.class);
//			} else if ("moon_car".equals(contractCode)) {// 汽车质押
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractMoonBao.class);
////				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractMoonCar.class);
////				contractPledge = net.arnx.jsonic.JSON.decode(borrow.getContractPawns(),ContractMoonCarPledge[].class);
//			} else if ("moon_cargo".equals(contractCode)) {// 动产质押
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractMoonBao.class);
////				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractMoonCargo.class);
////				contractPledge = net.arnx.jsonic.JSON.decode(borrow.getContractPawns(),ContractMoonCarPledge[].class);
//			} else if("credit".equals(contractCode)) {//信用标
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractCredit.class);
//			} else if("moon_bao".equals(contractCode)){
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractMoonBao.class);
//			} else if("moon_zhuandaibao".equals(contractCode)){
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractMoonZhuandaobao.class);
//			} else if("moon_securities".equals(contractCode)){
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractMoonSecurities.class);
//			} 
//			else if("moon_bao_person".equals(contractCode)){
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractMoonBaoPerson.class);
//			}else if("moon_agile".equals(contractCode)){
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractAgile.class);
//			} else if("moon_agile_person".equals(contractCode)){
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractAgilePerson.class);
//			}
//			else if ("zqzr".equals(contractCode)) {
//				contractParam = net.arnx.jsonic.JSON.decode(borrow.getContractParam(),ContractZqzr.class);
//			}
		}
		raiseTypeList = RaiseUtil.getRaiseTypeBeanList();
		if(user.getTypeId()!=1){
			return "none";
		}
		return RaiseUtil.getSignByBtypeBBtype(btp,raiseTypeCode);
	}
	
	/**
	 * 添加卡趣月标
	 * 
	 * @return
	 */
//	@Action(value = "/borrow/saveBorrowRaise", results = { @Result(name = "success", location = "/content/borrow/success.ftl", type = "freemarker") })

	@Action(value="/borrow/ajaxSaveBorrowRaise",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String saveBorrowRaise() {
		User user = getLoginUser();
		//权限判断
		if(borrow.getType().equals("7"))if (user.getAgency().getFlowRule()!=1)return ajax(Status.warn,"您无权执行该操作!");
		if(borrow.getType().equals("8"))if (user.getAgency().getPledgeRule()!=1)return ajax(Status.warn,"您无权执行该操作!");
		if(borrow.getType().equals("9"))if (user.getAgency().getCreditRule()!=1)return ajax(Status.warn,"您无权执行该操作!");
		if(borrow.getType().equals("10"))if (user.getAgency().getTasteRule()!=1)return ajax(Status.warn,"您无权执行该操作!");
		
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		if (!this.getMycode().equals(random)) {
			return ajax(Status.warn,"验证码输入错误!");
		}

		if (!userService.isPassword(user.getUsername(), safepwd, "1")) {
			return ajax(Status.warn,"安全密码输入错误!");
		}
		if (borrow.getName().equals("") || borrow.getName() == null) {
			return ajax(Status.warn,"标题不能为空，请重新输入!");
		} else if (borrow.getAccount() == null
				|| borrow.getAccount().equals("")) {
			return ajax(Status.warn,"借款金额不能为空，请重新输入!");
		} else if (borrow.getContent() == null
				|| borrow.getContent().equals("")) {
			return ajax(Status.warn,"标内容不能为空，请重新输入!");
//		} else if (StringUtil.isEmpty(borImageFirst)) {
//			addActionError("图片不能为空，请重新输入!");
//			return "error_ftl";
		} else if (borrow.getApr() <= 0 || borrow.getApr() >= 30) {
			return ajax(Status.warn,"请输入正确的借款利率!");
		} else if (borrow.getTimeLimit().equals("")
				|| borrow.getTimeLimit() == null) {
			return ajax(Status.warn,"请输入正确的借款月数!");
//		} else if (Integer.parseInt(borrow.getTimeLimit()) > 12
//				|| Integer.parseInt(borrow.getTimeLimit()) < 0) {
//			addActionError("请输入正确的借款月数!");
//			return "error_ftl";
//		} else if (borrow.getBorStages().equals("0")) {
//			addActionError("请设置正确的还款计划!");
//			return "error_ftl";
		} else if (!borrow.getAward().equals("0")) {
			if (borrow.getFunds().equals("") || borrow.getFunds().equals("0")) {
				return ajax(Status.warn,"请输入正确的奖励!");
			}
		} else if (isDxb1 != null
				&& (borrow.getPwd() == null || "".equals(borrow.getPwd()))) {
			return ajax(Status.warn,"请设置定向密码!");
		}
		if (borrow.getIsDxb().equals("1") && borrow.getPwd().equals("")) {
			return ajax(Status.warn,"请输入定向密码!");
		}
		if(StringUtils.isEmpty(areaId)){
			return ajax(Status.warn,"请选择项目所属地!");
		}
		String domain = areaService.getAreaDomain(areaId);
		String[] areaStore = CommonUtil.splitString(domain);
		borrow.setProvince(areaStore[0]);//省
		borrow.setCity(areaStore[1]);//市
		borrow.setArea(areaStore[2]);//区
		StringBuffer areaSotreBuffer = new StringBuffer();
		if(StringUtils.isNotEmpty(areaStore[0])){
			areaSotreBuffer.append(areaService.getAreaName(areaStore[0]));
			if(StringUtils.isNotEmpty(areaStore[1])){
				areaSotreBuffer.append(",").append(areaService.getAreaName(areaStore[1]));
				if(StringUtils.isNotEmpty(areaStore[2])){
					areaSotreBuffer.append(",").append(areaService.getAreaName(areaStore[2]));
				}
			}
		}
//		borrow.setAreaStore(areaSotreBuffer.toString());
		
		
//		if (StringUtils.isNotEmpty(borrow.getDirectInfo())) {
//			DirectInfo[] directInfoList = net.arnx.jsonic.JSON.decode(borrow.getDirectInfo(), DirectInfo[].class);
//			Map<String ,Object> umap = new HashMap<String ,Object>();
//			for (int i = 0; i < directInfoList.length; i++) {
//				umap.put("username", directInfoList[i].getDirectUserName());
//				User dUser = userService.getUser(umap);
//				if (dUser==null) {
//					return ajax(Status.warn,"直投用户["+directInfoList[i].getDirectUserName()+"]不存在!");
//				}
//				BigDecimal dm = dUser.getDirectMoney();
//				if (dm==null) dm = BigDecimal.valueOf(0);
//				if (dm.doubleValue() <Double.valueOf(directInfoList[i].getDirectMoney())) {
//					return ajax(Status.warn,"直投用户["+directInfoList[i].getDirectUserName()+"]的直投金额不足!");
//				}
//				directInfoList[i].setDirectUserId(dUser.getId());
//			}
//			String tempDirect = net.arnx.jsonic.JSON.encode(directInfoList);
//			borrow.setDirectInfo(tempDirect);
//			
//		}
		
		//直投信息
//		if (directUserNames != null && directUserNames.length > 0 && directTenderMoney != null
//				&& directTenderMoney.length > 0) {
//			List<DirectInfo> directInfoList = new ArrayList<DirectInfo>();
//			Map<String ,Object> umap = new HashMap<String ,Object>();
//			for (int i = 0; i < directUserNames.length; i++) {
//				umap.put("username", directUserNames[i]);
//				User dUser = userService.getUser(umap);
//				if (dUser==null) {
//					addActionError("直投用户["+directUserNames[i]+"]不存在!");
//					return "error_ftl";
//				}
//				BigDecimal dm = dUser.getDirectMoney();
//				if (dm==null) dm = BigDecimal.valueOf(0);
//				if (dm.doubleValue() <directTenderMoney[i].doubleValue() ) {
//					addActionError("直投用户["+directUserNames[i]+"]的直投金额不足!");
//					return "error_ftl";
//				}
//				
//				DirectInfo di = new DirectInfo();
//				di.setUserId(dUser.getId());
//				di.setUserName(directUserNames[i]);
//				di.setDirectMoney(directTenderMoney[i].toString());
//				directInfoList.add(di);
//			}
//
//			String tempDis = JsonUtil.listToJson(directInfoList);
//
//			borrow.setDirectInfo(tempDis);
//		}
		
//		//房产
//		if ("01".equals(borrow.getBusinessType())) {
//			borrow.setContractParam(JSON.encode(cpme));
//		} else
//		//车的抵押物
//		if ("02".equals(borrow.getBusinessType())) {
//
//		} else 
//		//动产的抵押物
//		if ("03".equals(borrow.getBusinessType())) {
//
//		}
				
		borrow.setSchedule("0");
		borrow.setRepaymentAccount("0");
		borrow.setUserId(user.getId());
		borrow.setOperatorIp(getRequestRemoteAddr());
		borrow.setUpdatePersion(String.valueOf(user.getId()));
		borrow.setModifyDate(new Date());
		borrow.setCreateDate(new Date());
		borrow.setAddTime(new Date());
		borrow.setBalance(borrow.getAccount());
		borrow.setTenderTimes("0");// 投标次数
		//borrow.setStyle("2");// 默认按月付息，到期还本
		borrow.setIsdaty("1");//天标

		borrow.setWanderPieceMoney(MonthUtil.moneyForPerPiece(borrow));

//		borrow.setBorImageFirst(CommonUtil.decodeUrl(borImageFirst));
		
//		if ("10".equals(borrow.getType())) {//体验标，设置体验标记
//			borrow.setTasteRule(2);
//		} else {
//			borrow.setTasteRule(3);//非体验标
//		}
		

//		if (borrow.getBorStages() != null
//				&& !"".equals(borrow.getBorStages().trim())) {
//
//			String stg = borrow.getBorStages();
//			if (stg.endsWith(":")) {
//				stg = stg.substring(0, stg.length() - 1);
//			}
//
//			String[] tgs = stg.split(":");
//			borrow.setDivides(tgs.length);
//		}
		//borrow.setDivides(Integer.parseInt(borrow.getTimeLimit())+1);

		if (vouchers != null && vouchers.length > 0 && vouchersTitle != null
				&& vouchersTitle.length > 0) {
//			List<NoteImg> noteImgList = new ArrayList<NoteImg>();
			
			List<ImageArray> imList = new ArrayList<ImageArray>();
			
			String[] vtArray = CommonUtil.array_unique(vouchersTitle);
			for(String title :vtArray){
				ImageArray ia = new ImageArray();
				ia.setName(title);
				
				List<BorrowImage> biList = new ArrayList<BorrowImage>();
				
				for (int i = 0; i < vouchers.length; i++) {
					String devc = CommonUtil.decodeUrl(vouchers[i]);
//					NoteImg nt = new NoteImg();
//					nt.setUrl(devc);
//					nt.setName(vouchersTitle[i]);
//					noteImgList.add(nt);
					if(title.equals(vouchersTitle[i])){
						BorrowImage bi = new BorrowImage();
						bi.setUrl(devc);
						biList.add(bi);
					}
				}
				
				ia.setImgUrlList(biList);
				imList.add(ia);
			}
//			String tempVcs = JsonUtil.listToJson(noteImgList);
			String tempVcs = JsonUtil.toJson(imList);
			System.out.println("image==="+tempVcs);
			borrow.setBorImage(tempVcs);
		}
		
		Map<Integer,String> acMap = AuditContent.mapInit();
		//审核内容
		if( auditStatus != null && auditStatus.length >0){
			for (int i = 0; i < auditStatus.length; i++) {
				acMap.put(auditStatus[i], "1");
			}
		}
		
		List<AuditContent> acList = new ArrayList<AuditContent>();

		for(Map.Entry<Integer, String> entry: acMap.entrySet()){
			AuditContent ac = new AuditContent();
			ac.setName(AuditContent.mapPar.get(entry.getKey()));
			ac.setStatus(entry.getValue());
			ac.setFlag(entry.getKey());
			acList.add(ac);
		}
		String tempVcs = JsonUtil.listToJson(acList);
//		borrow.setAuditContentJson(tempVcs);
		System.out.println(tempVcs);
		
		String borContent = JsonUtil.beanToJson(borrowContent);
		System.out.println(borContent);
		borrow.setBorrowInfoJson(borContent);
//		
//		if (StringUtils.isNotEmpty(borrow.getRateStep())) {
//			
//			if("11".equals(borrow.getType())) {
//				RateStepAgile[] rates = JSON.decode(borrow.getRateStep(),
//						RateStepAgile[].class);
//				borrow.setRateLow(rates[0].getRateDay());
//				borrow.setApr(rates[0].getRateDay().doubleValue());
//				borrow.setRateHigh(rates[rates.length - 1].getRateDay());
//				
//				borrow.setRateYearLow(CommonUtil.setPriceScale2BigDecimal(rates[0].getRateYear().doubleValue()));
//				borrow.setRateYearHeight(CommonUtil.setPriceScale2BigDecimal(rates[rates.length - 1].getRateYear().doubleValue()));
//				
//				borrow.setTimeLimitMonth(Double.valueOf(borrow.getTimeLimit()));
//				
//				borrow.setValidTime(String.valueOf(Integer.parseInt(borrow.getTimeLimit()) - borrow.getStopBuyDays()));
//				borrow.setRepaymentPeriod(1);
//				borrow.setAgileFlg(1);
//				borrow.setRedeemFeeRate(borrow.getRedeemFeeRate().divide(BigDecimal.valueOf(1000)));
//				
//			} else {
//
//				RateStep[] rates = JSON.decode(borrow.getRateStep(),
//						RateStep[].class);
//				borrow.setRateLow(rates[0].getRateDay());
//				borrow.setApr(rates[0].getRateDay().doubleValue());
//				borrow.setRateHigh(rates[rates.length - 1].getRateDay());
//				
//				borrow.setRateYearLow(CommonUtil.setPriceScale2BigDecimal(rates[0].getRateYear().doubleValue()));
//				borrow.setRateYearHeight(CommonUtil.setPriceScale2BigDecimal(rates[rates.length - 1].getRateYear().doubleValue()));
//				
//				borrow.setTimeLimitMonth(Double.valueOf(borrow.getTimeLimit()));
//			}
//		}
		
		

		log.info("添加标");
		borrowService.addBorrow(borrow);

		return ajax(Status.success,"操作成功!");
	}
	
	@Action(value="/borrow/agileRepaymentList",results={
			@Result(name="success", location="/content/borrow/agileRepaymentList.ftl", type="freemarker")
	})
	public String agileRepaymentList() {
		
		if (getLoginUser().getTypeId().intValue() == 0) {
			addActionError("只有借款人才能访问！");
			return "error_ftl";
		}
		
		if(pager == null){
			pager = new Pager();
		}
		User user = this.getLoginUser();
		Map<String,Object> qMap = new HashMap<String,Object>();
		//int[] array = {1,3};
		qMap.put("userId", user.getId());
//		qMap.put("keywords", keywords);
		qMap.put("orderBy", " verify_time desc");
		qMap.put("type", 11);
		
		pager = borrowService.queryBorrowListForRepay(pager,qMap);
		
		return SUCCESS;
	}
	
	@Action(value="/borrow/agileRepaymentDetail",results={
			@Result(name="success", location="/content/borrow/agileRepaymentDetail.ftl", type="freemarker")
	})
	public String agileRepaymentDetail() {
		
		if (getLoginUser().getTypeId().intValue() == 0) {
			addActionError("只有借款人才能访问！");
			return "error_ftl";
		}
		
		if(pager == null){
			pager = new Pager();
		}
		User user = this.getLoginUser();
		Map<String,Object> qMap = new HashMap<String,Object>();
		//int[] array = {1,3};
		qMap.put("userId", user.getId());
//		qMap.put("keywords", keywords);
		qMap.put("orderBy", " verify_time desc");
		qMap.put("borrowId", bid);
		
		borrowTempList = borrowTenderService.getJkmxByUserid(qMap);
		Map<String,Object> rmap = new HashMap<String,Object>();
		rmap.put("borrowId", bid);
		List<BorrowRepaymentDetail> btempList =  borrowRepaymentDetailService.queryBorrowRepaymentList(rmap);
		if (btempList!=null && btempList.size()>0) {
			borrowRepaymentDetail = btempList.get(0);
		}
		
		return SUCCESS;
	}
	
	
	/**
	 * 灵活宝最终还款
	 * 
	 * @return
	 */
	@Action(value="/borrow/ajaxAgileRepaymentBack",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String ajaxAgileRepaymentBack() {
		
		List<MailRepayForInvestor> mailList = new ArrayList<MailRepayForInvestor>();
		int number =borrowTenderService.updateBorrowAgile(brdId,mailList,getRequestRemoteAddr());

		if(number==1){
			return ajax(Status.error,"您账户可用金额不足，请充值!");
		} else if (number==2) {
			return ajax(Status.error,"本期已还完!");
		}
		
		
		return ajax(Status.success,"还款成功!");
	}
	
	/**
	 * 逾期还款(全额)
	 * 
	 * @return
	 */
	@Action(value="/borrow/ajaxLateRepaymentBackFull",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String ajaxLateRepaymentBackFull() {
		
		List<MailRepayForInvestor> mailList = new ArrayList<MailRepayForInvestor>();
		int number = borrowTenderService.updateBorrowLate(bid, null, mailList, getRequestRemoteAddr());
		//updateBorrowLate
		//int number =borrowTenderService.updateBorrowAgile(brdId,mailList,getRequestRemoteAddr());

		if(number==1){
			return ajax(Status.error,"您账户可用金额不足，请充值!");
		} else if (number==2) {
			return ajax(Status.error,"本期已还完!");
		}
		
		return ajax(Status.success,"还款成功!");
	}
	/**
	 * 逾期还款（有损）
	 * 
	 * @return
	 */
	@Action(value="/borrow/ajaxLateRepaymentBackLost",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String ajaxLateRepaymentBackLost() {
		
		List<MailRepayForInvestor> mailList = new ArrayList<MailRepayForInvestor>();
		int number = borrowTenderService.updateBorrowLate(bid, backMoney, mailList, getRequestRemoteAddr());
		//updateBorrowLate
		//int number =borrowTenderService.updateBorrowAgile(brdId,mailList,getRequestRemoteAddr());

		if(number==1){
			return ajax(Status.error,"您账户可用金额不足，请充值!");
		} else if (number==2) {
			return ajax(Status.error,"本期已还完!");
		}
		
		return ajax(Status.success,"还款成功!");
	}
	/**
	 * 逾期还款信息查看（有损）
	 * 
	 * @return
	 */
	@Action(value="/borrow/lateRepaymentBackLost",results={
			@Result(name="success", location="/content/borrow/poput_late_borrow_lost.ftl", type="freemarker")
	})
	public String lateRepaymentBackLost() {
		
		borrow = borrowService.getBorrowById(bid);
		
		return SUCCESS;
	}

	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
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

	public String getIsDxb1() {
		return isDxb1;
	}

	public void setIsDxb1(String isDxb1) {
		this.isDxb1 = isDxb1;
	}

	public String getSafepwd() {
		return safepwd;
	}

	public void setSafepwd(String safepwd) {
		this.safepwd = safepwd;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public RateStep getRateStep() {
		return rateStep;
	}

	public void setRateStep(RateStep rateStep) {
		this.rateStep = rateStep;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BorrowTender getBorrowTender() {
		return borrowTender;
	}

	public void setBorrowTender(BorrowTender borrowTender) {
		this.borrowTender = borrowTender;
	}

	public BigDecimal getUserAbleMoney() {
		return userAbleMoney;
	}

	public void setUserAbleMoney(BigDecimal userAbleMoney) {
		this.userAbleMoney = userAbleMoney;
	}

	public BigDecimal getContinueTotal() {
		return continueTotal;
	}

	public void setContinueTotal(BigDecimal continueTotal) {
		this.continueTotal = continueTotal;
	}

	public int getShowUserAbleMoney() {
		return showUserAbleMoney;
	}

	public void setShowUserAbleMoney(int showUserAbleMoney) {
		this.showUserAbleMoney = showUserAbleMoney;
	}

	public int getShowContinueTotal() {
		return showContinueTotal;
	}

	public void setShowContinueTotal(int showContinueTotal) {
		this.showContinueTotal = showContinueTotal;
	}

	public Integer getPieceAble() {
		return pieceAble;
	}

	public void setPieceAble(Integer pieceAble) {
		this.pieceAble = pieceAble;
	}

	public Integer getPieceContinue() {
		return pieceContinue;
	}

	public void setPieceContinue(Integer pieceContinue) {
		this.pieceContinue = pieceContinue;
	}

	public Integer getPieceTaste() {
		return pieceTaste;
	}

	public void setPieceTaste(Integer pieceTaste) {
		this.pieceTaste = pieceTaste;
	}

	public Integer getPieceTender() {
		return pieceTender;
	}

	public void setPieceTender(Integer pieceTender) {
		this.pieceTender = pieceTender;
	}

	public Integer getAutoRate() {
		return autoRate;
	}

	public void setAutoRate(Integer autoRate) {
		this.autoRate = autoRate;
	}

	public String[] getDirectUserNames() {
		return directUserNames;
	}

	public void setDirectUserNames(String[] directUserNames) {
		this.directUserNames = directUserNames;
	}

	public BigDecimal[] getDirectTenderMoney() {
		return directTenderMoney;
	}

	public void setDirectTenderMoney(BigDecimal[] directTenderMoney) {
		this.directTenderMoney = directTenderMoney;
	}

	public String getDxpwd() {
		return dxpwd;
	}

	public void setDxpwd(String dxpwd) {
		this.dxpwd = dxpwd;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public BigDecimal getShowMoneyTender() {
		return showMoneyTender;
	}

	public void setShowMoneyTender(BigDecimal showMoneyTender) {
		this.showMoneyTender = showMoneyTender;
	}

	public Integer getBtp() {
		return btp;
	}

	public void setBtp(Integer btp) {
		this.btp = btp;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}


	public List<User> getBorrowerList() {
		return borrowerList;
	}

	public void setBorrowerList(List<User> borrowerList) {
		this.borrowerList = borrowerList;
	}

	public List<RaiseTypeBean> getRaiseTypeList() {
		return raiseTypeList;
	}

	public void setRaiseTypeList(List<RaiseTypeBean> raiseTypeList) {
		this.raiseTypeList = raiseTypeList;
	}

	public String getRaiseTypeCode() {
		return raiseTypeCode;
	}

	public void setRaiseTypeCode(String raiseTypeCode) {
		this.raiseTypeCode = raiseTypeCode;
	}

	public ContractParamBase getContractParam() {
		return contractParam;
	}

	public void setContractParam(ContractParamBase contractParam) {
		this.contractParam = contractParam;
	}

	public ContractPledgeBase[] getContractPledge() {
		return contractPledge;
	}

	public void setContractPledge(ContractPledgeBase[] contractPledge) {
		this.contractPledge = contractPledge;
	}

	public List<BorrowTemp> getBorrowTempList() {
		return borrowTempList;
	}

	public void setBorrowTempList(List<BorrowTemp> borrowTempList) {
		this.borrowTempList = borrowTempList;
	}

	public BorrowRepaymentDetail getBorrowRepaymentDetail() {
		return borrowRepaymentDetail;
	}

	public void setBorrowRepaymentDetail(BorrowRepaymentDetail borrowRepaymentDetail) {
		this.borrowRepaymentDetail = borrowRepaymentDetail;
	}

	public Integer getBrdId() {
		return brdId;
	}

	public void setBrdId(Integer brdId) {
		this.brdId = brdId;
	}

	public BigDecimal getBackMoney() {
		return backMoney;
	}

	public void setBackMoney(BigDecimal backMoney) {
		this.backMoney = backMoney;
	}
	public int[] getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int[] auditStatus) {
		this.auditStatus = auditStatus;
	}

	public BorrowContent getBorrowContent() {
		return borrowContent;
	}

	public void setBorrowContent(BorrowContent borrowContent) {
		this.borrowContent = borrowContent;
	}
}
