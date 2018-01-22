package com.qmd.action.base;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.qmd.bean.BaseBean;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserInfo;
import com.qmd.service.html.HtmlService;
import com.qmd.service.user.UserAccountService;
import com.qmd.service.user.UserInfoService;
import com.qmd.service.user.UserService;
import com.qmd.util.*;
import com.qmd.util.redis.CacheUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ApiBaseAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7466250364788576721L;

	Logger log = Logger.getLogger(ApiBaseAction.class);

	private static final String HEADER_ENCODING = "UTF-8";
	private static final boolean HEADER_NO_CACHE = true;
	private static final String HEADER_TEXT_CONTENT_TYPE = "text/plain";
	private static final String HEADER_JSON_CONTENT_TYPE = "application/json";

	public static final String STATUS_PARAMETER_NAME = "status";// 操作状态参数名称
	public static final String MESSAGE_PARAMETER_NAME = "message";// 操作消息参数名称

	@Resource
	protected UserService userService;
	@Resource
	UserInfoService userinfoService;

	@Resource
	protected UserAccountService userAccountService;

	@Resource
	protected HtmlService htmlService;

	// @Resource
	// MemCachedClient memcachedClient;
	// public MemCachedClient getMemcachedClient() {
	// return memcachedClient;
	// }
	//
	// public void setMemcachedClient(MemCachedClient memcachedClient) {
	// this.memcachedClient = memcachedClient;
	// }


	protected String toBind(){


		User loginUser = getLoginUser();
		Map<String,String> pmap = new HashMap<String,String>();
		pmap.put("phone",getLoginUser().getPhone());

		Map mapxx=new HashMap();//输出
		if("2".equals(loginUser.getRealStatus())){
			mapxx.put("rcd", "M00020");
			mapxx.put("rmg", "请输入银行卡短信验证码");
		}else{

			mapxx.put("rcd", "M00010");
			mapxx.put("rmg", ApiConstantUtil.M00010);

		}

		return ajax(JsonUtil.toJson(mapxx));
	}

	protected String toBind(User loginUser){


		Map<String,String> pmap = new HashMap<String,String>();
		pmap.put("phone",getLoginUser().getPhone());

		Map mapxx=new HashMap();//输出
		if("2".equals(loginUser.getRealStatus())){
			mapxx.put("rcd", "M00020");
			mapxx.put("rmg", "请输入银行卡短信验证码");
		}else{

			mapxx.put("rcd", "M00010");
			mapxx.put("rmg", ApiConstantUtil.M00010);

		}

		return ajax(JsonUtil.toJson(mapxx));
	}

	// 操作状态（警告、错误、成功）
	public enum Status {
		warn, error, success
	}

	protected String token;// token

	protected String id;
	protected String[] ids;
	protected Pager pager = new Pager();
	protected String redirectUrl;// 跳转URL
	private String mycode;// 验证码

	protected String msg;
	protected String msgUrl;

	protected Integer pageNumber;
	protected Integer pageSize;
	protected String orderBy;
	protected String orderSort;

	public void initPage() {
		if (pager == null) {
			pager = new Pager();
		}
		if (pageNumber != null) {
			pager.setPageNumber(pageNumber);
		}
		if (pageSize != null) {
			pager.setPageSize(pageSize);
		}
	}

	// @Resource(name = "memberServiceImpl")
	// protected MemberService memberService;

	// 获取系统配置信息
	// public Setting getSetting() {
	// return SettingUtil.getSetting();
	// }

	public UserAccount getSelfaccountHeader() {
		return this.userAccountService.getUserAccountByUserId(getLoginUser()
				.getId());
	}

	public String getMycode() {
		return mycode.toLowerCase();
	}

	public void setMycode(String mycode) {
		this.mycode = mycode;
	}
	// 获取当前登录会员,若未登录则返回null
	// public User getLoginUser() {
	// Map<String,User> map = (Map<String, User>)
	// this.getMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME);
	// if(map==null){
	// return null;
	// }
	// User loginUser = map.get(ConstantUtil.USER_NAME);
	// return loginUser;
	// }
	public User getLoginUser() {
		// User user = (User) getSession(User.USER_ID_SESSION_NAME);
		// if (user == null) {
		// return null;
		// }
		// Map<String,Object> map = new HashMap<String,Object>();
		// map.put("id", userId);
		// User loginUser = userService.getUser(map);
		// return loginUser;
		// 首先要从redis里通过token获取user，如果redis出错了，则在catch代码里取从数据库里获取user
		User user = null;
		if (StringUtils.isNotEmpty(token)) {
			try {
				user = (User) CacheUtil.getObjValueWithOutCatch("user:"+token);
			} catch (Exception e) {
				e.printStackTrace();
				user = userService.getByToken(token);
			}
		}
		return user;
	}
	public CommonMess getCommMess() {
		return htmlService.buildCommonMess();
	}

	// 获取当前登录会员详细信息,若未登录则返回null
	public UserInfo getLoginUserInfo() {
		// Map<String,UserInfo> map = (Map<String, UserInfo>)
		// this.getMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME);
		// if(map==null){
		// return null;
		// }
		// UserInfo loginUserInfo = map.get(ConstantUtil.USERINFO_NAME);
		// return loginUserInfo;
		// UserInfo userInfo = (UserInfo)
		// getSession(UserInfo.USER_INFO_ID_SESSION_NAME);
		// if (userInfo == null) {
		// return null;
		UserInfo userInfo = userinfoService
				.findByUserId(getLoginUser().getId());
		setSession(UserInfo.USER_INFO_ID_SESSION_NAME, userInfo);// TODO 这行以后要删掉
		// }
		return userInfo;
	}

	/**
	 * 重新载入当前登录会员信息
	 * 
	 * @return
	 */
	public UserInfo reloadLoginUserInfo() {
		UserInfo userInfo = userinfoService.findByUserId(getLoginUser().getId());
		setSession(UserInfo.USER_INFO_ID_SESSION_NAME, userInfo);// TODO 这行以后要删掉
		return userInfo;
	}

	/**
	 * 重新载入会员信息
	 */
	public void reloadUser() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", getLoginUser().getUsername());
		User user = this.userService.getUser(map);
		CacheUtil.setObjValue("user:"+user.getToken(), user);
	}

	// 判断是否为添加
	public Boolean getIsAddAction() {
		if (id == null) {
			return true;
		} else {
			return false;
		}
	}

	// 判断是否为编辑
	public Boolean getIsEditAction() {
		if (id != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * //设置Memcached public void setMemcachedByCookie(String cookieName,Object
	 * memcachedObject){ String uuid = CommonUtil.getUUID();
	 * this.setCookie(cookieName, uuid); this.getMemcachedClient().set(uuid,
	 * memcachedObject); }
	 * 
	 * //获取Memcached public Object getMemcachedByCookie(String cookieName){
	 * String uuid = this.getCookie(cookieName); return
	 * this.getMemcachedClient().get(uuid); }
	 * 
	 * //修改对象后更新Memcached的值 public void replaceMemcachedByCookie(String
	 * cookieName,Object memcachedObject){ String uuid =
	 * this.getCookie(cookieName); this.getMemcachedClient().replace(uuid,
	 * memcachedObject); }
	 * 
	 * /** 更新Memcached
	 * 
	 * @param cookieName
	 *            cookie名称
	 * @param memcachedName
	 *            需要更新的memcached名称
	 * @param object
	 *            需要更新的对象
	 */
	// @SuppressWarnings("unchecked")
	// public void replaceMemcachedByCookie(String cookieName,String
	// memcachedName,Object object){
	// Map<String,Object> map = (Map<String, Object>)
	// this.getMemcachedByCookie(cookieName);
	// map.put(memcachedName, object);
	// String uuid = this.getCookie(cookieName);
	// this.getMemcachedClient().replace(uuid, map);
	// }

	/**
	 * //清除Memcached同时清除Cookie public void removeMemcached(String cookieName){
	 * String uuid = this.getCookie(cookieName); this.removeCookie(cookieName);
	 * this.getMemcachedClient().delete(uuid); }
	 **/
	// 获取Request
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	// 获取Response
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	// 获取ServletContext
	protected ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	// 获取Attribute
	protected Object getAttribute(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}

	// 设置Attribute
	protected void setAttribute(String name, Object value) {
		ServletActionContext.getRequest().setAttribute(name, value);
	}

	// 获取Parameter
	protected String getParameter(String name) {
		return ServletActionContext.getRequest().getParameter(name);
	}

	// 获取Parameter数组
	protected String[] getParameterValues(String name) {
		return ServletActionContext.getRequest().getParameterValues(name);
	}

	// 获取Session
	protected Object getSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session.get(name);
	}

	// 设置Session
	protected void setSession(String name, Object value) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put(name, value);
	}

	// 移除Session
	protected void removeSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.remove(name);
	}

	// 获取Cookie
	protected String getCookie(String name) {
		Cookie cookies[] = ServletActionContext.getRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	// 设置Cookie
	protected void setCookie(String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(ServletActionContext.getRequest().getContextPath() + "/");
		ServletActionContext.getResponse().addCookie(cookie);
	}

	// 设置Cookie
	protected void setCookie(String name, String value, Integer maxAge) {
		Cookie cookie = new Cookie(name, value);
		if (maxAge != null) {
			cookie.setMaxAge(maxAge);
		}
		cookie.setPath(ServletActionContext.getRequest().getContextPath() + "/");
		System.out.println(ServletActionContext.getRequest().getContextPath()
				+ "/");
		ServletActionContext.getResponse().addCookie(cookie);
	}

	// 移除Cookie
	protected void removeCookie(String name) {
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		cookie.setPath(ServletActionContext.getRequest().getContextPath() + "/");
		ServletActionContext.getResponse().addCookie(cookie);
	}

	// 获取真实路径
	protected String getRealPath(String path) {
		return ServletActionContext.getServletContext().getRealPath(path);
	}

	// 获取ContextPath
	protected String getContextPath() {
		return ServletActionContext.getRequest().getContextPath();
	}

	// AJAX输出
	protected String ajax(String content, String contentType) {
		try {
			HttpServletResponse response = initResponse(contentType);
			
			String jsonpCallback = getRequest().getParameter("callback");//客户端请求参数
			if(StringUtils.isNotEmpty(jsonpCallback)){
				response.getWriter().write(jsonpCallback+"("+content+")");
			}else{
				response.getWriter().write(content);
			}
			response.getWriter().flush();

			log.info("      【响应内容】[" + content + "]");

		} catch (IOException e) {
			e.printStackTrace();
		}
		// return NONE;
		return null;
	}

	// 根据文本内容输出AJAX
	protected String ajax(String text) {
		return ajax(text, HEADER_TEXT_CONTENT_TYPE);
	}

	// 根据操作状态输出AJAX
	protected String ajax(Status status) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS_PARAMETER_NAME, status.toString());
		JsonUtil.toJson(response, jsonMap);
		// return NONE;
		return null;
	}

	// 根据操作状态、消息内容输出AJAX
	protected String ajax(Status status, String message) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		Map<String, String> jsonMap = new HashMap<String, String>();

		jsonMap.put(STATUS_PARAMETER_NAME, status.toString());
		jsonMap.put(MESSAGE_PARAMETER_NAME, message);
		JsonUtil.toJson(response, jsonMap);
		// return NONE;
		return null;
	}

	protected String ajaxJson(String code, String message) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		BaseBean bean = new BaseBean();
		bean.setRcd(code);
		bean.setRmg(message);
		JsonUtil.toJson(response, bean);
		String json = JsonUtil.toJson(bean);
		log.info("      【响应内容】[" + json + "]");

		// return NONE;
		return null;
	}

	// 根据Object输出AJAX
	protected String ajax(Object object) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		JsonUtil.toJson(response, object);
		// return NONE;
		return null;
	}

	// 根据boolean状态输出AJAX
	protected String ajax(boolean booleanStatus) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS_PARAMETER_NAME, booleanStatus);
		JsonUtil.toJson(response, jsonMap);
		// return NONE;
		return null;
	}

	@SuppressWarnings("unused")
	private HttpServletResponse initResponse(String contentType) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType(contentType + ";charset=" + HEADER_ENCODING);
		String pragma = getP();
		String value = getV();
		if (HEADER_NO_CACHE) {
			// response.addHeader( pragma, value);
			response.setDateHeader("Expires", 1L);
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS");
		return response;
	}

	/**
	 * 随机数 用于刷新页面校验码
	 * 
	 * @return
	 */
	public String getCommonRandomFlash() {

		return CommonUtil.getRandomString(8);
	}

	// 获取货币格式字符串
	public String getCurrencyFormat() {
		return CommonUtil.getCurrencyFormat();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	private String getP() {
		return new StringBuffer("yB-derewoP").reverse().toString();
	}

	private String getV() {
		return new StringBuffer("+").append("+").append("TSR").reverse()
				.toString();
	}

	public String obtainUserIp() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public String getRequestRemoteAddr() {

		String myip = getRequest().getHeader("x-forwarded-for");
		myip = CommonUtil.ipSplit(myip);
		if (myip == null) {
			myip = getRequest().getHeader("X-Real-IP");
		}
		if (myip == null) {
			myip = getRequest().getRemoteAddr();
		}
		return myip;
	}

	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsgUrl() {
		return msgUrl;
	}

	public void setMsgUrl(String msgUrl) {
		this.msgUrl = msgUrl;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderSort() {
		return orderSort;
	}

	public void setOrderSort(String orderSort) {
		this.orderSort = orderSort;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
