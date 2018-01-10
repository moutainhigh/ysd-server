package com.qmd.mode.phone;
import java.io.Serializable;
import java.util.Date;
public class PhoneLimit implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = -658971777067276450L;
	
//系统id
private Integer id;
//发短信者ip
private String ip;
//手机号码
private String phone;
//动活id
private Integer activityId;

private Integer sendNumber;//发送次数

//建创时间
private Date createDate;
/**
 * 系统id 的取得
 * @return
 */
public Integer getId() {
    return id;
}
/**
 * 系统id 的设置
 * @param id
 */
public void setId(Integer id) {
    this.id = id;
}

/**
 * 发短信者ip 的取得
 * @return
 */
public String getIp() {
    return ip;
}
/**
 * 发短信者ip 的设置
 * @param ip
 */
public void setIp(String ip) {
    this.ip = ip;
}

/**
 * 手机号码 的取得
 * @return
 */
public String getPhone() {
    return phone;
}
/**
 * 手机号码 的设置
 * @param phone
 */
public void setPhone(String phone) {
    this.phone = phone;
}

/**
 * 动活id 的取得
 * @return
 */
public Integer getActivityId() {
    return activityId;
}
/**
 * 动活id 的设置
 * @param activityId
 */
public void setActivityId(Integer activityId) {
    this.activityId = activityId;
}

/**
 * 建创时间 的取得
 * @return
 */
public Date getCreateDate() {
    return createDate;
}
/**
 * 建创时间 的设置
 * @param createDate
 */
public void setCreateDate(Date createDate) {
    this.createDate = createDate;
}
public Integer getSendNumber() {
	return sendNumber;
}
public void setSendNumber(Integer sendNumber) {
	this.sendNumber = sendNumber;
}

}