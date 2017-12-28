package net.qmdboss.action.admin;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.qmdboss.bean.ComparatorUserRecharge;
import net.qmdboss.bean.QueryDetailsBaofoo;
import net.qmdboss.bean.QueryDetailsGfb;
import net.qmdboss.bean.QueryDetailsXszf;
import net.qmdboss.entity.RechargeConfig;
import net.qmdboss.entity.SourceRecord;
import net.qmdboss.entity.UserAccountRecharge;
import net.qmdboss.payment.BaofooUtil;
import net.qmdboss.payment.GopayUtils;
import net.qmdboss.payment.HnapayUtils;
import net.qmdboss.service.RechargeConfigService;
import net.qmdboss.service.SourceRecordService;
import net.qmdboss.service.UserAccountRechargeService;
import net.qmdboss.util.ImageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@ParentPackage("admin")
public class SourceRecordAction extends BaseAdminAction{

	private static final long serialVersionUID = 9051999755781110207L;

	private String paymentProductId;
	private SourceRecord sourceRecord;
	private File file;
	private List<SourceRecord> sourceRecordList;
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private String dateType;//提交时间【0】还是完成时间【1】
	private List<UserAccountRecharge> userAccountRechargeList;//充值记录
	private Integer status;//处理状态
	
	@Resource(name = "rechargeConfigServiceImpl")
	private RechargeConfigService rechargeConfigService;
	@Resource(name = "sourceRecordServiceImpl")
	private SourceRecordService sourceRecordService;
	@Resource(name = "userAccountRechargeServiceImpl")
	private UserAccountRechargeService userAccountRechargeService;
	
	//所有充值記錄列表
	public String list(){
		pager = sourceRecordService.findPager(pager);
		return LIST;
	}
	//
	public String add(){
		return INPUT;
	}
	
	
	//导入充值记录数据
	public String save() throws BiffException, IOException, ParseException{
		String uploadPath =ImageUtil.copyFile(getServletContext(), file);
		InputStream is = new FileInputStream(ImageUtil.IMG_DIR + uploadPath);
		Workbook rwb = Workbook.getWorkbook(is);
		Sheet st = rwb.getSheet(0);
		Cell c00 = st.getCell(0, 0);
		System.out.println(st.getRows()+"行");
		System.out.println(st.getColumns()+"列");
		List<SourceRecord> sourceRecordList = new ArrayList<SourceRecord>(); 
//		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		RechargeConfig r = rechargeConfigService.get(sourceRecord.getRechargeConfig().getId());
		try{
			if(r.getName().equals("国付宝")){
				sourceRecordList = sourceRecordService.getGfbRecord(sourceRecord, st);
			}else if(r.getName().equals("新生支付")){
				sourceRecordList = sourceRecordService.getGfbRecord(sourceRecord, st);
			}else if(r.getName().equals("宝付")){
				sourceRecordList = sourceRecordService.getBaofooRecord(sourceRecord, st);
			}
		}catch (Exception e) {
			addActionError("导入文件跟接口不匹配!");
			return ERROR;
		}
//		for(int i=1,j=st.getRows();i<j;i++){
//			for(int m=0,n=st.getColumns();m<n;m++){
//				LabelCell l =(LabelCell) (st.getCell(m, i));
//				System.out.println(i+"行="+l.getString());
//			}
//			SourceRecord s = new SourceRecord();
//			s.setRechargeConfig(sourceRecord.getRechargeConfig());
//			s.setAddDate(simple.parse(st.getCell(0, i).getContents()));
//			if(StringUtils.isNotEmpty(st.getCell(1, i).getContents())){
//				s.setCompletionDate(simple.parse(st.getCell(1, i).getContents()));
//			}
//			s.setTransactionNumber(st.getCell(2, i).getContents());
//			s.setTradeState(st.getCell(3, i).getContents());
//			s.setTreatmentState(st.getCell(4, i).getContents());
//			s.setTradeType(st.getCell(5, i).getContents());
//			s.setBalanceType(st.getCell(6, i).getContents());
//			s.setPayment(st.getCell(7, i).getContents());
//			s.setTradingFloor(st.getCell(8, i).getContents());
//			s.setCounterparty(st.getCell(9, i).getContents());
//			s.setAssociatedBank(st.getCell(10, i).getContents());
//			s.setBankOrderNumber(st.getCell(11, i).getContents());
//			s.setOrderNumber(st.getCell(12, i).getContents());
//			s.setActualAmount(BigDecimal.valueOf(Double.valueOf(st.getCell(13, i).getContents())));
//			s.setTransactionAmount(BigDecimal.valueOf(Double.valueOf(st.getCell(14, i).getContents())));
//			s.setHandlingCharge(BigDecimal.valueOf(Double.valueOf(st.getCell(15, i).getContents())));
//			s.setRemark(st.getCell(16, i).getContents());
//			
//			sourceRecordList.add(s);
//		}
		rwb.close();
		//验证导入记录是否重复
		int i=2;
		for(SourceRecord s:sourceRecordList){System.out.println(s.getOrderNumber());
			if(sourceRecordService.getSourceRecord(s.getOrderNumber())!=null){
				addActionError("第"+i+"行出现错误,订单号跟之前数据重复，请认真核对!");
				return ERROR;
			}
			i++;
		}
		
		sourceRecordService.saveLosts(sourceRecordList);
//		sourceRecordService.save(sourceRecordList.get(0));
		logInfo = "导入数据";
//		redirectUrl="source_record!list.action";
		return SUCCESS;
	}
	
