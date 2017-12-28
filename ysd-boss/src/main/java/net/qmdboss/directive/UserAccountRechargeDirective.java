package net.qmdboss.directive;

import freemarker.core.Environment;
import freemarker.template.*;
import net.qmdboss.entity.UserAccountRecharge;
import net.qmdboss.service.UserAccountRechargeService;
import net.qmdboss.util.DirectiveUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Component("userAccountRechargeDirective")
public class UserAccountRechargeDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "userAccountRecharge_tag";
	private static final String TRADENO_PARAMETER_NAME = "trade_no";
	
	@Resource(name = "userAccountRechargeServiceImpl")
	private UserAccountRechargeService userAccountRechargeService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String tradeNo = DirectiveUtil.getStringParameter(TRADENO_PARAMETER_NAME, params);
		
		UserAccountRecharge recharge = userAccountRechargeService.getUserAccountRecharge(tradeNo);
		
		if (body != null ) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(recharge);
			}
			body.render(env.getOut());
		}
	}

}