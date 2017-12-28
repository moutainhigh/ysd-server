package net.qmdboss.directive;

import freemarker.core.Environment;
import freemarker.template.*;
import net.qmdboss.entity.RechargeConfig;
import net.qmdboss.service.RechargeConfigService;
import net.qmdboss.util.DirectiveUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;


@Component("rechargeConfigDirective")
public class RechargeConfigDirective implements TemplateDirectiveModel{

	public static final String TAG_NAME = "rechargeConfig_tag";
	private static final String ID_PARAMETER_NAME = "id";
	
	@Resource(name = "rechargeConfigServiceImpl")
	private RechargeConfigService rechargeConfigService;
	
	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer id = DirectiveUtil.getIntegerParameter(ID_PARAMETER_NAME, params);
		
		RechargeConfig rechargeConfig = null;
		
		rechargeConfig = rechargeConfigService.get(id);
		
		if (body != null && rechargeConfig != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(rechargeConfig);
			}
			body.render(env.getOut());
		}
	}
}
