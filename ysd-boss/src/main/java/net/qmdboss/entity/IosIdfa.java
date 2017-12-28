package net.qmdboss.entity;

import javax.persistence.Entity;

/**
 * IOS 推广过来手机信息
 * @author Administrator
 *
 */
@Entity
public class IosIdfa extends BaseEntity {
	
	private static final long serialVersionUID = 1896464260836478377L;
	// 唯一标识
	private String idfa;
	// ip地址
	private String ip;
	// 点入唯一标识
	private String drkey;
	// 来源
	private String source;
	// 渠道活动id，也是趣米等的appid
	private Integer placeChilderId;
	// 物理地址
	private String mac;
	// 用户标识(万普)
	private String udid;
	// 苹果设备系统版本号
	private String os;
	// 回调url
	private String callback;
	// 状态:1已绑定
	private Integer status;


	/**
	 * 唯一标识 的取得
	 * 
	 * @return
	 */
	public String getIdfa() {
		return idfa;
	}

	/**
	 * 唯一标识 的设置
	 * 
	 * @param idfa
	 */
	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}

	/**
	 * ip地址 的取得
	 * 
	 * @return
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * ip地址 的设置
	 * 
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 点入唯一标识 的取得
	 * 
	 * @return
	 */
	public String getDrkey() {
		return drkey;
	}

	/**
	 * 点入唯一标识 的设置
	 * 
	 * @param drkey
	 */
	public void setDrkey(String drkey) {
		this.drkey = drkey;
	}

	/**
	 * 来源 的取得
	 * 
	 * @return
	 */
	public String getSource() {
		return source;
	}

	/**
	 * 来源 的设置
	 * 
	 * @param source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * 渠道活动id，也是趣米等的appid 的取得
	 * 
	 * @return
	 */
	public Integer getPlaceChilderId() {
		return placeChilderId;
	}

	/**
	 * 渠道活动id，也是趣米等的appid 的设置
	 * 
	 * @param placeChilderId
	 */
	public void setPlaceChilderId(Integer placeChilderId) {
		this.placeChilderId = placeChilderId;
	}

	/**
	 * 物理地址 的取得
	 * 
	 * @return
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * 物理地址 的设置
	 * 
	 * @param mac
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}

	/**
	 * 用户标识(万普) 的取得
	 * 
	 * @return
	 */
	public String getUdid() {
		return udid;
	}

	/**
	 * 用户标识(万普) 的设置
	 * 
	 * @param udid
	 */
	public void setUdid(String udid) {
		this.udid = udid;
	}

	/**
	 * 苹果设备系统版本号 的取得
	 * 
	 * @return
	 */
	public String getOs() {
		return os;
	}

	/**
	 * 苹果设备系统版本号 的设置
	 * 
	 * @param os
	 */
	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * 回调url 的取得
	 * 
	 * @return
	 */
	public String getCallback() {
		return callback;
	}

	/**
	 * 回调url 的设置
	 * 
	 * @param callback
	 */
	public void setCallback(String callback) {
		this.callback = callback;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}