	//跳转到数据比对页面
	public String dataComparison(){
		sourceRecordList = new ArrayList<SourceRecord>();
		return "comparison";
	}

	//执行数据比对功能
	public String doComparison(){
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
		Map<String,String> map = new HashMap<String,String>();
		map.put("dateType", dateType);
		map.put("startDate",startDate != null ? dateformat.format(startDate):null);
		map.put("endDate",endDate != null ? dateformat.format(endDate): null);
		map.put("rechargeConfigId",sourceRecord!= null && sourceRecord.getRechargeConfig().getId()!=null ? sourceRecord.getRechargeConfig().getId().toString():null);
		map.put("tradeState", sourceRecord!= null ? sourceRecord.getTradeState():null);//交易状态
		map.put("orderNumber",sourceRecord!= null ?  sourceRecord.getOrderNumber():null);//订单号
		
		try{
			List<SourceRecord> sList = sourceRecordService.getSourceRecordList(map);
			List<SourceRecord> sListTemp = new ArrayList<SourceRecord>();
			sourceRecordList = new ArrayList<SourceRecord>();
			for(SourceRecord s:sList){
				UserAccountRecharge recharge = userAccountRechargeService.getUserAccountRecharge(s.getOrderNumber());
				if(recharge!=null){
					if(!s.getTradeState().equals(recharge.getStatus().toString())){
						if(s.getTransactionAmount().compareTo(recharge.getMoney())==0){
							s.setMoneyState(1);
						}else{
							s.setMoneyState(0);
						}
						s.setComparisonState(0);
						sourceRecordList.add(s);
					}else{
						if(s.getTransactionAmount().compareTo(recharge.getMoney())==0){
							s.setMoneyState(1);
							s.setComparisonState(1);
							sListTemp.add(s);
						}else{
							s.setMoneyState(0);
							s.setComparisonState(1);
							sourceRecordList.add(s);
						}
					}
				}else{
					s.setMoneyState(0);
					s.setComparisonState(2);
					sourceRecordList.add(s);
				}
			}
			sourceRecordList.addAll(sListTemp);
		}catch (Exception e) {
			addActionError("数据量太大，请选择时间段后比对!");
			return ERROR;
		}
		return "comparison";
	}
	
	
	/**
	 * 跳转到【新生支付】批量查询  充值记录
	 * @return
	 */
	public String toSource(){
		RechargeConfig r = rechargeConfigService.get(id);
		if(r.getName().equals("国付宝")){
			return "gfb";
		}else if(r.getName().equals("新生支付")){
			return "xszf";
		}else if(r.getName().equals("宝付")){
			return "baofoo";
		}else{
			addActionError("参数错误!");
			return ERROR;
		}
	}
	
