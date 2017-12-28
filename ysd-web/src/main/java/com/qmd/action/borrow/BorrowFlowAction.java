package com.qmd.action.borrow;

import com.qmd.action.base.BaseAction;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccount;
import com.qmd.service.borrow.BorrowFlowDivideService;
import com.qmd.service.borrow.BorrowFlowFullService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.borrow.BorrowTenderService;
import com.qmd.service.user.UserAccountService;
import com.qmd.service.user.UserService;
import com.qmd.util.*;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service("borrowFlowAction")
@InterceptorRefs({ @InterceptorRef(value = "userVerifyInterceptor"),
		@InterceptorRef(value = "qmdDefaultStack") })
public class BorrowFlowAction extends BaseAction {

	private static final long serialVersionUID = -3174004229386962982L;

	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(BorrowFlowAction.class);

	@Resource
	UserService userService;
	@Resource
	BorrowService borrowService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	BorrowTenderService borrowTenderService;
	@Resource
	BorrowFlowDivideService borrowFlowDivideService;
	@Resource
	BorrowFlowFullService borrowFlowFullService;

	public Integer bId;
	private Borrow borrow; // 标的信息

	private Integer memberType;// 会员类型 0:个人;1:企业

	private String mycode;// 验证码
	private String isDxb1;// 是否有定向密码
	private String borImageFirst;// 标的图片
	private String[] vouchers;// 标的凭证图片
	private String[] vouchersTitle;// 标的凭证标题

	private Double wanderPieceInterest;
	private User user;
	private BigDecimal userAbleMoney;
	private BigDecimal continueTotal;
	private int maxWanderPiece;
	private int minWanderPiece;
	private int maxWanderPieceContinue;
	private int minWanderPieceContinue;

	private BorrowTender borrowTender;
	private String tenderMoney;
	private String tenderMoneyContinue;
	private String errorMsg;

	private Map<String, Object> root = new HashMap<String, Object>();
	private String dxpwd;// 定向密码
	private String safepwd;// 交易密码

	/**
	 * 添加流转标页面
	 */
	@Action(value = "/borrow/borrowInputFlow", results = {
			@Result(name = "success", location = "/content/borrow/borrowInputFlow.ftl", type = "freemarker"),
			@Result(name = "fail", location = "/content/borrow/toSuccess.ftl", type = "freemarker") })
	public String borrowInputFlow() {
		User user = getLoginUser();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", user.getId());
		User user1 = userService.getUser(map);
		Map<String, Object> bMap = new HashMap<String, Object>();
		bMap.put("userId", user.getId());
		int[] array = { 0 };
		bMap.put("array", array);

		if (user.getTypeId() != 1 || user1.getTypeId() != 1) {
			return "fail";
		} else {
			memberType = user.getMemberType();
			borrow = new Borrow();
			return "success";
		}
	}

	/**
	 * addBorFlow 添加流转标
	 * 
	 * @return
	 */
	@Action(value = "/borrow/addBorFlow", results = {
			@Result(name = "success", location = "/content/borrow/success.ftl", type = "freemarker"),
			@Result(name = "input", location = "/content/borrow/borrowInputFlow.ftl", type = "freemarker") })
	public String addBorFlow() {

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
//		} else if (StringUtil.isEmpty(borImageFirst)) {
//			addActionError("图片不能为空，请重新输入!");
//			return "error_ftl";
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
			/*
			 * }else if
			 * (!WanderUtil.isMod(borrow.getTimeLimit(),String.valueOf(borrow
			 * .getWanderRedeemTimes()))){ addActionError("请输入正确的借款时长和借款分期!");
			 * return "error_ftl"; } else
			 * if(borrow.getWanderStages()==null||borrow
			 * .getWanderStages().equals("")){ addActionError("请设置回购计划!");
			 * return "error_ftl";
			 */
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
		borrow.setType("5");// 新流转标
		borrow.setVaryYearRate(BigDecimal.valueOf(borrow.getApr()*0.365));
		borrow.setDivides(Integer.valueOf(borrow.getValidTime()));

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

//		borrow.setBorImageFirst(CommonUtil.decodeUrl(borImageFirst));
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

		if (addFlg) {
			borrowService.addBorrow(borrow);
		} else {
			borrowService.updateBorrowMess(borrow);
		}

		return "success";
	}

	/**
	 * 弹出投标页面
	 * 3644
	 * @return
	 */
	@Action(value = "/borrow/poputFlow", results = { @Result(name = "success", location = "/content/borrow/poputFlow.ftl", type = "freemarker") })
	public String poputFlow() {

		borrow = this.borrowService.getBorrowById(bId);
		if (borrow == null) {
			return ERROR;
		}

		wanderPieceInterest = borrow.getWanderPieceMoney().doubleValue()
				* Double.valueOf(borrow.getTimeLimit()) * borrow.getApr()
				/ 1000;

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
	 * 投标
	 * 
	 * @return
	 */
	@Action(value = "/borrow/investFlowDo", results = { @Result(name = "success", params = {
			"root", "root" }, type = "json") })
	public String investFlowDo() {

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
			setSession(ConstantUtil.RANDOM_COOKIE_NAME, null);
			if (!mycode.equals(random)) {
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

			if ("5".equals(borrow.getType())) {
				borrowTender = new BorrowTender();
				borrowTender.setBorrowId(bId);

				borrowTender.setAbleAmount(CommonUtil
						.setPriceScale(thisTenderMoney));
				borrowTender.setContinueAmount(CommonUtil
						.setPriceScale(thisTenderMoneyContinue));

				borrowTender.setMoney(String.valueOf((Double
						.valueOf(tenderMoney) + Double
						.valueOf(tenderMoneyContinue))
						* borrow.getWanderPieceMoney().doubleValue()));

				if ("2".equals(borrow.getStyle())) {
					inret = this.borrowFlowDivideService.borrowInvestDo(user,
							borrowTender, this.obtainUserIp());
				} else {
					inret = this.borrowFlowFullService.borrowInvestDo(user,
							borrowTender, this.obtainUserIp());
				}
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

	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getMycode() {
		return mycode.toLowerCase();
	}

	public void setMycode(String mycode) {
		this.mycode = mycode;
	}

	public String getIsDxb1() {
		return isDxb1;
	}

	public void setIsDxb1(String isDxb1) {
		this.isDxb1 = isDxb1;
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

	public Double getWanderPieceInterest() {
		return wanderPieceInterest;
	}

	public void setWanderPieceInterest(Double wanderPieceInterest) {
		this.wanderPieceInterest = wanderPieceInterest;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public int getMaxWanderPiece() {
		return maxWanderPiece;
	}

	public void setMaxWanderPiece(int maxWanderPiece) {
		this.maxWanderPiece = maxWanderPiece;
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

	public BorrowTender getBorrowTender() {
		return borrowTender;
	}

	public void setBorrowTender(BorrowTender borrowTender) {
		this.borrowTender = borrowTender;
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

}
