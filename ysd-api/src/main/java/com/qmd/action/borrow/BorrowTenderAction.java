package com.qmd.action.borrow;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.bean.borrow.BorrowPoputMessage;
import com.qmd.bean.user.UserHongbaoItem;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserHongbao;
import com.qmd.service.borrow.*;
import com.qmd.service.user.UserHongbaoService;
import com.qmd.util.ApiConstantUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目投资
 *
 */
@InterceptorRefs({ 
	@InterceptorRef(value = "userVerifyInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack") })
@Service("borrowTenderAction")
public class BorrowTenderAction extends BaseAction {
	
	private static final long serialVersionUID = 6842387158056555592L;
	private String dxpwd;//定向密码
	private String safepwd;// 安全密码
	private String tenderMoney;//投资金额
	private String clientType;
	
	private User user;
	private Borrow borrow;
	private Integer[] hongbao;//红包ID数组
	private String hongbaoArray;//红包ID IOS专用
	BorrowPoputMessage bean;
	List<UserHongbaoItem> hbItemList;

	@Resource
	BorrowService borrowService;
	@Resource
	BorrowSecondService borrowSecondService;
	@Resource
	BorrowTenderService borrowTenderService;
	@Resource
	BorrowPromoteService borrowPromoteService;
	@Resource
	UserHongbaoService userHongbaoService;
	@Resource
	BorrowTasteService borrowTasteService;
	
	/**
	 * 投资窗口 弹出
	 * @return
	 */
	@Action(value = "/poputInvest/detail",results = { @Result(name = "success", location = "/content/h5invest/poputInvest.ftl", type = "freemarker") })
	public String borrowPoputInvest() {
			
			 bean = new BorrowPoputMessage();
//			if(StringUtils.isEmpty(tenderMoney)){
//				return ajaxJson("M0007_1",ApiConstantUtil.M0007_1);
//			}
			bean.setId(Integer.parseInt(id));
//			ppm.setTenderMoney(tenderMoney);
			borrow = this.borrowService.getBorrowById(Integer.parseInt(id));
			if (borrow == null) {
				msg="此标不存在!";
				return "error_ftl";
			}

			if (Double.valueOf(borrow.getBalance()) <= 0) {
				msg="项目已满!";
				return "error_ftl";
			}
			
			bean.setAwardScale(borrow.getAwardScale()!= null ?borrow.getAwardScale().toString():"0");
			bean.setIsDxb(borrow.getIsDxb());

			reloadUser();
			User u =getLoginUser(); 

			if("16".equals(borrow.getType())){
				Map<String, Object> tMap = new HashMap<String, Object>();
				tMap.put("userId", u.getId());
				tMap.put("backStatus", 0);
				tMap.put("noBorrowType", "17");
				List<BorrowTender> btList = borrowTenderService.getTenderDetailByUserid(tMap);
				if(btList.size() > 0){
					msg="您已投资过，不符合新手条件!";
					return "error_ftl";
				}
			}
			
			if(u != null){
				bean.setAbleMoney(u.getAbleMoney()!= null ?u.getAbleMoney().toString():"0");
				bean.setAwardMoney(u.getAwardMoney()!= null ?u.getAwardMoney().toString():"");
				bean.setTasteMoney(u.getTasteMoney()!= null ?u.getTasteMoney().toString():"");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", u.getId());
				map.put("status", 0);
				map.put("isHfive", 1);
				map.put("limitStart", Integer.parseInt(borrow.getTimeLimit()));
				map.put("limitEnd", Integer.parseInt(borrow.getTimeLimit()));
				map.put("orderBy", " t.end_time asc ");
				map.put("pageStart", 0);
				map.put("pageSize", 12);
				List<UserHongbao> hongbaoList = userHongbaoService.queryListByMap(map);
				 hbItemList = new ArrayList<UserHongbaoItem>();
				
				if(hongbaoList  != null && hongbaoList.size() > 0){
					for(UserHongbao hb:hongbaoList){
						UserHongbaoItem hbItem = new UserHongbaoItem();
						BeanUtils.copyProperties(hb, hbItem);
						hbItemList.add(hbItem);
					}
					bean.setUserHongbaoItem(hbItemList);
				}
			}
			
			return SUCCESS;
		
	}