	/**
	 * 执行批量查询【新生支付】数据
	 * @return
	 */
	public String doSourceXszf(){
		StringBuffer logInfoStringBuffer = new StringBuffer("批量查询\"新生\"接口数据; ");
		if(getSession("rechargeSession")!=null){
			removeSession("rechargeSession");
		}
		
		Map<String ,String> map = new HashMap<String,String>();
		if(startDate!= null && endDate!= null){//订单提交时间的范围
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)-1);
			endDate = calendar.getTime();
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddHHmmss");
			if(startDate.after(endDate)){
				addActionError("开始时间不能晚于结束时间!");
				return ERROR;
			}
			if(endDate.after(new Date())){
				addActionError("结束时间至少是当前时间的前一天!");
				return ERROR;
			}
			map.put("beginTime", dateformat.format(startDate));
			map.put("endTime", dateformat.format(endDate));
			logInfoStringBuffer.append("开始时间").append(dateformat.format(startDate)).append("结束时间").append(dateformat.format(endDate)+";");
		}else{
			addActionError("请选择时间范围!");
			return ERROR;
		}
		RechargeConfig rechargeConfig = rechargeConfigService.get(id);
		if(rechargeConfig == null){
			addActionError("充值方式不存在!");
			return ERROR;
		}
		HnapayUtils xszf = new HnapayUtils();
		String str = xszf.getExamineVerify(rechargeConfig, null, map);
		if(StringUtils.isEmpty(str)){
			addActionError("连接超时或接口问题");
			return ERROR;
		}
		Map<String,Object> maps = xszf.verifyStr(str);
		Object errorCode = maps.get("ErrorCode");
		if(errorCode!= null){
			addActionError(xszf.errorCode(errorCode.toString()));
			return ERROR;
		}
		Object resultCode = maps.get("resultCode");
		if(resultCode!= null && !resultCode.toString().equals("0000")){
			addActionError("没有查到相应记录");
			return ERROR;
		}
		String queryDetailsSize = maps.get("queryDetailsSize").toString();//记录条数
		logInfoStringBuffer.append("查询接口数据条数:"+queryDetailsSize);
		
		/**接口数据 *  0：已接受 	1：处理中 	2：处理成功 	3：处理失败 
		 */
