package net.qmdboss.action.admin;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import net.qmdboss.DTO.*;
import net.qmdboss.bean.AccountDetail;
import net.qmdboss.bean.BorrowTenderItem;
import net.qmdboss.bean.CensusUserListBean;
import net.qmdboss.bean.UserListBean;
import net.qmdboss.entity.AccountCash;
import net.qmdboss.entity.Listing;
import net.qmdboss.entity.ReportFirstTender;
import net.qmdboss.entity.User;
import net.qmdboss.service.*;
import net.qmdboss.util.CommonUtil;
import net.qmdboss.util.SettingUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Boolean;

@ParentPackage("admin")
public class DeriveReportAction  extends BaseAdminAction {

	private static final long serialVersionUID = 5759030605544502091L;

	private User user;
	private String type;
	private String amount;
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private Boolean isExact;//是否精确查找
	private AccountCash accountCash;
	private List<AccountDetail> accountDetailList;
	private List<Listing> listingList;
	private List<AccountCash> accountCashList;
	
	private List<UserAccountCashDTO> userAccountCashDTOList;
	
	private List<UserAccountDTO> userAccountDTOList;
	

	private Integer rechargeInterfaceId;//充值方式ID
	private String dateType;//提交时间【0】还是完成时间【1】
	private Integer status;//处理状态
	private String moneygt;//大于金额
	private String moneylt;//小于金额
	private String username;
	
	@Resource(name = "userAccountServiceImpl")
	private UserAccountService userAccountService;
	@Resource(name = "userAccountDetailServiceImpl")
	private UserAccountDetailService userAccountDetailService;
	@Resource(name = "userRepaymentDetailServiceImpl")
	private UserRepaymentDetailService userRepaymentDetailService;
	@Resource(name = "listingServiceImpl")
	private ListingService listingService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "userAccountRechargeServiceImpl")
	private UserAccountRechargeService userAccountRechargeService;
	
	@Resource(name = "accountCashServiceImpl")
	private AccountCashService accountCashService;
	
	@Resource(name = "rewardsServiceImpl")
	private RewardsService rewardsService;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;

	@Resource(name = "reportFirstTenderServiceImpl")
	private ReportFirstTenderService reportFirstTenderService;
	
	@Resource(name = "reportUserAccountBackupServiceImpl")
	private ReportUserAccountBackupService reportUserAccountBackupService;
	
