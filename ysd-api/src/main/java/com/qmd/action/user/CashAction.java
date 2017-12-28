package com.qmd.action.user;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.bean.funds.UserCashBean;
import com.qmd.bean.funds.UserCashReal;
import com.qmd.mode.user.*;
import com.qmd.mode.util.Listing;
import com.qmd.service.borrow.BorrowRepaymentDetailService;
import com.qmd.service.user.*;
import com.qmd.service.util.ListingService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConfigUtil;
import com.qmd.util.ConstantUtil;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 提现
 *
 */
@InterceptorRefs({ 
		@InterceptorRef(value = "userVerifyInterceptor"),
		@InterceptorRef(value = "qmdDefaultStack") })
	@Service("cashAction")
public class CashAction extends BaseAction   {

	private static final long serialVersionUID = 6388112224388918414L;
	
	private User user;
	private UserAccountRecharge userAccountRecharge;
	UserCashBean ucb ;
	
	private String type;//资金明细
	
	private List<AccountCash> accountCashList;//提现记录列表

	private List<UserAccountRecharge> userAccRechargeList;
	private String cashType;//体现方式类型
	private String feeValue;//额外提现手续费
	private String fixedFee;//固定提现手续费
	
	private Integer bankId;//提现银行ID
	private BigDecimal cashMoney;//提现金额
	private String safepwd;// 安全密码
	private String cardNo;//
	
	@Resource
	UserService userService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	UserAccountDetailService userAccountDetailService;
	@Resource
	UserAccountRechargeService userAccountRechargeService;
	@Resource
	AccountBankService accountBankService;
	@Resource
	AccountCashService  accountCashService;
	@Resource
	ListingService listingService;
	@Resource
	UserRepaymentDetailService userRepaymentDetailService;
	@Resource
	BorrowRepaymentDetailService borrowRepaymentDetailService;

	/**
	 * 我要提现
	 * @return
	 */
	@Action(value = "/cash/cashTo",results={@Result(name="success", location="/content/h5user/cash.ftl", type="freemarker")})
	public String cashTo(){
		try{
			reloadUser();
			user =getLoginUser();
	        ucb = new UserCashBean();
			Double ableCashMoney=user.getAbleMoney().doubleValue();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id" , user.getId());
			List<AccountBank> abl = accountBankService.getAccountBankList(user.getId());
			
			if(abl == null || abl.size() <1){
				 msg="请设置提现账户!";
				 return "error_ftl";
			}
			 
//			if(StringUtils.isEmpty(user.getPayPassword()) ){
//				msg="请设置交易密码!";
//				 return "error_ftl";
//			}
			 ucb.setAbleMoney(CommonUtil.setPriceScale(user.getAbleMoney()).toString());
			 if(abl!= null && abl.size() > 0){
				 ucb.setBankId(abl.get(0).getBankId());
				 ucb.setBankName(abl.get(0).getName());
				 ucb.setBranch(abl.get(0).getBranch());
				 String card= abl.get(0).getAccount();
				 ucb.setCardNo(card);
				 cardNo = card.substring(card.length()-4, card.length());
			 }
			 ucb.setCashMoney(CommonUtil.setPriceScale(new BigDecimal( ableCashMoney)).toString());
			 ucb.setRealname(user.getRealName());
			 

			 Map<String,Object> map1 = new HashMap<String,Object>();
			 map1.put("userId" , user.getId());
			 int[] cashStatusArray =  {0,1,4};//状态码【0-审核中；1-审核成功；2-审核失败；3-用户取消；4-处理中】
			 map1.put("cashStatusArray" , cashStatusArray);
			 map1.put("minDate" ,  CommonUtil.date2begin(CommonUtil.getFirstDayOfMonth(new Date())));
			 map1.put("maxDate" , new Date());
			 
			 Integer userCashChargeTimes =  accountCashService.queryAccountCashListCount(map1);//当月提现次数

			 String val1 = listingService.getKeyValue(ConstantUtil.CASH_CHARGE_TIMES);//总免费提现次数
			 String val2 = listingService.getKeyValue(ConstantUtil.CASH_CHARGE_MONEY);//扣费比例

			 Listing listing2 =this.listingService.getListing(ConstantUtil.FIXED_CASH_FEE);
			 fixedFee = listing2.getKeyValue();
			 ucb.setFeeScale(val2);
			 ucb.setFeeFixed(fixedFee);
			 
			 ucb.setUserCashChargeTimes(userCashChargeTimes.toString());
			 ucb.setCashChargeTimes(val1);
			 
			 return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			 msg="程序内部错误!";
			 return "error_ftl";
		}
	}