//		List<QueryDetails> qList = (ArrayList<QueryDetails>) maps.get("queryDetails");
		Map<String,QueryDetailsXszf> qmap = (Map<String, QueryDetailsXszf>) maps.get("queryDetails");
		if(Integer.parseInt(queryDetailsSize) != qmap.size()){
			addActionError("查询结果不统一!");
			return ERROR;
		}
		try{
			//查询新生支付数据 【0：失败;1：成功;2:充值中】
			List<UserAccountRecharge> uList = userAccountRechargeService.findUserAccountRechargeList(null, status, rechargeConfig, startDate, endDate);
			userAccountRechargeList = new ArrayList<UserAccountRecharge>();
			for(UserAccountRecharge u:uList){
				String tNo=u.getTradeNo();
				QueryDetailsXszf qd = qmap.get(tNo);
				if(qd!= null && u.getStatus().equals(1) && qd.getStateCode().equals("2") && u.getMoney().equals(qd.getPayAmount())){
					u.setCompareStatus(1);//成功
				}else if(qd!= null && u.getStatus().equals(2) && qd.getStateCode().equals("1") && u.getMoney().equals(qd.getPayAmount())){
					u.setCompareStatus(2);//未完结成功（两边都是处理中）
				}else if(qd != null && u.getStatus().equals(2) && qd.getStateCode().equals("2")){
					u.setCompareStatus(0);//补单(本地充值中，接口成功)
				}else if(qd != null && u.getStatus().equals(1) && !qd.getStateCode().equals("2")){
					u.setCompareStatus(3);//异常数据(本地成功，接口非成功)
				}else{	
					u.setPortData(4+":接口没有此订单");
					u.setCompareStatus(4);//(本地有数据，接口无数据)
				}
				if(qd!= null){
					u.setPortData(qd.getStateCode()+":"+xszf.resCode(qd.getStateCode()));
				}
				
	//			if(qd!=null){
	//				u.setCompareStatus(qd.getStateCode());
	//			}
				userAccountRechargeList.add(u);
			}
		}catch (Exception e) {
			addActionError("数据量太大，请选择时间段后查询!");
			return ERROR;
		}
		ComparatorUserRecharge Comparator = new ComparatorUserRecharge();
		Collections.sort(userAccountRechargeList, Comparator);
		setSession("rechargeSession", userAccountRechargeList);
		logInfo = logInfoStringBuffer.toString();
		return "xszf";
	}
	
	
	/**
	 * 查询单笔【新生支付】订单
	 */
	public String doSignleXszf(){
		if(getSession("dg_rechargeSession")!=null){
			removeSession("dg_rechargeSession");
		}		
		StringBuffer logInfoStringBuffer = new StringBuffer("单个查询\"新生\"接口数据; ");
		UserAccountRecharge u = userAccountRechargeService.get(id);
		if(u==null){
			return ajax(Status.error, "此条充值记录不存在");
		}
		RechargeConfig rc = u.getRechargeInterface();
		
		HnapayUtils xszf = new HnapayUtils();
		String str = xszf.getExamineVerify(rc, u, null);
		if(StringUtils.isEmpty(str)){
			return ajax(Status.error, "连接超时或接口问题");
		}
		Map<String,Object> maps = xszf.verifyStr(str);
		Object errorCode = maps.get("ErrorCode");
		if(errorCode!= null){
			return ajax(Status.error, xszf.errorCode(errorCode.toString()));
		}
		Object resultCode = maps.get("resultCode");
		if(resultCode!= null && !resultCode.toString().equals("0000")){
			return ajax(Status.error, "没有查到相应记录");
		}
		
		String queryDetailsSize = maps.get("queryDetailsSize").toString();//记录条数
		logInfoStringBuffer.append("查询接口数据条数:"+queryDetailsSize);
		
		/**接口数据
		 *  0：已接受  1：处理中 2：处理成功 3：处理失败 
		 */
		Map<String,QueryDetailsXszf> qmap = (Map<String, QueryDetailsXszf>) maps.get("queryDetails");
		if( !queryDetailsSize.equals("1") || qmap.size()!=1){
			return ajax(Status.error, "查询结果不统一");
		}
		String tNo=u.getTradeNo();
		QueryDetailsXszf qd = qmap.get(tNo);
		if(qd!= null && u.getStatus().equals(1) && qd.getStateCode().equals("2") && u.getMoney().equals(qd.getPayAmount())){
			u.setCompareStatus(1);//成功
		}else if(qd!= null && u.getStatus().equals(2) && qd.getStateCode().equals("1") && u.getMoney().equals(qd.getPayAmount())){
			u.setCompareStatus(2);//未完结成功（两边都是处理中）
		}else if(qd != null && u.getStatus().equals(2) && qd.getStateCode().equals("2")){//本地充值中，接口成功
			u.setCompareStatus(0);//失败
		}else if(qd != null && u.getStatus().equals(1) && !qd.getStateCode().equals("2")){//本地成功，接口非成功
			u.setCompareStatus(3);//失败
		}else{
			u.setPortData(4+":订单号不存在");
			u.setCompareStatus(4);//失败
		}

		if(qd!= null){
			u.setPortData(qd.getStateCode()+":"+xszf.resCode(qd.getStateCode()));
		}
		
		setSession("dg_rechargeSession", u);
		logInfo = logInfoStringBuffer.toString();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS_PARAMETER_NAME, "success");
		
		String str_p= u.getPortData();
		if(str_p != null && str_p.split(":").length==2){
			str_p=str_p.split(":")[1];
		}
		jsonMap.put("portData",str_p);
		int c = u.getCompareStatus();
		String str_c= "";
		if(c==0){
			str_c="补单";
		}else if(c==1){
			str_c="充值成功";
		}else if(c==2){
			str_c="未完结的充值";
		}else if(c==3){
			str_c="异常数据(本地成功，接口非成功)";
		}else if(c==4){
			str_c="失败(本地有数据，接口无数据)";
		}
		jsonMap.put("compareStatus",str_c);
		jsonMap.put(MESSAGE_PARAMETER_NAME,"查询成功");
		return ajax(jsonMap);
	}

	/**
	 * 批量处理处理【新生支付】接口数据跟本地数据
	 */
	public String comparison(){
		StringBuffer logInfoStringBuffer = new StringBuffer("批量处理接口数据和本地数据状态;");
		try{
			userAccountRechargeList = (List<UserAccountRecharge>) getSession("rechargeSession");
			if(userAccountRechargeList==null || userAccountRechargeList.size()<1){
				return ajax(Status.error, "没有查询到数据集或者数据已处理，不要重复处理...");
			}
			
			logInfoStringBuffer.append("处理条数:"+userAccountRechargeList.size());
			logInfo = logInfoStringBuffer.toString();
			userAccountRechargeService.update(userAccountRechargeList);
		}catch (Exception e) {
			addActionError("数据量太大，请选择时间段后处理!");
			return ERROR;
		}
		removeSession("rechargeSession");
		return ajax(Status.success, "成功");
	}
	
	/**
	 * 单个处理【新生支付】接口数据跟本地数据
	 */
	public String doSignle(){
		StringBuffer logInfoStringBuffer = new StringBuffer("单个处理接口数据和本地数据状态: ");
		UserAccountRecharge u = (UserAccountRecharge) getSession("dg_rechargeSession");
		if(u==null){
			return ajax(Status.error, "没有查询到数据或者数据已处理，不要重复处理...");
		}
		logInfoStringBuffer.append("订单号:"+u.getTradeNo());
		userAccountRechargeService.updateCompare(u);
		logInfoStringBuffer.append("处理成功");
		logInfo = logInfoStringBuffer.toString();
		removeSession("dg_rechargeSession");
		return ajax(Status.success,"处理成功");
	}
	
	
	/**
	 * 跳转到【国付宝】批量查询  充值记录
	 * @return
	 */
	public String toSourceGfb(){
		return "gfb";
	}
	
	/**
	 *  执行批量查询【国付宝】数据
	 * @return
	 */
	public String doSourceGfb(){
		if(getSession("rechargeSession")!= null){
			removeSession("rechargeSession");
		}
		StringBuffer logInfoStringBuffer = new StringBuffer("批量查询\"国付宝\"接口数据; ");
		if(getSession("rechargeSession")!=null){
			removeSession("rechargeSession");
		}
		
		Map<String ,String> map = new HashMap<String,String>();
		if(startDate!= null && endDate!= null){//订单提交时间的范围
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)-1);
			endDate = calendar.getTime();
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddHHmmss");
			if(startDate.after(endDate)){
				addActionError("开始时间不能晚于结束时间!");
				return ERROR;
			}
			if(endDate.after(new Date())){
				addActionError("结束时间至少是当前时间的前一天!");
				return ERROR;
			}
			map.put("beginTime", dateformat.format(startDate));
			map.put("endTime", dateformat.format(endDate));
			logInfoStringBuffer.append("开始时间").append(dateformat.format(startDate)).append("结束时间").append(dateformat.format(endDate)+";");
		}else{
			addActionError("请选择时间范围!");
			return ERROR;
		}
		RechargeConfig rechargeConfig = rechargeConfigService.get(id);
		if(rechargeConfig == null){
			addActionError("充值方式不存在!");
			return ERROR;
		}
		try{
			List<UserAccountRecharge> userRechargeList = userAccountRechargeService.findUserAccountRechargeList(null, status, rechargeConfig, startDate, endDate);
			GopayUtils gopay=new GopayUtils();
	//		UserAccountRecharge u = userAccountRechargeService.getUserAccountRecharge("2013012911483621659");
			userAccountRechargeList = new ArrayList<UserAccountRecharge>();
			for(UserAccountRecharge u : userRechargeList){
				QueryDetailsGfb qd = gopay.getExamineVerify(rechargeConfig,u,getRequest());
				if(qd!= null && u.getStatus().equals(1)&& qd.getOrgTxnStat().equals("0000") && qd.getOrgtranAmt().equals(u.getMoney().toString())){//本地成功，接口成功
					u.setCompareStatus(1);
				}else if(qd!= null && u.getStatus().equals(2) && !qd.getOrgTxnStat().equals("0000") &&!qd.getOrgTxnStat().equals("其它")){//本地充值中，接口处理中
					u.setCompareStatus(2);
				}else if(qd!= null && u.getStatus().equals(1) && !qd.getOrgTxnStat().equals("0000") &&!qd.getOrgTxnStat().equals("其它")){//本地成功，接口充值中
					u.setCompareStatus(0);
				}else if(qd!= null && u.getStatus().equals(2) && qd.getOrgTxnStat().equals("0000")){//本地充值中，接口成功
					u.setCompareStatus(3);
				}else{
					u.setPortData(4+":订单号不存在");
					u.setCompareStatus(4);
				}
				
				if(qd!= null){
					u.setPortData(qd.getOrgTxnStat()+":"+gopay.errorOrgTxnStat(qd.getOrgTxnStat()));
				}
				userAccountRechargeList.add(u);
			}
		}catch (Exception e) {
			addActionError("数据量太大，请选择时间段后查询!");
			return ERROR;
		}
		setSession("rechargeSession", userAccountRechargeList);
		return "gfb";
	}
	
	
	/**
	 * 查询单笔【国付宝】订单
	 */
	public String doSignleGfb(){
		if(getSession("dg_rechargeSession")!=null){
			removeSession("dg_rechargeSession");
		}
		StringBuffer logInfoStringBuffer = new StringBuffer("单个查询\"新生\"接口数据; ");
		UserAccountRecharge u = userAccountRechargeService.get(id);
		if(u==null){
			return ajax(Status.error, "此条充值记录不存在");
		}
		RechargeConfig rc = u.getRechargeInterface();
		
		GopayUtils gopay = new GopayUtils();
		QueryDetailsGfb qd = gopay.getExamineVerify(rc, u, getRequest());
		
		if(qd!= null && u.getStatus().equals(1)&& qd.getOrgTxnStat().equals("0000") && qd.getOrgtranAmt().equals(u.getMoney().toString())){//本地成功，接口成功
			u.setCompareStatus(1);
		}else if(qd!= null && u.getStatus().equals(2) && !qd.getOrgTxnStat().equals("0000") &&!qd.getOrgTxnStat().equals("其它")){//本地充值中，接口处理中
			u.setCompareStatus(2);
		}else if(qd!= null && u.getStatus().equals(1) && !qd.getOrgTxnStat().equals("0000") &&!qd.getOrgTxnStat().equals("其它")){//本地成功，接口充值中
			u.setCompareStatus(0);
		}else if(qd!= null && u.getStatus().equals(2) && qd.getOrgTxnStat().equals("0000")){//本地充值中，接口成功
			u.setCompareStatus(3);
		}else{
			u.setPortData(4+":订单号不存在");
			u.setCompareStatus(4);
		}
		
		if(qd!= null){
			u.setPortData(qd.getOrgTxnStat()+":"+gopay.errorOrgTxnStat(qd.getOrgTxnStat()));
		}
		
		setSession("dg_rechargeSession", u);
		logInfo = logInfoStringBuffer.toString();
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS_PARAMETER_NAME, "success");
		
		String str_p= u.getPortData();
		if(str_p != null && str_p.split(":").length==2){
			str_p=str_p.split(":")[1];
		}
		jsonMap.put("portData",str_p);
		int c = u.getCompareStatus();
		String str_c= "";
		if(c==0){
			str_c="补单";
		}else if(c==1){
			str_c="充值成功";
		}else if(c==2){
			str_c="未完结的充值";
		}else if(c==3){
			str_c="异常数据(本地成功，接口非成功)";
		}else if(c==4){
			str_c="失败(本地有数据，接口无数据)";
		}
		jsonMap.put("compareStatus",str_c);
		jsonMap.put(MESSAGE_PARAMETER_NAME,"查询成功");
		return ajax(jsonMap);
		
		
		
	}
	
	/**
	 * 单个处理新生支付接口数据跟本地数据
	 */
