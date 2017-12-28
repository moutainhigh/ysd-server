package net.qmdboss.directive;

import freemarker.core.Environment;
import freemarker.template.*;
import net.qmdboss.entity.User;
import net.qmdboss.service.UserService;
import net.qmdboss.util.DirectiveUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Component("userByAgencyDirective")
public class UserByAgencyDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "user_by_agency";
	private static final String AGENCY_ID = "agencyid";
	private static final String AGENCY_TYPE = "agencytype";
	private static final String USER_ID = "userid";
	
	@Resource(name = "userServiceImpl")
	private UserService userService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

		Integer agencyid = DirectiveUtil.getIntegerParameter(AGENCY_ID, params);
		Integer agencytype = DirectiveUtil.getIntegerParameter(AGENCY_TYPE, params);
		Integer id = DirectiveUtil.getIntegerParameter(USER_ID, params);
		User user = new User();
		if(id == null){
			user = userService.getUserByAgency(agencyid, agencytype);
		}else{
			user = userService.get(id);
		}
		if (body != null && user != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(user);
			}
			body.render(env.getOut());
		}
	}

}