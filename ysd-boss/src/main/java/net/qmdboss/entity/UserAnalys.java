package net.qmdboss.entity;

import javax.persistence.Entity;

@Entity
public class UserAnalys extends BaseEntity {

	private static final long serialVersionUID = -3911186225388835969L;

	// 用户ID
	private Integer userId;
	// 一级推广人用户ID
	private Integer tgOneLevelUserId;
	// 渠道活动ID
	private Integer placeChilderId;
	private String placeName;
	// 设备的唯一标识【Android是44位;IOS是64位】
	private String deviceToken;

	private String imei;

	private String idfa;

	private String mac;
	// app类型【0:IOS;1:Android;2:其它】
	private String appType;
	// 浏览器数据信息
	private String browserType;
	// 备注
	private String remark;

	/**
	 * 用户ID 的取得
	 * 
	 * @return
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * 用户ID 的设置
	 * 
	 * @param userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 一级推广人用户ID 的取得
	 * 
	 * @return
	 */
	public Integer getTgOneLevelUserId() {
		return tgOneLevelUserId;
	}

	/**
	 * 一级推广人用户ID 的设置
	 * 
	 * @param tgOneLevelUserId
	 */
	public void setTgOneLevelUserId(Integer tgOneLevelUserId) {
		this.tgOneLevelUserId = tgOneLevelUserId;
	}

	/**
	 * 渠道活动ID 的取得
	 * 
	 * @return
	 */
	public Integer getPlaceChilderId() {
		return placeChilderId;
	}

	/**
	 * 渠道活动ID 的设置
	 * 
	 * @param placeChilderId
	 */
	public void setPlaceChilderId(Integer placeChilderId) {
		this.placeChilderId = placeChilderId;
	}

	/**
	 * 设备的唯一标识【Android是44位;IOS是64位】 的取得
	 * 
	 * @return
	 */
	public String getDeviceToken() {
		return deviceToken;
	}

	/**
	 * 设备的唯一标识【Android是44位;IOS是64位】 的设置
	 * 
	 * @param deviceToken
	 */
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	/**
	 * app类型【0:IOS;1:Android;2:其它】 的取得
	 * 
	 * @return
	 */
	public String getAppType() {
		return appType;
	}

	/**
	 * app类型【0:IOS;1:Android;2:其它】 的设置
	 * 
	 * @param appType
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}

	/**
	 * 浏览器数据信息 的取得
	 * 
	 * @return
	 */
	public String getBrowserType() {
		return browserType;
	}

	/**
	 * 浏览器数据信息 的设置
	 * 
	 * @param browserType
	 */
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	/**
	 * 备注 的取得
	 * 
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 备注 的设置
	 * 
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

}