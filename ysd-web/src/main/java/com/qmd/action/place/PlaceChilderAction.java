package com.qmd.action.place;

import com.qmd.action.base.BaseAction;
import com.qmd.mode.place.PlaceChilder;
import com.qmd.mode.user.User;
import com.qmd.mode.util.Hongbao;
import com.qmd.mode.util.Scrollpic;
import com.qmd.service.place.PlaceChilderService;
import com.qmd.service.util.ListingService;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConfigUtil;
import com.qmd.util.ConstantUtil;
import com.qmd.util.HttpRequestDeviceUtils;
import com.qmd.util.md5.PWDUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("placeChilderAction")
public class PlaceChilderAction extends BaseAction {

	private static final long serialVersionUID = 7939222435914416706L;
	
	private String r;//

	private Scrollpic scrollpic;
	
	private PlaceChilder placeChilder;
	private Hongbao hongbao;
	private List<Scrollpic> huobanpicList;

	@Resource
	PlaceChilderService placeChilderService;
	@Resource
	ListingService listingService;
	
	/**
	 * zdl 修改的渠道注册页面
	 * @return
	 */
	@Action(value = "/place", results = {
			@Result(name = "qudao_app", location = "/content/spread/canals_app.ftl", type = "freemarker"),
			@Result(name = "qudao_pc", location = "/content/spread/canals.ftl", type = "freemarker"),
			//@Result(name = "qudao", location = "/content/spread/place.ftl", type = "freemarker"),
			@Result(name = "success", location = "/content/login/reg.ftl", type = "freemarker") })
	public String toPlace() {
		removeSession(User.USER_ID_SESSION_NAME);
		if(StringUtils.isNotEmpty(r)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("random", r);
			map.put("status", 1);
			List<PlaceChilder> list = placeChilderService.queryListByMap(map);
			if(list != null && list.size() >0){
				if(getCookie(ConstantUtil.PLACE_COOKIE_NAME) != null){
					placeChilder = list.get(0);
					placeChilder.setViewNum(placeChilder.getViewNum()+1);
					placeChilderService.update(placeChilder);
				}
				//加密 r
				String cookie = CommonUtil.encodeUrl("1_"+r);
				System.out.println(cookie +"，解密后="+CommonUtil.decodeUrl(cookie));
				setCookie(ConstantUtil.PLACE_COOKIE_NAME, cookie, ConstantUtil.PLACE_COOKIE_AGE);
				List<Scrollpic> lists = listingService.findScrollpicList(3);
				if(lists != null && lists.size() >0){
					scrollpic = lists.get(0);
				}
				//合作伙伴
				huobanpicList = listingService.findScrollpicList(1);
				//获取首次投资红包金额
				hongbao = listingService.getHongbao(2);
				
				//判定请求的设备类型
				if(HttpRequestDeviceUtils.isMobileDevice(getRequest())){
					return "qudao_app";
				}
				return "qudao_pc";
			}
		}
		
		return SUCCESS;
	
	}
	
	private User user;
	
	private String username;//用户名
	private String pwd;//密码
	private String code;//图形验证码
	private String smsCode;//手机号验证码
	

	/**
	 * 渠道推广-会员注册
	 * @return
	 */
	@Action(value="/ajaxNewPlace",results={@Result(type="json")})
	public String ajaxNewPlace() throws Exception{
		removeSession(User.USER_ID_SESSION_NAME);
		Object userPhoneNum = getSession(User.USER_PHONE_NUM); 
		removeSession(User.USER_PHONE_NUM);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(username)){
			jsonMap.put("rcd", "1");
			jsonMap.put("rmg", "请输入手机号");
			return ajax(jsonMap);
		}
		 Pattern p =  Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		 Matcher m = p.matcher(username);
		 if(!m.matches()){
			 jsonMap.put("rcd", "1");
			 jsonMap.put("rmg", "手机号格式不正确");
			 return ajax(jsonMap);
		 }
		
		if(StringUtils.isEmpty(pwd)){
			 jsonMap.put("rcd", "2");
			 jsonMap.put("rmg", "请输入密码");
			 return ajax(jsonMap);
		}
		if(!(pwd.matches(".*[a-zA-Z]+.*$"))){
			 jsonMap.put("rcd", "2");
			 jsonMap.put("rmg", "密码必须包含字母！");
			 return ajax(jsonMap);
		}
		if(pwd.length()<8 || pwd.length()>16){
			 jsonMap.put("rcd", "2");
			 jsonMap.put("rmg", "密码长度必须在8-16个字符之间！");
			 return ajax(jsonMap);
		}

