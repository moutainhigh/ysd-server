package com.qmd.action;

import com.qmd.action.base.BaseAction;
import com.qmd.util.ConstantUtil;
import com.qmd.util.RandomNumUtil;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@SuppressWarnings("serial")
@Service("randomAction")
public class RandomAction extends BaseAction {
	private ByteArrayInputStream inputStream;
	
	@Action(value="/rand",results={@Result(name="success", type="stream")})
	public String execute() throws Exception {
		RandomNumUtil rdnu = RandomNumUtil.Instance(); // 获取数字的验证码
//		RandomNumLettersUtil rdnu = RandomNumLettersUtil.Instance(); // 获取数字+字母的验证码
//		RandomChineseUtil rdnu = RandomChineseUtil.Instance(); //获取汉字验证码
		this.setInputStream(rdnu.getImage());// 取得带有随机字符串的图片
//		ActionContext.getContext().getSession().put("random", rdnu.getString());// 取得随机字符串放入HttpSession
		
//		String uuid = CommonUtil.getUUID();
//		this.setCookie(ConstantUtil.RANDOM_COOKIE_NAME, uuid);//sessionID
//		this.getMemcachedClient().set(uuid, rdnu.getString());
//		this.setMemcachedByCookie(ConstantUtil.RANDOM_COOKIE_NAME, rdnu.getString());
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, rdnu.getString());
		return SUCCESS;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	} 

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
}
