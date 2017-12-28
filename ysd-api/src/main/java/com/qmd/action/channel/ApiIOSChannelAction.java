package com.qmd.action.channel;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.mode.place.IosIdfa;
import com.qmd.service.place.IosIdfaService;
import org.apache.struts2.convention.annotation.Action;

import javax.annotation.Resource;
import java.util.Date;

public class ApiIOSChannelAction extends ApiBaseAction{


	private static final long serialVersionUID = -8091006859195214256L;
	
	private String idfa;
	private String ip;
	private String callback;
	private String source;
	
	private String drkey;
	
	private String appid;
	private String channel;
	private String mac;
	
	private String udid;
	private String app;
	private String os;
	private String callbackurl;

	private String imei;
	
	@Resource
	private IosIdfaService iosIdfaService;
	

	/**
	 * 点乐
	 * @return
	 */
	@Action(value = "/api/adclickDianjoy")
	public String dianjoy(){
		IosIdfa entity = new IosIdfa();
		entity.setIdfa(idfa);
		entity.setIp(ip);
		entity.setCallback(callback);
		entity.setSource("02951618");
		entity.setCreateDate(new Date());
//		entity.setPlaceChilderId(Integer.parseInt(ConfigUtil.getConfigUtil().get("qmd.place.childer.dianjoy")));
		iosIdfaService.add(entity);
		return ajaxJson("R0001", "成功");
	}
	
	/**
	 * 点入
	 * @return
	 */
	@Action(value = "/api/adclickDianru")
	public String dianru(){
		IosIdfa entity = new IosIdfa();
		String idfaOrMac = drkey.substring(32);
		if(idfaOrMac.indexOf(":") == -1){
			entity.setIdfa(idfaOrMac);
		}else{
			entity.setMac(idfaOrMac);
		}
		callback = "http://api.mobile.dianru.com/callback/index.do?drkey=" + drkey;// TODO ip?
		entity.setCallback(callback);
		entity.setSource("34630415");
		entity.setCreateDate(new Date());
//		entity.setPlaceChilderId(Integer.parseInt(ConfigUtil.getConfigUtil().get("qmd.place.childer.dianru")));
		iosIdfaService.add(entity);
		return ajaxJson("R0001", "成功");
	}
	
	/**
	 * 趣米-ios
	 * @return
	 */
	@Action(value = "/api/adclickQumi")
	public String qumi(){
		try {
			IosIdfa entity = new IosIdfa();

			entity.setIdfa(idfa);
			entity.setCallback(callback);
			entity.setSource("74118517");
			entity.setCreateDate(new Date());
//		entity.setPlaceChilderId(Integer.parseInt(appid));
			entity.setMac(mac);		
			iosIdfaService.add(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return ajax("{\"message\":\"error\", \"success\":\"false\"}");
		}
		return ajax("{\"message\":\"success\", \"success\":\"true\"}");
	}

	/**
	 * 趣米-Android
	 * @return
	 */
	@Action(value = "/api/adclickQumiAndorid")
	public String qumiAndroid(){
		try {
			IosIdfa entity = new IosIdfa();

			entity.setIdfa(imei);
			entity.setIp(ip);
			entity.setCallback(callback);
			entity.setSource("71841562");
			entity.setCreateDate(new Date());
//		entity.setPlaceChilderId(Integer.parseInt(appid));
			entity.setMac(mac);		
			iosIdfaService.add(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return ajax("{\"message\":\"error\", \"success\":\"false\"}");
		}
		return ajax("{\"message\":\"success\", \"success\":\"true\"}");
	}
		
	/**
	 * 万普
	 * @return
	 */
	@Action(value = "/api/adclickWapx")
	public String wapx(){
		try {
			IosIdfa entity = new IosIdfa();
			entity.setUdid(udid);
			entity.setIdfa(idfa);
			entity.setOs(os);
			entity.setIp(ip);
			entity.setCallback(callbackurl);
			entity.setCreateDate(new Date());
			entity.setSource("14873916");
//		entity.setPlaceChilderId(Integer.parseInt(app));
			iosIdfaService.add(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return ajax("{\"message\":\"失败\",\"success\":false}");
		}
		return ajax("{\"message\":成功\",\"success\":true}");
	}
	
	
	/**
	 * 万普ios-2
	 * @return
	 */
	@Action(value = "/api/adclickWapxTwo")
	public String wapx2(){
		try {
			IosIdfa entity = new IosIdfa();
			entity.setUdid(udid);
			entity.setIdfa(idfa);
			entity.setOs(os);
			entity.setIp(ip);
			entity.setCallback(callbackurl);
			entity.setCreateDate(new Date());
			entity.setSource("404726108");
//		entity.setPlaceChilderId(Integer.parseInt(app));
			iosIdfaService.add(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return ajax("{\"message\":\"失败\",\"success\":false}");
		}
		return ajax("{\"message\":成功\",\"success\":true}");
	}
	
	
	
	
	
	

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDrkey() {
		return drkey;
	}

	public void setDrkey(String drkey) {
		this.drkey = drkey;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getCallbackurl() {
		return callbackurl;
	}

	public void setCallbackurl(String callbackurl) {
		this.callbackurl = callbackurl;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
}
