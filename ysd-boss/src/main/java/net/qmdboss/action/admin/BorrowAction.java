package net.qmdboss.action.admin;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import net.qmdboss.entity.*;
import net.qmdboss.service.*;
import net.qmdboss.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.SecureRandom;
import java.util.Random;


@ParentPackage("admin")
public class BorrowAction extends BaseAdminAction {
	Logger log = Logger.getLogger(BorrowAction.class);
	
	private Logger verifyLog = Logger.getLogger("bossVerifyLog");
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Borrow borrow;
	private Integer borrowId;
	SettingUtil settingUtil;
	InterestCalUtil interestCalUtil;
	@Resource(name = "borrowServiceImpl")
	private BorrowService borrowService ;
	@Resource(name = "adminServiceImpl")
	private AdminService  adminService;
	
	@Resource(name = "listingServiceImpl")
	private ListingService  listingService;
	
	@Resource(name = "userAccountServiceImpl")
	private UserAccountService userAccountService;
	
	@Resource(name = "borrowDetailServiceImpl")
	private BorrowDetailService borrowDetailService;
	
	@Resource(name = "userAccountDetailServiceImpl")
	private UserAccountDetailService userAccountDetailService;
	
	@Resource(name = "borrowRepaymentDetailServiceImpl")
	private BorrowRepaymentDetailService borrowRepaymentDetailService;
	
	@Resource(name = "userRepaymentDetailServiceImpl")
	private UserRepaymentDetailService userRepaymentDetailService;
	
	@Resource(name = "borrowPledgeServiceImpl")
	private BorrowPledgeService borrowPledgeService;
	@Resource(name = "borrowSecondServiceImpl")
	private BorrowSecondService borrowSecondService;
	@Resource(name = "borrowWanderServiceImpl")
	private BorrowWanderService borrowWanderServiceImpl;
	@Resource(name = "borrowMonthServiceImpl")
	private BorrowMonthService borrowMonthServiceImpl;
	@Resource(name = "borrowFlowServiceImpl")
	private BorrowFlowService borrowFlowServiceImpl;
	
	@Resource(name = "borrowPromotePledgeServiceImpl")
	private BorrowPromoteService borrowPromotePledgeServiceImpl;
	@Resource(name = "borrowPromoteFlowServiceImpl")
	private BorrowPromoteService borrowPromoteFlowServiceImpl;


	@Resource(name = "userServiceImpl")
	private UserService userService;

	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	
	private PaymentView retBorrow;
	
	private WanderRepayPlanDetail wanderRepayPlanDetail;
	List<BorrowRepaymentDetail> borrowRepaymentDetailList;
	
	private Integer borrowStatus;
	
