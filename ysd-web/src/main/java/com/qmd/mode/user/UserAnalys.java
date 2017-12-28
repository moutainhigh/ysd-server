package com.qmd.mode.user;

import java.io.Serializable;
import java.util.Date;
public class UserAnalys implements Serializable {
//
private Integer id;
//
private Date createDate;
//
private Date modifyDate;
//用户ID
private Integer userId;
//一级推广人用户ID
private Integer tgOneLevelUserId;
//渠道活动ID
private Integer placeChilderId;
private String placeName;
//设备的唯一标识【Android是44位;IOS是64位】
private String deviceToken;
//app类型【0:IOS;1:Android;2:其它】
private String appType;

private String imei;
//浏览器数据信息
private String browserType;
//备注
private String remark;
/**
 *  的取得
 * @return
 */
public Integer getId() {
    return id;
}
/**
 *  的设置
 * @param id
 */
public void setId(Integer id) {
    this.id = id;
}

/**
 *  的取得
 * @return
 */
public Date getCreateDate() {
    return createDate;
}
/**
 *  的设置
 * @param createDate
 */
public void setCreateDate(Date createDate) {
    this.createDate = createDate;
}

/**
 *  的取得
 * @return
 */
public Date getModifyDate() {
    return modifyDate;
}
/**
 *  的设置
 * @param modifyDate
 */
public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
}

/**
 * 用户ID 的取得
 * @return
 */
public Integer getUserId() {
    return userId;
}
/**
 * 用户ID 的设置
 * @param userId
 */
public void setUserId(Integer userId) {
    this.userId = userId;
}

/**
 * 一级推广人用户ID 的取得
 * @return
 */
public Integer getTgOneLevelUserId() {
    return tgOneLevelUserId;
}
/**
 * 一级推广人用户ID 的设置
 * @param tgOneLevelUserId
 */
public void setTgOneLevelUserId(Integer tgOneLevelUserId) {
    this.tgOneLevelUserId = tgOneLevelUserId;
}

/**
 * 渠道活动ID 的取得
 * @return
 */
public Integer getPlaceChilderId() {
    return placeChilderId;
}
/**
 * 渠道活动ID 的设置
 * @param placeChilderId
 */
public void setPlaceChilderId(Integer placeChilderId) {
    this.placeChilderId = placeChilderId;
}

/**
 * 设备的唯一标识【Android是44位;IOS是64位】 的取得
 * @return
 */
public String getDeviceToken() {
    return deviceToken;
}
/**
 * 设备的唯一标识【Android是44位;IOS是64位】 的设置
 * @param deviceToken
 */
public void setDeviceToken(String deviceToken) {
    this.deviceToken = deviceToken;
}

/**
 * app类型【0:IOS;1:Android;2:其它】 的取得
 * @return
 */
public String getAppType() {
    return appType;
}
/**
 * app类型【0:IOS;1:Android;2:其它】 的设置
 * @param appType
 */
public void setAppType(String appType) {
    this.appType = appType;
}

/**
 * 浏览器数据信息 的取得
 * @return
 */
public String getBrowserType() {
    return browserType;
}
/**
 * 浏览器数据信息 的设置
 * @param browserType
 */
public void setBrowserType(String browserType) {
    this.browserType = browserType;
}

/**
 * 备注 的取得
 * @return
 */
public String getRemark() {
    return remark;
}
/**
 * 备注 的设置
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

}