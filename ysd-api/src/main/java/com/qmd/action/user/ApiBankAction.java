package com.qmd.action.user;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.bank.BankCard;
import com.qmd.bean.bank.BankList;
import com.qmd.bean.bank.CashBank;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.mode.util.Listing;
import com.qmd.service.borrow.BorrowRepaymentDetailService;
import com.qmd.service.user.*;
import com.qmd.service.util.ListingService;
import com.qmd.util.CommonUtil;
import com.qmd.util.JsonUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银行卡
 *
 */
@InterceptorRefs({ 
		@InterceptorRef(value = "apiUserInterceptor"),
		@InterceptorRef(value = "qmdDefaultStack") })
	@Service("apiBankAction")
public class ApiBankAction extends ApiBaseAction   {

	private static final long serialVersionUID = 6388112224388918414L;

	Logger log = Logger.getLogger(ApiBankAction.class);
	private User user;
	private CashBank cashBank;
	private AccountBank accountBank;
	private String btype;
	
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
	 * 跳转银行卡绑定
	 * @return
	 */
	@Action(value="/api/bankTo",results={@Result(type="json")})
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String ajaxBankTo(){
		List<Listing> listingList  = listingService.getChildListingBySignList("account_bank");
//		User loginUser = getLoginUser();
		Map<String,Object> map= new HashMap<String ,Object>();
		map.put("id", getLoginUser().getId());
		User userLogin = this.userService.getUser(map);
		BankList backSetList = new BankList();
		List<BankCard> bankList = new ArrayList<BankCard>();
		List<AccountBank> accountBankList = getAccountBankList();
		if(accountBankList.size()>0){
			backSetList.setBankId(accountBankList.get(0).getBankId());
			backSetList.setCardNo(accountBankList.get(0).getAccount());
			backSetList.setBranch(accountBankList.get(0).getBranch());
			backSetList.setStatus(accountBankList.get(0).getStatus().toString());
		}else{
            //return toBind();
		}
		for(Listing ls : listingList){
			BankCard bac = new BankCard();
			bac.setBankId(ls.getKeyValue());
			bac.setBankName(ls.getName());
			bankList.add(bac);
		}
		backSetList.setRealName(userLogin.getRealName());
		backSetList.setId(userLogin.getId().toString());
		backSetList.setPhone(userLogin.getPhone());
		
		backSetList.setIdNo(userLogin.getCardId());
		backSetList.setRegisterTime(CommonUtil.getDate2String(userLogin.getCreateDate(), "yyyyMMddHHmmss"));
		backSetList.setBankCardList(bankList);
		return ajax(JsonUtil.toJson(backSetList));		
		
	}

