package com.qmd.directive;

import com.qmd.mode.borrow.Borrow;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.util.ListingService;
import com.qmd.util.DirectiveUtil;
import com.qmd.util.bean.DeferInfo;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 项目是否可以申请展期
 * @author Xsf
 *
 */
@Component("deferApplyDirective")
public class DeferApplyDirective implements TemplateDirectiveModel {

	public static final String TAG_NAME = "defer_apply";
	
	private static final String BORROW_ID = "bid";
	private static final String TIME_LIMIT = "time_limit";
	private static final String REPAYMENT_TIME = "repayment_time";
	
	@Resource
	private ListingService listingService;
	@Resource
	BorrowService borrowService;//标
	
	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		
		Integer borrowId = DirectiveUtil.getIntegerParameter(BORROW_ID, params);
		String timeLimit = DirectiveUtil.getStringParameter(TIME_LIMIT, params);//项目期限
		Date repaymentTime = DirectiveUtil.getDateParameter(REPAYMENT_TIME, params);//还款日
		
		//一、可申请 提示‘可申请时间截止到XXXX-XX-XX’
		//二、不能申请 提示 ‘XXXX-XX-XX起可申请’
		//三、已过期
		//四、达到最大申请数
		
		DeferInfo deferInfo = new DeferInfo();
		Integer status = 2;
		String title = "未知";
		String start_sign = "defer_start_"+timeLimit;
		String end_sign = "defer_end_"+timeLimit;
		
		String start = listingService.getKeyValue(start_sign);
		String end = listingService.getKeyValue(end_sign);
		
		Borrow b = borrowService.getBorrowById(borrowId);
		String keyValue = listingService.getKeyValue("defer_max_times");
		
//		if(StringUtils.isNotEmpty(keyValue) &&  b.getDeferCount() < Integer.parseInt(keyValue) &&  StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end) && repaymentTime != null){
//			//可申请起始日
//			Date s_date  = CommonUtil.date2begin( CommonUtil.getDateBefore(repaymentTime, Integer.parseInt(start)) );
//			
//			//申请截止日期
//			Date e_date  =  CommonUtil.date2end( CommonUtil.getDateBefore(repaymentTime, Integer.parseInt(end) + 1 ) );
//			
//			Date d = new Date();//当前日期
//			if(CommonUtil.compareDate(d, s_date) == 1 && CommonUtil.compareDate(e_date, d) == 1 ){
//				status = 1 ;
//				title = "可申请时间截止到"+ CommonUtil.getDate2String(e_date, "yyyy-MM-dd");
//			}else if(CommonUtil.compareDate(d, s_date) < 0){
//				status = 0;
//				title = CommonUtil.getDate2String(s_date, "yyyy-MM-dd")+"起可申请";
//			}else if(CommonUtil.compareDate(e_date, d) < 1){
//				status = 2;
//				title = "已过期";
//			}
//			
//		}else{
//			status =3;
//			title = "禁止申请";
//		}
		
		deferInfo.setStatus(status);
		deferInfo.setTitle(title);
		
		
		if (body != null && deferInfo != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(deferInfo);
			}
			body.render(env.getOut());
		}
	}

}