	/**
	 * 我要提现-输入提现金额返回 实际到账金额
	 * @return
	 */
	@Action(value = "/cash/cashChange", results = { @Result(type = "json") })
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String cashChange()throws Exception{
		try{
			User user = getLoginUser();
			//输入提现金额
			if(cashMoney.compareTo(BigDecimal.ZERO) <= 0){
					return ajax(Status.error, "请输入正确的提现金额!");
				 
			}
			BigDecimal inputValue= cashMoney;
			Listing listing =this.listingService.getListing(ConstantUtil.OTHER_CASH_FEE);
			feeValue= listing.getKeyValue();
			
			BigDecimal ableMoney = user.getAbleMoney();
			listing =this.listingService.getListing(ConstantUtil.MAX_CASH_MONEY);
			String maxCashMoney= listing.getKeyValue();//最大提现金额
			Listing listing1 =this.listingService.getListing(ConstantUtil.MIN_CASH_MONEY);
			String minCashMoney= (listing1==null?"0":listing1.getKeyValue());//最小提现金额
			
			if(cashMoney.compareTo(ableMoney) >0 ){
				return ajax(Status.error, "申请提现金额大于实际可提现额度!");
			}
			if(inputValue.compareTo(new BigDecimal( 100)) < 0){
				return ajax(Status.error, "提现金额必须大于100元!");
			}

			if(inputValue.compareTo(new BigDecimal( maxCashMoney)) > 0){
				return ajax(Status.error, ApiConstantUtil.getM00015_7(maxCashMoney));
			}
			if(inputValue.compareTo(new BigDecimal( minCashMoney)) < 0){
				return ajax(Status.error,  ApiConstantUtil.getM00015_7_2(maxCashMoney));
			}
			
			Listing listing2 =this.listingService.getListing(ConstantUtil.FIXED_CASH_FEE);
			fixedFee = listing2.getKeyValue();//固定手续费
			BigDecimal cashFee = BigDecimal.ZERO;
			
			//超过提现次数加收手续费金额
			 Map<String,Object> map1 = new HashMap<String,Object>();
			 map1.put("userId" , user.getId());
			 int[] cashStatusArray =  {0,1,4};//状态码【0-审核中；1-审核成功；2-审核失败；3-用户取消；4-处理中】
			 map1.put("cashStatusArray" , cashStatusArray);
			 map1.put("minDate" ,  CommonUtil.date2begin(CommonUtil.getFirstDayOfMonth(new Date())));
			 map1.put("maxDate" , new Date());
			 
			Integer userCashChargeTimes = accountCashService.queryAccountCashListCount(map1);//当月提现次数
			 
			String val1 = listingService.getKeyValue(ConstantUtil.CASH_CHARGE_TIMES);
			String val2 = listingService.getKeyValue(ConstantUtil.CASH_CHARGE_MONEY);
			Integer cashChargeTimes = Integer.parseInt(val1);
			BigDecimal cashChargeMoney = new BigDecimal(val2);
			if (userCashChargeTimes>=cashChargeTimes) {
				cashFee = cashFee.add(new BigDecimal(fixedFee)).add( cashMoney.multiply(cashChargeMoney)) ; 
			}
			 
			BigDecimal realMoney = inputValue.subtract(cashFee);//实际到账金额
			UserCashReal ucr = new UserCashReal();
			ucr.setRealMoney(CommonUtil.setPriceScale(realMoney).toString());
			return ajax(Status.success, CommonUtil.setPriceScale(realMoney).toString());
			
		}catch (Exception e) {
			e.printStackTrace();
			 return ajax(Status.error, ApiConstantUtil.S0001);
		}
	}

	
	
	

