package com.qmd.action.borrow;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.mode.agency.Agency;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.mode.user.User;
import com.qmd.service.agency.AgencyService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.util.*;
import com.qmd.util.bean.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@InterceptorRefs({ @InterceptorRef(value = "userVerifyInterceptor"),
		@InterceptorRef(value = "qmdDefaultStack") })
@Service("borrowPromoteAction")
public class BorrowPromoteAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(BorrowPromoteAction.class);

	private Logger tenderLog = Logger.getLogger("userTenderLog");

	private Borrow borrow;
	private Integer btp;
	private int borrowIsday;
	private BigDecimal borrowAccount;
	private BigDecimal borrowApr;
	private int borrowTimeLimit;
	private int borrowStyle;
	private int borrowDivides;

	private List<BorrowRepaymentDetail> borrowRepaymentDetailList;
	private BorrowRepaymentDetail borrowRepaymentDetail;

	private String mycode;
	private String[] vouchers;
	private String[] vouchersTitle;
	private String[] carTitle;//车辆标题
	private String[] carImg;//车辆图片
	
	private String brandId;//品牌ID
	private String seriesId;//车系ID

	private RepaymentInfo repaymentInfo;
	private CarInfoJson carInfoJson;
	private CarTime carTime;
	private VerifyMessJson verifyMessJson;
	
	private Integer borrowId;
	private String keywords;
	private String safepwd;
	
	private List<NoteImg> borrowImgList;
	private Agency agency;//对接服务商
	private List<User> borrowerList; //对接服务商下属借款人
	
	private List<RaiseTypeBean> raiseTypeList;

	@Resource
	BorrowService borrowService;
	@Resource
	AgencyService  agencyService;

	/**
	 * 添加月标页面
	 */
	@Action(value = "/borrow/inputBorrowPromote", results = { @Result(name = "success", location = "/content/borrow/borrowInputPromote.ftl", type = "freemarker") })
	public String inputBorrowPromote() {

		User user = getLoginUser();
		if (user.getTypeId() != 1) {
			addActionError("确认借款人!");
			return "error_ftl";
		}

		if (btp == 12) {
			borrowIsday = 0;
		} else {
			borrowIsday = 1;
		}

		return SUCCESS;
	}
	
	/**
	 * 添加月标页面
	 */
	@Action(value = "/borrow/inputBorrowRaise", results = { @Result(name = "success", location = "/content/borrow/borrowInputRaiseCrowd.ftl", type = "freemarker"), 
				@Result(name="agile", location="/content/borrow/borrowInputRaiseAgile.ftl", type="freemarker"),
				@Result(name="fail", location="/content/borrow/toSuccess.ftl", type="freemarker")})
		public String inputBorrowRaise() {

			User user = getLoginUser();
			if(user.getTypeId()!=3){
				return "fail";
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", user.getId());
			User user1 = userService.getUser(map);
			
			//获取对接服务商信息、担保服务商信息
			agency = agencyService.baseLoad(user1.getAgencyid());
//			if (agency.getAgencydbid()!=null && agency.getAgencydbid()!=0) {
//				agencyDb = agencyDbService.baseLoad(agency.getAgencydbid());
//			}
//			
			// 设定初始标的信息
			borrow = new Borrow();
			borrow.setType(String.valueOf(btp));
//			if (agencyDb!=null) {
////				borrow.setAgencyGuaranteeId(agencyDb.getId());
////				borrow.setAgencyGuaranteeName(agencyDb.getCompanyName());
//			}
			// 取得对接服务商下属的借款人信息
			Map<String,Object> uMap = new HashMap<String,Object>();
			uMap.put("inviteUserid", user.getId());
			borrowerList = userService.getUserList(uMap);
			
			raiseTypeList = RaiseUtil.getRaiseTypeBeanList();
			
			if (btp!=null && btp==11) {
				return "agile";
			}

			if (btp == 15) {
				borrowIsday = 0;
			} else {
				borrowIsday = 1;
			}

			return SUCCESS;
	}
	/**
	 * 添加月标页面
	 */
	@Action(value = "/borrow/inputBorrowPromoteCopy", results = { @Result(name = "success", location = "/content/borrow/borrowInputPromoteCopy.ftl", type = "freemarker") })
	public String inputBorrowPromoteCopy() {

		User user = getLoginUser();
		if (user.getTypeId() != 1) {
			addActionError("确认借款人!");
			return "error_ftl";
		}
		borrow = borrowService.getBorrowById(borrowId);
		if (user.getId().intValue() != borrow.getUserId().intValue()) {
			addActionError("续标人必须是原借款人!");
			return "error_ftl";
		}
		if (borrow.getParentFlg()!=1) {
			addActionError("不能续标!");
			return "error_ftl";
		}
		
		
		btp =Integer.parseInt( borrow.getType());

		if (btp == 12) {
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
		
		Map<String,Object> map1 =new HashMap<String,Object>();
		map1.put("userId", user.getId());
		int[] array1 = {1,3,5,7};
		map1.put("array", array1);
		map1.put("parentId", borrow.getId());
		
		Double dd1 = borrowService.queryBorrowAccountSum(map1);
		if (dd1==null)dd1=0D;
		borrow.setChildMoneyFinish(dd1.intValue());
		
		Map<String,Object> map2 =new HashMap<String,Object>();
		map2.put("userId", user.getId());
		int[] array2 = {0};
		map2.put("array", array2);
		map2.put("parentId", borrow.getId());
		
		Double dd2 = borrowService.queryBorrowAccountSum(map2);
		if (dd2==null)dd2=0D;
		borrow.setChildMoneyReady(dd2.intValue());
		
		borrow.setChildMoneyWait(borrow.getParentMoney()-borrow.getChildMoneyFinish() - borrow.getChildMoneyReady());
		
		//public Double queryBorrowAccountSum(Map<String,Object> map)

		return SUCCESS;
	}

	@Action(value = "/borrow/ajaxShowPlan", results = { @Result(name = "success", location = "/content/borrow/borrowInputPromote_plan.ftl", type = "freemarker") })
	public String ajaxShowPlan() {

		repaymentInfo = PromoteUtil.promotePlan(borrowIsday, borrowStyle,
				borrowTimeLimit, borrowDivides, borrowAccount, borrowApr);

		return SUCCESS;

	}

	/** 保存标信息 */
	@Action(value = "/borrow/saveBorrowPromote", results = { @Result(name = "success", location = "/content/borrow/success.ftl", type = "freemarker") })
	public String saveBorrowPromote() {

		// 校验输入内容
		String ret = checkBorrowInput(borrow);
		if (ret != null)
			return ret;

		User user = getLoginUser();

		// double borTotal=Double.parseDouble(borrow.getAccount());
		// double money = 0;
		// String str[]=borrow.getBorStages().split(":");
		// int num =str.length;
		// String dayStr[]=new String[num] ;
		// String moneyStr[]=new String[num];
		// boolean zeroFlg = false;
		// for(int i=0;i<str.length;i++) {
		// String str1[]=str[i].split(",");
		// dayStr[i]=str1[0];
		// moneyStr[i]=String.valueOf((Double.parseDouble(str1[1])/borTotal)*borTotal);//按比例计算每期还款金额
		// money += Double.parseDouble(moneyStr[i]);
		// if (Double.parseDouble(str1[1])==0) {
		// zeroFlg = true;
		// }
		// }
		// if(Double.parseDouble(borrow.getAccount())!=money){
		// addActionError("请选择正确的还款期限和还款金额!");
		// return "error_ftl";
		// }
		// String total=
		// CalculationUtil.totalMonthMoney(borrow);//计算月标预计还款总额（不计算奖励）
		borrow.setSchedule("0");
		// borrow.setRepaymentAccount(total);//TODO 初审时设置，复审时更新
		borrow.setUserId(user.getId());
		borrow.setOperatorIp(getRequestRemoteAddr());
		borrow.setUpdatePersion(String.valueOf(user.getId()));
		borrow.setModifyDate(new Date());
		borrow.setCreateDate(new Date());
		borrow.setAddTime(new Date());
		borrow.setBalance(borrow.getAccount());
		borrow.setTenderTimes("0");// 投标次数

		borrow.setAutoTenderRepayWay(Integer.parseInt(borrow.getStyle()));

		if (vouchers != null && vouchers.length > 0 && vouchersTitle != null
				&& vouchersTitle.length > 0) {
			List<NoteImg> noteImgList = new ArrayList<NoteImg>();
			for (int i = 0; i < vouchers.length; i++) {
				String devc = CommonUtil.decodeUrl(vouchers[i]);
				NoteImg nt = new NoteImg();
				nt.setUrl(devc);
				nt.setName(vouchersTitle[i]);
				noteImgList.add(nt);
			}

			String tempVcs = JsonUtil.listToJson(noteImgList);

			borrow.setBorImage(tempVcs);
		}

		if ("0".equals(borrow.getIsday())) {
			borrow.setFinalRateYear(BigDecimal.valueOf(borrow.getApr() / 100));
			borrow.setVaryYearRate(borrow.getFinalRateYear());
			borrow.setVaryMonthLimit(NumberUtil.setPriceScale(new BigDecimal(borrow.getTimeLimit())));
		} else if ("1".equals(borrow.getIsday())) {
			borrow.setFinalRateYear(BigDecimal.valueOf(borrow.getApr() / 1000 * 360));
			borrow.setVaryYearRate(borrow.getFinalRateYear());
			borrow.setVaryMonthLimit(NumberUtil.setPriceScale(BigDecimal.valueOf(
					Integer.parseInt(borrow.getTimeLimit())/30)));
		}
		
		borrowService.addBorrow(borrow);

		return "success";

	}

	/** 保存投标 */
	public String saveTender() {
		checkTenderInput();

		return null;
	}

	private String checkBorrowInput(Borrow obj) {

		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		if (mycode == null || !mycode.equals(random)) {
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
			addActionError("请输入正确的借款月数!");
			return "error_ftl";
			// }else if(Integer.parseInt(borrow.getTimeLimit()) > 12 ||
			// Integer.parseInt(borrow.getTimeLimit())%1 !=0){
			// addActionError("请输入正确的借款月数!");
			// return "error_ftl";
		} else if (!borrow.getAward().equals("0")) {
			if (borrow.getFunds().equals("") || borrow.getFunds().equals("0")) {
				addActionError("请输入正确的奖励!");
				return "error_ftl";
			}
		} else if (borrow.getIsDxb() != null && "1".equals(borrow.getIsDxb())
				&& (borrow.getPwd() == null || "".equals(borrow.getPwd()))) {
			addActionError("请设置定向密码!");
			return "error_ftl";
		}
		if (borrow.getIsDxb().equals("1") && borrow.getPwd().equals("")) {
			addActionError("请输入定向密码!");
			return "error_ftl";
		}
		return null;
	}
	
	/**
	 * 用户中心 借款管理
	 * 
	 * @return
	 */
	@Action(value = "/borrow/borrowListCopy", results = {
			@Result(name = "success", location = "/content/borrow/borrowListCopy.ftl", type = "freemarker"),
			@Result(name = "fail", location = "/content/user/login.ftl", type = "freemarker") })
	public String borrowListCopy() {

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
			
			if (StringUtils.isNotEmpty(keywords)) {
				String str = CommonUtil.changeChar(keywords);

				qMap.put("keywords", str);
			}
			
			qMap.put("userId", user.getId());
			qMap.put("parentFlg", 1);
			int[] array = {0,1,3,5,7};
			qMap.put("array", array);
			
			qMap.put("orderBy", "create_date desc");

			
			pager = borrowService.queryBorrowListCopy(pager, qMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	@Action(value = "/borrow/saveBorrowCrowd", results = { @Result(name = "success", location = "/content/borrow/success.ftl", type = "freemarker") })
	public String saveBorrowCrowd() {
		User user = getLoginUser();
		
		String random = (String) getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		
		if(StringUtils.isEmpty(user.getPayPassword())){
			addActionError("请设置安全密码!");
			return "error_ftl";
		}
		if (!this.getMycode().equalsIgnoreCase(random)) {
			addActionError("验证码输入错误!");
			return "error_ftl";
		}

		if (!userService.isPassword(user.getUsername(), safepwd, "1")) {
//			return ajax(Status.warn,"安全密码输入错误!");
			addActionError("安全密码输入错误!");
			return "error_ftl";
		}
		if (borrow.getName().equals("") || borrow.getName() == null) {
//			return ajax(Status.warn,"标题不能为空，请重新输入!");
			addActionError("标题不能为空，请重新输入!");
			return "error_ftl";
		} else if (borrow.getAccount() == null
				|| borrow.getAccount().equals("")) {
//			return ajax(Status.warn,"募集金额不能为空，请重新输入!");
			addActionError("募集金额不能为空，请重新输入!");
			return "error_ftl";
		}else if (borrow.getUserId()== null) {

			addActionError("借款人不能为空，请添加!");
			return "error_ftl";
//		}else if (borrow.getAccountYes() == null
//				|| borrow.getAccountYes().equals("")) {
////			return ajax(Status.warn,"募集金额不能为空，请重新输入!");
//			addActionError("车辆估价金额不能为空，请重新输入!");
//			return "error_ftl";
//		} else if (borrow.getContent() == null
//				|| borrow.getContent().equals("")) {
////			return ajax(Status.warn,"车辆描述不能为空，请重新输入!");
//			addActionError("车辆描述不能为空，请重新输入!");
//			return "error_ftl";
//		} else if (StringUtil.isEmpty(borImageFirst)) {
//			addActionError("图片不能为空，请重新输入!");
//			return "error_ftl";
//		} else if (borrow.getApr() <= 0 || borrow.getApr() >= 30) {
//			return ajax(Status.warn,"请输入正确的借款利率!");
//		} else if (borrow.getTimeLimit().equals("")
//				|| borrow.getTimeLimit() == null) {
//			return ajax(Status.warn,"请输入正确的借款月数!");
//		} 
		}else if (!borrow.getAward().equals("0")) {
			if (borrow.getFunds().equals("") || borrow.getFunds().equals("0")) {
				addActionError("请输入正确的奖励!");
				return "error_ftl";
			}
		} else if (borrow.getIsDxb() != null && "1".equals(borrow.getIsDxb())
				&& (borrow.getPwd() == null || "".equals(borrow.getPwd()))) {
			addActionError("请设置定向密码!");
			return "error_ftl";
		}
		if (borrow.getIsDxb().equals("1") && borrow.getPwd().equals("")) {
			addActionError("请输入定向密码!");
			return "error_ftl";
		}
		
//		String carInTime = (String) carInfoJson.getCarSpec().subSequence(0, 4);
//		if(Integer.valueOf(carInTime)>Integer.valueOf(carTime.getOnCardTimeYear())){
//			addActionError("上牌年限选择有误!");
//			return "error_ftl";
//		}
//		if(StringUtils.isEmpty(areaId)){
//			return ajax(Status.warn,"请选择项目所属地!");
//		}
//		String domain = areaService.getAreaDomain(areaId);
//		String[] areaStore = CommonUtil.splitString(domain);
//		borrow.setProvince(areaStore[0]);//省
//		borrow.setCity(areaStore[1]);//市
//		borrow.setArea(areaStore[2]);//区
//		StringBuffer areaSotreBuffer = new StringBuffer();
//		if(StringUtils.isNotEmpty(areaStore[0])){
//			areaSotreBuffer.append(areaService.getAreaName(areaStore[0]));
//			if(StringUtils.isNotEmpty(areaStore[1])){
//				areaSotreBuffer.append(",").append(areaService.getAreaName(areaStore[1]));
//				if(StringUtils.isNotEmpty(areaStore[2])){
//					areaSotreBuffer.append(",").append(areaService.getAreaName(areaStore[2]));
//				}
//			}
//		}
		
//		borrow.setAreaStore(areaSotreBuffer.toString());
		borrow.setSchedule("0");
		// borrow.setRepaymentAccount(total);//TODO 初审时设置，复审时更新
		borrow.setAgencyId(user.getAgencyid());
		borrow.setOperatorIp(getRequestRemoteAddr());
		borrow.setUpdatePersion(String.valueOf(user.getId()));
		borrow.setModifyDate(new Date());
		borrow.setCreateDate(new Date());
		borrow.setAddTime(new Date());
		borrow.setBalance(borrow.getAccount());
		borrow.setTenderTimes("0");// 投标次数
		
		String messJson = JsonUtil.beanToJson(verifyMessJson);
		borrow.setBorrowVerifyJson(messJson);

		borrow.setAutoTenderRepayWay(Integer.parseInt(borrow.getStyle()));
		if ("0".equals(borrow.getIsday())) {
			borrow.setFinalRateYear(BigDecimal.valueOf(borrow.getApr() / 100));
			borrow.setVaryYearRate(borrow.getFinalRateYear());
			borrow.setVaryMonthLimit(NumberUtil.setPriceScale(new BigDecimal(borrow.getTimeLimit())));
		} else if ("1".equals(borrow.getIsday())) {
			borrow.setFinalRateYear(BigDecimal.valueOf(borrow.getApr() / 1000 * 360));
			borrow.setVaryYearRate(borrow.getFinalRateYear());
			borrow.setVaryMonthLimit(NumberUtil.setPriceScale(BigDecimal.valueOf(
					Integer.parseInt(borrow.getTimeLimit())/30)));
		}


//		borrow.setBorImageFirst(CommonUtil.decodeUrl(borImageFirst));
//		carInfoJson.setOnCardTime(carTime.getOnCardTimeYear()+"年"+carTime.getOnCardTimeMonth()+"月");
//		carInfoJson.setCertificationTime(carTime.getCertificationTimeYear()+"年"+carTime.getCertificationTimeMonth()+"月");
//		carInfoJson.setWarrantyExpiresTime(carTime.getWarrantyExpiresTimeYear()+"年"+carTime.getWarrantyExpiresTimeMonth()+"月");
//		carInfoJson.setInsurancExpiresTime(carTime.getInsurancExpiresTimeYear()+"年"+carTime.getInsurancExpiresTimeMonth()+"月");
//		carInfoJson.setCommercialExpiresTime(carTime.getCommercialExpiresTimeYear()+"年"+carTime.getCommercialExpiresTimeMonth()+"月");
//		
//		
//		String carJson = JsonUtil.beanToJson(carInfoJson);
//		
//		borrow.setBorrowInfoJson(carJson);
		if (vouchers != null && vouchers.length > 0 && vouchersTitle != null
				&& vouchersTitle.length > 0) {
			List<NoteImg> noteImgList = new ArrayList<NoteImg>();
			List<NoteImg> carImgList = new ArrayList<NoteImg>();
			for (int i = 0; i < vouchers.length; i++) {
				String devc = CommonUtil.decodeUrl(vouchers[i]);
				NoteImg nt = new NoteImg();
				nt.setUrl(devc);
				nt.setName(vouchersTitle[i]);
//				if(vouchersTitle[i].equals("1")){
//					carImgList.add(nt);
//				}else{
					noteImgList.add(nt);
//				}
				
			}

			String tempVcs = JsonUtil.listToJson(noteImgList);

			borrow.setBorImage(tempVcs);//交易凭证
//			String imgVcs = JsonUtil.listToJson(carImgList);
//
//			borrow.setBorImage2(imgVcs);//车辆图片
		}
		

//		if (carTitle != null && carTitle.length > 0 && carImg != null
//				&& carImg.length > 0) {
//			List<NoteImg> noteImgList = new ArrayList<NoteImg>();
//			for (int i = 0; i < carImg.length; i++) {
//				String devc = CommonUtil.decodeUrl(carImg[i]);
//				NoteImg nt = new NoteImg();
//				nt.setUrl(devc);
//				nt.setName(carTitle[i]);
//				noteImgList.add(nt);
//			}
//
//			
//		}
		if(borrow.getIsday().equals("0")){
			borrow.setDivides(Integer.valueOf(borrow.getTimeLimit()));
		}
		log.info("添加众筹项目");
		borrowService.addBorrow(borrow);

		return SUCCESS;
	}
	

	@Action(value="/borrow/ajaxBrand",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String ajaxBrand(){
		URL url = null;
		HttpURLConnection con = null;
		String paramters = "type=1";
		String inputLine = "";
		try {
			url = new URL("http://www.autohome.com.cn/ashx/AjaxIndexCarFind.ashx");
			con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");

			OutputStream out = con.getOutputStream();

			out.write(paramters.getBytes("GBK"));
			out.flush();
			out.close();
			
			BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream(),"GBK"));
			inputLine=in.readLine();							
			in.close();
			System.out.println("inputLine=="+inputLine);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ajax(Status.success,inputLine);
	}
	
	
	@Action(value="/borrow/ajaxSeries",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String ajaxSeries(){
		URL url = null;
		HttpURLConnection con = null;
		String paramters = "type=3&value="+brandId;
		String inputLine = "";
		try {
			url = new URL("http://www.autohome.com.cn/ashx/AjaxIndexCarFind.ashx");
			con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");

			OutputStream out = con.getOutputStream();

			out.write(paramters.getBytes("GBK"));
			out.flush();
			out.close();
			
			BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream(),"GBK"));
			 inputLine=in.readLine();							
			in.close();
			System.out.println("inputLine=="+inputLine);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ajax(Status.success,inputLine);
	}
	

	@Action(value="/borrow/ajaxSpec",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String ajaxSpec(){
		URL url = null;
		HttpURLConnection con = null;
		String paramters = "type=5&value="+seriesId;
		String inputLine = "";
		try {
			url = new URL("http://www.autohome.com.cn/ashx/AjaxIndexCarFind.ashx");
			con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");

			OutputStream out = con.getOutputStream();

			out.write(paramters.getBytes("GBK"));
			out.flush();
			out.close();
			
			BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream(),"GBK"));
			inputLine=in.readLine();							
			in.close();
			System.out.println("inputLine=="+inputLine);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ajax(Status.success,inputLine);
	}
	

	private String checkTenderInput() {
		return null;
	}

	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	
	public int getBorrowIsday() {
		return borrowIsday;
	}

	public void setBorrowIsday(int borrowIsday) {
		this.borrowIsday = borrowIsday;
	}

	public BigDecimal getBorrowAccount() {
		return borrowAccount;
	}

	public void setBorrowAccount(BigDecimal borrowAccount) {
		this.borrowAccount = borrowAccount;
	}

	public BigDecimal getBorrowApr() {
		return borrowApr;
	}

	public void setBorrowApr(BigDecimal borrowApr) {
		this.borrowApr = borrowApr;
	}

	public int getBorrowTimeLimit() {
		return borrowTimeLimit;
	}

	public void setBorrowTimeLimit(int borrowTimeLimit) {
		this.borrowTimeLimit = borrowTimeLimit;
	}

	public int getBorrowStyle() { 
		return borrowStyle;
	}

	public void setBorrowStyle(int borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public int getBorrowDivides() {
		return borrowDivides;
	}

	public void setBorrowDivides(int borrowDivides) {
		this.borrowDivides = borrowDivides;
	}

	public List<BorrowRepaymentDetail> getBorrowRepaymentDetailList() {
		return borrowRepaymentDetailList;
	}

	public void setBorrowRepaymentDetailList(
			List<BorrowRepaymentDetail> borrowRepaymentDetailList) {
		this.borrowRepaymentDetailList = borrowRepaymentDetailList;
	}

	public BorrowRepaymentDetail getBorrowRepaymentDetail() {
		return borrowRepaymentDetail;
	}

	public void setBorrowRepaymentDetail(
			BorrowRepaymentDetail borrowRepaymentDetail) {
		this.borrowRepaymentDetail = borrowRepaymentDetail;
	}

	public String getMycode() {
		return mycode;
	}

	public void setMycode(String mycode) {
		this.mycode = mycode;
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

	public RepaymentInfo getRepaymentInfo() {
		return repaymentInfo;
	}

	public void setRepaymentInfo(RepaymentInfo repaymentInfo) {
		this.repaymentInfo = repaymentInfo;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public List<NoteImg> getBorrowImgList() {
		return borrowImgList;
	}

	public void setBorrowImgList(List<NoteImg> borrowImgList) {
		this.borrowImgList = borrowImgList;
	}

	public String getSafepwd() {
		return safepwd;
	}

	public void setSafepwd(String safepwd) {
		this.safepwd = safepwd;
	}

	public String[] getCarTitle() {
		return carTitle;
	}

	public void setCarTitle(String[] carTitle) {
		this.carTitle = carTitle;
	}

	public CarInfoJson getCarInfoJson() {
		return carInfoJson;
	}

	public void setCarInfoJson(CarInfoJson carInfoJson) {
		this.carInfoJson = carInfoJson;
	}

	public CarTime getCarTime() {
		return carTime;
	}

	public void setCarTime(CarTime carTime) {
		this.carTime = carTime;
	}

	public String[] getCarImg() {
		return carImg;
	}

	public void setCarImg(String[] carImg) {
		this.carImg = carImg;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(String seriesId) {
		this.seriesId = seriesId;
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

	public VerifyMessJson getVerifyMessJson() {
		return verifyMessJson;
	}

	public void setVerifyMessJson(VerifyMessJson verifyMessJson) {
		this.verifyMessJson = verifyMessJson;
	}


	// //////////////////////////////////////////////////////////////////////////////////

}
