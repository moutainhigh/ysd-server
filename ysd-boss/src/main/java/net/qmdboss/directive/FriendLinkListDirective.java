package net.qmdboss.directive;

import freemarker.core.Environment;
import freemarker.template.*;
import net.qmdboss.entity.FriendLink;
import net.qmdboss.service.FriendLinkService;
import net.qmdboss.util.DirectiveUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component("friendLinkListDirective")
public class FriendLinkListDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "friend_link_list";
	private static final String TYPE_PARAMETER_NAME = "type";
	private static final String COUNT_PARAMETER_NAME = "count";
	
	@Resource(name = "friendLinkServiceImpl")
	private FriendLinkService friendLinkService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String type = DirectiveUtil.getStringParameter(TYPE_PARAMETER_NAME, params);
		Integer count = DirectiveUtil.getIntegerParameter(COUNT_PARAMETER_NAME, params);
		
		List<FriendLink> friendLinkList = friendLinkService.getFriendLinkList(type, count);
		
		if (body != null && friendLinkList != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(friendLinkList);
			}
			body.render(env.getOut());
		}
	}

}