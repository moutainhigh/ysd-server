package com.qmd.action.agency;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.common.BasePager;
import com.qmd.mode.agency.Agency;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.AccountCash;
import com.qmd.mode.user.User;
import com.qmd.service.agency.AgencyService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.borrow.BorrowTenderService;
import com.qmd.service.user.AccountBankService;
import com.qmd.service.user.AccountCashService;
import com.qmd.service.user.UserService;
import com.qmd.util.CommonUtil;
import com.qmd.util.JsonUtil;
import com.qmd.util.bean.NoteImg;
import net.sf.json.JSONException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@InterceptorRefs({ @InterceptorRef(value = "userVerifyInterceptor"),
@InterceptorRef(value = "qmdDefaultStack") })
@Service("agencyPtAction")
public class AgencyPtAction  extends BaseAction{
	
	private static final long serialVersionUID = 1491118498746637638L;
	private String[] vouchers;// 企业详细信息图片地址
	private String[] vouchersTitle;// 图片标题
	private List<NoteImg> voucherImgList;
	
	private String[] vouchersSj;// 实景图片
	private String[] vouchersTitleSj;// 实景图片标题
	private List<NoteImg> voucherImgListSj;

	private AccountBank accountBank;
	private Agency agency;
	private User user;
	private List<User> userList;
	
	private List<Borrow> borrowList;
	private Integer borrowId;//标ID
	private Integer buserid;//【放款跳转到添加银行账户 传递参数】

	Map<String, Object> root = new HashMap<String, Object>();
	private AccountCash accountCash;
	@Resource
	AgencyService agencyService;
	@Resource
	UserService userService;
	@Resource
	AccountBankService accountBankService;
	@Resource
	BorrowService borrowService;
	@Resource
	AccountCashService accountCashService;
	@Resource
	BorrowTenderService borrowTenderService;

	//-------------------------------------------会员个人中心--------------------------------------------------------------------
	