	/**
	 * 我要提现-提交
	 * @return
	 */
	@Action(value = "/cash/cashSave", results = { @Result(type = "json") })
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String cashSave(){
		try{
			
			reloadUser();
			User user1 = getLoginUser();
			
			User userLogin = getLoginUser();
			if(!userService.isPassword(userLogin.getUsername(), safepwd, "1")){
				return ajax(Status.error, ApiConstantUtil.S0002);
			}
			
			
			AccountBank accountBank1 =userService.queryAccountBank(user1.getId()).get(0);// .getAccountBank(bankId);
			
			if(cashMoney.toString().matches("^(([1-9]\\d*)|\\d)(\\.\\d{1,2})?$")){
				if(cashMoney.doubleValue()<100){
					return ajax(Status.error, ApiConstantUtil.M00015_0);
				}
			}else{
				return ajax(Status.error, ApiConstantUtil.M00015_1);
			}
			
			AccountCash accountCash = new AccountCash();
			accountCash.setTotal(cashMoney);
			
			
			if(cashMoney.compareTo(user1.getAbleMoney()) >0 ){
				return ajax(Status.error, ApiConstantUtil.M00015_2);
			}
			
			accountCash.setAbleCashMoney(user1.getAbleMoney());
			accountCash = this.accountCashService.cashMoney(user1, accountCash, accountBank1,cashType,getRequestRemoteAddr());


			Map<String,String> pmap = new HashMap<String,String>();
			pmap.put("orderNo",accountCash.getTradeNo());
			pmap.put("userId",userService.get(userLogin.getId()).getBankCustNo());
			pmap.put("amount",  accountCash.getTotal().toString() ) ;
			pmap.put("Fee",  accountCash.getFee().toString() ) ;
			msg = com.ysd.util.HttpUtil.post(ConfigUtil.getConfigUtil().get("pay.url")+"/cashApp",pmap);

			if(msg == null || "".equals(msg)){
				return ajax(Status.error, "存管服务器无法访问,请重新尝试");
			}

			return ajax(Status.success, msg);
		}catch (Exception e) {
			e.printStackTrace();
			 return ajax(Status.error, ApiConstantUtil.S0001);
		}
	}

	

	
	public Double getAbleCash(User user){
		 BigDecimal lastAbleMoney=new BigDecimal(0.00);
		 int[] array;
//			BigDecimal ableMoney;
		//******获取昨天可用额度***
		Map<String,Object> repaymap = new HashMap<String,Object>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
		System.out.println(calendar.getTime());
		String time = simple.format(calendar.getTime());
		Integer dailyDate = Integer.parseInt(time);
		repaymap.put("userId", user.getId());
		repaymap.put("dailyDate", dailyDate);
		repaymap.put("status", 1);
		//******
		SimpleDateFormat simple1 = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = null;
		try {
			Date max = simple1.parse(simple1.format( new Date()));

			calendar.setTime(max);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
			calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR)+24);
			beginDate=calendar.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//*****************
		repaymap.put("startDate",beginDate );
		repaymap.put("finishDate", new Date());
		
		Map<String,Object> cashymap = new HashMap<String,Object>();
		cashymap.put("userId", user.getId());
		cashymap.put("beginDate",beginDate );
		cashymap.put("endDate", new Date());
		array =new int[]{0,1,4};
		cashymap.put("array", array);
		
		
		//*********获取提现当日还款******************
		double repayAccount = 0;
		List<UserRepaymentDetail> reportAccountList= userRepaymentDetailService.queryUserRepaymentDetailList(repaymap);
		if(reportAccountList.size()>0){
			for(UserRepaymentDetail userRepaymentDetail:reportAccountList){
				if(!"1".equals(userRepaymentDetail.getApplyContinueTotal())||Integer.valueOf(userRepaymentDetail.getApplyContinueTotal())!=1){
					repayAccount +=Double.parseDouble(userRepaymentDetail.getRepaymentAccount());
				}
			}
		}
		
		//************************************************
		
		//***********今日申请中和提现成功以及处理中的提现记录******************
		double succCashAccount = 0;
		accountCashList = accountCashService.gainCashLish(cashymap);//提现成功的
		if(accountCashList.size()>0){
			for(AccountCash accountCash: accountCashList){
				succCashAccount += CommonUtil.bigDecimal2Double(accountCash.getTotal());
			}
		}
		//*******************************************
		
		//******************查询用户撤销的或后台审核不通过的操作撤销日前一天申请提现记录*******************************
		
		
		Map<String,Object> remap = new HashMap<String,Object>();
		remap.put("userId", user.getId());
		remap.put("firstDate",beginDate );
		remap.put("lastDate", new Date());
		remap.put("backDate", beginDate);
		array =new int[]{2,3};
		remap.put("array", array);
		double recallAccount = 0;
		accountCashList = accountCashService.gainCashLish(remap);//今天0点之前申请的并现在取消的提现。
		if(accountCashList.size()>0){
			for(AccountCash accountCash: accountCashList){
				recallAccount += CommonUtil.bigDecimal2Double(accountCash.getTotal());
			}
		}
		
				
		
