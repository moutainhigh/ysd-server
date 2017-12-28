package com.qmd.directive;

import com.qmd.mode.borrow.Borrow;
import com.qmd.service.borrow.BorrowService;
import com.qmd.util.DirectiveUtil;
import com.qmd.util.Pager;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据分类获取文章列表
 * @author Xsf
 *
 */
@Component("borrowNubbleDirective")
public class BorrowNubbleDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "borrow_list";
	private static final String TYPE_PARAMETER_NAME = "type";
	private static final String COUNT_PARAMETER_NAME = "count";
	
	@Resource
	private BorrowService borrowService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String type = DirectiveUtil.getStringParameter(TYPE_PARAMETER_NAME, params);
		Integer count = DirectiveUtil.getIntegerParameter(COUNT_PARAMETER_NAME, params);
		Pager pager = new Pager();
		Map<String,Object> map= new HashMap<String ,Object>();
		int[] array = {1,3,4,5,7};
		map.put("array", array);
		if (type!=null&&!"".equals(type)) {
			if (type.indexOf(",")>0) {
				String[] typearray = type.split(",");
				map.put("typearray", typearray);
			} else {
				map.put("bType", type);
			}
		}
		
		pager.setThisSize(0);
		pager.setPageSize(count);
		map.put("pager", pager);
		map.put("orderBy", "b.show_sort desc,b.verify_time desc");
		pager = borrowService.queryBorrowList(pager,map);
		List<Borrow> borrowList = (List<Borrow>)pager.getResult();
		if (body != null && borrowList!=null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(borrowList);
			}
			body.render(env.getOut());
		}
	}
	
}