//	@Resource(name = "reportUserAccountBackupServiceImpl")
//	private ReportUserAccountBackupService reportUserAccountBackupService;
//
//	@Resource(name = "basiceRechargeServiceImpl")
//	private BasiceRechargeService basiceRechargeService;
//
//	@Resource(name = "borrowDetailServiceImpl")
//	private BorrowDetailService borrowDetailService;
	
	
	/**
	 * 导出所有用户账户数据
	 * @return
	 */
	public String exceAccount(){
		Map<String,Object> map = new HashMap<String, Object>();
		List<User> accountList = new ArrayList<User>();
		
		if(StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(amount)){
			Pattern numberPattern = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?");
			Matcher matcher = numberPattern.matcher(amount);
			if (!matcher.matches()) {
				addErrorMessages("额度只允许输入数字!");
				return ERROR;
			}
			map.put("type", type);
			map.put("amount", amount);
			
		}
		if(user!=null){
			map.put("memberType", user.getMemberType());
			map.put("typeId", user.getTypeId());
			map.put("username", user.getUsername());
		}
		
		//accountList = userAccountService.getPagerByUser(map);
		userAccountDTOList=userAccountService.findAccountDTOByUser(map);
		OutputStream out = null;
		try {
			 out = getResponse().getOutputStream();
			 getResponse().reset();//清空buffer,设置页面不缓存
			 getResponse().setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("账户列表"+  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xls", "UTF-8"));
			 getResponse().setContentType("application/msexcel;charset=UTF-8");
			 
			WritableWorkbook wwb = Workbook.createWorkbook(out);
			WritableSheet ws = wwb.createSheet("用户账户记录", 0);
		
			Label l= new Label(0, 0, "userid");
			ws.addCell(l);
			
			//l= new Label(1, 0, "会员类型");//个人/企业
			//ws.addCell(l);
			//l= new Label(2, 0, "会员属性");//投资人/借款人
			//ws.addCell(l);

			l= new Label(1, 0, "用户名");
			ws.addCell(l);
			l= new Label(2, 0, "真实姓名");
			ws.addCell(l);
			l= new Label(3, 0, "总金额");
			ws.addCell(l);
			l= new Label(4, 0, "可用金额");
			ws.addCell(l);
			l= new Label(5, 0, "冻结金额");
			ws.addCell(l);
			l= new Label(6, 0, "红包金额");
			ws.addCell(l);
			
			l= new Label(7, 0, "投资者待收本金");
			ws.addCell(l);
			l= new Label(8, 0, "投资者待收利息");
			ws.addCell(l);
			//l= new Label(12, 0, "借款人待付本金");
			//ws.addCell(l);
			//l= new Label(13, 0, "借款人待付利息");
			//ws.addCell(l);
			
			int i=1;
			for(UserAccountDTO udto:userAccountDTOList){
				l=new Label(0,i, udto.getId().toString() );
				
			/*	ws.addCell(l);
				//l= new Label(1, i, user.getMemberType()==null || user.getMemberType()==0?"个人":"企业");
				//ws.addCell(l);
				//l= new Label(2, i, user.getTypeId()==null || user.getTypeId()==0?"投资者":"借款人");
				//ws.addCell(l);
				
				l= new Label(3, i, udto.getUsername());
				ws.addCell(l);
				l= new Label(4, i, udto.getRealName());
				ws.addCell(l);
				l= new Label(5, i, udto.getTotal()!=null ? SettingUtil.currencyFormat(udto.getTotal()) : "0.00");
				ws.addCell(l);
				l= new Label(6, i, udto.getAbleMoney() != null ? SettingUtil.currencyFormat(udto.getAbleMoney()) : "0.00");
				ws.addCell(l);
				l= new Label(7, i, udto.getUnableMoney() != null ? SettingUtil.currencyFormat(udto.getUnableMoney()) : "0.00");
				ws.addCell(l);
				//l= new Label(8, i, udto.getCollection() != null ? SettingUtil.currencyFormat(user.getAccount().getCollection()) : "0.00");
				//ws.addCell(l);
				//l= new Label(9, i, user.getAccount().getContinueTotal() != null ? SettingUtil.currencyFormat(user.getAccount().getContinueTotal()) : "0.00");
				//ws.addCell(l);
				
				l= new Label(10, i, udto.getInvestorCollectionCapital() != null ? SettingUtil.currencyFormat(udto.getInvestorCollectionCapital()) : "0.00");
				ws.addCell(l);
				l= new Label(11, i, udto.getInvestorCollectionInterest() != null ? SettingUtil.currencyFormat(udto.getInvestorCollectionInterest()) : "0.00");
				ws.addCell(l);
				//l= new Label(12, i, udto.getBorrowerCollectionCapital() != null ? SettingUtil.currencyFormat(user.getAccount().getBorrowerCollectionCapital()) : "0.00");
				//ws.addCell(l);
				//l= new Label(13, i, udto.getBorrowerCollectionInterest() != null ? SettingUtil.currencyFormat(user.getAccount().getBorrowerCollectionInterest()) : "0.00");
				//ws.addCell(l);
*/				
				
				
				
				
				ws.addCell(l);
				l= new Label(1, i, udto.getUsername());
				ws.addCell(l);
				l= new Label(2, i, udto.getRealName());
				ws.addCell(l);
				l= new Label(3, i, udto.getTotal()!=null ? SettingUtil.currencyFormat(udto.getTotal()) : "0.00");
				ws.addCell(l);
				l= new Label(4, i, udto.getAbleMoney() != null ? SettingUtil.currencyFormat(udto.getAbleMoney()) : "0.00");
				ws.addCell(l);
				l= new Label(5, i, udto.getUnableMoney() != null ? SettingUtil.currencyFormat(udto.getUnableMoney()) : "0.00");
				ws.addCell(l);
				l= new Label(6, i, udto.getAwardMoney()!= null ? SettingUtil.currencyFormat(udto.getAwardMoney()) : "0.00");
				ws.addCell(l);
				l= new Label(7, i, udto.getInvestorCollectionCapital() != null ? SettingUtil.currencyFormat(udto.getInvestorCollectionCapital()) : "0.00");
				ws.addCell(l);
				l= new Label(8, i, udto.getInvestorCollectionInterest() != null ? SettingUtil.currencyFormat(udto.getInvestorCollectionInterest()) : "0.00");
				ws.addCell(l);
			
				
				
			
				i++;
			}
			ws.setColumnView(0, 8);// 设置列宽
            ws.setColumnView(1, 10);
            ws.setColumnView(2, 10);
            ws.setColumnView(3, 15);
            ws.setColumnView(4, 15);
            ws.setColumnView(5, 15);
            ws.setColumnView(6, 15);
            ws.setColumnView(7, 15);
          //  ws.setColumnView(8, 15);
          //  ws.setColumnView(9, 15);
            ws.setColumnView(10, 15);
            ws.setColumnView(11, 15);
           // ws.setColumnView(12, 15);
           // ws.setColumnView(13, 15);
            ws.setRowView(0, 600);// 设置行高
            
            wwb.write();
            wwb.close();
            out.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logInfo ="导出所有用户账户记录";
		return null;
	}
	
	/**
	 * 导出用户充值数据
	 * @return
	 */
	public String exceRecharge(){
		Map<String,Object> map = new HashMap<String, Object>();
		if(pager != null && StringUtils.isNotEmpty(pager.getSearchBy())){
			map.put("search", pager.getSearchBy());
			if(StringUtils.isNotEmpty(pager.getKeyword())){
				map.put("keyword", pager.getKeyword());
			}
		}

		map.put("dateType", dateType);
		
		if(startDate!=null){
			if(endDate == null){
				addErrorMessages("截止时间必填!");
				return ERROR;
			}
		}
		if(endDate!=null){
			if(startDate == null){
				addErrorMessages("起始时间必填!");
				return ERROR;
			}
		}
		if(startDate != null && endDate != null){
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
			map.put("startDate", dateformat.format(startDate));
			map.put("endDate", dateformat.format(endDate));
		}
		map.put("rechargeInterfaceId",rechargeInterfaceId);
		map.put("status", status);
		
		if("".equals(moneygt) &&  !"".equals(moneylt)){
			map.put("moneylt", moneylt);
		}else if("".equals(moneylt) && !"".equals(moneygt)){
			map.put("moneygt", moneygt);
		}else if("".equals(moneylt)&& !"".equals(moneylt)){
			if(new BigDecimal(map.get("moneylt").toString()).compareTo(new BigDecimal(map.get("moneygt").toString()))<0){
				addErrorMessages("金额参数错误!");
				return ERROR;
			}
			map.put("moneygt", moneygt);
			map.put("moneylt", moneylt);
		}
		map.put("queryMode", "0");//查询模式【1：精确查找；0：模糊查询】
		List<UserAccountRechargeDTO> rList = userAccountRechargeService.getRechargeList(map);
		System.out.println("导出充值记录条数:"+rList.size());
		OutputStream out = null;
		try {
			 out = getResponse().getOutputStream();
			 getResponse().reset();//清空buffer,设置页面不缓存
			 getResponse().setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("用户充值记录"+  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xls", "UTF-8"));
			 getResponse().setContentType("application/msexcel;charset=UTF-8");
			 
			WritableWorkbook wwb = Workbook.createWorkbook(out);
			WritableSheet ws = wwb.createSheet("用户充值记录", 0);
		
			Label l= new Label(0, 0, "用户ID");
			ws.addCell(l);
			l= new Label(1, 0, "用户名");
			ws.addCell(l);
			l= new Label(2, 0, "真实姓名");
			ws.addCell(l);
			l= new Label(3, 0, "订单号");
			ws.addCell(l);
			l= new Label(4, 0, "充值时间");
			ws.addCell(l);
			l= new Label(5, 0, "完成时间");
			ws.addCell(l);
			l= new Label(6, 0, "类型");
			ws.addCell(l);
			l= new Label(7, 0, "充值接口");
			ws.addCell(l);
			l= new Label(8, 0, "充值金额");
			ws.addCell(l);
			l= new Label(9, 0, "手续费");
			ws.addCell(l);
			l= new Label(10, 0, "状态");
			ws.addCell(l);
			//l= new Label(11, 0, "IP");
			//ws.addCell(l);
			//l= new Label(12, 0, "备注");
			//ws.addCell(l);
			
			int i=1;
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(UserAccountRechargeDTO u:rList){
				//User user = u.getUser();
				if(u!= null){
					l=new Label(0,i,u.getId().toString());
					ws.addCell(l);
					
					l= new Label(1, i, u.getUsername());
					ws.addCell(l);
					l= new Label(2, i, u.getRealName());
					ws.addCell(l);
					l= new Label(3, i, u.getTradeNo());
					ws.addCell(l);
					String type="";
					if(u.getType().equals("0")){
						type="线下充值";
					}else if(u.getType().equals("1")){
						type="线上充值";
					}
					l= new Label(4, i, simple.format(u.getCreateDate()));
					ws.addCell(l);
					l= new Label(5, i, u.getRechargeDate()==null?"": simple.format(u.getRechargeDate()));
					ws.addCell(l);
					l= new Label(6, i,type);
					ws.addCell(l);
					String rechargeInterface="";
					
					
					if(u.getRechargeInterfaceId()==0){
						rechargeInterface="线下充值";
					}else if(u.getRechargeInterfaceId()==34){
						rechargeInterface="网银支付";
					}else if(u.getRechargeInterfaceId()==40){
						rechargeInterface="快捷支付";
					}else if(u.getRechargeInterfaceId()==70){
						rechargeInterface="快捷支付";
					}
					
					
					l= new Label(7, i,rechargeInterface);
					ws.addCell(l);
					l= new Label(8, i, u.getMoney()!=null ? SettingUtil.currencyFormat(u.getMoney()) : "0.00");
					ws.addCell(l);
					l= new Label(9, i, u.getFee() != null ? SettingUtil.currencyFormat(u.getFee()) : "0.00");
					ws.addCell(l);
					String status="";
					if(u.getStatus() == 0){
						status="失败";
					}else if(u.getStatus() == 1){
						status="成功";
					}else if(u.getStatus() == 2){
						status="审核中";
					}
					l= new Label(10, i, status);
					ws.addCell(l);
					//l= new Label(11, i, u.getIpUser());
					//ws.addCell(l);
					//l= new Label(12, i, u.getRemark());
					//ws.addCell(l);
					i++;
				}
				
			}
			ws.setColumnView(0, 8);// 设置列宽
            ws.setColumnView(1, 15);
            ws.setColumnView(2, 25);
            ws.setColumnView(3, 10);
            ws.setColumnView(4, 10);
            ws.setColumnView(5, 10);
            ws.setColumnView(6, 10);
            ws.setColumnView(7, 15);
            ws.setColumnView(8, 15);
            ws.setColumnView(9, 15);
            ws.setColumnView(10, 15);
            ws.setColumnView(11, 15);
            ws.setRowView(0, 600);// 设置行高
            
            wwb.write();
            wwb.close();
            out.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logInfo ="导出用户充值记录";
		return null;
	}
	
	
	/**
	 * 导出用户资金明细
	 * @return
	 */
	public String exceAccountDetail(){
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,String> maplist = new HashMap<String, String>();
		map.put("type", type);
		map.put("isExact", isExact);
		if(pager != null && StringUtils.isNotEmpty(pager.getKeyword())){
			map.put("username", pager.getKeyword());
		}

		if(user!=null){
			map.put("memberType", user.getMemberType());
			map.put("typeId", user.getTypeId());
		}
		
		String typeName="";
		Boolean flag=false;//判断是否根据类型导出数据
		if(StringUtils.isNotEmpty(type)){
			typeName = listingService.findChildListingByKeyValue("account_type",type);
			flag=true;
		}else{
			List<Listing> listingList = listingService.getChildListingBySignList("account_type");
			for(Listing l:listingList){
				maplist.put(l.getKeyValue(), l.getName());
			}
		}
		
		if(startDate!=null){
			if(endDate == null){
				addErrorMessages("截止时间必填!");
				return ERROR;
			}
		}
		if(endDate!=null){
			if(startDate == null){
				addErrorMessages("起始时间必填!");
				return ERROR;
			}
		}
		if(startDate != null && endDate != null){
			
			map.put("startDate", CommonUtil.getDate2String(CommonUtil.date2begin(startDate), "yyyy-MM-dd HH:mm:ss"));
			map.put("endDate", CommonUtil.getDate2String(CommonUtil.date2end(endDate), "yyyy-MM-dd HH:mm:ss"));	
			
		}
		
		//List<UserAccountDetail> uList = userAccountDetailService.getAccountDetailList(map);
		List<UserAccountDetailDTO> uList = userAccountDetailService.getAccountDetailListByHql(map);
		System.out.println("导出资金明细条数:"+uList.size());
		OutputStream out = null;
		try {
			 out = getResponse().getOutputStream();
			 getResponse().reset();//清空buffer,设置页面不缓存
			 getResponse().setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("用户资金明细"+  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xls", "UTF-8"));
			 getResponse().setContentType("application/msexcel;charset=UTF-8");
			 
			WritableWorkbook wwb = Workbook.createWorkbook(out);
			WritableSheet ws = wwb.createSheet("用户资金明细", 0);
		
			Label l= new Label(0, 0, "ID");
			ws.addCell(l);
		    l= new Label(1, 0, "userid");
			ws.addCell(l);
			l= new Label(2, 0, "用户名");
			ws.addCell(l);
			l= new Label(3, 0, "真实姓名");
			ws.addCell(l);
			l= new Label(4, 0, "操作类型");
			ws.addCell(l);
			l= new Label(5, 0, "记录时间");
			ws.addCell(l);
			l= new Label(6, 0, "总额");
			ws.addCell(l);
			l= new Label(7, 0, "操作金额");
			ws.addCell(l);
			l= new Label(8, 0, "可用余额");	
			ws.addCell(l);
			l= new Label(9, 0, "冻结金额");	
			ws.addCell(l);
			l= new Label(10, 0, "投资人待收本金");
			ws.addCell(l);
			l= new Label(11, 0, "投资人待收利息");
			ws.addCell(l);
			
			int i=1;
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(UserAccountDetailDTO u:uList){
	
					l=new Label(0,i,u.getId().toString());
					ws.addCell(l);
					l=new Label(1,i,u.getUserid().toString());
					ws.addCell(l);
					l= new Label(2, i, u.getUsername());
					ws.addCell(l);
					l= new Label(3, i, u.getRealName());
					ws.addCell(l);
	
					l= new Label(4, i,u.getTypeValue());
					ws.addCell(l);
					l= new Label(5, i,simple.format(u.getCreateDate()));
					ws.addCell(l);
					l= new Label(6, i,u.getTotal()!=null ? SettingUtil.currencyFormat(u.getTotal()) : "0.00");
					ws.addCell(l);
					l= new Label(7, i,u.getMoney()!=null ? SettingUtil.currencyFormat(u.getMoney()) : "0.00");
					ws.addCell(l);
					l= new Label(8, i, u.getUseMoney()!=null ? SettingUtil.currencyFormat(u.getUseMoney()) : "0.00");
					ws.addCell(l);
					l= new Label(9, i, u.getNoUseMoney()!=null ? SettingUtil.currencyFormat(u.getNoUseMoney()) : "0.00");
					ws.addCell(l);
					l= new Label(10, i, u.getInvestorCollectionCapital()!=null ? SettingUtil.currencyFormat(u.getInvestorCollectionCapital()) : "0.00");
					ws.addCell(l);
					l= new Label(11, i, u.getInvestorCollectionInterest()!=null ? SettingUtil.currencyFormat(u.getInvestorCollectionInterest()) : "0.00");
					ws.addCell(l);
					
					i++;
				
				
			}
			ws.setColumnView(0, 8);// 设置列宽
            ws.setColumnView(1, 8);
            ws.setColumnView(2, 15);
            ws.setColumnView(3, 10);
            ws.setColumnView(4, 15);
            ws.setColumnView(5, 20);
            ws.setColumnView(6, 17);
            ws.setColumnView(7, 17);
            ws.setColumnView(8, 17);
            ws.setColumnView(9, 17);
            ws.setColumnView(10, 17);
            ws.setColumnView(11, 17);
            ws.setRowView(0, 600);// 设置行高
            
            wwb.write();
            wwb.close();
            out.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logInfo ="导出用户资金明细";
		return null;
	}
	
	
	
	/*
	 * 导出资金分类列表
	 */
	public String execAccountTotal(){
		
		if(startDate!=null){
			if(endDate == null){
				addErrorMessages("截止时间必填!");
				return ERROR;
			}
		}

		if(endDate!=null){
			if(startDate == null){
				addErrorMessages("起始时间必填!");
				return ERROR;
			}
		}
		
		accountDetailList = userAccountDetailService.getUserAccountDetailTotalByType(type,startDate,endDate);
		if(StringUtils.isEmpty(type)){
			listingList = listingService.getChildListingBySignList("account_type");
			List<Listing> tempList = new ArrayList<Listing>();
			for(Listing l: listingList){
				for(AccountDetail a:accountDetailList){
					if(l.getKeyValue().equals(a.getType())){
						tempList.add(l);
					}
				}
			}
			listingList.removeAll(tempList);
		}
		
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dataScope="全部所有记录";//时间范围
		if(startDate!= null && endDate != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)-1);
			endDate = calendar.getTime();
			dataScope= "#自[" + simple.format(startDate) + "]至["+simple.format(endDate)+"]";
		}
		OutputStream out = null;
		try {
			 out = getResponse().getOutputStream();
			 getResponse().reset();//清空buffer,设置页面不缓存
			 getResponse().setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("资金操作类别总记录"+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".xls", "UTF-8"));
			 getResponse().setContentType("application/msexcel;charset=UTF-8");
			 
			WritableWorkbook wwb = Workbook.createWorkbook(out);
			WritableSheet ws = wwb.createSheet("资金操作类别总记录", 0);
			
			 /**
			   * 定义单元格样式
			   */
			WritableFont titleFont = new jxl.write.WritableFont(
				WritableFont.ARIAL, 13, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
			//格式化字体
		    WritableCellFormat wcf = new jxl.write.WritableCellFormat(titleFont);
				  
			Label l = new Label(0, 0, "#操作人" ,wcf);
			ws.addCell(l);
			l = new Label(0, 1, ":"+adminService.getLoginAdmin().getUsername(),wcf);
			ws.addCell(l);
			l = new Label(0, 4, dataScope,wcf);
			ws.addCell(l);
			l = new Label(0, 6, "#下载时间:" + simple.format(new Date()),wcf);
			ws.addCell(l);
			l = new Label(0, 8, "#----------------------------------------资金操作类别总记录----------------------------------------",wcf);
			ws.addCell(l);
			int i = 10;
			l = new Label(0, i, "条数",wcf);
			ws.addCell(l);
			l= new Label(1, i, "分类",wcf);
			ws.addCell(l);
			l= new Label(2, i, "标识",wcf);
			ws.addCell(l);
			l= new Label(3, i, "总额",wcf);
			ws.addCell(l);
			
			i++;
			for(AccountDetail a:accountDetailList){
				String name = listingService.findChildListingByKeyValue("account_type",a.getType());
				if(StringUtils.isNotEmpty(name)){
					l=new Label(0, i, a.getCount().toString(),wcf);
					ws.addCell(l);
					l= new Label(1, i, name,wcf);
					ws.addCell(l);
					l= new Label(2, i, a.getType(),wcf);
					ws.addCell(l);
					l= new Label(3, i, SettingUtil.currencyFormat(a.getMoney()).toString(),wcf);
					ws.addCell(l);
					i++;
				}
			}
			if(listingList!= null && listingList.size()>0 ){
				for(Listing listing:listingList){
					l=new Label(0, i,"0",wcf);
					ws.addCell(l);
					l= new Label(1, i, listingService.findChildListingByKeyValue("account_type",listing.getKeyValue()),wcf);
					ws.addCell(l);
					l= new Label(2, i, listing.getKeyValue(),wcf);
					ws.addCell(l);
					l= new Label(3, i, SettingUtil.currencyFormat(new BigDecimal(0)),wcf);
					ws.addCell(l);
					i++;
				}
			}
			l = new Label(0, i+2, "#--------------------------------------------资金操作类别总记录--------------------------------------------",wcf);
			ws.addCell(l);
			
			ws.setColumnView(0, 20);// 设置列宽
            ws.setColumnView(1, 20);
            ws.setColumnView(2, 20);
            ws.setColumnView(3, 20);
            ws.setRowView(0, 400);// 设置行高
            ws.setRowView(1, 400);
            ws.setRowView(2, 400);
            ws.setRowView(3, 400);
			
            wwb.write();
            wwb.close();
            out.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logInfo ="资金操作类别总记录";
		
		
		
		return SUCCESS;
	}
	
	public String execAccountCash(){
		if(startDate!=null){
			if(endDate == null){
				addErrorMessages("截止时间必填!");
				return ERROR;
			}
		}

		if(endDate!=null){
			if(startDate == null){
				addErrorMessages("起始时间必填!");
				return ERROR;
			}
		}
		
		String strName="";
		String typeName="";
		//accountCashList = accountCashService.getAccountCashList(accountCash, startDate, endDate);
		userAccountCashDTOList=accountCashService.getAccountCashDTOList(accountCash, startDate, endDate);
		
	
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/**if(accountCash.getUser().getTypeId()!=null){
			if(accountCash.getUser().getTypeId()==0){
				typeName="投资者";
			}else if(accountCash.getUser().getTypeId()==1){
				typeName="借款者";
			}else{
				
			}
		}
		**/
		String dataScope=typeName+"全部所有记录";//时间范围
		if(startDate!= null && endDate != null){
		
			
			dataScope= "#自[" + simple.format(startDate) + "]至["+simple.format(endDate)+"]";
		}
		OutputStream out = null;
		if(accountCash.getStatus()!=null){
			if(accountCash.getStatus()==0){
				strName="审核中";
			}else if(accountCash.getStatus()==1){
				strName="审核成功";
			}else if(accountCash.getStatus()==2){
				strName="审核失败";
			}else if(accountCash.getStatus()==3){
				strName="用户取消";
			}else if(accountCash.getStatus()==4){
				strName="处理中";
			}else{
				strName="所有的";
			}
		}
		try {
			 out = getResponse().getOutputStream();
			 getResponse().reset();//清空buffer,设置页面不缓存
			 getResponse().setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(typeName+"资金提现"+strName +"的记录"+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".xls", "UTF-8"));
			 getResponse().setContentType("application/msexcel;charset=UTF-8");
			 
			WritableWorkbook wwb = Workbook.createWorkbook(out);
			WritableSheet ws = wwb.createSheet("资金提现记录", 0);
			
			 /**
			   * 定义单元格样式
			   */
			WritableFont titleFont = new jxl.write.WritableFont(
				WritableFont.ARIAL, 13, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
			//格式化字体
		    WritableCellFormat wcf = new jxl.write.WritableCellFormat(titleFont);
				  
			Label l = new Label(0, 0, "#操作人" ,wcf);
			ws.addCell(l);
			l = new Label(0, 1, ":"+adminService.getLoginAdmin().getUsername(),wcf);
			ws.addCell(l);
			l = new Label(0, 4, dataScope,wcf);
			ws.addCell(l);
			l = new Label(0, 6, "#下载时间:" + simple.format(new Date()),wcf);
			ws.addCell(l);
			l = new Label(0, 8, "#------------------------------------------------"+typeName+"资金提现"+strName +"的记录"+userAccountCashDTOList.size()+"条------------------------------------------------",wcf);
			ws.addCell(l);
			int i = 10;
			l = new Label(0, i, "id",wcf);
			ws.addCell(l);
			l= new Label(1, i, "用户名",wcf);
			ws.addCell(l);
			l= new Label(2, i, "真实姓名",wcf);
			ws.addCell(l);
			l= new Label(3, i, "用户类型",wcf);
			ws.addCell(l);
			l= new Label(4, i, "提现账号",wcf);
			ws.addCell(l);
			l= new Label(5, i, "提现银行",wcf);
			ws.addCell(l);
			l= new Label(6, i, "支行",wcf);
			ws.addCell(l);
			l= new Label(7, i, "提现总额",wcf);
			ws.addCell(l);
			l= new Label(8, i, "到账金额",wcf);
			ws.addCell(l);
			l= new Label(9, i, "手续费",wcf);
			ws.addCell(l);
			l= new Label(10, i, "调整金额",wcf);
			ws.addCell(l);
			l= new Label(11, i, "提现时间",wcf);
			ws.addCell(l);
			l= new Label(12, i, "状态",wcf);
			ws.addCell(l);
			
			i++;
			for(UserAccountCashDTO a:userAccountCashDTOList){
				//String name = listingService.findChildListingByKeyValue("account_bank",a.getBank());
				String name=a.getBankName();
				if(a.getStatus()==0){
					strName="审核中";
				}else if(a.getStatus()==1){
					strName="审核成功";
				}else if(a.getStatus()==3){
					strName="用户取消";
				}else if(a.getStatus()==4){
					strName="处理中";
				}else{
					strName="失败";
				}
				if(StringUtils.isNotEmpty(a.getStatus().toString())){
					l=new Label(0, i, a.getId().toString(),wcf);
					ws.addCell(l);
					l= new Label(1, i,a.getUsername(),wcf);
					ws.addCell(l);
					l= new Label(2, i, a.getRealName(),wcf);
					ws.addCell(l);
					
					if(a.getTypeId()==null){
						System.out.println("============="+a.getId());
						typeName="投资者";
					}
					else if(a.getTypeId()==0){
						typeName="投资者";
					}else{
						typeName="借款者";
					}
					l= new Label(3, i, typeName,wcf);
					ws.addCell(l);
					l= new Label(4, i, a.getAccount().toString(),wcf);
					ws.addCell(l);
					l= new Label(5, i, name,wcf);
					ws.addCell(l);
					l= new Label(6, i,a.getBranch() ,wcf);
					ws.addCell(l);
					l= new Label(7, i, SettingUtil.currencyFormat(a.getTotal()).toString(),wcf);
					ws.addCell(l);
					l= new Label(8, i, SettingUtil.currencyFormat(a.getCredited()).toString(),wcf);
					ws.addCell(l);
					l= new Label(9, i, SettingUtil.currencyFormat(a.getFee()).toString(),wcf);
					ws.addCell(l);
					l= new Label(10, i, a.getChangeNum().toString(),wcf);
					ws.addCell(l);
					l= new Label(11, i, a.getCreateDate().toString(),wcf);
					ws.addCell(l);
					l= new Label(12, i,strName ,wcf);
					ws.addCell(l);
					i++;
				}
			}
			
			l = new Label(0, i+2, "#--------------------------------------------资金提现记录--------------------------------------------",wcf);
			ws.addCell(l);
			
			ws.setColumnView(0, 20);// 设置列宽
            ws.setColumnView(1, 20);
            ws.setColumnView(2, 20);
            ws.setColumnView(3, 20);
            ws.setColumnView(4, 30);
            ws.setColumnView(5, 30);// 设置列宽
            ws.setColumnView(6, 30);
            ws.setColumnView(7, 20);
            ws.setColumnView(8, 20);
            ws.setColumnView(9, 20);
            ws.setColumnView(10, 30);
            ws.setColumnView(11, 30);
            ws.setColumnView(12, 20);
            ws.setRowView(0, 400);// 设置行高
            ws.setRowView(1, 400);
            ws.setRowView(2, 400);
            ws.setRowView(3, 400);
            ws.setRowView(4, 400);// 设置行高
            ws.setRowView(5, 400);
            ws.setRowView(6, 400);
            ws.setRowView(7, 400);
            ws.setRowView(8, 400);// 设置行高
            ws.setRowView(9, 400);
            ws.setRowView(10, 400);
            ws.setRowView(11, 400);
            ws.setRowView(12, 400);
			
            wwb.write();
            wwb.close();
            out.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logInfo ="资金提现记录";
		
		
		
		return null;
	}
	
	/**
	 * 导出投资者待收记录
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String exceUserRepermentDetail() throws UnsupportedEncodingException{
		Map<String,Object> map = new HashMap<String, Object>();
		if(startDate != null) {
			map.put("startDate",  CommonUtil.date2begin(startDate));
		}
		
		if(endDate != null) {
			map.put("endDate",  CommonUtil.date2end(endDate));
		}
		
		if(pager != null&&"user.username".equals( pager.getSearchBy()) ){
			if(StringUtils.isNotEmpty(pager.getKeyword())){
				map.put("username", URLDecoder.decode(pager.getKeyword(), "utf-8"));
			}
		}
		if(pager != null&&"borrow.name".equals(pager.getSearchBy()) ){
			if(StringUtils.isNotEmpty(pager.getKeyword())){
				map.put("borrowName", URLDecoder.decode(pager.getKeyword(), "utf-8"));
			}
		}
		List<UserRepaymentDetailDTO> dlist = null;
		try{
			//dlist = userRepaymentDetailService.findUserRepaymentDetailList(map);
			dlist=(List<UserRepaymentDetailDTO>) userRepaymentDetailService.findUserRepaymentDetailPagerByY(null, map).getResult();
			System.out.println(dlist.size());	
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		OutputStream out = null;
		try {
			 out = getResponse().getOutputStream();
			 getResponse().reset();//清空buffer,设置页面不缓存
			 getResponse().setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("UserRepaymentDetail"+  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xls", "UTF-8"));
			 getResponse().setContentType("application/msexcel;charset=UTF-8");
			 
			WritableWorkbook wwb = Workbook.createWorkbook(out);
			WritableSheet ws = wwb.createSheet("用户待收明细", 0);
		
			Label l= new Label(0, 0, "ID");
			ws.addCell(l);
			l= new Label(1, 0, "用户名");
			ws.addCell(l);
			//l= new Label(1, 0, "姓名");
			//ws.addCell(l);
			//l= new Label(3, 0, "手机号码");
			//ws.addCell(l);
			l= new Label(2, 0, "标名");
			ws.addCell(l);
			l= new Label(3, 0, "项目期限");
			ws.addCell(l);
			l= new Label(4, 0, "期数");
			ws.addCell(l);
			l= new Label(5, 0, "应还时间");
			ws.addCell(l);
			l= new Label(6, 0, "应还金额");
			ws.addCell(l);
			l= new Label(7, 0, "渠道来源");
			ws.addCell(l);
			//l= new Label(9, 0, "续投状态");
			//ws.addCell(l);
			l= new Label(8, 0, "还款状态");
			ws.addCell(l);
			
			int i=1;
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			for(UserRepaymentDetailDTO d:dlist){
				//User user = d.getUser();
				//Borrow borrow = d.getBorrow();
				if(d!= null){
					l=new Label(0,i,String.valueOf(d.getId()));
					ws.addCell(l);//ID
					l= new Label(1, i, d.getUsername());
					ws.addCell(l);//用户名
					//l= new Label(2, i, user.getRealName());
					//ws.addCell(l);//姓名
				    //l= new Label(3, i, user.getPhone());
					//ws.addCell(l);//手机
					l= new Label(2, i, d.getBorrowName());
					ws.addCell(l);//标名
					l= new Label(3, i, d.getTimeLimit());
					ws.addCell(l);//项目期限
					l= new Label(4, i, d.getBorrowPeriods()+"/"+d.getBorrowDivides());
					ws.addCell(l);//期数
				
					l= new Label(5, i, simple.format(d.getRepaymentDate()));
					ws.addCell(l);//应还时间
					l= new Label(6, i, d.getRepaymentAccount() );
					ws.addCell(l);//应还金额
					l= new Label(7, i, d.getPlaceName());
					ws.addCell(l);//渠道来源
					//l= new Label(9, i, "1".equals(d.getApplyContinueTotal())?"续投":"关闭" );
					//ws.addCell(l);//续投状态
					l= new Label(8, i, "1".equals(d.getStatus())?"已还":"未还" );
					ws.addCell(l);//续投状态
					i++;
				}
				
			}
			ws.setColumnView(0, 8);// 设置列宽
            ws.setColumnView(1, 15);
            ws.setColumnView(2, 20);
            ws.setColumnView(3, 15);
            ws.setColumnView(4, 20);
            ws.setColumnView(5, 17);
            ws.setColumnView(6, 17);
            ws.setColumnView(7, 17);
            ws.setColumnView(8, 17);
            ws.setColumnView(9, 17);
            ws.setColumnView(10, 17);
            ws.setRowView(0, 600);// 设置行高
            
            wwb.write();
            wwb.close();
            out.close();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logInfo ="导出用户待收明细";
		return null;
	}
	
	/**
	 * 导出所有用户账户数据
	 * @return
	 */
	public String exceCensusUserList(){
		if(endDate == null) {
			addErrorMessages("请输入日期!");
			return ERROR;
		}
		if (username!=null) {
			username = username.trim();
			try {
				username = URLDecoder.decode(username,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<CensusUserListBean> dataList = new ArrayList<CensusUserListBean>();
		
		dataList = userService.queryCensusUserList(endDate,username);
		OutputStream out = null;
		try {
			 out = getResponse().getOutputStream();
			 getResponse().reset();//清空buffer,设置页面不缓存
			 getResponse().setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("每日资金核对表"+  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xls", "UTF-8"));
			 getResponse().setContentType("application/msexcel;charset=UTF-8");
			 
			WritableWorkbook wwb = Workbook.createWorkbook(out);
			WritableSheet ws = wwb.createSheet("每日资金核对表", 0);
		
			Label l= new Label(0, 0, "用户ID");
			ws.addCell(l);
			l= new Label(1, 0, "用户名");
			ws.addCell(l);
			l= new Label(2, 0, "姓名");
			ws.addCell(l);
			l= new Label(3, 0, "总金额");
			ws.addCell(l);
			l= new Label(4, 0, "资金转入");
			ws.addCell(l);
			l= new Label(5, 0, "累计充值");
			ws.addCell(l);
			l= new Label(6, 0, "已收净利息");
			ws.addCell(l);
			l= new Label(7, 0, "累计奖励");
			ws.addCell(l);
			l= new Label(8, 0, "提现总额");
			ws.addCell(l);
			l= new Label(9, 0, "扣费总额");
			ws.addCell(l);
			l= new Label(10, 0, "验算金额");
			ws.addCell(l);
			
			int i=1;
			for(CensusUserListBean item:dataList){
				l=new Label(0,i,item.getUserId() == null ? "":item.getUserId().toString() );
				ws.addCell(l);
				l= new Label(1, i, item.getUsername()==null ?"": item.getUsername());
				ws.addCell(l);
				l= new Label(2, i, item.getRealName()==null ? "":item.getRealName());
				ws.addCell(l);
				l= new Label(3, i, item.getTotal0()==null?"0.00":SettingUtil.currencyFormat(item.getTotal0()));
				ws.addCell(l);
				l= new Label(4, i, item.getSuma11()==null?"0.00":SettingUtil.currencyFormat(item.getSuma11()));
				ws.addCell(l);
				l= new Label(5, i, item.getSuma2()==null?"0.00":SettingUtil.currencyFormat(item.getSuma2()));
				ws.addCell(l);
				l= new Label(6, i, item.getSuma3()==null?"0.00":SettingUtil.currencyFormat(item.getSuma3()));
				ws.addCell(l);
				l= new Label(7, i, item.getSuma4()==null?"0.00":SettingUtil.currencyFormat(item.getSuma4()));
				ws.addCell(l);
				l= new Label(8, i, item.getSumb21()==null?"0.00":SettingUtil.currencyFormat(item.getSumb21()));
				ws.addCell(l);
				l= new Label(9, i, item.getSumb41()==null?"0.00":SettingUtil.currencyFormat(item.getSumb41()));
				ws.addCell(l);
				l= new Label(10, i, item.getSum0()==null?"0.00":SettingUtil.currencyFormat(item.getSum0()));
				ws.addCell(l);
				i++;
			}
			ws.setColumnView(0, 8);// 设置列宽
            ws.setColumnView(1, 10);
            ws.setColumnView(2, 10);
            ws.setColumnView(3, 15);
            ws.setColumnView(4, 15);
            ws.setColumnView(5, 15);
            ws.setColumnView(6, 15);
            ws.setColumnView(7, 15);
            ws.setColumnView(8, 15);
            ws.setColumnView(9, 15);
            ws.setColumnView(10, 15);
         
            ws.setRowView(0, 600);// 设置行高
            
            wwb.write();
            wwb.close();
            out.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logInfo ="导出每日资金核对表";
		return SUCCESS;
	}
	
	/**
	 * 导出所有用户账户数据
	 * @return
	 */
	public String exceCensusUserDetail(){
		if(startDate == null) {
			addErrorMessages("请输入开始日期!");
			return ERROR;
		}
		if(endDate == null) {
			addErrorMessages("请输入结束日期!");
			return ERROR;
		}
		
		if (username!=null) {
			username = username.trim();
			try {
				username = URLDecoder.decode(username,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isEmpty(username)) {
			addErrorMessages("请输入用户名!");
			return ERROR;
		}
		
		Integer dateIntBegin = CommonUtil.getIntDate(startDate);
		Integer dateIntEnd = CommonUtil.getIntDate(endDate);
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<CensusUserListBean> dataList = new ArrayList<CensusUserListBean>();
		
		dataList = reportUserAccountBackupService.queryCensusUserDetail(dateIntBegin, dateIntEnd, username);
		OutputStream out = null;
		try {
			 out = getResponse().getOutputStream();
			 getResponse().reset();//清空buffer,设置页面不缓存
			 getResponse().setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("用户资金核对表"+  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xls", "UTF-8"));
			 getResponse().setContentType("application/msexcel;charset=UTF-8");
			 
			WritableWorkbook wwb = Workbook.createWorkbook(out);
			WritableSheet ws = wwb.createSheet("用户资金核对表", 0);
		
			Label l= new Label(0, 0, "日期");
			ws.addCell(l);
			l= new Label(1, 0, "总金额");
			ws.addCell(l);
			l= new Label(2, 0, "资金转入");
			ws.addCell(l);
			l= new Label(3, 0, "累计充值");
			ws.addCell(l);
			l= new Label(4, 0, "已收净利息");
			ws.addCell(l);
			l= new Label(5, 0, "累计奖励");
			ws.addCell(l);
			l= new Label(6, 0, "提现总额");
			ws.addCell(l);
			l= new Label(7, 0, "扣费总额");
			ws.addCell(l);
			l= new Label(8, 0, "验算金额");
			ws.addCell(l);
			
			int i=1;
			for(CensusUserListBean item:dataList){
				l=new Label(0,i,item.getDailyWorkDateInt() == null ? "":item.getUserId().toString() );
				ws.addCell(l);
				l= new Label(1, i, item.getTotal0()==null?"0.00":SettingUtil.currencyFormat(item.getTotal0()));
				ws.addCell(l);
				l= new Label(2, i, item.getSuma11()==null?"0.00":SettingUtil.currencyFormat(item.getSuma11()));
				ws.addCell(l);
				l= new Label(3, i, item.getSuma2()==null?"0.00":SettingUtil.currencyFormat(item.getSuma2()));
				ws.addCell(l);
				l= new Label(4, i, item.getSuma3()==null?"0.00":SettingUtil.currencyFormat(item.getSuma3()));
				ws.addCell(l);
				l= new Label(5, i, item.getSuma4()==null?"0.00":SettingUtil.currencyFormat(item.getSuma4()));
				ws.addCell(l);
				l= new Label(6, i, item.getSumb21()==null?"0.00":SettingUtil.currencyFormat(item.getSumb21()));
				ws.addCell(l);
				l= new Label(7, i, item.getSumb41()==null?"0.00":SettingUtil.currencyFormat(item.getSumb41()));
				ws.addCell(l);
				l= new Label(8, i, item.getSum0()==null?"0.00":SettingUtil.currencyFormat(item.getSum0()));
				ws.addCell(l);
				i++;
			}
			ws.setColumnView(0, 8);// 设置列宽
            ws.setColumnView(1, 15);
            ws.setColumnView(2, 15);
            ws.setColumnView(3, 15);
            ws.setColumnView(4, 15);
            ws.setColumnView(5, 15);
            ws.setColumnView(6, 15);
            ws.setColumnView(7, 15);
            ws.setColumnView(8, 15);
         
            ws.setRowView(0, 600);// 设置行高
            
            wwb.write();
            wwb.close();
            out.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logInfo ="导出用户资金核对表";
		return SUCCESS;
	}
	
	
	
	/**
	 * 导出所有用户账户数据
	 * @return
	 */
	public String exceUserList(){
		Map<String,Object> map = new HashMap<String, Object>();
		if(startDate == null) {
			addErrorMessages("请输入开始日期!");
			return ERROR;
		}else{
			map.put("beginDate", CommonUtil.getDate2String(CommonUtil.date2begin(startDate), "yyyy-MM-dd HH:mm:ss"));
		}
		if(endDate == null) {
			addErrorMessages("请输入结束日期!");
			return ERROR;
		}else{
			map.put("endDate", CommonUtil.getDate2String(CommonUtil.date2end(endDate), "yyyy-MM-dd HH:mm:ss"));
		}
		
		if (username!=null) {
			username = username.trim();
			try {
				username = URLDecoder.decode(username,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(username)) {
			map.put("username", username);
		}
		
		String adminPlaceName=adminService.getLoginAdmin().getUsername();
		
		String placeNameOn=listingService.findChildListingByKeyValue("place_admin", adminPlaceName);
		if(StringUtils.isNotBlank(placeNameOn)){
			map.put("placeChilderName", placeNameOn);
		}
		
		
		/**
		if("yufeng".equals(adminPlaceName)){
			map.put("placeChilderName", "浴风");
		}

		**/
		

		
		List<UserListBean> dataList = new ArrayList<UserListBean>();
		
		dataList = userService.getListByHsql(map);
		OutputStream out = null;
		try {
			 out = getResponse().getOutputStream();
			 getResponse().reset();//清空buffer,设置页面不缓存
			 getResponse().setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("用户数据表"+  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xls", "UTF-8"));
			 getResponse().setContentType("application/msexcel;charset=UTF-8");
			 
			WritableWorkbook wwb = Workbook.createWorkbook(out);
			WritableSheet ws = wwb.createSheet("用户数据表", 0);
		
			Label l= new Label(0, 0, "ID");
			ws.addCell(l);
			l= new Label(1, 0, "注册时间");
			ws.addCell(l);
			l= new Label(2, 0, "会员类型/属性");
			ws.addCell(l);
			l= new Label(3, 0, "用户名");
			ws.addCell(l);
			l= new Label(4, 0, "姓名");
			ws.addCell(l);

			l= new Label(5, 0, "实名状态");
			ws.addCell(l);
			l= new Label(6, 0, "渠道名称");
			ws.addCell(l);
			l= new Label(7, 0, "注册来源");
			ws.addCell(l);
			
			int i=1;
			for(UserListBean item:dataList){
				l=new Label(0,i,item.getId().toString() );
				ws.addCell(l);
				
				l= new Label(1, i, CommonUtil.getDate2String(item.getCreateDate(), "yyyy-MM-dd HH:mm:ss") );
				ws.addCell(l);
				
				
				String memberTypeValue="";
				String typeValue="";
			
				if(item.getMemberType()==0){
					memberTypeValue="个人";
				}else if(item.getMemberType()==1){
					memberTypeValue="企业";
				}else{
					
				}
				if(item.getTypeId()==0){
					typeValue="投资人";
				}else if(item.getTypeId()==1){
					typeValue="借款人";
				}else if(item.getTypeId()==3){
					typeValue="服务商";
				}
				else{
					
				}
		
				l= new Label(2, i, memberTypeValue+"/"+typeValue);
				ws.addCell(l);
				l= new Label(3, i, item.getUsername()==null?"":item.getUsername());
				ws.addCell(l);
				l= new Label(4, i, item.getRealName()==null?"":item.getRealName());
				ws.addCell(l);
				
				
				l= new Label(5, i, item.getRealStatusStr());
				ws.addCell(l);
				l= new Label(6, i, item.getPlaceName());
				ws.addCell(l);

				if("0".equals(item.getAppType())){
					l= new Label(7, i, "ios");
				}else if("1".equals(item.getAppType())){
					l= new Label(7, i, "安卓");
				}else{
					l= new Label(7, i, "pc或其他");
				}

				ws.addCell(l);
				i++;
			}
			ws.setColumnView(0, 8);// 设置列宽
            ws.setColumnView(1, 15);
            ws.setColumnView(2, 15);
            ws.setColumnView(3, 15);
            ws.setColumnView(4, 15);
            ws.setColumnView(5, 15);
         
            ws.setRowView(0, 600);// 设置行高
            
            wwb.write();
            wwb.close();
            out.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	

	
	/**
	 * 导出所有用户首次数据
	 * @return
	 */
	public String exceFirstTenderList(){
		Map<String,Object> map = new HashMap<String, Object>();
		if(startDate == null) {
			addErrorMessages("请输入开始日期!");
			return ERROR;
		}else{
			map.put("beginDate", CommonUtil.getDate2String(CommonUtil.date2begin(startDate), "yyyy-MM-dd HH:mm:ss"));
		}
		if(endDate == null) {
			addErrorMessages("请输入结束日期!");
			return ERROR;
		}else{
			map.put("endDate", CommonUtil.getDate2String(CommonUtil.date2end(endDate), "yyyy-MM-dd HH:mm:ss"));
		}
		
		if (username!=null) {
			username = username.trim();
			try {
				username = URLDecoder.decode(username,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
	

		if(StringUtils.isNotEmpty(username)) {
			map.put("placeChilderName", username);
		}
		
		
		String adminPlaceName=adminService.getLoginAdmin().getUsername();
		
		
		String placeNameOn=listingService.findChildListingByKeyValue("place_admin", adminPlaceName);
		if(StringUtils.isNotBlank(placeNameOn)){
			map.put("placeChilderName", placeNameOn);
		}
		/**
		if("yufeng".equals(adminPlaceName)){
			map.put("placeChilderName", "浴风");
		}
		if("miaodian".equals(adminPlaceName)){
			map.put("placeChilderName", "秒点");
		}
		if("qutou".equals(adminPlaceName)){
			map.put("placeChilderName", "趣投");
		}
		if("guanghui".equals(adminPlaceName)){
			map.put("placeChilderName", "光辉");
		}
		if("fuqun".equals(adminPlaceName)){
			map.put("placeChilderName", "富群");
		}
		**/

		
		
		List<ReportFirstTender> dataList = new ArrayList<ReportFirstTender>();
		
		dataList = reportFirstTenderService.getListByHsql(map);
		OutputStream out = null;
		try {
			 out = getResponse().getOutputStream();
			 getResponse().reset();//清空buffer,设置页面不缓存
			 getResponse().setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("首次投资"+  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xls", "UTF-8"));
			 getResponse().setContentType("application/msexcel;charset=UTF-8");
			 
			WritableWorkbook wwb = Workbook.createWorkbook(out);
			WritableSheet ws = wwb.createSheet("首次投资报表", 0);
		
			Label l= new Label(0, 0, "userid");
			ws.addCell(l);
			l= new Label(1, 0, "手机号");
			ws.addCell(l);
			l= new Label(2, 0, "投资时间");
			ws.addCell(l);
			l= new Label(3, 0, "实际投资金额");
			ws.addCell(l);
			l= new Label(4, 0, "使用红包总额");
			ws.addCell(l);
			l= new Label(5, 0, "项目名称");
			ws.addCell(l);
			l= new Label(6, 0, "项目期限");
			ws.addCell(l);
			l= new Label(7, 0, "渠道来源");
			ws.addCell(l);
			l= new Label(8, 0, "来源");
			ws.addCell(l);
			
			int i=1;
			for(ReportFirstTender item:dataList){
				l=new Label(0,i,item.getUserId().toString() );
				ws.addCell(l);
				l= new Label(1, i, item.getPhone()==null?"":item.getPhone());
				ws.addCell(l);
				l= new Label(2, i, CommonUtil.getDate2String(item.getTenderDate(), "yyyy-MM-dd HH:mm:ss") );
				ws.addCell(l);
				l= new Label(3, i, item.getMoney()==null?"0":item.getMoney().toString());
				ws.addCell(l);
				l= new Label(4, i, item.getHongbaoAmount());
				ws.addCell(l);
				l= new Label(5, i, item.getBorrowName());
				ws.addCell(l);
				l= new Label(6, i, item.getTimeLimit()+"天");
				ws.addCell(l);
				l= new Label(7, i, item.getPlaceChilderName());
				ws.addCell(l);
				String clientTypeValue="";
				
				switch(item.getClientType())
				{
				case 0:clientTypeValue="pc";break;
				case 1:clientTypeValue="安卓";break;
				case 2: clientTypeValue="ios";break;
				case 3: clientTypeValue="H5";break;
				case 5: clientTypeValue="新H5";break;
				default:clientTypeValue="";
				}
				l= new Label(8, i, clientTypeValue);
				ws.addCell(l);
				i++;
			}
			ws.setColumnView(0, 8);// 设置列宽
            ws.setColumnView(1, 15);
            ws.setColumnView(2, 15);
            ws.setColumnView(3, 15);
            ws.setColumnView(4, 15);
            ws.setColumnView(5, 15);
            ws.setColumnView(6, 15);
            ws.setColumnView(7, 15);
            ws.setColumnView(8, 10);
         
            ws.setRowView(0, 600);// 设置行高
            
            wwb.write();
            wwb.close();
            out.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	private String placeChilderName;

	/**
	 * 导出所有用户投资数据
	 * @return
	 */
	public String exceBorrowTenderList(){
		Map<String,Object> map = new HashMap<String, Object>();
		if(startDate == null) {
			addErrorMessages("请输入开始日期!");
			return ERROR;
		}else{
			map.put("beginDate", CommonUtil.getDate2String(CommonUtil.date2begin(startDate), "yyyy-MM-dd HH:mm:ss"));
		}
		if(endDate == null) {
			addErrorMessages("请输入结束日期!");
			return ERROR;
		}else{
			map.put("endDate", CommonUtil.getDate2String(CommonUtil.date2end(endDate), "yyyy-MM-dd HH:mm:ss"));
		}
		
		if (username!=null) {
			username = username.trim();
			try {
				username = URLDecoder.decode(username,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(username)) {
			map.put("username", username);
		}

		if (placeChilderName!=null) {
			placeChilderName = placeChilderName.trim();
			try {
				placeChilderName = new String(placeChilderName.getBytes("ISO-8859-1"), "UTF-8");// URLDecoder.decode(placeChilderName,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(placeChilderName)) {
			map.put("placeChilderName", placeChilderName);
		}
		
		String adminPlaceName=adminService.getLoginAdmin().getUsername();
		String placeNameOn=listingService.findChildListingByKeyValue("place_admin", adminPlaceName);
		if(StringUtils.isNotBlank(placeNameOn)){
			map.put("placeChilderName", placeNameOn);
		}
		/**
		if("yufeng".equals(adminPlaceName)){
			map.put("placeChilderName", "浴风");
		}
		if("miaodian".equals(adminPlaceName)){
			map.put("placeChilderName", "秒点");
		}
		if("qutou".equals(adminPlaceName)){
			map.put("placeChilderName", "趣投");
		}
		if("guanghui".equals(adminPlaceName)){
			map.put("placeChilderName", "光辉");
		}
		if("fuqun".equals(adminPlaceName)){
			map.put("placeChilderName", "富群");
		}
		**/
		List<BorrowTenderItem> dataList = new ArrayList<BorrowTenderItem>();
		
		dataList = reportFirstTenderService.getBorrowTenderByHsql(map);
		OutputStream out = null;
		try {
			 out = getResponse().getOutputStream();
			 getResponse().reset();//清空buffer,设置页面不缓存
			 getResponse().setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("投资记录"+  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xls", "UTF-8"));
			 getResponse().setContentType("application/msexcel;charset=UTF-8");
			 
			WritableWorkbook wwb = Workbook.createWorkbook(out);
			WritableSheet ws = wwb.createSheet("投资记录报表", 0);
		
			Label l= new Label(0, 0, "用户名");
			ws.addCell(l);
			l= new Label(1, 0, "姓名");
			ws.addCell(l);
			l= new Label(2, 0, "投资时间");
			ws.addCell(l);
			l= new Label(3, 0, "实际投资金额");
			ws.addCell(l);
			l= new Label(4, 0, "项目红包使用金额");
			ws.addCell(l);
			l= new Label(5, 0, "投资项目");
			ws.addCell(l);
			l= new Label(6, 0, "项目期限");
			ws.addCell(l);
			l= new Label(7, 0, "渠道注册来源");
			ws.addCell(l);
			l= new Label(8, 0, "投资来源");
			ws.addCell(l);
			
			int i=1;
			for(BorrowTenderItem item:dataList){
				l=new Label(0,i,item.getUsername() );
				ws.addCell(l);
				l= new Label(1, i, item.getRealname());
				ws.addCell(l);
				l= new Label(2, i, CommonUtil.getDate2String(item.getCreateDate(), "yyyy-MM-dd HH:mm:ss") );
				ws.addCell(l);
				l= new Label(3, i, item.getAccount());
				ws.addCell(l);
				l= new Label(4, i, item.getHongbaoAmount());
				ws.addCell(l);
				l= new Label(5, i, item.getBorrowName());
				ws.addCell(l);
				l= new Label(6, i, item.getTimeLimit());
				ws.addCell(l);
				l= new Label(7, i, item.getPlaceChilderName());
				ws.addCell(l);
				

				String StatusVal="";
					switch(item.getClientType()) {  
					    case 0: StatusVal="pc";break;  
					    case 1: StatusVal="安卓";break;
					    case 2: StatusVal="ios";break;
					    case 3: StatusVal="H5";break;
					    default: StatusVal="新h5";break;  
					}
				
				l= new Label(8, i, StatusVal);
				
				
			
				ws.addCell(l);
				i++;
			}
			ws.setColumnView(0, 15);// 设置列宽
            ws.setColumnView(1, 10);
            ws.setColumnView(2, 20);
            ws.setColumnView(3, 15);
            ws.setColumnView(4, 15);
            ws.setColumnView(5, 15);
            ws.setColumnView(6, 20);
            ws.setColumnView(7, 10);
            ws.setColumnView(8, 15);
            ws.setRowView(0, 600);// 设置行高
            
            wwb.write();
            wwb.close();
            out.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<AccountDetail> getAccountDetailList() {
		return accountDetailList;
	}

	public void setAccountDetailList(List<AccountDetail> accountDetailList) {
		this.accountDetailList = accountDetailList;
	}

	public List<Listing> getListingList() {
		return listingList;
	}

	public void setListingList(List<Listing> listingList) {
		this.listingList = listingList;
	}

	public List<AccountCash> getAccountCashList() {
		return accountCashList;
	}

	public void setAccountCashList(List<AccountCash> accountCashList) {
		this.accountCashList = accountCashList;
	}

	public AccountCash getAccountCash() {
		return accountCash;
	}

	public void setAccountCash(AccountCash accountCash) {
		this.accountCash = accountCash;
	}

	public Integer getRechargeInterfaceId() {
		return rechargeInterfaceId;
	}

	public void setRechargeInterfaceId(Integer rechargeInterfaceId) {
		this.rechargeInterfaceId = rechargeInterfaceId;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMoneygt() {
		return moneygt;
	}

	public void setMoneygt(String moneygt) {
		this.moneygt = moneygt;
	}

	public String getMoneylt() {
		return moneylt;
	}

	public void setMoneylt(String moneylt) {
		this.moneylt = moneylt;
	}

	public Boolean getIsExact() {
		return isExact;
	}

	public void setIsExact(Boolean isExact) {
		this.isExact = isExact;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPlaceChilderName() {
		return placeChilderName;
	}

	public void setPlaceChilderName(String placeChilderName) {
		this.placeChilderName = placeChilderName;
	}
	
}
