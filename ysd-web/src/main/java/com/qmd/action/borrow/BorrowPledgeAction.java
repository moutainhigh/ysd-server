package com.qmd.action.borrow;

import com.qmd.action.base.BaseAction;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.util.BorrowBean;
import com.qmd.service.borrow.BorrowPledgeService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.borrow.BorrowTenderService;
import com.qmd.service.user.UserAccountService;
import com.qmd.util.ConstantUtil;
import com.qmd.util.ImageUtil;
import com.qmd.util.InterestCalUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@InterceptorRefs({
		@InterceptorRef(value = "userVerifyInterceptor", params = {
				"excludeMethods", "borrowDetail,xBorrowList,payreturn,paynotify,result" }),
		@InterceptorRef(value = "qmdDefaultStack") })
@Service("borrowPledgeAction")
public class BorrowPledgeAction extends BaseAction {

	private static final long serialVersionUID = -4601770421327870644L;
	Logger log = Logger.getLogger(BorrowPledgeAction.class);

	public Integer bId;// 标的ID
	private Borrow borrow; // 标的信息
	private User user; // 用户

	private String safePassword;// 交易密码

	private File[] borImagesFile; //标的图片文件

	private int num; // 
	private List<BorrowBean> borrowBeanList; // 还款计划临时列表
	private String[] borImageList; // 标的图片列表
	
	// 投标
	Map<String,Object> root = new HashMap<String,Object>();
	private String errorMsg;
	
	private String interest;

	/**
	 * 会员类型：0个人;1企业
	 */
	private int memberType;

	@Resource
	BorrowPledgeService borrowPledgeService;
	@Resource
	BorrowService borrowService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	BorrowTenderService borrowTenderService;

	/**
	 * 跳转到添加质押标页面
	 */
	@Action(value = "/borrow/toaddBorrow", results = {
			@Result(name = "credit", location = "/content/borrow/inputborrow.ftl", type = "freemarker"),
			@Result(name = "fail", location = "/content/borrow/toSuccess.ftl", type = "freemarker") })
	public String toaddBorrow() {
		User user = getLoginUser();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", user.getId());
		User user1 = userService.getUser(map);
		// type=1:借款者
		if (user.getTypeId() != 1 || user1.getTypeId() != 1) {
			return "fail";
		} else {
			// TODO 设置用户类型
			memberType = 1;
			return "credit";
		}
	}

	/**
	 * 跳转到还款计划页面
	 */
	@Action(value = "/borrow/repayPlan", results = { @Result(name = "success", location = "/content/borrow/repayPlan.ftl", type = "freemarker") })
	public String repayPlan() {

		num = num / 10;
		borrowBeanList = new ArrayList<BorrowBean>();
		for (int i = 0; i < num; i++) {
			BorrowBean bean = new BorrowBean();
			bean.setPayday((i + 1) * 10);
			borrowBeanList.add(bean);
		}

		return "success";
	}