		//******************今日奖励金额*******************************
		Date myDate = new Date();
		Date myMiniDate = CommonUtil.date2begin(myDate);
		Date myMaxDate = CommonUtil.date2end(myDate);
		
		//***投标奖励***
		BigDecimal today_award_add = userAccountDetailService.getSumMoney(user.getId(), "award_add",myMiniDate,myMaxDate);
		if (today_award_add==null) {
			today_award_add = BigDecimal.valueOf(0);
		}
		//***线下充值奖励***
		BigDecimal today_recharge_offline_reward = userAccountDetailService.getSumMoney(user.getId(), "recharge_offline_reward",myMiniDate,myMaxDate);
		if (today_recharge_offline_reward==null) {
			today_recharge_offline_reward = BigDecimal.valueOf(0);
		}
		//***大后台奖励***
		BigDecimal today_offline_reward = userAccountDetailService.getSumMoney(user.getId(), "offline_reward",myMiniDate,myMaxDate);
		if (today_offline_reward==null) {
			today_offline_reward = BigDecimal.valueOf(0);
		}
		//***续投奖励***
		BigDecimal today_award_continued = userAccountDetailService.getSumMoney(user.getId(), "award_continued",myMiniDate,myMaxDate);
		if (today_award_continued==null) {
			today_award_continued = BigDecimal.valueOf(0);
		}
		
		//*****续投宝转出金额***
		BigDecimal today_roll_out_money =userAccountDetailService.getSumMoney(user.getId(), "roll_out_money",myMiniDate,myMaxDate);
		if (today_roll_out_money==null){
			today_roll_out_money = BigDecimal.valueOf(0);
		}
		
		//*****获取资金转入金额***
		BigDecimal today_money_into =userAccountDetailService.getSumMoney(user.getId(), "money_into",myMiniDate,myMaxDate);
		if (today_money_into==null){
			today_money_into = BigDecimal.valueOf(0);
		}
		
		//*******************************************
		
		double ableCashAccount=lastAbleMoney.doubleValue()+repayAccount+recallAccount-succCashAccount 
				+ today_award_add.doubleValue()+today_recharge_offline_reward.doubleValue()+today_offline_reward.doubleValue()+today_award_continued.doubleValue()+today_roll_out_money.doubleValue()+today_money_into.doubleValue();//最大提现额
		
//		log.info("["+user.getUsername()+"]最大可提现："+ableCashAccount+"="+lastAbleMoney.doubleValue()+"+"+repayAccount+"+"+recallAccount+"-"+succCashAccount 
//				+"+"+ today_award_add.doubleValue()+"+"+today_recharge_offline_reward.doubleValue()+"+"+today_offline_reward.doubleValue()+"+"+today_award_continued.doubleValue()+"+"+today_roll_out_money.doubleValue()+"+"+today_money_into.doubleValue());
//		
//		moneyLog.debug("  ["+user.getUsername()+"]最大可提现["+ableCashAccount+"]=" +
//											 "用户昨天可用额["+lastAbleMoney.doubleValue()+"]+" +
//											       "当日还款["+repayAccount+"]+" +
//									    "日前提现今日取消金额["+recallAccount+"]-" +
//					 "今日申请中和提现成功以及处理中的提现记录["+succCashAccount+"]+" +
//											       "投标奖励["+today_award_add.doubleValue()+"]+" +
//											   "线下充值奖励["+today_recharge_offline_reward.doubleValue()+"]+" +
//											     "大后台奖励["+today_offline_reward.doubleValue()+"]+" +
//											       "续投奖励["+today_award_continued.doubleValue()+"]+" +
//											 "续投宝转出金额["+today_roll_out_money.doubleValue()+"]+" +
//											      "转入金额["+today_money_into.doubleValue()+"]"
//		);
		
		// 整理金额格式
		BigDecimal temp = BigDecimal.valueOf(ableCashAccount);
		ableCashAccount = CommonUtil.setPriceScale(temp).doubleValue();
		
		if(ableCashAccount<0){
			ableCashAccount=0.00;
		}else if(ableCashAccount>user.getAbleMoney().doubleValue()){
			ableCashAccount=user.getAbleMoney().doubleValue();
		}
		
//		moneyLog.debug("  ["+user.getUsername()+"]最大可提现["+ableCashAccount+"]用户可用金额[" +user.getAbleMoney().doubleValue()+"]");
		
