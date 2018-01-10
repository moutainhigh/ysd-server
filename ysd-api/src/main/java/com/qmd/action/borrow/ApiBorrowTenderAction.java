package com.qmd.action.borrow;

import com.qmd.action.base.ApiBaseAction;
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
import com.qmd.util.CommonUtil;
import com.qmd.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目投资
 *
 */
@InterceptorRefs({ 
	@InterceptorRef(value = "apiUserInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack") })
@Service("apiBorrowTenderAction")
public class ApiBorrowTenderAction extends ApiBaseAction {

	private static final long serialVersionUID = 4804405059860975651L;
	
	private String dxpwd;//定向密码
	private String safepwd;// 安全密码
	private String tenderMoney;//投资金额
	private String clientType;
	
	private User user;
	private Borrow borrow;
	private Integer[] hongbao;//红包ID数组
	private String hongbaoArray;//红包ID IOS专用

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
	@Action(value = "/api/poputInvest/detail")
	public String borrowPoputInvest() {
		try{
			
			BorrowPoputMessage bean = new BorrowPoputMessage();
//			if(StringUtils.isEmpty(tenderMoney)){
//				return ajaxJson("M0007_1",ApiConstantUtil.M0007_1);
//			}
			bean.setId(Integer.parseInt(id));
//			ppm.setTenderMoney(tenderMoney);
			borrow = this.borrowService.getBorrowById(Integer.parseInt(id));
			if (borrow == null) {
				ajaxJson("M0010",ApiConstantUtil.M0010);
			}

			if (Double.valueOf(borrow.getBalance()) <= 0) {
				return ajaxJson("M0007_6",ApiConstantUtil.M0007_6);
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
				if(u !=null){
					if(u.getId()>=2777&u.getId()<=2788){
						
					}else if(btList.size() > 0){
						return ajaxJson("M0007_12","您已投资过，不符合新手条件");
					}
				}
			}
			
			if(u != null){
				bean.setAbleMoney(u.getAbleMoney().toString());
				bean.setAwardMoney(u.getAwardMoney()!= null ?u.getAwardMoney().toString():"");
				bean.setTasteMoney(u.getTasteMoney()!= null ?u.getTasteMoney().toString():"");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", u.getId());
				map.put("status", 0);
				map.put("isApp", 1);
				map.put("limitStart", Integer.parseInt(borrow.getTimeLimit()));
				map.put("limitEnd", Integer.parseInt(borrow.getTimeLimit()));
				map.put("orderBy", " t.end_time asc ");
				map.put("pageStart", 0);
				map.put("pageSize", 12);
				List<UserHongbao> hongbaoList = userHongbaoService.queryListByMap(map);
				List<UserHongbaoItem> hbItemList = new ArrayList<UserHongbaoItem>();
				
				if(hongbaoList  != null && hongbaoList.size() > 0){
					for(UserHongbao hb:hongbaoList){
						UserHongbaoItem hbItem = new UserHongbaoItem();
						BeanUtils.copyProperties(hb, hbItem);
						hbItemList.add(hbItem);
					}
					bean.setUserHongbaoItem(hbItemList);
				}
			}
			
			return ajax(JsonUtil.toJson(bean));
		}catch (Exception e) {
			e.printStackTrace();
			 return ajaxJson("S0001",ApiConstantUtil.S0001);
		}
	}

	
	