	/**
	 * 密码输入完成-点击确定
	 * @return
	 */
	@Action(value = "/investDo", results = { @Result(type = "json") })
	@InputConfig(resultName = "error_ftl,success_ftl")
	public String borrowInvestDo() {
		
		try{
			BorrowTender borrowTender = new BorrowTender();
			borrowTender.setMoney(tenderMoney);
			borrowTender.setBorrowId(Integer.parseInt(id));
			borrowTender.setAbleAmount(tenderMoney);
			borrowTender.setContinueAmount("0");
			borrowTender.setClientType(Integer.valueOf(clientType));
			
			reloadUser();
			if(StringUtils.isEmpty(getLoginUser().getPayPassword()) ){
				return ajax(Status.error, ApiConstantUtil.S0004);
			}
			if(StringUtils.isEmpty(clientType)){
				return ajax(Status.error, ApiConstantUtil.M0008_15);
			}
			
			if(!userService.isPassword(getLoginUser().getUsername(), safepwd, "1")){
	
				return ajax(Status.error, ApiConstantUtil.S0002);
			}
			
			if (StringUtils.isEmpty(borrowTender.getMoney())) {
				return ajax(Status.error, ApiConstantUtil.M0007_1);
			}
			if (borrowTender.getMoney().compareTo(getLoginUser().getAbleMoney().toString())>0) {
				return ajax(Status.error, ApiConstantUtil.M0007_0);
			}
			
			if (!this.isNumeric(borrowTender.getMoney())) {
				return ajax(Status.error, ApiConstantUtil.M0007_2);
			}
			if (Double.valueOf(borrowTender.getMoney()) % 100 != 0) {
				return ajax(Status.error, ApiConstantUtil.M0007_3);
			}
			
			
			user = this.getLoginUser();
			UserAccount userAccount = this.userAccountService.getUserAccountByUserId(user.getId());
			borrow = this.borrowService.getBorrowById(Integer.parseInt(id));

			if ("1".equals(borrow.getIsDxb())) {
				if (dxpwd == null || "".equals(dxpwd)) {
					return ajax(Status.error, ApiConstantUtil.M0007_10);
				}
				if (!dxpwd.equals(borrow.getPwd())) {
					return ajax(Status.error, ApiConstantUtil.M0007_11);
				}
			}
			
			// 投标金额大于可用金额
//			if (userAccount.getAbleMoney().doubleValue() < Double.parseDouble(borrowTender.getMoney())) {
//				return	ajaxJson("M0007_0",ApiConstantUtil.M0007_0);
//			}
			// 投标额度是否小于最小投资金额
			if (Double.valueOf(borrowTender.getMoney()) < Double.valueOf(borrow.getLowestAccount())) {
				return ajax(Status.error,ApiConstantUtil.getM0007_4(borrow.getLowestAccount()));
			}
			// 投标总额大于最大投标额
			if (borrow.getMostAccount() != null&&!"".equals(borrow.getMostAccount())) {
				Map<String, Object> tMap = new HashMap<String, Object>();
				tMap.put("userId", user.getId());
				tMap.put("borrowId", borrow.getId());
				Object totalAmount = this.borrowTenderService.getByStatementId(
						"BorrowTender.selectAllAccountByUserid", tMap);
				if (totalAmount != null) {
					double tAmount = Double.valueOf(totalAmount.toString());
					if ((tAmount + Double.valueOf(borrowTender.getMoney())) > Double
							.valueOf(borrow.getMostAccount())) {
						return ajax(Status.error, ApiConstantUtil.getM0007_5(borrow.getMostAccount()));
					}
				} else if (Integer.parseInt(borrow.getMostAccount()) < Integer.parseInt(borrowTender.getMoney())) {
					return ajax(Status.error, ApiConstantUtil.getM0007_5(borrow.getMostAccount()));
				}
			}
			if (Double.valueOf(borrow.getBalance()) <= 0) {
				return ajax(Status.error, ApiConstantUtil.M0007_6);
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
				System.out.println("hongbaoArray-----------------------"+hongbaoArray);
				if(StringUtils.isNotEmpty(hongbaoArray)){
//					hongbaoArray = hongbaoArray.substring(1, hongbaoArray.length()-1);
					String[] bb = hongbaoArray.split(",");
					int length = bb.length;
					hongbao = new Integer[length];
					System.out.println("length-----------------------"+length);
					if(length >1){
						for (int i = 0; i < length; i++) {
							hongbao[i] = Integer.parseInt(bb[i]);
						}
					}else{
						hongbao[0] = Integer.parseInt( hongbaoArray);
					}
				}
//				System.out.println("hongbao_length="+hongbao.length);
				inret = this.borrowPromoteService.borrowInvestDo(user,
						borrowTender, this.obtainUserIp(),hongbao);
			} else if("17".equals(borrow.getType())){
				inret = this.borrowTasteService.borrowInvestDo(user,
						borrowTender, this.obtainUserIp());
			}else {// 质押标
				inret = this.borrowService.borrowInvestDo(user, borrowTender,
						this.obtainUserIp());
			}
			if (inret == 0) {
				return ajax(Status.success, "投资成功");
			} else if (inret == 1) {
				return ajax(Status.error,ApiConstantUtil.M0007_6);
			} else if (inret == 2) {
				return ajax(Status.error, ApiConstantUtil.M0007_0);
			} else if (inret == 3) {
				return ajax(Status.error, ApiConstantUtil.M0007_7);
			} else if (inret == 4) {
				return ajax(Status.error, ApiConstantUtil.M0007_8);
			} else if (inret == 5) {
				return ajax(Status.error, "新手项目只能未投资过的用户才能参加");
			} else {
				return ajax(Status.error, ApiConstantUtil.M0007_9);
			}
		}catch (Exception e) {
			e.printStackTrace();
			 return ajax(Status.error, ApiConstantUtil.S0001);
		}
	}

	public String getSafepwd() {
		return safepwd;
	}

	public void setSafepwd(String safepwd) {
		this.safepwd = safepwd;
	}

	public String getTenderMoney() {
		return tenderMoney;
	}

	public void setTenderMoney(String tenderMoney) {
		this.tenderMoney = tenderMoney;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getDxpwd() {
		return dxpwd;
	}

	public void setDxpwd(String dxpwd) {
		this.dxpwd = dxpwd;
	}

	public Integer[] getHongbao() {
		return hongbao;
	}

	public void setHongbao(Integer[] hongbao) {
		this.hongbao = hongbao;
	}

	public String getHongbaoArray() {
		return hongbaoArray;
	}

	public void setHongbaoArray(String hongbaoArray) {
		this.hongbaoArray = hongbaoArray;
	}

	public BorrowPoputMessage getBean() {
		return bean;
	}

	public void setBean(BorrowPoputMessage bean) {
		this.bean = bean;
	}

	public List<UserHongbaoItem> getHbItemList() {
		return hbItemList;
	}

	public void setHbItemList(List<UserHongbaoItem> hbItemList) {
		this.hbItemList = hbItemList;
	}

	
}