	@Action(value="/agency_pt/detail",
			results={@Result(name="success", location="/content/agency/pt/agency_pt_detail.ftl", type="freemarker"),
					 @Result(name="msg", location="/content/message_page.ftl", type="freemarker")})
	@InputConfig(resultName = "error_ftl")
	public String detail(){
		User loginuser = getLoginUser();
		agency = agencyService.baseLoad(loginuser.getAgencyid());			
		
		reloadUser();
		if(loginuser.getMemberType() != null && loginuser.getMemberType() == 1 && loginuser.getRealStatus() ==1){
			return "success";
		}else{
			msg = "请先进行实名认证";
			msgUrl = getContextPath()+"/userCenter/realname.do";
			return "msg";
		}
	}
	
	
	@Action(value="/agency_pt/subpageDetail",
			results={@Result(name="success", location="/content/agency/pt/agency_pt_subpage_detail.ftl", type="freemarker"),
					 @Result(name="msg", location="/content/message_page.ftl", type="freemarker")})
	@InputConfig(resultName = "error_ftl")
	public String subpageDetail(){
		User loginuser = getLoginUser();
		agency = agencyService.baseLoad(loginuser.getAgencyid());			
		voucherImgList = new ArrayList<NoteImg>();//公司宣传图集合
		String tmp = agency.getImg();//公司宣传图
		if (tmp != null && !"".equals(tmp.trim())) {
			try{
				voucherImgList =  JsonUtil.toObject(tmp, new TypeReference<ArrayList<NoteImg>>(){}); 
			}catch(JSONException je) {
				NoteImg ni = new NoteImg();
				ni.setName("");
				ni.setUrl(tmp);
				voucherImgList.add(ni);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		voucherImgListSj = new ArrayList<NoteImg>();//公司实景图集合
		String tmpSj = agency.getLogo3();//公司实景图
		if (tmpSj != null && !"".equals(tmpSj.trim())) {
			try{
				voucherImgListSj =  JsonUtil.toObject(tmpSj, new TypeReference<ArrayList<NoteImg>>(){}); 
			}catch(JSONException je) {
				NoteImg ni = new NoteImg();
				ni.setName("");
				ni.setUrl(tmpSj);
				voucherImgListSj.add(ni);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		reloadUser();
		if(loginuser.getMemberType() != null && loginuser.getMemberType() == 1 && loginuser.getRealStatus() ==1){
			return "success";
		}else{
			msg = "请先进行实名认证";
			msgUrl = getContextPath()+"/userCenter/realname.do";
			return "msg";
		}
	}
	/**
	 * 修改服务商资料详细信息
	 * @return
	 */
	@Action(value="/agency_pt/ajaxUpdateSubpageDetail",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String updateBusinessDetail(){
		if(agency == null){
			return ajax(Status.warn,"参数错误!");
		}
		Agency ab = agencyService.baseLoad(getLoginUser().getAgencyid());
		if(ab == null){
			return ajax(Status.warn,"参数错误!");
		}
		
		String devc = "";
		if (vouchers != null && vouchers.length > 0 ) {
			if(StringUtils.isNotEmpty(vouchers[0])){
				devc = CommonUtil.decodeUrl(vouchers[0]);
				ab.setLogo(devc);//公司LOGO
			}
			
			if(StringUtils.isNotEmpty(vouchers[1])){
				devc = CommonUtil.decodeUrl(vouchers[1]);
				ab.setLogo1(devc);//顶部广告图
			}
//			if(StringUtils.isNotEmpty(vouchers[2])){
//				devc = CommonUtil.decodeUrl(vouchers[2]);
//				ab.setLogo2(devc);
//			}
//			if(StringUtils.isNotEmpty(vouchers[3])){
//				devc = CommonUtil.decodeUrl(vouchers[3]);
//				ab.setLogo3(devc);
//			}
			if(StringUtils.isNotEmpty(vouchers[2])){
				devc = CommonUtil.decodeUrl(vouchers[2]);
				ab.setLogo2(devc);//地址图片
			}
			//轮播图片
			List<NoteImg> noteImgList = new ArrayList<NoteImg>();
			for (int i = 3; i < vouchers.length; i++) {
				devc = CommonUtil.decodeUrl(vouchers[i]);
				NoteImg nt = new NoteImg();
				nt.setUrl(devc);
				if(vouchersTitle != null && StringUtils.isNotEmpty(vouchersTitle[i - 3])){
					nt.setName(vouchersTitle[i - 3]);
				}
				noteImgList.add(nt);
			}

			String tempVcs = JsonUtil.listToJson(noteImgList);
			ab.setImg(tempVcs);
		}
		
		//实景图片
		if(vouchersSj != null && vouchersSj.length >0){
			List<NoteImg> noteImgList = new ArrayList<NoteImg>();
			for(int i=0; i<vouchersSj.length; i++){
				devc = CommonUtil.decodeUrl(vouchersSj[i]);
				NoteImg nt = new NoteImg();
				nt.setUrl(devc);
				if(vouchersTitleSj != null && StringUtils.isNotEmpty(vouchersTitleSj[i])){
					nt.setName(vouchersTitleSj[i]);
				}
				noteImgList.add(nt);
				
			}
			String tempVcs = JsonUtil.listToJson(noteImgList);
			ab.setLogo3(tempVcs);
		}
		
		ab.setIntroduction(agency.getIntroduction());
		ab.setSubpagePhone(agency.getSubpagePhone());
		ab.setSubpageAddress(agency.getSubpageAddress());
		ab.setLinkman(agency.getLinkman());
		ab.setLinkmanMobile(agency.getLinkmanMobile());
		agencyService.baseUpdate(ab);
		return ajax(Status.success,"操作成功!");
		
	}
	
	
	/**
	 * 平台服务商 给借款人添加银行 页面跳转
	 * @return
	 */
	@Action(value="/agency_pt/toBankInput",
			results={@Result(name="success", location="/content/agency/pt/bank_input.ftl", type="freemarker"),
					@Result(name="msg", location="/content/message_page.ftl", type="freemarker")})
	public String toBankInput(){
		User loginuser = getLoginUser();
		if (loginuser.getTypeId()!=1||(!"1".equals(loginuser.getAgencytype()) )) {
			addActionError("只有特定服务商才能操作!");
			return "error_ftl";
		}
		
		if (loginuser.getPayPassword()==null||"".equals(loginuser.getPayPassword())) {
			msg = "请先设置安全密码！";
			msgUrl =getContextPath()+ "/userCenter/toPayPassword.do";
			return "msg";
		} else if (loginuser.getRealStatus()==null||loginuser.getRealStatus()!=1) {
			msg = "请先进行账户认证！";
			msgUrl = getContextPath()+"/userCenter/realname.do";
			return "msg";
		}
		

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("inviteUserid",getLoginUser().getId());
		
		userList = userService.getUserList(map);
		
		
		return SUCCESS;
	}
	
	
	/**
	 * 执行添加银行账户方法
	 * @return
	 */
	@Action(value="/agency_pt/bankInput",results={@Result(name="success", location="bankList", type="redirectAction")})
	public String bankInput(){
		User userLogin = getLoginUser();
		if (userLogin.getTypeId()!=1||(!"1".equals(userLogin.getAgencytype()) )) {
			addActionError("只有特定服务商才能操作!");
			return "error_ftl";
		}
		if(!userService.isPassword(userLogin.getUsername(), user.getPayPassword(), "1")){
			addActionError("安全密码输入错误!");
			return "error_ftl";
		}
//		if(accountBank.getId()==null){
//			if(getAccountBankList().size()>=5){
//				addActionError("一个用户最多添加5个银行账号!");
//				return "error_ftl";
//			}
//		}else{
//			Boolean flag=false;
//			List<AccountBank> accountBankList = getAccountBankList();
//			for(AccountBank bank:accountBankList){
//				if(bank.getId().equals(accountBank.getId())){
//					flag=true;
//				}
//			}
//			if(!flag){
//				addActionError("参数错误!");
//				return "error_ftl";
//			}
//		}
		if(user == null || user.getId() == null){
			addActionError("请选择借款人!");
			return "error_ftl";
		}
		if(StringUtils.isEmpty(accountBank.getBankId())){
			addActionError("请选择开户银行!");
			return "error_ftl";
		}
		if(StringUtils.isEmpty(accountBank.getAccount())){
			addActionError("请输入银行账号!");
			return "error_ftl";
		}
		if(StringUtils.isEmpty(accountBank.getBranch())){
			addActionError("请输入开户行名称!");
			return "error_ftl";
		}
		
		accountBank.setAgencyId(userLogin.getAgencyid());//保存对接服务商ID
		accountBank.setModifyDate(new Date());
		accountBank.setAddIp(getRequestRemoteAddr());
//		if(accountBank.getId()==null){
			accountBank.setCreateDate(new Date());
			accountBank.setUserId(user.getId());
			this.userService.addAccountBank(accountBank);
			addActionMessage("添加成功");
//		}else{
//			this.userService.updateAccountBank(accountBank);
//			addActionMessage("修改成功");
//		}
		redirectUrl=getContextPath()+"/agency_pt/bankList.do";
		return "success_ftl";
	}
	
	

	/**
	 * 跳转到银行账户列表页面
	 * @return
	 */
	@Action(value="/agency_pt/bankList",results={@Result(name="success", location="/content/agency/pt/bank_list.ftl", type="freemarker")})
	public String bankList(){
		User userLogin = getLoginUser();
		if (userLogin.getTypeId()!=1||(!"1".equals(userLogin.getAgencytype()) )) {
			addActionError("只有特定服务商才能操作!");
			return "error_ftl";
		}
		AccountBank ab = new AccountBank();
		ab.setAgencyId(userLogin.getAgencyid());
		BasePager<AccountBank> basePager =  accountBankService.queryPageByOjbect(ab, 10, pager.getPageNumber(), "create_date desc");
		pager = CommonUtil.basePager2Pager(pager, basePager);
		return SUCCESS;
	}
	
	
	/**
	 * 删除银行账户
	 * @return
	 */
	@Action(value="/agency_pt/ajaxBankDelete",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String ajaxBankDelete(){
		Boolean flag=false;
		User userLogin = getLoginUser();
		if (userLogin.getTypeId()!=1||(!"1".equals(userLogin.getAgencytype()) )) {
			return ajax(Status.success,"只有特定服务商才能操作!");
		}
		
		AccountBank ab = accountBankService.getAccountBank(accountBank.getId());
		
		if(ab.getAgencyId().compareTo(userLogin.getAgencyid())==0 ){
			flag=true;
		}
		
		if(flag){
			this.userService.deleteAccountBank(ab.getId());
			return ajax(Status.success,"删除成功!");
		}else{
			return ajax(Status.error,"参数错误!");
		}
	}
	
	


	/**
	 * 跳转到抵押质押放款页面
	 * @return
	 */
	@Action(value="/agency_pt/cashMonth",results={@Result(name="success", location="/content/agency/pt/cash_month.ftl", type="freemarker")})
	public String cashMonth(){
		User userLogin = getLoginUser();
		if (userLogin.getTypeId()!=1||(!"1".equals(userLogin.getAgencytype()) )) {
			addActionError("只有特定服务商才能操作!");
			return "error_ftl";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userLogin.getId());
//		map.put("bType", 6);//月标
		map.put("bType", 8);//卡趣新标（抵押质押）
		int[] array = {3}; //满标审核通过
		map.put("array", array);
		map.put("isfangkuan", 0);//【未放款的标】
		borrowList = borrowService.queryUserBorrowList(map);
		
		return SUCCESS;
	}


	/**
	 * 跳转到流转标放款页面
	 * @return
	 */
	@Action(value="/agency_pt/cashFlow",results={@Result(name="success", location="/content/agency/pt/cash_flow.ftl", type="freemarker")})
	public String cashFlow(){
		User userLogin = getLoginUser();
		if (userLogin.getTypeId()!=1||(!"1".equals(userLogin.getAgencytype()) )) {
			addActionError("只有特定服务商才能操作!");
			return "error_ftl";
		}
				
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userLogin.getId());
//		map.put("bType", 5);//流转标
		map.put("bType", 7);//卡趣新标(债权流转)
		int[] array = {1}; //发标审核通过
		map.put("array", array);
		map.put("isfangkuan", 0);//【未放款的标】
//		map.put("fkMoney", 1);//【可放款金额是大于0】
		
		borrowList = borrowService.queryUserBorrowList(map);
		return SUCCESS;
	}
	

	/**
	 * 抵押质押放款保存
	 * @return
	 */
	@Action(value="/agency_pt/ajaxCashMonthSave",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String cashMonthSave(){
		User userLogin = getLoginUser();
		if (userLogin.getTypeId()!=1||(!"1".equals(userLogin.getAgencytype()) )) {
			return ajax(Status.warn,"只有特定服务商才能操作!");
		}

		if(!userService.isPassword(userLogin.getUsername(), user.getPayPassword(), "1")){
			return ajax(Status.warn,"安全密码输入错误!");
		}
		
		if(borrowId == null){
			return ajax(Status.warn,"请选择项目编号!");
		}
		if(user == null || user.getId() == null ||accountBank == null || accountBank.getId() == null){
			return ajax(Status.warn,"请选择借款人提现账户!");
		}

		Borrow borrow = borrowService.getBorrowById(borrowId);
//		if(borrow.getIsfangkuan() != null && borrow.getIsfangkuan() !=0){
//			return ajax(Status.warn,"此项目不允许放款 或 已申请放款!");
//		}
		//查询放款申请状态
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", user.getId());
		int[] array =new int[]{0,4};
		map.put("array", array);
		map.put("borrowid", borrowId);
		map.put("agencyUserid", borrow.getUserId());
		List<AccountCash> ac_list =	accountCashService.gainCashLish(map);
		
		if(ac_list != null && ac_list.size() >0){
			return ajax(Status.warn,"您有放款申请正在处理中或已成功，请核对后再申请!");
		}
		
		user = userService.getById(user.getId(), new User());
		
		AccountBank a_bank =accountBankService.getAccountBank(accountBank.getId());//借款人提现账户
		String cashType = "2";//提现方式类型【0:固定收费；1：收手续费【10天】；2：按百分比提现】
		
		AccountCash ac = new AccountCash();
		ac.setBorrowid(borrowId);//标ID【用于放款】
		ac.setAgencyUserid(borrow.getUserId());//服务商userId【用于放款】
		ac.setUserId(user.getId());//借款人userId
		
		this.accountCashService.cashMoneyByAgencyMonth(userLogin, ac, borrow, a_bank,cashType,getRequestRemoteAddr());
		return ajax(Status.success,"抵押质押放款成功!");
	}
	

	/**
	 * 流转标放款保存
	 * @return
	 */
	@Action(value="/agency_pt/ajaxCashFlowSave",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String cashFlowSave(){
		User userLogin = getLoginUser();
		if (userLogin.getTypeId()!=1||(!"1".equals(userLogin.getAgencytype()) )) {
			return ajax(Status.warn,"只有特定服务商才能操作!");
		}

		if(!userService.isPassword(userLogin.getUsername(), user.getPayPassword(), "1")){
			return ajax(Status.warn,"安全密码输入错误!");
		}
		if(borrowId == null){
			return ajax(Status.warn,"请选择项目编号!");
		}
		if(user == null || user.getId() == null ||accountBank == null || accountBank.getId() == null){
			return ajax(Status.warn,"请选择借款人提现账户!");
		}

		Borrow borrow = borrowService.getBorrowById(borrowId);
//		if(borrow.getIsfangkuan() != null && borrow.getIsfangkuan() !=0){
//			return ajax(Status.warn,"此项目不允许放款 或 已申请放款!");
//		}
		Map<String, Object> tMap = new HashMap<String, Object>();
		tMap.put("borrowId", borrow.getId());
		tMap.put("createDate", CommonUtil.date2begin(new Date()));
		Object totalAmount = this.borrowTenderService.getByStatementId("BorrowTender.selectAllAccountByUserid", tMap);//投标成功的流转金额
		
		BigDecimal tenderSumMoney = new BigDecimal(totalAmount== null ?"0":totalAmount.toString());
		
		//已放款金额
		tMap.put("agencyUserid", getLoginUser().getId());
		
		Object cashAmount = this.accountCashService.getByStatementId("AccountCash.selectAllTotalMoney", tMap);//已放款的金额
		BigDecimal cashSumMoney  = new BigDecimal(cashAmount== null ?"0":cashAmount.toString());
		
		BigDecimal fkMoney = tenderSumMoney.subtract(cashSumMoney);
		if(fkMoney.compareTo(new BigDecimal(0))<=0 ){
			return ajax(Status.warn,"此项目可放款金额不足，请选择其它项目!");
		}
		//查询放款申请状态
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", user.getId());
		int[] array =new int[]{0,4};
		map.put("array", array);
		map.put("borrowid", borrowId);
		map.put("agencyUserid", borrow.getUserId());
		List<AccountCash> ac_list =	accountCashService.gainCashLish(map);
		
		if(ac_list != null && ac_list.size() >0){
			return ajax(Status.warn,"您有放款申请正在处理中或已成功，请核对后再申请!");
		}
		
		user = userService.getById(user.getId(), new User());
		
		AccountBank a_bank =accountBankService.getAccountBank(accountBank.getId());//借款人提现账户
		String cashType = "2";//提现方式类型【0:固定收费；1：收手续费【10天】；2：按百分比提现】
		
		AccountCash ac = new AccountCash();
		ac.setBorrowid(borrowId);//标ID【用于放款】
		ac.setAgencyUserid(borrow.getUserId());//服务商userId【用于放款】
		ac.setUserId(user.getId());//借款人userId
		ac.setTotal(fkMoney);//提现金额
		this.accountCashService.cashMoneyByAgencyFlow(userLogin, ac, borrow, a_bank,cashType,getRequestRemoteAddr());
		return ajax(Status.success,"债权流转放款成功!");
	}
	
	
	
	/**
	 * 跳转到放款记录页面
	 * @return
	 */
	@Action(value="/agency_pt/cashList",results={@Result(name="success", location="/content/agency/pt/cash_list.ftl", type="freemarker")})
	public String cashList(){
		User userLogin = getLoginUser();
		if (userLogin.getTypeId()!=1||(!"1".equals(userLogin.getAgencytype()) )) {
			addActionError("只有特定服务商才能操作!");
			return "error_ftl";
		}
		
		AccountCash ac = new AccountCash();
		ac.setAgencyUserid(userLogin.getId());
		String orderBy = "a.create_date desc";
		
		BasePager<AccountCash> basePager =  accountCashService.queryPageByOjbect(ac, pager.getPageSize(), pager.getPageNumber(), orderBy);
		pager = CommonUtil.basePager2Pager(pager, basePager);
		
		return SUCCESS;
	}
	

	@Action(value="/ajaxBorrowInfo",results={@Result(type="json")})
	public String ajaxBorrowInfo(){
		Map<String,Object> map = new HashMap<String,Object>();
		if(borrowId != null){
			Borrow b = borrowService.getBorrowById(borrowId);
			map.put("borrow", b);
			

			//流转标获取可放款金额
			if("5".equals(b.getType())||"7".equals(b.getType())||"8".equals(b.getType())||"9".equals(b.getType())){

				
				Map<String, Object> tMap = new HashMap<String, Object>();
				tMap.put("borrowId", b.getId());
				tMap.put("createDate", CommonUtil.date2begin(new Date()));
				Object totalAmount = this.borrowTenderService.getByStatementId("BorrowTender.selectAllAccountByUserid", tMap);//投标成功的流转金额
				
				BigDecimal tenderSumMoney = new BigDecimal(totalAmount== null ?"0":totalAmount.toString());//投标成功的总金额
				map.put("tenderSumMoney", tenderSumMoney);
				
				//贴息金额
				Double totalTiexiFee = (Double)this.borrowTenderService.getByStatementId("BorrowTender.selectAllTiexiFeeByUserid", tMap);//投标成功后的贴息金额
				BigDecimal tenderSumTiexiFee = BigDecimal.valueOf(totalTiexiFee==null?0:totalTiexiFee);
				map.put("tenderSumTiexiFee", tenderSumTiexiFee);
				
				//已放款金额
				tMap.put("agencyUserid", getLoginUser().getId());
				
				Object cashAmount = this.accountCashService.getByStatementId("AccountCash.selectAllTotalMoney", tMap);//已放款的金额
				BigDecimal cashSumMoney  = new BigDecimal(cashAmount== null ?"0":cashAmount.toString());
				map.put("cashSumMoney", cashSumMoney);
				
				// 放款金额=投资总额 - 贴息金额-已放总额
				BigDecimal fkMoney = tenderSumMoney.subtract(tenderSumTiexiFee).subtract(cashSumMoney);
				if(fkMoney.compareTo(new BigDecimal(0))<=0 ){
					map.put("message", "此项目可放款金额不足，请选择其它项目！");
					map.put("status", Status.error);
					return ajax(map);
				}
				
				map.put("fkMoney", fkMoney);
				
			}
			
			
			map.put("successTime", CommonUtil.getDate2String(b.getSuccessTime(), "yyyy-MM-dd HH:mm:ss"));
			map.put("verifyTime", CommonUtil.getDate2String(b.getVerifyTime(), "yyyy-MM-dd HH:mm:ss"));
			Integer userId = b.getUserId();//借款人ID
			user = userService.getById(userId, new User());
			
			map.put("user", user);
			Map<String,Object> mapBank = new HashMap<String,Object>();
			mapBank.put("userId", userId);
			List<AccountBank> a_bList = accountBankService.baseList(mapBank);
			
			map.put("bankList", a_bList);//null
			map.put("status",Status.success);
		}else{
			map.put("message", "请选择项目编号！");
			map.put("status", Status.error);
		}
		return ajax(map);
	}
	

	@Action(value="/ajaxRecallCashInfo", results = {@Result(name = "success", params = {"root", "root" }, type = "json")})
	public String ajaxRecallCashInfo(){
		User userLogin = getLoginUser();
		if (userLogin.getTypeId()!=1||(!"1".equals(userLogin.getAgencytype()) )) {
			addActionError("只有特定服务商才能操作!");
			return "error_ftl";
		}
		
		if(accountCash == null){
			root.put("errorMsg", "参数错误!");
			ajax(root);
			return SUCCESS;
		}
		
		int ret = accountCashService.reCallCashByAgency(accountCash.getId());
		if(ret ==1){
			root.put("errorMsg", "OK");
		}else{
			root.put("errorMsg", "操作失败");
		}
		ajax(root);
		return SUCCESS;
		
//		Map<String,Object> map = new HashMap<String,Object>();
//		Borrow b = borrowService.getBorrowById(borrowId);
//		map.put("borrow", b);
//		map.put("successTime", b.getSuccessTime().toLocaleString());
//		Integer userId = b.getBorrowerId();//借款人ID
//		user = userService.getById(userId, new User());
//		
//		map.put("user", user);
//		Map<String,Object> mapBank = new HashMap<String,Object>();
//		mapBank.put("userId", userId);
//		List<AccountBank> a_bList = accountBankService.baseList(mapBank);
//		
//		map.put("bankList", a_bList);//null
//		map.put("status",Status.success);
//		return ajax(map);
	}
	
	@Action(value="/agency_pt/poputView",results={@Result(name="success", location="/content/agency/pt/poput_cash_view.ftl", type="freemarker")})
	public String poputBack(){
		accountCash = accountCashService.get(accountCash.getId());
		if(accountCash == null){
			addActionError("放款记录不存在");
			return "error_ftl";
		}
		return SUCCESS;
	}
	
	
	//------------------------------------------------------前台显示-非会员登录访问(在struts.xml 配置)------------------------------------------------------------
	

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
	public List<NoteImg> getVoucherImgList() {
		return voucherImgList;
	}
	public void setVoucherImgList(List<NoteImg> voucherImgList) {
		this.voucherImgList = voucherImgList;
	}


	public Agency getAgency() {
		return agency;
	}


	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String[] getVouchersSj() {
		return vouchersSj;
	}

	public void setVouchersSj(String[] vouchersSj) {
		this.vouchersSj = vouchersSj;
	}

	public String[] getVouchersTitleSj() {
		return vouchersTitleSj;
	}

	public void setVouchersTitleSj(String[] vouchersTitleSj) {
		this.vouchersTitleSj = vouchersTitleSj;
	}

	public List<NoteImg> getVoucherImgListSj() {
		return voucherImgListSj;
	}

	public void setVoucherImgListSj(List<NoteImg> voucherImgListSj) {
		this.voucherImgListSj = voucherImgListSj;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public AccountBank getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(AccountBank accountBank) {
		this.accountBank = accountBank;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public List<Borrow> getBorrowList() {
		return borrowList;
	}

	public void setBorrowList(List<Borrow> borrowList) {
		this.borrowList = borrowList;
	}

	public Integer getBuserid() {
		return buserid;
	}

	public void setBuserid(Integer buserid) {
		this.buserid = buserid;
	}

	public AccountCash getAccountCash() {
		return accountCash;
	}

	public void setAccountCash(AccountCash accountCash) {
		this.accountCash = accountCash;
	}

	public Map<String, Object> getRoot() {
		return root;
	}

	public void setRoot(Map<String, Object> root) {
		this.root = root;
	}

	
}