	/**
	 * 投资窗口 弹出 新功能
	 * @return
	 */
	@Action(value = "/api/poputInvestH/detail")
	public String borrowPoputInvestH() {
		try{
			
			BorrowPoputMessage bean = new BorrowPoputMessage();
//			if(StringUtils.isEmpty(tenderMoney)){
//				return ajaxJson("M0007_1",ApiConstantUtil.M0007_1);
//			}
			bean.setId(Integer.parseInt(id));
//			ppm.setTenderMoney(tenderMoney);
			borrow = this.borrowService.getBorrowById(Integer.parseInt(id));
			if (borrow == null) {
				ajaxJson("M0010",ApiConstantUtil.M0010);
			}
			
			if (Double.valueOf(borrow.getBalance()) <= 0) {
				return ajaxJson("M0007_6",ApiConstantUtil.M0007_6);
			}
			
			
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rcd","R0001");
			map.put("rmg","成功");
			map.put("apr",CommonUtil.setPriceScale(borrow.getVaryYearRate().multiply(new BigDecimal(100))).doubleValue());//年利率
			map.put("lowestAccount",borrow.getLowestAccount());//最低投标额
			map.put("mostAccount",borrow.getMostAccount());//最高投标额
			map.put("balance",borrow.getBalance());//剩余投资额
			map.put("timeLimit",borrow.getTimeLimit());//项目天数
			

			reloadUser();
			User u =getLoginUser(); 
			if("16".equals(borrow.getType())){
				Map<String, Object> tMap = new HashMap<String, Object>();
				tMap.put("userId", u.getId());
				tMap.put("backStatus", 0);
				tMap.put("noBorrowType", "17");
				List<BorrowTender> btList = borrowTenderService.getTenderDetailByUserid(tMap);
				if(u !=null){
					if(u.getId()>=2777&u.getId()<=2788){
						
					}else if(btList.size() > 0){
						return ajaxJson("M0007_12","您已投资过，不符合新手条件");
					}
				}
			}
			
			
			if(u != null){
				map.put("ableMoney",u.getAbleMoney()!= null ?u.getAbleMoney().toString():0);		
			}
			

			return ajax(JsonUtil.toJson(map));
		}catch (Exception e) {
			e.printStackTrace();
			 return ajaxJson("S0001",ApiConstantUtil.S0001);
		}
	}
	/**
	 * 密码输入完成-点击确定
	 * @return
	 */
	@Action(value = "/api/investDo/detail")
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
				return ajaxJson("S0004",ApiConstantUtil.S0004);
			}
			if(StringUtils.isEmpty(clientType)){
				return ajaxJson("M0008_15",ApiConstantUtil.M0008_15);
			}
			
			if(!userService.isPassword(getLoginUser().getUsername(), safepwd, "1")){
	
				return ajaxJson("S0002",ApiConstantUtil.S0002);
			}
			
			if (StringUtils.isEmpty(borrowTender.getMoney())) {
				return	ajaxJson("M0007_1",ApiConstantUtil.M0007_1);
			}
			
			if (!this.isNumeric(borrowTender.getMoney())) {
				return ajaxJson("M0007_2",ApiConstantUtil.M0007_2);
			}
			if (Double.valueOf(borrowTender.getMoney()) % 100 != 0) {
				return	ajaxJson("M0007_3",ApiConstantUtil.M0007_3);
			}
			
			
			user = this.getLoginUser();
			UserAccount userAccount = this.userAccountService.getUserAccountByUserId(user.getId());
			borrow = this.borrowService.getBorrowById(Integer.parseInt(id));

			if ("1".equals(borrow.getIsDxb())) {
				if (dxpwd == null || "".equals(dxpwd)) {
					return	ajaxJson("M0007_10",ApiConstantUtil.M0007_10);
				}
				if (!dxpwd.equals(borrow.getPwd())) {
					return	ajaxJson("M0007_11",ApiConstantUtil.M0007_11);
				}
			}
			
			// 投标金额大于可用金额
