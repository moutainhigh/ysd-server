package net.qmdboss.directive;

import freemarker.core.Environment;
import freemarker.template.*;
import net.qmdboss.util.DirectiveUtil;
import net.qmdboss.util.SettingUtil;
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
//
//		ret = SettingUtil.getSetting().getShopUrl() + "/index.img?image=" + ret;

		String ret = SettingUtil.getSetting().getShopImgContext() + imgurl;
		if (body != null && ret != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(ret);
			}
			body.render(env.getOut());
		}
	}

}