package com.qmd.directive;

import com.qmd.util.ConstantUtil;
import com.qmd.util.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 加密图片URL
 */
@Component("imageUrlDecodeDirective")
public class ImageUrlDecodeDirective implements TemplateDirectiveModel {
	public static final String TAG_NAME = "imageUrlDecode";

	private static final String IMGURL_PARAMETER_NAME = "imgurl";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String imgurl = DirectiveUtil.getStringParameter(IMGURL_PARAMETER_NAME,
				params);

//		String ret = CommonUtil.encodeUrl(imgurl);
//		ret = ConstantUtil.WEB_SITE + "/index.img?image=" + ret;
		String ret = ConstantUtil.WEB_IMG+ ConstantUtil.WEB_IMG_CONTEXT_PATH + imgurl;

		if (body != null && ret != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(ret);
			}
			body.render(env.getOut());
		}
	}

}