package com.qmd.mode.user;

import java.io.Serializable;
import java.util.Date;
public class UserStartingApp implements Serializable {
//
private Integer id;
//
private Date createDate;
//
private Date modifyDate;
//设备的唯一标识【Android是44位;IOS是64位】
private String deviceToken;
//app类型【0:IOS;1:Android】
private String appType;

private String placeName;

private Integer placeChilderId;

private String imei;
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
 * app类型【0:IOS;1:Android】 的取得
 * @return
 */
public String getAppType() {
    return appType;
}
/**
 * app类型【0:IOS;1:Android】 的设置
 * @param appType
 */
public void setAppType(String appType) {
    this.appType = appType;
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
public Integer getPlaceChilderId() {
	return placeChilderId;
}
public void setPlaceChilderId(Integer placeChilderId) {
	this.placeChilderId = placeChilderId;
}

}