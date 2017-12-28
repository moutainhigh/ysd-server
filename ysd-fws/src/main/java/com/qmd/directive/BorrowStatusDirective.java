package com.qmd.directive;

import com.qmd.mode.borrow.Borrow;
import com.qmd.service.borrow.BorrowService;
import com.qmd.util.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Component("borrowStatusDirective")
public class BorrowStatusDirective implements TemplateDirectiveModel{
	public static final String TAG_NAME = "borrow_status";
	private static final String TYPE_PARAMETER_NAME = "borrow_id";
	
	@Resource
	private BorrowService borrowService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String id = DirectiveUtil.getStringParameter(TYPE_PARAMETER_NAME, params);
		Borrow borrow = borrowService.getBorrowById(Integer.parseInt(id));
		if (body != null && borrow!=null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(borrow);
			}
			body.render(env.getOut());
		}
	}
	
}