		return ableCashAccount;
	}
	

	/**
	 * 获取总免费提现金额
	 * @return
	 */
	public Double freeCashMomey(User user){
		 Map<String,Object> cnmap = new HashMap<String,Object>();
		 cnmap.put("userId", user.getId());
		 int[] array ={0,4};
		 cnmap.put("array", array);
		 accountCashList = accountCashService.gainCashLish(cnmap);//申请中的和处理中的
		 double cashMoney=0;
		 if(accountCashList.size()>0){
			 for(AccountCash accountCash: accountCashList){
				 cashMoney += CommonUtil.bigDecimal2Double(accountCash.getTotal());
			 }
		 }
		 BigDecimal sum=halfMonthRecharge();
		 double money=0;
		//******************************************
		 money=user.getTotal().doubleValue()-sum.doubleValue()-cashMoney;//总免费提现额
		// 整理金额格式
		BigDecimal temp = BigDecimal.valueOf(money);
		money = CommonUtil.setPriceScale(temp).doubleValue();
		 
//		 moneyLog.debug("  ["+user.getUsername()+"]免费提现总额["+money+"]=用户总额[" +user.getTotal().doubleValue()+"]-15日内充值["+sum.doubleValue()+"]-申请中处理中提现["+cashMoney+"]");
		 
		if( money >0){
				
		}else{
			money =0;
		}
		return money;
	}

	
	/**
	 * 15天内充值金额
	 * @return
	 */
	public BigDecimal halfMonthRecharge(){
		BigDecimal sum = new BigDecimal(0.00);
		Date endTime = new Date();
		Date startTime =  CommonUtil.getDateBefore(endTime,ConstantUtil.HALF_MONTH_CASH_DATE);
		User user1= getLoginUser();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id" , user1.getId());
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("status", 1);
		userAccRechargeList= this.userAccountRechargeService.getUserAccountRecharge(map);
//		Map<String,Object> map1 = new HashMap<String,Object>();
//		map1.put("sign", "deduction");
//		map1.put("name", "其它提现手续费");
		Listing listing =this.listingService.getListing(ConstantUtil.OTHER_CASH_FEE);
		feeValue= listing.getKeyValue();
		
//		Map<String,Object> map2 = new HashMap<String,Object>();
//		map2.put("sign", "cash_type");
//		map2.put("name", "提现类型");
		Listing listing1 =this.listingService.getListing(ConstantUtil.CASH_TYPE_NUMBER);
		cashType = listing1.getKeyValue();
		System.out.println(cashType);
		
//		Map<String,Object> map3 = new HashMap<String,Object>();
//		map3.put("sign", "deduction");
//		map3.put("name", "提现手续费");
		Listing listing2 =this.listingService.getListing(ConstantUtil.FIXED_CASH_FEE);
		fixedFee = listing2.getKeyValue();
		//*************15天充值额************
		for(UserAccountRecharge u : userAccRechargeList){
			sum=sum.add(u.getMoney());
		}
//		user.setHalfMonthyMoney(sum);
		return sum;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<AccountCash> getAccountCashList() {
		return accountCashList;
	}

	public void setAccountCashList(List<AccountCash> accountCashList) {
		this.accountCashList = accountCashList;
	}

	public List<UserAccountRecharge> getUserAccRechargeList() {
		return userAccRechargeList;
	}

	public void setUserAccRechargeList(List<UserAccountRecharge> userAccRechargeList) {
		this.userAccRechargeList = userAccRechargeList;
	}

	public String getCashType() {
		return cashType;
	}

	public void setCashType(String cashType) {
		this.cashType = cashType;
	}

	public String getFeeValue() {
		return feeValue;
	}

	public void setFeeValue(String feeValue) {
		this.feeValue = feeValue;
	}

	public String getFixedFee() {
		return fixedFee;
	}

	public void setFixedFee(String fixedFee) {
		this.fixedFee = fixedFee;
	}


	public Integer getBankId() {
		return bankId;
	}


	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}


	public BigDecimal getCashMoney() {
		return cashMoney;
	}


	public void setCashMoney(BigDecimal cashMoney) {
		this.cashMoney = cashMoney;
	}


	public String getSafepwd() {
		return safepwd;
	}


	public void setSafepwd(String safepwd) {
		this.safepwd = safepwd;
	}


	public UserCashBean getUcb() {
		return ucb;
	}


	public void setUcb(UserCashBean ucb) {
		this.ucb = ucb;
	}


	public String getCardNo() {
		return cardNo;
	}


	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	
	
	
	
	
	
	
	

}