//			if (userAccount.getAbleMoney().doubleValue() < Double.parseDouble(borrowTender.getMoney())) {
//				return	ajaxJson("M0007_0",ApiConstantUtil.M0007_0);
//			}
			// 投标额度是否小于最小投资金额
			if (Double.valueOf(borrowTender.getMoney()) < Double.valueOf(borrow.getLowestAccount())) {
				return ajaxJson("M0007_4",ApiConstantUtil.getM0007_4(borrow.getLowestAccount()));
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
						return	ajaxJson("M0007_5",ApiConstantUtil.getM0007_5(borrow.getMostAccount()));
					}
				} else if (Integer.parseInt(borrow.getMostAccount()) < Integer.parseInt(borrowTender.getMoney())) {
					return	ajaxJson("M0007_5",ApiConstantUtil.getM0007_5(borrow.getMostAccount()));
				}
			}
			if (Double.valueOf(borrow.getBalance()) <= 0) {
				return ajaxJson("M0007_6",ApiConstantUtil.M0007_6);
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
				return ajaxJson("R0001","投资成功");
			} else if (inret == 1) {
				return ajaxJson("M0007_6",ApiConstantUtil.M0007_6);
			} else if (inret == 2) {
				return ajaxJson("M0007_0",ApiConstantUtil.M0007_0);
			} else if (inret == 3) {
				return ajaxJson("M0007_7",ApiConstantUtil.M0007_7);
			} else if (inret == 4) {
				return ajaxJson("M0007_8",ApiConstantUtil.M0007_8);
			} else if (inret == 5) {
				return ajaxJson("M0007_9","新手项目只能未投资过的用户才能参加");
            } else if (inret == 999) {
                return ajaxJson("M0007_9", "存管处理失败");
			} else {
				return ajaxJson("M0007_9",ApiConstantUtil.M0007_9);
			}
		}catch (Exception e) {
			e.printStackTrace();
			 return ajaxJson("S0001",ApiConstantUtil.S0001);
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
	
	/**
	 * 密码输入完成-点击确定 
	 * @return
	 */
	@Action(value = "/api/investDoH/detail")
	public String borrowInvestDoH() {
		
		try{
			BorrowTender borrowTender = new BorrowTender();
			borrowTender.setMoney(tenderMoney);
			borrowTender.setBorrowId(Integer.parseInt(id));
			borrowTender.setAbleAmount(tenderMoney);
			borrowTender.setContinueAmount("0");
			borrowTender.setClientType(Integer.valueOf(clientType));
			
			reloadUser();
			if(StringUtils.isEmpty(getLoginUser().getPayPassword()) ){
				return ajaxJson("S0004",ApiConstantUtil.S0004);
			}
			if(StringUtils.isEmpty(clientType)){
				return ajaxJson("M0008_15",ApiConstantUtil.M0008_15);
			}
			
			if(!userService.isPassword(getLoginUser().getUsername(), safepwd, "1")){
	
				return ajaxJson("S0002",ApiConstantUtil.S0002);
			}
			
			if (StringUtils.isEmpty(borrowTender.getMoney())) {
				return	ajaxJson("M0007_1",ApiConstantUtil.M0007_1);
			}
			
			if (!this.isNumeric(borrowTender.getMoney())) {
				return ajaxJson("M0007_2",ApiConstantUtil.M0007_2);
			}
			if (Double.valueOf(borrowTender.getMoney()) % 100 != 0) {
				return	ajaxJson("M0007_3",ApiConstantUtil.M0007_3);
			}
			
			
			user = this.getLoginUser();
			UserAccount userAccount = this.userAccountService.getUserAccountByUserId(user.getId());
			borrow = this.borrowService.getBorrowById(Integer.parseInt(id));

			if ("1".equals(borrow.getIsDxb())) {
				if (dxpwd == null || "".equals(dxpwd)) {
					return	ajaxJson("M0007_10",ApiConstantUtil.M0007_10);
				}
				if (!dxpwd.equals(borrow.getPwd())) {
					return	ajaxJson("M0007_11",ApiConstantUtil.M0007_11);
				}
			}
			
			// 投标金额大于可用金额
//			if (userAccount.getAbleMoney().doubleValue() < Double.parseDouble(borrowTender.getMoney())) {
//				return	ajaxJson("M0007_0",ApiConstantUtil.M0007_0);
//			}
			// 投标额度是否小于最小投资金额
			if (Double.valueOf(borrowTender.getMoney()) < Double.valueOf(borrow.getLowestAccount())) {
				return ajaxJson("M0007_4",ApiConstantUtil.getM0007_4(borrow.getLowestAccount()));
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
						return	ajaxJson("M0007_5",ApiConstantUtil.getM0007_5(borrow.getMostAccount()));
					}
				} else if (Integer.parseInt(borrow.getMostAccount()) < Integer.parseInt(borrowTender.getMoney())) {
					return	ajaxJson("M0007_5",ApiConstantUtil.getM0007_5(borrow.getMostAccount()));
				}
			}
			if (Double.valueOf(borrow.getBalance()) <= 0) {
				return ajaxJson("M0007_6",ApiConstantUtil.M0007_6);
			}
			int inret = 1;// 0 投标成功
			
			//标只有新手标16和天标  14
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
				inret = this.borrowPromoteService.borrowInvestDoHNew(user,
						borrowTender, this.obtainUserIp(),hongbao);
			} else if("17".equals(borrow.getType())){//没用
				inret = this.borrowTasteService.borrowInvestDo(user,
						borrowTender, this.obtainUserIp());
			}else {// 质押标                                            没用
				inret = this.borrowService.borrowInvestDo(user, borrowTender,
						this.obtainUserIp());
			}
			if (inret == 0) {
				Map<String, Object> tMapSucc = new HashMap<String, Object>();
				tMapSucc.put("rcd", "R0001");
				tMapSucc.put("msg", "投资成功");
				tMapSucc.put("borrowName",borrow.getName());
				tMapSucc.put("account",borrowTender.getMoney());//实际投资额
	
				return ajax(JsonUtil.toJson(tMapSucc));
			} else if (inret == 1) {
				return ajaxJson("M0007_6",ApiConstantUtil.M0007_6);
			} else if (inret == 2) {
				return ajaxJson("M0007_0",ApiConstantUtil.M0007_0);
			} else if (inret == 3) {
				return ajaxJson("M0007_7",ApiConstantUtil.M0007_7);
			} else if (inret == 4) {
				return ajaxJson("M0007_8",ApiConstantUtil.M0007_8);
			} else if (inret == 5) {
				return ajaxJson("M0007_9","新手项目只能未投资过的用户才能参加");
			}else if (inret == 211) {
				return ajaxJson("M0007_211","投资金额大于剩余可投金额");
			}
			else {
				return ajaxJson("M0007_9",ApiConstantUtil.M0007_9);
			}
		}catch (Exception e) {
			e.printStackTrace();
			 return ajaxJson("S0001",ApiConstantUtil.S0001);
		}
	}

	
}