//	public String signleGfb(){
//		
//		return ajax(Status.success,"处理成功");	
//	}
	
	/**
	 * 查询单笔【宝付】订单
	 */
	public String doSignleBaofoo(){
		if(getSession("dg_rechargeSession")!=null){
			removeSession("dg_rechargeSession");
		}		
		StringBuffer logInfoStringBuffer = new StringBuffer("单个查询\"宝付\"接口数据; ");
		UserAccountRecharge u = userAccountRechargeService.get(id);
		if(u==null){
			return ajax(Status.error, "此条充值记录不存在");
		}
		RechargeConfig rc = u.getRechargeInterface();
		
		BaofooUtil baofoo = new BaofooUtil();
		QueryDetailsBaofoo qd = baofoo.getExamineVerify(rc, u);
		
		if(qd!= null && u.getStatus().equals(1)&& qd.getCheckResult().equals("Y") && qd.getFactMoney().compareTo(u.getMoney())==0 ){//本地成功，接口成功
			u.setCompareStatus(1);
		}else if(qd!= null && u.getStatus().equals(2) && qd.getCheckResult().equals("P")){//本地充值中，接口处理中
			u.setCompareStatus(2);
		}else if(qd!= null && u.getStatus().equals(1) && !qd.getCheckResult().equals("P")){//本地成功，接口充值中
			u.setCompareStatus(0);
		}else if(qd!= null && u.getStatus().equals(2) && qd.getCheckResult().equals("Y")){//本地充值中，接口成功
			u.setCompareStatus(3);
		}else{
			u.setPortData(4+":订单号不存在");
			u.setCompareStatus(4);
		}
		
		if(qd!= null){
			u.setPortData(qd.getCheckResult()+":"+baofoo.resCode(qd.getCheckResult()));
		}
		
		setSession("dg_rechargeSession", u);
		logInfo = logInfoStringBuffer.toString();
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS_PARAMETER_NAME, "success");
		
		String str_p= u.getPortData();
		if(str_p != null && str_p.split(":").length==2){
			str_p=str_p.split(":")[1];
		}
		jsonMap.put("portData",str_p);
		int c = u.getCompareStatus();
		String str_c= "";
		if(c==0){
			str_c="补单";
		}else if(c==1){
			str_c="充值成功";
		}else if(c==2){
			str_c="未完结的充值";
		}else if(c==3){
			str_c="异常数据(本地成功，接口非成功)";
		}else if(c==4){
			str_c="失败(本地有数据，接口无数据)";
		}
		jsonMap.put("compareStatus",str_c);
		jsonMap.put(MESSAGE_PARAMETER_NAME,"查询成功");
		return ajax(jsonMap);
	}
	
	/**
	 * 执行批量查询【宝付】数据
	 * @return
	 */
	public String doSourceBaofoo(){
		StringBuffer logInfoStringBuffer = new StringBuffer("批量查询\"宝付\"接口数据; ");
		if(getSession("rechargeSession")!=null){
			removeSession("rechargeSession");
		}
		
		Map<String ,String> map = new HashMap<String,String>();
		if(startDate!= null && endDate!= null){//订单提交时间的范围
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)-1);
			endDate = calendar.getTime();
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddHHmmss");
			if(startDate.after(endDate)){
				addActionError("开始时间不能晚于结束时间!");
				return ERROR;
			}
			if(endDate.after(new Date())){
				addActionError("结束时间至少是当前时间的前一天!");
				return ERROR;
			}
			map.put("beginTime", dateformat.format(startDate));
			map.put("endTime", dateformat.format(endDate));
			logInfoStringBuffer.append("开始时间").append(dateformat.format(startDate)).append("结束时间").append(dateformat.format(endDate)+";");
		}else{
			addActionError("请选择时间范围!");
			return ERROR;
		}
		RechargeConfig rechargeConfig = rechargeConfigService.get(id);
		if(rechargeConfig == null){
			addActionError("充值方式不存在!");
			return ERROR;
		}
		
		try{
		/**接口数据 *  0：已接受 	1：处理中 	2：处理成功 	3：处理失败 
		 */

		//查询宝付数据 【0：失败;1：成功;2:充值中】
		List<UserAccountRecharge> uList = userAccountRechargeService.findUserAccountRechargeList(null, status, rechargeConfig, startDate, endDate);
		userAccountRechargeList = new ArrayList<UserAccountRecharge>();
		BaofooUtil baofoo = new BaofooUtil();
		for(UserAccountRecharge u:uList){
			
			QueryDetailsBaofoo qd = baofoo.getExamineVerify(rechargeConfig, u);
			if(qd!= null && u.getStatus().equals(1)&& qd.getCheckResult().equals("Y") && qd.getFactMoney().compareTo(u.getMoney())==0 ){//本地成功，接口成功
				u.setCompareStatus(1);
			}else if(qd!= null && u.getStatus().equals(2) && qd.getCheckResult().equals("P")){//本地充值中，接口处理中
				u.setCompareStatus(2);
			}else if(qd!= null && u.getStatus().equals(1) && !qd.getCheckResult().equals("P")){//本地成功，接口充值中
				u.setCompareStatus(0);
			}else if(qd!= null && u.getStatus().equals(2) && qd.getCheckResult().equals("Y")){//本地充值中，接口成功
				u.setCompareStatus(3);
			}else{
				u.setPortData(4+":订单号不存在");
				u.setCompareStatus(4);
			}
			if(qd!= null){
				u.setPortData(qd.getCheckResult()+":"+baofoo.resCode(qd.getCheckResult()));
			}
			userAccountRechargeList.add(u);
		}
		ComparatorUserRecharge Comparator = new ComparatorUserRecharge();
		Collections.sort(userAccountRechargeList, Comparator);
		setSession("rechargeSession", userAccountRechargeList);
		}catch (Exception e) {
			addActionError("数据量太大，请选择时间段后执行!");
			return ERROR;
		}
		logInfo = logInfoStringBuffer.toString();
		return "baofoo";
	}
	
	
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	public SourceRecord getSourceRecord() {
		return sourceRecord;
	}

	public void setSourceRecord(SourceRecord sourceRecord) {
		this.sourceRecord = sourceRecord;
	}
	//获取所有支付方式
	public List<RechargeConfig> getRechargeConfigList(){
		return rechargeConfigService.getRechargeConfigList();
	}
	public List<SourceRecord> getSourceRecordList() {
		return sourceRecordList;
	}
	public void setSourceRecordList(List<SourceRecord> sourceRecordList) {
		this.sourceRecordList = sourceRecordList;
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
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getPaymentProductId() {
		return paymentProductId;
	}
	public void setPaymentProductId(String paymentProductId) {
		this.paymentProductId = paymentProductId;
	}
	public List<UserAccountRecharge> getUserAccountRechargeList() {
		return userAccountRechargeList;
	}
	public void setUserAccountRechargeList(
			List<UserAccountRecharge> userAccountRechargeList) {
		this.userAccountRechargeList = userAccountRechargeList;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