		if(StringUtils.isEmpty(code)){
			 jsonMap.put("rcd", "3");
			 jsonMap.put("rmg", "请输入图形验证码");
			 return ajax(jsonMap);
		}
		
//		 String random = (String)getSession(ConstantUtil.RANDOM_COOKIE_NAME);
//		 removeSession(ConstantUtil.RANDOM_COOKIE_NAME);
//		 if(!code.equalsIgnoreCase(random)){
//			 jsonMap.put("rcd", "3");
//			 jsonMap.put("rmg", "图形验证码输入错误");
//			 return ajax(jsonMap);
//		 }
		
		
		/**yueshang*/
        if (ConfigUtil.isProdEnv()) {
			if(StringUtils.isEmpty(smsCode)){
				 jsonMap.put("rcd", "4");
				 jsonMap.put("rmg", "请输入短信验证码");
				 return ajax(jsonMap);
			}
			if(userPhoneNum ==null) {
				jsonMap.put("rcd", "4");
				 jsonMap.put("rmg", "请发送短信验证码");
				 return ajax(jsonMap);
			} 
			if(!smsCode.equals(userPhoneNum)){
				 jsonMap.put("rcd", "4");
				 jsonMap.put("rmg", "短信验证码不正确");
				 return ajax(jsonMap);
			}
        }
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("username",username);
		map.put("tempFlg", 0);
		if(this.userService.getUser(map) != null){
			 jsonMap.put("rcd", "1");
			 jsonMap.put("rmg", "用户已存在");
			 return ajax(jsonMap);
		}
		

		User user = new User();
		user.setUsername(username);
		user.setPhone(username);
		user.setPassword(pwd);
		
		//用户基本信息添加
		user.setTypeId(0);
		user.setMemberType(0);
		
		String random = CommonUtil.getRandomString(8);
		user.setRandomNum(random);//保存随机8位密码
		user.setPassword(PWDUtil.encode(user.getPassword(), random));//对密码进行加密处理
		
		user.setAddIp(getRequestRemoteAddr());
		user.setLastIp(getRequestRemoteAddr());
		user.setPhoneStatus(1);
		

		String placeCookie = getCookie(ConstantUtil.PLACE_COOKIE_NAME);
		if(StringUtils.isNotEmpty(placeCookie)){
			placeCookie = CommonUtil.decodeUrl(placeCookie);
			String flag = placeCookie.split("_")[0];
			if("1".equals(flag)){//渠道活动注册用户
				String spreadRandom = placeCookie.split("_")[1];
				map.clear();
				map.put("random", spreadRandom);
				map.put("status", 1);
				List<PlaceChilder> list = placeChilderService.queryListByMap(map);
				if(list != null && list.size() >0){
					PlaceChilder pc = list.get(0);
					user.setPlaceChilderId(pc.getId());
				}
			}else if ("0".equals(flag)){
				String tgNo = placeCookie.split("_")[1];
				User u = new User();
				u.setTgNo(tgNo);
				List<User> user_list = userService.queryListByObject(u);
				if (user_list != null && user_list.size() == 1) {
					u = user_list.get(0);
					user.setTgOneLevelUserId(u.getId());// 一级推广人
				}
			}
		}
		String agent = getRequest().getHeader("user-agent"); 
		user.setBrowserType(agent);
		
		this.userService.addUser(user);
		

		// 写入会员登录Session
		setSession(User.USER_ID_SESSION_NAME, user);
		// 写入会员登录Cookie
		setCookie(User.USER_USERNAME_COOKIE_NAME, URLEncoder.encode(user.getUsername().toLowerCase(), "UTF-8"),User.USER_COOKIE_AGE);
		
		setCookie(User.USER_USERTYPE_COOKIE_NAME, URLEncoder.encode(user.getTypeId().toString(), "UTF-8"),User.USER_COOKIE_AGE);
		
		setCookie(User.USER_USEREALSTATUS_COOKIE_NAME, URLEncoder.encode("0", "UTF-8"),User.USER_COOKIE_AGE);

		 jsonMap.put("rcd", "R0001");
		 jsonMap.put("rmg", "注册成功");
		 return ajax(jsonMap);
		
	}
	
	@Action(value = "/regSuccess", results = {
			@Result(name = "success", location = "/content/spread/success.ftl", type = "freemarker") })
	public String regSuccess() {
		if(StringUtils.isNotEmpty(id)){
			placeChilder = placeChilderService.get(Integer.parseInt(id));
		}
		return SUCCESS;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public Scrollpic getScrollpic() {
		return scrollpic;
	}

	public void setScrollpic(Scrollpic scrollpic) {
		this.scrollpic = scrollpic;
	}

	public PlaceChilder getPlaceChilder() {
		return placeChilder;
	}

	public void setPlaceChilder(PlaceChilder placeChilder) {
		this.placeChilder = placeChilder;
	}

	public Hongbao getHongbao() {
		return hongbao;
	}

	public void setHongbao(Hongbao hongbao) {
		this.hongbao = hongbao;
	}

	public List<Scrollpic> getHuobanpicList() {
		return huobanpicList;
	}

	public void setHuobanpicList(List<Scrollpic> huobanpicList) {
		this.huobanpicList = huobanpicList;
	}
	
	
}
