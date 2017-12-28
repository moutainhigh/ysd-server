package com.qmd.action;

import com.qmd.action.base.BaseAction;
import com.qmd.util.ConstantUtil;
import com.qmd.util.RandomNumLettersPlaceUtil;
import com.qmd.util.RandomNumLettersUtil;
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
//		RandomNumUtil rdnu = RandomNumUtil.Instance(); // 获取数字的验证码
		RandomNumLettersUtil rdnu = RandomNumLettersUtil.Instance(); // 获取数字+字母的验证码
//		RandomChineseUtil rdnu = RandomChineseUtil.Instance(); //获取汉字验证码
		this.setInputStream(rdnu.getImage());// 取得带有随机字符串的图片
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, rdnu.getString().toLowerCase());
		return SUCCESS;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	} 

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
	
	
	@Action(value="/placeRand",results={@Result(name="success", type="stream")})
	public String placeRand() throws Exception {
//		RandomNumUtil rdnu = RandomNumUtil.Instance(); // 获取数字的验证码
		RandomNumLettersPlaceUtil rdnu = RandomNumLettersPlaceUtil.Instance(); // 获取数字+字母的验证码
//		RandomChineseUtil rdnu = RandomChineseUtil.Instance(); //获取汉字验证码
		this.setInputStream(rdnu.getImage());// 取得带有随机字符串的图片
		setSession(ConstantUtil.RANDOM_COOKIE_NAME, rdnu.getString().toLowerCase());
		return SUCCESS;
	}
	
}
