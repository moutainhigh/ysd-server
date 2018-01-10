package com.qmd.directive;

import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.borrow.BorrowTenderService;
import com.qmd.util.CalculationUtil;
import com.qmd.util.DirectiveUtil;
import com.qmd.util.bean.RateStep;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@Component("rateDirectiveByStep")
public class RateDirectiveByStep implements TemplateDirectiveModel {


	public static final String TAG_NAME = "rate_by_step";
	private static final String ID = "id";//投资记录ID
	private static final String TYPE = "type";//【0：理财产品；5：流转标】
	private static final String IS_YEAR = "is_year";//【0：天利率；1年利率】
	
	
	@Resource
	private BorrowService borrowService;
	@Resource
	private BorrowTenderService borrowTenderService;
	
	
	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer id = DirectiveUtil.getIntegerParameter(ID, params);
		Integer type = DirectiveUtil.getIntegerParameter(TYPE, params);
		Integer isYear = DirectiveUtil.getIntegerParameter(IS_YEAR, params);
		
		String rate = "";
		
		if(type == 5){//流转标
			BorrowTender bt = borrowTenderService.get(id);
			Borrow b = borrowService.getBorrowById(bt.getBorrowId());
			
			RateStep rateStep = CalculationUtil.hitBorrowStep(b, new BigDecimal(bt.getAccount()) );
			
			if(isYear == 0){
				rate = rateStep.getRateDay().toString()+"‰";
			}else if(isYear ==1){
				rate = rateStep.getRateYear().toString()+"%";
			}
		}
		
		if (body != null && !"".equals(rate)) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(rate);
			}
			body.render(env.getOut());
		}
	}

	
}
