package com.qmd.mode.util;

import java.io.Serializable;
import java.util.Date;


/**
 * 首页图片滚动
 * @author Xsf
 *
 */

public class Scrollpic implements Serializable{

	private static final long serialVersionUID = -5580185060470750989L;
	private Integer id;//编号
    private Date createDate;
    private Date modifyDate;
	private String name;// 名称
	private String url;// 链接地址;
	private Boolean isVisible;// 是否显示
	private Boolean isBlankTarget;// 是否在新窗口中打开
	private Boolean isApp;//app端
	private Integer orderList;// 排序
	private String img;//图片
	private String remark;//描述
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	public Boolean getIsBlankTarget() {
		return isBlankTarget;
	}
	public void setIsBlankTarget(Boolean isBlankTarget) {
		this.isBlankTarget = isBlankTarget;
	}
	public Integer getOrderList() {
		return orderList;
	}
	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Boolean getIsApp() {
		return isApp;
	}
	public void setIsApp(Boolean isApp) {
		this.isApp = isApp;
	}

}