	private String safepwd;// 安全密码
	private String realName;//真实姓名
	private String idNo;//身份证号
	private String bankId;//
	/**
	 * 银行卡签约 提交
	 * 
	 * @return
	
	@Action(value="/api/bankSignSave")
	@InputConfig(resultName = "error_no_back_ftl,success_ftl")
	public String bankSignSave() {
		log.info("开始签约");
		User user = getLoginUser();
		if (user == null) {
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}
		if(StringUtils.isEmpty(user.getPayPassword())) {
			user.setPayPassword(PWDUtil.encode(safepwd,user.getRandomNum()));
			
		}else if(!userService.isPassword(user.getUsername(), safepwd, "1")) {
			return ajaxJson("S0002", ApiConstantUtil.S0002);
		}
		
		//银行卡信息
		List<AccountBank> accountBankList = getAccountBankList();
		if(btype.equals("0")){
			if(accountBankList.size()>=1){
					return ajaxJson("M0008_10", ApiConstantUtil.M0008_10);
			}
			accountBank = new AccountBank();
		}else if(btype.equals("1")){
			accountBank= accountBankList.get(0);
		}
		accountBank.setBtype(btype);
		accountBank.setCreateDate(new Date());
		accountBank.setModifyDate(new Date());
		accountBank.setAddIp(getRequestRemoteAddr());
		accountBank.setBankId(bankId);
		accountBank.setAccount(cashBank.getCardNo());
		accountBank.setBranch(cashBank.getBranch());
		accountBank.setUserId(user.getId());
		
		if(StringUtils.isEmpty(realName)){
			return ajaxJson("M0008_18", ApiConstantUtil.M0008_18);
		}
		if(StringUtils.isEmpty(idNo)){
			return ajaxJson("M0008_19", ApiConstantUtil.M0008_19);
		}
		if(idNo.length()<18){
			return ajaxJson("M0008_20", ApiConstantUtil.M0008_20);
		}
		user.setRealName(realName);
		Map<String,Object> qMap = new HashMap<String,Object>();
		qMap.put("cardId", idNo);
		User qUser = userService.getUser(qMap);
		if(qUser != null){
			if(qUser.getId().compareTo(user.getId())!=0){
				return ajaxJson("M0009_1", ApiConstantUtil.M0009_1);
			}
		}else{
			user.setCardId(idNo);
			user.setCardType("1");
			String birthday = user.getCardId().substring(6, 14);
			user.setBirthday(CommonUtil.getString2Date(birthday, "yyyyMMdd"));
			String sex ="1";
			String str= user.getCardId().substring(16, 17);
			if((Integer.valueOf(str)/2)==0){
				sex = "2";
			}
			user.setSex(sex);
		}

		int ret = accountBankService.insertBankInfo(user, accountBank);
		BaseBean bean = new BaseBean();
		if(ret ==1 ){
			bean.setRcd("R0001");
			bean.setRmg("绑卡认证成功");
		}else{
			bean.setRcd("R0002");
			bean.setRmg("绑卡认证失败");
		}
		return ajax(JsonUtil.toJson(bean));
	}
	
	 */
	/**
	 * 银行卡绑定
	 * @return
	
	@Action(value="/api/bankSave",results={@Result(type="json")})
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String ajaxBankSave(){
	
		User userLogin = getLoginUser();
		List<AccountBank> accountBankList = getAccountBankList();
		if(btype.equals("0")){
			if(accountBankList.size()>=1){
					return ajaxJson("M0008_10", ApiConstantUtil.M0008_10);
			}
			accountBank = new AccountBank();
		}else if(btype.equals("1")){
			accountBank= accountBankList.get(0);
		}
			
		if(StringUtils.isEmpty(cashBank.getBankId())){
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}
		if(StringUtils.isEmpty(cashBank.getCardNo())){
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}
		if(StringUtils.isEmpty(cashBank.getBranch())){
			return ajaxJson("E0002", ApiConstantUtil.E0002);
		}
		accountBank.setModifyDate(new Date());
		accountBank.setAddIp(getRequestRemoteAddr());
		accountBank.setBankId(cashBank.getBankId());
		accountBank.setAccount(cashBank.getCardNo());
		accountBank.setBranch(cashBank.getBranch());
		
		if(btype.equals("0")){
			accountBank.setCreateDate(new Date());
			accountBank.setUserId(userLogin.getId());
			this.userService.addAccountBank(accountBank);
			return ajaxJson("R0001", "添加成功");
		}else if(btype.equals("1")){
			
			this.userService.updateAccountBank(accountBank);
			return ajaxJson("R0001", "修改成功");
		}
		return ajaxJson("S0001", ApiConstantUtil.S0001);
		
	}
	
	 */
	public List<AccountBank> getAccountBankList(){
		return this.userService.queryAccountBank(getLoginUser().getId());
	}

	
	
	
	
	public String getBtype() {
		return btype;
	}

	public void setBtype(String btype) {
		this.btype = btype;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CashBank getCashBank() {
		return cashBank;
	}

	public void setCashBank(CashBank cashBank) {
		this.cashBank = cashBank;
	}

	public AccountBank getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(AccountBank accountBank) {
		this.accountBank = accountBank;
	}


	public String getSafepwd() {
		return safepwd;
	}


	public void setSafepwd(String safepwd) {
		this.safepwd = safepwd;
	}


	public String getRealName() {
		return realName;
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}


	public String getIdNo() {
		return idNo;
	}


	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}


	public String getBankId() {
		return bankId;
	}


	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	
	
}
