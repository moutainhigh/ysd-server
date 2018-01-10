package com.qmd.directive;

import com.qmd.service.user.UserAccountDetailService;
import com.qmd.util.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;


@Component("userAccountDetailCountDirective")
public class UserAccountDetailCountDirective implements TemplateDirectiveModel{
	public static final String TAG_NAME = "userAccountDetailSum_by_type_count";
	private static final String TYPES_PARAMETER_NAME = "types";
	private static final String USERID_PARAMETER_NAME = "userid";
	private static final String COUNT_TYPE_PARAMETER_NAME = "countType";
	
	@Resource
	private UserAccountDetailService userAccountDetailService;
	
	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String types = DirectiveUtil.getStringParameter(TYPES_PARAMETER_NAME, params);
		Integer userid = DirectiveUtil.getIntegerParameter(USERID_PARAMETER_NAME, params);
		
		String countType = DirectiveUtil.getStringParameter(COUNT_TYPE_PARAMETER_NAME, params);
		
		BigDecimal sum = new BigDecimal(0);
		if (StringUtils.isNotEmpty(types)) {
			
			String[] ts = types.split(",");
			BigDecimal[] sums = new BigDecimal[ts.length];
			
			for(int i =0;i<ts.length;i++) {
				sums[i] = userAccountDetailService.getSumMoney(userid,ts[i]);
				if (i==0) {
					sum = sums[0];
				} else {
					if ("add".equals(countType)) {
						sum = sum.add(sums[i]);
					} else if ("sub".equals(countType)) {
						sum = sum.subtract(sums[i]);
					}
				}
			}
			
			
		}
		if (body != null && sum != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(sum);
			}
			body.render(env.getOut());
		}
	}
}