	private List<Listing> continueAwardRateList;
	private VerifyMessJson verifyMessJson;
	private List<NoteImg> varImgList;
	private List<NoteImg> carImgList;
	private File file;
	private String type;
	
	
	/**
	 * 所有标列表
	 * @return
	 */
	public String list() {
		try {
			if (borrow==null) {
				borrow = new Borrow();
			}
			pager = borrowService.getBorrowPage(borrow,  pager);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	//发标待审标列表?borrow.status=0
	public String tenderList(){
		try {
			if (borrow==null) {
				borrow = new Borrow();
				borrow.setStatus(0);
			}
			pager = borrowService.getBorrowPage(borrow,  pager);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	//满标待审标列表?borrow.status=5
	public String fullList(){
		try {
			if (borrow==null) {
				borrow = new Borrow();
				borrow.setStatus(5);
			}
			pager = borrowService.getBorrowPage(borrow,  pager);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	/**
	 * 标列表
	 * @return
	 */
	public String borrowList() {
			pager = borrowService.getBorrowPage(borrow, pager);
		return "borrowList";
	}
	
	/**
	 * 过期标查询
	 * @return
	 */
	public String outDateBorrow(){
		pager = borrowService.getOutDateBorrowPage(borrow, borrowId, pager);
		return "outDate";
	}
	
	/**
	 * 修改编辑审核标
	 * 
	 * @return
	 */
	public String edit() {
		borrow = borrowService.load(id);

		// 质押标：计算每期收益
		if ("1".equals(borrow.getType())) {
			// 计算标的每一期的收益
			InterestCalUtil rp = new InterestCalUtil();
			retBorrow = rp.payback(borrow.getAccount().doubleValue(), // 计算值
					borrow.getAccount().doubleValue(), // 标的总额
					borrow.getApr() , // 天利率
					Integer.valueOf(borrow.getTimeLimit()), // 借款期限（天）
					borrow.getBorStages(),2);// 还款计划
		}else if("4".equals(borrow.getType())){
			// 计算标的每一期的收益
			InterestCalUtil rp = new InterestCalUtil();
			retBorrow = rp.payback(borrow.getAccount().doubleValue(), // 计算值
						borrow.getAccount().doubleValue(), // 标的总额
						borrow.getApr() , // 年利率
						Integer.valueOf(borrow.getTimeLimit()), // 借款期限（月）
						borrow.getBorStages(),1);// 还款计划
		} else if("2".equals(borrow.getType())) {
			wanderRepayPlanDetail = new WanderRepayPlanDetail(borrow);
		}
		varImgList = new ArrayList<NoteImg>();

		String carImg = borrow.getBorImage();
		if (carImg != null && !"".equals(carImg.trim())) {

			try {
				JSONArray jsonarray = JSONArray.fromObject(carImg);
				// System.out.println(jsonarray);
				varImgList = (List<NoteImg>) JSONArray.toList(
						jsonarray, NoteImg.class);
			} catch (JSONException je) {
				NoteImg ni = new NoteImg();
				ni.setName("");
				ni.setUrl(carImg);
				varImgList.add(ni);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
//		carImgList = new ArrayList<NoteImg>();
//
//		String imgs = borrow.getBorImage2();
//		if (imgs != null && !"".equals(imgs.trim())) {
//
//			try {
//				JSONArray jsonarray = JSONArray.fromObject(imgs);
//				// System.out.println(jsonarray);
//				carImgList = (List<NoteImg>) JSONArray.toList(
//						jsonarray, NoteImg.class);
//			} catch (JSONException je) {
//				NoteImg ni = new NoteImg();
//				ni.setName("");
//				ni.setUrl(carImg);
//				carImgList.add(ni);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}


		continueAwardRateList = listingService.getChildListingBySignList(ConstantUtil.CONTINUE_AWARD_RATE_LIST);

		return INPUT;
	}
	/**
	 * 查询标详情
	 * @return
	 * @author zhanf
	 */
	public String query(){
		borrow = borrowService.load(id);
		if ("1".equals(borrow.getType())) {
			// 计算标的每一期的收益
			InterestCalUtil rp = new InterestCalUtil();
			retBorrow = rp.payback(borrow.getAccount().doubleValue(), // 计算值
					borrow.getAccount().doubleValue(), // 标的总额
					borrow.getApr() , // 天利率
					Integer.valueOf(borrow.getTimeLimit()), // 借款期限（天）
					borrow.getBorStages(),2);// 还款计划
		}else if("4".equals(borrow.getType())){
			// 计算标的每一期的收益
			if(borrow.getStatus()==3){
				borrowRepaymentDetailList=borrowRepaymentDetailService.getByBorrowId(id);
				
			}else{
				InterestCalUtil rp = new InterestCalUtil();
				retBorrow = rp.payback(borrow.getAccount().doubleValue(), // 计算值
							borrow.getAccount().doubleValue(), // 标的总额
							borrow.getApr() , // 年利率
							Integer.valueOf(borrow.getTimeLimit()), // 借款期限（月）
							borrow.getBorStages(),1);// 还款计划
			}
		}else if("2".equals(borrow.getType())) {
			wanderRepayPlanDetail = new WanderRepayPlanDetail(borrow);
		}
		return "details";
	}
	
	
	/**
	 * 查询并可修改标内容
	 * @return
	 * @author zhanf
	 */
	public String updateContent(){
		if(StringUtils.isEmpty(type)){
			type="0";
		}
		borrow = borrowService.load(id);
		if ("1".equals(borrow.getType())) {
			// 计算标的每一期的收益
			InterestCalUtil rp = new InterestCalUtil();
			retBorrow = rp.payback(borrow.getAccount().doubleValue(), // 计算值
					borrow.getAccount().doubleValue(), // 标的总额
					borrow.getApr() , // 天利率
					Integer.valueOf(borrow.getTimeLimit()), // 借款期限（天）
					borrow.getBorStages(),2);// 还款计划
		}else if("4".equals(borrow.getType())){
			// 计算标的每一期的收益
			InterestCalUtil rp = new InterestCalUtil();
			retBorrow = rp.payback(borrow.getAccount().doubleValue(), // 计算值
						borrow.getAccount().doubleValue(), // 标的总额
						borrow.getApr() , // 年利率
						Integer.valueOf(borrow.getTimeLimit()), // 借款期限（月）
						borrow.getBorStages(),1);// 还款计划
		}else if("2".equals(borrow.getType())) {
			wanderRepayPlanDetail = new WanderRepayPlanDetail(borrow);
		}
		return "contents";
	}
	
	// 撤回
		public String withdraw() {
			StringBuffer logInfoStringBuffer = new StringBuffer();
				Admin admin = adminService.getLoginAdmin();
			boolean flag =	borrowService.withdrawBorrow(id, admin);
			if(flag){
				logInfoStringBuffer.append( "用户名为："+adminService.getLoginAdmin().getUsername()+"撤回标: " + id);
				logInfo = logInfoStringBuffer.toString();
				addActionMessage("撤回成功");
				return ajax(Status.success, "撤回成功!");
			}
			if (!flag) {
				addActionMessage("撤回失败");
				return ajax(Status.error, "撤回失败!");
			}
//			redirectUrl = "borrow!list.action";
			return ajax(Status.success, "撤回成功!");

//			return "success";
			
		}
		
		/**
		 * 审核标操作并修改一些记录
		 * 新接口
		 * @author zdl
		 */
	@InputConfig(resultName = "error")
	public String updateNew() {
		
		verifyLog.debug("【标审核 开始】["+id+"]");
		int flg = -1;
		try{
		settingUtil = new SettingUtil();
		//Borrow borrow1 =borrowService.loadLock(id) ;
		//UserAccount  userAccount;
		Admin admin = adminService.getLoginAdmin();
		StringBuffer logInfoStringBuffer = new StringBuffer();



		if(borrowStatus==0){// 初审
			
			if ("2".equals(borrow.getType())) {// 流转标：直接将标发布成招标中
				verifyLog.debug("   初审  流转标["+id+"]");
				flg = borrowWanderServiceImpl.updateBorrowWander(borrow,id, admin);// 审核流转标
			} else if ("5".equals(borrow.getType())) {// 流转标：直接将标发布成招标中
				verifyLog.debug("   初审  流转标["+id+"]");
				flg = borrowFlowServiceImpl.updateBorrowFlow(borrow,id, admin);// 审核流转标
			} else if ("11".equals(borrow.getType())||"12".equals(borrow.getType())||"14".equals(borrow.getType())||"15".equals(borrow.getType())||"16".equals(borrow.getType())||"17".equals(borrow.getType())||"20".equals(borrow.getType())) {// 直接将标发布成招标中
				verifyLog.debug("   初审  标["+id+"]");
				
				if(borrow.getFeeType()!=null && borrow.getFeeType()==0&&StringUtils.isEmpty(borrow.getPartAccount())){
					this.addErrorMessages("借款手续费不能为空!");
//							addActionError("登录密码不能为空!");
					return ERROR;
				}
//					
				if (file != null) {
					String filePath = ImageUtil.copyImageFile(getServletContext(), file);
					borrow.setBorImageFirst(filePath);
				} else {
					this.addErrorMessages("法律意见书不能为空");
					return ERROR;
				}
				Borrow borrow1 = borrowService.get(id);
				if(borrow1.getStatus().compareTo(0)!=0){
					this.addErrorMessages("此标状态已不是初审状态，请查验再操作");
					return ERROR;
				}

				if(borrow.getBaseApr() == null || borrow.getAwardApr() == null){
					this.addErrorMessages("请输入显示年利率");
					return ERROR;
				}
				if(borrow1.getVaryYearRate().multiply(new BigDecimal(100)).compareTo( borrow.getBaseApr().add(borrow.getAwardApr())) !=0 ){
					this.addErrorMessages("显示年利率跟实际年利率不一致");
					return ERROR;
				}
				if(borrow.getType().equals("17")){
					String messJson = JsonUtil.beanToJson(verifyMessJson);
					borrow.setBorrowVerifyJson(messJson);
				}else{
					borrow.setBorrowVerifyJson(borrow1.getBorrowVerifyJson());
				}
				if(borrow.getDepositMoney().compareTo(BigDecimal.ZERO)<0 || borrow.getDepositMoney().compareTo(borrow1.getAccount().divide(new BigDecimal(2))) >=0){
					addErrorMessages("保证金不能超过项目金额的50%,审核失败!");
					return ERROR;
				}
				if (borrow.getFeeType() == null || borrow.getFeeType() == 0) {
					if(new BigDecimal(borrow.getPartAccount()).compareTo(BigDecimal.ZERO)<0 || new BigDecimal(borrow.getPartAccount()).compareTo((new BigDecimal(50))) >=0){
						addErrorMessages("手续费不能超过项目金额的50%,审核失败!");
						return ERROR;
					}
				}else{
					if(borrow.getFeeMoney().compareTo(BigDecimal.ZERO)<0 || borrow.getFeeMoney().compareTo(borrow1.getAccount().divide(new BigDecimal(2))) >=0){
						addErrorMessages("手续费不能超过项目金额的50%,审核失败!");
						return ERROR;
					}
				}
				flg = borrowPromotePledgeServiceImpl.updateBorrowPreliminary(borrow,id, admin);// 审核标
			} else if ("13".equals(borrow.getType())) {// 直接将标发布成招标中
				verifyLog.debug("   初审  标["+id+"]");
				flg = borrowPromoteFlowServiceImpl.updateBorrowPreliminary(borrow,id, admin);// 审核流转标
			} else {
				verifyLog.debug("   初审  非流转标["+id+"]");
				flg = borrowService.updateBorrowPreliminary(borrow,id,admin);
			}
			logInfoStringBuffer.append("用户名为："+adminService.getLoginAdmin().getUsername()+"初审核标: " + borrow.getName());
		}else if(borrowStatus==5){// 满标审核
			
			if (borrow.getStatus()==4) {// 审核不通过
				verifyLog.debug("   满标审核  不通过["+id+"]");
				//满标审核不通过，回滚投资人投资金额
				flg = borrowService.updateBorrowBack(borrow, id, admin);
				logInfoStringBuffer.append("用户名为："+adminService.getLoginAdmin().getUsername()+"审核标: " + borrow.getName()+"失败");
			} else if (borrow.getStatus()==3) {// 审核通过
				
				if ("0".equals(borrow.getType())) {// 秒标
					verifyLog.debug("   满标审核  通过  秒标["+id+"]");
					// 满标审核:秒标
					flg = borrowSecondService.updateBorrowSecondFull(borrow, id, admin);
				}else if ("1".equals(borrow.getType())) {// 质押标
					verifyLog.debug("   满标审核  通过  天标["+id+"]");
					// 满标审核:质押标
					flg = borrowPledgeService.updateBorrowPledgeFull(borrow, id, admin);
				}else if("4".equals(borrow.getType())){//月标
					verifyLog.debug("   满标审核  通过  月标["+id+"]");
					flg = borrowMonthServiceImpl.updateBorrowMonthFull(borrow, id, admin);
				} else if("11".equals(borrow.getType())||"12".equals(borrow.getType())||"14".equals(borrow.getType())||"15".equals(borrow.getType())||"16".equals(borrow.getType())){//标
					verifyLog.debug("   满标审核  通过  标["+id+"]");
					Borrow borrow1 = borrowService.get(id);
					if(borrow1.getStatus().compareTo(5)!=0){
						this.addErrorMessages("此标状态已不是满标审核状态，请查验再操作");
						return ERROR;
					}
					flg = borrowPromotePledgeServiceImpl.updateBorrowFullNew(borrow, id, admin);
				}else if("17".equals(borrow.getType())){//标
					verifyLog.debug("   满标审核  通过  标["+id+"]");
					flg = borrowPromotePledgeServiceImpl.updateTiYanBorrowFull(borrow, id, admin);
				} else if("13".equals(borrow.getType())){//标
					verifyLog.debug("   满标审核  通过  标["+id+"]");
					flg = borrowPromoteFlowServiceImpl.updateBorrowFull(borrow, id, admin);
				}
				 
				if (flg==0) {
					verifyLog.debug("   满标审核  通过后 发送邮件["+id+"]");
					//查找所有投过此标的用户-xsf
					Borrow borrow1 =borrowService.get(id);
					System.out.println("成功投标人数:"+borrow1.getBorrowDetailSet().size()+"人");
					for(BorrowDetail borrowDetail:borrow1.getBorrowDetailSet()){
						if(borrowDetail.getUser().getEmailStatus()!=null && borrowDetail.getUser().getEmailStatus().equals(1)){
							mailService.sendFullBorrow(borrowDetail,borrow1);
						}
					}
				}
				
				logInfoStringBuffer.append("用户名为："+adminService.getLoginAdmin().getUsername()+"审核满标: " + borrow.getName());
			}
			
			if (flg==1) {
				addActionMessage("标的状态已改,审核失败!");
				addErrorMessages("标的状态已改,审核失败!");
			}if (flg==2) {
				addActionMessage("还款利息大于借款人可用余额,审核失败!");
				addErrorMessages("还款利息大于借款人可用余额,审核失败!");
			} else if(flg != 0) {
				addActionMessage("审核失败!");
				addErrorMessages("审核失败!");
			}
	
		}



		logInfo =logInfoStringBuffer.toString() ;
		
		redirectUrl = "borrow!list.action";

		}catch(Exception e) {
			e.printStackTrace();
			verifyLog.debug("【标审核 异常】["+e.getMessage()+"]");
			addActionMessage("系统异常,审核失败!");
			addErrorMessages("系统异常,审核失败!");
			flg = -1;
		}
		
		verifyLog.debug("【标审核 结束】["+id+"] ret["+flg+"]");

		/*if(borrowStatus==0 && flg ==0 && borrow.getStatus().intValue() == 1){
			//调用存管接口
			Borrow bb = borrowService.get(id);
			String pay_url = SettingUtil.getSetting().getPayUrl();

			log.debug("pay_url:"+pay_url);
			Map<String,String> pmap = new HashMap<String,String>();
			pmap.put("borrowId",String.valueOf(id));
			pmap.put("borrowAmt",String.valueOf(bb.getAccount()));
			pmap.put("borrCustId",userService.get(bb.getUser().getId()).getBankCustNo());
			pmap.put("borrowerInterestAmt",String.valueOf(bb.getVaryYearRate().doubleValue()*100));
			pmap.put("borrowType","1");
			pmap.put("validTime",bb.getValidTime());
			pmap.put("investDateType","1");
			pmap.put("investPeriod",bb.getTimeLimit());
			pmap.put("name",bb.getName());

			String msg = om.ysd.util.HttpUtil.post(pay_url+"/addProject",pmap);
			if(msg == null || "".equals(msg)){
				addActionError("存管服务器无法访问,请重新尝试！");

				//状态修改为 status = 0, cg_status = 4;
				//borrowService.update();

				return ERROR;
			}
			return SUCCESS;
		}*/


		if (flg != 0) {
			return ERROR;
		}
		
		return SUCCESS;
	}
		
	/**
	 * 审核标操作并修改一些记录
	 * @return
	 */
		@InputConfig(resultName = "error")
		public String update() {
			
			verifyLog.debug("【标审核 开始】["+id+"]");
			int flg = -1;
			try{
			settingUtil = new SettingUtil();
			//Borrow borrow1 =borrowService.loadLock(id) ;
			//UserAccount  userAccount;
			Admin admin = adminService.getLoginAdmin();
			StringBuffer logInfoStringBuffer = new StringBuffer();
			
			if(borrowStatus==0){// 初审
				
				if ("2".equals(borrow.getType())) {// 流转标：直接将标发布成招标中
					verifyLog.debug("   初审  流转标["+id+"]");
					flg = borrowWanderServiceImpl.updateBorrowWander(borrow,id, admin);// 审核流转标
				} else if ("5".equals(borrow.getType())) {// 流转标：直接将标发布成招标中
					verifyLog.debug("   初审  流转标["+id+"]");
					flg = borrowFlowServiceImpl.updateBorrowFlow(borrow,id, admin);// 审核流转标
				} else if ("11".equals(borrow.getType())||"12".equals(borrow.getType())||"14".equals(borrow.getType())||"15".equals(borrow.getType())||"16".equals(borrow.getType())||"17".equals(borrow.getType())) {// 直接将标发布成招标中
					verifyLog.debug("   初审  标["+id+"]");
					
					if(borrow.getFeeType()!=null && borrow.getFeeType()==0&&StringUtils.isEmpty(borrow.getPartAccount())){
						this.addErrorMessages("借款手续费不能为空!");
//						addActionError("登录密码不能为空!");
						return ERROR;
					}
//				
					if (file != null) {
						String filePath = ImageUtil.copyImageFile(getServletContext(), file);
						borrow.setBorImageFirst(filePath);
					} else {
						this.addErrorMessages("法律意见书不能为空");
						return ERROR;
					}
					Borrow borrow1 = borrowService.get(id);
					if(borrow1.getStatus().compareTo(0)!=0){
						this.addErrorMessages("此标状态已不是初审状态，请查验再操作");
						return ERROR;
					}

					if(borrow.getBaseApr() == null || borrow.getAwardApr() == null){
						this.addErrorMessages("请输入显示年利率");
						return ERROR;
					}
					if(borrow1.getVaryYearRate().multiply(new BigDecimal(100)).compareTo( borrow.getBaseApr().add(borrow.getAwardApr())) !=0 ){
						this.addErrorMessages("显示年利率跟实际年利率不一致");
						return ERROR;
					}
					if(borrow.getType().equals("17")){
						String messJson = JsonUtil.beanToJson(verifyMessJson);
						borrow.setBorrowVerifyJson(messJson);
					}else{
						borrow.setBorrowVerifyJson(borrow1.getBorrowVerifyJson());
					}
					if(borrow.getDepositMoney().compareTo(BigDecimal.ZERO)<0 || borrow.getDepositMoney().compareTo(borrow1.getAccount().divide(new BigDecimal(2))) >=0){
						addErrorMessages("保证金不能超过项目金额的50%,审核失败!");
						return ERROR;
					}
					if (borrow.getFeeType() == null || borrow.getFeeType() == 0) {
						if(new BigDecimal(borrow.getPartAccount()).compareTo(BigDecimal.ZERO)<0 || new BigDecimal(borrow.getPartAccount()).compareTo((new BigDecimal(50))) >=0){
							addErrorMessages("手续费不能超过项目金额的50%,审核失败!");
							return ERROR;
						}
					}else{
						if(borrow.getFeeMoney().compareTo(BigDecimal.ZERO)<0 || borrow.getFeeMoney().compareTo(borrow1.getAccount().divide(new BigDecimal(2))) >=0){
							addErrorMessages("手续费不能超过项目金额的50%,审核失败!");
							return ERROR;
						}
					}
					flg = borrowPromotePledgeServiceImpl.updateBorrowPreliminary(borrow,id, admin);// 审核标
				} else if ("13".equals(borrow.getType())) {// 直接将标发布成招标中
					verifyLog.debug("   初审  标["+id+"]");
					flg = borrowPromoteFlowServiceImpl.updateBorrowPreliminary(borrow,id, admin);// 审核流转标
				} else {
					verifyLog.debug("   初审  非流转标["+id+"]");
					flg = borrowService.updateBorrowPreliminary(borrow,id,admin);
				}
				logInfoStringBuffer.append("用户名为："+adminService.getLoginAdmin().getUsername()+"初审核标: " + borrow.getName());
			}else if(borrowStatus==5){// 满标审核
				
				if (borrow.getStatus()==4) {// 审核不通过
					verifyLog.debug("   满标审核  不通过["+id+"]");
					//满标审核不通过，回滚投资人投资金额
					flg = borrowService.updateBorrowBack(borrow, id, admin);
					logInfoStringBuffer.append("用户名为："+adminService.getLoginAdmin().getUsername()+"审核标: " + borrow.getName()+"失败");
				} else if (borrow.getStatus()==3) {// 审核通过
					
					if ("0".equals(borrow.getType())) {// 秒标
						verifyLog.debug("   满标审核  通过  秒标["+id+"]");
						// 满标审核:秒标
						flg = borrowSecondService.updateBorrowSecondFull(borrow, id, admin);
					}else if ("1".equals(borrow.getType())) {// 质押标
						verifyLog.debug("   满标审核  通过  天标["+id+"]");
						// 满标审核:质押标
						flg = borrowPledgeService.updateBorrowPledgeFull(borrow, id, admin);
					}else if("4".equals(borrow.getType())){//月标
						verifyLog.debug("   满标审核  通过  月标["+id+"]");
						flg = borrowMonthServiceImpl.updateBorrowMonthFull(borrow, id, admin);
					} else if("11".equals(borrow.getType())||"12".equals(borrow.getType())||"14".equals(borrow.getType())||"15".equals(borrow.getType())||"16".equals(borrow.getType())){//标
						verifyLog.debug("   满标审核  通过  标["+id+"]");
						Borrow borrow1 = borrowService.get(id);
						if(borrow1.getStatus().compareTo(5)!=0){
							this.addErrorMessages("此标状态已不是满标审核状态，请查验再操作");
							return ERROR;
						}
						flg = borrowPromotePledgeServiceImpl.updateBorrowFull(borrow, id, admin);
					}else if("17".equals(borrow.getType())){//标
						verifyLog.debug("   满标审核  通过  标["+id+"]");
						flg = borrowPromotePledgeServiceImpl.updateTiYanBorrowFull(borrow, id, admin);
					} else if("13".equals(borrow.getType())){//标
						verifyLog.debug("   满标审核  通过  标["+id+"]");
						flg = borrowPromoteFlowServiceImpl.updateBorrowFull(borrow, id, admin);
					}
					 
					if (flg==0) {
						verifyLog.debug("   满标审核  通过后 发送邮件["+id+"]");
						//查找所有投过此标的用户-xsf
						Borrow borrow1 =borrowService.get(id);
						System.out.println("成功投标人数:"+borrow1.getBorrowDetailSet().size()+"人");
						for(BorrowDetail borrowDetail:borrow1.getBorrowDetailSet()){
							if(borrowDetail.getUser().getEmailStatus()!=null && borrowDetail.getUser().getEmailStatus().equals(1)){
								mailService.sendFullBorrow(borrowDetail,borrow1);
							}
						}
					}
					
					logInfoStringBuffer.append("用户名为："+adminService.getLoginAdmin().getUsername()+"审核满标: " + borrow.getName());
				}
				
				if (flg==1) {
					addActionMessage("标的状态已改,审核失败!");
					addErrorMessages("标的状态已改,审核失败!");
				}if (flg==2) {
					addActionMessage("还款利息大于借款人可用余额,审核失败!");
					addErrorMessages("还款利息大于借款人可用余额,审核失败!");
				} else if(flg != 0) {
					addActionMessage("审核失败!");
					addErrorMessages("审核失败!");
				}
		
			}
			logInfo =logInfoStringBuffer.toString() ;
			
			redirectUrl = "borrow!list.action";

			}catch(Exception e) {
				e.printStackTrace();
				verifyLog.debug("【标审核 异常】["+e.getMessage()+"]");
				addActionMessage("系统异常,审核失败!");
				addErrorMessages("系统异常,审核失败!");
				flg = -1;
			}
			
			verifyLog.debug("【标审核 结束】["+id+"] ret["+flg+"]");
			
			if (flg != 0) {
				return ERROR;
			}
			
			return SUCCESS;
		}
	/**
	 * 后台修改标信息（包括正在发布和招标中的）
	 * @return
	 */
	public String updateMess(){
		Borrow borrow1 =borrowService.load(id) ;
		StringBuffer logInfoStringBuffer = new StringBuffer();
		borrow1.setMostAccount(borrow.getMostAccount());
		borrow1.setValidTime(borrow.getValidTime());
		
		borrow1.setIsDxb(borrow.getIsDxb());
		borrow1.setAward(borrow.getAward());
		borrow1.setRongXunFlg(borrow.getRongXunFlg());
		borrow1.setName(borrow.getName());
		if(!borrow.getAward().equals("0")){
			borrow1.setFunds(borrow.getFunds());
		}else {
			borrow1.setFunds("0");
		}
		if(borrow.getIsDxb().equals("1")){
			borrow1.setPwd(borrow.getPwd());
		}else{
			borrow1.setPwd("");
		}
		
		borrow1.setShowTop(borrow.getShowTop());
		if (borrow.getShowSort()==null) {
			borrow.setShowSort(1000);
		}
		borrow1.setShowSort(borrow.getShowSort());
		
		logInfoStringBuffer.append("用户名为："+adminService.getLoginAdmin().getUsername()+"修改标: " + borrow1.getName()+";修改内容为：");
		if(!borrow.getAward().equals(borrow1.getAward())){
			if(borrow.getAward().equals("0")){
				logInfoStringBuffer.append("取消奖励。");
			}else if(borrow.getAward().equals("1")){
				logInfoStringBuffer.append("设置奖励为："+borrow.getFunds()+"%;");
			}else{
				logInfoStringBuffer.append("设置奖励为："+borrow.getFunds()+"%;并设置为最后一次还款时发放奖励。");
			}
		}
		if(!borrow.getIsDxb().equals(borrow1.getIsDxb())){
			if(borrow.getAward().equals("0")){
				logInfoStringBuffer.append("取消定向密码。：");
			}else{
				logInfoStringBuffer.append("设置定向密码，密码为："+borrow.getPwd()+".");
			}
		}
//		if(borrow.getIsDxb().equals(borrow1.getIsDxb())&& (!borrow.getPwd().equals(borrow1.getPwd()))){
//			logInfoStringBuffer.append("修改定向密码，密码为："+borrow.getPwd()+".");
//		}
		borrowService.update(borrow1);
		logInfo =logInfoStringBuffer.toString() ;
//		redirectUrl = "borrow!list.action?borrow.status="+borrow1.getStatus();
		if(borrow1.getStatus() == 0){
			redirectUrl = "borrow!tenderList.action";
		}else if(borrow1.getStatus() == 5){
			redirectUrl = "borrow!fullList.action";
		}else {
			redirectUrl = "borrow!list.action";
		}
		return SUCCESS;
	}
	
	public String borrowRepayment() {
		return SUCCESS;
	}

		public Integer getBorrowId() {
			return borrowId;
		}

		public void setBorrowId(Integer borrowId) {
			this.borrowId = borrowId;
		}

		public Borrow getBorrow() {
			return borrow;
		}

		public void setBorrow(Borrow borrow) {
			this.borrow = borrow;
		}

		public PaymentView getRetBorrow() {
			return retBorrow;
		}

		public void setRetBorrow(PaymentView retBorrow) {
			this.retBorrow = retBorrow;
		}
		public WanderRepayPlanDetail getWanderRepayPlanDetail() {
			return wanderRepayPlanDetail;
		}
		public void setWanderRepayPlanDetail(WanderRepayPlanDetail wanderRepayPlanDetail) {
			this.wanderRepayPlanDetail = wanderRepayPlanDetail;
		}

		public Integer getBorrowStatus() {
			return borrowStatus;
		}

		public void setBorrowStatus(Integer borrowStatus) {
			this.borrowStatus = borrowStatus;
		}

		public List<BorrowRepaymentDetail> getBorrowRepaymentDetailList() {
			return borrowRepaymentDetailList;
		}

		public void setBorrowRepaymentDetailList(
				List<BorrowRepaymentDetail> borrowRepaymentDetailList) {
			this.borrowRepaymentDetailList = borrowRepaymentDetailList;
		}

		public List<Listing> getContinueAwardRateList() {
			return continueAwardRateList;
		}

		public void setContinueAwardRateList(List<Listing> continueAwardRateList) {
			this.continueAwardRateList = continueAwardRateList;
		}

		public VerifyMessJson getVerifyMessJson() {
			return verifyMessJson;
		}

		public void setVerifyMessJson(VerifyMessJson verifyMessJson) {
			this.verifyMessJson = verifyMessJson;
		}

		public List<NoteImg> getVarImgList() {
			return varImgList;
		}

		public void setVarImgList(List<NoteImg> varImgList) {
			this.varImgList = varImgList;
		}

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}

		public List<NoteImg> getCarImgList() {
			return carImgList;
		}

		public void setCarImgList(List<NoteImg> carImgList) {
			this.carImgList = carImgList;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
		
}
