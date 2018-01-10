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


@Component("userAccountDetailDirective")
public class UserAccountDetailDirective implements TemplateDirectiveModel{
	public static final String TAG_NAME = "userAccountDetailSum_by_type";
	private static final String TYPE_PARAMETER_NAME = "type";
	private static final String USERID_PARAMETER_NAME = "userid";
	
	@Resource
	private UserAccountDetailService userAccountDetailService;
	
	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String type = DirectiveUtil.getStringParameter(TYPE_PARAMETER_NAME, params);
		Integer userid = DirectiveUtil.getIntegerParameter(USERID_PARAMETER_NAME, params);
		
		BigDecimal sum = new BigDecimal(0);
		if (StringUtils.isNotEmpty(type)) {
			sum = userAccountDetailService.getSumMoney(userid,type);
		}
		if (body != null && sum != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(sum);
			}
			body.render(env.getOut());
		}
	}
}