	/**
	 * 新增质押标
	 * 
	 * @return
	 */
	@Action(value = "/borrow/insertPledge", results = {
			@Result(name = "success", location = "/content/borrow/success.ftl", type = "freemarker"),
			@Result(name = "input", location = "/content/borrow/insertborrow.ftl", type = "freemarker") })
	public String insertPledge() {

		try {

			User user = getLoginUser();

			// 进度
			borrow.setSchedule("0");
			// 用户
			borrow.setUserId(user.getId());
			// 操作IP
			borrow.setOperatorIp(getRequestRemoteAddr());
			// 更新者
			borrow.setUpdatePersion(String.valueOf(user.getId()));
			// 创建时间
			borrow.setCreateDate(new Date());
			borrow.setBalance(borrow.getAccount());
			// 状态 0:待审核
			borrow.setStatus(0);
			// 天标
			borrow.setIsdaty("1");
			// 标的天数
			borrow.setTimeLimit(borrow.getTimeLimitDay());

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

			this.getBorrowPledgeService().insertBorrow(borrow);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}
	
	/**
	 * 投标详情页
	 * @return
	 */
	@Action(value="/borrow/detailPledge",
			results={@Result(name="success", location="/content/borrow/borrowPledgeDetail.ftl", type="freemarker"),
					@Result(name="noBorrowExist", location="/content/borrow/noBorrowExist.ftl", type="freemarker")})
	public String detailPledge(){
		try{
		borrow = this.borrowService.getBorrowById(this.getbId());
		if(borrow == null){
			return "noBorrowExist";
		}
		// 设置有效时间
		if(borrow.getValidTime() == null || "".equals(borrow.getValidTime())){
			borrow.setValidTime("1");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(borrow.getVerifyTime());
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + Integer.valueOf(borrow.getValidTime()));
		borrow.setOverDate(c.getTime());// 结束时间
		
		// 借款者信息
		Map<String,Object> uMap = new HashMap<String,Object>();
		uMap.put("id", borrow.getUserId());
		user = userService.getUser(uMap);
		
		// 借款者的待还记录
		Map<String,Object> bMap = new HashMap<String,Object>();
		bMap.put("userId", user.getId());
		int[] array = {3};
		bMap.put("array", array);
		// TODO 标相关的借款者信息
		//userBorrowList = this.getBorrowService().queryUserBorrowList(bMap);
		//borrowTenderList = this.borrowTenderService.getBorrowTenderByBorrowId(borrow.getId());
		//this.setInterest(interestCalUtil.payback(100L, Double.valueOf(borrow.getApr()/100), Integer.valueOf(borrow.getTimeLimit())).getTotalLiXi());
		
		// 标的图片
		String tmp = borrow.getBorImage();
		if (tmp != null && !"".equals(tmp.trim())) {
			borImageList = tmp.split(",");
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 投资详情页面
	 * @return
	 */
	@Action(value="/borrow/investPledge",
			results={@Result(name="success", location="/content/borrow/borrowPledgeInvest.ftl", type="freemarker"),
					@Result(name="noBorrowExist", location="/content/borrow/noBorrowExist.ftl", type="freemarker")})
	public String investPledge(){
		// 标的详情
		borrow = this.borrowService.getBorrowById(this.getbId());
		
		// 标不存在
		if(borrow == null){
			return "noBorrowExist";
		}
		// 设置标的过期时间
		Calendar c = Calendar.getInstance();
		c.setTime(borrow.getVerifyTime());
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + Integer.valueOf(borrow.getValidTime()));
		borrow.setOverDate(c.getTime());
		
		// 登陆者信息
		user = this.getLoginUser();
		
		InterestCalUtil interestCalUtil = new InterestCalUtil();
		interest = interestCalUtil.payback(100d,Double.valueOf(borrow.getAccount()),borrow.getApr(),30,borrow.getBorStages(),2).getTotalLiXi();
		
		//this.setInterest(interestCalUtil.payback(100L, Double.valueOf(borrow.getApr()/100), Integer.valueOf(borrow.getTimeLimit())).getTotalLiXi());
		
		return SUCCESS;
	}
	
	private String investMoney;
	
	/**
	 * 投标
	 * @return
	 */
	@Action(value="/borrow/investPledgeSubmit",
			results={@Result(name="success",params={"root","root"},type="json")})
	public String investPledgeSubmit () {
		
		String random = (String)getSession(ConstantUtil.RANDOM_COOKIE_NAME);
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
		if(!this.getMycode().equalsIgnoreCase(random)){
			this.setErrorMsg("验证码错误");
			root.put("errorMsg", this.getErrorMsg());
			return SUCCESS;
		}
		
		if(investMoney == null || "".equals(investMoney)){
			this.setErrorMsg("金额不需为空");
			root.put("errorMsg", this.getErrorMsg());
			return SUCCESS;
		}
		if(investMoney != null && !"".equals(investMoney)){
			if(!this.isNumeric(investMoney)){
				this.setErrorMsg("金额必须为数字");
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}
			if(Double.valueOf(investMoney)%100 !=0){
				this.setErrorMsg("金额必须为100整数倍");
				root.put("errorMsg", this.getErrorMsg());
				return SUCCESS;
			}
		}
		
		// 取得当前投资者信息
		user = this.getLoginUser();
		// 取得当前投资者的账户
		UserAccount userAccount = this.userAccountService.getUserAccountByUserId(user.getId());		
		// 标的信息
		borrow = this.borrowService.getBorrowById(bId);
		
		if(borrow.getStatus()!=1){
			errorMsg = "此标不是招标中";
			root.put("errorMsg", this.getErrorMsg());
			return SUCCESS;
		}
		
		//验证是否是借款人自己投标
		if(user.getId().intValue() == borrow.getUserId().intValue()){
			errorMsg = "你是借款人，不能自己投标";
			root.put("errorMsg", this.getErrorMsg());
			return SUCCESS;
		}
		//投标金额大于可用金额
		if(userAccount.getAbleMoney().doubleValue() < Double.parseDouble(investMoney)){
			errorMsg = "余额不足";
			root.put("errorMsg", this.getErrorMsg());
			return SUCCESS;
		}
		//投标额度是否小于最小投资金额
		if(Double.valueOf(investMoney) < Double.valueOf(borrow.getLowestAccount())){
			errorMsg = "投标金额小于最小投标额";
			root.put("errorMsg", this.getErrorMsg());
			return SUCCESS;
		}
		//投标总额大于最大投标额
		if(borrow.getMostAccount() != null){
			Map<String,Object> tMap = new HashMap<String,Object>();
			tMap.put("userId", user.getId());
			tMap.put("borrowId", borrow.getId());
			Object totalAmount = this.borrowTenderService.getByStatementId("BorrowTender.selectAllAccountByUserid",tMap);
			if(totalAmount != null){
				double tAmount = Double.valueOf(totalAmount.toString());
				if((tAmount + Double.valueOf(investMoney))> Double.valueOf(borrow.getMostAccount())){
					errorMsg = "投标总额大于最大投标额";
					root.put("errorMsg", this.getErrorMsg());
					return SUCCESS;
				}
			}
		}
		if(Double.valueOf(borrow.getBalance()) <= 0){
			errorMsg = "此标已满";
			root.put("errorMsg", this.getErrorMsg());
			return SUCCESS;
		}
		borrowPledgeService.insertBorrowInvest(user, investMoney, bId, this.obtainUserIp());
		//this.borrowService.borrowInvestDo(user, borrowTender, this.obtainUserIp());
		
		return SUCCESS;
	}

	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	public String getSafePassword() {
		return safePassword;
	}

	public void setSafePassword(String safePassword) {
		this.safePassword = safePassword;
	}

	public File[] getBorImagesFile() {
		return borImagesFile;
	}

	public void setBorImagesFile(File[] borImagesFile) {
		this.borImagesFile = borImagesFile;
	}

	public BorrowPledgeService getBorrowPledgeService() {
		return borrowPledgeService;
	}

	public void setBorrowPledgeService(BorrowPledgeService borrowPledgeService) {
		this.borrowPledgeService = borrowPledgeService;
	}

	public int getMemberType() {
		return memberType;
	}

	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<BorrowBean> getBorrowBeanList() {
		return borrowBeanList;
	}

	public void setBorrowBeanList(List<BorrowBean> borrowBeanList) {
		this.borrowBeanList = borrowBeanList;
	}

	public Integer getbId() {
		return bId;
	}

	public void setbId(Integer bId) {
		this.bId = bId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String[] getBorImageList() {
		return borImageList;
	}

	public void setBorImageList(String[] borImageList) {
		this.borImageList = borImageList;
	}

	public BorrowService getBorrowService() {
		return borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public Map<String, Object> getRoot() {
		return root;
	}

	public void setRoot(Map<String, Object> root) {
		this.root = root;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(String investMoney) {
		this.investMoney = investMoney;
	}

}
