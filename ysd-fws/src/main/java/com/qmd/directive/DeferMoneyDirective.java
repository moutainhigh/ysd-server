package com.qmd.directive;

import com.qmd.mode.borrow.BorrowTemp;
import com.qmd.service.borrow.BorrowTenderService;
import com.qmd.util.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("deferMoneyDirective")
public class DeferMoneyDirective implements TemplateDirectiveModel {

		public static final String TAG_NAME = "defer_money";
		
		public static final String BORROW_ID = "bid";
		public static final String USER_ID = "userid";

		@Resource
		BorrowTenderService borrowTenderService;//投标

		@SuppressWarnings("unchecked")
		public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
			Integer bid = DirectiveUtil.getIntegerParameter(BORROW_ID, params);//项目ID
			Integer userid = DirectiveUtil.getIntegerParameter(USER_ID, params);//服务商用户ID
			
			BigDecimal agreeDeferMoney = BigDecimal.ZERO;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userId", userid);
			map.put("borrowId", bid);
			map.put("deferStatus", 1);
			int[] status = {3,7};
			map.put("status", status); 
			List<BorrowTemp> borrowTempList = borrowTenderService.getJkmxByUserid(map);
			for(BorrowTemp bt:borrowTempList){
				agreeDeferMoney = agreeDeferMoney.add(bt.getLoanAmountFee());
			}
			
			if (body != null && agreeDeferMoney != null) {
				if (loopVars.length > 0) {
					loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(agreeDeferMoney);
				}
				body.render(env.getOut());
			}
		}

	}